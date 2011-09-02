package org.kuali.student.enrollment.class2.registration.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.dto.MeetingScheduleInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class ScheduleDataWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    private MeetingScheduleInfo meetingSchedule;

    private String courseTitle;
    private String courseOfferingCode;
    private List<String> days;
    private String startTime;
    private String endTime;

    public ScheduleDataWrapper() {
        this.days = new ArrayList<String>();
    }

    public ScheduleDataWrapper(MeetingScheduleInfo meetingSchedule) {
        this();
        this.meetingSchedule = meetingSchedule;
        // timePeriods string should be in format "TU,TH;1130,1330"
        String timePeriods = getMeetingSchedule().getTimePeriods();
        String[] timePeriodsSplit = StringUtils.splitPreserveAllTokens(timePeriods, ";");
        Collections.addAll(days, StringUtils.split(timePeriodsSplit[0], ","));
        String[] timesArray = StringUtils.split(timePeriodsSplit[1], ",");
        this.startTime = timesArray[0];
        this.endTime = timesArray[1];
    }

    public MeetingScheduleInfo getMeetingSchedule() {
        return meetingSchedule;
    }

    public void setMeetingSchedule(MeetingScheduleInfo meetingSchedule) {
        this.meetingSchedule = meetingSchedule;
    }

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
        for(String day: getDays()){
            daysArray = daysArray + "'" + day + "',";
        }
        daysArray = StringUtils.removeEnd(daysArray, ",") + "]";
        return "{days:" + daysArray + ", startTime: '" + startTime + "', endTime: '" + endTime + "', name: '"
                + courseOfferingCode + " " + courseTitle + " " + getDisplayableTime() +"' }";
    }

    public String getDisplayableTime(){
        //return human readable time format
        StringBuffer buffer = new StringBuffer();
        for (String day : getDays()) {
            if (StringUtils.isNotBlank(buffer.toString())) {
                buffer.append(", ");
            }
            buffer.append(day);
        }
        buffer.append("      ").append(startTime).append("-").append(endTime);
        return buffer.toString();
    }
}
