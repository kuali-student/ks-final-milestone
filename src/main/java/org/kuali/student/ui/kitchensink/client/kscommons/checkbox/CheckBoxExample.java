package org.kuali.student.ui.kitchensink.client.kscommons.checkbox;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CheckBoxExample extends Composite {

    private static final String CHECK_BOX_3 = "CheckBox 3";
    private static final String CHECK_BOX_2 = "CheckBox 2";
    private static final String CHECK_BOX_1 = "CheckBox 1";
    boolean box1Checked;
    boolean box2Checked;
    boolean box3Checked;

    private KSButton submitButton ;

    private StringBuffer sb = new StringBuffer("You clicked: ");

    final VerticalPanel main = new VerticalPanel();
    final KSCheckBox checkBox1 = new KSCheckBox(CHECK_BOX_1);
    final KSCheckBox checkBox2 = new KSCheckBox(CHECK_BOX_2);
    final KSCheckBox checkBox3 = new KSCheckBox(CHECK_BOX_3);

    public CheckBoxExample() {

        main.addStyleName(STYLE_EXAMPLE);

        submitButton = new KSButton("Submit", new MyClickHandler() );      

        main.add(checkBox1);
        main.add(checkBox2);
        main.add(checkBox3);
        
        main.add(submitButton);

        super.initWidget(main);
    }

    public class MyClickHandler implements ClickHandler  {

        @Override
        public void onClick(ClickEvent event) {
            if (checkBox1.getValue()) {
                sb.append(checkBox1.getText());
                sb.append(", ");
            }
            if (checkBox2.getValue()) {
                sb.append(checkBox2.getText());
                sb.append(", ");
            }
            if (checkBox3.getValue()) {
                sb.append(checkBox3.getText());
                sb.append(", ");
            }
            Window.alert(sb.toString() );

        }
    }     
}
