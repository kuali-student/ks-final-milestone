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

import org.kuali.student.common.ui.client.widgets.impl.KSConfirmationDialogImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * A confirmation dialog with cancel and confirm buttons.  The confirmation information to be displayed is
 * set by using setWidget.
 * 
 * @author Kuali Student Team
 * @deprecated
 */
public class KSConfirmationDialog extends KSConfirmationDialogAbstract{ 
	

	private final KSConfirmationDialogAbstract dialog = GWT.create(KSConfirmationDialogImpl.class);

    /**
     * Centers the dialog.
     * 
     */
	@Override
    public void center() {
        dialog.center();
        
    }

    /**
     * Gets the title of this confirmation dialog.
     * 
     * @return the title of the confirmation dialog
     */
    @Override
    public String getMessageTitle() {
        return dialog.getMessageTitle();
    }

    /**
     * Returns true if this dialog has been canceled, false otherwise.
     * 
     * @return true if this dialog has been canceled, false otherwise.
     */
    @Override
    public boolean isCanceled() {
        return dialog.isCanceled();
    }


    /**
     * Sets the title of this confirmation dialog to the String passed in.
     * 
     * @param messageTitle the title of this confirmation dialog (shown above the content)
     */
    @Override
    public void setMessageTitle(String messageTitle) {
        dialog.setMessageTitle(messageTitle);        
    }
    
    /**
     * Sets the content of this confirmation dialog.  Content passed in *should* display information or a
     * question that can be cancelled or confirmed.
     * 
     * @param w the Widget to be used in the panel content
     */
    public void setWidget(Widget w){
    	dialog.setWidget(w);
    }
    
    /**
     * Shows the confirmation dialog and resets the cancelled flag to false.
     * 
     */
    public void show(){
    	dialog.show();
    }

    /**
     * Adds a handler to the confirmation button.
     * 
     * @param handler the ClickHandler used to handle click events on the confirm button
     */
    @Override
    public void addConfirmHandler(ClickHandler handler) {
        dialog.addConfirmHandler(handler);
        
    }

 }
