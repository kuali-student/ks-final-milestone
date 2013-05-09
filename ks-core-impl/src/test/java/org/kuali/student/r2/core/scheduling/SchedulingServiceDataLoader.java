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

package org.kuali.student.r2.core.scheduling;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.*;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Test data loader for Scheduling Service testing
 * @author andrewlubbers
 *
 */
public class SchedulingServiceDataLoader {

    ///////////////////////
    // START / END TIMES
    ///////////////////////

    public final static Long START_TIME_MILLIS_8_00_AM = (long) (8 * 60 * 60 * 1000);
    public final static Long START_TIME_MILLIS_10_00_AM = (long) (10 * 60 * 60 * 1000);
    public final static Long START_TIME_MILLIS_1_00_PM = (long) (13 * 60 * 60 * 1000);
    public final static Long START_TIME_MILLIS_3_00_PM = (long) (15 * 60 * 60 * 1000);

    public final static Long END_TIME_MILLIS_8_50_AM = (long) (8 * 60 * 60 * 1000 + 50 * 60 * 1000);
    public final static Long END_TIME_MILLIS_9_10_AM = (long) (8 * 60 * 60 * 1000 + 70 * 60 * 1000);
    public final static Long END_TIME_MILLIS_10_50_AM = (long) (10 * 60 * 60 * 1000 + 50 * 60 * 1000);
    public final static Long END_TIME_MILLIS_11_10_AM = (long) (10 * 60 * 60 * 1000 + 70 * 60 * 1000);
    public final static Long END_TIME_MILLIS_1_50_PM = (long) (13 * 60 * 60 * 1000 + 50 * 60 * 1000);
    public final static Long END_TIME_MILLIS_2_10_PM = (long) (13 * 60 * 60 * 1000 + 70 * 60 * 1000);
    public final static Long END_TIME_MILLIS_3_50_PM = (long) (15 * 60 * 60 * 1000 + 50 * 60 * 1000);
    public final static Long END_TIME_MILLIS_4_10_PM = (long) (15 * 60 * 60 * 1000 + 70 * 60 * 1000);

    public final static Long START_TIME_MILLIS_5_10_PM = (long) (17 * 60 * 60 * 1000 + 10 * 60 * 1000);
    public final static Long END_TIME_MILLIS_6_00_PM = (long) (18 * 60 * 60 * 1000);

    public final static String ATP_ID = "TestATP";
    public final static String ROOM_ID = "Room1";

    private ContextInfo contextInfo;

    private AtpService atpService;
    private RoomService roomService;

    private ScheduleInfo testSchedule1;
    private ScheduleInfo testSchedule2;
    private ScheduleInfo testSchedule3;

    public SchedulingServiceDataLoader() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        contextInfo.setCurrentDate(new Date());
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

    public SchedulingServiceDataLoader (SchedulingService schedulingService) {
        this();
        setSchedulingService(schedulingService);
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }
    private SchedulingService schedulingService;
    private static String principalId = SchedulingServiceDataLoader.class.getSimpleName();

    public void loadData () throws InvalidParameterException, DataValidationErrorException, MissingParameterException, AlreadyExistsException, DoesNotExistException, ReadOnlyException, OperationFailedException, PermissionDeniedException {
        //////////////////////////
        // DAYS OF WEEK
        //////////////////////////

        // days of week M W F
        List<Integer> DOW_M_W_F= new ArrayList<Integer>();
        DOW_M_W_F.add(Calendar.MONDAY);
        DOW_M_W_F.add(Calendar.WEDNESDAY);
        DOW_M_W_F.add(Calendar.FRIDAY);
        // days of week T TH
        List<Integer> DOW_T_TH = new ArrayList<Integer>();
        DOW_T_TH.add(Calendar.TUESDAY);
        DOW_T_TH.add(Calendar.THURSDAY);

        ////////////////////
        // TEST DATA
        ////////////////////
        CommonServiceConstants.setIsIdAllowedOnCreate(contextInfo, true);

        loadTimeSlotInfo("1", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_M_W_F, START_TIME_MILLIS_8_00_AM, END_TIME_MILLIS_8_50_AM);
        loadTimeSlotInfo("2", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_M_W_F, START_TIME_MILLIS_8_00_AM, END_TIME_MILLIS_9_10_AM);
        loadTimeSlotInfo("3", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_T_TH, START_TIME_MILLIS_8_00_AM, END_TIME_MILLIS_8_50_AM);
        loadTimeSlotInfo("4", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_T_TH, START_TIME_MILLIS_8_00_AM, END_TIME_MILLIS_9_10_AM);
        loadTimeSlotInfo("5", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_M_W_F, START_TIME_MILLIS_10_00_AM, END_TIME_MILLIS_10_50_AM);
        loadTimeSlotInfo("6", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_M_W_F, START_TIME_MILLIS_10_00_AM, END_TIME_MILLIS_11_10_AM);
        loadTimeSlotInfo("7", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_T_TH, START_TIME_MILLIS_10_00_AM, END_TIME_MILLIS_10_50_AM);
        loadTimeSlotInfo("8", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_T_TH, START_TIME_MILLIS_10_00_AM, END_TIME_MILLIS_11_10_AM);
        loadTimeSlotInfo("9", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_M_W_F, START_TIME_MILLIS_1_00_PM, END_TIME_MILLIS_1_50_PM);
        loadTimeSlotInfo("10", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_M_W_F, START_TIME_MILLIS_1_00_PM, END_TIME_MILLIS_2_10_PM);
        loadTimeSlotInfo("11", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_T_TH, START_TIME_MILLIS_1_00_PM, END_TIME_MILLIS_1_50_PM);
        loadTimeSlotInfo("12", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_T_TH, START_TIME_MILLIS_1_00_PM, END_TIME_MILLIS_2_10_PM);
        loadTimeSlotInfo("13", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_M_W_F, START_TIME_MILLIS_3_00_PM, END_TIME_MILLIS_3_50_PM);
        loadTimeSlotInfo("14", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_M_W_F, START_TIME_MILLIS_3_00_PM, END_TIME_MILLIS_4_10_PM);
        loadTimeSlotInfo("15", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_T_TH, START_TIME_MILLIS_3_00_PM, END_TIME_MILLIS_3_50_PM);
        loadTimeSlotInfo("16", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_T_TH, START_TIME_MILLIS_3_00_PM, END_TIME_MILLIS_4_10_PM);

        loadTimeSlotInfo("toDelete", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_M_W_F, START_TIME_MILLIS_5_10_PM, END_TIME_MILLIS_6_00_PM);
        loadTimeSlotInfo("toUpdate", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING, DOW_M_W_F, START_TIME_MILLIS_5_10_PM, END_TIME_MILLIS_6_00_PM);

        CommonServiceConstants.setIsIdAllowedOnCreate(contextInfo, false);

        setupAtpAndRoomForDisplay(ATP_ID,ROOM_ID);

        testSchedule1 = setupScheduleInfo("testScheduleId1",ATP_ID,false,ROOM_ID);
        testSchedule2 = setupScheduleInfo("testScheduleId2",ATP_ID,false,ROOM_ID);
        testSchedule3 = setupScheduleInfo("testScheduleId3",ATP_ID,false,ROOM_ID);
        schedulingService.createSchedule(testSchedule1.getTypeKey(), testSchedule1, contextInfo);
        schedulingService.createSchedule(testSchedule2.getTypeKey(), testSchedule2, contextInfo);
        schedulingService.createSchedule(testSchedule3.getTypeKey(), testSchedule3, contextInfo);

    }

    private void loadTimeSlotInfo (String ts_id, String stateKey, String typeKey, List<Integer> weekdays, Long startTimeInMillisecs, Long endTimeInMillisecs)
            throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        TimeSlotInfo ts = new TimeSlotInfo();
        ts.setId(ts_id);
        ts.setWeekdays(weekdays);
        TimeOfDayInfo startTime = new TimeOfDayInfo();
        startTime.setMilliSeconds(startTimeInMillisecs);
        ts.setStartTime(startTime);
        TimeOfDayInfo endTime = new TimeOfDayInfo();
        endTime.setMilliSeconds(endTimeInMillisecs);
        ts.setEndTime(endTime);
        ts.setStateKey(stateKey);
        ts.setTypeKey(typeKey);
        schedulingService.createTimeSlot(typeKey, ts, contextInfo);
    }

    public static ScheduleRequestInfo setupScheduleRequestInfo(String scheduleRequestInfoId,
                                                               String ScheduleRequestComponentInfoId, String scheduleRequestInfoName) {
        ScheduleRequestInfo scheduleRequestInfo = new ScheduleRequestInfo();
        scheduleRequestInfo.setId(scheduleRequestInfoId);

        // SSRTODO: Add real data here
        scheduleRequestInfo.setScheduleId("schedule id");
        scheduleRequestInfo.setScheduleRequestSetId("schedule request set id");


        scheduleRequestInfo.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);
        scheduleRequestInfo.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        scheduleRequestInfo.setName(scheduleRequestInfoName);

        List<ScheduleRequestComponentInfo> componentInfoList = new ArrayList<ScheduleRequestComponentInfo>();
        ScheduleRequestComponentInfo componentInfo = new ScheduleRequestComponentInfo();
        componentInfo.setId(ScheduleRequestComponentInfoId);
        List<String> buildingIds = new ArrayList<String>();
        buildingIds.add("097");
        buildingIds.add("039");
        componentInfo.setBuildingIds(buildingIds);
        List<String> campusIds = new ArrayList<String>();
        campusIds.add("MAIN");
        componentInfo.setCampusIds(campusIds);
        List<String> orgIds = new ArrayList<String>();
        orgIds.add("1001");
        orgIds.add("1010");
        componentInfo.setOrgIds(orgIds);
        List<String> roomIds = new ArrayList<String>();
        roomIds.add("1115097");
        roomIds.add("1505039");
        componentInfo.setRoomIds(roomIds);
        List<String> timeSlotIds = new ArrayList<String>();
        timeSlotIds.add("1");
        timeSlotIds.add("2");
        componentInfo.setTimeSlotIds(timeSlotIds);
        List<String> resourceTypeKeys = new ArrayList<String>();
        resourceTypeKeys.add(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);
        componentInfo.setResourceTypeKeys(resourceTypeKeys);

        componentInfoList.add(componentInfo);

        scheduleRequestInfo.setScheduleRequestComponents(componentInfoList);

        return scheduleRequestInfo;
    }

    public static ScheduleInfo setupScheduleInfo(String id,String atpId,boolean isTBA,String roomId) {

        ScheduleInfo info = new ScheduleInfo();

        info.setTypeKey(SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE);
        info.setStateKey(SchedulingServiceConstants.SCHEDULE_STATE_ACTIVE);
        info.setAtpId(atpId);
        info.setId(id);

        List<ScheduleComponentInfo> scheduleComponents = new ArrayList<ScheduleComponentInfo>();

        ScheduleComponentInfo scheduleComponentInfo = new ScheduleComponentInfo();
        scheduleComponentInfo.setId("ScheduleComponent1");
        scheduleComponentInfo.setIsTBA(Boolean.valueOf(isTBA));

        List<String> timeSlotIds = new ArrayList();
        timeSlotIds.add("1");
        timeSlotIds.add("2");

        scheduleComponentInfo.setRoomId(roomId);
        scheduleComponentInfo.setTimeSlotIds(timeSlotIds);
        scheduleComponents.add(scheduleComponentInfo);

        info.setScheduleComponents(scheduleComponents);

        return info;
    }

    private void setupAtpAndRoomForDisplay(String atpId,String roomId)
    throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, AlreadyExistsException, PermissionDeniedException, OperationFailedException{

        AtpInfo atp = new AtpInfo();
        atp.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        atp.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        atp.setCode("123");
        atp.setName("Test");
        atp.setStartDate(new Date());
        atp.setEndDate(new Date());
        atp.setId(atpId);
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("Test");
        atp.setDescr(descr);

        atpService.createAtp(atp.getTypeKey(),atp,contextInfo);

        BuildingInfo buildingInfo = new BuildingInfo();
        buildingInfo.setId("Building1");
        buildingInfo.setStateKey("kuali.room.building.state.active");
        buildingInfo.setTypeKey("kuali.room.building.type.test");

        getRoomService().createBuilding(buildingInfo.getTypeKey(),buildingInfo,contextInfo);

        RoomInfo room = new RoomInfo();
        room.setId(roomId);
        room.setTypeKey("kuali.room.type.classroom");
        room.setStateKey("kuali.room.room.state.active");

        getRoomService().createRoom("Building1",room.getTypeKey(),room,contextInfo);

    }

}