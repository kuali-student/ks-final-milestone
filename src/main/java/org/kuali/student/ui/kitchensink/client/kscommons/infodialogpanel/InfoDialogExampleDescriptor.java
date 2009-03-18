package org.kuali.student.ui.kitchensink.client.kscommons.infodialogpanel;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class InfoDialogExampleDescriptor extends KitchenSinkExample {
    public InfoDialogExampleDescriptor() {
        super();
        super.addResource("java", "InfoDialogExample.java", "kscommons/infodialogpanel/InfoDialogExample.java", "Example usage of KSInfoDialogPanel.");
        super.addResource("css", "KSInfoDialogPanel.css", "KSInfoDialogPanel.css", "Default styling of KSInfoDialogPanel.");
        super.addResource("css", "KSDialogPanel.css", "KSDialogPanel.css", "Default styling of KSDialogPanel.");
    }
    public String getDescription() {       
        return "InfoDialogPanel is a resizeable, non-modal panel that pops up over other widgets. It overlays the browser's client area (and any previously-created popups). It can be set to be moveable or not as required."; 
    }

    public Widget getExampleWidget() {
        return new InfoDialogExample();
    }
 
    public String getTitle() {
        return "Info Dialog Panel";
    }

}
