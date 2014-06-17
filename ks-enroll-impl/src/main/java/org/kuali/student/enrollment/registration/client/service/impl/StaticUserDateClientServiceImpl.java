/**
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by pauldanielrichardson on 6/16/14
 */
package org.kuali.student.enrollment.registration.client.service.impl;

import org.kuali.student.enrollment.registration.client.service.StaticUserDateClientService;
import org.kuali.student.enrollment.registration.client.service.impl.util.StaticUserDateUtil;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * This class implements RESTful services for setting and retrieving
 * static date for testing registration dates. These should be used
 * instead of using the system date (since the time is constantly
 * changing).
 *
 * @author Kuali Student Team
 */
public class StaticUserDateClientServiceImpl implements StaticUserDateClientService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ScheduleOfClassesClientServiceImpl.class);

    @Override
    public Response setStaticDate(@PathParam("userId") String userId, @QueryParam("date") String date) {
        ResponseBuilder response;

        try {
            StaticUserDateUtil.putDateInMap(userId, date);
            response = Response.ok();
        } catch (InvalidParameterException ex) {
            LOGGER.warn("Unable to set static date for userId {} / date {} ... invalid parameters.", userId, date, ex);
            response = Response.status(Response.Status.BAD_REQUEST);
        } catch (Exception ex) {
            LOGGER.warn("Unable to set static date to {} for userId {}: {}", date, userId, ex.getMessage(), ex);
            response = Response.serverError();
        }

        return response.build();
    }

    @Override
    public Response getStaticDate(@PathParam("userId") String userId) {
        ResponseBuilder response;

        try {
            response = Response.ok(StaticUserDateUtil.getDateFromMap(userId));
        } catch (InvalidParameterException ex) {
            LOGGER.warn("Unable to get static date for userId {} ... invalid parameters.", userId, ex);
            response = Response.status(Response.Status.BAD_REQUEST);
        } catch (Exception ex) {
            LOGGER.warn("Unable to get static date for userId {}: {}", userId, ex.getMessage(), ex);
            response = Response.serverError();
        }

        return response.build();
    }

    @Override
    public Response clearStaticDate(@PathParam("userId") String userId) {
        ResponseBuilder response;

        try {
            StaticUserDateUtil.removeDateFromMap(userId);
            response = Response.ok();
        } catch (InvalidParameterException ex) {
            LOGGER.warn("Unable to remove static date for userId {} ... invalid parameters.", userId, ex);
            response = Response.status(Response.Status.BAD_REQUEST);
        } catch (Exception ex) {
            LOGGER.warn("Unable to remove static date from map for userId {}: {}", userId, ex.getMessage(), ex);
            response = Response.serverError();
        }

        return response.build();
    }

    @Override
    public Response getStaticDates() {
        ResponseBuilder response;

        try {
            response = Response.ok(StaticUserDateUtil.getDatesFromMap());
        } catch (Exception ex) {
            LOGGER.warn("Unable to get static date map: {}", ex.getMessage(), ex);
            response = Response.serverError();
        }
        return response.build();
    }

}
