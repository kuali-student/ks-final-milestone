package org.kuali.student.lum.lu.ui.course.client.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.LUConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseAdminConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer.CourseSections;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

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
	protected List<KSButton> cancelButtons = new ArrayList<KSButton>();
	
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
   		
        setViewContext(getViewContext());
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
                if (isNew) {
                    Application.navigate(AppLocations.Locations.CURRICULUM_MANAGEMENT.getLocation());
                } else {
                    //Cancel the proposal and navigate the user back to curriculum home if cancel was successful.
                    workflowUtil.cancel(new Callback<Boolean>() {
                        @Override
                        public void exec(Boolean result) {
                            if (result) {
                                Application.navigate(AppLocations.Locations.CURRICULUM_MANAGEMENT.getLocation());
                            }
                        }

                    });
                }
            }
        });
	
		cancelButton.addStyleName("ks-button-spacing");
		cancelButtons.add(cancelButton);
		return cancelButton;
    }
	
	/**
	 * Processes the save, approve, or approve and activate button clicks. The action is determined
	 * by the value of the state parameter.
	 * 
	 * @param state The state to set on the course when saving course data. DRAFT=Save, APPROVED=Approve, and
	 * ACTIVE=Approve & Activate
	 */
	protected void handleButtonClick(final String state){
		
    	//Set state on course before performing save action
		cluProposalModel.set(QueryPath.parse(CreditCourseConstants.STATE), state);
    	
    	final SaveActionEvent saveActionEvent = getSaveActionEvent(state);
    	
    	//Store the rules if save was called
    	if((String)cluProposalModel.get(CreditCourseConstants.ID)!=null && cfg instanceof CourseAdminConfigurer){
    		((CourseAdminConfigurer )cfg).getRequisitesSection().storeRules(new Callback<Boolean>(){
    			public void exec(Boolean result) {
					if(result){
						doAdminSaveAction(saveActionEvent, state);
					}else{
						KSNotifier.show("Error saving rules.");
					}
				}
    		});
    	}else{
            doAdminSaveAction(saveActionEvent, state);    		
    	}
	}
		
	/**
	 * Returns a SaveActionEvent with the appropriate ActionCompleteCallback, which will take additional admin actions once
	 * save is complete. The action (i.e. button clicked) is determined by the value of the state parameter 
	 * 
	 * @param state The state to set on the course when saving course data. DRAFT=Save, ACTIVE=Approve & Activate
	 * @return the save event that will be fired based on the button click
	 */
	private SaveActionEvent getSaveActionEvent(final String state){
    	final SaveActionEvent saveActionEvent = new SaveActionEvent(false);
		if (DtoConstants.STATE_ACTIVE.equalsIgnoreCase(state)){
    		saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
				@Override
				public void onActionComplete(ActionEvent actionEvent) {
					if (saveActionEvent.isSaveSuccessful()){
						workflowUtil.blanketApprove(new Callback<Boolean>(){
							@Override
							public void exec(Boolean result) {
								
								final ViewContext viewContext = new ViewContext();
				                viewContext.setId((String)cluProposalModel.get(CreditCourseConstants.ID));
				                viewContext.setIdType(IdType.OBJECT_ID);															
								if (DtoConstants.STATE_ACTIVE.equalsIgnoreCase(state)){
									KSNotifier.show("Course approved and activated. It may take a minute or two for course status to be updated. Refresh to see latest status.");
									Application.navigate(AppLocations.Locations.VIEW_COURSE.getLocation(), viewContext);
								}
								
							}
						});
					}      
				}
	    	});
    	} else {
    		//User clicked Save button. When user clicks save, both document upload and cancel should be enabled
    		saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
				@Override
				public void onActionComplete(ActionEvent action) {
					for (KSButton cancelButton:cancelButtons){
						cancelButton.setEnabled(true);
						((CourseAdminConfigurer )cfg).getDocumentTool().beforeShow(NO_OP_CALLBACK);
					}					
				}    			
    		});
    	}
		
		return saveActionEvent;
	}
	
	/**
	 * Fires the SaveActionEvent to be handled by the {@link CourseProposalController}. 
	 * 
	 *  @see CourseProposalController#registerModelsAndHandlers()
	 *  @param saveActionEvent SaveActionEvent to fire
	 *  @param state The state to set on the course when saving course data. DRAFT=Save, APPROVED=Approve, and
	 *  ACTIVE=Approve & Activate
	 */
	private void doAdminSaveAction(final SaveActionEvent saveActionEvent, String state){
		if (DtoConstants.STATE_APPROVED.equalsIgnoreCase(state) || DtoConstants.STATE_ACTIVE.equalsIgnoreCase(state)){
			//For Approved action, validate required fields for next (i.e.Approved) state before firing the save action
			cluProposalModel.validateNextState((new Callback<List<ValidationResultInfo>>() {
	            @Override
	            public void exec(List<ValidationResultInfo> result) {
	
	            	boolean isSectionValid = isValid(result, true);
	
	            	if(isSectionValid){
	            		CourseAdminController.this.fireApplicationEvent(saveActionEvent);            	}
	            	else{
	            		KSNotifier.add(new KSNotification("Unable to save, please check fields for errors.", false, true, 5000));
	            	}
	
	            }
	        }));
		} else {
    		CourseAdminController.this.fireApplicationEvent(saveActionEvent);			
		} 		
	}
	
	/**
	 * 
	 * Override {@link CourseProposalController} because end term should always be editable 
	 * in admin screens.
	 */
    protected void progressiveEnableFields() {
        super.progressiveEnableFields();
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
    public void addMenuItemSection(String parentMenu, final String sectionName, final String widgetId, final Widget widget) {    	
        KSMenuItemData parentItem = null;
        for (int i = 0; i < topLevelMenuItems.size(); i++) {
            if (topLevelMenuItems.get(i).getLabel().equals(parentMenu)) {
                parentItem = topLevelMenuItems.get(i);
                break;
            }
        }

        KSMenuItemData item = new KSMenuItemData(sectionName);
    	widget.getElement().setId(widgetId);
    	item.setClickHandler(new ClickHandler(){
			@Override
    	    public void onClick(ClickEvent event) {
			    Element element = DOM.getElementById(widgetId);
			    scrollToSection(element);
			}
    	});

        if (parentItem != null) {
            parentItem.addSubItem(item);
        } else {
            topLevelMenuItems.add(item);
        }

        menu.refresh();
    }

    public native void scrollToSection(Element element) /*-{
        element.scrollIntoView();
    }-*/;

	@Override
	protected void configureScreens(DataModelDefinition modelDefinition, final Callback<Boolean> onReadyCallback) {
		super.configureScreens(modelDefinition, previousEndTermConfigurationCallback(onReadyCallback));
	}

	/**
	 * This callback is used to configure the previous end term field after the screens have been configured.
	 * 
	 * @param onReadyCallback
	 * @return
	 */
	protected Callback<Boolean> previousEndTermConfigurationCallback(final Callback<Boolean> onReadyCallback){
		return new Callback<Boolean>(){

			@Override
			public void exec(final Boolean result) {
				requestModel(new ModelRequestCallback<DataModel>(){
					@Override
					public void onModelReady(DataModel model) {
						//In admin screens add the previous end term field to the active dates section and update it's values   
						//ONLY when in edit mode. This way it doesn't attempt to retrieve non-existent "Course Info" section;
						//only the "Summary" section has been configured in non-edit mode.
						if (model.getDefinition().getMetadata().isCanEdit()){
							String versionedFromId = model.get("versionInfo/versionedFromId");
							if(versionedFromId!=null && !versionedFromId.isEmpty()){
								//Add the required field 
								//See why the required for next state is not set
								VerticalSectionView view = (VerticalSectionView) viewMap.get(CourseSections.COURSE_INFO);							
								Section activeDatesSection = view.getSection(LUUIConstants.ACTIVE_DATES_LABEL_KEY);
								Metadata meta = cfg.getModelDefinition().getMetadata(CourseProposalConfigurer.PROPOSAL_PATH + "/" + CreditCourseConstants.PREV_END_TERM);
								if(meta!=null&&meta.getConstraints().get(0)!=null){
									meta.getConstraints().get(0).setRequiredForNextState(true);
									meta.getConstraints().get(0).setNextState("ACTIVE");
								}
								FieldDescriptor fd = cfg.addField(activeDatesSection, CourseProposalConfigurer.PROPOSAL_PATH + "/" + CreditCourseConstants.PREV_END_TERM, cfg.generateMessageInfo(LUUIConstants.PROPOSAL_PREV_END_TERM));
								
								//FIXME: This static method should not live in WorkflowUtilities
								WorkflowUtilities.updateCrossField(fd, model);
							}
						}
						
						onReadyCallback.exec(result);
					}
					
					@Override
					public void onRequestFail(Throwable cause) {
						throw new RuntimeException("Error getting model",cause);
					}
				});
			}
		};
	}

    @Override
    public void setViewContext(ViewContext viewContext) {
        //Determine the permission type being checked
        if (viewContext.getId() != null && !viewContext.getId().isEmpty()) {
            if (viewContext.getIdType() != IdType.COPY_OF_OBJECT_ID
                    && viewContext.getIdType() != IdType.COPY_OF_KS_KEW_OBJECT_ID) {
                //Id provided, and not a copy id, so opening an existing proposal
                viewContext.setPermissionType(PermissionType.OPEN);
            } else {
                //Copy id provided, so creating a proposal for modification
                viewContext.setPermissionType(PermissionType.INITIATE);
            }
        } else {
            //No id in view context, so creating new empty proposal
            viewContext.setPermissionType(PermissionType.INITIATE);

        }
        
        context = viewContext; 
    }
	
	/**
	 * This method adds any permission attributes required for checking admin permissions
	 */
	public void addPermissionAttributes(Map<String, String> attributes){
		super.addPermissionAttributes(attributes);
		
		ViewContext viewContext = getViewContext();
		
		//Determine the permission type being checked
    	if(viewContext.getId() != null && !viewContext.getId().isEmpty()){
    		if(viewContext.getIdType() != IdType.COPY_OF_OBJECT_ID && viewContext.getIdType() != IdType.COPY_OF_KS_KEW_OBJECT_ID){
    			//Id provided, and not a copy id, so opening an existing proposal
    			attributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, LUConstants.PROPOSAL_TYPE_COURSE_CREATE_ADMIN);
    		} else{
    			//Copy id provided, so creating a proposal for modification
    			attributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, LUConstants.PROPOSAL_TYPE_COURSE_MODIFY_ADMIN);
    		}
    	} else{
    		//No id in view context, so creating new empty proposal
			attributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, LUConstants.PROPOSAL_TYPE_COURSE_CREATE_ADMIN);    		
    	}    	
	}
	
}

