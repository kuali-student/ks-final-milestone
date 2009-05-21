package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.mvc.Callback;
import com.google.gwt.user.client.ui.Composite;

public abstract class KSConfirmButtonPanelAbstract extends Composite{
	
    public abstract void addConfirmationCallback(final Callback<Boolean> callback);
	
	public abstract void setConfirmFocus();
	
	public abstract void setCancelFocus();
}
