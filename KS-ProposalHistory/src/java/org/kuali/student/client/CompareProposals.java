/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.student.client;



import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 *
 * @author simstu
 */
public class CompareProposals extends PopupPanel {
    
    private Button closeButton;
    private VerticalPanel contents;

    CompareProposals() {

        super(false);
        contents = new VerticalPanel();


    }
    
    private void createCloseButton() {
        // TODO: Handler to launch the Compare Popup
        // Randomn Button Handler Event


        // Compare Button
        closeButton = new Button();

        closeButton.setText("Compare Versions");
        // rootPanel.add(compareButton, 29, 507);

        closeButton.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                        Window.alert("Popup for compare...!");
                }
        });


    }

}
