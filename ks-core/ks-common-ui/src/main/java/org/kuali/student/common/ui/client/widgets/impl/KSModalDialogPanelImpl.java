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

import org.kuali.student.common.ui.client.widgets.KSModalDialogPanelAbstract;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSModalDialogPanelImpl extends KSModalDialogPanelAbstract{ 
	private PopupPanel glass = new PopupPanel();
	
	public KSModalDialogPanelImpl(){
		super();
		setupDefaultStyle();
		super.setModal(true);
		super.setResizable(false);
		
		Window.addResizeHandler(new ResizeHandler(){

			public void onResize(ResizeEvent event) {
				if(isShowing()){
					KSModalDialogPanelImpl.this.show();
				}
			}
		});
		
	}
	
	@Override
    public void show(){
		glass.addStyleName(KSStyles.KS_MOUSE_WAIT);
		glass.show();
		super.center();
	}
	
	@Override
    public void hide(){
		super.hide();
		glass.removeStyleName(KSStyles.KS_MOUSE_WAIT);
		glass.hide();
	}
	
	private void setupDefaultStyle(){
		super.addStyleName(KSStyles.KS_MODAL_POPUP);
		glass.addStyleName(KSStyles.KS_MODAL_GLASS);
	}

    @Override
    public void addStyleName(String style) {
        // TODO Gary Struthers - THIS METHOD NEEDS JAVADOCS
        super.addStyleName(style);
    }

    @Override
    public void setHeader(String headerText) {
        // TODO Gary Struthers - THIS METHOD NEEDS JAVADOCS
        super.setHeader(headerText);
    }

    @Override
    public void setWidget(Widget w) {
        // TODO Gary Struthers - THIS METHOD NEEDS JAVADOCS
        super.setWidget(w);
    }
	
}
