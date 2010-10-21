package org.kuali.student.lum.lu.ui.course.client.configuration;

import java.util.*;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ListToTextBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.MenuSectionController;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityFieldConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.sections.WarnContainer;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentList;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentListBinding;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.rules.SubrulePreviewWidget;
import org.kuali.student.common.ui.client.widgets.table.summary.ShowRowConditionCallback;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowEnhancedNavController;
import org.kuali.student.lum.common.client.lo.LUConstants;
import org.kuali.student.lum.common.client.lo.TreeStringBinding;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.AcademicSubjectOrgInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.*;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseConfigurer.CourseSections;
import org.kuali.student.lum.lu.ui.course.client.configuration.ViewCourseConfigurer.ViewCourseSections;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsDataModel;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsSummaryView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class CourseSummaryConfigurer implements
	CreditCourseProposalConstants,
	CreditCourseProposalInfoConstants,
	CreditCourseConstants,
	CreditCourseFormatConstants,
	CreditCourseActivityConstants,
	MetaInfoConstants,
	CreditCourseDurationConstants,
	FeeInfoConstants,
	LearningObjectiveConstants,
	AcademicSubjectOrgInfoConstants,
	AffiliatedOrgInfoConstants,
	CreditCourseRevenueInfoConstants,
	CreditCourseExpenditureInfoConstants
{
    //Override paths for course and proposal so they are root
    public static final String PROPOSAL = "";
    public static final String COURSE = "";
    public static final String PROPOSAL_TITLE_PATH = "proposal/name";
    private List<ValidationResultInfo> validationInfos = new ArrayList<ValidationResultInfo>();
    private boolean showingValidation = false;
    private static final String OPTIONAL = "o";

    protected String type = "course";
    protected String state = "draft";
    protected String groupName;
    protected DataModelDefinition modelDefinition;

    private Controller controller;
    private SummaryTableSection tableSection;
    private String modelId;

	private BlockingTask loadDataTask = new BlockingTask("Retrieving Data");
    
    private class EditHandler implements ClickHandler{

    	Enum<?> view;

    	public EditHandler(Enum<?> view){
    		this.view = view;
    	}

		@Override
		public void onClick(ClickEvent event) {
			controller.showView(view);
		}
    }

    public CourseSummaryConfigurer(String type, String state,
            String groupName, DataModelDefinition modelDefinition, Controller controller, String modelId) {
        this.type = type;
        this.state = state;
        this.groupName = groupName;
        this.modelDefinition = modelDefinition;
        this.controller = controller;
        this.modelId = modelId;
        tableSection = new SummaryTableSection((Controller)controller);
    }

    protected VerticalSectionView initSectionView (Enum<?> viewEnum, String labelKey) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), modelId);
        section.addStyleName(LUConstants.STYLE_SECTION);
        return section;
    }
    protected String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(groupName, type, state, labelKey);
    }
    protected MessageKeyInfo generateMessageInfo(String labelKey) {
        return new MessageKeyInfo(groupName, type, state, labelKey);
    }
    protected SummaryTableFieldRow getFieldRow(String fieldKey, MessageKeyInfo messageKey) {
        return getFieldRow(fieldKey, messageKey, null, null, null, null, false);
    }
    protected SummaryTableFieldRow getFieldRow(String fieldKey, MessageKeyInfo messageKey, boolean optional) {
        return getFieldRow(fieldKey, messageKey, null, null, null, null, optional);
    }

    protected SummaryTableFieldRow getFieldRow(String fieldKey, MessageKeyInfo messageKey, Widget widget, Widget widget2, String parentPath, ModelWidgetBinding<?> binding, boolean optional) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        FieldDescriptorReadOnly fd = new FieldDescriptorReadOnly(path.toString(), messageKey, meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        if(binding != null){
        	fd.setWidgetBinding(binding);
        }
        fd.setOptional(optional);

        FieldDescriptorReadOnly fd2 = new FieldDescriptorReadOnly(path.toString(), messageKey, meta);
        if (widget2 != null) {
            fd2.setFieldWidget(widget2);
        }
        if(binding != null){
        	fd2.setWidgetBinding(binding);
        }
        fd2.setOptional(optional);

        SummaryTableFieldRow fieldRow = new SummaryTableFieldRow(fd,fd2);

        return fieldRow;
    }

	public VerticalSectionView generateProposalSummarySection(boolean canEditSections){
        tableSection.setEditable(canEditSections);
        tableSection.addSummaryTableFieldBlock(generateCourseInformationForProposal());
        tableSection.addSummaryTableFieldBlock(generateGovernanceSection());
        tableSection.addSummaryTableFieldBlock(generateCourseLogisticsSection());
        tableSection.addSummaryTableFieldBlock(generateLearningObjectivesSection());
        tableSection.addSummaryTableFieldBlock(generateRequirementsSection());
        tableSection.addSummaryTableFieldBlock(generateActiveDatesSection());
        tableSection.addSummaryTableFieldBlock(generateFeesSection());
        tableSection.addSummaryTableFieldBlock(generateProposalDocumentsSection());
        
        if(controller instanceof WorkflowEnhancedNavController){
	        final WarnContainer infoContainer1;
	        final WarnContainer infoContainer2;

	        infoContainer1 = generateWorkflowWidgetContainer(((WorkflowEnhancedNavController)controller).getWfUtilities().getWorkflowActionsWidget());
	        infoContainer2 = generateWorkflowWidgetContainer(((WorkflowEnhancedNavController)controller).getWfUtilities().getWorkflowActionsWidget());

	        ((WorkflowEnhancedNavController)controller).getWfUtilities().addSubmitCallback(new Callback<Boolean>(){

				@Override
				public void exec(Boolean result) {
					if(result){
						tableSection.setEditable(false);
						if(controller instanceof MenuSectionController){
							((MenuSectionController) controller).removeMenuNavigation();
						}
					}

				}
			});
	        //Override beforeShow for summary section here to allow for custom validation mechanism on the table
	        VerticalSectionView verticalSection = new VerticalSectionView(CourseSections.SUMMARY, getLabel(LUConstants.SUMMARY_LABEL_KEY), modelId){
	        	@Override
	        	public void beforeShow(final Callback<Boolean> onReadyCallback) {

	        		super.beforeShow(new Callback<Boolean>(){

						@Override
						public void exec(final Boolean result) {
							if(result){
								((WorkflowEnhancedNavController)controller).getWfUtilities().doValidationCheck(new Callback<List<ValidationResultInfo>>(){

									@Override
									public void exec(
											List<ValidationResultInfo> validationResult) {
										//validationInfos = validationResult;
										tableSection.enableValidation(showingValidation);
										ErrorLevel isValid = tableSection.processValidationResults(validationResult, true);

										validationInfos = validationResult;
			                        	if(isValid != ErrorLevel.ERROR){
			                				infoContainer1.showWarningLayout(false);
			                				infoContainer2.showWarningLayout(false);
			                				((WorkflowEnhancedNavController)controller).getWfUtilities().enableWorkflowActionsWidgets(true);
			                        	}
			                        	else{
			                        		infoContainer1.showWarningLayout(true);
			                        		infoContainer2.showWarningLayout(true);
			                        		((WorkflowEnhancedNavController)controller).getWfUtilities().enableWorkflowActionsWidgets(false);
			                        	}
										onReadyCallback.exec(result);

									}});
							}
							else{
								onReadyCallback.exec(result);
							}
						}});

	        	}
	        };

	        verticalSection.addWidget(infoContainer1);
	        verticalSection.addSection(tableSection);
	        verticalSection.addWidget(infoContainer2);
	        return verticalSection;
        }
        else{
        	VerticalSectionView verticalSection = new VerticalSectionView(CourseSections.SUMMARY, getLabel(LUConstants.SUMMARY_LABEL_KEY), modelId);
        	verticalSection.addSection(tableSection);
        	GWT.log("CourseSummaryConfigurer - Summary table needs a workflow controller to provide submit/validation mechanism");
        	return verticalSection;
        }


    }

    private SummaryTableFieldBlock generateProposalDocumentsSection() {
    	SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        block.addEditingHandler(new EditHandler(CourseSections.DOCUMENTS));
        block.setTitle(getLabel(LUConstants.TOOL_DOCUMENTS_LABEL_KEY));
    	block.addSummaryTableFieldRow(getFieldRow("proposal/id", generateMessageInfo(LUConstants.TOOL_DOCUMENTS_LABEL_KEY),
         		new DocumentList(LUConstants.REF_DOC_RELATION_PROPOSAL_TYPE,false, false), new DocumentList(LUConstants.REF_DOC_RELATION_PROPOSAL_TYPE,false, false), null, new DocumentListBinding("proposal/id"), false));
		return block;
	}

	private List<Anchor> validateLinks = new ArrayList<Anchor>();
    private WarnContainer generateWorkflowWidgetContainer(Widget w){
    	WarnContainer warnContainer = new WarnContainer();
        warnContainer.add(w);
        w.addStyleName("ks-button-spacing");
        warnContainer.add(new KSButton("Return to Curriculum Management", ButtonStyle.ANCHOR_LARGE_CENTERED, new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				Application.navigate(AppLocations.Locations.CURRICULUM_MANAGEMENT.getLocation());
			}
		}));
        //TODO use messages here
        KSLabel label = new KSLabel("This proposal has missing fields.  ");
        final String showText = "Show what's missing.";
        final String hideText = "Hide error highlighting.";
        final Anchor link = new Anchor(showText);
        validateLinks.add(link);
        link.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(!showingValidation){
					for(int i=0; i< validateLinks.size(); i++){
						validateLinks.get(i).setText(hideText);
					}
					showingValidation = true;
					tableSection.enableValidation(showingValidation);
					tableSection.processValidationResults(validationInfos, true);
				}
				else{
					for(int i=0; i< validateLinks.size(); i++){
						validateLinks.get(i).setText(showText);
					}
					showingValidation = false;
					tableSection.enableValidation(showingValidation);
					tableSection.removeValidationHighlighting();
				}
			}
		});
        warnContainer.addWarnWidget(label);
        warnContainer.addWarnWidget(link);
        return warnContainer;
    }

	public VerticalSectionView generateCourseSummarySection(){
        tableSection.setEditable(false);
        tableSection.addSummaryTableFieldBlock(generateCourseInformation());
        tableSection.addSummaryTableFieldBlock(generateGovernanceSection());
        tableSection.addSummaryTableFieldBlock(generateCourseLogisticsSection());
        tableSection.addSummaryTableFieldBlock(generateLearningObjectivesSection());
        tableSection.addSummaryTableFieldBlock(generateRequirementsSection());
        tableSection.addSummaryTableFieldBlock(generateActiveDatesSection());
        tableSection.addSummaryTableFieldBlock(generateFeesSection());

        VerticalSectionView verticalSection = new VerticalSectionView(ViewCourseSections.DETAILED, getLabel(LUConstants.SUMMARY_LABEL_KEY), modelId, false);
        verticalSection.addSection(tableSection);

        return verticalSection;
    }

	@SuppressWarnings("unchecked")
	public SummaryTableFieldBlock generateCourseInformationForProposal(){
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        block.addEditingHandler(new EditHandler(CourseSections.COURSE_INFO));
        block.setTitle(getLabel(LUConstants.INFORMATION_LABEL_KEY));
        block.addSummaryTableFieldRow(getFieldRow(PROPOSAL_TITLE_PATH, generateMessageInfo(LUConstants.PROPOSAL_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + COURSE_TITLE, generateMessageInfo(LUConstants.COURSE_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + TRANSCRIPT_TITLE, generateMessageInfo(LUConstants.SHORT_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + SUBJECT_AREA, generateMessageInfo(LUConstants.SUBJECT_CODE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + COURSE_NUMBER_SUFFIX, generateMessageInfo(LUConstants.COURSE_NUMBER_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + INSTRUCTORS, generateMessageInfo(LUConstants.INSTRUCTORS_LABEL_KEY), null, null, null, new KeyListModelWigetBinding("personId"), false));

        block.addSummaryMultiplicity(getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + CROSS_LISTINGS,
		        LUConstants.CROSS_LISTED_ITEM_LABEL_KEY,
		        Arrays.asList(
		                Arrays.asList(SUBJECT_AREA, LUConstants.SUBJECT_CODE_LABEL_KEY),
		                Arrays.asList(COURSE_NUMBER_SUFFIX, LUConstants.COURSE_NUMBER_LABEL_KEY))));
        block.addSummaryMultiplicity(getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + JOINTS,
		        LUConstants.JOINT_OFFER_ITEM_LABEL_KEY,
		        Arrays.asList(
		                Arrays.asList(CreditCourseJointsConstants.COURSE_ID, LUConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY))));
        block.addSummaryMultiplicity(getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + VERSIONS,
		        LUConstants.VERSION_CODE_LABEL_KEY,
		        Arrays.asList(
		                Arrays.asList("variationCode", LUConstants.VERSION_CODE_LABEL_KEY),
		                Arrays.asList("variationTitle", LUConstants.TITLE_LABEL_KEY))));

        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + PROPOSAL_DESCRIPTION + "/" + RichTextInfoConstants.PLAIN, generateMessageInfo(LUConstants.DESCRIPTION_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow("proposal/rationale", generateMessageInfo(LUConstants.PROPOSAL_RATIONALE_LABEL_KEY)));


        return block;
    }

    public SummaryTableFieldBlock generateCourseInformation(){
        SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        block.addEditingHandler(new EditHandler(CourseSections.COURSE_INFO));
        block.setTitle(getLabel(LUConstants.INFORMATION_LABEL_KEY));
        //block.addSummaryTableFieldRow(getFieldRow(PROPOSAL_TITLE_PATH, generateMessageInfo(LUConstants.PROPOSAL_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + COURSE_TITLE, generateMessageInfo(LUConstants.COURSE_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + TRANSCRIPT_TITLE, generateMessageInfo(LUConstants.SHORT_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + SUBJECT_AREA, generateMessageInfo(LUConstants.SUBJECT_CODE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + COURSE_NUMBER_SUFFIX, generateMessageInfo(LUConstants.COURSE_NUMBER_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + INSTRUCTORS, generateMessageInfo(LUConstants.INSTRUCTORS_LABEL_KEY), null, null, null, new KeyListModelWigetBinding("personId"), false));

        block.addSummaryMultiplicity(getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + CROSS_LISTINGS,
		        LUConstants.CROSS_LISTED_ITEM_LABEL_KEY,
		        Arrays.asList(
		                Arrays.asList(SUBJECT_AREA, LUConstants.SUBJECT_CODE_LABEL_KEY),
		                Arrays.asList(COURSE_NUMBER_SUFFIX, LUConstants.COURSE_NUMBER_LABEL_KEY))));
        block.addSummaryMultiplicity(getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + JOINTS,
		        LUConstants.JOINT_OFFER_ITEM_LABEL_KEY,
		        Arrays.asList(
		                Arrays.asList(CreditCourseJointsConstants.COURSE_ID, LUConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY))));
        block.addSummaryMultiplicity(getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + VERSIONS,
		        LUConstants.VERSION_CODE_LABEL_KEY,
		        Arrays.asList(
		                Arrays.asList("variationCode", LUConstants.VERSION_CODE_LABEL_KEY),
		                Arrays.asList("variationTitle", LUConstants.TITLE_LABEL_KEY))));

        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + PROPOSAL_DESCRIPTION + "/" + RichTextInfoConstants.PLAIN, generateMessageInfo(LUConstants.DESCRIPTION_LABEL_KEY)));

        return block;
    }

    public SummaryTableFieldBlock generateGovernanceSection(){
    	SummaryTableFieldBlock block = new SummaryTableFieldBlock();
    	block.addEditingHandler(new EditHandler(CourseSections.GOVERNANCE));
        block.setTitle(getLabel(LUConstants.GOVERNANCE_LABEL_KEY));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + CAMPUS_LOCATIONS, generateMessageInfo(LUConstants.CAMPUS_LOCATION_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + CURRICULUM_OVERSIGHT_ORGS_, generateMessageInfo(LUConstants.ACADEMIC_SUBJECT_ORGS_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + ADMIN_ORGS, generateMessageInfo(LUConstants.ADMIN_ORG_LABEL_KEY)));
        return block;
    }

    @SuppressWarnings("unchecked")
	public SummaryTableFieldBlock generateCourseLogisticsSection(){
    	SummaryTableFieldBlock block = new SummaryTableFieldBlock();
    	block.addEditingHandler(new EditHandler(CourseSections.COURSE_LOGISTICS));
        block.setTitle(getLabel(LUConstants.LOGISTICS_LABEL_KEY));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + TERMS_OFFERED, generateMessageInfo(LUConstants.TERMS_OFFERED_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + GRADING_OPTIONS, generateMessageInfo(LUConstants.LEARNING_RESULT_ASSESSMENT_SCALE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + CreditCourseConstants.DURATION + "/" + "atpDurationTypeKey", generateMessageInfo(LUConstants.DURATION_TYPE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + CreditCourseConstants.DURATION + "/" + "timeQuantity", generateMessageInfo(LUConstants.DURATION_QUANTITY_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + PASS_FAIL, generateMessageInfo(LUConstants.LEARNING_RESULT_PASS_FAIL_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + AUDIT, generateMessageInfo(LUConstants.LEARNING_RESULT_AUDIT_LABEL_KEY)));

        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + CreditCourseConstants.FINAL_EXAM, generateMessageInfo(LUConstants.FINAL_EXAM_STATUS_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + CreditCourseConstants.FINAL_EXAM_RATIONALE, generateMessageInfo(LUConstants.FINAL_EXAM_RATIONALE_LABEL_KEY), true));

        //Outcomes
        Map<String, ModelWidgetBinding> customBindings = new HashMap<String, ModelWidgetBinding>();
        ListToTextBinding resultValuesBinding = new ListToTextBinding();
        customBindings.put("resultValues", resultValuesBinding);
        String outcomesKey = COURSE + QueryPath.getPathSeparator() + CREDIT_OPTIONS;
        MultiplicityConfiguration outcomesConfig = getMultiplicityConfig(outcomesKey,
        		LUConstants.LEARNING_RESULT_OUTCOME_LABEL_KEY,
		        Arrays.asList(
		                Arrays.asList(CreditCourseConstants.TYPE, LUConstants.LEARNING_RESULT_OUTCOME_TYPE_LABEL_KEY),
		                Arrays.asList(CREDIT_OPTION_FIXED_CREDITS, LUConstants.CONTACT_HOURS_LABEL_KEY, OPTIONAL),
		                Arrays.asList(CREDIT_OPTION_MIN_CREDITS, LUConstants.CREDIT_OPTION_MIN_CREDITS_LABEL_KEY, OPTIONAL),
		                Arrays.asList(CREDIT_OPTION_MAX_CREDITS, LUConstants.CREDIT_OPTION_MAX_CREDITS_LABEL_KEY, OPTIONAL),
		                Arrays.asList("resultValues", LUConstants.CREDIT_OPTION_FIXED_CREDITS_LABEL_KEY, OPTIONAL)),
		                customBindings);

        //Massive workaround for result values problem where we dont want to show them on certain selections,
        //in most cases you want to just use the optional flag and have it be based on empty/null data
        //but since this data is sometimes not empty/null when we dont want to show it, it requires a show
        //condition callback
        tableSection.addShowRowCallback(new ShowRowConditionCallback(){
			@Override
			public void processShowConditions(SummaryTableFieldRow row,
					DataModel column1, DataModel column2) {
				if(row.getFieldDescriptor1() != null &&
						row.getFieldDescriptor1().getFieldKey().contains(CREDIT_OPTIONS) &&
						row.getFieldDescriptor1().getFieldKey().contains("resultValues")){
		    		String type = row.getFieldDescriptor1().getFieldKey().replace("resultValues", CreditCourseConstants.TYPE);
		    		Object data1 = null;
		    		Object data2 = null;
		    		if(column1 != null){
		    			data1 = column1.get(type);
		    		}
		    		if(column2 != null){
		    			data2 = column2.get(type);
		    		}

		    		if(data1 != null && data1 instanceof String){
		    			if(!((String)data1).equals("kuali.resultComponentType.credit.degree.multiple")){
		    				row.setShown(false);
		    			}
		    		}
		    		else if(data2 != null && data2 instanceof String){
		    			if(!((String)data2).equals("kuali.resultComponentType.credit.degree.multiple")){
		    				row.setShown(false);
		    			}
		    		}
		    	}
			}
		});

        block.addSummaryMultiplicity(outcomesConfig);

        //Formats
        MultiplicityConfiguration formatsConfig = getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + FORMATS,
		        LUConstants.FORMAT_LABEL_KEY,
		        null);
        MultiplicityConfiguration activitiesConfig = getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + FORMATS + QueryPath.getPathSeparator()
        		+ QueryPath.getWildCard() + QueryPath.getPathSeparator() + ACTIVITIES,
		        LUConstants.ACTIVITY_LITERAL_LABEL_KEY,
		        Arrays.asList(
		                Arrays.asList(ACTIVITY_TYPE, LUConstants.ACTIVITY_TYPE_LABEL_KEY),
		                Arrays.asList(CONTACT_HOURS + "/" + "unitQuantity", LUConstants.CONTACT_HOURS_LABEL_KEY),
		                Arrays.asList(CONTACT_HOURS + "/" + "unitType", "per"),
		                Arrays.asList(CreditCourseActivityConstants.DURATION + "/" + "atpDurationTypeKey", LUConstants.DURATION_TYPE_LABEL_KEY),
		                Arrays.asList(CreditCourseActivityConstants.DURATION + "/" + "timeQuantity", LUConstants.DURATION_LITERAL_LABEL_KEY),
		                Arrays.asList(DEFAULT_ENROLLMENT_ESTIMATE, LUConstants.CLASS_SIZE_LABEL_KEY)));
        formatsConfig.setNestedConfig(activitiesConfig);
        block.addSummaryMultiplicity(formatsConfig);

        return block;
    }

    public SummaryTableFieldBlock generateLearningObjectivesSection(){
    	SummaryTableFieldBlock block = new SummaryTableFieldBlock();
    	block.addEditingHandler(new EditHandler(CourseSections.LEARNING_OBJECTIVES));
        block.setTitle(getLabel(LUConstants.LEARNING_OBJECTIVES_LABEL_KEY));
        SummaryTableFieldRow loRow = getFieldRow(COURSE + "/" + CreditCourseConstants.COURSE_SPECIFIC_LOS, generateMessageInfo(LUConstants.LEARNING_OBJECTIVES_LABEL_KEY),
        		new KSListPanel(), new KSListPanel(), null, new TreeStringBinding(), false);
        loRow.addContentCellStyleName("summaryTable-lo-cell");
        block.addSummaryTableFieldRow(loRow);

        return block;
    }

    public SummaryTableFieldBlock generateActiveDatesSection(){
    	SummaryTableFieldBlock block = new SummaryTableFieldBlock();
    	block.addEditingHandler(new EditHandler(CourseSections.ACTIVE_DATES));
        block.setTitle(getLabel(LUConstants.ACTIVE_DATES_LABEL_KEY));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + START_TERM, generateMessageInfo(LUConstants.START_TERM_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + END_TERM, generateMessageInfo(LUConstants.END_TERM_LABEL_KEY)));
        //Probably wrong - checkbox
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + PILOT_COURSE, generateMessageInfo(LUConstants.PILOT_COURSE_LABEL_KEY)));
        return block;
    }

    public SummaryTableFieldBlock generateFeesSection() {
    	SummaryTableFieldBlock block = new SummaryTableFieldBlock();
    	block.addEditingHandler(new EditHandler(CourseSections.FINANCIALS));
        block.setTitle(getLabel(LUConstants.FINANCIALS_LABEL_KEY));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + "feeJustification" + "/" + RichTextInfoConstants.PLAIN, generateMessageInfo(LUConstants.JUSTIFICATION_FEE)));
        //Fees
        MultiplicityConfiguration feesConfig = getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + FEES,
        		LUConstants.FEE,
		        Arrays.asList(
		                Arrays.asList("rateType", "Rate Type"),
		                Arrays.asList("feeType", "Fee Type")));
        //Note the use of empty string to remove the additional row from display in the summary table
        MultiplicityConfiguration amountsConfig = getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + FEES + QueryPath.getPathSeparator()
        		+ QueryPath.getWildCard() + QueryPath.getPathSeparator() + "feeAmounts",
        		"",
		        Arrays.asList(
		                Arrays.asList("currencyQuantity", "Amount")));
		feesConfig.setNestedConfig(amountsConfig);
		block.addSummaryMultiplicity(feesConfig);

		//Revenue
		MultiplicityConfiguration revenueConfig = getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + "revenues",
				LUConstants.REVENUE,
		        Arrays.asList(
		        		Arrays.asList("affiliatedOrgs/0/orgId", "Organization"),
		                Arrays.asList("affiliatedOrgs/0/percentage", "Percentage")));
		block.addSummaryMultiplicity(revenueConfig);

		//Expenditure
		MultiplicityConfiguration expenditureConfig = getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + "expenditure"
				+ QueryPath.getPathSeparator() + "affiliatedOrgs",
				LUConstants.EXPENDITURE,
		        Arrays.asList(
		                Arrays.asList("orgId", "Organization"),
		                Arrays.asList("percentage", "Percentage")));
		block.addSummaryMultiplicity(expenditureConfig);

        return block;
	}

    public SummaryTableFieldBlock generateRequirementsSection(){

    	final SummaryTableFieldBlock block = new SummaryTableFieldBlock();
    	block.addEditingHandler(new EditHandler(CourseSections.COURSE_REQUISITES));
        block.setTitle(getLabel(LUConstants.REQUISITES_LABEL_KEY));

        KSBlockingProgressIndicator.addTask(loadDataTask);
        CourseRequirementsDataModel.getStatementTypes(new Callback<List<StatementTypeInfo>>() {

            @Override
            public void exec(List<StatementTypeInfo> stmtTypes) {
                for (StatementTypeInfo stmtType : stmtTypes) {
                    if (stmtType.getId().contains("kuali.statement.type.course.enrollmentEligibility") ||
                        stmtType.getId().contains("kuali.statement.type.course.creditConstraints")) {
                        continue;
                    }

                    final FlowPanel panel1 = new FlowPanel();
                    final FlowPanel panel2 = new FlowPanel();
                    final SummaryTableFieldRow arow = new SummaryTableFieldRow(addRequisiteField(panel1, stmtType), addRequisiteField(panel2, stmtType));
                    block.addSummaryTableFieldRow(arow);
                }

                controller.requestModel(modelId, new ModelRequestCallback<DataModel>() {

                    @Override
                    public void onModelReady(DataModel model) {
                        if (model != null) {
                            tableSection.updateWidgetData(model);
                        }
                        KSBlockingProgressIndicator.removeTask(loadDataTask);
                    }

                    @Override
                    public void onRequestFail(Throwable cause) {
                        KSBlockingProgressIndicator.removeTask(loadDataTask);
                    }
                });
            }
        });

        return block;
    }

    private FieldDescriptorReadOnly addRequisiteField(final FlowPanel panel, final StatementTypeInfo stmtType) {

        final ModelWidgetBinding<FlowPanel> widgetBinding = new ModelWidgetBinding<FlowPanel>() {

            @Override
            public void setModelValue(FlowPanel panel, DataModel model, String path) {
            }

            @Override
            public void setWidgetValue(final FlowPanel panel, DataModel model, String path) {
                final CourseRequirementsDataModel reqDataModel = new CourseRequirementsDataModel(controller);

                panel.clear();
                String courseId = (model).getRoot().get("id");
                reqDataModel.retrieveStatementTypes(courseId, new Callback<Boolean>() {
                    @Override
                    public void exec(Boolean result) {
                        Iterator<StatementTreeViewInfo> iter = reqDataModel.getCourseReqInfo(stmtType.getId()).iterator();
                        Widget ruleWidget;
                        if (iter.hasNext()) {
                            StatementTreeViewInfo rule = iter.next();
                            ruleWidget = new SubrulePreviewWidget(rule, true, CourseRequirementsSummaryView.getCluSetWidgetList(rule));
                            panel.add(ruleWidget);
                        }
                    }
                });
            }
        };

        FieldDescriptorReadOnly requisiteField = new FieldDescriptorReadOnly(COURSE + "/" + CreditCourseConstants.ID, new MessageKeyInfo(stmtType.getName()), null, panel);
        requisiteField.setWidgetBinding(widgetBinding);

        return requisiteField;
    }

    private MultiplicityConfiguration getMultiplicityConfig(String path,
            String itemLabelMessageKey, List<List<String>> fieldKeysAndLabels){
    	return getMultiplicityConfig(path, itemLabelMessageKey, fieldKeysAndLabels, null);
    }

	private MultiplicityConfiguration getMultiplicityConfig(String path,
            String itemLabelMessageKey, List<List<String>> fieldKeysAndLabels, Map<String, ModelWidgetBinding> customBindings){
    	QueryPath parentPath = QueryPath.concat(path);
        MultiplicityConfiguration config = new MultiplicityConfiguration(MultiplicityConfiguration.MultiplicityType.TABLE,
                MultiplicityConfiguration.StyleType.TOP_LEVEL_GROUP, modelDefinition.getMetadata(parentPath));
        config.setItemLabel(getLabel(itemLabelMessageKey));
        config.setUpdateable(false);
        config.setShowHeaders(true);

        FieldDescriptorReadOnly parentFd = buildFieldDescriptor(path, getLabel(itemLabelMessageKey), null);
        config.setParent(parentFd);

        if (fieldKeysAndLabels != null) {
            for (List<String> fieldKeyAndLabel : fieldKeysAndLabels) {
            	MultiplicityFieldConfiguration fd = buildMultiplicityFD(fieldKeyAndLabel.get(0),
                        fieldKeyAndLabel.get(1), parentPath.toString());
            	if(fieldKeyAndLabel.size() == 3 && fieldKeyAndLabel.get(2).equals(OPTIONAL)){
            		fd.setOptional(true);
            	}
            	if(customBindings != null && customBindings.containsKey(fieldKeyAndLabel.get(0))){
            		fd.setModelWidgetBinding(customBindings.get(fieldKeyAndLabel.get(0)));
            	}
                config.addFieldConfiguration(fd);
            }
        }

        return config;
    }


    //TODO next 3 methods below should be moved into some kind of multiplicity helper class
    private MultiplicityFieldConfiguration buildMultiplicityFD(
            String fieldKey, String labelKey, String parentPath) {

        QueryPath fieldPath = QueryPath.concat(parentPath, QueryPath.getWildCard(), fieldKey);
        Metadata meta = modelDefinition.getMetadata(fieldPath);

        MultiplicityFieldConfiguration fd = new MultiplicityFieldConfiguration(
                fieldPath.toString(), generateMessageInfo(labelKey), meta, null);


        return fd;

    }

    private FieldDescriptorReadOnly buildFieldDescriptor(String fieldKey, String messageKey,String parentPath) {
        return buildFieldDescriptor(fieldKey, messageKey, parentPath, null, null);
    }

    private FieldDescriptorReadOnly buildFieldDescriptor(String fieldKey, String messageKey, String parentPath, Widget widget, ModelWidgetBinding<?> binding) {

        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        FieldDescriptorReadOnly fd = new FieldDescriptorReadOnly(path.toString(), generateMessageInfo(messageKey), meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        if (binding != null) {
            fd.setWidgetBinding(binding);
        }
        return fd;
    }

	public VerticalSectionView generateCourseBriefSection() {
		SummaryTableSection courseBriefSection = new SummaryTableSection(controller);
		courseBriefSection.setEditable(false);
		SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + COURSE_TITLE, generateMessageInfo(LUConstants.COURSE_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + "code", generateMessageInfo(LUConstants.COURSE_NUMBER_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + ADMIN_ORGS, generateMessageInfo(LUConstants.ADMIN_ORG_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + PROPOSAL_DESCRIPTION + "/" + RichTextInfoConstants.PLAIN, generateMessageInfo(LUConstants.DESCRIPTION_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + CURRICULUM_OVERSIGHT_ORGS_, generateMessageInfo(LUConstants.ACADEMIC_SUBJECT_ORGS_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + CAMPUS_LOCATIONS, generateMessageInfo(LUConstants.CAMPUS_LOCATION_LABEL_KEY)));

        Map<String, ModelWidgetBinding> customBindings = new HashMap<String, ModelWidgetBinding>();
        ListToTextBinding resultValuesBinding = new ListToTextBinding();
        customBindings.put("resultValues", resultValuesBinding);
        String outcomesKey = COURSE + QueryPath.getPathSeparator() + CREDIT_OPTIONS;
        MultiplicityConfiguration outcomesConfig = getMultiplicityConfig(outcomesKey,
        		LUConstants.LEARNING_RESULT_OUTCOME_LABEL_KEY,
		        Arrays.asList(
		                Arrays.asList(CreditCourseConstants.TYPE, LUConstants.LEARNING_RESULT_OUTCOME_TYPE_LABEL_KEY),
		                Arrays.asList(CREDIT_OPTION_FIXED_CREDITS, LUConstants.CONTACT_HOURS_LABEL_KEY, OPTIONAL),
		                Arrays.asList(CREDIT_OPTION_MIN_CREDITS, LUConstants.CREDIT_OPTION_MIN_CREDITS_LABEL_KEY, OPTIONAL),
		                Arrays.asList(CREDIT_OPTION_MAX_CREDITS, LUConstants.CREDIT_OPTION_MAX_CREDITS_LABEL_KEY, OPTIONAL),
		                Arrays.asList("resultValues", LUConstants.CREDIT_OPTION_FIXED_CREDITS_LABEL_KEY, OPTIONAL)),
		                customBindings);

        //Massive workaround for result values problem where we dont want to show them on certain selections,
        //in most cases you want to just use the optional flag and have it be based on empty/null data
        //but since this data is sometimes not empty/null when we dont want to show it, it requires a show
        //condition callback
        courseBriefSection.addShowRowCallback(new ShowRowConditionCallback(){
			@Override
			public void processShowConditions(SummaryTableFieldRow row,
					DataModel column1, DataModel column2) {
				if(row.getFieldDescriptor1() != null &&
						row.getFieldDescriptor1().getFieldKey().contains(CREDIT_OPTIONS) &&
						row.getFieldDescriptor1().getFieldKey().contains("resultValues")){
		    		String type = row.getFieldDescriptor1().getFieldKey().replace("resultValues", CreditCourseConstants.TYPE);
		    		Object data1 = null;
		    		Object data2 = null;
		    		if(column1 != null){
		    			data1 = column1.get(type);
		    		}
		    		if(column2 != null){
		    			data2 = column2.get(type);
		    		}

		    		if(data1 != null && data1 instanceof String){
		    			if(!((String)data1).equals("kuali.resultComponentType.credit.degree.multiple")){
		    				row.setShown(false);
		    			}
		    		}
		    		else if(data2 != null && data2 instanceof String){
		    			if(!((String)data2).equals("kuali.resultComponentType.credit.degree.multiple")){
		    				row.setShown(false);
		    			}
		    		}
		    	}
			}
		});
        block.addSummaryMultiplicity(outcomesConfig);

        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + TERMS_OFFERED, generateMessageInfo(LUConstants.TERMS_OFFERED_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + GRADING_OPTIONS, generateMessageInfo(LUConstants.LEARNING_RESULT_ASSESSMENT_SCALE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + PASS_FAIL, generateMessageInfo(LUConstants.LEARNING_RESULT_PASS_FAIL_LABEL_KEY), true));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + AUDIT, generateMessageInfo(LUConstants.LEARNING_RESULT_AUDIT_LABEL_KEY), true));
        MultiplicityConfiguration formatsConfig = getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + FORMATS,
		        LUConstants.FORMAT_LABEL_KEY,
		        null);
        MultiplicityConfiguration activitiesConfig = getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + FORMATS + QueryPath.getPathSeparator()
        		+ QueryPath.getWildCard() + QueryPath.getPathSeparator() + ACTIVITIES,
		        LUConstants.ACTIVITY_LITERAL_LABEL_KEY,
		        Arrays.asList(
		                Arrays.asList(ACTIVITY_TYPE, LUConstants.ACTIVITY_TYPE_LABEL_KEY),
		                Arrays.asList(CONTACT_HOURS + "/" + "unitQuantity", LUConstants.CONTACT_HOURS_LABEL_KEY),
		                Arrays.asList(CONTACT_HOURS + "/" + "unitType", "per"),
		                Arrays.asList(CreditCourseActivityConstants.DURATION + "/" + "atpDurationTypeKey", LUConstants.DURATION_TYPE_LABEL_KEY),
		                Arrays.asList(CreditCourseActivityConstants.DURATION + "/" + "timeQuantity", LUConstants.DURATION_LITERAL_LABEL_KEY),
		                Arrays.asList(DEFAULT_ENROLLMENT_ESTIMATE, LUConstants.CLASS_SIZE_LABEL_KEY)));
        formatsConfig.setNestedConfig(activitiesConfig);
        block.addSummaryMultiplicity(formatsConfig);
        //Fees
        MultiplicityConfiguration feesConfig = getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + FEES,
        		LUConstants.FEE,
		        Arrays.asList(
		                Arrays.asList("rateType", "Rate Type"),
		                Arrays.asList("feeType", "Fee Type")));
        //Note the use of empty string to remove the additional row from display in the summary table
        MultiplicityConfiguration amountsConfig = getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + FEES + QueryPath.getPathSeparator()
        		+ QueryPath.getWildCard() + QueryPath.getPathSeparator() + "feeAmounts",
        		"",
		        Arrays.asList(
		                Arrays.asList("currencyQuantity", "Amount")));
		feesConfig.setNestedConfig(amountsConfig);
		block.addSummaryMultiplicity(feesConfig);
/*        block.addSummaryMultiplicity(getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + CROSS_LISTINGS,
		        LUConstants.CROSS_LISTED_ITEM_LABEL_KEY,
		        Arrays.asList(
		                Arrays.asList(SUBJECT_AREA, LUConstants.SUBJECT_CODE_LABEL_KEY),
		                Arrays.asList(COURSE_NUMBER_SUFFIX, LUConstants.COURSE_NUMBER_LABEL_KEY))));
        block.addSummaryMultiplicity(getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + JOINTS,
		        LUConstants.JOINT_OFFER_ITEM_LABEL_KEY,
		        Arrays.asList(
		                Arrays.asList(CreditCourseJointsConstants.COURSE_ID, LUConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY))));
        block.addSummaryMultiplicity(getMultiplicityConfig(COURSE + QueryPath.getPathSeparator() + VERSIONS,
		        LUConstants.VERSION_CODE_LABEL_KEY,
		        Arrays.asList(
		                Arrays.asList("variationCode", LUConstants.VERSION_CODE_LABEL_KEY),
		                Arrays.asList("variationTitle", LUConstants.TITLE_LABEL_KEY))));*/
		courseBriefSection.addSummaryTableFieldBlock(block);
        VerticalSectionView verticalSection = new VerticalSectionView(ViewCourseSections.BRIEF, "At a Glance", modelId, false);
        verticalSection.addSection(courseBriefSection);

        return verticalSection;
	}

	public VerticalSectionView generateCourseCatalogSection() {
		VerticalSectionView verticalSection = new VerticalSectionView(ViewCourseSections.CATALOG, "Catalog View", modelId, false);
		FieldDescriptorReadOnly catalogField = new FieldDescriptorReadOnly("", null, null, new HTML());
		catalogField.hideLabel();
		catalogField.setWidgetBinding(new ModelWidgetBinding<HTML>(){

			@Override
			public void setModelValue(HTML widget, DataModel model, String path) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setWidgetValue(HTML widget, DataModel model, String path) {
				String code = model.get("code");
				String title = model.get(COURSE + "/" + COURSE_TITLE);
				String credits = "";
				String outcomesKey = COURSE + QueryPath.getPathSeparator() + CREDIT_OPTIONS;
				Data outcomes = model.get(outcomesKey);
				if(outcomes !=null){
					Iterator<Property> iter = outcomes.realPropertyIterator();
					String list = "";
					ListToTextBinding binding = new ListToTextBinding();
					while(iter.hasNext()){
						Property prop = iter.next();
						if (prop.getKey() instanceof Integer){
		                	Integer number = (Integer)prop.getKey();
		                	Object value = outcomes.get(number);
		                	if(value instanceof Data){
		                		list = list + binding.getStringList(model, outcomesKey + "/" + number +"/" + "resultValues") + ", ";
		                	}
		                }
					}

					if(!list.isEmpty()){
						list = list.trim();
						list = list.substring(0, list.length() - 1);
            			credits = "(" + list + ")";
            		}
				}

				String description = model.get(COURSE + "/" + PROPOSAL_DESCRIPTION + "/" + RichTextInfoConstants.PLAIN);
				String catalogText = "<b> " + code + " " + title + " " + credits + "</b> " + description + " ";
				catalogText.replace(" null ", "");
				catalogText.trim();
				widget.setHTML(catalogText);

			}
		});
		verticalSection.addField(catalogField);

		return verticalSection;
	}
}
