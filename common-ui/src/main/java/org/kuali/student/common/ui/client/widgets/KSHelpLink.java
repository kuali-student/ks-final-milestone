package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.images.HelpIcons;
import org.kuali.student.common.ui.client.widgets.impl.KSHelpLinkImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class KSHelpLink extends KSHelpLinkAbstract { 


    
    private KSHelpLinkAbstract helpLink = GWT.create(KSHelpLinkImpl.class);
    
    public KSHelpLink() {
		initWidget(helpLink);
	}

    public HelpInfo getHelpInfo() {
        return helpLink.getHelpInfo();
    }

    public void setHelpInfo(HelpInfo helpInfo) {
        helpLink.setHelpInfo(helpInfo);
    }

    public HelpLinkState getState() {
        return helpLink.getState();
    }
    
    public void setStateDefault() {
        helpLink.setStateDefault();
    }
	
    public void setStateOK() {
        helpLink.setStateOK();
    }
    
    public void setStateOK(String text) {
        helpLink.setStateOK(text);
    }
	
    public void setStateError() {
        helpLink.setStateError();
    }
    
    public void setStateError(String text) {
        helpLink.setStateError(text);
    }
   
}
