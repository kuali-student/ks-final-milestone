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
    private String buildingId;
    private String roomCode;
    private int roomCapacity;

    private List<String> features;

    //For informational display only
    private String daysUI;
    private String startTimeUI;
    private String endTimeUI;

    private List<String> colocatedAOs;

    private EditRenderHelper editRenderHelper;

    public ScheduleWrapper(){
        features = new ArrayList<String>();
        this.colocatedAOs = new ArrayList<String>();
        this.editRenderHelper = new EditRenderHelper();
    }

    public ScheduleWrapper(ScheduleWrapper wrapper){
        this.scheduleComponentInfo = wrapper.getScheduleComponentInfo();
        this.scheduleRequestComponentInfo = wrapper.getScheduleRequestComponentInfo();
        this.days = wrapper.getDays();
        this.startTime = wrapper.getStartTime();
        this.startTimeAMPM = wrapper.getStartTimeAMPM();
        this.endTime = wrapper.getEndTime();
        this.endTimeAMPM = wrapper.getEndTimeAMPM();
        this.tba = wrapper.isTba();
        this.buildingCode = wrapper.getBuildingCode();
        this.buildingId = wrapper.getBuildingId();
        this.roomCode = wrapper.getRoomCode();
        this.roomCapacity = wrapper.getRoomCapacity();
        this.features = wrapper.getFeatures();
        this.daysUI = wrapper.getDaysUI();
        this.startTimeUI = wrapper.getStartTimeUI();
        this.endTimeUI = wrapper.getEndTimeUI();
        this.room = wrapper.getRoom();
        this.building = wrapper.getBuilding();
        this.colocatedAOs = new ArrayList<String>();
        this.editRenderHelper = new EditRenderHelper();
    }

    public void copyForEditing(ScheduleWrapper wrapper){
        this.scheduleComponentInfo = null;
        this.scheduleRequestComponentInfo = null;
        this.days = wrapper.getDays();
        this.tba = wrapper.isTba();
        this.startTime = wrapper.getStartTime();
        this.startTimeAMPM = wrapper.getStartTimeAMPM();
        this.endTime = wrapper.getEndTime();
        this.endTimeAMPM = wrapper.getEndTimeAMPM();
        this.buildingId = wrapper.getBuildingId();
        this.buildingCode = wrapper.getBuildingCode();
        this.roomCode = wrapper.getRoomCode();
        this.roomCapacity = wrapper.getRoomCapacity();
        this.room = wrapper.getRoom();
        this.building = wrapper.getBuilding();
        this.colocatedAOs = new ArrayList<String>();
        this.editRenderHelper = new EditRenderHelper();
    }

    public ScheduleWrapper(ScheduleRequestComponentInfo scheduleRequestComponentInfo){
        this.scheduleRequestComponentInfo = scheduleRequestComponentInfo;
        this.colocatedAOs = new ArrayList<String>();
        this.editRenderHelper = new EditRenderHelper();
    }

    public ScheduleWrapper(ScheduleComponentInfo scheduleComponentInfo){
        this.scheduleComponentInfo = scheduleComponentInfo;
        this.colocatedAOs = new ArrayList<String>();
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

    public boolean isRequestAlreadySaved() {
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

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public List<String> getColocatedAOs() {
        return colocatedAOs;
    }

    public void setColocatedAOs(List<String> colocatedAOs) {
        this.colocatedAOs = colocatedAOs;
    }

    public EditRenderHelper getEditRenderHelper() {
        return editRenderHelper;
    }

    public class EditRenderHelper implements Serializable{

        public String getColocatedAOs(){
            StringBuffer buffer = new StringBuffer();
            buffer.append("This activity is colocated with:<br>");
            for (String code : colocatedAOs){
                buffer.append(code + "<br>");
            }

            return StringUtils.removeEnd(buffer.toString(),"<br>");
        }
    }
}
