package org.kuali.student.ui.kitchensink.client.kscommons.accordionpanel;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class AccordionPanelExampleDescriptor extends KitchenSinkExample {
    public AccordionPanelExampleDescriptor() {
        super();
        super.addResource("java", "AccordionPanelExample.java", "kscommons/accordionpanel/AccordionPanelExample.java", "Example usage of KSAccordionPanel.");
        super.addResource("css", "KSAccordionPanel.css", "KSAccordionPanel.css", "Default styling of KSAccordionPanel.");
        super.addResource("css", "AccordionPanelExample.css", "examplecss/AccordionPanelExample.css", "Example styling of KSAccordionPanel.");
    }
    public String getDescription() {       
        return "AccordionPanel allows user to expand and contract content by clicking on headings.";
    }

    public Widget getExampleWidget() {
        return new AccordionPanelExample();
    }

    public String getTitle() {
        return "Accordion Panel";
    }

}
