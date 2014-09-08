package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentScheduleCourseResult", propOrder = {
        "courseId", "courseCode", "description", "credits", "longName","regGroupId", "activityOfferings"})
public class StudentScheduleCourseResult {
    private String courseId;
    private String courseCode;
    private String description;
    private String credits;
    private String gradingOptionId;
    private String longName;
    private String regGroupCode;
    private String regGroupId;
    private String masterLprId;
    private List<String> creditOptions;
    private Map<String, String> gradingOptions;
    private List<StudentScheduleActivityOfferingResult> activityOfferings;
    private boolean isWaitlisted;
    private String createTime;

    public List<StudentScheduleActivityOfferingResult> getActivityOfferings() {
        if (activityOfferings == null) {
            activityOfferings = new ArrayList<StudentScheduleActivityOfferingResult>();
        }
        return activityOfferings;
    }

    public void setActivityOfferings(List<StudentScheduleActivityOfferingResult> activityOfferings) {
        this.activityOfferings = activityOfferings;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getGradingOptionId() { return gradingOptionId; }

    public void setGradingOptionId(String gradingOptionId) { this.gradingOptionId = gradingOptionId; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getRegGroupCode() { return regGroupCode; }

    public void setRegGroupCode(String regGroupCode) { this.regGroupCode = regGroupCode; }

    public String getMasterLprId() { return masterLprId; }

    public void setMasterLprId(String masterLprId) { this.masterLprId = masterLprId; }

    public List<String> getCreditOptions() {
        if(creditOptions == null){
            creditOptions = new ArrayList<String>();
        }
        return creditOptions;
    }

    public void setCreditOptions(List<String> creditOptions) { this.creditOptions = creditOptions; }

    public Map<String, String> getGradingOptions() {
        if(gradingOptions == null){
            gradingOptions = new HashMap<String, String>();
        }
        return gradingOptions;
    }

    public void setGradingOptions(Map<String, String> gradingOptions) { this.gradingOptions = gradingOptions; }

    public boolean isWaitlisted() { return isWaitlisted; }

    public void setWaitlisted(boolean isWaitlisted) { this.isWaitlisted = isWaitlisted; }

    public String getRegGroupId() {
        return regGroupId;
    }

    public void setRegGroupId(String regGroupId) {
        this.regGroupId = regGroupId;
    }

    public void setCreateTime (String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime () {
        return this.createTime;
    }
}
