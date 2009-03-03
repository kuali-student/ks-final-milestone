package org.kuali.student.ui.kitchensink.client.kscommons.textarea;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_TEXTAREA;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_TEXTAREA_FANCY;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;

public class TextAreaExample extends Composite {

    private final SimplePanel main = new SimplePanel();
    private final FlexTable table = new FlexTable();
    
    private final KSTextArea textArea1 = new KSTextArea();
    private final KSTextArea textArea2 = new KSTextArea();
    
    final KSLabel label1 = new KSLabel("Default TextArea");
    final KSLabel label2 = new KSLabel("Fancy TextArea");
    

    public TextAreaExample() {
        main.addStyleName(STYLE_EXAMPLE);
        textArea1.addStyleName(STYLE_TEXTAREA);
        textArea2.addStyleName(STYLE_TEXTAREA);
        textArea2.addStyleName(STYLE_TEXTAREA_FANCY);
        
        int row = 0;
        table.setWidget(row, 0, textArea1);
        table.setWidget(row, 1, label1);

        row++;
        table.setWidget(row, 0, textArea2);
        table.setWidget(row, 1, label2);

        main.add(table);
        super.initWidget(main);
    }


}
