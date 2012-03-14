package org.kuali.student.lum.lu.ui.course.client.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Data.Property;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ListToTextBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.MenuSectionController;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityFieldConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.sections.WarnContainer;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.table.summary.ShowRowConditionCallback;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentList;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentListBinding;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.statement.ui.client.widgets.rules.SubrulePreviewWidget;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowEnhancedNavController;
import org.kuali.student.lum.common.client.lo.TreeStringBinding;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.base.AcademicSubjectOrgInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.base.MetaInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.AffiliatedOrgInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseExpenditureInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseFormatConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseJointsConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseProposalConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseProposalInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseRevenueInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.LearningObjectiveConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer.CourseSections;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer.KeyListModelWigetBinding;
import org.kuali.student.lum.lu.ui.course.client.configuration.ViewCourseConfigurer.ViewCourseSections;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;
import org.kuali.student.lum.lu.ui.course.client.controllers.VersionsController;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsSummaryView;
import org.kuali.student.lum.lu.ui.course.client.requirements.HasRequirements;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class CourseRetireSummaryConfigurer extends CourseSummaryConfigurer {

    private boolean showingValidation = false;
    private List<ValidationResultInfo> validationInfos = new ArrayList<ValidationResultInfo>();
    private List<Anchor> validateLinks = new ArrayList<Anchor>(); //KSLAB-1985

	public CourseRetireSummaryConfigurer(){
        
    }

    public CourseRetireSummaryConfigurer(String type, String state, String groupName,
            DataModelDefinition modelDefinition,
            List<StatementTypeInfo> stmtTypes, Controller controller,
            String modelId) {
        this.type = type;
        this.state = state;
        this.groupName = groupName;
        this.modelDefinition = modelDefinition;
        this.stmtTypes = stmtTypes;
        this.controller = controller;
        this.modelId = modelId;
        tableSection = new SummaryTableSection((Controller) controller);
    }
    
    public void init(String type, String state, String groupName,
            DataModelDefinition modelDefinition,
            List<StatementTypeInfo> stmtTypes, Controller controller,
            String modelId) {
        this.type = type;
        this.state = state;
        this.groupName = groupName;
        this.modelDefinition = modelDefinition;
        this.stmtTypes = stmtTypes;
        this.controller = controller;
        this.modelId = modelId;
        tableSection = new SummaryTableSection((Controller) controller);
    }

    public VerticalSectionView generateProposalSummarySection(boolean canEditSections) {
        tableSection.setEditable(canEditSections);
        tableSection.addSummaryTableFieldBlock(generateRetirementInfoSection());
//        tableSection.addSummaryTableFieldBlock(generateCollaboratorSection());        
        tableSection.addSummaryTableFieldBlock(generateProposalDocumentsSection());

        if (   controller instanceof WorkflowEnhancedNavController
            && ((WorkflowEnhancedNavController) controller).getWfUtilities() != null) {
            final WarnContainer infoContainer1; // Header widget (with warnings if necessary)
            final WarnContainer infoContainer2; // Footer widget (with warnings if necessary)
            //WarnContainers initialized with buttons common to all states (error or otherwise)
            Widget topWorkflowActionWidget= ((WorkflowEnhancedNavController) controller).getWfUtilities().getWorkflowActionsWidget();
            Widget bottomWorkflowActionWidget = ((WorkflowEnhancedNavController) controller).getWfUtilities().getWorkflowActionsWidget();
            
            topWorkflowActionWidget.ensureDebugId("Top-Workflow-Actions");            
            bottomWorkflowActionWidget.ensureDebugId("Bottom-Workflow-Actions");
            
            infoContainer1= generateWorkflowWidgetContainer(topWorkflowActionWidget);
            infoContainer2= generateWorkflowWidgetContainer(bottomWorkflowActionWidget);

            ((WorkflowEnhancedNavController) controller).getWfUtilities()
                    .addSubmitCallback(new Callback<Boolean>() {

                        @Override
                        public void exec(Boolean result) { //Don't place a breakpoint here:  It will stall debugging for some unknown reason!
                            if (result) {
                                tableSection.setEditable(false);
                                if (controller instanceof MenuSectionController) {
                                    ((MenuSectionController) controller).removeMenuNavigation();
                                }
                            }

                        }
                    });

            // Override beforeShow for summary section here to allow for custom validation mechanism on the table
            VerticalSectionView verticalSection = new VerticalSectionView(CourseSections.SUMMARY, getLabel(LUUIConstants.SUMMARY_LABEL_KEY), modelId) {
                                
                @Override
                public void beforeShow(final Callback<Boolean> onReadyCallback) { //Don't place a breakpoint here:  It will stall debugging for some unknown reason!

                    super.beforeShow(new Callback<Boolean>() {

                        @Override
                        public void exec(final Boolean result) { //Don't place a breakpoint here:  It will stall debugging for some unknown reason!

                            if (result) {
                                // Make sure workflow actions and status updated before showing.
                                ((WorkflowEnhancedNavController) controller).getWfUtilities().refresh();

                                // Show validation errors if they exist
                                ((WorkflowEnhancedNavController) controller).getWfUtilities().doValidationCheck(new Callback<List<ValidationResultInfo>>() {
                                            
                                    @Override
                                    public void exec(List<ValidationResultInfo> validationResults) { //Don't place a breakpoint here:  It will stall debugging for some unknown reason!

                                        tableSection.enableValidation(showingValidation);   //  I think passing true here turns on all validation highlighting automatically (i.e: without requiring "click to show") [KSCM-250]
                                        
                                        initializeHeaders(validationResults);
                                        resolveMissingFieldsWarnings();
                                        // proposal submission warnings resolution moved to overridden processValidationResults below.
                                        
                                        onReadyCallback.exec(result);   // calls CourseProposalController.showView.finalView [KSCM-250]
                                    }
                                });
                            } else {
                                onReadyCallback.exec(result);
                            }
                        }
                    });
                }
                
                /*
                 * Appropriately initializes yellow warning (WarnContainers called infoContainers here) boxes at page top/bottom.
                 */                
                void initializeHeaders(List<ValidationResultInfo> validationResults){                  
                    ErrorLevel isValid = tableSection.processValidationResults(validationResults, true);                                            
                    validationInfos = validationResults;
                    
                    if (isValid == ErrorLevel.OK) {
                        
                        infoContainer1.showWarningLayout(false);
                        infoContainer2.showWarningLayout(false);
                        
                        ((WorkflowEnhancedNavController) controller).getWfUtilities().enableWorkflowActionsWidgets(true);
                    } else { //KSLAB-1985

                        infoContainer1.clearWarnLayout();
                        infoContainer2.clearWarnLayout();

                        infoContainer1.showWarningLayout(true);
                        infoContainer2.showWarningLayout(true);
                        ((WorkflowEnhancedNavController) controller).getWfUtilities().enableWorkflowActionsWidgets(false);
                    }
                }
                
                /* 
                 * Shows missing fields warnings if and their links, if appropriate. 
                 */
                public void resolveMissingFieldsWarnings(){

                    if (tableSection.getIsMissingFields()) {
                        final Anchor link1 = new Anchor("Show what's missing.");
                        final Anchor link2 = new Anchor("Show what's missing.");                        
                        ClickHandler showHideMsgClickHandler = new ClickHandler() {                            
                            
                            @Override   // Sets link action
                            public void onClick(ClickEvent event) { //Don't place a breakpoint here:  It will stall debugging for some unknown reason!
                                
                                if (!showingValidation) {

                                    for (int i = 0; i < validateLinks.size(); i++) {
                                        
                                        validateLinks.get(i).setText("Hide error highlighting.");
                                    }
                                    
                                    showingValidation = true;
                                    
                                    tableSection.enableValidation(showingValidation);
                                    tableSection.processValidationResults(validationInfos, true);
                                } else {

                                    for (int i = 0; i < validateLinks.size(); i++) {
                                        
                                        validateLinks.get(i).setText("Show what's missing.");
                                    }
                                    
                                    showingValidation = false;
                                    
                                    tableSection.enableValidation(showingValidation);
                                    tableSection.removeValidationHighlighting();
                                }
                            }
                        };
                        
                        validateLinks.add(link1);   // Enable links
                        validateLinks.add(link2);

                        link1.addClickHandler(showHideMsgClickHandler);
                        link2.addClickHandler(showHideMsgClickHandler);

                        infoContainer1.addWarnWidget(new KSLabel("This proposal has missing fields.  "));
                        infoContainer1.addWarnWidget(link1);
                        infoContainer2.addWarnWidget(new KSLabel("This proposal has missing fields.  "));
                        infoContainer2.addWarnWidget(link2);
                    }
                }

                @Override   //overridden from BaseSection to handle conflict warnings   [KSCM-250]
                public ErrorLevel processValidationResults(List<ValidationResultInfo> validationResults) {                    

                    tableSection.processValidationResults(validationResults, false);
                    resolveProposalSubmissionWarnings();
                    
                    return super.processValidationResults(validationResults);
                }
                
                /* 
                 * Shows proposal submission warnings if appropriate.
                 *  i.e: If conflict warnings exist //[KSCM-250]
                 */
                public void resolveProposalSubmissionWarnings(){
                    
                    if (tableSection.getHasWarnings()) {

                        infoContainer1.addWarnWidgetBlock(new KSLabel("This proposal contains warnings that prevent it from being submitted."));
                        infoContainer2.addWarnWidgetBlock(new KSLabel("This proposal contains warnings that prevent it from being submitted."));
                    }
                }
               
            };
            // Widget-adding order matters
            verticalSection.addWidget(infoContainer1); // Header widget (with warnings if necessary)
            verticalSection.addSection(tableSection);
            verticalSection.addWidget(infoContainer2); // Footer widget (with warnings if necessary)

            return verticalSection;

        } else {

            VerticalSectionView verticalSection = new VerticalSectionView(
                    CourseSections.SUMMARY,
                    getLabel(LUUIConstants.SUMMARY_LABEL_KEY), modelId);
            verticalSection.addSection(tableSection);
            GWT.log("CourseSummaryConfigurer - Summary table needs a workflow controller to provide submit/validation mechanism");

            return verticalSection;
        }

    }

    private SummaryTableFieldBlock generateProposalDocumentsSection() {
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        block.addEditingHandler(new EditHandler(CourseSections.DOCUMENTS));
        block.setTitle(getLabel(LUUIConstants.TOOL_DOCUMENTS_LABEL_KEY));
        block.addSummaryTableFieldRow(getFieldRow("proposal/id",
                generateMessageInfo(LUUIConstants.TOOL_DOCUMENTS_LABEL_KEY),
                new DocumentList(LUUIConstants.REF_DOC_RELATION_PROPOSAL_TYPE,
                        false, false), new DocumentList(
                        LUUIConstants.REF_DOC_RELATION_PROPOSAL_TYPE, false,
                        false), null, new DocumentListBinding("proposal/id"),
                false));
        return block;
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

    @SuppressWarnings("unchecked")
    public SummaryTableFieldBlock generateRetirementInfoSection() {
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        block.addEditingHandler(new EditHandler(CourseSections.COURSE_INFO));
        block.addSummaryTableFieldRow(getFieldRow(PROPOSAL_TITLE_PATH,
                generateMessageInfo(LUUIConstants.PROPOSAL_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + RETIREMENT_RATIONALE,
                generateMessageInfo(LUUIConstants.RETIREMENT_RATIONALE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + START_TERM, 
        		generateMessageInfo(LUUIConstants.START_TERM_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + END_TERM,
                generateMessageInfo(LUUIConstants.END_TERM_LABEL_KEY)));
//        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + OTHER_COMMENTS,
//                generateMessageInfo(LUUIConstants.OTHER_COMMENTS_LABEL_KEY)));

        return block;
    }

}