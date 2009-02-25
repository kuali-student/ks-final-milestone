package org.kuali.student.ui.kitchensink.client.kscommons.help;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class HelpExampleDescriptor extends KitchenSinkExample {
    public HelpExampleDescriptor() {
        super();
        super.addResource("java", "HelpExample.java", "kscommons/help/HelpExample.java", "Example usage of KSHelp.");
    }
    public String getDescription() {       
        return "This will add a customizable link for detailed help information. Currently the help text is a hard coded example but will eventaully be an RPC call  ";
    }

    public Widget getExampleWidget() {
        return new HelpExample();
    }
 
    public String getTitle() {
        return "Help";
    }

}
