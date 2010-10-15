package org.kuali.student.common.ui.client.widgets.notification;

import org.kuali.student.common.ui.client.widgets.KSImage;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class LoadingDiv extends PopupPanel{
	HorizontalPanel panel = new HorizontalPanel();
	public LoadingDiv(){
		KSImage kSImage = new KSImage("images/common/twiddler3.gif");
		panel.add(kSImage);
		panel.add(new Label("Loading..."));
		this.setWidget(panel);
		this.getElement().setAttribute("style", "z-index: 5; border: 1px solid grey;");
	}
}
