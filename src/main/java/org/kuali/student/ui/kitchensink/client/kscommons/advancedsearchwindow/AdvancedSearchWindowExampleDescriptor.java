package org.kuali.student.ui.kitchensink.client.kscommons.advancedsearchwindow;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;
import org.kuali.student.ui.kitchensink.client.kscommons.suggestbox.SuggestBoxExample;

import com.google.gwt.user.client.ui.Widget;

public class AdvancedSearchWindowExampleDescriptor extends KitchenSinkExample {
    public AdvancedSearchWindowExampleDescriptor(){
        super();
        super.addResource("java", "AdvancedSearchWindowExample.java", "kscommons/advancedsearchwindow/AdvancedSearchWindowExample.java", "Example usage of KSAdvancedSearchWindow.");
        super.addResource("css", "KSAdvancedSearchWindow.css", "KSAdvancedSearchWindow.css", "Default styling of KSAdvancedSearchWindow.");
        super.addCssResource(KSCommonResources.INSTANCE.advancedSearchWindowCss());
    }
    
    @Override
    public String getDescription() {
        return "The Advanced Search Window allows a user to search for specific items, select them, and have the result returned to the caller.";
    }

    @Override
    public Widget getExampleWidget() {
        return new AdvancedSearchWindowExample();
    }

    @Override
    public String getTitle() {
        return "Advanced Search Window";
    }
    


}
