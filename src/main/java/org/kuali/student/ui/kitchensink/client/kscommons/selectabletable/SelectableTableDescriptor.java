package org.kuali.student.ui.kitchensink.client.kscommons.selectabletable;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class SelectableTableDescriptor extends KitchenSinkExample {
    public SelectableTableDescriptor() {
        super();
        super.addResource("java", "SelectableTableExample.java", "kscommons/selectabletable/SelectableTableExample.java", "Example usage of KSSelectableTable.");
        //super.addResource("css", "KSSelectableTable.css", "KSSelectableTable.css", "Default styling of KSSelectableTable.");
        //super.addResource("css", "SelectableTableExample.css", "examplecss/SelectableTableExample.css", "Example styling of KSSelectableTable.");
    }
    public String getDescription() {       
        return "SelectableTable is a table which has items that are selectable.   It uses an implementation of the ListItems interface with a set of data to produce the list."; 
    }

    public Widget getExampleWidget() {
        return new SelectableTableExample();
    }
 
    public String getTitle() {
        return "Selectable Table";
    }


}
