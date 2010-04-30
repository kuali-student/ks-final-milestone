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


}
