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
import org.kuali.student.enrollment.class2.scheduleofclasses.sort.ComparatorModel;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a wrapper for Registration Group data
 *
 * @author Kuali Student Team
 */
public class RegistrationGroupWrapper implements Serializable, ComparatorModel {
    //added for ARG
    private ActivityOfferingClusterInfo aoCluster;
    private FormatOfferingInfo formatOfferingInfo;

    private RegistrationGroupInfo rgInfo;
    private String aoActivityCodeText;
    private String aoStateNameText;
    private String stateKey;
    private String aoTypeNameText;
    private String aoInstructorText;
    private String aoMaxEnrText;
    private String rgMaxEnrText;
    private String aoClusterName;
    private String aoEditLink;
    private String scheduledState = "";
    private String startTimeDisplay = "";
    private String endTimeDisplay = "";
    private String daysDisplayName = "";
    private String buildingName = "";
    private String buildingCode = "";
    private String roomName = "";
    private String requisite;
    private String commonRequisite;

    private List<String> startTime = new ArrayList<String>();
    private List<String> endTime = new ArrayList<String>();
    private List<String> weekDays = new ArrayList<String>();

    private List<String> bldgNameList = new ArrayList<String>();
    private List<String> bldgCodeList = new ArrayList<String>();

    public RegistrationGroupWrapper() {
        rgInfo = new RegistrationGroupInfo();
    }

    public RegistrationGroupWrapper(RegistrationGroupInfo info) {
        rgInfo = info;
    }

    public ActivityOfferingClusterInfo getAoCluster() {
        return aoCluster;
    }

    public void setAoCluster(ActivityOfferingClusterInfo aoCluster) {
        this.aoCluster = aoCluster;
    }

    public FormatOfferingInfo getFormatOfferingInfo() {
        return formatOfferingInfo;
    }

    public void setFormatOfferingInfo(FormatOfferingInfo formatOfferingInfo) {
        this.formatOfferingInfo = formatOfferingInfo;
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

    public void setStateKey(String key, String stateKey) {
        // TODO: Add the cnacelled state here when it is added in M6
        if(key.equalsIgnoreCase(LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY)){
            this.stateKey = "<span class=\"uif-invalid-state\">" + stateKey + "</span>";
        }else{
            this.stateKey = stateKey;
        }
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

    public void setStartTimeDisplay(String startTimeDisplay,boolean appendForDisplay){
        setStartTimeDisplay(startTimeDisplay, false, null);
    }

    public void setStartTimeDisplay(String startTimeDisplay,boolean appendForDisplay, String dlTypeClass) {
        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.startTimeDisplay)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.startTimeDisplay = this.startTimeDisplay + "<br><span " + cssClass + " >" + startTimeDisplay + "</span>";
        }else{
            this.startTimeDisplay = "<span " + cssClass + " >" + startTimeDisplay + "</span>";
        }
    }

    public String getEndTimeDisplay() {
        return endTimeDisplay;
    }

    public void setEndTimeDisplay(String endTimeDisplay) {
        setEndTimeDisplay(endTimeDisplay, false);
    }

    public void setEndTimeDisplay(String endTimeDisplay,boolean appendForDisplay) {
        setEndTimeDisplay(endTimeDisplay, false, null);
    }

    public void setEndTimeDisplay(String endTimeDisplay,boolean appendForDisplay, String dlTypeClass) {
        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.endTimeDisplay)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.endTimeDisplay = this.endTimeDisplay + "<br><span " + cssClass + " >" + endTimeDisplay + "</span>";
        }else{
            this.endTimeDisplay = "<span " + cssClass + " >" + endTimeDisplay + "</span>";
        }
    }

    public String getDaysDisplayName() {
        return daysDisplayName;
    }

    public void setDaysDisplayName(String daysDisplayName) {
        setDaysDisplayName(daysDisplayName, false);
    }

    public void setDaysDisplayName(String daysDisplayName,boolean appendForDisplay) {
        setDaysDisplayName(daysDisplayName, false, null);
    }

    public void setDaysDisplayName(String daysDisplayName,boolean appendForDisplay, String dlTypeClass) {
        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.daysDisplayName)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.daysDisplayName = this.daysDisplayName + "<br><span " + cssClass + " >" + daysDisplayName + "</span>";
        }else{
            this.daysDisplayName = "<span " + cssClass + " >" + daysDisplayName + "</span>";
        }
    }

    public List<String> getStartTime() {
        return startTime;
    }

    public void setStartTime(List<String> startTime) {
        this.startTime = startTime;
    }

    public List<String> getEndTime() {
        return endTime;
    }

    public void setEndTime(List<String> endTime) {
        this.endTime = endTime;
    }

    public String getScheduledState() {
        return scheduledState;
    }

    public void setScheduledState(String scheduledState) {
        setScheduledState(scheduledState, false);
    }

    public void setScheduledState(String scheduledState,boolean appendForDisplay) {
        setScheduledState(scheduledState, false, null);
    }

    public void setScheduledState(String scheduledState,boolean appendForDisplay, String dlTypeClass) {
        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.scheduledState)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.scheduledState = this.scheduledState + "<br><span " + cssClass + " >" + scheduledState + "</span>";
        }else{
            this.scheduledState = "<span " + cssClass + " >" + scheduledState + "</span>";
        }
    }

    public List<String> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<String> weekDays) {
        this.weekDays = weekDays;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        setBuildingName(buildingName, false);
    }

    public void setBuildingName(String buildingName,boolean appendForDisplay) {
        setBuildingName(buildingName, false, null);
    }

    public void setBuildingName(String buildingName,boolean appendForDisplay, String dlTypeClass) {
        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.buildingName)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.buildingName = this.buildingName + "<br><span " + cssClass + " >" + buildingName + "</span>";
        }else{
            this.buildingName = "<span " + cssClass + " >" + buildingName + "</span>";
        }
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        setBuildingCode(buildingCode, false);
    }

    public void setBuildingCode(String buildingCode,boolean appendForDisplay) {
        setBuildingCode(buildingCode, false, null);
    }

    public void setBuildingCode(String buildingCode,boolean appendForDisplay, String dlTypeClass) {
        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.buildingCode)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.buildingCode = this.buildingCode + "<br><span " + cssClass + " >" + buildingCode + "</span>";
        }else{
            this.buildingCode = buildingCode;
        }
    }

    public void setBuildingCodeWithTooltip(String buildingCode, String buildingName, String dlTypeClass) {
        String cssClass = "";
        boolean  appendForDisplay = true;
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.buildingCode)){
            appendForDisplay = false;
        } else {
            appendForDisplay = true;
        }
        String underlineCssClass = "<span style=\"border-bottom: 1px dotted;\">";

/*
        if (appendForDisplay){
            this.buildingCode = this.buildingCode + "<br><span " + cssClass + " >" + "  [id='SchOfClasses-RegGroup-BuildingCodeAndName-Tooltip' messageText='" + buildingCode + "'" + " toolTip.tooltipContent="+ "'" + buildingName +"']" + "</span>";
        }else{
            this.buildingCode = "<span " + cssClass + " >" + "  [id='SchOfClasses-RegGroup-BuildingCodeAndName-Tooltip' messageText=" + "'" + buildingCode + "'" + " toolTip.tooltipContent=" + "'" + buildingName + "']" + "</span>";
        }
*/
        String BldgCodeMark = "<span " + cssClass + "title='" + buildingName  + "'>" + underlineCssClass + buildingCode + "</span> </span>";
        if (appendForDisplay){
            this.buildingCode = this.buildingCode + "<br>" + BldgCodeMark;
        }else{
            this.buildingCode = BldgCodeMark;
        }

    }

    public void setBuildingCodeWithTooltip(String buildingCode, String buildingName) {
        String underlineCssClass = "<span style=\"border-bottom: 1px dotted;\">";

        String BldgCodeMark = "<span title='" + buildingName  + "'>" + underlineCssClass + buildingCode + "</span> </span>";

        this.buildingCode = BldgCodeMark;
    }

    public List<String> getBldgNameList() {
        return bldgNameList;
    }

    public void setBldgNameList(List<String> bldgNameList) {
        this.bldgNameList = bldgNameList;
    }

    public List<String> getBldgCodeList() {
        return bldgCodeList;
    }

    public void setBldgCodeList(List<String> bldgCodeList) {
        this.bldgCodeList = bldgCodeList;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        setRoomName(roomName, false);
    }

    public void setRoomName(String roomName,boolean appendForDisplay) {
        setRoomName(roomName, false, null);
    }

    public void setRoomName(String roomName,boolean appendForDisplay, String dlTypeClass) {
        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "class=\"" + dlTypeClass + "\"";
        }
        if(StringUtils.isEmpty(this.roomName)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.roomName = this.roomName + "<br><span " + cssClass + " >" + roomName + "</span>";
        }else{
            this.roomName = "<span " + cssClass + " >" + roomName + "</span>";
        }
    }

    public String getRgMaxEnrText() {
        return rgMaxEnrText;
    }

    public void setRgMaxEnrText(String rgMaxEnrText) {
        this.rgMaxEnrText = rgMaxEnrText;
    }

    public String getAoClusterName() {
        return aoClusterName;
    }

    public void setAoClusterName(String aoClusterName) {
        this.aoClusterName = aoClusterName;
    }

    public String getAoEditLink() {
        return aoEditLink;
    }

    public void setAoEditLink(String aoEditLink) {
        this.aoEditLink = aoEditLink;
    }

    public String getRequisite() {
        return requisite;
    }

    public void setRequisite(String requisite) {
        this.requisite = requisite;
    }

    public String getCommonRequisite() {
        return commonRequisite;
    }

    public void setCommonRequisite(String commonRequisite) {
        this.commonRequisite = commonRequisite;
    }
}
