package org.kuali.student.enrollment.registration.client.service;

import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;

import javax.ws.rs.GET;
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

    /**
     * This is the "one click" registration method. It will first create a registration request then submit that
     * request to the registration engine.
     *
     * @param termCode
     * @param courseCode
     * @param regGroupName
     * @return The response should be instant and give a handle to the registrationRequestId. The registration engine is
     * ansynchonous so the client will need to poll the system for status updates.
     * @throws Exception
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/registerreggroup")
    public RegistrationResponseInfo registerForRegistrationGroupByTermCodeAndCourseCodeAndRegGroupName(@QueryParam("termCode") String termCode,
                                                                                                       @QueryParam("courseCode") String courseCode,
                                                                                                       @QueryParam("regGroupName") String regGroupName) throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/stats/regengine")
    public Response getRegEngineStats() throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/stats/regengine/clear")
    public Response clearRegEngineStats() throws Exception;
}
