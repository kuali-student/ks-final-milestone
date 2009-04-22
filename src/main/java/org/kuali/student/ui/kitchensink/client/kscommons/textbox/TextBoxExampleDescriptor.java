package org.kuali.student.ui.kitchensink.client.kscommons.textbox;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class TextBoxExampleDescriptor extends KitchenSinkExample {
    public TextBoxExampleDescriptor() {
        super();
        super.addResource("java", "TextBoxExample.java", "kscommons/textbox/TextBoxExample.java", "Example usage of KSTextBox.");
        super.addResource("css", "KSTextBox.css", "KSTextBox.css", "Default styling of KSTextBox.");
        super.addCssResource(KSCommonResources.INSTANCE.textBoxCss());
    }
    public String getDescription() {       
        return "TextBox a standard single-line text box used to capture user input. "; 
    }

    public Widget getExampleWidget() {
        return new TextBoxExample();
    }
 
    public String getTitle() {
        return "Text Box";
    }

}
