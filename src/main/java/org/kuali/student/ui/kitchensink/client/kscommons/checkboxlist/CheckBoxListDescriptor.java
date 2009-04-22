package org.kuali.student.ui.kitchensink.client.kscommons.checkboxlist;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class CheckBoxListDescriptor extends KitchenSinkExample {
    public CheckBoxListDescriptor() {
        super();
        super.addResource("java", "CheckboxListExample.java", "kscommons/checkboxlist/CheckboxListExample.java", "Example usage of KSCheckboxList.");
        super.addResource("css", "KSCheckbox.css", "KSCheckbox.css", "Default styling of KSCheckBox.");
        super.addCssResource(KSCommonResources.INSTANCE.checkboxCss());
    }
    public String getDescription() {       
        return "Checkbox List is a panel which contains checkboxes which belong to the same group.  It uses an implementation of the ListItems interface with a set of data to produce the list."; 
    }

    public Widget getExampleWidget() {
        return new CheckBoxListExample();
    }
 
    public String getTitle() {
        return "Checkbox List";
    }

}
