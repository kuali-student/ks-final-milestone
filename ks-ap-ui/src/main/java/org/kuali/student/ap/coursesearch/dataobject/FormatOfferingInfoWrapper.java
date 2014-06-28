package org.kuali.student.ap.coursesearch.dataobject;

import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 6/20/14
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class FormatOfferingInfoWrapper {
    private String formatOfferingName;
    private String formatOfferingId;
    private boolean selected;
    private String termId;
    private String courseOfferingCode;

    private List<ActivityFormatDetailsWrapper> activityFormatDetailsWrappers;


    public FormatOfferingInfoWrapper(FormatOfferingInfo formatOfferingInfo, String courseOfferingCode) {
        formatOfferingName = formatOfferingInfo.getName();
        formatOfferingId = formatOfferingInfo.getId();
        termId = formatOfferingInfo.getTermId();
        this.courseOfferingCode = courseOfferingCode;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getFormatOfferingName() {
        return formatOfferingName;
    }

    public void setFormatOfferingName(String formatOfferingName) {
        this.formatOfferingName = formatOfferingName;
    }

    public String getFormatOfferingId() {
        return formatOfferingId;
    }

    public void setFormatOfferingId(String formatOfferingId) {
        this.formatOfferingId = formatOfferingId;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    /**
     * Get an XML safe representation of the termId by replacing "." with "-"
     * @return A termId with all occurrences of "." replaced with "-"
     */
    public String getXmlSafeTermId() {
        return termId.replace(".", "-");
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public List<ActivityFormatDetailsWrapper> getActivityFormatDetailsWrappers() {
        return activityFormatDetailsWrappers;
    }

    public void setActivityFormatDetailsWrappers(List<ActivityFormatDetailsWrapper> activityFormatDetailsWrappers) {
        this.activityFormatDetailsWrappers = activityFormatDetailsWrappers;
    }
}
