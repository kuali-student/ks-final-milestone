package org.kuali.student.lum.program.client.major.proposal;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
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
        rootSection = new VerticalSectionView(ProgramSections.SUMMARY, ProgramProperties.get().proposal_menu_sections_summary(), ProgramConstants.PROGRAM_MODEL_ID, true);
    }

    /**
     * This is an inherited method called by the framework to layout components on the screen.
     */
    @Override
    protected void buildLayout() {
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
            
            // Get a reference to the widget so we can add it to the root section of the screen
            Widget widget = workflowUtilities.getWorkflowActionsWidget();
            
            // Enable widget so it is visible
            // TODO: Is this needed here or can we do this when displaying widget
            workflowUtilities.enableWorkflowActionsWidgets(true);
            
            // Add the widget to the root section of the screen
            rootSection.addWidget(widget); 
        }
 
        
        // Loop over all configurations in the configuration manager
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(controller);
            }
            rootSection.addSection((Section) configuration.getView());
        }    
     }
}
