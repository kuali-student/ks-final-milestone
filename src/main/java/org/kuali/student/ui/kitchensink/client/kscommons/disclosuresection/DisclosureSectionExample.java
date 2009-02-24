package org.kuali.student.ui.kitchensink.client.kscommons.disclosuresection;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.ui.kitchensink.client.kscommons.KSWidgetFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class DisclosureSectionExample extends Composite {

    final HorizontalPanel main = new HorizontalPanel();
    final KSDisclosureSection section = KSWidgetFactory.getDisclosureSectionInstance("Click Here", null, false);
    final KSLabel label;;

    public DisclosureSectionExample() { 
        
        main.addStyleName(STYLE_EXAMPLE);
        
        label = new KSLabel("Test", false);

        section.clear();
        section.add(new Image("images/flower3.jpg"));
       
        main.add(section);

        super.initWidget(main);
    }
}
