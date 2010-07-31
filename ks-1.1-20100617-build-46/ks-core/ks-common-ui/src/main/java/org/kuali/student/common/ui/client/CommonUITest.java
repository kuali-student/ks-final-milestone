/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.demo.HeaderDemo;
import org.kuali.student.common.ui.client.demo.KSLightBoxDemo;
import org.kuali.student.common.ui.client.widgets.ApplicationPanel;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.table.SimpleWidgetTable;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CommonUITest implements EntryPoint {
	   
    @Override
    public void onModuleLoad() {
		final AbsolutePanel panel = ApplicationPanel.get();
		panel.add(new KSLightBoxDemo());
		panel.add(new HeaderDemo());
		/*
    	panel.add(new Button("Test Notifications", new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
            		KSNotifier.add(new KSNotification("Thisisalongmessagethatwillneedtowordwrapbutnotallofitisverywordwrapfriendlyasyoucanplainlysee", false, 10000));
            		new Timer() {
						
						@Override
						public void run() {
	                        KSNotifier.add(new KSNotification("Short message <a href='http://xkcd.com/' target='_blank'>with html</a>", true, 10000));
	                		new Timer() {
								
								@Override
								public void run() {
			                        KSNotifier.add(new KSNotification(new Image("images/common/KS_logo_white_transparent.png"), 10000));
								}
							}.schedule(5000);
						}
					}.schedule(5000);
            }
    	}));

		// flood out the body to test scrolling
		for (int i=0; i<500; i++) {
			panel.add(new Label("label " + i));
		}
		*/
	}
	
	public static class TestLightboxContent extends Composite {
		final VerticalPanel panel = new VerticalPanel();
		final HorizontalPanel buttonPanel = new HorizontalPanel();
		final HorizontalPanel horizontalContent = new HorizontalPanel();
		final VerticalPanel verticalContent = new VerticalPanel();
		
		public TestLightboxContent() {
			super.initWidget(panel);
			panel.add(buttonPanel);
			panel.add(horizontalContent);
			panel.add(verticalContent);

            for (int i=0; i<50; i++) {
                horizontalContent.add(new HTML("<div style='padding-left: 1em'>item&nbsp;" + i + "</div>"));
                verticalContent.add(new Label("item " + i));
            }
            
			buttonPanel.add(new KSButton("horizontal", new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					for (int i=0; i<10; i++) {
						horizontalContent.add(new HTML("<div style='padding-left: 1em'>item&nbsp;" + i + "</div>"));
					}
				}
			}));
			
			buttonPanel.add(new KSButton("vertical", new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					for (int i=0; i<10; i++) {
						verticalContent.add(new Label("item " + i));
					}
				}
			}));
			
		}
	}

    public void onModuleLoad_Original() {
    	/*final ClickHandler handler = new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}};
			
    	KSWrapper wrapper = new KSWrapper();
    	StylishDropDown dropDown = new StylishDropDown("Navigation", Theme.INSTANCE.getCommonImages().getWarningIcon(), MenuImageLocation.RIGHT);
    	dropDown.addStyleName("KS-Navigation-DropDown");
    	dropDown.setShowSelectedItem(true);
    	List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
    	items.add(new KSMenuItemData("Curriculum Management"){{setClickHandler(handler); setShownIcon(Theme.INSTANCE.getCommonImages().getOkIcon());}});
    	items.add(new KSMenuItemData("Organizations"));
    	items.add(new KSMenuItemData("Rice"));
    	dropDown.setArrowImage(Theme.INSTANCE.getCommonImages().getDropDownIconWhite());
    	dropDown.setItems(items);
    	
    	StylishDropDown dropDown2 = new StylishDropDown("Propose New Curriculum", Theme.INSTANCE.getCommonImages().getWarningIcon(), MenuImageLocation.LEFT);
    	dropDown2.addStyleName("KS-LPNavigation-DropDown");
    	dropDown2.setShowSelectedItem(false);
    	List<KSMenuItemData> proposeItems = new ArrayList<KSMenuItemData>();
    	proposeItems.add(new KSMenuItemData("Courses"){{
    		addSubItem(new KSMenuItemData("Academic Course", handler));
    		addSubItem(new KSMenuItemData("Non Academic Course", handler));
    	}});
    	proposeItems.add(new KSMenuItemData("Programs"){{
    		addSubItem(new KSMenuItemData("Undergraduate"){{
    			addSubItem(new KSMenuItemData("Associate Degree", handler));
        		addSubItem(new KSMenuItemData("Baccalaureate Degree Major", handler));
        		addSubItem(new KSMenuItemData("Baccalaureate Degree Minor", handler));
        		addSubItem(new KSMenuItemData("Undergraduate Certificate", handler));
    		}});
    		addSubItem(new KSMenuItemData("Graduate"){{
    			addSubItem(new KSMenuItemData("Graduate Certificate", handler));
        		addSubItem(new KSMenuItemData("Masters Degree", handler));
        		addSubItem(new KSMenuItemData("Doctoral Degree", handler));
    		}});
    		addSubItem(new KSMenuItemData("Professional"){{
    			addSubItem(new KSMenuItemData("Professional Degree", handler));
        		addSubItem(new KSMenuItemData("Professional Certificate", handler));
    		}});
    	}});
    	
    	proposeItems.add(new KSMenuItemData("Experiential Learning"){{
    		addSubItem(new KSMenuItemData("Externship", handler));
    		addSubItem(new KSMenuItemData("Internship", handler));
    		addSubItem(new KSMenuItemData("Practicum", handler));
    	}});
    	
    	dropDown2.setItems(proposeItems);
    	
    	//KSLumLandingPage landing = new KSLumLandingPage();
    	//wrapper.setContent(landing);
    	
    	RootPanel.get().add(new KSDatePicker());
    	RootPanel.get().add(dropDown);
    	RootPanel.get().add(dropDown2);*/
    	//final KSLabel label = new KSLabel("Name");

    	//RootPanel.get().add(label);
/*    	final KSTitleContainerImpl title = new KSTitleContainerImpl();
    	title.setTitle("NAME1");
    	KSButton button = new KSButton("Change", new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				title.setTitle("NEW NAME");
				//label.
			}});
    	RootPanel.get().add(title);*/        
    	List<String> columns = new ArrayList<String>();
        columns.add("Name");
        columns.add("Permissions");
        columns.add("Workflow Permissions");
        columns.add("Remove Person");
        SimpleWidgetTable table = new SimpleWidgetTable(columns);
        List<Widget> widgets = new ArrayList<Widget>();
        widgets.add(new Label("Hello"));
        widgets.add(new Label("Hello2"));
        widgets.add(new Label(""));
        widgets.add(new Label("Hello4"));
        table.addRow(widgets);
    	RootPanel.get().add(table);
    	
/*    	KSWrapper wrapper = new KSWrapper();

    	KSTitleContainerImpl container = new KSTitleContainerImpl("Introduction to Geology", "Unsubmitted Proposal", "Workflow Overview");
    	KSTabPanel tabPanel = new KSTabPanel();
    	container.setContent(tabPanel);

    	VerticalPanel vPanel = new VerticalPanel();
    	vPanel.add(new KSButton("Button"));
    	vPanel.add(new KSLabel("Label"));
    	vPanel.add(Theme.INSTANCE.getCommonImages().getAsterisk());
    	vPanel.add(new KSRichEditor());
    	KSBasicMenu menu = new KSBasicMenu();
    	List<KSMenuItemData> menuItems = new ArrayList<KSMenuItemData>();
    	menuItems.add(new KSMenuItemData("Item 1")
    	{{
    		addSubItem(new KSMenuItemData("Child 1"){{setClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					
					
				}
			});}});
    		addSubItem(new KSMenuItemData("Child 2"){{setClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					
					
				}
			});}});
    	}});
    	menuItems.add(new KSMenuItemData("Item 2")
    	{{
    		addSubItem(new KSMenuItemData("Child 1"){{setClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					
					
				}
			});}});
    		addSubItem(new KSMenuItemData("Child 2"){{setClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					
					
				}
			});}});
    		
    	}});
    	menu.setTitle("Menu Title");
    	menu.setDescription("Choose an Item");
    	menu.setItems(menuItems);
    	vPanel.add(menu);
    	VerticalPanel vPanel2 = new VerticalPanel();
    	vPanel2.add(new KSLabel("Summary Content"));
    	VerticalPanel vPanel3 = new VerticalPanel();
    	vPanel3.add(new KSLabel("Comments!"));
    	
    	tabPanel.addTab("1", "EDIT PROPOSAL", vPanel);
    	tabPanel.addTab("2", "PROPOSAL SUMMARY", vPanel2);
    	tabPanel.addTab("3", "Add & View Comments", vPanel3, TabPosition.RIGHT);
    	tabPanel.selectTab("1");
    	
    	wrapper.setContent(container);
    	List<KSLabel> headerLinks = new ArrayList<KSLabel>();
    	headerLinks.add(new KSLabel("KUALI REFERENCE UNIVERSITY"));
    	headerLinks.add(new KSLabel("KRU PORTAL"));
    	headerLinks.add(new KSLabel("DIRECTORIES"));
    	headerLinks.add(new KSLabel("SEARCH KRU"));
    	wrapper.setHeaderCustomLinks(headerLinks);
    	List<KSLabel> footerLinks = new ArrayList<KSLabel>();
    	footerLinks.add(new KSLabel("KUALI REFERENCE UNIVERSITY"));
    	footerLinks.add(new KSLabel("KRU PORTAL"));
    	footerLinks.add(new KSLabel("DIRECTORIES"));
    	footerLinks.add(new KSLabel("SEARCH KRU"));
    	wrapper.setFooterLinks(footerLinks);
    	RootPanel.get().add(wrapper);*/
    }
}
