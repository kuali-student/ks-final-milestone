package org.kuali.student.ap.coursesearch.dataobject;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 6/9/14
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class ActivityOfferingDetailsWrapper {
    private String activityOfferingId;
    private String activityOfferingCode;
    private String instructorId;
    private String days;
    private String time;
    private String location;
    private int currentEnrollment;
    private int maxEnrollment;
    private boolean honors;
    private String classUrl;
    private String requirementsUrl;
    private boolean selected;
    private String regGroupIdForSingleFO;

    public String getActivityOfferingId() {
        return activityOfferingId;
    }

    public void setActivityOfferingId(String activityOfferingId) {
        this.activityOfferingId = activityOfferingId;
    }

    public String getActivityOfferingCode() {
        return activityOfferingCode;
    }

    public void setActivityOfferingCode(String activityOfferingCode) {
        this.activityOfferingCode = activityOfferingCode;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCurrentEnrollment() {
        return currentEnrollment;
    }

    public void setCurrentEnrollment(int currentEnrollment) {
        this.currentEnrollment = currentEnrollment;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public boolean isHonors() {
        return honors;
    }

    public void setHonors(boolean honors) {
        this.honors = honors;
    }

    public String getClassUrl() {
        return classUrl;
    }

    public void setClassUrl(String classUrl) {
        this.classUrl = classUrl;
    }

    public String getRequirementsUrl() {
        return requirementsUrl;
    }

    public void setRequirementsUrl(String requirementsUrl) {
        this.requirementsUrl = requirementsUrl;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getRegGroupIdForSingleFO() {
        return regGroupIdForSingleFO;
    }

    public void setRegGroupIdForSingleFO(String regGroupIdForSingleFO) {
        this.regGroupIdForSingleFO = regGroupIdForSingleFO;
    }
}
