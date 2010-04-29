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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.event.ModifyActionEvent;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.ui.client.mvc.controller.OrgProposalController;
import org.kuali.student.core.organization.ui.client.mvc.view.MembersTable;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
import org.kuali.student.core.organization.ui.client.view.OrganizationWidget.Scope;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class OrgLocateTree extends Composite implements HasStateChanges {
        
    private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
    
    DeckPanel w = new DeckPanel();
    Tree tree = new Tree();
    String activeHierarchyId;
    VerticalSectionView containingSection;
    
    boolean loaded = false;
    
    public OrgLocateTree() {
        super.initWidget(w);
        w.add(tree);
        
        tree.setWidth("100%");
        
        tree.addStyleName("KS-Org-Tree");
        
        KSLabel lbl = new KSLabel("Please Wait...");
        w.add(lbl);
        w.showWidget(1);
    }
    
    public OrgLocateTree(VerticalSectionView section) {
        this();
        this.containingSection = section;
    }

    public void refreshTree(){        
        loaded=false;
        onLoad();
    }
    protected void onLoad(){
        if (!loaded){
            loaded = true;
            
            orgRpcServiceAsync.getOrgHierarchies(new AsyncCallback<List<OrgHierarchyInfo>>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(List<OrgHierarchyInfo> result) {
                    if(result != null){
                        for(OrgHierarchyInfo orgHInfo:result){
                            getOrgTree(orgHInfo.getRootOrgId(),orgHInfo.getId());
                        }                
                    }
                }
            });
            
        } else {
            while(w.getWidgetCount() != 1)
                w.remove(w.getWidgetCount() - 1);
            w.showWidget(0);
        }
    }
    
    protected void getOrgTree(String orgId, String hierarchyId){
        orgRpcServiceAsync.getOrgDisplayTree(orgId, hierarchyId, 0, new AsyncCallback<List<OrgTreeInfo> >(){
            
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(List<OrgTreeInfo>  results) {
                String selectId = null;
                Map<String,TreeItem> itemMap = new HashMap<String, TreeItem>();

                for(OrgTreeInfo orgTreeInfo:results){
                    
                    TreeItem item = (itemMap.get(orgTreeInfo.getOrgId()) == null? 
                                new TreeItem():
                                itemMap.get(orgTreeInfo.getOrgId()));
                    item.setWidget(new OrgWidget(orgTreeInfo.getOrgId(), orgTreeInfo.getDisplayName(),orgTreeInfo.getPositions()));
                    if(selectNode != null && selectNode.equals(orgTreeInfo.getDisplayName())) {
                        selectId = orgTreeInfo.getOrgId();
                        selectNode = null;
                    }
                    item.setUserObject(orgTreeInfo.getOrgId());
                    itemMap.put(orgTreeInfo.getOrgId(), item);

                    if(orgTreeInfo.getParentId()!=null && !"".equals(orgTreeInfo.getParentId())){
                        if(itemMap.get(orgTreeInfo.getParentId()) != null)
                            itemMap.get(orgTreeInfo.getParentId()).addItem(item);
                        else {
                            TreeItem parent = new TreeItem();
                            parent.setUserObject(orgTreeInfo.getParentId());
                            parent.addItem(item);
                            itemMap.put(orgTreeInfo.getParentId(), parent);
                        }
                    } else {
                        tree.addItem(item);
                        while(w.getWidgetCount() != 1)
                            w.remove(w.getWidgetCount() - 1);
                        if(selectWidget != null) {
                            w.add(selectWidget);
                            w.showWidget(1);
                        } else {
                            w.showWidget(0);
                        }
                    }
                    
                }
                
                if(selectId != null) {
                    TreeItem item = itemMap.get(selectId);
                    for(item = item.getParentItem(); item != null; item = item.getParentItem()) {
                        item.setState(true);
                    }
                    item = itemMap.get(selectId);
                    item.setSelected(true);
                }
            }
        });
    }
    
    class OrgWidget extends Composite {
        VerticalPanel w = new VerticalPanel();
        boolean showMembers = false;
        String name;
        
        public OrgWidget(final String id, final String name, final long positions) {
            super.initWidget(w);
            this.name = name;
            final MembersTable memberTable = new MembersTable();
            memberTable.setOrgId(id);
            
            final KSLabel label = new KSLabel(name);
            ClickHandler handlerModify = new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Controller orgController = containingSection.getController();
                    orgController.fireApplicationEvent(new ModifyActionEvent(id));
                }
            };
            label.addClickHandler(handlerModify);
            
            ClickHandler memberHandler = new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    if(!showMembers){
                        ((KSLabel)event.getSource()).getElement().setInnerText("Hide Members " + "["+ positions + "]");
                        w.add(memberTable);
                        memberTable.fetchMemberTable();
                        memberTable.setVisible(true);
                        showMembers = true;
                        
                    }
                    else{
                        ((KSLabel)event.getSource()).getElement().setInnerText("Members " + "["+ positions + "]");
                        memberTable.setVisible(false);
                        showMembers = false;
                    }
                }

            };
            
            
            final KSLabel members = new KSLabel("Members " + "["+ positions + "]");
            members.addStyleName("action");
            members.addClickHandler(memberHandler);
            members.getElement().setAttribute("value", ""+Scope.build(Scope.ORG_PERSON_RELATIONS, Scope.MODIFY).value());
            
            w.add(label);
            w.add(members);
            w.addStyleName("KS-Org-Tree-Member-Section");
            
            addStyleName("KS-Org-Widget");
        }
        
    }
    
    String selectNode = null;
    OrganizationWidget selectWidget = null;
    @Override
    public void loadState(String state) {
        if(state != null && !state.trim().equals("")) {
            String[] split = state.split("&");
            selectNode = split[0];
            if(split.length > 2) {
                int scope = Integer.parseInt(split[1]);
                String orgId = split[2];
                final DeckPanel deck = OrgLocateTree.this.w;
                selectWidget = new OrganizationWidget(orgId, Scope.make(scope));
                selectWidget.addCloseButton("Back", new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        deck.remove(deck.getWidgetCount() - 1);
                        deck.showWidget(deck.getWidgetCount() - 1);
                    }
                });
            }
            if(selectNode != null && w.getWidgetCount() == 1) {
                for(int i = 0; i < tree.getItemCount(); i++) {
                    TreeItem item = tree.getItem(i);
                    if(((OrgWidget)item.getWidget()).name.equals(selectNode)) {
                        item.setSelected(true);
                        for(item = item.getParentItem(); item != null; item = item.getParentItem()) {
                            item.setState(true);
                        }
                        break;
                    }
                }
                selectNode = null;
                if(selectWidget != null) {
                    w.add(selectWidget);
                    w.showWidget(1);
                    selectWidget = null;
                }
            }
        }
    }

    @Override
    public String saveState() {
        if(tree.getSelectedItem() != null) {
            String treeNode = ((OrgWidget)tree.getSelectedItem().getWidget()).name;
            if(w.getWidgetCount() > 1) {
                int scope = ((OrganizationWidget)w.getWidget(1)).scope.value();
                String orgId = ((OrganizationWidget)w.getWidget(1)).orgId;
                return treeNode+"&"+scope+"&"+orgId;
            }
            return treeNode;
        }
        return null;
    }

}
