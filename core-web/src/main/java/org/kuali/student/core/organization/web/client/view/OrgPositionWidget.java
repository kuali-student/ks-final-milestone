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

import org.kuali.student.core.atp.dto.TimeAmountInfo;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.web.client.service.OrgRpcService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgPositionWidget extends Composite {

    ListBox posTypeDropDown;
    TextBox posTitle;
    TextArea posDesc;
    TextBox posMinRelation;
    TextBox posDuration;
    TextBox posMaxRelation;
    TextBox atpDurationType;
    HTML divider = new HTML("<hr/>");

    FlexTable fTable = null;     
   
    DockPanel root = new DockPanel();
    
    String posType;
    String posId;
    String posVersion;
    String orgType;
       
    public OrgPositionWidget(){
        super.initWidget(root);
        
        posTypeDropDown = new ListBox();
        posTitle = new TextBox();
        posDesc = new TextArea();
        posMinRelation = new TextBox();
        posMaxRelation = new TextBox();
        posDuration = new TextBox();
        atpDurationType = new TextBox();        

    }
       
    protected void onLoad(){
        fTable = new FlexTable();

        loadPersonRelationTypes();
               
        fTable.setWidget(0,0, new Label("Position"));
        fTable.setWidget(0,1, posTypeDropDown);
        
        if (posId != null){
            fTable.setWidget(0,2, getRemoveLink());
        }
        
        fTable.setWidget(1,0, new Label("Title"));
        fTable.setWidget(1,1, posTitle);

        fTable.setWidget(2,0, new Label("Description"));
        fTable.setWidget(2,1, posDesc);
               
        fTable.setWidget(4,0, new Label("Min people"));
        fTable.setWidget(4,1, posMinRelation);

        fTable.setWidget(5,0, new Label("Max people"));
        fTable.setWidget(5,1, posMaxRelation);

       
        root.setWidth("50%");
        root.add(divider, DockPanel.NORTH);
        root.add(fTable,DockPanel.CENTER);
        root.setCellHorizontalAlignment(fTable, DockPanel.ALIGN_CENTER);
        root.setStyleName("ks-position");
        
        divider.setVisible(false);
    }

    public OrgPositionRestrictionInfo getPositionRestrictionInfo(){
        OrgPositionRestrictionInfo orgPosRestriction = new OrgPositionRestrictionInfo();
        
        orgPosRestriction.setId(posId);
        
        MetaInfo posMetaInfo = new MetaInfo();
        posMetaInfo.setVersionInd(posVersion);        
        orgPosRestriction.setMetaInfo(posMetaInfo);
        
        orgPosRestriction.setOrgPersonRelationTypeKey(posTypeDropDown.getValue(posTypeDropDown.getSelectedIndex()));
        orgPosRestriction.setTitle(posTitle.getText());
        orgPosRestriction.setDesc(posDesc.getText());
        
        TimeAmountInfo durationTimeAmtInfo = new TimeAmountInfo();
        durationTimeAmtInfo.setAtpDurationTypeKey(atpDurationType.getText());
        try {
            durationTimeAmtInfo.setTimeQuantity(Integer.valueOf(posDuration.getText()));            
        } catch (NumberFormatException e) {
        }      

        orgPosRestriction.setStdDuration(durationTimeAmtInfo);        
        try {
            orgPosRestriction.setMinNumRelations(Integer.valueOf(posMinRelation.getText()));
        } catch (NumberFormatException e) {

        }
        orgPosRestriction.setMaxNumRelations(posMaxRelation.getText());
               
        return orgPosRestriction;        
    }
    
    protected void setOrgPositionRestrictionInfo(OrgPositionRestrictionInfo orgPosRestriction){
        posId = orgPosRestriction.getId();
        posVersion = orgPosRestriction.getMetaInfo().getVersionInd();
        posType = orgPosRestriction.getOrgPersonRelationTypeKey();
        posTitle.setText(orgPosRestriction.getTitle());
        posDesc.setText(orgPosRestriction.getDesc());
        //TODO: Need to fix this
        try{
            posDuration.setText(String.valueOf(orgPosRestriction.getStdDuration().getTimeQuantity()));
        } catch (Exception e){
        }
        try {
            posMinRelation.setText(orgPosRestriction.getMinNumRelations().toString());
        } catch (Exception e) {
            posMinRelation.setText("");
            // TODO: handle exception
        }
        posMaxRelation.setText(orgPosRestriction.getMaxNumRelations()); 
        
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
                    posTypeDropDown.addItem(posTypeInfo.getName(),posTypeInfo.getKey());
                    if (posTypeInfo.getKey().equals(posType)){
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
