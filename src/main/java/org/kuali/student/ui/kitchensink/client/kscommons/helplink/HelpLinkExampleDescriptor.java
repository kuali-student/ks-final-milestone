package org.kuali.student.ui.kitchensink.client.kscommons.helplink;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class HelpLinkExampleDescriptor extends KitchenSinkExample {
    public HelpLinkExampleDescriptor() {
        super();
        super.addResource("java", "HelpLinkExample.java", "kscommons/helplink/HelpLinkExample.java", "Example usage of KSHelpLink.");
        super.addResource("css", "KSHelpLink.css", "KSHelpLink.css", "Default styling of KSHelpLink.");
        super.addResource("css", "KSToolTip.css", "KSToolTip.css", "Default styling of KSToolTip.");
        super.addResource("css", "KSInfoDialogPanel.css", "KSInfoDialogPanel.css", "Default styling of KSInfoDialogPanel.");
        super.addResource("css", "KSDialogPanel.css", "KSDialogPanel.css", "Default styling of KSDialogPanel.");
        super.addCssResource(KSCommonResources.INSTANCE.helpLinkCss());
        super.addCssResource(KSCommonResources.INSTANCE.toolTipCss());
        super.addCssResource(KSCommonResources.INSTANCE.infoDialogPanelCss());
        super.addCssResource(KSCommonResources.INSTANCE.dialogPanelCss());
    }
    public String getDescription() {       
        return "This adds a dynamic link to indicate progress/status on a field.";
    }

    public Widget getExampleWidget() {
        return new HelpLinkExample();
    }
 
    public String getTitle() {
        return "Help Link";
    }

}
