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
 	
 	                HTMLPanel title = new HTMLPanel("<h3>Kuali Reference University</h3>");
 	                title.addStyleName("ks-header-container");
 	                title.addStyleName("ks-header");
 	                rootPanel.add(title, 0, 0);
 	                title.setSize("689px", "16px");
 	
 	                Label CLUTitle = new Label("Introduction to Geology that is a very long title about specific things");
 	                rootPanel.add(CLUTitle, 21, 76);
 	                CLUTitle.setSize("441px", "13px");
 	
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
 	                reviewPanel.add(new HTML("Proposal History"));
 	                HTML html = new HTML("View changes for individual fields in the change log. Submit proposal using Workflow Actions menu above.");
 	                html.setHeight("37px");
 	                reviewPanel.add(html);
 	
 	                Hyperlink hyperlink = new Hyperlink("proposal History", false, "newProposalHistoryToken");
 	                reviewPanel.add(hyperlink);
 	
 	
 	                // Version Table
 	                FlexTable versionTable = new FlexTable();
 	                versionTable.setText(0, 0, "Version");
 	                versionTable.setText(0, 1, "Date");
 	                versionTable.setText(0, 2, "Change By");
 	                versionTable.setText(1, 0, "Current Working Draft");
 	                versionTable.setText(1, 1, "january 7, 2010");
 	                versionTable.setText(1, 2, "Julia Davinchi");
 	                versionTable.setText(2, 0, "Changes By Geographic Dept");
 	                versionTable.setText(2, 1, "October 5, 2009");
 	                versionTable.setText(2, 2, "Martha Lee");
 	
 	                reviewPanel.add(versionTable);
 	                versionTable.setSize("564px", "125px");
 	
 	
 	                // Add the Panels
 	                TabPanel tabPanel = new TabPanel();

                        tabPanel.addStyleName("ks-page-navigation");

 	                // tabPanel.setSize("649px", "214px");
 	                tabPanel.add(editPanel, "Edit Proposal");
 	                tabPanel.add(reviewPanel, "Review Proposal");
 	                
 	
 	                tabPanel.add(new HTML("Supporting Documents"), "Supporting Documents");
 	                tabPanel.selectTab(2);
 	
 	
 	                // Add the Tab Panel to Root
 	                rootPanel.add(tabPanel, 19, 179);
 	
 	
 	
 	
 	
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