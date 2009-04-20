package org.kuali.student.ui.kitchensink.client.kscommons.checkbox;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class CheckBoxExampleDescriptor extends KitchenSinkExample {
    public CheckBoxExampleDescriptor() {
        super();
        super.addResource("java", "CheckBoxExample.java", "kscommons/checkbox/CheckBoxExample.java", "Example usage of KSCheckBox.");
        super.addResource("css", "KSCheckbox.css", "KSCheckbox.css", "Default styling of KSCheckBox.");
        super.addCssResource(KSCommonResources.INSTANCE.checkboxCss());
    }
    public String getDescription() {       
        return "CheckBox is used to select one or more options or items.";
    }

    public Widget getExampleWidget() {
        return new CheckBoxExample();
    }

    public String getTitle() {
        return "Check Box";
    }

}
