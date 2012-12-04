package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OfferingInstructorWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleComponentWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.SeatPoolWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.ActivityOfferingForm;
import org.kuali.student.enrollment.class2.courseoffering.service.ActivityOfferingMaintainable;
import org.kuali.student.enrollment.class2.courseoffering.service.SeatPoolUtilityService;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.ViewHelperUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

import javax.xml.namespace.QName;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ActivityOfferingMaintainableImpl extends MaintainableImpl implements ActivityOfferingMaintainable {
    private static final long serialVersionUID = 1L;
    private transient CourseOfferingService courseOfferingService;
    private transient CourseOfferingSetService courseOfferingSetService;
    private transient TypeService typeService;
    private transient StateService stateService;
    private transient AcademicCalendarService academicCalendarService;
    private transient SchedulingService schedulingService;
    private transient RoomService roomService;
    private transient PopulationService populationService;
    private transient SeatPoolUtilityService seatPoolUtilityService = new SeatPoolUtilityServiceImpl();

    private static final String TIME_FORMAT_STRING = "hh:mm a";

    @Override
    public void saveDataObject() {
        if(getMaintenanceAction().equals(KRADConstants.MAINTENANCE_EDIT_ACTION)) {

            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper) getDataObject();
            disassembleInstructorsWrapper(activityOfferingWrapper.getInstructors(), activityOfferingWrapper.getAoInfo());

            List<SeatPoolDefinitionInfo> seatPools = this.getSeatPoolDefinitions(activityOfferingWrapper.getSeatpools());

            seatPoolUtilityService.updateSeatPoolDefinitionList(seatPools, activityOfferingWrapper.getAoInfo().getId(), contextInfo);

            if (activityOfferingWrapper.isSchedulesRevised()){
                processRevisedSchedules(activityOfferingWrapper);
            } else {
                //If Schedule Actuals available but not revised, skip processing schedule request
                if (StringUtils.isBlank(activityOfferingWrapper.getAoInfo().getScheduleId())){
                    createOrUpdateScheduleRequests(activityOfferingWrapper);
                }
            }

            try {
                ActivityOfferingInfo activityOfferingInfo = getCourseOfferingService().updateActivityOffering(activityOfferingWrapper.getAoInfo().getId(), activityOfferingWrapper.getAoInfo(), contextInfo);
                activityOfferingWrapper.setAoInfo(activityOfferingInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void createOrUpdateScheduleRequests(ActivityOfferingWrapper wrapper) {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        //For revise, schedule  request should be already there.. but for some ref data, it's missing..
        if (wrapper.getScheduleRequestInfo() == null){
            ScheduleRequestInfo scheduleRequest = new ScheduleRequestInfo();
            scheduleRequest.setRefObjectId(wrapper.getAoInfo().getId());
            scheduleRequest.setRefObjectTypeKey(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);
            scheduleRequest.setName("Schedule request for " + wrapper.getAoInfo().getCourseOfferingCode() + " - " + wrapper.getAoInfo().getActivityCode());
            scheduleRequest.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST);
            scheduleRequest.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_STATE_CREATED);
            wrapper.setScheduleRequestInfo(scheduleRequest);
        }

        wrapper.getScheduleRequestInfo().getScheduleRequestComponents().clear();

        for (ScheduleWrapper scheduleWrapper : wrapper.getRequestedScheduleComponents()) {
            if (!scheduleWrapper.isRequestAlreadySaved()){
                ScheduleRequestComponentInfo componentInfo = buildScheduleComponentRequest(scheduleWrapper);
                wrapper.getScheduleRequestInfo().getScheduleRequestComponents().add(componentInfo);
            }
            else {
                wrapper.getScheduleRequestInfo().getScheduleRequestComponents().add(scheduleWrapper.getScheduleRequestComponentInfo());
            }
        }

        if (StringUtils.isBlank(wrapper.getScheduleRequestInfo().getId())){

            try{
                ScheduleRequestInfo createdScheduleRequestInfo = getSchedulingService().createScheduleRequest(SchedulingServiceConstants.SCHEDULE_REQUEST_TYPE_SCHEDULE_REQUEST,wrapper.getScheduleRequestInfo(), contextInfo);
                wrapper.setScheduleRequestInfo(createdScheduleRequestInfo);
            } catch (Exception e){
                throw new RuntimeException(e);
            }

        } else {

            try{
               ScheduleRequestInfo updatedScheduleRequestInfo = getSchedulingService().updateScheduleRequest(wrapper.getScheduleRequestInfo().getId(),wrapper.getScheduleRequestInfo(), contextInfo);
               wrapper.setScheduleRequestInfo(updatedScheduleRequestInfo);
            } catch (Exception e){
                throw new RuntimeException(e);
            }

        }

    }

    private ScheduleRequestComponentInfo buildScheduleComponentRequest(ScheduleWrapper scheduleWrapper){

        ScheduleRequestComponentInfo componentInfo = new ScheduleRequestComponentInfo();
        componentInfo.setId(UUIDHelper.genStringUUID());
        componentInfo.setIsTBA(scheduleWrapper.isTba());

        if(scheduleWrapper.getRoom() != null) {
            List<String> room = new ArrayList<String>();
            room.add(scheduleWrapper.getRoom().getId());
            componentInfo.setRoomIds(room);
        }

        componentInfo.setResourceTypeKeys(scheduleWrapper.getFeatures());

        TimeSlotInfo timeSlot = new TimeSlotInfo();
        timeSlot.setTypeKey(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING);
        timeSlot.setStateKey(SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY);
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
            TimeSlotInfo createdTimeSlot = getSchedulingService().createTimeSlot(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING,timeSlot, ContextUtils.createDefaultContextInfo());
            componentInfo.getTimeSlotIds().add(createdTimeSlot.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return componentInfo;
    }


    private List<Integer> buildDaysForDTO(String days){

        List<Integer> weekdays  = new ArrayList<Integer>();

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
                    ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

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

    public boolean addScheduleRequestComponent(ActivityOfferingForm form){

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
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

        if (form.isMainPage()){
            activityOfferingWrapper.getRequestedScheduleComponents().add(scheduleWrapper);
        } else {
            activityOfferingWrapper.getRevisedScheduleRequestComponents().add(scheduleWrapper);
        }

        activityOfferingWrapper.setNewScheduleRequest(new ScheduleWrapper());

        return true;
    }

    public void prepareForScheduleRevise(ActivityOfferingWrapper wrapper){
         wrapper.getRevisedScheduleRequestComponents().clear();
        for (ScheduleWrapper scheduleWrapper : wrapper.getRequestedScheduleComponents()) {
            ScheduleWrapper forRevise = new ScheduleWrapper(scheduleWrapper);
            wrapper.getRevisedScheduleRequestComponents().add(forRevise);
        }
    }

    private boolean deleteScheduleRequest(ActivityOfferingWrapper wrapper){
        StatusInfo statusInfo;
        try{
            statusInfo = getSchedulingService().deleteScheduleRequest(wrapper.getScheduleRequestInfo().getId(), ContextUtils.createDefaultContextInfo());
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        if (!statusInfo.getIsSuccess()){
            throw new RuntimeException("Error deleting the schedule request - " + statusInfo.getMessage());
        } else {
            return statusInfo.getIsSuccess();
        }
    }

    public void processRevisedSchedules(ActivityOfferingWrapper activityOfferingWrapper){

        if (activityOfferingWrapper.getRequestedScheduleComponents().isEmpty()){
            //If there are no schedule components, it's not needed to have the request
            deleteScheduleRequest(activityOfferingWrapper);
        } else {
            //Create/update schedule requests
            createOrUpdateScheduleRequests(activityOfferingWrapper);
        }

        try {

            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            //Schedule AO
            StatusInfo statusInfo = getCourseOfferingService().scheduleActivityOffering(activityOfferingWrapper.getId(), contextInfo);

            if (!statusInfo.getIsSuccess()){
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,statusInfo.getMessage());
                return;
            }

            ActivityOfferingInfo latestAO = getCourseOfferingService().getActivityOffering(activityOfferingWrapper.getAoInfo().getId(), contextInfo);

            //This will change the AO/FO/CO state and gets the updated AO
            latestAO = CourseOfferingServiceStateHelper.updateScheduledActivityOffering(latestAO,getCourseOfferingService(),getCourseOfferingSetService(), contextInfo);

            //Copy only certain fields to the existing DTO to avoid unnecessary overwriting to the user modifications
            activityOfferingWrapper.getAoInfo().setStateKey(latestAO.getStateKey());
            activityOfferingWrapper.getAoInfo().setScheduleId(latestAO.getScheduleId());
            activityOfferingWrapper.getAoInfo().setSchedulingStateKey(latestAO.getSchedulingStateKey());

            //Set it in the wrapper and load all the revised schedule Actuals
            loadScheduleActuals(activityOfferingWrapper);

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            ActivityOfferingInfo info = getCourseOfferingService().getActivityOffering(dataObjectKeys.get(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID), contextInfo);
            ActivityOfferingWrapper wrapper = new ActivityOfferingWrapper(info);

            //get the course offering
            CourseOfferingInfo courseOfferingInfo = getCourseOfferingService().getCourseOffering(info.getCourseOfferingId(), contextInfo);

            // get the format offering
            FormatOfferingInfo formatOfferingInfo = getCourseOfferingService().getFormatOffering(info.getFormatOfferingId(), contextInfo);
            wrapper.setFormatOffering(formatOfferingInfo);

            // Added for WaitList Tanveer 06/27/2012
            wrapper.setWaitListLevelTypeKey(courseOfferingInfo.getWaitlistLevelTypeKey());
            wrapper.setWaitListTypeKey(courseOfferingInfo.getWaitlistTypeKey());
            wrapper.setHasWaitList(courseOfferingInfo.getHasWaitlist());
            if (!wrapper.getHasWaitList())
                wrapper.setWaitListText("There is no wait list for this offering.");
            if (wrapper.getWaitListLevelTypeKey().equals("Course Offering")){
                wrapper.setWaitListText("This waitlist is managed at the Course Offering level.");
                wrapper.setToolTipText("There is one waitlist for all Activity Offerings");
            }
            if (wrapper.getWaitListLevelTypeKey().equals("Activity Offering")){
                wrapper.setWaitListText("This waitlist is managed at the Activity Offering level.");
                wrapper.setToolTipText("Each Activity Offering has its own wait list.");
            }

            // Set the display string (e.g. 'FALL 2020 (9/26/2020 to 12/26/2020)')
            TermInfo term = getAcademicCalendarService().getTerm(info.getTermId(), contextInfo);
            if (term != null) {
                wrapper.setTermName(term.getName());
            }
            wrapper.setTermDisplayString(getTermDisplayString(info.getTermId(), term));

            wrapper.setCourseOfferingCode(info.getCourseOfferingCode());
            wrapper.setCourseOfferingTitle(info.getCourseOfferingTitle());

            String sCredits = courseOfferingInfo.getCreditCnt();
            if (sCredits == null) {
                sCredits = "0";
            }
            wrapper.setCredits(sCredits);
            //wrapper.setAbbreviatedActivityCode(info.getActivityCode().toUpperCase().substring(0,3));
            wrapper.setActivityCode(info.getActivityCode());
            wrapper.setAbbreviatedCourseType(getTypeService().getType(info.getTypeKey(), contextInfo).getName().toUpperCase().substring(0, 3));

            //process instructor effort
            assembleInstructorWrapper(info.getInstructors(), wrapper);


            boolean readOnlyView = Boolean.parseBoolean(dataObjectKeys.get("readOnlyView"));
            wrapper.setReadOnlyView(readOnlyView);

            document.getNewMaintainableObject().setDataObject(wrapper);
            document.getOldMaintainableObject().setDataObject(wrapper);
            document.getDocumentHeader().setDocumentDescription("Edit AO - " + info.getActivityCode());
            StateInfo state = getStateService().getState(wrapper.getAoInfo().getStateKey(), contextInfo);
            wrapper.setStateName(state.getName());
            TypeInfo typeInfo = getTypeService().getType(wrapper.getAoInfo().getTypeKey(), contextInfo);
            wrapper.setTypeName(typeInfo.getName());

            // Get/Set SeatPools
            List<SeatPoolDefinitionInfo> seatPoolDefinitionInfoList = getCourseOfferingService().getSeatPoolDefinitionsForActivityOffering(info.getId(), contextInfo);

            //Sort the seatpools by priority order
            Collections.sort(seatPoolDefinitionInfoList, new Comparator<SeatPoolDefinitionInfo>() {
                @Override
                public int compare(SeatPoolDefinitionInfo sp1, SeatPoolDefinitionInfo sp2) {
                    return sp1.getProcessingPriority().compareTo(sp2.getProcessingPriority());
                }
            });

            List<SeatPoolWrapper> seatPoolWrapperList = new ArrayList<SeatPoolWrapper>();

            for(SeatPoolDefinitionInfo seatPoolDefinitionInfo :  seatPoolDefinitionInfoList){
                SeatPoolWrapper spWrapper = new SeatPoolWrapper();

                PopulationInfo pInfo = getPopulationService().getPopulation(seatPoolDefinitionInfo.getPopulationId(), contextInfo);
                spWrapper.setSeatPoolPopulation(pInfo);
                spWrapper.setSeatPool(seatPoolDefinitionInfo);
                spWrapper.setId(seatPoolDefinitionInfo.getId());
                seatPoolWrapperList.add(spWrapper);
            }
            wrapper.setSeatpools(seatPoolWrapperList);

            loadSchedules(wrapper);

            return wrapper;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void loadSchedules(ActivityOfferingWrapper wrapper){

        try{
            List<String> socIds = getCourseOfferingSetService().getSocIdsByTerm(wrapper.getAoInfo().getTermId(), ContextUtils.createDefaultContextInfo());

            if (socIds != null && !socIds.isEmpty()){
                //For M5, it should have only one SOC
                if (socIds.size() > 1){
                    throw new RuntimeException("More than one SOC found for a term");
                }

                SocInfo soc = getCourseOfferingSetService().getSoc(socIds.get(0),ContextUtils.createDefaultContextInfo());
                wrapper.setSocInfo(soc);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        loadScheduleRequests(wrapper);
        loadScheduleActuals(wrapper);
    }

    /**
     * This method loads the schedule requests/components.
     * For M5, we're going to have only one schedule request.
     *
     * @param wrapper  ActivityOfferingWrapper
     */
    protected void loadScheduleRequests(ActivityOfferingWrapper wrapper){

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            List<ScheduleRequestInfo> requestInfos = getSchedulingService().getScheduleRequestsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,wrapper.getId(), contextInfo);

            if (requestInfos.size() > 1){  // For M5, we should have only one Schedule Request
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Multiple schedule requests not supported in M5 implementation");
                return;
            }

            if (!requestInfos.isEmpty()){

                ScheduleRequestInfo scheduleRequestInfo = requestInfos.get(0);
                wrapper.setScheduleRequestInfo(scheduleRequestInfo);

                for (ScheduleRequestComponentInfo componentInfo : scheduleRequestInfo.getScheduleRequestComponents()) {
                    ScheduleWrapper scheduleWrapper = new ScheduleWrapper(componentInfo);
                    scheduleWrapper.setTba(componentInfo.getIsTBA());

                    List<TimeSlotInfo> timeSlotInfos = getSchedulingService().getTimeSlotsByIds(componentInfo.getTimeSlotIds(), contextInfo);

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

                        RoomInfo room = getRoomService().getRoom(componentInfo.getRoomIds().get(0), contextInfo);

                        scheduleWrapper.setRoom(room);
                        scheduleWrapper.setRoomCode(room.getRoomCode());

                        if (!room.getRoomUsages().isEmpty()){
                            scheduleWrapper.setRoomCapacity(room.getRoomUsages().get(0).getHardCapacity());
                        }

                        BuildingInfo buildingInfo = getRoomService().getBuilding(room.getBuildingId(), contextInfo);
                        scheduleWrapper.setBuilding(buildingInfo);
                        scheduleWrapper.setBuildingCode(buildingInfo.getBuildingCode());
                        scheduleWrapper.setBuildingId(room.getBuildingId());
                    }

                    wrapper.getRequestedScheduleComponents().add(scheduleWrapper);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void loadScheduleActuals(ActivityOfferingWrapper wrapper){

        if (wrapper.getAoInfo().getScheduleId() == null) return;

        try {

            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            ScheduleInfo scheduleInfo = getSchedulingService().getSchedule(wrapper.getAoInfo().getScheduleId(), contextInfo);
            wrapper.setScheduleInfo(scheduleInfo);

            //Clear Actuals first (it may be having old ones before schedules revised)
            wrapper.getActualScheduleComponents().clear();

            for (ScheduleComponentInfo componentInfo : scheduleInfo.getScheduleComponents()) {
                ScheduleWrapper scheduleWrapper = new ScheduleWrapper(componentInfo);
                scheduleWrapper.setTba(componentInfo.getIsTBA());

                List<TimeSlotInfo> timeSlotInfos = getSchedulingService().getTimeSlotsByIds(componentInfo.getTimeSlotIds(), contextInfo);

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

                    RoomInfo room = getRoomService().getRoom(componentInfo.getRoomId(), contextInfo);

                    scheduleWrapper.setRoom(room);
                    scheduleWrapper.setRoomCode(room.getRoomCode());

                    if (!room.getRoomUsages().isEmpty()){
                        scheduleWrapper.setRoomCapacity(room.getRoomUsages().get(0).getHardCapacity());
                    }

                    BuildingInfo buildingInfo = getRoomService().getBuilding(room.getBuildingId(), contextInfo);
                    scheduleWrapper.setBuilding(buildingInfo);
                    scheduleWrapper.setBuildingCode(buildingInfo.getBuildingCode());
                }

                wrapper.getActualScheduleComponents().add(scheduleWrapper);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * unwrap seatPoolWrapper. If the seatPoolWrapper is null or contains no seatPools, return null
     *
     * @param seatPoolWrappers list of SeatPoolWrappers to unwrap
     * @return list of SeatPoolDefinitionInfo objects derived from the wrappers
     */
    private List<SeatPoolDefinitionInfo> getSeatPoolDefinitions(List<SeatPoolWrapper> seatPoolWrappers)   {

        List<SeatPoolDefinitionInfo> spRet = new ArrayList<SeatPoolDefinitionInfo>();

        if(seatPoolWrappers != null){
            for(SeatPoolWrapper seatPoolWrapper : seatPoolWrappers){
                SeatPoolDefinitionInfo seatPool = seatPoolWrapper.getSeatPool();
                seatPool.setPopulationId(seatPoolWrapper.getSeatPoolPopulation().getId());
                spRet.add(seatPool);
            }
        }

        return spRet;
    }

    private String getTermDisplayString(String termId, TermInfo term) {
        // Return Term as String display like 'FALL 2020 (9/26/2020-12/26/2020)'
        StringBuilder    stringBuilder = new StringBuilder();
        Formatter        formatter     = new Formatter(stringBuilder, Locale.US);
        String           displayString = termId; // use termId as a default.
        if (term != null) {
            String           startDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getStartDate());
            String           endDate   = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getEndDate());
            String           termType  = term.getName();
            formatter.format("%s (%s to %s)", termType, startDate, endDate);
            displayString = stringBuilder.toString();
        }
        return displayString;
    }

    private void assembleInstructorWrapper(List<OfferingInstructorInfo> instructors, ActivityOfferingWrapper wrapper){
        if(instructors!= null && !instructors.isEmpty()){
            for(OfferingInstructorInfo instructor : instructors){
                OfferingInstructorWrapper instructorWrapper = new OfferingInstructorWrapper(instructor);
                if(instructor.getPercentageEffort() != null){
                    instructorWrapper.setsEffort(Integer.toString(instructor.getPercentageEffort().intValue()));
                }
                wrapper.getInstructors().add(instructorWrapper);
            }
        }
    }

    private void disassembleInstructorsWrapper(List<OfferingInstructorWrapper> instructors, ActivityOfferingInfo aoInfo){
        aoInfo.setInstructors(new ArrayList<OfferingInstructorInfo>());
        if(instructors!= null && !instructors.isEmpty()){
            for(OfferingInstructorWrapper instructor : instructors){
                aoInfo.getInstructors().add(disassembleInstructorWrapper(instructor));
            }
        }
    }

    private OfferingInstructorInfo disassembleInstructorWrapper(OfferingInstructorWrapper instructor){
        OfferingInstructorInfo instructorInfo = new OfferingInstructorInfo(instructor.getOfferingInstructorInfo());
        if(!StringUtils.isBlank(instructor.getsEffort())){
            instructorInfo.setPercentageEffort(new Float(instructor.getsEffort()));
        }


        if(StringUtils.isBlank(instructorInfo.getStateKey())) {
            try {
                StateInfo state = getStateService().getState(LprServiceConstants.TENTATIVE_STATE_KEY, ContextUtils.createDefaultContextInfo());
                instructorInfo.setStateKey(state.getKey());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return instructorInfo;
    }

    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper)document.getNewMaintainableObject().getDataObject();
        document.getDocumentHeader().setDocumentDescription("Activity Offering");
        try {
            StateInfo state = getStateService().getState(wrapper.getAoInfo().getStateKey(), ContextUtils.createDefaultContextInfo());
            wrapper.setStateName(state.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        super.processAfterAddLine(view, collectionGroup, model, addLine);

        if (addLine instanceof ScheduleComponentWrapper) {
            ScheduleComponentWrapper scheduleComponentWrapper = (ScheduleComponentWrapper)addLine;
            if ("1".equals(scheduleComponentWrapper.getAddDaysSpecifiedBoolean())) {
                if (null != scheduleComponentWrapper.getAddWeekDayOptions()) {
                    List<String> weekDayLabels = Arrays.asList("Su ","M ","T ","W ","Th ","F ","Sa ");
                    StringBuilder weekDays = new StringBuilder();
                    for (Integer day : scheduleComponentWrapper.getAddWeekDayOptions()) {
                        weekDays.append(weekDayLabels.get(day));
                    }
                    scheduleComponentWrapper.setWeekDays(weekDays.toString());
                }
            }
            else {
                scheduleComponentWrapper.setWeekDays("To Be Announced");
            }
            if (null != scheduleComponentWrapper.getAddRoomResources()) {
                StringBuilder resources = new StringBuilder();
                for (String resource : scheduleComponentWrapper.getAddRoomResources()) {
                    if (resources.length() > 0) {
                        resources.append(", ");
                    }
                    resources.append(resource);
                }
                scheduleComponentWrapper.setRoomFeatures(resources.toString());
            }
        }
        else if(addLine instanceof OfferingInstructorWrapper) {
            // set the person name if it's null, in the case of user-input personell id
            OfferingInstructorWrapper instructor = (OfferingInstructorWrapper) addLine;
            if (instructor.getOfferingInstructorInfo().getPersonName() == null && instructor.getOfferingInstructorInfo().getPersonId() != null) {
                List<Person> personList = ViewHelperUtil.getInstructorByPersonId(instructor.getOfferingInstructorInfo().getPersonId());
                if (personList.size() == 1) {
                    instructor.getOfferingInstructorInfo().setPersonName(personList.get(0).getName());
                }
            }
        }
    }

    @Override
    protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof OfferingInstructorWrapper){   //Personnel
            OfferingInstructorWrapper instructor = (OfferingInstructorWrapper) addLine;

            //check duplication
            MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
            List<OfferingInstructorWrapper> instructors = activityOfferingWrapper.getInstructors();
            if(instructors != null && !instructors.isEmpty()){
                for(OfferingInstructorWrapper thisInst : instructors){
                    if(instructor.getOfferingInstructorInfo().getPersonId().equals(thisInst.getOfferingInstructorInfo().getPersonId())){
                        GlobalVariables.getMessageMap().putErrorForSectionId("ao-personnelgroup", ActivityOfferingConstants.MSG_ERROR_INSTRUCTOR_DUPLICATE, instructor.getOfferingInstructorInfo().getPersonId());
                        return false;
                    }
                }
            }

            //validate ID
            List<Person> lstPerson = ViewHelperUtil.getInstructorByPersonId(instructor.getOfferingInstructorInfo().getPersonId());
            if(lstPerson == null || lstPerson.isEmpty()){
                GlobalVariables.getMessageMap().putErrorForSectionId("ao-personnelgroup", ActivityOfferingConstants.MSG_ERROR_INSTRUCTOR_NOTFOUND, instructor.getOfferingInstructorInfo().getPersonId());
                return false;
            }
        }
        else if (addLine instanceof SeatPoolWrapper){   //Seat Pool
            SeatPoolWrapper seatPool = (SeatPoolWrapper) addLine;
            //check duplication
            MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
            ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
            List<SeatPoolWrapper> pools = activityOfferingWrapper.getSeatpools();
            if(pools != null && !pools.isEmpty()){
                for (SeatPoolWrapper pool : pools ) {
                    if (seatPool.getSeatPoolPopulation().getId().equals( pool.getSeatPoolPopulation().getId())) {
                        GlobalVariables.getMessageMap().putErrorForSectionId("ao-seatpoolgroup", ActivityOfferingConstants.MSG_ERROR_SEATPOOL_DUPLICATE, pool.getSeatPoolPopulation().getName());
                        return false;
                    }
                }
            }
        }
        return super.performAddLineValidation(view, collectionGroup, model, addLine);
    }

    @Override
    protected void processBeforeAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof OfferingInstructorWrapper){
            OfferingInstructorWrapper instructor = (OfferingInstructorWrapper) addLine;
            instructor.setOfferingInstructorInfo(disassembleInstructorWrapper(instructor));
        }
    }

    @Override
    public void processCollectionDeleteLine(View view, Object model, String collectionPath, int lineIndex) {

        if (StringUtils.endsWith(collectionPath, "revisedScheduleRequestComponents")){
            ActivityOfferingForm form = (ActivityOfferingForm)model;
            if(form.isScheduleEditInProgress()){
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Editing a schedule request in progress. Please update it first before processing");
                return;
            }
            ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
            wrapper.setSchedulesRevised(true);
            wrapper.getRevisedScheduleRequestComponents().remove(lineIndex);
        }else {
            super.processCollectionDeleteLine(view,model,collectionPath,lineIndex);
        }
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return this.typeService;
    }

    public StateService getStateService() {
        if(stateService == null) {
            stateService = CourseOfferingResourceLoader.loadStateService();
        }
        return stateService;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }

    protected CourseOfferingSetService getCourseOfferingSetService(){
        if (courseOfferingSetService == null){
            courseOfferingSetService = CourseOfferingResourceLoader.loadCourseOfferingSetService();
        }
        return courseOfferingSetService;
    }

    private AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
            academicCalendarService = CourseOfferingResourceLoader.loadAcademicCalendarService();
        }

        return academicCalendarService;
    }

    private PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return populationService;
    }

    public SchedulingService getSchedulingService() {
        if(schedulingService == null){
            schedulingService =  CourseOfferingResourceLoader.loadSchedulingService();
        }
        return schedulingService;
    }

    public RoomService getRoomService(){
        if (roomService == null){
            roomService = CourseOfferingResourceLoader.loadRoomService();
        }
        return roomService;
    }

}