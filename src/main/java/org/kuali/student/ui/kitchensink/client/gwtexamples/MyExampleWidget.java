package org.kuali.student.ui.kitchensink.client.gwtexamples;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class MyExampleWidget extends Composite {
    final Label myLabel = new Label("Hello, World!");
    public MyExampleWidget() {
        super.initWidget(myLabel);
    }
    protected void onLoad() {
        myLabel.setStyleName("MyLabel");
    }
}
