package org.kuali.student.core.organization.ui.client.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;

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

public class OrgLocateTree extends Composite {
    
    DeckPanel w = new DeckPanel();
    Tree tree = new Tree();
    String activeHierarchyId;
    
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

    protected void onLoad(){
        if (!loaded){
            loaded = true;
            
            OrgRpcService.Util.getInstance().getOrgHierarchies(new AsyncCallback<List<OrgHierarchyInfo>>(){
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
                        while(w.getWidgetCount() != 1)
                            w.remove(w.getWidgetCount() - 1);
                        w.showWidget(0);
                    }
                    
                }
                
            }
        });
    }
    
    class OrgWidget extends Composite {
        HorizontalPanel w = new HorizontalPanel();
        
        public OrgWidget(final String id, final String name) {
            super.initWidget(w);
            
            final KSLabel label = new KSLabel(name);
            
            ClickHandler handler = new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    final DeckPanel deck = OrgLocateTree.this.w;
                    final OrgCreatePanel orgCreatePanel = new OrgCreatePanel(((Hyperlink)event.getSource()).getTargetHistoryToken(), new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            deck.remove(deck.getWidgetCount() - 1);
                            deck.showWidget(deck.getWidgetCount() - 1);
                        }
                    });
                    orgCreatePanel.addSelectionHandler(new SelectionHandler<OrgInfo>(){
                        @Override
                        public void onSelection(SelectionEvent<OrgInfo> event) {
                            label.setText(event.getSelectedItem().getLongName());
                        }
                    });
                    orgCreatePanel.setOrgId(id);
                    deck.add(orgCreatePanel);
                    deck.showWidget(deck.getWidgetCount() - 1);
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
