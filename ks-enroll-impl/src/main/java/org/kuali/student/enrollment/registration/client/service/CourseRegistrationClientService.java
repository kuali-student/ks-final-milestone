package org.kuali.student.enrollment.registration.client.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This class performance registration functions for a particular user. Unlike the ScheduleOfClasses service, each
 * method requires that the principal user information be populated in the context.
 */
@Path("/")
public interface CourseRegistrationClientService {
    public static final String LPRTRANS_ITEM_WAITLIST_STATE_KEY = "kuali.lpr.trans.item.state.waitlist";
    public static final String LPRTRANS_ITEM_WAITLIST_AVAILABLE_STATE_KEY = "kuali.lpr.trans.item.state.waitlistActionAvailable";

    /**
     * Gets the current registration status for a particular registration request
     *
     * @param regReqId
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/registrationRequest")
    Response getRegistrationStatus(@QueryParam("regRequestId") String regReqId);

    /**
     * This is the "one click" registration method. It will first create a registration request then submit that
     * request to the registration engine.
     *
     * @param termCode
     * @param courseCode
     * @param regGroupCode
     * @param regGroupId      optional, but the term, course, and reg group name are not checked if you supply the id
     * @param gradingOptionId
     * @param credits
     * @param allowWaitlist
     * @param allowRepeatedCourses
     * @return The response should be instant and give a handle to the registrationRequestId. The registration engine is
     * asynchronous so the client will need to poll the system for status updates.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/registrationRequest")
    Response createAndSubmitAddCourseRegistrationRequest(@FormParam("termCode") String termCode,
                                                         @FormParam("courseCode") String courseCode,
                                                         @FormParam("regGroupCode") String regGroupCode,
                                                         @FormParam("regGroupId") String regGroupId,
                                                         @FormParam("credits") String credits,
                                                         @FormParam("gradingOption") String gradingOptionId,
                                                         @FormParam("allowWaitlist") boolean allowWaitlist,
                                                         @FormParam("allowRepeatedCourses") boolean allowRepeatedCourses);

    /**
     * Creates a new RegistrationRequest with type Update
     * and submits it to be processed
     *
     * @param courseCode      - course code for the selected course
     * @param masterLprId     - Master LPR Id for the selected course
     * @param credits         - current credits registered for
     * @param gradingOptionId - current grading option registered for
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/registrationRequest")
    Response createAndSubmitUpdateCourseRegistrationRequest(@FormParam("courseCode") String courseCode,
                                                            @FormParam("regGroupId") String regGroupId,
                                                            @FormParam("masterLprId") String masterLprId,
                                                            @FormParam("credits") String credits,
                                                            @FormParam("gradingOptionId") String gradingOptionId);

    /**
     * This method drops a registration group. It will first create a drop request and then submit that request
     * to the registration engine.
     *
     * @param masterLprId
     * @return The response should be instant and give a handle to the registrationRequestId. The registration engine is
     * asynchronous so the client will need to poll the system for status updates.
     */
    @DELETE
    @Path("/registrationRequest")
    Response createAndSubmitDropCourseRegistrationRequest(@QueryParam("masterLprId") String masterLprId);

    /**
     * Updates the credit/reg options for waitlist LPRs using a registration request.
     *
     * @param courseCode e.g., ENGL101
     * @param masterLprId Represents the waitlist RG LPRs for a waitlist
     * @param credits String version of credits, e.g. "3.0"
     * @param gradingOptionId Represents letter, pass/fail, or audit
     * @return A ScheduleItemResult object
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/waitlistRegistrationRequest")
    Response createAndSubmitUpdateWaitlistRegistrationRequest(@FormParam("courseCode") String courseCode,
                                                              @FormParam("regGroupId") String regGroupId,
                                                              @FormParam("masterLprId") String masterLprId,
                                                              @FormParam("credits") String credits,
                                                              @FormParam("gradingOptionId") String gradingOptionId);

    /**
     * Creates and submits a drop waitlist registration request
     *
     * @param masterLprId
     * @return
     */
    @DELETE
    @Path("/waitlistRegistrationRequest")
    Response createAndSubmitDropWaitlistRegistrationRequest(@QueryParam("masterLprId") String masterLprId);

    /**
     * Finds all LPRs for a logged in personId and drops them. If term is passed - deletes LPRs only for that term.
     * Returns a Response object with status.
     *
     * @param termId - optional
     * @param termCode - optional, human readable code representing the term. ex: 201208
     * @return Empty Response Object or Response object with Error text
     */
    @DELETE
    @Path("/studentSchedule")
    Response clearScheduleRS(@QueryParam("termId") String termId,
                             @QueryParam("termCode") String termCode);

    /**
     * SEARCH for STUDENT REGISTRATION INFO
     *
     * @param termId - optional
     * @param termCode - optional, human readable code representing the term. ex: 201208
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/studentSchedule")
    Response getStudentScheduleAndWaitlistedCoursesByTerm(@QueryParam("termId") String termId,
                                                          @QueryParam("termCode") String termCode);

    /**
     * Get the Learning Plan for the currently logged in user for the specified term
     * @param termId Optional, but more efficient. id of the term to pull learning plan information
     * @param termCode Optional, but less efficient if used instead of termId. Code Ex:  201208.
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/learningPlan")
    Response getLearningPlan(@QueryParam("termId") String termId,
                             @QueryParam("termCode") String termCode);

}
