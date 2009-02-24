package org.kuali.student.ui.kitchensink.client.kscommons.infopopuppanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_POPUP_PANEL;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSInfoPopupPanel;
import org.kuali.student.ui.kitchensink.client.kscommons.KSWidgetFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class InfoPopupExample extends Composite {


    final SimplePanel main = new SimplePanel();

    final KSInfoPopupPanel infoPopup = KSWidgetFactory.getInfoPopupPanelInstance();
    
    final KSButton showButton = new KSButton("Click to see Popup");
    final KSButton hideButton = new KSButton("Click to close Popup");


    public InfoPopupExample() {

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


        try {
            infoPopup.addHeader("Info Popup Panel");
            infoPopup.add(new Image("images/flower2.jpg") );
            infoPopup.addStyleName(STYLE_POPUP_PANEL);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        infoPopup.add(hideButton);
        main.add(showButton);



        super.initWidget(main);
       

    }


}
