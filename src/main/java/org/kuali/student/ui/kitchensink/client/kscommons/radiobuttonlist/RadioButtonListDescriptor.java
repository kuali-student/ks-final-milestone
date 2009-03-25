package org.kuali.student.ui.kitchensink.client.kscommons.radiobuttonlist;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class RadioButtonListDescriptor extends KitchenSinkExample {
    public RadioButtonListDescriptor() {
        super();
        super.addResource("java", "RadioButtonListExample.java", "kscommons/radiobuttonlist/RadioButtonListExample.java", "Example usage of KSRadioButtonList.");
        //super.addResource("css", "KSRadioButtonList.css", "KSRadioButtonList.css", "Default styling of KSRadioButtonList.");
        //super.addResource("css", "RadioButtonListExample.css", "examplecss/RadioButtonListExample.css", "Example styling of KSRadioButtonList.");
    }
    public String getDescription() {       
        return "Radio Button List is a panel which contains radio buttons which belong to the same group.  It uses an implementation of the ListItems interface with a set of data to produce the list."; 
    }

    public Widget getExampleWidget() {
        return new RadioButtonListExample();
    }
 
    public String getTitle() {
        return "Radio Button List";
    }

}
