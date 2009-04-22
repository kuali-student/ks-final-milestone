package org.kuali.student.ui.kitchensink.client.kscommons.textarea;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class TextAreaExampleDescriptor extends KitchenSinkExample {
    public TextAreaExampleDescriptor() {
        super();
        super.addResource("java", "TextAreaExample.java", "kscommons/textarea/TextAreaExample.java", "Example usage of KSTextArea.");
        super.addResource("css", "KSTextArea.css", "KSTextArea.css", "Default styling of KSTextArea.");
        super.addCssResource(KSCommonResources.INSTANCE.textAreaCss());
    }
    public String getDescription() {       
        return "TextArea is a text box that allows multiple lines of text to be entered. "; 
    }

    public Widget getExampleWidget() {
        return new TextAreaExample();
    }
 
    public String getTitle() {
        return "Text Area";
    }

}
