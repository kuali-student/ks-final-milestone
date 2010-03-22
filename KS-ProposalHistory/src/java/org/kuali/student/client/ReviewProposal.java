/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.student.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


/**
 *
 * @author simstu
 */
public class ReviewProposal extends FlowPanel {


    ReviewProposal() {
        
        addHeaderText();
        this.add(createReviewTable());

        // Testing
        // showChangeLog();
        
    }


    private void addHeaderText() {

        // Breadcrumbs...

        // Panel Title
        HTML panelTitle = new HTML("<h2>Review Proposal</h2>");
        panelTitle.addStyleName("ks-heading-page-title");
        this.add(panelTitle);


        HTML introHtml = new HTML("View changes for individual fields in the change log. Submit proposal using the Workflow Actions menu above.");
        introHtml.setHeight("40px");
        this.add(introHtml);

        // Hyperlink hyperlink = new Hyperlink("Proposal History", false, "newProposalHistoryToken");
        // this.add(hyperlink);

        // Add a Button for Proposal History
        Button proposalHistoryButton = new Button();
        proposalHistoryButton.setText("Proposal History");

        proposalHistoryButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {

                showProposalHistory();

            }
        });


  
        this.add(proposalHistoryButton);

        // Put in the event handler here...
        // ChangeLog changeLog = new ChangeLog();
        // changeLog.setModal(true);
        // changeLog.setAutoHideEnabled(true);
        // changeLog.show();

    }



    private Widget createReviewTable() {

        // Version Table
        FlexTable versionTable = new FlexTable();

        // Setup the Headers
        versionTable.getColumnFormatter().setWidth(0, "200px");
        versionTable.getColumnFormatter().setWidth(1, "100px");
        versionTable.getColumnFormatter().setWidth(2, "100px");
        versionTable.getColumnFormatter().setWidth(3, "100px");

        Label titleHeaderLabel = new Label("");
        titleHeaderLabel.setHorizontalAlignment(Label.ALIGN_CENTER);

        // titleHeaderLabel.setStyleName("ks-table th");
        versionTable.getRowFormatter().addStyleName(0, "ks-table-th");

        versionTable.setWidget(0, 0,titleHeaderLabel);
        Label nameHeaderLabel = new Label("Last Updated");
        nameHeaderLabel.setHorizontalAlignment(Label.ALIGN_CENTER);
        versionTable.setWidget(0, 1,nameHeaderLabel);
        Label dateHeaderLabel = new Label("Owner");
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


        // How do we add widgets to the cells?

        versionTable.setText(2, 0, "Curriculum Oversight");
        versionTable.setText(2, 1, "March 18, 2010");
        versionTable.setText(2, 2, "Stuart Sim");
        versionTable.setWidget(2, 3, new ChangeLogButton());

        versionTable.setText(3, 0, "Campus Locations");
        versionTable.setText(3, 1, "March 17, 2010");
        versionTable.setText(3, 2, "Stuart Sim");
        versionTable.setWidget(3, 3, new ChangeLogButton());

        versionTable.setText(4, 0, "Administering Organization");
        versionTable.setText(4, 1, "March 14, 2010");
        versionTable.setText(4, 2, "Stuart Sim");
        versionTable.setWidget(4, 3, new ChangeLogButton());


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
        versionTable.setWidget(6, 3, new ChangeLogButton());

        versionTable.setText(7, 0, "Instructor");
        versionTable.setText(7, 1, "March 4, 2010");
        versionTable.setText(7, 2, "Stuart Sim");
        versionTable.setWidget(7, 3, new ChangeLogButton());

        versionTable.setText(8, 0, "Scheduling Duration");
        versionTable.setText(8, 1, "March 3, 2010");
        versionTable.setText(8, 2, "Claire Barnes");
        versionTable.setWidget(8, 3, new ChangeLogButton());

        versionTable.setText(9, 0, "Scheduling Duration Type");
        versionTable.setText(9, 1, "March 3, 2010");
        versionTable.setText(9, 2, "Claire Barnes");
        versionTable.setWidget(9, 3, new ChangeLogButton());

        versionTable.setText(10, 0, "Course Format 1");
        versionTable.setText(10, 1, "March 5, 2010");
        versionTable.setText(10, 2, "Bob Smith");
        versionTable.setWidget(10, 3, new ChangeLogButton());

        versionTable.setText(11, 0, "Course Activity 1");
        versionTable.setText(11, 1, "March 2, 2010");
        versionTable.setText(11, 2, "Bob Smith");
        versionTable.setWidget(11, 3, new ChangeLogButton());




        // Design Note TODO
        // The above will be replaced by a table builder for
        // the Proposal and Clu Info Structures
        // i.e. add Section(sectionName), addRow(section, rowdata)


        // versionTable.setSize("560px", "200px");




        return versionTable;



    }


    public void showProposalHistory() {

        ProposalHistory proposalHistory = new ProposalHistory();
        proposalHistory.setModal(true);
        proposalHistory.setAutoHideEnabled(false);
        
        proposalHistory.center();
        proposalHistory.show();
    }

    public void showChangeLog() {
        ChangeLog changeLog = new ChangeLog();
        changeLog.setModal(true);
        changeLog.setAutoHideEnabled(false);
        
        changeLog.center();
        changeLog.show();
   
    }


    // This is a button right now but it could be a graphic
    public class ChangeLogButton extends Button {

        ChangeLogButton() {

            this.setText("Change Log");

            this.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {

                    showChangeLog();
                }
        });

        }


    }

}
