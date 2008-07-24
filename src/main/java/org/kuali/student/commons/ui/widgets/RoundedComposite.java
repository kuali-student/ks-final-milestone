package org.kuali.student.commons.ui.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class RoundedComposite extends Composite {
    final FlexTable panel = new FlexTable();
    final Image topLeft = new Image("images/rounded-panel-topleft.gif");
    final Image topRight = new Image("images/rounded-panel-topright.gif");
    final Image bottomLeft = new Image("images/rounded-panel-bottomleft.gif");
    final Image bottomRight = new Image("images/rounded-panel-bottomright.gif");
    
    Widget widget;
    
    public RoundedComposite() {
        panel.setWidget(0, 0, topLeft);
        panel.setWidget(0, 2, topRight);
        panel.setWidget(2, 0, bottomLeft);
        panel.setWidget(2, 2, bottomRight);
        
        panel.getCellFormatter().addStyleName(0, 1, "KS-RoundedComposite-TopMiddle");
        panel.getCellFormatter().addStyleName(1, 0, "KS-RoundedComposite-Left");
        panel.getCellFormatter().addStyleName(1, 2, "KS-RoundedComposite-Right");
        panel.getCellFormatter().addStyleName(2, 1, "KS-RoundedComposite-BottomMiddle");
            
        super.initWidget(panel);
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
        panel.setWidget(1, 1, widget);
    }
    
}
