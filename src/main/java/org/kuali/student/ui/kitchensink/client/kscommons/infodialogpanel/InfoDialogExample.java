package org.kuali.student.ui.kitchensink.client.kscommons.infodialogpanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_IMAGE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_INFO_DIALOG_PANEL;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSInfoDialogPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class InfoDialogExample extends Composite {


    final SimplePanel main = new SimplePanel();

    private final VerticalPanel containerPanel = new VerticalPanel();
    final KSInfoDialogPanel infoPopup = new KSInfoDialogPanel();

    final KSButton showButton = new KSButton("Click to see Popup");
    final KSButton hideButton = new KSButton("Click to close Popup");
    private KSImage image = new KSImage("images/flower3.jpg");


    public InfoDialogExample() {

        main.addStyleName(STYLE_EXAMPLE);


        showButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                infoPopup.show();

            }});

        hideButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                infoPopup.hide();

            }});



        image.addStyleName(STYLE_IMAGE);
        containerPanel.add(new KSImage("images/flower3.jpg") );
        containerPanel.addStyleName(STYLE_INFO_DIALOG_PANEL);
        containerPanel.add(hideButton);

        infoPopup.setHeader("Info Dialog Panel");
        infoPopup.setWidget(containerPanel);

        main.add(showButton);

        super.initWidget(main);


    }


}
