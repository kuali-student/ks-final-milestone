/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.r2.core.scheduling.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.RoomServiceConstants;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.SchedulingServiceDataLoader;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.*;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * This class contains a suite of unit tests for the KS implementation of the Scheduling Service
 *
 * @author andrewlubbers
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:scheduling-impl-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestSchedulingServiceImpl {

    @Resource(name = "schedulingServiceImpl")
    private SchedulingService schedulingService;
    @Resource(name = "mockRoomService")
    private RoomService roomService;

    @Resource(name = "typeServiceImpl" )
    private TypeService typeService;

    @Resource(name = "criteriaLookupService" )
    private CriteriaLookupService criteriaLookupService;

    @Resource(name = "atpEnrService" )
    private AtpService atpService;


    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public static String principalId = "123";
    public ContextInfo contextInfo = null;

    @Before
    public void setUp() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        try {
            loadData();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

     private void loadData() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, AlreadyExistsException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        SchedulingServiceDataLoader loader = new SchedulingServiceDataLoader(this.schedulingService);
        loader.setAtpService(atpService);
         loader.setRoomService(roomService);
        loader.loadData();

        TypeInfo info =  createTypeInfo(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, "testType", "This is a test", "refObjectUri");
        try {
            typeService.createType(info.getKey(), info, contextInfo);
        } catch (AlreadyExistsException e) {
            throw new DataValidationErrorException(e);
        }
    }

    private TypeInfo createTypeInfo(String typeKey, String typeName, String descr, String refObjectUri) {
        TypeInfo type = new TypeInfo();
        type.setKey(typeKey);
        type.setName(typeName);
        type.setDescr(new RichTextHelper().fromPlain(descr));
        type.setRefObjectUri(refObjectUri);
        type.setEffectiveDate(new Date());
        return type;
    }


    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public RoomService getRoomService() {
        if (roomService == null){
            roomService = GlobalResourceLoader.getService(new QName(RoomServiceConstants.NAMESPACE,
                    RoomServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public CriteriaLookupService getCriteriaLookupService() {
        return criteriaLookupService;
    }

    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }

    @Test
    public void testSchedulingServiceSetup() {
        assertNotNull(schedulingService);
    }

    @Test(expected=DoesNotExistException.class)
    public void testDoesNotExist() throws Exception {
        schedulingService.getTimeSlot("100", contextInfo);
    }

    @Test
    public void testgetTimeSlot() throws Exception {
        // test get by id for all records
        for (int i = 1; i<= 16; i++) {
            TimeSlot ts = schedulingService.getTimeSlot("" + i, contextInfo);
            assertNotNull(ts);
            assertEquals("" + i, ts.getId());
        }

        // test specific records - 2
        TimeSlot ts = schedulingService.getTimeSlot("2", contextInfo);
        List<Integer> dow = ts.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(dow.contains(Calendar.MONDAY));
        assertTrue(dow.contains(Calendar.WEDNESDAY));
        assertTrue(dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(dow.contains(Calendar.TUESDAY));
        assertFalse(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);
        assertEquals(ts.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_9_10_AM);

        // test specific records - 3
        ts = schedulingService.getTimeSlot("3", contextInfo);
        dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(dow.contains(Calendar.MONDAY));
        assertFalse(dow.contains(Calendar.WEDNESDAY));
        assertFalse(dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(dow.contains(Calendar.TUESDAY));
        assertTrue(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);
        assertEquals(ts.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_8_50_AM);

        // test specific records - 10
        ts = schedulingService.getTimeSlot("10", contextInfo);
        dow = ts.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(dow.contains(Calendar.MONDAY));
        assertTrue(dow.contains(Calendar.WEDNESDAY));
        assertTrue(dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(dow.contains(Calendar.TUESDAY));
        assertFalse(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_1_00_PM);
        assertEquals(ts.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_2_10_PM);
    }

    @Test
    public void testgetTimeSlotIdsByType() throws Exception {
        List<String> l_actoff = schedulingService.getTimeSlotIdsByType(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, contextInfo);
        assertEquals(18, l_actoff.size());
        assertTrue(l_actoff.contains("1"));
        assertTrue(l_actoff.contains("16"));

        List l_final = schedulingService.getTimeSlotIdsByType(SchedulingServiceConstants.TIME_SLOT_TYPE_FINAL_EXAM, contextInfo);
        assertEquals(0, l_final.size());
    }

    @Test
    public void testgetTimeSlotsByIds() throws Exception {
        // test case: all valid ids
        List<String> valid_ids = new ArrayList<String>();
        valid_ids.add("2");
        valid_ids.add("15");
        List<TimeSlotInfo> l_valid_ts = schedulingService.getTimeSlotsByIds(valid_ids, contextInfo);
        assertEquals(2, valid_ids.size());

        TimeSlot ts2 = null, ts15 = null;
        // ensure the list has only time slots with ids 2 and 15
        for(TimeSlotInfo ts : l_valid_ts) {
            assertTrue(valid_ids.contains(ts.getId()));
            if(ts.getId().equals("2")) {
                ts2 = ts;
            }
            else {
                ts15 = ts;
            }
        }

        assertEquals("2", ts2.getId());
        List<Integer> dow = ts2.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(dow.contains(Calendar.MONDAY));
        assertTrue(dow.contains(Calendar.WEDNESDAY));
        assertTrue(dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(dow.contains(Calendar.TUESDAY));
        assertFalse(dow.contains(Calendar.THURSDAY));
        assertEquals(ts2.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);
        assertEquals(ts2.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_9_10_AM);

        assertEquals("15", ts15.getId());
        dow = ts15.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(dow.contains(Calendar.MONDAY));
        assertFalse(dow.contains(Calendar.WEDNESDAY));
        assertFalse(dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(dow.contains(Calendar.TUESDAY));
        assertTrue(dow.contains(Calendar.THURSDAY));
        assertEquals(ts15.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_3_00_PM);
        assertEquals(ts15.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_3_50_PM);

        // test case: all invalid ids
        List<String> invalid_ids = new ArrayList<String>();
        invalid_ids.add("100");
        invalid_ids.add("300");
        try {
            schedulingService.getTimeSlotsByIds(invalid_ids, contextInfo);
            fail("Should not be here - test invalid_ids");
        } catch (DoesNotExistException e) {}
        catch (Exception e) { fail("Should throw DoesNotExistException - invalid_ids"); }

        // test case: mixture of valid and invalid
        List<String> mix_ids = new ArrayList<String>();
        mix_ids.add("10");
        mix_ids.add("1000");
        try {
            schedulingService.getTimeSlotsByIds(mix_ids, contextInfo);
            fail("Should not be here - test mix_ids");
        } catch (DoesNotExistException e) {}
        catch (Exception e) { fail("Should throw DoesNotExistException - mix_ids"); }
    }

    @Test
    public void getValidDaysOfWeekByTimeSlotType() throws Exception {
        List<Integer> valid_days_act_off = schedulingService.getValidDaysOfWeekByTimeSlotType(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, contextInfo);
        // should return days Monday through Saturday
        assertTrue(valid_days_act_off.contains(Calendar.MONDAY));
        assertTrue(valid_days_act_off.contains(Calendar.TUESDAY));
        assertTrue(valid_days_act_off.contains(Calendar.WEDNESDAY));
        assertTrue(valid_days_act_off.contains(Calendar.THURSDAY));
        assertTrue(valid_days_act_off.contains(Calendar.FRIDAY));
        assertTrue(valid_days_act_off.contains(Calendar.SATURDAY));
        assertTrue(valid_days_act_off.contains(Calendar.SUNDAY));

        List<Integer> valid_days_final = schedulingService.getValidDaysOfWeekByTimeSlotType(SchedulingServiceConstants.TIME_SLOT_TYPE_FINAL_EXAM, contextInfo);
        // should not return any days
        assertFalse(valid_days_final.contains(Calendar.MONDAY));
        assertFalse(valid_days_final.contains(Calendar.TUESDAY));
        assertFalse(valid_days_final.contains(Calendar.WEDNESDAY));
        assertFalse(valid_days_final.contains(Calendar.THURSDAY));
        assertFalse(valid_days_final.contains(Calendar.FRIDAY));
        assertFalse(valid_days_final.contains(Calendar.SATURDAY));
        assertFalse(valid_days_final.contains(Calendar.SUNDAY));
    }

    @Test
    public void testgetTimeSlotsByDaysAndStartTime () throws Exception {
        // should return records 3 and 4
        List<Integer> dow = new ArrayList<Integer>();
        dow.add(Calendar.TUESDAY);
        dow.add(Calendar.THURSDAY);
        TimeOfDayInfo startTime = new TimeOfDayInfo();
        startTime.setMilliSeconds(SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);
        List<TimeSlotInfo> tsi = schedulingService.getTimeSlotsByDaysAndStartTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, dow, startTime, contextInfo);
        assertEquals(2, tsi.size());

        assertEquals("3", tsi.get(0).getId());
        TimeSlot ts = tsi.get(0);
        List<Integer> ts_dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(ts_dow.contains(Calendar.MONDAY));
        assertFalse(ts_dow.contains(Calendar.WEDNESDAY));
        assertFalse(ts_dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(ts_dow.contains(Calendar.TUESDAY));
        assertTrue(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);

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
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);

    }

    @Test
    public void testgetTimeSlotsByDaysAndStartTimeAndEndTime () throws Exception {
        // should return record 3
        List<Integer> dow = new ArrayList<Integer>();
        dow.add(Calendar.TUESDAY);
        dow.add(Calendar.THURSDAY);
        TimeOfDayInfo startTime = new TimeOfDayInfo();
        startTime.setMilliSeconds(SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);
        TimeOfDayInfo endTime = new TimeOfDayInfo();
        endTime.setMilliSeconds(SchedulingServiceDataLoader.END_TIME_MILLIS_8_50_AM);
        List<TimeSlotInfo> tsi = schedulingService.getTimeSlotsByDaysAndStartTimeAndEndTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, dow, startTime, endTime, contextInfo);
        assertEquals(1, tsi.size());
        assertEquals("3", tsi.get(0).getId());
        TimeSlot ts = tsi.get(0);
        List<Integer> ts_dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(ts_dow.contains(Calendar.MONDAY));
        assertFalse(ts_dow.contains(Calendar.WEDNESDAY));
        assertFalse(ts_dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(ts_dow.contains(Calendar.TUESDAY));
        assertTrue(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_8_00_AM);
        assertEquals(ts.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_8_50_AM);

        // should return record 10
        dow = new ArrayList<Integer>();
        dow.add(Calendar.MONDAY);
        dow.add(Calendar.WEDNESDAY);
        dow.add(Calendar.FRIDAY);
        startTime = new TimeOfDayInfo();
        startTime.setMilliSeconds(SchedulingServiceDataLoader.START_TIME_MILLIS_1_00_PM);
        endTime = new TimeOfDayInfo();
        endTime.setMilliSeconds(SchedulingServiceDataLoader.END_TIME_MILLIS_2_10_PM);
        tsi = schedulingService.getTimeSlotsByDaysAndStartTimeAndEndTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, dow, startTime, endTime, contextInfo);
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
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_1_00_PM);
        assertEquals(ts.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_2_10_PM);

    }

    @Test
    public void testcreateScheduleRequest () throws Exception {
        String scheduleRequestInfoId = "createScheduleRequest-infoId";
        String scheduleRequestInfoRefObjectId = "createScheduleRequest-RefObjectId";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId";
        String scheduleRequestInfoName = "testCreateScheduleRequest";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        // add one AttributeInfo into ScheduleRequestInfo to test ScheduleRequestInfo
        AttributeInfo attributeInfo = new AttributeInfo();
        String attributeKey = "attributeInfoKey";
        String attributeValue = "attributeInfoValue";

        attributeInfo.setKey(attributeKey);
        attributeInfo.setValue(attributeValue);
        List<AttributeInfo>  attributes = new ArrayList<AttributeInfo>();

        attributes.add(attributeInfo);

        scheduleRequestInfo.setAttributes(attributes);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST,
                scheduleRequestInfo,  contextInfo);


        // returnInfo should not be null
        assertNotNull(returnInfo);
        assertTrue(returnInfo.getRefObjectId().equals(scheduleRequestInfoRefObjectId));
        assertTrue(returnInfo.getId().equals(scheduleRequestInfoId));
        assertTrue(returnInfo.getRefObjectTypeKey().equals(SchedulingServiceDataLoader.REF_OBJECT_TYPE_KEY_ACTIVITY_OFFERING));
        assertTrue(returnInfo.getName().equals(scheduleRequestInfoName));

        List<ScheduleRequestComponentInfo> componentInfoList = returnInfo.getScheduleRequestComponents();
        assertNotNull(componentInfoList);
        assertFalse(componentInfoList.isEmpty());
        ScheduleRequestComponentInfo componentInfo = componentInfoList.get(0);
        assertNotNull(componentInfo);
        assertTrue(componentInfo.getId().equals(scheduleRequestComponentInfoId));
        List<AttributeInfo> saveAttributes = returnInfo.getAttributes();
        assertNotNull(saveAttributes);
        assertFalse(saveAttributes.isEmpty());
        AttributeInfo savedAttributeInfo = saveAttributes.get(0);
        assertNotNull(savedAttributeInfo);
        assertTrue(savedAttributeInfo.getKey().equals(attributeKey));
        assertTrue(savedAttributeInfo.getValue().equals(attributeValue));

    }

    @Test
    public void testupdateScheduleRequest () throws Exception {

        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "updateScheduleRequest-infoId";
        String scheduleRequestInfoRefObjectId = "updateScheduleRequest-RefObjectId";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId";
        String scheduleRequestInfoName = "testCreateScheduleRequest";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);
        String newRequestName = "updatedScheduleRequest";
        returnInfo.setName(newRequestName);

        // set one of the schedule components to isTBA of true
        returnInfo.getScheduleRequestComponents().get(0).setIsTBA(true);

        ScheduleRequestInfo updatedReturnInfo = schedulingService.updateScheduleRequest(scheduleRequestInfoId,
                returnInfo, contextInfo);
        assertNotNull(updatedReturnInfo);
        assertTrue(updatedReturnInfo.getName().equals(newRequestName));
        assertTrue(updatedReturnInfo.getRefObjectId().equals(scheduleRequestInfoRefObjectId));
        assertTrue(updatedReturnInfo.getId().equals(scheduleRequestInfoId));
        assertTrue(updatedReturnInfo.getRefObjectTypeKey().equals(SchedulingServiceDataLoader.REF_OBJECT_TYPE_KEY_ACTIVITY_OFFERING));

        List<ScheduleRequestComponentInfo> componentInfoList = updatedReturnInfo.getScheduleRequestComponents();
        assertNotNull(componentInfoList);
        assertFalse(componentInfoList.isEmpty());
        ScheduleRequestComponentInfo componentInfo = componentInfoList.get(0);
        assertNotNull(componentInfo);
        assertTrue(componentInfo.getId().equals(scheduleRequestComponentInfoId));

        // ensure one of the components has a tba of true
        boolean oneTBAComponent = false;
        for (ScheduleRequestComponentInfo updatedComp : updatedReturnInfo.getScheduleRequestComponents()) {
            if(updatedComp.getIsTBA()) {
                oneTBAComponent = true;
                break;
            }
        }

        assertTrue(oneTBAComponent);

    }


    @Test
    public void testdeleteScheduleRequest () throws Exception {

        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "testdeleteScheduleRequest-Id";
        String scheduleRequestInfoRefObjectId = "deleteScheduleRequest-RefObjectId";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId";
        String scheduleRequestInfoName = "testDeleteScheduleRequest";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        StatusInfo deleteStatus = schedulingService.deleteScheduleRequest( scheduleRequestInfoId,  contextInfo);

        assertTrue(deleteStatus.getIsSuccess());

    }

    @Test
    public void testgetScheduleRequest () throws Exception {
        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "testGetScheduleRequest-Id";
        String scheduleRequestInfoRefObjectId = "testGetScheduleRequest-RefObjectId";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId";
        String scheduleRequestInfoName = "testGetScheduleRequest";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        // explicitly set the isTBA field on the components
        for (ScheduleRequestComponentInfo comp : scheduleRequestInfo.getScheduleRequestComponents()) {
            comp.setIsTBA(false);
        }

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST,
                scheduleRequestInfo, contextInfo);

        // creation success
        assertNotNull(returnInfo);

        ScheduleRequestInfo requestInfo = schedulingService.getScheduleRequest(scheduleRequestInfoId, contextInfo);

        // requestInfo should not be null
        assertNotNull(requestInfo);
        assertTrue(requestInfo.getRefObjectId().equals(scheduleRequestInfoRefObjectId));
        assertTrue(requestInfo.getId().equals(scheduleRequestInfoId));
        assertTrue(requestInfo.getName().equals(scheduleRequestInfoName));
        assertTrue(requestInfo.getRefObjectTypeKey().equals(SchedulingServiceDataLoader.REF_OBJECT_TYPE_KEY_ACTIVITY_OFFERING));

        List<ScheduleRequestComponentInfo> componentInfoList = requestInfo.getScheduleRequestComponents();
        assertNotNull(componentInfoList);
        assertFalse(componentInfoList.isEmpty());
        ScheduleRequestComponentInfo componentInfo = componentInfoList.get(0);
        assertNotNull(componentInfo);
        assertTrue(componentInfo.getId().equals(scheduleRequestComponentInfoId));
        assertFalse(componentInfo.getIsTBA());

    }

    @Test
    public void testgetScheduleRequestsByIds () throws Exception {
        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "testGetScheduleRequestsByIds-Id1";
        String scheduleRequestInfoRefObjectId = "testGetScheduleRequest-RefObjectId1";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId1";
        String scheduleRequestInfoName = "testGetScheduleRequestsByIds";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // create the second ScheduleRequestInfo
        String scheduleRequestInfoId2 = "testGetScheduleRequestsByIds-Id2";
        String scheduleRequestInfoRefObjectId2 = "testGetScheduleRequest-RefObjectId2";
        String scheduleRequestComponentInfoId2 = "scheduleRequest-ComponentInfoId2";
        String scheduleRequestInfoName2 = "testGetScheduleRequestsByIds2";
        ScheduleRequestInfo scheduleRequestInfo2 = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId2,
                scheduleRequestInfoRefObjectId2, scheduleRequestComponentInfoId2, scheduleRequestInfoName2);

        returnInfo  = schedulingService.createScheduleRequest(
                SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST,
                scheduleRequestInfo2,  contextInfo);

        // creation success
        assertNotNull(returnInfo);


        List<String> scheduleRequestIds = new ArrayList<String>();
        scheduleRequestIds.add(scheduleRequestInfoId);
        scheduleRequestIds.add(scheduleRequestInfoId2);

        List<ScheduleRequestInfo> requestInfos = schedulingService.getScheduleRequestsByIds(scheduleRequestIds, contextInfo);

        // requestInfo should  be null
        assertNotNull(requestInfos);
        assertTrue(!requestInfos.isEmpty());
        assertEquals(requestInfos.size(), 2);
        ScheduleRequestInfo requestInfo1 = requestInfos.get(0);
        ScheduleRequestInfo requestInfo2 = requestInfos.get(1);
        assertNotNull(requestInfo1);
        assertNotNull(requestInfo2);

        // verify first request
        assertTrue(requestInfo1.getRefObjectId().equals(scheduleRequestInfoRefObjectId));
        assertTrue(requestInfo1.getId().equals(scheduleRequestInfoId));
        assertTrue(requestInfo1.getName().equals(scheduleRequestInfoName));
        assertTrue(requestInfo1.getRefObjectTypeKey().equals(SchedulingServiceDataLoader.REF_OBJECT_TYPE_KEY_ACTIVITY_OFFERING));

        List<ScheduleRequestComponentInfo> componentInfoList = requestInfo1.getScheduleRequestComponents();
        assertNotNull(componentInfoList);
        assertFalse(componentInfoList.isEmpty());
        ScheduleRequestComponentInfo componentInfo = componentInfoList.get(0);
        assertNotNull(componentInfo);
        assertTrue(componentInfo.getId().equals(scheduleRequestComponentInfoId));

        // verify second request
        assertTrue(requestInfo2.getRefObjectId().equals(scheduleRequestInfoRefObjectId2));
        assertTrue(requestInfo2.getId().equals(scheduleRequestInfoId2));
        assertTrue(requestInfo2.getName().equals(scheduleRequestInfoName2));
        assertTrue(requestInfo2.getRefObjectTypeKey().equals(SchedulingServiceDataLoader.REF_OBJECT_TYPE_KEY_ACTIVITY_OFFERING));

        List<ScheduleRequestComponentInfo> componentInfoList2 = requestInfo2.getScheduleRequestComponents();
        assertNotNull(componentInfoList2);
        assertFalse(componentInfoList2.isEmpty());
        ScheduleRequestComponentInfo componentInfo2 = componentInfoList2.get(0);
        assertNotNull(componentInfo2);
        assertEquals(componentInfo2.getId(), scheduleRequestComponentInfoId2);
    }

    @Test
    public void testgetScheduleRequestIdsByType() throws Exception {
        String requestType =  SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST;

        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "getScheduleRequestIdsByType-Id1";
        String scheduleRequestInfoRefObjectId = "getScheduleRequestByType-RefObjectId";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId";
        String scheduleRequestInfoName = "testGetScheduleRequestsByType";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);


        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest( requestType,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // create the second ScheduleRequestInfo
        String scheduleRequestInfoId2 = "getScheduleRequestIdsByType-Id2";
        String scheduleRequestInfoRefObjectId2 = "getScheduleRequestByType-RefObjectId2";
        String scheduleRequestComponentInfoId2 = "scheduleRequest-ComponentInfoId2";
        String scheduleRequestInfoName2 = "testGetScheduleRequestsByType2";
        ScheduleRequestInfo scheduleRequestInfo2 = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId2,
                scheduleRequestInfoRefObjectId2, scheduleRequestComponentInfoId2, scheduleRequestInfoName2);

        returnInfo  = schedulingService.createScheduleRequest(requestType,
                scheduleRequestInfo2,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        List<String> requestIds = schedulingService.getScheduleRequestIdsByType(requestType, contextInfo);

        assertNotNull(requestIds);
        assertTrue(!requestIds.isEmpty());
        assertEquals(requestIds.size(), 2);
        String requestId1 = requestIds.get(0);
        String requestId2 = requestIds.get(1);
        assertNotNull(requestId1);
        assertNotNull(requestId2);
        assertTrue(requestId1.equals(scheduleRequestInfoId));
        assertTrue(requestId2.equals(scheduleRequestInfoId2));

    }

    @Test
    public void testgetScheduleRequestsByRefObject () throws Exception {
        String requestType =  SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST;

        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "ScheduleRequestsByRefObject-Id1";
        String scheduleRequestInfoRefObjectId = "getRequestsByRefObject-RefObjectId";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId1";
        String scheduleRequestInfoName = "testGetScheduleRequestByRefObject";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(requestType,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // create the second ScheduleRequestInfo
        String scheduleRequestInfoId2 = "ScheduleRequestsByRefObject-Id2";
        String scheduleRequestComponentInfoId2 = "scheduleRequest-ComponentInfoId2";
        String scheduleRequestInfoName2 = "testGetScheduleRequestByRefObject2";
        ScheduleRequestInfo scheduleRequestInfo2 = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId2,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId2, scheduleRequestInfoName2);

        returnInfo  = schedulingService.createScheduleRequest(requestType,
                scheduleRequestInfo2,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        List<ScheduleRequestInfo> scheduleRequests = schedulingService.getScheduleRequestsByRefObject(SchedulingServiceDataLoader.REF_OBJECT_TYPE_KEY_ACTIVITY_OFFERING, scheduleRequestInfoRefObjectId, contextInfo);

        assertNotNull(scheduleRequests);
        assertTrue(!scheduleRequests.isEmpty());
        assertEquals(scheduleRequests.size(), 2);

        List<String> expectedIds = new ArrayList<String>(2);
        expectedIds.add(scheduleRequestInfoId);
        expectedIds.add(scheduleRequestInfoId2);

        for (ScheduleRequestInfo sr : scheduleRequests) {
            assertNotNull(sr);
            expectedIds.remove(sr.getId());
        }

        // make sure all of our expected ids have been removed
        assertTrue(expectedIds.isEmpty());
    }

    @Test
    public void testCreateScheduleInfo () throws Exception {

        String scheduleId = "1";
        String atpId = SchedulingServiceDataLoader.ATP_ID;
        String roomId = "room1";

        ScheduleInfo scheduleInfo = SchedulingServiceDataLoader.setupScheduleInfo(scheduleId,atpId,false,roomId);
        ScheduleInfo returnedInfo = schedulingService.createSchedule(scheduleInfo.getTypeKey(),scheduleInfo,contextInfo);

        assertNotNull(returnedInfo);

        scheduleInfo = schedulingService.getSchedule(scheduleId,contextInfo);

        assertEquals(scheduleId,scheduleInfo.getId());
        assertEquals(atpId,scheduleInfo.getAtpId());
        assertEquals(1,scheduleInfo.getScheduleComponents().size());
        assertEquals(2,scheduleInfo.getScheduleComponents().get(0).getTimeSlotIds().size());
        assertEquals(false,scheduleInfo.getScheduleComponents().get(0).getIsTBA());
        assertEquals(roomId,scheduleInfo.getScheduleComponents().get(0).getRoomId());

    }


    @Test
    public void testUpdateScheduleInfo () throws Exception {

        String scheduleId = "1";
        String atpId = SchedulingServiceDataLoader.ATP_ID;
        String roomId = SchedulingServiceDataLoader.ROOM_ID;

        ScheduleInfo scheduleInfo = SchedulingServiceDataLoader.setupScheduleInfo(scheduleId,atpId,false,roomId);
        ScheduleInfo returnedInfo = schedulingService.createSchedule(scheduleInfo.getTypeKey(),scheduleInfo,contextInfo);

        assertNotNull(returnedInfo);
        assertEquals(1,returnedInfo.getScheduleComponents().size());

        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.setKey("Test");
        attributeInfo.setValue("test");

        returnedInfo.getAttributes().add(attributeInfo);
        returnedInfo.getScheduleComponents().get(0).setIsTBA(false);

        scheduleInfo = schedulingService.updateSchedule(scheduleId,returnedInfo,contextInfo);

        assertNotNull(scheduleInfo);
        assertEquals(false,scheduleInfo.getScheduleComponents().get(0).getIsTBA());
        assertEquals(1,scheduleInfo.getAttributes().size());

    }

    @Test
    public void testDeleteScheduleInfo () throws Exception {

        String scheduleId = "1";
        String atpId = SchedulingServiceDataLoader.ATP_ID;
        String roomId = SchedulingServiceDataLoader.ROOM_ID;

        ScheduleInfo scheduleInfo = SchedulingServiceDataLoader.setupScheduleInfo(scheduleId,atpId,false,roomId);
        ScheduleInfo returnedInfo = schedulingService.createSchedule(scheduleInfo.getTypeKey(),scheduleInfo,contextInfo);

        assertNotNull(returnedInfo);

        scheduleInfo = schedulingService.getSchedule(scheduleId,contextInfo);
        assertNotNull(scheduleInfo);

        StatusInfo status = schedulingService.deleteSchedule(scheduleId,contextInfo);
        assertEquals(true, status.getIsSuccess());

        try{
           schedulingService.getSchedule(scheduleId,contextInfo);
            fail("Found a schedule after deleting it.");
        } catch(DoesNotExistException e){

        }

    }

    @Test
    public void testgetScheduleRequestDisplay () throws Exception {
        String requestType =  SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST;

        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "ScheduleRequestsByRefObject-Id1";
        String scheduleRequestInfoRefObjectId = "getRequestsByRefObject-RefObjectId";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId1";
        String scheduleRequestInfoName = "testGetScheduleRequestByRefObject";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(requestType,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // get schedule request display info
        ScheduleRequestDisplayInfo displayInfo = getSchedulingService().getScheduleRequestDisplay(scheduleRequestInfoId, contextInfo);

        assertNotNull(displayInfo);
        assertEquals(displayInfo.getId(), scheduleRequestInfo.getId());
        assertEquals(displayInfo.getName(), scheduleRequestInfo.getName());
        assertTrue(displayInfo.getScheduleRequestComponentDisplays().size() > 0);
        assertTrue(displayInfo.getScheduleRequestComponentDisplays().get(0).getBuildings().size() > 0);
        assertTrue(displayInfo.getScheduleRequestComponentDisplays().get(0).getRooms().size() > 0);
        assertTrue(displayInfo.getScheduleRequestComponentDisplays().get(0).getOrgs().size()>0);
    }

    @Test
    public void testgetScheduleRequestDisplaysByIds()  throws Exception {
        String requestType =  SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST;

        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "ScheduleRequestsByRefObject-Id1";
        String scheduleRequestInfoRefObjectId = "getRequestsByRefObject-RefObjectId";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId1";
        String scheduleRequestInfoName = "testGetScheduleRequestByRefObject";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(requestType,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // get schedule request display info
        List<String> requestIdList = new ArrayList<String>();
        requestIdList.add(scheduleRequestInfoId);
        List<ScheduleRequestDisplayInfo> displayInfoList = getSchedulingService().getScheduleRequestDisplaysByIds(requestIdList, contextInfo);

        assertNotNull(displayInfoList);
        assertTrue(displayInfoList.size() > 0);
        assertEquals(displayInfoList.get(0).getId(), scheduleRequestInfo.getId());
        assertEquals(displayInfoList.get(0).getName(), scheduleRequestInfo.getName());

        for (ScheduleRequestDisplayInfo displayInfo : displayInfoList) {
            assertNotNull(displayInfo);
            assertTrue(displayInfo.getScheduleRequestComponentDisplays().size() > 0);
            assertTrue(displayInfo.getScheduleRequestComponentDisplays().get(0).getBuildings().size() > 0);
            assertTrue(displayInfo.getScheduleRequestComponentDisplays().get(0).getRooms().size() > 0);
            assertTrue(displayInfo.getScheduleRequestComponentDisplays().get(0).getOrgs().size() > 0);
        }

    }

    @Test
    public void searchForScheduleRequestDisplays() throws Exception {
        String requestType =  SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST;

        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "ScheduleRequestsByRefObject-Id1";
        String scheduleRequestInfoRefObjectId = "getRequestsByRefObject-RefObjectId";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId1";
        String scheduleRequestInfoName = "testGetScheduleRequestByRefObject";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(requestType,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // get schedule request display info
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        Predicate p = null;

        qBuilder.setPredicates();
        p = equal("name", scheduleRequestInfoName);
        pList.add(p);

        qBuilder.setPredicates(p);

        List<ScheduleRequestDisplayInfo> displayInfoList = getSchedulingService().searchForScheduleRequestDisplays(qBuilder.build(), contextInfo);

        assertNotNull(displayInfoList);
        assertTrue(displayInfoList.size() > 0);
        assertEquals(displayInfoList.get(0).getId(), scheduleRequestInfo.getId());
        assertEquals(displayInfoList.get(0).getName(), scheduleRequestInfo.getName());

        for (ScheduleRequestDisplayInfo displayInfo : displayInfoList) {
            assertNotNull(displayInfo);
            assertTrue(displayInfo.getScheduleRequestComponentDisplays().size() > 0);
            assertTrue(displayInfo.getScheduleRequestComponentDisplays().get(0).getBuildings().size() > 0);
            assertTrue(displayInfo.getScheduleRequestComponentDisplays().get(0).getRooms().size() > 0);
            assertTrue(displayInfo.getScheduleRequestComponentDisplays().get(0).getOrgs().size() > 0);
        }

        pList.clear();
        displayInfoList.clear();
        qBuilder.setPredicates();
        p = equal("refObjectId", scheduleRequestInfoRefObjectId);
        pList.add(p);

        qBuilder.setPredicates(p);

        displayInfoList = getSchedulingService().searchForScheduleRequestDisplays(qBuilder.build(), contextInfo);
        assertNotNull(displayInfoList);
        assertTrue(displayInfoList.size() > 0);
        assertEquals(displayInfoList.get(0).getId(), scheduleRequestInfo.getId());
        assertEquals(displayInfoList.get(0).getName(), scheduleRequestInfo.getName());

        for (ScheduleRequestDisplayInfo displayInfo : displayInfoList) {
            assertNotNull(displayInfo);
            assertTrue(displayInfo.getScheduleRequestComponentDisplays().size() > 0);
            assertTrue(displayInfo.getScheduleRequestComponentDisplays().get(0).getBuildings().size() > 0);
            assertTrue(displayInfo.getScheduleRequestComponentDisplays().get(0).getRooms().size() > 0);
            assertTrue(displayInfo.getScheduleRequestComponentDisplays().get(0).getOrgs().size() > 0);
        }

    }

    @Test
    public void testGetScheduleDisplay() throws Exception {

        String scheduleId = "1";
        String atpId = SchedulingServiceDataLoader.ATP_ID;
        String roomId = SchedulingServiceDataLoader.ROOM_ID;

        ScheduleInfo scheduleInfo = SchedulingServiceDataLoader.setupScheduleInfo(scheduleId,atpId,false,roomId);

        ScheduleInfo returnedInfo = schedulingService.createSchedule(scheduleInfo.getTypeKey(),scheduleInfo,contextInfo);

        assertNotNull(returnedInfo);

        ScheduleDisplayInfo displayInfo = schedulingService.getScheduleDisplay(scheduleId,contextInfo);
        assertNotNull(displayInfo);

        assertEquals(scheduleId,displayInfo.getId());
        assertNotNull(displayInfo.getAtp());
        assertNotNull(displayInfo.getScheduleComponentDisplays().get(0).getRoom());
        assertNotNull(displayInfo.getScheduleComponentDisplays().get(0).getBuilding());

    }

    @Test
    public void testSearchForScheduleDisplays() throws Exception {

        String scheduleId = "1";
        String atpId = SchedulingServiceDataLoader.ATP_ID;
        String roomId = SchedulingServiceDataLoader.ROOM_ID;

        ScheduleInfo scheduleInfo = SchedulingServiceDataLoader.setupScheduleInfo(scheduleId,atpId,false,roomId);

        ScheduleInfo returnedInfo = schedulingService.createSchedule(scheduleInfo.getTypeKey(),scheduleInfo,contextInfo);

        assertNotNull(returnedInfo);

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();

        qBuilder.setPredicates();
        Predicate p = equal("atpId", SchedulingServiceDataLoader.ATP_ID);
        pList.add(p);

        qBuilder.setPredicates(p);

        List<ScheduleDisplayInfo> list = schedulingService.searchForScheduleDisplays(qBuilder.build(),contextInfo);

        assertEquals(1,list.size());

        ScheduleDisplayInfo displayInfo = list.get(0);

        assertNotNull(displayInfo);

        assertEquals(scheduleId,displayInfo.getId());
        assertNotNull(displayInfo.getAtp());
        assertNotNull(displayInfo.getScheduleComponentDisplays().get(0).getRoom());
        assertNotNull(displayInfo.getScheduleComponentDisplays().get(0).getBuilding());

    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }
}