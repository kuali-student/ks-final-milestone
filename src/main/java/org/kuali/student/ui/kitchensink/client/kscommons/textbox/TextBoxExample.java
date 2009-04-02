package org.kuali.student.ui.kitchensink.client.kscommons.textbox;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSLabel;
//import org.kuali.student.common.ui.client.widgets.KSTextBox;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.kuali.student.common.ui.client.widgets.counting.KSTextBox;
import org.kuali.student.common.ui.client.widgets.counting.impl.KSTextBoxImpl;
public class TextBoxExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
     
    final KSTextBox textBox = new KSTextBox(30);
    //final KSTextBoxImpl textBox = new KSTextBoxImpl();
    final KSLabel label = new KSLabel("Click inside the box to start entering. ");

    public TextBoxExample() {
        main.addStyleName(STYLE_EXAMPLE);
        
        main.add(label);
        main.add(textBox);
      
        super.initWidget(main);
    }


}
