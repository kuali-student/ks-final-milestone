package org.kuali.student.ui.kitchensink.client.kscommons.button;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.user.client.ui.Widget;

public class ButtonExampleDescriptor extends KitchenSinkExample {
    
    
    public ButtonExampleDescriptor() {
        super();
        super.addResource("java", "ButtonExample.java", "kscommons/button/ButtonExample.java", "Example usage of KSButton.");
        super.addResource("css", "KSButton.css", "KSButton.css", "Default styling of KSButton.");
        super.addCssResource(KSCommonResources.INSTANCE.buttonCss());
    }
    public String getDescription() {       
        return "Button is used to initiate some action, e.g. submit a form, start a search, etc.";
    }

    public Widget getExampleWidget() {
        return new ButtonExample();
    }

    public String getTitle() {
        return "Button";
    }
    

}
