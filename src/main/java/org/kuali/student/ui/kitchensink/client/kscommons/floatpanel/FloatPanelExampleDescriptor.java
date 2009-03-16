package org.kuali.student.ui.kitchensink.client.kscommons.floatpanel;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class FloatPanelExampleDescriptor extends KitchenSinkExample {
    public FloatPanelExampleDescriptor() {
        super();
        super.addResource("java", "FloatPanelExample.java", "kscommons/floatpanel/FloatPanelExample.java", "Example usage of KSFloatPanel.");
        super.addResource("css", "KSFloatPanel.css", "KSFloatPanel.css", "Default styling of KSFloatPanel.");
    }
    public String getDescription() {       
        return "FloatPanel is a panel that can pop up over other widgets. It overlays the browser's client area (and any previously-created popups). It is not modal, i.e. user can still interact with other panels "; 
    }

    public Widget getExampleWidget() {
        return new FloatPanelExample();
    }
 
    public String getTitle() {
        return "Float Panel";
    }

}
