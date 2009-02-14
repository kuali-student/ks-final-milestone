package org.kuali.student.core.ui.client.widgets;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

public class KSModalPopupPanel extends KSPopupPanel{
	private PopupPanel popup = new PopupPanel();
	private PopupPanel glass = new PopupPanel();
	private boolean isShowing = false;
	
	public KSModalPopupPanel(){
		super();
		
		popup.setModal(true);
		
		Window.addResizeHandler(new ResizeHandler(){

			public void onResize(ResizeEvent event) {
				if(isShowing){
					KSModalPopupPanel.this.show();
				}
			}
		});
	}
	
	public void show(){
		glass.addStyleName(KSStyles.KS_MOUSE_WAIT);
		glass.show();
		popup.center();
		isShowing = true;
	}
	
	public void hide(){
		super.hide();
		glass.removeStyleName(KSStyles.KS_MOUSE_WAIT);
		glass.hide();
	}
	
	private void setupDefaultStyle(){
		popup.addStyleName(KSStyles.KS_MODAL_POPUP);
		glass.addStyleName(KSStyles.KS_MODAL_GLASS);
	}
	
}
