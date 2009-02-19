package org.kuali.student.ui.kitchensink.client.kscommons.infopopuppanel;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class InfoPopupExampleDescriptor extends KitchenSinkExample {
    public InfoPopupExampleDescriptor() {
        super();
        super.addResource("java", "InfoPopupExample.java", "kscommons/infopopuppanel/InfoPopupExample.java", "Example usage of KSInfoPopupPanel.");
    }
    public String getDescription() {       
        return "InfoPopupPanel is a panel that can pop up over other widgets. It overlays the browser's client area (and any previously-created popups). It is not modal, i.e. user can still interact with other panels "; 
    }

    public Widget getExampleWidget() {
        return new InfoPopupExample();
    }
 
    public String getTitle() {
        return "KSInfoPopupPanel";
    }

}
