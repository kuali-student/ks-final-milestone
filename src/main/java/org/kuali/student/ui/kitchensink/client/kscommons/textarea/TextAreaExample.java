package org.kuali.student.ui.kitchensink.client.kscommons.textarea;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TextAreaExample extends Composite {

    private final VerticalPanel main = new VerticalPanel();
    
    private final KSTextArea textArea = new KSTextArea();
    
    final KSLabel label = new KSLabel("Click inside the box to start entering text. "); 

    public TextAreaExample() {
        main.addStyleName(STYLE_EXAMPLE);
        
        main.add(label);
        main.add(textArea);

        super.initWidget(main);
    }


}
