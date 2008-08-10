package org.kuali.student.ui.personidentity.client.view;



import java.util.List;
import java.util.Vector;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.widgets.DateBox;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfo;
import org.kuali.student.ui.personidentity.client.controller.PersonIdentityController;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCitizenshipInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCreateInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonNameInfo;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PersonCreateInfoWidget extends Composite {
	
	VerticalPanel 				rootPanel 		= new VerticalPanel();
	SearchListBox				personTypes 	= new SearchListBox();
	PersonNameInfoWidget		pNameWidget		= new PersonNameInfoWidget();
	PersonCitizenshipInfoWidget pCitzInfoWidget = new PersonCitizenshipInfoWidget();
	
	VerticalPanel				adminContainer	= new VerticalPanel();
	
	//DisclosurePanel 			discNameInfo	= new DisclosurePanel("Name", true);
	//DisclosurePanel 			discNameInfo	= new DisclosurePanel(I18N.i18nConstant.name(), true);
	DisclosurePanel 			discNameInfo	= new DisclosurePanel(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("name"), true);
	
	//DisclosurePanel 			discCitzInfo 	= new DisclosurePanel("Citizenship Details", true);
	//DisclosurePanel 			discCitzInfo 	= new DisclosurePanel(I18N.i18nConstant.citizenshipDetails(), true);
	DisclosurePanel 			discCitzInfo 	= new DisclosurePanel(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("citizenshipDetails"), true);
	
	//DisclosurePanel 			discBioInfo 	= new DisclosurePanel("Biographic Details", true);
	//DisclosurePanel 			discBioInfo 	= new DisclosurePanel(I18N.i18nConstant.biographicDetails(), true);
	DisclosurePanel 			discBioInfo 	= new DisclosurePanel(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("biographicDetails"), true);
	
	//DisclosurePanel 			discAdminInfo 	= new DisclosurePanel("Admin Details", true);
	//DisclosurePanel 			discAdminInfo 	= new DisclosurePanel(I18N.i18nConstant.adminDetails(), true);
	DisclosurePanel 			discAdminInfo 	= new DisclosurePanel(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("adminDetails"), true);
	
	DateBox						dob					= new DateBox();
	Label						personId			= new Label();
	SearchListBox				gender				= new SearchListBox();	
	TextArea					createUserComment	= new TextArea();
	
	VerticalPanel				vpBioData 			= new VerticalPanel();
	FlexTable					fTable				= new FlexTable();
	
	//Label						lDob				= new Label("Date of Birth");
	//Label						lDob				= new Label(I18N.i18nConstant.dateOfBirth());
	Label						lDob				= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("dateOfBirth"));
	
	//Label						lPersonId			= new Label("Student Number");
	//Label						lPersonId			= new Label(I18N.i18nConstant.studentNumber());
	Label						lPersonId			= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("studentNumber"));
	
	//Label						lGender				= new Label("Gender");
	//Label						lGender				= new Label(I18N.i18nConstant.gender());
	Label						lGender				= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("gender"));
	Label						lPersonType			= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("personType"));
	
						
	LabelWidget					lwCreateComment		= null;
	
	VerticalPanel				dynamicPanel = new VerticalPanel();
	
	boolean						isEditable	= false;
	
	public void clear(){
		dob.clear();
		personId.setText("");
		gender.setItemSelected(0, false);
		createUserComment.setText("");
		createUserComment.addStyleName("KS-TextArea");
		pNameWidget.populateFields(new GwtPersonNameInfo());
		pCitzInfoWidget.populateFields(new GwtPersonCitizenshipInfo());
	}
	
	
	public List<String> getPersonTypeKeys(){
		List<String> lRet = new Vector<String>();
		
		lRet.add(personTypes.getValue(personTypes.getSelectedIndex()));
			
		return lRet;
	}
	
	public void setEditable(boolean editable){
		this.isEditable = editable;
		this.displayEditable();
	}
	
	protected void displayEditable(){
		pNameWidget.setEditable(this.isEditable);
		pCitzInfoWidget.setEditable(this.isEditable);
	}
	
	public GwtPersonCreateInfo getPersonObj(){
		GwtPersonCreateInfo pInfo = new GwtPersonCreateInfo();
		
		Vector<GwtPersonNameInfo> vNameList = new Vector<GwtPersonNameInfo>();
		
		
		if(dob.box.getText() != null && !dob.box.getText().equals(""))
			pInfo.setBirthDate(dob.formatter.parse(dob.box.getText()));
		pInfo.setGender(gender.getValue(gender.getSelectedIndex()).charAt(0));
		
		vNameList.add(pNameWidget.getNameInfoObj());
		pInfo.setName(vNameList);
		pInfo.setCitizenship(pCitzInfoWidget.getPersonCitzInfoObj());		
		pInfo.setCreateUserComment(createUserComment.getText());
		
		return pInfo;
	}
	
	
	
	
	public PersonCreateInfoWidget(){		
		
		rootPanel.addStyleName("KS-PersonIdentity-Panel");		
		personTypes.addStyleName("KS-ListBox");
		
		personTypes.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				String s = "";
			}});
		
		personTypes.addChangeListener(new ChangeListener(){
			public void onChange(Widget sender) {
				updateSelectedPersonTypeDisplayList();
			}});
		

		//discNameInfo.addStyleName("KS-NameInfo-Panel");
		discNameInfo.addStyleName("KS-CitzInfo-Panel");
		
		discCitzInfo.addStyleName("KS-CitzInfo-Panel");
		//discBioInfo.addStyleName("KS-BioInfo-Panel");
		discBioInfo.addStyleName("KS-CitzInfo-Panel");
		//discAdminInfo.addStyleName("KS-AdminInfo-Panel");
		discAdminInfo.addStyleName("KS-CitzInfo-Panel");
		
		
		dob.addStyleName("KS-TextBox");
		personId.addStyleName("KS-Label");
		gender.addStyleName("KS-ListBox");
		
		lDob.addStyleName("KS-Label");
		lPersonId.addStyleName("KS-Label");		
		lGender.addStyleName("KS-Label");
		
		rootPanel.setWidth("75%");
		rootPanel.add(lPersonType);
		rootPanel.add(personTypes);
		
		discNameInfo.setWidth("100%");
		discNameInfo.setTitle("Name");
		discNameInfo.add(pNameWidget);
		
		
		//fTable.setWidget(0, 0, lPersonId);
		//personId.setReadOnly(true);
		//fTable.setWidget(0, 1, personId);
		
		fTable.setWidget(1, 0, lDob);
		fTable.setWidget(1, 1, dob);
		
		fTable.setWidget(2, 0, lGender);
		
		//gender.addItem("Male", "M");
		//gender.addItem(I18N.i18nConstant.male(), "M");
		gender.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("male"), "M");
		
		//gender.addItem("Female", "F");				
		//gender.addItem(I18N.i18nConstant.female(), "F");
		gender.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("female"), "F");
		

		fTable.setWidget(2, 1, gender);
		
		discBioInfo.setWidth("100%");		
		discBioInfo.add(fTable);
						
		discCitzInfo.setWidth("100%");
		//discCitzInfo.setTitle("Citizenship Details");
		//discCitzInfo.setTitle(I18N.i18nConstant.citizenshipDetails());
		discCitzInfo.setTitle(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("citizenshipDetails"));
		
		discCitzInfo.add(pCitzInfoWidget);
						
		//lwCreateComment = new LabelWidget("Create Comment", createUserComment);
		//lwCreateComment = new LabelWidget(I18N.i18nConstant.createComment(), createUserComment);
		lwCreateComment = new LabelWidget(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("createComment"), createUserComment);
		lwCreateComment.setLabelStyleName("KS-Label");
		lwCreateComment.getLabel().setWidth("30%");
		
		adminContainer.add(lwCreateComment);
		adminContainer.setWidth("100%");
		discAdminInfo.setWidth("100%");
		//discAdminInfo.setTitle("Admin Details");
		//discAdminInfo.setTitle(I18N.i18nConstant.adminDetails());
		discAdminInfo.setTitle(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("adminDetails"));
		
		discAdminInfo.add(adminContainer);
		
		rootPanel.add(discNameInfo);
		rootPanel.add(discBioInfo);
		rootPanel.add(discCitzInfo);
		rootPanel.add(discAdminInfo);
		rootPanel.add(dynamicPanel);
		
		
		initWidget(rootPanel);
	}
	
	public PersonCreateInfoWidget(List <PersonTypeDisplay> pList){
							
		initWidget(rootPanel);
	}
	
	protected void updateSelectedPersonTypeDisplayList(){
		String value = personTypes.getSelectedValue();
		
		if(value != null && !"".equals(value)){
			PersonIdentityController.getService().fetchPersonType(value, 
					new AsyncCallback<PersonTypeInfo>(){
					public void onFailure(Throwable caught) {
						Window.alert("Could not load dynamic attrbute types");
					}				
					public void onSuccess(PersonTypeInfo result) {	
						List<PersonAttributeSetTypeInfo> setList =   result.getAttributeSets();
						dynamicPanel.clear();
						if(setList != null){
							for(PersonAttributeSetTypeInfo info: setList){
								DisclosurePanel dynPanel = new DisclosurePanel(info.getName(), true);
								dynPanel.addStyleName("KS-CitzInfo-Panel");
								FlexTable dynTable = new FlexTable();
								dynTable.setTitle(info.getName());
								dynTable.addStyleName("KS-Label");
								List<PersonAttributeTypeInfo> pTypes = info.getAttributeTypes();
								for(int i=0; i< pTypes.size(); i++){
									PersonAttributeTypeInfo pType =  pTypes.get(i);
									
									String type = pType.getType();
									
									if("String".equals(type)){
										
										EditLabel eLabel = new EditLabel(false);
										eLabel.setIsEditable(true);
										Label lbl = new Label();
										
										if(pType.getLabel() != null)
											lbl = new Label(pType.getLabel());
										
										dynTable.setWidget(i, 0, lbl);
										dynTable.setWidget(i, 1, eLabel);
									}
								}
								dynPanel.add(dynTable);
								dynPanel.setWidth("800px");
								dynamicPanel.add(dynPanel);
							}
						} 
					}}
			);
		}
	}
	
	public void setPersonTypeDisplayList(List <PersonTypeDisplay> pList){
		if(pList != null){
			personTypes.clear();
			for(int i=0;i<pList.size(); i++){
				personTypes.addItem(pList.get(i).getName(), pList.get(i).getId().toString());
			}
			this.updateSelectedPersonTypeDisplayList();
		}
	}
	
}
