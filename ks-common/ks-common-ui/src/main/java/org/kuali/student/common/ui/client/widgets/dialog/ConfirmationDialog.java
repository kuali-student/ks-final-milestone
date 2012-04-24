/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.ui.client.widgets.dialog;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;

public class ConfirmationDialog {
	private KSLightBox dialog;

	private KSButton cancel = new KSButton("Cancel", ButtonStyle.ANCHOR_LARGE_CENTERED);
	private KSButton confirm = new KSButton("Confirm", ButtonStyle.PRIMARY_SMALL);
	private HorizontalBlockFlowPanel buttonPanel = new HorizontalBlockFlowPanel();
	private KSLabel messageLabel = new KSLabel();
	private SectionTitle title = SectionTitle.generateH3Title("");
	
	private FlowPanel layout = new FlowPanel();
	
	public ConfirmationDialog(String titleText, String message){
		setupLayout(titleText, message);
	}
	
	public ConfirmationDialog(String titleText, String message, String confirmText){
		confirm.setText(confirmText);
		setupLayout(titleText, message);
	}
	
	private void setupLayout(String titleText, String message){
		//title.setText(titleText);
		dialog = new KSLightBox(titleText);

		cancel.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				dialog.hide();
			}
		});
		
		messageLabel.setText(message);
		dialog.addButton(confirm);
		dialog.addButton(cancel);
		layout.add(messageLabel);
		layout.add(buttonPanel);
		layout.addStyleName("ks-confirmation-message-layout");
		messageLabel.setStyleName("ks-confirmation-message-label");
		dialog.setWidget(layout);
		dialog.setSize(600, 130);
	}
	
	public void show(){
		dialog.show();
	}
	
	public void hide(){
		dialog.hide();
	}
	
	public void removeCloseLink(){
		dialog.removeCloseLink();
	}
	
	public HandlerRegistration addCloseHandler(CloseHandler handler){
		return dialog.addCloseHandler(handler);
	}
	
	public void setMessageText(String text){
		messageLabel.setText(text);
	}
	
	public KSButton getConfirmButton(){
		return confirm;
	}
	
	public KSButton getCancelButton(){
		return cancel;
	}
}
