package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

public abstract class KSConfirmationDialogAbstract extends KSModalDialogPanel {

    public KSConfirmationDialogAbstract() {
        super();
    }

    public abstract void setMessageTitle(String messageTitle);

    public abstract String getMessageTitle();

    public abstract boolean isCanceled();

    //public abstract void setWidget(Widget w);

    public abstract void center();
    
    public abstract void addConfirmHandler(ClickHandler handler);

    //public abstract void show();

}