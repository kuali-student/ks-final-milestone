package org.kuali.student.ui.kitchensink.client.kscommons.disclosuresection;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class DisclosureSectionExample extends Composite {

    final HorizontalPanel main = new HorizontalPanel();
    final KSDisclosureSection section = GWT.create(KSDisclosureSection.class);
    final KSLabel label = GWT.create(KSLabel.class);

    public DisclosureSectionExample() { 
        
        main.addStyleName(STYLE_EXAMPLE);
        
        label.init("Test", false);
        section.init("Click Here", null, false);

        section.getPanel().clear();
        section.getPanel().add(new Image("images/flower3.jpg"));
       
        main.add(section);

        super.initWidget(main);
    }
}
