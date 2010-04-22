/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

import org.kuali.student.common.ui.client.widgets.KSPopupPanelAbstract;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSPopupPanelImpl extends KSPopupPanelAbstract{ 
	private final PopupPanel popup = new PopupPanel();
//	private final SimplePanel content = new SimplePanel();
	
	private boolean isShowing = false;
	
	
	public KSPopupPanelImpl(){
		
	//	popup.add(content);
		
		setupDefaultStyle();
		this.addCloseHandler(new CloseHandler(){

            @Override
            public void onClose(CloseEvent event) {
                isShowing = false;
                
            }});
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
	@Override
    public void setWidget(Widget w){
	//	content.setWidget(w);
	    popup.setWidget(w);
	}
	@Override
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
	
	@Override
    public void addStyleName(String style){
		popup.addStyleName(style);
	}

	@Override
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
