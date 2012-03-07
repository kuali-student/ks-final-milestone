package org.kuali.student.lum.lu.ui.course.client.controllers;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseAdminRetireConfigurer;
import org.kuali.student.lum.lu.ui.course.client.widgets.CourseWorkflowActionList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * Controller for retire screen
 * 
 * @author Will
 *
 */
public class CourseAdminRetireController extends CourseAdminWithoutVersionController{
		
	/**
	 * Override the intitailzeController method to use CourseAdminConfigurer 
	 */
	@Override
	protected void initializeController() {
   		
		super.cfg = GWT.create(CourseAdminRetireConfigurer.class);
		
   		cfg.setState(DtoConstants.STATE_RETIRED.toUpperCase());
   		super.setDefaultModelId(cfg.getModelId());
   		super.registerModelsAndHandlers();
   		super.addStyleName("ks-course-admin");  	   		   		
    }
	
	/**
	 * Override the getSaveButton to provide a new set of buttons for the admin screens
	 */
	@Override
	public KSButton getSaveButton(){
		KSButton saveButton =  new KSButton("Retire", new ClickHandler(){
            public void onClick(ClickEvent event) {
            	handleButtonClick(DtoConstants.STATE_RETIRED);            	
            }
        });
		
		return saveButton;
	}
				
	public KSButton getCancelButton(){
		KSButton button = new KSButton("Cancel", new ClickHandler(){
            public void onClick(ClickEvent event) {       
            	Application.navigate(AppLocations.Locations.VIEW_COURSE.getLocation());
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

	                
					CourseWorkflowActionList.setCourseState(viewContext.getId(), DtoConstants.STATE_RETIRED, new Callback<String>(){
						@Override
						public void exec(String result) {
							if (result == null){
								KSNotifier.add(new KSNotification("Course saved, but unable to set retire state.", false, true, 5000));
							} else {
								KSNotifier.show("Course saved and retired.");									
								Application.navigate(AppLocations.Locations.VIEW_COURSE.getLocation(), viewContext);
							}							
						}
					});							
				}      
			}
    	});
    	
   		CourseAdminRetireController.this.fireApplicationEvent(saveActionEvent);    		
	}
	
	
	/**
     * Override the setHeaderTitle to display proper header title for admin screens
     */
	@Override
	protected void setHeaderTitle(){
		StringBuffer sb = new StringBuffer();
		sb.append(cluProposalModel.get(cfg.getCourseTitlePath()));
		sb.append(" (Retirement)");
				
		currentTitle = sb.toString();
		
		this.setContentTitle(currentTitle);
    	this.setName(currentTitle);
    	WindowTitleUtils.setContextTitle(currentTitle);		
    }

	protected  BaseDataOrchestrationRpcServiceAsync getCourseProposalRpcService(){
    	return courseServiceAsync;
    }
    	
    public boolean startSectionRequired(){
    	//There is no start section for retire screen
    	return false;
    }

    @Override
	public boolean isAuthorizationRequired() {
		//FIXME: Need to add proper authorization checks for admin modify.
		return false;
	}

	@Override
	protected void progressiveEnableFields() {
		//Does nothing, there are no progressive enabled fields on retire screens.
	}
	
	/**
	 * Override the getStateforSaveAction since the save action for retire course will change state to 
	 * retired.  
	 */
	@Override
    protected String getStateforSaveAction(DataModel model){
    	return cfg.getState();
    }
    
}
