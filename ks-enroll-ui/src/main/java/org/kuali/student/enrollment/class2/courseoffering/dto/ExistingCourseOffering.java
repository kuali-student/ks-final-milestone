package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;

public class ExistingCourseOffering {
    private String id;
    private String termCode;
    private String courseOfferingCode;
    private String courseTitle;
    private String credits;
    private String grading;

    private boolean selected;

    private CourseOfferingInfo courseOfferingInfo;

    public ExistingCourseOffering(){

    }

    public ExistingCourseOffering(CourseOfferingInfo courseOfferingInfo){
        this.courseOfferingInfo = courseOfferingInfo;
        setId(courseOfferingInfo.getId());
        setCourseOfferingCode(courseOfferingInfo.getCourseOfferingCode());
        setCourseTitle(courseOfferingInfo.getCourseOfferingTitle());
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
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

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getGrading() {
        return grading;
    }

    public void setGrading(String grading) {
        this.grading = grading;
    }

    public CourseOfferingInfo getCourseOfferingInfo() {
        return courseOfferingInfo;
    }

    public void setCourseOfferingInfo(CourseOfferingInfo courseOfferingInfo) {
        this.courseOfferingInfo = courseOfferingInfo;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
