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
public class ProposalHistory extends PopupPanel {

    private Button closeButton;

    ProposalHistory() {

        createVersionsTable();
        this.add(closeButton);

    }

    public Widget createVersionsTable() {

        // Version Table
        FlexTable versionTable = new FlexTable();

        // Setup the Headers
        versionTable.getColumnFormatter().setWidth(0, "200px");
        versionTable.getColumnFormatter().setWidth(1, "100px");
        versionTable.getColumnFormatter().setWidth(2, "100px");
        versionTable.getColumnFormatter().setWidth(3, "100px");

        Label titleHeaderLabel = new Label("Title");
        titleHeaderLabel.setHorizontalAlignment(Label.ALIGN_CENTER);

        // titleHeaderLabel.setStyleName("ks-table th");
        versionTable.getRowFormatter().addStyleName(0, "ks-table-th");

        versionTable.setWidget(0, 0,titleHeaderLabel);
        Label nameHeaderLabel = new Label("Name");
        nameHeaderLabel.setHorizontalAlignment(Label.ALIGN_CENTER);
        versionTable.setWidget(0, 1,nameHeaderLabel);
        Label dateHeaderLabel = new Label("Last Updated");
        dateHeaderLabel.setHorizontalAlignment(Label.ALIGN_CENTER);
        versionTable.setWidget(0, 2,dateHeaderLabel);
        Label changeHeaderLabel = new Label("Change Log");
        changeHeaderLabel.setHorizontalAlignment(Label.ALIGN_CENTER);
        versionTable.setWidget(0, 3,changeHeaderLabel);



        versionTable.addStyleName("ks-table-container");
        versionTable.addStyleName("ks-table");

        // Populate the Table

        // Governance Sub Section
        versionTable.setText(1, 0, "Governance");
        versionTable.setText(1, 1, "");
        versionTable.setText(1, 2, "");
        versionTable.setText(1, 3, "");
        versionTable.getRowFormatter().addStyleName(1, "td-subhead");


        versionTable.setText(2, 0, "Curriculum Oversight");
        versionTable.setText(2, 1, "March 18, 2010");
        versionTable.setText(2, 2, "Stuart Sim");
        versionTable.setText(2, 3, "");

        versionTable.setText(3, 0, "Campus Locations");
        versionTable.setText(3, 1, "March 17, 2010");
        versionTable.setText(3, 2, "Stuart Sim");
        versionTable.setText(3, 3, "");

        versionTable.setText(4, 0, "Administering Organization");
        versionTable.setText(4, 1, "March 14, 2010");
        versionTable.setText(4, 2, "Stuart Sim");
        versionTable.setText(4, 3, "");


        // Course Logistics Section

        // Governance Sub Section
        versionTable.setText(5, 0, "Course Logistics");
        versionTable.setText(5, 1, "");
        versionTable.setText(5, 2, "");
        versionTable.setText(5, 3, "");
        versionTable.getRowFormatter().addStyleName(1, "td-subhead"); // BROKEN - css not being picked up?


        versionTable.setText(6, 0, "First Expected Offering");
        versionTable.setText(6, 1, "March 4, 2010");
        versionTable.setText(6, 2, "Tim Duck");
        versionTable.setText(6, 3, "");

        versionTable.setText(7, 0, "Instructor");
        versionTable.setText(7, 1, "March 4, 2010");
        versionTable.setText(7, 2, "Stuart Sim");
        versionTable.setText(7, 3, "");

        versionTable.setText(8, 0, "Scheduling Duration");
        versionTable.setText(8, 1, "March 3, 2010");
        versionTable.setText(8, 2, "Claire Barnes");
        versionTable.setText(8, 3, "");

        versionTable.setText(9, 0, "Scheduling Duration Type");
        versionTable.setText(9, 1, "March 3, 2010");
        versionTable.setText(9, 2, "Claire Barnes");
        versionTable.setText(9, 3, "");

        versionTable.setText(10, 0, "Course Format 1");
        versionTable.setText(10, 1, "March 5, 2010");
        versionTable.setText(10, 2, "Bob Smith");
        versionTable.setText(10, 3, "");

        versionTable.setText(11, 0, "Course Activity 1");
        versionTable.setText(11, 1, "March 2, 2010");
        versionTable.setText(11, 2, "Bob Smith");
        versionTable.setText(11, 3, "");

        // Design Note TODO
        // The above will be replaced by a table builder for
        // the Proposal and Clu Info Structures
        // i.e. add Section(sectionName), addRow(section, rowdata)

        versionTable.setSize("560px", "200px");

        return versionTable;

    }


    private void createCloseButton() {
        // TODO: Handler to launch the Compare Popup
        // Randomn Button Handler Event


        // Compare Button
        closeButton = new Button();

        closeButton.setText("Close this Window");
        // rootPanel.add(compareButton, 29, 507);

        closeButton.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                        hide();
                }
        });


    }


//        public void demo()
//        {
//        // Create a panel and add it to the screen
//        panel = new VerticalPanel();
//        RootPanel.get("demo").add(panel);
//        panel.setStyleName("table-center");
//        //
//        // Create a PopUpPanel with a button to close it
//        popup = new PopUpPanel(false);
//        popup.setStyleName("demo-PopUpPanel");
//        PopUpPanelContents = new VerticalPanel();
//        popup.setText("PopUpPanel");
//        message = new HTML("Click 'Close' to close");
//        message.setStyleName("demo-PopUpPanel-message");
//        listener = new ClickListener()
//        {
//            public void onClick(Widget sender)
//            {
//                popup.hide();
//            }
//        };
//        button = new Button("Close", listener);
//        holder = new SimplePanel();
//        holder.add(button);
//        holder.setStyleName("demo-PopUpPanel-footer");
//        PopUpPanelContents.add(message);
//        PopUpPanelContents.add(holder);
//        popup.setWidget(PopUpPanelContents);
//        //
//        // Add a button to the demo to show the above PopUpPanel
//        listener = new ClickListener()
//        {
//            public void onClick(Widget sender)
//            {
//                popup.center();
//            }
//        };
//        button = new Button("Show PopUpPanel", listener);
//        panel.add(button);
//    }


}
