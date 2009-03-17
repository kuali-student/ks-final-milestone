package org.kuali.student.ui.kitchensink.client.kscommons.picklist;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class PickListDescriptor extends KitchenSinkExample {
    public PickListDescriptor() {
        super();
        super.addResource("java", "PickListExample.java", "kscommons/picklist/PickListExample.java", "Example usage of KSPickList.");
        //super.addResource("css", "KSPickList.css", "KSPickList.css", "Default styling of KSPickList.");
        //super.addResource("css", "PickListExample.css", "examplecss/PickListExample.css", "Example styling of KSPickList.");
    }
    public String getDescription() {       
        return "PickList is a list with 2 \"buckets\" with buttons inbetween to transfer between these buckets.  It uses an implementation of the ListItems interface with a set of data to produce the list."; 
    }

    public Widget getExampleWidget() {
        return new PickListExample();
    }
 
    public String getTitle() {
        return "Pick List";
    }


}
