/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by vgadiyak on 9/18/12
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingDisplayWrapper {

    private ActivityOfferingDisplayInfo aoDisplayInfo;
    private String information;
    private String startTimeDisplay;
    private String endTimeDisplay;
    private String daysDisplayName;
    private String buildingName;
    private String roomName;
    private String tbaDisplayName;
    private String instructorDisplayNames;
    private String activityOfferingCode;
    private String colocatedAoInfo = "";


    public ActivityOfferingDisplayWrapper(){
        aoDisplayInfo = new ActivityOfferingDisplayInfo();
    }

    public ActivityOfferingDisplayInfo getAoDisplayInfo() {
        return aoDisplayInfo;
    }

    public void setAoDisplayInfo(ActivityOfferingDisplayInfo aoDisplayInfo) {
        this.aoDisplayInfo = aoDisplayInfo;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getStartTimeDisplay() {
        return startTimeDisplay;
    }

    public void setStartTimeDisplay(String startTimeDisplay) {
        this.startTimeDisplay = startTimeDisplay;
    }

    public void setStartTimeDisplay(String startTimeDisplay,boolean appendForDisplay) {
        if (appendForDisplay && this.startTimeDisplay!=null){
            this.startTimeDisplay = this.startTimeDisplay + "<br>" + StringUtils.defaultString(startTimeDisplay);
        }else{
            this.startTimeDisplay = StringUtils.defaultString(startTimeDisplay);
        }

    }

    public String getEndTimeDisplay() {
        return endTimeDisplay;
    }

    public void setEndTimeDisplay(String endTimeDisplay) {
        this.endTimeDisplay = endTimeDisplay;
    }

    public void setEndTimeDisplay(String endTimeDisplay,boolean appendForDisplay) {
        if (appendForDisplay && this.endTimeDisplay!=null){
            this.endTimeDisplay = this.endTimeDisplay + "<br>" + StringUtils.defaultString(endTimeDisplay);
        }else{
            this.endTimeDisplay = StringUtils.defaultString(endTimeDisplay);
        }

    }

    public String getDaysDisplayName() {
        return daysDisplayName;
    }

    public void setDaysDisplayName(String daysDisplayName) {
        this.daysDisplayName = daysDisplayName;
    }

    public void setDaysDisplayName(String daysDisplayName,boolean appendForDisplay) {
        if (appendForDisplay && this.daysDisplayName!=null){
            this.daysDisplayName = this.daysDisplayName + "<br>" + StringUtils.defaultString(daysDisplayName);
        }else{
            this.daysDisplayName = StringUtils.defaultString(daysDisplayName);
        }

    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setBuildingName(String buildingName,boolean appendForDisplay) {
        if (appendForDisplay && this.buildingName!=null){
            this.buildingName = this.buildingName + "<br>" + StringUtils.defaultString(buildingName);
        }else{
            this.buildingName = StringUtils.defaultString(buildingName);
        }

    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setRoomName(String roomName,boolean appendForDisplay) {
        if (appendForDisplay && this.roomName!=null){
            this.roomName = this.roomName + "<br>" + StringUtils.defaultString(roomName);
        }else{
            this.roomName = StringUtils.defaultString(roomName);
        }

    }

    public String getInstructorDisplayNames() {
        return instructorDisplayNames;
    }

    public void setInstructorDisplayNames(String instructorDisplayNames) {
        this.instructorDisplayNames = instructorDisplayNames;
    }

    public void setInstructorDisplayNames(String instructorDisplayNames,boolean appendForDisplay) {
        if (appendForDisplay && this.instructorDisplayNames!=null){
            this.instructorDisplayNames = this.instructorDisplayNames + "<br>" + StringUtils.defaultString(instructorDisplayNames);
        }else{
            this.instructorDisplayNames = StringUtils.defaultString(instructorDisplayNames);
        }

    }

    public String getTbaDisplayName() {
        return tbaDisplayName;
    }

    public void setTbaDisplayName(boolean tba) {
        tbaDisplayName = StringUtils.EMPTY;
        if (tba){
            tbaDisplayName =  "TBA";
        }
    }

    public String getActivityOfferingCode() {
        return activityOfferingCode;
    }

    public void setActivityOfferingCode(String activityOfferingCode) {
        this.activityOfferingCode = activityOfferingCode;
    }

    public String getColocatedAoInfo() {
        return colocatedAoInfo;
    }

    public void setColocatedAoInfo(String colocatedAoInfo) {
        this.colocatedAoInfo = colocatedAoInfo;
    }

    /**
     * This method return a colocated AO code for current course. This will
     * be displayed as the tooltip (if colocated AO exists) at manage and delete AO screen.
     *
     * @return
     */
    @SuppressWarnings("unused")
    public String getColocatedAoInfoUI(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("This activity is colocated with:<br>");
        buffer.append(colocatedAoInfo + "<br>");

        return StringUtils.removeEnd(buffer.toString(),"<br>");
    }

}
