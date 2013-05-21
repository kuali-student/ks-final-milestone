/*
 * Copyright 2012 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class for both {@link ScheduleRequestComponentInfo} as well as {@link ScheduleComponentInfo} used at
 * Edit Activity Offering Screen. As we display the same information for RDL and ADL at UI, there is no
 * need for having seperate wrappers for ScheduleRequestComponentInfo and ScheduleComponentInfo.
 *
 * @author Kuali Student Team
 */
public class ScheduleWrapper implements Serializable{

    //DTOs
    private TimeSlotInfo timeSlot;
    private BuildingInfo building;
    private RoomInfo room;

    private ScheduleRequestComponentInfo scheduleRequestComponentInfo;
    private ScheduleComponentInfo scheduleComponentInfo;
    private ScheduleRequestInfo scheduleRequestInfo;

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
        features = new ArrayList<>();
        this.colocatedAOs = new ArrayList<>();
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
        this.colocatedAOs = new ArrayList<>();
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
        this.colocatedAOs = new ArrayList<>();
        this.editRenderHelper = new EditRenderHelper();
    }

    public ScheduleWrapper(ScheduleRequestInfo scheduleRequestInfo, ScheduleRequestComponentInfo scheduleRequestComponentInfo){
        this();
        this.scheduleRequestComponentInfo = scheduleRequestComponentInfo;
        this.scheduleRequestInfo = scheduleRequestInfo;
    }

    public ScheduleWrapper(ScheduleComponentInfo scheduleComponentInfo){
        this();
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

    public ScheduleRequestInfo getScheduleRequestInfo() {
        return scheduleRequestInfo;
    }

    public void setScheduleRequestInfo(ScheduleRequestInfo scheduleRequestInfo) {
        this.scheduleRequestInfo = scheduleRequestInfo;
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

    /* KSENROLL-6378 replaces displaying the checkboxes instead as simple-text due to partial-colocation not yet being
     * implemented; this is a simple UI-helper to build a simple string for that purpose.
     * See ActivityOfferingEdit-ScheduleSection.xml id="ActivityOffering-CoLocated-checkbox"
     * ~brandon.gresham
     */
    public String getColocatedAOsAsStringForUi() {
        StringBuilder sb = new StringBuilder();

        if( colocatedAOs != null && !colocatedAOs.isEmpty() ) {
            for( String s : colocatedAOs ) {
                sb.append(" " + s).append(",");
            }
            sb.setLength(sb.length()-1); // trim trailing comma
        }

        String result = sb.toString().trim();
        if( result.isEmpty() ) {
            result = "(nothing)";
        }

        return result;
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

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        private String buildingName;

        public boolean isShowColocateToolTip(){
            return colocatedAOs != null && !colocatedAOs.isEmpty();
        }

        public String getColocatedAOs(){
            if (colocatedAOs == null){
                return StringUtils.EMPTY;
            }
            StringBuffer buffer = new StringBuffer();
            buffer.append("This activity is colocated with:<br>");
            for (String code : colocatedAOs){
                buffer.append(code + "<br>");
            }

            return StringUtils.removeEnd(buffer.toString(),"<br>");
        }

    }
}
