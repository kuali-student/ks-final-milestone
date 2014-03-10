package org.kuali.student.enrollment.registration.client.service;

import org.kuali.student.enrollment.registration.client.service.dto.ScheduleCalendarEventResult;
import org.kuali.student.enrollment.registration.client.service.dto.StudentScheduleTermResult;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;

import javax.security.auth.login.LoginException;
import javax.ws.rs.GET;
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
     * @return The response should be instant and give a handle to the registrationRequestId. The registration engine is
     * ansynchonous so the client will need to poll the system for status updates.
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws ReadOnlyException
     * @throws AlreadyExistsException
     * @throws LoginException
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/registerreggroup")
    public Response registerForRegistrationGroupRS(@QueryParam("termCode") String termCode,
                                                   @QueryParam("courseCode") String courseCode,
                                                   @QueryParam("regGroupCode") String regGroupCode,
                                                   @QueryParam("regGroupId") String regGroupId,
                                                   @QueryParam("credits") String credits,
                                                   @QueryParam("gradingOption") String gradingOptionId);

    /**
     * Returns statistics for the registration engine.
     *
     * @return JSON representing various statistics as a JSON-map of elements (ie: entities) where each element contains
     * an array of key-value pairs.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/stats/regengine")
    public Response getRegEngineStatsRS();

    /**
     * SEARCH for STUDENT REGISTRATION INFO
     *
     * @param termId
     * @param termCode
     * @return StudentScheduleCourseResult
     * @throws LoginException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/personschedule")
    public List<StudentScheduleTermResult> searchForScheduleByPersonAndTermRS(@QueryParam("termId") String termId,
                                                                              @QueryParam("termCode") String termCode) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/personschedulecalendar")
    public List<List<ScheduleCalendarEventResult>> searchForScheduleCalendarByPersonAndTermRS(@QueryParam("termId") String termId,
                                                                                              @QueryParam("termCode") String termCode) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

    /**
     * Clears the overall registration engine stats.
     * It will not clear the MQ stats plugin.
     *
     * @return Http Response
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/stats/regengine/clear")
    public Response clearRegEngineStatsRS();

    /**
     * Finds all LPRs for a given personId and deletes them
     * Returns an empty List of StudentScheduleCourseResult
     *
     * @param personId Principal ID
     * @return Empty Response Object or Response object with Error text
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     */

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/clearpersonlprs")
    public Response clearLPRsByPersonRS(@QueryParam("person") String personId) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

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
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DataValidationErrorException
     * @throws ReadOnlyException
     * @throws AlreadyExistsException
     */

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/updateScheduleItem")
    public Response updateScheduleItemRS(@QueryParam("courseCode") String courseCode,
                                         @QueryParam("regGroupCode") String regGroupCode,
                                         @QueryParam("masterLprId") String masterLprId,
                                         @QueryParam("credits") String credits,
                                         @QueryParam("gradingOptionId") String gradingOptionId);

    /**
     * This is the "one click" registration method. It will first create a registration request then submit that
     * request to the registration engine.
     *
     * @param masterLprId
     * @return The response should be instant and give a handle to the registrationRequestId. The registration engine is
     * ansynchonous so the client will need to poll the system for status updates.
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws ReadOnlyException
     * @throws AlreadyExistsException
     * @throws LoginException
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/dropRegistrationGroup")
    public Response dropRegistrationGroupRS(@QueryParam("masterLprId") String masterLprId);

    /**
     * Gets the current registration status for a particular registration request
     * @param regReqId
     * @return
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getRegistrationStatus")
    public Response getRegistrationStatusRS(@QueryParam("regReqId") String regReqId);

}
