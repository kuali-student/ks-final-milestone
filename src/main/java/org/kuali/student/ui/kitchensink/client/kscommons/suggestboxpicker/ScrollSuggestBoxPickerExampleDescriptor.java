package org.kuali.student.ui.kitchensink.client.kscommons.suggestboxpicker;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ScrollSuggestBoxPickerExampleDescriptor extends KitchenSinkExample {
    public ScrollSuggestBoxPickerExampleDescriptor(){
        super();
        super.addResource("java", "ScrollSuggestBoxPickerExample.java", "kscommons/suggestboxpicker/ScrollSuggestBoxPickerExample.java", "Example usage of KSSuggestBoxPicker with PagingScrollTable.");
        super.addResource("css", "KSTextBox.css", "KSTextBox.css", "Default styling of KSSuggestBox.");
        super.addResource("css", "KSSuggestBoxPicker.css", "KSSuggestBoxPicker.css", "Default styling of KSSuggestBoxPicker.");
        super.addResource("css", "KSAdvancedSearchWindow.css", "KSAdvancedSearchWindow.css", "Default styling of KSAdvancedSearchWindow.");
        super.addCssResource(KSCommonResources.INSTANCE.textBoxCss());
        super.addCssResource(KSCommonResources.INSTANCE.suggestBoxPickerCss());
        super.addCssResource(KSCommonResources.INSTANCE.advancedSearchWindowCss());
    }
    
    @Override
    public String getDescription() {
        return "The suggest box picker allows a user to select a suggested item and add it to a list, they can also use an advanced search to find and select multiple items.";
    }

    @Override
    public Widget getExampleWidget() {
        return new SuggestBoxPickerExample();
    }

    @Override
    public String getTitle() {
        return "Page Suggest Box Picker";
    }
    


}
