package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSPopupPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSPopupPanel extends KSPopupPanelAbstract{ 
	private final KSPopupPanelAbstract popupPanel = GWT.create(KSPopupPanelImpl.class);
//	private final SimplePanel content = new SimplePanel();
	
	
	
	public KSPopupPanel(){

	}
	public int getX(){
	    //return popup.get.getPopupLeft();
	    return popupPanel.getX();
	}
    public int getY(){
       // return popup.getPopupTop();
        return popupPanel.getY();
    }
	public void setLocation(int x, int y){
	    popupPanel.setLocation(x, y);
	}
	public void setWidget(Widget w){
	//	content.setWidget(w);
	    popupPanel.setWidget(w);
	}
	public void setPixelSize(int w, int h){
      //  content.setPixelSize(w,h);
	    popupPanel.setPixelSize(w,h);
	}

	public void show(){
	    popupPanel.show();
	}
	
	public void center(){
	    popupPanel.center();
	}
	
	public void hide(){
	    popupPanel.hide();
	}
	
	public void addStyleName(String style){
	    popupPanel.addStyleName(style);
	}

	public void setModal(boolean modal) {
	    popupPanel.setModal(modal);
		
	}

	public boolean isShowing() {
		return popupPanel.isShowing();
	}
	
	public void addCloseHandler(CloseHandler handler){
	    popupPanel.addCloseHandler(handler);
	}
	
	public void setAutoHide(boolean autoHide){
	    popupPanel.setAutoHide(autoHide);
	}
}
