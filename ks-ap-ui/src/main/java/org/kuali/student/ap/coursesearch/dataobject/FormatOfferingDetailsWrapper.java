package org.kuali.student.ap.coursesearch.dataobject;

import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 6/9/14
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class FormatOfferingDetailsWrapper {
    private String formatName;
    private List<ActivityOfferingDetailsWrapper> activityOfferingDetailsWrappers;

    public FormatOfferingDetailsWrapper(FormatOfferingInfo formatOffering) {
        formatName = formatOffering.getName();
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public List<ActivityOfferingDetailsWrapper> getActivityOfferingDetailsWrappers() {
        return activityOfferingDetailsWrappers;
    }

    public void setActivityOfferingDetailsWrappers(List<ActivityOfferingDetailsWrapper> activityOfferingDetailsWrappers) {
        this.activityOfferingDetailsWrappers = activityOfferingDetailsWrappers;
    }
}
