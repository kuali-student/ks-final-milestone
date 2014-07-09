package org.kuali.student.ap.coursesearch.dataobject;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;

import java.util.List;

public class ActivityOfferingDetailsWrapper {
    private String activityOfferingId;
    private String activityOfferingCode;
    //From Bonnie: need to investigate how to handle multiple instructors vs. single instructor
    private String instructorName;
    private String firstInstructorDisplayName;
    private String instructorDisplayNames;

    private String days;
    private String time;
    private String location;
    private int currentEnrollment;
    private int maxEnrollment;
    private boolean honors;
    private String classUrl;
    private List<String> activityOfferingRequisites;
    private boolean selected;
    private String regGroupCode;
    private String regGroupId;
    private String activityFormatName;
    private boolean partOfRegGroup = false;
    private boolean singleFormatOffering;
    private boolean inPlan;
    private boolean validActivity;

    public ActivityOfferingDetailsWrapper() {
    }

    public ActivityOfferingDetailsWrapper(ActivityOfferingInfo activityOffering, boolean partOfRegGroup,
            boolean singleFormatOffering) {
        this.activityOfferingId = activityOffering.getId();
        this.activityOfferingCode = activityOffering.getActivityCode();
        this.maxEnrollment = activityOffering.getMaximumEnrollment() == null ? 0 : activityOffering.getMaximumEnrollment();
        this.honors = Boolean.TRUE.equals(activityOffering.getIsHonorsOffering());
        this.partOfRegGroup = partOfRegGroup;
        this.singleFormatOffering = singleFormatOffering;
    }

    public String getActivityFormatName() {
        return activityFormatName;
    }

    public void setActivityFormatName(String activityFormatName) {
        this.activityFormatName = activityFormatName;
    }

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

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getFirstInstructorDisplayName() {
        return firstInstructorDisplayName;
    }

    public void setFirstInstructorDisplayName(String firstInstructorDisplayName) {
        this.firstInstructorDisplayName = firstInstructorDisplayName;
    }

    public String getInstructorDisplayNames() {
        return instructorDisplayNames;
    }

    public void setInstructorDisplayNames(String instructorDisplayNames) {
        this.instructorDisplayNames = instructorDisplayNames;
    }

    public void setInstructorDisplayNames(String instructorDisplayNames, boolean appendForDisplay) {
        if (appendForDisplay && this.instructorDisplayNames != null) {
            this.instructorDisplayNames = this.instructorDisplayNames + "<br>" + StringUtils.defaultString(instructorDisplayNames);
        } else {
            this.instructorDisplayNames = StringUtils.defaultString(instructorDisplayNames);
        }

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

    public List<String> getActivityOfferingRequisites() {
        return activityOfferingRequisites;
    }

    public void setActivityOfferingRequisites(List<String> activityOfferingRequisites) {
        this.activityOfferingRequisites = activityOfferingRequisites;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getRegGroupCode() {
        return regGroupCode;
    }

    public void setRegGroupCode(String regGroupCode) {
        this.regGroupCode = regGroupCode;
    }

    public boolean getPartOfRegGroup() {
        return partOfRegGroup;
    }

    public void setPartOfRegGroup(boolean partOfRegGroup) {
        this.partOfRegGroup = partOfRegGroup;
    }

    public boolean getSingleFormatOffering() {
        return singleFormatOffering;
    }

    public void setSingleFormatOffering(boolean singleFormatOffering) {
        this.singleFormatOffering = singleFormatOffering;
    }

    public String getRegGroupId() {
        return regGroupId;
    }

    public void setRegGroupId(String regGroupId) {
        this.regGroupId = regGroupId;
    }

    public boolean isInPlan() {
        return inPlan;
    }

    public void setInPlan(boolean inPlan) {
        this.inPlan = inPlan;
    }

    public boolean isValidActivity() {
        return validActivity;
    }

    public void setValidActivity(boolean validActivity) {
        this.validActivity = validActivity;
    }
}
