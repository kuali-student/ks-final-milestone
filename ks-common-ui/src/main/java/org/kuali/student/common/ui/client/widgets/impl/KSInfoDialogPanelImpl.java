package org.kuali.student.common.ui.client.widgets.impl;


import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;

public class KSInfoDialogPanelImpl extends KSDialogPanelImpl{ 
		
	public KSInfoDialogPanelImpl(){
		super();
		super.setModal(false);
		super.setAutoHide(true);
		super.setResizable(true);
		
		Window.addResizeHandler(new ResizeHandler(){
			public void onResize(ResizeEvent event) {
				if(isShowing()){
					show();
				}
			}
		});
		setupDefaultStyle();
	}
	
	private void setupDefaultStyle(){
		super.addStyleName(KSStyles.KS_INFO_POPUP);
	}



}
