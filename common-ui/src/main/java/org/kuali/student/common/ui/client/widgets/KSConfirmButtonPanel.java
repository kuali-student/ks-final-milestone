package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSConfirmButtonPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;

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
	 * Adds a click handler to handle click events on the Confirm button.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSConfirmButtonPanelAbstract#addConfirmHandler(com.google.gwt.event.dom.client.ClickHandler)
	 */
	public void addConfirmHandler(ClickHandler handler){
		confirmButtonPanel.addConfirmHandler(handler);
	}
	

	/**
	 * Adds a click handler to handle click events on the Cancel button.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSConfirmButtonPanelAbstract#addCancelHandler(com.google.gwt.event.dom.client.ClickHandler)
	 */
	public void addCancelHandler(ClickHandler handler){
		confirmButtonPanel.addCancelHandler(handler);
	}
	
	/**
	 * Sets the current focus to the Confirm button.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSConfirmButtonPanelAbstract#setConfirmFocus()
	 */
	public void setConfirmFocus(){
		confirmButtonPanel.setConfirmFocus();
	}
	
	
	/**
	 * Sets the current focus to the Cancel button.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSConfirmButtonPanelAbstract#setCancelFocus()
	 */
	public void setCancelFocus(){
		confirmButtonPanel.setCancelFocus();
	}

}
