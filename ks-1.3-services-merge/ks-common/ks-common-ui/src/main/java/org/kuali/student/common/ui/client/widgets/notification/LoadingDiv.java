package org.kuali.student.common.ui.client.widgets.notification;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class LoadingDiv extends PopupPanel{
	HorizontalPanel panel = new HorizontalPanel();
	public LoadingDiv(){
		Image Image = new Image("images/common/twiddler3.gif");
		panel.add(Image);
		panel.add(new Label("Loading..."));
		this.setWidget(panel);
		this.getElement().setAttribute("style", "z-index: 5; border: 1px solid grey;");
	}
}
