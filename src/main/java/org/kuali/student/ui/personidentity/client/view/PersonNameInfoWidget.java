package org.kuali.student.ui.personidentity.client.view;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.ui.personidentity.client.model.GwtPersonNameInfo;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PersonNameInfoWidget extends Composite {

	protected GwtPersonNameInfo pNameInfo = null;		
	
	boolean 		isEditable 		= false;
	
	
	VerticalPanel 	rootPanel		= new VerticalPanel();
	FlexTable		fTable			= new FlexTable();
		
	EditLabel		givenName		= new EditLabel(false);
	EditLabel		middleName		= new EditLabel(false);
	EditLabel		surName			= new EditLabel(false);
	EditLabel		suffix			= new EditLabel(false);
	EditLabel		nonStandardName	= new EditLabel(false);
	EditLabel		personTitle		= new EditLabel(false);
	CheckBox		isPreferredName	= new CheckBox();
	
	//Label	lGivenName			= new Label("Given Name");
	Label	lGivenName			= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("givenName"));
	//Label	lMiddleName			= new Label("Middle Name");
	Label	lMiddleName			= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("middleName"));
	//Label	lSurName			= new Label("Surname");
	Label	lSurName			= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("surname"));
	//Label	lSuffix				= new Label("Suffix");
	Label	lSuffix				= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("suffix"));
	//Label	lNonStandardName	= new Label("Non Standard Name");
	Label	lNonStandardName	= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("nonStandardName"));
	//Label	lPersonTitle		= new Label("Title");
	Label	lPersonTitle		= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("title"));
	//Label	lIsPreferredName	= new Label("Preferred Name");
	Label	lIsPreferredName	= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("preferredName"));
	
	
	
	
	public PersonNameInfoWidget(){
		
		rootPanel.add(fTable);
		this.arrangeFields();
		this.initWidget(rootPanel);
	}
	
	public PersonNameInfoWidget(GwtPersonNameInfo pInfo){
		this.pNameInfo = pInfo;
		this.populateFields(this.pNameInfo);
		this.arrangeFields();
		toggleEdit();
				
		this.initWidget(fTable);
	}
	
	public GwtPersonNameInfo getNameInfoObj(){
		GwtPersonNameInfo pInfo = new GwtPersonNameInfo();
		
		pInfo.setGivenName(givenName.getText());
		pInfo.setMiddleName(middleName.getText());
		// this hasn't been created on the service side. but they require it.
		pInfo.setNameType("OFFICAL"); 
		pInfo.setNonStandardName(nonStandardName.getText());
		pInfo.setPersonTitle(personTitle.getText());
		pInfo.setPreferredName(isPreferredName.isChecked());
		pInfo.setSuffix(suffix.getText());
		pInfo.setSurname(surName.getText());
		
		return pInfo;
	}
	
	
	
	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
		toggleEdit();
	}

	protected void toggleEdit(){
		
		
		
			givenName.setIsEditable(isEditable);
			middleName.setIsEditable(isEditable);
			suffix.setIsEditable(isEditable);
			surName.setIsEditable(isEditable);
			nonStandardName.setIsEditable(isEditable);
			personTitle.setIsEditable(isEditable);
			isPreferredName.setEnabled(isEditable);		
	}
	
	protected void arrangeFields(){
		givenName.addStyleName("KS-Label");
		middleName.addStyleName("KS-Label");
		surName.addStyleName("KS-Label");
		suffix.addStyleName("KS-Label");
		nonStandardName.addStyleName("KS-Label");
		personTitle.addStyleName("KS-Label");
		isPreferredName.addStyleName("KS-Label");

		// Set style for Label and Text parts of EditLabel objects
		givenName.setLblStyle("KS-givenName-Value-Label");		
		givenName.addEditWidgetStyle("KS-TextBox");
		middleName.setLblStyle("KS-middleName-Value-Label");
		middleName.addEditWidgetStyle("KS-TextBox");
		surName.setLblStyle("KS-surName-Value-Label");
		surName.addEditWidgetStyle("KS-TextBox");
		suffix.setLblStyle("KS-suffix-Value-Label");
		suffix.addEditWidgetStyle("KS-TextBox");
		nonStandardName.setLblStyle("KS-nonStandardName-Value-Label");
		nonStandardName.addEditWidgetStyle("KS-TextBox");		
		personTitle.setLblStyle("KS-personTitle-Value-Label");
		personTitle.addEditWidgetStyle("KS-TextBox");
	
		
		lGivenName.addStyleName("KS-Label");
		lMiddleName.addStyleName("KS-Label");
		lSurName.addStyleName("KS-Label");
		lSuffix.addStyleName("KS-Label");
		lNonStandardName.addStyleName("KS-Label");
		lPersonTitle.addStyleName("KS-Label");
		lIsPreferredName.addStyleName("KS-Label");
	
			
		fTable.setWidget(0, 0, lPersonTitle);
		fTable.setWidget(0, 1, personTitle);
		
		fTable.setWidget(1, 0, lGivenName);
		fTable.setWidget(1, 1, givenName);
		
		fTable.setWidget(2, 0, lMiddleName);
		fTable.setWidget(2, 1, middleName);
		
		fTable.setWidget(3, 0, lSurName);
		fTable.setWidget(3, 1, surName);
		
		fTable.setWidget(4, 0, lSuffix);
		fTable.setWidget(4, 1, suffix);
		
		fTable.setWidget(5, 0, lNonStandardName);
		fTable.setWidget(5, 1, nonStandardName);
						
		fTable.setWidget(6, 0, lIsPreferredName);
		fTable.setWidget(6, 1, isPreferredName);
		
		isPreferredName.setEnabled(isEditable);
		
	}
	
	public void populateFields(GwtPersonNameInfo pNameInfo){				
		if(pNameInfo != null){
			givenName.setText(pNameInfo.getGivenName());
			middleName.setText(pNameInfo.getMiddleName());
			surName.setText(pNameInfo.getSurname());
			suffix.setText(pNameInfo.getSuffix());
			nonStandardName.setText(pNameInfo.getNonStandardName());
			personTitle.setText(pNameInfo.getPersonTitle());
			isPreferredName.setChecked(pNameInfo.getPreferredName());
		} else{
			givenName.clear();
			middleName.clear();
			surName.clear();
			suffix.clear();
			nonStandardName.clear();
			personTitle.clear();
			isPreferredName.setChecked(false);
		}
	}
	
	
}
