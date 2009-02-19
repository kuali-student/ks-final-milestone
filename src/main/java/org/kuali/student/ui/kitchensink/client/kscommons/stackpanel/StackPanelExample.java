package org.kuali.student.ui.kitchensink.client.kscommons.stackpanel;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStackPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StackPanelExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    final KSStackPanel stackPanel = GWT.create(KSStackPanel.class);
    final KSLabel label = GWT.create(KSLabel.class);

    public StackPanelExample() {

        label.setText("Click in the box to open the stack: ");
        stackPanel.add(new Image("images/flowers.jpg"), "Flowers1");
        stackPanel.add(new Image("images/flowers.jpg"), "Flowers2");
        stackPanel.add(new Image("images/flowers.jpg"), "Flowers3");
        main.add(stackPanel);
        super.initWidget(main);
    }

}
