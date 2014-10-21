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

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.RoomServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.SchedulingServiceDataLoader;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleDisplayInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestDisplayInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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

    @Resource(name = "SchedulingService")
    private SchedulingService schedulingService;

    @Resource(name = "mockRoomService")
    private RoomService roomService;

    @Resource(name = "typeServiceImpl" )
    private TypeService typeService;

    @Resource(name = "criteriaLookupService" )
    private CriteriaLookupService criteriaLookupService;

    @Resource(name = "atpEnrService" )
    private AtpService atpService;

    public static String principalId = "123";
    public ContextInfo contextInfo = null;

    private String scheduleRequestInfoId, scheduleRequestComponentInfoId, scheduleRequestInfoName, scheduleRequestSetInfoId;
    private String scheduleRequestInfoId2, scheduleRequestComponentInfoId2, scheduleRequestInfoName2,requestType;

    public static final TimeOfDayInfo TOD_8_AM = new TimeOfDayInfo(8, 0);
    public static final TimeOfDayInfo TOD_8_50_AM = new TimeOfDayInfo(8, 50);
    public static final TimeOfDayInfo TOD_9_10_AM = new TimeOfDayInfo(9, 10);
    public static final TimeOfDayInfo TOD_1_PM = new TimeOfDayInfo(13, 0);
    public static final TimeOfDayInfo TOD_2_10_PM = new TimeOfDayInfo(14, 10);
    public static final TimeOfDayInfo TOD_3_PM = new TimeOfDayInfo(15, 0);
    public static final TimeOfDayInfo TOD_3_50_PM = new TimeOfDayInfo(15, 50);

    @Before
    public void setUp() throws Exception {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        initScheduleRequestByRefObj();
        loadData();
    }

    public void initScheduleRequestByRefObj() {

        requestType =  SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST;

        scheduleRequestInfoId = "ScheduleRequestsByRefObject-Id1";
        scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId1";
        scheduleRequestInfoName = "testGetScheduleRequestByRefObject";

        scheduleRequestSetInfoId = "ScheduleRequestsByRefObject-srs-Id1";

        // create a ScheduleRequestInfo 2
        scheduleRequestInfoId2 = "ScheduleRequestsByRefObject-Id2";
        scheduleRequestComponentInfoId2 = "scheduleRequest-ComponentInfoId2";
        scheduleRequestInfoName2 = "testGetScheduleRequestByRefObject2";

    }

    private void loadData() throws Exception {
        createTypeData();
        SchedulingServiceDataLoader loader = new SchedulingServiceDataLoader(this.schedulingService);
        loader.setAtpService(atpService);
        loader.setRoomService(roomService);
        loader.loadData();
    }

    private void createTypeData() throws Exception {

        TypeInfo info =  createTypeInfo(requestType, "testType", "This is a test", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_REQUEST);

        typeService.createType(info.getKey(), info, contextInfo);

        info =  createTypeInfo(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL,
                "Fall Full", "Fall Full Time Slot for testing", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_TIME_SLOT);
        typeService.createType(info.getKey(), info, contextInfo);

        info =  createTypeInfo(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_SPRING,
                "Spring Full", "Spring Full Time Slot for testing", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_TIME_SLOT);
        typeService.createType(info.getKey(), info, contextInfo);

        info =  createTypeInfo(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_ADHOC,
                "Ad Hoc",  "Ad Hoc Time Slot for testing", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_TIME_SLOT);
        typeService.createType(info.getKey(), info, contextInfo);

        info =  createTypeInfo(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_TBA,
                "TBA", "TBA Time Slot for testing", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE_TIME_SLOT);
        typeService.createType(info.getKey(), info, contextInfo);

        info =  createTypeInfo(SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE, "testType", "This is a test", SchedulingServiceConstants.REF_OBJECT_URI_SCHEDULE);
        typeService.createType(info.getKey(), info, contextInfo);

        // Create type type relationships.
        String tsGroupingType = SchedulingServiceConstants.TIME_SLOT_TYPE_GROUPING;
        String ttRelationGroupType = TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY;
        String fallTsType = SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL;
        String springTsType = SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_SPRING;
        String adHocTsType = SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_ADHOC;
        String tbaTsType = SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_TBA;

        TypeTypeRelationInfo typeTypeRelationInfo = createTypeTypeRelationInfo(tsGroupingType, fallTsType, ttRelationGroupType);
        typeService.createTypeTypeRelation(ttRelationGroupType, tsGroupingType, fallTsType, typeTypeRelationInfo, contextInfo);
        typeTypeRelationInfo = createTypeTypeRelationInfo(tsGroupingType, springTsType , ttRelationGroupType);
        typeService.createTypeTypeRelation(ttRelationGroupType, tsGroupingType, springTsType, typeTypeRelationInfo, contextInfo);
        typeTypeRelationInfo = createTypeTypeRelationInfo(tsGroupingType, adHocTsType, ttRelationGroupType);
        typeService.createTypeTypeRelation(ttRelationGroupType, tsGroupingType, adHocTsType, typeTypeRelationInfo, contextInfo);
        typeTypeRelationInfo = createTypeTypeRelationInfo(tsGroupingType, tbaTsType , ttRelationGroupType);
        typeService.createTypeTypeRelation(ttRelationGroupType, tsGroupingType, tbaTsType, typeTypeRelationInfo, contextInfo);
    }

    private TypeTypeRelationInfo createTypeTypeRelationInfo(String ownerTypeKey, String relatedTypeKey, String typeTypeTypeKey) {
        TypeTypeRelationInfo typeTypeRelationInfo = new TypeTypeRelationInfo();
        typeTypeRelationInfo.setStateKey(TypeServiceConstants.TYPE_TYPE_RELATION_ACTIVE_STATE_KEY);
        typeTypeRelationInfo.setTypeKey(typeTypeTypeKey);
        typeTypeRelationInfo.setOwnerTypeKey(ownerTypeKey);
        typeTypeRelationInfo.setRelatedTypeKey(relatedTypeKey);
        typeTypeRelationInfo.setRank(1);
        return typeTypeRelationInfo;
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
    public void testSetMillis() {
        // Checks that setMillis
        TimeOfDayInfo setMillis_8_30_2AM = TimeOfDayHelper.setMillis(8L * 60 * 60 * 1000L +
                30L * 60 * 1000L + 2000L);  // 8:30 and 2 seconds
        TimeOfDayInfo TOD_8_30_2_AM = new TimeOfDayInfo(8, 30, 2);
        assertEquals(TOD_8_30_2_AM, setMillis_8_30_2AM);
    }

    @Test
    public void testgetTimeSlot() throws Exception {
        // test get by id
        for (int i = 1; i<= 16; i++) {
            int tsId = 100 + i;
            TimeSlot ts = schedulingService.getTimeSlot("ts" + tsId, contextInfo);
            assertNotNull(ts);
            assertEquals("ts" + tsId, ts.getId());
        }

        // test specific records - 2
        TimeSlot ts = schedulingService.getTimeSlot("ts102", contextInfo);
        List<Integer> dow = ts.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(dow.contains(Calendar.MONDAY));
        assertTrue(dow.contains(Calendar.WEDNESDAY));
        assertTrue(dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(dow.contains(Calendar.TUESDAY));
        assertFalse(dow.contains(Calendar.THURSDAY));

        assertEquals(ts.getStartTime(), TOD_8_AM);
        assertEquals(ts.getEndTime(), TOD_9_10_AM);

        // test specific records - 3
        ts = schedulingService.getTimeSlot("ts103", contextInfo);
        dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(dow.contains(Calendar.MONDAY));
        assertFalse(dow.contains(Calendar.WEDNESDAY));
        assertFalse(dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(dow.contains(Calendar.TUESDAY));
        assertTrue(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime(), TOD_8_AM);
        assertEquals(ts.getEndTime(), TOD_8_50_AM);

        // test specific records - 10
        ts = schedulingService.getTimeSlot("ts110", contextInfo);
        dow = ts.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(dow.contains(Calendar.MONDAY));
        assertTrue(dow.contains(Calendar.WEDNESDAY));
        assertTrue(dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(dow.contains(Calendar.TUESDAY));
        assertFalse(dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime(), TOD_1_PM);
        assertEquals(ts.getEndTime(), TOD_2_10_PM);
    }

    @Test
    public void testGetScheduleRequestsByRefObjects() throws Exception {

        String refObjectType= "ScheduleRequestsByRefObject-AO-type";
        List<String> refObjectIds = new ArrayList<String>();
        refObjectIds.add("ScheduleRequestsByRefObject-ao-Id1");
        refObjectIds.add("ScheduleRequestsByRefObject-ao-Id2");
        refObjectIds.add("Ao1");
        refObjectIds.add("Ao2");

        ScheduleRequestSetInfo scheduleRequestSetInfo = SchedulingServiceDataLoader.setupScheduleRequestSetInfo(scheduleRequestSetInfoId,
                refObjectIds,
                refObjectType,
                false,
                8);

        schedulingService.createScheduleRequestSet(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET,
                refObjectType, scheduleRequestSetInfo, contextInfo);
        // create a ScheduleRequestInfo 1
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestComponentInfoId, null, scheduleRequestSetInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(requestType,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // create a ScheduleRequestInfo 2
        ScheduleRequestInfo scheduleRequestInfo2 = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId2,
                scheduleRequestComponentInfoId2, null, scheduleRequestSetInfoId, scheduleRequestInfoName2);

        ScheduleRequestInfo returnInfo2  = schedulingService.createScheduleRequest(requestType,
                scheduleRequestInfo2,  contextInfo);

        // creation success
        assertNotNull(returnInfo2);

        List<ScheduleRequestInfo> scheduleRequestInfos = schedulingService.getScheduleRequestsByRefObjects(refObjectType,refObjectIds,contextInfo);

        assertNotNull(scheduleRequestInfos);
        assertTrue(!scheduleRequestInfos.isEmpty());
    }

    @Test
    public void testCanUpdateTimeSlotRDL() throws Exception {

        //// RDL Test
        scheduleRequestInfoId = "testCanUpdateTimeSlot-Id1";
        String scheduleRequestSetInfoId = "testCanUpdateTimeSlot-scheduleRequestInfoId";
        String scheduleRequestComponentInfoId = "testCanUpdateTimeSlot-ComponentInfoId";
        String scheduleRequestInfoName = "testCanUpdateTimeSlot";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestComponentInfoId, null, scheduleRequestSetInfoId, scheduleRequestInfoName);
        String timeSlotId = scheduleRequestInfo.getScheduleRequestComponents().get(0).getTimeSlotIds().get(0);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                requestType,
                scheduleRequestInfo, contextInfo);
        assertNotNull(returnInfo);

        assertFalse(schedulingService.canUpdateTimeSlot(timeSlotId, contextInfo));
        assertTrue(schedulingService.canUpdateTimeSlot("nonexistentRdltimeslotid", contextInfo));
    }

    @Test
    public void testCanUpdateTimeSlotADL() throws Exception {
        //// ADL Test
        String scheduleId = "testCanUpdateTimeSlot-Id1";
        String atpId = SchedulingServiceDataLoader.ATP_ID;
        String roomId = "testCanUpdateTimeSlot-room1";

        ScheduleInfo scheduleInfo = SchedulingServiceDataLoader.setupScheduleInfo(scheduleId,atpId, false, roomId);

        ScheduleInfo returnedInfo = schedulingService.createSchedule(scheduleInfo.getTypeKey(), scheduleInfo, contextInfo);
        assertNotNull(returnedInfo);

        String timeSlotId = scheduleInfo.getScheduleComponents().get(0).getTimeSlotIds().get(0);
        assertFalse(schedulingService.canUpdateTimeSlot(timeSlotId, contextInfo));
        assertTrue(schedulingService.canUpdateTimeSlot("nonexistentAdltimeslotid", contextInfo));

    }

    @Test
    public void testgetTimeSlotIdsByType() throws Exception {
        List<String> l_actoff = schedulingService.getTimeSlotIdsByType(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL, contextInfo);
        assertEquals(22, l_actoff.size());
        assertTrue(l_actoff.contains("ts101"));
        assertTrue(l_actoff.contains("ts116"));

        List<String> l_final = schedulingService.getTimeSlotIdsByType(SchedulingServiceConstants.TIME_SLOT_TYPE_EXAM, contextInfo);
        assertTrue(l_final.isEmpty());
    }

    @Test
    public void testgetTimeSlotsByIds() throws Exception {
        // test case: all valid ids
        List<String> valid_ids = new ArrayList<String>();
        valid_ids.add("ts102");
        valid_ids.add("ts115");
        List<TimeSlotInfo> l_valid_ts = schedulingService.getTimeSlotsByIds(valid_ids, contextInfo);
        assertEquals(2, valid_ids.size());

        TimeSlot ts2 = null, ts15 = null;
        // ensure the list has only time slots with ids 2 and 15
        for(TimeSlotInfo ts : l_valid_ts) {
            assertTrue(valid_ids.contains(ts.getId()));
            if(ts.getId().equals("ts102")) {
                ts2 = ts;
            }
            else {
                ts15 = ts;
            }
        }

        assertNotNull(ts2);
        assertEquals("ts102", ts2.getId());
        List<Integer> dow = ts2.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(dow.contains(Calendar.MONDAY));
        assertTrue(dow.contains(Calendar.WEDNESDAY));
        assertTrue(dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(dow.contains(Calendar.TUESDAY));
        assertFalse(dow.contains(Calendar.THURSDAY));
        assertEquals(ts2.getStartTime(), TOD_8_AM);
        assertEquals(ts2.getEndTime(), TOD_9_10_AM);

        assertNotNull(ts15);
        assertEquals("ts115", ts15.getId());
        dow = ts15.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(dow.contains(Calendar.MONDAY));
        assertFalse(dow.contains(Calendar.WEDNESDAY));
        assertFalse(dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(dow.contains(Calendar.TUESDAY));
        assertTrue(dow.contains(Calendar.THURSDAY));
        assertEquals(ts15.getStartTime(), TOD_3_PM);
        assertEquals(ts15.getEndTime(), TOD_3_50_PM);

        // test case: all invalid ids
        List<String> invalid_ids = new ArrayList<String>();
        invalid_ids.add("bad1");
        invalid_ids.add("bad2");
        try {
            schedulingService.getTimeSlotsByIds(invalid_ids, contextInfo);
            fail("Should not be here - test invalid_ids");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("No data was found for : bad1, bad2", e.getMessage());
        }

        // test case: mixture of valid and invalid
        List<String> mix_ids = new ArrayList<String>();
        mix_ids.add("ts110");
        mix_ids.add("bad1");
        try {
            schedulingService.getTimeSlotsByIds(mix_ids, contextInfo);
            fail("Should not be here - test mix_ids");
        } catch (DoesNotExistException e) {
            assertNotNull(e.getMessage());
            assertEquals("Missing data for : bad1", e.getMessage());
        }
    }

    @Test
    public void getValidDaysOfWeekByTimeSlotType() throws Exception {
        List<Integer> valid_days_act_off = schedulingService.getValidDaysOfWeekByTimeSlotType(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL, contextInfo);
        // should return days Monday through Saturday
        assertTrue(valid_days_act_off.contains(Calendar.MONDAY));
        assertTrue(valid_days_act_off.contains(Calendar.TUESDAY));
        assertTrue(valid_days_act_off.contains(Calendar.WEDNESDAY));
        assertTrue(valid_days_act_off.contains(Calendar.THURSDAY));
        assertTrue(valid_days_act_off.contains(Calendar.FRIDAY));
        assertTrue(valid_days_act_off.contains(Calendar.SATURDAY));
        assertTrue(valid_days_act_off.contains(Calendar.SUNDAY));

        List<Integer> valid_days_final = schedulingService.getValidDaysOfWeekByTimeSlotType(SchedulingServiceConstants.TIME_SLOT_TYPE_EXAM, contextInfo);
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
    public void testgetTimeSlotsByDaysAndStartTime() throws Exception {
        // should return records ts103, ts5, ts104
        List<Integer> dow = new ArrayList<Integer>();
        dow.add(Calendar.TUESDAY);
        dow.add(Calendar.THURSDAY);
        TimeOfDayInfo startTime;
        startTime = SchedulingServiceDataLoader.TOD_8_00_AM;
        List<TimeSlotInfo> tsi = schedulingService
                .getTimeSlotsByDaysAndStartTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL, dow, startTime, contextInfo);
        assertEquals(3, tsi.size());

        assertEquals("ts103", tsi.get(0).getId());
        TimeSlot ts = tsi.get(0);
        List<Integer> ts_dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(ts_dow.contains(Calendar.MONDAY));
        assertFalse(ts_dow.contains(Calendar.WEDNESDAY));
        assertFalse(ts_dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(ts_dow.contains(Calendar.TUESDAY));
        assertTrue(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime(), TOD_8_AM);

        assertEquals("ts5", tsi.get(1).getId());
        ts = tsi.get(1);
        ts_dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(ts_dow.contains(Calendar.MONDAY));
        assertFalse(ts_dow.contains(Calendar.WEDNESDAY));
        assertFalse(ts_dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(ts_dow.contains(Calendar.TUESDAY));
        assertTrue(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime(), TOD_8_AM);

        assertEquals("ts104", tsi.get(2).getId());
        ts = tsi.get(2);
        ts_dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(ts_dow.contains(Calendar.MONDAY));
        assertFalse(ts_dow.contains(Calendar.WEDNESDAY));
        assertFalse(ts_dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(ts_dow.contains(Calendar.TUESDAY));
        assertTrue(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime(), TOD_8_AM);
    }

    @Test
    public void testgetTimeSlotsByDaysAndStartTimeAndEndTime() throws Exception {
        // should return record ts103
        List<Integer> dow = new ArrayList<Integer>();
        dow.add(Calendar.TUESDAY);
        dow.add(Calendar.THURSDAY);
        TimeOfDayInfo startTime = SchedulingServiceDataLoader.TOD_8_00_AM;
        TimeOfDayInfo endTime  = SchedulingServiceDataLoader.TOD_8_50_AM;
        List<TimeSlotInfo> tsi = schedulingService
                .getTimeSlotsByDaysAndStartTimeAndEndTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL, dow, startTime, endTime, contextInfo);
        assertEquals(1, tsi.size());
        assertEquals("ts103", tsi.get(0).getId());
        TimeSlot ts = tsi.get(0);
        List<Integer> ts_dow = ts.getWeekdays();
        // should not contain Monday, Wednesday, Friday
        assertFalse(ts_dow.contains(Calendar.MONDAY));
        assertFalse(ts_dow.contains(Calendar.WEDNESDAY));
        assertFalse(ts_dow.contains(Calendar.FRIDAY));
        // should contain Tuesday or Thursday
        assertTrue(ts_dow.contains(Calendar.TUESDAY));
        assertTrue(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime(), TOD_8_AM);
        assertEquals(ts.getEndTime(), TOD_8_50_AM);

        // should return record 10
        dow = new ArrayList<Integer>();
        dow.add(Calendar.MONDAY);
        dow.add(Calendar.WEDNESDAY);
        dow.add(Calendar.FRIDAY);
        startTime = SchedulingServiceDataLoader.TOD_1_00_PM;
        endTime = SchedulingServiceDataLoader.TOD_2_10_PM;
        tsi = schedulingService.getTimeSlotsByDaysAndStartTimeAndEndTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL, dow, startTime, endTime, contextInfo);
        assertEquals(1, tsi.size());
        assertEquals("ts110", tsi.get(0).getId());
        ts = tsi.get(0);
        ts_dow = ts.getWeekdays();
        // should contain Monday, Wednesday, Friday
        assertTrue(ts_dow.contains(Calendar.MONDAY));
        assertTrue(ts_dow.contains(Calendar.WEDNESDAY));
        assertTrue(ts_dow.contains(Calendar.FRIDAY));
        // should not contain Tuesday or Thursday
        assertFalse(ts_dow.contains(Calendar.TUESDAY));
        assertFalse(ts_dow.contains(Calendar.THURSDAY));
        assertEquals(ts.getStartTime(), TOD_1_PM);
        assertEquals(ts.getEndTime(), TOD_2_10_PM);
    }

    @Test
    public void testCreateScheduleRequestSet() throws Exception {
        String scheduleRequestSetId = "srsId";
        List<String> refObjectIds = new ArrayList<String>();
        String refObjectType= "ScheduleRequestsByRefObject-AO-type";
        ScheduleRequestSetInfo srsInfo = SchedulingServiceDataLoader.setupScheduleRequestSetInfo(scheduleRequestSetId, refObjectIds, refObjectType, false, null);

        ScheduleRequestSetInfo returnSrsInfo = schedulingService
                .createScheduleRequestSet(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET,
                        refObjectType, srsInfo, contextInfo);

        assertNotNull(returnSrsInfo);
    }

    @Test
    public void testCreateScheduleRequest () throws Exception {
        String scheduleId = "schId";
        scheduleRequestInfoId = "createScheduleRequest-infoId";
        String scheduleRequestSetInfoId = "scheduleRequest-scheduleRequestInfoId";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId";
        String scheduleRequestInfoName = "testCreateScheduleRequest";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestComponentInfoId, scheduleId, scheduleRequestSetInfoId, scheduleRequestInfoName);

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
                requestType,
                scheduleRequestInfo,  contextInfo);

        // returnInfo should not be null
        assertNotNull(returnInfo);
        assertTrue(returnInfo.getScheduleId().equals(scheduleId));
        assertTrue(returnInfo.getScheduleRequestSetId().equals(scheduleRequestSetInfoId));
        assertTrue(returnInfo.getId().equals(scheduleRequestInfoId));
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
    public void testUpdateScheduleRequest () throws Exception {

        // create a ScheduleRequestInfo
        scheduleRequestInfoId = "updateScheduleRequest-infoId";
        String scheduleRequestSetInfoId = "scheduleRequest-scheduleRequestInfoId";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId";
        String scheduleRequestInfoName = "testCreateScheduleRequest";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestComponentInfoId, null, scheduleRequestSetInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                requestType,
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
        assertTrue(updatedReturnInfo.getId().equals(scheduleRequestInfoId));
        assertTrue(updatedReturnInfo.getScheduleRequestSetId().equals(scheduleRequestSetInfoId));
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
        scheduleRequestInfoId = "testdeleteScheduleRequest-Id";
        String scheduleRequestSetInfoId = "scheduleRequest-scheduleRequestInfoId";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId";
        String scheduleRequestInfoName = "testDeleteScheduleRequest";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestComponentInfoId, null, scheduleRequestSetInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                requestType,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        StatusInfo deleteStatus = schedulingService.deleteScheduleRequest( scheduleRequestInfoId,  contextInfo);

        assertTrue(deleteStatus.getIsSuccess());

    }

    @Test
    public void testgetScheduleRequest () throws Exception {
        // create a ScheduleRequestInfo
        scheduleRequestInfoId = "testGetScheduleRequest-Id";
        String scheduleRequestSetInfoId = "scheduleRequest-scheduleRequestInfoId";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId";
        String scheduleRequestInfoName = "testGetScheduleRequest";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestComponentInfoId, null, scheduleRequestSetInfoId, scheduleRequestInfoName);

        // explicitly set the isTBA field on the components
        for (ScheduleRequestComponentInfo comp : scheduleRequestInfo.getScheduleRequestComponents()) {
            comp.setIsTBA(false);
        }

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                requestType,
                scheduleRequestInfo, contextInfo);

        // creation success
        assertNotNull(returnInfo);

        ScheduleRequestInfo requestInfo = schedulingService.getScheduleRequest(scheduleRequestInfoId, contextInfo);

        // requestInfo should not be null
        assertNotNull(requestInfo);
        assertTrue(requestInfo.getId().equals(scheduleRequestInfoId));
        assertTrue(requestInfo.getScheduleRequestSetId().equals(scheduleRequestSetInfoId));
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
        scheduleRequestInfoId = "testGetScheduleRequestsByIds-Id1";
        String scheduleRequestSetInfoId = "scheduleRequest-scheduleRequestInfoId1";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId1";
        String scheduleRequestInfoName = "testGetScheduleRequestsByIds";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestComponentInfoId, null, scheduleRequestSetInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(
                requestType,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // create the second ScheduleRequestInfo
        String scheduleRequestInfoId2 = "testGetScheduleRequestsByIds-Id2";
        String scheduleRequestSetInfoId2 = "scheduleRequest-scheduleRequestInfoId2";
        String scheduleRequestComponentInfoId2 = "scheduleRequest-ComponentInfoId2";
        String scheduleRequestInfoName2 = "testGetScheduleRequestsByIds2";
        ScheduleRequestInfo scheduleRequestInfo2 = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId2,
                scheduleRequestComponentInfoId2, null, scheduleRequestSetInfoId2, scheduleRequestInfoName2);

        returnInfo  = schedulingService.createScheduleRequest(
                requestType,
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

        assertTrue(requestInfo1.getId().equals(scheduleRequestInfoId));
        assertTrue(requestInfo1.getName().equals(scheduleRequestInfoName));
        assertTrue(requestInfo1.getScheduleRequestSetId().equals(scheduleRequestSetInfoId));
        List<ScheduleRequestComponentInfo> componentInfoList = requestInfo1.getScheduleRequestComponents();
        assertNotNull(componentInfoList);
        assertFalse(componentInfoList.isEmpty());
        ScheduleRequestComponentInfo componentInfo = componentInfoList.get(0);
        assertNotNull(componentInfo);
        assertTrue(componentInfo.getId().equals(scheduleRequestComponentInfoId));

        // verify second request
        assertTrue(requestInfo2.getId().equals(scheduleRequestInfoId2));
        assertTrue(requestInfo2.getName().equals(scheduleRequestInfoName2));
        assertTrue(requestInfo2.getScheduleRequestSetId().equals(scheduleRequestSetInfoId2));

        List<ScheduleRequestComponentInfo> componentInfoList2 = requestInfo2.getScheduleRequestComponents();
        assertNotNull(componentInfoList2);
        assertFalse(componentInfoList2.isEmpty());
        ScheduleRequestComponentInfo componentInfo2 = componentInfoList2.get(0);
        assertNotNull(componentInfo2);
        assertEquals(componentInfo2.getId(), scheduleRequestComponentInfoId2);
    }

    @Test
    public void testgetScheduleRequestIdsByType() throws Exception {

        // create a ScheduleRequestInfo
        scheduleRequestInfoId = "getScheduleRequestIdsByType-Id1";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId";
        String scheduleRequestInfoName = "testGetScheduleRequestsByType";
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestComponentInfoId, null, null, scheduleRequestInfoName);


        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest( requestType,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // create the second ScheduleRequestInfo
        String scheduleRequestInfoId2 = "getScheduleRequestIdsByType-Id2";
        String scheduleRequestComponentInfoId2 = "scheduleRequest-ComponentInfoId2";
        String scheduleRequestInfoName2 = "testGetScheduleRequestsByType2";
        ScheduleRequestInfo scheduleRequestInfo2 = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId2,
                scheduleRequestComponentInfoId2, null, null, scheduleRequestInfoName2);

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

        // create a ScheduleRequestSetInfo

        String scheduleRequestSetInfoId = "ScheduleRequestsByRefObject-srs-Id1";
        List<String> refObjectIds = new ArrayList<String>();
        refObjectIds.add("ScheduleRequestsByRefObject-ao-Id1");
        refObjectIds.add("ScheduleRequestsByRefObject-ao-Id2");
        String refObjectType= "ScheduleRequestsByRefObject-AO-type";
        Boolean maxEnrFlag = false;
        Integer maxEnr = 10;

        ScheduleRequestSetInfo scheduleRequestSetInfo = SchedulingServiceDataLoader.setupScheduleRequestSetInfo(scheduleRequestSetInfoId,
                refObjectIds,
                refObjectType,
                maxEnrFlag,
                maxEnr);

        schedulingService.createScheduleRequestSet(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET,
                refObjectType, scheduleRequestSetInfo, contextInfo);
        // create a ScheduleRequestInfo 1
        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestComponentInfoId, null, scheduleRequestSetInfoId, scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(requestType,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // create a ScheduleRequestInfo 2
        ScheduleRequestInfo scheduleRequestInfo2 = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId2,
                scheduleRequestComponentInfoId2, null, scheduleRequestSetInfoId, scheduleRequestInfoName2);

        ScheduleRequestInfo returnInfo2  = schedulingService.createScheduleRequest(requestType,
                scheduleRequestInfo2,  contextInfo);

        // creation success
        assertNotNull(returnInfo2);

        List<ScheduleRequestInfo> scheduleRequests = schedulingService.getScheduleRequestsByRefObject(refObjectType, refObjectIds.get(0), contextInfo);

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
    public void testSearchForTimeSlotIds() throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        String fallFull = SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL;
        String springFull = SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_SPRING;


        //  Search for TimeSlots of type Fall Full.
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.in("timeSlotType", fallFull)
        );

        QueryByCriteria criteria = qbcBuilder.build();
        //  Put the result collection into another collection so that it can be sorted.
        List<String> timeSlotIds = new ArrayList<String>(schedulingService.searchForTimeSlotIds(criteria, contextInfo));
        assertEquals(22, timeSlotIds.size());
        Collections.sort(timeSlotIds);
        assertEquals("toDelete", timeSlotIds.get(0));
        assertEquals("ts6", timeSlotIds.get(21));

        //  Search for TimeSlots of type Fall or Spring Full and days MWF
        String days = "MWF";
        String terms[] = {fallFull, springFull};
        qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.and(
                        PredicateFactory.in("timeSlotType", terms),
                        PredicateFactory.equal("weekdays", days)
                )
        );
        criteria = qbcBuilder.build();
        timeSlotIds = new ArrayList<String>(schedulingService.searchForTimeSlotIds(criteria, contextInfo));
        assertEquals(12, timeSlotIds.size());
        Collections.sort(timeSlotIds);
        assertEquals("toDelete", timeSlotIds.get(0));
        assertEquals("ts8", timeSlotIds.get(11));

    }

    @Test
    public void testSearchForTimeSlots() throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        String fallFull = SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL;
        String springFull = SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_SPRING;


        //  Search for TimeSlots of type Fall Full.
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.in("timeSlotType", fallFull)
        );

        QueryByCriteria criteria = qbcBuilder.build();
        List<TimeSlotInfo> timeSlotInfos = schedulingService.searchForTimeSlots(criteria, contextInfo);
        assertEquals(22, timeSlotInfos.size());
        for (TimeSlotInfo ts : timeSlotInfos) {
            assertEquals(fallFull, ts.getTypeKey());
        }

        //  Search for TimeSlots of type Fall or Spring Full and days MWF
        String days = "MWF";
        String terms[] = {fallFull, springFull};
        qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.and(
                        PredicateFactory.in("timeSlotType", terms),
                        PredicateFactory.equal("weekdays", days)
                )
        );
        criteria = qbcBuilder.build();
        timeSlotInfos = schedulingService.searchForTimeSlots(criteria, contextInfo);
        assertEquals(12, timeSlotInfos.size());
        for (TimeSlotInfo ts : timeSlotInfos) {
            assertTrue(StringUtils.equals(fallFull, ts.getTypeKey())
                    || StringUtils.equals(springFull, ts.getTypeKey()));
            assertEquals(days, SchedulingServiceUtil.weekdaysList2WeekdaysString(ts.getWeekdays()));
        }
    }

    /**
     * This one can be combined with testSearchForTimeSlots() once the issue has been addressed.
     * TODO: KSENROLL-10246
     */
    @Ignore
    @Test
    public void testSearchForTimeSlotsParamValueDoesNotMatchForLongBug() throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        String fallFull = SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL;
        String days = "MWF";

        //  Search for TimeSlots of type Fall Full and days MWF, and start time 8am
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.and(
                        PredicateFactory.equal("timeSlotType", fallFull),
                        PredicateFactory.equal("weekdays", days),
                        PredicateFactory.equal("startTimeMillis",
                                TimeOfDayHelper.getMillis(SchedulingServiceDataLoader.TOD_8_00_AM))
                )
        );
        QueryByCriteria criteria = qbcBuilder.build();
        List<TimeSlotInfo> timeSlotInfos = schedulingService.searchForTimeSlots(criteria, contextInfo);
        assertEquals(3, timeSlotInfos.size());
        for (TimeSlotInfo ts : timeSlotInfos) {
            assertEquals(fallFull, ts.getTypeKey());
            assertEquals(days, SchedulingServiceUtil.weekdaysList2WeekdaysString(ts.getWeekdays()));
            assertEquals(TOD_8_AM, ts.getStartTime());
        }
    }

    @Test
    public void testgetScheduleRequestDisplay () throws Exception {

        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "ScheduleRequestsByRefObject-Id1";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId1";
        String scheduleRequestInfoName = "testGetScheduleRequestByRefObject";

        String scheduleRequestSetId = "searchForScheduleRequestDisplaySetId";
        List<String> refObjectIds = new ArrayList<String>();
        refObjectIds.add("Ao1");
        refObjectIds.add("Ao2");
        ScheduleRequestSetInfo setInfo =  SchedulingServiceDataLoader.setupScheduleRequestSetInfo(scheduleRequestSetId, refObjectIds,
                "REF_OBJECT_URI_GLOBAL_PREFIX",
                false, 168);

        ScheduleRequestSetInfo returnSetInfo = schedulingService.createScheduleRequestSet(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET,
                "REF_OBJECT_URI_GLOBAL_PREFIX", setInfo, contextInfo );


        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestComponentInfoId, null, returnSetInfo.getId(), scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(requestType,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // get schedule request display info
        ScheduleRequestDisplayInfo displayInfo = getSchedulingService().getScheduleRequestDisplay(scheduleRequestInfoId, contextInfo);

        assertNotNull(displayInfo);
        assertEquals(displayInfo.getId(), scheduleRequestInfo.getId());
        assertEquals(displayInfo.getName(), scheduleRequestInfo.getName());
        assertTrue(!displayInfo.getScheduleRequestComponentDisplays().isEmpty());
        assertTrue(!displayInfo.getScheduleRequestComponentDisplays().get(0).getBuildings().isEmpty());
        assertTrue(!displayInfo.getScheduleRequestComponentDisplays().get(0).getRooms().isEmpty());
        assertTrue(!displayInfo.getScheduleRequestComponentDisplays().get(0).getOrgs().isEmpty());
    }

    @Test
    public void testgetScheduleRequestDisplaysByIds()  throws Exception {

        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "ScheduleRequestsByRefObject-Id1";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId1";
        String scheduleRequestInfoName = "testGetScheduleRequestByRefObject";

        String scheduleRequestSetId = "searchForScheduleRequestDisplaySetId";
        List<String> refObjectIds = new ArrayList<String>();
        refObjectIds.add("Ao1");
        refObjectIds.add("Ao2");
        ScheduleRequestSetInfo setInfo =  SchedulingServiceDataLoader.setupScheduleRequestSetInfo(scheduleRequestSetId, refObjectIds,
                "REF_OBJECT_URI_GLOBAL_PREFIX",
                false, 168);

        ScheduleRequestSetInfo returnSetInfo = schedulingService.createScheduleRequestSet(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET,
                "REF_OBJECT_URI_GLOBAL_PREFIX", setInfo, contextInfo );


        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestComponentInfoId, null, returnSetInfo.getId(), scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(requestType,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // get schedule request display info
        List<String> requestIdList = new ArrayList<String>();
        requestIdList.add(scheduleRequestInfoId);
        List<ScheduleRequestDisplayInfo> displayInfoList = getSchedulingService().getScheduleRequestDisplaysByIds(requestIdList, contextInfo);

        assertNotNull(displayInfoList);
        assertTrue(!displayInfoList.isEmpty());
        assertEquals(displayInfoList.get(0).getId(), scheduleRequestInfo.getId());
        assertEquals(displayInfoList.get(0).getName(), scheduleRequestInfo.getName());

        for (ScheduleRequestDisplayInfo displayInfo : displayInfoList) {
            assertNotNull(displayInfo);
            assertTrue(!displayInfo.getScheduleRequestComponentDisplays().isEmpty());
            assertTrue(!displayInfo.getScheduleRequestComponentDisplays().get(0).getBuildings().isEmpty());
            assertTrue(!displayInfo.getScheduleRequestComponentDisplays().get(0).getRooms().isEmpty());
            assertTrue(!displayInfo.getScheduleRequestComponentDisplays().get(0).getOrgs().isEmpty());
        }
    }

    @Test
    public void searchForScheduleRequestDisplays() throws Exception {

        // create a ScheduleRequestInfo
        String scheduleRequestInfoId = "ScheduleRequestsByRefObject-Id1";
        String scheduleRequestComponentInfoId = "scheduleRequest-ComponentInfoId1";
        String scheduleRequestInfoName = "testGetScheduleRequestByRefObject";
        String scheduleRequestSetId = "searchForScheduleRequestDisplaySetId";
        List<String> refObjectIds = new ArrayList<String>();
        refObjectIds.add("Ao1");
        refObjectIds.add("Ao2");
        ScheduleRequestSetInfo setInfo =  SchedulingServiceDataLoader.setupScheduleRequestSetInfo(scheduleRequestSetId, refObjectIds,
                "REF_OBJECT_URI_GLOBAL_PREFIX",
                false, 168);

        ScheduleRequestSetInfo returnSetInfo = schedulingService.createScheduleRequestSet(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET,
                "REF_OBJECT_URI_GLOBAL_PREFIX", setInfo, contextInfo );

        ScheduleRequestInfo scheduleRequestInfo = SchedulingServiceDataLoader.setupScheduleRequestInfo(scheduleRequestInfoId,
                scheduleRequestComponentInfoId, null, returnSetInfo.getId(), scheduleRequestInfoName);

        ScheduleRequestInfo returnInfo  = schedulingService.createScheduleRequest(requestType,
                scheduleRequestInfo,  contextInfo);

        // creation success
        assertNotNull(returnInfo);

        // get schedule request display info
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        Predicate p;

        qBuilder.setPredicates();
        p = equal("name", scheduleRequestInfoName);

        qBuilder.setPredicates(p);

        List<ScheduleRequestDisplayInfo> displayInfoList = getSchedulingService().searchForScheduleRequestDisplays(qBuilder.build(), contextInfo);

        assertNotNull(displayInfoList);
        assertTrue(!displayInfoList.isEmpty());
        assertEquals(displayInfoList.get(0).getId(), scheduleRequestInfo.getId());
        assertEquals(displayInfoList.get(0).getName(), scheduleRequestInfo.getName());

        for (ScheduleRequestDisplayInfo displayInfo : displayInfoList) {
            assertNotNull(displayInfo);
            assertTrue("Schedule Request Component Ids should be non-empty", !displayInfo.getScheduleRequestComponentDisplays().isEmpty());
            assertTrue("Buildings should be non-empty", !displayInfo.getScheduleRequestComponentDisplays().get(0).getBuildings().isEmpty());
            assertTrue("Rooms should be non-empty", !displayInfo.getScheduleRequestComponentDisplays().get(0).getRooms().isEmpty());
            assertTrue("Orgs should be non-empty", !displayInfo.getScheduleRequestComponentDisplays().get(0).getOrgs().isEmpty());
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

        ScheduleDisplayInfo displayInfo = null;
        for(int i=0 ; i<5 ; i++) {

            long start = System.currentTimeMillis();

            displayInfo = schedulingService.getScheduleDisplay(scheduleId,contextInfo);

            System.out.println("[TestSchedulingServiceImpl][testGetScheduleDisplay] Time elapsed = " + (System.currentTimeMillis()-start));

        }

        assertNotNull(displayInfo);

        assertEquals(scheduleId,displayInfo.getId());
        assertNotNull(displayInfo.getAtp());
        assertNotNull(displayInfo.getScheduleComponentDisplays().get(0).getRoom());
        assertNotNull(displayInfo.getScheduleComponentDisplays().get(0).getBuilding());

    }


    @Test
    public void testGetScheduleDisplaysByIds() throws Exception {

        List<String> scheduleIds = new ArrayList<String>();
        scheduleIds.add("1");
        scheduleIds.add("2");

        String atpId = SchedulingServiceDataLoader.ATP_ID;
        String roomId = SchedulingServiceDataLoader.ROOM_ID;

        ScheduleInfo scheduleInfo = null;
        ScheduleInfo returnedInfo = null;

        for(String scheduleId : scheduleIds) {

            scheduleInfo = SchedulingServiceDataLoader.setupScheduleInfo(scheduleId,atpId,false,roomId);
            returnedInfo = schedulingService.createSchedule(scheduleInfo.getTypeKey(),scheduleInfo,contextInfo);
            assertNotNull(returnedInfo);

        }

        List<ScheduleDisplayInfo> displayInfos = null;
        for(int i=0 ; i<5 ; i++) {
            long start = System.currentTimeMillis();
            displayInfos = schedulingService.getScheduleDisplaysByIds(scheduleIds,contextInfo);
            System.out.println("[TestSchedulingServiceImpl][testGetScheduleDisplaysByIds] Time Elapsed = " + (System.currentTimeMillis()-start));
        }

        for(ScheduleDisplayInfo displayInfo : displayInfos) {

            assertNotNull(displayInfo);
            assertTrue(scheduleIds.contains(displayInfo.getId()));
            assertNotNull(displayInfo.getAtp());
            assertNotNull(displayInfo.getScheduleComponentDisplays().get(0).getRoom());
            assertNotNull(displayInfo.getScheduleComponentDisplays().get(0).getBuilding());

        }

    }




    @Test
    public void testSearchForScheduleDisplays() throws Exception {
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();

        qBuilder.setPredicates();
        Predicate p = equal("atpId", SchedulingServiceDataLoader.ATP_ID);

        qBuilder.setPredicates(p);

        List<ScheduleDisplayInfo> list = schedulingService.searchForScheduleDisplays(qBuilder.build(),contextInfo);

        //  There are 3 schedules in test data
        assertEquals(3,list.size());

        ScheduleDisplayInfo displayInfo = list.get(0);

        assertNotNull(displayInfo);

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