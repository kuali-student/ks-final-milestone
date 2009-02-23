package org.kuali.student.ui.kitchensink.client.kscommons.radiobutton;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSRadioButton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RadioButtonExample extends Composite {


    private static final String PICK_ME = "Pick me";
    private static final String GROUP1 = "Group1";  
    private static final String GROUP2 = "Group2";


    final VerticalPanel main = new VerticalPanel();
    
    final CaptionPanel captionPanel1 = new CaptionPanel("Pick one from this group....");
    final CaptionPanel captionPanel2 = new CaptionPanel(".... And one from this group");
    
    final  VerticalPanel group1Panel = new VerticalPanel();
    final  HorizontalPanel group2Panel = new HorizontalPanel();
    

    final KSRadioButton ksRadio1 = GWT.create( KSRadioButton.class);
    final KSRadioButton ksRadio2 = GWT.create( KSRadioButton.class);
    final KSRadioButton ksRadio3 = GWT.create( KSRadioButton.class);
    final KSRadioButton ksRadio4 = GWT.create( KSRadioButton.class);
    
    final KSRadioButton ksRadio5 = GWT.create( KSRadioButton.class);
    final KSRadioButton ksRadio6 = GWT.create( KSRadioButton.class);
    final KSRadioButton ksRadio7 = GWT.create( KSRadioButton.class);

    public RadioButtonExample() {

        main.addStyleName(STYLE_EXAMPLE);

        initButtons();

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

    private void initButtons() {
        ksRadio1.init(GROUP1, PICK_ME, false);
        ksRadio2.init(GROUP1, PICK_ME, false);
        ksRadio3.init(GROUP1, PICK_ME, false);
        ksRadio4.init(GROUP1, PICK_ME, false);
        
        ksRadio5.init(GROUP2, PICK_ME, false);
        ksRadio6.init(GROUP2, PICK_ME, false);
        ksRadio7.init(GROUP2, PICK_ME, false);


    }

}
