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
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.web.client.service.OrgRpcService;

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
public class OrgPositionWidget extends Composite {

    ListBox posTypeDropDown;
    TextBox posTitle;
    TextArea posDesc;
    TextBox posMinRelation;
    TextBox posDuration;
    TextBox posMaxRelation;
    TextBox atpDurationType;

    FlexTable fTable = null;     
   
    SimplePanel root = new SimplePanel();
    
    String posType;
    String posId;
    String orgType;
    
    public OrgPositionWidget(){
        super.initWidget(root);
        
        posTitle = new TextBox();
        posDesc = new TextArea();
        posMinRelation = new TextBox();
        posMaxRelation = new TextBox();
        posDuration = new TextBox();
        atpDurationType = new TextBox();        
    }
       
    protected void onLoad(){
        fTable = new FlexTable();
        posTypeDropDown = new ListBox();
        loadPersonRelationTypes();
               
        fTable.setWidget(0,0, new Label("Position"));
        fTable.setWidget(0,1, posTypeDropDown);
        
        fTable.setWidget(1,0, new Label("Title"));
        fTable.setWidget(1,1, posTitle);

        fTable.setWidget(2,0, new Label("Description"));
        fTable.setWidget(2,1, posDesc);
               
        fTable.setWidget(4,0, new Label("Min people"));
        fTable.setWidget(4,1, posMinRelation);

        fTable.setWidget(5,0, new Label("Max people"));
        fTable.setWidget(5,1, posMaxRelation);

        root.add(fTable);
    }

    public OrgPositionRestrictionInfo getPositionRestrictionInfo(){
        OrgPositionRestrictionInfo orgPosRestriction = new OrgPositionRestrictionInfo();
        
        orgPosRestriction.setId(posId);
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
        posType = orgPosRestriction.getOrgPersonRelationTypeKey();
        posTitle.setText(orgPosRestriction.getTitle());
        posDesc.setText(orgPosRestriction.getDesc());
        posDuration.setText(orgPosRestriction.getStdDuration().getTimeQuantity().toString());
        posMinRelation.setText(orgPosRestriction.getMinNumRelations().toString());       
        posMaxRelation.setText(orgPosRestriction.getMaxNumRelations());        
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
     
}
