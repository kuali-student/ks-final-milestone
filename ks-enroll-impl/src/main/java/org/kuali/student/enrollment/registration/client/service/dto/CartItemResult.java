package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CartItemResult", propOrder = {
        "cartItemId", "courseCode", "regGroupCode", "courseTitle",
        "credits", "grading", "creditOptions", "gradingOptions",
        "schedule"})
public class CartItemResult {
    private String cartItemId;
    private String courseCode;
    private String regGroupCode;
    private String courseTitle;
    private String credits;
    private String grading;
    private List<String> creditOptions;
    private Map<String, String> gradingOptions;
    private List<ActivityOfferingScheduleResult> schedule;

    public String getCartItemId() { return cartItemId; }

    public void setCartItemId(String cartItemId) { this.cartItemId = cartItemId; }

    public String getCourseCode() { return courseCode; }

    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getRegGroupCode() { return regGroupCode; }

    public void setRegGroupCode(String regGroupCode) { this.regGroupCode = regGroupCode; }

    public String getCourseTitle() { return courseTitle; }

    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public String getCredits() { return credits; }

    public void setCredits(String credits) { this.credits = credits; }

    public String getGrading() { return grading; }

    public void setGrading(String grading) { this.grading = grading; }

    public List<String> getCreditOptions() { return creditOptions; }

    public void setCreditOptions(List<String> creditOptions) { this.creditOptions = creditOptions; }

    public Map<String, String> getGradingOptions() { return gradingOptions; }

    public void setGradingOptions(Map<String, String> gradingOptions) { this.gradingOptions = gradingOptions; }

    public List<ActivityOfferingScheduleResult> getSchedule() { return schedule; }

    public void setSchedule(List<ActivityOfferingScheduleResult> schedule) { this.schedule = schedule; }
}