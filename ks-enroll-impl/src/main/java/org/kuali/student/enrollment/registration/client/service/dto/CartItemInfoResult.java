package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CartItemInfoResult", propOrder = {
        "cartItemId", "courseCode", "regCode", "courseTitle",
        "credits", "grading", "creditOptions", "gradingOptions",
        "schedule"})
public class CartItemInfoResult {
    private String cartItemId;
    private String courseCode;
    private String regCode;
    private String courseTitle;
    private String credits;
    private String grading;
    private List<String> creditOptions;
    private HashMap<String, String> gradingOptions;
    private ActivityOfferingScheduleResult schedule;

    public String getCartItemId() { return cartItemId; }

    public void setCartItemId(String cartItemId) { this.cartItemId = cartItemId; }

    public String getCourseCode() { return courseCode; }

    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getRegCode() { return regCode; }

    public void setRegCode(String regCode) { this.regCode = regCode; }

    public String getCourseTitle() { return courseTitle; }

    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public String getCredits() { return credits; }

    public void setCredits(String credits) { this.credits = credits; }

    public String getGrading() { return grading; }

    public void setGrading(String grading) { this.grading = grading; }

    public List<String> getCreditOptions() { return creditOptions; }

    public void setCreditOptions(List<String> creditOptions) { this.creditOptions = creditOptions; }

    public HashMap<String, String> getGradingOptions() { return gradingOptions; }

    public void setGradingOptions(HashMap<String, String> gradingOptions) { this.gradingOptions = gradingOptions; }

    public ActivityOfferingScheduleResult getSchedule() { return schedule; }

    public void setSchedule(ActivityOfferingScheduleResult schedule) { this.schedule = schedule; }
}