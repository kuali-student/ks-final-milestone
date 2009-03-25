package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSConfirmButtonPanelAbstract;
import org.kuali.student.common.ui.client.widgets.KSMessages;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class KSConfirmButtonPanelImpl extends KSConfirmButtonPanelAbstract{
    private KSButton confirm = new KSButton(Application.getApplicationContext().getMessage(KSMessages.CONFIRM));
	private KSButton cancel = new KSButton(Application.getApplicationContext().getMessage(KSMessages.CANCEL));
	
	private SimplePanel confirmPanel = new SimplePanel();
	private SimplePanel cancelPanel = new SimplePanel();
	
	
	private FlowPanel buttonPanel = new FlowPanel();
	
	public KSConfirmButtonPanelImpl(){
		setupDefaultStyle();

		buttonPanel.add(cancel);
		buttonPanel.add(confirm);
		
		initWidget(buttonPanel);
		
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		buttonPanel.setHeight(confirm.getOffsetHeight() + "px");
	}

	public void addConfirmHandler(ClickHandler handler){
		confirm.addClickHandler(handler);
	}
	
	public void addCancelHandler(ClickHandler handler){
		cancel.addClickHandler(handler);
	}
	
	public void setConfirmFocus(){
		confirm.setFocus(true);
	}
	
	public void setCancelFocus(){
		cancel.setFocus(true);
	}
	
	private void setupDefaultStyle(){
		cancel.addStyleName(KSStyles.KS_CBP_CANCEL_BUTTON);
		confirm.addStyleName(KSStyles.KS_CBP_CONFIRM_BUTTON);
		buttonPanel.addStyleName(KSStyles.KS_CBP_BUTTON_PANEL);
	}

}
