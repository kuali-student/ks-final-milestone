package org.kuali.student.ap.coursesearch.dataobject;

import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 6/20/14
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class FormatOfferingInfoWrapper {
    private String formatName;
    private String formatId;
    private boolean selected;

    public FormatOfferingInfoWrapper(FormatOfferingInfo formatOfferingInfo) {
        formatName = formatOfferingInfo.getName();
        formatId = formatOfferingInfo.getFormatId();
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getFormatId() {
        return formatId;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
