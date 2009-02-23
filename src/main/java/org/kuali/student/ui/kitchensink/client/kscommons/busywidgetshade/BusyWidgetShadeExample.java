package org.kuali.student.ui.kitchensink.client.kscommons.busywidgetshade;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BusyWidgetShadeExample extends Composite {
    final VerticalPanel main = new VerticalPanel();
    final TextBox myTextBox = new TextBox();
    final Button myButton = new Button("Click me!", new ClickListener() {
        public void onClick(Widget sender) {
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
