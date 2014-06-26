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

    private List<ActivityFormatDetailsWrapper> activityFormatDetailsWrappers;


    public FormatOfferingInfoWrapper(FormatOfferingInfo formatOfferingInfo) {
        formatOfferingName = formatOfferingInfo.getName();
        formatOfferingId = formatOfferingInfo.getId();
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

    public List<ActivityFormatDetailsWrapper> getActivityFormatDetailsWrappers() {
        return activityFormatDetailsWrappers;
    }

    public void setActivityFormatDetailsWrappers(List<ActivityFormatDetailsWrapper> activityFormatDetailsWrappers) {
        this.activityFormatDetailsWrappers = activityFormatDetailsWrappers;
    }
}
