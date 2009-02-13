package org.kuali.student.core.ui.client.widgets;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;

public class KSInfoPopupPanel extends KSPopupPanel{
		
	public KSInfoPopupPanel(){
		super();
		popup.setModal(false);
		
		Window.addResizeHandler(new ResizeHandler(){
			public void onResize(ResizeEvent event) {
				if(isShowing){
					popup.center();
				}
			}
		});
		setupDefaultStyle();
	}
	
	private void setupDefaultStyle(){
		popup.addStyleName(KSStyles.KS_INFO_POPUP);
	}
	
	public void show(){
		popup.center();
		isShowing = true;
	}
	

	
}
