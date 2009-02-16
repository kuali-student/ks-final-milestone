package org.kuali.student.ui.kitchensink.client.kscommons.dropdown;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class DropDownExampleDescriptor extends KitchenSinkExample {
    public DropDownExampleDescriptor() {
        super();
        super.addResource("java", "DropDownExample.java", "kscommons/dropdown/DropDownExample.java", "Example usage of Drop Down List.");
    }
    public String getDescription() {       
        return "Drop Down list allows a user to select one item from a pre-populated list. May be configured to allow multiple selections.";
    }

    public Widget getExampleWidget() {
        return new DropDownExample();
    }

    public String getTitle() {
        return "Drop Down List";
    }

}
