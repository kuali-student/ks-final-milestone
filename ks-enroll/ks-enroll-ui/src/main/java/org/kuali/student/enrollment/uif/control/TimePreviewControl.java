package org.kuali.student.enrollment.uif.control;

import org.kuali.rice.krad.uif.control.ControlBase;

public class TimePreviewControl extends ControlBase{
    private String scheduleId;
    private String hoverComponentClass;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getHoverComponentClass() {
        return hoverComponentClass;
    }

    /**
     * It should be noted that this will match on the closest element to this control
     * with this class, traversing the DOM tree up from this control.
     */
    public void setHoverComponentClass(String hoverComponentClass) {
        this.hoverComponentClass = hoverComponentClass;
    }
}
