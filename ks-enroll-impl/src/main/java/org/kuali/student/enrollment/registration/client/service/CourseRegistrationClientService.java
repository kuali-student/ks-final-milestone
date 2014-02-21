package org.kuali.student.enrollment.registration.client.service;

import org.kuali.student.enrollment.registration.client.service.dto.ScheduleCalendarEventResult;
import org.kuali.student.enrollment.registration.client.service.dto.ScheduleItemResult;
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
     * @param userId       user id of the person you want to register in a course. This is for POC testing only and needs to be removed post POC for secuirty
     * @param termCode
     * @param courseCode
     * @param regGroupCode
     * @param regGroupId   optional, but the term, course, and reg group name are not checked if you supply the id
     * @param gradingOptionId
     * @param credits
     * @return The response should be instant and give a handle to the registrationRequestId. The registration engine is
     *         ansynchonous so the client will need to poll the system for status updates.
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
    public Response registerForRegistrationGroup(@QueryParam("userId") String userId,
                                                                                                       @QueryParam("termCode") String termCode,
                                                                                                       @QueryParam("courseCode") String courseCode,
                                                                                                       @QueryParam("regGroupCode") String regGroupCode,
                                                                                                       @QueryParam("regGroupId") String regGroupId,
                                                                                                       @QueryParam("credits") String credits,
                                                                                                       @QueryParam("gradingOption") String gradingOptionId);

    /**
     * Returns statistics for the registration engine.
     *
     * @return JSON representing various statistics as a JSON-map of elements (ie: entities) where each element contains
     *         an array of key-value pairs.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/stats/regengine")
    public Response getRegEngineStats();

    /**
     * SEARCH for STUDENT REGISTRATION INFO
     *
     * @param userId
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
    public List<StudentScheduleTermResult> searchForScheduleByPersonAndTerm(@QueryParam("userId") String userId,
                                                                              @QueryParam("termId") String termId,
                                                                              @QueryParam("termCode") String termCode) throws LoginException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/personschedulecalendar")
    public List<List<ScheduleCalendarEventResult>> searchForScheduleCalendarByPersonAndTerm(@QueryParam("userId") String userId,
                                                                              @QueryParam("termId") String termId,
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
    public Response clearRegEngineStats();

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
    public Response clearLPRsByPerson(@QueryParam("person") String personId) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

    /**
     * Creates a new RegistrationRequest with type Update
     * and submits it to be processed
     *
     * @param userId
     * @param termId
     * @param courseCode
     * @param regGroupCode
     * @param credits
     * @param gradingOption
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
    public ScheduleItemResult updateScheduleItem(@QueryParam("userId") String userId,
                                                 @QueryParam("termId") String termId,
                                                 @QueryParam("courseCode") String courseCode,
                                                 @QueryParam("regGroupCode") String regGroupCode,
                                                 @QueryParam("credits") String credits,
                                                 @QueryParam("gradingOptions") String gradingOption) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException, AlreadyExistsException;

}
