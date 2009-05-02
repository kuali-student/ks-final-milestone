package org.kuali.student.ui.kitchensink.client.kscommons.selectable;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class PageSelectableExampleDescriptor extends KitchenSinkExample {
    public PageSelectableExampleDescriptor() {
        super();
        super.addResource("java", "PageSelectableExample.java", "kscommons/selectable/PageSelectableExample.java", "Example usage of KSPageTable.");
        super.addResource("css", "KSScrollTable.css", "KSScrollTable.css", "Default styling of KSPageSelectable.");
        super.addCssResource(KSCommonResources.INSTANCE.scrollTableCss()); 
    }
    public String getDescription() {       
        return "Paging table showing multi row selections"; 
    }

    public Widget getExampleWidget() {
        return new PageSelectableExample();
    }
 
    public String getTitle() {
        return "Paging Table Selections";
    }
}