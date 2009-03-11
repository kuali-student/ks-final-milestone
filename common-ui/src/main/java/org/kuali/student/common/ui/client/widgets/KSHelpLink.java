package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.impl.KSHelpLinkImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
     * @see org.kuali.student.common.ui.client.widgets.KSHelpLinkAbstract#getHelpInfo()
     */
    public HelpInfo getHelpInfo() {
        return helpLink.getHelpInfo();
    }

    /**
     * Set the HelpInfo data object to be used for this help link.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSHelpLinkAbstract#setHelpInfo(org.kuali.student.common.ui.client.dto.HelpInfo)
     */
    public void setHelpInfo(HelpInfo helpInfo) {
        helpLink.setHelpInfo(helpInfo);
    }

    /**
     * Get the state of the help link (Default, OK, or Error) 
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSHelpLinkAbstract#getState()
     */
    public HelpLinkState getState() {
        return helpLink.getState();
    }
    
    /**
     * Sets the state of the help link to the default state/text/picture.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSHelpLinkAbstract#setStateDefault()
     */
    public void setStateDefault() {
        helpLink.setStateDefault();
    }
	
    /**
     * Sets the state of the help link to the OK state/text/picture.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSHelpLinkAbstract#setStateOK()
     */
    public void setStateOK() {
        helpLink.setStateOK();
    }
    
    /**
     * Sets the state of the help link to the OK state/picture and uses the text passed in.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSHelpLinkAbstract#setStateOK(java.lang.String)
     */
    public void setStateOK(String text) {
        helpLink.setStateOK(text);
    }
	
    /**
     * Sets the state of the help link to the Error state/text/picture.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSHelpLinkAbstract#setStateError()
     */
    public void setStateError() {
        helpLink.setStateError();
    }
    
    /**
     * Sets the state of the help link to the Error state/picture and uses the text passed in.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSHelpLinkAbstract#setStateError(java.lang.String)
     */
    public void setStateError(String text) {
        helpLink.setStateError(text);
    }
   
}
