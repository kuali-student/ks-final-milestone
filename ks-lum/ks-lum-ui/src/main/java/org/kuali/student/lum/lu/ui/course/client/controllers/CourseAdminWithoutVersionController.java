package org.kuali.student.lum.lu.ui.course.client.controllers;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseAdminConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseAdminWithoutVersionConfigurer;
import org.kuali.student.lum.lu.ui.course.client.widgets.CourseWorkflowActionList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * Controller for create/modify admin screens
 * 
 * @author Will
 *
 */
public class CourseAdminWithoutVersionController extends CourseAdminController{
		
	/**
	 * Override the intitailzeController method to use CourseAdminConfigurer 
	 */
	@Override
	protected void initializeController() {
   		
		super.cfg = GWT.create(CourseAdminWithoutVersionConfigurer.class);
		
		//FIXME: This may not be what we want to do
   		cfg.setState(DtoConstants.STATE_APPROVED.toUpperCase());
   		cfg.setNextState(DtoConstants.STATE_ACTIVE.toUpperCase());
   		super.setDefaultModelId(cfg.getModelId());
   		super.registerModelsAndHandlers();
   		super.addStyleName("ks-course-admin");  	   		   		
    }
	
	/**
	 * Override the getSaveButton to provide a new set of buttons for the admin screens
	 */
	@Override
	public KSButton getSaveButton(){
		KSButton saveButton =  new KSButton("Save", new ClickHandler(){
            public void onClick(ClickEvent event) {
            	handleButtonClick(DtoConstants.STATE_APPROVED);            	
            }
        });
		
		return saveButton;
	}
		
	public KSButton getActivateButton(){
		KSButton activateButton = new KSButton("Activate", new ClickHandler(){
            public void onClick(ClickEvent event) {       
            	handleButtonClick(DtoConstants.STATE_ACTIVE);
            }
        });
		
		activateButton.addStyleName("ks-button-spacing");		
		
		//Enable the activateButton only when state is not active 
		boolean isApproved = DtoConstants.STATE_APPROVED.equalsIgnoreCase((String)cluProposalModel.get("state"));
		activateButton.setEnabled(isApproved);

		return activateButton;
    }
		
	public KSButton getCancelButton(){
		KSButton button = new KSButton("Cancel", new ClickHandler(){
            public void onClick(ClickEvent event) {       
            	Application.navigate(AppLocations.Locations.CURRICULUM_MANAGEMENT.getLocation());
            }
        });
	
		button.addStyleName("ks-button-spacing");
		return button;
    }
	
	/**
	 * This processes the save or activate button clicks
	 * 
	 * @param state The state to set on the course when saving course data.
	 */
	protected void handleButtonClick(final String state){
		final SaveActionEvent saveActionEvent = new SaveActionEvent(false);

    	saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
			@Override
			public void onActionComplete(ActionEvent actionEvent) {
				if (saveActionEvent.isSaveSuccessful()){
	                final ViewContext viewContext = new ViewContext();
	                viewContext.setId((String)cluProposalModel.get(CreditCourseConstants.ID));
	                viewContext.setIdType(IdType.OBJECT_ID);											
					if (DtoConstants.STATE_ACTIVE.equalsIgnoreCase(state)){
						//Call change state method to change the state of the course active when user clicks Save & Activate button
						CourseWorkflowActionList.setCourseState(viewContext.getId(), DtoConstants.STATE_ACTIVE, new Callback<String>(){
							@Override
							public void exec(String result) {
								if (result == null){
									KSNotifier.show("Course saved, but activation failed.");
								} else {
									KSNotifier.show("Course saved and activated.");									
									Application.navigate(AppLocations.Locations.VIEW_COURSE.getLocation(), viewContext);
								}							
							}
						});							
					} else if (DtoConstants.STATE_APPROVED.equalsIgnoreCase(state)){
						KSNotifier.show("Course saved.");
						Application.navigate(AppLocations.Locations.VIEW_COURSE.getLocation(), viewContext);						
					}
				}      
			}
    	});
    	
    	//Store the rules if save was called
    	if((String)cluProposalModel.get(CreditCourseConstants.ID)!=null && cfg instanceof CourseAdminConfigurer){
    		((CourseAdminConfigurer )cfg).getRequisitesSection(this).storeRules(new Callback<Boolean>(){
    			public void exec(Boolean result) {
					if(result){
						CourseAdminWithoutVersionController.this.fireApplicationEvent(saveActionEvent); 
					}else{
						KSNotifier.show("Error saving rules.");
					}
				}
    		});
    	}else{
    		CourseAdminWithoutVersionController.this.fireApplicationEvent(saveActionEvent);    		
    	}
	}
	
	
	/**
     * Override the setHeaderTitle to display proper header title for admin screens
     */
	@Override
	protected void setHeaderTitle(){
		StringBuffer sb = new StringBuffer();
		sb.append("Modify: ");
		sb.append(cluProposalModel.get(cfg.getCourseTitlePath()));
		sb.append(" (Admin)");
				
		currentTitle = sb.toString();
		
		this.setContentTitle(currentTitle);
    	this.setName(currentTitle);
    	WindowTitleUtils.setContextTitle(currentTitle);		
    }

	protected  BaseDataOrchestrationRpcServiceAsync getCourseProposalRpcService(){
    	return courseServiceAsync;
    }
    	
    public boolean startSectionRequired(){
    	return false;
    }
	@Override
	public boolean isAuthorizationRequired() {
		//FIXME: Need to add proper authorization checks for admin modify.
		return false;
	}

}
