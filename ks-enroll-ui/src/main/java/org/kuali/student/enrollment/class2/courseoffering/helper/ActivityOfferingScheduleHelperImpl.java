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
package org.kuali.student.enrollment.class2.courseoffering.helper;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ColocatedActivity;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.helper.infc.ActivityOfferingScheduleHelper;
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
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.*;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This is a helper class for handling all the schedule related logics in Activity Offering Maintenance document.
 *
 * The reason behind having a helper class is, as the scheduling is going to be a complex system and also it
 * involves external scheduler, this would allow the institutions to just refactor/enhance the scheduling logic
 * without affecting the other logics in Activity Offering Maintenance document.
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingScheduleHelperImpl implements ActivityOfferingScheduleHelper{

    private CourseOfferingService courseOfferingService;
    private SchedulingService schedulingService;
    private RoomService roomService;
    private CourseOfferingSetService courseOfferingSetService;

    protected static final String TIME_FORMAT_STRING = "hh:mm a";

    @Transactional // If it's already a part of transaction, it's ok.. Otherwise, create a new transaction boundary for all the changes.
    public void saveSchedules(ActivityOfferingWrapper wrapper,ContextInfo defaultContextInfo){

        if (defaultContextInfo == null){
            defaultContextInfo = createContextInfo();
        }

        if (wrapper.isSchedulingCompleted()){
            processRevisedSchedules(wrapper,defaultContextInfo);
        } else {
            createOrUpdateScheduleRequests(wrapper,defaultContextInfo);
        }

    }

    public void loadSchedules(ActivityOfferingWrapper wrapper,ContextInfo defaultContextInfo){

        try{
            List<String> socIds = getCourseOfferingSetService().getSocIdsByTerm(wrapper.getAoInfo().getTermId(), defaultContextInfo);

            if (socIds != null && !socIds.isEmpty()){
                //For M5, it should have only one SOC
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
        loadScheduleActuals(wrapper,defaultContextInfo);

    }

    public void processRevisedSchedules(ActivityOfferingWrapper activityOfferingWrapper,ContextInfo defaultContextInfo){

        createOrUpdateScheduleRequests(activityOfferingWrapper,defaultContextInfo);

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
            //Copy only certain fields to the existing DTO to avoid unnecessary overwriting to the user modifications
//            activityOfferingWrapper.getAoInfo().setStateKey(latestAO.getStateKey());
//            activityOfferingWrapper.getAoInfo().setScheduleId(latestAO.getScheduleId());
//            activityOfferingWrapper.getAoInfo().setSchedulingStateKey(latestAO.getSchedulingStateKey());

            if (activityOfferingWrapper.isColocatedAO()){
                for (ColocatedActivity colocatedActivity : activityOfferingWrapper.getColocatedActivities()){
                    ActivityOfferingInfo ao = getCourseOfferingService().getActivityOffering(colocatedActivity.getActivityOfferingInfo().getId(),defaultContextInfo);
                    ActivityOfferingInfo updatedAO = updateScheduledActivityOffering(ao, defaultContextInfo);
                    updatedAO.setStateKey(latestAO.getStateKey());
//    TODOSSR                updatedAO.setScheduleId(latestAO.getScheduleId());
                    updatedAO.setSchedulingStateKey(latestAO.getSchedulingStateKey());
                    colocatedActivity.setActivityOfferingInfo(updatedAO);
                    colocatedActivity.setAoId(updatedAO.getId());
                }
            }

            //Set it in the wrapper and load all the revised schedule Actuals
            loadScheduleActuals(activityOfferingWrapper,defaultContextInfo);

        }catch (Exception e){
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

        if (scheduleWrapper.isTba()) {
            //  TBA requests must have at least one field blank.
            boolean isOneEmpty = false;
            List<String> values = Arrays.asList(
                    scheduleWrapper.getDays(),
                    scheduleWrapper.getStartTime(),
                    scheduleWrapper.getEndTime(),
                    scheduleWrapper.getBuildingCode(),
                    scheduleWrapper.getRoomCode());

            for (String s : values) {
                if (StringUtils.isEmpty(s)) {
                    isOneEmpty = true;
                    break;
                }
            }

            if ( ! isOneEmpty) {
                addErrorMessage(ScheduleInput.WEEKDAYS, "TBA requests must have at least one blank field.");
            }
            else {

                // AM/PM Fields are required if the time is entered
                if (StringUtils.isNotEmpty(scheduleWrapper.getStartTime())) {
                    checkRequiredScheduleInput(scheduleWrapper.getStartTimeAMPM(), ScheduleInput.START_TIME_AMPM);
                }

                //If am/pm selected, then time is required
                if (StringUtils.isNotEmpty(scheduleWrapper.getStartTimeAMPM())) {
                    checkRequiredScheduleInput(scheduleWrapper.getStartTime(), ScheduleInput.START_TIME);
                }

                if(StringUtils.isNotEmpty(scheduleWrapper.getEndTime())) {
                    checkRequiredScheduleInput(scheduleWrapper.getEndTimeAMPM(), ScheduleInput.END_TIME_AMPM);
                }

                //If am/pm selected, then time is required
                if(StringUtils.isNotEmpty(scheduleWrapper.getEndTimeAMPM())) {
                    checkRequiredScheduleInput(scheduleWrapper.getEndTime(), ScheduleInput.END_TIME);
                }
            }
        }
        else {
            // all fields are required to at least have a value
            checkRequiredScheduleInput(scheduleWrapper.getDays(), ScheduleInput.WEEKDAYS);
            checkRequiredScheduleInput(scheduleWrapper.getStartTime(), ScheduleInput.START_TIME);
            checkRequiredScheduleInput(scheduleWrapper.getStartTimeAMPM(), ScheduleInput.START_TIME_AMPM);
            checkRequiredScheduleInput(scheduleWrapper.getEndTime(), ScheduleInput.END_TIME);
            checkRequiredScheduleInput(scheduleWrapper.getEndTimeAMPM(), ScheduleInput.END_TIME_AMPM);
            checkRequiredScheduleInput(scheduleWrapper.getBuildingCode(), ScheduleInput.BUILDING);
            checkRequiredScheduleInput(scheduleWrapper.getRoomCode(), ScheduleInput.ROOM);

        }

        // The following validations should apply to both TBA and non-TBA schedule requests

        // if there are no other errors
        if(!GlobalVariables.getMessageMap().hasErrors()) {
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

                // if the building code is empty, but the room code is not, display an error
                if (StringUtils.isEmpty(scheduleWrapper.getBuildingCode())) {
                    if (StringUtils.isNotEmpty(scheduleWrapper.getRoomCode())) {
                        addErrorMessage(ScheduleInput.BUILDING, "A Facility code is required if a room code is entered");
                    }
                } else {
                    ContextInfo contextInfo = createContextInfo();

                    // if a building code exists, validate the building code and populate the building info
                    List<BuildingInfo> buildings = getRoomService().getBuildingsByBuildingCode(scheduleWrapper.getBuildingCode(), contextInfo);
                    if (buildings.isEmpty()) {
                        addErrorMessage(ScheduleInput.BUILDING, "Facility code was invalid");
                    } else {
                        scheduleWrapper.setBuilding(buildings.get(0));
                    }

                    // if a building code exists and a room code exists, validate the room code and populate the room info
                    if (StringUtils.isNotEmpty(scheduleWrapper.getRoomCode())) {
                        List<RoomInfo> rooms = getRoomService().getRoomsByBuildingAndRoomCode(scheduleWrapper.getBuildingCode(), scheduleWrapper.getRoomCode(), contextInfo);
                        if (rooms.isEmpty()) {
                            addErrorMessage(ScheduleInput.ROOM, "Room code was invalid");
                        } else {
                            RoomInfo room = rooms.get(0);
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

    /*protected boolean deleteScheduleRequest(ActivityOfferingWrapper wrapper){
        StatusInfo statusInfo;
        try{
            statusInfo = getSchedulingService().deleteScheduleRequest(wrapper.getScheduleRequestInfo().getId(), createContextInfo());
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        if (!statusInfo.getIsSuccess()){
            throw new RuntimeException("Error deleting the schedule request - " + statusInfo.getMessage());
        } else {
            return statusInfo.getIsSuccess();
        }
    }*/

    /*protected void buildScheduleRequestInfo(ActivityOfferingWrapper wrapper){

        ScheduleRequestInfo scheduleRequest = wrapper.getScheduleRequestInfo();

        if (scheduleRequest == null){
            scheduleRequest = new ScheduleRequestInfo();
        }

        *//*TODOSSR if (wrapper.isColocatedAO()){
            scheduleRequest.setRefObjectId(wrapper.getColocatedOfferingSetInfo().getId());
            scheduleRequest.setRefObjectTypeKey(LuiServiceConstants.LUI_SET_COLOCATED_OFFERING_TYPE_KEY);
            scheduleRequest.setName("Schedule request for the Coloset " + wrapper.getColocatedOfferingSetInfo().getId());
        } else {
            scheduleRequest.setRefObjectId(wrapper.getAoInfo().getId());
            scheduleRequest.setRefObjectTypeKey(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);
            scheduleRequest.setName("Schedule request for " + wrapper.getAoInfo().getCourseOfferingCode() + " - " + wrapper.getAoInfo().getActivityCode());
        }*//*

        scheduleRequest.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);
        scheduleRequest.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
        wrapper.setScheduleRequestInfo(scheduleRequest);
    }*/

    public void createOrUpdateScheduleRequests(ActivityOfferingWrapper wrapper,ContextInfo defaultContextInfo) {

        /*
         1. Handle all the deleted RDLs first
         */
        for (ScheduleWrapper scheduleWrapperDeleted : wrapper.getDeletedScheduleComponents()){
            try {
                getSchedulingService().deleteScheduleRequest(scheduleWrapperDeleted.getScheduleRequestInfo().getId(),defaultContextInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        ScheduleRequestSetInfo set = wrapper.getScheduleRequestSetInfo();

        /**
         * If there are no Schedule requests available for an AO, just delete the Schedule Request Set
         */
        if (wrapper.getRequestedScheduleComponents().isEmpty()){
            if (set != null && StringUtils.isNotBlank(set.getId())){
                try {
                    StatusInfo statusInfo = getSchedulingService().deleteScheduleRequestSet(set.getId(),defaultContextInfo);
                    if (!statusInfo.getIsSuccess()){
                        throw new OperationFailedException("Cant delete the schedule request set " + statusInfo.getMessage());
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        for (ScheduleWrapper scheduleWrapper : wrapper.getRequestedScheduleComponents()){
            if (!scheduleWrapper.isRequestAlreadySaved()){


                if (set == null){
                    set = new ScheduleRequestSetInfo();
                    set.setRefObjectTypeKey(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);
                    set.setName("Schedule request set for " + wrapper.getAoInfo().getCourseOfferingCode() + " - " + wrapper.getAoInfo().getActivityCode());
                    set.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_STATE_CREATED);
                    set.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET);
                    set.getRefObjectIds().add(wrapper.getId());
                    try {
                        set = getSchedulingService().createScheduleRequestSet(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET,CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,set,defaultContextInfo);
                        wrapper.setScheduleRequestSetInfo(set);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                ScheduleRequestInfo scheduleRequest = new ScheduleRequestInfo();
                scheduleRequest.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);
                scheduleRequest.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
                scheduleRequest.setScheduleRequestSetId(set.getId());

                try {
                    ScheduleRequestComponentInfo componentInfo = buildScheduleComponentRequest(scheduleWrapper,defaultContextInfo);
                    scheduleRequest.getScheduleRequestComponents().add(componentInfo);

                    ScheduleRequestInfo newScheduleRequest = getSchedulingService().createScheduleRequest(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST,scheduleRequest,defaultContextInfo);
                    scheduleWrapper.setScheduleRequestInfo(newScheduleRequest);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }

            /*if (scheduleWrapper.getScheduleRequestInfo() != null && StringUtils.isNotBlank(scheduleWrapper.getScheduleRequestInfo().getId())){
                if (wrapper.isPartOfColoSetOnLoadAlready()){
                    if (!wrapper.isColocatedAO()){
                        //If there are no activities associated with the colo, then delete the RDL for colo
                        if (wrapper.getColocatedOfferingSetInfo().getActivityOfferingIds().isEmpty()){
                            StatusInfo statusInfo = getSchedulingService().deleteScheduleRequest(wrapper.getScheduleRequestInfo().getId(),defaultContextInfo);
                            if (!statusInfo.getIsSuccess()){
                                 throw new RuntimeException("Error deleting RDLs for the coloset");
                            }
                        }
                        //create new RDL for this AO and copy Colo RDL data to create new RDL for this AO
                        wrapper.setScheduleRequestInfo(new ScheduleRequestInfo());
                        createScheduleComponent = true;
                    }
                } else {
                    if (wrapper.isColocatedAO()){
                        //Should not be the problem. We just need to change the RDLs to point to Colo instead of AO, which is
                        // already happening at buildScheduleRequestInfo(wrapper)
                    }
                }*/


        }

        //If there are no already persisted RDLs and no schedule request components exists, skip creating an empty RDL
        /*if ((wrapper.getScheduleRequestInfo() == null || StringUtils.isBlank(wrapper.getScheduleRequestInfo().getId())) &&
            wrapper.getRequestedScheduleComponents().isEmpty()){
            return;
        }

        boolean createScheduleComponent = false;

       *//**
         * This block should cover these scenarios.
         * 1. When the user opens an AO and it was not colocated on load and then user decides to change the AO to colocated
         *        In this case, If there are RDLs already exists, change those to be a crosslisted RDLs
         *  2. When the user opens a cross listed AO and then user decides to change it to non cross listed AO
         *        For M6, this should not be the problem as user has to delete all the RDLs first before uncheck
         *//*
        try{
            if (wrapper.getScheduleRequestInfo() != null && StringUtils.isNotBlank(wrapper.getScheduleRequestInfo().getId())){
                if (wrapper.isPartOfColoSetOnLoadAlready()){
                    if (!wrapper.isColocatedAO()){
                        //If there are no activities associated with the colo, then delete the RDL for colo
                        *//*TODOSSR if (wrapper.getColocatedOfferingSetInfo().getActivityOfferingIds().isEmpty()){
                            StatusInfo statusInfo = getSchedulingService().deleteScheduleRequest(wrapper.getScheduleRequestInfo().getId(),defaultContextInfo);
                            if (!statusInfo.getIsSuccess()){
                                 throw new RuntimeException("Error deleting RDLs for the coloset");
                            }
                        }*//*
                        //create new RDL for this AO and copy Colo RDL data to create new RDL for this AO
                        wrapper.setScheduleRequestInfo(new ScheduleRequestInfo());
                        createScheduleComponent = true;
                    }
                } else {
                    if (wrapper.isColocatedAO()){
                        //Should not be the problem. We just need to change the RDLs to point to Colo instead of AO, which is
                        // already happening at buildScheduleRequestInfo(wrapper)
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        buildScheduleRequestInfo(wrapper);

        *//**
         * After MSE, user can add RDLs but not needed to convert those RLDs to ADLs immediately.
         * We have a check box at the ui. If user selects that, we can convert to ADL.
         * Otherwise, we should just store the RDL and dont send it to scheduler.
         *
         * NOT FOR M6. NEEDED WHEN WORKING ON PARTIAL COLO
         *//*
        *//*if (wrapper.isSchedulingCompleted()){
            if (wrapper.getEditRenderHelper().isRDLsChanged()){
                AttributeInfo scheduleAttr = null;
                for (AttributeInfo attr : wrapper.getScheduleRequestInfo().getAttributes()){
                    if (StringUtils.equals(attr.getKey(),"is.send.rdls.to.scheduler")){
                        scheduleAttr = attr;
                        break;
                    }
                }
                if (scheduleAttr == null){
                    scheduleAttr = new AttributeInfo();
                    scheduleAttr.setKey("is.send.rdls.to.scheduler");
                    wrapper.getScheduleRequestInfo().getAttributes().add(scheduleAttr);
                }
                if (wrapper.getEditRenderHelper().isPersistRDLInPublishedSOC()){
                    scheduleAttr.setValue(Boolean.FALSE.toString());
                } else {
                    scheduleAttr.setValue(Boolean.TRUE.toString());
                }
            }
        }*//*

        wrapper.getScheduleRequestInfo().getScheduleRequestComponents().clear();

        for (ScheduleWrapper scheduleWrapper : wrapper.getRequestedScheduleComponents()) {
            if (!scheduleWrapper.isRequestAlreadySaved() || createScheduleComponent){
                try{
                    ScheduleRequestComponentInfo componentInfo = buildScheduleComponentRequest(scheduleWrapper,defaultContextInfo);
                    wrapper.getScheduleRequestInfo().getScheduleRequestComponents().add(componentInfo);
                }catch (Exception ex){
                    throw new RuntimeException("Unable to buildScheduleComponentRequest for AO[" + wrapper.getId() + "]", ex);
                }
            }
            else {
                wrapper.getScheduleRequestInfo().getScheduleRequestComponents().add(scheduleWrapper.getScheduleRequestComponentInfo());
            }
        }

        if (StringUtils.isBlank(wrapper.getScheduleRequestInfo().getId())){

            try{
                ScheduleRequestInfo createdScheduleRequestInfo = getSchedulingService().createScheduleRequest(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST,wrapper.getScheduleRequestInfo(), defaultContextInfo);
                wrapper.setScheduleRequestInfo(createdScheduleRequestInfo);
            } catch (Exception e){
                throw new RuntimeException(e);
            }

        } else {
            ScheduleRequestInfo requestInfo = wrapper.getScheduleRequestInfo();

            try{
               ScheduleRequestInfo updatedScheduleRequestInfo = getSchedulingService().updateScheduleRequest(requestInfo.getId(),requestInfo, defaultContextInfo);
               wrapper.setScheduleRequestInfo(updatedScheduleRequestInfo);
            } catch (Exception e){
                throw new RuntimeException(e);
            }

        }*/

    }

    private ScheduleRequestComponentInfo buildScheduleComponentRequest(ScheduleWrapper scheduleWrapper,ContextInfo defaultContextInfo) throws Exception{

        ScheduleRequestComponentInfo componentInfo = new ScheduleRequestComponentInfo();
//        componentInfo.setId(UUIDHelper.genStringUUID());
        componentInfo.setIsTBA(scheduleWrapper.isTba());

        if(scheduleWrapper.getRoom() != null) {
            List<String> room = new ArrayList<>();
            room.add(scheduleWrapper.getRoom().getId());
            componentInfo.setRoomIds(room);
        }

        componentInfo.setResourceTypeKeys(scheduleWrapper.getFeatures());

        TimeSlotInfo timeSlot = new TimeSlotInfo();
        timeSlot.setTypeKey(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING);
        timeSlot.setStateKey(SchedulingServiceConstants.TIME_SLOT_STATE_ACTIVE);
        List<Integer> days = buildDaysForDTO(scheduleWrapper.getDays());
        timeSlot.setWeekdays(days);

        DateFormat df = new SimpleDateFormat(TIME_FORMAT_STRING);

        if (StringUtils.isNotEmpty(scheduleWrapper.getStartTime())) {
            try {
                long time = df.parse(scheduleWrapper.getStartTime() + " " + scheduleWrapper.getStartTimeAMPM()).getTime();
                TimeOfDayInfo timeOfDayInfo = new TimeOfDayInfo();
                timeOfDayInfo.setMilliSeconds(time);
                timeSlot.setStartTime(timeOfDayInfo);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        if (StringUtils.isNotEmpty(scheduleWrapper.getEndTime())) {
            try {
                long time = df.parse(scheduleWrapper.getEndTime() + " " + scheduleWrapper.getEndTimeAMPM()).getTime();
                TimeOfDayInfo timeOfDayInfo = new TimeOfDayInfo();
                timeOfDayInfo.setMilliSeconds(time);
                timeSlot.setEndTime(timeOfDayInfo);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            TimeSlotInfo createdTimeSlot = getSchedulingService().createTimeSlot(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING,timeSlot, defaultContextInfo);
            componentInfo.getTimeSlotIds().add(createdTimeSlot.getId());
        } catch (Exception e) {
            throw new Exception("Error creating timeslot: " + timeSlot, e);
        }

        return componentInfo;
    }

    private List<Integer> buildDaysForDTO(String days){

        List<Integer> weekdays  = new ArrayList<>();

        if(days != null) {

            if (StringUtils.containsIgnoreCase(days,"M")){
                weekdays.add(Calendar.MONDAY);
            }

            if (StringUtils.containsIgnoreCase(days,"T")){
                weekdays.add(Calendar.TUESDAY);
            }

            if (StringUtils.containsIgnoreCase(days,"W")){
                weekdays.add(Calendar.WEDNESDAY);
            }

            if (StringUtils.containsIgnoreCase(days,"H")){
                weekdays.add(Calendar.THURSDAY);
            }

            if (StringUtils.containsIgnoreCase(days,"F")){
                weekdays.add(Calendar.FRIDAY);
            }

            if (StringUtils.containsIgnoreCase(days,"S")){
                weekdays.add(Calendar.SATURDAY);
            }

            if (StringUtils.containsIgnoreCase(days,"U")){
                weekdays.add(Calendar.SUNDAY);
            }

        }

        return weekdays;
    }

    private String buildDaysForUI(List<Integer> days){

        StringBuilder returnValue = new StringBuilder();

        for (Integer day : days) {
            switch (day){
                case Calendar.MONDAY:
                   returnValue.append("M ");
                   break;
                case Calendar.TUESDAY:
                    returnValue.append("T ");
                   break;
                case Calendar.WEDNESDAY:
                    returnValue.append("W ");
                   break;
                case Calendar.THURSDAY:
                    returnValue.append("H ");
                   break;
                case Calendar.FRIDAY:
                    returnValue.append("F ");
                   break;
                case Calendar.SATURDAY:
                    returnValue.append("S ");
                   break;
                case Calendar.SUNDAY:
                    returnValue.append("U ");
                   break;
            }
        }

        return StringUtils.removeEnd(returnValue.toString(), " ");
    }

    private List<ScheduleRequestInfo> getScheduleRequestsByAO(String aoId, ContextInfo contextInfo){
        List<ScheduleRequestInfo> scheduleRequests = new ArrayList<>();
        try{
            List<ScheduleRequestSetInfo> scheduleRequestSets = getSchedulingService().getScheduleRequestSetsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, aoId, contextInfo);
            if(scheduleRequestSets != null && !scheduleRequestSets.isEmpty()){
                for(ScheduleRequestSetInfo srs : scheduleRequestSets){
                    List<ScheduleRequestInfo> scheduleRequestInfos = getSchedulingService().getScheduleRequestsByScheduleRequestSet(srs.getId(), contextInfo);
                    if(scheduleRequestInfos != null && !scheduleRequestInfos.isEmpty()){
                        scheduleRequests.addAll(scheduleRequestInfos);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return scheduleRequests;

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
                ScheduleRequestSetInfo set = getSchedulingService().getScheduleRequestSet(scheduleRequestInfos.get(0).getScheduleRequestSetId(),defaultContextInfo);
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
                scheduleWrapper.setTimeSlot(timeSlotInfos.get(0));

                DateFormat df = new SimpleDateFormat(TIME_FORMAT_STRING);

                Date timeForDisplay;
                if(scheduleWrapper.getTimeSlot().getStartTime().getMilliSeconds() != null) {
                    timeForDisplay = new Date(scheduleWrapper.getTimeSlot().getStartTime().getMilliSeconds());
                    String formattedTime = df.format(timeForDisplay);
                    //Set for read only display purpose in the format hh:mm a
                    scheduleWrapper.setStartTimeUI(formattedTime);
                    //Set only hh:mm for user editable purpose
                    scheduleWrapper.setStartTime(StringUtils.substringBefore(formattedTime," "));
                    scheduleWrapper.setStartTimeAMPM(StringUtils.substringAfter(formattedTime," "));
                }

                if(scheduleWrapper.getTimeSlot().getEndTime().getMilliSeconds() != null) {
                    timeForDisplay = new Date(scheduleWrapper.getTimeSlot().getEndTime().getMilliSeconds());
                    String formattedTime = df.format(timeForDisplay);
                    scheduleWrapper.setEndTimeUI(df.format(timeForDisplay));
                    //Set for read only display purpose in the format hh:mm a
                    scheduleWrapper.setEndTimeUI(formattedTime);
                    //Set only hh:mm for user editable purpose
                    scheduleWrapper.setEndTime(StringUtils.substringBefore(formattedTime," "));
                    scheduleWrapper.setEndTimeAMPM(StringUtils.substringAfter(formattedTime," "));
                }

                String daysUI = buildDaysForUI(scheduleWrapper.getTimeSlot().getWeekdays());
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

    public void loadScheduleActuals(ActivityOfferingWrapper wrapper,ContextInfo defaultContextInfo){

        if (wrapper.getAoInfo().getScheduleIds() != null) {

            try {

                List<ScheduleInfo> scheduleInfos = getSchedulingService().getSchedulesByIds(wrapper.getAoInfo().getScheduleIds(), defaultContextInfo);

                for (ScheduleInfo scheduleInfo : scheduleInfos) {

                    /**
                     * Until we implement external scheduler, there is going to be only one Schedule component for every scheduleinfo
                     * and the UI doesnt allow us to add multiple compoents to a schedulerequest.
                     */
                    for (ScheduleComponentInfo componentInfo : scheduleInfo.getScheduleComponents()) {
                        ScheduleWrapper scheduleWrapper = new ScheduleWrapper(scheduleInfo,componentInfo);
                        scheduleWrapper.setTba(componentInfo.getIsTBA());

                        List<TimeSlotInfo> timeSlotInfos = getSchedulingService().getTimeSlotsByIds(componentInfo.getTimeSlotIds(), defaultContextInfo);

                        if (!timeSlotInfos.isEmpty()){
                            scheduleWrapper.setTimeSlot(timeSlotInfos.get(0));

                            DateFormat df = new SimpleDateFormat(TIME_FORMAT_STRING);

                            Date timeForDisplay;
                            if (scheduleWrapper.getTimeSlot().getStartTime().getMilliSeconds() != null){
                                timeForDisplay = new Date(scheduleWrapper.getTimeSlot().getStartTime().getMilliSeconds());
                                scheduleWrapper.setStartTimeUI(df.format(timeForDisplay));
                            }

                            if (scheduleWrapper.getTimeSlot().getEndTime().getMilliSeconds() != null){
                                timeForDisplay = new Date(scheduleWrapper.getTimeSlot().getEndTime().getMilliSeconds());
                                scheduleWrapper.setEndTimeUI(df.format(timeForDisplay));
                            }

                            scheduleWrapper.setDaysUI(buildDaysForUI(scheduleWrapper.getTimeSlot().getWeekdays()));
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

        // should be only one, if none or more than one is found, throw an exception
        if (socIds == null || socIds.size() != 1) {
            throw new OperationFailedException("Unexpected results from socService.getSocIdsByTerm, expecting exactly one soc id, received: " + socIds);
        }

        SocInfo socInfo = getCourseOfferingSetService().getSoc(socIds.get(0), context);

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

    public void deleteRequestedAndActualSchedules(ActivityOfferingInfo activity){
        try {
            List<ScheduleRequestInfo> rdls = getSchedulingService().getScheduleRequestsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,activity.getId(),createContextInfo());
            for (ScheduleRequestInfo rdl : rdls) {
                StatusInfo status = getSchedulingService().deleteScheduleRequest(rdl.getId(),createContextInfo());
                if (!status.getIsSuccess()){
                     throw new RuntimeException("Cant delete RDL");
                }
            }
            /* TODOSSR if (StringUtils.isNotBlank(activity.getScheduleId())){
                 StatusInfo status = getSchedulingService().deleteSchedule(activity.getScheduleId(),ContextUtils.createDefaultContextInfo());
                 if (!status.getIsSuccess()){
                     throw new RuntimeException("Cant delete RDL");
                 }
                activity.setScheduleId("");
            }*/
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    protected ContextInfo createContextInfo(){
        return ContextUtils.createDefaultContextInfo();
    }

}
