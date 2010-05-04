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

import static org.kuali.student.core.organization.ui.client.mvc.view.CommonConfigurer.getLabel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.mvc.CollectionModel;
import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

class OrgPositionWidget extends OrgMultiWidget {
    private static final String POS_VERSION = "posVersion";
    private static final String POS_ID = "posId";
    private static final String FORM = "form";
    private static final String NEW = "new";
    private static final String DELETED = "deleted";

    private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);

    static final String FAKE_ID = "FAKE-ID-";
    private ListItems orgPosTypeList;
    List<Map<String,Object>> forms = new ArrayList<Map<String,Object>>();
    private CollectionModel<OrgPositionRestrictionInfo> model = new CollectionModel<OrgPositionRestrictionInfo>();
    int newCount;

    public OrgPositionWidget() {
        this(null);
    }
    public OrgPositionWidget(String orgId) {
        this(orgId,false);
    }
    public OrgPositionWidget(String orgId, boolean open) {
        super(new KSDisclosureSection(getLabel("orgPositionRelationSection"), null, open));
        if(orgPosTypeList == null)
            populateOrgPositionTypes();
        this.orgId = orgId;
        if(orgId == null) {
            OrgPositionRestrictionInfo info = new OrgPositionRestrictionInfo();
            createBlankPosition(info);
            model.add(info);
        } else {
            populatePositionInfos();
        }
    }

    public KSFormLayoutPanel createBlankPosition(final OrgPositionRestrictionInfo info) {
        KSFormLayoutPanel orgForm = new KSFormLayoutPanel();

        final KSDropDown posTypeDropDown = new KSDropDown();

        posTypeDropDown.setBlankFirstItem(false);
        addFormField(posTypeDropDown, getLabel("orgPositionRelationTypeKey"), "position", orgForm);

        KSTextBox title = new KSTextBox();
        title.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                info.setTitle(event.getValue());
                model.update(info);
            }
        });
        addFormField(title, getLabel("orgPositionRelationTitle"), "title", orgForm);

        KSTextArea desc = new KSTextArea();
        desc.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                info.setDesc(event.getValue());
                model.update(info);
            }
        });
        addFormField(desc, getLabel("orgPositionRelationDesc"), "desc", orgForm);

        KSTextBox minPeople = new KSTextBox();
        minPeople.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                try {
                    info.setMinNumRelations(Integer.valueOf(event.getValue()));
                    model.update(info);
                } catch (NumberFormatException e) { //going to set it to null explicitly since that was the old behavior implictly
                    info.setMinNumRelations(0);
                }
            }
        });
        addFormField(minPeople, getLabel("orgPositionRelationMinPpl"), "min", orgForm);

        KSTextBox maxPeople = new KSTextBox();
        maxPeople.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                info.setMaxNumRelations(event.getValue());
                model.update(info);
            }
        });
        addFormField(maxPeople, getLabel("orgPositionRelationMaxPpl"), "max", orgForm);

        final HashMap<String, Object> map = new HashMap<String, Object>();

        add(orgForm, new CloseHandler<OrgMultiWidget>() {
            @Override
            public void onClose(CloseEvent<OrgMultiWidget> event) {
                map.put(DELETED, Boolean.TRUE);
            }});

        if(info.getId() == null) {
            info.setId(FAKE_ID+(newCount++));
            map.put(NEW,info.getId());
        }

        if(orgPosTypeList == null)
            DeferredCommand.addCommand(new IncrementalCommand() {
                @Override
                public boolean execute() {
                    if(orgPosTypeList != null) {
                        posTypeDropDown.setListItems(orgPosTypeList);
                        if(map.get(NEW) != null && !orgPosTypeList.getItemIds().isEmpty())
                            info.setOrgPersonRelationTypeKey(orgPosTypeList.getItemIds().get(0));
                        posTypeDropDown.addSelectionChangeHandler(new SelectionChangeHandler() {

							@Override
							public void onSelectionChange(
									SelectionChangeEvent event) {
								info.setOrgPersonRelationTypeKey(posTypeDropDown.getSelectedItem());
                                model.update(info);
							}});
                        return false;
                    }
                    return true;
                }});
        else {
            posTypeDropDown.setListItems(orgPosTypeList);
            if(map.get(NEW) != null && !orgPosTypeList.getItemIds().isEmpty())
                info.setOrgPersonRelationTypeKey(orgPosTypeList.getItemIds().get(0));
            posTypeDropDown.addSelectionChangeHandler(new SelectionChangeHandler() {

				@Override
				public void onSelectionChange(SelectionChangeEvent event) {
					info.setOrgPersonRelationTypeKey(posTypeDropDown.getSelectedItem());
                    model.update(info);

				}});
        }

        map.put(FORM,orgForm);
        forms.add(map);

        return orgForm;
    }

    private void populateOrgPositionTypes() {
        orgRpcServiceAsync.getOrgPersonRelationTypes(new AsyncCallback<List<OrgPersonRelationTypeInfo>>(){
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
        orgRpcServiceAsync.getPositionRestrictionsByOrg(orgId,
                new AsyncCallback<List<OrgPositionRestrictionInfo>>(){

                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    public void onSuccess(List<OrgPositionRestrictionInfo> orgPositions) {
                        for(OrgPositionRestrictionInfo info : orgPositions) {
                            model.add(info);
                            final KSFormLayoutPanel orgPosForm = createBlankPosition(info);
                            Map<String,Object> map = forms.get(forms.size() - 1); //should always be the last one
                            map.put(POS_ID,info.getId());
                            map.put(POS_VERSION,info.getMetaInfo().getVersionInd());

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
        for (int i = forms.size() - 1; i > -1; i--) {
            final Map<String, Object> formMap = forms.get(i);
            KSFormLayoutPanel orgPosForm = (KSFormLayoutPanel) formMap.get(FORM);
            if (orgPosForm.getFieldText("title").trim().length() == 0)
                continue; //skipping this one
            if(formMap.get(POS_ID) == null && formMap.get(DELETED) != null)
                continue; //if not created AND deleted
            OrgPositionRestrictionInfo orgPosRestriction = new OrgPositionRestrictionInfo();

            orgPosRestriction.setId((String) formMap.get(POS_ID));
            orgPosRestriction.setOrgId(orgId);

            MetaInfo posMetaInfo = new MetaInfo();
            posMetaInfo.setVersionInd((String) formMap.get(POS_VERSION));
            orgPosRestriction.setMetaInfo(posMetaInfo);

            orgPosRestriction.setOrgPersonRelationTypeKey((String)orgPosForm.getFieldValue("position"));
            orgPosRestriction.setTitle(orgPosForm.getFieldText("title"));
            orgPosRestriction.setDesc(orgPosForm.getFieldText("desc"));

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
                orgPosRestriction.setMinNumRelations(Integer.valueOf((String)orgPosForm.getFieldValue("min")));
            } catch (NumberFormatException e) {

            }
            orgPosRestriction.setMaxNumRelations((String)orgPosForm.getFieldValue("max"));

            if (formMap.get(NEW) != null){
                if(formMap.get(DELETED) != null)
                    continue;
                orgPosRestriction.setId(null);
                orgRpcServiceAsync.addPositionRestrictionToOrg(orgPosRestriction,
                        new AsyncCallback<OrgPositionRestrictionInfo>(){
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    public void onSuccess(final OrgPositionRestrictionInfo result) {
                        model.get((String) formMap.get(NEW)).setId(result.getId());
                        fireEvent(new SaveEvent() { //TODO fix this terrible terrible hack
                            public String toString() {
                                return "Org position created with id: " + result.getId();
                            }
                        });
                    }
                });
            }else if(formMap.get(DELETED) != null){
                orgRpcServiceAsync.removePositionRestrictionFromOrg(orgPosRestriction.getOrgId(), orgPosRestriction.getOrgPersonRelationTypeKey(),
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
                orgRpcServiceAsync.updatePositionRestrictionForOrg(orgPosRestriction,
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

    public CollectionModel<OrgPositionRestrictionInfo> getModel() {
        return model;
    }
    public void setModel(CollectionModel<OrgPositionRestrictionInfo> model) {
        this.model = model;
    }
    @Override
    protected void create() {
        OrgPositionRestrictionInfo info = new OrgPositionRestrictionInfo();
        createBlankPosition(info);
        model.add(info);
    }
    @Override
    public int calculateSaveableWidgetCount() {
        int count = 0;
        for (Map<String,Object> formMap : forms) {
            KSFormLayoutPanel orgPosForm = (KSFormLayoutPanel) formMap.get(FORM);
            if (orgPosForm.getFieldText("title").trim().length() == 0)
                continue; //skipping this one
            if(formMap.get(POS_ID) == null && formMap.get(DELETED) != null)
                continue; //if not created AND deleted
           count++;
        }
        return count;
    }

}
