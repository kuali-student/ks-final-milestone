package org.kuali.student.ui.kitchensink.client.kscommons.modalpopuppanel;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ModalPopupPanelExampleDescriptor extends KitchenSinkExample {
    public ModalPopupPanelExampleDescriptor() {
        super();
        super.addResource("java", "ModalPopupPanelExample.java", "kscommons/modalpopuppanel/ModalPopupPanelExample.java", "Example usage of KSModalPopupPanel.");
        super.addResource("css", "ModalPopupPanelExample.css", "examplecss/ModalPopupPanelExample.css", "Example styling of KSModalPopupPanel.");
    }
    public String getDescription() {       
        return "ModalPopupPanel is a panel that can pop up over other widgets. It overlays the browser's client area (and any previously-created popups) "; 
    }

    public Widget getExampleWidget() {
        return new ModalPopupPanelExample();
    }
 
    public String getTitle() {
        return "Modal Popup Panel";
    }

}
