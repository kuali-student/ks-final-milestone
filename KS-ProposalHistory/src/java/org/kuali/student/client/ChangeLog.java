/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.student.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 *
 * @author simstu
 */
public class ChangeLog extends PopupPanel {

    private Button closeButton;
    
    // default Constructor
    ChangeLog() {

        this.add(createVersionsTable());

    }

    private void addHeaderText() {

        // Breadcrumbs...

        // Panel Title
        HTML panelTitle = new HTML("<h2>Change Log</h2>");
        panelTitle.addStyleName("ks-heading-page-title");
        this.add(panelTitle);


        HTML introHtml = new HTML("This list shows the changes made to the Proposal Histry Attribute.");
        introHtml.setHeight("40px");
        this.add(introHtml);

    }

    public Widget createVersionsTable() {

        // Version Table
        FlexTable versionTable = new FlexTable();

        // Setup the Headers
        versionTable.getColumnFormatter().setWidth(0, "200px");
        versionTable.getColumnFormatter().setWidth(1, "100px");
        versionTable.getColumnFormatter().setWidth(2, "100px");
        versionTable.getColumnFormatter().setWidth(3, "100px");

        Label titleHeaderLabel = new Label("Attribute");
        titleHeaderLabel.setHorizontalAlignment(Label.ALIGN_CENTER);

        // titleHeaderLabel.setStyleName("ks-table th");
        versionTable.getRowFormatter().addStyleName(0, "ks-table-th");

        versionTable.setWidget(0, 0,titleHeaderLabel);
        Label nameHeaderLabel = new Label("Value");
        nameHeaderLabel.setHorizontalAlignment(Label.ALIGN_CENTER);
        versionTable.setWidget(0, 1,nameHeaderLabel);
        Label dateHeaderLabel = new Label("Last Updated");
        dateHeaderLabel.setHorizontalAlignment(Label.ALIGN_CENTER);
        versionTable.setWidget(0, 2,dateHeaderLabel);
        Label changeHeaderLabel = new Label("Owner");
        changeHeaderLabel.setHorizontalAlignment(Label.ALIGN_CENTER);
        versionTable.setWidget(0, 3,changeHeaderLabel);



        versionTable.addStyleName("ks-table-container");
        versionTable.addStyleName("ks-table");

        // Populate the Table

        // Governance Sub Section
        versionTable.setText(1, 0, "Change History");
        versionTable.setText(1, 1, "");
        versionTable.setText(1, 2, "");
        versionTable.setText(1, 3, "");
        versionTable.getRowFormatter().addStyleName(1, "td-subhead");


        versionTable.setText(2, 0, "Title");
        versionTable.setText(2, 1, "Biology 101");
        versionTable.setText(2, 2, "March 18, 2010");
        versionTable.setText(2, 3, "Stuart Sim");

        versionTable.setText(3, 0, "Title");
        versionTable.setText(3, 1, "Inroduction to Biology");
        versionTable.setText(3, 2, "March 17, 2010");
        versionTable.setText(3, 3, "Tom Barnes");

        versionTable.setText(4, 0, "Title");
        versionTable.setText(4, 1, "Biology for Beginners");
        versionTable.setText(4, 2, "March 14, 2010");
        versionTable.setText(4, 3, "Stuart Sim");


        

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
}
