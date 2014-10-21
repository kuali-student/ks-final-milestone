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
import org.kuali.student.common.test.util.CrudInfoTester;
import org.kuali.student.common.test.util.ListOfObjectTester;
import org.kuali.student.common.test.util.ListOfStringTester;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomFixedResourceInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.dto.RoomUsageInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.SchedulingServiceDataLoader;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleBatchInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleDisplayInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestDisplayInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleTransactionInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class tests Scheduling Service
 *
 * @author Mezba Mahtab
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:scheduling-mock-impl-test-context.xml"})
public class TestSchedulingServiceMockImpl {

    ///////////////////////////////
    // DATA VARIABLES
    ///////////////////////////////

    @Resource
    private SchedulingService schedulingService;
    @Resource
    private AtpService atpService;
    @Resource
    private RoomService roomService;
    @Resource
    private TypeService typeService;
    @Resource
    private OrganizationService organizationService;

    public static String principalId1 = "123";
    public static String principalId2 = "321";
    public ContextInfo callContext = null;
    public CrudInfoTester crudInfoTester = null;

    public static final TimeOfDayInfo TOD_8_AM = new TimeOfDayInfo(8, 0);
    public static final TimeOfDayInfo TOD_8_50_AM = new TimeOfDayInfo(8, 50);
    public static final TimeOfDayInfo TOD_9_10_AM = new TimeOfDayInfo(9, 10);
    public static final TimeOfDayInfo TOD_10_AM = new TimeOfDayInfo(10, 0);
    public static final TimeOfDayInfo TOD_10_50_AM = new TimeOfDayInfo(10, 50);
    public static final TimeOfDayInfo TOD_1_PM = new TimeOfDayInfo(13, 0);
    public static final TimeOfDayInfo TOD_2_10_PM = new TimeOfDayInfo(14, 10);
    public static final TimeOfDayInfo TOD_3_PM = new TimeOfDayInfo(15, 0);
    public static final TimeOfDayInfo TOD_3_50_PM = new TimeOfDayInfo(15, 50);

    /////////////////////////////
    // ACCESSORS AND MODIFIERS
    /////////////////////////////

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    //////////////////////////////
    // TESTS AND FUNCTIONALS
    //////////////////////////////

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
    public void testCrudSchedule () throws Exception {

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
        assertTrue(IDS_SCHEDULE_INFO.isEmpty());

        // test get by type
        // -------------------
        assertEquals(actual_scheduleInfo1.getTypeKey(), SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE);
        assertEquals(actual_scheduleInfo2.getTypeKey(), SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE);
        IDS_SCHEDULE_INFO = schedulingService.getScheduleIdsByType(SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE, callContext);
        assertEquals(2, IDS_SCHEDULE_INFO.size());
        assertEquals(actual_scheduleInfo1.getId(), IDS_SCHEDULE_INFO.get(0));
        assertEquals(actual_scheduleInfo2.getId(), IDS_SCHEDULE_INFO.get(1));
        IDS_SCHEDULE_INFO = schedulingService.getScheduleIdsByType(SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE + "324", callContext);
        assertTrue(IDS_SCHEDULE_INFO.isEmpty());

        // test delete
        // -----------------
        StatusInfo status = schedulingService.deleteSchedule(actual_scheduleInfo1.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual_scheduleInfo1 = schedulingService.getSchedule(actual_scheduleInfo1.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted ScheduleInfo");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals(actual_scheduleInfo1.getId(), dnee.getMessage());
        }
    }

    @Test
    public void testCanUpdateTimeSlotRDL() throws Exception {

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

        List<ScheduleRequestComponentInfo> scheduleRequestComponentInfos = new ArrayList<ScheduleRequestComponentInfo>();
        ScheduleRequestComponentInfo scheduleRequestComponentInfo1 = new ScheduleRequestComponentInfo();
        scheduleRequestComponentInfo1.setId("11");
        scheduleRequestComponentInfo1.setIsTBA(false);
        scheduleRequestComponentInfo1.setBuildingIds(buildingIds1);
        scheduleRequestComponentInfo1.setCampusIds(campusIds1);
        scheduleRequestComponentInfo1.setOrgIds(orgIds1);
        scheduleRequestComponentInfo1.setResourceTypeKeys(resourceTypeKeys1);
        scheduleRequestComponentInfo1.setRoomIds(roomIds1);
        scheduleRequestComponentInfo1.setTimeSlotIds(timeSlotIds1);
        scheduleRequestComponentInfos.add(scheduleRequestComponentInfo1);
        ScheduleRequestComponentInfo scheduleRequestComponentInfo2 = new ScheduleRequestComponentInfo();
        scheduleRequestComponentInfo2.setId("12");
        scheduleRequestComponentInfo2.setIsTBA(false);
        scheduleRequestComponentInfo2.setBuildingIds(buildingIds2);
        scheduleRequestComponentInfo2.setCampusIds(campusIds2);
        scheduleRequestComponentInfo2.setOrgIds(orgIds2);
        scheduleRequestComponentInfo2.setResourceTypeKeys(resourceTypeKeys2);
        scheduleRequestComponentInfo2.setRoomIds(roomIds2);
        scheduleRequestComponentInfo2.setTimeSlotIds(timeSlotIds2);
        scheduleRequestComponentInfos.add(scheduleRequestComponentInfo2);


        ScheduleRequestSetInfo srsInfo  = new ScheduleRequestSetInfo();
        crudInfoTester.initializeInfoForTestCreate(srsInfo,SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET, SchedulingServiceConstants.SCHEDULE_REQUEST_SET_STATE_CREATED);
        srsInfo.setRefObjectTypeKey("ao_type");
        srsInfo.setRefObjectIds(Arrays.asList("ao1"));
        ScheduleRequestSetInfo srs = schedulingService.createScheduleRequestSet(srsInfo.getTypeKey(), "ao_type", srsInfo, callContext);
        ScheduleRequestInfo expected = new ScheduleRequestInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected, SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        expected.setScheduleRequestComponents(scheduleRequestComponentInfos);
        expected.setScheduleRequestSetId(srs.getId());
        ScheduleRequestInfo actual = schedulingService.createScheduleRequest(expected.getTypeKey(), expected, callContext);
        crudInfoTester.testCreate(expected, actual);

        assertFalse(schedulingService.canUpdateTimeSlot(expected.getScheduleRequestComponents().get(1).getTimeSlotIds().get(1), callContext));
        assertTrue(schedulingService.canUpdateTimeSlot("test-for-nonexistent-timeslot-rdl", callContext));
    }

    @Test
    public void testCanUpdateTimeSlotADL() throws Exception {

        List<String> timeSlotIds1 = ListOfStringTester.generateRandomListOfStringIds(4, 100);
        List<String> timeSlotIds2 = ListOfStringTester.generateRandomListOfStringIds(2, 100);
        ScheduleComponentInfo scheduleComponentInfo1 = new ScheduleComponentInfo();
        scheduleComponentInfo1.setId("1");
        scheduleComponentInfo1.setRoomId("r1");
        scheduleComponentInfo1.setTimeSlotIds(timeSlotIds1);
        ScheduleComponentInfo scheduleComponentInfo2 = new ScheduleComponentInfo();
        scheduleComponentInfo2.setId("2");
        scheduleComponentInfo2.setRoomId("r2");
        scheduleComponentInfo2.setTimeSlotIds(timeSlotIds2);
        List<ScheduleComponentInfo> scheduleComponentInfos = new ArrayList<ScheduleComponentInfo>();
        scheduleComponentInfos.add(scheduleComponentInfo1);
        scheduleComponentInfos.add(scheduleComponentInfo2);

        // test create
        // ----------------
        ScheduleInfo expected_scheduleInfo = new ScheduleInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected_scheduleInfo, SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE, SchedulingServiceConstants.SCHEDULE_STATE_ACTIVE);
        expected_scheduleInfo.setAtpId("124");
        expected_scheduleInfo.setScheduleComponents(scheduleComponentInfos);
        ScheduleInfo actual_scheduleInfo1 = schedulingService.createSchedule(expected_scheduleInfo.getTypeKey(), expected_scheduleInfo, callContext);
        crudInfoTester.testCreate(expected_scheduleInfo, actual_scheduleInfo1);

        assertFalse(schedulingService.canUpdateTimeSlot(expected_scheduleInfo.getScheduleComponents().get(1).getTimeSlotIds().get(1), callContext));
        assertTrue(schedulingService.canUpdateTimeSlot("test-for-nonexistent-timeslot-adl", callContext));

    }

    // test crud ScheduleRequest
    @Test
    public void testCrudScheduleRequest () throws Exception {


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
        ScheduleRequestSetInfo srsInfo  = new ScheduleRequestSetInfo();
        crudInfoTester.initializeInfoForTestCreate(srsInfo,SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET, SchedulingServiceConstants.SCHEDULE_REQUEST_SET_STATE_CREATED);
        srsInfo.setRefObjectTypeKey("ao_type");
        srsInfo.setRefObjectIds(Arrays.asList("ao1"));
        ScheduleRequestSetInfo srs = schedulingService.createScheduleRequestSet(srsInfo.getTypeKey(), "ao_type", srsInfo, callContext);
        ScheduleRequestInfo expected = new ScheduleRequestInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected, SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        expected.setScheduleRequestComponents(scheduleRequestComponentInfos1);
        expected.setScheduleRequestSetId(srs.getId());
        ScheduleRequestInfo actual = schedulingService.createScheduleRequest(expected.getTypeKey(), expected, callContext);
        crudInfoTester.testCreate(expected, actual);
        new ListOfObjectTester().check(expected.getScheduleRequestComponents(), actual.getScheduleRequestComponents());
        new ListOfObjectTester().check(scheduleRequestComponentInfos1, actual.getScheduleRequestComponents());

        // test read
        // ----------------
        expected = actual;
        actual = schedulingService.getScheduleRequest(expected.getId(), callContext);
        crudInfoTester.initializeInfoForTestRead(expected);
        crudInfoTester.testRead(expected, actual);
        assertEquals(expected.getScheduleRequestSetId(), actual.getScheduleRequestSetId());
        new ListOfObjectTester().check(expected.getScheduleRequestComponents(), actual.getScheduleRequestComponents());
        new ListOfObjectTester().check(scheduleRequestComponentInfos1, actual.getScheduleRequestComponents());

        // test update
        // ----------------
        expected = actual;
        expected.setScheduleId("sr_updated_sched1");
        crudInfoTester.initializeInfoForTestUpdate(expected, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        expected.setScheduleRequestComponents(scheduleRequestComponentInfos2);
        actual = schedulingService.updateScheduleRequest(actual.getId(), expected, callContext);
        crudInfoTester.testUpdate(expected, actual);
        assertEquals(actual.getScheduleId(), "sr_updated_sched1");
        new ListOfObjectTester().check(expected.getScheduleRequestComponents(), actual.getScheduleRequestComponents());
        new ListOfObjectTester().check(scheduleRequestComponentInfos2, actual.getScheduleRequestComponents());

        // create a 2nd ScheduleRequest
        // --------------------------------
        ScheduleRequestSetInfo srsInfo2  = new ScheduleRequestSetInfo();
        crudInfoTester.initializeInfoForTestCreate(srsInfo2,SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET, SchedulingServiceConstants.SCHEDULE_REQUEST_SET_STATE_CREATED);
        srsInfo2.setRefObjectTypeKey("ao_type");
        srsInfo2.setRefObjectIds(Arrays.asList("ao2"));
        ScheduleRequestSetInfo srs2 = schedulingService.createScheduleRequestSet(srsInfo2.getTypeKey(), "ao_type", srsInfo2, callContext);
        ScheduleRequestInfo expected2 = new ScheduleRequestInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected2, SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        expected2.setScheduleId("sr_schedid_exp2");
        expected2.setScheduleRequestSetId(srs2.getId());
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
        assertTrue(IDS.isEmpty());

        // test get by type
        // -------------------
        assertEquals(actual.getTypeKey(), SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);
        assertEquals(actual2.getTypeKey(), SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);
        IDS = schedulingService.getScheduleRequestIdsByType(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, callContext);
        assertEquals(2, IDS.size());
        assertEquals(actual.getId(), IDS.get(0));
        assertEquals(actual2.getId(), IDS.get(1));
        IDS = schedulingService.getScheduleRequestIdsByType(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST + "123", callContext);
        assertTrue(IDS.isEmpty());

        // test get ids by ref object type key
        // --------------------------------------
        assertEquals(actual2.getScheduleId(), "sr_schedid_exp2");
        IDS = schedulingService.getScheduleRequestIdsByRefObject("ao_type", "ao1", callContext);
        assertEquals(1, IDS.size());
        assertEquals(expected.getId(), IDS.get(0));

        // test delete
        // -----------------
        StatusInfo status = schedulingService.deleteScheduleRequest(actual.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = schedulingService.getScheduleRequest(actual.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted ScheduleRequestInfo");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals(actual.getId(), dnee.getMessage());
        }
    }


    // test crud ScheduleTransaction
    @Test
    public void testCrudScheduleTransaction () throws Exception {

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
        expected.setScheduleId("124");
        expected.setScheduleBatchId("100");
        expected.setStatusMessage("1980 - Status Could Not Compute");
        expected.setScheduleRequestComponents(scheduleRequestComponentInfos1);
        ScheduleTransactionInfo actual = schedulingService.createScheduleTransaction(expected.getScheduleBatchId(), expected.getTypeKey(), expected, callContext);
        crudInfoTester.testCreate(expected, actual);
        assertEquals("124", actual.getScheduleId());
        assertEquals(expected.getScheduleBatchId(), actual.getScheduleBatchId());
        assertEquals("100", actual.getScheduleBatchId());
        assertEquals(expected.getScheduleId(), actual.getScheduleId());
        assertEquals(expected.getStatusMessage(), actual.getStatusMessage());
        assertEquals("1980 - Status Could Not Compute", actual.getStatusMessage());

        // test read
        // ----------------
        expected = actual;
        actual = schedulingService.getScheduleTransaction(expected.getId(), callContext);
        crudInfoTester.initializeInfoForTestRead(expected);
        crudInfoTester.testRead(expected, actual);
        assertEquals("124", actual.getScheduleId());
        assertEquals(expected.getScheduleBatchId(), actual.getScheduleBatchId());
        assertEquals("100", actual.getScheduleBatchId());
        assertEquals(expected.getScheduleId(), actual.getScheduleId());
        assertEquals(expected.getStatusMessage(), actual.getStatusMessage());
        assertEquals("1980 - Status Could Not Compute", actual.getStatusMessage());

        // test update
        // ----------------
        expected = actual;
        crudInfoTester.initializeInfoForTestUpdate(expected, SchedulingServiceConstants.SCHEDULE_TRANSACTION_STATE_COMPLETED);
        expected.setScheduleId("420");
        expected.setScheduleBatchId("404");
        expected.setStatusMessage("2012 - Status Could Not Compute");
        actual = schedulingService.updateScheduleTransaction(actual.getId(), expected, callContext);
        crudInfoTester.testUpdate(expected, actual);
        assertEquals("420", actual.getScheduleId());
        assertEquals(expected.getScheduleBatchId(), actual.getScheduleBatchId());
        assertEquals("404", actual.getScheduleBatchId());
        assertEquals(expected.getScheduleId(), actual.getScheduleId());
        assertEquals(expected.getStatusMessage(), actual.getStatusMessage());
        assertEquals("2012 - Status Could Not Compute", actual.getStatusMessage());

        // create a 2nd ScheduleTransaction
        // -------------------------------------
        ScheduleTransactionInfo expected2 = new ScheduleTransactionInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected2, SchedulingServiceConstants.SCHEDULE_TRANSACTION_TYPE_REQUEST_TRANSACTION, SchedulingServiceConstants.SCHEDULE_TRANSACTION_STATE_COMPLETED);
        expected2.setScheduleId("421");
        expected2.setScheduleBatchId("200");
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
        assertTrue(IDS.isEmpty());

        // test get by type
        // -------------------
        assertEquals(actual.getTypeKey(), SchedulingServiceConstants.SCHEDULE_TRANSACTION_TYPE_REQUEST_TRANSACTION);
        assertEquals(actual2.getTypeKey(), SchedulingServiceConstants.SCHEDULE_TRANSACTION_TYPE_REQUEST_TRANSACTION);
        IDS = schedulingService.getScheduleTransactionIdsByType(SchedulingServiceConstants.SCHEDULE_TRANSACTION_TYPE_REQUEST_TRANSACTION, callContext);
        assertEquals(2, IDS.size());
        assertEquals(actual.getId(), IDS.get(0));
        assertEquals(actual2.getId(), IDS.get(1));
        IDS = schedulingService.getScheduleTransactionIdsByType(SchedulingServiceConstants.SCHEDULE_TRANSACTION_TYPE_REQUEST_TRANSACTION + "123", callContext);
        assertTrue(IDS.isEmpty());

        // test delete
        // -----------------
        StatusInfo status = schedulingService.deleteScheduleTransaction(actual.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = schedulingService.getScheduleTransaction(actual.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted ScheduleTransactionInfo");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals(actual.getId(), dnee.getMessage());
        }
    }

    // test crud ScheduleBatch
    @Test
    public void testCrudScheduleBatch () throws Exception {

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
        assertTrue(IDS.isEmpty());

        // test get by type
        // -------------------
        assertEquals(actual.getTypeKey(), SchedulingServiceConstants.SCHEDULE_BATCH_TYPE_BATCH);
        assertEquals(actual2.getTypeKey(), SchedulingServiceConstants.SCHEDULE_BATCH_TYPE_BATCH);
        IDS = schedulingService.getScheduleBatchIdsByType(SchedulingServiceConstants.SCHEDULE_BATCH_TYPE_BATCH, callContext);
        assertEquals(2, IDS.size());
        assertEquals(actual.getId(), IDS.get(0));
        assertEquals(actual2.getId(), IDS.get(1));
        IDS = schedulingService.getScheduleBatchIdsByType(SchedulingServiceConstants.SCHEDULE_BATCH_TYPE_BATCH + "123", callContext);
        assertTrue(IDS.isEmpty());

        // test delete
        // -----------------
        StatusInfo status = schedulingService.deleteScheduleBatch(actual.getId(), callContext);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = schedulingService.getScheduleBatch(actual.getId(), callContext);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted ScheduleBatchInfo");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals(actual.getId(), dnee.getMessage());
        }
    }

    // test crud TimeSlot
    @Test
    public void testCrudTimeSlot() throws Exception {

        // test data
        // -----------------

        // days of week M W F
        List<Integer> DOW_M_W_F= new ArrayList<Integer>();
        DOW_M_W_F.add(Calendar.MONDAY);
        DOW_M_W_F.add(Calendar.WEDNESDAY);
        DOW_M_W_F.add(Calendar.FRIDAY);
        // days of week T TH
        List<Integer> DOW_T_TH = new ArrayList<Integer>();
        DOW_T_TH.add(Calendar.TUESDAY);
        DOW_T_TH.add(Calendar.THURSDAY);

        // test create
        // ----------------
        TimeSlotInfo expected = new TimeSlotInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected, SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY);
        expected.setName("1"); //Timeslot Code.
        expected.setWeekdays(DOW_M_W_F);
        TimeOfDayInfo startTime = TOD_8_AM; // 8 AM
        expected.setStartTime(startTime);
        TimeOfDayInfo endTime = TOD_8_50_AM; // 8:50 AM
        expected.setEndTime(endTime);
        TimeSlotInfo actual = schedulingService.createTimeSlot(expected.getTypeKey(), expected, callContext);
        crudInfoTester.testCreate(expected, actual);
        assertEquals(expected.getWeekdays(), actual.getWeekdays());
        assertEquals(DOW_M_W_F, actual.getWeekdays());
        assertEquals(expected.getStartTime(), actual.getStartTime());
        assertEquals(TOD_8_AM.getHour(), actual.getStartTime().getHour());
        assertEquals(TOD_8_AM.getMinute(), actual.getStartTime().getMinute());
        assertEquals(TOD_8_AM.getSecond(), actual.getStartTime().getSecond());
        assertEquals(expected.getEndTime(), actual.getEndTime());
        assertEquals(TOD_8_50_AM.getHour(), actual.getEndTime().getHour());
        assertEquals(TOD_8_50_AM.getMinute(), actual.getEndTime().getMinute());
        assertEquals(TOD_8_50_AM.getSecond(), actual.getEndTime().getSecond());

        // test read
        // ----------------
        expected = actual;
        actual = schedulingService.getTimeSlot(expected.getId(), callContext);
        crudInfoTester.initializeInfoForTestRead(expected);
        crudInfoTester.testRead(expected, actual);

        // test update
        // ----------------
        expected = actual;
        expected.setWeekdays(DOW_T_TH);
        TimeOfDayInfo startTime_update = TOD_10_AM; // 10 AM
        expected.setStartTime(startTime_update);
        TimeOfDayInfo endTime_update = TOD_10_50_AM; // 10:50 AM
        expected.setEndTime(endTime_update);
        crudInfoTester.initializeInfoForTestUpdate(expected, SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY);
        actual = schedulingService.updateTimeSlot(actual.getId(), expected, callContext);
        crudInfoTester.testUpdate(expected, actual);
        assertEquals(expected.getWeekdays(), actual.getWeekdays());
        assertEquals(DOW_T_TH, actual.getWeekdays());
        assertEquals(expected.getStartTime(), actual.getStartTime());
        assertEquals(expected.getEndTime(), actual.getEndTime());

        // create a 2nd TimeSlot
        // -------------------------------
        TimeSlotInfo expected2 = new TimeSlotInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected2, SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY);
        expected2.setWeekdays(DOW_T_TH);
        TimeOfDayInfo startTime2 = TOD_10_AM; // 10 AM
        expected2.setStartTime(startTime2);
        TimeOfDayInfo endTime2 = TOD_10_50_AM; // 10:50 AM
        expected2.setEndTime(endTime2);
        TimeSlotInfo actual2 = schedulingService.createTimeSlot(expected2.getTypeKey(), expected2, callContext);

        // test bulk get
        // -------------------
        List<String> IDS = new ArrayList<String>();
        IDS.add(actual.getId());
        IDS.add(actual2.getId());
        List<TimeSlotInfo> infos = schedulingService.getTimeSlotsByIds(IDS, callContext);
        assertEquals(IDS.size(), infos.size());
        for (TimeSlotInfo info: infos) {
            if (!IDS.remove(info.getId())) {
                fail(info.getId());
            }
        }
        assertTrue(IDS.isEmpty());

        // test get by type
        // -------------------
        assertEquals(actual.getTypeKey(), SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY);
        assertEquals(actual2.getTypeKey(), SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY);
        IDS = schedulingService.getTimeSlotIdsByType(SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, callContext);
        assertEquals(2, IDS.size());
        assertEquals(actual.getId(), IDS.get(0));
    }

    // test some TimeSlot operations as specified in Jira 525
    // as well as older tests that I didn't want to delete
    @Test
    public void testTimeSlotOperations() throws Exception {

        // Note: these tests also incorporate specific tests as mentioned in Jira 525
        // See: https://jira.kuali.org/browse/KSENROLL-525

        // times
        Long START_TIME_MILLIS_8_00_AM = (long) (8 * 60 * 60 * 1000);
        Long START_TIME_MILLIS_10_00_AM = (long) (10 * 60 * 60 * 1000);
        Long START_TIME_MILLIS_1_00_PM = (long) (13 * 60 * 60 * 1000);
        Long START_TIME_MILLIS_3_00_PM = (long) (15 * 60 * 60 * 1000);

        Long END_TIME_MILLIS_8_50_AM = (long) (8 * 60 * 60 * 1000 + 50 * 60 * 1000);
        Long END_TIME_MILLIS_9_10_AM = (long) (8 * 60 * 60 * 1000 + 70 * 60 * 1000);
        Long END_TIME_MILLIS_10_50_AM = (long) (10 * 60 * 60 * 1000 + 50 * 60 * 1000);
        Long END_TIME_MILLIS_11_10_AM = (long) (10 * 60 * 60 * 1000 + 70 * 60 * 1000);
        Long END_TIME_MILLIS_1_50_PM = (long) (13 * 60 * 60 * 1000 + 50 * 60 * 1000);
        Long END_TIME_MILLIS_2_10_PM = (long) (13 * 60 * 60 * 1000 + 70 * 60 * 1000);
        Long END_TIME_MILLIS_3_50_PM = (long) (15 * 60 * 60 * 1000 + 50 * 60 * 1000);
        Long END_TIME_MILLIS_4_10_PM = (long) (15 * 60 * 60 * 1000 + 70 * 60 * 1000);

        Long START_TIME_MILLIS_5_10_PM = (long) (17 * 60 * 60 * 1000 + 10 * 60 * 1000);
        Long END_TIME_MILLIS_6_00_PM = (long) (18 * 60 * 60 * 1000);

        // days of week M W F
        List<Integer> DOW_M_W_F= new ArrayList<Integer>();
        DOW_M_W_F.add(Calendar.MONDAY);
        DOW_M_W_F.add(Calendar.WEDNESDAY);
        DOW_M_W_F.add(Calendar.FRIDAY);
        // days of week T TH
        List<Integer> DOW_T_TH = new ArrayList<Integer>();
        DOW_T_TH.add(Calendar.TUESDAY);
        DOW_T_TH.add(Calendar.THURSDAY);

        // load data for Jira 525
        // --------------------------
        CommonServiceConstants.setIsIdAllowedOnCreate(callContext, true);

        loadTimeSlotInfo("1", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_M_W_F, START_TIME_MILLIS_8_00_AM, END_TIME_MILLIS_8_50_AM);
        loadTimeSlotInfo("2", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_M_W_F, START_TIME_MILLIS_8_00_AM, END_TIME_MILLIS_9_10_AM);
        loadTimeSlotInfo("3", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_T_TH, START_TIME_MILLIS_8_00_AM, END_TIME_MILLIS_8_50_AM);
        loadTimeSlotInfo("4", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_T_TH, START_TIME_MILLIS_8_00_AM, END_TIME_MILLIS_9_10_AM);
        loadTimeSlotInfo("5", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_M_W_F, START_TIME_MILLIS_10_00_AM, END_TIME_MILLIS_10_50_AM);
        loadTimeSlotInfo("6", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_M_W_F, START_TIME_MILLIS_10_00_AM, END_TIME_MILLIS_11_10_AM);
        loadTimeSlotInfo("7", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_T_TH, START_TIME_MILLIS_10_00_AM, END_TIME_MILLIS_10_50_AM);
        loadTimeSlotInfo("8", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_T_TH, START_TIME_MILLIS_10_00_AM, END_TIME_MILLIS_11_10_AM);
        loadTimeSlotInfo("9", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_M_W_F, START_TIME_MILLIS_1_00_PM, END_TIME_MILLIS_1_50_PM);
        loadTimeSlotInfo("10", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_M_W_F, START_TIME_MILLIS_1_00_PM, END_TIME_MILLIS_2_10_PM);
        loadTimeSlotInfo("11", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_T_TH, START_TIME_MILLIS_1_00_PM, END_TIME_MILLIS_1_50_PM);
        loadTimeSlotInfo("12", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_T_TH, START_TIME_MILLIS_1_00_PM, END_TIME_MILLIS_2_10_PM);
        loadTimeSlotInfo("13", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_M_W_F, START_TIME_MILLIS_3_00_PM, END_TIME_MILLIS_3_50_PM);
        loadTimeSlotInfo("14", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_M_W_F, START_TIME_MILLIS_3_00_PM, END_TIME_MILLIS_4_10_PM);
        loadTimeSlotInfo("15", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_T_TH, START_TIME_MILLIS_3_00_PM, END_TIME_MILLIS_3_50_PM);
        loadTimeSlotInfo("16", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_T_TH, START_TIME_MILLIS_3_00_PM, END_TIME_MILLIS_4_10_PM);
        loadTimeSlotInfo("toDelete", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_M_W_F, START_TIME_MILLIS_5_10_PM, END_TIME_MILLIS_6_00_PM);
        loadTimeSlotInfo("toUpdate", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, DOW_M_W_F, START_TIME_MILLIS_5_10_PM, END_TIME_MILLIS_6_00_PM);

        CommonServiceConstants.setIsIdAllowedOnCreate(callContext, false);

        // start tests
        // ------------------

        // test get by id for all records
        for (int i = 1; i<= 16; i++) {
            TimeSlot ts = schedulingService.getTimeSlot("" + i, callContext);
            assertNotNull(ts);
            assertEquals("" + i, ts.getId());
        }

        // test specific records - 2
        TimeSlot ts = schedulingService.getTimeSlot("2", callContext);
        List<Integer> dow = ts.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(dow.contains(Calendar.MONDAY));
        assertTrue(dow.contains(Calendar.WEDNESDAY));
        assertTrue(dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(dow.contains(Calendar.TUESDAY));
        assertFalse(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getHour(), TOD_8_AM.getHour()); // 8:00 AM
        assertEquals(ts.getStartTime().getMinute(), TOD_8_AM.getMinute());
        assertEquals(ts.getStartTime().getSecond(), TOD_8_AM.getSecond());
        assertEquals(ts.getEndTime().getHour(), TOD_9_10_AM.getHour()); // 9:10 AM
        assertEquals(ts.getEndTime().getMinute(), TOD_9_10_AM.getMinute());
        assertEquals(ts.getEndTime().getSecond(), TOD_9_10_AM.getSecond());

        // test specific records - 3
        ts = schedulingService.getTimeSlot("3", callContext);
        dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(dow.contains(Calendar.MONDAY));
        assertFalse(dow.contains(Calendar.WEDNESDAY));
        assertFalse(dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(dow.contains(Calendar.TUESDAY));
        assertTrue(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getHour(), TOD_8_AM.getHour()); // 8:00 AM
        assertEquals(ts.getStartTime().getMinute(), TOD_8_AM.getMinute());
        assertEquals(ts.getStartTime().getSecond(), TOD_8_AM.getSecond());
        assertEquals(ts.getEndTime().getHour(), TOD_8_50_AM.getHour()); // 8:50 AM
        assertEquals(ts.getEndTime().getMinute(), TOD_8_50_AM.getMinute());
        assertEquals(ts.getEndTime().getSecond(), TOD_8_50_AM.getSecond());

        // test specific records - 10
        ts = schedulingService.getTimeSlot("10", callContext);
        dow = ts.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(dow.contains(Calendar.MONDAY));
        assertTrue(dow.contains(Calendar.WEDNESDAY));
        assertTrue(dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(dow.contains(Calendar.TUESDAY));
        assertFalse(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getHour(), TOD_1_PM.getHour()); // 1:00 PM
        assertEquals(ts.getStartTime().getMinute(), TOD_1_PM.getMinute());
        assertEquals(ts.getStartTime().getSecond(), TOD_1_PM.getSecond());
        assertEquals(ts.getEndTime().getHour(), TOD_2_10_PM.getHour()); // 2:10 PM
        assertEquals(ts.getEndTime().getMinute(), TOD_2_10_PM.getMinute());
        assertEquals(ts.getEndTime().getSecond(), TOD_2_10_PM.getSecond());

        // test get time slot ids by type
        List<String> l_actoff = schedulingService.getTimeSlotIdsByType(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, callContext);
        assertEquals(18, l_actoff.size());
        assertTrue(l_actoff.contains("1"));
        assertTrue(l_actoff.contains("16"));

        List l_final = schedulingService.getTimeSlotIdsByType(SchedulingServiceConstants.TIME_SLOT_TYPE_EXAM, callContext);
        assertTrue(l_final.isEmpty());

        // test case: all valid ids
        List<String> valid_ids = new ArrayList<String>();
        valid_ids.add("2");
        valid_ids.add("15");
        List<TimeSlotInfo> l_valid_ts = schedulingService.getTimeSlotsByIds(valid_ids, callContext);
        assertEquals(2, valid_ids.size());

        assertEquals("2", l_valid_ts.get(0).getId());
        ts = l_valid_ts.get(0);
        dow = ts.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(dow.contains(Calendar.MONDAY));
        assertTrue(dow.contains(Calendar.WEDNESDAY));
        assertTrue(dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(dow.contains(Calendar.TUESDAY));
        assertFalse(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getHour(), TOD_8_AM.getHour()); // 8:00 AM
        assertEquals(ts.getStartTime().getMinute(), TOD_8_AM.getMinute());
        assertEquals(ts.getStartTime().getSecond(), TOD_8_AM.getSecond());
        assertEquals(ts.getEndTime().getHour(), TOD_9_10_AM.getHour()); // 9:10 AM
        assertEquals(ts.getEndTime().getMinute(), TOD_9_10_AM.getMinute());
        assertEquals(ts.getEndTime().getSecond(), TOD_9_10_AM.getSecond());

        assertEquals("15", l_valid_ts.get(1).getId());
        ts = l_valid_ts.get(1);
        dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(dow.contains(Calendar.MONDAY));
        assertFalse(dow.contains(Calendar.WEDNESDAY));
        assertFalse(dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(dow.contains(Calendar.TUESDAY));
        assertTrue(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getHour(), TOD_3_PM.getHour()); // 3:00 PM
        assertEquals(ts.getStartTime().getMinute(), TOD_3_PM.getMinute());
        assertEquals(ts.getStartTime().getSecond(), TOD_3_PM.getSecond());
        assertEquals(ts.getEndTime().getHour(), TOD_3_50_PM.getHour()); // 3:50 AM
        assertEquals(ts.getEndTime().getMinute(), TOD_3_50_PM.getMinute());
        assertEquals(ts.getEndTime().getSecond(), TOD_3_50_PM.getSecond());

        // test case: all invalid ids
        List<String> invalid_ids = new ArrayList<String>();
        invalid_ids.add("100");
        invalid_ids.add("300");
        try {
            schedulingService.getTimeSlotsByIds(invalid_ids, callContext);
            fail("DoesNotExistException should have been thrown due to invalid ids");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("100", e.getMessage());
        }

        // test case: mixture of valid and invalid
        List<String> mix_ids = new ArrayList<String>();
        mix_ids.add("10");
        mix_ids.add("1000");
        try {
            schedulingService.getTimeSlotsByIds(mix_ids, callContext);
            fail("DoesNotExistException should have been thrown due to invalid ids");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("1000", e.getMessage());
        }

        // get valid days of week by time slot
        List<Integer> valid_days_act_off = schedulingService.getValidDaysOfWeekByTimeSlotType(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, callContext);
        // should return days Monday through Friday
        assertTrue(valid_days_act_off.contains(Calendar.MONDAY));
        assertTrue(valid_days_act_off.contains(Calendar.TUESDAY));
        assertTrue(valid_days_act_off.contains(Calendar.WEDNESDAY));
        assertTrue(valid_days_act_off.contains(Calendar.THURSDAY));
        assertTrue(valid_days_act_off.contains(Calendar.FRIDAY));
        assertFalse(valid_days_act_off.contains(Calendar.SATURDAY));
        assertFalse(valid_days_act_off.contains(Calendar.SUNDAY));

        List<Integer> valid_days_final = schedulingService.getValidDaysOfWeekByTimeSlotType(SchedulingServiceConstants.TIME_SLOT_TYPE_EXAM, callContext);
        // should not return any days
        assertFalse(valid_days_final.contains(Calendar.MONDAY));
        assertFalse(valid_days_final.contains(Calendar.TUESDAY));
        assertFalse(valid_days_final.contains(Calendar.WEDNESDAY));
        assertFalse(valid_days_final.contains(Calendar.THURSDAY));
        assertFalse(valid_days_final.contains(Calendar.FRIDAY));
        assertFalse(valid_days_final.contains(Calendar.SATURDAY));
        assertFalse(valid_days_final.contains(Calendar.SUNDAY));

        // test getTimeSlotsByDaysAndStartTime
        // should return records 3 and 4
        dow = new ArrayList<Integer>();
        dow.add(Calendar.TUESDAY);
        dow.add(Calendar.THURSDAY);
        TimeOfDayInfo startTime = TOD_8_AM; // 8 AM
        List<TimeSlotInfo> tsi = schedulingService.getTimeSlotsByDaysAndStartTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, dow, startTime, callContext);
        assertEquals(2, tsi.size());

        assertEquals("3", tsi.get(0).getId());
        ts = tsi.get(0);
        List<Integer> ts_dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(ts_dow.contains(Calendar.MONDAY));
        assertFalse(ts_dow.contains(Calendar.WEDNESDAY));
        assertFalse(ts_dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(ts_dow.contains(Calendar.TUESDAY));
        assertTrue(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getHour(), TOD_8_AM.getHour());
        assertEquals(ts.getStartTime().getMinute(), TOD_8_AM.getMinute());
        assertEquals(ts.getStartTime().getSecond(), TOD_8_AM.getSecond());

        assertEquals("4", tsi.get(1).getId());
        ts = tsi.get(1);
        ts_dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(ts_dow.contains(Calendar.MONDAY));
        assertFalse(ts_dow.contains(Calendar.WEDNESDAY));
        assertFalse(ts_dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(ts_dow.contains(Calendar.TUESDAY));
        assertTrue(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getHour(), TOD_8_AM.getHour());
        assertEquals(ts.getStartTime().getMinute(), TOD_8_AM.getMinute());
        assertEquals(ts.getStartTime().getSecond(), TOD_8_AM.getSecond());

        // test getTimeSlotsByDaysAndStartTimeAndEndTime
        // should return record 3
        dow = new ArrayList<Integer>();
        dow.add(Calendar.TUESDAY);
        dow.add(Calendar.THURSDAY);
        startTime = TOD_8_AM; // 8 AM
        TimeOfDayInfo endTime = TOD_8_50_AM; // 8:50 AM
        tsi = schedulingService.getTimeSlotsByDaysAndStartTimeAndEndTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, dow, startTime, endTime, callContext);
        assertEquals(1, tsi.size());
        assertEquals("3", tsi.get(0).getId());
        ts = tsi.get(0);
        ts_dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(ts_dow.contains(Calendar.MONDAY));
        assertFalse(ts_dow.contains(Calendar.WEDNESDAY));
        assertFalse(ts_dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(ts_dow.contains(Calendar.TUESDAY));
        assertTrue(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getHour(), TOD_8_AM.getHour());
        assertEquals(ts.getStartTime().getMinute(), TOD_8_AM.getMinute());
        assertEquals(ts.getStartTime().getSecond(), TOD_8_AM.getSecond());
        assertEquals(ts.getEndTime().getHour(), TOD_8_50_AM.getHour());
        assertEquals(ts.getEndTime().getMinute(), TOD_8_50_AM.getMinute());
        assertEquals(ts.getEndTime().getSecond(), TOD_8_50_AM.getSecond());

        // should return record 10
        dow = new ArrayList<Integer>();
        dow.add(Calendar.MONDAY);
        dow.add(Calendar.WEDNESDAY);
        dow.add(Calendar.FRIDAY);
        startTime = TOD_1_PM; // 1 PM
        endTime = TOD_2_10_PM; // 2:10 PM
        tsi = schedulingService.getTimeSlotsByDaysAndStartTimeAndEndTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD, dow, startTime, endTime, callContext);
        assertEquals(1, tsi.size());
        assertEquals("10", tsi.get(0).getId());
        ts = tsi.get(0);
        ts_dow = ts.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(ts_dow.contains(Calendar.MONDAY));
        assertTrue(ts_dow.contains(Calendar.WEDNESDAY));
        assertTrue(ts_dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(ts_dow.contains(Calendar.TUESDAY));
        assertFalse(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getHour(), TOD_1_PM.getHour());
        assertEquals(ts.getStartTime().getMinute(), TOD_1_PM.getMinute());
        assertEquals(ts.getStartTime().getSecond(), TOD_1_PM.getSecond());
        assertEquals(ts.getEndTime().getHour(), TOD_2_10_PM.getHour());
        assertEquals(ts.getEndTime().getMinute(), TOD_2_10_PM.getMinute());
        assertEquals(ts.getEndTime().getSecond(), TOD_2_10_PM.getSecond());

        // test areTimeSlotsInConflict
        assertTrue(schedulingService.areTimeSlotsInConflict("1", "2", callContext));
        assertFalse(schedulingService.areTimeSlotsInConflict("1", "3", callContext)); // different days
        assertTrue(schedulingService.areTimeSlotsInConflict("5", "6", callContext));
        assertFalse(schedulingService.areTimeSlotsInConflict("1", "5", callContext));
        assertFalse(schedulingService.areTimeSlotsInConflict("2", "6", callContext));
    }

    private void loadTimeSlotInfo (String ts_id, String stateKey, String typeKey, List<Integer> weekdays, Long startTimeInMillisecs, Long endTimeInMillisecs)
            throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        TimeSlotInfo ts = new TimeSlotInfo();
        ts.setId(ts_id);
        ts.setWeekdays(weekdays);
        TimeOfDayInfo startTime = TimeOfDayHelper.setMillis(startTimeInMillisecs);
        ts.setStartTime(startTime);
        TimeOfDayInfo endTime = TimeOfDayHelper.setMillis(endTimeInMillisecs);
        ts.setEndTime(endTime);
        ts.setStateKey(stateKey);
        ts.setTypeKey(typeKey);
        schedulingService.createTimeSlot(typeKey, ts, callContext);
    }

    // test the schedule display methods
    @Test
    public void testDisplayObjects () throws Exception {

        // test data
        // -------------------------

        // create Building BUILDING
        BuildingInfo BUILDING = new BuildingInfo();
        BUILDING.setName("TEST BLD");
        BUILDING.setTypeKey("TEST BLD");
        crudInfoTester.initializeInfoForTestCreate(BUILDING, BUILDING.getTypeKey(), BUILDING.getStateKey());
        BUILDING = roomService.createBuilding(BUILDING.getTypeKey(), BUILDING, callContext);
        List<String> BLD_IDS = new ArrayList<String>();
        BLD_IDS.add(BUILDING.getId());

        // create Room ROOM
        RoomInfo ROOM = new RoomInfo();
        ROOM.setRoomCode("TEST ROOM");
        ROOM.setName("LAB");
        ROOM.setTypeKey("TEST ROOM");
        ROOM.setStateKey("TEST");
        crudInfoTester.initializeInfoForTestCreate(ROOM, ROOM.getTypeKey(), ROOM.getStateKey());
        ROOM.setAccessibilityTypeKeys(new ArrayList<String>());
        ROOM.setRoomFixedResources(new ArrayList<RoomFixedResourceInfo>());
        ROOM.setRoomUsages(new ArrayList<RoomUsageInfo>());
        ROOM.setBuildingId(BUILDING.getId());
        ROOM = roomService.createRoom(BUILDING.getId(), ROOM.getTypeKey(), ROOM, callContext);
        List<String> ROOM_IDS = new ArrayList<String>();
        ROOM_IDS.add(ROOM.getId());

        // create TimeSlot TIME_SLOT
        List<Integer> DOW_T_TH = new ArrayList<Integer>();
        DOW_T_TH.add(Calendar.TUESDAY);
        DOW_T_TH.add(Calendar.THURSDAY);
        TimeSlotInfo expected = new TimeSlotInfo() ;
        crudInfoTester.initializeInfoForTestCreate(expected, SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY);
        expected.setWeekdays(DOW_T_TH);
        TimeOfDayInfo startTime = new TimeOfDayInfo(8, 0); // 8 AM
        expected.setStartTime(startTime);
        TimeOfDayInfo endTime = new TimeOfDayInfo(8, 50); // 8:50 AM
        expected.setEndTime(endTime);
        TimeSlotInfo TIME_SLOT = schedulingService.createTimeSlot(SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, expected, callContext);
        List<String> TIME_SLOT_IDS = new ArrayList<String>();
        TIME_SLOT_IDS.add(TIME_SLOT.getId());

        // create ScheduleComponent SCHEDULE_COMPONENT
        ScheduleComponentInfo SCHEDULE_COMPONENT = new ScheduleComponentInfo();
        SCHEDULE_COMPONENT.setId("0");
        SCHEDULE_COMPONENT.setRoomId(ROOM.getId());
        SCHEDULE_COMPONENT.setTimeSlotIds(TIME_SLOT_IDS);
        List<ScheduleComponentInfo> SCHEDULE_COMPONENTS = new ArrayList<ScheduleComponentInfo>();
        SCHEDULE_COMPONENTS.add(SCHEDULE_COMPONENT);

        // create an academic time period ATP_INFO
        AtpInfo ATP_INFO = new AtpInfo();
        ATP_INFO.setStartDate(new Date(System.currentTimeMillis()));
        ATP_INFO.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        ATP_INFO = atpService.createAtp(AtpServiceConstants.ATP_FALL_TYPE_KEY, ATP_INFO, callContext);

        // create a schedule SCHEDULE
        ScheduleInfo SCHEDULE = new ScheduleInfo() ;
        crudInfoTester.initializeInfoForTestCreate(SCHEDULE, SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE, SchedulingServiceConstants.SCHEDULE_STATE_ACTIVE);
        SCHEDULE.setAtpId(ATP_INFO.getId());
        SCHEDULE.setScheduleComponents(SCHEDULE_COMPONENTS);
        SCHEDULE = schedulingService.createSchedule(SCHEDULE.getTypeKey(), SCHEDULE, callContext);

        // test get display object
        ScheduleDisplayInfo scheduleDisplayInfo = schedulingService.getScheduleDisplay(SCHEDULE.getId(), callContext);
        assertEquals(scheduleDisplayInfo.getId(), SCHEDULE.getId());
        assertEquals(scheduleDisplayInfo.getAtp().getId(), SCHEDULE.getAtpId());

        // create a Schedule Request
        ScheduleRequestComponentInfo SCHEDULE_REQUEST_CMP = new ScheduleRequestComponentInfo();
        SCHEDULE_REQUEST_CMP.setIsTBA(false);
        SCHEDULE_REQUEST_CMP.setBuildingIds(BLD_IDS);
        SCHEDULE_REQUEST_CMP.setRoomIds(ROOM_IDS);
        SCHEDULE_REQUEST_CMP.setTimeSlotIds(TIME_SLOT_IDS);
        List<ScheduleRequestComponentInfo> SCHEDULE_REQUEST_CMPS = new ArrayList<ScheduleRequestComponentInfo>();
        SCHEDULE_REQUEST_CMPS.add(SCHEDULE_REQUEST_CMP);

        String scheduleRequestSetId = "searchForScheduleRequestDisplaySetId";
        List<String> refObjectIds = new ArrayList<String>();
        refObjectIds.add("Ao1");
        refObjectIds.add("Ao2");
        ScheduleRequestSetInfo setInfo =  SchedulingServiceDataLoader.setupScheduleRequestSetInfo(scheduleRequestSetId, refObjectIds,
                "REF_OBJECT_URI_GLOBAL_PREFIX",
                false, 168);

        schedulingService.createScheduleRequestSet(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET,
                "REF_OBJECT_URI_GLOBAL_PREFIX", setInfo, callContext );

        ScheduleRequestInfo SCHEDULE_REQUEST = new ScheduleRequestInfo();
        SCHEDULE_REQUEST.setScheduleRequestComponents(SCHEDULE_REQUEST_CMPS);
        SCHEDULE_REQUEST.setScheduleRequestSetId(setInfo.getId());

        crudInfoTester.initializeInfoForTestCreate(SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        SCHEDULE_REQUEST = schedulingService.createScheduleRequest(SCHEDULE_REQUEST.getTypeKey(), SCHEDULE_REQUEST, callContext);

        // test the request display
        ScheduleRequestDisplayInfo SR_DISPL_INF = schedulingService.getScheduleRequestDisplay(SCHEDULE_REQUEST.getId(), callContext);
        assertEquals(SR_DISPL_INF.getScheduleRequestComponentDisplays().size(), 1);
        assertTrue(SR_DISPL_INF.getTypeKey().equalsIgnoreCase(SCHEDULE_REQUEST.getTypeKey()));


    }

}
