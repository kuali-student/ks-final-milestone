package org.kuali.student.enrollment.class1.timeslot.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.enrollment.class1.timeslot.dto.TimeSlotWrapper;
import org.kuali.student.enrollment.class1.timeslot.service.TimeSlotViewHelperService;
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
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

                String daysUI = buildDaysForUI(timeSlotInfo.getWeekdays());
                tsWrapper.setDaysDisplayName(daysUI);

                timeSlotWrappers.add(tsWrapper);
            }
        }
        // add some test data
        TimeSlotInfo timeSlotInfo = new TimeSlotInfo();
        timeSlotInfo.setName("A");
        timeSlotInfo.setTypeKey("type1");
        Integer[] days = {0, 1, 2};
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

        String daysUI = buildDaysForUI(timeSlotInfo.getWeekdays());
        wrapper.setDaysDisplayName(daysUI);

        timeSlotWrappers.add(wrapper);

        return timeSlotWrappers;
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
