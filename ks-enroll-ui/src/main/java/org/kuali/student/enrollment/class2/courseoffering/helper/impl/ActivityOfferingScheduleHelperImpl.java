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
 * Created by venkat on 11/30/12
 */
package org.kuali.student.enrollment.class2.courseoffering.helper.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class1.util.WeekDaysDtoAndUIConversions;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ColocatedActivity;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.helper.ActivityOfferingScheduleHelper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
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
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is a helper class for handling all the schedule related logics in Activity Offering Maintenance document.
 *
 * The reason behind having a helper class is, as the scheduling is going to be a complex system and also it
 * involves external scheduler, this would allow the institutions to just refactor/enhance the scheduling logic
 * without affecting the other logic in Activity Offering Maintenance document.
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingScheduleHelperImpl implements ActivityOfferingScheduleHelper {

    private CourseOfferingService courseOfferingService;
    private SchedulingService schedulingService;
    private RoomService roomService;
    private CourseOfferingSetService courseOfferingSetService;

    @Transactional // If it's already a part of transaction, it's ok.. Otherwise, create a new transaction boundary for all the changes.
    public void saveSchedules(ActivityOfferingWrapper wrapper,ContextInfo defaultContextInfo){

        if (defaultContextInfo == null) {
            defaultContextInfo = createContextInfo();
        }

        savePreMSE(wrapper, defaultContextInfo);

        if (wrapper.isSchedulingCompleted()){
            savePostMSE(wrapper, defaultContextInfo);
        }
    }

    public void loadSchedules(ActivityOfferingWrapper wrapper,ContextInfo defaultContextInfo){

        try {
            List<String> socIds = getCourseOfferingSetService().getSocIdsByTerm(wrapper.getAoInfo().getTermId(), defaultContextInfo);

            if (socIds != null && !socIds.isEmpty()){
                if (socIds.size() > 1){
                    throw new RuntimeException("More than one SOC found for a term");
                }
                SocInfo soc = getCourseOfferingSetService().getSoc(socIds.get(0),defaultContextInfo);
                wrapper.setSocInfo(soc);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        loadScheduleRequests(wrapper,defaultContextInfo);
        loadSchedulesAndComponents(wrapper, defaultContextInfo);
    }

    public void savePostMSE(ActivityOfferingWrapper activityOfferingWrapper,ContextInfo defaultContextInfo){

        if (activityOfferingWrapper.isSchedulingCompleted() && !activityOfferingWrapper.isSendRDLsToSchedulerAfterMSE()){
            return;
        }

        try {
            //Schedule AO
            StatusInfo statusInfo = getCourseOfferingService().scheduleActivityOffering(activityOfferingWrapper.getId(), defaultContextInfo);

            if (!statusInfo.getIsSuccess()){
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,statusInfo.getMessage());
                return;
            }

            ActivityOfferingInfo latestAO = getCourseOfferingService().getActivityOffering(activityOfferingWrapper.getAoInfo().getId(), defaultContextInfo);

            //This will change the AO/FO/CO state and gets the updated AO
            latestAO = updateScheduledActivityOffering(latestAO, defaultContextInfo);

            activityOfferingWrapper.setAoInfo(latestAO);

            if (activityOfferingWrapper.isColocatedAO()){
                for (ColocatedActivity colocatedActivity : activityOfferingWrapper.getColocatedActivities()){
                    ActivityOfferingInfo ao = getCourseOfferingService()
                            .getActivityOffering(colocatedActivity.getActivityOfferingInfo().getId(), defaultContextInfo);
                    ActivityOfferingInfo updatedAO = updateScheduledActivityOffering(ao, defaultContextInfo);
                    colocatedActivity.setActivityOfferingInfo(updatedAO);
                    colocatedActivity.setAoId(updatedAO.getId());
                }
            }

            //Set it in the wrapper and load all the revised Schedules and ScheduleComponents
            loadSchedulesAndComponents(activityOfferingWrapper, defaultContextInfo);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addScheduleRequestComponent(ActivityOfferingWrapper activityOfferingWrapper){

        ScheduleWrapper scheduleWrapper = activityOfferingWrapper.getNewScheduleRequest();

        boolean success = validateNewScheduleRequest(scheduleWrapper);

        if (!success){
            return false;
        }

        // Add a space between selected days ("MTWHFSU") for the UI display(read-only) string
        if (StringUtils.isNotBlank(scheduleWrapper.getDays())){
            scheduleWrapper.setDaysUI(scheduleWrapper.getDays().replace("", " ").trim().toUpperCase());
        }

        if (scheduleWrapper.getStartTime() != null) {
            scheduleWrapper.setStartTimeUI(scheduleWrapper.getStartTime() + " " + scheduleWrapper.getStartTimeAMPM());
        }
        if (scheduleWrapper.getEndTime() != null) {
            scheduleWrapper.setEndTimeUI(scheduleWrapper.getEndTime() + " " + scheduleWrapper.getEndTimeAMPM());
        }

        if (StringUtils.isBlank(scheduleWrapper.getRoomCode())){
            scheduleWrapper.setRoom(null);
        }

        activityOfferingWrapper.getRequestedScheduleComponents().add(scheduleWrapper);

        ScheduleWrapper newScheduleWrapper = new ScheduleWrapper();
        for (ColocatedActivity activity : activityOfferingWrapper.getColocatedActivities()) {
            newScheduleWrapper.getColocatedAOs().add(activity.getEditRenderHelper().getCode());
        }

        activityOfferingWrapper.setNewScheduleRequest(newScheduleWrapper);

        return true;
    }

    protected boolean validateNewScheduleRequest(ScheduleWrapper scheduleWrapper){

        GlobalVariables.getMessageMap().clearErrorMessages();

        // validate the weekdays
        if (StringUtils.isNotEmpty(scheduleWrapper.getDays())) {
            String scheduleDays = StringUtils.upperCase(scheduleWrapper.getDays());
            List<Integer> parsedWeekdays = SchedulingServiceUtil.weekdaysString2WeekdaysList(scheduleDays);
            if(parsedWeekdays.isEmpty() || scheduleDays.trim().length() > parsedWeekdays.size()) {
                addErrorMessage(ScheduleInput.WEEKDAYS, "Day characters are invalid");
            }
        }

        // if a room or a building were entered, ensure the building and room code are valid
        try {

            ContextInfo contextInfo = createContextInfo();
            int firstInfo = 0;

            // if a building code exists, validate the building code and populate the building info
            if (StringUtils.isNotBlank(scheduleWrapper.getBuildingCode())){
                List<BuildingInfo> buildings = retrieveBuildingInfo(scheduleWrapper.getBuildingCode(),true);
                if (buildings.isEmpty()) {
                    addErrorMessage(ScheduleInput.BUILDING, "Facility code was invalid");
                } else {
                    scheduleWrapper.setBuilding(buildings.get(firstInfo));
                }

                // if a building code exists and a room code exists, validate the room code and populate the room info
                if (!buildings.isEmpty() && StringUtils.isNotEmpty(scheduleWrapper.getRoomCode())) {
                    List<RoomInfo> rooms = getRoomService().getRoomsByBuildingAndRoomCode(scheduleWrapper.getBuildingCode(), scheduleWrapper.getRoomCode(), contextInfo);
                    if (rooms.isEmpty()) {
                        addErrorMessage(ScheduleInput.ROOM, "Room code was invalid");
                    } else {
                        RoomInfo room = rooms.get(firstInfo);
                        if (room.getRoomUsages() != null && !room.getRoomUsages().isEmpty()) {
                            scheduleWrapper.setRoomCapacity(room.getRoomUsages().get(0).getHardCapacity());
                        }
                        scheduleWrapper.setRoom(room);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return !GlobalVariables.getMessageMap().hasErrors();
    }

    private void checkRequiredScheduleInput(String inputValue, ScheduleInput input) {
        if(StringUtils.isBlank(inputValue)) {
            addErrorMessage(input, input.getRequiredMessage());
        }
    }

    private void addErrorMessage(ScheduleInput input, String message) {
        GlobalVariables.getMessageMap().putError(input.getBeanId(), RiceKeyConstants.ERROR_CUSTOM, message);
    }

    /**
     * Delete Schedule Requests.
     * @param wrappers A list of schedule request wrappers.
     * @param defaultContextInfo
     */
    private void deleteScheduleRequestComponents(List<ScheduleWrapper> wrappers, ContextInfo defaultContextInfo) {
        for (ScheduleWrapper scheduleWrapperDeleted : wrappers) {
            try {
                String scheduleRequestId = scheduleWrapperDeleted.getScheduleRequestInfo().getId();
                StatusInfo statusInfo = getSchedulingService().deleteScheduleRequest(scheduleRequestId, defaultContextInfo);
                if (!statusInfo.getIsSuccess()){
                    throw new OperationFailedException("Cant delete the schedule request " + statusInfo.getMessage());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Make a new ScheduleRequestSetInfo.
     */
    private ScheduleRequestSetInfo makeScheduleRequestSetInfo(String aoId, String courseCode, String aoCode) {
        ScheduleRequestSetInfo scheduleRequestSetInfo = new ScheduleRequestSetInfo();
        scheduleRequestSetInfo.setRefObjectTypeKey(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);
        scheduleRequestSetInfo.setName(String.format("Schedule Request Set for %s - %s", courseCode, aoCode));
        scheduleRequestSetInfo.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_STATE_CREATED);
        scheduleRequestSetInfo.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET);
        scheduleRequestSetInfo.getRefObjectIds().add(aoId);
        return scheduleRequestSetInfo;
    }

    /**
     * Persists a ScheduleRequestSetInfo.
     */
    private ScheduleRequestSetInfo updateScheduleRequestSetInfo(ScheduleRequestSetInfo scheduleRequestSetInfo, ContextInfo context) {
        try {
            scheduleRequestSetInfo = getSchedulingService()
                    .updateScheduleRequestSet(scheduleRequestSetInfo.getId(), scheduleRequestSetInfo, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return scheduleRequestSetInfo;
    }

    /**
     * Persists a new ScheduleRequestSetInfo.
     */
    private ScheduleRequestSetInfo createScheduleRequestSetInfo(ScheduleRequestSetInfo scheduleRequestSetInfo, ContextInfo context) {
        try {
            scheduleRequestSetInfo = getSchedulingService()
                    .createScheduleRequestSet(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET,
                            CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,
                            scheduleRequestSetInfo, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return scheduleRequestSetInfo;
    }

    /**
     * Deletes a ScheduleRequestSetInfo.
     */
    private void deleteScheduleRequestSetInfo(String id, ContextInfo context) {
        try {
            StatusInfo statusInfo = getSchedulingService().deleteScheduleRequestSet(id,context);
            if (!statusInfo.getIsSuccess()){
                throw new OperationFailedException("Cant delete the schedule request set " + statusInfo.getMessage());
            }
                return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void savePreMSE(ActivityOfferingWrapper wrapper, ContextInfo contextInfo) {

        //  Handle deleted schedule requests.
        deleteScheduleRequestComponents(wrapper.getDeletedScheduleComponents(), contextInfo);

        /**
         * If the AO has no ScheduleRequests then delete the Schedule Request Set.
         * (This still misses when the AO is part of a colo set (with multiple AOs) on load) but user
         * deleted all the RDLs and unchecked the colo checkbox. In this case, we should not be deleting the set,
         * just remove the AO from the SRS and save.)
         */
        ScheduleRequestSetInfo scheduleRequestSetInfo = wrapper.getScheduleRequestSetInfo();

        if (wrapper.getRequestedScheduleComponents().isEmpty() && !wrapper.isRemovedFromColoSet()) {
            if (scheduleRequestSetInfo != null && StringUtils.isNotBlank(scheduleRequestSetInfo.getId())){
                deleteScheduleRequestSetInfo(scheduleRequestSetInfo.getId(), contextInfo);
            }
        }

        String aoId = wrapper.getId();
        ActivityOfferingInfo aoInfo =  wrapper.getAoInfo();

        if (wrapper.getScheduleRequestSetInfo() == null){
            scheduleRequestSetInfo = makeScheduleRequestSetInfo(aoId, aoInfo.getCourseOfferingCode(), aoInfo.getActivityCode());
        }

        //  If this is a new ScheduleRequestSet then set the ref object Id to this AO and create it.
        if (StringUtils.isBlank(scheduleRequestSetInfo.getId())){
            if (! scheduleRequestSetInfo.getRefObjectIds().contains(aoId)){
                 scheduleRequestSetInfo.getRefObjectIds().add(aoId);
            }
            scheduleRequestSetInfo = createScheduleRequestSetInfo(scheduleRequestSetInfo, contextInfo);
            wrapper.setScheduleRequestSetInfo(scheduleRequestSetInfo);
        } else {
            //  If the AO is leaving a colocation
            if (wrapper.isPartOfColoSetOnLoadAlready() && !wrapper.isColocatedAO()){
                /*
                 * Remove this AO id from the SRS and see if it's empty. If so, then just reuse this SRS.
                 * Othewise, create a new SRS for this AO.
                 */
                scheduleRequestSetInfo.getRefObjectIds().remove(aoId);

                if (scheduleRequestSetInfo.getRefObjectIds().isEmpty()){
                    scheduleRequestSetInfo.getRefObjectIds().add(aoId);
                    scheduleRequestSetInfo = updateScheduleRequestSetInfo(scheduleRequestSetInfo, contextInfo);
                    wrapper.setScheduleRequestSetInfo(scheduleRequestSetInfo);
                } else {
                    //Save the existing set with the current AO removed from it and create a new sch set for this ao.
                    updateScheduleRequestSetInfo(scheduleRequestSetInfo, contextInfo);

                    //Create new sch set for this ao.
                    ScheduleRequestSetInfo newSrs = makeScheduleRequestSetInfo(aoId, aoInfo.getCourseOfferingCode(), aoInfo.getActivityCode());
                    scheduleRequestSetInfo = createScheduleRequestSetInfo(newSrs, contextInfo);
                    wrapper.setScheduleRequestSetInfo(scheduleRequestSetInfo);

                    //As we're creating new sch set for this ao, we've do the same with sch request and component. So,
                    //we're iterating through all the RDLs and pull out the sch request and component.
                    for (ScheduleWrapper scheduleWrapper : wrapper.getRequestedScheduleComponents()){
                        scheduleWrapper.resetForNewRDL();
                    }
                }
            } else if (wrapper.isColocatedAO()){
                //Just make sure the current ao is added to the sch set.
                if (!scheduleRequestSetInfo.getRefObjectIds().contains(aoId)){
                    scheduleRequestSetInfo.getRefObjectIds().add(aoId);
                }
                scheduleRequestSetInfo = updateScheduleRequestSetInfo(scheduleRequestSetInfo,contextInfo);
                wrapper.setScheduleRequestSetInfo(scheduleRequestSetInfo);
            }
        }

        for (ScheduleWrapper scheduleWrapper : wrapper.getRequestedScheduleComponents()){
            if (!scheduleWrapper.isRequestAlreadySaved()){

                ScheduleRequestInfo scheduleRequest = new ScheduleRequestInfo();
                scheduleRequest.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);
                scheduleRequest.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
                scheduleRequest.setScheduleRequestSetId(wrapper.getScheduleRequestSetInfo().getId());

                try {
                    ScheduleRequestComponentInfo componentInfo = buildScheduleComponentRequest(scheduleWrapper,contextInfo);
                    scheduleRequest.getScheduleRequestComponents().add(componentInfo);

                    ScheduleRequestInfo newScheduleRequest = getSchedulingService().createScheduleRequest(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST,scheduleRequest,contextInfo);
                    scheduleWrapper.setScheduleRequestInfo(newScheduleRequest);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            } else if (scheduleWrapper.isModified()){
                try {
                    ScheduleRequestInfo scheduleRequest = scheduleWrapper.getScheduleRequestInfo();
                    ScheduleRequestComponentInfo componentInfo = buildScheduleComponentRequest(scheduleWrapper,contextInfo);
                    scheduleRequest.getScheduleRequestComponents().clear();
                    scheduleRequest.getScheduleRequestComponents().add(componentInfo);

                    ScheduleRequestInfo newScheduleRequest = getSchedulingService().updateScheduleRequest(scheduleRequest.getId(),scheduleRequest,contextInfo);
                    scheduleWrapper.setScheduleRequestInfo(newScheduleRequest);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private ScheduleRequestComponentInfo buildScheduleComponentRequest(ScheduleWrapper scheduleWrapper,ContextInfo defaultContextInfo) throws Exception{

        ScheduleRequestComponentInfo componentInfo = new ScheduleRequestComponentInfo();
        componentInfo.setIsTBA(scheduleWrapper.isTba());

        componentInfo.getBuildingIds().clear();
        if( scheduleWrapper.getBuilding() != null && scheduleWrapper.getBuilding().getId() != null ) {
            componentInfo.getBuildingIds().add( scheduleWrapper.getBuilding().getId() );
        }

        if(scheduleWrapper.getRoom() != null) {
            List<String> room = new ArrayList<String>();
            room.add(scheduleWrapper.getRoom().getId());
            componentInfo.setRoomIds(room);
        }

        componentInfo.setResourceTypeKeys(scheduleWrapper.getFeatures());

        TimeSlotInfo timeSlot = new TimeSlotInfo();
        timeSlot.setTypeKey(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD);
        timeSlot.setStateKey(SchedulingServiceConstants.TIME_SLOT_STATE_ACTIVE);
        List<Integer> days = WeekDaysDtoAndUIConversions.buildDaysForDTO(scheduleWrapper.getDays());
        timeSlot.setWeekdays(days);

        if (StringUtils.isNotEmpty(scheduleWrapper.getStartTime())) {
            long time = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(scheduleWrapper.getStartTime() + " " + scheduleWrapper.getStartTimeAMPM()).getTime();
            TimeOfDayInfo timeOfDayInfo = new TimeOfDayInfo();
            timeOfDayInfo.setMilliSeconds(time);
            timeSlot.setStartTime(timeOfDayInfo);
        }

        if (StringUtils.isNotEmpty(scheduleWrapper.getEndTime())) {
            long time = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(scheduleWrapper.getEndTime() + " " + scheduleWrapper.getEndTimeAMPM()).getTime();
            TimeOfDayInfo timeOfDayInfo = new TimeOfDayInfo();
            timeOfDayInfo.setMilliSeconds(time);
            timeSlot.setEndTime(timeOfDayInfo);
        }

        try {
            TimeSlotInfo createdTimeSlot = getSchedulingService().createTimeSlot(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD,timeSlot, defaultContextInfo);
            componentInfo.getTimeSlotIds().add(createdTimeSlot.getId());
        } catch (Exception e) {
            throw new Exception("Error creating timeslot: " + timeSlot, e);
        }

        return componentInfo;
    }

    /**
     * This method loads the schedule requests/components.
     * Support multiple schedule requests
     *
     * @param wrapper  ActivityOfferingWrapper
     */
    public void loadScheduleRequests(ActivityOfferingWrapper wrapper,ContextInfo defaultContextInfo){

        try {

            List<ScheduleRequestInfo> scheduleRequestInfos = getSchedulingService().getScheduleRequestsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, wrapper.getId(), defaultContextInfo);

            //For Full colo, there must be only one ScheduleRequestSet.
            if (!scheduleRequestInfos.isEmpty()){
                int firstScheduleRequestInfo = 0;
                ScheduleRequestSetInfo set = getSchedulingService().getScheduleRequestSet(scheduleRequestInfos.get(firstScheduleRequestInfo).getScheduleRequestSetId(),defaultContextInfo);
                wrapper.setScheduleRequestSetInfo(set);
            }

            for (ScheduleRequestInfo scheduleRequestInfo : scheduleRequestInfos){
                for (ScheduleRequestComponentInfo componentInfo : scheduleRequestInfo.getScheduleRequestComponents()) {

                    /**
                     * If RDLs exists, dont allow the user to change the crosslist checkbox
                     */
                    wrapper.getEditRenderHelper().setPersistedRDLsExists(true);

                    ScheduleWrapper scheduleWrapper = new ScheduleWrapper(scheduleRequestInfo,componentInfo);
                    buildScheduleWrapper(wrapper,scheduleWrapper,componentInfo,defaultContextInfo);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void buildScheduleWrapper(ActivityOfferingWrapper wrapper,ScheduleWrapper scheduleWrapper,ScheduleRequestComponentInfo componentInfo,ContextInfo defaultContextInfo){

        scheduleWrapper.setTba(componentInfo.getIsTBA());

        try {
            List<TimeSlotInfo> timeSlotInfos = getSchedulingService().getTimeSlotsByIds(componentInfo.getTimeSlotIds(), defaultContextInfo);

            if (!timeSlotInfos.isEmpty()){
                int firstTimeSlotInfo = 0;
                scheduleWrapper.setTimeSlot(timeSlotInfos.get(firstTimeSlotInfo));

                Date timeForDisplay;
                if(scheduleWrapper.getTimeSlot().getStartTime().getMilliSeconds() != null) {
                    timeForDisplay = new Date(scheduleWrapper.getTimeSlot().getStartTime().getMilliSeconds());
                    String formattedTime = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay);
                    //Set for read only display purpose in the format hh:mm a
                    scheduleWrapper.setStartTimeUI(formattedTime);
                    //Set only hh:mm for user editable purpose
                    scheduleWrapper.setStartTime(StringUtils.substringBefore(formattedTime," "));
                    scheduleWrapper.setStartTimeAMPM(StringUtils.substringAfter(formattedTime," "));
                }

                if(scheduleWrapper.getTimeSlot().getEndTime().getMilliSeconds() != null) {
                    timeForDisplay = new Date(scheduleWrapper.getTimeSlot().getEndTime().getMilliSeconds());
                    String formattedTime = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay);
                    //Set for read only display purpose in the format hh:mm a
                    scheduleWrapper.setEndTimeUI(formattedTime);
                    //Set only hh:mm for user editable purpose
                    scheduleWrapper.setEndTime(StringUtils.substringBefore(formattedTime," "));
                    scheduleWrapper.setEndTimeAMPM(StringUtils.substringAfter(formattedTime," "));
                }

                String daysUI = WeekDaysDtoAndUIConversions.buildDaysForUI(scheduleWrapper.getTimeSlot().getWeekdays());
                scheduleWrapper.setDaysUI(daysUI);
                scheduleWrapper.setDays(StringUtils.remove(daysUI, " "));
            }

            if (!componentInfo.getRoomIds().isEmpty()){

                RoomInfo room = getRoomService().getRoom(componentInfo.getRoomIds().get(0), defaultContextInfo);

                scheduleWrapper.setRoom(room);
                scheduleWrapper.setRoomCode(room.getRoomCode());

                if (!room.getRoomUsages().isEmpty()){
                    scheduleWrapper.setRoomCapacity(room.getRoomUsages().get(0).getHardCapacity());
                }

                BuildingInfo buildingInfo = getRoomService().getBuilding(room.getBuildingId(), defaultContextInfo);
                scheduleWrapper.setBuilding(buildingInfo);
                scheduleWrapper.setBuildingCode(buildingInfo.getBuildingCode());
                scheduleWrapper.setBuildingId(room.getBuildingId());
            } else if (!componentInfo.getBuildingIds().isEmpty()){
                String buildingId = componentInfo.getBuildingIds().get(0);
                BuildingInfo buildingInfo = getRoomService().getBuilding(buildingId, defaultContextInfo);
                scheduleWrapper.setBuilding(buildingInfo);
                scheduleWrapper.setBuildingCode(buildingInfo.getBuildingCode());
                scheduleWrapper.setBuildingId(buildingId);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        loadColocatedAOs(wrapper,scheduleWrapper);
        wrapper.getRequestedScheduleComponents().add(scheduleWrapper);
    }

    protected void loadColocatedAOs(ActivityOfferingWrapper wrapper,ScheduleWrapper scheduleWrapper){
        if (wrapper.isColocatedAO()){
            for (ColocatedActivity activity : wrapper.getColocatedActivities()){
                scheduleWrapper.getColocatedAOs().add(activity.getEditRenderHelper().getCode());
            }
        }
    }

    public void loadSchedulesAndComponents(ActivityOfferingWrapper wrapper, ContextInfo defaultContextInfo){

        if (wrapper.getAoInfo().getScheduleIds() != null) {
            try {
                List<ScheduleInfo> scheduleInfos = getSchedulingService().getSchedulesByIds(wrapper.getAoInfo().getScheduleIds(), defaultContextInfo);

                for (ScheduleInfo scheduleInfo : scheduleInfos) {
                    /**
                     * Until we implement external scheduler, there is going to be only one Schedule component for every scheduleinfo
                     * and the UI doesn't allow us to add multiple components to a schedule request.
                     */
                    for (ScheduleComponentInfo componentInfo : scheduleInfo.getScheduleComponents()) {
                        ScheduleWrapper scheduleWrapper = new ScheduleWrapper(scheduleInfo,componentInfo);
                        scheduleWrapper.setTba(componentInfo.getIsTBA());

                        List<TimeSlotInfo> timeSlotInfos = getSchedulingService().getTimeSlotsByIds(componentInfo.getTimeSlotIds(), defaultContextInfo);

                        if (!timeSlotInfos.isEmpty()){
                            int firstTimeSlotInfo = 0;
                            scheduleWrapper.setTimeSlot(timeSlotInfos.get(firstTimeSlotInfo));

                            Date timeForDisplay;
                            if (scheduleWrapper.getTimeSlot().getStartTime().getMilliSeconds() != null){
                                timeForDisplay = new Date(scheduleWrapper.getTimeSlot().getStartTime().getMilliSeconds());
                                scheduleWrapper.setStartTimeUI(DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay));
                            }

                            if (scheduleWrapper.getTimeSlot().getEndTime().getMilliSeconds() != null){
                                timeForDisplay = new Date(scheduleWrapper.getTimeSlot().getEndTime().getMilliSeconds());
                                scheduleWrapper.setEndTimeUI(DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay));
                            }

                            scheduleWrapper.setDaysUI(WeekDaysDtoAndUIConversions.buildDaysForUI(scheduleWrapper.getTimeSlot().getWeekdays()));
                        }

                        if (StringUtils.isNotBlank(componentInfo.getRoomId())){

                            RoomInfo room = getRoomService().getRoom(componentInfo.getRoomId(), defaultContextInfo);

                            scheduleWrapper.setRoom(room);
                            scheduleWrapper.setRoomCode(room.getRoomCode());

                            if (!room.getRoomUsages().isEmpty()){
                                scheduleWrapper.setRoomCapacity(room.getRoomUsages().get(0).getHardCapacity());
                            }

                            BuildingInfo buildingInfo = getRoomService().getBuilding(room.getBuildingId(), defaultContextInfo);
                            scheduleWrapper.setBuilding(buildingInfo);
                            scheduleWrapper.setBuildingCode(buildingInfo.getBuildingCode());
                        }

                        loadColocatedAOs(wrapper,scheduleWrapper);
                        wrapper.getActualScheduleComponents().add(scheduleWrapper);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected ActivityOfferingInfo updateScheduledActivityOffering(ActivityOfferingInfo activityOfferingInfo,ContextInfo context)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {

        // Keep track of the state before any changes, to avoid extra processing if the AO state does not change
        String aoCurrentState = activityOfferingInfo.getStateKey();

        // Find the term-level SOC for this activity offering and find out its state
        List<String> socIds = getCourseOfferingSetService().getSocIdsByTerm(activityOfferingInfo.getTermId(), context);

        int firstsocId = 0;

        // should be only one, if none or more than one is found, throw an exception
        if (socIds == null || socIds.size() != 1) {
            throw new OperationFailedException("Unexpected results from socService.getSocIdsByTerm, expecting exactly one soc id, received: " + socIds);
        }

        SocInfo socInfo = getCourseOfferingSetService().getSoc(socIds.get(firstsocId), context);

        String aoNextState = null;

        // If the SOC is in Final Edits state, and the Scheduled Activity Offering is in Draft state, set the Activity Offering state to Approved
        if (socInfo.getStateKey().equals(CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY)) {
            if (LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY.equals(activityOfferingInfo.getStateKey())) {
                aoNextState = LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY;
            }
        }
        // If the SOC is in Final Edits state, and the Scheduled Activity Offering is in Draft state, AND the Activity Offering is Scheduled, then set the Activity Offering State to Offered
        else if (socInfo.getStateKey().equals(CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY)) {
            if (LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY.equals(activityOfferingInfo.getStateKey())) {
                if(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY.equals(activityOfferingInfo.getSchedulingStateKey())) {
                    aoNextState = LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY;
                }
            }
        }

        if (StringUtils.equals(aoCurrentState, aoNextState)) {
            return activityOfferingInfo;
        }
        else {
            StatusInfo statusInfo = getCourseOfferingService().changeActivityOfferingState(activityOfferingInfo.getId(), aoNextState, context);
            if (!statusInfo.getIsSuccess()){
                throw new OperationFailedException("Error updating Activity offering state to " + aoNextState + " " + statusInfo);
            }
            return getCourseOfferingService().getActivityOffering(activityOfferingInfo.getId(), context);
        }
    }

    public SchedulingService getSchedulingService() {
        if(schedulingService == null){
            schedulingService =  CourseOfferingResourceLoader.loadSchedulingService();
        }
        return schedulingService;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }

    public RoomService getRoomService(){
        if (roomService == null){
            roomService = CourseOfferingResourceLoader.loadRoomService();
        }
        return roomService;
    }

    protected CourseOfferingSetService getCourseOfferingSetService(){
        if (courseOfferingSetService == null){
            courseOfferingSetService = CourseOfferingResourceLoader.loadCourseOfferingSetService();
        }
        return courseOfferingSetService;
    }

    private enum ScheduleInput {
        WEEKDAYS("document.newMaintainableObject.dataObject.newScheduleRequest.days", "Day(s) are required"),
        START_TIME("document.newMaintainableObject.dataObject.newScheduleRequest.startTime", "Start time is required"),
        START_TIME_AMPM("document.newMaintainableObject.dataObject.newScheduleRequest.startTimeAMPM", "Start time AM/PM is required"),
        END_TIME("document.newMaintainableObject.dataObject.newScheduleRequest.endTime", "End time is required"),
        END_TIME_AMPM("document.newMaintainableObject.dataObject.newScheduleRequest.endTimeAMPM", "End time AM/PM is required"),
        BUILDING("document.newMaintainableObject.dataObject.newScheduleRequest.buildingCode", "Facility is required"),
        ROOM("document.newMaintainableObject.dataObject.newScheduleRequest.roomCode", "Room is required");

        private final String beanId;
        private final String requiredMessage;

        private ScheduleInput(String beanId, String requiredMessage) {
            this.beanId = beanId;
            this.requiredMessage = requiredMessage;
        }

        public String getBeanId() {
            return beanId;
        }

        public String getRequiredMessage() {
            return requiredMessage;
        }
    }

    public void deleteRequestedAndActualSchedules(ScheduleRequestSetInfo schSet,String activityId,List<String> deleteScheduleIds,ContextInfo defaultContextInfo){
        try {
            List<ScheduleRequestInfo> rdls = getSchedulingService().getScheduleRequestsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, activityId, createContextInfo());
            for (ScheduleRequestInfo rdl : rdls) {
                if (!StringUtils.equals(rdl.getScheduleRequestSetId(),schSet.getId())){
                    //Util we implement partial colo, there is going to be only one sch set
                    StatusInfo status = getSchedulingService().deleteScheduleRequestSet(rdl.getScheduleRequestSetId(), defaultContextInfo);
                    if (!status.getIsSuccess()){
                         throw new RuntimeException("Cant delete RDL");
                    }
                    break;
                }
            }
            for (String schId : deleteScheduleIds){
                 StatusInfo status = getSchedulingService().deleteSchedule(schId,ContextUtils.createDefaultContextInfo());
                 if (!status.getIsSuccess()){
                     throw new RuntimeException("Cant delete RDL" + status.getMessage());
                 }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    protected ContextInfo createContextInfo(){
        return ContextUtils.createDefaultContextInfo();
    }


    public List<BuildingInfo> retrieveBuildingInfo(String buildingCode,boolean strictMatch) throws Exception{

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        if (!strictMatch){
            buildingCode = StringUtils.upperCase(buildingCode) + "%";
        }
        qbcBuilder.setPredicates(PredicateFactory.like("buildingCode", buildingCode));

        QueryByCriteria criteria = qbcBuilder.build();

        List<BuildingInfo> b = getRoomService().searchForBuildings(criteria, createContextInfo());
        return b;
    }
}
