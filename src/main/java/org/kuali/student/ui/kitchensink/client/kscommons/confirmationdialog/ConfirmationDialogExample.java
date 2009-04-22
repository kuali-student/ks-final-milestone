package org.kuali.student.ui.kitchensink.client.kscommons.confirmationdialog;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ConfirmationDialogExample extends Composite {


    final SimplePanel main = new SimplePanel();

    private final VerticalPanel containerPanel = new VerticalPanel();
    final KSConfirmationDialog confPopup = new KSConfirmationDialog();

    final KSButton showButton = new KSButton("Click to see Dialog");

    private KSLabel question = new KSLabel("\nAre you sure you want to do that?\n\n");


    public ConfirmationDialogExample() {

        main.addStyleName(STYLE_EXAMPLE);

        showButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                confPopup.show();

            }});



        containerPanel.add(question );

        confPopup.setWidget(containerPanel);
        confPopup.setMessageTitle("Confirmation Dialog");
        confPopup.addConfirmHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                Window.alert("Action has been confirmed");
                confPopup.hide();
                
            }});


        main.add(showButton);

        super.initWidget(main);


    }


}
