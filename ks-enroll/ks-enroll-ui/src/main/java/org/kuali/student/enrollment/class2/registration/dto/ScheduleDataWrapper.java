package org.kuali.student.enrollment.class2.registration.dto;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 9/1/11
 * Time: 1:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleDataWrapper {
    private String courseTitle;
    private String courseOfferingCode;
    private List<String> days;
    private String startTime;
    private String endTime;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getJsScheduleObject(){
        String daysArray = "[";
        for(String day: days){
            daysArray = daysArray + "'" + day + "',";
        }
        daysArray = StringUtils.removeEnd(daysArray, ",") + "]";
        return "{days:" + daysArray + ", startTime: '" + startTime + "', endTime: '" + endTime + "', name: '"
                + courseOfferingCode + " " + courseTitle + " " + getDisplayableTime() +"' }";
    }

    public String getDisplayableTime(){
        //return human readable time format
        return "";
    }
}
