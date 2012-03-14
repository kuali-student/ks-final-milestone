package org.kuali.student.enrollment.class2.acal.dto;

import org.joda.time.DateTime;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    private String keyDateNameUI;

    private KeyDateInfo keyDateInfo;
    private TypeInfo typeInfo;

    public KeyDateWrapper(){
    }

    public KeyDateWrapper(KeyDateInfo keydate){
        this.setKeyDateInfo(keydate);
        this.setStartDate(keydate.getStartDate());
        this.setEndDate(keydate.getEndDate());
        this.setAllDay(keydate.getIsAllDay());
        this.setDateRange(keydate.getIsDateRange());
        this.setKeyDateType(keydate.getTypeKey());

        if (!this.isAllDay()){
            DateFormat dfm = new SimpleDateFormat("hh:mm");

            startTime = dfm.format(keydate.getStartDate());
            if (this.isDateRange()){
                endTime = dfm.format(keydate.getEndDate());
            }

            dfm = new SimpleDateFormat("a");
            startTimeAmPm = dfm.format(keydate.getStartDate());
            if (this.isDateRange()){
                endTimeAmPm = dfm.format(keydate.getEndDate());
            }
        }
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

    public String getKeyDateNameUI() {
        return keyDateNameUI;
    }

    public void setKeyDateNameUI(String keyDateNameUI) {
        this.keyDateNameUI = keyDateNameUI;
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }
}
