package org.kuali.student.common.ui.client.configurable.mvc.sections;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

public class ClearBreak extends Composite{
	public ClearBreak(){
		HTML html = new HTML("<br class='clear'/>");
		//html.addStyleName("clear");
		this.initWidget(html);
	}
}
