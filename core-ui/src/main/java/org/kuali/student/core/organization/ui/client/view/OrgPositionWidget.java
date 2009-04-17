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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

class OrgPositionWidget extends OrgMultiWidget {
    private ListItems orgPosTypeList;
    List<Map<String,Object>> forms = new ArrayList<Map<String,Object>>();
    
    public OrgPositionWidget() {
        this(null);
    }
    public OrgPositionWidget(String orgId) {
        this(orgId,false);
    }
    public OrgPositionWidget(String orgId, boolean open) {
        super(new KSDisclosureSection("Positions", null, open));
        if(orgPosTypeList == null)
            populateOrgPositionTypes();
        this.orgId = orgId;
        if(orgId == null)
            createBlankPosition();
        else {
            populatePositionInfos();
        }
    }
    
    public KSFormLayoutPanel createBlankPosition() {
        KSFormLayoutPanel orgForm = new KSFormLayoutPanel();

        final KSDropDown posTypeDropDown = new KSDropDown();

        addFormField(posTypeDropDown, "Position", "position", orgForm);
        addFormField(new KSTextBox(), "Title", "title", orgForm);
        addFormField(new KSTextArea(), "Description", "desc", orgForm);
        addFormField(new KSTextBox(), "Min people", "min", orgForm);
        addFormField(new KSTextBox(), "Max people", "max", orgForm);

        final HashMap<String, Object> map = new HashMap<String, Object>();
        
        add(orgForm, new CloseHandler<OrgMultiWidget>() {
            @Override
            public void onClose(CloseEvent<OrgMultiWidget> event) {
                map.put("deleted", Boolean.TRUE);
            }});
        
        if(orgPosTypeList == null)
            DeferredCommand.addCommand(new IncrementalCommand() {
                @Override
                public boolean execute() {
                    if(orgPosTypeList != null) {
                        posTypeDropDown.setListItems(orgPosTypeList);
                        return false;
                    }
                    return true;
                }});
        else
            posTypeDropDown.setListItems(orgPosTypeList);

        map.put("form",orgForm);
        forms.add(map);

        return orgForm;
    }

    private void populateOrgPositionTypes() {
        OrgRpcService.Util.getInstance().getOrgPersonRelationTypes(new AsyncCallback<List<OrgPersonRelationTypeInfo>>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(List<OrgPersonRelationTypeInfo> posTypes) {
                final Map<String,String> map = new LinkedHashMap<String, String>();
                for(OrgPersonRelationTypeInfo info : posTypes) {
                    map.put(info.getId(), info.getName());
                }
                orgPosTypeList = new ListItems() {

                    @Override
                    public List<String> getAttrKeys() {
                        return null; //apparently unused
                    }

                    @Override
                    public String getItemAttribute(String id, String attrkey) {
                        return null; //apparently unused
                    }

                    @Override
                    public int getItemCount() {
                        return map.size();
                    }

                    @Override
                    public List<String> getItemIds() {
                        return new ArrayList<String>(map.keySet());
                    }

                    @Override
                    public String getItemText(String id) {
                        return map.get(id);
                    }
                };
            }
        });
    }
    
    private void populatePositionInfos() {
        OrgRpcService.Util.getInstance().getPositionRestrictionsByOrg(orgId,
                new AsyncCallback<List<OrgPositionRestrictionInfo>>(){
        
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }
        
                    public void onSuccess(List<OrgPositionRestrictionInfo> orgPositions) {
                        for(OrgPositionRestrictionInfo info : orgPositions) {
                            final KSFormLayoutPanel orgPosForm = createBlankPosition();
                            Map<String,Object> map = forms.get(forms.size() - 1); //should always be the last one
                            map.put("posId",info.getId());
                            map.put("posVersion",info.getMetaInfo().getVersionInd());
                            
                            final String posType = info.getOrgPersonRelationTypeKey();
                            
                            orgPosForm.setFieldValue("title", info.getTitle());        
                            orgPosForm.setFieldValue("desc", info.getDesc());
                            
                            //TODO: Need to fix this
                            try{
                                //setFormValue("duration", orgPosRestriction.getStdDuration().getTimeQuantity());
                            } catch (Exception e){
                            }
                            try {
                                orgPosForm.setFieldValue("min", info.getMinNumRelations().toString());
                            } catch (Exception e) {
                                orgPosForm.setFieldValue("min", "");
                                // TODO: handle exception
                            }
                            
                            orgPosForm.setFieldValue("max",info.getMaxNumRelations()); 
                            
                            final KSDropDown posTypeDropDown = (KSDropDown) orgPosForm.getFieldWidget("position");
                            
                            if(posTypeDropDown.getListItems() != null)
                                posTypeDropDown.selectItem(posType);
                            else
                                DeferredCommand.addCommand(new IncrementalCommand() {
                                    @Override
                                    public boolean execute() {
                                        if(posTypeDropDown.getListItems() != null) {
                                            posTypeDropDown.selectItem(posType);
                                            return false;
                                        }
                                        return true;
                                    }});
                            posTypeDropDown.setEnabled(false);
                        }
                    }            
        });
    }
    
    @Override
    protected void save() {
        for (Map<String,Object> formMap : forms) {
            KSFormLayoutPanel orgPosForm = (KSFormLayoutPanel) formMap.get("form");
            if (orgPosForm.getFieldValue("title").trim().length() == 0)
                continue; //skipping this one
            OrgPositionRestrictionInfo orgPosRestriction = new OrgPositionRestrictionInfo();
            
            orgPosRestriction.setId((String) formMap.get("posId"));
            orgPosRestriction.setOrgId(orgId);
            
            MetaInfo posMetaInfo = new MetaInfo();
            posMetaInfo.setVersionInd((String) formMap.get("posVersion"));        
            orgPosRestriction.setMetaInfo(posMetaInfo);
            
            orgPosRestriction.setOrgPersonRelationTypeKey(orgPosForm.getFieldValue("position"));
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
            
            if (orgPosRestriction.getId() == null){
                OrgRpcService.Util.getInstance().addPositionRestrictionToOrg(orgPosRestriction,
                        new AsyncCallback<OrgPositionRestrictionInfo>(){
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }
        
                    public void onSuccess(final OrgPositionRestrictionInfo result) {
                        fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                            public String toString() {
                                return "Org position created with id: " + result.getId();
                            }
                        });
                    }
                });
            }else if(formMap.get("deleted") != null){
                OrgRpcService.Util.getInstance().removePositionRestrictionFromOrg(orgPosRestriction.getOrgId(), orgPosRestriction.getOrgPersonRelationTypeKey(), 
                        new AsyncCallback<StatusInfo>(){
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }
        
                    public void onSuccess(final StatusInfo result) {
                        fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                            public String toString() {
                                return "Org position deleted: " + result.getMessage();
                            }
                        });
                    }
                }); 
            }else{
                OrgRpcService.Util.getInstance().updatePositionRestrictionForOrg(orgPosRestriction,
                        new AsyncCallback<OrgPositionRestrictionInfo>(){
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }
        
                    public void onSuccess(final OrgPositionRestrictionInfo result) {
                        fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                            public String toString() {
                                return "Org position updated for: " + result.getId();
                            }
                        });
                    }
                });                    
            }
        }
    }

    @Override
    protected void create() {
        createBlankPosition();
    }
    @Override
    public int calculateSaveableWidgetCount() {
        int count = 0;
        for (Map<String,Object> formMap : forms) {
            KSFormLayoutPanel orgPosForm = (KSFormLayoutPanel) formMap.get("form");
            if (orgPosForm.getFieldValue("title").trim().length() == 0)
                continue; //skipping this one
            count++;
        }
        return count;
    }

}