package org.kuali.student.client;

/*
 	* To change this template, choose Tools | Templates
 	* and open the template in the editor.
 	*/
 	
 	
 	import com.google.gwt.core.client.EntryPoint;
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
 	
 	
 	/**
 	* Main entry point.
 	*
 	* @author simstu
 	*/
 	public class proposal implements EntryPoint {
 	   /**
 	    * Creates a new instance of studentEntryPoint
 	    */
 	   public proposal() {
 	   }
 	
 	   /**
 	    * The entry point method, called automatically by loading a module
 	    * that declares an implementing class as an entry-point
 	    */
 	   private Button compareButton;
 	        public void onModuleLoad() {
 	                RootPanel rootPanel = RootPanel.get();
 	
 	                rootPanel.addStyleName("body");
 	                rootPanel.addStyleName("ks-page-container");

 	                HTMLPanel title = new HTMLPanel("<h3>Kuali Reference University</h3> build March 19 2010");

                        title.addStyleName("ks-header-container");
 	                title.addStyleName("ks-header");
                        
 	                
 	                title.setSize("689px", "16px");
 	                rootPanel.add(title, 0, 0);

 	                Label CLUTitle = new Label("Introduction to Geology that is a very long title about specific things");
 	                rootPanel.add(CLUTitle, 21, 76);
 	                CLUTitle.setSize("441px", "13px");
 	                CLUTitle.addStyleName("ks-heading-course-title");
 	                //
 	                // Drop Down Combo Boxes
 	                // Replacement KS Widget:
 	
 	                ListBox workflowActionsCombo = new ListBox();
 	                rootPanel.add(workflowActionsCombo, 29, 105);
 	                workflowActionsCombo.setSize("97px", "22px");
 	
 	                ListBox proposalActionsComboBox = new ListBox();
 	                rootPanel.add(proposalActionsComboBox, 193, 105);
 	                proposalActionsComboBox.setSize("97px", "22px");
 	
 	                //
 	                // Main Tab Panel
 	                // Replacement KS Widget:
 	
 	                FlowPanel editPanel;
 	                FlowPanel reviewPanel;
 	
 	                // Edit Proposal Main Tab
 	                editPanel = new FlowPanel();
 	                editPanel.add(new HTML("Existing M4 Edit Proposal Screen Here..."));
 	
 	
 	                // Review Proposal Main Tab
 	                reviewPanel = new FlowPanel();
 	
 	                // Breadcrumbs...
 	
 	                // Panel Title
 	                HTML panelTitle = new HTML("<h2>Review Proposal</h2>");
 	                panelTitle.addStyleName("ks-heading-page-title");
                        reviewPanel.add(panelTitle);

                        

                        HTML introHtml = new HTML("View changes for individual fields in the change log. Submit proposal using Workflow Actions menu above.");
 	                introHtml.setHeight("40px");
 	                reviewPanel.add(introHtml);
 	
 	                Hyperlink hyperlink = new Hyperlink("proposal History", false, "newProposalHistoryToken");
 	                reviewPanel.add(hyperlink);
 	
 	
 	                
 	                // Add the Panels
 	                TabPanel tabPanel = new TabPanel();

                        // tabPanel.addStyleName("ks-page-navigation");

                        // tabPanel.addStyleName("ks-page-tabs-left");
                        // tabPanel.addStyleName("active-tab");

 	                // tabPanel.setSize("649px", "214px");
 	                tabPanel.add(editPanel, "   Edit Proposal   ");

                        tabPanel.add(reviewPanel, "   Review Proposal   ");
 	                reviewPanel.add(createVersionsTable());
 	
 	                tabPanel.add(new HTML("   Supporting Documents   "), "Supporting Documents");
 	                tabPanel.selectTab(1);




 	
 	                // Add the Tab Panel to Root
 	                rootPanel.add(tabPanel, 0, 179);
 	
 	
 	
 	
 	
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
 	
 	}