package org.kuali.student.enrollment.registration.client.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Daniel on 3/5/14.
 */
@Path("/")
public interface DevelopmentLoginClientService {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/login")
    public Response login(@QueryParam("userId") String userId,
                          @QueryParam("password") String password,
                          @Context HttpServletRequest request);
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/logout")
    public Response logout(@Context HttpServletRequest request);

}
