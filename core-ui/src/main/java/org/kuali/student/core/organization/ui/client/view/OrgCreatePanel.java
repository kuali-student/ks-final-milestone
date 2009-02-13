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
package org.kuali.student.core.organization.ui.client.view;

import java.util.List;

import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

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
    public static final String CREATE_ORG_RELATIONS = "Relations";
    
    String type;
    String orgId = null;
    String orgType = null;
    String orgVersion = null;
    
    ListBox orgTypeDropDown;
    TextBox orgName;
    TextBox orgAbbrev;
    TextArea orgDesc;
    
    //These should be date boxes
    TextBox orgEffectiveDate;
    TextBox orgExpirationDate;
    
    VerticalPanel vOrganization;
    VerticalPanel vPositions;
    VerticalPanel vRelations;    
    
    boolean loaded = false;
    
    
    public OrgCreatePanel(String type){
        this.type=type;
        super.initWidget(vPanel);
    }
    

    protected void onLoad(){
        vPanel.setWidth("100%");
        //vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        if (!loaded){
            loaded = true;
            if (type.equals(CREATE_ORG_ALL)){
                getOrganization();
                getOrgPositions(true);
                getOrgRelations(true);
            } else if (type.equals(CREATE_ORG_POSITIONS)){
                getOrgPositions(false);            
            } else if (type.equals(CREATE_ORG_RELATIONS)){
                getOrgRelations(false); 
            }
            
            Button btnCreateOrg = new Button("Create");
            
            if (orgId != null){
                btnCreateOrg.setText("Update");
            }
            
            btnCreateOrg.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                    vPanel.clear();
                    
                    if (type.equals(CREATE_ORG_ALL)){
                        saveOrganization();
                    } else if (type.equals(CREATE_ORG_POSITIONS)){
                        savePositions();            
                    } else if (type.equals(CREATE_ORG_RELATIONS)){
                        saveRelations();
                    }
                }
            });

            vPanel.add(btnCreateOrg);
        }
    }
    
    protected void getOrganization(){
        vOrganization = new VerticalPanel();
        
        FlexTable fTable = new FlexTable();
        
        orgTypeDropDown = new ListBox();      
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

        vOrganization.setWidth("100%");
        vOrganization.setStyleName("ks-section");
        vOrganization.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        vOrganization.add(fTable);
        
        vPanel.add(new SectionLabel("Organization"));
        vPanel.add(vOrganization);
        
        if (orgId != null){
            populateOrgInfo();
        }
        
        populateOrgTypes();
    }
    
    
    protected void getOrgPositions(boolean bShowAll){
        vPanel.add(new SectionLabel("Positions"));
        
        vPositions = new VerticalPanel();
        vPositions.setStyleName("ks-section");
        vPositions.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        
        if (orgId != null && bShowAll){
            populateOrgPositions();
        } else {
            vPositions.add(new OrgPositionWidget());
            vPositions.add(getPositionLink());            
        }
         
        vPanel.add(vPositions);
    }

    protected void getOrgRelations(boolean bShowAll){
        vPanel.add(new SectionLabel("Relationships"));

        vRelations = new VerticalPanel();
        vRelations.setStyleName("ks-section");
        vRelations.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        
        if (orgId != null && bShowAll){
            populateOrgRelations();
        } else {        
            vRelations.add( new OrgRelationWidget());
            vRelations.add(getRelationLink());
        }
              
        vPanel.add(vRelations);

    }
    
    protected void saveOrganization(){
        DateTimeFormat dateFmt = DateTimeFormat.getFormat("MM/dd/yyyy");
        
        OrgInfo orgInfo = new OrgInfo();
        
        MetaInfo orgMetaInfo = new MetaInfo();
        orgMetaInfo.setVersionInd(orgVersion);
        orgInfo.setMetaInfo(orgMetaInfo);
        
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
        
        if (orgId == null){
            OrgRpcService.Util.getInstance().createOrganization(orgInfo,new AsyncCallback<OrgInfo>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }
    
                public void onSuccess(OrgInfo result) {
                    orgId = result.getId();
                    savePositions();
                    saveRelations();
                    vPanel.add(new Label("Org created with id: " + orgId));
                }
            });
        } else {
            orgInfo.setId(orgId);
            OrgRpcService.Util.getInstance().updateOrganization(orgInfo,new AsyncCallback<OrgInfo>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }
    
                public void onSuccess(OrgInfo result) {
                    savePositions();
                    saveRelations();
                    vPanel.add(new Label("Organization saved"));
                }
            });            
        }
    }
    
    protected void savePositions(){
        //Only every other widget is a orgPositionWidget
        int widgetCount = vPositions.getWidgetCount();
        for (int i=0; i < widgetCount && widgetCount > 1; i+=2){            
            OrgPositionWidget orgPosWidget = (OrgPositionWidget)vPositions.getWidget(i);
            OrgPositionRestrictionInfo orgPosRestrictionInfo = orgPosWidget.getPositionRestrictionInfo();
            
            if (orgPosRestrictionInfo.getTitle().length() > 0){
                orgPosRestrictionInfo.setOrgId(orgId);
                if (orgPosRestrictionInfo.getId() == null){
                    OrgRpcService.Util.getInstance().addPositionRestrictionToOrg(orgPosRestrictionInfo,
                            new AsyncCallback<OrgPositionRestrictionInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
            
                        public void onSuccess(OrgPositionRestrictionInfo result) {
                            vPanel.add(new Label("Org position created with id: " + result.getId()));                    
                        }
                    });
                } else {
                    OrgRpcService.Util.getInstance().updatePositionRestrictionForOrg(orgPosRestrictionInfo,
                            new AsyncCallback<OrgPositionRestrictionInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
            
                        public void onSuccess(OrgPositionRestrictionInfo result) {
                            vPanel.add(new Label("Org position updated for: " + result.getId()));                    
                        }
                    });                    
                }
            }
        }
    }
    
    protected void saveRelations(){
        int widgetCount = vRelations.getWidgetCount();
        for (int i=0; i < widgetCount && widgetCount > 1; i+=2){        
            OrgRelationWidget orgRelationWidget = (OrgRelationWidget)vRelations.getWidget(i);
            OrgOrgRelationInfo orgRelationInfo = orgRelationWidget.getOrgOrgRelationInfo();
            
            if (orgRelationInfo.getRelatedOrgId().length() > 0){
                orgRelationInfo.setOrgId(orgId);                
                if (orgRelationInfo.getId() == null){
                    OrgRpcService.Util.getInstance().createOrgOrgRelation(orgRelationInfo, 
                            new AsyncCallback<OrgOrgRelationInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
            
                        public void onSuccess(OrgOrgRelationInfo result) {
                            vPanel.add(new Label("Org relation created with id: " + result.getId()));
                        }
                    });
                } else {
                    OrgRpcService.Util.getInstance().updateOrgOrgRelation(orgRelationInfo, 
                            new AsyncCallback<OrgOrgRelationInfo>(){
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }
            
                        public void onSuccess(OrgOrgRelationInfo result) {
                            vPanel.add(new Label("Org relation updated for id: " + result.getId()));
                        }
                    });
                }
            }
        }
    }
    
    protected void populateOrgTypes(){
        OrgRpcService.Util.getInstance().getOrgTypes(new AsyncCallback<List<OrgTypeInfo>>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(List<OrgTypeInfo> orgTypes) {
                    orgTypeDropDown.addItem("Select", "");
                    int i=0;
                    for(OrgTypeInfo orgTypeInfo:orgTypes){
                        i++;
                        orgTypeDropDown.addItem(orgTypeInfo.getName(),orgTypeInfo.getKey());
                        if (orgTypeInfo.getKey().equals(orgType)){
                            orgTypeDropDown.setSelectedIndex(i);
                        }
                    }
                }
          });
    }

    protected void populateOrgInfo(){
        OrgRpcService.Util.getInstance().getOrganization(orgId, new AsyncCallback<OrgInfo>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(OrgInfo orgInfo) {
                DateTimeFormat dateFmt = DateTimeFormat.getFormat("MM/dd/yyyy");                
                
                orgVersion = orgInfo.getMetaInfo().getVersionInd();
                orgType = orgInfo.getType();
                orgName.setText(orgInfo.getLongName());
                orgAbbrev.setText(orgInfo.getShortName());
                orgDesc.setText(orgInfo.getDesc());
                try{
                    orgEffectiveDate.setText(dateFmt.format(orgInfo.getEffectiveDate()));
                } catch (Exception e) {
                }
                try {
                    orgExpirationDate.setText(dateFmt.format(orgInfo.getExpirationDate()));
                } catch (Exception e) {
                }                
            }            
        });
    }

    protected void populateOrgPositions(){
        OrgRpcService.Util.getInstance().getPositionRestrictionsByOrg(orgId, 
                new AsyncCallback<List<OrgPositionRestrictionInfo>>(){
        
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }
        
                    public void onSuccess(List<OrgPositionRestrictionInfo> orgPositions) {                   
                        int i = 0;
                        for (OrgPositionRestrictionInfo orgPos:orgPositions){
                            if ( i > 0){
                                vPositions.add(new HTML("<hr/>"));
                            }
                            OrgPositionWidget orgPositionWidget = new OrgPositionWidget();
                            orgPositionWidget.setOrgPositionRestrictionInfo(orgPos);
                            vPositions.add(orgPositionWidget);
                            i++;
                        }
                        vPositions.add(getPositionLink());
                    }            
        });
    }
    
    protected void populateOrgRelations(){
        OrgRpcService.Util.getInstance().getOrgOrgRelationsByOrg(orgId, 
                new AsyncCallback<List<OrgOrgRelationInfo>>(){

                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    public void onSuccess(List<OrgOrgRelationInfo> orgRelations) {                   
                        int i = 0;
                        for (OrgOrgRelationInfo orgRelationInfo:orgRelations){
                            if ( i > 0){
                                vRelations.add(new HTML("<hr/>"));
                            }
                            OrgRelationWidget orgRelationWidget = new OrgRelationWidget();
                            orgRelationWidget.setOrgOrgRelationInfo(orgRelationInfo);
                            vRelations.add(orgRelationWidget);
                            i++;
                        }
                        vRelations.add(getRelationLink());
                    }            
            });        
    }
    
    protected Hyperlink getPositionLink(){
        Hyperlink addPositionLink = new Hyperlink("(+)add position", "addPos");
        addPositionLink.setStyleName("action");
        addPositionLink.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                if (vPositions.getWidgetCount() > 1){
                    vPositions.insert(new HTML("<hr/>"), vPositions.getWidgetCount()-1);
                }
                vPositions.insert(new OrgPositionWidget(), vPositions.getWidgetCount()-1);
            }
        });
        
        return addPositionLink;
    }
    
    
    protected Hyperlink getRelationLink(){
        Hyperlink addRelationLink = new Hyperlink("(+)add relation", "addRelation");
        addRelationLink.setStyleName("action");
        addRelationLink.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                if (vRelations.getWidgetCount() > 1){
                    vRelations.insert(new HTML("<hr/>"), vRelations.getWidgetCount()-1);
                }
                vRelations.insert(new OrgRelationWidget(), vRelations.getWidgetCount()-1);
            }
        });
        
        return addRelationLink; 
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
 
    
}
