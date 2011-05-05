package org.kuali.student.lum.lu.ui.course.client.controllers;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities;
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
            	final SaveActionEvent saveActionEvent = new SaveActionEvent(false);
            	saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
					@Override
					public void onActionComplete(ActionEvent actionEvent) {
						if (saveActionEvent.isSaveSuccessful()){
							workflowUtil.blanketApprove();
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
