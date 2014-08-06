package org.kuali.student.enrollment.registration.client.service.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by swedev on 7/29/2014.
 */
public class ResultValueGroupCourseOptions {
    String courseOfferingId;
    Map<String, String> creditOptions;
    Map<String, String> gradingOptions;


    public Map<String, String> getCreditOptions() {
        if(creditOptions == null){
            creditOptions = new HashMap<>();
        }
        return creditOptions;
    }

    public void setCreditOptions(Map<String, String> creditOptions) {
        this.creditOptions = creditOptions;
    }

    public Map<String, String> getGradingOptions() {
        if(gradingOptions == null){
            gradingOptions = new HashMap<>();
        }
        return gradingOptions;
    }

    public void setGradingOptions(Map<String, String> gradingOptions) {
        this.gradingOptions = gradingOptions;
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }
}
