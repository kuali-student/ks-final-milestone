package org.kuali.student.lum.program.client.major.proposal;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.WarnContainer;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowEnhancedNavController;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.view.CatalogInformationViewConfiguration;
import org.kuali.student.lum.program.client.major.view.LearningObjectivesViewConfiguration;
import org.kuali.student.lum.program.client.major.view.MajorKeyProgramInfoViewConfiguration;
import org.kuali.student.lum.program.client.major.view.ManagingBodiesViewConfiguration;
import org.kuali.student.lum.program.client.major.view.ProgramRequirementsViewConfiguration;
import org.kuali.student.lum.program.client.major.view.ProposalChangeImpactViewConfiguration;
import org.kuali.student.lum.program.client.major.view.ProposalInformationViewConfiguration;
import org.kuali.student.lum.program.client.major.view.SpecializationsViewConfiguration;
import org.kuali.student.lum.program.client.major.view.SupportingDocsViewConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * This configuration is used to define what widgets should appear on the screen
 * for the major proposal controller.  
 * <p>
 * Note that this is pretty much a copy of the major discipline controller, 
 * but this controller has a couple extra tabs .
 * 
 * @author cmann
 */
public class MajorProposalSummaryConfiguration extends AbstractControllerConfiguration {

    public MajorProposalSummaryConfiguration() {
    	super();
    }

    /**
     * This is an inherited method called by the framework to layout components on the screen.
     */
    @Override
    protected void buildLayout() {
    	
        if (controller instanceof WorkflowEnhancedNavController
                && ((WorkflowEnhancedNavController) controller).getWfUtilities() != null) {
	        final WarnContainer infoContainerHeader; // Header widget (with warnings if necessary)
	        //WarnContainers initialized with buttons common to all states (error or otherwise)
	        infoContainerHeader = generateWorkflowWidgetContainer(((WorkflowEnhancedNavController) controller)
	                    .getWfUtilities().getWorkflowActionsWidget());
	        rootSection = new VerticalSectionView(ProgramSections.SUMMARY, ProgramProperties.get().proposal_menu_sections_summary(), ProgramConstants.PROGRAM_MODEL_ID, true){
	
				@Override
				public void beforeShow(final Callback<Boolean> onReadyCallback) {
					super.beforeShow(new Callback<Boolean>() {
	
						@Override
						public void exec(final Boolean result) {
	                        if (result) {
	                            // Make sure workflow actions and status updated before showing.
	                            ((WorkflowEnhancedNavController) controller).getWfUtilities().refresh();
	                            ((WorkflowEnhancedNavController) controller).getWfUtilities().requestAndSetupModel(new Callback<Boolean>(){
									public void exec(Boolean modelReadyResult) {
			                            // Show validation error if they exist
			                            ((WorkflowEnhancedNavController) controller).getWfUtilities().doValidationCheck(new Callback<List<ValidationResultInfo>>() {
	                                        @Override
	                                        public void exec(List<ValidationResultInfo> validationResult) { //Don't place a breakpoint here:  It will stall debugging for some unknown reason!
	                                            ErrorLevel isValid = rootSection.processValidationResults(
	                                                    validationResult, true);
	
	                                            if (isValid == ErrorLevel.OK) {
	                                                infoContainerHeader.showWarningLayout(false);
	                                                ((WorkflowEnhancedNavController) controller)
	                                                                .getWfUtilities()
	                                                                .enableWorkflowActionsWidgets(true);
	                                            } else { //KSLAB-1985
                                                    ((WorkflowEnhancedNavController) controller).getWfUtilities()
                                                    .enableWorkflowActionsWidgets(false);
	                                            }
	                                        	onReadyCallback.exec(result);
	                                        }
	                                    });
									}
								});
	                        }else{
	                        	onReadyCallback.exec(result);
	                        }
						}
					});
				}
	        	
	        };
	        
	        rootSection.addWidget(infoContainerHeader); // Header widget (with warnings if necessary)
        }
    	
    	
    	
    	
        ConfigurationManager configurationManager = new ConfigurationManager(configurer);
    	
        // Initialize tabs on left of screen
        configurationManager.registerConfiguration(ProposalInformationViewConfiguration.createSpecial(controller));
        configurationManager.registerConfiguration(ProposalChangeImpactViewConfiguration.createSpecial(controller));
        configurationManager.registerConfiguration(MajorKeyProgramInfoViewConfiguration.createSpecial(controller));
        configurationManager.registerConfiguration(ManagingBodiesViewConfiguration.createSpecial(controller));
        configurationManager.registerConfiguration(SpecializationsViewConfiguration.createSpecial(controller));
        configurationManager.registerConfiguration(CatalogInformationViewConfiguration.createSpecial(controller));
        configurationManager.registerConfiguration(new ProgramRequirementsViewConfiguration(controller));
        configurationManager.registerConfiguration(LearningObjectivesViewConfiguration.createSpecial(controller));
        configurationManager.registerConfiguration(SupportingDocsViewConfiguration.createSpecial(controller));

        // Add the work flow utilities to the screen light box to the screen
        // Instance of check ensures we only do this for majors
        if (controller instanceof MajorProposalController){
            
            // Grab the work flow utilities widget we initialized in the controller
            final WorkflowUtilities workflowUtilities = ((MajorProposalController) controller).getWfUtilities();
            workflowUtilities.addSubmitCallback(new Callback<Boolean>() {

                @Override
                public void exec(Boolean result) {
                    if (result) {
                        ((MajorProposalController) controller).setStatus();
                    }

                }
            });
            //Add fields to workflow utils screens
            if(workflowUtilities!=null){
            	controller.requestModel(new ModelRequestCallback<DataModel>(){
					public void onModelReady(DataModel model) {
						//Only display if this is a modification
						String versionedFromId = model.get("versionInfo/versionedFromId");
						if(versionedFromId!=null && !versionedFromId.isEmpty()){
							//Add the previous start term since we need it as a widget so it can act as a cross field constraint
							workflowUtilities.addApproveDialogField("", "startTerm",  new MessageKeyInfo(ProgramProperties.get().programInformation_startTerm()), MajorProposalSummaryConfiguration.this.configurer.getModelDefinition(), true, true);
						    workflowUtilities.addApproveDialogField("proposal", "prevEndTerm", new MessageKeyInfo(ProgramProperties.get().majorDiscipline_prevEndTerm()), MajorProposalSummaryConfiguration.this.configurer.getModelDefinition(),false);
							workflowUtilities.addApproveDialogField("proposal", "prevEndProgramEntryTerm", new MessageKeyInfo(ProgramProperties.get().majorDiscipline_prevEndProgramEntryTerm()), MajorProposalSummaryConfiguration.this.configurer.getModelDefinition(),false);
							workflowUtilities.addApproveDialogField("proposal", "prevEndInstAdmitTerm", new MessageKeyInfo(ProgramProperties.get().majorDiscipline_prevEndInstAdmitTerm()), MajorProposalSummaryConfiguration.this.configurer.getModelDefinition(),false);
							workflowUtilities.updateApproveFields();
							workflowUtilities.progressiveEnableFields();	
						}else{
							//Ignore this field (so blanket approve works if this is a new course proposal and not modifiaction)
							workflowUtilities.addIgnoreDialogField("proposal/prevEndTerm");
							workflowUtilities.addIgnoreDialogField("proposal/prevEndProgramEntryTerm");
							workflowUtilities.addIgnoreDialogField("proposal/prevEndInstAdmitTerm");
						}
					}
					public void onRequestFail(Throwable cause) {
					}
            	});
            }
            
        }
 
        
        // Loop over all configurations in the configuration manager
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(controller);
            }
            rootSection.addSection((Section) configuration.getView());
        }    
     }
    
    
    // Initializes a WarnContainer with Action options dropdown, and Curriculum Management link 
    private WarnContainer generateWorkflowWidgetContainer(Widget w) {

        WarnContainer warnContainer = new WarnContainer();

        warnContainer.add(w);
        w.addStyleName("ks-button-spacing");
        warnContainer.add(new KSButton("Return to Curriculum Management",
                ButtonStyle.DEFAULT_ANCHOR, new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) { //Don't place a breakpoint here:  It will stall debugging for some unknown reason!
                        Application
                                .navigate(AppLocations.Locations.CURRICULUM_MANAGEMENT
                                        .getLocation());
                    }
                }));

        // KSLAB-1985:  Warning logic/display moved to generateProposalSummarySection() where error states are established

        return warnContainer;
    }
}
