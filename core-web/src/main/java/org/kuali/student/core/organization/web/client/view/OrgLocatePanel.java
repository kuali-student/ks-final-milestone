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
package org.kuali.student.core.organization.web.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.web.client.service.OrgRpcService;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
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
public class OrgLocatePanel extends Composite{

    VerticalPanel vPanel = new VerticalPanel();
    VerticalPanel browsePanel;
    
    SimplePanel orgChart;
    HorizontalPanel orgList;
       
    SimplePanel results = new SimplePanel();
       
    String activeHierarchyId;
    
    Map<String, String> orgRootHierarchy = new HashMap<String,String>();
    
    boolean loaded = false;
        
    public OrgLocatePanel(){
        super.initWidget(vPanel);
    }
  
    protected void onLoad(){
        vPanel.setWidth("100%");
        vPanel.add(createLocateMenu());
        vPanel.add(new SectionLabel("Browse Organizations"));
        vPanel.add(results);
        
        getBrowseResults();
    }
    
    private Widget createLocateMenu(){
        VerticalPanel locateMenuPanel = new VerticalPanel();
        locateMenuPanel.setWidth("100%");
        locateMenuPanel.setStyleName("ks-section");
        
        FlexTable fTable = new FlexTable();
        fTable.setWidget(0,0, new SectionLabel("Search"));       
        fTable.setWidget(0,1, new SectionLabel("Browse"));      
        
        locateMenuPanel.add(fTable);
        
        return locateMenuPanel;
    }
    
    private void getBrowseResults() {
        browsePanel = new VerticalPanel();
        browsePanel.setStyleName("ks-section");
        
        orgList = new HorizontalPanel();

        orgChart = new SimplePanel();
        OrgRpcService.Util.getInstance().getOrgHierarchies(new AsyncCallback<List<OrgHierarchyInfo>>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(List<OrgHierarchyInfo> result) {
                List<String> orgRootIds = new ArrayList<String>();               
                for(OrgHierarchyInfo orgHInfo:result){
                    orgRootIds.add(orgHInfo.getRootOrgId());
                    orgRootHierarchy.put(orgHInfo.getRootOrgId(), orgHInfo.getKey());
                }                
                
                getOrgList(orgRootIds);
            }
        });

        browsePanel.add(orgList);
        browsePanel.add(orgChart);
        results.setWidget(browsePanel);
    }
    
    protected void getOrgList(List<String> orgIds){
        OrgRpcService.Util.getInstance().getOrganizationsByIdList(orgIds, new AsyncCallback<List<OrgInfo>>(){
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
        OrgRpcService.Util.getInstance().getAllDescendants(orgId, activeHierarchyId, new AsyncCallback<List<String>>(){
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
        String orgId;
        
        public OrgWidget(OrgInfo orgInfo){
            orgId = orgInfo.getId();
            super.initWidget(vOrgPanel);
            orgLink = new Hyperlink(orgInfo.getLongName(), "viewOrg");
            orgLink.addClickListener(new ClickListener(){           
                public void onClick(Widget sender) {
                    removeWidgetsRight(vOrgPanel.getParent().getParent());
                    if (orgRootHierarchy.containsKey(orgId)){
                        activeHierarchyId = orgRootHierarchy.get(orgId);
                    }
                    getOrgChildren(orgId);
                    orgChart.setWidget((new OrgChartWidget(orgId,activeHierarchyId,3)));
            }});
            
            orgEditLbl = new Hyperlink("Edit", "editOrg");
            orgEditLbl.setStyleName("action");
            orgEditLbl.addClickListener(new OrgActionClickListener(orgId, OrgCreatePanel.CREATE_ORG_ALL));
            
            orgAddPosLbl = new Hyperlink("(+)org pos", "addPosRel");
            orgAddPosLbl.setStyleName("action");
            orgAddPosLbl.addClickListener(new OrgActionClickListener(orgId, OrgCreatePanel.CREATE_ORG_POSITIONS));

            orgAddRelLbl = new Hyperlink("(+)org rel", "addOrgRel");
            orgAddRelLbl.setStyleName("action");
            orgAddRelLbl.addClickListener(new OrgActionClickListener(orgId, OrgCreatePanel.CREATE_ORG_RELATIONS));

            FlexTable ft = new FlexTable();
            ft.setWidget(0, 1, orgEditLbl);
            ft.setWidget(0, 2, orgAddRelLbl);
            ft.setWidget(0, 3, orgAddPosLbl);
            
            vOrgPanel.add(orgLink);
            vOrgPanel.add(ft);
        }        
    }
    
    public class OrgActionClickListener implements ClickListener{

        String orgPanelType;
        String orgId;
        
        public OrgActionClickListener(String orgId, String orgPanelType){
            this.orgPanelType = orgPanelType;
            this.orgId = orgId;
        }
        
        public void onClick(Widget sender) {
            SimplePanel workPanel  = (SimplePanel)vPanel.getParent().getParent();
            OrgCreatePanel orgCreatePanel = new OrgCreatePanel(orgPanelType);
            orgCreatePanel.setOrgId(orgId);
            workPanel.setWidget(orgCreatePanel);            
        }

    }
}
