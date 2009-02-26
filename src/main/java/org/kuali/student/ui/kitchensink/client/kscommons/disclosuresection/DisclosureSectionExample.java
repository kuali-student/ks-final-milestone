package org.kuali.student.ui.kitchensink.client.kscommons.disclosuresection;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class DisclosureSectionExample extends Composite {

    final HorizontalPanel main = new HorizontalPanel();
    final KSDisclosureSection section = new KSDisclosureSection("Click Here", null, false);
    final KSLabel label = new KSLabel("Test", false);

    public DisclosureSectionExample() { 
        
        main.addStyleName(STYLE_EXAMPLE);
        
        section.clear();
        section.add(new KSImage("images/flower3.jpg"));
       
        main.add(section);

        super.initWidget(main);
    }
}
