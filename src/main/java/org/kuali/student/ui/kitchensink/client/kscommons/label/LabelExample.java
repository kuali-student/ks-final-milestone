package org.kuali.student.ui.kitchensink.client.kscommons.label;

import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LabelExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    
    final KSLabel label1 = GWT.create(KSLabel.class);
//    final KSLabel ksLabel2 = new KSLabel("This is another very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very long label with wrap",true);


    public LabelExample() {

        label1.setText("This is a label");

        main.add(label1);
//        panel.add(ksLabel2);

        super.initWidget(main);
    }


}
