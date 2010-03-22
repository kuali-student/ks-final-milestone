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
 	   
            public void onModuleLoad() {


                RootPanel rootPanel = RootPanel.get();

                rootPanel.addStyleName("body");
                rootPanel.addStyleName("ks-page-container");

                HTMLPanel title = new HTMLPanel("<h3>Kuali Reference University</h3> build March 22 2010");

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
                workflowActionsCombo.addItem("Check Inbox");
                workflowActionsCombo.addItem("Add Comment");

                ListBox proposalActionsComboBox = new ListBox();
                rootPanel.add(proposalActionsComboBox, 193, 105);
                proposalActionsComboBox.setSize("97px", "22px");
                proposalActionsComboBox.addItem("Review Proposal");
                //
                // Main Tab Panel
                // Replacement KS Widget:

                FlowPanel editPanel;
                FlowPanel reviewPanel;

                // Edit Proposal Main Tab
                editPanel = new FlowPanel();
                editPanel.add(new HTML("Existing M4 Edit Proposal Screen Here..."));


                // Review Proposal Main Tab
                reviewPanel = new ReviewProposal();

                


                // Add the Panels
                TabPanel tabPanel = new TabPanel();

                // tabPanel.addStyleName("ks-page-navigation");

                // tabPanel.addStyleName("ks-page-tabs-left");
                // tabPanel.addStyleName("active-tab");

                // tabPanel.setSize("649px", "214px");
                tabPanel.add(editPanel, "   Edit Proposal   ");

                tabPanel.add(reviewPanel, "   Review Proposal   ");
                // reviewPanel.add(createVersionsTable());

                tabPanel.add(new HTML("   Supporting Documents   "), "Supporting Documents");
                tabPanel.selectTab(1);

                // Add the Tab Panel to Root
                rootPanel.add(tabPanel, 0, 179);

                // TODO: Handler to launch the Compare Popup
                // Randomn Button Handler Event


               
        }

}