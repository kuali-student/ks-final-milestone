package org.kuali.student.ui.kitchensink.client.kscommons.modalpopuppanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_POPUP_PANEL;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSModalPopupPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class ModalPopupPanelExample extends Composite {


    final SimplePanel main = new SimplePanel();

    final KSModalPopupPanel popupPanel = new KSModalPopupPanel();   
    final KSButton showButton ;
    final KSButton hideButton ;


    public ModalPopupPanelExample() {
        main.addStyleName(STYLE_EXAMPLE);

        showButton = new KSButton("Click to see Popup", new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                popupPanel.show();
                
            }});
        hideButton = new KSButton("Click to close Popup", new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                popupPanel.hide();

            }});
        
        popupPanel.addHeader("Modal Popup Panel");
        popupPanel.add(new Image("images/flower1.jpg") );
        popupPanel.add(hideButton);
        popupPanel.addStyleName(STYLE_POPUP_PANEL);

        main.add(showButton);

        super.initWidget(main);
    }


}
