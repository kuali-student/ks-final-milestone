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

import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;
import org.kuali.student.core.organization.ui.client.view.OrganizationWidget.Scope;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgLocatePanel extends Composite implements HasStateChanges{

    private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
    
    VerticalPanel root = new VerticalPanel();
    VerticalPanel browsePanel;
    
    SimplePanel orgChart;
    HorizontalPanel orgList;
       
    KSDisclosureSection orgSection = new KSDisclosureSection("Organization Chart", null, true);
    SimplePanel results = new SimplePanel();
       
    String activeHierarchyId;
    
    Map<String, String> orgRootHierarchy = new HashMap<String,String>();
    
    boolean loaded = false;
        
    public OrgLocatePanel(){
        super.initWidget(root);
    }
  
    protected void onLoad(){
        if (!loaded){
            root.setWidth("100%");
            //root.add(createLocateMenu());
            
            root.add(results);
            
            getBrowseResults();            
            loaded = true;
        }       
    }
    
//    private Widget createLocateMenu(){
//        VerticalPanel locateMenuPanel = new VerticalPanel();
//        locateMenuPanel.setWidth("100%");
//        locateMenuPanel.setStyleName("ks-section");
//        
//        FlexTable fTable = new FlexTable();
//        //fTable.setWidget(0,0, new SectionLabel("Search"));       
//        //fTable.setWidget(0,1, new SectionLabel("Browse"));      
//        
//        locateMenuPanel.add(fTable);
//        
//        return locateMenuPanel;
//    }
    
    private void getBrowseResults() {
        browsePanel = new VerticalPanel();
        browsePanel.setWidth("100%");
        
        orgList = new HorizontalPanel();        
        orgChart = new SimplePanel();
        
        orgRpcServiceAsync.getOrgHierarchies(new AsyncCallback<List<OrgHierarchyInfo>>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(List<OrgHierarchyInfo> result) {
                List<String> orgRootIds = new ArrayList<String>();               
                if(result != null){
	                for(OrgHierarchyInfo orgHInfo:result){
	                    orgRootIds.add(orgHInfo.getRootOrgId());
	                    orgRootHierarchy.put(orgHInfo.getRootOrgId(), orgHInfo.getId());
	                }                
                }
                if(!orgRootIds.isEmpty()){
                	getOrgList(orgRootIds);
                }
            }
        });

        browsePanel.add(orgList);

        //wrap org chart in vertical panel       
        orgSection.add(orgChart);
        orgSection.setVisible(false);
        orgChart.setWidth("1024px");
             
        browsePanel.add(orgSection);
        
        results.setWidget(browsePanel);
    }
    
    protected void getOrgList(List<String> orgIds){
        orgRpcServiceAsync.getOrganizationsByIdList(orgIds, new AsyncCallback<List<OrgInfo>>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(List<OrgInfo> result) {
     
                FlexTable resultTable = new FlexTable();
                int i = 0;
                for(OrgInfo orgInfo:result){ 
                    resultTable.setWidget(i, 0, new OrgWidget(orgInfo));
                    i++;
                }
                orgList.add(resultTable);
            }
        });
    }

    protected void getOrgChildren(String orgId){
        orgRpcServiceAsync.getAllDescendants(orgId, activeHierarchyId, new AsyncCallback<List<String>>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(List<String> result) {
                getOrgList(result);
            }
        });
    }
    
       
    protected void removeWidgetsRight(Widget w){
        for (int i=orgList.getWidgetCount()-1; i > orgList.getWidgetIndex(w);i--){
            orgList.remove(i);
        }
    }
    
    public class OrgWidget extends Composite{
        VerticalPanel vOrgPanel = new VerticalPanel(); 
        
        Hyperlink orgLink;
        Hyperlink orgEditLbl;
        Hyperlink orgAddRelLbl;
        Hyperlink orgAddPosLbl;
        Hyperlink orgAddPersonRelLbl;
        
        String orgId;
        
        public OrgWidget(OrgInfo orgInfo){
            orgId = orgInfo.getId();
            super.initWidget(vOrgPanel);
            orgLink = new Hyperlink(orgInfo.getLongName(), "viewOrg");
            orgLink.addClickHandler(new ClickHandler(){           
                public void onClick(ClickEvent event) {
                    removeWidgetsRight(vOrgPanel.getParent().getParent());
                    if (orgRootHierarchy.containsKey(orgId)){
                        activeHierarchyId = orgRootHierarchy.get(orgId);
                    }
                    getOrgChildren(orgId);
//                    orgChart.setWidget((new OrgChartWidget(orgId,activeHierarchyId,0)));
//                    orgSection.setVisible(true);
            }});
            
            orgEditLbl = new Hyperlink("Edit", "editOrg");
            orgEditLbl.setStyleName("action");
            orgEditLbl.addClickHandler(new OrgActionClickHandler(orgId, Scope.ORG_MODIFY_ALL));
            
            orgAddPosLbl = new Hyperlink("(+)org pos", "addPosRel");
            orgAddPosLbl.setStyleName("action");
            orgAddPosLbl.addClickHandler(new OrgActionClickHandler(orgId, Scope.ORG_POSITIONS, Scope.MODIFY));

            orgAddRelLbl = new Hyperlink("(+)org rel", "addOrgRel");
            orgAddRelLbl.setStyleName("action");
            orgAddRelLbl.addClickHandler(new OrgActionClickHandler(orgId, Scope.ORG_RELATIONS, Scope.MODIFY));

            orgAddPersonRelLbl = new Hyperlink("(+)person rel", "addPersonRel");
            orgAddPersonRelLbl.setStyleName("action");
            orgAddPersonRelLbl.addClickHandler(new OrgActionClickHandler(orgId, Scope.ORG_PERSON_RELATIONS, Scope.MODIFY));

            
            FlexTable ft = new FlexTable();
            ft.setWidget(0, 1, orgEditLbl);
            ft.setWidget(0, 2, orgAddRelLbl);
            ft.setWidget(0, 3, orgAddPosLbl);
            ft.setWidget(0, 4, orgAddPersonRelLbl);
            
            vOrgPanel.add(orgLink);
            vOrgPanel.add(ft);
        }        
    }
    
    public class OrgActionClickHandler implements ClickHandler{

        Scope scope;
        String orgId;
        
        public OrgActionClickHandler(String orgId, Scope... scopes){
            this.scope = Scope.build(scopes);
            this.orgId = orgId;
        }
        
        public void onClick(ClickEvent event) {
            SimplePanel workPanel  = (SimplePanel)root.getParent().getParent();
            OrganizationWidget orgCreatePanel = new OrganizationWidget(orgId, scope);
            workPanel.setWidget(orgCreatePanel);            
        }

    }

    @Override
    public void loadState(String state) {
        // TODO ddean - THIS METHOD NEEDS JAVADOCS
        
    }

    @Override
    public String saveState() {
        // TODO ddean - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
