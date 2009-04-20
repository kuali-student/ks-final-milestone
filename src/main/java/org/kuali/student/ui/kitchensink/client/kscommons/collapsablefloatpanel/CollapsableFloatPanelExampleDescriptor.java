package org.kuali.student.ui.kitchensink.client.kscommons.collapsablefloatpanel;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class CollapsableFloatPanelExampleDescriptor extends KitchenSinkExample {
    public CollapsableFloatPanelExampleDescriptor() {
        super();
        super.addResource("java", "CollapsableFloatPanelExample.java", "kscommons/collapsablefloatpanel/CollapsableFloatPanelExample.java", "Example usage of KSFloatPanel.");
        super.addResource("css", "KSFloatPanel.css", "KSFloatPanel.css", "Default styling of KSCollapsableFloatPanel.");
        super.addCssResource(KSCommonResources.INSTANCE.floatPanelCss());
    }
    public String getDescription() {       
        return "CollapsableFloatPanel is a panel that can pop up over other widgets. It overlays the browser's client area (and any previously-created popups). It is not modal, i.e. user can still interact with other panels "; 
    }

    public Widget getExampleWidget() {
        return new CollapsableFloatPanelExample();
    }
 
    public String getTitle() {
        return "Collapsable Float Panel";
    }

}
