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

    public void setStartTimeDisplay(String startTimeDisplay) {
       this.startTimeDisplay = StringUtils.defaultString(startTimeDisplay);
    }

    public String getEndTimeDisplay() {
        return endTimeDisplay;
    }

    public void setEndTimeDisplay(String endTimeDisplay) {
        this.endTimeDisplay = StringUtils.defaultString(endTimeDisplay);
    }

    public String getDaysDisplayName() {
        return daysDisplayName;
    }

    public void setDaysDisplayName(String daysDisplayName) {
        this.daysDisplayName = StringUtils.defaultString(daysDisplayName);
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

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
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

    public void setBuildingCode(String buildingCode) {
        String cssClass = "style=\"border-bottom: 1px dotted;\"";
        this.buildingCode = "<span " + cssClass + " >" + StringUtils.defaultString(buildingCode) + "</span>";
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = StringUtils.defaultString(roomName);
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
