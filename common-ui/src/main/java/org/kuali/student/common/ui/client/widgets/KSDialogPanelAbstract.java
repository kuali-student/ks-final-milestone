package org.kuali.student.common.ui.client.widgets;


import com.google.gwt.user.client.ui.Widget;

public abstract class KSDialogPanelAbstract extends KSPopupPanel{  

	public abstract void setHeader(String headerText);
	
	public abstract boolean removeHeader();
  
    public abstract void setResizable(boolean resizable);
   	
}
