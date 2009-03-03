package org.kuali.student.ui.kitchensink.client.kscommons.textbox;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_TEXTBOX_BACKGROUND;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_TEXTBOX_BORDER;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_TEXTBOX_LARGE;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;

public class TextBoxExample extends Composite {

    final SimplePanel main = new SimplePanel();
    private final FlexTable table = new FlexTable();
    
    final KSTextBox textBox1 = new KSTextBox();
    final KSTextBox textBox2 = new KSTextBox();
    final KSTextBox textBox3 = new KSTextBox();
    final KSTextBox textBox4 = new KSTextBox();
    
    final KSLabel label1 = new KSLabel("Default Textbox");
    final KSLabel label2 = new KSLabel("TextBox with border");
    final KSLabel label3 = new KSLabel("TextBox with background colour");
    final KSLabel label4 = new KSLabel("Textbox with large text");
 

    public TextBoxExample() {
        main.addStyleName(STYLE_EXAMPLE);

        textBox2.addStyleName(STYLE_TEXTBOX_BORDER);;
        textBox3.addStyleName(STYLE_TEXTBOX_BACKGROUND);
        textBox4.addStyleName(STYLE_TEXTBOX_LARGE);
        
        int row = 0;
        table.setWidget(row, 0, textBox1);
        table.setWidget(row, 1, label1);

        row++;
        table.setWidget(row, 0, textBox3);
        table.setWidget(row, 1, label3);

        row++;
        table.setWidget(row, 0, textBox4);
        table.setWidget(row, 1, label4);
        
        row++;
        table.setWidget(row, 0, textBox2);
        table.setWidget(row, 1, label2);
        
        main.add(table);

        super.initWidget(main);
    }


}
