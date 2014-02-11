package org.kuali.student.enrollment.registration.client.service.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingLocationTimeResult", propOrder = {
        "activityOfferingType", "activityOfferingLocationTime"})
public class ActivityOfferingScheduleResult {
    private String activityOfferingType;
    private List<ActivityOfferingLocationTimeResult> activityOfferingLocationTime;

    public String getActivityOfferingType() {
        return activityOfferingType;
    }

    public void setActivityOfferingType(String activityOfferingType) {
        this.activityOfferingType = activityOfferingType;
    }

    public List<ActivityOfferingLocationTimeResult> getActivityOfferingLocationTime() {
        if (activityOfferingLocationTime == null) {
            activityOfferingLocationTime = new ArrayList<ActivityOfferingLocationTimeResult>();
        }
        return activityOfferingLocationTime;
    }

    public void setActivityOfferingLocationTime(List<ActivityOfferingLocationTimeResult> activityOfferingLocationTime) {
        this.activityOfferingLocationTime = activityOfferingLocationTime;
    }
}
