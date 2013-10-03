package org.kuali.student.enrollment.class1.timeslot.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
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

        // add test records
        if(timeSlotTypes.size() > 0 && timeSlotWrappers.size() == 0) {
            for(String tsTypeKey : timeSlotTypes) {
                TypeInfo typeInfo = getTypeService().getType(tsTypeKey, contextInfo);
                TimeSlotWrapper tsWrapper = createTimeSlotTestRecord(tsTypeKey, typeInfo.getName(), "test" + typeInfo.getName());
                timeSlotWrappers.add(tsWrapper);
            }
        }

        return timeSlotWrappers;
    }

    public boolean isUniqueTimeSlot(TimeSlotForm form) throws Exception {

        List<Integer> days = WeekDaysDtoAndUIConversions.buildDaysForDTO(form.getAddOrEditDays());
        long startTime = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(form.getAddOrEditStartTime() + " " + form.getAddOrEditStartTimeAmPm()).getTime();
        TimeOfDayInfo startTimeOfDayInfo = new TimeOfDayInfo();
        startTimeOfDayInfo.setMilliSeconds(startTime);

        long endTime = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(form.getAddOrEditEndTime() + " " + form.getAddOrEditEndTimeAmPm()).getTime();
        TimeOfDayInfo endTimeOfDayInfo = new TimeOfDayInfo();
        endTimeOfDayInfo.setMilliSeconds(endTime);

        List<TimeSlotInfo> ts = getSchedulingService().getTimeSlotsByDaysAndStartTimeAndEndTime(form.getAddOrEditTermKey(),days,startTimeOfDayInfo,endTimeOfDayInfo,createContextInfo());

        return ts.isEmpty();
    }

    public void createTimeSlot(TimeSlotForm form) throws Exception {

        TimeSlotWrapper newTSWrapper = new TimeSlotWrapper();

        TimeSlotInfo newTSInfo = new TimeSlotInfo();
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
        newTSInfo.setEndTime(timeOfDayInfo);
        newTSInfo.setTypeKey(form.getAddOrEditTermKey());

        TimeSlotInfo createdTimeSlot = getSchedulingService().createTimeSlot(form.getAddOrEditTermKey(),newTSInfo, createContextInfo());
        newTSWrapper.setTimeSlotInfo(createdTimeSlot);
        newTSWrapper.setDaysDisplayName(StringUtils.upperCase(form.getAddOrEditDays()));
        newTSWrapper.setEnableDeleteButton(true);
        newTSWrapper.setStartTimeDisplay(form.getAddOrEditStartTime() + " " + form.getAddOrEditStartTimeAmPm());
        newTSWrapper.setEndTimeDisplay(form.getAddOrEditEndTime() + " " + form.getAddOrEditEndTimeAmPm());
        TypeInfo type = getTypeInfo(form.getAddOrEditTermKey());
        newTSWrapper.setTypeName(type.getName());
        form.getTimeSlotResults().add(newTSWrapper);

        form.setAddOrEditDays("");
        form.setAddOrEditEndTime("");
        form.setAddOrEditEndTimeAmPm("");
        form.setAddOrEditStartTime("");
        form.setAddOrEditStartTimeAmPm("");
        form.setAddOrEditTermKey("");

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

    private TimeSlotWrapper createTimeSlotTestRecord(String typeKey, String typeName, String code) {
        // add some test data
        TimeSlotInfo timeSlotInfo = new TimeSlotInfo();
        timeSlotInfo.setName(code);
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
        wrapper.setTypeKey(typeKey);
        wrapper.setTypeName(typeName);

        return wrapper;
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
