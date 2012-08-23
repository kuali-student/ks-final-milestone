package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: venkat
 * Date: 8/21/12
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleWrapper implements Serializable{

    //DTOs
    private TimeSlotInfo timeSlot;
    private BuildingInfo building;
    private RoomInfo room;
    private ScheduleRequestInfo scheduleRequest;
    private ScheduleInfo actualSchedule;

    //Properties
    private String days;
    private String startTime;
    private String startTimeAMPM;
    private String endTime;
    private String endTimeAMPM;
    private String facilityName;
    private String roomNumber;
    private String roomCapacity;

    private List<String> features;

    //For informational display only
    private String daysUI;
    private String startTimeUI;
    private String endTimeUI;

    public ScheduleWrapper(){
        features = new ArrayList<String>();
    }

    public TimeSlotInfo getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlotInfo timeSlot) {
        this.timeSlot = timeSlot;
    }

    public BuildingInfo getBuilding() {
        return building;
    }

    public void setBuilding(BuildingInfo building) {
        this.building = building;
    }

    public RoomInfo getRoom() {
        return room;
    }

    public void setRoom(RoomInfo room) {
        this.room = room;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeAMPM() {
        return startTimeAMPM;
    }

    public void setStartTimeAMPM(String startTimeAMPM) {
        this.startTimeAMPM = startTimeAMPM;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTimeAMPM() {
        return endTimeAMPM;
    }

    public void setEndTimeAMPM(String endTimeAMPM) {
        this.endTimeAMPM = endTimeAMPM;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(String roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public ScheduleRequestInfo getScheduleRequest() {
        return scheduleRequest;
    }

    public void setScheduleRequest(ScheduleRequestInfo scheduleRequest) {
        this.scheduleRequest = scheduleRequest;
    }

    public ScheduleInfo getActualSchedule() {
        return actualSchedule;
    }

    public void setActualSchedule(ScheduleInfo actualSchedule) {
        this.actualSchedule = actualSchedule;
    }

    public String getDaysUI() {
        return daysUI;
    }

    public String getStartTimeUI() {
        return startTimeUI;
    }

    public String getEndTimeUI() {
        return endTimeUI;
    }

    public void setDaysUI(String daysUI) {
        this.daysUI = daysUI;
    }

    public void setStartTimeUI(String startTimeUI) {
        this.startTimeUI = startTimeUI;
    }

    public void setEndTimeUI(String endTimeUI) {
        this.endTimeUI = endTimeUI;
    }

    public boolean isAlreadySaved() {
        if (scheduleRequest != null){
            return StringUtils.isNotBlank(scheduleRequest.getId());
        }
        return false;
    }

}
