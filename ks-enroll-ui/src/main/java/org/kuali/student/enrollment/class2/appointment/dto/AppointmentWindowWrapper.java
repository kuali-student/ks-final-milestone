package org.kuali.student.enrollment.class2.appointment.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.class2.acal.dto.TimeSetWrapper;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;

import java.util.Date;

public class AppointmentWindowWrapper extends TimeSetWrapper {
    AppointmentWindowInfo appointmentWindowInfo;
    //termType and termYear fields are used for AppointmentWindowWrapper lookup view only
    String termType;
    String termYear;

    String id;
    String windowName;
    String periodName;
    String periodKey;
    String assignedPopulationName;
    String windowTypeKey;
    String windowTypeName;

    //Assignment Info -- for inquiry view
    Integer numberOfStudents;
    Integer numberOfSlots;
    Float meanStudentsPerSlot;

    String firstSlotPopulated;
    String lastSlotPopulated;

    Date assignmentsCreated;
    
    //Appt rule type
    String slotRuleEnumType;
    
    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getTermYear() {
        return termYear;
    }

    public void setTermYear(String termYear) {
        this.termYear = termYear;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWindowName() {
        return windowName;
    }

    public void setWindowName(String windowName) {
        this.windowName = windowName;
    }

    public AppointmentWindowWrapper() {
        appointmentWindowInfo = new AppointmentWindowInfo();
    }

    public AppointmentWindowInfo getAppointmentWindowInfo() {
        return appointmentWindowInfo;
    }

    public void setAppointmentWindowInfo(AppointmentWindowInfo appointmentWindowInfo) {
        this.appointmentWindowInfo = appointmentWindowInfo;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getPeriodKey() {
        return periodKey;
    }

    public void setPeriodKey(String periodKey) {
        this.periodKey = periodKey;
    }

    public String getAssignedPopulationName() {
        return assignedPopulationName;
    }

    public void setAssignedPopulationName(String assignedPopulationName) {
        this.assignedPopulationName = assignedPopulationName;
    }

    public String getWindowTypeKey() {
        return windowTypeKey;
    }

    public void setWindowTypeKey(String windowTypeKey) {
        this.windowTypeKey = windowTypeKey;
    }

    public String getWindowTypeName() {
        return windowTypeName;
    }

    public void setWindowTypeName(String windowTypeName) {
        this.windowTypeName = windowTypeName;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(Integer numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public Integer getNumberOfSlots() {
        return numberOfSlots;
    }

    public void setNumberOfSlots(Integer numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
    }

    public Float getMeanStudentsPerSlot() {
        return meanStudentsPerSlot;
    }

    public void setMeanStudentsPerSlot(Float meanStudentsPerSlot) {
        this.meanStudentsPerSlot = meanStudentsPerSlot;
    }

    public String getLastSlotPopulated() {
        return lastSlotPopulated;
    }

    public void setLastSlotPopulated(String lastSlotPopulated) {
        this.lastSlotPopulated = lastSlotPopulated;
    }

    public Date getAssignmentsCreated() {
        return assignmentsCreated;
    }

    public void setAssignmentsCreated(Date assignmentsCreated) {
        this.assignmentsCreated = assignmentsCreated;
    }
    
    public void setSlotRuleEnumType(String slotRuleEnumType){
        this.slotRuleEnumType = slotRuleEnumType;
    }
    
    public String getSlotRuleEnumType() {
        return slotRuleEnumType;
    }

    public String getFirstSlotPopulated() {
        return firstSlotPopulated;
    }

    public void setFirstSlotPopulated(String firstSlotPopulated) {
        this.firstSlotPopulated = firstSlotPopulated;
    }

    //This is for UI display purpose
    public String getStartDateUI(){
        if (getStartDate() != null) {
           return DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(getStartDate());

        }else{
            return StringUtils.EMPTY;
        }

    }

    //This is for UI display purpose
    public String getEndDateUI(){
        if (endDateUI != null) {
            return DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(endDateUI);
        }else{
            return StringUtils.EMPTY;
        }

    }

}
