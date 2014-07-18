package org.kuali.student.enrollment.registration.client.service.impl;

import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesClientService;
import org.kuali.student.enrollment.registration.client.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.List;

public class ScheduleOfClassesClientServiceImpl extends ScheduleOfClassesServiceImpl implements ScheduleOfClassesClientService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ScheduleOfClassesClientServiceImpl.class);

    private static final String EXCEPTION_MSG="Exception Thrown";

    /**
     * COURSE OFFERINGS *
     */

    @Override
    public Response searchForCourseOfferings(String termId, String termCode, String criteria) {
        Response.ResponseBuilder response;

        try {
            List<CourseSearchResult> courseSearchResults = searchForCourseOfferingsByCriteriaLocal(termId, termCode, criteria);
            response = Response.ok(courseSearchResults);
        } catch (Exception e) {
            LOGGER.warn(EXCEPTION_MSG, e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    @Override
    public Response searchForCourseOfferingsByTermAndCourse(String termId, String termCode, String courseCode) {
        Response.ResponseBuilder response;

        try {
            List<CourseSearchResult> courseSearchResults = searchForCourseOfferingsByCourseLocal(termId, termCode, courseCode);
            response = Response.ok(courseSearchResults);
        } catch (Exception e) {
            LOGGER.warn(EXCEPTION_MSG, e);
            response = Response.serverError().entity(e.getMessage());
        }

        return response.build();
    }

    @Override
    public Response searchForCourseOfferingsAndPrimaryAosByTermAndCourse(String termId, String termCode, String courseCode) {
        Response.ResponseBuilder response;

        try {
            List<CourseAndPrimaryAOSearchResult> courseSearchResults = searchForCourseOfferingsAndPrimaryAosByTermAndCourseLocal(termId, termCode, courseCode);
            response = Response.ok(courseSearchResults);
        } catch (Exception e) {
            LOGGER.warn(EXCEPTION_MSG, e);
            response = Response.serverError().entity(e.getMessage());
        }

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

}
