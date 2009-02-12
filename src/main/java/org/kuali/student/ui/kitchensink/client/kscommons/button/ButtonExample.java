package org.kuali.student.ui.kitchensink.client.kscommons.button;

import org.kuali.student.core.ui.client.widgets.KSButton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ButtonExample extends Composite {

    final HorizontalPanel panel = new HorizontalPanel();
    final KSButton ksButton = 
        new KSButton("Click Me", 
                new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("Button has been clicked");

            }});

    public ButtonExample() {
        panel.addStyleName("ksButton");
        panel.add(ksButton);
        super.initWidget(panel);
    }
}
