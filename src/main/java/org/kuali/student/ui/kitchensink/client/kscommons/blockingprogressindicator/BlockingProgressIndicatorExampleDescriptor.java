package org.kuali.student.ui.kitchensink.client.kscommons.blockingprogressindicator;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class BlockingProgressIndicatorExampleDescriptor extends KitchenSinkExample {
    public BlockingProgressIndicatorExampleDescriptor() {
        super();
        super.addResource("java", "BlockingProgressExample.java", "kscommons/blockingprogressindicator/BlockingProgressIndicatorExample.java", "Example usage of KSBlockingProgressIndicator.");
    }
    public String getDescription() {       
        return "BlockingProgressIndicator...... "; 
    }

    public Widget getExampleWidget() {
        return new BlockingProgressIndicatorExample();
    }
 
    public String getTitle() {
        return "KSBlockingProgressIndicator";
    }

}
