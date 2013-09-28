package org.kuali.student.enrollment.class1.timeslot.service.impl;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.enrollment.class1.timeslot.dto.TimeSlotWrapper;
import org.kuali.student.enrollment.class1.timeslot.service.TimeSlotViewHelperService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * View helper for Manage Time Slots.
 */
public class TimeSlotViewHelperServiceImpl
        extends KSViewHelperServiceImpl implements TimeSlotViewHelperService {

    private ContextInfo contextInfo;

    @Override
    public List<TimeSlotWrapper> findTimeSlots(List<String> timeSlotTypes) {
        List<TimeSlotWrapper> timeSlotWrappers = new ArrayList<TimeSlotWrapper>();

        TimeSlotInfo timeSlotInfo = new TimeSlotInfo();
        timeSlotInfo.setName("A");
        timeSlotInfo.setTypeKey("type1");
        Integer[] days = {0,1,2};
        timeSlotInfo.setWeekdays(Arrays.asList(days));
        TimeOfDayInfo timeOfDayInfo1 = new TimeOfDayInfo();
        timeOfDayInfo1.setMilliSeconds(1000L);
        timeSlotInfo.setStartTime(timeOfDayInfo1);
        TimeOfDayInfo timeOfDayInfo2 = new TimeOfDayInfo();
        timeOfDayInfo2.setMilliSeconds(5000L);
        timeSlotInfo.setStartTime(timeOfDayInfo2);

        TimeSlotWrapper wrapper = new TimeSlotWrapper();
        wrapper.setTimeSlotInfo(timeSlotInfo);

        timeSlotWrappers.add(wrapper);

        return timeSlotWrappers;
    }

    private ContextInfo getContextInfo() {
        if (contextInfo == null) {
            contextInfo = ContextUtils.createDefaultContextInfo();
        }
        return contextInfo;
    }
}
