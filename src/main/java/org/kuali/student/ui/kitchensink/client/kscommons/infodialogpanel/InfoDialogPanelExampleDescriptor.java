package org.kuali.student.ui.kitchensink.client.kscommons.infodialogpanel;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class InfoDialogPanelExampleDescriptor extends KitchenSinkExample {
    public InfoDialogPanelExampleDescriptor() {
        super();
        super.addResource("java", "InfoDialogPanelExample.java", "kscommons/infodialogpanel/InfoDialogPanelExample.java", "Example usage of KSInfoDialogPanel.");
        super.addResource("css", "KSInfoDialogPanel.css", "KSInfoDialogPanel.css", "Default styling of KSInfoDialogPanel.");
        super.addResource("css", "KSResizablePanel.css", "KSResizablePanel.css", "Default styling of KSResizablePanel.");
        super.addResource("css", "KSDialogPanel.css", "KSDialogPanel.css", "Default styling of KSDialogPanel.");
    }
    public String getDescription() {       
        return "InfoDialogPanel is a resizeable, non-modal panel that pops up over other widgets. It overlays the browser's client area (and any previously-created popups). It can be set to be moveable or not as required."; 
    }

    public Widget getExampleWidget() {
        return new InfoDialogPanelExample();
    }
 
    public String getTitle() {
        return "Info Dialog Panel";
    }

}
