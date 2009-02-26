package org.kuali.student.ui.kitchensink.client.kscommons.stackpanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStackPanel;
import org.kuali.student.ui.kitchensink.client.kscommons.KSWidgetFactory;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StackPanelExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    final KSStackPanel stackPanel = KSWidgetFactory.getStackPanelInstance();
    final KSLabel label = KSWidgetFactory.getLabelInstance("Click in the box to open the stack: ", false);

    public StackPanelExample() {

        main.addStyleName(STYLE_EXAMPLE);
        
        try {
            stackPanel.getStackPanel().add(KSWidgetFactory.getImageInstance("images/flower1.jpg"), "Orange");
//            stackPanel.getStackPanel().add(new Image("images/flower2.jpg"), "Blue");
//            stackPanel.getStackPanel().add(new Image("images/flower3.jpg"), "Yellow");
        } catch (Exception e) {
            Window.alert("Error" + e.toString());
        }
        main.add(stackPanel);
        super.initWidget(main);
    }

}
