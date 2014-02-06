package org.kuali.student.enrollment.registration.client.service.dto;


import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleTimeResult", propOrder = {
        "days", "startTime", "endTime"})
public class ScheduleTimeResult {
    private String days;
    private String startTime;
    private String endTime;

    public String getDays() { return days; }

    public void setDays(String days) { this.days = days; }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) { this.endTime = endTime; }
}
