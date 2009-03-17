package org.kuali.student.ui.kitchensink.client.kscommons.sidebar;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class SidebarExampleDescriptor extends KitchenSinkExample {
    public SidebarExampleDescriptor() {
        super();
        super.addResource("java", "SidebarExample.java", "kscommons/sidebar/SidebarExample.java", "Example usage of KSSidebar.");
        super.addResource("css", "KSSidebar.css", "KSSidebar.css", "Default styling of KSSidebar.");
        super.addResource("css", "SidebarExample.css", "examplecss/SidebarExample.css", "Example styling of KSSidebar.");
    }
    public String getDescription() {       
        return "Sidebar is a panel that can appear on either the right or left hand side.  Clicking on one of its tabs will pop the panel out and show specific content.  Clicking on the same tab again will close the sidebar."; 
    }

    public Widget getExampleWidget() {
        return new SidebarExample();
    }
 
    public String getTitle() {
        return "Sidebar";
    }

}
