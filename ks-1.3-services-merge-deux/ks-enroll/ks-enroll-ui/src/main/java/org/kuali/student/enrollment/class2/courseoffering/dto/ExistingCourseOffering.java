package org.kuali.student.enrollment.class2.courseoffering.dto;

public class ExistingCourseOffering {

    private String termCode;
    private String courseOfferingCode;
    private String courseTitle;
    private String credits;
    private String grading;

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

}
