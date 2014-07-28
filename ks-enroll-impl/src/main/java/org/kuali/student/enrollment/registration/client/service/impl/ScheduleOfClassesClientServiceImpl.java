package org.kuali.student.enrollment.registration.client.service.impl;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesClientService;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityTypeSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.CourseOfferingInfoSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.EligibilityCheckResult;
import org.kuali.student.enrollment.registration.client.service.dto.InstructorSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.TermSearchResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.search.elastic.ElasticEmbedded;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleOfClassesClientServiceImpl extends ScheduleOfClassesServiceImpl implements ScheduleOfClassesClientService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ScheduleOfClassesClientServiceImpl.class);

    private static final String EXCEPTION_MSG="Exception Thrown";
    private ElasticEmbedded elasticEmbedded;

    private RuleManagementService ruleManagementService;

    /**
     * COURSE SEARCH *
     */

    @Override
    public Response searchForCourseOfferings(@QueryParam("termId") String termId, @QueryParam("termCode") String termCode, @QueryParam("criteria") String criteria) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, IOException {
        //Look up the term id if only term code is passed in
        termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode);

        //Query based on title and description, boost title so it's more important than description
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("longName", criteria.toLowerCase()).boost(1.5f))
                .should(QueryBuilders.matchQuery("courseDescription", criteria.toLowerCase()).boost(1f));

        //Parse out course prefixes from the search criteria (CHEM101A) and add them to the Query
        for(String token:criteria.toLowerCase().split("\\s")){
            if(token.matches("^[a-z]{4}([0-9]{0,3}|[0-9]{3}[a-z]*)$")){
                boolQuery = boolQuery.should(QueryBuilders.wildcardQuery("courseCode", token + "*").boost(2f));
            }
        }

        //Filter all results based on the term id
        QueryBuilder query = QueryBuilders.filteredQuery(boolQuery,
                FilterBuilders.termsFilter("termId", termId.toLowerCase().split("\\.")));

        //Perform the search
        SearchResponse sr = elasticEmbedded.getClient()
                .prepareSearch("ks")
                .setTypes("courseoffering")
                .setQuery(query)
                .setSize(100)
                .execute().actionGet();

        //Parse the results and add to a JSON array
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
     * PREREQUISITES
     */

    public Response searchForPrerequisitesByCourseOffering(String courseOfferingId){
        Response.ResponseBuilder response;
        List<String> prerequisites = new ArrayList<String>();
        ruleManagementService = getRuleManagementService();

        try{
            List<ReferenceObjectBinding> referenceObjectBindings = ruleManagementService.findReferenceObjectBindingsByReferenceObject(CourseOfferingServiceConstants.REF_OBJECT_URI_COURSE_OFFERING, courseOfferingId);
            for(ReferenceObjectBinding referenceObjectBinding: referenceObjectBindings){
                try{
                    String prerequisite = ruleManagementService.translateNaturalLanguageForObject("KS-KRMS-NL-USAGE-1005", "agenda", referenceObjectBinding.getKrmsObjectId(), "en");
                    prerequisites.add(prerequisite);
                } catch(Exception e){
                    LOGGER.warn(EXCEPTION_MSG, e);
                    response = Response.serverError().entity(e.getMessage());
                }
            }
        }catch(Exception e){
            LOGGER.warn(EXCEPTION_MSG, e);
            response = Response.serverError().entity(e.getMessage());
        }
        response = Response.ok(prerequisites);
        return  response.build();
    }

    public RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
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
