package org.kuali.student.lum.program.client.major.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Data.Property;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.shared.IdAttributes;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class MajorViewController extends MajorController {

    // TODO: Change to program and copy msgs
    private static final String MSG_GROUP = "course";
    
    // The drop-down with the action items
    private final DropdownList actionBox = new DropdownList(ActionType.getValues());
 
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
                    
                    // Show the modify program light box.  This is triggered when you choose the 
                    // modify program option in the action drop-down box
                    buildModifyDialog(viewContext,"/HOME/CURRICULUM_HOME/COURSE_PROPOSAL",programModel);                   
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
     * This method builds the light box that appears when you choose to modify a program.
     * 
     * @param viewContext
     * @param modifyPath
     * @param model
     */
    private void buildModifyDialog(final ViewContext viewContext, final String modifyPath, final DataModel model){
        final KSLightBox modifyDialog = new KSLightBox();
        
        //Create a dialog for course selection
        modifyDialog.setTitle((getMessage("modifyCourse")));

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
        
        HorizontalPanel titlePanel = new HorizontalPanel();
        KSLabel titleLabel = new KSLabel(getMessage("modifyCourseSubTitle"));
        titleLabel.addStyleName("bold");
        titlePanel.add(titleLabel);
        
        layout.add(titlePanel);
        
        final KSRadioButton radioOptionModifyNoVersion = new KSRadioButton("modifyCreditCourseButtonGroup", getMessage("modifyCourseNoVersion"));
        final KSRadioButton radioOptionModifyWithVersion = new KSRadioButton("modifyCreditCourseButtonGroup", getMessage("modifyCourseWithVersion"));
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
                  // Navigate to program admin screens (does not exist yet)   
                    
                } else if (radioOptionModifyWithVersion.getValue() && curriculumReviewOption.getValue()){
                    // If the curriculum review option IS checked and the modify with version radio button IS selected
                    // Navigate to the modify by proposal controller/screen
                    String versionIndId = getStringProperty(ProgramConstants.VERSION_IND_ID);
 
                    // Pass the ID and the type to the proposal controller
                    // using the view context.  We then read it in the
                    // setViewContext method and use it to initialize the
                    // work flow utilities
                    final ViewContext viewContext = new ViewContext();
                    viewContext.setId(versionIndId);
                    viewContext.setIdType(IdAttributes.IdType.COPY_OF_OBJECT_ID); 
                    Application.navigate(AppLocations.Locations.PROGRAM_PROPOSAL.getLocation(), viewContext);

                 } else if (radioOptionModifyWithVersion.getValue()){
                   // If the curriculum review option NOT checked and the modify with version radio button IS selected
                   // Navigate to normal modify program screen
                    
                     
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
        // a user can only check the box when the program state is active, retired, or approved (it must be the latest version when in approved state)
        // See https://wiki.kuali.org/display/KULSTG/Course%2C+Proposal%2C+and+Program+Action+Dropdown+Items
  
        if(isCurrentVersion){
            layout.add(radioOptionModifyWithVersion);
            layout.add(curriculumReviewOption);
        }
        
        modifyDialog.setWidget(layout);
        modifyDialog.show();
    } 
 
    
    /**
     * 
     * This method will grab a message based on a key.
     * 
     * @param courseMessageKey
     * @return
     */
    public String getMessage(String courseMessageKey) {
        String msg = Application.getApplicationContext().getMessage(MSG_GROUP, courseMessageKey);
        if (msg == null) {
            msg = courseMessageKey;
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
			    
			   if (isLatest){
    			    List<String> values = new ArrayList<String>();
    	            values.add(ActionType.NO_ACTION.getValue());
    	            values.add(ActionType.MODIFY.getValue());
    	            values.add(ActionType.RETIRE.getValue());
    		        actionBox.setList(values);	
			   }
			   else {
			       // If this is NOT the latest version it can
		            // TODO where is this in the spec?
		            List<String> values = new ArrayList<String>();
		            values.add(ActionType.NO_ACTION.getValue());
		            values.add(ActionType.MODIFY.getValue());
		            values.add(ActionType.RETIRE.getValue());
		            actionBox.setList(values);   
			   }
			   
			}        	
        });
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
}
