package org.kuali.student.ui.kitchensink.client.kscommons.selectable;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ScrollSelectableExampleDescriptor extends KitchenSinkExample {
    public ScrollSelectableExampleDescriptor() {
        super();
        super.addResource("java", "ScrollSelectableExample.java", "kscommons/selectable/ScrollSelectableExample.java", "Example usage of ScrollTable with row selections.");
        super.addResource("css", "KSScrollTable.css", "KSScrollTable.css", "Default styling of KSPageSelectable.");
        super.addCssResource(KSCommonResources.INSTANCE.scrollTableCss()); 
    }
    public String getDescription() {       
        return "Scroll table showing multi row selections"; 
    }

    public Widget getExampleWidget() {
        return new ScrollSelectableExample();
    }
 
    public String getTitle() {
        return "Scroll Table Selections";
    }
}