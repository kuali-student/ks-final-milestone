package org.kuali.student.ui.kitchensink.client.kscommons.radiobutton;

import org.kuali.student.core.ui.client.widgets.KSRadioButton;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RadioButtonExample extends Composite {


    final VerticalPanel main = new VerticalPanel();
    
    final CaptionPanel captionPanel1 = new CaptionPanel("Pick one from this group");
    final CaptionPanel captionPanel2 = new CaptionPanel(".... And one from this group");
    
    final  VerticalPanel group1Panel = new VerticalPanel();
    final  HorizontalPanel group2Panel = new HorizontalPanel();

    final KSRadioButton ksRadio1 = new KSRadioButton("Group1", "Pick me");
    final KSRadioButton ksRadio2 = new KSRadioButton("Group1", "Pick me");
    final KSRadioButton ksRadio3 = new KSRadioButton("Group1", "Pick me");
    final KSRadioButton ksRadio4 = new KSRadioButton("Group1", "Pick me");
    
    final KSRadioButton ksRadio5 = new KSRadioButton("Group2", "Pick me");
    final KSRadioButton ksRadio6 = new KSRadioButton("Group2", "Pick me");
    final KSRadioButton ksRadio7 = new KSRadioButton("Group2", "Pick me");

    public RadioButtonExample() {

        group1Panel.add(ksRadio1); 
        group1Panel.add(ksRadio2);
        group1Panel.add(ksRadio3);
        group1Panel.add(ksRadio4); 
        captionPanel1.add(group1Panel);

        group2Panel.add(ksRadio5);
        group2Panel.add(ksRadio6);
        group2Panel.add(ksRadio7);
        captionPanel2.add(group2Panel);
        
        
        main.add(captionPanel1);
        main.add(captionPanel2);
         
        super.initWidget(main);
    }


}
