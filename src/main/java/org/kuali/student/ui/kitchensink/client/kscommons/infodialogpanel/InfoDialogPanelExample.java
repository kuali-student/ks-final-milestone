package org.kuali.student.ui.kitchensink.client.kscommons.infodialogpanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSInfoDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.VerticalPanel;

public class InfoDialogPanelExample extends Composite {

    private final VerticalPanel main = new VerticalPanel();

    private final KSLabel description = new KSLabel("Click the button to see the panel then click anywhere outside the panel to close it.\n\n");
    private final KSInfoDialogPanel dialog = new KSInfoDialogPanel();
    private final KSButton showButton = new KSButton("Click to see Popup");

    public InfoDialogPanelExample() {

        main.addStyleName(STYLE_EXAMPLE);

        showButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                dialog.setWidget(new Frame("http://www.google.com/"));
                dialog.center();
                dialog.setAutoHide(true);
                dialog.show();
            }});

        dialog.setHeader("Info Dialog Panel");

        main.add(description);
        main.add(showButton);

        super.initWidget(main);
    }
}
