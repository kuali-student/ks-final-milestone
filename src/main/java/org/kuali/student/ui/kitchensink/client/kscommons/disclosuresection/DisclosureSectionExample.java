package org.kuali.student.ui.kitchensink.client.kscommons.disclosuresection;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.ui.kitchensink.client.kscommons.KSWidgetFactory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class DisclosureSectionExample extends Composite {

    final HorizontalPanel main = new HorizontalPanel();
    final KSDisclosureSection section = KSWidgetFactory.getDisclosureSectionInstance("Click Here", null, false);
    final KSLabel label = KSWidgetFactory.getLabelInstance("Test", false);

    public DisclosureSectionExample() { 
        
        main.addStyleName(STYLE_EXAMPLE);
        
        section.clear();
        section.add(KSWidgetFactory.getImageInstance("images/flower3.jpg"));
       
        main.add(section);

        super.initWidget(main);
    }
}
