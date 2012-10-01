package org.kuali.student.common.ui.client.widgets.field.layout.button;

import org.kuali.student.common.ui.client.widgets.KSButton;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class ButtonLayout extends Composite{
	protected FlowPanel layout = new FlowPanel();
	
	public ButtonLayout(){
		layout.addStyleName("ks-form-button-container");
		this.initWidget(layout);
	}
	
	public void addButton(KSButton button){
		button.addStyleName("ks-button-right-margin");
		layout.add(button);
	}
	
	public void removeButton(KSButton button){
		layout.remove(button);
	}
	
	public void setVisible(boolean visible){
		layout.setVisible(visible);
	}
}
