package org.kuali.student.core.organization.ui.client.view;

import java.util.List;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.FormField;
import org.kuali.student.common.ui.client.widgets.forms.FormLayoutPanel;
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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class OrgPersonRelationWidget extends Composite{
    boolean loaded = false;
    
    String orgPersonRelId = null;
    String orgId;
    String orgPersonRelType;
    String orgPersonRelVersion;
    
    FlexTable fTable = null;
    
    SimplePanel root = new SimplePanel();
    
    FormLayoutPanel orgPersonRelForm = null;
    
    ListBox orgPersonRelTypeDropDown = null;

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
            root.add(fTable);
            loaded = true;
            
        }
    }

    private void initForm(){
        orgPersonRelForm = new FormLayoutPanel();
        
        orgPersonRelTypeDropDown = new ListBox();
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
        FormField ff = new FormField();
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
        
        orgPersonRelationInfo.setType(orgPersonRelTypeDropDown.getValue(orgPersonRelTypeDropDown.getSelectedIndex()));
        
        //TODO: This should lookup orgId based on related org name
        orgPersonRelationInfo.setPersonId(orgPersonRelForm.getFieldValue("relPersonId"));
        
        orgPersonRelationInfo.setEffectiveDate(((KSDatePicker)orgPersonRelForm.getFieldWidget("relEffDate")).getDate());
        orgPersonRelationInfo.setExpirationDate(((KSDatePicker)orgPersonRelForm.getFieldWidget("relExpDate")).getDate());
        
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

        ((KSDatePicker)orgPersonRelForm.getFieldWidget("relEffDate")).setDate(orgPersonRelationInfo.getEffectiveDate());
        ((KSDatePicker)orgPersonRelForm.getFieldWidget("relExpDate")).setDate(orgPersonRelationInfo.getExpirationDate());
    }
    
    protected void loadOrgPersonRelationTypes(){
        OrgRpcService.Util.getInstance().getPositionRestrictionsByOrg(orgId, new AsyncCallback<List<OrgPositionRestrictionInfo>>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(List<OrgPositionRestrictionInfo> orgPositionRestrictions) {
                orgPersonRelTypeDropDown.addItem("Select", "");

                int i = 0;
                for(OrgPositionRestrictionInfo orgPositionRestriction:orgPositionRestrictions){
                    i++;
                    orgPersonRelTypeDropDown.addItem(orgPositionRestriction.getTitle(),orgPositionRestriction.getOrgPersonRelationTypeKey());
                    if (orgPositionRestriction.getOrgPersonRelationTypeKey().equals(orgPersonRelType)){
                        orgPersonRelTypeDropDown.setSelectedIndex(i);
                    }
                }
            }
        });                
    }
}
