package org.kuali.student.enrollment.registration.client.service;

import org.kuali.student.enrollment.registration.client.service.dto.PersonScheduleResult;
import org.kuali.student.r2.common.exceptions.*;

import javax.security.auth.login.LoginException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This class performance registration functions for a particular user. Unlike the ScheduleOfClasses service, each
 * method requires that the principal user information be populated in the context.
 */
@Path("/")
public interface CourseRegistrationClientService {

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
     * @return The response should be instant and give a handle to the registrationRequestId. The registration engine is
     * asynchronous so the client will need to poll the system for status updates.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/registrationRequest")
    Response createAndSubmitAddCourseRegistrationRequest(@FormParam("termCode") String termCode,
                                                         @FormParam("courseCode") String courseCode,
                                                         @FormParam("regGroupCode") String regGroupCode,
                                                         @FormParam("regGroupId") String regGroupId,
                                                         @FormParam("credits") String credits,
                                                         @FormParam("gradingOption") String gradingOptionId,
                                                         @FormParam("allowWaitlist") boolean allowWaitlist);

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
     * SEARCH for STUDENT REGISTRATION INFO
     *
     * @param termId
     * @param termCode
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/studentSchedule")
    PersonScheduleResult getStudentScheduleAndWaitlistedCoursesByTerm(@QueryParam("termId") String termId,
                                                                      @QueryParam("termCode") String termCode) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

    /**
     * Creates a new RegistrationRequest with type Update
     * and submits it to be processed
     *
     * @param courseCode      - course code for the selected course
     * @param regGroupCode    - Reg Group code for the selected course
     * @param masterLprId     - Master LPR Id for the selected course
     * @param credits         - current credits registered for
     * @param gradingOptionId - current grading option registered for
     * @return
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/registrationRequest")
    Response createAndSubmitUpdateCourseRegistrationRequest(@FormParam("courseCode") String courseCode,
                                                            @FormParam("regGroupCode") String regGroupCode,
                                                            @FormParam("masterLprId") String masterLprId,
                                                            @FormParam("termId") String termId,
                                                            @FormParam("credits") String credits,
                                                            @FormParam("gradingOptionId") String gradingOptionId);

    /**
     * Updates the credit/reg options for waitlist LPRs using a registration request.
     *
     * @param courseCode e.g., ENGL101
     * @param regGroupCode e.g., 1001
     * @param masterLprId Represents the waitlist RG LPRs for a waitlist
     * @param termId Represent a TermInfo (see AcademicCalendarService)
     * @param credits String version of credits, e.g. "3.0"
     * @param gradingOptionId Represents letter, pass/fail, or audit
     * @return A ScheduleItemResult object
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/waitlistRegistrationRequest")
    Response createAndSubmitUpdateWaitlistRegistrationRequest(@FormParam("courseCode") String courseCode,
                                                              @FormParam("regGroupCode") String regGroupCode,
                                                              @FormParam("masterLprId") String masterLprId,
                                                              @FormParam("termId") String termId,
                                                              @FormParam("credits") String credits,
                                                              @FormParam("gradingOptionId") String gradingOptionId);

    /**
     * Gets the current registration status for a particular registration request
     *
     * @param regReqId
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/registrationStatus")
    Response getRegistrationStatus(@QueryParam("regReqId") String regReqId);

    @DELETE
    @Path("/waitlistRegistrationRequest")
    Response createAndSubmitDropWaitlistRegistrationRequest(@QueryParam("masterLprId") String masterLprId);

}
