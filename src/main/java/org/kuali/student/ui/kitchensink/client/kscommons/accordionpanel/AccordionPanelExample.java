package org.kuali.student.ui.kitchensink.client.kscommons.accordionpanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSAccordionPanel;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AccordionPanelExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    final VerticalPanel canadian = new VerticalPanel();
    final VerticalPanel us = new VerticalPanel();
    final VerticalPanel other = new VerticalPanel();
    
    final KSLabel label = GWT.create(KSLabel.class);
    
    final KSAccordionPanel accordion = GWT.create(KSAccordionPanel.class);

    final KSCheckBox canUbc = GWT.create(KSCheckBox.class);
    final KSCheckBox canUt = GWT.create(KSCheckBox.class);
    final KSCheckBox canMcg = GWT.create(KSCheckBox.class);

    final KSCheckBox usFsu = GWT.create(KSCheckBox.class);
    final KSCheckBox usUmd = GWT.create(KSCheckBox.class);
    final KSCheckBox usUw = GWT.create(KSCheckBox.class);
    final KSCheckBox usUcb = GWT.create(KSCheckBox.class);
    final KSCheckBox usMit = GWT.create(KSCheckBox.class);
    final KSCheckBox usUsc = GWT.create(KSCheckBox.class);
    final KSCheckBox usCmu = GWT.create(KSCheckBox.class);
    final KSCheckBox usSjdc = GWT.create(KSCheckBox.class);

    final KSCheckBox usCam = GWT.create(KSCheckBox.class);
    final KSCheckBox usDel = GWT.create(KSCheckBox.class);
    final KSCheckBox usSyd = GWT.create(KSCheckBox.class);
    final KSCheckBox usJoh = GWT.create(KSCheckBox.class);

    public AccordionPanelExample() {
        
        main.addStyleName(STYLE_EXAMPLE);

        loadChoices();
        
        label.init("Click on a heading below to show or hide the institution list: ", false);
        
        accordion.addPanel("Canadian Institutions", canadian);
        accordion.addPanel("US Institutions", us);
        accordion.addPanel("Rest of the World", other);

        main.add(label);
        main.add(accordion);
        
        super.initWidget(main);
    }

    private void loadChoices() {

        canUbc.init("University of British Columbia");
        canUt.init("University of Toronto");
        canMcg.init("McGill University");
        canadian.add(canUbc);
        canadian.add(canUt);
        canadian.add(canMcg);

        usFsu.init("Florida State University");
        usUmd.init("University of Maryland, College Park");
        usUw.init("University of Washington");
        usUcb.init("University of California, Berkeley");
        usMit.init("Massachusetts Institute of Technology");
        usUsc.init("University of Southern California");
        usCmu.init("Carnegie Mellon University");
        usSjdc.init("San Joaquin Delta College");       
        us.add(usFsu);
        us.add(usUmd);
        us.add(usUw);
        us.add(usUcb);
        us.add(usMit);
        us.add(usUsc);
        us.add(usCmu);
        us.add(usSjdc);

        usCam.init("University of Cambridge");
        usDel.init("University of Delhi");
        usSyd.init("University of Sydney");
        usJoh.init("University of Johannesburg");
        other.add(usCam);
        other.add(usDel);
        other.add(usSyd);
        other.add(usJoh);    
    }
}
