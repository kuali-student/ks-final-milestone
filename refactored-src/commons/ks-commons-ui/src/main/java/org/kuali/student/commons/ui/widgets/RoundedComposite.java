package org.kuali.student.commons.ui.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class RoundedComposite extends Composite implements HasHorizontalAlignment, HasVerticalAlignment {
    final FlexTable panel = new FlexTable();
    final Image topLeft = new Image("images/rounded-panel-topleft.gif");
    final Image topRight = new Image("images/rounded-panel-topright.gif");
    final Image bottomLeft = new Image("images/rounded-panel-bottomleft.gif");
    final Image bottomRight = new Image("images/rounded-panel-bottomright.gif");
    
    final SimplePanel content = new SimplePanel();
    Widget widget;
    
    HorizontalAlignmentConstant horizontalAlignment = HasHorizontalAlignment.ALIGN_LEFT;
    VerticalAlignmentConstant verticalAlignment = HasVerticalAlignment.ALIGN_TOP;
    
    public RoundedComposite() {
        panel.setWidget(0, 0, topLeft);
        panel.setWidget(0, 2, topRight);
        panel.setWidget(1, 1, content);
        panel.setWidget(2, 0, bottomLeft);
        panel.setWidget(2, 2, bottomRight);
        
        panel.addStyleName("KS-RoundedComposite");
        panel.getCellFormatter().addStyleName(0, 1, "KS-RoundedComposite-TopMiddle");
        panel.getCellFormatter().addStyleName(1, 0, "KS-RoundedComposite-Left");
        panel.getCellFormatter().addStyleName(1, 1, "KS-RoundedComposite-Center");
        panel.getCellFormatter().addStyleName(1, 2, "KS-RoundedComposite-Right");
        panel.getCellFormatter().addStyleName(2, 1, "KS-RoundedComposite-BottomMiddle");
        
        panel.setCellPadding(0);
        panel.setCellSpacing(0);
        
        panel.getCellFormatter().setHeight(0, 0, "16px");
        panel.getCellFormatter().setHeight(0, 2, "16px");
        panel.getCellFormatter().setHeight(2, 0, "19px");
        panel.getCellFormatter().setHeight(2, 2, "19px");
        
        
        panel.getCellFormatter().setWidth(0, 0, "17px");
        panel.getCellFormatter().setWidth(0, 2, "17px");
        panel.getCellFormatter().setWidth(2, 0, "17px");
        panel.getCellFormatter().setWidth(2, 2, "17px");
        
        super.initWidget(panel);
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
        content.setWidget(widget);
        if (widget instanceof HasHorizontalAlignment) {
            ((HasHorizontalAlignment)widget).setHorizontalAlignment(horizontalAlignment);
        }
        if (widget instanceof HasVerticalAlignment) {
            ((HasVerticalAlignment)widget).setVerticalAlignment(verticalAlignment);
        }
    }
    
    public void remove(Widget widget) {
        content.remove(widget);
    }

    public HorizontalAlignmentConstant getHorizontalAlignment() {
        return this.horizontalAlignment;
    }

    public void setHorizontalAlignment(HorizontalAlignmentConstant align) {
        this.horizontalAlignment = align;
        panel.getCellFormatter().setHorizontalAlignment(1, 1, align);
        if (content instanceof HasHorizontalAlignment) {
            ((HasHorizontalAlignment)content).setHorizontalAlignment(align);
        }
        if (content.getWidget() != null && content.getWidget() instanceof HasHorizontalAlignment) {
            ((HasHorizontalAlignment)content.getWidget()).setHorizontalAlignment(align);
        }
    }

    public VerticalAlignmentConstant getVerticalAlignment() {
        return this.verticalAlignment;
    }

    public void setVerticalAlignment(VerticalAlignmentConstant align) {
        this.verticalAlignment = align;
        panel.getCellFormatter().setVerticalAlignment(1, 1, align);
        if (content instanceof HasVerticalAlignment) {
            ((HasVerticalAlignment)content).setVerticalAlignment(align);
        }
        if (content.getWidget() != null && content.getWidget() instanceof HasVerticalAlignment) {
            ((HasVerticalAlignment)content.getWidget()).setVerticalAlignment(align);
        }
    }
}
