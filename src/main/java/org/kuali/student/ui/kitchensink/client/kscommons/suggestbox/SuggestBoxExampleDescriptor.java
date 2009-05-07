package org.kuali.student.ui.kitchensink.client.kscommons.suggestbox;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class SuggestBoxExampleDescriptor extends KitchenSinkExample {
    public SuggestBoxExampleDescriptor(){
        super();
        super.addResource("java", "SuggestBoxExample.java", "kscommons/suggestbox/SuggestBoxExample.java", "Example usage of KSSuggestBox.");
        super.addResource("css", "KSTextBox.css", "KSTextBox.css", "Default styling of KSSuggestBox.");
        super.addCssResource(KSCommonResources.INSTANCE.textBoxCss());
    }
    
    @Override
    public String getDescription() {
        return "The suggest box can provide suggestions to the user as they type in text";
    }

    @Override
    public Widget getExampleWidget() {
        return new SuggestBoxExample();
    }

    @Override
    public String getTitle() {
        return "Suggestion Box";
    }
    

}
