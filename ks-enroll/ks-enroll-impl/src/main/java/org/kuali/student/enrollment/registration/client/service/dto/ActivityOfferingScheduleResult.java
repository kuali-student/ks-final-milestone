package org.kuali.student.enrollment.registration.client.service.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingLocationTimeResult", propOrder = {
        "activityOfferingId", "activityOfferingType", "activityOfferingTypeName", "activityOfferingLocationTime", "instructors"})
public class ActivityOfferingScheduleResult {

    private String activityOfferingId;
    private String activityOfferingType;
    private String activityOfferingTypeName;
    private List<ActivityOfferingLocationTimeResult> activityOfferingLocationTime;
    private List<InstructorSearchResult> instructors;

    public String getActivityOfferingId() { return activityOfferingId; }

    public void setActivityOfferingId(String activityOfferingId) { this.activityOfferingId = activityOfferingId; }

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

    public List<InstructorSearchResult> getInstructors() {
        if (instructors == null) {
            instructors = new ArrayList<InstructorSearchResult>();
        }
        return instructors;
    }

    public void setInstructors(List<InstructorSearchResult> instructors) {
        this.instructors = instructors;
    }

    public String getActivityOfferingTypeName() {
        return activityOfferingTypeName;
    }

    public void setActivityOfferingTypeName(String activityOfferingTypeName) {
        this.activityOfferingTypeName = activityOfferingTypeName;
    }
}
