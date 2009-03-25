package org.kuali.student.ui.kitchensink.client.kscommons.resizablepanel;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ResizablePanelExampleDescriptor extends KitchenSinkExample {
    public ResizablePanelExampleDescriptor() {
        super();
        super.addResource("java", "ResizablePanelExample.java", "kscommons/resizablepanel/ResizablePanelExample.java", "Example usage of KSResizablePanel.");
        super.addResource("css", "KSResizablePanel.css", "KSResizablePanel.css", "Default styling of KSResizablePanel.");
    }
    public String getDescription() {       
        return "ResizablePanel is a panel that can pop up over other widgets. A handle allows the user to resize the widget that is contained in the resizable panel."; 
    }

    public Widget getExampleWidget() {
        return new ResizablePanelExample();
    }
 
    public String getTitle() {
        return "Resizable Panel";
    }

}
