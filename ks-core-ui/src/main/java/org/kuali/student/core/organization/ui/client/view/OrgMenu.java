/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.organization.ui.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.menus.KSBasicMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
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
    
    Map<String,HasStateChanges> stateMap = new HashMap<String,HasStateChanges>();
    
    public OrgMenu(SimplePanel contentPanel){
        this.contentPanel = contentPanel;
        
        menuPanel = new KSBasicMenu();
        
        if(menuPanel instanceof KSBasicMenu) { //I know it is, but just in case I switch back to AccordionMenu
            KSBasicMenu bmenu = (KSBasicMenu)menuPanel;
            bmenu.setTitle("Organization Menu");
            bmenu.setDescription("create, modify, and browse organizations");
        }
        
        List<KSMenuItemData> menuItems = new ArrayList<KSMenuItemData>();
        
        KSMenuItemData createItem = new KSMenuItemData("Create"); 
        addSubItem(createItem, "Organization", new OrganizationWidget(OrganizationWidget.Scope.ORG_CREATE_ALL));
        addSubItem(createItem, "Position", new OrganizationWidget(OrganizationWidget.Scope.ORG_POSITIONS));
        
        KSMenuItemData modifyItem = new KSMenuItemData("Search");
        addSubItem(modifyItem, "Search & Modify", new OrgUpdatePanel());
        
        KSMenuItemData locateItem = new KSMenuItemData("Browse");
//        addSubItem(locateItem, "by Tree", new OrgLocateTree());
//        addSubItem(locateItem, "by List", new OrgLocatePanel());
//        addSubItem(locateItem, "by Chart", new OrgLocateChart());
//        addSubItem(locateItem, "by Name", new OrgLocateName());
        
        menuItems.add(createItem);
        menuItems.add(modifyItem);
        menuItems.add(locateItem);
        menuPanel.setItems(menuItems);
        this.setStyleName("ks-OrgMenu"); 

        this.add(menuPanel);
        
    }

    private <T extends Widget & HasStateChanges> void addSubItem(final KSMenuItemData group, String title, final T orgWidget) {

        KSMenuItemData item = new KSMenuItemData(title);
    
        item.setClickHandler(getClickHandler(orgWidget, "/"+group.getLabel()+"/"+title));              
        group.addSubItem(item);
    }
    
    private <T extends Widget & HasStateChanges> ClickHandler getClickHandler(final T w, final String state){
        stateMap.put(state, w);
//        StateHandler handler = new StateHandler() {
//            @Override
//            public void onStateChange(StateEvent event) {
//                System.out.println("stateevent: "+event.getState());
//                if(event.getState() != null && !event.getState().equals(""))
//                    History.newItem(state+"%"+event.getState(), false);
//            }
//        };
        return new ClickHandler() {
            public void onClick(ClickEvent arg0) {
//                boolean history = History.getToken().startsWith(state);
                if (contentPanel.getWidget() != null) {
//                    String s = ((HasStateChanges)contentPanel.getWidget()).saveState();
//                    if(s != null && !history)
//                        History.newItem(History.getToken()+"%"+s, false);
                    contentPanel.remove(contentPanel.getWidget());
                }
                contentPanel.setWidget(w);
                if(!history)
                    History.newItem(state, false);
            }
        };
    }

    boolean history = false;
    
    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String value = event.getValue();
        String[] split = value.split("%", 2);
        System.out.println("Got event #"+value);
        history = true;
        menuPanel.selectMenuItem(split[0].split("/")); // don't like the way this is implemented, but oh well
        history = false;
        if(split.length > 1) {
            HasStateChanges statefulWidget = stateMap.get(split[0]);
            if(statefulWidget != null)
                statefulWidget.loadState(split[1]);
        }
    }

    public void saveState() {
        HasStateChanges state = (HasStateChanges)contentPanel.getWidget();
        if(state != null) {
            String s = state.saveState();
            if(s != null)
                History.newItem(History.getToken()+"%"+s, false);
        }
    }
}
