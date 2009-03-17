package org.kuali.student.ui.kitchensink.client.kscommons.busywidgetshade;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class BusyWidgetShadeExampleDescriptor extends KitchenSinkExample {
    public BusyWidgetShadeExampleDescriptor() {
        super();
        super.addResource("java", "BusyWidgetShadeExample.java", "kscommons/busywidgetshade/BusyWidgetShadeExample.java", "Example usage of BusyWidgetShade.");
    }
    public String getDescription() {       
        return "BusyWidgetShade is used to shade out a specific widget (and any of its children) with a semitransparent image and an animated \"spinner\" indicating that work is being done.";
    }

    public Widget getExampleWidget() {
        return new BusyWidgetShadeExample();
    }

    public String getTitle() {
        return "Busy Widget Shade";
    }

}
