package org.kuali.student.common.ui.client;

import java.util.ArrayList;
import java.util.List;


import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSButton.ButtonImageAlign;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.YesNoGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.YesNoEnum;
import org.kuali.student.common.ui.client.widgets.containers.KSTitleContainerImpl;
import org.kuali.student.common.ui.client.widgets.containers.KSWrapper;
import org.kuali.student.common.ui.client.widgets.menus.KSBasicMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanelAbstract;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel.TabPosition;
import org.kuali.student.common.ui.client.widgets.tabs.impl.KSTabPanelImpl;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CommonUITest implements EntryPoint {
	
    @Override
    public void onModuleLoad() {
//    	KSButton button = new KSButton("A button");
//    	button.setImage(Theme.INSTANCE.getCommonImages().getHelpIcon().getImage(), ButtonImageAlign.RIGHT);
//    	button.addClickHandler(new ClickHandler(){
//
//			@Override
//			public void onClick(ClickEvent event) {
//				Window.alert("Hello there");
//				
//			}
//    	});
    	
    	ButtonGroup group = new YesNoGroup(new Callback<YesNoEnum>(){

			@Override
			public void exec(YesNoEnum result) {
				if(result == YesNoEnum.YES){
					Window.alert("Hello there");
				}
				else{
					Window.alert("No");
				}
				
			}
		});
    	RootPanel.get().add(group);
    	
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
