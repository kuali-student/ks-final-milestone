package org.kuali.student.enrollment.registration.client.service;

import org.kuali.student.enrollment.registration.client.service.dto.PersonScheduleResult;
import org.kuali.student.enrollment.registration.client.service.dto.ScheduleCalendarEventResult;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import javax.security.auth.login.LoginException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/registerreggroup")
    Response registerForRegistrationGroup(@QueryParam("termCode") String termCode,
                                                   @QueryParam("courseCode") String courseCode,
                                                   @QueryParam("regGroupCode") String regGroupCode,
                                                   @QueryParam("regGroupId") String regGroupId,
                                                   @QueryParam("credits") String credits,
                                                   @QueryParam("gradingOption") String gradingOptionId,
                                                   @QueryParam("allowWaitlist") boolean allowWaitlist);

    /**
     * This method drops a registration group. It will first create a drop request and then submit that request
     * to the registration engine.
     *
     * @param masterLprId
     * @return The response should be instant and give a handle to the registrationRequestId. The registration engine is
     * asynchronous so the client will need to poll the system for status updates.
     */
    @DELETE
    @Path("/dropRegistrationGroup")
    Response dropRegistrationGroup(@QueryParam("masterLprId") String masterLprId);

    /**
     * Returns statistics for the registration engine.
     *
     * @return JSON representing various statistics as a JSON-map of elements (ie: entities) where each element contains
     * an array of key-value pairs.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/stats/regengine")
    Response getRegEngineStats();

    /**
     * Clears the overall registration engine stats.
     * It will not clear the MQ stats plugin.
     *
     * @return Http Response
     */
    @DELETE
    @Path("/stats/regengine/clear")
    Response clearRegEngineStats();

    /**
     * SEARCH for STUDENT REGISTRATION INFO
     *
     * @param termId
     * @param termCode
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/personschedule")
    PersonScheduleResult searchForScheduleByPersonAndTerm(@QueryParam("termId") String termId,
                                                                            @QueryParam("termCode") String termCode) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/personschedulecalendar")
    List<List<ScheduleCalendarEventResult>> searchForScheduleCalendarByPersonAndTerm(@QueryParam("termId") String termId,
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
    @Path("/updateScheduleItem")
    Response updateScheduleItem(@FormParam("courseCode") String courseCode,
                                       @FormParam("regGroupCode") String regGroupCode,
                                       @FormParam("masterLprId") String masterLprId,
                                       @FormParam("termId") String termId,
                                       @FormParam("credits") String credits,
                                       @FormParam("gradingOptionId") String gradingOptionId);

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/updateWaitlistEntry")
    Response updateWaitlistEntry(@FormParam("courseCode") String courseCode,
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
    @Path("/getRegistrationStatus")
    Response getRegistrationStatus(@QueryParam("regReqId") String regReqId);

    @DELETE
    @Path("/dropFromWaitlistEntry")
    Response dropFromWaitlistEntry(@QueryParam("masterLprId") String masterLprId);

    /**
     * Finds all LPRs for a given personId and deletes them.
     * Returns a Response object with status.
     *
     * @param personId Principal ID
     * @return Empty Response Object or Response object with Error text
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     */
    @DELETE
    @Path("/clearpersonlprs")
    Response clearLPRsByPersonRS(@QueryParam("person") String personId) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

    /**
     * This method returns a roster of students on a waitlist for a particular registration group.
     *
     * @param termId - optional
     * @param termCode - human readable code representing the term. ex: 201208
     * @param courseCode - human readable code representing the course. ex: CHEM231
     * @param regGroupCode - human readable code representing the reg group. ex: 1001
     * @return list of WaitlistEntryResult
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getWaitlistRoster")
    Response searchForWaitlistRoster(@QueryParam("termId") String termId,
                                            @QueryParam("termCode") String termCode,
                                            @QueryParam("courseCode") String courseCode,
                                            @QueryParam("regGroupCode") String regGroupCode);

}
