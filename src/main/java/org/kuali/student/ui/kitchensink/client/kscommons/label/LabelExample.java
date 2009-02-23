package org.kuali.student.ui.kitchensink.client.kscommons.label;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.ui.kitchensink.client.kscommons.KSWidgetFactory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LabelExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    
    final KSLabel label1 = KSWidgetFactory.getLabelInstance("This is a label", false);
//    final KSLabel ksLabel2 = new KSLabel("This is another very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very long label with wrap",true);


    public LabelExample() {

        main.addStyleName(STYLE_EXAMPLE);

        main.add(label1);
//        panel.add(ksLabel2);

        super.initWidget(main);
    }


}
