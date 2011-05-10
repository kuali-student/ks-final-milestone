package org.kuali.student.lum.lu.ui.course.client.controllers;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseAdminConfigurer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * Controller for create/modify admin screens
 * 
 * @author Will
 *
 */
public class CourseAdminController extends CourseProposalController{

	/**
	 * Override the intitailzeController method to use CourseAdminConfigurer 
	 */
	@Override
	protected void initializeController() {
		super.cfg = GWT.create(CourseAdminConfigurer.class);
		super.proposalPath = cfg.getProposalPath();
   		super.workflowUtil = new WorkflowUtilities(CourseAdminController.this ,proposalPath);
		
   		cfg.setState(DtoConstants.STATE_APPROVED);
   		super.setDefaultModelId(cfg.getModelId());
   		registerModelsAndHandlers();
    }
	
	/**
	 * Override the getSaveButton to provide a new set of buttons for the admin screens
	 */
	@Override
	public KSButton getSaveButton(){
		return new KSButton("Approve", new ClickHandler(){
            public void onClick(ClickEvent event) {
				//When user clicks approve fire a save action to save the course data. If the
            	//save was successful, the action complete will be called, we can then blanket
				//approve the course. If blanket approve call was successful we can navigate the
				//user to the View Course screens for the recently approved course.

            	final SaveActionEvent saveActionEvent = new SaveActionEvent(false);
            	saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
					@Override
					public void onActionComplete(ActionEvent actionEvent) {
						if (saveActionEvent.isSaveSuccessful()){
							workflowUtil.blanketApprove(new Callback<Boolean>(){
								@Override
								public void exec(Boolean result) {
									//TODO: Async issues, how to handle if course not approved by wf yet
				                    ViewContext viewContext = new ViewContext();
				                    viewContext.setId((String)cluProposalModel.get(CreditCourseConstants.ID));
				                    viewContext.setIdType(IdType.OBJECT_ID);							
									Application.navigate(AppLocations.Locations.VIEW_COURSE.getLocation(), viewContext);
									KSNotifier.show("Course approved. It may take a minute or two for the course status to be updated.");
								}
							});
						}
					}            		
            	});
                CourseAdminController.this.fireApplicationEvent(saveActionEvent);
            }
        });
    }
	
	
    /**
     * Override the setHeaderTitle to display proper header title for admin screens
     */
	@Override
	protected void setHeaderTitle(){
    	String title = "New Course (Admin View)";
    	super.setContentTitle(title);
    	super.setName(title);
    	WindowTitleUtils.setContextTitle(title);
		super.currentTitle = title;
    }
    
}
