package org.kuali.student.ui.kitchensink.client.kscommons.textarea;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TextAreaExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    final KSTextArea textArea = GWT.create(KSTextArea.class);
 

    public TextAreaExample() {

        main.add(textArea);
        super.initWidget(main);
    }


}
