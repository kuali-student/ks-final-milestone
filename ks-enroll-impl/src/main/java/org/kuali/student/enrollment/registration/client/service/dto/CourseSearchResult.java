package org.kuali.student.enrollment.ui.registration.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseSearchResult", propOrder = {
        "courseOfferingId", "courseOfferingCode", "courseOfferingDesc", "courseOfferingCreditOptionDisplay",
        "courseOfferingGradingOptionDisplay", "studentSelectablePassFail", "auditCourse", "honorsCourse"})
public class CourseSearchResult {
    private String courseOfferingId;
    private String courseOfferingCode;
    private String courseOfferingDesc;
    private String courseOfferingCreditOptionDisplay;
    private String courseOfferingGradingOptionDisplay;

    private boolean studentSelectablePassFail;
    private boolean auditCourse;
    private boolean honorsCourse;

    public boolean isAuditCourse() {
        return auditCourse;
    }

    public void setAuditCourse(boolean auditCourse) {
        this.auditCourse = auditCourse;
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getCourseOfferingCreditOptionDisplay() {
        return courseOfferingCreditOptionDisplay;
    }

    public void setCourseOfferingCreditOptionDisplay(String courseOfferingCreditOptionDisplay) {
        this.courseOfferingCreditOptionDisplay = courseOfferingCreditOptionDisplay;
    }

    public String getCourseOfferingDesc() {
        return courseOfferingDesc;
    }

    public void setCourseOfferingDesc(String courseOfferingDesc) {
        this.courseOfferingDesc = courseOfferingDesc;
    }

    public String getCourseOfferingGradingOptionDisplay() {
        return courseOfferingGradingOptionDisplay;
    }

    public void setCourseOfferingGradingOptionDisplay(String courseOfferingGradingOptionDisplay) {
        this.courseOfferingGradingOptionDisplay = courseOfferingGradingOptionDisplay;
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public boolean isHonorsCourse() {
        return honorsCourse;
    }

    public void setHonorsCourse(boolean honorsCourse) {
        this.honorsCourse = honorsCourse;
    }

    public boolean isStudentSelectablePassFail() {
        return studentSelectablePassFail;
    }

    public void setStudentSelectablePassFail(boolean studentSelectablePassFail) {
        this.studentSelectablePassFail = studentSelectablePassFail;
    }
}