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

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
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
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

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

        /**
         * Create Time Slot data
         */

        String fullTermFallType = SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_FALL;
        String fullTermSpringType =  SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD_FULLTERM_SPRING;
        String adHocTermType =  SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_ADHOC;

        String[][] timeSlotsData = {
            //  This data is for testing search.
            //  {id, type, name, days, start time, end time}
            //  Base time slot
            {"ts101", fullTermFallType, "1000", "MWF", "08:00", "08:50"},
            {"ts102", fullTermFallType, "1010", "MWF", "08:00", "09:10"},
            {"ts103", fullTermFallType, "1001", "TH", "08:00", "08:50"},
            {"ts3", fullTermFallType, "1002", "MTWHF", "08:00", "08:50"},
            //  Same day pattern and type as base, but change up the end times
            {"ts4", fullTermFallType, "1003", "MWF", "08:00", "09:50"},
            {"ts5", fullTermFallType, "1004", "TH", "08:00", "09:50"},
            {"ts6", fullTermFallType, "1005", "MTWHF", "08:00", "09:50"},
            //  Ad hoc with different end time
            {"ts7", adHocTermType, "1006", "MWF", "08:00", "08:51"},
            //  Same day pattern and type as base, but change up the type
            {"ts8", fullTermSpringType, "1007", "MWF", "08:00", "08:50"},
            {"ts9", fullTermSpringType, "1008", "TH", "08:00", "08:50"},
            {"ts10", fullTermSpringType, "1009", "MTWHF", "08:00", "08:50"},

            //  Additional Fall Full Term time slots ...
            {"ts104", fullTermFallType, "1012", "TH", "08:00", "09:10"},
            {"ts105", fullTermFallType, "1013", "MWF", "10:00", "10:50"},
            {"ts106", fullTermFallType, "1014", "MWF", "10:00", "11:10"},
            {"ts107", fullTermFallType, "1015", "TH", "10:00", "10:50"},
            {"ts108", fullTermFallType, "1016", "TH", "10:00", "11:10"},
            {"ts109", fullTermFallType, "1017", "MWF", "13:00", "13:50"},
            {"ts110", fullTermFallType, "1018", "MWF", "13:00", "14:10"},
            {"ts111", fullTermFallType, "1019", "TH", "13:00", "13:50"},
            {"ts112", fullTermFallType, "1020", "TH", "13:00", "14:10"},
            {"ts113", fullTermFallType, "1021", "MWF", "15:00", "15:50"},
            {"ts114", fullTermFallType, "1022", "MWF", "15:00", "16:10"},
            {"ts115", fullTermFallType, "1023", "TH", "15:00", "15:50"},
            {"ts116", fullTermFallType, "1024", "TH", "15:00", "16:10"},

            //  Other Time Slots
            {"toDelete", fullTermFallType, "1025", "MWF", "17:10", "18:00"},
            {"toUpdate", fullTermFallType, "1026", "MWF", "17:10", "18:00"},
        };

        for (String[] ts: timeSlotsData) {
            TimeSlotInfo tsInfo = new TimeSlotInfo();
            tsInfo.setId(ts[0]);
            tsInfo.setTypeKey(ts[1]);
            tsInfo.setStateKey(SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY);
            tsInfo.setName(ts[2]);
            tsInfo.setWeekdays(SchedulingServiceUtil.weekdaysString2WeekdaysList(ts[3]));
            tsInfo.setStartTime(SchedulingServiceUtil.makeTimeOfDayFromMilitaryTimeString(ts[4]));
            tsInfo.setEndTime(SchedulingServiceUtil.makeTimeOfDayFromMilitaryTimeString(ts[5]));
            schedulingService.createTimeSlot(tsInfo.getTypeKey(), tsInfo, contextInfo);
        }

        CommonServiceConstants.setIsIdAllowedOnCreate(contextInfo, false);

        setupAtpAndRoomForDisplay(ATP_ID,ROOM_ID);

        ScheduleInfo testSchedule1 = setupScheduleInfo("testScheduleId1", ATP_ID,false,ROOM_ID);
        ScheduleInfo testSchedule2 = setupScheduleInfo("testScheduleId2", ATP_ID,false,ROOM_ID);
        ScheduleInfo testSchedule3 = setupScheduleInfo("testScheduleId3", ATP_ID,false,ROOM_ID);
        schedulingService.createSchedule(testSchedule1.getTypeKey(), testSchedule1, contextInfo);
        schedulingService.createSchedule(testSchedule2.getTypeKey(), testSchedule2, contextInfo);
        schedulingService.createSchedule(testSchedule3.getTypeKey(), testSchedule3, contextInfo);
    }

    private void loadTimeSlotInfo (String ts_id, String name, String stateKey, String typeKey, List<Integer> weekdays, Long startTimeInMillisecs, Long endTimeInMillisecs)
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
        schedulingService.createTimeSlot(typeKey, ts, contextInfo);
    }

    /**
     * Creates a ScheduleRequestSetInfo from given args.
     * @param scheduleRequestSetId
     * @param refObjectIds
     * @param maxEnrollmentShared
     * @param maxEnrollment
     * @return
     */
    public static ScheduleRequestSetInfo setupScheduleRequestSetInfo(String scheduleRequestSetId, List<String> refObjectIds,
                                                                     String refObjectType,
                                                                     Boolean maxEnrollmentShared, Integer maxEnrollment) {
        ScheduleRequestSetInfo srsInfo = new ScheduleRequestSetInfo();
        srsInfo.setId(scheduleRequestSetId);
        srsInfo.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET);
        srsInfo.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        srsInfo.setRefObjectTypeKey(refObjectType);
        srsInfo.setRefObjectIds(refObjectIds);
        srsInfo.setMaxEnrollmentShared(maxEnrollmentShared);
        srsInfo.setMaximumEnrollment(maxEnrollment);

        return srsInfo;
    }

    public static ScheduleRequestInfo setupScheduleRequestInfo(String scheduleRequestInfoId,
                                                               String scheduleRequestComponentInfoId,
                                                               String scheduleId,
                                                               String scheduleRequestSetId,
                                                               String scheduleRequestInfoName) {
        ScheduleRequestInfo scheduleRequestInfo = new ScheduleRequestInfo();
        scheduleRequestInfo.setId(scheduleRequestInfoId);

        scheduleRequestInfo.setScheduleId(scheduleId);
        scheduleRequestInfo.setScheduleRequestSetId(scheduleRequestSetId);

        scheduleRequestInfo.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);
        scheduleRequestInfo.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        scheduleRequestInfo.setName(scheduleRequestInfoName);

        List<ScheduleRequestComponentInfo> componentInfoList = new ArrayList<ScheduleRequestComponentInfo>();
        ScheduleRequestComponentInfo componentInfo = new ScheduleRequestComponentInfo();
        componentInfo.setId(scheduleRequestComponentInfoId);
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
        timeSlotIds.add("ts101");
        timeSlotIds.add("ts102");
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
        scheduleComponentInfo.setId(id + "-ScheduleComponent1");
        scheduleComponentInfo.setIsTBA(Boolean.valueOf(isTBA));

        List<String> timeSlotIds = new ArrayList();
        timeSlotIds.add("ts101");
        timeSlotIds.add("ts102");

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