package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSModalDialogPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSModalDialogPanel extends KSModalDialogPanelAbstract{ 
	private KSModalDialogPanelAbstract panel = GWT.create(KSModalDialogPanelImpl.class);
	
	public KSModalDialogPanel(){

		
	}
	@Override
	public void show(){
		panel.show();
	}
	@Override
	public void hide(){
	    panel.hide();
	}
	@Override
    public void addStyleName(String style) {
        panel.addStyleName(style);
    }
	@Override
    public void setHeader(String headerText) {
        panel.setHeader(headerText);
    }
	@Override
    public void setModal(boolean modal) {
        panel.setModal(modal);
    }
	@Override
    public void setWidget(Widget w) {
        panel.setWidget(w);
    }
	
}
