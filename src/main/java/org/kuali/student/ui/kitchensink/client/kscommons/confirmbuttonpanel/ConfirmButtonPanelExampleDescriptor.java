package org.kuali.student.ui.kitchensink.client.kscommons.confirmbuttonpanel;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ConfirmButtonPanelExampleDescriptor extends KitchenSinkExample {
    public ConfirmButtonPanelExampleDescriptor() {
        super();
        super.addResource("java", "ConfirmButtonPanelExample.java", "kscommons/confirmbuttonpanel/ConfirmButtonPanelExample.java", "Example usage of KSConfirmButtonPanel.");
        super.addResource("css", "KSConfirmButtonPanel.css", "KSConfirmButtonPanel.css", "Default styling of KSConfirmButtonPanel.");
        super.addCssResource(KSCommonResources.INSTANCE.confirmButtonPanelCss());
    }
    public String getDescription() {       
        return "ConfirmButtonPanel is a panel with a cancel and a confirm button, providing a standard combination of buttons. "; 
    }

    public Widget getExampleWidget() {
        return new ConfirmButtonPanelExample();
    }
 
    public String getTitle() {
        return "Confirm Button Panel";
    }

}
