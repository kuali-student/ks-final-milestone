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
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ColocatedActivity;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.helper.ActivityOfferingScheduleHelper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseofferingset.util.CourseOfferingSetUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
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
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    @Transactional // If it's already a part of transaction, it's ok.. Otherwise, create a new transaction boundary for all the changes.
    public void saveSchedules(ActivityOfferingWrapper wrapper,ContextInfo defaultContextInfo){
        boolean performScheduling = false; //indicate whether or not to do the actual AO scheduling (and therefore change AO lifecycle state)
        if (defaultContextInfo == null) {
            defaultContextInfo = createContextInfo();
        }

        savePreMSE(wrapper, defaultContextInfo);

        if (wrapper.isSchedulingCompleted() && wrapper.isSendRDLsToSchedulerAfterMSE()){
            performScheduling = true;
        }
        savePostMSE(wrapper, performScheduling, defaultContextInfo);
    }

    public void loadSchedules(ActivityOfferingWrapper wrapper,ContextInfo defaultContextInfo){

        try {
            SocInfo soc = CourseOfferingSetUtil.getMainSocForTermId(wrapper.getAoInfo().getTermId(), defaultContextInfo);
            if (soc != null){
                wrapper.setSocInfo(soc);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        loadScheduleRequests(wrapper,defaultContextInfo);
        loadSchedulesAndComponents(wrapper, defaultContextInfo);
    }

    public void savePostMSE(ActivityOfferingWrapper activityOfferingWrapper, boolean performScheduling, ContextInfo defaultContextInfo){

        try {
            //Schedule AO
            ActivityOfferingInfo latestAO;

            //determine/retrieve AO scheduling state based on existing ASIs/RSIs (without sending AO to scheduler)
            String aoSchedulingStateKey = activityOfferingWrapper.getActivityOfferingScheduleingStateKey();

            if (performScheduling) { // send to scheduler and AO scheduling state is updated inside COServiceImpl.scheduleActivityOffering()
                StatusInfo statusInfo = CourseOfferingManagementUtil.getCourseOfferingService().scheduleActivityOffering(activityOfferingWrapper.getId(), defaultContextInfo);
                if (!statusInfo.getIsSuccess()){
                    GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,statusInfo.getMessage());
                    return;
                }
                //retrieve the latest AO after AO scheduling
                latestAO = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOffering(activityOfferingWrapper.getAoInfo().getId(), defaultContextInfo);
            } else {  //if not to send AO to scheduler, update AO scheduling state based on existing ASI/RSI
                latestAO = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOffering(activityOfferingWrapper.getAoInfo().getId(), defaultContextInfo);
                latestAO.setSchedulingStateKey(aoSchedulingStateKey);
                latestAO = updateActivityOfferingSchedulingState (latestAO, aoSchedulingStateKey, defaultContextInfo);
            }

            //only perform AO lifecycle state change if AO scheduling has happened
            if (performScheduling) {
                latestAO = updateScheduledActivityOfferingLifecycleState(latestAO, defaultContextInfo);
            }

            activityOfferingWrapper.setAoInfo(latestAO);

            if (activityOfferingWrapper.isColocatedAO()){
                for (ColocatedActivity colocatedActivity : activityOfferingWrapper.getColocatedActivities()){
                    ActivityOfferingInfo ao = CourseOfferingManagementUtil.getCourseOfferingService()
                            .getActivityOffering(colocatedActivity.getActivityOfferingInfo().getId(), defaultContextInfo);
                    ActivityOfferingInfo updatedAO = null;

                    // if AO scheduling has happened, colo AOs scheduling state should have been updated inside COServiceImpl.scheduleActivityOffering()
                    if (performScheduling) {
                        updatedAO = updateScheduledActivityOfferingLifecycleState(ao, defaultContextInfo);
                    } else {
                        if (StringUtils.equals(aoSchedulingStateKey, LuiServiceConstants.LUI_AO_SCHEDULING_STATE_UNSCHEDULED_KEY)
                                || StringUtils.equals(aoSchedulingStateKey, LuiServiceConstants.LUI_AO_SCHEDULING_STATE_EXEMPT_KEY)) {
                            ao.setSchedulingStateKey(aoSchedulingStateKey);
                            updatedAO = updateActivityOfferingSchedulingState (ao, aoSchedulingStateKey, defaultContextInfo);
                        }
                    }

                    if (updatedAO != null) {
                        colocatedActivity.setActivityOfferingInfo(updatedAO);
                        colocatedActivity.setAoId(updatedAO.getId());
                    } else {
                        colocatedActivity.setActivityOfferingInfo(ao);
                        colocatedActivity.setAoId(ao.getId());
                    }

                }
            }

            //Set it in the wrapper and load all the revised Schedules and ScheduleComponents
            if (performScheduling) {
                loadSchedulesAndComponents(activityOfferingWrapper, defaultContextInfo);
            }
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

        if (StringUtils.isBlank(scheduleWrapper.getRoomCode())){
            scheduleWrapper.setRoom(null);
        }

        if (StringUtils.isBlank(scheduleWrapper.getBuildingCode())){
            scheduleWrapper.setBuilding(null);
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

        // validate the weekdays
        if (StringUtils.isNotEmpty(scheduleWrapper.getDays())) {
            String scheduleDays = StringUtils.upperCase(scheduleWrapper.getDays());
            List<Integer> parsedWeekdays = SchedulingServiceUtil.weekdaysString2WeekdaysList(scheduleDays);
            if(parsedWeekdays.isEmpty() || scheduleDays.trim().length() > parsedWeekdays.size()) {
                addErrorMessage(ScheduleInput.WEEKDAYS, "Day characters are invalid, duplicates not allowed.");
            }
        }

        // if a room or a building were entered, ensure the building and room code are valid
        try {

            ContextInfo contextInfo = createContextInfo();
            int firstInfo = 0;

            // if a building code exists, validate the building code and populate the building info
            if (StringUtils.isNotBlank(scheduleWrapper.getBuildingCode())){
                List<BuildingInfo> buildings = retrieveBuildingInfoByCode(scheduleWrapper.getBuildingCode(),true);
                if (buildings.isEmpty()) {
                    addErrorMessage(ScheduleInput.BUILDING, "Facility code was invalid.");
                } else {
                    scheduleWrapper.setBuilding(buildings.get(firstInfo));
                }

                // if a building code exists and a room code exists, validate the room code and populate the room info
                if (!buildings.isEmpty() && StringUtils.isNotEmpty(scheduleWrapper.getRoomCode())) {
                    List<RoomInfo> rooms = CourseOfferingManagementUtil.getRoomService().getRoomsByBuildingAndRoomCode(scheduleWrapper.getBuildingCode(), scheduleWrapper.getRoomCode(), contextInfo);
                    if (rooms.isEmpty()) {
                        addErrorMessage(ScheduleInput.ROOM, "Room code was invalid.");
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
                StatusInfo statusInfo = CourseOfferingManagementUtil.getSchedulingService().deleteScheduleRequest(scheduleRequestId, defaultContextInfo);
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
            scheduleRequestSetInfo = CourseOfferingManagementUtil.getSchedulingService()
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
            scheduleRequestSetInfo = CourseOfferingManagementUtil.getSchedulingService()
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
            StatusInfo statusInfo = CourseOfferingManagementUtil.getSchedulingService().deleteScheduleRequestSet(id, context);
            if (!statusInfo.getIsSuccess()){
                throw new OperationFailedException("Cant delete the schedule request set " + statusInfo.getMessage());
            }
                return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // A little hack-y, but looks like pretty much all the methods in this class --cclin
    private ScheduleRequestSetInfo getScheduleRequestSetInfo(String id, ContextInfo context) {
        try {
            ScheduleRequestSetInfo srs = CourseOfferingManagementUtil.getSchedulingService().getScheduleRequestSet(id, context);
            return srs;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ScheduleRequestSetInfo getScheduleRequestSetInfoByRefObject(String aoId, ContextInfo context) {
        try {
            List<ScheduleRequestSetInfo> srses =
                    CourseOfferingManagementUtil.getSchedulingService().getScheduleRequestSetsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,
                            aoId, context);
            // Assume only zero or one SRS with this ao ID (should never be more than 1 until we support partial colo)
            ScheduleRequestSetInfo srs = KSCollectionUtils.getOptionalZeroElement(srses);
            return srs;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void savePreMSE(ActivityOfferingWrapper wrapper, ContextInfo contextInfo) {

        //  Handle deleted schedule requests.
        deleteScheduleRequestComponents(wrapper.getDeletedScheduleComponents(), contextInfo);

        String aoId = wrapper.getId();
        ActivityOfferingInfo aoInfo =  wrapper.getAoInfo();

        ScheduleRequestSetInfo scheduleRequestSetInfo = wrapper.getScheduleRequestSetInfo();

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
                ScheduleRequestSetInfo origSRS = getScheduleRequestSetInfo(scheduleRequestSetInfo.getId(), contextInfo);
                // KSENROLL-11145 (AFT CCO 2.13)
                // Delete the old SRSes associated with ao IDs added to the colo set
                if (scheduleRequestSetInfo.getRefObjectIds().containsAll(origSRS.getRefObjectIds()) &&
                        scheduleRequestSetInfo.getRefObjectIds().size() > origSRS.getRefObjectIds().size()) {
                    // Determine if new SRS added new AO ids (which means old ones need to be removed).  That is,
                    // if x, y are added to an SRS containing z (thus, x, y, z are now in SRS with z), then the SRS
                    // with x and with y should be deleted, otherwise there are two SRSes around.

                    // Do the set difference between the current ref object IDS (e.g., x, y, z)
                    // with the original (e.g., z) to get the AO ids whose original
                    List<String> aoIdsUsedToRemoveSRS = new ArrayList<String>();
                    aoIdsUsedToRemoveSRS.addAll(scheduleRequestSetInfo.getRefObjectIds());
                    // Don't delete the original ones because they are what's being added to
                    aoIdsUsedToRemoveSRS.removeAll(origSRS.getRefObjectIds());
                    for (String aoIdIter: aoIdsUsedToRemoveSRS) {
                        ScheduleRequestSetInfo srsToDelete = getScheduleRequestSetInfoByRefObject(aoIdIter, contextInfo);
                        if (srsToDelete != null) {
                            deleteScheduleRequestSetInfo(srsToDelete.getId(), contextInfo);
                        }
                    }
                }
                List<String> aoIdsForSRSDeletion = new ArrayList<String>(scheduleRequestSetInfo.getRefObjectIds());
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
                    ScheduleRequestComponentInfo componentInfo = buildScheduleComponentRequest(wrapper,scheduleWrapper,contextInfo);
                    scheduleRequest.getScheduleRequestComponents().add(componentInfo);

                    ScheduleRequestInfo newScheduleRequest = CourseOfferingManagementUtil.getSchedulingService().createScheduleRequest(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST,scheduleRequest,contextInfo);
                    scheduleWrapper.setScheduleRequestInfo(newScheduleRequest);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            } else if (scheduleWrapper.isModified()){
                try {
                    ScheduleRequestInfo scheduleRequest = scheduleWrapper.getScheduleRequestInfo();
                    ScheduleRequestComponentInfo componentInfo = buildScheduleComponentRequest(wrapper,scheduleWrapper,contextInfo);
                    scheduleRequest.getScheduleRequestComponents().clear();
                    scheduleRequest.getScheduleRequestComponents().add(componentInfo);

                    ScheduleRequestInfo newScheduleRequest = CourseOfferingManagementUtil.getSchedulingService().updateScheduleRequest(scheduleRequest.getId(),scheduleRequest,contextInfo);
                    scheduleWrapper.setScheduleRequestInfo(newScheduleRequest);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private ScheduleRequestComponentInfo buildScheduleComponentRequest(ActivityOfferingWrapper wrapper,ScheduleWrapper scheduleWrapper,ContextInfo defaultContextInfo) throws Exception{

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

        TimeSlotInfo timeSlotInfo = fetchOrCreateTimeSlot(wrapper, scheduleWrapper, defaultContextInfo);
        componentInfo.getTimeSlotIds().add(timeSlotInfo.getId());

        return componentInfo;
    }

    public TimeSlotInfo fetchOrCreateTimeSlot(ActivityOfferingWrapper aoWrapper,ScheduleWrapper scheduleWrapper,ContextInfo defaultContextInfo) throws Exception {

        TimeOfDayInfo startTimeOfDayInfo = new TimeOfDayInfo();
        TimeOfDayInfo endTimeOfDayInfo = new TimeOfDayInfo();

        List<Integer> days = SchedulingServiceUtil.weekdaysString2WeekdaysList(scheduleWrapper.getDays());

        String startTimeString = scheduleWrapper.getStartTime();
        if (StringUtils.isNotEmpty(startTimeString)) {
            startTimeOfDayInfo = TimeOfDayHelper.makeTimeOfDayInfoFromTimeString(startTimeString);
        }

        String endTimeString = scheduleWrapper.getEndTime();
        if (StringUtils.isNotEmpty(endTimeString)) {
            endTimeOfDayInfo =  TimeOfDayHelper.makeTimeOfDayInfoFromTimeString(endTimeString);
        }

        TimeSlotInfo timeSlot;

        /**
         * 1. If TBA, look for tba timeslot and use that
         * 2. Otherwise, look for standard timeslot and use that. If there is no standard timeslot exists, create
         * an adhoc one if permission exists for the user.
         */

        if (scheduleWrapper.isTba()){
            List<TimeSlotInfo> existingTimeSlots = CourseOfferingManagementUtil.getSchedulingService().getTimeSlotsByDaysAndStartTimeAndEndTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_TBA,days,startTimeOfDayInfo,endTimeOfDayInfo,defaultContextInfo);
            if (existingTimeSlots.isEmpty()){
                timeSlot = new TimeSlotInfo();
                timeSlot.setTypeKey(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_TBA);
                timeSlot.setStateKey(SchedulingServiceConstants.TIME_SLOT_STATE_ACTIVE);
                    timeSlot.setStartTime(startTimeOfDayInfo);
                    timeSlot.setEndTime(endTimeOfDayInfo);
                timeSlot.setWeekdays(days);
                timeSlot = CourseOfferingManagementUtil.getSchedulingService().createTimeSlot(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_TBA,timeSlot,defaultContextInfo);
            } else {
                timeSlot = KSCollectionUtils.getRequiredZeroElement(existingTimeSlots);
            }
        } else {
            /*
            Look for standard timeslot.
             */
            List<TimeSlotInfo> existingTimeSlots = new ArrayList<TimeSlotInfo>();

            // Type Type may not be available for terms which uses adhoc timeslots
            if (StringUtils.isNotBlank(aoWrapper.getTimeSlotType())){
                //Each term type associated with only one standard timeslot type
                existingTimeSlots = CourseOfferingManagementUtil.getSchedulingService().getTimeSlotsByDaysAndStartTimeAndEndTime(aoWrapper.getTimeSlotType(),days,startTimeOfDayInfo,endTimeOfDayInfo,defaultContextInfo);
            }

            //If standard TS exists, use that. Otherwise, check for Adhoc and create one
            if (!existingTimeSlots.isEmpty()){
                 timeSlot = KSCollectionUtils.getRequiredZeroElement(existingTimeSlots);
            } else {
                existingTimeSlots = CourseOfferingManagementUtil.getSchedulingService().getTimeSlotsByDaysAndStartTimeAndEndTime(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_ADHOC,days,startTimeOfDayInfo,endTimeOfDayInfo,defaultContextInfo);
                //If AdHoc TS exists, use that. Otherwise, check create one if the user has permission
                if (!existingTimeSlots.isEmpty()){
                    timeSlot = KSCollectionUtils.getRequiredZeroElement(existingTimeSlots);
                } else {
                    if (aoWrapper.isAuthorizedToModifyEndTimeTS()){
                        timeSlot = new TimeSlotInfo();
                        timeSlot.setTypeKey(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_ADHOC);
                        timeSlot.setStateKey(SchedulingServiceConstants.TIME_SLOT_STATE_ACTIVE);
                        timeSlot.setStartTime(startTimeOfDayInfo);
                        timeSlot.setEndTime(endTimeOfDayInfo);
                        timeSlot.setWeekdays(days);
                        timeSlot = CourseOfferingManagementUtil.getSchedulingService().createTimeSlot(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_ADHOC,timeSlot,defaultContextInfo);
                    } else { // This never happen as the user restricts displaying a free end time field for dept scheduling coordinators
                        throw new PermissionDeniedException("Sorry, you dont have permission to create a adhoc timeslot");
                    }
                }
            }
        }

        return timeSlot;
    }

    /**
     * This method loads the schedule requests/components.
     * Support multiple schedule requests
     *
     * @param wrapper  ActivityOfferingWrapper
     */
    public void loadScheduleRequests(ActivityOfferingWrapper wrapper,ContextInfo defaultContextInfo){

        try {

            List<ScheduleRequestInfo> scheduleRequestInfos = CourseOfferingManagementUtil.getSchedulingService().getScheduleRequestsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, wrapper.getId(), defaultContextInfo);

            //For Full colo, there must be only one ScheduleRequestSet.
            if (!scheduleRequestInfos.isEmpty()){
                int firstScheduleRequestInfo = 0;
                ScheduleRequestSetInfo set = CourseOfferingManagementUtil.getSchedulingService().getScheduleRequestSet(scheduleRequestInfos.get(firstScheduleRequestInfo).getScheduleRequestSetId(),defaultContextInfo);
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
            List<TimeSlotInfo> timeSlotInfos = CourseOfferingManagementUtil.getSchedulingService().getTimeSlotsByIds(componentInfo.getTimeSlotIds(), defaultContextInfo);

            if (!timeSlotInfos.isEmpty()){
                int firstTimeSlotInfo = 0;
                scheduleWrapper.setTimeSlot(timeSlotInfos.get(firstTimeSlotInfo));

                TimeOfDayInfo startTime = scheduleWrapper.getTimeSlot().getStartTime();
                if(startTime != null && !startTime.equals(new TimeOfDayInfo(0,0,0))) {
                    String formattedTime = TimeOfDayHelper.makeFormattedTimeForAOSchedules(startTime);
                    scheduleWrapper.setStartTime(formattedTime);
                }

                TimeOfDayInfo endTime = scheduleWrapper.getTimeSlot().getEndTime();
                if (endTime != null && !endTime.equals(new TimeOfDayInfo(0,0,0))) {
                    String formattedTime = TimeOfDayHelper.makeFormattedTimeForAOSchedules(endTime);
                    scheduleWrapper.setEndTime(formattedTime);
                }

                String daysUI = SchedulingServiceUtil.weekdaysList2WeekdaysString(scheduleWrapper.getTimeSlot().getWeekdays());
                scheduleWrapper.setDaysUI(daysUI);
                scheduleWrapper.setDays(StringUtils.remove(daysUI, " "));
            }

            if (!componentInfo.getRoomIds().isEmpty()){

                RoomInfo room = CourseOfferingManagementUtil.getRoomService().getRoom(componentInfo.getRoomIds().get(0), defaultContextInfo);

                scheduleWrapper.setRoom(room);
                scheduleWrapper.setRoomCode(room.getRoomCode());

                if (!room.getRoomUsages().isEmpty()){
                    scheduleWrapper.setRoomCapacity(room.getRoomUsages().get(0).getHardCapacity());
                }

                BuildingInfo buildingInfo = CourseOfferingManagementUtil.getRoomService().getBuilding(room.getBuildingId(), defaultContextInfo);
                scheduleWrapper.setBuilding(buildingInfo);
                scheduleWrapper.setBuildingCode(buildingInfo.getBuildingCode());
                scheduleWrapper.setBuildingId(room.getBuildingId());
            } else if (!componentInfo.getBuildingIds().isEmpty()){
                String buildingId = componentInfo.getBuildingIds().get(0);
                BuildingInfo buildingInfo = CourseOfferingManagementUtil.getRoomService().getBuilding(buildingId, defaultContextInfo);
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
                List<ScheduleInfo> scheduleInfos = CourseOfferingManagementUtil.getSchedulingService().getSchedulesByIds(wrapper.getAoInfo().getScheduleIds(), defaultContextInfo);

                for (ScheduleInfo scheduleInfo : scheduleInfos) {
                    /**
                     * Until we implement external scheduler, there is going to be only one Schedule component for every scheduleinfo
                     * and the UI doesn't allow us to add multiple components to a schedule request.
                     */
                    for (ScheduleComponentInfo componentInfo : scheduleInfo.getScheduleComponents()) {
                        ScheduleWrapper scheduleWrapper = new ScheduleWrapper(scheduleInfo,componentInfo);
                        scheduleWrapper.setTba(componentInfo.getIsTBA());

                        List<TimeSlotInfo> timeSlotInfos = CourseOfferingManagementUtil.getSchedulingService().getTimeSlotsByIds(componentInfo.getTimeSlotIds(), defaultContextInfo);

                        if (!timeSlotInfos.isEmpty()){
                            int firstTimeSlotInfo = 0;
                            scheduleWrapper.setTimeSlot(timeSlotInfos.get(firstTimeSlotInfo));

                            TimeOfDayInfo startTime = scheduleWrapper.getTimeSlot().getStartTime();
                            if (startTime != null && !startTime.equals(new TimeOfDayInfo(0,0,0))){
                                scheduleWrapper.setStartTime(TimeOfDayHelper.makeFormattedTimeForAOSchedules(startTime));
                            }

                            TimeOfDayInfo endTime = scheduleWrapper.getTimeSlot().getEndTime();
                            if (endTime != null && !endTime.equals(new TimeOfDayInfo(0,0,0))){
                                scheduleWrapper.setEndTime(TimeOfDayHelper.makeFormattedTimeForAOSchedules(endTime));
                            }
                            scheduleWrapper.setDaysUI(SchedulingServiceUtil.weekdaysList2WeekdaysString(scheduleWrapper.getTimeSlot().getWeekdays()));
                        }

                        if (StringUtils.isNotBlank(componentInfo.getRoomId())){

                            RoomInfo room = CourseOfferingManagementUtil.getRoomService().getRoom(componentInfo.getRoomId(), defaultContextInfo);

                            scheduleWrapper.setRoom(room);
                            scheduleWrapper.setRoomCode(room.getRoomCode());

                            if (!room.getRoomUsages().isEmpty()){
                                scheduleWrapper.setRoomCapacity(room.getRoomUsages().get(0).getHardCapacity());
                            }

                            BuildingInfo buildingInfo = CourseOfferingManagementUtil.getRoomService().getBuilding(room.getBuildingId(), defaultContextInfo);
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

    // update AO scheduling state
    private ActivityOfferingInfo updateActivityOfferingSchedulingState(ActivityOfferingInfo activityOfferingInfo, String activityOfferingSchedulingStateKey, ContextInfo context) {
        //CourseOfferingManagementUtil.mergeAttribute(activityOfferingInfo.getAttributes(), CourseOfferingServiceConstants.ACTIVITY_OFFERING_SCHEDULING_STATE_ATTR, activityOfferingSchedulingStateKey);
        activityOfferingInfo.setSchedulingStateKey(activityOfferingSchedulingStateKey);
        try {
            ActivityOfferingInfo updatedAO = CourseOfferingManagementUtil.getCourseOfferingService().updateActivityOffering(activityOfferingInfo.getId(), activityOfferingInfo, context);
            return updatedAO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ActivityOfferingInfo updateScheduledActivityOfferingLifecycleState(ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {

        // Keep track of the state before any changes, to avoid extra processing if the AO state does not change
        String aoCurrentState = activityOfferingInfo.getStateKey();

        // Find the term-level SOC for this activity offering and find out its state
        SocInfo socInfo = CourseOfferingSetUtil.getMainSocForTermId(activityOfferingInfo.getTermId(), context);
        String aoNextState = null;

        // If the SOC is in Locked, Final Edits, Publishing or Published, and the Scheduled Activity Offering is in Draft state, set the Activity Offering state to Offered
        if (socInfo != null) {
            if (socInfo.getStateKey().equals(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY)
                    || socInfo.getStateKey().equals(CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY)
                    || socInfo.getStateKey().equals(CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY)
                    || socInfo.getStateKey().equals(CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY)) {
                if (LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY.equals(activityOfferingInfo.getStateKey())
                    || LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY.equals(activityOfferingInfo.getStateKey())) {
                    String aoSchedulingState = activityOfferingInfo.getSchedulingStateKey();
                    if (LuiServiceConstants.LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY.equals(aoSchedulingState) ||
                            LuiServiceConstants.LUI_AO_SCHEDULING_STATE_EXEMPT_KEY.equals(aoSchedulingState)) {
                        // KSENROLL-10792 Should allow AO scheduling state of exempt (which indicates a TBA)
                        // to cause AO state to become offered (before this, it was not changed).
                        aoNextState = LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY;
                    }
                }
            }
        }

        if (StringUtils.equals(aoCurrentState, aoNextState)) {
            return activityOfferingInfo;
        } else {
            StatusInfo statusInfo = CourseOfferingManagementUtil.getCourseOfferingService().changeActivityOfferingState(activityOfferingInfo.getId(), aoNextState, context);
            if (!statusInfo.getIsSuccess()){
                throw new OperationFailedException("Error updating Activity offering state to " + aoNextState + " " + statusInfo);
            }
            ActivityOfferingInfo updatedAO = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOffering(activityOfferingInfo.getId(), context);
            //updatedAO.setSchedulingStateKey(activityOfferingInfo.getSchedulingStateKey());
            return updatedAO;
        }
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
            List<ScheduleRequestInfo> rdls = CourseOfferingManagementUtil.getSchedulingService().getScheduleRequestsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, activityId, createContextInfo());
            for (ScheduleRequestInfo rdl : rdls) {
                if (!StringUtils.equals(rdl.getScheduleRequestSetId(),schSet.getId())){
                    //Util we implement partial colo, there is going to be only one sch set
                    StatusInfo status = CourseOfferingManagementUtil.getSchedulingService().deleteScheduleRequestSet(rdl.getScheduleRequestSetId(), defaultContextInfo);
                    if (!status.getIsSuccess()){
                         throw new RuntimeException("Cant delete RDL");
                    }
                    break;
                }
            }
            for (String schId : deleteScheduleIds){
                 StatusInfo status = CourseOfferingManagementUtil.getSchedulingService().deleteSchedule(schId,ContextUtils.createDefaultContextInfo());
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
     * Searches for buildings given a building code.
     * @param buildingCode A building code.
     * @return A List of BuildingInfos
     * @throws Exception
     */
    public List<BuildingInfo> retrieveBuildingInfoByCode(String buildingCode) throws Exception {
        return  retrieveBuildingInfoByCode(buildingCode, false);
    }

    /**
     * This method retrieves a list of matching end times for days and startime entered by the user. This loads
     * only the standard timeslots.
     *
     * @param days
     * @param startTime
     * @param timeSlotType
     * @return
     * @throws Exception
     */
    public List<String> getEndTimes(String days,String startTime,String timeSlotType) throws Exception{

        if (StringUtils.isBlank(timeSlotType)){
            return new ArrayList<String>();
        }

        List<Integer> daysArray = SchedulingServiceUtil.weekdaysString2WeekdaysList(days);
        TimeOfDayInfo timeOfDayInfo = TimeOfDayHelper.makeTimeOfDayInfoFromTimeString(startTime);
        List<TimeSlotInfo> timeSlotInfos = CourseOfferingManagementUtil.getSchedulingService().getTimeSlotsByDaysAndStartTime(timeSlotType,daysArray,timeOfDayInfo,createContextInfo());
        List<String> endTimes = new ArrayList<String>();

        for (TimeSlotInfo ts : timeSlotInfos){
            TimeOfDayInfo st = ts.getStartTime();
            if (st != null) {
                endTimes.add(TimeOfDayHelper.makeFormattedTimeForAOSchedules(ts.getEndTime()));
            }
        }

        Collections.sort(endTimes, new Comparator<String>() {
            @Override
            public int compare(String time1, String time2) {

                String startam = StringUtils.substringAfter(time1, " ");
                String starttime = StringUtils.substringBefore(time1, " ");
                String endam = StringUtils.substringAfter(time2, " ");
                String endtime = StringUtils.substringBefore(time2, " ");

                return startam.compareToIgnoreCase(endam) + starttime.compareToIgnoreCase(endtime);
            }
        });

        return endTimes;
    }

}
