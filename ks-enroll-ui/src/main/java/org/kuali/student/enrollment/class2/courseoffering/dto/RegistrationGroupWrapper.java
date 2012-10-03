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
 * Created by vgadiyak on 8/17/12
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;

import java.io.Serializable;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class RegistrationGroupWrapper implements Serializable {

    private RegistrationGroupInfo rgInfo;
    private String aoActivityCodeText;
    private String aoStateNameText;
    private String stateKey;
    private String aoTypeNameText;
    private String aoInstructorText;
    private String aoMaxEnrText;

    private String startTimeDisplay = "";
    private String endTimeDisplay = "";
    private String daysDisplayName = "";
    private String buildingName = "";
    private String roomName = "";

    public RegistrationGroupWrapper() {
        rgInfo = new RegistrationGroupInfo();
    }

    public RegistrationGroupWrapper(RegistrationGroupInfo info) {
        rgInfo = info;
    }

    public RegistrationGroupInfo getRgInfo() {
        return rgInfo;
    }

    public void setRgInfo(RegistrationGroupInfo rgInfo) {
        this.rgInfo = rgInfo;
    }

    public String getAoActivityCodeText() {
        return aoActivityCodeText;
    }

    public void setAoActivityCodeText(String aoActivityCodeText) {
        this.aoActivityCodeText = aoActivityCodeText;
    }

    public String getAoStateNameText() {
        return aoStateNameText;
    }

    public void setAoStateNameText(String aoStateNameText) {
        this.aoStateNameText = aoStateNameText;
    }

    public String getStateKey() {
        return stateKey;
    }

    public void setStateKey(String stateKey) {
        this.stateKey = stateKey;
    }

    public String getAoTypeNameText() {
        return aoTypeNameText;
    }

    public void setAoTypeNameText(String aoTypeNameText) {
        this.aoTypeNameText = aoTypeNameText;
    }

    public String getAoInstructorText() {
        return aoInstructorText;
    }

    public void setAoInstructorText(String aoInstructorText) {
        this.aoInstructorText = aoInstructorText;
    }

    public String getAoMaxEnrText() {
        return aoMaxEnrText;
    }

    public void setAoMaxEnrText(String aoMaxEnrText) {
        this.aoMaxEnrText = aoMaxEnrText;
    }

    public String getStartTimeDisplay() {
        return startTimeDisplay;
    }

    public void setStartTimeDisplay(String startTimeDisplay) {
        setStartTimeDisplay(startTimeDisplay, false);
    }

    public void setStartTimeDisplay(String startTimeDisplay,boolean appendForDisplay) {
        if(StringUtils.isEmpty(this.startTimeDisplay)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.startTimeDisplay = this.startTimeDisplay + "<br>" + StringUtils.defaultString(startTimeDisplay);
        }else{
            this.startTimeDisplay = StringUtils.defaultString(startTimeDisplay);
        }
    }

    public String getEndTimeDisplay() {
        return endTimeDisplay;
    }

    public void setEndTimeDisplay(String endTimeDisplay) {
        setEndTimeDisplay(endTimeDisplay, false);
    }

    public void setEndTimeDisplay(String endTimeDisplay,boolean appendForDisplay) {
        if(StringUtils.isEmpty(this.endTimeDisplay)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.endTimeDisplay = this.endTimeDisplay + "<br>" + StringUtils.defaultString(endTimeDisplay);
        }else{
            this.endTimeDisplay = StringUtils.defaultString(endTimeDisplay);
        }
    }

    public String getDaysDisplayName() {
        return daysDisplayName;
    }

    public void setDaysDisplayName(String daysDisplayName) {
        setDaysDisplayName(daysDisplayName, false);
    }

    public void setDaysDisplayName(String daysDisplayName,boolean appendForDisplay) {
        if(StringUtils.isEmpty(this.daysDisplayName)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.daysDisplayName = this.daysDisplayName + "<br>" + StringUtils.defaultString(daysDisplayName);
        }else{
            this.daysDisplayName = StringUtils.defaultString(daysDisplayName);
        }
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        setBuildingName(buildingName, false);
    }

    public void setBuildingName(String buildingName,boolean appendForDisplay) {
        if(StringUtils.isEmpty(this.buildingName)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.buildingName = this.buildingName + "<br>" + StringUtils.defaultString(buildingName);
        }else{
            this.buildingName = StringUtils.defaultString(buildingName);
        }
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        setRoomName(roomName, false);
    }

    public void setRoomName(String roomName,boolean appendForDisplay) {
        if(StringUtils.isEmpty(this.roomName)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.roomName = this.roomName + "<br>" + StringUtils.defaultString(roomName);
        }else{
            this.roomName = StringUtils.defaultString(roomName);
        }
    }
}
