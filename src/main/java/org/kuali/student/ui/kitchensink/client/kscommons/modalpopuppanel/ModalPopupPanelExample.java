package org.kuali.student.ui.kitchensink.client.kscommons.modalpopuppanel;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSModalPopupPanel;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class ModalPopupPanelExample extends Composite {


    final SimplePanel main = new SimplePanel();

    final KSModalPopupPanel popupPanel = GWT.create(KSModalPopupPanel.class);
    
    final KSButton showButton = GWT.create(KSButton.class);
    final KSButton hideButton = GWT.create(KSButton.class);


    public ModalPopupPanelExample() {

        showButton.setHTML("Click to see Popup");
        hideButton.setHTML("Click to close Popup");
        
        popupPanel.addHeader("Test text");
        popupPanel.add(new Image("images/flowers.jpg") );
        popupPanel.add(hideButton);
        popupPanel.addStyleName(KSStyles.KS_BLOCKING_TASK_WINDOW);

        
        hideButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                popupPanel.hide();
                
            }});
        
        showButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                popupPanel.show();

            }});
        main.add(showButton);



        super.initWidget(main);
    }


}
