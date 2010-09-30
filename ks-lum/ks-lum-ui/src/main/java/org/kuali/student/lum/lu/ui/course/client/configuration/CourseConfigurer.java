/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.configuration;

import java.util.*;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.HasDataValueBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ListOfStringBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.*;
import org.kuali.student.common.ui.client.configurable.mvc.sections.*;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.ListOfStringWidget;
import org.kuali.student.common.ui.client.widgets.commenttool.CommentPanel;
import org.kuali.student.common.ui.client.widgets.commenttool.CommentTool;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.comments.ui.client.widgets.decisiontool.DecisionPanel;
import org.kuali.student.core.workflow.ui.client.widgets.CollaboratorTool;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowEnhancedNavController;
import org.kuali.student.lum.common.client.lo.LOBuilder;
import org.kuali.student.lum.common.client.lo.LOBuilderBinding;
import org.kuali.student.lum.common.client.lo.LUConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseJointsConstants;
import org.kuali.student.lum.lu.ui.course.client.controllers.CourseProposalController;
import org.kuali.student.lum.lu.ui.course.client.requirements.CourseRequirementsViewController;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;


/**
 * This is the configuration factory class for creating a proposal.
 *
 * @author Kuali Student Team
 */
public class CourseConfigurer extends AbstractCourseConfigurer {
    protected String groupName;

    protected String type = "course";
    protected String state = "draft";

    protected boolean WITH_DIVIDER = true;
    protected boolean NO_DIVIDER = false;

    
    public static final String PROPOSAL_PATH = "proposal";
    public static final String PROPOSAL_TITLE_PATH = "proposal/name";
    public static final String COURSE_TITLE_PATH = "/courseTitle";
    protected DocumentTool documentTool;

    //Override paths for course and proposal so they are root
    public static final String PROPOSAL = "";
    public static final String COURSE = "";

    public enum CourseSections {
        CLU_BEGIN, PEOPLE_PERMISSOMS, SUMMARY, AUTHORS_RATIONALE, GOVERNANCE, COURSE_LOGISTICS, COURSE_INFO, LEARNING_OBJECTIVES,
        COURSE_REQUISITES, ACTIVE_DATES, FINANCIALS, ATTACHMENTS, COMMENTS,DECISIONS, DOCUMENTS,
        PROGRAM_INFO, ASSEMBLER_TEST
    }

    protected DataModelDefinition modelDefinition;

    public void setModelDefinition(DataModelDefinition modelDefinition) {
        this.modelDefinition = modelDefinition;
    }

    public void configure(final CourseProposalController layout) {
        groupName = LUConstants.COURSE_GROUP_NAME;

        groupName = LUConstants.COURSE_GROUP_NAME;

        if (modelDefinition.getMetadata().isCanEdit()) {
        	addCluStartSection(layout);
            String sections = getLabel(LUConstants.COURSE_SECTIONS);

            //ProposalInformation
            //layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.PROPOSAL_INFORMATION_LABEL_KEY)}, generateAuthorsRationaleSection());

            layout.addMenu(sections);

            //Course Content
            layout.addMenuItem(sections, generateCourseInfoSection());
            layout.addMenuItem(sections, generateGovernanceSection());
            layout.addMenuItem(sections, generateCourseLogisticsSection());
            layout.addMenuItem(sections, generateLearningObjectivesSection());

            //Student Eligibility
            layout.addMenuItem(sections, generateCourseRequisitesSection(layout));

            //Administrative
            layout.addMenuItem(sections, generateActiveDatesSection());
            layout.addMenuItem(sections, generateFinancialsSection());
            
            //Authors & Collaborators
            layout.addMenuItem(sections, new CollaboratorTool(CourseSections.PEOPLE_PERMISSOMS, LUConstants.SECTION_AUTHORS_AND_COLLABORATORS,
                    getH2Title(LUConstants.SECTION_AUTHORS_AND_COLLABORATORS)));
            
            //Documents
            documentTool = new DocumentTool(CourseSections.DOCUMENTS, getLabel(LUConstants.TOOL_DOCUMENTS_LABEL_KEY));
            documentTool.setModelDefinition(modelDefinition);
            layout.addMenuItem(sections, documentTool);
            
            //Summary
            CourseSummaryConfigurer summaryConfigurer = new CourseSummaryConfigurer(type, state, groupName, modelDefinition, (Controller)layout);
            layout.addSpecialMenuItem(summaryConfigurer.generateProposalSummarySection(true), "Review and Submit");
            
            //Add common buttons to sections except for sections with specific button behavior
            List<Enum<?>> excludedViews = new ArrayList<Enum<?>>();
            excludedViews.add(CourseSections.PEOPLE_PERMISSOMS);
            excludedViews.add(CourseSections.DOCUMENTS);
            layout.addCommonButton(LUConstants.COURSE_SECTIONS, layout.getSaveButton(), excludedViews);
            layout.addCommonButton(LUConstants.COURSE_SECTIONS, layout.getCancelButton(CourseSections.SUMMARY), excludedViews);
            
            //Specific buttons for certain views
            //TODO people and permissions will use a different button than continue
            layout.addButtonForView(CourseSections.PEOPLE_PERMISSOMS, getContinueButton(layout));
            layout.addButtonForView(CourseSections.DOCUMENTS, getContinueButton(layout));
        }
        else{
        	 CourseSummaryConfigurer summaryConfigurer = new CourseSummaryConfigurer(type, state, groupName, modelDefinition, (Controller)layout);
        	 layout.removeMenuNavigation();
             layout.addView(summaryConfigurer.generateProposalSummarySection(false));
        }
        
        layout.setDefaultView(CourseSections.SUMMARY);
        layout.addContentWidget(layout.getWfUtilities().getWorkflowStatusLabel());
        final CommentTool commentTool = new CommentTool(CourseSections.COMMENTS, getLabel(LUConstants.TOOL_COMMENTS_LABEL_KEY), "kuali.comment.type.generalRemarks", "Proposal Comments");
        commentTool.setController((Controller)layout);
        
        layout.addContentWidget(new KSButton("Comments", ButtonStyle.DEFAULT_ANCHOR, new ClickHandler() {
            
            @Override
            public void onClick(ClickEvent event) {
                commentTool.show();
            }
        }));

        
        final DecisionPanel decisionPanel = new DecisionPanel(CourseSections.DECISIONS, getLabel(LUConstants.TOOL_DECISION_LABEL_KEY), "kuali.comment.type.generalRemarks");
        layout.addView(decisionPanel);
        layout.addContentWidget(new KSButton("Decisions", ButtonStyle.DEFAULT_ANCHOR, new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
            	decisionPanel.show();
            }
        }));

    }
    
    protected KSButton getContinueButton(final CourseProposalController layout){
        return new KSButton("Continue", new ClickHandler(){
                    public void onClick(ClickEvent event) {
                    	layout.showNextViewOnMenu();
                    }
                });
    }

    public void addCluStartSection(CourseProposalController layout) {
        VerticalSectionView section = initSectionView(CourseSections.CLU_BEGIN, LUConstants.START_LABEL_KEY);

        addField(section, PROPOSAL_TITLE_PATH, generateMessageInfo(LUConstants.PROPOSAL_TITLE_LABEL_KEY));

        //addField(section, PROPOSAL + "/" + PROPOSER_PERSON, generateMessageInfo(LUConstants.PROPOSAL_PERSON_LABEL_KEY), new PersonList()) ;
        layout.addStartViewPopup(section);
        layout.getStartPopup().setMaxHeight(200);
    }

    protected View generateCourseRequisitesSection(Controller layout) {
        return new CourseRequirementsViewController(layout, getLabel(LUConstants.REQUISITES_LABEL_KEY), CourseSections.COURSE_REQUISITES, false);
    }

    protected SectionView generateActiveDatesSection() {
        VerticalSectionView section = initSectionView(CourseSections.ACTIVE_DATES, LUConstants.ACTIVE_DATES_LABEL_KEY);

        addField(section, COURSE + "/" + START_TERM, generateMessageInfo(LUConstants.START_TERM_LABEL_KEY));
        addField(section, COURSE + "/" + END_TERM, generateMessageInfo(LUConstants.END_TERM_LABEL_KEY));
        addField(section, COURSE + "/" + PILOT_COURSE, generateMessageInfo(LUConstants.PILOT_COURSE_LABEL_KEY), new KSCheckBox(getLabel(LUConstants.PILOT_COURSE_TEXT_LABEL_KEY)));

        return section;
    }

    protected VerticalSection generateActiveDateEndSection() {
        VerticalSection endDate = initSection(getH3Title(LUConstants.END_DATE_LABEL_KEY), WITH_DIVIDER);
        addField(endDate, COURSE + "/" + EXPIRATION_DATE, generateMessageInfo(LUConstants.EXPIRATION_DATE_LABEL_KEY));
        return endDate;
    }

    protected VerticalSection generateActiveDateStartSection() {
        VerticalSection startDate = initSection(getH3Title(LUConstants.START_DATE_LABEL_KEY), WITH_DIVIDER);
        addField(startDate, COURSE + "/" + CreditCourseConstants.EFFECTIVE_DATE, generateMessageInfo(LUConstants.EFFECTIVE_DATE_LABEL_KEY));
        return startDate;
    }

    protected SectionView generateGovernanceSection() {
        VerticalSectionView section = initSectionView(CourseSections.GOVERNANCE, LUConstants.GOVERNANCE_LABEL_KEY);
        addField(section, COURSE + "/" + CAMPUS_LOCATIONS, generateMessageInfo(LUConstants.CAMPUS_LOCATION_LABEL_KEY));
        addField(section, COURSE + "/" + CURRICULUM_OVERSIGHT_ORGS_, generateMessageInfo(LUConstants.ACADEMIC_SUBJECT_ORGS_KEY));
        addField(section, COURSE + "/" + ADMIN_ORGS, generateMessageInfo(LUConstants.ADMIN_ORG_LABEL_KEY));

        return section;
    }

    public SectionView generateCourseInfoSection() {
        VerticalSectionView section = initSectionView(CourseSections.COURSE_INFO, LUConstants.INFORMATION_LABEL_KEY);
        addField(section, PROPOSAL_TITLE_PATH, generateMessageInfo(LUConstants.PROPOSAL_TITLE_LABEL_KEY));
        addField(section, COURSE + "/" + COURSE_TITLE, generateMessageInfo(LUConstants.COURSE_TITLE_LABEL_KEY));
        addField(section, COURSE + "/" + TRANSCRIPT_TITLE, generateMessageInfo(LUConstants.SHORT_TITLE_LABEL_KEY));
        section.addSection(generateCourseNumberSection());
        FieldDescriptor instructorsFd = addField(section, COURSE + "/" + INSTRUCTORS, generateMessageInfo(LUConstants.INSTRUCTORS_LABEL_KEY));
        instructorsFd.setWidgetBinding(new KeyListModelWigetBinding("personId"));
        section.addSection(generateDescriptionRationaleSection());

        return section;
    }

    protected GroupSection generateCourseNumberSection() {

        //COURSE NUMBER
        GroupSection courseNumber = new GroupSection(getH4Title(""));
        courseNumber.addStyleName(LUConstants.STYLE_SECTION);
        courseNumber.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        addField(courseNumber, COURSE + "/" + SUBJECT_AREA, generateMessageInfo(LUConstants.SUBJECT_CODE_LABEL_KEY));
        addField(courseNumber, COURSE + "/" + COURSE_NUMBER_SUFFIX, generateMessageInfo(LUConstants.COURSE_NUMBER_LABEL_KEY));
//        addField(courseNumber, COURSE + "/" + SUBJECT_AREA);
//        addField(courseNumber, COURSE + "/" + COURSE_NUMBER_SUFFIX);

        courseNumber.addSection(generateCrossListed_Ver_Joint_Section());

        return courseNumber;
    }

    protected CollapsableSection generateCrossListed_Ver_Joint_Section() {
        CollapsableSection result = new CollapsableSection(getLabel(LUConstants.CL_V_J_LABEL_KEY));

//        addField(result, COURSE + "/" + CROSS_LISTINGS, null, new CrossListedList(COURSE + "/" + CROSS_LISTINGS));
//        addField(result, COURSE + "/" + JOINTS, null, new OfferedJointlyList(COURSE + "/" + JOINTS));
//        addField(result, COURSE + "/" + VERSIONS, null, new VersionCodeList(COURSE + "/" + VERSIONS));
        addMultiplicityFields(result, COURSE + QueryPath.getPathSeparator() + CROSS_LISTINGS,
                LUConstants.ADD_CROSS_LISTED_LABEL_KEY,
                LUConstants.CROSS_LISTED_ITEM_LABEL_KEY,
                Arrays.asList(
                        new MultiplicityFieldConfig(
                                SUBJECT_AREA, 
                                LUConstants.SUBJECT_CODE_LABEL_KEY, null, null, true),
                        new MultiplicityFieldConfig(
                                COURSE_NUMBER_SUFFIX, 
                                LUConstants.COURSE_NUMBER_LABEL_KEY, null, null, true)),
                        null,
                        null);
        addMultiplicityFields(result, COURSE + QueryPath.getPathSeparator() + JOINTS,
                LUConstants.ADD_EXISTING_LABEL_KEY,
                LUConstants.JOINT_OFFER_ITEM_LABEL_KEY,
                Arrays.asList(
                        new MultiplicityFieldConfig(
                                CreditCourseJointsConstants.COURSE_ID, 
                                LUConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY, null, null, true)),
                                null,
                                null);
        addMultiplicityFields(result, COURSE + QueryPath.getPathSeparator() + VERSIONS,
                LUConstants.ADD_VERSION_CODE_LABEL_KEY,
                LUConstants.VERSION_CODE_LABEL_KEY,
                Arrays.asList(
                        new MultiplicityFieldConfig(
                                "variationCode", 
                                LUConstants.VERSION_CODE_LABEL_KEY, null, null, true), 
                        new MultiplicityFieldConfig(
                                "variationTitle", 
                                LUConstants.TITLE_LABEL_KEY, null, null, true)
                ),
                null,
                null);
        return result;
    }
    
    protected void addFeeMultiplicityFields(Section section,  
            String path, String addItemlabelMessageKey,
            String itemLabelMessageKey, List<MultiplicityFieldConfig> fieldConfigs,
            Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> swappableFieldsDefinition,
            List<String> deletionParentKeys) {
        MultiplicityConfiguration config = setupMultiplicityConfig(
                MultiplicityConfiguration.MultiplicityType.GROUP,
                MultiplicityConfiguration.StyleType.TOP_LEVEL_GROUP,
                path, addItemlabelMessageKey, itemLabelMessageKey,
                fieldConfigs, swappableFieldsDefinition, deletionParentKeys);
        MultiplicitySection ms = null;
        ms = new MultiplicitySection(config, swappableFieldsDefinition, deletionParentKeys);
        section.addSection(ms);

    }
    
    protected MultiplicityConfiguration setupMultiplicityConfig(
            MultiplicityConfiguration.MultiplicityType multiplicityType,
            MultiplicityConfiguration.StyleType styleType,
            String path, String addItemlabelMessageKey,
            String itemLabelMessageKey, List<MultiplicityFieldConfig> fieldConfigs,
            Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> swappableFieldsDefinition,
            List<String> deletionParentKeys) {
        QueryPath parentPath = QueryPath.concat(path);
        MultiplicityConfiguration config = new MultiplicityConfiguration(multiplicityType,
                styleType, getMetaData(parentPath.toString()));
        config.setAddItemLabel(getLabel(addItemlabelMessageKey));
        config.setItemLabel(getLabel(itemLabelMessageKey));
        config.setUpdateable(true);

        FieldDescriptor parentFd = buildMuliplicityParentFieldDescriptor(path, getLabel(itemLabelMessageKey), null);
        config.setParent(parentFd);

        if (fieldConfigs != null) {
            for (MultiplicityFieldConfig fieldConfig : fieldConfigs) {
                MultiplicityFieldConfiguration fc = buildMultiplicityFD(fieldConfig.getFieldKey(),
                        fieldConfig.getLabelKey(), parentPath.toString());
                config.addFieldConfiguration(fc);
                if (fieldConfig.isNextLine()) {
                    config.nextLine();
                }
            }
        }
        return config;
    }

    protected void addMultiplicityFields(Section section,  
            String path, String addItemlabelMessageKey,
            String itemLabelMessageKey, List<MultiplicityFieldConfig> fieldConfigs,
            Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> swappableFieldsDefinition,
            List<String> deletionParentKeys) {
        MultiplicityConfiguration config = setupMultiplicityConfig(
                MultiplicityConfiguration.MultiplicityType.GROUP,
                MultiplicityConfiguration.StyleType.TOP_LEVEL_GROUP,
                path, addItemlabelMessageKey, itemLabelMessageKey,
                fieldConfigs, swappableFieldsDefinition, deletionParentKeys);
        MultiplicitySection ms = null;
        ms = new MultiplicitySection(config, swappableFieldsDefinition, deletionParentKeys);
        section.addSection(ms);
    }

    protected Metadata getMetaData(String fieldKey) {
        return modelDefinition.getMetadata(QueryPath.concat(fieldKey));
    }

    protected MultiplicityFieldConfiguration buildMultiplicityFD(
            String fieldKey, String labelKey, String parentPath) {

        QueryPath fieldPath = QueryPath.concat(parentPath, QueryPath.getWildCard(), fieldKey);
        Metadata meta = modelDefinition.getMetadata(fieldPath);

        MultiplicityFieldConfiguration fd = new MultiplicityFieldConfiguration(
                fieldPath.toString(), generateMessageInfo(labelKey), meta, null);
        

        return fd;

    }

    protected FieldDescriptor buildMuliplicityParentFieldDescriptor(String fieldKey, String messageKey, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        FieldDescriptor fd = new FieldDescriptor(path.toString(), generateMessageInfo(messageKey), meta);
        return fd;
    }

    protected VerticalSection generateCourseInfoShortTitleSection() {
        VerticalSection shortTitle = initSection(getH3Title(LUConstants.SHORT_TITLE_LABEL_KEY), WITH_DIVIDER);
        addField(shortTitle, COURSE + "/" + TRANSCRIPT_TITLE, null);
        return shortTitle;
    }

    protected VerticalSection generateLongTitleSection() {
        VerticalSection longTitle = initSection(getH3Title(LUConstants.TITLE_LABEL_KEY), WITH_DIVIDER);
        addField(longTitle, COURSE + "/" + COURSE_TITLE, null);
        return longTitle;
    }

    protected VerticalSection generateDescriptionRationaleSection() {
        SectionTitle title = getH4Title(LUConstants.PROPOSAL_TITLE_SECTION_LABEL_KEY);
        VerticalSection description = initSection(title, !WITH_DIVIDER);
        title.setStyleName("cluProposalTitleSection");
        //FIXME [KSCOR-225] Temporary fix til we have a real rich text editor
        //addField(description, COURSE + "/" + DESCRIPTION, null);
        addField(description, COURSE + "/" + PROPOSAL_DESCRIPTION + "/" + RichTextInfoConstants.PLAIN, generateMessageInfo(LUConstants.DESCRIPTION_LABEL_KEY));
        addField(description, "proposal/rationale", generateMessageInfo(LUConstants.PROPOSAL_RATIONALE_LABEL_KEY));
        return description;
    }

    public SectionView generateCourseLogisticsSection() {
        VerticalSectionView section = initSectionView(CourseSections.COURSE_LOGISTICS, LUConstants.LOGISTICS_LABEL_KEY);
        section.setInstructions(getLabel(LUConstants.LOGISTICS_LABEL_KEY + "-instruct") + "<br><br>");

        section.addSection(generateSchedulingSection());
        section.addSection(generateDurationSection());
        section.addSection(generateLearningResultsSection());
        section.addSection(generateCourseFormatsSection());

        return section;
    }

    protected Section generateLearningResultsSection() {
        VerticalSection learningResults = initSection(getH3Title(LUConstants.LEARNING_RESULTS_LABEL_KEY), WITH_DIVIDER);
        learningResults.setInstructions(getLabel(LUConstants.LEARNING_RESULTS_LABEL_KEY + "-instruct") + "<br><br><br>");

        learningResults.addSection(generateGradesAssessmentsSection());
        learningResults.addSection(generateStudentRegistrationOptionsSection());
        learningResults.addSection(generateFinalExamSection());
        learningResults.addSection(generateOutcomesSection());

        return learningResults;
    }

    protected Section generateOutcomesSection() {

        String path = COURSE + QueryPath.getPathSeparator() + CREDIT_OPTIONS;
        QueryPath creditTypeFullPath = QueryPath.concat(path, QueryPath.getWildCard(), CreditCourseConstants.TYPE);
        QueryPath creditOptionFixedFullPath = QueryPath.concat(path, QueryPath.getWildCard(), CREDIT_OPTION_FIXED_CREDITS);
        QueryPath creditOptionMinFullPath = QueryPath.concat(path, QueryPath.getWildCard(), CREDIT_OPTION_MIN_CREDITS);
        QueryPath creditOptionMaxFullPath = QueryPath.concat(path, QueryPath.getWildCard(), CREDIT_OPTION_MAX_CREDITS);
        QueryPath creditResultValuesFullPath = QueryPath.concat(path, QueryPath.getWildCard(), "resultValues");

        VerticalSection courseOutcomes = initSection(getH3Title(LUConstants.LEARNING_RESULT_OUTCOME_LABEL_KEY), WITH_DIVIDER);
        Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> swappableFieldsDefinition =
            new HashMap<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>>();
        SwapCompositeCondition fixedCreditCondition = new SwapCompositeCondition(
                CompositeConditionOperator.AND);
        fixedCreditCondition.getChildrenConditions().add(
                makeCondition(creditTypeFullPath, LUConstants.LEARNING_RESULT_OUTCOME_TYPE_LABEL_KEY, "kuali.resultComponentType.credit.degree.fixed")
        );
        fixedCreditCondition.setConditionId("1");
        SwapCompositeCondition multipleCreditCondition = new SwapCompositeCondition(
                CompositeConditionOperator.AND);
        multipleCreditCondition.getChildrenConditions().add(
                makeCondition(creditTypeFullPath, LUConstants.LEARNING_RESULT_OUTCOME_TYPE_LABEL_KEY, "kuali.resultComponentType.credit.degree.multiple")
        );
        multipleCreditCondition.setConditionId("2");
        SwapCompositeCondition variableCreditCondition = new SwapCompositeCondition(
                CompositeConditionOperator.AND);
        variableCreditCondition.getChildrenConditions().add(
                makeCondition(creditTypeFullPath, LUConstants.LEARNING_RESULT_OUTCOME_TYPE_LABEL_KEY, "kuali.resultComponentType.credit.degree.range")
        );
        variableCreditCondition.setConditionId("3");
        
        swappableFieldsDefinition.put(fixedCreditCondition,
                Arrays.asList(
                        new SwapCompositeConditionFieldConfig(
                                new MultiplicityFieldConfiguration(
                                        creditOptionFixedFullPath.toString(), 
                                        generateMessageInfo(LUConstants.CREDIT_OPTION_FIXED_CREDITS_LABEL_KEY),
                                        modelDefinition.getMetadata(creditOptionFixedFullPath),
                                        null),
                                null
                        )
                )
        );
        MultiplicityFieldWidgetInitializer multipleCreditInitializer = 
            new MultiplicityFieldWidgetInitializer() {
                @Override
                public ModelWidgetBinding getModelWidgetBindingInstance() {
                    return new ListOfStringBinding();
                }
                @Override
                public Widget getNewWidget() {
                    return new ListOfStringWidget("Add Item");
                }
        };
        
        swappableFieldsDefinition.put(multipleCreditCondition,
                Arrays.asList(
                        new SwapCompositeConditionFieldConfig(
                                new MultiplicityFieldConfiguration(
                                        creditResultValuesFullPath.toString(),
                                        generateMessageInfo(LUConstants.CREDIT_OPTION_FIXED_CREDITS_LABEL_KEY),
                                        modelDefinition.getMetadata(creditResultValuesFullPath),
                                        multipleCreditInitializer),
                                null
                        )
                )
        );
        swappableFieldsDefinition.put(variableCreditCondition,
                Arrays.asList(
                        new SwapCompositeConditionFieldConfig(
                                new MultiplicityFieldConfiguration(
                                        creditOptionMinFullPath.toString(), 
                                        generateMessageInfo(LUConstants.CREDIT_OPTION_MIN_CREDITS_LABEL_KEY),
                                        modelDefinition.getMetadata(creditOptionMinFullPath),
                                        null),
                                null
                        ),
                        new SwapCompositeConditionFieldConfig(
                                new MultiplicityFieldConfiguration(
                                        creditOptionMaxFullPath.toString(), 
                                        generateMessageInfo(LUConstants.CREDIT_OPTION_MAX_CREDITS_LABEL_KEY),
                                        modelDefinition.getMetadata(creditOptionMaxFullPath),
                                        null),
                                null
                        )
                )
        );
        
        addMultiplicityFields(
                courseOutcomes, 
                path, 
                LUConstants.LEARNING_RESULT_OUTCOME_LABEL_KEY,
                LUConstants.LEARNING_RESULT_OUTCOME_LABEL_KEY,
                Arrays.asList(
                        new MultiplicityFieldConfig(
                                CreditCourseConstants.TYPE,
                                LUConstants.LEARNING_RESULT_OUTCOME_TYPE_LABEL_KEY,
                                null, null, true)
                ), swappableFieldsDefinition, null);

        return courseOutcomes;

    }

    protected Section generateStudentRegistrationOptionsSection() {
        VerticalSection studentRegistrationOptionsSection = initSection(getH3Title(LUConstants.LEARNING_RESULTS_STUDENT_REGISTRATION_LABEL_KEY), WITH_DIVIDER);

        addField(studentRegistrationOptionsSection, COURSE + "/" + AUDIT, generateMessageInfo(LUConstants.LEARNING_RESULT_AUDIT_LABEL_KEY), new KSCheckBox(getLabel(LUConstants.LEARNING_RESULT_AUDIT_TEXT_LABEL_KEY)));
        addField(studentRegistrationOptionsSection, COURSE + "/" + PASS_FAIL, generateMessageInfo(LUConstants.LEARNING_RESULT_PASS_FAIL_LABEL_KEY), new KSCheckBox(getLabel(LUConstants.LEARNING_RESULT_PASS_FAIL_TEXT_LABEL_KEY)));

        return studentRegistrationOptionsSection;
    }

    protected Section generateGradesAssessmentsSection() {
        VerticalSection gradesAssessments = initSection(getH3Title(LUConstants.LEARNING_RESULTS_GRADES_ASSESSMENTS_LABEL_KEY), WITH_DIVIDER);

        addField(gradesAssessments, COURSE + "/" + GRADING_OPTIONS, generateMessageInfo(LUConstants.LEARNING_RESULT_ASSESSMENT_SCALE_LABEL_KEY));

        return gradesAssessments;
    }

    protected VerticalSection generateCourseFormatsSection() {
        //COURSE FORMATS
        VerticalSection courseFormats = initSection(getH3Title(LUConstants.FORMATS_LABEL_KEY), WITH_DIVIDER);
        courseFormats.setHelp(getLabel(LUConstants.FORMATS_LABEL_KEY + "-help"));
        courseFormats.setInstructions(getLabel(LUConstants.FORMATS_LABEL_KEY + "-instruct"));
        
        MultiplicityConfiguration courseFormatConfig = setupMultiplicityConfig(
                MultiplicityConfiguration.MultiplicityType.GROUP,
                MultiplicityConfiguration.StyleType.TOP_LEVEL_GROUP,
                COURSE + "/" + FORMATS, LUConstants.COURSE_ADD_FORMAT_LABEL_KEY,
                LUConstants.FORMAT_LABEL_KEY,
                null, null, null);
        MultiplicityConfiguration activitiesConfig = setupMultiplicityConfig(
                MultiplicityConfiguration.MultiplicityType.GROUP,
                MultiplicityConfiguration.StyleType.SUB_LEVEL_GROUP,
                COURSE + "/" + FORMATS + "/*/" + ACTIVITIES, 
                LUConstants.ADD_ACTIVITY_LABEL_KEY,
                LUConstants.ACTIVITY_LITERAL_LABEL_KEY,
                Arrays.asList(
                        new MultiplicityFieldConfig(
                                ACTIVITY_TYPE,
                                LUConstants.ACTIVITY_TYPE_LABEL_KEY,
                                null,
                                null,
                                true),
                        new MultiplicityFieldConfig(
                                CONTACT_HOURS + "/" + "unitQuantity",
                                LUConstants.CONTACT_HOURS_LABEL_KEY,
                                null,
                                null,
                                false),
                        new MultiplicityFieldConfig(
                                CONTACT_HOURS + "/" + "unitType",
                                LUConstants.CONTACT_HOURS_FREQUENCY_LABEL_KEY,
                                null,
                                null,
                                true),
                        new MultiplicityFieldConfig(
                                CreditCourseActivityConstants.DURATION + "/" + "atpDurationTypeKey",
                                LUConstants.DURATION_TYPE_LABEL_KEY,
                                null,
                                null,
                                false),
                        new MultiplicityFieldConfig(
                                CreditCourseActivityConstants.DURATION + "/" + "timeQuantity",
                                LUConstants.DURATION_LITERAL_LABEL_KEY,
                                null,
                                null,
                                true),
                        new MultiplicityFieldConfig(
                                DEFAULT_ENROLLMENT_ESTIMATE,
                                LUConstants.CLASS_SIZE_LABEL_KEY,
                                null,
                                null,
                                true)
                ), null, null);
        courseFormatConfig.setNestedConfig(activitiesConfig);

        MultiplicitySection ms = null;
        ms = new MultiplicitySection(courseFormatConfig, 
                null, null);
        courseFormats.addSection(ms);
        
        return courseFormats;
    }

    protected VerticalSection generateSchedulingSection() {
        VerticalSection scheduling = initSection(getH3Title(LUConstants.SCHEDULING_LABEL_KEY), WITH_DIVIDER);
        addField(scheduling, COURSE + "/" + TERMS_OFFERED, generateMessageInfo(LUConstants.TERMS_OFFERED_LABEL_KEY));
        return scheduling;
    }

    protected VerticalSection generateDurationSection() {
        VerticalSection duration = initSection(getH3Title(LUConstants.DURATION_LITERAL_LABEL_KEY), WITH_DIVIDER);
        duration.setInstructions(getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY + "-instruct"));
        GroupSection duration_group = new GroupSection();
        addField(duration_group, COURSE + "/" + CreditCourseConstants.DURATION + "/" + "atpDurationTypeKey", generateMessageInfo(LUConstants.DURATION_TYPE_LABEL_KEY));
        addField(duration_group, COURSE + "/" + CreditCourseConstants.DURATION + "/" + "timeQuantity", generateMessageInfo(LUConstants.DURATION_QUANTITY_LABEL_KEY));

        duration.addSection(duration_group);
        return duration;
    }

    protected VerticalSection generateFinalExamSection() {
        VerticalSection finalExam = initSection(getH3Title(LUConstants.FINAL_EXAM_LABEL_KEY), WITH_DIVIDER);
        GroupSection finalExam_group = new GroupSection();
        GroupSection finalExamRationale_group = new GroupSection();
        GroupSection finalExamRationale_group2 = new GroupSection();

        FieldDescriptor field = addField(finalExam_group, COURSE + "/" + CreditCourseConstants.FINAL_EXAM, generateMessageInfo(LUConstants.FINAL_EXAM_STATUS_LABEL_KEY));
        KSSelectItemWidgetAbstract picker = (KSSelectItemWidgetAbstract) (((KSPicker) field.getFieldWidget()).getInputWidget());
        addField(finalExamRationale_group, COURSE + "/" + CreditCourseConstants.FINAL_EXAM_RATIONALE, generateMessageInfo(LUConstants.FINAL_EXAM_RATIONALE_LABEL_KEY));
        addField(finalExamRationale_group2, COURSE + "/" + CreditCourseConstants.FINAL_EXAM_RATIONALE, generateMessageInfo(LUConstants.FINAL_EXAM_RATIONALE_LABEL_KEY));
        SwapSection swapSection = new SwapSection(picker);
        swapSection.addSection(finalExamRationale_group, "ALT");
        swapSection.addSection(finalExamRationale_group2, "None");
        finalExam.addSection(finalExam_group);

        finalExam.addSection(swapSection);
        return finalExam;
    }

    protected VerticalSection generateInstructorsSection() {
        VerticalSection instructors = initSection(getH3Title(LUConstants.INSTRUCTOR_LABEL_KEY), WITH_DIVIDER);
        addField(instructors, COURSE + "/" + PRIMARY_INSTRUCTOR + "/personId");
        return instructors;
    }

    protected SectionView generateLearningObjectivesSection() {
        VerticalSectionView section = initSectionView(CourseSections.LEARNING_OBJECTIVES, LUConstants.LEARNING_OBJECTIVES_LABEL_KEY);
        section.addSection(generateLearningObjectivesNestedSection());
        return section;
    }

    protected VerticalSection generateLearningObjectivesNestedSection() {
        VerticalSection los = initSection(null, NO_DIVIDER);

//        QueryPath path = QueryPath.concat(null, COURSE + "/" + COURSE_SPECIFIC_LOS + "/" + "*" + "/" + CreditCourseCourseSpecificLOsConstants.INCLUDED_SINGLE_USE_LO + "/" + "description");
        QueryPath path = QueryPath.concat(COURSE, COURSE_SPECIFIC_LOS, "*", "loInfo", "desc");
        Metadata meta = modelDefinition.getMetadata(path);

        // FIXME [KSCOR-225]  where should repo key come from?
        FieldDescriptor fd = addField(los,
                CreditCourseConstants.COURSE_SPECIFIC_LOS,
                null,
                new LOBuilder(type, state, groupName, "kuali.loRepository.key.singleUse", meta),
                COURSE);

        // have to do this here, because decision on binding is done in ks-core,
        // and we obviously don't want ks-core referring to LOBuilder
        fd.setWidgetBinding(LOBuilderBinding.INSTANCE);

        los.addStyleName("KS-LUM-Section-Divider");
        return los;
    }

    public class PersonList extends KSDropDown {
        final SimpleListItems people = new SimpleListItems();

        public PersonList() {
            final PersonList us = this;
            final String userId = Application.getApplicationContext().getUserId();

            //FIXME: [KSCOR-225] Commented out search code to display drop down with only current user, and disable select
            people.addItem(userId, userId);
            us.setListItems(people);
            us.selectItem(userId);
            us.setBlankFirstItem(false);
            this.setEnabled(false);

            /*
                SearchRpcServiceAsync searchRpcServiceAsync = GWT.create(SearchRpcService.class);
                SearchRequest searchRequest = new SearchRequest();
                searchRequest.setSearchKey("person.search.personQuickViewByGivenName");
                searchRequest.setSortColumn("person.resultColumn.GivenName");
                searchRequest.setSortDirection(SortDirection.ASC);
                searchRpcServiceAsync.search(searchRequest, new KSAsyncCallback<SearchResult>() {

                    @Override
                    public void onSuccess(SearchResult result) {
                        for (SearchResultRow r : result.getRows()) {
                            people.addItem(r.getCells().get(0).getValue(), r.getCells().get(1).getValue());
                        }
                        us.setListItems(people);
                        us.selectItem(userId);
                    }

                    @Override
                    public void handleFailure(Throwable caught) {
                        Window.alert("Unable to contact the SearchService for the list of users");
                        people.addItem(userId, userId);
                        us.setListItems(people);
                        us.selectItem(userId);
                    }
                });
             */
        }

        public boolean isMultipleSelect() {
            return true;
        }
    }

    public class ProposerPersonList extends KSLabelList {
        public ProposerPersonList() {
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }

    public SectionView generateProgramInfoSection() {
        VerticalSectionView section = initSectionView(CourseSections.PROGRAM_INFO, LUConstants.INFORMATION_LABEL_KEY);
        section.addSection(generateShortTitleSection());
        return section;
    }

    protected VerticalSection generateShortTitleSection() {
        VerticalSection shortTitle = initSection(getH3Title(LUConstants.SHORT_TITLE_LABEL_KEY), WITH_DIVIDER);
        addField(shortTitle, "cluInfo/officialIdentifier/shortName", null);
        return shortTitle;
    }

    protected VerticalSectionView initSectionView(Enum<?> viewEnum, String labelKey) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), CLU_PROPOSAL_MODEL);
        section.addStyleName(LUConstants.STYLE_SECTION);
        return section;
    }


    protected VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section;
        if (title != null) {
            section = new VerticalSection(title);
        } else {
            section = new VerticalSection();
        }
        section.addStyleName(LUConstants.STYLE_SECTION);
        if (withDivider)
            section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        return section;
    }

    protected MessageKeyInfo generateMessageInfo(String labelKey) {
        return new MessageKeyInfo(groupName, type, state, labelKey);
    }

    protected String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(groupName, type, state, labelKey);
    }

    protected SectionTitle getH1Title(String labelKey) {
        return SectionTitle.generateH1Title(getLabel(labelKey));
    }

    protected SectionTitle getH2Title(String labelKey) {
        return SectionTitle.generateH2Title(getLabel(labelKey));
    }

    protected SectionTitle getH3Title(String labelKey) {
        return SectionTitle.generateH3Title(getLabel(labelKey));
    }

    protected SectionTitle getH4Title(String labelKey) {
        return SectionTitle.generateH4Title(getLabel(labelKey));
    }

    protected SectionTitle getH5Title(String labelKey) {
        return SectionTitle.generateH5Title(getLabel(labelKey));
    }

    // TODO - when DOL is pushed farther down into LOBuilder,
    // revert these 5 methods to returning void again.

    public FieldDescriptor addField(Section section, String fieldKey) {
        return addField(section, fieldKey, null, null, null);
    }

    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey) {
        return addField(section, fieldKey, messageKey, null, null);
    }

    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
        return addField(section, fieldKey, messageKey, widget, null);
    }

    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, String parentPath) {
        return addField(section, fieldKey, messageKey, null, parentPath);
    }

    public FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        FieldDescriptor fd = new FieldDescriptor(path.toString(), messageKey, meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        section.addField(fd);
        return fd;
    }

    protected SectionView generateFinancialsSection() {
        VerticalSectionView section = initSectionView(CourseSections.FINANCIALS, LUConstants.FINANCIALS_LABEL_KEY);

        VerticalSection justiFee = initSection(getH3Title(LUConstants.COURSE_FEE_TITLE), WITH_DIVIDER);

//        addField(description, COURSE + "/" + PROPOSAL_DESCRIPTION + "/" + RichTextInfoConstants.PLAIN, generateMessageInfo(LUConstants.DESCRIPTION_LABEL_KEY));

        addField(justiFee, COURSE + "/" + "feeJustification" + "/" + RichTextInfoConstants.PLAIN,  generateMessageInfo(LUConstants.JUSTIFICATION_FEE));
        section.addSection(justiFee);
        Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> swappableFieldsDefinition =
            new HashMap<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>>();
        
        // condition: 
        //    if rateType field is Variable Rate Fee
        //    if rateType field is Fixed Rate Fee
        //    if rateType field is Multiple Rate Fee
        //    if rateType field is Per Credit Fee
//        String feesPathString = COURSE + QueryPath.getPathSeparator() + FEES;
        QueryPath feesPath = QueryPath.concat(COURSE, FEES);
        QueryPath rateTypeFieldPath = QueryPath.concat(feesPath.toString(), QueryPath.getWildCard(), "rateType");
//        fees/*/feeAmounts/currencyQuantity
        QueryPath deletionPath = QueryPath.concat(feesPath.toString(), QueryPath.getWildCard(), "feeAmounts");
        QueryPath singularFeeAmountFieldPath = QueryPath.concat(feesPath.toString(), QueryPath.getWildCard(), "feeAmounts", "0", "currencyQuantity"); 
        QueryPath minFeeAmountFieldPath = QueryPath.concat(feesPath.toString(), QueryPath.getWildCard(), "feeAmounts", "0", "currencyQuantity"); 
        QueryPath maxFeeAmountFieldPath = QueryPath.concat(feesPath.toString(), QueryPath.getWildCard(), "feeAmounts", "1", "currencyQuantity"); 
        Metadata feeAmountFieldMeta = modelDefinition.getMetadata(singularFeeAmountFieldPath);
        
        SwapCompositeCondition variableRateCondition = new SwapCompositeCondition(
                CompositeConditionOperator.AND);
        variableRateCondition.getChildrenConditions().add(
                makeCondition(rateTypeFieldPath, "Rate Type", "variableRateFee")
        );
        variableRateCondition.setConditionId("0");
        
        SwapCompositeCondition fixedRateCondition = new SwapCompositeCondition(
                CompositeConditionOperator.AND);
        fixedRateCondition.getChildrenConditions().add(
                makeCondition(rateTypeFieldPath, "Rate Type", "fixedRateFee")
        );
        fixedRateCondition.setConditionId("1");

        SwapCompositeCondition perCreditRateCondition = new SwapCompositeCondition(
                CompositeConditionOperator.AND);
        perCreditRateCondition.getChildrenConditions().add(
                makeCondition(rateTypeFieldPath, "Rate Type", "perCreditFee")
        );
        perCreditRateCondition.setConditionId("2");

        SwapCompositeCondition multipleRateCondition = new SwapCompositeCondition(
                CompositeConditionOperator.AND);
        multipleRateCondition.getChildrenConditions().add(
                makeCondition(rateTypeFieldPath, "Rate Type", "multipleRateFee")
        );
        multipleRateCondition.setConditionId("3");

        swappableFieldsDefinition.put(variableRateCondition,
                Arrays.asList(
                        new SwapCompositeConditionFieldConfig(
                                new MultiplicityFieldConfiguration(
                                        minFeeAmountFieldPath.toString(), 
                                        new MessageKeyInfo("Mininum Amount"), feeAmountFieldMeta,
                                        null),
                                null
                        ),
                        new SwapCompositeConditionFieldConfig(
                                new MultiplicityFieldConfiguration(
                                        maxFeeAmountFieldPath.toString(), 
                                        new MessageKeyInfo("Maximum Amount"), feeAmountFieldMeta,
                                        null),
                                null
                        ))
        );
        
        swappableFieldsDefinition.put(fixedRateCondition,
                Arrays.asList(
                        new SwapCompositeConditionFieldConfig(
                                new MultiplicityFieldConfiguration(
                                        singularFeeAmountFieldPath.toString(), 
                                        new MessageKeyInfo("Amount"), feeAmountFieldMeta,
                                        null), 
                                null))
        );

        swappableFieldsDefinition.put(perCreditRateCondition,
                Arrays.asList(
                        new SwapCompositeConditionFieldConfig(
                                new MultiplicityFieldConfiguration(
                                        singularFeeAmountFieldPath.toString(), 
                                        new MessageKeyInfo("Amount"), feeAmountFieldMeta,
                                        null),
                                null))
        );
        
        MultiplicityConfiguration multipleFeesConfig = setupMultiplicityConfig(
                MultiplicityConfiguration.MultiplicityType.GROUP,
                MultiplicityConfiguration.StyleType.BORDERLESS_TABLE,
                COURSE + QueryPath.getPathSeparator() + FEES + QueryPath.getPathSeparator() + 
                    QueryPath.getWildCard() + QueryPath.getPathSeparator() + "feeAmounts",
                LUConstants.ADD_ANOTHER_FEE,
                LUConstants.FEE,
                Arrays.asList(
                        new MultiplicityFieldConfig(
                                "currencyQuantity", 
                                "Amount", null, null, true)),
                null,
                null);
        swappableFieldsDefinition.put(multipleRateCondition,
                Arrays.asList(
                        new SwapCompositeConditionFieldConfig(
                                null, multipleFeesConfig
                                ))
                );

        addFeeMultiplicityFields(justiFee, 
                COURSE + QueryPath.getPathSeparator() + FEES,
                LUConstants.ADD_A_FEE,
                LUConstants.FEE,
                Arrays.asList(
                        new MultiplicityFieldConfig(
                                "feeType", 
                                "Fee Type", null, null, true),
                        new MultiplicityFieldConfig(
                                "rateType", 
                                "Rate Type", null, null, true)),
                swappableFieldsDefinition,
                Arrays.asList(
                        deletionPath.toString()));
        
        section.addSection(justiFee);
        
        
        VerticalSection financialSection = initSection(getH3Title(LUConstants.FINANCIAL_INFORMATION), WITH_DIVIDER);
        setupRevenueSection(financialSection);
        setupExpenditureSection(financialSection);
        section.addSection(financialSection);

        return section;
    }
    
    protected void setupRevenueSection(Section parentSection) {
        // TODO customize multiplicity and change "Percentage" label into LUConstants.AMOUNT
        QueryPath revenuePath = QueryPath.concat(COURSE, "revenues");
        QueryPath affiliatedOrgIdSubPath = QueryPath.concat("affiliatedOrgs", "0", "orgId");
        QueryPath percentageSubPath = QueryPath.concat("affiliatedOrgs", "0", "percentage");
        addMultiplicityFields(parentSection, 
                revenuePath.toString(), 
                LUConstants.ADD_ANOTHER_ORGANIZATION, 
                LUConstants.REVENUE,
                Arrays.asList(
                        new MultiplicityFieldConfig(
                                affiliatedOrgIdSubPath.toString(), 
                                LUConstants.REVENUE, null, null, true),
                        new MultiplicityFieldConfig(
                                percentageSubPath.toString(), 
                                "Percentage", null, null, true)                                
                ),
                null,
                null
        );
    }
    
    protected void setupExpenditureSection(Section parentSection) {
        // TODO customize multiplicity and change "Percentage" label into LUConstants.AMOUNT
        QueryPath expenditureAffiliatedOrgPath = QueryPath.concat(COURSE, "expenditure", "affiliatedOrgs");
        QueryPath affiliatedOrgIdSubPath = QueryPath.concat("orgId");
        QueryPath percentageSubPath = QueryPath.concat("percentage");
        addMultiplicityFields(parentSection, 
                expenditureAffiliatedOrgPath.toString(), 
                LUConstants.ADD_ANOTHER_ORGANIZATION, 
                LUConstants.EXPENDITURE,
                Arrays.asList(
                        new MultiplicityFieldConfig(
                                affiliatedOrgIdSubPath.toString(), 
                                LUConstants.EXPENDITURE, null, null, true),
                        new MultiplicityFieldConfig(
                                percentageSubPath.toString(), 
                                "Percentage", null, null, true)                                
                ),
                null,
                null
        );
    }
    
    protected SwapCondition makeCondition(QueryPath fieldPath, String messageLabelKey, 
            String value) {
        SwapCondition swapCondition = new SwapCondition();
        swapCondition.setFd(new FieldDescriptor(
                fieldPath.toString(), 
                new MessageKeyInfo(messageLabelKey),
                modelDefinition.getMetadata(fieldPath)));
        swapCondition.setValue(value);
        return swapCondition;
    }


    @Override
    public String getCourseTitlePath() {
        return COURSE_TITLE_PATH;
    }

    @Override
    public String getProposalPath() {
        return PROPOSAL_PATH;
    }

    @Override
    public String getProposalTitlePath() {
        return PROPOSAL_TITLE_PATH;
    }

    @Override
    public Class<? extends Enum<?>> getViewsEnum() {
        return CourseConfigurer.CourseSections.class;
    }


    @Override
    public String getSectionTitle(DataModel model) {

        StringBuffer sb = new StringBuffer();
        sb.append("Modify Course: ");
        sb.append(model.get("courseCode"));
        sb.append(" - ");
        sb.append(model.get("transcriptTitle"));

        return sb.toString();
    }

    @Override
    public String getProposalHeaderTitle(DataModel model) {
        StringBuffer sb = new StringBuffer();
        if (model.get("copyOfCourseId") != null) {
            sb.append("Modify Course: ");
            sb.append(model.get("courseCode"));
            sb.append(" - ");
            sb.append(model.get("transcriptTitle"));
        } else {
            sb.append("New Course: ");
            sb.append(model.get(getCourseTitlePath()));
        }

        return sb.toString();
    }
}


class KeyListModelWigetBinding extends ModelWidgetBindingSupport<HasDataValue> {
    protected String key;
    HasDataValueBinding hasDataValueBinding = HasDataValueBinding.INSTANCE;

    public KeyListModelWigetBinding(String key) {
        this.key = key;
    }

    @Override
    public void setModelValue(HasDataValue widget, DataModel model, String path) {
        // convert from the structure path/0/<id> into path/0/<key>/<id>
        hasDataValueBinding.setModelValue(widget, model, path);

        QueryPath qPath = QueryPath.parse(path);
        Value value = ((KSSelectedList) widget).getValueWithTranslations();

        Data idsData = null;
        Data idsDataStruct = null;

        if (value != null) {
            idsData = value.get();
        }
        if (idsData != null) {
            for (Data.Property p : idsData) {
                if (!"_runtimeData".equals(p.getKey())) {
                    String id = p.getValue();
                    // old translation path path/_runtimeData/0/id-translation
                    QueryPath translationPath = new QueryPath();
                    translationPath.add(new Data.StringKey(qPath.toString()));
                    translationPath.add(new Data.StringKey("_runtimeData"));
                    translationPath.add(new Data.IntegerKey((Integer) p.getKey()));
                    translationPath.add(new Data.StringKey("id-translation"));

                    Data idItem = new Data();
                    String translation = model.get(translationPath.toString());
                    Data idItemRuntime = new Data();
                    Data idItemTranslation = new Data();
                    idsDataStruct = (idsDataStruct == null) ? new Data() : idsDataStruct;
                    idItem.set(this.key, id);
                    // new translation path/0/_runtimeData/<key>/id-translation
                    idItemTranslation.set("id-translation", translation);
                    idItemRuntime.set(this.key, idItemTranslation);
                    idItem.set("_runtimeData", idItemRuntime);
                    idsDataStruct.add(idItem);
                }
            }
        }

        model.set(qPath, idsDataStruct);
    }

    @Override
    public void setWidgetValue(HasDataValue widget, DataModel model, String path) {
        DataModel middleManModel = new DataModel();
        if (model != null && model.getRoot() != null) {
            middleManModel = new DataModel(model.getDefinition(), model.getRoot().copy());
        }
        // convert from the structure path/0/<key>/<id> into path/0/<id>
        QueryPath qPath = QueryPath.parse(path);
        Object value = null;
        Data idsData = null;
        Data newIdsData = null;
        Data newIdsRuntimeData = null;

        if (middleManModel != null) {
            value = middleManModel.get(qPath);
        }

        if (value != null) {
            idsData = (Data) value;
            if (idsData != null) {
                for (Data.Property p : idsData) {
                    if (!"_runtimeData".equals(p.getKey())) {
                        Data idItem = p.getValue();
                        String id = idItem.get(key);
                        Data runtimeData = idItem.get("_runtimeData");
                        Data translationData = runtimeData.get(key);
                        newIdsData = (newIdsData == null) ? new Data() : newIdsData;
                        newIdsData.add(id);
                        newIdsRuntimeData = (newIdsRuntimeData == null) ? new Data() : newIdsRuntimeData;
                        newIdsRuntimeData.add(translationData);
                    }
                }
            }
        }
        if (newIdsData != null) {
            newIdsData.set("_runtimeData", newIdsRuntimeData);
            middleManModel.set(qPath, newIdsData);
            hasDataValueBinding.setWidgetValue(widget, middleManModel, path);
        }
    }
}


class MultiplicityFieldConfig {
    protected String fieldKey;
    protected String labelKey;
    boolean nextLine;
    
    public MultiplicityFieldConfig() {
    }
    public MultiplicityFieldConfig(String fieldKey, String labelKey,
            Widget fieldWidget, ModelWidgetBinding modelWidgetBinding, boolean nextLine) {
        setFieldKey(fieldKey);
        setLabelKey(labelKey);
        setNextLine(nextLine);
    }
    public String getFieldKey() {
        return fieldKey;
    }
    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }
    public String getLabelKey() {
        return labelKey;
    }
    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }
    public boolean isNextLine() {
        return nextLine;
    }
    public void setNextLine(boolean nextLine) {
        this.nextLine = nextLine;
    }
}


