package org.kuali.student.ui.kitchensink.client.kscommons.resizablepanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSPopupPanel;
import org.kuali.student.common.ui.client.widgets.impl.KSResizablePanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ResizablePanelExample extends Composite {

    private final VerticalPanel main = new VerticalPanel();

    private final KSLabel description = new KSLabel("Click the button to see the panel then click anywhere outside the panel to close it.\n\n");
    private final KSPopupPanel popup = new KSPopupPanel();
    private final KSResizablePanelImpl resizableContent = GWT.create(KSResizablePanelImpl.class);
    private final KSImage image = new KSImage("images/flower1.jpg") ;

    private KSButton showButton ;

    public ResizablePanelExample() {
        main.addStyleName(STYLE_EXAMPLE);

        showButton = new KSButton("Click to see Panel", new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                resizableContent.setWidget(image);
                popup.setAutoHide(true);
                popup.setWidget(resizableContent);
                popup.center();
                popup.show();

            }});

        popup.addCloseHandler(new CloseHandler<KSPopupPanel>() {

            @Override
            public void onClose(CloseEvent<KSPopupPanel> arg0) {
                resizableContent.remove(image);

            }});
        
        main.add(description);
        main.add(showButton);

        super.initWidget(main);
    }

}
