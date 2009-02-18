package org.kuali.student.ui.kitchensink.client.kscommons.checkbox;

import org.kuali.student.common.ui.client.widgets.KSCheckBox;

import com.google.gwt.core.client.GWT;
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

    StringBuffer sb = new StringBuffer();

    final VerticalPanel main = new VerticalPanel();
    final KSCheckBox checkBox1 = GWT.create(KSCheckBox.class);
    final KSCheckBox checkBox2 = GWT.create(KSCheckBox.class);
    final KSCheckBox checkBox3 = GWT.create(KSCheckBox.class);

    public CheckBoxExample() {
        
        checkBox1.setText(CHECK_BOX_1);
        checkBox2.setText(CHECK_BOX_2);
        checkBox3.setText(CHECK_BOX_3);

        checkBox1.addClickHandler(new MyClickHandler());
        checkBox2.addClickHandler(new MyClickHandler());
        checkBox3.addClickHandler(new MyClickHandler());

        main.add(checkBox1);
        main.add(checkBox2);
        main.add(checkBox3);

        super.initWidget(main);
    }

    public class MyClickHandler implements ClickHandler  {

        @Override
        public void onClick(ClickEvent event) {
            KSCheckBox b = (KSCheckBox)(event.getSource());
            if (b.getText().equals(CHECK_BOX_1)) {
                box1Checked = b.getValue();
            }
            else if (b.getText().equals(CHECK_BOX_2)) {
                box2Checked = b.getValue();
            }
            else if (b.getText().equals(CHECK_BOX_3)) {
                box3Checked = b.getValue();
            }

            sb = new StringBuffer();
            sb.append(box1Checked?" Box 1":" ");
            sb.append(box2Checked?" Box 2":" ");
            sb.append(box3Checked?" Box 3":" ");

            Window.alert("You have clicked : " + sb.toString() );

        }     
    }
}
