/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.organization.ui.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.impl.KSAccordionPanelImpl;
import org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSBasicMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.menus.impl.KSAccordionMenuImpl;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgMenu extends VerticalPanel implements ValueChangeHandler<String>{
    
    SimplePanel contentPanel;
    private KSMenu menuPanel;
    
    public OrgMenu(SimplePanel contentPanel){
        this.contentPanel = contentPanel;
        
        menuPanel = new KSBasicMenu();
        
        List<KSMenuItemData> menuItems = new ArrayList<KSMenuItemData>();
        
        KSMenuItemData createItem = new KSMenuItemData("Create"); 
        addSubItem(createItem, "Organization", new OrganizationWidget(OrganizationWidget.Scope.ORG_CREATE_ALL));
        addSubItem(createItem, "Position", new OrganizationWidget(OrganizationWidget.Scope.ORG_POSITIONS));
        
        KSMenuItemData modifyItem = new KSMenuItemData("Search");
        addSubItem(modifyItem, "Search & Modify", new OrgUpdatePanel());
        
        KSMenuItemData locateItem = new KSMenuItemData("Browse");
        addSubItem(locateItem, "by Tree", new OrgLocateTree());
        addSubItem(locateItem, "by List", new OrgLocatePanel());
        addSubItem(locateItem, "by Chart", new OrgLocateChart());
        addSubItem(locateItem, "by Name", new OrgLocateName());
        
        menuItems.add(createItem);
        menuItems.add(modifyItem);
        menuItems.add(locateItem);
        menuPanel.setItems(menuItems);
        this.setStyleName("ks-OrgMenu"); 

        this.add(menuPanel);
        
    }

    private void addSubItem(final KSMenuItemData group, String title, final Widget orgWidget) {

        KSMenuItemData item = new KSMenuItemData(title);
    
        item.setClickHandler(getClickHandler(orgWidget, "/"+group.getLabel()+"/"+title));              
        group.addSubItem(item);
    }
    
    private ClickHandler getClickHandler(final Widget w, final String state){
        return new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                if (contentPanel.getWidget() != null) {
                    contentPanel.remove(contentPanel.getWidget());
                }
                contentPanel.setWidget(w);
                History.newItem(state);
            }
        };
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String value = event.getValue();
        System.out.println("Got event #"+value);
        menuPanel.selectMenuItem(value.split("/")); // don't like the way this is implemented, but oh well
    }                    
}
