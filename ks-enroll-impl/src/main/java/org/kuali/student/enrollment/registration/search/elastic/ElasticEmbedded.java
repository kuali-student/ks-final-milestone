package org.kuali.student.enrollment.registration.search.elastic;

import com.google.common.collect.Iterables;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.joda.time.DateTime;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.dto.CourseSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * A simple class that instantiates an elasticsearch Node and obtains a client
 * This is intended for use as a singleton inside a spring context
 */
public class ElasticEmbedded {

    public static final Logger LOG = Logger.getLogger(ElasticEmbedded.class);
    public static final String KS_ELASTIC_CLUSTER = "ks.elastic.cluster";
    public static final String KS_ELASTIC_INDEX = "ks";
    public static final String COURSEOFFERING_ELASTIC_TYPE = "courseoffering";
    public static final String REGISTRATION_GROUP_ELASTIC_TYPE = "registrationGroup";

    private SearchService searchService;
    private LRCService lrcService;
    private ScheduleOfClassesService scheduleOfClassesService;
    private Node node;
    private Client client;
    private boolean indexOnStartup = true;

    public ElasticEmbedded() throws IOException {
        super();
    }

    private DateTime lastUpdated; //Keep track of timeout for the elastic "cache"
    private long timeToRefreshMs = (5 * 60 * 60 * 1000); //Max time before refreshing the cache/reindexing
    private final static int PARTITION_SIZE = 10000; // for large data sets we should partition

    /**
     * Starts up a node and gets a handle to the client. This is a hook for spring application context to start up
     * embedded elastic during application startup.
     *
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws IOException
     * @throws DoesNotExistException
     */
    public void init() throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, IOException, DoesNotExistException {
        LOG.info("Starting Elastic Client");

        //Start a local client
        node = NodeBuilder.nodeBuilder().local(true).clusterName(KS_ELASTIC_CLUSTER).node();
        client = node.client();

        //Wait for yellow status to avoid errors for bulk insertion
        client.admin().cluster().prepareHealth().setWaitForYellowStatus().execute().actionGet();

        boolean indexExists = client.admin().indices().prepareExists(KS_ELASTIC_INDEX).execute().actionGet().isExists();
        if (indexExists) {
            LOG.info("index exists. Deleting index then reinitializing.");
            client.admin().indices().prepareDelete(KS_ELASTIC_INDEX).execute().actionGet();  // on init always delete existing indexes
        }
        client.admin().indices().prepareCreate(KS_ELASTIC_INDEX).execute().actionGet();  // create new index

        applyCourseOfferingIndexMappings();
        applyRegistrationGroupIndexMappings();  // apply mappings for registration groups.

        LOG.info("Elastic Client Started");

        //Prefetch the data
        if (indexOnStartup) {
            getClient();
        }
    }

    /**
     * This method pushes course data into elastic search
     *
     * @throws DoesNotExistException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws IOException
     */
    private void indexCourseOfferingData() throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, IOException {
        LOG.info("Loading Course Offering Data into Elastic");
        Date startTime = new Date();

        //Grab all the data from the services
        List<CourseSearchResult> courses = getAllCourseOfferings();
        indexCourseOfferingData(courses);
    }

    private void indexCourseOfferingData(List<CourseSearchResult> courses) throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, IOException {
        LOG.info("Loading Course Offering Data into Elastic");
        Date startTime = new Date();

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        ObjectMapper mapper = new ObjectMapper();

        //Create a bulk request to push all data into elastic
        for (CourseSearchResult searchResult : courses) {
            String json = mapper.writeValueAsString(searchResult);
            bulkRequest.add(client.prepareIndex(KS_ELASTIC_INDEX, COURSEOFFERING_ELASTIC_TYPE, searchResult.getCourseId()).setSource(json));
        }

        //Execute the bulk operation
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            throw new RuntimeException("Error Bulk Loading elasticsearch courseofferings: " + bulkResponse.buildFailureMessage());
        }

        LOG.info("Done Loading Course Offering Data - " + (System.currentTimeMillis() - startTime.getTime()) + "ms");
    }

    //Turn of index analysis for some fields so they do exact matches (Ids with "." or "-" in them were treated as
    // multiple terms instead of a single string)
    private void applyCourseOfferingIndexMappings() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder().
                startObject().
                    startObject(COURSEOFFERING_ELASTIC_TYPE).
                        startObject("properties").
                            startObject("cluId").
                                field("type", "string").field("store", "yes").field("index", "not_analyzed").
                            endObject().
                            startObject("courseId").
                                field("type", "string").field("store", "yes").field("index", "not_analyzed").
                            endObject().
                            startObject("termId").
                                field("type", "string").field("store", "yes").field("index", "not_analyzed").
                            endObject().
                            startObject("state").
                                field("type", "string").field("store", "yes").field("index", "not_analyzed").
                            endObject().
                            startObject("courseIdentifierType").
                                field("type", "string").field("store", "yes").field("index", "not_analyzed").
                            endObject().
                        endObject().
                    endObject().
                endObject();
        client.admin().indices().preparePutMapping(KS_ELASTIC_INDEX).setType(COURSEOFFERING_ELASTIC_TYPE).setSource(builder).execute().actionGet();

    }

    private void applyRegistrationGroupIndexMappings() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder().
                startObject().
                startObject(REGISTRATION_GROUP_ELASTIC_TYPE).
                startObject("properties").
                startObject("courseOfferingId").
                field("type", "string").field("store", "yes").field("index", "not_analyzed").
                endObject().
                startObject("regGroupId").
                field("type", "string").field("store", "yes").field("index", "not_analyzed").
                endObject().
                startObject("regGroupState").
                field("type", "string").field("store", "yes").field("index", "not_analyzed").
                endObject().
                startObject("termId").
                field("type", "string").field("store", "yes").field("index", "not_analyzed").
                endObject().
                startObject("activityOfferingIds").
                field("type", "string").field("store", "yes").field("index", "not_analyzed").
                endObject().
                // more mapping
                        endObject().
                endObject().
                endObject();
        client.admin().indices().preparePutMapping(KS_ELASTIC_INDEX).setType(REGISTRATION_GROUP_ELASTIC_TYPE).setSource(builder).execute().actionGet();

    }

    private void indexRegistrationGroupData() throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, IOException {
        //Grab all the data from the services
        Collection<RegGroupSearchResult> allRegGroups = getAllRegistrationGroups();

        indexRegistrationGroupData(allRegGroups);
    }

    private void indexRegistrationGroupData(Collection<RegGroupSearchResult> registrationGroups) throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, IOException {
        LOG.info("Loading Registration Group Data into Elastic");
        Date startTime = new Date();

        // break up large set into smaller pieces
        Iterable<List<RegGroupSearchResult>> regGroupSubSets = Iterables.partition(registrationGroups, PARTITION_SIZE);

        Iterator<List<RegGroupSearchResult>> iterator = regGroupSubSets.iterator();
        int partition = 0;
        while (iterator.hasNext()) {
            List<RegGroupSearchResult> regGroups = iterator.next();
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            ObjectMapper mapper = new ObjectMapper();

            //Create a bulk request to push all data into elastic
            for (RegGroupSearchResult searchResult : regGroups) {
                String json = mapper.writeValueAsString(searchResult);
                bulkRequest.add(client.prepareIndex(KS_ELASTIC_INDEX, REGISTRATION_GROUP_ELASTIC_TYPE, searchResult.getRegGroupId()).setSource(json));
            }

            //Execute the bulk operation
            BulkResponse bulkResponse = bulkRequest.execute().actionGet();
            if (bulkResponse.hasFailures()) {
                throw new RuntimeException("Error Bulk Loading elasticsearch courseofferings: " + bulkResponse.buildFailureMessage());
            }

            LOG.info("Done Loading Registration Group Partition: " + ++partition);
        }

        LOG.info("Done Loading Reg Group Data - " + (System.currentTimeMillis() - startTime.getTime()) + "ms");
    }

    /**
     * Method to grab all of the course offerings from a search service (get the data that will be pushed into elastic)
     *
     * @return list of all active course search results
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     */
    private List<CourseSearchResult> getAllCourseOfferings() throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        List<CourseSearchResult> courseSearchResults = scheduleOfClassesService.getCourseOfferings(null, null, ContextUtils.createDefaultContextInfo());

        return courseSearchResults;
    }

    /**
     * Pass in a list of course offering ids. We will run a query for those ids and update the cache.
     * @param courseOfferingIds
     * @throws DoesNotExistException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws IOException
     */
    public void updateCourseOfferingCache(List<String> courseOfferingIds) throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, IOException {
        LOG.info("Updating index for course offering ids: " + courseOfferingIds);
        List<CourseSearchResult> courseSearchResults = scheduleOfClassesService.getCourseOfferings(courseOfferingIds, null, ContextUtils.createDefaultContextInfo());

        if(courseSearchResults != null && !courseSearchResults.isEmpty()){
           indexCourseOfferingData(courseSearchResults);
        }
    }

    /**
     * Pass in a list of registration group ids. We will run a query for those ids and update the cache.
     * @param registrationGroupIds
     * @throws DoesNotExistException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws IOException
     */
    public void updateRegistrationGroupCache(List<String> registrationGroupIds) throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, IOException {
        LOG.info("Updating index for registration group ids: " + registrationGroupIds);
        Collection<RegGroupSearchResult> regGroupSearchResults = scheduleOfClassesService.getRegGroups(registrationGroupIds, ContextUtils.createDefaultContextInfo());

        if(regGroupSearchResults != null && !regGroupSearchResults.isEmpty()){
            indexRegistrationGroupData(regGroupSearchResults);
        }
    }

    protected Collection<RegGroupSearchResult> getAllRegistrationGroups() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ContextInfo context = ContextUtils.createDefaultContextInfo();
        // the null search returns all registration groups
        return scheduleOfClassesService.searchForRegGroups(null, context);
    }

    /**
     * Hook to close the node, should be called by spring application context.
     */
    public void shutdown() {
        node.close();
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * This method is quick way of keeping the indexed course data up-to-date by refreshing every timeToRefreshMs.
     *
     * @return an elastic search client
     */
    public synchronized Client getClient() {
        if (lastUpdated == null || DateTime.now().getMillis() > (lastUpdated.getMillis() + timeToRefreshMs)) {
            try {
                if (lastUpdated == null) {
                    //If this is a first time run, block while indexing
                    indexCourseOfferingData();
                    indexRegistrationGroupData();
                } else {
                    //Otherwise async start a reindex and continue with stale data
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                indexCourseOfferingData();
                            } catch (Exception e) {
                                throw new RuntimeException("Error updating course offering data", e);
                            }
                        }
                    } .start();
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                indexRegistrationGroupData();
                            } catch (Exception e) {
                                throw new RuntimeException("Error updating reg group data", e);
                            }
                        }
                    } .start();
                }
            } catch (Exception e) {
                throw new RuntimeException("Error updating data", e);
            }

            lastUpdated = DateTime.now();
        }

        return client;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    public synchronized void setTimeToRefreshMs(long timeToRefreshMs) {
        this.timeToRefreshMs = timeToRefreshMs;
    }

    public void setScheduleOfClassesService(ScheduleOfClassesService scheduleOfClassesService) {
        this.scheduleOfClassesService = scheduleOfClassesService;
    }

    public boolean isIndexOnStartup() {
        return indexOnStartup;
    }

    /**
     * when the application starts, this allows you to disable the automatic index.
     *
     * By default this is set to true
     * @param indexOnStartup
     */
    public void setIndexOnStartup(boolean indexOnStartup) {
        this.indexOnStartup = indexOnStartup;
    }
}
