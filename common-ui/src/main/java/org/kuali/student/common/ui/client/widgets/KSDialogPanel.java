package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSDialogPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The KSDialogPanel is a popup panel that can be movable and resizable.  To make the dialog
 * mobile a header title must be set, which creates a title bar to drag the window around with.  
 * Similarly, the resizable flag must be set to true(default) for the window
 * to be resized (through a draggable icon in the botton right hand corner).
 * 
 * @author Kuali Student Team
 *
 */
/**
 * This is a description of what this class does - Bsmith don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
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
     * @see org.kuali.student.common.ui.client.widgets.KSPopupPanel#addCloseHandler(com.google.gwt.event.logical.shared.CloseHandler)
     */
    public void addCloseHandler(CloseHandler handler) {
        dialogPanel.addCloseHandler(handler);
    }


    /**
     * Adds a style to this dialog panel.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSPopupPanel#addStyleName(java.lang.String)
     */
    public void addStyleName(String style) {
        dialogPanel.addStyleName(style);
    }


    /**
     * Centers the dialog panel (and shows it).
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSPopupPanel#center()
     */
    public void center() {
        dialogPanel.center();
    }


    /**
     * Gets the X coordinate of the dialog.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSPopupPanel#getX()
     */
    public int getX() {
        return dialogPanel.getX();
    }


    /**
     * Gets the Y coordinate of the dialog.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSPopupPanel#getY()
     */
    public int getY() {
        return dialogPanel.getY();
    }


    /**
     * Hides the dialog panel.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSPopupPanel#hide()
     */
    public void hide() {
        dialogPanel.hide();
    }


    /**
     * If this panel is currently being shown, returns true.  Otherwise, returns
     * false.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSPopupPanel#isShowing()
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
     * @see org.kuali.student.common.ui.client.widgets.KSPopupPanel#setAutoHide(boolean)
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
     * @see org.kuali.student.common.ui.client.widgets.KSPopupPanel#setLocation(int, int)
     */
    public void setLocation(int x, int y) {
        dialogPanel.setLocation(x, y);
    }


    /**
     * Sets this dialog's modality.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSPopupPanel#setModal(boolean)
     */
    public void setModal(boolean modal) {
        dialogPanel.setModal(modal);
    }


    /**
     * Set the size of this dialog to the width and height specified.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSPopupPanel#setPixelSize(int, int)
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
     * @see org.kuali.student.common.ui.client.widgets.KSPopupPanel#setWidget(com.google.gwt.user.client.ui.Widget)
     */
    public void setWidget(Widget w) {
        dialogPanel.setWidget(w);
    }


    /**
     * Shows the dialog panel.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSPopupPanel#show()
     */
    public void show() {
        dialogPanel.show();
    }

}
