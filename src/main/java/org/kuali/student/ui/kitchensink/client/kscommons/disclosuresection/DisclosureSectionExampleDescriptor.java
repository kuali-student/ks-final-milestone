package org.kuali.student.ui.kitchensink.client.kscommons.disclosuresection;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class DisclosureSectionExampleDescriptor extends KitchenSinkExample {
    public DisclosureSectionExampleDescriptor() {
        super();
        super.addResource("java", "DisclosureSectionExample.java", "kscommons/disclosuresection/DisclosureSectionExample.java", "Example usage of KSDisclosureSection.");
    }
    public String getDescription() {       
        return "DisclosureSection consists of a header and a content panel that shows and hides the content when a user clicks on the header.";
    }

    public Widget getExampleWidget() {
        return new DisclosureSectionExample();
    }

    public String getTitle() {
        return "KSDisclosureSection";
    }

}
