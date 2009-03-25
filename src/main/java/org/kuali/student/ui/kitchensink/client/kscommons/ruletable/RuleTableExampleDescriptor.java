package org.kuali.student.ui.kitchensink.client.kscommons.ruletable;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class RuleTableExampleDescriptor extends KitchenSinkExample {

    public RuleTableExampleDescriptor() {
        super();
        super.addResource("java", "RuleTableExample.java", "kscommons/ruletable/RuleTableExample.java", "Example usage of Rule table.");
        //super.addResource("css", "KSToolTip.css", "KSToolTip.css", "Default styling of KSToolTip.");
    }
    public String getDescription() {       
        return "Algrbra expression editor"; 
    }

    public Widget getExampleWidget() {
        return new RuleTableExample();
    }
 
    public String getTitle() {
        return "Rule table";
    }
}
