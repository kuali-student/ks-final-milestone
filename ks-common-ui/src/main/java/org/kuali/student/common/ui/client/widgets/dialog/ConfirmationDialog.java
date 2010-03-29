package org.kuali.student.common.ui.client.widgets.dialog;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;

public class ConfirmationDialog {
	private KSLightBox dialog;
	
	//TODO use button grouping here
	private KSButton cancel = new KSButton("Cancel", ButtonStyle.DEFAULT_ANCHOR);
	private KSButton confirm = new KSButton("Confirm", ButtonStyle.PRIMARY_SMALL);
	private HorizontalBlockFlowPanel buttonPanel = new HorizontalBlockFlowPanel();
	private KSLabel messageLabel = new KSLabel();
	
	private FlowPanel layout = new FlowPanel();
	
	public ConfirmationDialog(String titleText, String message){
		setupLayout(titleText, message);
	}
	
	public ConfirmationDialog(String titleText, String message, String confirmText){
		confirm.setText(confirmText);
		setupLayout(titleText, message);
	}
	
	private void setupLayout(String titleText, String message){
		dialog = new KSLightBox(titleText);

		cancel.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				dialog.hide();
			}
		});
		
		messageLabel.setText(message);
		buttonPanel.add(cancel);
		buttonPanel.add(confirm);
		layout.add(messageLabel);
		layout.add(buttonPanel);
		dialog.setWidget(layout);
		
	}
	
	public void show(){
		dialog.show();
	}
	
	public void hide(){
		dialog.hide();
	}
	
	public KSButton getConfirmButton(){
		return confirm;
	}
	
	public KSButton getCancelButton(){
		return cancel;
	}
}
