package org.kuali.student.ui.kitchensink.client.kscommons.basicmenu;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class BasicMenuExampleDescriptor extends KitchenSinkExample {
    public BasicMenuExampleDescriptor() {
        super();
        super.addResource("java", "BasicMenuExample.java", "kscommons/basicmenu/BasicMenuExample.java", "Example usage of KSBasicMenu.");
        super.addResource("css", "KSBasicMenu.css", "KSBasicMenu.css", "Default styling of KSBasicMenu.");    
        super.addCssResource(KSCommonResources.INSTANCE.basicMenuCss());
    }
    public String getDescription() {       
        return "BasicMenu provides a basic navigation menu.";
    }

    public Widget getExampleWidget() {
        return new BasicMenuExample();
    }

    public String getTitle() {
        return "Basic Menu";
    }

}
