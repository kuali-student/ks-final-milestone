package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentScheduleCourseResult", propOrder = {
        "courseCode", "description", "credits", "longName", "activityOfferings"})
public class StudentScheduleCourseResult {
    private String courseCode;
    private String description;
    private String credits;
    private String longName;
    private List<StudentScheduleActivityOfferingResult> activityOfferings;

    public List<StudentScheduleActivityOfferingResult> getActivityOfferings() {
        if (activityOfferings == null) {
            activityOfferings = new ArrayList<StudentScheduleActivityOfferingResult>();
        }
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

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }
}
