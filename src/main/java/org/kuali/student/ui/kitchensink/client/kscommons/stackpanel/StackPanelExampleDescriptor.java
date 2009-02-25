package org.kuali.student.ui.kitchensink.client.kscommons.stackpanel;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class StackPanelExampleDescriptor extends KitchenSinkExample {
    public StackPanelExampleDescriptor() {
        super();
        super.addResource("java", "StackPanelExample.java", "kscommons/stackpanel/StackPanelExample.java", "Example usage of KSStackPanel.");
    }
    public String getDescription() {       
        return "A panel that stacks its children vertically, displaying only one at a time, with a header for each child which the user can click to display. "; 
    }

    public Widget getExampleWidget() {
        return new StackPanelExample();
    }
 
    public String getTitle() {
        return "Stack Panel";
    }

}
