package org.kuali.student.enrollment.registration.client.service.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingLocationTimeResult", propOrder = {
        "activityOfferingType", "activityOfferingLocationTime"})
public class ActivityOfferingScheduleResult {
    private String activityOfferingType;
    private List<ActivityOfferingLocationTimeResult> activityOfferingLocationTime;

    public String getActivityOfferingType() { return activityOfferingType; }

    public void setActivityOfferingType(String activityOfferingType) { this.activityOfferingType = activityOfferingType; }

    public List<ActivityOfferingLocationTimeResult> getActivityOfferingLocationTime() { return activityOfferingLocationTime; }

    public void setActivityOfferingLocationTime(List<ActivityOfferingLocationTimeResult> activityOfferingLocationTime) { this.activityOfferingLocationTime = activityOfferingLocationTime; }
}
