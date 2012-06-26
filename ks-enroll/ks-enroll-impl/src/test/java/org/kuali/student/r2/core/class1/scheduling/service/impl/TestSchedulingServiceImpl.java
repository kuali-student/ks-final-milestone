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

package org.kuali.student.r2.core.class1.scheduling.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.class1.scheduling.SchedulingServiceDataLoader;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: andy
 * Date: 6/5/12
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:scheduling-impl-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
@Ignore
public class TestSchedulingServiceImpl {

    @Resource(name = "schedulingServiceValidationDecorator")
    private SchedulingService schedulingService;

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

    private void loadData() throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        SchedulingServiceDataLoader loader = new SchedulingServiceDataLoader (this.schedulingService);
        loader.loadData();
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
        List<String> l_actoff = schedulingService.getTimeSlotIdsByType(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, contextInfo);
        assertEquals(16, l_actoff.size());
        for (int i=1; i<=16; i++) {
            assertEquals("" + i, l_actoff.get(i - 1));
        }
        List l_final = schedulingService.getTimeSlotIdsByType(SchedulingServiceConstants.TIME_SLOT_TYPE_FINAL_EXAM_KEY, contextInfo);
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

        assertEquals("2", l_valid_ts.get(0).getId());
        TimeSlot ts = l_valid_ts.get(0);
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
        assertEquals(ts.getStartTime().getMilliSeconds(), SchedulingServiceDataLoader.START_TIME_MILLIS_3_00_PM);
        assertEquals(ts.getEndTime().getMilliSeconds(), SchedulingServiceDataLoader.END_TIME_MILLIS_3_50_PM);

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
        List<Integer> valid_days_act_off = schedulingService.getValidDaysOfWeekByTimeSlotType(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, contextInfo);
        // should return days Monday through Friday
        assertTrue(valid_days_act_off.contains(Calendar.MONDAY));
        assertTrue(valid_days_act_off.contains(Calendar.TUESDAY));
        assertTrue(valid_days_act_off.contains(Calendar.WEDNESDAY));
        assertTrue(valid_days_act_off.contains(Calendar.THURSDAY));
        assertTrue(valid_days_act_off.contains(Calendar.FRIDAY));
        // should not contain Sat or Sun
        assertFalse(valid_days_act_off.contains(Calendar.SATURDAY));
        assertFalse(valid_days_act_off.contains(Calendar.SUNDAY));

        List<Integer> valid_days_final = schedulingService.getValidDaysOfWeekByTimeSlotType(SchedulingServiceConstants.TIME_SLOT_TYPE_FINAL_EXAM_KEY, contextInfo);
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
        List<TimeSlotInfo> tsi = schedulingService.getTimeSlotsByDaysAndStartTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, dow, startTime, contextInfo);
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
        List<TimeSlotInfo> tsi = schedulingService.getTimeSlotsByDaysAndStartTimeAndEndTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, dow, startTime, endTime, contextInfo);
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
        tsi = schedulingService.getTimeSlotsByDaysAndStartTimeAndEndTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, dow, startTime, endTime, contextInfo);
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
        String scheduleRequestInfoId = "57a05c2c-2b15-4a9a-b34d-b0f8551cb188";
        String scheduleRequestInfoRefObjectId = "290C813A-90C8-4782-A3F2-307ACDB89B62";
        String scheduleRequestComponentInfoId = "33547e55-f106-4eaa-96cb-eb99df6e1fd6";
        String scheduleRequestInfoName = "testCreateScheduleRequest";
        ScheduleRequestInfo scheduleRequestInfo = setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                                                SchedulingServiceConstants.SCHEDULE_REQUEST_NORMAL_REQUEST_TYPE,
                                                scheduleRequestInfo,  contextInfo);


        // returnInfo should not be null
        assertNotNull(returnInfo);
        assertTrue(returnInfo.getRefObjectId().equals(scheduleRequestInfoRefObjectId));
        assertTrue(returnInfo.getId().equals(scheduleRequestInfoId));
        assertTrue(returnInfo.getRefObjectTypeKey().equals(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING));
        assertTrue(returnInfo.getName().equals(scheduleRequestInfoName));

        List<ScheduleRequestComponentInfo> componentInfoList = returnInfo.getScheduleRequestComponents();
        assertNotNull(componentInfoList);
        assertFalse(componentInfoList.isEmpty());
        ScheduleRequestComponentInfo componentInfo = componentInfoList.get(0);
        assertNotNull(componentInfo);
        assertTrue(componentInfo.getId().equals(scheduleRequestComponentInfoId));

    }

    @Test
    public void testupdateScheduleRequest () throws Exception {

        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "f27848f3-0b07-4bd6-8755-2cdf2e6e4e93";
        String scheduleRequestInfoRefObjectId = "311b0c72-7bfc-4781-859b-8e9fd9738d0d";
        String scheduleRequestComponentInfoId = "e4b9e244-4c68-401f-be60-5c791e668729";
        String scheduleRequestInfoName = "testCreateScheduleRequest";
        ScheduleRequestInfo scheduleRequestInfo = setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                SchedulingServiceConstants.SCHEDULE_REQUEST_NORMAL_REQUEST_TYPE,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);
        String newRequestName = "updatedScheduleRequest";
        returnInfo.setName(newRequestName);

        ScheduleRequestInfo updatedReturnInfo = schedulingService.updateScheduleRequest("scheduleRequestId",
                returnInfo, contextInfo);
        assertNotNull(updatedReturnInfo);
        assertTrue(updatedReturnInfo.getName().equals(newRequestName));
        assertTrue(updatedReturnInfo.getRefObjectId().equals(scheduleRequestInfoRefObjectId));
        assertTrue(updatedReturnInfo.getId().equals(scheduleRequestInfoId));
        assertTrue(updatedReturnInfo.getRefObjectTypeKey().equals(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING));

        List<ScheduleRequestComponentInfo> componentInfoList = updatedReturnInfo.getScheduleRequestComponents();
        assertNotNull(componentInfoList);
        assertFalse(componentInfoList.isEmpty());
        ScheduleRequestComponentInfo componentInfo = componentInfoList.get(0);
        assertNotNull(componentInfo);
        assertTrue(componentInfo.getId().equals(scheduleRequestComponentInfoId));

    }


    @Test
    public void testdeleteScheduleRequest () throws Exception {

        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "59617e1d-e716-4abd-9dda-386c595c56ad";
        String scheduleRequestInfoRefObjectId = "c80780dc-0c3c-4707-8a14-f5b68d3760c1";
        String scheduleRequestComponentInfoId = "21f8675e-0159-4875-9e24-3e3013bae7dd";
        String scheduleRequestInfoName = "testDeleteScheduleRequest";
        ScheduleRequestInfo scheduleRequestInfo = setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                SchedulingServiceConstants.SCHEDULE_REQUEST_NORMAL_REQUEST_TYPE,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        StatusInfo deleteStatus = schedulingService.deleteScheduleRequest( scheduleRequestInfoId,  contextInfo);

        assertTrue(deleteStatus.getIsSuccess());

    }

    @Test
    public void testgetScheduleRequest () throws Exception {
        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "d2e9946b-2f5b-430b-943b-99c52de7a49c";
        String scheduleRequestInfoRefObjectId = "08903125-8b3c-40f5-886b-79d6aa5b4bc0";
        String scheduleRequestComponentInfoId = "6d76d8fa-8ac0-4bc6-9d44-6dacef95b21b";
        String scheduleRequestInfoName = "testGetScheduleRequest";
        ScheduleRequestInfo scheduleRequestInfo = setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                SchedulingServiceConstants.SCHEDULE_REQUEST_NORMAL_REQUEST_TYPE,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        ScheduleRequestInfo requestInfo = schedulingService.getScheduleRequest(scheduleRequestInfoId, contextInfo);

        // requestInfo should not be null
        assertNotNull(requestInfo);
        assertTrue(requestInfo.getRefObjectId().equals(scheduleRequestInfoRefObjectId));
        assertTrue(requestInfo.getId().equals(scheduleRequestInfoId));
        assertTrue(requestInfo.getName().equals(scheduleRequestInfoName));
        assertTrue(requestInfo.getRefObjectTypeKey().equals(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING));

        List<ScheduleRequestComponentInfo> componentInfoList = requestInfo.getScheduleRequestComponents();
        assertNotNull(componentInfoList);
        assertFalse(componentInfoList.isEmpty());
        ScheduleRequestComponentInfo componentInfo = componentInfoList.get(0);
        assertNotNull(componentInfo);
        assertTrue(componentInfo.getId().equals(scheduleRequestComponentInfoId));

    }

    @Test
    public void testgetScheduleRequestsByIds () throws Exception {
        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "d2e9946b-2f5b-430b-943b-99c52de7a49c";
        String scheduleRequestInfoRefObjectId = "08903125-8b3c-40f5-886b-79d6aa5b4bc0";
        String scheduleRequestComponentInfoId = "6d76d8fa-8ac0-4bc6-9d44-6dacef95b21b";
        String scheduleRequestInfoName = "testGetScheduleRequestsByIds";
        ScheduleRequestInfo scheduleRequestInfo = setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                SchedulingServiceConstants.SCHEDULE_REQUEST_NORMAL_REQUEST_TYPE,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // create the second ScheduleRequestInfo
        String scheduleRequestInfoId2 = "59617e1d-e716-4abd-9dda-386c595c56ad";
        String scheduleRequestInfoRefObjectId2 = "c80780dc-0c3c-4707-8a14-f5b68d3760c1";
        String scheduleRequestComponentInfoId2 = "21f8675e-0159-4875-9e24-3e3013bae7dd";
        String scheduleRequestInfoName2 = "testGetScheduleRequestsByIds2";
        ScheduleRequestInfo scheduleRequestInfo2 = setupScheduleRequestInfo(scheduleRequestInfoId2,
                scheduleRequestInfoRefObjectId2, scheduleRequestComponentInfoId2, scheduleRequestInfoName2);

        returnInfo  = schedulingService.createScheduleRequest(
                SchedulingServiceConstants.SCHEDULE_REQUEST_NORMAL_REQUEST_TYPE,
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
        assertTrue(requestInfo1.getRefObjectTypeKey().equals(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING));

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
        assertTrue(requestInfo2.getRefObjectTypeKey().equals(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING));

        List<ScheduleRequestComponentInfo> componentInfoList2 = requestInfo2.getScheduleRequestComponents();
        assertNotNull(componentInfoList2);
        assertFalse(componentInfoList2.isEmpty());
        ScheduleRequestComponentInfo componentInfo2 = componentInfoList.get(0);
        assertNotNull(componentInfo2);
        assertTrue(componentInfo2.getId().equals(scheduleRequestComponentInfoId2));
    }

    @Test
    public void testgetScheduleRequestIdsByType() throws Exception {
        String requestType =  SchedulingServiceConstants.SCHEDULE_REQUEST_NORMAL_REQUEST_TYPE;

        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "d2e9946b-2f5b-430b-943b-99c52de7a49c";
        String scheduleRequestInfoRefObjectId = "08903125-8b3c-40f5-886b-79d6aa5b4bc0";
        String scheduleRequestComponentInfoId = "6d76d8fa-8ac0-4bc6-9d44-6dacef95b21b";
        String scheduleRequestInfoName = "testGetScheduleRequestsByIds";
        ScheduleRequestInfo scheduleRequestInfo = setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);


        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest( requestType,
                                            scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // create the second ScheduleRequestInfo
        String scheduleRequestInfoId2 = "59617e1d-e716-4abd-9dda-386c595c56ad";
        String scheduleRequestInfoRefObjectId2 = "c80780dc-0c3c-4707-8a14-f5b68d3760c1";
        String scheduleRequestComponentInfoId2 = "21f8675e-0159-4875-9e24-3e3013bae7dd";
        String scheduleRequestInfoName2 = "testGetScheduleRequestsByIds2";
        ScheduleRequestInfo scheduleRequestInfo2 = setupScheduleRequestInfo(scheduleRequestInfoId2,
                scheduleRequestInfoRefObjectId2, scheduleRequestComponentInfoId2, scheduleRequestInfoName2);

        returnInfo  = schedulingService.createScheduleRequest(requestType,
                                scheduleRequestInfo2,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        List<String> requestIds = schedulingService.getScheduleRequestIdsByType(
                SchedulingServiceConstants.SCHEDULE_REQUEST_NORMAL_REQUEST_TYPE, contextInfo);

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
        String requestType =  SchedulingServiceConstants.SCHEDULE_REQUEST_NORMAL_REQUEST_TYPE;

        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "7aeda5df-a4af-4bfc-b8f9-d0d9db995e6f";
        String scheduleRequestInfoRefObjectId = "ce15c886-d3f6-4225-b6eb-6b5e0bff18cc";
        String scheduleRequestComponentInfoId = "5a13e938-8334-4fa5-8c57-7638ef69d456";
        String scheduleRequestInfoName = "testGetScheduleRequestByRefObject";
        ScheduleRequestInfo scheduleRequestInfo = setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(requestType,
                                                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // create the second ScheduleRequestInfo
        String scheduleRequestInfoId2 = "9c6f365c-c747-448c-afb6-1267a3e417fd";
        String scheduleRequestComponentInfoId2 = "1dbd9fcf-0251-42cc-8666-5254c4416e94";
        String scheduleRequestInfoName2 = "testGetScheduleRequestsByIds2";
        ScheduleRequestInfo scheduleRequestInfo2 = setupScheduleRequestInfo(scheduleRequestInfoId2,
                scheduleRequestInfoRefObjectId, scheduleRequestComponentInfoId2, scheduleRequestInfoName2);

        returnInfo  = schedulingService.createScheduleRequest(requestType,
                                scheduleRequestInfo2,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        List<String> scheduleRequestIds = schedulingService.getScheduleRequestsByRefObject(
                                            requestType, scheduleRequestInfoRefObjectId, contextInfo);

        assertNotNull(scheduleRequestIds);
        assertTrue(!scheduleRequestIds.isEmpty());
        assertEquals(scheduleRequestIds.size(), 2);
        String requestId1 = scheduleRequestIds.get(0);
        String requestId2 = scheduleRequestIds.get(1);
        assertNotNull(requestId1);
        assertNotNull(requestId2);
        assertTrue(requestId1.equals(scheduleRequestInfoId));
        assertTrue(requestId2.equals(scheduleRequestInfoId2));

    }


    private ScheduleRequestInfo setupScheduleRequestInfo(String scheduleRequestInfoId, String scheduleRequestInfoRefObjectId,
                                                         String ScheduleRequestComponentInfoId, String scheduleRequestInfoName) {
        ScheduleRequestInfo scheduleRequestInfo = new ScheduleRequestInfo();
        scheduleRequestInfo.setId(scheduleRequestInfoId);
        scheduleRequestInfo.setRefObjectId(scheduleRequestInfoRefObjectId);
        scheduleRequestInfo.setRefObjectTypeKey(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);
        scheduleRequestInfo.setName(scheduleRequestInfoName);

        List<ScheduleRequestComponentInfo> componentInfoList = new ArrayList<ScheduleRequestComponentInfo>();
        ScheduleRequestComponentInfo componentInfo = new ScheduleRequestComponentInfo();
        componentInfo.setId(ScheduleRequestComponentInfoId);
        List<String> buildingIds = new ArrayList<String>();
        buildingIds.add("UW1");
        buildingIds.add("UW2");
        componentInfo.setBuildingIds(buildingIds);
        List<String> campusIds = new ArrayList<String>();
        campusIds.add("UW-Seattle");
        componentInfo.setCampusIds(campusIds);
        List<String> orgIds = new ArrayList<String>();
        orgIds.add("UW-IT");
        orgIds.add("UW-Comp");
        componentInfo.setOrgIds(orgIds);
        List<String> roomIds = new ArrayList<String>();
        roomIds.add("Chem-101");
        roomIds.add("Law-201");
        componentInfo.setRoomIds(roomIds);
        List<String> timeSlotIds = new ArrayList<String>();
        timeSlotIds.add("1");
        timeSlotIds.add("2");
        componentInfo.setTimeSlotIds(timeSlotIds);
        List<String> resourceTypeKeys = new ArrayList<String>();
        resourceTypeKeys.add(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);
        componentInfo.setResourceTypeKeys(resourceTypeKeys);

        componentInfoList.add(componentInfo);

        scheduleRequestInfo.setScheduleRequestComponents(componentInfoList);

        return scheduleRequestInfo;
    }
}
