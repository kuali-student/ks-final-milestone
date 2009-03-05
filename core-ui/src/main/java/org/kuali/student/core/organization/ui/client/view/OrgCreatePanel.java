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

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.FormField;
import org.kuali.student.common.ui.client.widgets.forms.FormLayoutPanel;
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
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
    public static final String CREATE_ORG_RELATIONS = "Relations";
    
    String type;
    String orgId = null;
    String orgType = null;
    String orgVersion = null;
    
    ListBox orgTypeDropDown;
    FormLayoutPanel orgForm = new FormLayoutPanel();
    
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
               
        orgTypeDropDown = new ListBox();      
               
        addFormField(orgTypeDropDown,"Type", "orgType");
        addFormField(new KSTextBox(), "Name", "orgName");
        addFormField(new KSTextBox(), "Abbreviation", "orgAbbrev");
        addFormField(new KSTextArea(), "Description", "orgDesc");
        addFormField(new KSDatePicker(), "Effective Date", "orgEffDate");
        addFormField(new KSDatePicker(), "Expiration Date", "orgExpDate");
                   
        vOrganization.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        vOrganization.setWidth("100%");      
        vOrganization.add(orgForm);
        
        KSDisclosureSection orgSection = new KSDisclosureSection("Organization",null, true);
        orgSection.add(vOrganization);
        
        vPanel.add(orgSection);
        
        if (orgId != null){
            populateOrgInfo();
        }
        
        populateOrgTypes();
    }
    
    private void addFormField(Widget w, String label, String name){
        FormField ff = new FormField();
        ff.setLabelText(label);
        ff.setWidget(w);
        //((HasName)w).setName(name);
        ff.setHelpInfo(new HelpInfo());
        ff.setName(name);
        
        orgForm.addFormField(ff);
    }    
    
    protected void getOrgPositions(boolean bShowAll){
        KSDisclosureSection posSection = new KSDisclosureSection("Positions",null, type.equals(CREATE_ORG_POSITIONS));
        
        vPositions = new VerticalPanel();
        vPositions.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        vPositions.setWidth("100%");
        
        if (orgId != null && bShowAll){
            populateOrgPositions();
        } else {
            vPositions.add(new OrgPositionWidget());
            vPositions.add(getPositionLink());            
        }
        
        posSection.add(vPositions);
        vPanel.add(posSection);
    }

    protected void getOrgRelations(boolean bShowAll){
        KSDisclosureSection relationSection = new KSDisclosureSection("Relations",null, false);
        
        vRelations = new VerticalPanel();
        vRelations.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        vRelations.setWidth("100%");        
        
        if (orgId != null && bShowAll){
            populateOrgRelations();
        } else {        
            vRelations.add( new OrgRelationWidget());
            vRelations.add(getRelationLink());
        }
              
        relationSection.add(vRelations);
        vPanel.add(relationSection);

    }
    
    protected void saveOrganization(){       
        OrgInfo orgInfo = new OrgInfo();
        
        MetaInfo orgMetaInfo = new MetaInfo();
        orgMetaInfo.setVersionInd(orgVersion);
        orgInfo.setMetaInfo(orgMetaInfo);
        
        orgInfo.setType(orgTypeDropDown.getValue((orgTypeDropDown.getSelectedIndex())));        
        orgInfo.setShortDesc(orgForm.getFieldValue("orgDesc"));
        orgInfo.setLongName(orgForm.getFieldValue("orgName"));
        orgInfo.setShortName(orgForm.getFieldValue("orgAbbrev"));

        orgInfo.setEffectiveDate(((KSDatePicker)orgForm.getFieldWidget("orgEffDate")).getDate());
        orgInfo.setExpirationDate(((KSDatePicker)orgForm.getFieldWidget("orgExpDate")).getDate());
        
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
                        orgTypeDropDown.addItem(orgTypeInfo.getName(),orgTypeInfo.getId());
                        if (orgTypeInfo.getId().equals(orgType)){
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
                orgVersion = orgInfo.getMetaInfo().getVersionInd();
                orgType = orgInfo.getType();
                orgForm.setFieldValue("orgName",orgInfo.getLongName());
                orgForm.setFieldValue("orgAbbrev",orgInfo.getShortName());
                orgForm.setFieldValue("orgDesc",orgInfo.getShortDesc());

                ((KSDatePicker)orgForm.getFieldWidget("orgEffDate")).setDate(orgInfo.getEffectiveDate());
                ((KSDatePicker)orgForm.getFieldWidget("orgExpDate")).setDate(orgInfo.getExpirationDate());
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
                        if (orgPositions != null){
                            for (OrgPositionRestrictionInfo orgPos:orgPositions){
                                if ( i > 0){
                                    vPositions.add(new HTML("<hr/>"));
                                }
                                OrgPositionWidget orgPositionWidget = new OrgPositionWidget();
                                orgPositionWidget.setOrgPositionRestrictionInfo(orgPos);
                                vPositions.add(orgPositionWidget);
                                i++;
                            }
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
