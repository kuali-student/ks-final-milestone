package org.kuali.student.ui.kitchensink.client.kscommons.disclosuresection;

import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class DisclosureSectionExample extends Composite {

    final HorizontalPanel main = new HorizontalPanel();
    final KSDisclosureSection section = new KSDisclosureSection("Click here");

    public DisclosureSectionExample() {
        
        section.add(new Image("images/flowers.jpg"));
        
        main.add(section);

        super.initWidget(main);
    }
}
