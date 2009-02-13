package org.kuali.student.ui.kitchensink.client.kscommons.checkbox;

import org.kuali.student.core.ui.client.widgets.KSButton;
import org.kuali.student.core.ui.client.widgets.KSCheckBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CheckBoxExample extends Composite {

    private static final String CHECK_BOX_3 = "CheckBox 3";
    private static final String CHECK_BOX_2 = "CheckBox 2";
    private static final String CHECK_BOX_1 = "CheckBox 1";
    boolean box1Checked;
    boolean box2Checked;
    boolean box3Checked;

    StringBuffer sb = new StringBuffer();

    final VerticalPanel panel = new VerticalPanel();
    final KSCheckBox ksCheckBox1 = new KSCheckBox(CHECK_BOX_1);
    final KSCheckBox ksCheckBox2 = new KSCheckBox(CHECK_BOX_2);
    final KSCheckBox ksCheckBox3 = new KSCheckBox(CHECK_BOX_3);

    public CheckBoxExample() {

        ksCheckBox1.addClickHandler(new MyClickHandler());
        ksCheckBox2.addClickHandler(new MyClickHandler());
        ksCheckBox3.addClickHandler(new MyClickHandler());

        panel.add(ksCheckBox1);
        panel.add(ksCheckBox2);
        panel.add(ksCheckBox3);

        super.initWidget(panel);
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
