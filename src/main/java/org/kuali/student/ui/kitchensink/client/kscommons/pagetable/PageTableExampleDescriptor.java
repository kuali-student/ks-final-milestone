package org.kuali.student.ui.kitchensink.client.kscommons.pagetable;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class PageTableExampleDescriptor extends KitchenSinkExample {
    public PageTableExampleDescriptor() {
        super();
        super.addResource("java", "PageTableExample.java", "kscommons/pagetable/PageTableExample.java", "Example usage of KSPageTable.");
        super.addResource("css", "KSScrollTable.css", "KSScrollTable.css", "Default styling of KSPageTable.");
        super.addCssResource(KSCommonResources.INSTANCE.scrollTableCss()); 
    }
    public String getDescription() {       
        return "PageTable"; 
    }

    public Widget getExampleWidget() {
        return new PageTableExample();
    }
 
    public String getTitle() {
        return "PageTable";
    }
}