package org.kuali.student.ui.kitchensink.client.kscommons.infodialogpanel;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class InfoDialogExampleDescriptor extends KitchenSinkExample {
    public InfoDialogExampleDescriptor() {
        super();
        super.addResource("java", "InfoDialogExample.java", "kscommons/infodialogpanel/InfoDialogExample.java", "Example usage of KSInfoDialogPanel.");
        super.addResource("css", "KSInfoDialogPanel.css", "KSInfoDialogPanel.css", "Default styling of KSInfoDialogPanel.");
        super.addResource("css", "InfoDialogExample.css", "examplecss/InfoDialogExample.css", "Example styling of KSInfoDialogPanel.");
    }
    public String getDescription() {       
        return "InfoDialogPanel is a resizeable, moveable panel that can pop up over other widgets. It overlays the browser's client area (and any previously-created popups). It is not modal, i.e. user can still interact with other panels "; 
    }

    public Widget getExampleWidget() {
        return new InfoDialogExample();
    }
 
    public String getTitle() {
        return "Info Dialog Panel";
    }

}
