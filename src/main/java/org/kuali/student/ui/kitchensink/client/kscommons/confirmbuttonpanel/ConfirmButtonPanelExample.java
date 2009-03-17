package org.kuali.student.ui.kitchensink.client.kscommons.confirmbuttonpanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSConfirmButtonPanel;
import org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

public class ConfirmButtonPanelExample extends Composite {


    final SimplePanel main = new SimplePanel();

    private final CaptionPanel containerPanel = new CaptionPanel("Button Panel");
    final KSConfirmButtonPanel buttonPanel = new KSConfirmButtonPanel();

    final KSButton showButton = new KSButton("Click to see Dialog");

    public ConfirmButtonPanelExample() {

        main.addStyleName(STYLE_EXAMPLE);

        buttonPanel.addConfirmHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                Window.alert("Action confirmed");

            }});

        buttonPanel.addCancelHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                Window.alert("Action cancelled");

            }});

        containerPanel.setStyleName(KitchenSinkStyleConstants.STYLE_BUTTON_PANEL);
        containerPanel.add(buttonPanel );



        main.add(containerPanel);
        super.initWidget(main);


    }


}
