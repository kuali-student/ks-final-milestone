package org.kuali.student.ui.kitchensink.client.kscommons.dialogpanel;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class DialogPanelExampleDescriptor extends KitchenSinkExample {
    public DialogPanelExampleDescriptor() {
        super();
        super.addResource("java", "DialogPanelExample.java", "kscommons/dialogpanel/DialogPanelExample.java", "Example usage of KSDialogPanel.");
        super.addResource("css", "KSDialogPanel.css", "KSDialogPanel.css", "Default styling of KSDialogPanel.");
        super.addResource("css", "KSResizablePanel.css", "KSResizablePanel.css", "Default styling of KSResizablePanel.");
        super.addCssResource(KSCommonResources.INSTANCE.dialogPanelCss());
        super.addCssResource(KSCommonResources.INSTANCE.resizablePanelCss());
    }
    public String getDescription() {       
        return "DialogPanel is a non-modal panel that pops up over other widgets. May be set to be moveable or resizeable as required. It overlays the browser's client area (and any previously-created popups)."; 
    }

    public Widget getExampleWidget() {
        return new DialogPanelExample();
    }
 
    public String getTitle() {
        return "Dialog Panel";
    }

}
