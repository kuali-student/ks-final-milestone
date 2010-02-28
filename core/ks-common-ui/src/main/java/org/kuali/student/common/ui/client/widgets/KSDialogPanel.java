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
package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSDialogPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * The KSDialogPanel is a popup panel that can be movable and resizable.  To make the dialog
 * mobile a header title must be set, which creates a title bar to drag the window around with.  
 * Similarly, the resizable flag must be set to true(default) for the window
 * to be resized (through a draggable icon in the botton right hand corner).
 * 
 * @author Kuali Student Team
 * @deprecated
 */
public class KSDialogPanel extends KSPopupPanel{ 
    private final KSDialogPanelAbstract dialogPanel = GWT.create(KSDialogPanelImpl.class);

	
	/**
	 * Creates a new dialog panel which is resizable
	 * 
	 */
	public KSDialogPanel(){
	}


    /**
     * Adds a CloseHandler on this dialog which handles any close events.  Close events are fired when the dialog's
     * hide() method is called.
     * 
     * @param handler the CloseHandler to handle close events on this dialog
     *      
     */
    public void addCloseHandler(CloseHandler handler) {
        dialogPanel.addCloseHandler(handler);
    }


    /**
     * Adds a style to this dialog panel.
     * 
     * @param style the style name to add
     */
    public void addStyleName(String style) {
        dialogPanel.addStyleName(style);
    }


    /**
     * Centers the dialog panel (and shows it).
     * 
     */
    public void center() {
        dialogPanel.center();
    }


    /**
     * Gets the X coordinate of the dialog.
     * 
     * @return the x coordinate
     */
    public int getX() {
        return dialogPanel.getX();
    }


    /**
     * Gets the Y coordinate of the dialog.
     * 
     * @return the y coordinate
     */
    public int getY() {
        return dialogPanel.getY();
    }


    /**
     * Hides the dialog panel.
     * 
     */
    public void hide() {
        dialogPanel.hide();
    }


    /**
     * If this panel is currently being shown, returns true.  Otherwise, returns
     * false.
     * 
     * @return true if the panel is showing, false otherwise
     */
    public boolean isShowing() {
        return dialogPanel.isShowing();
    }


    /**
     * Removes the header from this dialog panel.  This disables the movable functionality
     * of the panel.
     * 
     * @return True if the header was removed successfully, false otherwise.
     */
    public boolean removeHeader() {
        return dialogPanel.removeHeader();
    }


    /**
     * If set to true, auto hide is enable.  Auto hide automatically hides the dialog when
     * a user clicks out of it.
     * 
     * @param autoHide autohide flag value
     */
    public void setAutoHide(boolean autoHide) {
        dialogPanel.setAutoHide(autoHide);
    }


    /**
     * Set the header text.  This adds a header to the dialog panel which makes the dialog
     * movable by allowing a user to drag and drop the header.
     * 
     * @param headerText the title of the header/titlebar
     */
    public void setHeader(String headerText) {
        dialogPanel.setHeader(headerText);
    }


    /**
     * Sets the dialog to the x,y coordinates specified.
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void setLocation(int x, int y) {
        dialogPanel.setLocation(x, y);
    }


    /**
     * Sets the modal flag of the dialog panel.  When set to true, other components outside this panel cannot be interacted with.
     * 
     * @param modal the modal flag value
     */
    public void setModal(boolean modal) {
        dialogPanel.setModal(modal);
    }


    /**
     * Set the size of this dialog to the width and height specified.
     * 
     * @param w the new width of the dialog
     * @param h the new height of the dialog
     * 
     */
    public void setPixelSize(int w, int h) {
        dialogPanel.setPixelSize(w, h);
    }

 
    /**
     * Sets the resizable option of this dialog.  If set to true, this dialog is resizable by
     * user through dragging and dropping an image "handle" in the bottom right hand corner of the
     * panel.  If set to false, the panel is not resizable.
     *  
     * @param resizable true if the panel is resizable, false otherwise.
     */
    public void setResizable(boolean resizable) {
        dialogPanel.setResizable(resizable);
    }


    /**
     * Sets the content of this dialog panel.  Layout should be handled by the widget passed in.
     * 
     * @param w the Widget to be used by the dialog panel
     */
    public void setWidget(Widget w) {
        dialogPanel.setWidget(w);
    }


    /**
     * Shows the dialog panel.
     * 
     */
    public void show() {
        dialogPanel.show();
    }

}
