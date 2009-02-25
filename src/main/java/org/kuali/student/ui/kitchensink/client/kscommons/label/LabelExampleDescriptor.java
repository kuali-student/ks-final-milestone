package org.kuali.student.ui.kitchensink.client.kscommons.label;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class LabelExampleDescriptor extends KitchenSinkExample {
    public LabelExampleDescriptor() {
        super();
        super.addResource("java", "LabelExample.java", "kscommons/label/LabelExample.java", "Example usage of KSLabel.");
    }
    public String getDescription() {       
        return "Label is a widget that contains arbitrary text, not interpreted as HTML Label is used to display static text on the screen.  For long labels it is possible to wrap the text or just let it flow. "; 
    }

    public Widget getExampleWidget() {
        return new LabelExample();
    }
 
    public String getTitle() {
        return "Label";
    }

}
