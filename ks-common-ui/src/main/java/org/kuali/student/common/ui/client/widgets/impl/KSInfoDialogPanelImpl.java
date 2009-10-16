/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
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
