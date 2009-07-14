package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.logical.shared.CloseHandler;

import com.google.gwt.user.client.ui.Widget;
/**
 * @deprecated
 * */
public abstract class KSPopupPanelAbstract { 

	public abstract int getX();
    public abstract int getY();
	public abstract void setLocation(int x, int y);



	public abstract void show();
	
	public abstract void center();
	
	public abstract void hide();


	public abstract boolean isShowing();
	
	public abstract void addCloseHandler(CloseHandler handler);
	
	public abstract void setAutoHide(boolean autoHide);
    public abstract void setWidget(Widget w);
    public abstract void setPixelSize(int w, int h);
    public abstract void addStyleName(String style);
    public abstract void setModal(boolean modal);
}
