package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentScheduleActivityOfferingResult", propOrder = {
        "activityOfferingId", "activityOfferingTypeName", "activityOfferingType", "scheduleComponents"})
public class StudentScheduleActivityOfferingResult {
    private String activityOfferingId;
    private String activityOfferingTypeName;
    private String activityOfferingType;
    private List<ActivityOfferingScheduleComponentResult> scheduleComponents;
    private List<InstructorSearchResult> instructors;

    public String getActivityOfferingId() {
        return activityOfferingId;
    }

    public void setActivityOfferingId(String activityOfferingId) {
        this.activityOfferingId = activityOfferingId;
    }

    public String getActivityOfferingTypeName() {
        return activityOfferingTypeName;
    }

    public void setActivityOfferingTypeName(String activityOfferingTypeName) {
        this.activityOfferingTypeName = activityOfferingTypeName;
    }

    public String getActivityOfferingType() {
        return activityOfferingType;
    }

    public void setActivityOfferingType(String activityOfferingType) {
        this.activityOfferingType = activityOfferingType;
    }

    public List<ActivityOfferingScheduleComponentResult> getScheduleComponents() {
        if (scheduleComponents == null) {
            scheduleComponents = new ArrayList<ActivityOfferingScheduleComponentResult>();
        }
        return scheduleComponents;
    }

    public void setScheduleComponents(List<ActivityOfferingScheduleComponentResult> scheduleComponents) {
        this.scheduleComponents = scheduleComponents;
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

}
