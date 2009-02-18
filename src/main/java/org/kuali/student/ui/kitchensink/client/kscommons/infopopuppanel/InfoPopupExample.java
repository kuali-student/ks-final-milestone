package org.kuali.student.ui.kitchensink.client.kscommons.infopopuppanel;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSInfoPopupPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class InfoPopupExample extends Composite {


    final SimplePanel main = new SimplePanel();

    final KSInfoPopupPanel infoPopup = GWT.create(KSInfoPopupPanel.class);
    
    final KSButton showButton = GWT.create(KSButton.class);
    final KSButton hideButton = GWT.create(KSButton.class);


    public InfoPopupExample() {

        showButton.setHTML("Click to see Popup");
        hideButton.setHTML("Click to close Popup");

        infoPopup.add(new Image("images/flowers.jpg") );
        infoPopup.add(hideButton);
        
        hideButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                infoPopup.hide();
                
            }});
        
        showButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                infoPopup.show();

            }});
        main.add(showButton);



        super.initWidget(main);
    }


}
