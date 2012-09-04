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
import static org.junit.Assert.assertEquals;

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
        // the primary purpose of this test is to show the equals method of ScheduleComponentInfo works
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

        // test data
        // -------------------------
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
        List<ScheduleComponentInfo> scheduleComponentInfos_Updated = new ArrayList<ScheduleComponentInfo>();
        scheduleComponentInfos_Updated.add(sci2);
        List<String> timeSlotIds_scheduleInfo2 = ListOfStringTester.generateRandomListOfStringIds(5, 100);
        ScheduleComponentInfo sci_scheduleInfo2 = new ScheduleComponentInfo();
        sci_scheduleInfo2.setId("77");
        sci_scheduleInfo2.setRoomId("r77");
        sci_scheduleInfo2.setTimeSlotIds(timeSlotIds_scheduleInfo2);
        List<ScheduleComponentInfo> scheduleComponentInfos_scheduleInfo2 = new ArrayList<ScheduleComponentInfo>();
        scheduleComponentInfos_scheduleInfo2.add(sci_scheduleInfo2);

        // test create
        // ----------------
        ScheduleInfo expected_scheduleInfo1 = new ScheduleInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected_scheduleInfo1, SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE, SchedulingServiceConstants.SCHEDULE_STATE_ACTIVE);
        expected_scheduleInfo1.setAtpId("124");
        expected_scheduleInfo1.setScheduleComponents(scheduleComponentInfos);
        ScheduleInfo actual_scheduleInfo1 = schedulingService.createSchedule(expected_scheduleInfo1.getTypeKey(), expected_scheduleInfo1, callContext);
        crudInfoTester.testCreate(expected_scheduleInfo1, actual_scheduleInfo1);
        assertEquals(expected_scheduleInfo1.getAtpId(), actual_scheduleInfo1.getAtpId());
        assertEquals(actual_scheduleInfo1.getAtpId(), "124");
        new ListOfObjectTester().check(expected_scheduleInfo1.getScheduleComponents(), actual_scheduleInfo1.getScheduleComponents());
        new ListOfObjectTester().check(scheduleComponentInfos, actual_scheduleInfo1.getScheduleComponents());

        // test read
        // ----------------
        expected_scheduleInfo1 = actual_scheduleInfo1;
        actual_scheduleInfo1 = schedulingService.getSchedule(expected_scheduleInfo1.getId(), callContext);
        crudInfoTester.initializeInfoForTestRead(expected_scheduleInfo1);
        crudInfoTester.testRead(expected_scheduleInfo1, actual_scheduleInfo1);
        assertEquals(expected_scheduleInfo1.getAtpId(), actual_scheduleInfo1.getAtpId());
        assertEquals(actual_scheduleInfo1.getAtpId(), "124");
        new ListOfObjectTester().check(expected_scheduleInfo1.getScheduleComponents(), actual_scheduleInfo1.getScheduleComponents());
        new ListOfObjectTester().check(scheduleComponentInfos, actual_scheduleInfo1.getScheduleComponents());

        // test update
        // ----------------
        expected_scheduleInfo1 = actual_scheduleInfo1;
        crudInfoTester.initializeInfoForTestUpdate(expected_scheduleInfo1, SchedulingServiceConstants.SCHEDULE_STATE_ACTIVE);
        expected_scheduleInfo1.setAtpId("420");
        expected_scheduleInfo1.setScheduleComponents(scheduleComponentInfos_Updated);
        actual_scheduleInfo1 = schedulingService.updateSchedule(actual_scheduleInfo1.getId(), expected_scheduleInfo1, callContext);
        crudInfoTester.testUpdate(expected_scheduleInfo1, actual_scheduleInfo1);
        assertEquals(expected_scheduleInfo1.getAtpId(), actual_scheduleInfo1.getAtpId());
        assertEquals(actual_scheduleInfo1.getAtpId(), "420");
        new ListOfObjectTester().check(expected_scheduleInfo1.getScheduleComponents(), actual_scheduleInfo1.getScheduleComponents());
        new ListOfObjectTester().check(scheduleComponentInfos_Updated, actual_scheduleInfo1.getScheduleComponents());

        // create a 2nd Schedule
        // --------------------------
        ScheduleInfo expected_scheduleInfo2 = new ScheduleInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected_scheduleInfo2, SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE, SchedulingServiceConstants.SCHEDULE_STATE_ACTIVE);
        expected_scheduleInfo1.setAtpId("786");
        expected_scheduleInfo2.setScheduleComponents(scheduleComponentInfos);
        ScheduleInfo actual_scheduleInfo2 = schedulingService.createSchedule(expected_scheduleInfo2.getTypeKey(), expected_scheduleInfo2, callContext);

        // test bulk get
        // -----------------
        List<String> IDS_SCHEDULE_INFO = new ArrayList<String>();
        IDS_SCHEDULE_INFO.add(actual_scheduleInfo1.getId());
        IDS_SCHEDULE_INFO.add(actual_scheduleInfo2.getId());

        List<ScheduleInfo> scheduleInfos = schedulingService.getSchedulesByIds(IDS_SCHEDULE_INFO, callContext);
        assertEquals(IDS_SCHEDULE_INFO.size(), scheduleInfos.size());
        for (ScheduleInfo scheduleInfo : scheduleInfos) {
            if (!IDS_SCHEDULE_INFO.remove(scheduleInfo.getId())) {
                fail(scheduleInfo.getId());
            }
        }
        assertEquals(0, IDS_SCHEDULE_INFO.size());

        // test get by type
        // -------------------
        assertEquals(actual_scheduleInfo1.getTypeKey(), SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE);
        assertEquals(actual_scheduleInfo2.getTypeKey(), SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE);
        IDS_SCHEDULE_INFO = schedulingService.getScheduleIdsByType(SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE, callContext);
        assertEquals(2, IDS_SCHEDULE_INFO.size());
        assertEquals(actual_scheduleInfo1.getId(), IDS_SCHEDULE_INFO.get(0));
        assertEquals(actual_scheduleInfo2.getId(), IDS_SCHEDULE_INFO.get(1));
        IDS_SCHEDULE_INFO = schedulingService.getScheduleIdsByType(SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE + "324", callContext);
        assertEquals(0, IDS_SCHEDULE_INFO.size());

        // test delete
        // -----------------
        StatusInfo status = schedulingService.deleteSchedule(actual_scheduleInfo1.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual_scheduleInfo1 = schedulingService.getSchedule(actual_scheduleInfo1.getId(), callContext);
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

        // create a 2nd ScheduleRequest
        // --------------------------------
        ScheduleRequestInfo expected2 = new ScheduleRequestInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected2, SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        expected2.setRefObjectId("421");
        expected2.setRefObjectTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
        expected2.setScheduleRequestComponents(scheduleRequestComponentInfos2);
        ScheduleRequestInfo actual2 = schedulingService.createScheduleRequest(expected2.getTypeKey(), expected2, callContext);

        // test bulk get
        // -------------------
        List<String> IDS = new ArrayList<String>();
        IDS.add(actual.getId());
        IDS.add(actual2.getId());
        List<ScheduleRequestInfo> scheduleRequestInfos = schedulingService.getScheduleRequestsByIds(IDS, callContext);
        assertEquals(IDS.size(), scheduleRequestInfos.size());
        for (ScheduleRequestInfo scheduleRequestInfo: scheduleRequestInfos) {
            if (!IDS.remove(scheduleRequestInfo.getId())) {
                fail(scheduleRequestInfo.getId());
            }
        }
        assertEquals(0, IDS.size());

        // test get by type
        // -------------------
        assertEquals(actual.getTypeKey(), SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);
        assertEquals(actual2.getTypeKey(), SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);
        IDS = schedulingService.getScheduleRequestIdsByType(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, callContext);
        assertEquals(2, IDS.size());
        assertEquals(actual.getId(), IDS.get(0));
        assertEquals(actual2.getId(), IDS.get(1));
        IDS = schedulingService.getScheduleRequestIdsByType(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST + "123", callContext);
        assertEquals(0, IDS.size());

        // test get ids by ref object type key
        // --------------------------------------
        assertEquals(actual.getRefObjectId(), "420");
        assertEquals(actual.getRefObjectTypeKey(), CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        assertEquals(actual2.getRefObjectId(), "421");
        assertEquals(actual2.getRefObjectTypeKey(), CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
        IDS = schedulingService.getScheduleRequestIdsByRefObject(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY, "420", callContext);
        assertEquals(1, IDS.size());
        assertEquals(actual.getId(), IDS.get(0));
        IDS = schedulingService.getScheduleRequestIdsByRefObject(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY, "421", callContext);
        assertEquals(1, IDS.size());
        assertEquals(actual2.getId(), IDS.get(0));
        IDS = schedulingService.getScheduleRequestIdsByRefObject(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY + "123", "421", callContext);
        assertEquals(0, IDS.size());

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

        // create a 2nd ScheduleTransaction
        // -------------------------------------
        ScheduleTransactionInfo expected2 = new ScheduleTransactionInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected2, SchedulingServiceConstants.SCHEDULE_TRANSACTION_TYPE_REQUEST_TRANSACTION, SchedulingServiceConstants.SCHEDULE_TRANSACTION_STATE_COMPLETED);
        expected2.setRefObjectId("421");
        expected2.setRefObjectTypeKey(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
        expected2.setScheduleBatchId("200");
        expected2.setScheduleId("22");
        expected2.setStatusMessage("1980 - Status Could Not Compute");
        expected2.setScheduleRequestComponents(scheduleRequestComponentInfos2);
        ScheduleTransactionInfo actual2 = schedulingService.createScheduleTransaction(expected2.getScheduleBatchId(), expected2.getTypeKey(), expected2, callContext);

        // test bulk get
        // -------------------
        List<String> IDS = new ArrayList<String>();
        IDS.add(actual.getId());
        IDS.add(actual2.getId());
        List<ScheduleTransactionInfo> scheduleTransactionInfos = schedulingService.getScheduleTransactionsByIds(IDS, callContext);
        assertEquals(IDS.size(), scheduleTransactionInfos.size());
        for (ScheduleTransactionInfo scheduleTransactionInfo: scheduleTransactionInfos) {
            if (!IDS.remove(scheduleTransactionInfo.getId())) {
                fail(scheduleTransactionInfo.getId());
            }
        }
        assertEquals(0, IDS.size());

        // test get by type
        // -------------------
        assertEquals(actual.getTypeKey(), SchedulingServiceConstants.SCHEDULE_TRANSACTION_TYPE_REQUEST_TRANSACTION);
        assertEquals(actual2.getTypeKey(), SchedulingServiceConstants.SCHEDULE_TRANSACTION_TYPE_REQUEST_TRANSACTION);
        IDS = schedulingService.getScheduleTransactionIdsByType(SchedulingServiceConstants.SCHEDULE_TRANSACTION_TYPE_REQUEST_TRANSACTION, callContext);
        assertEquals(2, IDS.size());
        assertEquals(actual.getId(), IDS.get(0));
        assertEquals(actual2.getId(), IDS.get(1));
        IDS = schedulingService.getScheduleTransactionIdsByType(SchedulingServiceConstants.SCHEDULE_TRANSACTION_TYPE_REQUEST_TRANSACTION + "123", callContext);
        assertEquals(0, IDS.size());

        // test get ids by ref object type key
        // --------------------------------------
        assertEquals(actual.getRefObjectId(), "420");
        assertEquals(actual.getRefObjectTypeKey(), CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);
        assertEquals(actual2.getRefObjectId(), "421");
        assertEquals(actual2.getRefObjectTypeKey(), CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY);
        IDS = schedulingService.getScheduleTransactionIdsByRefObject(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY, "420", callContext);
        assertEquals(1, IDS.size());
        assertEquals(actual.getId(), IDS.get(0));
        IDS = schedulingService.getScheduleTransactionIdsByRefObject(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY, "421", callContext);
        assertEquals(1, IDS.size());
        assertEquals(actual2.getId(), IDS.get(0));
        IDS = schedulingService.getScheduleTransactionIdsByRefObject(CourseOfferingSetServiceConstants.ROLLOVER_RESULT_TYPE_KEY + "123", "421", callContext);
        assertEquals(0, IDS.size());

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
    @Test
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
        expected.setOrgId("111");
        expected.setRequestingPersonId("222");
        expected.setStatusMessage("COMPLETED");
        ScheduleBatchInfo actual = schedulingService.createScheduleBatch(expected.getTypeKey(), expected, callContext);

        crudInfoTester.testCreate(expected, actual);
        assertEquals(expected.getOrgId(), actual.getOrgId());
        assertEquals("111", actual.getOrgId());
        assertEquals(expected.getRequestingPersonId(), actual.getRequestingPersonId());
        assertEquals("222", actual.getRequestingPersonId());
        assertEquals(expected.getStatusMessage(), actual.getStatusMessage());
        assertEquals("COMPLETED", actual.getStatusMessage());

        // test read
        // ----------------
        expected = actual;
        actual = schedulingService.getScheduleBatch(expected.getId(), callContext);
        crudInfoTester.initializeInfoForTestRead(expected);
        crudInfoTester.testRead(expected, actual);
        assertEquals(expected.getOrgId(), actual.getOrgId());
        assertEquals("111", actual.getOrgId());
        assertEquals(expected.getRequestingPersonId(), actual.getRequestingPersonId());
        assertEquals("222", actual.getRequestingPersonId());
        assertEquals(expected.getStatusMessage(), actual.getStatusMessage());
        assertEquals("COMPLETED", actual.getStatusMessage());

        // test update
        // ----------------
        expected = actual;
        crudInfoTester.initializeInfoForTestUpdate(expected, SchedulingServiceConstants.SCHEDULE_BATCH_STATE_COMPLETED);
        expected.setOrgId("100");
        expected.setRequestingPersonId("100");
        expected.setStatusMessage("UPDATED");
        actual = schedulingService.updateScheduleBatch(actual.getId(), expected, callContext);
        crudInfoTester.testUpdate(expected, actual);
        assertEquals(expected.getOrgId(), actual.getOrgId());
        assertEquals("100", actual.getOrgId());
        assertEquals(expected.getRequestingPersonId(), actual.getRequestingPersonId());
        assertEquals("100", actual.getRequestingPersonId());
        assertEquals(expected.getStatusMessage(), actual.getStatusMessage());
        assertEquals("UPDATED", actual.getStatusMessage());

        // create a 2nd ScheduleBatch
        // -------------------------------
        ScheduleBatchInfo expected2 = new ScheduleBatchInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected2, SchedulingServiceConstants.SCHEDULE_BATCH_TYPE_BATCH, SchedulingServiceConstants.SCHEDULE_BATCH_STATE_COMPLETED);
        expected.setOrgId("200");
        expected.setRequestingPersonId("200");
        expected.setStatusMessage("CLONED");
        ScheduleBatchInfo actual2 = schedulingService.createScheduleBatch(expected2.getTypeKey(), expected2, callContext);

        // test bulk get
        // -------------------
        List<String> IDS = new ArrayList<String>();
        IDS.add(actual.getId());
        IDS.add(actual2.getId());
        List<ScheduleBatchInfo> infos = schedulingService.getScheduleBatchesByIds(IDS, callContext);
        assertEquals(IDS.size(), infos.size());
        for (ScheduleBatchInfo info: infos) {
            if (!IDS.remove(info.getId())) {
                fail(info.getId());
            }
        }
        assertEquals(0, IDS.size());

        // test get by type
        // -------------------
        assertEquals(actual.getTypeKey(), SchedulingServiceConstants.SCHEDULE_BATCH_TYPE_BATCH);
        assertEquals(actual2.getTypeKey(), SchedulingServiceConstants.SCHEDULE_BATCH_TYPE_BATCH);
        IDS = schedulingService.getScheduleBatchIdsByType(SchedulingServiceConstants.SCHEDULE_BATCH_TYPE_BATCH, callContext);
        assertEquals(2, IDS.size());
        assertEquals(actual.getId(), IDS.get(0));
        assertEquals(actual2.getId(), IDS.get(1));
        IDS = schedulingService.getScheduleBatchIdsByType(SchedulingServiceConstants.SCHEDULE_BATCH_TYPE_BATCH + "123", callContext);
        assertEquals(0, IDS.size());

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
