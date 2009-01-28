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

import java.util.List;

import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.web.client.service.OrgRpcService;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
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
    HorizontalPanel hPanel = new HorizontalPanel();
    
    FlexTable resultTable = new FlexTable();
    
    public OrgLocatePanel(){
        super.initWidget(vPanel);
    }
  

    protected void onLoad(){
        Label label = new Label();
        
        label.setText("Browse Organizations");
        label.setStyleName("info");

        OrgRpcService.Util.getInstance().getOrgHierarchies(new AsyncCallback<List<OrgHierarchyInfo>>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(List<OrgHierarchyInfo> result) {
                buildOrgResults(result);
            }
            
        });
        vPanel.add(createLocateMenu());
        
    }
    
    private Widget createLocateMenu(){
        FlexTable fTable = new FlexTable();
        fTable.setWidget(0,0, new SectionLabel("Search"));       
        fTable.setWidget(0,1, new SectionLabel("Browse"));      
        
        return fTable;
    }
    
    private void buildOrgResults(List<OrgHierarchyInfo> result) {
        // TODO Auto-generated method stub
        resultTable.clear();
        int i = 0;
        for(OrgHierarchyInfo orgHInfo:result){
            resultTable.setWidget(i, 0, new Label(orgHInfo.getName()));
            i++;
        }
        
    }

    
}
