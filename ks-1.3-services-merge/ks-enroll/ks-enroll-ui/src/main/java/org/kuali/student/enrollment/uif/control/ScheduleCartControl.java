package org.kuali.student.enrollment.uif.control;

import org.kuali.rice.krad.uif.control.ControlBase;
import org.kuali.rice.krad.uif.element.Header;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 9/2/11
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleCartControl extends ControlBase{
    private Header header;
    private String removeText;
    private String removeJsFunction;
    private String scheduleId;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getRemoveText() {
        return removeText;
    }

    public void setRemoveText(String removeText) {
        this.removeText = removeText;
    }

    public String getRemoveJsFunction() {
        return removeJsFunction;
    }

    public void setRemoveJsFunction(String removeJsFunction) {
        this.removeJsFunction = removeJsFunction;
    }
}
