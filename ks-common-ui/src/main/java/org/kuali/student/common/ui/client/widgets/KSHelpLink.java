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

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.impl.KSHelpLinkImpl;

import com.google.gwt.core.client.GWT;

/**
 * The KSHelpLink widget is intended to display help information next to some input field.  When the input entered correctly, it can be
 * switched to an OK state and display an appropriate message.  When the input is in error, it can be switched to an error state.  Clicking
 * on the help link in its default state will open a window with additional help information.
 * 
 * @author Kuali Student Team
 *
 */
public class KSHelpLink extends KSHelpLinkAbstract { 


    
    private KSHelpLinkAbstract helpLink = GWT.create(KSHelpLinkImpl.class);
    
    /**
     * Creates a new help link widget.
     * 
     */
    public KSHelpLink() {
		initWidget(helpLink);
	}

    /**
     * Get the HelpInfo data object used for this help link.
     * 
     * @return the HelpInfo data object used in this help link.
     */
    public HelpInfo getHelpInfo() {
        return helpLink.getHelpInfo();
    }

    /**
     * Set the HelpInfo data object to be used for this help link.
     *
     * @param helpInfo the HelpInfo data to be used in this help link
     */
    public void setHelpInfo(HelpInfo helpInfo) {
        helpLink.setHelpInfo(helpInfo);
    }

    /**
     * Get the state of the help link (Default, OK, or Error) 
     * 
     * @return state of this help link widget (Default, OK, or Error) 
     */
    public HelpLinkState getState() {
        return helpLink.getState();
    }
    
    /**
     * Sets the state of the help link to the default state/text/picture.
     * 
     */
    public void setStateDefault() {
        helpLink.setStateDefault();
    }
	
    /**
     * Sets the state of the help link to the OK state/text/picture.
     * 
     */
    public void setStateOK() {
        helpLink.setStateOK();
    }
    
    /**
     * Sets the state of the help link to the OK state/picture and uses the text passed in.
     * 
     * @param text the text to be used for the OK state
     */
    public void setStateOK(String text) {
        helpLink.setStateOK(text);
    }
	
    /**
     * Sets the state of the help link to the Error state/text/picture.
     * 
     */
    public void setStateError() {
        helpLink.setStateError();
    }
    
    /**
     * Sets the state of the help link to the Error state/picture and uses the text passed in.
     * 
     * @param text the text to be used for the Error state
     */
    public void setStateError(String text) {
        helpLink.setStateError(text);
    }
   
}
