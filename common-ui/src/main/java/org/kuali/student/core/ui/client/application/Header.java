package org.kuali.student.core.ui.client.application;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;

public class Header extends Composite {
	private final FlowPanel panel = new FlowPanel();
	
	public Header() {
		super.initWidget(panel);
		panel.addStyleName("KS-Header");
		panel.add(new Image("images/logo-ks.gif"));
	}
}
