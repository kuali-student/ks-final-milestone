package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.dto.HelpInfo;

import com.google.gwt.user.client.ui.Composite;


public abstract class KSHelpLinkAbstract extends Composite { 

    public enum HelpLinkState {
        DEFAULT,
        OK,
        ERROR
    }


    public abstract HelpInfo getHelpInfo();

    public abstract void setHelpInfo(HelpInfo helpInfo);

    public abstract HelpLinkState getState();
    
    public abstract void setStateDefault();
    public abstract void setStateOK();
    public abstract void setStateOK(String text);

	
    public abstract void setStateError();
    public abstract void setStateError(String text); 
   
}
