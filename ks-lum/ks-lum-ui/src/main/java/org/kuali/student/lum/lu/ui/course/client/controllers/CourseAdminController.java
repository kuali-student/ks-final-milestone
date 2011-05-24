package org.kuali.student.lum.lu.ui.course.client.controllers;

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
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseAdminConfigurer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;

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
		
   		cfg.setState(DtoConstants.STATE_DRAFT.toUpperCase());
   		cfg.setNextState(DtoConstants.STATE_APPROVED.toUpperCase());
   		super.setDefaultModelId(cfg.getModelId());
   		super.registerModelsAndHandlers();
   		super.addStyleName("ks-course-admin");
   		//this.removeMenu();  	   		   		
    }
	
	/**
	 * Override the getSaveButton to provide a new set of buttons for the admin screens
	 */
	@Override
	public KSButton getSaveButton(){
		return new KSButton("Save", new ClickHandler(){
            public void onClick(ClickEvent event) {
            	handleButtonClick(DtoConstants.STATE_DRAFT);            	
            }
        });		
	}
		
	public KSButton getApproveButton(){
		return new KSButton("Approve", new ClickHandler(){
            public void onClick(ClickEvent event) {       
            	handleButtonClick(DtoConstants.STATE_APPROVED);
            }
        });
    }
		
	public KSButton getApproveAndActivateButton(){
		return new KSButton("Approve and Activate", new ClickHandler(){
            public void onClick(ClickEvent event) {
            	handleButtonClick(DtoConstants.STATE_ACTIVE);
            }
        });		
	}

	public KSButton getCancelButton(){
		KSButton button = new KSButton("Cancel", new ClickHandler(){
            public void onClick(ClickEvent event) {       
            	
            }
        });
	
		button.setEnabled(false);
		return button;
    }
	
	/**
	 * This processes the save, approve, or approve and activate button clicks
	 * 
	 * @param state The state to set on the course when saving course data.
	 */
	protected void handleButtonClick(final String state){
    	cluProposalModel.set(QueryPath.parse(CreditCourseConstants.STATE), state);
    	final SaveActionEvent saveActionEvent = new SaveActionEvent(false);
    	if (DtoConstants.STATE_APPROVED.equalsIgnoreCase(state) || DtoConstants.STATE_ACTIVE.equalsIgnoreCase(state)){
	    	saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
				@Override
				public void onActionComplete(ActionEvent actionEvent) {
					if (saveActionEvent.isSaveSuccessful()){
						workflowUtil.blanketApprove(new Callback<Boolean>(){
							@Override
							public void exec(Boolean result) {
				                ViewContext viewContext = new ViewContext();
				                viewContext.setId((String)cluProposalModel.get(CreditCourseConstants.ID));
				                viewContext.setIdType(IdType.OBJECT_ID);							
								Application.navigate(AppLocations.Locations.VIEW_COURSE.getLocation(), viewContext);
								if (DtoConstants.STATE_APPROVED.equalsIgnoreCase(state)){
									KSNotifier.show("Course approved.");
								} else {
									KSNotifier.show("Course approved and activated.");
								}
							}
						});
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
