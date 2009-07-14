package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
/**
 * @deprecated
 * */
public abstract class KSConfirmButtonPanelAbstract extends Composite{
	
	public abstract void addConfirmHandler(ClickHandler handler);
	
	public abstract void addCancelHandler(ClickHandler handler);
	
	public abstract void setConfirmFocus();
	
	public abstract void setCancelFocus();
}
