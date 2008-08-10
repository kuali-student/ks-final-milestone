package org.kuali.student.ui.kitchensink.client.kscommons.busywidgetshade;

import org.kuali.student.commons.ui.widgets.BusyWidgetShade;

import com.google.gwt.user.client.Timer;
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
            // shade the entire panel on which this button sits
            BusyWidgetShade.shade(panel);
            
            // now simulate work being done asynchronously, with a callback to unshade the panel
            Timer t = new Timer() {
                public void run() {
                    // ok, work is completed, now unshade the panel
                    BusyWidgetShade.unshade(panel);
                }
            };
            
            // simulate work for 5 seconds
            t.schedule(5000);
        }
    });
    
    public BusyWidgetShadeExample() {
        panel.add(myTextBox);
        panel.add(myButton);
        super.initWidget(panel);
    }
}
