package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSModalPopupPanel extends KSPopupPanel{
	private PopupPanel glass = new PopupPanel();
	
	public KSModalPopupPanel(){
		super();
		setupDefaultStyle();
		super.setModal(true);
		
		Window.addResizeHandler(new ResizeHandler(){

			public void onResize(ResizeEvent event) {
				if(isShowing()){
					KSModalPopupPanel.this.show();
				}
			}
		});
		
	}
	
	public void show(){
		glass.addStyleName(KSStyles.KS_MOUSE_WAIT);
		glass.show();
		super.center();
	}
	
	public void hide(){
		super.hide();
		glass.removeStyleName(KSStyles.KS_MOUSE_WAIT);
		glass.hide();
	}
	
	private void setupDefaultStyle(){
		super.addStyleName(KSStyles.KS_MODAL_POPUP);
		glass.addStyleName(KSStyles.KS_MODAL_GLASS);
	}
	
}
