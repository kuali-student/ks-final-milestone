package org.kuali.student.ui.kitchensink.client.kscommons.textarea;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.ui.kitchensink.client.kscommons.KSWidgetFactory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TextAreaExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    final KSTextArea textArea = KSWidgetFactory.getTextAreaInstance();
 

    public TextAreaExample() {
        main.addStyleName(STYLE_EXAMPLE);
        main.add(textArea);
        super.initWidget(main);
    }


}
