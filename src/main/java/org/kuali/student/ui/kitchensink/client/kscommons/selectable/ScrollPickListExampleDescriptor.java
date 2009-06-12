package org.kuali.student.ui.kitchensink.client.kscommons.selectable;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ScrollPickListExampleDescriptor extends KitchenSinkExample {
    public ScrollPickListExampleDescriptor() {
        super();
        super.addResource("java", "ScrollPickListExample.java", "kscommons/selectable/ScrollPickListExample.java", "Example usage of ScrollTable as a picklist.");
        super.addResource("css", "KSScrollTable.css", "KSScrollTable.css", "Default styling of KSScrollPicklist.");
        super.addCssResource(KSCommonResources.INSTANCE.scrollTableCss()); 

    }
    public String getDescription() {       
        return "Scroll table picklist"; 
    }

    public Widget getExampleWidget() {
        return new ScrollPickListExample();
    }
 
    public String getTitle() {
        return "Scroll Table Picklist";
    }
}