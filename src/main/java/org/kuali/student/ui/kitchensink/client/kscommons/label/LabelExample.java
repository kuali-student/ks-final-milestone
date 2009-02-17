package org.kuali.student.ui.kitchensink.client.kscommons.label;

import org.kuali.student.core.ui.client.widgets.KSLabel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LabelExample extends Composite {


    final VerticalPanel panel = new VerticalPanel();
    final KSLabel ksLabel1 = new KSLabel("This is a label");
//    final KSLabel ksLabel2 = new KSLabel("This is another very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very long label with wrap",true);


    public LabelExample() {

        panel.add(ksLabel1);
//        panel.add(ksLabel2);

        super.initWidget(panel);
    }


}
