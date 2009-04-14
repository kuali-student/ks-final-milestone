package org.kuali.student.core.organization.ui.client.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent.EditMode;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.person.ui.client.service.PersonRpc;
import org.kuali.student.core.person.ui.client.view.PersonSearchWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class OrgPersonRelationWidget extends Composite{
    boolean loaded = false;
    boolean deleted = false;
    String orgPersonRelId = null;
    String orgId;
    String orgPersonRelType;
    String orgPersonRelVersion;
    
    FlexTable fTable = null;
    
    SimplePanel root = new SimplePanel();
    
    KSFormLayoutPanel orgPersonRelForm = null;
    
    KSDropDown orgPersonRelTypeDropDown = null;

    KSModalDialogPanel searchPopup = new KSModalDialogPanel();
    
    public OrgPersonRelationWidget(){
        super.initWidget(root);
        
    }
    
    public OrgPersonRelationWidget(String orgId) {
    	this();
    	this.orgId=orgId;
	}

	protected void onLoad(){
        if (!loaded){
            
            if (orgPersonRelForm == null){
                initForm();
            }
            
            fTable = new FlexTable();
            fTable.setWidget(0,0, orgPersonRelForm);

            PersonSearchWidget personSearchWidget = new PersonSearchWidget();
            personSearchWidget.addSelectionHandler(new SelectionHandler<PersonInfo>(){
                public void onSelection(SelectionEvent<PersonInfo> event) {
                	PersonInfo o = event.getSelectedItem();
                    orgPersonRelForm.setFieldValue("relPersonName", o.getPersonNameInfoList().get(0).getGivenName());
                    orgPersonRelForm.setFieldValue("relPersonId", o.getId());
                    searchPopup.hide();
                }
            });
            
            VerticalPanel popupContent = new VerticalPanel();
            popupContent.add(personSearchWidget);
            popupContent.add(new KSButton("Cancel", new ClickHandler(){
                public void onClick(ClickEvent event) {
                    searchPopup.hide();
                }                
            }));
            
            searchPopup.setWidget(popupContent);
            
            KSButton searchButton = new KSButton("Find Person", new ClickHandler(){
                public void onClick(ClickEvent event) {
                    searchPopup.show();
                }                
            });
            fTable.setWidget(0,1, searchButton);
            fTable.getCellFormatter().setVerticalAlignment(0, 1, VerticalPanel.ALIGN_TOP);
            
            if (orgPersonRelId != null){
                fTable.setWidget(1,0, getRemoveLink());
            }
            
            root.add(fTable);
            loaded = true;
            
        }
    }

    private Widget getRemoveLink() {
        Hyperlink hLink = new Hyperlink("(-)remove","");
        hLink.setStyleName("action");

        hLink.addClickHandler(new ClickHandler(){

            public void onClick(ClickEvent event) {
                //TODO: Add call to remove personrelation
            	if(!deleted){
            		deleted=true;
            		orgPersonRelForm.setEditMode(EditMode.VIEW_ONLY);
            		fTable.setWidget(1, 0, new KSLabel("Removed"));
            	}
            }            
        });
        
        return hLink;
	}

	private void initForm(){
        orgPersonRelForm = new KSFormLayoutPanel();
        
        orgPersonRelTypeDropDown = new KSDropDown();
        loadOrgPersonRelationTypes();        
        
        addFormField(new KSTextBox(), "Person", "relPersonName");
        KSTextBox relPersonId = new KSTextBox();
        relPersonId.setEnabled(true);
        addFormField(relPersonId, "Person Id", "relPersonId");
        addFormField(orgPersonRelTypeDropDown, "Relationship", "relType");
        addFormField(new KSDatePicker(), "Effective date", "relEffDate");
        addFormField(new KSDatePicker(), "Expiration date", "relExpDate");
    }
    


	private void addFormField(Widget w, String label, String name){
        KSFormField ff = new KSFormField();
        ff.setLabelText(label);
        ff.setWidget(w);
        ff.setHelpInfo(new HelpInfo());
        ff.setName(name);
        
        orgPersonRelForm.addFormField(ff);
    }

    public OrgPersonRelationInfo getOrgPersonRelationInfo(){       
    	OrgPersonRelationInfo orgPersonRelationInfo = new OrgPersonRelationInfo();        
        
    	orgPersonRelationInfo.setId(orgPersonRelId);
        
        MetaInfo orgPersonRelMetaInfo = new MetaInfo();
        orgPersonRelMetaInfo.setVersionInd(orgPersonRelVersion);
        orgPersonRelationInfo.setMetaInfo(orgPersonRelMetaInfo);
        
        orgPersonRelationInfo.setType(orgPersonRelTypeDropDown.getSelectedItem());
        
        //TODO: This should lookup orgId based on related org name
        orgPersonRelationInfo.setPersonId(orgPersonRelForm.getFieldValue("relPersonId"));
        
        orgPersonRelationInfo.setEffectiveDate(((KSDatePicker)orgPersonRelForm.getFieldWidget("relEffDate")).getValue());
        orgPersonRelationInfo.setExpirationDate(((KSDatePicker)orgPersonRelForm.getFieldWidget("relExpDate")).getValue());
        
        return orgPersonRelationInfo;
    }
    
    public void setOrgPersonRelationInfo(OrgPersonRelationInfo orgPersonRelationInfo){
        if (orgPersonRelForm == null){
            initForm();
        }
        
        orgPersonRelForm.setFieldValue("relPersonId",orgPersonRelationInfo.getPersonId());
        orgPersonRelId = orgPersonRelationInfo.getId();
        orgPersonRelType = orgPersonRelationInfo.getType();
        orgPersonRelVersion = orgPersonRelationInfo.getMetaInfo().getVersionInd();
        
        PersonRpc.Util.getInstance().fetchPerson(orgPersonRelationInfo.getPersonId(), 
                new AsyncCallback<PersonInfo>(){

                    public void onFailure(Throwable caught) {
                    }

                    public void onSuccess(PersonInfo personInfo) {
                    	orgPersonRelForm.setFieldValue("relPersonName", personInfo.getPersonNameInfoList().get(0).getGivenName());                       
                    }            
        });

        //Disable editing of rel org id and name
        ((FocusWidget)orgPersonRelForm.getFieldWidget("relPersonId")).setEnabled(false);
        ((FocusWidget)orgPersonRelForm.getFieldWidget("relPersonName")).setEnabled(false);

        ((KSDatePicker)orgPersonRelForm.getFieldWidget("relEffDate")).setValue(orgPersonRelationInfo.getEffectiveDate());
        ((KSDatePicker)orgPersonRelForm.getFieldWidget("relExpDate")).setValue(orgPersonRelationInfo.getExpirationDate());
    }
    
    public void loadOrgPersonRelationTypes(){
    	if(orgId!=null){
	        OrgRpcService.Util.getInstance().getPositionRestrictionsByOrg(orgId, new AsyncCallback<List<OrgPositionRestrictionInfo>>(){
	            public void onFailure(Throwable caught) {
	                Window.alert(caught.getMessage());
	            }
	
	            public void onSuccess(List<OrgPositionRestrictionInfo> orgPositionRestrictions) {
//	                orgPersonRelTypeDropDown.addItem("Select", "");
//		            
//	                if(orgPositionRestrictions!=null){
//		                int i = 0;
//		                for(OrgPositionRestrictionInfo orgPositionRestriction:orgPositionRestrictions){
//		                    i++;
//		                    orgPersonRelTypeDropDown.addItem(orgPositionRestriction.getTitle(),orgPositionRestriction.getOrgPersonRelationTypeKey());
//		                    if (orgPositionRestriction.getOrgPersonRelationTypeKey().equals(orgPersonRelType)){
//		                        orgPersonRelTypeDropDown.setSelectedIndex(i);
//		                    }
//		                }
//	                }
	                if(orgPositionRestrictions != null) {
	                    final Map<String,String> restrictions = new LinkedHashMap<String, String>();
	                    for (OrgPositionRestrictionInfo info : orgPositionRestrictions) {
                            restrictions.put(info.getOrgPersonRelationTypeKey(), info.getTitle());
                        }
	                    ListItems items = new ListItems() {

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
	                    orgPersonRelTypeDropDown.setListItems(items);
	                    if(orgPersonRelType != null)
	                        orgPersonRelTypeDropDown.selectItem(orgPersonRelType);
	                }
	            }
	        });             
    	}
    }

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgId() {
		return orgId;
	}

	public boolean isDeleted() {
		return deleted;
	}
}
