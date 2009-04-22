package org.kuali.student.ui.kitchensink.client.kscommons.formlayoutpanel;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class FormLayoutPanelExampleDescriptor extends KitchenSinkExample {
    public FormLayoutPanelExampleDescriptor() {
        super();
        super.addResource("java", "FormLayoutPanelExample.java", "kscommons/formlayoutpanel/FormLayoutPanelExample.java", "Example usage of KSFormLayoutPanel.");
        super.addResource("xml", "gwt-form-messages.xml", "kscommons/formlayoutpanel/gwt-form-messages.xml", "I18N message configuration for form");
        super.addResource("css", "KSFormLayout.css", "KSFormLayout.css", "Default styling of KSFormLayoutPanel.");
        super.addCssResource(KSCommonResources.INSTANCE.formLayoutCss());
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
