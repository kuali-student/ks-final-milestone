/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.core.organization.ui.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HorizontalPanel;

class OrgRelationWidget extends OrgMultiWidget {
    private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
    
    private ListItems orgRelTypeList;
    
    List<Map<String,Object>> forms = new ArrayList<Map<String,Object>>();
    
    public OrgRelationWidget() {
        this(null);
    }
    public OrgRelationWidget(String orgId) {
        this(orgId,false);
    }
    public OrgRelationWidget(String orgId, boolean open) {
        super(new KSDisclosureSection("Relationships / Links", null, open));
        if(orgRelTypeList == null)
            populateOrgRelationTypes();
        this.orgId = orgId;
        if(orgId == null)
            createBlankRelation();
        else {
            populateRelationInfos();
        }
    }

    private void populateRelationInfos() {
        orgRpcServiceAsync.getOrgOrgRelationsByOrg(orgId, 
                new AsyncCallback<List<OrgOrgRelationInfo>>(){

                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    public void onSuccess(List<OrgOrgRelationInfo> orgRelations) {                   
                        for (OrgOrgRelationInfo orgRelationInfo:orgRelations) {
                            final KSFormLayoutPanel orgRelForm = createBlankRelation();
                            Map<String,Object> map = forms.get(forms.size() - 1); //should always be the last one
                            orgRelForm.setFieldValue("relOrgId",orgRelationInfo.getRelatedOrgId());
                            map.put("orgRelId",orgRelationInfo.getId());
                            map.put("orgRelVersion",orgRelationInfo.getMetaInfo().getVersionInd());
                            final String orgRelType = orgRelationInfo.getType();
                            
                            //TODO would be nicer to just have this info fetched with initial request
                            orgRpcServiceAsync.getOrganization(orgRelationInfo.getRelatedOrgId(), 
                                    new AsyncCallback<OrgInfo>(){

                                        public void onFailure(Throwable caught) {
                                        }

                                        public void onSuccess(OrgInfo orgInfo) {
                                            orgRelForm.setFieldValue("relOrgName", orgInfo.getLongName());                       
                                        }            
                            });
                            
                            final KSDropDown orgRelTypeDropDown = (KSDropDown) orgRelForm.getFieldWidget("relType");
                            
                            if(orgRelTypeDropDown.getListItems() != null)
                                orgRelTypeDropDown.selectItem(orgRelType);
                            else
                                DeferredCommand.addCommand(new IncrementalCommand() {
                                    @Override
                                    public boolean execute() {
                                        if(orgRelTypeDropDown.getListItems() != null) {
                                            orgRelTypeDropDown.selectItem(orgRelType);
                                            return false;
                                        }
                                        return true;
                                    }});

                            //Disable editing of rel org id and name
                            ((FocusWidget)orgRelForm.getFieldWidget("relOrgId")).setEnabled(false);
                            ((FocusWidget)orgRelForm.getFieldWidget("relOrgName")).setEnabled(false);
                            
                            orgRelForm.setFieldValue("relNote", orgRelationInfo.getAttributes().get("Note"));
                            
                            ((KSDatePicker)orgRelForm.getFieldWidget("relEffDate")).setValue(orgRelationInfo.getEffectiveDate());
                            ((KSDatePicker)orgRelForm.getFieldWidget("relExpDate")).setValue(orgRelationInfo.getExpirationDate());
                        }
                    }            
            });        
    }
    
    private void populateOrgRelationTypes() {
        orgRpcServiceAsync.getOrgOrgRelationTypes(new AsyncCallback<List<OrgOrgRelationTypeInfo>>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(List<OrgOrgRelationTypeInfo> orgRelTypes) {
                final Map<String,String> map = new LinkedHashMap<String, String>();
                for(OrgOrgRelationTypeInfo info : orgRelTypes) {
                    map.put(info.getId(), info.getName());
                }
                orgRelTypeList = new ListItems() {

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

    public KSFormLayoutPanel createBlankRelation() {
        HorizontalPanel panel = new HorizontalPanel();
        final KSFormLayoutPanel orgRelForm = new KSFormLayoutPanel();

        final KSDropDown orgRelTypeDropDown = new KSDropDown();

        addFormField(new KSTextBox(), "Organization", "relOrgName", orgRelForm);
        KSTextBox relOrgId = new KSTextBox();
        relOrgId.setEnabled(true);
        addFormField(relOrgId, "Organization Id", "relOrgId", orgRelForm);
        addFormField(orgRelTypeDropDown, "Relationship", "relType", orgRelForm);
        addFormField(new KSDatePicker(), "Effective date", "relEffDate", orgRelForm);
        addFormField(new KSDatePicker(), "Expiration date", "relExpDate", orgRelForm);
        addFormField(new KSTextArea(), "Note", "relNote", orgRelForm);

        panel.add(orgRelForm);
        panel.add(new KSButton("Find Org", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final KSAdvancedSearchWindow orgSearchPopup = new KSAdvancedSearchWindow(orgRpcServiceAsync, "org.search.orgQuickViewByHierarchyShortName", "org.resultColumn.orgId", "Find Organization");

                orgSearchPopup.addSelectionHandler(new SelectionHandler<List<String>>(){
                    @Override
                    public void onSelection(SelectionEvent<List<String>> event) {
                        String orgId = event.getSelectedItem().get(0);
                        orgRpcServiceAsync.getOrganization(orgId, new AsyncCallback<OrgInfo>(){
                            public void onFailure(Throwable caught) {
                                Window.alert(caught.getMessage());
                            }

                            public void onSuccess(OrgInfo orgInfo) {
                                orgRelForm.setFieldValue("relOrgName", orgInfo.getLongName());
                                orgRelForm.setFieldValue("relOrgId", orgInfo.getId());
                                orgSearchPopup.hide();
                            }            
                        });                                        
                    }
                });
                
                orgSearchPopup.show();
            }}));
        
        final HashMap<String, Object> map = new HashMap<String, Object>();
        
        add(panel, new CloseHandler<OrgMultiWidget>() {
            @Override
            public void onClose(CloseEvent<OrgMultiWidget> event) {
                map.put("deleted", Boolean.TRUE);
            }});
        
        if(orgRelTypeList == null)
            DeferredCommand.addCommand(new IncrementalCommand() {
                @Override
                public boolean execute() {
                    if(orgRelTypeList != null) {
                        orgRelTypeDropDown.setListItems(orgRelTypeList);
                        return false;
                    }
                    return true;
                }});
        else
            orgRelTypeDropDown.setListItems(orgRelTypeList);

        map.put("form",orgRelForm);
        forms.add(map);
        return orgRelForm;
    }

    @Override
    protected void save() {
        for (Map<String,Object> formMap : forms) {
            KSFormLayoutPanel form = (KSFormLayoutPanel) formMap.get("form");
            if (form.getFieldText("relOrgId").length() == 0)
                continue; //skipping this one
            if(formMap.get("orgRelId") == null && formMap.get("deleted") != null)
                continue; //if not created AND deleted
            OrgOrgRelationInfo orgRelationInfo = new OrgOrgRelationInfo();
            
            orgRelationInfo.setOrgId(orgId);
            orgRelationInfo.setId((String) formMap.get("orgRelId"));
            
            MetaInfo orgRelMetaInfo = new MetaInfo();
            orgRelMetaInfo.setVersionInd((String) formMap.get("orgRelVersion"));
            orgRelationInfo.setMetaInfo(orgRelMetaInfo);
            
            orgRelationInfo.setType((String)form.getFieldValue("relType"));
            
            //TODO: This should lookup orgId based on related org name
            orgRelationInfo.setRelatedOrgId((String)form.getFieldValue("relOrgId"));
            
            orgRelationInfo.setEffectiveDate((Date)form.getFieldValue("relEffDate"));
            orgRelationInfo.setExpirationDate((Date)form.getFieldValue("relExpDate"));
            
            orgRelationInfo.getAttributes().put("Note", (String) form.getFieldValue("relNote"));
            
            if (orgRelationInfo.getId() == null){
                orgRpcServiceAsync.createOrgOrgRelation(orgRelationInfo, 
                        new AsyncCallback<OrgOrgRelationInfo>(){
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }
        
                    public void onSuccess(final OrgOrgRelationInfo result) {
                        fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                            public String toString() {
                                return "Org relation created with id: " + result.getId();
                            }
                        });
                    }
                });
            }else if(formMap.get("deleted") != null){
                orgRpcServiceAsync.removeOrgOrgRelation(orgRelationInfo.getId(), 
                        new AsyncCallback<StatusInfo>(){
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }
        
                    public void onSuccess(final StatusInfo result) {
                        fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                            public String toString() {
                                return "Org relation deleted: " + result.getMessage();
                            }
                        });
                    }
                });
            }else{
                orgRpcServiceAsync.updateOrgOrgRelation(orgRelationInfo, 
                        new AsyncCallback<OrgOrgRelationInfo>(){
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }
        
                    public void onSuccess(final OrgOrgRelationInfo result) {
                        fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                            public String toString() {
                                return "Org relation updated with id: " + result.getId();
                            }
                        });
                    }
                });
            }
        }
    }

    @Override
    protected void create() {
        createBlankRelation();
    }
    @Override
    public int calculateSaveableWidgetCount() {
        int count = 0;
        for (Map<String,Object> formMap : forms) {
            KSFormLayoutPanel form = (KSFormLayoutPanel) formMap.get("form");
            if (form.getFieldText("relOrgId").length() == 0)
                continue; //skipping this one
            if(formMap.get("orgRelId") == null && formMap.get("deleted") != null)
                continue; //if not created AND deleted
           count++;
        }
        return count;
    }
}
