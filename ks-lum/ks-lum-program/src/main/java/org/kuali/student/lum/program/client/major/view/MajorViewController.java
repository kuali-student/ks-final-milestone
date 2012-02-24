package org.kuali.student.lum.program.client.major.view;



import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Data.Property;
import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.common.ui.client.security.SecurityContext;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.shared.IdAttributes;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.lu.LUUIPermissions;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.common.client.widgets.DropdownList;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.ProgramStatus;
import org.kuali.student.lum.program.client.events.ModelLoadedEvent;
import org.kuali.student.lum.program.client.events.ProgramViewEvent;
import org.kuali.student.lum.program.client.major.ActionType;
import org.kuali.student.lum.program.client.major.MajorController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalPanel;


public class MajorViewController extends MajorController implements RequiresAuthorization{

    // TODO: Change to program and copy msgs
    private static final String MSG_GROUP = "program";
    
    /**
     * Initialize the action drop-down with a list of values.  Note that these values
     * will be changed further down in the code depending on if we are working with the latest 
     * version of the program.
     */
    private final DropdownList actionBox = new DropdownList(ActionType.getValuesForMajorDiscipline(false));
 
    // Used to pass flag if this is the latest version of the program from
    // an async call to the light box so we can conditionally decided
    // to display the use curriculum review process checkbox
    private boolean isCurrentVersion;
    
    /**
     * Constructor.
     *
     * @param programModel
     */
    public MajorViewController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus) {
        super(programModel, viewContext, eventBus);
        configurer = GWT.create(MajorViewConfigurer.class);  
        
        // Initialize handlers and action drop-down
        initHandlers();
     }
 
    private void initHandlers() {
        
        /*
         * Action drop-down box on-change
         */
        actionBox.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                
                // Get the action selected in the drop-down
                ActionType actionType = ActionType.of(actionBox.getSelectedValue());
                ViewContext viewContext = getViewContext();
                
                // If modify is selected
                if (actionType == ActionType.MODIFY) {
                    processModifyActionType(viewContext);
                }
                // If retire is selected
                else if (actionType == ActionType.RETIRE) {
                    // TODO: retire is not implemented yet for program
                }
            }
        });
        /*
         * Initial value selected in action drop-down
         */
        eventBus.addHandler(ProgramViewEvent.TYPE, new ProgramViewEvent.Handler() {
            @Override
            public void onEvent(ProgramViewEvent event) {
                actionBox.setSelectedIndex(0);
            }
        });
        
        /* 
         * Code executed right after model is loaded from service
         */
        eventBus.addHandler(ModelLoadedEvent.TYPE, new ModelLoadedEvent.Handler() {
            @Override
            public void onEvent(ModelLoadedEvent event) {
                
                /*
                 * Reload values in the drop-down if the data model changes
                 * since we may have loaded a different version
                 */
                resetActionList();
                 
                String type = context.getAttributes().get(ProgramConstants.TYPE);
                if (type != null) {
                    context.getAttributes().remove(ProgramConstants.TYPE);
                    if (type.equals(ProgramConstants.VARIATION_TYPE_KEY)) {
                        showVariationView();
                    } else {
                    	//Take out the vairationId if it exists for cleaner navigation
                    	context.getAttributes().remove(ProgramConstants.VARIATION_ID);
                        showView(ProgramSections.VIEW_ALL);
                    }
                } else {
                	context.getAttributes().remove(ProgramConstants.VARIATION_ID);
                    showView(ProgramSections.VIEW_ALL);
                }
            }
        });
    }

    /**
     * 
     * This method process the modify a program action that was selected. A permission
     * check is done to see if the modify program lightbox shoudl be presented to the
     * user.
     * 
     * The modify programl lightbox is only shown to the admin role.
     * 
     * @param viewContext
     */
    private void processModifyActionType(final ViewContext viewContext) {
    	SecurityContext securityContext = Application.getApplicationContext().getSecurityContext(); 
    	
    	securityContext.checkScreenPermission("useCurriculumReview", new Callback<Boolean>() {
            @Override
            public void exec(Boolean result) {
                final boolean isAuthorized = result;

                // Show the modify program light box only for admin role.
                if (isAuthorized) {
                    buildModifyDialog(viewContext, "/HOME/CURRICULUM_HOME/COURSE_PROPOSAL", programModel);
                } else {
                    if (isProgramStatusValidForProposal()) {
                        showModifyProgramWithNewVersionCurriculumReviewView();
                    } else {
                        showModifyProgramWithoutVersionView(viewContext);
                    }
                }
            }
        });
    }

    /**
     * 
     * This method builds the light box that appears when you choose to modify a program.
     * 
     * @param viewContext
     * @param modifyPath
     * @param model
     */
    private void buildModifyDialog(final ViewContext viewContext, final String modifyPath, final DataModel model){
        final KSLightBox modifyDialog = new KSLightBox();
        
        //Create a dialog for course selection
        modifyDialog.setTitle((getMessage("modifyProgramSubTitle")));

        final VerticalPanel layout = new VerticalPanel();
        layout.addStyleName("ks-form-module-fields");
                
        final KSButton continueButton = new KSButton(getMessage("continue"));
        
        modifyDialog.addButton(continueButton);
        Anchor cancelLink = new Anchor("Cancel");
       
        // Cancel should just close the dialog
        cancelLink.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                modifyDialog.hide();
            }
        });
        modifyDialog.addButton(cancelLink);
        
        //HorizontalPanel titlePanel = new HorizontalPanel();
        KSLabel titleLabel = new KSLabel(getMessage("modifyProgramSubTitle"));
        titleLabel.addStyleName("bold");
        modifyDialog.setNonCaptionHeader(titleLabel);
        //titlePanel.add(titleLabel);
        
        //layout.add(titlePanel);
        
        final KSRadioButton radioOptionModifyNoVersion = new KSRadioButton("modifyCreditProgramButtonGroup", getMessage("modifyProgramNoVersion"));
        final KSRadioButton radioOptionModifyWithVersion = new KSRadioButton("modifyCreditProgramButtonGroup", getMessage("modifyProgramWithVersion"));
        final KSCheckBox curriculumReviewOption = new KSCheckBox(getMessage("useCurriculumReview"));
        
        
        radioOptionModifyNoVersion.addValueChangeHandler(new ValueChangeHandler<Boolean>(){
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                if(event.getValue()){
                    curriculumReviewOption.setEnabled(false);
                    curriculumReviewOption.setValue(false);
                }
            }
        });
        radioOptionModifyNoVersion.setValue(true);
        

        curriculumReviewOption.setEnabled(false);
        
        radioOptionModifyWithVersion.addValueChangeHandler(new ValueChangeHandler<Boolean>(){
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                if(event.getValue()){
                    curriculumReviewOption.setEnabled(true);
                }
            }
        });
        
        /*
         * Continue button clicked.
         */
        continueButton.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                 if (radioOptionModifyNoVersion.getValue()){
                    // If modify w/out version radio button is chosen 
                    // we just edit the program.  We do not create a copy.
                    // We navigate to the edit program controller
                     showModifyProgramWithoutVersionView(viewContext);
                 } else if (radioOptionModifyWithVersion.getValue() && curriculumReviewOption.getValue()){
                    // If the curriculum review option IS checked and the modify with version radio button IS selected
                    // We need to create a copy of the program (by passing in COPY_OF_OBJECT_ID)
                    // and then transfer control to the proposal controller (the proposal controller has
                    // extra section for entering proposal related data
                    showModifyProgramWithNewVersionCurriculumReviewView();
                 } else if (radioOptionModifyWithVersion.getValue()){
                    // If we are just choosing to modify a program but want to create a new version
                    // AND we are not using the proposal process
                    // We make a copy of the data model and transfer control
                    // to the edit program screen
                     
                     // Pass the ID and the type to the proposal controller
                     // using the view context.  We then read it in the
                     // setViewContext method and use it to initialize the
                     // work flow utilities
    
                     String versionIndId = getStringProperty(ProgramConstants.VERSION_IND_ID);
                     viewContext.setId(versionIndId);
                     viewContext.setIdType(IdType.COPY_OF_OBJECT_ID);
          
                     //ProgramRegistry.setSection(ProgramSections.getEditSection(getCurrentViewEnum()));
                     HistoryManager.navigate(AppLocations.Locations.EDIT_PROGRAM.getLocation(), viewContext);
                }   
                
                // Hide dialog after clicking
                modifyDialog.hide();
            }           
        });
        
        //Check that this is the latest version with an async call and only show modify with version options if it is the latest
       
        layout.add(radioOptionModifyNoVersion);
 
        // the curriculum review check box implements "modify by proposal"
        // a user can only check the box when the program state is active, retired, or approved (it must be the latest version when in these states)
        // See https://wiki.kuali.org/display/KULSTG/Course%2C+Proposal%2C+and+Program+Action+Dropdown+Items
        if (isProgramStatusValidForProposal()) {
            layout.add(radioOptionModifyWithVersion);
            layout.add(curriculumReviewOption);
        }
        
        modifyDialog.setWidget(layout);
        modifyDialog.show();
    } 
 
    /**
     * 
     * This method checks if the program state is active, retired, or approved (it must be
     * the latest version when in these states)
     * 
     * @return
     */
    private boolean isProgramStatusValidForProposal() {
        ProgramStatus status = ProgramStatus.of(programModel);

        if (isCurrentVersion
                && (status == ProgramStatus.ACTIVE || status == ProgramStatus.APPROVED || status == ProgramStatus.ACTIVE)) {
            return true;
        }

        return false;
    }
    
    /**
     * 
     * This method will grab a message based on a key.
     * 
     * @param courseMessageKey
     * @return
     */
    public String getMessage(String programMessageKey) {
        String msg = Application.getApplicationContext().getMessage(MSG_GROUP, programMessageKey);
        if (msg == null) {
            msg = programMessageKey;
        }
        return msg;
    }   
    
    /**
     * 
     * This method will set the values in the action list drop-down.
     *
     */
    protected void resetActionList() {
    	
        // Get the current state of the program (SUPERSEDED, ACTIVE, APPROVED, DRAFT)
    	ProgramStatus status = ProgramStatus.of(programModel);
    	
    	// Get the version independent indicator
        String versionIndId = getStringProperty(ProgramConstants.VERSION_IND_ID);
        
        // The the version sequence number
        Long sequenceNumber = programModel.get(ProgramConstants.VERSION_SEQUENCE_NUMBER);
               
        // Clear the drop-down list and prepare to populate it with values
        actionBox.clear();
  
        // Call the server to see if this is the latest version of the program
        // and update the drop-down accordingly
    	programRemoteService.isLatestVersion(versionIndId, sequenceNumber, new KSAsyncCallback<Boolean>(){
			public void onSuccess(Boolean isLatest) {
			 
			    // TODO PLEASE REVIEW.  Should we be passing values from async calls to light boxes
			    // using instance variables like this? (we are doing this in course as well)
			    isCurrentVersion = isLatest;
			    
			    // Populate the action box drop-down with different values depending 
			    // on if we are working with the latest version of the program 
			    // or a historical version
                actionBox.setList(ActionType.getValuesForMajorDiscipline(isLatest));

                if (!isCurrentVersion) {
                    Application.getApplicationContext().getSecurityContext().checkScreenPermission("useCurriculumReview", new Callback<Boolean>() {
                        @Override
                        public void exec(Boolean result) {
                            final boolean isAuthorized = result;

                            if (!isAuthorized) {
                                actionBox.removeItem(ActionType.MODIFY.getValue());
                            }
                        }
                    });
                }

 			}        	
        });
    	
   	
    	// Get the reference ID of the proposal from the XML model
    	// Note: the filter puts in in the model, see ProposalWorkflowFilter.applyOutboundDataFilter
    	String referenceId = programModel.getRoot().get("id");
    	
    	// When the program being viewed is in DRAFT state, check to see if it exists as part of program proposal instead of admin modify. 
    	// If its part of a program proposal then we don't want to display the program actions drop down since user is not allowed to take
    	// any actions on a DRAFT program proposal outside of proposal process.
    	// TODO PLEASE REVIEW.  If this async call runs slow, will the box remain visible? Is this an issue?
    	//      Answer: Yes, it might be an issue, possible solution might to block user action w/progress bar until finished.    	
        if (status == ProgramStatus.DRAFT){
	    	programRemoteService.isProposal( "kuali.proposal.referenceType.clu", referenceId,  new KSAsyncCallback<Boolean>(){
	            public void onSuccess(Boolean isProposal) {
	             
	                // If this is a proposal then we cannot take any actions on it
	                // So hide the action box
	                if (isProposal){
	                    actionBox.setVisible(false);
	                }
	              
	            }           
	        });
        }
    } 
  
    private void showVariationView() {
        String variationId = context.getAttributes().get(ProgramConstants.VARIATION_ID);
        if (variationId != null) {
            final Data variationMap = getDataProperty(ProgramConstants.VARIATIONS);
            if (variationMap != null) {
                int row = 0;
                for (Property p : variationMap) {
                    final Data variationData = p.getValue();
                    if (variationData != null) {
                        if (variationData.get(ProgramConstants.ID).equals(variationId)) {
                            //FIXME: Find a better way to do this.
                            // We shouldn't be maintaining two separate datamodels for progs and variations
                            Data credData = getDataProperty(ProgramConstants.CREDENTIAL_PROGRAM);
                            variationData.set(ProgramConstants.CREDENTIAL_PROGRAM, credData);
                            ProgramRegistry.setData(variationData);
                            ProgramRegistry.setRow(row);
                        }
                        row++;
                    }
                }
                HistoryManager.navigate(AppLocations.Locations.VIEW_VARIATION.getLocation(), context);
            }
        }
    }

    @Override
    protected void configureView() {
        super.configureView();
        addContentWidget(actionBox);
        initialized = true;
    }

    /**
     * 
     * This method navigates to the edit program controller.
     * 
     * @param viewContext
     */
    private void showModifyProgramWithoutVersionView(final ViewContext viewContext) {
        ProgramRegistry.setSection(ProgramSections.getEditSection(getCurrentViewEnum()));
         HistoryManager.navigate(AppLocations.Locations.EDIT_PROGRAM.getLocation(), viewContext);
    }

    /**
     * 
     * This method transfers control to the proposal controller.
     * 
     */
    private void showModifyProgramWithNewVersionCurriculumReviewView() {
        String versionIndId = getStringProperty(ProgramConstants.VERSION_IND_ID);
 
        // Pass the ID and the type to the proposal controller
        // using the view context.  We then read it in the
        // setViewContext method and use it to initialize the
        // work flow utilities
        final ViewContext viewContext = new ViewContext();
        viewContext.setId(versionIndId);
        viewContext.setIdType(IdAttributes.IdType.COPY_OF_OBJECT_ID); 
        Application.navigate(AppLocations.Locations.PROGRAM_PROPOSAL.getLocation(), viewContext);
    }
    
    @Override
	public boolean isAuthorizationRequired() {
		return true;
	}

	@Override
	public void setAuthorizationRequired(boolean required) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void checkAuthorization(final AuthorizationCallback authCallback) {
		Application.getApplicationContext().getSecurityContext().checkScreenPermission(LUUIPermissions.USE_FIND_PROGRAM_SCREEN, new Callback<Boolean>() {
			@Override
			public void exec(Boolean result) {

				final boolean isAuthorized = result;
	        
				if(isAuthorized){
					authCallback.isAuthorized();
				}
				else
					authCallback.isNotAuthorized("User is not authorized: " + LUUIPermissions.USE_FIND_PROGRAM_SCREEN);
			}	
		});
	}
}
