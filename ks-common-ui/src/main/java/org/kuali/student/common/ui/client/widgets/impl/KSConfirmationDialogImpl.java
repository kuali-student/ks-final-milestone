/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.images.KSImages;
import org.kuali.student.common.ui.client.widgets.KSConfirmButtonPanel;
import org.kuali.student.common.ui.client.widgets.KSConfirmationDialogAbstract;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

@Deprecated
public class KSConfirmationDialogImpl extends KSConfirmationDialogAbstract{ 
	
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
	
	
	public KSConfirmationDialogImpl(){
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
				KSConfirmationDialogImpl.this.hide();
				canceled = true;
			}
		});
		content.add(buttonPanel);
		super.setWidget(content);
	}
	
	@Override
    public void show(){
		super.show();
		canceled = false;
	}
	
	@Override
    public void center(){
		super.show();
		canceled = false;
	}
	
	@Override
    public void setWidget(Widget w){
		widgetWrapper.setWidget(w);
	}
	
	@Override
    public boolean isCanceled() {
		return canceled;
	}

	@Override
    public String getMessageTitle() {
		return messageTitle;
	}

	@Override
    public void setMessageTitle(String messageTitle) {
		titleLabel.setText(messageTitle);
		this.messageTitle = messageTitle;
	}
	
	public void addConfirmHandler(ClickHandler handler){
	    buttonPanel.addConfirmHandler(handler);
	}
	
	private void setupDefaultStyle(){
		widgetWrapper.addStyleName(KSStyles.KS_CONFIRM_DIALOG_CONTENT);
		titleLabel.addStyleName(KSStyles.KS_CONFIRM_DIALOG_TITLE);
		titlePanel.addStyleName(KSStyles.KS_CONFIRM_DIALOG_TITLE_PANEL);
		this.addStyleName(KSStyles.KS_CONFIRM_DIALOG);
		confirmImage.addStyleName(KSStyles.KS_CONFIRM_DIALOG_IMAGE);
	}
	
}
