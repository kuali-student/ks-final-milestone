package org.kuali.student.ui.kitchensink.client.kscommons.dialogpanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSImage;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DialogPanelExample extends Composite {


    final SimplePanel main = new SimplePanel();

    private final VerticalPanel containerPanel = new VerticalPanel();
    final KSDialogPanel dialog = new KSDialogPanel();

    final KSButton showButton = new KSButton("Click to see Popup");
    final KSButton hideButton = new KSButton("Click to close Popup");
    private KSImage image = new KSImage("images/flower2.jpg");


    public DialogPanelExample() {

        main.addStyleName(STYLE_EXAMPLE);

        showButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                dialog.show();
                dialog.center();
            }});

        hideButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                dialog.hide();

            }});

        containerPanel.add(new KSImage("images/flower2.jpg") );
        containerPanel.add(hideButton);

        dialog.setHeader("Dialog Panel");
        dialog.setWidget(containerPanel);
        dialog.setResizable(false);

        main.add(showButton);

        super.initWidget(main);


    }


}
