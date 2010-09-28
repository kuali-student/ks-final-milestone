package org.kuali.student.common.ui.client.widgets.dialog;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ButtonEnum;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;

public class ButtonMessageDialog<T extends ButtonEnum> {
	
	private KSLabel messageLabel = new KSLabel();
	private SectionTitle title = SectionTitle.generateH3Title("");
	
	private FlowPanel layout = new FlowPanel();
	
	private KSLightBox dialog;
	private ButtonGroup<T> buttons;
	
	public ButtonMessageDialog(String titleText, String message, ButtonGroup<T> buttons){
		this.buttons = buttons;
		setupLayout(titleText, message);
	}
	
	private void setupLayout(String titleText, String message){
		//title.setText();
		dialog = new KSLightBox(titleText);		
		messageLabel.setText(message);
		layout.add(messageLabel);
		layout.add(buttons);
		layout.addStyleName("ks-confirmation-message-layout");
		messageLabel.setStyleName("ks-confirmation-message-label");
		dialog.setWidget(layout);
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
	
	public ButtonGroup<T> getButtonGroup(){
		return buttons;
	}
}
