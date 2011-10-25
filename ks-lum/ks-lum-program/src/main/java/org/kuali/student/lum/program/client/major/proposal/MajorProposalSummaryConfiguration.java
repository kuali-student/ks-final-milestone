package org.kuali.student.lum.program.client.major.proposal;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.WarnContainer;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowEnhancedNavController;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
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

	boolean showEditLinks = false;
	
	public MajorProposalSummaryConfiguration(Configurer configurer, boolean showEditLinks) {
    	super();
    	this.setConfigurer(configurer);
        this.showEditLinks = showEditLinks;
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
	        rootSection = new VerticalSectionView(ProgramSections.SUMMARY, getLabel(ProgramMsgConstants.PROPOSAL_MENU_SECTIONS_SUMMARY), ProgramConstants.PROGRAM_MODEL_ID, true){
	
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
    	
    	
    	
    	
        ConfigurationManager configurationManager = new ConfigurationManager();
    	
        // Initialize tabs on left of screen
        if (showEditLinks){
        	configureSectionsWithEditLinks(configurationManager);
        } else {
        	configureSectionsWithoutEditLinks(configurationManager);
        }
        
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
							workflowUtilities.addApproveDialogField("", "startTerm",  generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_STARTTERM), MajorProposalSummaryConfiguration.this.configurer.getModelDefinition(), true, true);
						    workflowUtilities.addApproveDialogField("proposal", "prevEndTerm", generateMessageInfo(ProgramMsgConstants.MAJORDISCIPLINE_PREVENDTERM), MajorProposalSummaryConfiguration.this.configurer.getModelDefinition(),false);
							workflowUtilities.addApproveDialogField("proposal", "prevEndProgramEntryTerm", generateMessageInfo(ProgramMsgConstants.MAJORDISCIPLINE_PREVENDPROGRAMENTRYTERM), MajorProposalSummaryConfiguration.this.configurer.getModelDefinition(),false);
							workflowUtilities.addApproveDialogField("proposal", "prevEndInstAdmitTerm", generateMessageInfo(ProgramMsgConstants.MAJORDISCIPLINE_PREVENDINSTADMITTERM), MajorProposalSummaryConfiguration.this.configurer.getModelDefinition(),false);
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
    
    protected void configureSectionsWithEditLinks(ConfigurationManager configurationManager){
	    configurationManager.registerConfiguration(ProposalInformationViewConfiguration.createSpecial(configurer, controller));
	    configurationManager.registerConfiguration(ProposalChangeImpactViewConfiguration.createSpecial(configurer, controller));
	    configurationManager.registerConfiguration(MajorKeyProgramInfoViewConfiguration.createSpecial(configurer, controller));
	    configurationManager.registerConfiguration(ManagingBodiesViewConfiguration.createSpecial(configurer, controller));
	    configurationManager.registerConfiguration(SpecializationsViewConfiguration.createSpecial(configurer, controller));
	    configurationManager.registerConfiguration(CatalogInformationViewConfiguration.createSpecial(configurer, controller));
	    configurationManager.registerConfiguration(new ProgramRequirementsViewConfiguration(configurer, controller, true));
	    configurationManager.registerConfiguration(LearningObjectivesViewConfiguration.createSpecial(configurer, controller));
	    configurationManager.registerConfiguration(SupportingDocsViewConfiguration.createSpecial(configurer, controller));
    }
    
    protected void configureSectionsWithoutEditLinks(ConfigurationManager configurationManager){
	    configurationManager.registerConfiguration(ProposalInformationViewConfiguration.create(configurer));
	    configurationManager.registerConfiguration(ProposalChangeImpactViewConfiguration.create(configurer));
	    configurationManager.registerConfiguration(MajorKeyProgramInfoViewConfiguration.create(configurer));
	    configurationManager.registerConfiguration(ManagingBodiesViewConfiguration.create(configurer));
	    configurationManager.registerConfiguration(SpecializationsViewConfiguration.create(configurer));
	    configurationManager.registerConfiguration(CatalogInformationViewConfiguration.create(configurer));
	    configurationManager.registerConfiguration(new ProgramRequirementsViewConfiguration(configurer, controller, false));
	    configurationManager.registerConfiguration(LearningObjectivesViewConfiguration.create(configurer));
	    configurationManager.registerConfiguration(SupportingDocsViewConfiguration.create(configurer));    
    }    
    
    // Initializes a WarnContainer with Action options dropdown, and Curriculum Management link 
    private WarnContainer generateWorkflowWidgetContainer(Widget w) {

        WarnContainer warnContainer = new WarnContainer();

        warnContainer.add(w);
        w.addStyleName("ks-button-spacing");
//        warnContainer.add(new KSButton("Return to Curriculum Management",
//                ButtonStyle.DEFAULT_ANCHOR, new ClickHandler() {
//
//                    @Override
//                    public void onClick(ClickEvent event) { //Don't place a breakpoint here:  It will stall debugging for some unknown reason!
//                        Application
//                                .navigate(AppLocations.Locations.CURRICULUM_MANAGEMENT
//                                        .getLocation());
//                    }
//                }));

        // KSLAB-1985:  Warning logic/display moved to generateProposalSummarySection() where error states are established

        return warnContainer;
    }
}
