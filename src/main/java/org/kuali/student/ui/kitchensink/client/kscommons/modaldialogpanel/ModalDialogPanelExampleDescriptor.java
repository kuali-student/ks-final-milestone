package org.kuali.student.ui.kitchensink.client.kscommons.modaldialogpanel;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ModalDialogPanelExampleDescriptor extends KitchenSinkExample {
    public ModalDialogPanelExampleDescriptor() {
        super();
        super.addResource("java", "ModalDialogPanelExample.java", "kscommons/modalpopuppanel/ModalDialogPanelExample.java", "Example usage of KSModalDialogPanel.");
        super.addResource("css", "ModalDialogPanelExample.css", "examplecss/ModalDialogPanelExample.css", "Example styling of KSModalDialogPanel.");
    }
    public String getDescription() {       
        return "ModalDialogPanel is a moveable panel that can pop up over other widgets. It overlays the browser's client area (and any previously-created popups) "; 
    }

    public Widget getExampleWidget() {
        return new ModalDialogPanelExample();
    }
 
    public String getTitle() {
        return "Modal Dialog Panel";
    }

}
