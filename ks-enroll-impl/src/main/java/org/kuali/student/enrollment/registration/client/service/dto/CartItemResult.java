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
@XmlType(name = "CartItemResult", propOrder = {
        "cartItemId", "courseCode", "regGroupCode", "courseTitle",
        "credits", "grading", "creditOptions", "gradingOptions", "gradingOptionCount",
        "schedule"})
public class CartItemResult {
    private String cartItemId;
    private String courseCode;
    private String regGroupId;
    private String regGroupCode;
    private String courseTitle;
    private String credits;
    private String grading;
    private List<String> creditOptions;
    private Map<String, String> gradingOptions;
    private int gradingOptionCount;
    private List<ActivityOfferingScheduleResult> schedule;
    private List<Link> actionLinks;

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

    public List<ActivityOfferingScheduleResult> getSchedule() {
        if(schedule == null){
            schedule = new ArrayList<ActivityOfferingScheduleResult>();
        }
        return schedule;
    }

    public void setSchedule(List<ActivityOfferingScheduleResult> schedule) { this.schedule = schedule; }

    public String getRegGroupId() {
        return regGroupId;
    }

    public void setRegGroupId(String regGroupId) {
        this.regGroupId = regGroupId;
    }

    public List<Link> getActionLinks() {
        if(actionLinks == null){
            actionLinks = new ArrayList<Link>();
        }
        return actionLinks;
    }

    public void setActionLinks(List<Link> actionLinks) {
        this.actionLinks = actionLinks;
    }

    public int getGradingOptionCount() {
        gradingOptionCount = getGradingOptions().size();
        return gradingOptionCount;
    }

    public void setGradingOptionCount(int gradingOptionCount) {
        this.gradingOptionCount = gradingOptionCount;
    }
}