package org.kuali.student.enrollment.registration.client.service;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: pauldanielrichardson
 * Date: 6/16/14
 * Time: 1:10 PM
 *
 * RESTful services for setting and retrieving static date for testing
 * registration dates. These should be used instead of using the system
 * date (since the time is constantly changing).
 *
 */
@Path("/")
public interface StaticUserDateClientService {

    /**
     * Sets the static date in memory for the provided user id.
     * Date should be in the format yyyy-MM-dd@HH:mm
     *
     * @param userId the user id
     * @param date the date to store
     * @return http response
     */
    @PUT
    @Path("/staticdate/{userId}")
    Response setStaticDate(@PathParam("userId") String userId,
                           @QueryParam("date") String date);

    /**
     * Gets the static date in memory for the provided user id
     * If the date exists, it will be returned in the format yyyy-MM-dd@HH:mm
     * If not, it will be null.
     *
     * Additionally, date can be passed into the method, which will then
     * set the date for that user as well. This is an alternative to the
     * PUT method above for users who may want to update a date using a
     * simple browser call.
     *
     * @param userId the user id
     * @return http response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/staticdate/{userId}")
    Response getStaticDate(@PathParam("userId") String userId);

    /**
     * Gets the static date in memory for the provided user id
     * If the date exists, it will be returned in the format yyyy-MM-dd@HH:mm
     * If not, it will be null.
     *
     * Additionally, date can be passed into the method, which will then
     * set the date for that user as well. This is an alternative to the
     * PUT method above for users who may want to update a date using a
     * simple browser call.
     *
     * @param userId the user id
     * @param date changes the user's stored date/time
     * @return http response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/staticdate/{userId}/{date}")
    Response getStaticDate(@PathParam("userId") String userId,
                           @PathParam("date") String date);

    /**
     * Clears the static date in memory for the provided user id.
     *
     * @param userId the user id
     * @return http response
     */
    @DELETE
    @Path("/staticdate/{userId}")
    Response clearStaticDate(@PathParam("userId") String userId);

    /**
     * Gets all existing mappings of userId -> static dates.
     * Dates will be in the format yyyy-MM-dd@HH:mm
     *
     * @return http response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/staticdate")
    Response getStaticDates();
}
