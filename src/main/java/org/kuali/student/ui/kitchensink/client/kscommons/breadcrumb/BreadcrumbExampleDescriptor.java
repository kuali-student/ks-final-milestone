package org.kuali.student.ui.kitchensink.client.kscommons.breadcrumb;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class BreadcrumbExampleDescriptor extends KitchenSinkExample {
    public BreadcrumbExampleDescriptor() {
        super();
        super.addResource("java", "BreadcrumbExample.java", "kscommons/breadcrumb/BreadcrumbExample.java", "Example usage of KSBreadcrumb.");
        super.addResource("css", "KSBreadcrumb.css", "KSBreadcrumb.css", "Default styling of KSBreadcrumb.");
        super.addCssResource(KSCommonResources.INSTANCE.breadcrumbCss());
    }
    public String getDescription() {       
        return "Breadcrumb is used to give users a way of tracking how they got to the currrent page";
    }

    public Widget getExampleWidget() {
        return new BreadcrumbExample();
    }

    public String getTitle() {
        return "Breadcrumb";
    }

}
