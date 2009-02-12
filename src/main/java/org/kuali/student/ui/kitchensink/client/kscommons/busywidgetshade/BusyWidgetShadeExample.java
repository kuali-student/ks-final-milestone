package org.kuali.student.ui.kitchensink.client.kscommons.busywidgetshade;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BusyWidgetShadeExample extends Composite {
    final VerticalPanel panel = new VerticalPanel();
    final TextBox myTextBox = new TextBox();
    final Button myButton = new Button("Click me!", new ClickListener() {
        public void onClick(Widget sender) {
            Window.alert("widget shade temporarily disabled");
        }
    });
    
    public BusyWidgetShadeExample() {
        panel.add(myTextBox);
        panel.add(myButton);
        super.initWidget(panel);
    }
}
