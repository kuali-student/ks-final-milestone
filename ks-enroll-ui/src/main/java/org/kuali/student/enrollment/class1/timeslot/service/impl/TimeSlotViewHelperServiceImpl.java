package org.kuali.student.enrollment.class1.timeslot.service.impl;

import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.enrollment.class1.timeslot.dto.TimeSlotWrapper;
import org.kuali.student.enrollment.class1.timeslot.form.TimeSlotForm;
import org.kuali.student.enrollment.class1.timeslot.service.TimeSlotViewHelperService;
import org.kuali.student.enrollment.class1.util.WeekDaysDtoAndUIConversions;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
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
                     if(timeSlotInfo.getTypeKey() != null) {
                        TypeInfo typeInfo = getTypeService().getType(timeSlotInfo.getTypeKey(), contextInfo);
                         tsWrapper.setTypeName(typeInfo.getName());
                     }

                     // convert typeKey to displayable typeName

                     timeSlotWrappers.add(tsWrapper);
                 }
             }
             // add some test data
             TimeSlotInfo timeSlotInfo = new TimeSlotInfo();
             timeSlotInfo.setName("A");
             timeSlotInfo.setTypeKey("SummerFull");
             Integer[] days = {2, 4, 6};
             timeSlotInfo.setWeekdays(Arrays.asList(days));
             TimeOfDayInfo timeOfDayInfo1 = new TimeOfDayInfo();
             timeOfDayInfo1.setMilliSeconds(1000L);
             timeSlotInfo.setStartTime(timeOfDayInfo1);
             TimeOfDayInfo timeOfDayInfo2 = new TimeOfDayInfo();
             timeOfDayInfo2.setMilliSeconds(8925000L);
             timeSlotInfo.setEndTime(timeOfDayInfo2);

             TimeSlotWrapper wrapper = new TimeSlotWrapper();
             wrapper.setTimeSlotInfo(timeSlotInfo);
             Date timeForDisplay;
             if (timeSlotInfo.getStartTime().getMilliSeconds() != null) {
                 timeForDisplay = new Date(timeSlotInfo.getStartTime().getMilliSeconds());
                 wrapper.setStartTimeDisplay(DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay));
             }

             if (timeSlotInfo.getEndTime().getMilliSeconds() != null) {
                 timeForDisplay = new Date(timeSlotInfo.getEndTime().getMilliSeconds());
                 wrapper.setEndTimeDisplay(DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay));
             }

             String daysUI = WeekDaysDtoAndUIConversions.buildDaysForUI(timeSlotInfo.getWeekdays());
             wrapper.setDaysDisplayName(daysUI);
             wrapper.setTypeKey(timeSlotInfo.getTypeKey());
             wrapper.setTypeName(timeSlotInfo.getTypeKey());
             timeSlotWrappers.add(wrapper);

             return timeSlotWrappers;
         }

    public void createTimeSlot(TimeSlotForm form) throws Exception {

        TimeSlotWrapper newTSWrapper = new TimeSlotWrapper();

        TimeSlotInfo newTSInfo = new TimeSlotInfo();
        newTSInfo.setTypeKey(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD);
        newTSInfo.setStateKey(SchedulingServiceConstants.TIME_SLOT_STATE_ACTIVE);
        List<Integer> days = WeekDaysDtoAndUIConversions.buildDaysForDTO(form.getAddOrEditDays());
        newTSInfo.setWeekdays(days);

        long time = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(form.getAddOrEditStartTime() + " " + form.getAddOrEditStartTimeAmPm()).getTime();
        TimeOfDayInfo timeOfDayInfo = new TimeOfDayInfo();
        timeOfDayInfo.setMilliSeconds(time);
        newTSInfo.setStartTime(timeOfDayInfo);

        time = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(form.getAddOrEditEndTime() + " " + form.getAddOrEditEndTimeAmPm()).getTime();
        timeOfDayInfo = new TimeOfDayInfo();
        timeOfDayInfo.setMilliSeconds(time);
        newTSInfo.setStartTime(timeOfDayInfo);

        TimeSlotInfo createdTimeSlot = getSchedulingService().createTimeSlot(SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_STANDARD,newTSInfo, createContextInfo());
        newTSWrapper.setTimeSlotInfo(createdTimeSlot);
        newTSWrapper.setDaysDisplayName(form.getAddOrEditDays());
        newTSWrapper.setEnableDeleteButton(true);
        newTSWrapper.setStartTimeDisplay(form.getAddOrEditStartTime() + " " + form.getAddOrEditStartTimeAmPm());
        newTSWrapper.setEndTimeDisplay(form.getAddOrEditEndTime() + " " + form.getAddOrEditEndTimeAmPm());
        newTSWrapper.setTypeName(form.getAddOrEditTermKey());//Get the name
        form.getTimeSlotResults().add(0, newTSWrapper);

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
