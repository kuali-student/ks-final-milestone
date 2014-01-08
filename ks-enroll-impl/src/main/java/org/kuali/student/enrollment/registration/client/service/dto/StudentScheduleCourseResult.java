package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentScheduleCourseResult", propOrder = {
        "courseCode", "description", "credits", "activityOfferings"})
public class StudentScheduleCourseResult {
    private String courseCode;
    private String description;
    private String credits;
    private List<StudentScheduleActivityOfferingResult> activityOfferings;

    public List<StudentScheduleActivityOfferingResult> getActivityOfferings() {
        return activityOfferings;
    }

    public void setActivityOfferings(List<StudentScheduleActivityOfferingResult> activityOfferings) {
        this.activityOfferings = activityOfferings;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
