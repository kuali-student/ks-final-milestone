package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;

import com.google.gwt.user.client.ui.Widget;

public class SwapCondition {
    private FieldDescriptor fd;
    private String value;
    public FieldDescriptor getFd() {
        return fd;
    }
    public void setFd(FieldDescriptor fd) {
        this.fd = fd;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    private String nvl(String inString) {
        return (inString == null)? "" : inString;
    }
    public boolean evaluate(GroupSection section, Map<String, String> helperFieldKeys) {
        boolean result = false;
        String widgetValue = null;
        // could have used getField(String) method here but the getField method does
        // not check the fields of sub sections.
        List<FieldDescriptor> sectionFds = section.getFields();
        Widget fieldWidget = null;
        if (sectionFds != null) {
            for (FieldDescriptor sectionFd : sectionFds) {
                if (sectionFd.getFieldKey().equals(helperFieldKeys.get(fd.getFieldKey()))) {
                    fieldWidget = sectionFd.getFieldWidget();
                }
            }
        }
        if (fieldWidget != null && fieldWidget instanceof KSSelectItemWidgetAbstract) {
            widgetValue = ((KSSelectItemWidgetAbstract)fieldWidget).getSelectedItem();
        } else if (fieldWidget != null && fieldWidget instanceof KSPicker) {
            Widget inputWidget = ((KSPicker)fieldWidget).getInputWidget();
            widgetValue = ((KSSelectItemWidgetAbstract)inputWidget).getSelectedItem();
        }
        if (nvl(this.value).equals(widgetValue)) {
            result = true;
        }
        return result;
    }
}
