package org.kuali.student.ui.kitchensink.client.gwtexamples;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class TestExample extends KitchenSinkExample {

    public TestExample() {
        super();
        super.addResource("java", "MyExampleWidget.java", "gwtexamples/MyExampleWidget.java", "Example widget using a label wrapped in a composite.");
        super.addResource("css", "MyExampleWidget.css", "examplecss/MyExampleWidget.css", "CSS used to style MyExampleWidget.");
    }

    public String getDescription() {
        return "This is a test of the kitchen sink example framework";
    }

    public Widget getExampleWidget() {
        return new MyExampleWidget();
    }

    
    public String getTitle() {
        return "Test Example";
    }
    
}
