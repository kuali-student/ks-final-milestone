package org.kuali.student.ui.kitchensink.client.kscommons.radiobutton;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class RadioButtonExampleDescriptor extends KitchenSinkExample {
    public RadioButtonExampleDescriptor() {
        super();
        super.addResource("java", "RadioButtonExample.java", "kscommons/radiobutton/RadioButtonExample.java", "Example usage of Radio Button.");
    }
    public String getDescription() {       
        return "RadioButton is used to allow users to indicate a single option"; 
    }

    public Widget getExampleWidget() {
        return new RadioButtonExample();
    }
 
    public String getTitle() {
        return "Radio Button";
    }

}
