package org.kuali.student.enrollment.registration.client.service;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: Charles
 * Date: 7/21/14
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CourseRegistrationAdminClientService {


    /**
     * Returns statistics for the registration engine.
     *
     * @return JSON representing various statistics as a JSON-map of elements (ie: entities) where each element contains
     * an array of key-value pairs.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
     * Finds all LPRs for a given personId and deletes them. If term is passed - deletes LPRs only for that term.
     * Returns a Response object with status.
     *
     * @param personId Principal ID
     * @param termId - optional
     * @param termCode - optional, human readable code representing the term. ex: 201208
     * @return Empty Response Object or Response object with Error text
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     */
    @DELETE
    @Path("/clearpersonlprs")
    Response clearLPRsByPersonRS(@QueryParam("person") String personId,
                                 @QueryParam("termId") String termId,
                                 @QueryParam("termCode") String termCode) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

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
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/waitlistRoster")
    Response getWaitlistRoster(@QueryParam("termId") String termId,
                               @QueryParam("termCode") String termCode,
                               @QueryParam("courseCode") String courseCode,
                               @QueryParam("regGroupCode") String regGroupCode);
}
