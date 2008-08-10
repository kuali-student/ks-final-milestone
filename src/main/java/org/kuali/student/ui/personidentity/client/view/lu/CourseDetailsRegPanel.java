package org.kuali.student.ui.personidentity.client.view.lu;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.registration.client.controller.RegistrationController;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationDisplay;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationInfo;
import org.kuali.student.registration.client.model.RegistrationModelState;
import org.kuali.student.registration.client.service.RegistrationService;
import org.kuali.student.ui.personidentity.client.ModelState;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;
import org.kuali.student.ui.personidentity.client.model.lu.LuModelState;
import org.kuali.student.ui.personidentity.client.view.AdminEditPanel;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Garey
 *
 */
public class CourseDetailsRegPanel extends VerticalPanel {

	HorizontalPanel	buttonPanel = null;
	Button			register	= null;
	Button			addToBasket	= null;
	
	CourseDetails	cDetails	= null;
	
	public CourseDetailsRegPanel(){
		super();
		
		setup();
		register.addStyleName("KS-Button");
		addToBasket.addStyleName("KS-Button");
	}
	
	ClickListener listAddBasket = new ClickListener(){
		public void onClick(Widget sender) {
			
			if(ModelState.getInstance().getCurrPerson() != null
					&& LuModelState.getInstance().getCurrLui() != null	
			){																
			RegistrationController.getInstance().createLuiPersonRelation(
					ModelState.getInstance().getCurrPerson().getPersonId(), 
					LuModelState.getInstance().getCurrLui().getLuiId(), 
					RegistrationService.STATE_BASKET , RegistrationService.REL_STUDENT, new GwtLuiPersonRelationInfo());
			}
			//Show message and disable buttons
			cDetails.showNotification(true, CourseDetails.BASKET_ADD);
			register.setEnabled(false);
			addToBasket.setEnabled(false);
		}};
	
		ClickListener listRegister = new ClickListener(){
			public void onClick(Widget sender) {
				
				if(ModelState.getInstance().getCurrPerson() != null
						&& LuModelState.getInstance().getCurrLui() != null	
				){																
				RegistrationController.getInstance().createLuiPersonRelation(
						ModelState.getInstance().getCurrPerson().getPersonId(), 
						LuModelState.getInstance().getCurrLui().getLuiId(), 
						RegistrationService.STATE_COMPLETE , RegistrationService.REL_STUDENT, new GwtLuiPersonRelationInfo());
				}
				//Show message and disable buttons
				cDetails.showNotification(true, CourseDetails.REGISTER_NOTE);
				register.setEnabled(false);
				addToBasket.setEnabled(false);
			}};
	
	
	protected void setup(){
		register	= new Button(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("register"));
		addToBasket = new Button(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("addToBasket"));
		buttonPanel = new HorizontalPanel();
		cDetails	= new CourseDetails();
		
		addToBasket.addClickListener(listAddBasket);
		register.addClickListener(listRegister);
		
		register.setVisible(false);
		addToBasket.setVisible(false);		
				
		buttonPanel.add(register);
		buttonPanel.add(addToBasket);
		
		this.add(cDetails);
		this.add(buttonPanel);		
	}
	
	/**
	 * @param in
	 */
	public void populate(GwtLuiInfo in){
		cDetails.populate(in);
		
		ModelState.getInstance().getCurrPerson();
		List<GwtLuiPersonRelationDisplay> courses = RegistrationModelState.getInstance().getCurrUserRelations();
		register.setEnabled(true);
		addToBasket.setEnabled(true);
		cDetails.showNotification(false, null);
		
		for(GwtLuiPersonRelationDisplay info: courses)
		{
			//if they are registered for or have this course in their cart
			if(LuModelState.getInstance().getCurrLui().getLuiId().equals( info.getLuiDisplay().getLuiId() ))
			{
				register.setEnabled(false);
				addToBasket.setEnabled(false);
				if(info.getRelationState().getState().equals(RegistrationService.STATE_BASKET.getState()))
				{
					cDetails.showNotification(true, CourseDetails.IN_BASKET);
				}
				else if(info.getRelationState().getState().equals(RegistrationService.STATE_COMPLETE.getState()))
				{
					cDetails.showNotification(true, CourseDetails.REGISTERED);
				}
			}
		}
		
		//if there is a current person display the registration buttons
		if(ModelState.getInstance().getCurrPerson() != null)
		{
			register.setVisible(true);
			addToBasket.setVisible(true);
		}
		else
		{
			register.setVisible(false);
			addToBasket.setVisible(false);
		}

	}
	public void populate(GwtCluInfo in){
		cDetails.populate(in);
		register.setVisible(false);
		addToBasket.setVisible(false);
		cDetails.showNotification(false, null);
	}
	public void populate(GwtCluDisplay in){
		cDetails.populate(in);
		register.setVisible(false);
		addToBasket.setVisible(false);
		cDetails.showNotification(false, null);
	}
	/**
	 * @return the register
	 */
	public Button getRegister() {
		return register;
	}

	/**
	 * @return the addToBasket
	 */
	public Button getAddToBasket() {
		return addToBasket;
	}

	public CourseDetails getCourseDetialsWidget(){
		return this.cDetails;
	}
	
}
