/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Mezba Mahtab on 9/3/12
 */
package org.kuali.student.r2.core.scheduling.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.test.util.CrudInfoTester;
import org.kuali.student.enrollment.test.util.ListOfObjectTester;
import org.kuali.student.enrollment.test.util.ListOfStringTester;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleBatchInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleTransactionInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class tests Scheduling Service
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:scheduling-mock-impl-test-context.xml"})
public class TestSchedulingServiceMockImpl {

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }
    @Resource
    private SchedulingService schedulingService;
    public static String principalId1 = "123";
    public static String principalId2 = "321";
    public ContextInfo callContext = null;
    public CrudInfoTester crudInfoTester = null;

    @Before
    public void setUp() {
        principalId1 = "123";
        principalId1 = "321";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId1);
        crudInfoTester = new CrudInfoTester(principalId1, principalId2, callContext);
    }

    // test ScheduleComponentInfo
    @Test
    public void testScheduleComponentInfo () {
        // the primary purpose of this test is to show the equal method of ScheduleComponentInfo works
        String id1 = "10";
        String id2 = "20";
        String roomId1 = "r1";
        String roomId2 = "r2";
        List<String> timeSlotIds1 = ListOfStringTester.generateRandomListOfStringIds(4, 100);
        List<String> timeSlotIds2 = ListOfStringTester.generateRandomListOfStringIds(2, 100);

        ScheduleComponentInfo sci_1 = new ScheduleComponentInfo();
        sci_1.setId(id1);
        sci_1.setRoomId(roomId1);
        sci_1.setTimeSlotIds(timeSlotIds1);

        ScheduleComponentInfo sci_2 = new ScheduleComponentInfo();
        sci_2.setId(id1);
        sci_2.setRoomId(roomId1);
        sci_2.setTimeSlotIds(timeSlotIds1);

        // the two ScheduleComponentInfo are now equal
        assertTrue(sci_1.equals(sci_2));

        // now we will change each field and the equals should be false, and then we will change it back and it should be true again
        sci_2.setId(id2);
        assertFalse(sci_1.equals(sci_2));
        sci_2.setId(id1);
        assertTrue(sci_1.equals(sci_2));
        sci_2.setRoomId(roomId2);
        assertFalse(sci_1.equals(sci_2));
        sci_2.setRoomId(roomId1);
        assertTrue(sci_1.equals(sci_2));
        sci_2.setTimeSlotIds(timeSlotIds2);
        assertFalse(sci_1.equals(sci_2));
        sci_2.setTimeSlotIds(timeSlotIds1);
        assertTrue(sci_1.equals(sci_2));
    }

    // test ScheduleRequestComponentInfo
    @Test
    public void testScheduleRequestComponentInfo () {
        // the primary purpose of this test is to show the equal method of ScheduleRequestComponentInfo works

        List<String> buildingIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> campusIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> orgIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> resourceTypeKeys1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> roomIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> timeSlotIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);

        List<String> buildingIds2 = ListOfStringTester.generateRandomListOfStringIds(3, 1000);
        List<String> campusIds2 = ListOfStringTester.generateRandomListOfStringIds(3, 1000);
        List<String> orgIds2 = ListOfStringTester.generateRandomListOfStringIds(3, 1000);
        List<String> resourceTypeKeys2 = ListOfStringTester.generateRandomListOfStringIds(3, 1000);
        List<String> roomIds2 = ListOfStringTester.generateRandomListOfStringIds(3, 1000);
        List<String> timeSlotIds2 = ListOfStringTester.generateRandomListOfStringIds(3, 1000);

        ScheduleRequestComponentInfo srci_1 = new ScheduleRequestComponentInfo();
        srci_1.setId("11");
        srci_1.setIsTBA(false);
        srci_1.setBuildingIds(buildingIds1);
        srci_1.setCampusIds(campusIds1);
        srci_1.setOrgIds(orgIds1);
        srci_1.setResourceTypeKeys(resourceTypeKeys1);
        srci_1.setRoomIds(roomIds1);
        srci_1.setTimeSlotIds(timeSlotIds1);

        ScheduleRequestComponentInfo srci_2 = new ScheduleRequestComponentInfo();
        srci_2.setId("11");
        srci_2.setIsTBA(false);
        srci_2.setBuildingIds(buildingIds1);
        srci_2.setCampusIds(campusIds1);
        srci_2.setOrgIds(orgIds1);
        srci_2.setResourceTypeKeys(resourceTypeKeys1);
        srci_2.setRoomIds(roomIds1);
        srci_2.setTimeSlotIds(timeSlotIds1);

        // the two ScheduleRequestComponentInfo are now equal
        assertTrue(srci_1.equals(srci_2));

        // now we will change each field and the equals should be false, and then we will change it back and it should be true again
        srci_2.setId("12");
        assertFalse(srci_1.equals(srci_2));
        srci_2.setId("11");
        assertTrue(srci_1.equals(srci_2));
        srci_2.setIsTBA(true);
        assertFalse(srci_1.equals(srci_2));
        srci_2.setIsTBA(false);
        assertTrue(srci_1.equals(srci_2));
        srci_2.setBuildingIds(buildingIds2);
        assertFalse(srci_1.equals(srci_2));
        srci_2.setBuildingIds(buildingIds1);
        assertTrue(srci_1.equals(srci_2));
        srci_2.setCampusIds(campusIds2);
        assertFalse(srci_1.equals(srci_2));
        srci_2.setCampusIds(campusIds1);
        assertTrue(srci_1.equals(srci_2));
        srci_2.setOrgIds(orgIds2);
        assertFalse(srci_1.equals(srci_2));
        srci_2.setOrgIds(orgIds1);
        assertTrue(srci_1.equals(srci_2));
        srci_2.setResourceTypeKeys(resourceTypeKeys2);
        assertFalse(srci_1.equals(srci_2));
        srci_2.setResourceTypeKeys(resourceTypeKeys1);
        assertTrue(srci_1.equals(srci_2));
        srci_2.setRoomIds(roomIds2);
        assertFalse(srci_1.equals(srci_2));
        srci_2.setRoomIds(roomIds1);
        assertTrue(srci_1.equals(srci_2));
        srci_2.setTimeSlotIds(timeSlotIds2);
        assertFalse(srci_1.equals(srci_2));
        srci_2.setTimeSlotIds(timeSlotIds1);
        assertTrue(srci_1.equals(srci_2));
    }

    // test crud Schedule
    @Test
    public void testCrudSchedule () throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,Exception {

        // test create
        // ----------------
        ScheduleInfo expected = new ScheduleInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected, SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE, SchedulingServiceConstants.SCHEDULE_STATE_ACTIVE);
        expected.setAtpId("124");
        List<String> timeSlotIds1 = ListOfStringTester.generateRandomListOfStringIds(4, 100);
        List<String> timeSlotIds2 = ListOfStringTester.generateRandomListOfStringIds(2, 100);
        ScheduleComponentInfo sci1 = new ScheduleComponentInfo();
        sci1.setId("1");
        sci1.setRoomId("r1");
        sci1.setTimeSlotIds(timeSlotIds1);
        ScheduleComponentInfo sci2 = new ScheduleComponentInfo();
        sci2.setId("2");
        sci2.setRoomId("r2");
        sci2.setTimeSlotIds(timeSlotIds2);
        List<ScheduleComponentInfo> scheduleComponentInfos = new ArrayList<ScheduleComponentInfo>();
        scheduleComponentInfos.add(sci1);
        scheduleComponentInfos.add(sci2);
        expected.setScheduleComponents(scheduleComponentInfos);
        ScheduleInfo actual = schedulingService.createSchedule(expected.getTypeKey(), expected, callContext);
        crudInfoTester.testCreate(expected, actual);
        assertEquals(expected.getAtpId(), actual.getAtpId());
        assertEquals(actual.getAtpId(), "124");
        new ListOfObjectTester().check(expected.getScheduleComponents(), actual.getScheduleComponents());
        new ListOfObjectTester().check(scheduleComponentInfos, actual.getScheduleComponents());

        // test read
        // ----------------
        expected = actual;
        actual = schedulingService.getSchedule(expected.getId(), callContext);
        crudInfoTester.initializeInfoForTestRead(expected);
        crudInfoTester.testRead(expected, actual);
        assertEquals(expected.getAtpId(), actual.getAtpId());
        assertEquals(actual.getAtpId(), "124");
        new ListOfObjectTester().check(expected.getScheduleComponents(), actual.getScheduleComponents());
        new ListOfObjectTester().check(scheduleComponentInfos, actual.getScheduleComponents());

        // test update
        // ----------------
        expected = actual;
        crudInfoTester.initializeInfoForTestUpdate(expected, SchedulingServiceConstants.SCHEDULE_STATE_ACTIVE);
        expected.setAtpId("420");
        List<ScheduleComponentInfo> scheduleComponentInfos_Updated = new ArrayList<ScheduleComponentInfo>();
        scheduleComponentInfos_Updated.add(sci2);
        expected.setScheduleComponents(scheduleComponentInfos_Updated);
        actual = schedulingService.updateSchedule(actual.getId(), expected, callContext);
        crudInfoTester.testUpdate(expected, actual);
        assertEquals(expected.getAtpId(), actual.getAtpId());
        assertEquals(actual.getAtpId(), "420");
        new ListOfObjectTester().check(expected.getScheduleComponents(), actual.getScheduleComponents());
        new ListOfObjectTester().check(scheduleComponentInfos_Updated, actual.getScheduleComponents());

        // test delete
        // -----------------
        StatusInfo status = schedulingService.deleteSchedule(actual.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = schedulingService.getSchedule(actual.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted ScheduleInfo");
        } catch (DoesNotExistException dnee) {
            // expected
        }
    }

    // test crud ScheduleRequest
    @Test
    public void testCrudScheduleRequest () throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,Exception {

        // test data (ScheduleRequestComponentInfo)
        // -------------------------------------------------
        List<String> buildingIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> campusIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> orgIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> resourceTypeKeys1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> roomIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> timeSlotIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);

        List<String> buildingIds2 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> campusIds2 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> orgIds2 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> resourceTypeKeys2 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> roomIds2 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> timeSlotIds2 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);

        List<ScheduleRequestComponentInfo> scheduleRequestComponentInfos1 = new ArrayList<ScheduleRequestComponentInfo>();
        ScheduleRequestComponentInfo srci_1_1 = new ScheduleRequestComponentInfo();
        srci_1_1.setId("11");
        srci_1_1.setIsTBA(false);
        srci_1_1.setBuildingIds(buildingIds1);
        srci_1_1.setCampusIds(campusIds1);
        srci_1_1.setOrgIds(orgIds1);
        srci_1_1.setResourceTypeKeys(resourceTypeKeys1);
        srci_1_1.setRoomIds(roomIds1);
        srci_1_1.setTimeSlotIds(timeSlotIds1);
        scheduleRequestComponentInfos1.add(srci_1_1);
        ScheduleRequestComponentInfo srci_1_2 = new ScheduleRequestComponentInfo();
        srci_1_2.setId("12");
        srci_1_2.setIsTBA(false);
        srci_1_2.setBuildingIds(buildingIds2);
        srci_1_2.setCampusIds(campusIds2);
        srci_1_2.setOrgIds(orgIds2);
        srci_1_2.setResourceTypeKeys(resourceTypeKeys2);
        srci_1_2.setRoomIds(roomIds2);
        srci_1_2.setTimeSlotIds(timeSlotIds2);
        scheduleRequestComponentInfos1.add(srci_1_2);

        List<ScheduleRequestComponentInfo> scheduleRequestComponentInfos2 = new ArrayList<ScheduleRequestComponentInfo>();
        ScheduleRequestComponentInfo srci_2_1 = new ScheduleRequestComponentInfo();
        srci_2_1.setId("21");
        srci_2_1.setIsTBA(false);
        srci_2_1.setBuildingIds(buildingIds2);
        srci_2_1.setCampusIds(campusIds2);
        srci_2_1.setOrgIds(orgIds2);
        srci_2_1.setResourceTypeKeys(resourceTypeKeys1);
        srci_2_1.setRoomIds(roomIds1);
        srci_2_1.setTimeSlotIds(timeSlotIds1);
        scheduleRequestComponentInfos2.add(srci_2_1);

        // test create
        // ----------------
        ScheduleRequestInfo expected = new ScheduleRequestInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected, SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        expected.setRefObjectId("124");
        expected.setRefObjectTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        expected.setScheduleRequestComponents(scheduleRequestComponentInfos1);
        ScheduleRequestInfo actual = schedulingService.createScheduleRequest(expected.getTypeKey(), expected, callContext);
        crudInfoTester.testCreate(expected, actual);
        assertEquals(expected.getRefObjectId(), actual.getRefObjectId());
        assertEquals("124", actual.getRefObjectId());
        assertEquals(expected.getRefObjectTypeKey(), actual.getRefObjectTypeKey());
        assertEquals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY, actual.getRefObjectTypeKey());
        new ListOfObjectTester().check(expected.getScheduleRequestComponents(), actual.getScheduleRequestComponents());
        new ListOfObjectTester().check(scheduleRequestComponentInfos1, actual.getScheduleRequestComponents());

        // test read
        // ----------------
        expected = actual;
        actual = schedulingService.getScheduleRequest(expected.getId(), callContext);
        crudInfoTester.initializeInfoForTestRead(expected);
        crudInfoTester.testRead(expected, actual);
        assertEquals(expected.getRefObjectId(), actual.getRefObjectId());
        assertEquals("124", actual.getRefObjectId());
        assertEquals(expected.getRefObjectTypeKey(), actual.getRefObjectTypeKey());
        assertEquals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY, actual.getRefObjectTypeKey());
        new ListOfObjectTester().check(expected.getScheduleRequestComponents(), actual.getScheduleRequestComponents());
        new ListOfObjectTester().check(scheduleRequestComponentInfos1, actual.getScheduleRequestComponents());

        // test update
        // ----------------
        expected = actual;
        crudInfoTester.initializeInfoForTestUpdate(expected, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        expected.setRefObjectId("420");
        expected.setRefObjectTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        expected.setScheduleRequestComponents(scheduleRequestComponentInfos2);
        actual = schedulingService.updateScheduleRequest(actual.getId(), expected, callContext);
        crudInfoTester.testUpdate(expected, actual);
        assertEquals(expected.getRefObjectId(), actual.getRefObjectId());
        assertEquals("420", actual.getRefObjectId());
        assertEquals(expected.getRefObjectTypeKey(), actual.getRefObjectTypeKey());
        assertEquals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY, actual.getRefObjectTypeKey());
        new ListOfObjectTester().check(expected.getScheduleRequestComponents(), actual.getScheduleRequestComponents());
        new ListOfObjectTester().check(scheduleRequestComponentInfos2, actual.getScheduleRequestComponents());

        // test delete
        // -----------------
        StatusInfo status = schedulingService.deleteScheduleRequest(actual.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = schedulingService.getScheduleRequest(actual.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted ScheduleRequestInfo");
        } catch (DoesNotExistException dnee) {
            // expected
        }
    }


    // test crud ScheduleTransaction
    @Test
    public void testCrudScheduleTransaction () throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,Exception {

        // test data (ScheduleRequestComponentInfo)
        // -------------------------------------------------
        List<String> buildingIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> campusIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> orgIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> resourceTypeKeys1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> roomIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> timeSlotIds1 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);

        List<String> buildingIds2 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> campusIds2 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> orgIds2 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> resourceTypeKeys2 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> roomIds2 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);
        List<String> timeSlotIds2 = ListOfStringTester.generateRandomListOfStringIds(2, 1000);

        List<ScheduleRequestComponentInfo> scheduleRequestComponentInfos1 = new ArrayList<ScheduleRequestComponentInfo>();
        ScheduleRequestComponentInfo srci_1_1 = new ScheduleRequestComponentInfo();
        srci_1_1.setId("11");
        srci_1_1.setIsTBA(false);
        srci_1_1.setBuildingIds(buildingIds1);
        srci_1_1.setCampusIds(campusIds1);
        srci_1_1.setOrgIds(orgIds1);
        srci_1_1.setResourceTypeKeys(resourceTypeKeys1);
        srci_1_1.setRoomIds(roomIds1);
        srci_1_1.setTimeSlotIds(timeSlotIds1);
        scheduleRequestComponentInfos1.add(srci_1_1);
        ScheduleRequestComponentInfo srci_1_2 = new ScheduleRequestComponentInfo();
        srci_1_2.setId("12");
        srci_1_2.setIsTBA(false);
        srci_1_2.setBuildingIds(buildingIds2);
        srci_1_2.setCampusIds(campusIds2);
        srci_1_2.setOrgIds(orgIds2);
        srci_1_2.setResourceTypeKeys(resourceTypeKeys2);
        srci_1_2.setRoomIds(roomIds2);
        srci_1_2.setTimeSlotIds(timeSlotIds2);
        scheduleRequestComponentInfos1.add(srci_1_2);

        List<ScheduleRequestComponentInfo> scheduleRequestComponentInfos2 = new ArrayList<ScheduleRequestComponentInfo>();
        ScheduleRequestComponentInfo srci_2_1 = new ScheduleRequestComponentInfo();
        srci_2_1.setId("21");
        srci_2_1.setIsTBA(false);
        srci_2_1.setBuildingIds(buildingIds2);
        srci_2_1.setCampusIds(campusIds2);
        srci_2_1.setOrgIds(orgIds2);
        srci_2_1.setResourceTypeKeys(resourceTypeKeys1);
        srci_2_1.setRoomIds(roomIds1);
        srci_2_1.setTimeSlotIds(timeSlotIds1);
        scheduleRequestComponentInfos2.add(srci_2_1);

        // test create
        // ----------------
        ScheduleTransactionInfo expected = new ScheduleTransactionInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected, SchedulingServiceConstants.SCHEDULE_TRANSACTION_TYPE_REQUEST_TRANSACTION, SchedulingServiceConstants.SCHEDULE_TRANSACTION_STATE_COMPLETED);
        expected.setRefObjectId("124");
        expected.setRefObjectTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        expected.setScheduleBatchId("100");
        expected.setScheduleId("11");
        expected.setStatusMessage("1980 - Status Could Not Compute");
        expected.setScheduleRequestComponents(scheduleRequestComponentInfos1);
        ScheduleTransactionInfo actual = schedulingService.createScheduleTransaction(expected.getScheduleBatchId(), expected.getTypeKey(), expected, callContext);
        crudInfoTester.testCreate(expected, actual);
        assertEquals(expected.getRefObjectId(), actual.getRefObjectId());
        assertEquals("124", actual.getRefObjectId());
        assertEquals(expected.getRefObjectTypeKey(), actual.getRefObjectTypeKey());
        assertEquals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY, actual.getRefObjectTypeKey());
        assertEquals(expected.getScheduleBatchId(), actual.getScheduleBatchId());
        assertEquals("100", actual.getScheduleBatchId());
        assertEquals(expected.getScheduleId(), actual.getScheduleId());
        assertEquals("11", actual.getScheduleId());
        assertEquals(expected.getStatusMessage(), actual.getStatusMessage());
        assertEquals("1980 - Status Could Not Compute", actual.getStatusMessage());

        // test read
        // ----------------
        expected = actual;
        actual = schedulingService.getScheduleTransaction(expected.getId(), callContext);
        crudInfoTester.initializeInfoForTestRead(expected);
        crudInfoTester.testRead(expected, actual);
        assertEquals(expected.getRefObjectId(), actual.getRefObjectId());
        assertEquals("124", actual.getRefObjectId());
        assertEquals(expected.getRefObjectTypeKey(), actual.getRefObjectTypeKey());
        assertEquals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY, actual.getRefObjectTypeKey());
        assertEquals(expected.getScheduleBatchId(), actual.getScheduleBatchId());
        assertEquals("100", actual.getScheduleBatchId());
        assertEquals(expected.getScheduleId(), actual.getScheduleId());
        assertEquals("11", actual.getScheduleId());
        assertEquals(expected.getStatusMessage(), actual.getStatusMessage());
        assertEquals("1980 - Status Could Not Compute", actual.getStatusMessage());

        // test update
        // ----------------
        expected = actual;
        crudInfoTester.initializeInfoForTestUpdate(expected, SchedulingServiceConstants.SCHEDULE_TRANSACTION_STATE_COMPLETED);
        expected.setRefObjectId("420");
        expected.setRefObjectTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        expected.setScheduleBatchId("404");
        expected.setScheduleId("31");
        expected.setStatusMessage("2012 - Status Could Not Compute");
        actual = schedulingService.updateScheduleTransaction(actual.getId(), expected, callContext);
        crudInfoTester.testUpdate(expected, actual);
        assertEquals(expected.getRefObjectId(), actual.getRefObjectId());
        assertEquals("420", actual.getRefObjectId());
        assertEquals(expected.getRefObjectTypeKey(), actual.getRefObjectTypeKey());
        assertEquals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY, actual.getRefObjectTypeKey());
        assertEquals(expected.getScheduleBatchId(), actual.getScheduleBatchId());
        assertEquals("404", actual.getScheduleBatchId());
        assertEquals(expected.getScheduleId(), actual.getScheduleId());
        assertEquals("31", actual.getScheduleId());
        assertEquals(expected.getStatusMessage(), actual.getStatusMessage());
        assertEquals("2012 - Status Could Not Compute", actual.getStatusMessage());

        // test delete
        // -----------------
        StatusInfo status = schedulingService.deleteScheduleTransaction(actual.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = schedulingService.getScheduleTransaction(actual.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted ScheduleTransactionInfo");
        } catch (DoesNotExistException dnee) {
            // expected
        }
    }

    // test crud ScheduleBatch
    //@Test
    public void testCrudScheduleBatch () throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,Exception {

        // test create
        // ----------------
        ScheduleBatchInfo expected = new ScheduleBatchInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected, SchedulingServiceConstants.SCHEDULE_BATCH_TYPE_BATCH, SchedulingServiceConstants.SCHEDULE_BATCH_STATE_COMPLETED);

        ScheduleBatchInfo actual = schedulingService.createScheduleBatch(expected.getTypeKey(), expected, callContext);
        crudInfoTester.testCreate(expected, actual);

        // test read
        // ----------------
        expected = actual;
        actual = schedulingService.getScheduleBatch(expected.getId(), callContext);
        crudInfoTester.initializeInfoForTestRead(expected);
        crudInfoTester.testRead(expected, actual);

        // test update
        // ----------------
        expected = actual;
        crudInfoTester.initializeInfoForTestUpdate(expected, SchedulingServiceConstants.SCHEDULE_BATCH_STATE_COMPLETED);
        actual = schedulingService.updateScheduleBatch(actual.getId(), expected, callContext);
        crudInfoTester.testUpdate(expected, actual);

        // test delete
        // -----------------
        StatusInfo status = schedulingService.deleteScheduleBatch(actual.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = schedulingService.getScheduleBatch(actual.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted ScheduleBatchInfo");
        } catch (DoesNotExistException dnee) {
            // expected
        }
    }

}
