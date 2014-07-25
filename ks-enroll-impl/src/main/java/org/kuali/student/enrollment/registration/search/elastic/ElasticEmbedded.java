package org.kuali.student.enrollment.registration.search.elastic;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
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
import org.kuali.student.r2.core.search.infc.SearchRequest;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel on 7/9/14.
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

    private Date lastUpdated;
    private long timeTORefreshMs = (5*60*1000);

    public void init() throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, IOException, DoesNotExistException {
        LOG.info("Starting Elastic Client");
        node = NodeBuilder.nodeBuilder().local(false).clusterName("ks.elastic.cluster").node();
        client = node.client();
//        client
//                .admin()
//                .indices()
//                .preparePutMapping("ks")
//                .setType("courseoffering")
//                .setSource(XContentFactory.jsonBuilder()
//                        .startObject()
//                        .startObject("courseoffering")
//                        .startObject("properties")
//                        .startObject("termId")
//                        .field("type", "string")
//                        .field("index", "not_analyzed")
//                        .endObject()
//                        .endObject()
//                        .endObject()
//                        .endObject())
//                .setIgnoreConflicts(true)
//                .execute()
//                .actionGet();
    }

    private void indexCourseOfferingData() throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, IOException {
        LOG.info("Loading Data into Elastic");
        Date startTime = new Date();
        List<CourseSearchResult> courses = getAllCourseOfferings();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        ObjectMapper mapper = new ObjectMapper();

        for (CourseSearchResult searchResult : courses) {
            String json = mapper.writeValueAsString(searchResult);
            bulkRequest.add(client.prepareIndex("ks", "courseoffering", searchResult.getCourseId()).setSource(json));
        }

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            throw new RuntimeException("Error Bulk Loading elasticsearch courseofferings");
        }

        LOG.info("Done Loading Data - " + (System.currentTimeMillis() - startTime.getTime()) + "ms");
    }

    private List<CourseSearchResult> getAllCourseOfferings() throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        List<CourseSearchResult> courseSearchResults = new ArrayList<>();

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.CO_SEARCH_INFO_SEARCH_KEY);
        ContextInfo context = ContextUtils.createDefaultContextInfo();

        SearchResult searchResults = searchService.search(searchRequest, context);

        for(SearchResultHelper.KeyValue keyValue:SearchResultHelper.wrap(searchResults)){

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

            //These two calls are cached
            List<String> creditResultsValueKeys = lrcService.getResultValuesGroup(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CREDITS), context).getResultValueKeys();
            List<ResultValueInfo> creditResultValues = lrcService.getResultValuesByKeys(creditResultsValueKeys, context);

            for(ResultValueInfo creditResultValue:creditResultValues){
                courseSearchResult.getCreditOptions().add(creditResultValue.getValue());
            }

            courseSearchResults.add(courseSearchResult);
        }

        return courseSearchResults;
    }

    public void shutdown() {
        node.close();
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public synchronized Client getClient() {
        if(lastUpdated == null || System.currentTimeMillis() > (lastUpdated.getTime() + timeTORefreshMs) ){
            new Thread(){
                public void run(){
                    try{
                        indexCourseOfferingData();
                    } catch (Exception e) {
                        throw new RuntimeException("Error updating data",e);
                    }
                }
            }.start();
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
