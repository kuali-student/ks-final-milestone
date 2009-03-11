package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.images.KSImages;
import org.kuali.student.common.ui.client.widgets.impl.KSConfirmationDialogImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A confirmation dialog with cancel and confirm buttons.  The confirmation information to be displayed is
 * set by using setWidget.
 * 
 * @author Kuali Student Team
 *
 */
public class KSConfirmationDialog extends KSConfirmationDialogAbstract{ 
	

	private final KSConfirmationDialogAbstract dialog = GWT.create(KSConfirmationDialogImpl.class);

    /**
     * Centers the dialog
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSConfirmationDialogAbstract#center()
     */
	@Override
    public void center() {
        dialog.center();
        
    }

    /**
     * Gets the title of this confirmation dialog.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSConfirmationDialogAbstract#getMessageTitle()
     */
    @Override
    public String getMessageTitle() {
        return dialog.getMessageTitle();
    }

    /**
     * Returns true if this dialog has been canceled, otherwise false.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSConfirmationDialogAbstract#isCanceled()
     */
    @Override
    public boolean isCanceled() {
        return dialog.isCanceled();
    }


    /**
     * Sets the title of this confirmation dialog to the String passed in.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSConfirmationDialogAbstract#setMessageTitle(java.lang.String)
     */
    @Override
    public void setMessageTitle(String messageTitle) {
        dialog.setMessageTitle(messageTitle);        
    }
    
    /**
     * Sets the content of this confirmation dialog.  Content passed in *should* display information or a
     * question that can be cancelled or confirmed.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSModalDialogPanel#setWidget(com.google.gwt.user.client.ui.Widget)
     */
    public void setWidget(Widget w){
    	dialog.setWidget(w);
    }
    
    /**
     * Shows the confirmation dialog and resets the cancelled flag to false.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSModalDialogPanel#show()
     */
    public void show(){
    	dialog.show();
    }

    /**
     * Adds a handler to the confirmation button.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSConfirmationDialogAbstract#addConfirmHandler(com.google.gwt.event.dom.client.ClickHandler)
     */
    @Override
    public void addConfirmHandler(ClickHandler handler) {
        dialog.addConfirmHandler(handler);
        
    }

 }
