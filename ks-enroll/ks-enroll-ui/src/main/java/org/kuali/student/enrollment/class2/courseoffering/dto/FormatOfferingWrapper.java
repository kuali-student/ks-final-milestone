package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

import java.io.Serializable;

public class FormatOfferingWrapper implements Serializable {
    private FormatOfferingInfo formatOfferingInfo;
    private String formatType;

    public FormatOfferingWrapper() {
        formatOfferingInfo = new FormatOfferingInfo();
//        formatOfferingInfo.setStateKey(CourseOfferingServiceConstants.MILESTONE_DRAFT_STATE_KEY);
    }

    public FormatOfferingWrapper(FormatOfferingInfo formatOfferingInfo) {
        this.formatOfferingInfo = formatOfferingInfo;
    }

    public FormatOfferingInfo getFormatOfferingInfo() {
        return formatOfferingInfo;
    }

    public void setFormatOfferingInfo(FormatOfferingInfo formatOfferingInfo) {
        this.formatOfferingInfo = formatOfferingInfo;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

}
