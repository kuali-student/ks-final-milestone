package org.kuali.student.common.ui.client.application;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationComposite  extends Composite {
	private final Panel body = new VerticalPanel();
	private final Header header = GWT.create(Header.class);
	private final SimplePanel content = new SimplePanel();
	
	public ApplicationComposite() {
		super.initWidget(body);
		body.add(header);
		body.add(content);
		super.addStyleName("KS-Application");
		content.addStyleName("KS-Application-Content");
	}
	
	public void setContent(Widget w) {
		content.setWidget(w);
	}
	
	public Widget getContent() {
		return content.getWidget();
	}
	 
}
