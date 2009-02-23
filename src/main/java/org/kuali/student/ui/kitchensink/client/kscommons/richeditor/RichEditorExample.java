package org.kuali.student.ui.kitchensink.client.kscommons.richeditor;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RichEditorExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    
    final KSRichEditor richEditor = GWT.create(KSRichEditor.class);
    
    final KSLabel label = GWT.create(KSLabel.class);

    public RichEditorExample() {

        label.init("Click in the box to open the editor: ", false);

        main.addStyleName(STYLE_EXAMPLE);

        main.add(label);
        main.add(richEditor);
        super.initWidget(main);
    }


}
