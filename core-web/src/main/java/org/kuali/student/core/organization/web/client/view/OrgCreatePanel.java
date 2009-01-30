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
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.web.client.service.OrgRpcService;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgCreatePanel extends Composite{

    VerticalPanel vPanel = new VerticalPanel();
    
    public static final String CREATE_ORG_ALL = "All";
    public static final String CREATE_ORG_POSITIONS = "Positions";
    public static final String CREATE_ORG_RELATIONS = "Organizations";
    
    String type;
    String orgId;
    
    ListBox orgTypeDropDown;
    TextBox orgName;
    TextBox orgAbbrev;
    TextArea orgDesc;
    
    //These should be date boxes
    TextBox orgEffectiveDate;
    TextBox orgExpirationDate;
    
    VerticalPanel vPositions;
    VerticalPanel vRelations;
    
    boolean loaded = false;
    
    
    public OrgCreatePanel(String type){
        this.type=type;
        super.initWidget(vPanel);
    }
    

    protected void onLoad(){
        //vPanel.setWidth("100%");
        //vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        if (!loaded){
            loaded = true;
            if (type.equals(CREATE_ORG_ALL)){
                getOrganization();
                getOrgPositions();
                getOrgRelations();
            } else if (type.equals(CREATE_ORG_POSITIONS)){
                getOrgPositions();            
            }
            
            Button btnCreateOrg = new Button("Create");
            btnCreateOrg.addClickListener(new ClickListener(){
                public void onClick(Widget sender) {
                    saveOrganization();
                    vPanel.clear();
                }
            });

            vPanel.add(btnCreateOrg);
        }
    }
    
    protected void getOrganization(){
        FlexTable fTable = new FlexTable();
        
        orgTypeDropDown = new ListBox();
        loadOrgTypes();
        
        orgName = new TextBox();
        orgAbbrev = new TextBox();
        orgDesc = new TextArea();
        orgEffectiveDate = new TextBox();
        orgExpirationDate = new TextBox();
        
        fTable.setWidget(0,0, new Label("Type"));
        fTable.setWidget(0,1, orgTypeDropDown);
        
        fTable.setWidget(1,0, new Label("Name"));
        fTable.setWidget(1,1, orgName);
        
        fTable.setWidget(2,0, new Label("Abbreviation"));
        fTable.setWidget(2,1, orgAbbrev);
        
        fTable.setWidget(3,0, new Label("Description"));
        fTable.setWidget(3,1, orgDesc);
        
        fTable.setWidget(4,0, new Label("Effective Date"));
        fTable.setWidget(4,1, orgEffectiveDate);

        fTable.setWidget(5,0, new Label("Expiration Date"));
        fTable.setWidget(5,1, orgExpirationDate);    

        vPanel.add(new SectionLabel("Organization"));
        vPanel.add(fTable);
    }
    
    
    protected void getOrgPositions(){
        vPanel.add(new SectionLabel("Positions"));
        
        vPositions = new VerticalPanel();
        vPositions.add( new OrgPositionWidget());
        
        vPanel.add(vPositions);
    }

    protected void getOrgRelations(){
        vPanel.add(new SectionLabel("Relationships"));

        vRelations = new VerticalPanel();
        vRelations.add( new OrgRelationWidget());
        
        vPanel.add(vRelations);

    }
    
    protected void saveOrganization(){
        DateTimeFormat dateFmt = DateTimeFormat.getFormat("MM/dd/yyyy");
        
        OrgInfo orgInfo = new OrgInfo();
               
        orgInfo.setType(orgTypeDropDown.getValue((orgTypeDropDown.getSelectedIndex())));        
        orgInfo.setDesc(orgDesc.getText());
        orgInfo.setLongName(orgName.getText());
        orgInfo.setShortName(orgAbbrev.getText());
        try{
            orgInfo.setEffectiveDate(dateFmt.parse(orgEffectiveDate.getText()));
        } catch (Exception e) {
        }
        try{        
            orgInfo.setExpirationDate(dateFmt.parse(orgExpirationDate.getText()));
        } catch (Exception e) {
        }
        
        OrgRpcService.Util.getInstance().createOrganization(orgInfo,new AsyncCallback<OrgInfo>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(OrgInfo result) {
                orgId = result.getId();
                savePositions();
                saveRelations();
            }
        });
    }
    
    protected void savePositions(){
        OrgPositionWidget orgPosWidget = (OrgPositionWidget)vPositions.getWidget(0);
        OrgPositionRestrictionInfo orgPosRestrictionInfo = orgPosWidget.getPositionRestrictionInfo();
        
        if (orgPosRestrictionInfo.getTitle() != null){
            orgPosRestrictionInfo.setOrgId(orgId);
            OrgRpcService.Util.getInstance().addPositionRestrictionToOrg(orgPosRestrictionInfo,
                    new AsyncCallback<OrgPositionRestrictionInfo>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }
    
                public void onSuccess(OrgPositionRestrictionInfo result) {
                }
            });
        }
    }
    
    protected void saveRelations(){
        OrgRelationWidget orgRelationWidget = (OrgRelationWidget)vRelations.getWidget(0);
        OrgOrgRelationInfo orgRelationInfo = orgRelationWidget.getOrgOrgRelationInfo();
        
        if (orgRelationInfo.getRelatedOrgId() != null){
            OrgRpcService.Util.getInstance().createOrgOrgRelation(orgRelationInfo, 
                    new AsyncCallback<OrgOrgRelationInfo>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }
    
                public void onSuccess(OrgOrgRelationInfo result) {
                    vPanel.clear();
                }
            });
        }
    }
    
    protected void loadOrgTypes(){
        OrgRpcService.Util.getInstance().getOrgTypes(new AsyncCallback<List<OrgTypeInfo>>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(List<OrgTypeInfo> orgTypes) {
                    orgTypeDropDown.addItem("Select", "");
                    orgTypeDropDown.setSelectedIndex(0);

                    for(OrgTypeInfo orgType:orgTypes){
                        orgTypeDropDown.addItem(orgType.getName(),orgType.getKey());
                    }
                }
          });
    }
}
