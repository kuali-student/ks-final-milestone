package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseSearchResult", propOrder = {
        "scheduleId", "startTimeDisplay", "startTimeMili",  "endTimeDisplay", "endTimeMili", "buildingName",
        "buildingCode", "roomName", "days", "tba"})
public class ScheduleSearchResult {
    String scheduleId;
    String startTimeDisplay;
    String endTimeDisplay;
    String startTimeMili;
    String endTimeMili;
    String buildingName;
    String buildingCode;
    String roomName;
    String days;
    Boolean tba;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStartTimeDisplay() {
        return startTimeDisplay;
    }

    public void setStartTimeDisplay(String startTimeDisplay) {
        this.startTimeDisplay = startTimeDisplay;
    }

    public String getEndTimeDisplay() {
        return endTimeDisplay;
    }

    public void setEndTimeDisplay(String endTimeDisplay) {
        this.endTimeDisplay = endTimeDisplay;
    }

    public String getStartTimeMili() {
        return startTimeMili;
    }

    public void setStartTimeMili(String startTimeMili) {
        this.startTimeMili = startTimeMili;
    }

    public String getEndTimeMili() {
        return endTimeMili;
    }

    public void setEndTimeMili(String endTimeMili) {
        this.endTimeMili = endTimeMili;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Boolean getTba() {
        return tba;
    }

    public void setTba(Boolean tba) {
        this.tba = tba;
    }
}
