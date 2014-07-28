package org.kuali.student.enrollment.registration.search.elastic;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.registration.client.service.dto.CourseSearchResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.SearchResultHelper;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple class that instantiates an elasticsearch Node and obtains a client
 * This is intended for use as a singleton inside a spring context
 */
public class ElasticEmbedded {

    public static final Logger LOG = Logger.getLogger(ElasticEmbedded.class);

    private SearchService searchService;
    private LRCService lrcService;
    private Node node;
    private Client client;

    public ElasticEmbedded() throws IOException {
        super();
    }

    private Date lastUpdated; //Keep track of timeout for the elastic "cache"
    private static final long TIME_TO_REFRESH_MS = (5 * 60 * 1000); //Max time before refreshing the cache/reindexing

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
        node = NodeBuilder.nodeBuilder().local(false).clusterName("ks.elastic.cluster").node();
        client = node.client();
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
        LOG.info("Loading Data into Elastic");
        Date startTime = new Date();

        //Grab all the data from the services
        List<CourseSearchResult> courses = getAllCourseOfferings();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        ObjectMapper mapper = new ObjectMapper();

        //Create a bulk request to push all data into elastic
        for (CourseSearchResult searchResult : courses) {
            String json = mapper.writeValueAsString(searchResult);
            bulkRequest.add(client.prepareIndex("ks", "courseoffering", searchResult.getCourseId()).setSource(json));
        }

        //Execute the bulk operation
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            throw new RuntimeException("Error Bulk Loading elasticsearch courseofferings");
        }

        LOG.info("Done Loading Data - " + (System.currentTimeMillis() - startTime.getTime()) + "ms");
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

        List<CourseSearchResult> courseSearchResults = new ArrayList<>();

        //Use search service to grab the course data
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.CO_SEARCH_INFO_SEARCH_KEY);
        ContextInfo context = ContextUtils.createDefaultContextInfo();

        SearchResult searchResults = searchService.search(searchRequest, context);

        //Process the results
        for (SearchResultHelper.KeyValue keyValue : SearchResultHelper.wrap(searchResults)) {

            CourseSearchResult courseSearchResult = new CourseSearchResult();
            courseSearchResult.setCourseCode(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_CODE));
            courseSearchResult.setCourseId(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_ID));
            courseSearchResult.setCourseLevel(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_LEVEL));
            courseSearchResult.setCourseNumber(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.COURSE_NUMBER));
            courseSearchResult.setCoursePrefix(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.COURSE_DIVISION));
            courseSearchResult.setLongName(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_LONG_NAME));
            courseSearchResult.setSeatsAvailable(Integer.parseInt(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.SEATS_AVAILABLE)));
            courseSearchResult.setTermId(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.ATP_ID));
            courseSearchResult.setCourseDescription(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_DESC));

            // Grab Learning Results data
            // These two calls are cached so they are actually more performant
            List<String> creditResultsValueKeys = lrcService.getResultValuesGroup(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CREDITS), context).getResultValueKeys();
            List<ResultValueInfo> creditResultValues = lrcService.getResultValuesByKeys(creditResultsValueKeys, context);

            //Add all the credits from the result value group
            for (ResultValueInfo creditResultValue : creditResultValues) {
                courseSearchResult.getCreditOptions().add(creditResultValue.getValue());
            }

            courseSearchResults.add(courseSearchResult);
        }

        return courseSearchResults;
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
        if (lastUpdated == null || System.currentTimeMillis() > (lastUpdated.getTime() + TIME_TO_REFRESH_MS)) {

            try {
                indexCourseOfferingData();
            } catch (Exception e) {
                throw new RuntimeException("Error updating data", e);
            }

            lastUpdated = new Date();
        }

        return client;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }
}
