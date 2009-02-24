package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public abstract class KSDisclosureSection extends Composite{
	
	public abstract void init(String headerText, Widget headerWidget, boolean isOpen);

    public abstract void clear();

    public abstract void add(Widget w);

    public abstract boolean remove(Widget w);

    public abstract boolean isOpen();

    public abstract boolean isVisible();

    public abstract boolean isAnimationEnabled();

    public abstract void setOpen(boolean isOpen);

    public abstract void setVisible(boolean visible);

	

}
