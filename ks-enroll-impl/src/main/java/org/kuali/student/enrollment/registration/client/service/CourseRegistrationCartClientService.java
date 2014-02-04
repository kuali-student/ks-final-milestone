package org.kuali.student.enrollment.registration.client.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Services for Registration Cart Operations
 */
public interface CourseRegistrationCartClientService {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/submitCart")
    /**
     *
     */
    public Response submitCart(@QueryParam("userId") String userId,
                               @QueryParam("cartId") String cartId);


}
