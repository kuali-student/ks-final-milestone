package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSConfirmButtonPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class KSConfirmButtonPanel extends KSConfirmButtonPanelAbstract{
	
	KSConfirmButtonPanelAbstract confirmButtonPanel = GWT.create(KSConfirmButtonPanelImpl.class);
	
	public KSConfirmButtonPanel(){
		this.initWidget(confirmButtonPanel);
		
	}

	public void addConfirmHandler(ClickHandler handler){
		confirmButtonPanel.addConfirmHandler(handler);
	}
	
	public void addCancelHandler(ClickHandler handler){
		confirmButtonPanel.addCancelHandler(handler);
	}
	
	public void setConfirmFocus(){
		confirmButtonPanel.setConfirmFocus();
	}
	
	public void setCancelFocus(){
		confirmButtonPanel.setCancelFocus();
	}

}
