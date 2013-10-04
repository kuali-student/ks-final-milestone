package org.kuali.student.enrollment.class1.timeslot.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.common.util.KSCollectionUtils;
import org.kuali.student.enrollment.class1.timeslot.dto.TimeSlotWrapper;
import org.kuali.student.enrollment.class1.timeslot.form.TimeSlotForm;
import org.kuali.student.enrollment.class1.timeslot.service.TimeSlotViewHelperService;
import org.kuali.student.enrollment.class1.util.WeekDaysDtoAndUIConversions;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * View helper for Manage Time Slots.
 */
public class TimeSlotViewHelperServiceImpl
        extends KSViewHelperServiceImpl implements TimeSlotViewHelperService {

    private ContextInfo contextInfo;
    private transient SchedulingService schedulingService;

    @Override
    public List<TimeSlotWrapper> findTimeSlots(List<String> timeSlotTypes)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        List<TimeSlotWrapper> timeSlotWrappers = new ArrayList<TimeSlotWrapper>();

        for (String tsTypeKey : timeSlotTypes) {
            Date timeForDisplay;

            List<String> timeSlotIds = getSchedulingService().getTimeSlotIdsByType(tsTypeKey, getContextInfo());
            List<TimeSlotInfo> timeSlotInfos = getSchedulingService().getTimeSlotsByIds(timeSlotIds, getContextInfo());

            for (TimeSlotInfo timeSlotInfo : timeSlotInfos) {
                TimeSlotWrapper tsWrapper = new TimeSlotWrapper();
                tsWrapper.setTimeSlotInfo(timeSlotInfo);
                if (timeSlotInfo.getStartTime().getMilliSeconds() != null) {
                    timeForDisplay = new Date(timeSlotInfo.getStartTime().getMilliSeconds());
                    tsWrapper.setStartTimeDisplay(DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay));
                }

                if (timeSlotInfo.getEndTime().getMilliSeconds() != null) {
                    timeForDisplay = new Date(timeSlotInfo.getEndTime().getMilliSeconds());
                    tsWrapper.setEndTimeDisplay(DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay));
                }

                String daysUI = WeekDaysDtoAndUIConversions.buildDaysForUI(timeSlotInfo.getWeekdays());
                tsWrapper.setDaysDisplayName(daysUI);
                tsWrapper.setTypeKey(timeSlotInfo.getTypeKey());
                if (timeSlotInfo.getTypeKey() != null) {
                    TypeInfo typeInfo = getTypeService().getType(timeSlotInfo.getTypeKey(), contextInfo);
                    tsWrapper.setTypeName(typeInfo.getName());
                }

                // convert typeKey to displayable typeName

                timeSlotWrappers.add(tsWrapper);
            }
        }

        return timeSlotWrappers;
    }

    public void createTimeSlot(TimeSlotForm form) throws Exception {

        TimeSlotWrapper newTSWrapper = new TimeSlotWrapper();

        TimeSlotInfo newTSInfo = new TimeSlotInfo();

        buildTimeSlotInfo(form,newTSInfo);

        TimeSlotInfo createdTimeSlot = getSchedulingService().createTimeSlot(form.getAddOrEditTermKey(),newTSInfo, createContextInfo());

        form.getTimeSlotResults().add(newTSWrapper);
        newTSWrapper.setTimeSlotInfo(createdTimeSlot);
        initializeTimeSlotWrapper(form,newTSWrapper);

    }

    public void updateTimeSlot(TimeSlotForm form,TimeSlotWrapper tsWrapper) throws Exception {

        buildTimeSlotInfo(form,tsWrapper.getTimeSlotInfo());

        TimeSlotInfo updatedTimeSlot = getSchedulingService().updateTimeSlot(tsWrapper.getTimeSlotInfo().getId(),tsWrapper.getTimeSlotInfo(),createContextInfo());

        if (form.getTermTypeSelections().contains(updatedTimeSlot.getTypeKey())){
            tsWrapper.setTimeSlotInfo(updatedTimeSlot);
            initializeTimeSlotWrapper(form,tsWrapper);
        } else {
            form.getTimeSlotResults().remove(tsWrapper);
        }

    }

    @Override
    public void deleteTimeSlots(TimeSlotForm form) throws Exception {
        List<TimeSlotWrapper> timeSlotWrappers = form.getTimeSlotResults();
        List<TimeSlotWrapper> selectedTimeSlots = form.getSelectedTimeSlots();

        selectedTimeSlots.clear();

        for(TimeSlotWrapper tsWrapper : timeSlotWrappers) {
            if(tsWrapper.isEnableDeleteButton() && tsWrapper.getIsChecked()) {
                selectedTimeSlots.add(tsWrapper);
                StatusInfo statusInfo = getSchedulingService().deleteTimeSlot(tsWrapper.getTimeSlotInfo().getId(), contextInfo);

            }
        }
    }

    public boolean isUniqueTimeSlot(TimeSlotForm form) throws Exception {
        return isUniqueTimeSlot(form,null);
    }

    public boolean isUniqueTimeSlot(TimeSlotForm form,TimeSlotInfo skipTS) throws Exception {

        List<Integer> days = WeekDaysDtoAndUIConversions.buildDaysForDTO(form.getAddOrEditDays());
        long startTime = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(form.getAddOrEditStartTime() + " " + form.getAddOrEditStartTimeAmPm()).getTime();
        TimeOfDayInfo startTimeOfDayInfo = new TimeOfDayInfo();
        startTimeOfDayInfo.setMilliSeconds(startTime);

        long endTime = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(form.getAddOrEditEndTime() + " " + form.getAddOrEditEndTimeAmPm()).getTime();
        TimeOfDayInfo endTimeOfDayInfo = new TimeOfDayInfo();
        endTimeOfDayInfo.setMilliSeconds(endTime);

        List<TimeSlotInfo> exisitingTS = getSchedulingService().getTimeSlotsByDaysAndStartTimeAndEndTime(form.getAddOrEditTermKey(),days,startTimeOfDayInfo,endTimeOfDayInfo,createContextInfo());

        if (exisitingTS.size() == 1 && skipTS != null){
            TimeSlotInfo ts = KSCollectionUtils.getRequiredZeroElement(exisitingTS);
            if (StringUtils.equals(ts.getId(),skipTS.getId())){
                return true;
            } else {
                return false;
            }
        } else {
            return exisitingTS.isEmpty();
        }

    }

    protected void buildTimeSlotInfo(TimeSlotForm form,TimeSlotInfo tsInfo){

        tsInfo.setStateKey(SchedulingServiceConstants.TIME_SLOT_STATE_ACTIVE);
        List<Integer> days = WeekDaysDtoAndUIConversions.buildDaysForDTO(form.getAddOrEditDays());
        tsInfo.setWeekdays(days);

        long time = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(form.getAddOrEditStartTime() + " " + form.getAddOrEditStartTimeAmPm()).getTime();
        TimeOfDayInfo timeOfDayInfo = new TimeOfDayInfo();
        timeOfDayInfo.setMilliSeconds(time);
        tsInfo.setStartTime(timeOfDayInfo);

        time = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(form.getAddOrEditEndTime() + " " + form.getAddOrEditEndTimeAmPm()).getTime();
        timeOfDayInfo = new TimeOfDayInfo();
        timeOfDayInfo.setMilliSeconds(time);
        tsInfo.setEndTime(timeOfDayInfo);
        tsInfo.setTypeKey(form.getAddOrEditTermKey());

    }

    protected void initializeTimeSlotWrapper(TimeSlotForm form,TimeSlotWrapper tsWrapper){
        Date timeForDisplay;

        String daysUI = WeekDaysDtoAndUIConversions.buildDaysForUI(tsWrapper.getTimeSlotInfo().getWeekdays());
        tsWrapper.setDaysDisplayName(daysUI);
        tsWrapper.setEnableDeleteButton(true);
        timeForDisplay = new Date(tsWrapper.getTimeSlotInfo().getStartTime().getMilliSeconds());
        tsWrapper.setStartTimeDisplay(DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay));
        timeForDisplay = new Date(tsWrapper.getTimeSlotInfo().getEndTime().getMilliSeconds());
        tsWrapper.setEndTimeDisplay(DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay));
        TypeInfo type = getTypeInfo(form.getAddOrEditTermKey());
        tsWrapper.setTypeName(type.getName());

        form.setAddOrEditDays("");
        form.setAddOrEditEndTime("");
        form.setAddOrEditEndTimeAmPm("");
        form.setAddOrEditStartTime("");
        form.setAddOrEditStartTimeAmPm("");
        form.setAddOrEditTermKey("");
    }

    private ContextInfo getContextInfo() {
        if (contextInfo == null) {
            contextInfo = ContextUtils.createDefaultContextInfo();
        }
        return contextInfo;
    }

    protected SchedulingService getSchedulingService() {
        if(schedulingService == null) {
            schedulingService = CourseOfferingResourceLoader.loadSchedulingService();
        }
        return schedulingService;
    }
}
