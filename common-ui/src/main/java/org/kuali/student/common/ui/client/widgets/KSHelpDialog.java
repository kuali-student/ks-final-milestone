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

/**
 * A dialog which is used to display help information.
 * 
 * @author Kuali Student Team
 *
 */
public class KSHelpDialog extends KSHelpDialogAbstract{  
	
	private final KSHelpDialogAbstract dialog = GWT.create(KSHelpDialogImpl.class); 

	
	/**
	 * Creates a help dialog.
	 * 
	 */
	public KSHelpDialog() {}
	
	/**
	 * Creates a help dialog that displays the help information passed in.
	 * 
	 * @param helpInfo
	 */
	public KSHelpDialog(HelpInfo helpInfo) {
	    dialog.init(helpInfo);
	}

    /**
     * Initializes the help dialog with the help information passed in.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSHelpDialogAbstract#init(org.kuali.student.common.ui.client.dto.HelpInfo)
     */
    @Override
    protected void init(HelpInfo helpInfo) {
        dialog.init(helpInfo);
        
    }
		
}
