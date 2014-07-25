package org.kuali.student.enrollment.registration.client.service.impl;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesClientService;
import org.kuali.student.enrollment.registration.client.service.dto.*;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.search.elastic.ElasticEmbedded;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

public class ScheduleOfClassesClientServiceImpl extends ScheduleOfClassesServiceImpl implements ScheduleOfClassesClientService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ScheduleOfClassesClientServiceImpl.class);

    private static final String EXCEPTION_MSG="Exception Thrown";
    private ElasticEmbedded elasticEmbedded;

    /**
     * COURSE SEARCH *
     */

    @Override
    public Response searchForCourseOfferings(@QueryParam("termId") String termId, @QueryParam("termCode") String termCode, @QueryParam("criteria") String criteria) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, IOException {
        termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("longName", criteria.toLowerCase()).boost(1.5f))
                .should(QueryBuilders.matchQuery("courseDescription", criteria.toLowerCase()).boost(1f));
        for(String token:criteria.toLowerCase().split("\\s")){
            if(token.matches("^[a-z]{4}([0-9]{0,3}|[0-9]{3}[a-z]{0,2})$")){
                boolQuery = boolQuery.should(QueryBuilders.wildcardQuery("courseCode", token + "*").boost(2f));
            }
        }

        QueryBuilder query = QueryBuilders.filteredQuery(boolQuery,
                FilterBuilders.termsFilter("termId", termId.toLowerCase().split("\\.")));

        SearchResponse sr = elasticEmbedded.getClient()
                .prepareSearch("ks")
                .setTypes("courseoffering")
                .setQuery(query)
                .setSize(100)
                .execute().actionGet();

        StringBuilder sb = new StringBuilder("[");
        int i = 0;
        for (SearchHit hit : sr.getHits().getHits()) {
            sb.append(hit.getSourceAsString());
            if (i < (sr.getHits().getHits().length-1)) {
                sb.append(",");
            }
            i++;
        }
        sb.append("]");

        Response.ResponseBuilder response = Response.ok(sb.toString());
        return response.build();
    }


    /**
     * REGISTRATION GROUPS *
     */

    @Override
    public Response searchForRegistrationGroups(String courseOfferingId, String termId, String termCode, String courseCode, String regGroupName) {
        Response.ResponseBuilder response;

        try {
            List<RegGroupSearchResult> regGroupSearchResults = searchForRegistrationGroupsLocal(courseOfferingId, termId, termCode, courseCode, regGroupName);
            response = Response.ok(regGroupSearchResults);
        } catch (Exception e) {
            LOGGER.warn(EXCEPTION_MSG, e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    /**
     * ACTIVITY OFFERINGS *
     */

    @Override
    public Response searchForActivityOfferings(String courseOfferingId, String termId, String termCode, String courseCode) {
        Response.ResponseBuilder response;

        try {
            List<ActivityOfferingSearchResult> activityOfferingSearchResults = searchForActivityOfferingsLocal(courseOfferingId, termId, termCode, courseCode);
            response = Response.ok(activityOfferingSearchResults);
        } catch (Exception e) {
            LOGGER.warn(EXCEPTION_MSG, e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    /**
     * ACTIVITY TYPES *
     */

    @Override
    public Response searchForActivityTypes(String courseOfferingId, String termId, String termCode, String courseCode) {
        Response.ResponseBuilder response;

        try {
            List<ActivityTypeSearchResult> activityTypeSearchResults = searchForActivityTypesLocal(courseOfferingId, termId, termCode, courseCode);
            response = Response.ok(activityTypeSearchResults);
        } catch (Exception e) {
            LOGGER.warn(EXCEPTION_MSG, e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    /**
     * INSTRUCTORS *
     */

    @Override
    public Response searchForInstructors(String courseOfferingId, String activityOfferingId, String termId, String termCode, String courseCode) {
        Response.ResponseBuilder response;

        try {
            List<InstructorSearchResult> instructorSearchResults = searchForInstructorsLocal(courseOfferingId, activityOfferingId, termId, termCode, courseCode);
            response = Response.ok(instructorSearchResults);
        } catch (Exception e) {
            LOGGER.warn(EXCEPTION_MSG, e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    /**
     * TERMS *
     */

    @Override
    public Response checkStudentEligibilityForTerm(String termId) {
        Response.ResponseBuilder response;

        try {
            EligibilityCheckResult eligibilityCheckResult = checkStudentEligibilityForTermLocal(termId);
            response = Response.ok(eligibilityCheckResult);
        } catch (Exception e) {
            LOGGER.warn(EXCEPTION_MSG, e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    @Override
    public Response searchForTerms(String termCode, boolean isActiveTerms) {
        Response.ResponseBuilder response;

        try {
            List<TermSearchResult> termSearchResults = searchForTermsLocal(termCode, isActiveTerms);
            response = Response.ok(termSearchResults);
        } catch (Exception e) {
            LOGGER.warn(EXCEPTION_MSG, e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    /**
     * COURSE OFFERING INFO *
     */

    @Override
    public Response searchForCourseOfferingInfo(String courseOfferingId) {
        Response.ResponseBuilder response;

        try {
            CourseOfferingInfoSearchResult courseOfferingSearchResults = searchForCourseOfferingInfoLocal(courseOfferingId);
            response = Response.ok(courseOfferingSearchResults);
        } catch (Exception e) {
            LOGGER.warn(EXCEPTION_MSG, e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    public void setElasticEmbedded(ElasticEmbedded elasticEmbedded) {
        this.elasticEmbedded = elasticEmbedded;
    }
}
