package org.kuali.student.ui.kitchensink.client.kscommons.listbox;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ListBoxExampleDescriptor extends KitchenSinkExample {
    public ListBoxExampleDescriptor() {
        super();
        super.addResource("java", "ListBoxExample.java", "kscommons/listbox/ListBoxExample.java", "Example usage of KSListBox.");
        super.addResource("css", "KSListBox.css", "KSListBox.css", "Default styling of KSListBox.");
    }
    public String getDescription() {       
        return "A widget that presents a list of choices to the user as a list box.";
    }

    public Widget getExampleWidget() {
        return new ListBoxExample();
    }

    public String getTitle() {
        return "List Box";
    }

}
