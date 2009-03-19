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
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgPositionWidget extends Composite {

    HTML divider = new HTML("<hr/>");

    KSFormLayoutPanel orgPosForm = null;
    
    FlexTable fTable = null;     
   
    DockPanel root = new DockPanel();

    ListBox posTypeDropDown;
    
    String posType;
    String posId;
    String posVersion;
    String orgType;
    
    boolean loaded = false;
       
    public OrgPositionWidget(){
        super.initWidget(root);       
    }
       
    protected void onLoad(){
        if (!loaded){
            if (orgPosForm == null){
                initForm();
            }               
            
            fTable = new FlexTable();
            fTable.setWidget(0,0,orgPosForm);
            if (posId != null){
                fTable.setWidget(0,1, getRemoveLink());
            }
            
            divider.setVisible(false);
            root.setWidth("50%");
            root.add(divider, DockPanel.NORTH);
            root.add(fTable,DockPanel.CENTER);
            root.setCellHorizontalAlignment(fTable, DockPanel.ALIGN_CENTER);
            root.setStyleName("ks-position");
            
            loaded = true;
        }
    }
    
    protected void initForm(){
        orgPosForm = new KSFormLayoutPanel();        
        
        posTypeDropDown = new ListBox();
        loadPersonRelationTypes();                              
        
        addFormField(posTypeDropDown,"Position", "position");
        addFormField(new KSTextBox(), "Title", "title");
        addFormField(new KSTextArea(), "Description", "desc");
        addFormField(new KSTextBox(), "Min people", "min");
        addFormField(new KSTextBox(), "Max people", "max");        
    }

    private void addFormField(Widget w, String label, String name){
        KSFormField ff = new KSFormField();
        ff.setLabelText(label);
        ff.setWidget(w);
        ((HasName)w).setName(name);
        ff.setHelpInfo(new HelpInfo());
        ff.setName(name);
        
        orgPosForm.addFormField(ff);
    }
    
    public OrgPositionRestrictionInfo getPositionRestrictionInfo(){
        OrgPositionRestrictionInfo orgPosRestriction = new OrgPositionRestrictionInfo();
        
        orgPosRestriction.setId(posId);
        
        MetaInfo posMetaInfo = new MetaInfo();
        posMetaInfo.setVersionInd(posVersion);        
        orgPosRestriction.setMetaInfo(posMetaInfo);
        
        orgPosRestriction.setOrgPersonRelationTypeKey(posTypeDropDown.getValue(posTypeDropDown.getSelectedIndex()));
        orgPosRestriction.setTitle(orgPosForm.getFieldValue("title"));
        orgPosRestriction.setDesc(orgPosForm.getFieldValue("desc"));
        
        /*
        TimeAmountInfo durationTimeAmtInfo = new TimeAmountInfo();
        durationTimeAmtInfo.setAtpDurationTypeKey(atpDurationType.getText());
        try {
            durationTimeAmtInfo.setTimeQuantity(Integer.valueOf(posDuration.getText()));            
        } catch (NumberFormatException e) {
        }      
        orgPosRestriction.setStdDuration(durationTimeAmtInfo);
        */
        
        try {
            orgPosRestriction.setMinNumRelations(Integer.valueOf(orgPosForm.getFieldValue("min")));
        } catch (NumberFormatException e) {

        }
        orgPosRestriction.setMaxNumRelations(orgPosForm.getFieldValue("max"));
               
        return orgPosRestriction;        
    }
    
    protected void setOrgPositionRestrictionInfo(OrgPositionRestrictionInfo orgPosRestriction){
        if (orgPosForm == null){
            initForm();
        }
        
        posId = orgPosRestriction.getId();
        posVersion = orgPosRestriction.getMetaInfo().getVersionInd();
        posType = orgPosRestriction.getOrgPersonRelationTypeKey();
        
        orgPosForm.setFieldValue("title", orgPosRestriction.getTitle());        
        orgPosForm.setFieldValue("desc", orgPosRestriction.getDesc());
        
        //TODO: Need to fix this
        try{
            //setFormValue("duration", orgPosRestriction.getStdDuration().getTimeQuantity());
        } catch (Exception e){
        }
        try {
            orgPosForm.setFieldValue("min", orgPosRestriction.getMinNumRelations().toString());
        } catch (Exception e) {
            orgPosForm.setFieldValue("min", "");
            // TODO: handle exception
        }
        
        orgPosForm.setFieldValue("max",orgPosRestriction.getMaxNumRelations()); 
        
        posTypeDropDown.setEnabled(false);
    }
        
    protected void loadPersonRelationTypes(){
        OrgRpcService.Util.getInstance().getOrgPersonRelationTypes(new AsyncCallback<List<OrgPersonRelationTypeInfo>>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(List<OrgPersonRelationTypeInfo> posTypes) {
                posTypeDropDown.addItem("Select", "");
               
                int i =0;
                for(OrgPersonRelationTypeInfo posTypeInfo:posTypes){
                    i++;
                    posTypeDropDown.addItem(posTypeInfo.getName(),posTypeInfo.getId());
                    if (posTypeInfo.getId().equals(posType)){
                        posTypeDropDown.setSelectedIndex(i);
                    }
                }
            }
        });        
    }
     
    protected Widget getRemoveLink(){
        Hyperlink hLink = new Hyperlink("(-)remove","");
        hLink.setStyleName("action");
        
        hLink.addClickHandler(new ClickHandler(){

            public void onClick(ClickEvent event) {
                //TODO: Add call to remove position
            }            
        });
        
        return hLink;
    }
    
    public void setDividerEnabled(boolean visible){
        divider.setVisible(visible);
    }
}
