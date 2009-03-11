package org.kuali.student.ui.kitchensink.client.kscommons.richeditor;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class RichEditorExampleDescriptor extends KitchenSinkExample {
    public RichEditorExampleDescriptor() {
        super();
        super.addResource("java", "RichEditorExample.java", "kscommons/richeditor/RichEditorExample.java", "Example usage of KSRichEditor.");
        super.addResource("css", "KSRichTextEditor.css", "KSRichTextEditor.css", "Default styling of KSRichEditor.");
        super.addResource("css", "RichEditorExample.css", "examplecss/RichEditorExample.css", "Example styling of KSRichEditor.");
    }
    public String getDescription() {       
        return "RichEditor is text editor that allows complex styling and formatting. Exact functionality will depend on the browser"; 
    }

    public Widget getExampleWidget() {
        return new RichEditorExample();
    }
 
    public String getTitle() {
        return "Rich Editor";
    }

}
