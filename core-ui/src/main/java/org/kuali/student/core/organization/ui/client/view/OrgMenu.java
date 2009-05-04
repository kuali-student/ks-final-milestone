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
        addSubItem(createItem, "Organization", new OrganizationWidget(OrganizationWidget.Scope.ORG_CREATE_ALL), "0.0");
        addSubItem(createItem, "Position", new OrganizationWidget(OrganizationWidget.Scope.ORG_POSITIONS), "0.1");
        
        KSMenuItemData modifyItem = new KSMenuItemData("Search");
//        modifyItem.setClickHandler(getClickHandler(new OrgUpdatePanel(), "1"));
        addSubItem(modifyItem, "Search / Modify", new OrgUpdatePanel(), "1.0");
        
        KSMenuItemData locateItem = new KSMenuItemData("Browse");
        addSubItem(locateItem, "by Tree", new OrgLocateTree(), "2.0");
        addSubItem(locateItem, "by List", new OrgLocatePanel(), "2.1");
        addSubItem(locateItem, "by Chart", new OrgLocateChart(), "2.2");
        addSubItem(locateItem, "by Name", new OrgLocateName(), "2.3");
        
        menuItems.add(createItem);
        menuItems.add(modifyItem);
        menuItems.add(locateItem);
        menuPanel.setItems(menuItems);
        this.setStyleName("ks-OrgMenu"); 

        this.add(menuPanel);
        
    }

    private void addSubItem(final KSMenuItemData group, String title, final Widget orgWidget, String state) {

        KSMenuItemData item = new KSMenuItemData(title);
    
        item.setClickHandler(getClickHandler(orgWidget, state));              
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
//        menuPanel.selectMenuItem(new String[] {"by name"}); // don't like the way this is implemented
        //TODO this all so hacky and relies on impl code and everything and -- gah! team1 needs to make their widgets better
        if(!value.matches("\\d+(\\.\\d)*(;session=\\d+)?"))
            return;
        String[] split = value.split(";");
        String session = split.length > 1? split[1].split("=")[1]: null;
        split = split[0].split("\\.");
        KSAccordionPanelImpl menu = ((KSAccordionMenu)menuPanel).getMenu();
        for(int i = 0; i < split.length; i++) {
//            if(i == split.length - 1)
                menu.selectOption(Integer.valueOf(split[i]));
            if(i != split.length - 1) {
                menu = ((KSAccordionMenuImpl)((FlowPanel)menu.getWidgetList().get(Integer.valueOf(split[i]))).getWidget(0)).getMenu();
            }
        }
//        if(((KSAccordionMenu)menuPanel).getMenu())
        List<KSMenuItemData> items = menuPanel.getItems();
        KSMenuItemData menuItemData = null;
        for(int i = 0; i < split.length; i++) {
            menuItemData = items.get(Integer.valueOf(split[i]));
            if(menuItemData.getSubItems() != null)
                items = menuItemData.getSubItems();
        }
        if(menuItemData != null && menuItemData.getClickHandler() != null) {
            menuItemData.getClickHandler().onClick(null);
        }
    }                    
}
