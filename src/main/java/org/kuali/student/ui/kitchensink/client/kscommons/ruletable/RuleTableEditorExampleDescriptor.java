package org.kuali.student.ui.kitchensink.client.kscommons.ruletable;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class RuleTableEditorExampleDescriptor extends KitchenSinkExample {

    public RuleTableEditorExampleDescriptor() {
        super();
        super.addResource("java", "RuleTableEditorExample.java", "kscommons/ruletable/RuleTableEditorExample.java", "Example usage of Rule table editor.");
        //super.addResource("css", "KSToolTip.css", "KSToolTip.css", "Default styling of KSToolTip.");
    }
    public String getDescription() {       
        return "Rule table editor"; 
    }

    public Widget getExampleWidget() {
        return new RuleTableEditorExample();
    }
 
    public String getTitle() {
        return "Rule table editor";
    }
}
