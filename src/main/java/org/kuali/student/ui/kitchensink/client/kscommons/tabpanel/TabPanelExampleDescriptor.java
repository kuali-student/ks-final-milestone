package org.kuali.student.ui.kitchensink.client.kscommons.tabpanel;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class TabPanelExampleDescriptor extends KitchenSinkExample { 
    public TabPanelExampleDescriptor() {
        super();
        super.addResource("java", "TabPanelExample.java", "kscommons/accordionpanel/TabPanelExample.java", "Example usage of KSTabPanel.");
        super.addResource("css", "KSTabPanel.css", "KSTabPanel.css", "Default styling of KSTabPanel.");
    }
    public String getDescription() {       
        return "TabPanel allows user to show and hide tabbed content.";
    }

    public Widget getExampleWidget() {
        return new TabPanelExample();
    }

    public String getTitle() {
        return "Tab Panel";
    }

}
