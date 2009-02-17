package org.kuali.student.ui.kitchensink.client.kscommons.infopopuppanel;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class InfoPopupExampleDescriptor extends KitchenSinkExample {
    public InfoPopupExampleDescriptor() {
        super();
        super.addResource("java", "InfoPopupExample.java", "kscommons/infopopuppanel/InfoPopupExample.java", "Example usage of InfoPopupPanel.");
    }
    public String getDescription() {       
        return "Info Popup Panel. "; 
    }

    public Widget getExampleWidget() {
        return new InfoPopupExample();
    }
 
    public String getTitle() {
        return "Info Popup Panel";
    }

}
