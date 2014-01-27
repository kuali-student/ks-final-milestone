package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentScheduleActivityOfferingResult", propOrder = {
        "activityOfferingId", "activityOfferingTypeShortName", "activityOfferingType", "scheduleComponents"})
public class StudentScheduleActivityOfferingResult {
    private String activityOfferingId;
    private String activityOfferingTypeShortName;
    private String activityOfferingType;
    private List<ActivityOfferingScheduleComponentResult> scheduleComponents;
    private List<InstructorSearchResult> instructors;

    public String getActivityOfferingId() {
        return activityOfferingId;
    }

    public void setActivityOfferingId(String activityOfferingId) {
        this.activityOfferingId = activityOfferingId;
    }

    public String getActivityOfferingTypeShortName() {
        return activityOfferingTypeShortName;
    }

    public void setActivityOfferingTypeShortName(String activityOfferingTypeShortName) {
        this.activityOfferingTypeShortName = activityOfferingTypeShortName;
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
