package org.kuali.student.ui.kitchensink.client.kscommons.accordionpanel;

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

        loadChoices();
        
        label.setText("Click on a heading below to show or hide the institution list: ");
        
        accordion.addPanel("Canadian Institutions", canadian);
        accordion.addPanel("US Institutions", us);
        accordion.addPanel("Rest of the World", other);

        main.add(label);
        main.add(accordion);
        
        super.initWidget(main);
    }

    private void loadChoices() {

        canUbc.setText("University of British Columbia");
        canUt.setText("University of Toronto");
        canMcg.setText("McGill University");
        canadian.add(canUbc);
        canadian.add(canUt);
        canadian.add(canMcg);

        usFsu.setText("Florida State University");
        usUmd.setText("University of Maryland, College Park");
        usUw.setText("University of Washington");
        usUcb.setText("University of California, Berkeley");
        usMit.setText("Massachusetts Institute of Technology");
        usUsc.setText("University of Southern California");
        usCmu.setText("Carnegie Mellon University");
        usSjdc.setText("San Joaquin Delta College");       
        us.add(usFsu);
        us.add(usUmd);
        us.add(usUw);
        us.add(usUcb);
        us.add(usMit);
        us.add(usUsc);
        us.add(usCmu);
        us.add(usSjdc);

        usCam.setText("University of Cambridge");
        usDel.setText("University of Delhi");
        usSyd.setText("University of Sydney");
        usJoh.setText("University of Johannesburg");
        other.add(usCam);
        other.add(usDel);
        other.add(usSyd);
        other.add(usJoh);    
    }
}
