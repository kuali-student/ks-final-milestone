package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.impl.KSHelpDialogImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class KSHelpDialog extends KSHelpDialogAbstract{  
	
	private final KSHelpDialogAbstract dialog = GWT.create(KSHelpDialogImpl.class); 

	
	public KSHelpDialog() {}
	
	public KSHelpDialog(HelpInfo helpInfo) {
	    dialog.init(helpInfo);
	}

    @Override
    protected void init(HelpInfo helpInfo) {
        dialog.init(helpInfo);
        
    }
		
}
