package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSPopupPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * KSPopupPanel provides much of the same functionality as gwt PopupPanel.
 * 
 * @author Kuali Student Team
 *
 */
public class KSPopupPanel extends KSPopupPanelAbstract{ 
	private final KSPopupPanelAbstract popupPanel = GWT.create(KSPopupPanelImpl.class);
//	private final SimplePanel content = new SimplePanel();
	
	
	
	public KSPopupPanel(){

	}
	/**
	 * Gets the X coordinate of the popup.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSPopupPanelAbstract#getX()
	 */
	public int getX(){
	    //return popup.get.getPopupLeft();
	    return popupPanel.getX();
	}
    /**
     * Gets the Y coordinate of the popup.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSPopupPanelAbstract#getY()
     */
    public int getY(){
       // return popup.getPopupTop();
        return popupPanel.getY();
    }
	/**
	 * Sets the x,y coordinates of the popup.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSPopupPanelAbstract#setLocation(int, int)
	 */
	public void setLocation(int x, int y){
	    popupPanel.setLocation(x, y);
	}
	/**
	 * Sets this popup's content to the widget passed in.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSPopupPanelAbstract#setWidget(com.google.gwt.user.client.ui.Widget)
	 */
	public void setWidget(Widget w){
	//	content.setWidget(w);
	    popupPanel.setWidget(w);
	}
	/**
	 * Set the size of the popup panel to the width and height specified.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSPopupPanelAbstract#setPixelSize(int, int)
	 */
	public void setPixelSize(int w, int h){
      //  content.setPixelSize(w,h);
	    popupPanel.setPixelSize(w,h);
	}

	/**
	 * Shows the popup panel.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSPopupPanelAbstract#show()
	 */
	public void show(){
	    popupPanel.show();
	}
	
	/**
	 * Centers and shows the popup panel.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSPopupPanelAbstract#center()
	 */
	public void center(){
	    popupPanel.center();
	}
	
	/**
	 * Hides the popup panel.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSPopupPanelAbstract#hide()
	 */
	public void hide(){
	    popupPanel.hide();
	}
	
	/**
	 * Adds a style name to the popup panel.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSPopupPanelAbstract#addStyleName(java.lang.String)
	 */
	public void addStyleName(String style){
	    popupPanel.addStyleName(style);
	}

	/**
	 * Sets the modal flag of the popup panel.  When set to true, other components cannot be interacted with.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSPopupPanelAbstract#setModal(boolean)
	 */
	public void setModal(boolean modal) {
	    popupPanel.setModal(modal);
		
	}

	/**
	 * Returns true if this popup panel is showing, false otherwise.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSPopupPanelAbstract#isShowing()
	 */
	public boolean isShowing() {
		return popupPanel.isShowing();
	}
	
	/**
	 * Adds a CloseHandler to this popup panel which handles close events.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSPopupPanelAbstract#addCloseHandler(com.google.gwt.event.logical.shared.CloseHandler)
	 */
	public void addCloseHandler(CloseHandler handler){
	    popupPanel.addCloseHandler(handler);
	}
	
	/**
	 * Sets the autohide flag.  When the flag is set to true, this popup panel will close when a user
	 * clicks outside it.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSPopupPanelAbstract#setAutoHide(boolean)
	 */
	public void setAutoHide(boolean autoHide){
	    popupPanel.setAutoHide(autoHide);
	}
}
