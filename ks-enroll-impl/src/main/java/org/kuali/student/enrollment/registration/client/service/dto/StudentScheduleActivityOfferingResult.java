package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentScheduleActivityOfferingResult", propOrder = {
        "activityOfferingId", "activityOfferingTypeName", "activityOfferingType", "activityOfferingCode",
        "seatsAvailable", "seatsOpen", "scheduleComponents", "instructors", "regGroupInfos", "requisites", "subterm"})
public class StudentScheduleActivityOfferingResult  implements Serializable {
    private String activityOfferingId;
    private String activityOfferingTypeName;
    private String activityOfferingType;
    private String activityOfferingCode;
    private Integer seatsAvailable;
    private Integer seatsOpen;
    private Integer maxWaitListSize; // null implies unbounded waitlist size (defined in CourseWaitListInfo)
    private Integer waitListSize; // Number of students on waitlist
    private List<ActivityOfferingScheduleComponentResult> scheduleComponents;
    private List<InstructorSearchResult> instructors;
    private Map<String, RegGroupLimitedInfoSearchResult> regGroupInfos;
    private List<String> requisites;
    private SubTermOfferingResult subterm;

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
            scheduleComponents = new ArrayList<>();
        }
        return scheduleComponents;
    }

    public void setScheduleComponents(List<ActivityOfferingScheduleComponentResult> scheduleComponents) {
        this.scheduleComponents = scheduleComponents;
    }

    public List<InstructorSearchResult> getInstructors() {
        if (instructors == null) {
            instructors = new ArrayList<>();
        }
        return instructors;
    }

    public void setInstructors(List<InstructorSearchResult> instructors) {
        this.instructors = instructors;
    }

    public String getActivityOfferingCode() {
        return activityOfferingCode;
    }

    public void setActivityOfferingCode(String activityOfferingCode) {
        this.activityOfferingCode = activityOfferingCode;
    }

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public Integer getSeatsOpen() {
        return seatsOpen;
    }

    public void setSeatsOpen(int seatsOpen) {
        this.seatsOpen = seatsOpen;
    }

    public Integer getMaxWaitListSize() {
        return maxWaitListSize;
    }

    public void setMaxWaitListSize(Integer maxWaitListSize) {
        this.maxWaitListSize = maxWaitListSize;
    }

    public Integer getWaitListSize() {
        return waitListSize;
    }

    public void setWaitListSize(Integer waitListSize) {
        this.waitListSize = waitListSize;
    }

    public List<String> getRequisites() {
        if(requisites == null){
            requisites = new ArrayList<>();
        }
        return requisites;
    }

    public void setRequisites(List<String> requisites) {
        this.requisites = requisites;
    }

    public SubTermOfferingResult getSubterm() {
        return subterm;
    }

    public void setSubterm(SubTermOfferingResult subterm) {
        this.subterm = subterm;
    }

    public Map<String, RegGroupLimitedInfoSearchResult> getRegGroupInfos() {
        if(regGroupInfos == null){
            regGroupInfos = new HashMap<String, RegGroupLimitedInfoSearchResult>();
        }
        return regGroupInfos;
    }

    public void setRegGroupInfos(Map<String, RegGroupLimitedInfoSearchResult> regGroupInfos) { this.regGroupInfos = regGroupInfos; }

}
