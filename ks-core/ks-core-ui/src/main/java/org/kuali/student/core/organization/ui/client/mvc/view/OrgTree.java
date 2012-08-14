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

package org.kuali.student.core.organization.ui.client.mvc.view;

import java.util.List;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.event.ModifyActionEvent;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
import org.kuali.student.core.organization.ui.client.theme.OrgTreeImages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeImages;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OrgTree  extends Composite{
    private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
    Panel  w = new SimplePanel();
    TreeImages images = (TreeImages)GWT.create(OrgTreeImages.class);
    Tree tree = new Tree(images);
    VerticalSectionView containingSection;
    boolean loaded = false;
    
    public OrgTree(){
        super.initWidget(w);
        w.add(tree);
        tree.setWidth("100%");
        KSLabel lbl = new KSLabel("Please Wait...");
        
        tree.addOpenHandler(new OpenHandler<TreeItem>(){

            @Override
            public void onOpen(OpenEvent<TreeItem> event) {
                TreeItem item = event.getTarget();
                Node rootNode = (Node)item.getWidget();
                String orgId = rootNode.getOrgId();
                String orgName = rootNode.getName();
                item.removeItems();
                item.addItem("");
                if(item.getChildCount()==1){
                    getOrgTree(orgId,rootNode.getHierarchyId(),item);
                }
            }
            
        });
    }
    
    public OrgTree(VerticalSectionView section) {
        this();
        this.containingSection = section;
    }
    
    public boolean isLoaded(){
        return loaded;
    }
    public void setLoaded(boolean loaded){
        this.loaded=loaded;
    }
    protected void onLoad(){
        if (!loaded){
            loaded = true;
        orgRpcServiceAsync.getOrgHierarchies(new KSAsyncCallback<List<OrgHierarchyInfo>>(){

            public void onSuccess(List<OrgHierarchyInfo> result) {
                if(result != null){
                    for(final OrgHierarchyInfo orgHInfo:result){

                        // setting maxLevel to -1 to obtain only the root Node
                        orgRpcServiceAsync.getOrgDisplayTree(orgHInfo.getRootOrgId(), orgHInfo.getId(), -1, new KSAsyncCallback<List<OrgTreeInfo> >(){

                            @Override
                            public void onSuccess(List<OrgTreeInfo> result) {
                                OrgTreeInfo root = result.get(0);
                                TreeItem item = new TreeItem(new RootNode(root.getOrgId(),root.getDisplayName(),root.getPositions(),root.getOrgHierarchyId()));
                                tree.addItem(item);
                                item.addItem("");
                            }
                            
                        });
                        
                    }                
                }
            }
        });
        }
    }
    
    
    protected void getOrgTree(final String orgId, final String hierarchyId, final TreeItem node){
        //Setting Max level to 1 to obtain relations at the first level.
        orgRpcServiceAsync.getOrgDisplayTree(orgId, hierarchyId, 1, new KSAsyncCallback<List<OrgTreeInfo> >(){

            @Override
            public void onSuccess(List<OrgTreeInfo> result) {
                for(OrgTreeInfo orgTreeInfo:result){
                    if (!orgTreeInfo.getOrgId().equals(orgId)) {
                        TreeItem item = new TreeItem(new OrgWidget(orgTreeInfo.getOrgId(), orgTreeInfo.getDisplayName(), orgTreeInfo.getPositions(), hierarchyId));
                        node.addItem(item);
                        item.addItem("");
                    }
                }
                node.getChild(0).remove();
                
                node.setState(true, false);
                
            }
            
        });
    }
    private class RootNode  extends Composite implements Node{
        VerticalPanel w = new VerticalPanel();
        String orgId;
        String name;
        String hierarchyId;
        boolean showMembers = false;
        public String getOrgId(){
            return orgId;
        }
        
        public String getName(){
            return name;
        }
        
        public String getHierarchyId(){
            return hierarchyId;
        }
        
        public RootNode( final String orgId,String name,final long positions, String hierarchyId){
            super.initWidget(w);
            this.name=name;
            this.orgId=orgId;
            this.hierarchyId = hierarchyId;

            KSLabel label = new KSLabel(name);
            label.addStyleName("KS-Org-Tree-Label");
            final MembersTable memberTable = new MembersTable();
            DOM.setElementAttribute(memberTable.getElement(), "id", "orgTreeMemberTable");
            memberTable.setOrgId(orgId);
            ClickHandler handlerModify = new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    Controller orgController = containingSection.getController();
                    orgController.fireApplicationEvent(new ModifyActionEvent(orgId));
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
            members.getElement().setAttribute("value", "");
            w.add(label);
            w.add(members);
            w.addStyleName("KS-Org-Tree-Section");
            addStyleName("KS-Org-Widget");
            
            
        }
    }
    
    private class OrgWidget extends Composite implements Node{
        VerticalPanel w = new VerticalPanel();
        boolean showMembers = false;
        String orgId;
        String name;
        String hierarchyId;
        
        public String getOrgId(){
            return orgId;
        }
        
        public String getName(){
            return name;
        }
        
        public String getHierarchyId(){
            return hierarchyId;
        }
        
        public OrgWidget(final String id, final String name, final long positions, String hierarchyId) {
            super.initWidget(w);
            this.name = name;
            this.orgId=id;
            this.hierarchyId=hierarchyId;
            final MembersTable memberTable = new MembersTable();
            DOM.setElementAttribute(memberTable.getElement(), "id", "orgTreeMemberTable");
            memberTable.setOrgId(id);
            
            final KSLabel label = new KSLabel(name);
            label.addStyleName("KS-Org-Tree-Label");
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
            members.getElement().setAttribute("value", "");
            
            w.add(label);
            w.add(members);
            w.addStyleName("KS-Org-Tree-Section");
            
            addStyleName("KS-Org-Widget");
        }
        
    }
    
    interface Node{
        public String getOrgId();
        
        public String getName();
        
        public String getHierarchyId();
    }
    
        
}
