package org.kuali.student.ui.kitchensink.client.kscommons.blockingprogressindicator;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class BlockingProgressIndicatorExampleDescriptor extends KitchenSinkExample {
    public BlockingProgressIndicatorExampleDescriptor() {
        super();
        super.addResource("java", "BlockingProgressExample.java", "kscommons/blockingprogressindicator/BlockingProgressIndicatorExample.java", "Example usage of KSBlockingProgressIndicator.");
        super.addResource("css", "KSBlockingProgress.css", "KSBlockingProgressIndicator.css", "Default styling of KSBlockingProgressIndicator.");
        super.addResource("css", "BlockingProgressExample.css", "examplecss//BlockingProgressIndicatorExample.css", "Example styling of KSBlockingProgressIndicator.");
    }
    public String getDescription() {       
        return "BlockingProgressIndicator displays a modal popup that shows the status of some long running task. Multiple tasks can be included on the display"; 
    }

    public Widget getExampleWidget() {
        return new BlockingProgressIndicatorExample();
    }
 
    public String getTitle() {
        return "Blocking Progress Bar";
    }

}
