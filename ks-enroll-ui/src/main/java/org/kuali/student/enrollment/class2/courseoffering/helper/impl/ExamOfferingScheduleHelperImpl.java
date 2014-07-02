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
 * Created by David Yin on 3/11/14
 */
package org.kuali.student.enrollment.class2.courseoffering.helper.impl;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.acal.dto.ExamPeriodWrapper;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExamOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.helper.ExamOfferingScheduleHelper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.ExamOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.ExamOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseofferingset.util.CourseOfferingSetUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class defines methods to load and save schedules in Exam Offering schedule logic
 *
 * @author Kuali Student Team
 */
public class ExamOfferingScheduleHelperImpl implements ExamOfferingScheduleHelper {
    public ExamOfferingScheduleHelperImpl() {
    }

    /**
     * This method saves the Request Scheduleing Information for a list of exam offerings
     *
     * @param eoWrappers  a list of ExamOfferingWrapper
     * @return StatusInfo
     */
    @Override
    @Transactional
    // If it's already a part of transaction, it's ok.. Otherwise, create a new transaction boundary for all the changes.
    public StatusInfo saveScheduleRequestBulk(List<ExamOfferingWrapper> eoWrappers, ContextInfo defaultContextInfo){
        if (!eoWrappers.isEmpty()) {
            for (ExamOfferingWrapper eoWrapper : eoWrappers) {
                ScheduleRequestSetInfo scheduleRequestSetInfo = eoWrapper.getScheduleRequestSetInfo();
                //  If this is a new ScheduleRequestSet then set the ref object Id to this EO and create it.
                if ((scheduleRequestSetInfo == null) || StringUtils.isBlank(scheduleRequestSetInfo.getId())){
                    scheduleRequestSetInfo = makeScheduleRequestSetInfo(eoWrapper.getEoInfo().getId());
                    scheduleRequestSetInfo = createScheduleRequestSetInfo(scheduleRequestSetInfo, defaultContextInfo);
                    eoWrapper.setScheduleRequestSetInfo(scheduleRequestSetInfo);
                }

                //There should be only one schedule (RSI) per EO
                for (ScheduleWrapper scheduleWrapper : eoWrapper.getRequestedScheduleComponents()) {
                    saveSingleScheduleRequest(scheduleWrapper, eoWrapper, defaultContextInfo);
                }
            }
        }

        return new StatusInfo();

    }

    /**
     * This method saves the Request Scheduleing Information one exam offerings
     *
     * @param eoWrapper ExamOfferingWrapper
     * @return StatusInfo
     */
    @Override
    @Transactional
    // If it's already a part of transaction, it's ok.. Otherwise, create a new transaction boundary for all the changes.
    public StatusInfo saveScheduleRequest(ExamOfferingWrapper eoWrapper, ContextInfo defaultContextInfo) {
        ScheduleRequestSetInfo scheduleRequestSetInfo = eoWrapper.getScheduleRequestSetInfo();
        //  If this is a new ScheduleRequestSet then set the ref object Id to this EO and create it.
        if ((scheduleRequestSetInfo == null) || StringUtils.isBlank(scheduleRequestSetInfo.getId())) {
            scheduleRequestSetInfo = makeScheduleRequestSetInfo(eoWrapper.getEoInfo().getId());
            scheduleRequestSetInfo = createScheduleRequestSetInfo(scheduleRequestSetInfo, defaultContextInfo);
            eoWrapper.setScheduleRequestSetInfo(scheduleRequestSetInfo);
        }

        //There should be only one schedule (RSI) per EO
        for (ScheduleWrapper scheduleWrapper : eoWrapper.getRequestedScheduleComponents()) {
            saveSingleScheduleRequest(scheduleWrapper, eoWrapper, defaultContextInfo);
        }

        return new StatusInfo();

    }

    /**
     * This method saves the Request Scheduleing Information for a given exam offering
     *
     * @param scheduleWrapper  ScheduleWrapper that wraps up request scheduling information
     * @param eoWrapper  the given exam offering wrapper
     * @return StatusInfo
     */
    private StatusInfo saveSingleScheduleRequest(ScheduleWrapper scheduleWrapper, ExamOfferingWrapper eoWrapper, ContextInfo defaultContextInfo) {
        if (scheduleWrapper.isToBeCreated()) {
            ScheduleRequestInfo scheduleRequestInfo = new ScheduleRequestInfo();
            scheduleRequestInfo.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);
            scheduleRequestInfo.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
            scheduleRequestInfo.setScheduleRequestSetId(eoWrapper.getScheduleRequestSetInfo().getId());

            try {
                ScheduleRequestComponentInfo componentInfo = buildScheduleReuestComponent(eoWrapper, scheduleWrapper, defaultContextInfo);
                scheduleRequestInfo.getScheduleRequestComponents().add(componentInfo);

                ScheduleRequestInfo newScheduleRequestInfo = CourseOfferingManagementUtil.getSchedulingService().createScheduleRequest(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST, scheduleRequestInfo, defaultContextInfo);
                scheduleWrapper.setScheduleRequestInfo(newScheduleRequestInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (scheduleWrapper.isModified()) {
            try {
                ScheduleRequestInfo scheduleRequest = scheduleWrapper.getScheduleRequestInfo();
                ScheduleRequestComponentInfo componentInfo = buildScheduleReuestComponent(eoWrapper, scheduleWrapper, defaultContextInfo);
                scheduleRequest.getScheduleRequestComponents().clear();
                scheduleRequest.getScheduleRequestComponents().add(componentInfo);

                ScheduleRequestInfo newScheduleRequest = CourseOfferingManagementUtil.getSchedulingService().updateScheduleRequest(scheduleRequest.getId(), scheduleRequest, defaultContextInfo);
                scheduleWrapper.setScheduleRequestInfo(newScheduleRequest);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return new StatusInfo();
    }

    /**
     * This method constructs the instance of ScheduleRequestComponentInfo
     *
     * @param scheduleWrapper  ScheduleWrapper that wraps up request scheduling information
     * @param eoWrapper  the given exam offering wrapper
     * @return ScheduleRequestComponentInfo
     */
    private ScheduleRequestComponentInfo buildScheduleReuestComponent(ExamOfferingWrapper eoWrapper, ScheduleWrapper scheduleWrapper, ContextInfo defaultContextInfo) throws Exception{
        ScheduleRequestComponentInfo componentInfo = new ScheduleRequestComponentInfo();
        componentInfo.setIsTBA(false);

        componentInfo.getBuildingIds().clear();
        if( scheduleWrapper.getBuilding() != null && scheduleWrapper.getBuilding().getId() != null ) {
            componentInfo.getBuildingIds().add(scheduleWrapper.getBuilding().getId());
        }

        if(scheduleWrapper.getRoom() != null) {
            List<String> roomIds = new ArrayList<String>();
            roomIds.add(scheduleWrapper.getRoom().getId());
            componentInfo.setRoomIds(roomIds);
        }

        componentInfo.setResourceTypeKeys(scheduleWrapper.getFeatures());

        TimeSlotInfo timeSlotInfo = fetchOrCreateTimeSlot(eoWrapper, scheduleWrapper, defaultContextInfo);
        scheduleWrapper.setTimeSlot(timeSlotInfo);
        componentInfo.getTimeSlotIds().add(timeSlotInfo.getId());

        return componentInfo;
    }

    /**
     * This method retrieves or create a timeslot given an exam offering and its scheduling infomation
     *
     * @param scheduleWrapper  ScheduleWrapper that wraps up request scheduling information
     * @param eoWrapper  the given exam offering wrapper
     * @return TimeSlotInfo
     */
    public TimeSlotInfo fetchOrCreateTimeSlot(ExamOfferingWrapper eoWrapper, ScheduleWrapper scheduleWrapper, ContextInfo defaultContextInfo) throws Exception {

        TimeOfDayInfo startTimeOfDayInfo = new TimeOfDayInfo();
        TimeOfDayInfo endTimeOfDayInfo = new TimeOfDayInfo();

        List<Integer> days = new ArrayList<Integer>();
        days.add(Integer.valueOf(scheduleWrapper.getDayInExamPeriod()));

        String startTimeString = scheduleWrapper.getStartTime();
        if (StringUtils.isNotBlank(startTimeString)) {
            startTimeOfDayInfo = TimeOfDayHelper.makeTimeOfDayInfoFromTimeString(startTimeString);
        }

        String endTimeString = scheduleWrapper.getEndTime();
        if (StringUtils.isNotEmpty(endTimeString)) {
            endTimeOfDayInfo = TimeOfDayHelper.makeTimeOfDayInfoFromTimeString(endTimeString);
        }

        TimeSlotInfo timeSlot;
        List<TimeSlotInfo> existingTimeSlots = CourseOfferingManagementUtil.getSchedulingService().getTimeSlotsByDaysAndStartTimeAndEndTime(SchedulingServiceConstants.TIME_SLOT_TYPE_EXAM_OFFERING_ADHOC, days, startTimeOfDayInfo, endTimeOfDayInfo, defaultContextInfo);
        //If AdHoc TS exists, use that. Otherwise, check create one if the user has permission
        if (!existingTimeSlots.isEmpty()) {
            timeSlot = KSCollectionUtils.getRequiredZeroElement(existingTimeSlots);
        } else {
            timeSlot = new TimeSlotInfo();
            timeSlot.setTypeKey(SchedulingServiceConstants.TIME_SLOT_TYPE_EXAM_OFFERING_ADHOC);
            timeSlot.setStateKey(SchedulingServiceConstants.TIME_SLOT_STATE_ACTIVE);
            timeSlot.setStartTime(startTimeOfDayInfo);
            timeSlot.setEndTime(endTimeOfDayInfo);
            timeSlot.setWeekdays(days);
            timeSlot = CourseOfferingManagementUtil.getSchedulingService().createTimeSlot(SchedulingServiceConstants.TIME_SLOT_TYPE_EXAM_OFFERING_ADHOC, timeSlot, defaultContextInfo);

        }

        return timeSlot;
    }

    /**
     * This method deletes request scheduling information for a list of exam offerings
     *
     * @param eoWrappers  a list of ExamOfferingWrapper
     * @return StatusInfo
     */
    public StatusInfo deleteScheduleRequestBulk (List<ExamOfferingWrapper> eoWrappers, ContextInfo defaultContextInfo) {
        if (!eoWrappers.isEmpty()) {
            for (ExamOfferingWrapper eoWrapper : eoWrappers) {
                for (ScheduleWrapper scheduleWrapper : eoWrapper.getDeletedRequestedScheduleComponents()) {
                    deleteScheduleRequest (scheduleWrapper, defaultContextInfo);
                }
            }
        }
        return new StatusInfo();
    }

    /**
     * This method deletes one request scheduling information
     *
     * @param scheduleWrapper  ScheduleWrapper that wraps up request scheduling information of an exam offering
     * @return StatusInfo
     */
    private StatusInfo deleteScheduleRequest (ScheduleWrapper scheduleWrapper, ContextInfo defaultContextInfo) {
        try {
            String scheduleRequestId = scheduleWrapper.getScheduleRequestInfo().getId();
            StatusInfo statusInfo = CourseOfferingManagementUtil.getSchedulingService().deleteScheduleRequest(scheduleRequestId, defaultContextInfo);
            if (!statusInfo.getIsSuccess()){
                throw new OperationFailedException("Cant delete the schedule request " + statusInfo.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new StatusInfo();
    }

    @Override
    public void loadSchedules(ExamOfferingWrapper eoWrapper, CourseOfferingManagementForm theForm, ContextInfo defaultContextInfo){
        loadScheduleRequests(eoWrapper, theForm, defaultContextInfo);
    }

    /**
     * This method loads the schedule requests/components.
     * Support multiple schedule requests
     *
     * @param eoWrapper  ExamOfferingWrapper
     * @param theForm CourseOfferingManagementForm
     */
    public void loadScheduleRequests(ExamOfferingWrapper eoWrapper, CourseOfferingManagementForm theForm, ContextInfo defaultContextInfo){
        try {
            List<ScheduleRequestInfo> scheduleRequestInfos = CourseOfferingManagementUtil.getSchedulingService().getScheduleRequestsByRefObject(
                    ExamOfferingServiceConstants.REF_OBJECT_URI_EXAM_OFFERING, eoWrapper.getEoInfo().getId(), defaultContextInfo);

            if (!scheduleRequestInfos.isEmpty()){
                int firstScheduleRequestIndex = 0;
                for (ScheduleRequestInfo scheduleRequestInfo : scheduleRequestInfos){
                    if(scheduleRequestInfo.getScheduleRequestComponents() != null && !scheduleRequestInfo.getScheduleRequestComponents().isEmpty()){
                        for (ScheduleRequestComponentInfo componentInfo : scheduleRequestInfo.getScheduleRequestComponents()) {
                            ScheduleWrapper scheduleWrapper = new ScheduleWrapper(scheduleRequestInfo,componentInfo);
                            buildScheduleWrapper(scheduleWrapper, componentInfo, theForm, defaultContextInfo);

                            eoWrapper.getRequestedScheduleComponents().add(scheduleWrapper);
                        }
                    }
                }
                //set current RSI of the eo
                if(eoWrapper.getRequestedScheduleComponents() != null && !eoWrapper.getRequestedScheduleComponents().isEmpty()){
                    eoWrapper.setRequestedSchedule(eoWrapper.getRequestedScheduleComponents().get(firstScheduleRequestIndex));
                    ScheduleRequestSetInfo scheduleRequestSet = CourseOfferingManagementUtil.getSchedulingService().getScheduleRequestSet(eoWrapper.getRequestedScheduleComponents().get(firstScheduleRequestIndex).getScheduleRequestInfo().getScheduleRequestSetId(),defaultContextInfo);
                    eoWrapper.setScheduleRequestSetInfo(scheduleRequestSet);
                }else{
                    ScheduleWrapper scheduleWrapper = new ScheduleWrapper();
                    //set current RSI of the eo
                    eoWrapper.setRequestedSchedule(scheduleWrapper);
                }
            } else {
                ScheduleWrapper scheduleWrapper = new ScheduleWrapper();
                //set current RSI of the eo
                eoWrapper.setRequestedSchedule(scheduleWrapper);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method constructs the ScheduleWrapper for a given exam offering
     *
     * @param scheduleWrapper ScheduleWrapper that contains request scheduling information
     * @param componentInfo ScheduleRequestComponentInfo
     * @param theForm CourseOfferingManagementForm
     */
    public void buildScheduleWrapper(ScheduleWrapper scheduleWrapper, ScheduleRequestComponentInfo componentInfo,
                                        CourseOfferingManagementForm theForm, ContextInfo defaultContextInfo){

        try {
            //There should be only one timeslot per ScheduleRequestComponentInfo
            String timeSlotId = KSCollectionUtils.getOptionalZeroElement(componentInfo.getTimeSlotIds());
            TimeSlotInfo timeSlot = CourseOfferingManagementUtil.getSchedulingService().getTimeSlot(timeSlotId, defaultContextInfo);
            scheduleWrapper.setTimeSlot(timeSlot);

            setScheduleTimeSlotInfo(scheduleWrapper, theForm.getExamPeriodWrapper());
            setScheduleRoomAndBuilding(scheduleWrapper, componentInfo, defaultContextInfo);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method constructs timeslot part of the ScheduleWrapper for a given exam offering
     *
     * @param scheduleWrapper ScheduleWrapper that contains request scheduling information
     * @param examPeriodWrapper ExamPeriodWrapper
     */
    public void setScheduleTimeSlotInfo(ScheduleWrapper scheduleWrapper, ExamPeriodWrapper examPeriodWrapper)
            throws OperationFailedException {

        TimeOfDayInfo startTime = scheduleWrapper.getTimeSlot().getStartTime();
        if (startTime != null && startTime.getHour() != null) {
            String startTimeUI = TimeOfDayHelper.makeFormattedTimeForAOSchedules(startTime);
            scheduleWrapper.setStartTimeUI(startTimeUI);
            scheduleWrapper.setStartTime(startTimeUI);
            //scheduleWrapper.setStartTimeAmPm(org.apache.commons.lang.StringUtils.substringAfter(startTimeUI, " "));
        }

        TimeOfDayInfo endTime = scheduleWrapper.getTimeSlot().getEndTime();
        if (endTime != null && endTime.getHour() != null) {
            String endTimeUI = TimeOfDayHelper.makeFormattedTimeForAOSchedules(endTime);
            scheduleWrapper.setEndTimeUI(endTimeUI);
            scheduleWrapper.setEndTime(endTimeUI);
            //scheduleWrapper.setEndTimeAmPm(org.apache.commons.lang.StringUtils.substringAfter(endTimeUI, " "));
        }

        List<Integer> days = scheduleWrapper.getTimeSlot().getWeekdays();
        if (days != null && days.size() > 0) {
            scheduleWrapper.setDaysUI(ExamOfferingManagementUtil.examPeriodDaysDisplay(days, examPeriodWrapper));
            scheduleWrapper.setDayInExamPeriod(KSCollectionUtils.getOptionalZeroElement(days).toString());
        }
    }

    /**
     * This method constructs facility part of the ScheduleWrapper for a given exam offering
     *
     * @param scheduleWrapper ScheduleWrapper that contains request scheduling information
     * @param componentInfo ScheduleRequestComponentInfo
     */
    public void setScheduleRoomAndBuilding(ScheduleWrapper scheduleWrapper, ScheduleRequestComponentInfo componentInfo,
                                            ContextInfo defaultContextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        if (!componentInfo.getRoomIds().isEmpty()){
            int firstRoomIndex = 0;
            String roomId = componentInfo.getRoomIds().get(firstRoomIndex);
            if (StringUtils.isNotBlank(roomId)) {
                RoomInfo room = CourseOfferingManagementUtil.getRoomService().getRoom(roomId, defaultContextInfo);
                scheduleWrapper.setRoom(room);
                scheduleWrapper.setRoomCode(room.getRoomCode());
                if (!room.getRoomUsages().isEmpty()){
                    int firstRoomUsagesIndex = 0;
                    scheduleWrapper.setRoomCapacity(room.getRoomUsages().get(firstRoomUsagesIndex).getHardCapacity());
                }

                BuildingInfo buildingInfo = CourseOfferingManagementUtil.getRoomService().getBuilding(room.getBuildingId(), defaultContextInfo);
                scheduleWrapper.setBuilding(buildingInfo);
                scheduleWrapper.setBuildingCode(buildingInfo.getBuildingCode());
                scheduleWrapper.setBuildingId(room.getBuildingId());
            }

        } else if (!componentInfo.getBuildingIds().isEmpty()){
            int firstBuildingIndex = 0;
            String buildingId = componentInfo.getBuildingIds().get(firstBuildingIndex);

            if (StringUtils.isNotBlank(buildingId)) {
                BuildingInfo buildingInfo = CourseOfferingManagementUtil.getRoomService().getBuilding(buildingId, defaultContextInfo);
                scheduleWrapper.setBuilding(buildingInfo);
                scheduleWrapper.setBuildingCode(buildingInfo.getBuildingCode());
                scheduleWrapper.setBuildingId(buildingId);
            }

        }
    }

    /**
     * This method validates the request scheduling information that a user enters
     *
     * @param requestedSchedule  ScheduleWrapper that contains request scheduling information
     * @param clusterIndex the AO cluster index. It is used for error message display
     * @param eoWrapperIndex used for error message display
     * @param eoDriverPerCO wether or not the the EO is driver per CO
     */
    public boolean validateScheduleRequestBulk(ScheduleWrapper requestedSchedule, int clusterIndex, int eoWrapperIndex, boolean eoDriverPerCO, ContextInfo defaultContextInfo){
        String examOfferingPath = null;
        boolean success = true;
        //if clusterIndex < 0, display driverPerCO EOs
        if (eoDriverPerCO) {
            examOfferingPath = "examOfferingWrapperList[" + eoWrapperIndex + "]";
        } else {
            examOfferingPath = "eoClusterResultList[" + clusterIndex + "].eoWrapperList[" + eoWrapperIndex + "]";
        }

        // validate the days in exam period
        if (StringUtils.isNotBlank(requestedSchedule.getDayInExamPeriod())) {
            if(!isInteger(requestedSchedule.getDayInExamPeriod())) {
                GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.dayInExamPeriod", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_INVALID_DAYS);
                success = false;
            }
        } else {
            GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.dayInExamPeriod", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_REQUIRED_FIELD_EMPTY);
            success = false;
        }

        //validate start/end time
        if (!validateTime(requestedSchedule, examOfferingPath)) {
            success = false;
        }

        // if a room or a building were entered, ensure the building and room code are valid
        try {
            int firstInfo = 0;
            BuildingInfo buildingRetrieved = null;
            // building code exists
            if (StringUtils.isNotBlank(requestedSchedule.getBuildingCode())){

                List<BuildingInfo> buildings = retrieveBuildingInfoByCode(requestedSchedule.getBuildingCode(),true);
                if (buildings.isEmpty()) {
                    GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.buildingCode", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_INVALID_BUILDING_CODE);
                    success = false;
                } else {
                    buildingRetrieved = buildings.get(firstInfo);
                    requestedSchedule.setBuilding(buildingRetrieved);
                    requestedSchedule.setBuildingCode(buildingRetrieved.getBuildingCode());
                }

                // if a building code exists and a room code exists, validate the room code and populate the room info
                if ((buildingRetrieved != null) && StringUtils.isNotBlank(requestedSchedule.getRoomCode())) {
                    List<RoomInfo> rooms = CourseOfferingManagementUtil.getRoomService().getRoomsByBuildingAndRoomCode(requestedSchedule.getBuildingCode(), requestedSchedule.getRoomCode(), defaultContextInfo);
                    if (rooms.isEmpty()) {
                        GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.roomCode", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_INVALID_ROOM_CODE);
                        success = false;
                    } else {
                        RoomInfo room = rooms.get(firstInfo);
                        if (room.getRoomUsages() != null && !room.getRoomUsages().isEmpty()) {
                            requestedSchedule.setRoomCapacity(room.getRoomUsages().get(0).getHardCapacity());
                        }
                        requestedSchedule.setRoom(room);
                        requestedSchedule.setRoomCode(room.getRoomCode());
                    }
                } else {
                    requestedSchedule.setRoom(null);
                }
            } else if (StringUtils.isBlank(requestedSchedule.getBuildingCode())) {
                requestedSchedule.setBuilding(null);
                if (StringUtils.isNotBlank(requestedSchedule.getRoomCode())) {
                    GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.roomCode", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_INVALID_BUILDING_ROOM);
                    success = false;
                } else {
                    requestedSchedule.setRoom(null);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return success;
    }

    /**
     * This method validates the request scheduling information that a user enters
     *
     * @param requestedSchedule  ScheduleWrapper that contains request scheduling information
     * @param selectedCollectionPath
     * @param selectedLine
     */
    public boolean validateScheduleRequest(ScheduleWrapper requestedSchedule, String selectedCollectionPath, String selectedLine, ContextInfo defaultContextInfo){
        String examOfferingPath = selectedCollectionPath + "[" + selectedLine + "]";
        boolean success = true;

        // validate the days in exam period
        if (StringUtils.isNotBlank(requestedSchedule.getDayInExamPeriod())) {
            if(!isInteger(requestedSchedule.getDayInExamPeriod())) {
                GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.dayInExamPeriod", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_INVALID_DAYS);
                success = false;
            }
        } else {
            GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.dayInExamPeriod", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_REQUIRED_FIELD_EMPTY);
            success = false;
        }

        //validate start/end time
        if (!validateTime(requestedSchedule, examOfferingPath)) {
            success = false;
        }

        // if a room or a building were entered, ensure the building and room code are valid
        try {
            int firstInfo = 0;
            BuildingInfo buildingRetrieved = null;
            // building code exists
            if (StringUtils.isNotBlank(requestedSchedule.getBuildingCode())){

                List<BuildingInfo> buildings = retrieveBuildingInfoByCode(requestedSchedule.getBuildingCode(),true);
                if (buildings.isEmpty()) {
                    GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.buildingCode", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_INVALID_BUILDING_CODE);
                    success = false;
                } else {
                    buildingRetrieved = buildings.get(firstInfo);
                    requestedSchedule.setBuilding(buildingRetrieved);
                    requestedSchedule.setBuildingCode(buildingRetrieved.getBuildingCode());
                }

                // if a building code exists and a room code exists, validate the room code and populate the room info
                if ((buildingRetrieved != null) && StringUtils.isNotBlank(requestedSchedule.getRoomCode())) {
                    List<RoomInfo> rooms = CourseOfferingManagementUtil.getRoomService().getRoomsByBuildingAndRoomCode(requestedSchedule.getBuildingCode(), requestedSchedule.getRoomCode(), defaultContextInfo);
                    if (rooms.isEmpty()) {
                        GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.roomCode", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_INVALID_ROOM_CODE);
                        success = false;
                    } else {
                        RoomInfo room = rooms.get(firstInfo);
                        if (room.getRoomUsages() != null && !room.getRoomUsages().isEmpty()) {
                            requestedSchedule.setRoomCapacity(room.getRoomUsages().get(0).getHardCapacity());
                        }
                        requestedSchedule.setRoom(room);
                        requestedSchedule.setRoomCode(room.getRoomCode());
                    }
                } else {
                    requestedSchedule.setRoom(null);
                }
            } else if (StringUtils.isBlank(requestedSchedule.getBuildingCode())) {
                requestedSchedule.setBuilding(null);
                if (StringUtils.isNotBlank(requestedSchedule.getRoomCode())) {
                    GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.roomCode", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_INVALID_BUILDING_ROOM);
                    success = false;
                } else {
                    requestedSchedule.setRoom(null);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return success;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }

        return true;
    }

    /**
     * This method persists a ScheduleRequestSetInfo
     *
     * @param scheduleRequestSetInfo  ScheduleRequestSetInfo
     *
     * @return ScheduleRequestSetInfo
     */
    private ScheduleRequestSetInfo createScheduleRequestSetInfo(ScheduleRequestSetInfo scheduleRequestSetInfo, ContextInfo context) {
        try {
            scheduleRequestSetInfo = CourseOfferingManagementUtil.getSchedulingService()
                    .createScheduleRequestSet(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET,
                            ExamOfferingServiceConstants.REF_OBJECT_URI_EXAM_OFFERING,
                            scheduleRequestSetInfo, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return scheduleRequestSetInfo;
    }

    /**
     * Searches for buildings given a building code.
     * @param buildingCode A building code.
     * @param strictMatch If false do a wildcard search matching at the beginning of the field. Otherwise, match exactly.
     * @return A List of BuildingInfos
     * @throws Exception
     */
    public List<BuildingInfo> retrieveBuildingInfoByCode(String buildingCode, boolean strictMatch) throws Exception {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

        if (!strictMatch){
            buildingCode = StringUtils.upperCase(buildingCode) + "%";
        }
        qbcBuilder.setPredicates(PredicateFactory.like("buildingCode", buildingCode));

        QueryByCriteria criteria = qbcBuilder.build();

        List<BuildingInfo> b = CourseOfferingManagementUtil.getRoomService().searchForBuildings(criteria, createContextInfo());
        return b;
    }

    /**
     * This method constructs a ScheduleRequestSetInfo
     *
     * @param eoId  exam offering id
     *
     * @return ScheduleRequestSetInfo
     */
    private ScheduleRequestSetInfo makeScheduleRequestSetInfo(String eoId) {
        ScheduleRequestSetInfo scheduleRequestSetInfo = new ScheduleRequestSetInfo();
        scheduleRequestSetInfo.setRefObjectTypeKey(ExamOfferingServiceConstants.REF_OBJECT_URI_EXAM_OFFERING);
        scheduleRequestSetInfo.setName(String.format("Exam Offering Schedule Request Set"));
        scheduleRequestSetInfo.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_STATE_CREATED);
        scheduleRequestSetInfo.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET);
        scheduleRequestSetInfo.getRefObjectIds().add(eoId);
        return scheduleRequestSetInfo;
    }

    protected ContextInfo createContextInfo(){
        return ContextUtils.createDefaultContextInfo();
    }

    /**
     * This method valids if a start time is prior to an end time
     *
     * @param requestedSchedule
     * @param examOfferingPath
     *
     * @return ScheduleRequestSetInfo
     */
    private boolean validateTime (ScheduleWrapper requestedSchedule, String examOfferingPath) {
        DateTime startTime = null;
        DateTime endTime = null;
        boolean success = true;

        KSDateTimeFormatter timeFormatter = new KSDateTimeFormatter("hh:mm aa");

        if (StringUtils.isNotBlank(requestedSchedule.getStartTime())) {
            try{
                startTime = timeFormatter.getFormatter().parseDateTime(requestedSchedule.getStartTime());
            } catch (Exception e) {
                GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.startTime", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_INVALID_START_TIME);
                success = false;
            }
        } else {
            GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.startTime", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_REQUIRED_FIELD_EMPTY);
            success = false;
        }

        if (StringUtils.isNotBlank(requestedSchedule.getEndTime())) {
            try{
                endTime = timeFormatter.getFormatter().parseDateTime(requestedSchedule.getEndTime());
            } catch (Exception e) {
                GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.endTime", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_INVALID_END_TIME);
                success = false;
            }
        } else {
            GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.endTime", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_REQUIRED_FIELD_EMPTY);
            success = false;
        }

        if (DateTimeComparator.getInstance().compare(startTime, endTime) > 0 ) {
            GlobalVariables.getMessageMap().putError(examOfferingPath + ".requestedSchedule.startTime", ExamOfferingConstants.EXAM_OFFERING_MSG_ERROR_SCHEDULING_INVALID_START_END_TIME);
            success = false;
        }

        return success;
    }
}
