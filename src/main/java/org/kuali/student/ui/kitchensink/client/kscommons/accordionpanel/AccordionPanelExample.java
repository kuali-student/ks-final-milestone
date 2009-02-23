package org.kuali.student.ui.kitchensink.client.kscommons.accordionpanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSAccordionPanel;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.ui.kitchensink.client.kscommons.KSWidgetFactory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AccordionPanelExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    final VerticalPanel canadian = new VerticalPanel();
    final VerticalPanel us = new VerticalPanel();
    final VerticalPanel other = new VerticalPanel();
    
    final KSLabel label = KSWidgetFactory.getLabelInstance("Click on a heading below to show or hide the institution list: ", false);
    
    final KSAccordionPanel accordion = KSWidgetFactory.getAccordionPanelInstance();

    final KSCheckBox canUbc = KSWidgetFactory.getCheckBoxInstance("University of British Columbia");
    final KSCheckBox canUt = KSWidgetFactory.getCheckBoxInstance("University of Toronto");
    final KSCheckBox canMcg = KSWidgetFactory.getCheckBoxInstance("McGill University");

    final KSCheckBox usFsu = KSWidgetFactory.getCheckBoxInstance("Florida State University");
    final KSCheckBox usUmd = KSWidgetFactory.getCheckBoxInstance("University of Maryland, College Park");
    final KSCheckBox usUw = KSWidgetFactory.getCheckBoxInstance("University of Washington");
    final KSCheckBox usUcb = KSWidgetFactory.getCheckBoxInstance("University of California, Berkeley");
    final KSCheckBox usMit = KSWidgetFactory.getCheckBoxInstance("Massachusetts Institute of Technology");
    final KSCheckBox usUsc = KSWidgetFactory.getCheckBoxInstance("University of Southern California");
    final KSCheckBox usCmu = KSWidgetFactory.getCheckBoxInstance("Carnegie Mellon University");
    final KSCheckBox usSjdc = KSWidgetFactory.getCheckBoxInstance("San Joaquin Delta College");

    final KSCheckBox usCam = KSWidgetFactory.getCheckBoxInstance("University of Cambridge");
    final KSCheckBox usDel = KSWidgetFactory.getCheckBoxInstance("University of Delhi");
    final KSCheckBox usSyd = KSWidgetFactory.getCheckBoxInstance("University of Sydney");
    final KSCheckBox usJoh = KSWidgetFactory.getCheckBoxInstance("University of Johannesburg");

    public AccordionPanelExample() {
        
        main.addStyleName(STYLE_EXAMPLE);

        loadChoices();
        
        accordion.addPanel("Canadian Institutions", canadian);
        accordion.addPanel("US Institutions", us);
        accordion.addPanel("Rest of the World", other);

        main.add(label);
        main.add(accordion);
        
        super.initWidget(main);
    }

    private void loadChoices() {

        canadian.add(canUbc);
        canadian.add(canUt);
        canadian.add(canMcg);
       
        us.add(usFsu);
        us.add(usUmd);
        us.add(usUw);
        us.add(usUcb);
        us.add(usMit);
        us.add(usUsc);
        us.add(usCmu);
        us.add(usSjdc);

        other.add(usCam);
        other.add(usDel);
        other.add(usSyd);
        other.add(usJoh);    
    }
}
