package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public abstract class KSLabel extends Composite{
	public abstract void init(String text, boolean wordWrap);
	public abstract Label getLabel();
}
