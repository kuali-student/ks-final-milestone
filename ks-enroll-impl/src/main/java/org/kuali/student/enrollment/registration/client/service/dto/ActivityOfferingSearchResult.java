package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseSearchResult", propOrder = {
        "formatOfferingId", "formatOfferingName", "formatId", "activityOfferingClusterId",
        "activityOfferingClusterName", "activityOfferingClusterPrivateName", "activityOfferingId", "activityOfferingCode",
        "activityOfferingType", "activityOfferingTypeName", "activityOfferingState", "activityOfferingMaxSeats", "scheduleId", "schedule", "atpId", "instructors"})
public class ActivityOfferingSearchResult {
    String formatOfferingId;
    String formatOfferingName;
    String formatId;
    String activityOfferingClusterId;
    String activityOfferingClusterName;
    String activityOfferingClusterPrivateName;
    String activityOfferingId;
    String activityOfferingCode;
    String activityOfferingTypeName;
    String activityOfferingType;
    String activityOfferingState;
    String activityOfferingMaxSeats;
    String scheduleId;
    String atpId;

    ActivityOfferingScheduleComponentResult schedule;
    List<InstructorSearchResult> instructors;

    public String getFormatOfferingId() {
        return formatOfferingId;
    }

    public void setFormatOfferingId(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
    }

    public String getFormatOfferingName() {
        return formatOfferingName;
    }

    public void setFormatOfferingName(String formatOfferingName) {
        this.formatOfferingName = formatOfferingName;
    }

    public String getFormatId() {
        return formatId;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    public String getActivityOfferingClusterId() {
        return activityOfferingClusterId;
    }

    public void setActivityOfferingClusterId(String activityOfferingClusterId) {
        this.activityOfferingClusterId = activityOfferingClusterId;
    }

    public String getActivityOfferingClusterName() {
        return activityOfferingClusterName;
    }

    public void setActivityOfferingClusterName(String activityOfferingClusterName) {
        this.activityOfferingClusterName = activityOfferingClusterName;
    }

    public String getActivityOfferingClusterPrivateName() {
        return activityOfferingClusterPrivateName;
    }

    public void setActivityOfferingClusterPrivateName(String activityOfferingClusterPrivateName) {
        this.activityOfferingClusterPrivateName = activityOfferingClusterPrivateName;
    }

    public String getActivityOfferingId() {
        return activityOfferingId;
    }

    public void setActivityOfferingId(String activityOfferingId) {
        this.activityOfferingId = activityOfferingId;
    }

    public String getActivityOfferingCode() {
        return activityOfferingCode;
    }

    public void setActivityOfferingCode(String activityOfferingCode) {
        this.activityOfferingCode = activityOfferingCode;
    }

    public String getActivityOfferingType() {
        return activityOfferingType;
    }

    public void setActivityOfferingType(String activityOfferingType) {
        this.activityOfferingType = activityOfferingType;
    }

    public String getActivityOfferingState() {
        return activityOfferingState;
    }

    public void setActivityOfferingState(String activityOfferingState) {
        this.activityOfferingState = activityOfferingState;
    }

    public String getActivityOfferingMaxSeats() {
        return activityOfferingMaxSeats;
    }

    public void setActivityOfferingMaxSeats(String activityOfferingMaxSeats) {
        this.activityOfferingMaxSeats = activityOfferingMaxSeats;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    public ActivityOfferingScheduleComponentResult getSchedule() {
        return schedule;
    }

    public void setSchedule(ActivityOfferingScheduleComponentResult schedule) {
        this.schedule = schedule;
    }

    public List<InstructorSearchResult> getInstructors() {
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