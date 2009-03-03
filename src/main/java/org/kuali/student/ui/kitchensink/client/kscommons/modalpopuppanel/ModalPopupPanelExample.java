package org.kuali.student.ui.kitchensink.client.kscommons.modalpopuppanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_INFO_DIALOG_PANEL;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ModalPopupPanelExample extends Composite {


    final SimplePanel main = new SimplePanel();

    final KSModalDialogPanel popupPanel = new KSModalDialogPanel();   
    
    private final VerticalPanel containerPanel = new VerticalPanel();
    KSButton showButton ;
    KSButton hideButton ;


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
        
//        containerPanel.add("Modal Popup Panel");
        containerPanel.add(new Image("images/flower1.jpg") );
        containerPanel.add(hideButton);
        popupPanel.setWidget(containerPanel);
        popupPanel.addStyleName(STYLE_INFO_DIALOG_PANEL);
        popupPanel.setModal(true);

        main.add(showButton);

        super.initWidget(main);
    }


}
