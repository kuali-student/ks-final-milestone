package org.kuali.student.common.ui.client.configurable.mvc.sections;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton.ButtonStyle;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class MultiplicityHeader extends Composite{
	
	private FlowPanel header = new FlowPanel();
	private FlowPanel actions = new FlowPanel();
	private FlowPanel clearDiv = new FlowPanel();
	private SectionTitle title;
	private KSLinkButton help;
	private KSLinkButton delete;
	
	public MultiplicityHeader(SectionTitle title){
		this.title = title;
		header.add(title);
		
		help = new KSLinkButton("?", ButtonStyle.HELP);
		delete = new KSLinkButton("X", ButtonStyle.DELETE);
		actions.add(help);
		actions.add(delete);
		actions.setStyleName("ks-form-header-title-actions");
		
		header.add(actions);
		
		clearDiv.setStyleName("clear");
		header.add(clearDiv);
		this.initWidget(header);
	}

	public void addDeleteHandler(ClickHandler handler){
		delete.addClickHandler(handler);
	}
	
	public void addHelpHandler(ClickHandler handler){
		help.addClickHandler(handler);
	}
	
}
