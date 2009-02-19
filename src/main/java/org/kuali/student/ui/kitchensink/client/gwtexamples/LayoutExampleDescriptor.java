package org.kuali.student.ui.kitchensink.client.gwtexamples;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;


import com.google.gwt.user.client.ui.Widget;

public class LayoutExampleDescriptor extends KitchenSinkExample {
    public LayoutExampleDescriptor() {
        super(); 
        super.addResource("java", "LayoutExample.java", "gwtexamples/LayoutExample.java", "Example .");
        super.addResource("css", "CSSLayoutExample.css", "examplecss/CSSLayoutExample.css", "CSS used to style .");
        
    }
    public String getDescription() {       
        return "asdf";
    }

    public Widget getExampleWidget() {
        return new LayoutExample();
    }

    public String getTitle() {
        return "CSS layout";
    }

}
