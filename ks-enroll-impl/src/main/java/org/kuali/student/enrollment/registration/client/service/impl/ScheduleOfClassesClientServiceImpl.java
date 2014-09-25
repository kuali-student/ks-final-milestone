package org.kuali.student.enrollment.registration.client.service.impl;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesClientService;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityTypeSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.CourseOfferingDetailsSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.EligibilityCheckResult;
import org.kuali.student.enrollment.registration.client.service.dto.InstructorSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.TermSearchResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleOfClassesClientServiceImpl extends ScheduleOfClassesServiceElasticImpl implements ScheduleOfClassesClientService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ScheduleOfClassesClientServiceImpl.class);
    private static final String EXCEPTION_MSG = "Exception Thrown";


    /**
     * COURSE SEARCH *
     */

    @Override
    public Response searchForCourseOfferings(@QueryParam("termId") String termId, @QueryParam("termCode") String termCode, @QueryParam("criteria") String criteria) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, IOException {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        //Look up the term id if only term code is passed in
        termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode, contextInfo);

        //Query based on title and description, boost title so it's more important than description
        //This type of query grabs only the score/match with the highest score
        DisMaxQueryBuilder disMaxQuery = QueryBuilders.disMaxQuery();

        List<String> fullTextMatchTerms = new ArrayList<>();

        //Parse out course prefixes from the search criteria (CHEM101A) and add them to the Query
        //lowercase is used because the default analyzer indexes in lowercase
        for (String token : criteria.toLowerCase().split("\\s")) {
            if (token.matches("^[a-z]{4}[0-9]{1,3}[a-z]*$")) {
                //DivisionAndCode search (Also Division and level)
                disMaxQuery = disMaxQuery.add(QueryBuilders.constantScoreQuery(FilterBuilders.prefixFilter("courseCode", token)).boost(4.0f));
            } else {
                //All non-course code terms go in here
                fullTextMatchTerms.add(token);
                if (token.matches("^[a-z]{4}$")) {
                    //Division
                    disMaxQuery = disMaxQuery.add(QueryBuilders.constantScoreQuery(FilterBuilders.termFilter("coursePrefix", token)).boost(1.5f));
                }
                if (token.matches("^[0-9]{3}$")) {
                    //Code
                    disMaxQuery = disMaxQuery.add(QueryBuilders.constantScoreQuery(FilterBuilders.termFilter("courseNumber", token)).boost(0.7f));
                }
            }
        }

        //If any of the terms are not course codes, do a full text search in the title and description
        //This will bubble up multi term matches ("American Literature" will score higher for
        //"African American Literature" than "World Literature"
        if (!fullTextMatchTerms.isEmpty()) {
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

            for (String term : fullTextMatchTerms) {
                boolQuery.should(QueryBuilders.prefixQuery("longName", term).boost(0.3f));
                boolQuery.should(QueryBuilders.prefixQuery("courseDescription", term).boost(0.1f));
            }

            disMaxQuery = disMaxQuery.add(boolQuery);
        }


        //Filter all results based on the term id
        QueryBuilder query = QueryBuilders.filteredQuery(disMaxQuery,
                FilterBuilders.andFilter(
                        FilterBuilders.termFilter("termId", termId),
                        FilterBuilders.termFilter("state", LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY)));
        //Perform the search
        SearchResponse sr = elasticEmbedded.getClient()
                .prepareSearch("ks")
                .setTypes("courseoffering")
                .setQuery(query)
                .setSize(200)
                .addSort("_score", SortOrder.DESC)
                .addSort("courseCode", SortOrder.ASC)
                .execute().actionGet();

        //Parse the results and add to a JSON array
        StringBuilder sb = new StringBuilder("[");
        int i = 0;
        for (SearchHit hit : sr.getHits().getHits()) {
            sb.append(hit.getSourceAsString());
            if (i < (sr.getHits().getHits().length - 1)) {
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
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        try {
            List<RegGroupSearchResult> regGroupSearchResults = searchForRegistrationGroupsLocal(courseOfferingId, termId, termCode, courseCode, regGroupName, contextInfo);
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
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        try {
            List<ActivityOfferingSearchResult> activityOfferingSearchResults = searchForActivityOfferingsLocal(courseOfferingId, termId, termCode, courseCode, contextInfo);
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
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        try {
            List<ActivityTypeSearchResult> activityTypeSearchResults = searchForActivityTypesLocal(courseOfferingId, termId, termCode, courseCode, contextInfo);
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
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        try {
            List<InstructorSearchResult> instructorSearchResults = searchForInstructorsLocal(courseOfferingId, activityOfferingId, termId, termCode, courseCode, contextInfo);
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
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        try {
            List<TermSearchResult> termSearchResults = searchForTermsLocal(termCode, isActiveTerms, contextInfo);
            response = Response.ok(termSearchResults);
        } catch (Exception e) {
            LOGGER.warn(EXCEPTION_MSG, e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    /**
     * COURSE OFFERING Details *
     * Returns a list of course offering details such as main info (code, name, desc, etc.),
     * cross-listed courses, prereqs, and AO info (main info, schedule, instructor, reg groups).     *
     */
    @Override
    public Response searchForCourseOfferingDetailsRS(String courseOfferingId, String courseCode) {
        Response.ResponseBuilder response;
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        try {
            CourseOfferingDetailsSearchResult courseOfferingSearchResults = searchForCourseOfferingDetails(courseOfferingId, courseCode, contextInfo);
            response = Response.ok(courseOfferingSearchResults);
        } catch (Exception e) {
            LOGGER.warn(EXCEPTION_MSG, e);
            return Response.serverError().entity(e.getMessage()).build();
        }
        return response.build();
    }

}
