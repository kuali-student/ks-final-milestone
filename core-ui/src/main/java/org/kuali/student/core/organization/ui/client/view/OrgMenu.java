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

import org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgMenu extends VerticalPanel{
    
    SimplePanel contentPanel;
    
    public OrgMenu(SimplePanel contentPanel){
        this.contentPanel = contentPanel;
        
        KSMenu menuPanel = new KSAccordionMenu();
        
        List<KSMenuItemData> menuItems = new ArrayList<KSMenuItemData>();
        
        KSMenuItemData createItem = new KSMenuItemData("Create"); 
        addSubItem(createItem, "Organization", new OrgCreatePanel(OrgCreatePanel.CREATE_ORG_ALL));
        addSubItem(createItem, "Position", new OrgCreatePanel(OrgCreatePanel.CREATE_ORG_POSITIONS));
        
        KSMenuItemData modifyItem = new KSMenuItemData("Modify");
        modifyItem.setClickHandler(getClickHandler(new OrgUpdatePanel()));
        
        KSMenuItemData locateItem = new KSMenuItemData("Locate");
        locateItem.setClickHandler(getClickHandler(new OrgLocateTree()));
        
        menuItems.add(createItem);
        menuItems.add(modifyItem);
        menuItems.add(locateItem);
        menuPanel.setItems(menuItems);
        this.setStyleName("ks-OrgMenu"); 

        this.add(menuPanel);
    }

    private void addSubItem(final KSMenuItemData group, String title, final Widget orgWidget) {

        KSMenuItemData item = new KSMenuItemData(title);
    
        item.setClickHandler(getClickHandler(orgWidget));              
        group.addSubItem(item);
    }
    
    private ClickHandler getClickHandler(final Widget w){
        return new ClickHandler() {
            public void onClick(ClickEvent arg0) {
                if (contentPanel.getWidget() != null) {
                    try{
                        contentPanel.remove(contentPanel.getWidget());
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                contentPanel.setWidget(w);        
            }
        };
    }                    
}
