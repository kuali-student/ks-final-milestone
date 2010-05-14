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

import org.kuali.student.common.ui.client.widgets.impl.KSModalDialogPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * KSModalDialogPanel is a modal popup window which has a dark glass effect behind it.  The user cannot click out
 * of the window to close it and must complete some action.  It is up to the the dialog content or calling class 
 * to provide a way to close the window after interaction has finished.
 * 
 * @author Kuali Student Team
 * @deprecated
 */
public class KSModalDialogPanel extends KSModalDialogPanelAbstract{ 
	private KSModalDialogPanelAbstract panel = GWT.create(KSModalDialogPanelImpl.class);
	
	public KSModalDialogPanel(){

		
	}
	
	/**
	 * Shows the modal dialog (centered) and the modal dialog glass panel.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSModalDialogPanelAbstract#show()
	 */
	@Override
	public void show(){
		panel.show();
	}
	
	/**
	 * Hides the modal dialog and the modal dialog glass panel.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSDialogPanel#hide()
	 */
	@Override
	public void hide(){
	    panel.hide();
	}
	
	/**
	 * Adds a style name to the modal dialog panel.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSDialogPanel#addStyleName(java.lang.String)
	 */
	@Override
    public void addStyleName(String style) {
        panel.addStyleName(style);
    }
	
	/**
	 * Sets the header for this modal dialog, this also allows the dialog to be movable.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSDialogPanel#setHeader(java.lang.String)
	 */
	@Override
    public void setHeader(String headerText) {
        panel.setHeader(headerText);
    }
	
	/**
	 * Sets the widget to be used in the modal dialog panel.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSDialogPanel#setWidget(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
    public void setWidget(Widget w) {
        panel.setWidget(w);
    }
	
}
