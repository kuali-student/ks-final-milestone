package org.kuali.student.core.organization.ui.client.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class OrgLocateTree extends Composite {
    
    Tree tree = new Tree();
    Map<String, String> orgRootHierarchy = new HashMap<String,String>();
    String activeHierarchyId;
    
    boolean loaded = false;
    
    public OrgLocateTree() {
        super.initWidget(tree);
    }

    protected void onLoad(){
        if (!loaded){
            tree.setWidth("100%");
            
            //this is a terrible idea
            addStyleName("KS-Disclosure-Content-Open");
            
            OrgRpcService.Util.getInstance().getOrgHierarchies(new AsyncCallback<List<OrgHierarchyInfo>>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(List<OrgHierarchyInfo> result) {
                    if(result != null){
                        for(OrgHierarchyInfo orgHInfo:result){
                            orgRootHierarchy.put(orgHInfo.getRootOrgId(), orgHInfo.getId());
                            getOrgTree(orgHInfo.getRootOrgId(),orgHInfo.getId());
                        }                
                    }
                }
            });
            
            loaded = true;
        }       
    }
    
    protected void getOrgList(List<String> orgIds, final TreeItem node){
        OrgRpcService.Util.getInstance().getOrganizationsByIdList(orgIds, new AsyncCallback<List<OrgInfo>>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(List<OrgInfo> result) {
                for (OrgInfo orgInfo : result) {
                    TreeItem treeItem = new TreeItem(orgInfo.getLongName());
                    treeItem.setUserObject(orgInfo);
                    if(node == null)
                        tree.addItem(treeItem);
                    else
                        node.addItem(treeItem);
                }
            }
        });
    }
    
    protected void getOrgTree(String orgId, String hierarchyId){
        OrgRpcService.Util.getInstance().getOrgDisplayTree(orgId, hierarchyId, 0, new AsyncCallback<List<OrgTreeInfo> >(){
            
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(List<OrgTreeInfo>  results) {
                Map<String,TreeItem> itemMap = new HashMap<String, TreeItem>();

                for(OrgTreeInfo orgTreeInfo:results){
                    
                    TreeItem item = (itemMap.get(orgTreeInfo.getOrgId()) == null? 
                                new TreeItem():
                                itemMap.get(orgTreeInfo.getOrgId()));
                    item.setWidget(new OrgWidget(orgTreeInfo.getOrgId(), orgTreeInfo.getDisplayName()));
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
                    }
                    
                }
                
            }
        });
    }
    
    static class OrgWidget extends Composite {
        HorizontalPanel w = new HorizontalPanel();
        
        public OrgWidget(final String id, final String name) {
            super.initWidget(w);
            
            KSLabel label = new KSLabel(name);
            
            ClickHandler handler = new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    final KSDialogPanel modal = new KSDialogPanel();
                    final OrgCreatePanel orgCreatePanel = new OrgCreatePanel(((Hyperlink)event.getSource()).getTargetHistoryToken(), new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                          modal.hide();
                        }});
                    ScrollPanel scroll = new ScrollPanel(orgCreatePanel);
                    scroll.setSize("20em", "20em");
                    orgCreatePanel.setOrgId(id);
                    modal.setWidget(scroll);
                    modal.setHeader(((Hyperlink)event.getSource()).getText());
                    modal.setResizable(true);
                    modal.setAutoHide(true);
                    modal.show();
                }
            };
            
            final Hyperlink edit = new Hyperlink("Edit",OrgCreatePanel.CREATE_ORG_ALL);
            edit.addStyleName("action");
            edit.addClickHandler(handler);
            final Hyperlink orgAddPosLbl = new Hyperlink("(+)org pos", OrgCreatePanel.CREATE_ORG_POSITIONS);
            orgAddPosLbl.addStyleName("action");
            orgAddPosLbl.addClickHandler(handler);
            final Hyperlink orgAddRelLbl = new Hyperlink("(+)org rel", OrgCreatePanel.CREATE_ORG_RELATIONS);
            orgAddRelLbl.addStyleName("action");
            orgAddRelLbl.addClickHandler(handler);
            final Hyperlink orgAddPersonRelLbl = new Hyperlink("(+)person rel", OrgCreatePanel.CREATE_ORG_PERSON_RELATIONS);
            orgAddPersonRelLbl.addStyleName("action");
            orgAddPersonRelLbl.addClickHandler(handler);
            
            w.add(label);
            w.add(edit);
            w.add(orgAddPosLbl);
            w.add(orgAddRelLbl);
            w.add(orgAddPersonRelLbl);
            
            addStyleName("KS-Org-Widget");
        }
        
    }
    
}
