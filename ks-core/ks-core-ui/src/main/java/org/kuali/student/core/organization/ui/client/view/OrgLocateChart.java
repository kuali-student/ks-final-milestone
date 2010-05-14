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

package org.kuali.student.core.organization.ui.client.view;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OrgLocateChart extends Composite implements HasStateChanges {

    ComplexPanel w = new VerticalPanel();
    private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
    
    boolean loaded = false;
        
    public OrgLocateChart() {
        super.initWidget(w);
    }

    protected void onLoad(){
        if (!loaded){
            w.setWidth("100%");
            
            addStyleName("KS-Org-Chart");
            
            orgRpcServiceAsync.getOrgHierarchies(new AsyncCallback<List<OrgHierarchyInfo>>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(List<OrgHierarchyInfo> result) {
                    if(result != null){
                        for(OrgHierarchyInfo orgHierarchyInfo:result){
                            KSLabel label = new KSLabel(orgHierarchyInfo.getName()+" Hierarchy");
                            label.addStyleName("page-title");
                            w.add(label);
                            w.add(new OrgChartWidget(orgHierarchyInfo.getRootOrgId(),orgHierarchyInfo.getId(),0));
                        }                
                    }
                }
            });
            
            loaded = true;
        }       
    }

    @Override
    public void loadState(String state) {
        // TODO ddean - THIS METHOD NEEDS JAVADOCS
        
    }

    @Override
    public String saveState() {
        // TODO ddean - THIS METHOD NEEDS JAVADOCS
        for(int i = 1; i < w.getWidgetCount(); i+=2) {
            OrgChartWidget o = (OrgChartWidget)w.getWidget(i);
            String orgId = o.orgId;
        }
        return null;
    }
}
