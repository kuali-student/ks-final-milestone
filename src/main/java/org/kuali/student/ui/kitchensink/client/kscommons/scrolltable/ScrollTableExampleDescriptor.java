package org.kuali.student.ui.kitchensink.client.kscommons.scrolltable;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ScrollTableExampleDescriptor extends KitchenSinkExample {
    public ScrollTableExampleDescriptor() {
        super();
        super.addResource("java", "ScrollTableExample.java", "kscommons/scrolltable/ScrollTableExample.java", "Example usage of KSScrollTable.");
        super.addResource("css", "KSScrollTable.css", "KSScrollTable.css", "Default styling of KSScrollTable.");
    }
    public String getDescription() {       
        return "ScrollTable"; 
    }

    public Widget getExampleWidget() {
        return new ScrollTableExample();
    }
 
    public String getTitle() {
        return "ScrollTable";
    }
}