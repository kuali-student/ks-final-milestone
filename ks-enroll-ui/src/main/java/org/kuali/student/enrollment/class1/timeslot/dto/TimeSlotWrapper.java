package org.kuali.student.enrollment.class1.timeslot.dto;

import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.io.Serializable;

/**
 * Wrapper class for managing {@link TimeSlotInfo}s.
 */
public class TimeSlotWrapper implements Serializable {
    private TimeSlotInfo timeSlotInfo;

    private boolean isChecked;

    private String typeName;
    private String typeKey;

    private String startTimeDisplay = "";
    private String endTimeDisplay = "";
    private String daysDisplayName = "";

    //hidden columns for toolbar
    private boolean enableDeleteButton = true;

    public TimeSlotWrapper() {
        timeSlotInfo = new TimeSlotInfo();
    }

    public TimeSlotInfo getTimeSlotInfo() {
        return timeSlotInfo;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean checked) {
        this.isChecked = checked;
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

    public String getStartTimeDisplay() {
        return startTimeDisplay;
    }

    public void setStartTimeDisplay(String startTimeDisplay) {
        this.startTimeDisplay = startTimeDisplay;
    }

    public String getEndTimeDisplay() {
        return endTimeDisplay;
    }

    public void setEndTimeDisplay(String endTimeDisplay) {
        this.endTimeDisplay = endTimeDisplay;
    }

    public String getDaysDisplayName() {
        return daysDisplayName;
    }

    public void setDaysDisplayName(String daysDisplayName) {
        this.daysDisplayName = daysDisplayName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public boolean isEnableDeleteButton() {
        return enableDeleteButton;
    }

    public void setEnableDeleteButton(boolean enableDeleteButton) {
        this.enableDeleteButton = enableDeleteButton;
    }
}
