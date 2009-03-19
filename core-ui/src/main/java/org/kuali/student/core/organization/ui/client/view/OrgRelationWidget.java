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
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgRelationWidget extends Composite{
    
    String orgRelId = null;
    String orgRelType;
    String orgRelVersion;
    
    FlexTable fTable = null;
    
    SimplePanel root = new SimplePanel();
    
    KSFormLayoutPanel orgRelForm = null;
    
    ListBox orgRelTypeDropDown = null;

    KSModalDialogPanel searchPopup = new KSModalDialogPanel();
    
    boolean loaded = false;
    
    public OrgRelationWidget(){
        super.initWidget(root);
        
    }
    
    protected void onLoad(){
        if (!loaded){
            
            if (orgRelForm == null){
                initForm();
            }
            
            fTable = new FlexTable();
            fTable.setWidget(0,0, orgRelForm);

            OrgSearchWidget orgSearchWidget = new OrgSearchWidget();
            orgSearchWidget.addSelectionHandler(new SelectionHandler<OrgInfo>(){
                public void onSelection(SelectionEvent<OrgInfo> event) {
                    OrgInfo o = event.getSelectedItem();
                    orgRelForm.setFieldValue("relOrgName", o.getLongName());
                    orgRelForm.setFieldValue("relOrgId", o.getId());
                    searchPopup.hide();
                }
            });
            
            VerticalPanel popupContent = new VerticalPanel();
            popupContent.add(orgSearchWidget);
            popupContent.add(new KSButton("Cancel", new ClickHandler(){
                public void onClick(ClickEvent event) {
                    searchPopup.hide();
                }                
            }));
            
            searchPopup.setWidget(popupContent);
            
            KSButton searchButton = new KSButton("Find Org", new ClickHandler(){
                public void onClick(ClickEvent event) {
                    searchPopup.show();
                }                
            });
            fTable.setWidget(0,1, searchButton);
            fTable.getCellFormatter().setVerticalAlignment(0, 1, VerticalPanel.ALIGN_TOP);
            root.add(fTable);
            loaded = true;
        }
    }
    
    private void initForm(){
        orgRelForm = new KSFormLayoutPanel();
        
        orgRelTypeDropDown = new ListBox();
        loadOrgRelationTypes();        
        
        addFormField(new KSTextBox(), "Organization", "relOrgName");
        KSTextBox relOrgId = new KSTextBox();
        relOrgId.setEnabled(true);
        addFormField(relOrgId, "Organization Id", "relOrgId");
        addFormField(orgRelTypeDropDown, "Relationship", "relType");
        addFormField(new KSDatePicker(), "Effective date", "relEffDate");
        addFormField(new KSDatePicker(), "Expiration date", "relExpDate");
        addFormField(new KSTextArea(), "Note", "relNote");        
    }
    
    private void addFormField(Widget w, String label, String name){
        KSFormField ff = new KSFormField();
        ff.setLabelText(label);
        ff.setWidget(w);
        ff.setHelpInfo(new HelpInfo());
        ff.setName(name);
        
        orgRelForm.addFormField(ff);
    }

    public OrgOrgRelationInfo getOrgOrgRelationInfo(){       
        OrgOrgRelationInfo orgRelationInfo = new OrgOrgRelationInfo();        
        
        orgRelationInfo.setId(orgRelId);
        
        MetaInfo orgRelMetaInfo = new MetaInfo();
        orgRelMetaInfo.setVersionInd(orgRelVersion);
        orgRelationInfo.setMetaInfo(orgRelMetaInfo);
        
        orgRelationInfo.setType(orgRelTypeDropDown.getValue(orgRelTypeDropDown.getSelectedIndex()));
        
        //TODO: This should lookup orgId based on related org name
        orgRelationInfo.setRelatedOrgId(orgRelForm.getFieldValue("relOrgId"));
        
        orgRelationInfo.setEffectiveDate(((KSDatePicker)orgRelForm.getFieldWidget("relEffDate")).getDate());
        orgRelationInfo.setExpirationDate(((KSDatePicker)orgRelForm.getFieldWidget("relExpDate")).getDate());
        
        return orgRelationInfo;
    }
    
    public void setOrgOrgRelationInfo(OrgOrgRelationInfo orgRelationInfo){
        if (orgRelForm == null){
            initForm();
        }
        
        orgRelForm.setFieldValue("relOrgId",orgRelationInfo.getRelatedOrgId());
        orgRelId = orgRelationInfo.getId();
        orgRelType = orgRelationInfo.getType();
        orgRelVersion = orgRelationInfo.getMetaInfo().getVersionInd();
        
        OrgRpcService.Util.getInstance().getOrganization(orgRelationInfo.getRelatedOrgId(), 
                new AsyncCallback<OrgInfo>(){

                    public void onFailure(Throwable caught) {
                    }

                    public void onSuccess(OrgInfo orgInfo) {
                        orgRelForm.setFieldValue("relOrgName", orgInfo.getLongName());                       
                    }            
        });

        //Disable editing of rel org id and name
        ((FocusWidget)orgRelForm.getFieldWidget("relOrgId")).setEnabled(false);
        ((FocusWidget)orgRelForm.getFieldWidget("relOrgName")).setEnabled(false);

        ((KSDatePicker)orgRelForm.getFieldWidget("relEffDate")).setDate(orgRelationInfo.getEffectiveDate());
        ((KSDatePicker)orgRelForm.getFieldWidget("relExpDate")).setDate(orgRelationInfo.getExpirationDate());
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
                    orgRelTypeDropDown.addItem(orgRelTypeInfo.getName(),orgRelTypeInfo.getId());
                    if (orgRelTypeInfo.getId().equals(orgRelType)){
                        orgRelTypeDropDown.setSelectedIndex(i);
                    }
                }
            }
        });                
    }
    
}
