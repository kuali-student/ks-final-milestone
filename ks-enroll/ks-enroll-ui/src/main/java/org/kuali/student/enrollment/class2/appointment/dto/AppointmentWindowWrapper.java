package org.kuali.student.enrollment.class2.appointment.dto;

import org.kuali.student.enrollment.class2.acal.dto.TimeSetWrapper;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;


public class AppointmentWindowWrapper extends TimeSetWrapper {
    AppointmentWindowInfo appointmentWindowInfo;
    String periodName;
    String periodKey;
    String assignedPopulationName;
    private String windowTypeKey;

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
}
