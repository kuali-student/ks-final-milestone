package org.kuali.student.ap.coursesearch.dataobject;

import java.util.List;

public class ActivityFormatDetailsWrapper {
    private String formatName;
    private String formatType;
    private String termId;
    private String courseOfferingCode;
    private String formatOfferingId;
    private List<ActivityOfferingDetailsWrapper> activityOfferingDetailsWrappers;
    private boolean validFormat;

    public ActivityFormatDetailsWrapper(String termId, String courseOfferingCode, String formatOfferingId, String activityFormatName, String activityTypeKey) {
        formatName = activityFormatName;
        formatType = activityTypeKey;
        this.termId = termId;
        this.courseOfferingCode = courseOfferingCode;
        this.formatOfferingId = formatOfferingId;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getFormatType() {
        return formatType;
    }

    /**
     * Get an XML safe representation of the formatType by replacing "." with "-"
     * @return A formatType with all occurrences of "." replaced with "-"
     */
    public String getXmlSafeFormatType() {
        return formatType.replace(".", "-");
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    public String getTermId() {
        return termId;
    }

    /**
     * Get an XML safe representation of the termId by replacing "." with "-"
     * @return A termId with all occurrences of "." replaced with "-"
     */
    public String getXmlSafeTermId() {
        return termId.replace(".", "-");
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getFormatOfferingId() {
        return formatOfferingId;
    }

    public void setFormatOfferingId(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
    }

    public List<ActivityOfferingDetailsWrapper> getActivityOfferingDetailsWrappers() {
        return activityOfferingDetailsWrappers;
    }

    public void setActivityOfferingDetailsWrappers(List<ActivityOfferingDetailsWrapper> activityOfferingDetailsWrappers) {
        this.activityOfferingDetailsWrappers = activityOfferingDetailsWrappers;
    }

    public boolean isValidFormat() {
        return validFormat;
    }

    public void setValidFormat(boolean validFormat) {
        this.validFormat = validFormat;
    }
}
