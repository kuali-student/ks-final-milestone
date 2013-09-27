package org.kuali.student.enrollment.class1.timeslot.dto;

import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

/**
 * Wrapper class for managing {@link TimeSlotInfo}s.
 *
 * NOTICE: Look at the Edit AO / Edit Delivery logistics code for how to deal with the display fields.
 *
 */
public class TimeSlotWrapper {
    private TimeSlotInfo timeSlotInfo;

    public TimeSlotInfo getTimeSlotInfo() {
        return timeSlotInfo;
    }

    public void setTimeSlotInfo(TimeSlotInfo timeSlotInfo) {
        this.timeSlotInfo = timeSlotInfo;
    }

    public String getDaysPattern() {
        return timeSlotInfo.getWeekdays().toString();
    }

    public String getStartTime() {
        return timeSlotInfo.getStartTime().toString();
    }

    public String getEndTime() {
        return timeSlotInfo.getEndTime().toString();
    }
}
