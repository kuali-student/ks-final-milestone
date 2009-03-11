package org.kuali.student.ui.kitchensink.client.kscommons.button;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ButtonExampleDescriptor extends KitchenSinkExample {
    public ButtonExampleDescriptor() {
        super();
        super.addResource("java", "ButtonExample.java", "kscommons/button/ButtonExample.java", "Example usage of KSButton.");
        super.addResource("css", "KSButton.css", "KSButton.css", "Default styling of KSButton.");
        super.addResource("css", "ButtonExample.css", "examplecss/ButtonExample.css", "Example styling of KSButton.");
    }
    public String getDescription() {       
        return "Button is used to initiate some action, e.g. submit a form, start a search, etc.";
    }

    public Widget getExampleWidget() {
        return new ButtonExample();
    }

    public String getTitle() {
        return "Button";
    }

}
