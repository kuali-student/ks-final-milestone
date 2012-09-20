package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    private ScheduleRequestComponentInfo scheduleRequestComponentInfo;
    private ScheduleComponentInfo scheduleComponentInfo;

    //Properties
    private String days;
    private String startTime;
    private String startTimeAMPM;
    private String endTime;
    private String endTimeAMPM;

    private boolean tba;

    private String buildingCode;
    private String roomCode;
    private int roomCapacity;

    private List<String> features;

    //For informational display only
    private String daysUI;
    private String startTimeUI;
    private String endTimeUI;

    public ScheduleWrapper(){
        features = new ArrayList<String>();
        setBuildingCode("CCC");
        setRoomCode("1115");
    }

    public ScheduleWrapper(ScheduleRequestComponentInfo scheduleRequestComponentInfo){
        this.scheduleRequestComponentInfo = scheduleRequestComponentInfo;
    }

    public ScheduleWrapper(ScheduleComponentInfo scheduleComponentInfo){
        this.scheduleComponentInfo = scheduleComponentInfo;
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

    public String getBuildingName() {
        if (building != null){
            return building.getName();
        }
        return StringUtils.EMPTY;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
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
        if (scheduleRequestComponentInfo != null){
            return StringUtils.isNotBlank(scheduleRequestComponentInfo.getId());
        }
        return false;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getRoomName() {
        if (room != null){
            return room.getRoomCode();
        }
        return StringUtils.EMPTY;
    }

    public boolean isTba() {
        return tba;
    }

    public void setTba(boolean tba) {
        this.tba = tba;
    }

    public String getTbaUI(){
        if (isTba()){
            return "TBA";
        }
        return StringUtils.EMPTY;
    }

    public String getFeaturesUI() {
        return "N/A";
    }

    public ScheduleRequestComponentInfo getScheduleRequestComponentInfo() {
        return scheduleRequestComponentInfo;
    }

    public ScheduleComponentInfo getScheduleComponentInfo() {
        return scheduleComponentInfo;
    }
}
