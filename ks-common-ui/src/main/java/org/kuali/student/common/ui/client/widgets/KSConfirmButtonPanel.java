package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSConfirmButtonPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * A panel with a cancel and a confirm button, which represents a standard combination of buttons.
 * 
 * @author Kuali Student Team
 * @deprecated
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
	 * Adds a click handler to handle click events on the Confirm button.
	 * 
	 * @param handler the ClickHandler to handle click events on the Confirm button.
	 */
	public void addConfirmHandler(ClickHandler handler){
		confirmButtonPanel.addConfirmHandler(handler);
	}
	

	/**
	 * Adds a click handler to handle click events on the Cancel button.
	 * 
	 * @param handler the ClickHandler to handle click events on the Cancel button.
	 */
	public void addCancelHandler(ClickHandler handler){
		confirmButtonPanel.addCancelHandler(handler);
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

}
