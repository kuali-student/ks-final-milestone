package org.kuali.student.ui.kitchensink.client.kscommons.progressindicator;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ProgressIndicatorExampleDescriptor extends KitchenSinkExample {
    public ProgressIndicatorExampleDescriptor() {
        super();
        super.addResource("java", "ProgressIndicatorExample.java", "kscommons/progressindicator/ProgressIndicatorExample.java", "Example usage of KSProgressIndicator.");
        super.addResource("css", "KSProgressIndicator.css", "KSProgressIndicator.css", "Default styling of KSProgressIndicator.");
        super.addCssResource(KSCommonResources.INSTANCE.progressIndicatorCss());
    }
    public String getDescription() {       
        return "ProgressIndicator is a widget that can be used to indicate some field specific activity. The indicator does not block other user activity."; 
    }

    public Widget getExampleWidget() {
        return new ProgressIndicatorExample();
    }
 
    public String getTitle() {
        return "Progress Indicator";
    }

}