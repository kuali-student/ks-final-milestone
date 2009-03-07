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

public class KSConfirmationDialog extends KSConfirmationDialogAbstract{ 
	

	private final KSConfirmationDialogAbstract dialog = GWT.create(KSConfirmationDialogImpl.class);
	
	public KSConfirmationDialog(){

	}

    @Override
    public void center() {
        dialog.center();
        
    }

    @Override
    public String getMessageTitle() {
        return dialog.getMessageTitle();
    }

    @Override
    public boolean isCanceled() {
        return dialog.isCanceled();
    }

    @Override
    public void setCanceled(boolean canceled) {
        dialog.setCanceled(canceled);        
    }

    @Override
    public void setMessageTitle(String messageTitle) {
        dialog.setMessageTitle(messageTitle);        
    }

 }
