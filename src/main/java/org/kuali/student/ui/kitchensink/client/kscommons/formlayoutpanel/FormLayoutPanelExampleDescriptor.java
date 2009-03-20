package org.kuali.student.ui.kitchensink.client.kscommons.formlayoutpanel;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class FormLayoutPanelExampleDescriptor extends KitchenSinkExample {
    public FormLayoutPanelExampleDescriptor() {
        super();
        super.addResource("java", "FormLayoutPanelExample.java", "kscommons/form/FormLayoutExample.java", "Example usage of KSFormLayoutPanel.");
        super.addResource("css", "KSFormLayout.css", "KSFormLayout.css", "Default styling of KSFormLayoutPanel.");
    }
    public String getDescription() {       
        return "This allows you to add input field widgets in a three column form layout.";
    }

    public Widget getExampleWidget() {
        return new FormLayoutPanelExample();
    }
 
    public String getTitle() {
        return "Form Layout Panel";
    }

}
