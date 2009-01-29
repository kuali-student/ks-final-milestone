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
    HorizontalPanel browsePanel = new HorizontalPanel();
       
    SimplePanel results = new SimplePanel();
       
    String activeHierarchyId;
    
    Map<String, String> orgRootHierarchy = new HashMap<String,String>();
    
        
    public OrgLocatePanel(){
        super.initWidget(vPanel);
    }
  
    protected void onLoad(){        
        vPanel.add(createLocateMenu());
        vPanel.add(new SectionLabel("Browse Organizations"));
        vPanel.add(results);
        
        getBrowseResults();
    }
    
    private Widget createLocateMenu(){
        FlexTable fTable = new FlexTable();
        fTable.setWidget(0,0, new SectionLabel("Search"));       
        fTable.setWidget(0,1, new SectionLabel("Browse"));      
        
        return fTable;
    }
    
    private void getBrowseResults() {
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
                    resultTable.setWidget(i, 0, getOrgLink(orgInfo));
                    i++;
                }
                browsePanel.add(resultTable);
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
    
    protected Widget getOrgLink(OrgInfo org){
        OrgLink orgLink = new OrgLink(org.getLongName(), "viewOrg");
        orgLink.setOrgId(org.getId());
        
        orgLink.addClickListener(new ClickListener(){           
            public void onClick(Widget sender) {
                removeWidgetsRight(sender.getParent());
                String orgId = ((OrgLink)sender).getOrgId();
                if (orgRootHierarchy.containsKey(orgId)){
                    activeHierarchyId = orgRootHierarchy.get(orgId);
                }
                getOrgChildren(((OrgLink)sender).getOrgId());
        }});
        
        return orgLink;
    }
    
    protected void removeWidgetsRight(Widget w){
        for (int i=browsePanel.getWidgetCount()-1; i > browsePanel.getWidgetIndex(w);i--){
            browsePanel.remove(i);
        }
    }
    
    public class OrgLink extends Hyperlink{
        String orgId;
        
        public OrgLink(String name, String targetHistoryToken){
            super(name, targetHistoryToken);
        }
        
        public String getOrgId() {
            return orgId;
        }
        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }
    }
}
