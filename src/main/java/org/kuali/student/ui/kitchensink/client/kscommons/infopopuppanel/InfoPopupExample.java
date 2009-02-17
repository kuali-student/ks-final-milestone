package org.kuali.student.ui.kitchensink.client.kscommons.infopopuppanel;

import org.kuali.student.core.ui.client.widgets.KSButton;
import org.kuali.student.core.ui.client.widgets.KSInfoPopupPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class InfoPopupExample extends Composite {


    final SimplePanel panel = new SimplePanel();
    KSInfoPopupPanel ksInfoPopup = new KSInfoPopupPanel();
    final KSButton showButton = new KSButton("Click to see Popup");
    final KSButton hideButton = new KSButton("Click to close Popup");


    public InfoPopupExample() {

        ksInfoPopup.add(new Image("images/flowers.jpg") );
        ksInfoPopup.add(hideButton);
        
        hideButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                ksInfoPopup.hide();
                
            }});
        
        showButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                ksInfoPopup.show();

            }});
        panel.add(showButton);



        super.initWidget(panel);
    }


}
