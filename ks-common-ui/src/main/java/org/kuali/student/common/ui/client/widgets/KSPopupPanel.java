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

package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSPopupPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * KSPopupPanel provides much of the same functionality as gwt PopupPanel.
 * 
 * @author Kuali Student Team

 * @deprecated


 */
public class KSPopupPanel extends KSPopupPanelAbstract{ 
	private final KSPopupPanelAbstract popupPanel = GWT.create(KSPopupPanelImpl.class);
//	private final SimplePanel content = new SimplePanel();
	
	
	
	public KSPopupPanel(){

	}
	/**
	 * Gets the X coordinate of the popup.
	 * 
	 * @return the x coordinate
	 */
	public int getX(){
	    //return popup.get.getPopupLeft();
	    return popupPanel.getX();
	}
    /**
     * Gets the Y coordinate of the popup.
     * 
     * @return the y coordinate
     */
    public int getY(){
       // return popup.getPopupTop();
        return popupPanel.getY();
    }
	/**
	 * Sets the x,y coordinates of the popup.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void setLocation(int x, int y){
	    popupPanel.setLocation(x, y);
	}
	/**
	 * Sets this popup's content to the widget passed in.
	 * 
	 * @param w the Widget to set this popup's content to
	 */
	public void setWidget(Widget w){
	//	content.setWidget(w);
	    popupPanel.setWidget(w);
	}
	/**
	 * Set the size of the popup panel to the width and height specified.
	 * 
	 * @param w the new width of the popup
	 * @param h the new height of the popup
	 */
	public void setPixelSize(int w, int h){
      //  content.setPixelSize(w,h);
	    popupPanel.setPixelSize(w,h);
	}

	/**
	 * Shows the popup panel.
	 * 
	 */
	public void show(){
	    popupPanel.show();
	}
	
	/**
	 * Centers and shows the popup panel.
	 * 
	 */
	public void center(){
	    popupPanel.center();
	}
	
	/**
	 * Hides the popup panel.
	 * 
	 */
	public void hide(){
	    popupPanel.hide();
	}
	
	/**
	 * Adds a style name to the popup panel.
	 * 
	 * @param style the name of the style to add
	 */
	public void addStyleName(String style){
	    popupPanel.addStyleName(style);
	}

	/**    
	 * Sets the modal flag of the popup panel.  When set to true, other components cannot be interacted with.
	 * 
	 * @param modal true if this popup is modal, false otherwise
	 */
	public void setModal(boolean modal) {
	    popupPanel.setModal(modal);
		
	}

	/**
	 * Returns true if this popup panel is showing, false otherwise.
	 * 
	 * @return true if this popup showing, false otherwise.
	 */
	public boolean isShowing() {
		return popupPanel.isShowing();
	}
	
	/**
	 * Adds a CloseHandler to this popup panel which handles close events.
	 * 
	 * @param handler a CloseHandler which will handle this popup's close events
	 */
	public void addCloseHandler(CloseHandler handler){
	    popupPanel.addCloseHandler(handler);
	}
	
	/**
	 * Sets the autohide flag.  When the flag is set to true, this popup panel will close when a user
	 * clicks outside it.
	 * 
	 * @param true if this popup is set to autohide, otherwise false.
	 */
	public void setAutoHide(boolean autoHide){
	    popupPanel.setAutoHide(autoHide);
	}
}
