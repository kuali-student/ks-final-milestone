package org.kuali.student.lum.lu.ui.course.client.controllers;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.LUConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseAdminConfigurer;
import org.kuali.student.lum.lu.ui.course.client.widgets.CourseWorkflowActionList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;

/**
 * Controller for create/modify course with proposal wrapper admin screens. This controller uses a different
 * configurer for admin screens and attempts to reuse as much of the validation, save & retreive logic coded
 * in the CourseProposalController.  Also it reuses the menu from CourseProposalController and adds click
 * handlers to button menu to navigate user b/w sections of the same view.
 * 
 * @author Will
 *
 */
public class CourseAdminController extends CourseProposalController{
	
	//Need to keep track of cancel buttons, so they can be enabled when course has been saved. 
	List<KSButton> cancelButtons = new ArrayList<KSButton>();
	
	/**
	 * Override the intitailzeController method to use CourseAdminConfigurer 
	 */
	@Override
	protected void initializeController() {
   		
		super.cfg = GWT.create(CourseAdminConfigurer.class);
		super.proposalPath = cfg.getProposalPath();
   		super.workflowUtil = new WorkflowUtilities(CourseAdminController.this ,proposalPath);
		
   		cfg.setState(DtoConstants.STATE_DRAFT.toUpperCase());
   		cfg.setNextState(DtoConstants.STATE_APPROVED.toUpperCase());
   		super.setDefaultModelId(cfg.getModelId());
   		super.registerModelsAndHandlers();
   		super.addStyleName("ks-course-admin");
   		currentDocType = LUConstants.PROPOSAL_TYPE_COURSE_CREATE_ADMIN;
   		//this.removeMenu();  	   		   		
    }
    
	/**
	 * Override the getSaveButton to provide a new set of buttons for the admin screens
	 */
	@Override
	public KSButton getSaveButton(){
		KSButton saveButton = new KSButton("Save", new ClickHandler(){
            public void onClick(ClickEvent event) {
            	handleButtonClick(DtoConstants.STATE_DRAFT);            	
            }
        });		
		
		saveButton.addStyleName("ks-button-spacing");
		return saveButton;
	}
		
	public KSButton getApproveButton(){
		KSButton approveButton = new KSButton("Approve", new ClickHandler(){
            public void onClick(ClickEvent event) {       
            	handleButtonClick(DtoConstants.STATE_APPROVED);
            }
        });
		
		approveButton.addStyleName("ks-button-spacing");
		return approveButton;
    }
		
	public KSButton getApproveAndActivateButton(){
		return new KSButton("Approve and Activate", new ClickHandler(){
            public void onClick(ClickEvent event) {
            	handleButtonClick(DtoConstants.STATE_ACTIVE);
            }
        });		
	}

	public KSButton getCancelButton(){
		KSButton cancelButton = new KSButton("Cancel Proposal", new ClickHandler(){
            public void onClick(ClickEvent event) {
            	//Cancel the proposal and navigate the user back to curriculum home if cancel was successful.
            	workflowUtil.cancel(new Callback<Boolean>(){
					@Override
					public void exec(Boolean result) {
						if (result){
							Application.navigate(AppLocations.Locations.CURRICULUM_MANAGEMENT.getLocation());							
						}						
					}
            		
            	});
            }
        });
	
		if (LUConstants.PROPOSAL_TYPE_COURSE_CREATE_ADMIN.equals(currentDocType)){
			//For new admin proposal, disable the cancel button intially since proposal doesn't exist
			//until they click save.
			cancelButton.setEnabled(false);
		}
		
		cancelButton.addStyleName("ks-button-spacing");
		cancelButtons.add(cancelButton);
		return cancelButton;
    }
	
	/**
	 * This processes the save, approve, or approve and activate button clicks. The action is determined
	 * by the value of the state parameter.
	 * 
	 * @param state The state to set on the course when saving course data. DRAFT=Save, APPROVED=Approve, and
	 * ACTIVE=Approve & Activate
	 */
	protected void handleButtonClick(final String state){
    	cluProposalModel.set(QueryPath.parse(CreditCourseConstants.STATE), state);
    	final SaveActionEvent saveActionEvent = new SaveActionEvent(false);
    	
    	if (DtoConstants.STATE_APPROVED.equalsIgnoreCase(state) || DtoConstants.STATE_ACTIVE.equalsIgnoreCase(state)){
        	// For "Approve" and "Approve & Activate" actions, automatically blanket approve the admin proposal so it 
        	// enters final state. This is accomplished by first saving the course (via saveActionEvent) and then by 
        	// executing the the blanket approve call upon a successful save. When user clicks either of these buttons 
        	// and blanket approve is successful, they are navigated to the view course screen.

    		saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
				@Override
				public void onActionComplete(ActionEvent actionEvent) {
					if (saveActionEvent.isSaveSuccessful()){
						workflowUtil.blanketApprove(new Callback<Boolean>(){
							@Override
							public void exec(Boolean result) {
						    	// FIXME:  Even though workflow rpc call to blanket approve is successful, the
								// asynchronous nature of workflow is causing timing issues here. Need to investigate
						    	// making blanket approve workflow more synchronous to avoid the timing issues.
								// NOTE: One solution is to not allow an activate here, and force user to activate from view
								// screen, the down side being user now requires two clicks.
								
								final ViewContext viewContext = new ViewContext();
				                viewContext.setId((String)cluProposalModel.get(CreditCourseConstants.ID));
				                viewContext.setIdType(IdType.OBJECT_ID);															
								if (DtoConstants.STATE_APPROVED.equalsIgnoreCase(state)){
									KSNotifier.show("Course approved. It may take a minute or two for course status to be updated. Refresh to see latest status.");
									Application.navigate(AppLocations.Locations.VIEW_COURSE.getLocation(), viewContext);
								} else if (DtoConstants.STATE_ACTIVE.equalsIgnoreCase(state)){
									//For "Approve and Activate", call change state rpc method to properly activate the course
									CourseWorkflowActionList.setCourseState(viewContext.getId(), DtoConstants.STATE_ACTIVE, new Callback<String>(){
										@Override
										public void exec(String result) {
											if (result == null){
												KSNotifier.show("Course approved, but activation failed.");
											} else {
												KSNotifier.show("Course approved and activated. It may take a minute or two for course status to be updated. Refresh to see latest status.");									
											}
											Application.navigate(AppLocations.Locations.VIEW_COURSE.getLocation(), viewContext);
										}
									});							
								}
								
							}
						});
					}      
				}
	    	});
    	} else {
    		//For a save event all this does is enable the cancel button to allow user to cancel the proposal.
    		saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
				@Override
				public void onActionComplete(ActionEvent action) {
					for (KSButton cancelButton:cancelButtons){
						cancelButton.setEnabled(true);
					}					
				}    			
    		});
    	}
    	
        CourseAdminController.this.fireApplicationEvent(saveActionEvent);		
	}
	
	/**
     * Override the setHeaderTitle to display proper header title for admin screens
     */
	@Override
	protected void setHeaderTitle(){
    	String title;
    	if (cluProposalModel.get(cfg.getProposalTitlePath()) != null){
    		title = getProposalTitle();
    	}
    	else{
    		title = "New Course (Admin Proposal)";
    	}
    	this.setContentTitle(title);
    	this.setName(title);
    	WindowTitleUtils.setContextTitle(title);
		currentTitle = title;
    }
    
	private String getProposalTitle(){
		StringBuffer sb = new StringBuffer();
		sb.append(cluProposalModel.get(cfg.getProposalTitlePath()));
		sb.append(" (Admin Proposal)");
		return sb.toString();
	}
	
	/**
	 * This is a special method for CourseAdminController, which adds a menu item to the navigation menu and navigates
	 * a user to a section within the view rather than a different view.
	 * 
	 * @param parentMenu
	 * @param sectionName
	 * @param sectionId
	 * @param section
	 */
    public void addMenuItemSection(String parentMenu, final String sectionName, final String sectionId, final Section section) {    	
        KSMenuItemData parentItem = null;
        for (int i = 0; i < topLevelMenuItems.size(); i++) {
            if (topLevelMenuItems.get(i).getLabel().equals(parentMenu)) {
                parentItem = topLevelMenuItems.get(i);
                break;
            }
        }

        KSMenuItemData item = new KSMenuItemData(sectionName);
    	((BaseSection)section).setSectionId(sectionId);
    	item.setClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {		
				//FIXME: This doesn't scroll to exactly the position stuff
				DOM.getElementById(sectionId).scrollIntoView();
			}    		
    	});

        if (parentItem != null) {
            parentItem.addSubItem(item);
        } else {
            topLevelMenuItems.add(item);
        }

        menu.refresh();
    }
    
}
