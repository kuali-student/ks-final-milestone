package org.kuali.student.enrollment.registration.client.service.dto;


import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityOfferingScheduleComponentResult", propOrder = {
        "sun", "mon", "tue", "wed", "thu", "fri", "sat", "startTime", "endTime", "buildingCode", "roomCode"})
public class ActivityOfferingScheduleComponentResult {
    private boolean sun;
    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private String startTime;
    private String endTime;
    private String days;
    private String buildingCode;
    private String roomCode;

    public String getDays() { return days; }

    public void setDays(String days) { this.days = days; }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isFri() {
        return fri;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }

    public boolean isMon() {
        return mon;
    }

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public boolean isSat() {
        return sat;
    }

    public void setSat(boolean sat) {
        this.sat = sat;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public boolean isSun() {
        return sun;
    }

    public void setSun(boolean sun) {
        this.sun = sun;
    }

    public boolean isThu() {
        return thu;
    }

    public void setThu(boolean thu) {
        this.thu = thu;
    }

    public boolean isTue() {
        return tue;
    }

    public void setTue(boolean tue) {
        this.tue = tue;
    }

    public boolean isWed() {
        return wed;
    }

    public void setWed(boolean wed) {
        this.wed = wed;
    }

    public void setBooleanSchedules(String daysOfTheWeek) {
        if (!StringUtils.isEmpty(daysOfTheWeek)) {
            setMon(daysOfTheWeek.contains("M"));
            setTue(daysOfTheWeek.contains("T"));
            setWed(daysOfTheWeek.contains("W"));
            setThu(daysOfTheWeek.contains("H"));
            setFri(daysOfTheWeek.contains("F"));
            setSat(daysOfTheWeek.contains("S"));
            setSun(daysOfTheWeek.contains("U"));
        }
    }
}
