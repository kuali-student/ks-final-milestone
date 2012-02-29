package org.kuali.student.enrollment.class2.acal.dto;

import org.kuali.student.enrollment.acal.dto.KeyDateInfo;

import java.util.Date;

public class KeyDateWrapper {

    private String keyDateType;
    private String endTimeAmPm;
    private String startTimeAmPm;
    private Date startDate;
    private Date endDate;
    private String startTime;
    private String endTime;
    private boolean allDay = false;
    private boolean dateRange = true;

    private KeyDateInfo keyDateInfo;

    public KeyDateWrapper(){
    }

    public String getKeyDateType() {
        return keyDateType;
    }

    public void setKeyDateType(String keyDateType) {
        this.keyDateType = keyDateType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTimeAmPm() {
        return endTimeAmPm;
    }

    public void setEndTimeAmPm(String endTimeAmPm) {
        this.endTimeAmPm = endTimeAmPm;
    }

    public String getStartTimeAmPm() {
        return startTimeAmPm;
    }

    public void setStartTimeAmPm(String startTimeAmPm) {
        this.startTimeAmPm = startTimeAmPm;
    }

    public KeyDateInfo getKeyDateInfo() {
        return keyDateInfo;
    }

    public void setKeyDateInfo(KeyDateInfo keyDateInfo) {
        this.keyDateInfo = keyDateInfo;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public boolean isDateRange() {
        return dateRange;
    }

    public void setDateRange(boolean dateRange) {
        this.dateRange = dateRange;
    }
}
