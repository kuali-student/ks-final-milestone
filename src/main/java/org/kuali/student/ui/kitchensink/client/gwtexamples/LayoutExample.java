package org.kuali.student.ui.kitchensink.client.gwtexamples;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class LayoutExample  extends Composite {
    final FlowPanel myContainer = new FlowPanel();
    final SimplePanel panel1 = new SimplePanel();
    final SimplePanel panel2 = new SimplePanel();
    public LayoutExample() {
        super.initWidget(myContainer);
        
        panel1.setStyleName("panel1");
        panel2.setStyleName("panel2");
        
        myContainer.add(panel1);
        myContainer.add(panel2);
    }
    protected void onLoad() {
        myContainer.setStyleName("myContainer");
    }
}
