package org.kuali.student.ui.kitchensink.client.kscommons.accordionpanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_ACCORDION_PANEL_IMAGE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_ACCORDION_PANEL;

import org.kuali.student.common.ui.client.widgets.KSAccordionPanel;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AccordionPanelExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    final VerticalPanel canadian = new VerticalPanel();
    final VerticalPanel us = new VerticalPanel();
    final VerticalPanel other = new VerticalPanel();
    
    final KSLabel label = new KSLabel("Click on a heading below to show or hide the images: ", false);
    
    final KSImage image1 = new KSImage("images/flower1.jpg");
    final KSImage image2 = new KSImage("images/flower2.jpg");
    final KSImage image3 = new KSImage("images/flower3.jpg");

    final KSAccordionPanel accordion = new KSAccordionPanel();


    public AccordionPanelExample() {
        
        main.addStyleName(STYLE_EXAMPLE);
      
        image1.addStyleName(STYLE_ACCORDION_PANEL_IMAGE);
        image2.addStyleName(STYLE_ACCORDION_PANEL_IMAGE);
        image3.addStyleName(STYLE_ACCORDION_PANEL_IMAGE);

        accordion.addPanel("Orange Flower", image1);
        accordion.addPanel("Blue Flower", image2);
        accordion.addPanel("Yellow Flower", image3);
        
        accordion.addStyleName(STYLE_ACCORDION_PANEL);

        main.add(label);
        main.add(accordion);
        
        super.initWidget(main);
    }


}
