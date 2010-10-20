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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.event.SaveEvent;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent.EditMode;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.core.authorization.ui.client.service.AuthorizationRpcService;
import org.kuali.student.core.authorization.ui.client.service.AuthorizationRpcServiceAsync;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;

class OrgWidget extends OrgAbstractWidget implements HasSelectionHandlers<OrgInfo> {
    private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
	private final AuthorizationRpcServiceAsync authzRpcServiceAsync = GWT.create(AuthorizationRpcService.class);
	
    private String orgVersion;
    private KSFormLayoutPanel orgForm;

    public OrgWidget() {
        this(null);
    }

    public OrgWidget(String orgId) {
        this(null, false);
    }

    public OrgWidget(String orgId, boolean open) {
        super(new KSDisclosureSection("Organization", null, open));
        this.orgId = orgId;

        orgForm = new KSFormLayoutPanel();

//    	authzRpcServiceAsync.hasPermission("KUALI", "Default", new AsyncCallback<Boolean>(){
//
//			@Override
//			public void onFailure(Throwable caught) {
//				//Did not get permissions
//				orgForm.setEditMode(EditMode.UNEDITABLE);
//				setupOrgForm();
//			}
//
//			@Override
//			public void onSuccess(Boolean result) {
//				if(result){
//					orgForm.setEditMode(EditMode.EDITABLE);
//				}else{
//					orgForm.setEditMode(EditMode.UNEDITABLE);
//				}
//				setupOrgForm();
//			}
//    		
//    	});
        orgForm.setEditMode(EditMode.EDITABLE);
        setupOrgForm();
    }

    private void setupOrgForm(){
        VerticalPanel panel = new VerticalPanel();
        
        addFormField(new KSDropDown(), "Type", "orgType", orgForm);
        addFormField(new KSTextBox(), "Name", "orgName", orgForm);
        addFormField(new KSTextBox(), "Abbreviation", "orgAbbrev", orgForm);
        addFormField(new KSTextArea(), "Description", "orgDesc", orgForm);
        addFormField(new KSDatePicker(), "Effective Date", "orgEffDate", orgForm);
        addFormField(new KSDatePicker(), "Expiration Date", "orgExpDate", orgForm);

        panel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        panel.setWidth("100%");
        w.setWidth("100%");
        panel.add(orgForm);

        w.add(panel);

        if (orgId != null)
            populateOrgInfo();

        populateOrgTypes();
    }
    
    private void populateOrgTypes() {
        orgRpcServiceAsync.getOrgTypes(new AsyncCallback<List<OrgTypeInfo>>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(final List<OrgTypeInfo> orgTypes) {
                final Map<String, String> ids = new LinkedHashMap<String, String>();
                for (OrgTypeInfo orgTypeInfo : orgTypes) {
                    ids.put(orgTypeInfo.getId(), orgTypeInfo.getName());
                }
                ListItems list = new ListItems() {

                    @Override
                    public List<String> getAttrKeys() {
                        return null; // apparently unused
                    }

                    @Override
                    public String getItemAttribute(String id, String attrkey) {
                        return null; // apparently unused
                    }

                    @Override
                    public int getItemCount() {
                        return orgTypes.size();
                    }

                    @Override
                    public List<String> getItemIds() {
                        return new ArrayList<String>(ids.keySet());
                    }

                    @Override
                    public String getItemText(String id) {
                        return ids.get(id);
                    }

                };
                KSDropDown orgTypeDropDown = (KSDropDown) orgForm.getFieldWidget("orgType");
                orgTypeDropDown.setEnabled(true);
                ListItems items = orgTypeDropDown.getListItems();
                orgTypeDropDown.setListItems(list);
                if (items != null) {
                    orgTypeDropDown.selectItem(((SingleListItem) items).getItemIds().get(0));
                }
            }
        });
    }

    protected void save() {
        OrgInfo orgInfo = new OrgInfo();

        MetaInfo orgMetaInfo = new MetaInfo();
        orgMetaInfo.setVersionInd(orgVersion);
        orgInfo.setMetaInfo(orgMetaInfo);

        KSDropDown orgTypeDropDown = (KSDropDown) orgForm.getFieldWidget("orgType");

        orgInfo.setType(orgTypeDropDown.getSelectedItems().get(0));
        orgInfo.setShortDesc((String) orgForm.getFieldValue("orgDesc"));
        orgInfo.setLongName((String) orgForm.getFieldValue("orgName"));
        orgInfo.setShortName((String) orgForm.getFieldValue("orgAbbrev"));

        orgInfo.setEffectiveDate(((KSDatePicker) orgForm.getFieldWidget("orgEffDate")).getValue());
        orgInfo.setExpirationDate(((KSDatePicker) orgForm.getFieldWidget("orgExpDate")).getValue());

        if (orgId == null) {
            orgRpcServiceAsync.createOrganization(orgInfo, new AsyncCallback<OrgInfo>() {
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(OrgInfo result) {
                    orgId = result.getId();
                    // fireSelectEvent(result);
                    fireEvent(new SaveEvent() { // TODO fix this terrible terrible hack
                        public String toString() {
                            return "Organization created with id: " + orgId;
                        }
                    });
                    fireSelectEvent(result);
                }
            });
        } else {
            orgInfo.setId(orgId);
            orgRpcServiceAsync.updateOrganization(orgInfo, new AsyncCallback<OrgInfo>() {
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                public void onSuccess(OrgInfo result) {
                    fireEvent(new SaveEvent() { // TODO fix this terrible terrible hack
                        public String toString() {
                            return "Organization saved";
                        }
                    });
                    fireSelectEvent(result);
                }
            });
        }

    }

    protected void populateOrgInfo() {
        orgRpcServiceAsync.getOrganization(orgId, new AsyncCallback<OrgInfo>() {

            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(OrgInfo orgInfo) {
           	
                KSDropDown orgTypeDropDown = (KSDropDown) orgForm.getFieldWidget("orgType");

                orgVersion = orgInfo.getMetaInfo().getVersionInd();
                String orgType = orgInfo.getType();
                if (orgTypeDropDown.getListItems() == null) {
                    orgTypeDropDown.setListItems(new SingleListItem(orgType));
                    orgTypeDropDown.setEnabled(false);
                }
                orgTypeDropDown.selectItem(orgType);
                orgForm.setFieldValue("orgName", orgInfo.getLongName());
                orgForm.setFieldValue("orgAbbrev", orgInfo.getShortName());
                orgForm.setFieldValue("orgDesc", orgInfo.getShortDesc());

                ((KSDatePicker) orgForm.getFieldWidget("orgEffDate")).setValue(orgInfo.getEffectiveDate());
                ((KSDatePicker) orgForm.getFieldWidget("orgExpDate")).setValue(orgInfo.getExpirationDate());
                // System.out.println(orgForm.getFieldValue("orgType"));

            }
        });
    }


    
    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<OrgInfo> handler) {
        return addHandler(handler, SelectionEvent.getType());
    }

    private void fireSelectEvent(OrgInfo selectedOrg) {
        SelectionEvent.fire(this, selectedOrg);
    }

}
