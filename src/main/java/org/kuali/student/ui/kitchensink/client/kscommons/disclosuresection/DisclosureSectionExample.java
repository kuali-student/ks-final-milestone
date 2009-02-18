package org.kuali.student.ui.kitchensink.client.kscommons.disclosuresection;

import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class DisclosureSectionExample extends Composite {

    final HorizontalPanel panel = new HorizontalPanel();
    final KSDisclosureSection ksSection = new KSDisclosureSection("Click here");

    public DisclosureSectionExample() {
        
        ksSection.add(new Image("images/flowers.jpg"));
        
        panel.add(ksSection);

        super.initWidget(panel);
    }
}
