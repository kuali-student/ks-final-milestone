package org.kuali.student.ui.kitchensink.client.kscommons.button;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.images.KSImages;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ButtonExample extends Composite {

    final SimplePanel main = new SimplePanel();
    VerticalPanel buttonPanel = new VerticalPanel();
    final KSLabel title = new KSLabel("Click a button: - ", false);
    
    final KSButton button1 = new KSButton("Default Button",new MyClickHandler());
    final KSButton imageButton = new KSButton("Image Button",new MyClickHandler());

    public ButtonExample() {
        
        buttonPanel.add(title);
        buttonPanel.add(button1);
        
        imageButton.setImage(KSImages.INSTANCE.okIcon().createImage(), KSButton.ButtonImageAlign.RIGHT);
        buttonPanel.add(imageButton);
        
        buttonPanel.addStyleName(STYLE_EXAMPLE);

        main.add(buttonPanel);

        super.initWidget(main);
    }
    
    public class MyClickHandler implements ClickHandler  {

        @Override
        public void onClick(ClickEvent event) {           
            Button b = (Button)(event.getSource());           
            Window.alert("You clicked: " + b.getText());            
        }     
    }
}
