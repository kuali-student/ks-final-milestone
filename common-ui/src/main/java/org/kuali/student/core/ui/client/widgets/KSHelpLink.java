package org.kuali.student.core.ui.client.widgets;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class KSHelpLink extends Composite {
//	private final Label widget = new Label("[?]");
    private final Image image = new Image("images/help.gif");
	public KSHelpLink() {
		super.initWidget(image);
		image.setTitle("Click for help on this field");
		image.addStyleDependentName("helpLink");
		image.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				Window.alert("This is some help.");
			}
		});
	}
}
