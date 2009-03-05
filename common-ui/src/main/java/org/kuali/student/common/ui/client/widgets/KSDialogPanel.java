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
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSDialogPanel extends KSDialogPanelAbstract{  
    private final KSDialogPanelAbstract dialogPanel = GWT.create(KSDialogPanelImpl.class);

	
	public KSDialogPanel(){
	}
	
	
	public void setHeader(String headerText){
	    dialogPanel.setHeader(headerText);
	}
	
	public boolean removeHeader(){
		return dialogPanel.removeHeader();
	}

	
    public void setWidget(Widget w) {
        dialogPanel.setWidget(w);
    }
    
    public void show(){
        dialogPanel.show();
    }
    
    public void center(){
        dialogPanel.center();
    }

    
    public void setResizable(boolean resizable){
        dialogPanel.setResizable(resizable);
    }
	
}
