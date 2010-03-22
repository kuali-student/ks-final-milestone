/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.student.client;



import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.PopupPanel;


/**
 *
 * @author simstu
 */
public class CompareProposals {
    
    private Button compareButton;

    private void createCloseButton() {
        // TODO: Handler to launch the Compare Popup
        // Randomn Button Handler Event


        // Compare Button
        compareButton = new Button();

        compareButton.setText("Compare Versions");
        // rootPanel.add(compareButton, 29, 507);

        compareButton.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                        Window.alert("Popup for compare...!");
                }
        });


    }

}
