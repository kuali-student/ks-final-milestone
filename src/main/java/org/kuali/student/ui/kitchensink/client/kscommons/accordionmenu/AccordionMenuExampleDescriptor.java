package org.kuali.student.ui.kitchensink.client.kscommons.accordionmenu;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class AccordionMenuExampleDescriptor extends KitchenSinkExample {
    public AccordionMenuExampleDescriptor() {
        super();
        super.addResource("java", "AccordionMenuExample.java", "kscommons/accordionmenu/AccordionMenuExample.java", "Example usage of KSAccordionMenu.");
    }
    public String getDescription() {       
        return "AccordionMenu allows users to expand and contract menus by clicking on headers. See the Kitchen Sink menu to the left for another example ";
    }

    public Widget getExampleWidget() {
        return new AccordionMenuExample();
    }

    public String getTitle() {
        return "KSAccordionMenu";
    }

}
