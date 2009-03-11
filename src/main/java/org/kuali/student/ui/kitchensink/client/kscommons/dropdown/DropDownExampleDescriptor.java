package org.kuali.student.ui.kitchensink.client.kscommons.dropdown;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class DropDownExampleDescriptor extends KitchenSinkExample {
    public DropDownExampleDescriptor() {
        super();
        super.addResource("java", "DropDownExample.java", "kscommons/dropdown/DropDownExample.java", "Example usage of KSDropDown.");
        super.addResource("css", "KSDropDown.css", "KSDropDown.css", "Default styling of KSDropDown.");
        super.addResource("css", "DropDownExample.css", "examplecss/DropDownExample.css", "Example styling of KSDropDown.");
    }
    public String getDescription() {       
        return "* DropDown is a widget that presents a list of choices to the user, either as a list box or as a drop-down list.";
    }

    public Widget getExampleWidget() {
        return new DropDownExample();
    }

    public String getTitle() {
        return "Drop Down";
    }

}
