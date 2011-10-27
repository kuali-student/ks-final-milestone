package org.kuali.student.enrollment.uif.control;

import org.kuali.rice.krad.uif.control.ControlBase;
import org.kuali.rice.krad.uif.field.HeaderField;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 9/2/11
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleCartControl extends ControlBase{
    private HeaderField headerField;
    private String scheduleId;

    public HeaderField getHeaderField() {
        return headerField;
    }

    public void setHeaderField(HeaderField headerField) {
        this.headerField = headerField;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
}
