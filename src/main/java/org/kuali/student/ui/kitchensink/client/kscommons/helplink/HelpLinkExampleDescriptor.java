package org.kuali.student.ui.kitchensink.client.kscommons.helplink;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class HelpLinkExampleDescriptor extends KitchenSinkExample {
    public HelpLinkExampleDescriptor() {
        super();
        super.addResource("java", "HelpLinkExample.java", "kscommons/helplink/HelpLinkExample.java", "Example usage of KSHelpLink.");
        super.addResource("css", "KSHelpLink.css", "KSHelpLink.css", "Default styling of KSHelpLink.");
        super.addResource("css", "HelpLinkExample.css", "examplecss/HelpLinkExample.css", "Example styling of KSHelpLink.");
    }
    public String getDescription() {       
        return "This will add a customizable link for detailed help information. Currently the help text is a hard coded example but will eventaully be an RPC call  ";
    }

    public Widget getExampleWidget() {
        return new HelpLinkExample();
    }
 
    public String getTitle() {
        return "Help Link";
    }

}
