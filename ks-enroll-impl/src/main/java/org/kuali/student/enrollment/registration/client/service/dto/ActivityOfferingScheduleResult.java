package org.kuali.student.enrollment.registration.client.service.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingLocationTimeResult", propOrder = {
        "activityOfferingId", "activityOfferingType", "seatsAvailable", "seatsOpen", "activityOfferingLocationTime", "instructors"})
public class ActivityOfferingScheduleResult {

    private String activityOfferingId;
    private String activityOfferingType;
    private int seatsAvailable;
    private int seatsOpen;
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

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public int getSeatsOpen() {
        return seatsOpen;
    }

    public void setSeatsOpen(int seatsOpen) {
        this.seatsOpen = seatsOpen;
    }
}
