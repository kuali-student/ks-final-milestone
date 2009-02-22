package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;

public abstract class KSButton extends Composite{

	public abstract void init(String html, ClickHandler handler);

	public abstract Button getButton();

}
