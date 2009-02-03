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

import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.web.client.service.OrgRpcService;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgRelationWidget extends Composite{

    TextBox relatedOrgName = null;
    TextBox relatedOrgId = null;
    ListBox orgRelTypeDropDown = null;
    TextBox orgEffectiveDate = null;
    TextBox orgExpirationDate = null;
    TextArea orgNote = null;
    
    String orgRelType;
    
    FlexTable fTable = null;
    
    SimplePanel root = new SimplePanel();
    
    public OrgRelationWidget(){
        super.initWidget(root);

        relatedOrgName = new TextBox();
        relatedOrgId = new TextBox();
        orgRelTypeDropDown = new ListBox();
        orgEffectiveDate = new TextBox();
        orgExpirationDate = new TextBox();
        orgNote = new TextArea();
        
    }
    
    protected void onLoad(){
        fTable = new FlexTable();

        loadOrgRelationTypes();        
        
        fTable.setWidget(0,0, new Label("Organization"));
        fTable.setWidget(0,1, relatedOrgName);
        
        fTable.setWidget(1,0, new Label("Organization Id"));
        fTable.setWidget(1,1, relatedOrgId);

        fTable.setWidget(2,0, new Label("Relationship"));
        fTable.setWidget(2,1, orgRelTypeDropDown);
        
        fTable.setWidget(3,0, new Label("Effective date"));
        fTable.setWidget(3,1, orgEffectiveDate);
        
        fTable.setWidget(4,0, new Label("Expiration date"));
        fTable.setWidget(4,1, orgExpirationDate);
        
        fTable.setWidget(5,0, new Label("Note"));
        fTable.setWidget(5,1, orgNote);
        
        root.add(fTable);
    }
    
    public OrgOrgRelationInfo getOrgOrgRelationInfo(){
        DateTimeFormat dateFmt = DateTimeFormat.getFormat("MM/dd/yyyy");
        
        OrgOrgRelationInfo orgRelationInfo = new OrgOrgRelationInfo();        
        orgRelationInfo.setId(relatedOrgId.getText());
        orgRelationInfo.setType(orgRelTypeDropDown.getValue(orgRelTypeDropDown.getSelectedIndex()));
        
        //TODO: This should lookup orgId based on related org name
        String relatedOrgId = relatedOrgName.getText();
        orgRelationInfo.setRelatedOrgId(relatedOrgId);

        try{
            orgRelationInfo.setEffectiveDate(dateFmt.parse(orgEffectiveDate.getText()));
        } catch (Exception e){
        }
        try {
            orgRelationInfo.setExpirationDate(dateFmt.parse(orgEffectiveDate.getText()));
        } catch (Exception e) {
        }
        
        return orgRelationInfo;
    }
    
    public void setOrgOrgRelationInfo(OrgOrgRelationInfo orgRelationInfo){
        DateTimeFormat dateFmt = DateTimeFormat.getFormat("MM/dd/yyyy");
               
        relatedOrgId.setText(orgRelationInfo.getId());
        orgRelType = orgRelationInfo.getType();
        
        OrgRpcService.Util.getInstance().getOrganization(orgRelationInfo.getRelatedOrgId(), 
                new AsyncCallback<OrgInfo>(){

                    public void onFailure(Throwable caught) {
                    }

                    public void onSuccess(OrgInfo orgInfo) {
                        relatedOrgName.setText(orgInfo.getLongName());                       
                    }            
        });

        try{
            orgEffectiveDate.setText(dateFmt.format(orgRelationInfo.getEffectiveDate()));
        } catch (Exception e){
        }
        try {
            orgExpirationDate.setText(dateFmt.format(orgRelationInfo.getExpirationDate()));
        } catch (Exception e) {
        }   
    }
    
    protected void loadOrgRelationTypes(){
        OrgRpcService.Util.getInstance().getOrgOrgRelationTypes(new AsyncCallback<List<OrgOrgRelationTypeInfo>>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(List<OrgOrgRelationTypeInfo> orgRelTypes) {
                orgRelTypeDropDown.addItem("Select", "");

                int i = 0;
                for(OrgOrgRelationTypeInfo orgRelTypeInfo:orgRelTypes){
                    i++;
                    orgRelTypeDropDown.addItem(orgRelTypeInfo.getName(),orgRelTypeInfo.getKey());
                    if (orgRelTypeInfo.getKey().equals(orgRelType)){
                        orgRelTypeDropDown.setSelectedIndex(i);
                    }
                }
            }
        });                
    }
    
}
