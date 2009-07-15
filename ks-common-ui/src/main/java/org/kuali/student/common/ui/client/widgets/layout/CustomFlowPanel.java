package org.kuali.student.common.ui.client.widgets.layout;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class CustomFlowPanel extends FlowPanel {
    protected abstract String getFlowStyle();
    
    @Override
    public void add(Widget w) {
        w.addStyleName(getFlowStyle());
        super.add(w);
    }
}
