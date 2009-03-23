package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.impl.KSHelpDialogImpl;

import com.google.gwt.core.client.GWT;

/**
 * A dialog which is used to display help information.
 * 
 * @author Kuali Student Team
 * 
 * @deprecated
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
	 * @param helpInfo the HelpInfo data to initialize this help dialog
	 */
	public KSHelpDialog(HelpInfo helpInfo) {
	    dialog.init(helpInfo);
	}

    /**
     * Initializes the help dialog with the help information passed in.
     * 
     * @param helpInfo the info data to initialize this help dialog
     */
    @Override
    protected void init(HelpInfo helpInfo) {
        dialog.init(helpInfo);
        
    }
		
}
