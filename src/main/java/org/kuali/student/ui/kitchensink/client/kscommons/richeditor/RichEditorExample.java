package org.kuali.student.ui.kitchensink.client.kscommons.richeditor;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.ui.kitchensink.client.kscommons.KSWidgetFactory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RichEditorExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    
    KSRichEditor richEditor;    
    final KSLabel label = new KSLabel("Click in the box to open the editor: ", false);

    public RichEditorExample() {

        try {
            richEditor = new KSRichEditor();
        } catch (Exception e) {
            e.printStackTrace();
        }  
        
        main.addStyleName(STYLE_EXAMPLE);

        main.add(label);
        main.add(richEditor);
        super.initWidget(main);
    }


}
