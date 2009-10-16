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

import java.util.List;

import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchRpc;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgUpdatePanel extends Composite implements HasStateChanges{
    private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);

    VerticalPanel root = new VerticalPanel();
    SimplePanel editPanel = new SimplePanel();
    DeckPanel w = new DeckPanel();    
    
    String orgId = null;
    
    boolean loaded = false;
    
    public OrgUpdatePanel(){
        root.setWidth("100%");
        super.initWidget(w);
        w.add(root);
        w.showWidget(0);
    }
    
    public void onLoad(){
        if (!loaded){
            if (orgId == null){
                

                
                final KSAdvancedSearchRpc orgSearchWidget = new KSAdvancedSearchRpc(orgRpcServiceAsync, "org.search.orgQuickViewByHierarchyShortName", "org.resultColumn.orgId");

                //orgSearchWidget.setMultipleSelect(false);
                orgSearchWidget.addSelectionHandler(new SelectionHandler<List<String>>(){
                    public void onSelection(SelectionEvent<List<String>> event) {                    
                        OrganizationWidget orgCreatePanel = new OrganizationWidget(event.getSelectedItem().get(0), OrganizationWidget.Scope.ORG_MODIFY_ALL);
                        orgCreatePanel.addCloseButton("Back", new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                              w.remove(w.getWidgetCount() - 1);
                              w.showWidget(w.getWidgetCount() - 1);
                            }});
                        w.add(orgCreatePanel);
                        w.showWidget(w.getWidgetCount() - 1);
                    }

                    
                });
                root.add(orgSearchWidget);
            } else {
                editPanel.setWidget(new OrganizationWidget(OrganizationWidget.Scope.ORG_CREATE_ALL));
            }
            loaded = true;
            root.add(editPanel); 
        }
        while(w.getWidgetCount() != 1)
            w.remove(w.getWidgetCount() - 1);
        w.showWidget(0);
    }

    @Override
    public void loadState(String state) {
        System.out.println("parsing "+state);
        if(state != null && !state.trim().equals("")) {
            String[] split = state.split("&");
            if(split.length > 1) {
                OrgSearchWidget search = (OrgSearchWidget)root.getWidget(0);
                search.orgName.setValue(split[0], false);
                search.selection = split[1];
                if(split.length > 2) {
                    search.resultSelection = split[2];
                }
            }
        }
    }

    @Override
    public String saveState() {
        OrgSearchWidget search = (OrgSearchWidget)root.getWidget(0);
        if(search.orgName.getValue().equals("") && search.orgHierarchyDropDown.getSelectedItem() == null)
            return null;
        return search.orgName.getValue()+"&"+search.orgHierarchyDropDown.getSelectedItem()+(search.resultTable.getSelectedItem() == null? "": "&"+search.resultTable.getSelectedItem());
    }

}
