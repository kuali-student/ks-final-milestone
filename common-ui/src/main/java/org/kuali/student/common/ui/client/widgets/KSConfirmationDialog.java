package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.images.KSImages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSConfirmationDialog extends KSModalDialogPanel{
	
	private boolean canceled = false;
	private String messageTitle;
	private final KSLabel titleLabel = new KSLabel();
	private final SimplePanel widgetWrapper = new SimplePanel();
	private final VerticalPanel content = new VerticalPanel();
	private final HorizontalPanel titlePanel = new HorizontalPanel();
	private final HorizontalPanel messagePanel = new HorizontalPanel();
	private final KSConfirmButtonPanel buttonPanel = new KSConfirmButtonPanel();
	//private final KSImages images = (KSImages)GWT.create(KSImages.class);
	private final Image confirmImage = new Image("images/confirm.png");//images.defaultIcon().createImage();
	
	
	public KSConfirmationDialog(){
		super();
		setupDefaultStyle();
		titlePanel.add(confirmImage);
		titlePanel.add(titleLabel);
		content.add(titlePanel);
		messagePanel.add(widgetWrapper);
		content.add(messagePanel);
		buttonPanel.addCancelHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				KSConfirmationDialog.this.hide();
				canceled = true;
			}
		});
		content.add(buttonPanel);
		super.setWidget(content);
	}
	
	public void show(){
		super.show();
		canceled = false;
	}
	
	public void center(){
		super.show();
		canceled = false;
	}
	
	public void setWidget(Widget w){
		widgetWrapper.setWidget(w);
	}
	
	public boolean isCanceled() {
		return canceled;
	}
	
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		titleLabel.setText(messageTitle);
		this.messageTitle = messageTitle;
	}	
	
	private void setupDefaultStyle(){
		widgetWrapper.addStyleName(KSStyles.KS_CONFIRM_DIALOG_CONTENT);
		titleLabel.addStyleName(KSStyles.KS_CONFIRM_DIALOG_TITLE);
		titlePanel.addStyleName(KSStyles.KS_CONFIRM_DIALOG_TITLE_PANEL);
		this.addStyleName(KSStyles.KS_CONFIRM_DIALOG);
		confirmImage.addStyleName(KSStyles.KS_CONFIRM_DIALOG_IMAGE);
	}
	
}
