package org.kuali.student.ui.kitchensink.client.kscommons.busywidgetshade;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BusyWidgetShadeExample extends Composite {
    final VerticalPanel main = new VerticalPanel();
    final TextBox myTextBox = new TextBox();
    final Button myButton = new Button("Click me!", new ClickHandler() {
        public void onClick(ClickEvent event) {
            Window.alert("widget shade temporarily disabled");
        }
    });
    
    public BusyWidgetShadeExample() {
        
        main.addStyleName(STYLE_EXAMPLE);

        main.add(myTextBox);
        main.add(myButton);
        super.initWidget(main);
    }
}
