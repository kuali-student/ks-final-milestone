package org.kuali.student.ui.kitchensink.client.kscommons.tooltip;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class ToolTipExampleDescriptor extends KitchenSinkExample {
    public ToolTipExampleDescriptor() {
        super();
        super.addResource("java", "ToolTipExample.java", "kscommons/tooltip/ToolTipExample.java", "Example usage of KSToolTip.");
        super.addResource("css", "ToolTipExample.css", "examplecss/ToolTipExample.css", "Example styling of KSToolTip.");
    }
    public String getDescription() {       
        return "ToolTip is a popup panel that appears when a user moves their mouse over a widget and disappears when the mouse leaves that widget."; 
    }

    public Widget getExampleWidget() {
        return new ToolTipExample();
    }
 
    public String getTitle() {
        return "Tool Tip";
    }

}
