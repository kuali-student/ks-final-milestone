package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.class2.scheduleofclasses.sort.ComparatorModel;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Main model object in Edit AO view.
 *
 * @see org.kuali.student.enrollment.class2.courseoffering.dto.ColocatedActivity
 *
 */
public class ExamOfferingWrapper implements Serializable, ComparatorModel{


    private String stateName;
    private String typeName;
    private String typeKey;
    private String startTimeDisplay = "";
    private String endTimeDisplay = "";
    private String daysDisplayName = "";
    private String buildingName = "";
    private String buildingCode = "";
    private String bldgCodeSimple = "";
    private String roomName = "";
    private ExamOfferingInfo eoInfo;
    private List<String> startTime;
    private List<String> endTime;
    private List<String> weekDays;
    private ActivityOfferingInfo aoInfo;

    public ExamOfferingWrapper(){
        startTime = new ArrayList<String>();
        endTime = new ArrayList<String>();
        weekDays = new ArrayList<String>();
        aoInfo = new ActivityOfferingInfo();
    }

    public ExamOfferingWrapper(ExamOfferingInfo info){
        this();
        eoInfo = info;
        startTime = new ArrayList<String>();
        endTime = new ArrayList<String>();
        weekDays = new ArrayList<String>();

    }

    public String courseOfferingTitle;
    public void setCourseOfferingTitle(String courseOfferingTitle) {
        this.courseOfferingTitle = courseOfferingTitle;
    }

    public String getCourseOfferingTitle() {
        return courseOfferingTitle;
    }


    private String activityCode = "";

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStartTimeDisplay() {
        return startTimeDisplay;
    }

    public void setStartTimeDisplay(String startTimeDisplay,boolean appendForDisplay) {
        if (appendForDisplay){
            this.startTimeDisplay = this.startTimeDisplay + "<br>" + StringUtils.defaultString(startTimeDisplay);
        }else{
            this.startTimeDisplay = StringUtils.defaultString(startTimeDisplay);
        }

    }

    public void setStartTimeDisplay(String startTimeDisplay, boolean appendForDisplay, String dlTypeClass) {
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

    public void setEndTimeDisplay(String endTimeDisplay,boolean appendForDisplay) {
        if (appendForDisplay){
            this.endTimeDisplay = this.endTimeDisplay + "<br>" + StringUtils.defaultString(endTimeDisplay);
        }else{
            this.endTimeDisplay = StringUtils.defaultString(endTimeDisplay);
        }
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

    public void setDaysDisplayName(String daysDisplayName,boolean appendForDisplay) {
        if (appendForDisplay){
            this.daysDisplayName = this.daysDisplayName + "<br>" + StringUtils.defaultString(daysDisplayName);
        }else{
            this.daysDisplayName = StringUtils.defaultString(daysDisplayName);
        }
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

    public List<String> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<String> weekDays) {
        this.weekDays = weekDays;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName,boolean appendForDisplay) {
        if (appendForDisplay){
            this.buildingName = this.buildingName + "<br>" + StringUtils.defaultString(buildingName);
        }else{
            this.buildingName = StringUtils.defaultString(buildingName);
        }
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

    public String getBldgCodeSimple() {
        return bldgCodeSimple;
    }

    public void setBldgCodeSimple(String bldgCodeSimple) {
        this.bldgCodeSimple = bldgCodeSimple;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode, boolean appendForDisplay) {
        String cssClass = "style=\"border-bottom: 1px dotted;\"";
        if (appendForDisplay){
            this.buildingCode = this.buildingCode + "<br><span " + cssClass + " >" + StringUtils.defaultString(buildingCode) + "</span>";
        }else{
            this.buildingCode = "<span " + cssClass + " >" + StringUtils.defaultString(buildingCode) + "</span>";
        }
    }

    public void setBuildingCode(String buildingCode, boolean appendForDisplay, String dlTypeClass) {
        String cssClass = "";
        if(!StringUtils.isEmpty(dlTypeClass)){
            cssClass = "style=\"border-bottom: 1px dotted;\"";
        }
        if(StringUtils.isEmpty(this.buildingCode)){
            appendForDisplay = false;
        }
        if (appendForDisplay){
            this.buildingCode = this.buildingCode + "<br><span " + cssClass + " >" + buildingCode + "</span>";
        }else{
            this.buildingCode = "<span " + cssClass + " >" + buildingCode + "</span>";
        }
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName,boolean appendForDisplay) {
        if (appendForDisplay){
            this.roomName = this.roomName + "<br>" + StringUtils.defaultString(roomName);
        }else{
            this.roomName = StringUtils.defaultString(roomName);
        }
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

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public ExamOfferingInfo getEoInfo() {
        return eoInfo;
    }

    public void setEoInfo(ExamOfferingInfo eoInfo) {
        this.eoInfo = eoInfo;
    }

    public ActivityOfferingInfo getAoInfo() {
        return aoInfo;
    }

    public void setAoInfo(ActivityOfferingInfo aoInfo) {
        this.aoInfo = aoInfo;
    }
}
