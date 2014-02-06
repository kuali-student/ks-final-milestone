package org.kuali.student.enrollment.registration.client.service.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingScheduleResult", propOrder = {
        "activityOfferingType", "location", "time"})
public class ActivityOfferingScheduleResult {
    private String activityOfferingType;
    private ScheduleLocationResult location;
    private ScheduleTimeResult time;

    public String getActivityOfferingType() { return activityOfferingType; }

    public void setActivityOfferingType(String activityOfferingType) { this.activityOfferingType = activityOfferingType; }

    public ScheduleLocationResult getLocation() { return location; }

    public void setLocation(ScheduleLocationResult location) { this.location = location; }

    public ScheduleTimeResult getTime() { return time; }

    public void setTime(ScheduleTimeResult time) { this.time = time; }
}
