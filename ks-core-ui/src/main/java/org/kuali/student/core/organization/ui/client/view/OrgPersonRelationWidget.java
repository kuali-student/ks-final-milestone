/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.mvc.CollectionModel;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.ModelListItems;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

class OrgPersonRelationWidget extends OrgMultiWidget {
    private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
    
    private ListItems orgPersonRelTypeList;
    
    List<Map<String,Object>> forms = new ArrayList<Map<String,Object>>();
    
    public OrgPersonRelationWidget() {
        this(null);
    }
    public OrgPersonRelationWidget(String orgId) {
        this(orgId,false);
    }
    public OrgPersonRelationWidget(String orgId, boolean open) {
        this(orgId, null, open);
    }
    public OrgPersonRelationWidget(final String orgId, final CollectionModel<OrgPositionRestrictionInfo> model, final boolean open) {
        super(new KSDisclosureSection("Membership", null, open));
        this.orgId = orgId;
        if(orgId != null) { //basically can't do much without an orgId
            if(model != null) {
                orgPersonRelTypeList = new ModelListItems<OrgPositionRestrictionInfo>() {
                    @Override
                    public List<String> getAttrKeys() {
                        return Arrays.asList("id");
                    }

                    @Override
                    public String getItemAttribute(String id, String attrkey) {
                        if("id".equals(attrkey) && id != null)
                            for(OrgPositionRestrictionInfo info : listItems)
                                if(id.equals(info.getOrgPersonRelationTypeKey()))
                                    return info.getId();
                        return null; //unused
                    }

                    @Override
                    public List<String> getItemIds() {
                        List<String> list = new ArrayList<String>();
                        for(OrgPositionRestrictionInfo info : listItems)
                            list.add(info.getOrgPersonRelationTypeKey());
                        return list;
                    }

                    @Override
                    public String getItemText(String id) {
                        if(id == null)
                            return null;
                        for(OrgPositionRestrictionInfo info : listItems)
                            if(id.equals(info.getOrgPersonRelationTypeKey()))
                                return info.getTitle();
                        return null;
                    }};
                ((ModelListItems<OrgPositionRestrictionInfo>)orgPersonRelTypeList).setModel(model);
            }
            else
                populateRelationshipTypes();
            populatePersonRelationInfos();
        }
    }
    
    public KSFormLayoutPanel createBlankPersonRelation() {
        HorizontalPanel panel = new HorizontalPanel();
        final KSFormLayoutPanel orgPersonRelForm = new KSFormLayoutPanel();

        final KSDropDown orgPersonRelTypeDropDown = new KSDropDown();

        addFormField(new KSTextBox(), "Person", "relPersonName", orgPersonRelForm);
        KSTextBox relPersonId = new KSTextBox();
        relPersonId.setEnabled(true);
        addFormField(relPersonId, "Person Id", "relPersonId", orgPersonRelForm);
        addFormField(orgPersonRelTypeDropDown, "Relationship", "relType", orgPersonRelForm);
        addFormField(new KSDatePicker(), "Effective date", "relEffDate", orgPersonRelForm);
        addFormField(new KSDatePicker(), "Expiration date", "relExpDate", orgPersonRelForm);

        panel.add(orgPersonRelForm);
        panel.add(new KSButton("Find Person", new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final KSModalDialogPanel searchPopup = new KSModalDialogPanel();
                
//                PersonSearchWidget personSearchWidget = new PersonSearchWidget();
//                personSearchWidget.addSelectionHandler(new SelectionHandler<PersonInfo>(){
//                    public void onSelection(SelectionEvent<PersonInfo> event) {
//                        PersonInfo o = event.getSelectedItem();
//                        orgPersonRelForm.setFieldValue("relPersonName", o.getPersonNameInfoList().get(0).getGivenName());
//                        orgPersonRelForm.setFieldValue("relPersonId", o.getId());
//                        searchPopup.hide();
//                    }
//                });
                
                VerticalPanel popupContent = new VerticalPanel();
                popupContent.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
//                popupContent.add(personSearchWidget);
                popupContent.add(new KSButton("Cancel", new ClickHandler(){
                    public void onClick(ClickEvent event) {
                        searchPopup.hide();
                    }                
                }));
                
                searchPopup.setWidget(popupContent);
                searchPopup.setResizable(false);
                searchPopup.show();
            }}));

        final HashMap<String, Object> map = new HashMap<String, Object>();
        
        add(panel, new CloseHandler<OrgMultiWidget>() {
            @Override
            public void onClose(CloseEvent<OrgMultiWidget> event) {
                map.put("deleted", Boolean.TRUE);
            }});
        
        if(orgPersonRelTypeList == null && orgId != null)
            DeferredCommand.addCommand(new IncrementalCommand() {
                @Override
                public boolean execute() {
                    if(orgPersonRelTypeList != null) {
                        orgPersonRelTypeDropDown.setListItems(orgPersonRelTypeList);
                        return false;
                    }
                    return true;
                }});
        else if(orgPersonRelTypeList != null)
            orgPersonRelTypeDropDown.setListItems(orgPersonRelTypeList);

        map.put("form",orgPersonRelForm);
        forms.add(map);
        
        return orgPersonRelForm;
    }

    private void populateRelationshipTypes() {
        if(orgId != null){
            orgRpcServiceAsync.getPositionRestrictionsByOrg(orgId, new AsyncCallback<List<OrgPositionRestrictionInfo>>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }
    
                public void onSuccess(List<OrgPositionRestrictionInfo> orgPositionRestrictions) {
                    if(orgPositionRestrictions != null) {
                        final Map<String,String> restrictions = new LinkedHashMap<String, String>();
                        for (OrgPositionRestrictionInfo info : orgPositionRestrictions) {
                            restrictions.put(info.getOrgPersonRelationTypeKey(), info.getTitle());
                        }
                        orgPersonRelTypeList = new ListItems() {

                            @Override
                            public List<String> getAttrKeys() {
                                return null;
                            }

                            @Override
                            public String getItemAttribute(String id, String attrkey) {
                                return null;
                            }

                            @Override
                            public int getItemCount() {
                                return restrictions.size();
                            }

                            @Override
                            public List<String> getItemIds() {
                                return new ArrayList<String>(restrictions.keySet());
                            }

                            @Override
                            public String getItemText(String id) {
                                return restrictions.get(id);
                            }                            
                        };
                    }
                }
            });             
        }
    }
    
    private void populatePersonRelationInfos() {
        orgRpcServiceAsync.getOrgPersonRelationsByOrg(orgId, 
                new AsyncCallback<List<OrgPersonRelationInfo>>(){

                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    public void onSuccess(List<OrgPersonRelationInfo> orgPersonRelations) {
                        for(OrgPersonRelationInfo orgPersonRelationInfo : orgPersonRelations) {
                            final KSFormLayoutPanel orgPersonRelForm = createBlankPersonRelation();
                            Map<String,Object> map = forms.get(forms.size() - 1); //should always be the last one
                            map.put("orgPersonRelId",orgPersonRelationInfo.getId());
                            map.put("orgPersonRelVersion",orgPersonRelationInfo.getMetaInfo().getVersionInd());
                            orgPersonRelForm.setFieldValue("relPersonId",orgPersonRelationInfo.getPersonId());
                            final String orgPersonRelType = orgPersonRelationInfo.getType();
                            
//                            PersonRpcServiceAsync personRpcService = GWT.create(PersonRpcService.class);
//                            personRpcService.fetchPerson(orgPersonRelationInfo.getPersonId(), 
//                                    new AsyncCallback<PersonInfo>(){
//
//                                        public void onFailure(Throwable caught) {
//                                        }
//
//                                        public void onSuccess(PersonInfo personInfo) {
//                                            orgPersonRelForm.setFieldValue("relPersonName", personInfo.getPersonNameInfoList().get(0).getGivenName());                       
//                                        }            
//                            });

                            //Disable editing of rel org id and name
                            ((FocusWidget)orgPersonRelForm.getFieldWidget("relPersonId")).setEnabled(false);
                            ((FocusWidget)orgPersonRelForm.getFieldWidget("relPersonName")).setEnabled(false);

                            ((KSDatePicker)orgPersonRelForm.getFieldWidget("relEffDate")).setValue(orgPersonRelationInfo.getEffectiveDate());
                            ((KSDatePicker)orgPersonRelForm.getFieldWidget("relExpDate")).setValue(orgPersonRelationInfo.getExpirationDate());
                            
                            final KSDropDown posTypeDropDown = (KSDropDown) orgPersonRelForm.getFieldWidget("relType");
                            
                            if(posTypeDropDown.getListItems() != null)
                                posTypeDropDown.selectItem(orgPersonRelType);
                            else
                                DeferredCommand.addCommand(new IncrementalCommand() {
                                    @Override
                                    public boolean execute() {
                                        if(posTypeDropDown.getListItems() != null) {
                                            posTypeDropDown.selectItem(orgPersonRelType);
                                            return false;
                                        }
                                        return true;
                                    }});
                        }
                    }
            });        
    }
    
    @Override
    protected void save() {
        for (final Map<String,Object> formMap : forms) {
            KSFormLayoutPanel orgPersonRelForm = (KSFormLayoutPanel) formMap.get("form");
            if (orgPersonRelForm.getFieldText("relPersonId").length() == 0)
                continue; //skipping this one
            if(formMap.get("orgPersonRelId") == null && formMap.get("deleted") != null)
                continue; //if not created AND deleted
            
            final OrgPersonRelationInfo orgPersonRelationInfo = new OrgPersonRelationInfo();        
            
            orgPersonRelationInfo.setId((String) formMap.get("orgPersonRelId"));
            orgPersonRelationInfo.setOrgId(orgId);
            
            MetaInfo orgPersonRelMetaInfo = new MetaInfo();
            orgPersonRelMetaInfo.setVersionInd((String) formMap.get("orgPersonRelVersion"));
            orgPersonRelationInfo.setMetaInfo(orgPersonRelMetaInfo);
            
            //TODO: This should lookup orgId based on related org name
            orgPersonRelationInfo.setPersonId(orgPersonRelForm.getFieldText("relPersonId"));
            
            orgPersonRelationInfo.setEffectiveDate(((KSDatePicker)orgPersonRelForm.getFieldWidget("relEffDate")).getValue());
            orgPersonRelationInfo.setExpirationDate(((KSDatePicker)orgPersonRelForm.getFieldWidget("relExpDate")).getValue());
            
            orgPersonRelationInfo.setType((String)orgPersonRelForm.getFieldValue("relType"));
            //TODO this should probably be done differently, especially the FAKE_ID constant in case FAKE_ID is part of institutions actual id structure
            if(((KSDropDown) orgPersonRelForm.getFieldWidget("relType")).getListItems().getItemAttribute(orgPersonRelationInfo.getType(), "id")!=null&&((KSDropDown) orgPersonRelForm.getFieldWidget("relType")).getListItems().getItemAttribute(orgPersonRelationInfo.getType(), "id").startsWith(OrgPositionWidget.FAKE_ID)) {
                final ListItems checking = ((KSDropDown) orgPersonRelForm.getFieldWidget("relType")).getListItems();
                DeferredCommand.addCommand(new IncrementalCommand() {
                    @Override
                    public boolean execute() {
                        if(checking.getItemAttribute(orgPersonRelationInfo.getType(), "id").startsWith(OrgPositionWidget.FAKE_ID)) {
                            return true;
                        }
                        doSave(formMap, orgPersonRelationInfo);
                        return false;
                    }});
            }
            
            doSave(formMap, orgPersonRelationInfo);
        }
    }
    
    private void doSave(Map<String, Object> formMap, OrgPersonRelationInfo orgPersonRelationInfo) {
        if (orgPersonRelationInfo.getId() == null){ //TODO deal with new creations that have been deleted (others too)
            orgRpcServiceAsync.createOrgPersonRelation(orgId,orgPersonRelationInfo.getPersonId(),orgPersonRelationInfo.getType(), orgPersonRelationInfo, 
                    new AsyncCallback<OrgPersonRelationInfo>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }
      
                public void onSuccess(final OrgPersonRelationInfo result) {
                    fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                        public String toString() {
                            return "Person relation created with id: " + result.getId();
                        }
                    });
                }
            });
        } else if(formMap.get("deleted") != null){
            orgRpcServiceAsync.removeOrgPersonRelation(orgPersonRelationInfo.getId(), 
                    new AsyncCallback<StatusInfo>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }
      
                public void onSuccess(final StatusInfo result) {
                    fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                        public String toString() {
                            return "Person relation deleted: " + result.getMessage();
                        }
                    });
                }
            });
        } else {
            orgRpcServiceAsync.updateOrgPersonRelation(orgPersonRelationInfo.getId(),orgPersonRelationInfo, new AsyncCallback<OrgPersonRelationInfo>(){
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }
      
                public void onSuccess(final OrgPersonRelationInfo result) {
                    fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                        public String toString() {
                            return "Person relation updated for id: " + result.getId();
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void create() {
        createBlankPersonRelation();
    }
    @Override
    public int calculateSaveableWidgetCount() {
        int count = 0;
        for (Map<String,Object> formMap : forms) {
            KSFormLayoutPanel orgPersonRelForm = (KSFormLayoutPanel) formMap.get("form");
            if (orgPersonRelForm.getFieldText("relPersonId").length() == 0)
                continue; //skipping this one
            if(formMap.get("orgPersonRelId") == null && formMap.get("deleted") != null)
                continue; //if not created AND deleted
           count++;
        }
        return count;
    }
}
