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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.registration.client.service.StaticUserDateClientService;
import org.kuali.student.enrollment.registration.client.service.dto.UserDateResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.StaticUserDateUtil;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * This class tests StaticUserDateClientServiceImpl
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:courseregistration-test-context.xml" })
public class TestStaticUserDateClientServiceImpl {
    private StaticUserDateClientService staticUserDateClientService=new StaticUserDateClientServiceImpl();

    private static final String USER_01="admin";
    private static final String USER_02="student";

    private static final String DATE_01="2014-06-16T15:51";
    private static final String DATE_02="I am an invalid date";
    private static final String DATE_03="2013-05-15T14:41";

    @Before
    public void setUp() {
        /*
        Remove entries for all users
         */
        StaticUserDateUtil.clearMap();
    }

    @Test
    public void testSetStaticDate() {
        Response response=staticUserDateClientService.setStaticDate(USER_01, DATE_01);

        // Verify that the add was successful
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testSetStaticDate_invalidDate() {
        Response response=staticUserDateClientService.setStaticDate(USER_01, DATE_02);

        // response should be a 400 ("Bad Request")
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testSetStaticDate_invalidUserId() {
        Response response=staticUserDateClientService.setStaticDate(null, DATE_01);

        // response should be a 400 ("Bad Request")
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetStaticDate() {
        String userId=USER_01;
        String date=DATE_03;

        staticUserDateClientService.setStaticDate(userId, date); //set the date

        Response response=staticUserDateClientService.getStaticDate(userId); //get it back
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Object entity=response.getEntity();
        assertTrue(entity instanceof UserDateResult); // verify that it's the right object

        UserDateResult userDateResult = (UserDateResult) entity;
        assertEquals(date, userDateResult.getDate()); // verify that the date is the same one we put in
    }

    @Test
    public void testGetStaticDate_invalidUserId() {
        Response response=staticUserDateClientService.getStaticDate(null);

        // response should be a 400 ("Bad Request")
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testClearStaticDate() {
        String userId=USER_01;

        staticUserDateClientService.setStaticDate(userId, DATE_03); //add a date entry for the user

        Response response=staticUserDateClientService.getStaticDate(userId);
        assertNotNull(response.getEntity()); // ensure we are getting an entity back

        response = staticUserDateClientService.clearStaticDate(userId); // try to clear it
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        response=staticUserDateClientService.getStaticDate(userId); // try to retrieve it again

        Object entity=response.getEntity();
        assertTrue(entity instanceof UserDateResult); // verify that it's the right object

        UserDateResult userDateResult = (UserDateResult) entity;
        assertNull(userDateResult.getDate()); // date should be null now
    }

    @Test
    public void testClearStaticDate_invalidUserId() {
        Response response=staticUserDateClientService.clearStaticDate(null);

        // response should be a 400 ("Bad Request")
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetStaticDates() {
        String[] users=new String[]{USER_01, USER_02}; //create an array of users
        String date=DATE_01;

        for (String user:users) {
            staticUserDateClientService.setStaticDate(user, date); //add a static date for each user
        }

        Response response=staticUserDateClientService.getStaticDates(); //get all the static dates back
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Object entity=response.getEntity();
        assertTrue(entity instanceof Collection<?>); //needs to be a list

        Collection<?> userDateResults=(Collection<?>) entity;
        assertEquals(users.length, userDateResults.size()); // should get back the same # of entries we put in

        boolean userFound=false;
        for (Object result:userDateResults) { // look for a user in the results
            assertTrue(result instanceof UserDateResult); // all objects in the collection should be a UserDateResult
            UserDateResult userDateResult = (UserDateResult) result;
            if (userDateResult.getUserId().equals(USER_01)) {
                assertEquals(date, userDateResult.getDate()); // make sure date matches
                userFound=true;
                break;
            }
        }
        assertTrue(userFound); // make sure we found the user we were looking for
    }
}
