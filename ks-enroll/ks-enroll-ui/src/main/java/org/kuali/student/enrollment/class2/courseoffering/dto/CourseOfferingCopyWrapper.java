package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseOfferingCopyWrapper implements Serializable{

    private CourseOfferingInfo coInfo;

    // Course Info
    private String courseOfferingCode;
    private String courseTitle;
    private String termId;
    private String creditCount;
    private String gradingOptionsId;
    private List<String> studentRegistrationGradingOptionsList;
    private String finalExamType;
    private String waitlistLevelTypeKey;
    private String waitlistTypeKey;
    private boolean isHonorsOffering;

    // Activity Offerings
    private List<ActivityOfferingWrapper> activityOfferingWrapperList;

    // Configure Course Offering Copy
    private boolean excludeCancelledActivityOfferings;
    private boolean excludeSchedulingInformation;
    private boolean excludeInstructorInformation;

    public CourseOfferingInfo getCoInfo() {
        return coInfo;
    }

    public void setCoInfo(CourseOfferingInfo coInfo) {
        this.coInfo = coInfo;
    }

    public CourseOfferingCopyWrapper(CourseOfferingInfo info){
        super();
        coInfo = info;
        clear();
    }
    
    public void clear() {
        setCourseOfferingCode("");
        setCourseTitle("");
        setTermId("");
        setCreditCount("");
        setGradingOptionsId("");
        setStudentRegistrationOptionsList(new ArrayList<String>());
        setFinalExamType("");
        setWaitlistLevelTypeKey("");
        setWaitlistTypeKey("");
        setIsHonors(false);
        setIsExcludeCancelledActivityOfferings(false);
        setIsExcludeSchedulingInformation(false);
        setIsExcludeInstructorInformation(false);
    }

    public List<ActivityOfferingWrapper> getActivityOfferingWrapperList() {
        return activityOfferingWrapperList;
    }

    public void setActivityOfferingWrapperList(List<ActivityOfferingWrapper> activityOfferingWrapperList) {
        this.activityOfferingWrapperList = activityOfferingWrapperList;
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(String creditCount) {
        this.creditCount = creditCount;
    }

    public String getGradingOptionsId() {
        return gradingOptionsId;
    }

    public void setGradingOptionsId(String gradingOptionsId) {
        this.gradingOptionsId = gradingOptionsId;
    }

    public List<String> getStudentRegistrationGradingOptionsList() {
        return studentRegistrationGradingOptionsList;
    }

    public void setStudentRegistrationOptionsList(List<String> studentRegistrationGradingOptionsList) {
        this.studentRegistrationGradingOptionsList = studentRegistrationGradingOptionsList;
    }

    public String getFinalExamType() {
        return finalExamType;
    }

    public void setFinalExamType(String finalExamType) {
        this.finalExamType = finalExamType;
    }

    public String getWaitlistLevelTypeKey() {
        return waitlistLevelTypeKey;
    }

    public void setWaitlistLevelTypeKey(String waitlistLevelTypeKey) {
        this.waitlistLevelTypeKey = waitlistLevelTypeKey;
    }

    public String getWaitlistTypeKey() {
        return waitlistTypeKey;
    }

    public void setWaitlistTypeKey(String waitlistTypeKey) {
        this.waitlistTypeKey = waitlistTypeKey;
    }

    public boolean isHonors() {
        return isHonorsOffering;
    }

    public void setIsHonors(boolean isHonorsOffering) {
        this.isHonorsOffering = isHonorsOffering;
    }

    public boolean isExcludeCancelledActivityOfferings() {
        return excludeCancelledActivityOfferings;
    }

    public void setIsExcludeCancelledActivityOfferings(boolean excludeCancelledActivityOfferings) {
        this.excludeCancelledActivityOfferings = excludeCancelledActivityOfferings;
    }

    public boolean isExcludeSchedulingInformation() {
        return excludeSchedulingInformation;
    }

    public void setIsExcludeSchedulingInformation(boolean excludeSchedulingInformation) {
        this.excludeSchedulingInformation = excludeSchedulingInformation;
    }

    public boolean isExcludeInstructorInformation() {
        return excludeInstructorInformation;
    }

    public void setIsExcludeInstructorInformation(boolean excludeInstructorInformation) {
        this.excludeInstructorInformation = excludeInstructorInformation;
    }

}
