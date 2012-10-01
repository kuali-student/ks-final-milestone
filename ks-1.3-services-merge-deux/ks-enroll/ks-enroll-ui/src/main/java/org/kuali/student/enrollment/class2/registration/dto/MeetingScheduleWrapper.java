package org.kuali.student.enrollment.class2.registration.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.dto.MeetingScheduleInfo;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.*;

//Needs to clean up the core slice codes
@Deprecated
public class MeetingScheduleWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    private MeetingScheduleInfo meetingSchedule;

    private String courseTitle;
    private String courseOfferingCode;
    private List<String> days;
    private String startTime;
    private String endTime;
    private String itemId;
    private String timeTypeName;
    private String regGroupTimesJsObject;

    public MeetingScheduleWrapper() {
        this.days = new ArrayList<String>();
    }

    public MeetingScheduleWrapper(MeetingScheduleInfo meetingSchedule) {
        this();
        this.meetingSchedule = meetingSchedule;
        // timePeriods string should be in format "TU,TH;1130,1330"
        String timePeriods = getMeetingSchedule().getScheduleId();
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

    public String getJsScheduleObject() {
        String daysArray = "[";
        for (String day : getDays()) {
            daysArray = daysArray + "'" + day + "',";
        }
        daysArray = StringUtils.removeEnd(daysArray, ",") + "]";
        String st = startTime.trim();
        String et = endTime.trim();
        if (st.length() == 3) {
            st = "0" + st;
        }
        if (et.length() == 3) {
            et = "0" + et;
        }
        if (itemId == null) {
            itemId = "";
        }
        if (timeTypeName == null) {
            timeTypeName = "";
        }

        return "{timeId:'" + itemId + "', timeType:'" + timeTypeName + "', days:" + daysArray + ", startTime: '" + st + "', endTime: '" + et
                + "', name: '" + courseOfferingCode + " " + courseTitle + "', displayableTime:'"+getDisplayableTime()+"' }";
    }

    public String getDisplayableTime() {
        //return human readable time format
        StringBuilder builder = new StringBuilder();
        for (String day : getDays()) {
            if (StringUtils.isNotBlank(builder.toString())) {
                builder.append(",");
            }
            builder.append(day);
        }

        String hrStart = startTime.trim();
        String hrEnd = endTime.trim();

        hrStart = convertToStandardTime(hrStart);
        hrEnd = convertToStandardTime(hrEnd);

        //Shorten string length if applicable
        //international ampm symbols
        Locale usersLocale = Locale.getDefault();
        DateFormatSymbols dfs = new DateFormatSymbols(usersLocale);
        String ampm[] = dfs.getAmPmStrings();
        String am = ampm[Calendar.AM];
        String pm = ampm[Calendar.PM];
        if (hrStart.endsWith(am) && hrEnd.endsWith(am)) {
            hrStart = hrStart.replace(am, "");

        } else if (hrStart.endsWith(pm) && hrEnd.endsWith(pm)) {
            hrStart = hrStart.replace(pm, "");
        }

        return hrStart + "-" + hrEnd + " | " + builder.toString();
    }

    private String convertToStandardTime(String time) {
        String minutes;
        String hours;
        if (time.length() == 3) {
            hours = time.substring(0, 1);
            minutes = time.substring(1);
        } else {
            hours = time.substring(0, 2);
            minutes = time.substring(2);
        }

        int hour = Integer.parseInt(hours);
        boolean isPM = false;
        if (hour > 12) {
            hour = hour - 12;
            isPM = true;
        } else if (hour == 12) {
            isPM = true;
        } else if (hour == 0) {
            hour = 12;
        }

        //international ampm symbols
        Locale usersLocale = Locale.getDefault();
        DateFormatSymbols dfs = new DateFormatSymbols(usersLocale);
        String ampm[] = dfs.getAmPmStrings();
        String am = ampm[Calendar.AM];
        String pm = ampm[Calendar.PM];

        hours = "" + hour;
        if (isPM) {
            time = hours + ":" + minutes + pm;
        } else {
            time = hours + ":" + minutes + am;
        }

        return time;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTimeTypeName() {
        return timeTypeName;
    }

    public void setTimeTypeName(String timeTypeName) {
        this.timeTypeName = timeTypeName;
    }

    public String getRegGroupTimesJsObject() {
        return regGroupTimesJsObject;
    }

    public void setRegGroupTimesJsObject(String regGroupTimesJsObject) {
        this.regGroupTimesJsObject = regGroupTimesJsObject;
    }
}
