package org.kuali.student.common.ui.client.configurable.mvc.sections;

import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class InfoMessage extends Composite{
	private HorizontalBlockFlowPanel layout = new HorizontalBlockFlowPanel();
	private KSImage icon = Theme.INSTANCE.getCommonImages().getWarningDiamondIcon();
	//TODO make changes to label to take in special text or an object that can links in it
	private KSLabel message = new KSLabel();
	
	public InfoMessage(String text, boolean messageVisible){
		layout.add(icon);
		this.message.setText(text);
		layout.add(message);
		layout.addStyleName("ks-message-static");
		this.initWidget(layout);
		this.setVisible(messageVisible);
	}
	
	
	public InfoMessage(){
		layout.add(icon);
		layout.add(message);
		this.initWidget(layout);
		this.setVisible(false);
	}
	
	public void setMessage(String text, boolean messageVisible){
		message.setText(text);
		this.setVisible(messageVisible);
	}
}
