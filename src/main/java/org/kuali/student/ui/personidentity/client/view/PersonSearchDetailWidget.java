/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.widgets.DateBox;
import org.kuali.student.ui.personidentity.client.ModelState;
import org.kuali.student.ui.personidentity.client.controller.PersonIdentityController;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Garey
 *
 * This class should be used to display a brief amount of data about a person.
 * The initial use is for the search result display. 
 * 
 * The only action on the page is to click the header hyperlink. this
 * should bring you to the person details screen.
 */
public class PersonSearchDetailWidget extends FlexTable {

	Hyperlink 	header	= new Hyperlink();
	Label		title	= new Label();
	//Label		lTitle	= new Label("Title: ");
	Label		lTitle	= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("title"));
	
	Label		dob		= new Label();
	//Label		lDob	= new Label("DOB: ");
	Label		lDob	= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("DOB"));
	
	Label		citz	= new Label();
	//Label		lCitz	= new Label("Citizenship: ");
	Label		lCitz	= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("citizenship"));
	
	GwtPersonInfo	pInfo = null;
	
	ClickListener displayPerson = new ClickListener(){		
		public void onClick(Widget sender) {
			ModelState.getInstance().setCurrPerson(pInfo);		
			PersonIdentityController.viewPersonDetailsScreen();
		}};
	
	/**
	 * 
	 */
	public PersonSearchDetailWidget() {
		this.arrangeWidget();		
	}
	
	public PersonSearchDetailWidget(GwtPersonInfo pInfo){
		this.pInfo = pInfo;
		this.arrangeWidget();
		this.populateWidget(pInfo);
	}
	
	protected void arrangeWidget(){
		setWidget(0, 0, header);
		
		header.addClickListener(displayPerson);
		
		lTitle.addStyleName("KS-personTitle-Label");
		lDob.addStyleName("KS-DOB-Label");
		lCitz.addStyleName("KS-Label");
		citz.addStyleName("KS-Label");
		
		setWidget(1, 0, lTitle);
		setWidget(1, 1, title);
		setWidget(1, 2, lDob);
		setWidget(1, 3, dob);
		
		setWidget(2, 0, lCitz);
		setWidget(2, 1, citz);	
	}
	
	public void populateWidget(GwtPersonInfo pInfo){
		String pName = "";
		
		pName += pInfo.getPreferredName().getPersonTitle();
		pName += " ";
		pName += pInfo.getPreferredName().getGivenName();
		pName += " ";
		pName += pInfo.getPreferredName().getMiddleName();
		pName += " ";
		pName += pInfo.getPreferredName().getSurname();
		
		header.setText( pName);
		title.setText("TBD");
		if(pInfo.getBirthDate() != null)
			dob.setText(DateBox.formatter.format(pInfo.getBirthDate()));
		citz.setText(pInfo.getCitizenship().getCountryOfCitizenshipCode());
		
		
	}

}
