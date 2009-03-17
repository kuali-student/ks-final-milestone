package org.kuali.student.ui.kitchensink.client.kscommons.confirmationdialog;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ConfirmationDialogExampleDescriptor extends KitchenSinkExample {
    public ConfirmationDialogExampleDescriptor() {
        super();
        super.addResource("java", "ConfirmatiionDialogExample.java", "kscommons/confirmationdialog/ConfirmationDialogExample.java", "Example usage of KSConfirmationDialogPanel.");
        super.addResource("css", "KSConfirmationDialog.css", "KSConfirmationDialog.css", "Default styling of KSConfirmationDialogPanel.");
        super.addResource("css", "KSModalDialogPanel.css", "KSModalDialogPanel.css", "Default styling of KSModalDialogPanel.");
        super.addResource("css", "KSDialogPanel.css", "KSDialogPanel.css", "Default styling of KSDialogPanel.");
    }
    public String getDescription() {       
        return "ConfirmationDialog is a panel that can pop up over other widgets and asks the user to Confirm or Cancel a spcific action. It overlays the browser's client area (and any previously-created popups). It is modal, i.e. user cannot interact with other panels "; 
    }

    public Widget getExampleWidget() {
        return new ConfirmationDialogExample();
    }
 
    public String getTitle() {
        return "Confirmation Dialog";
    }

}
