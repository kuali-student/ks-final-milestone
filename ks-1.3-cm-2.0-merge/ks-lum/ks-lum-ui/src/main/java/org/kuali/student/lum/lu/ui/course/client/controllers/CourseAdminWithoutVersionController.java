package org.kuali.student.lum.lu.ui.course.client.controllers;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
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
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseAdminWithoutVersionConfigurer;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.DtoConstants.DtoState;

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
	 * Override the progressive enable fields to only allow edit of end term and retire fields when pilot box checked and course is not active
	 */
	@Override
	protected void progressiveEnableFields() {
		super.progressiveEnableFields();
        final FieldDescriptor retireRationale = Application.getApplicationContext().getPathToFieldMapping(null,CreditCourseConstants.RETIREMENT_RATIONALE); 
        final FieldDescriptor lastTermOffered = Application.getApplicationContext().getPathToFieldMapping(null,CreditCourseConstants.LAST_TERM_OFFERED);
        final FieldDescriptor lastPublicationYear = Application.getApplicationContext().getPathToFieldMapping(null,CreditCourseConstants.LAST_PUBLICATION_YEAR);
        final FieldDescriptor specialCircumstances = Application.getApplicationContext().getPathToFieldMapping(null,CreditCourseConstants.SPECIAL_CIRCUMSTANCES);
        	
		String courseState = cluProposalModel.get(CreditCourseConstants.STATE);
		Boolean pilotValue = cluProposalModel.get(CreditCourseConstants.PILOT_COURSE);
		
		Boolean enableRetireFields = !DtoState.ACTIVE.equalsString(courseState) && (pilotValue == null || !pilotValue);
		
		BaseSection.progressiveEnableFields(enableRetireFields, retireRationale, lastTermOffered, lastPublicationYear, specialCircumstances);
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
					if (DtoConstants.STATE_ACTIVE.equalsIgnoreCase(state) || DtoConstants.STATE_APPROVED.equalsIgnoreCase(state)){
						KSNotifier.show("Course saved.");
						Application.navigate(AppLocations.Locations.VIEW_COURSE.getLocation(), viewContext);						
					}
				}      
			}
    	});
    	
    	//Store the rules if save was called
    	if((String)cluProposalModel.get(CreditCourseConstants.ID)!=null && cfg instanceof CourseAdminWithoutVersionConfigurer){
    		((CourseAdminWithoutVersionConfigurer)cfg).getRequisitesSection().storeRules(new Callback<Boolean>(){
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
	
	@Override
	protected Callback<Boolean> previousEndTermConfigurationCallback(Callback<Boolean> onReadyCallback) {
		//No need to display previous end term when modifying without version, just return original callback.
		return onReadyCallback;
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
