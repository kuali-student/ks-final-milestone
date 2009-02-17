package org.kuali.student.ui.kitchensink.client.kscommons.listbox;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ListBoxExampleDescriptor extends KitchenSinkExample {
    public ListBoxExampleDescriptor() {
        super();
        super.addResource("java", "ListBoxExample.java", "kscommons/listbox/ListBoxExample.java", "Example usage of List Box.");
    }
    public String getDescription() {       
        return "Drop Down list allows a user to select one item from a pre-populated list. May be configured to allow multiple selections.";
    }

    public Widget getExampleWidget() {
        return new ListBoxExample();
    }

    public String getTitle() {
        return "List Box";
    }

}
