package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSDialogPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSDialogPanel extends KSPopupPanel{ 
    private final KSDialogPanelAbstract dialogPanel = GWT.create(KSDialogPanelImpl.class);

	
	public KSDialogPanel(){
	}


    public void addCloseHandler(CloseHandler handler) {
        dialogPanel.addCloseHandler(handler);
    }


    public void addStyleName(String style) {
        dialogPanel.addStyleName(style);
    }


    public void center() {
        dialogPanel.center();
    }


    public boolean equals(Object obj) {
        return dialogPanel.equals(obj);
    }


    public int getX() {
        return dialogPanel.getX();
    }


    public int getY() {
        return dialogPanel.getY();
    }


    public int hashCode() {
        return dialogPanel.hashCode();
    }


    public void hide() {
        dialogPanel.hide();
    }


    public boolean isShowing() {
        return dialogPanel.isShowing();
    }


    public boolean removeHeader() {
        return dialogPanel.removeHeader();
    }


    public void setAutoHide(boolean autoHide) {
        dialogPanel.setAutoHide(autoHide);
    }


    public void setHeader(String headerText) {
        dialogPanel.setHeader(headerText);
    }


    public void setLocation(int x, int y) {
        dialogPanel.setLocation(x, y);
    }


    public void setModal(boolean modal) {
        dialogPanel.setModal(modal);
    }


    public void setPixelSize(int w, int h) {
        dialogPanel.setPixelSize(w, h);
    }

 
    public void setResizable(boolean resizable) {
        dialogPanel.setResizable(resizable);
    }


    public void setWidget(Widget w) {
        dialogPanel.setWidget(w);
    }


    public void show() {
        dialogPanel.show();
    }


    public String toString() {
        return dialogPanel.toString();
    }
}
