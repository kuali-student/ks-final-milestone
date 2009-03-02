package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSPopupPanel {
	private final PopupPanel popup = new PopupPanel();
//	private final SimplePanel content = new SimplePanel();
	
	private boolean isShowing = false;
	
	
	public KSPopupPanel(){
		
	//	popup.add(content);
		
		setupDefaultStyle();
	}
	public int getX(){
	    //return popup.get.getPopupLeft();
	    return popup.getAbsoluteLeft();
	}
    public int getY(){
       // return popup.getPopupTop();
        return popup.getAbsoluteTop();
    }
	public void setLocation(int x, int y){
	    popup.setPopupPosition(x, y);
	}
	public void setWidget(Widget w){
	//	content.setWidget(w);
	    popup.setWidget(w);
	}
	public void setPixelSize(int w, int h){
      //  content.setPixelSize(w,h);
        popup.setPixelSize(w,h);
	}

	public void show(){
		popup.show();
		isShowing = true;
	}
	
	public void center(){
		popup.center();
		isShowing = true;
	}
	
	public void hide(){
		popup.hide();
		isShowing = false;
	}
	
	private void setupDefaultStyle(){
		popup.addStyleName(KSStyles.KS_POPUP);	
		
	}
	
	public void addStyleName(String style){
		popup.addStyleName(style);
	}

	public void setModal(boolean modal) {
		popup.setModal(modal);
		
	}

	public boolean isShowing() {
		return isShowing;
	}
	
	public void addCloseHandler(CloseHandler handler){
		popup.addCloseHandler(handler);
	}
	
	public void setAutoHide(boolean autoHide){
		popup.setAutoHideEnabled(autoHide);
	}
}
