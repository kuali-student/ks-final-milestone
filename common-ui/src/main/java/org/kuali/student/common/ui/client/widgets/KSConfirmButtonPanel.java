package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.impl.KSConfirmButtonPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * A panel with a cancel and a confirm button, which represents a standard combination of buttons.
 * 
 * @author Kuali Student Team
 *
 */
public class KSConfirmButtonPanel extends KSConfirmButtonPanelAbstract{
	
	KSConfirmButtonPanelAbstract confirmButtonPanel = GWT.create(KSConfirmButtonPanelImpl.class);
	
	/**
	 * Creates the button panel.
	 * 
	 */
	public KSConfirmButtonPanel(){
		this.initWidget(confirmButtonPanel);
		
	}
	
	/**
	 * Sets the current focus to the Confirm button.
	 * 
	 */
	public void setConfirmFocus(){
		confirmButtonPanel.setConfirmFocus();
	}
	
	
	/**
	 * Sets the current focus to the Cancel button.
	 * 
	 */
	public void setCancelFocus(){
		confirmButtonPanel.setCancelFocus();
	}



    
    /**
     * Adds a callback that is called when the user presses the confirm or cancel button.  The callback
     * is returned true if the user confirms, false otherwise.
     * 
     */
    public void addConfirmationCallback(Callback<Boolean> callback) {
        confirmButtonPanel.addConfirmationCallback(callback);
        
    }

}
