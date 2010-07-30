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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.HasDataValueBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.UpdatableMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.sections.CollapsableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.RemovableItemWithHeader;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.commenttool.CommentPanel;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectedList;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowEnhancedController;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.AffiliatedOrgInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseCourseSpecificLOsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseJointsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.removeinm4.LOBuilderBinding;
import org.kuali.student.lum.lu.ui.course.client.views.CourseRequisitesSectionView;
import org.kuali.student.lum.lu.ui.course.client.widgets.CollaboratorTool;
import org.kuali.student.lum.lu.ui.course.client.widgets.FeeMultiplicity;
import org.kuali.student.lum.lu.ui.course.client.widgets.LOBuilder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;


/**
 * This is the configuration factory class for creating a proposal.
 *
 * @author Kuali Student Team
 *
 */
public class CourseConfigurer extends AbstractCourseConfigurer {
    protected String groupName;

    protected String type = "course";
    protected String state = "draft";

    protected boolean WITH_DIVIDER = true;
    protected boolean NO_DIVIDER = false;

    public static final String WORKFLOW_DOC_TYPE					= "CluCreditCourseProposal";
    public static final String PROPOSAL_ID_PATH                     = "/proposalId";
    public static final String PROPOSAL_TITLE_PATH                  = "/courseTitle";
    public static final String COURSE_TITLE_PATH                    = "/courseTitle";

    //Override paths for course and proposal so they are root
    public static final String PROPOSAL = "";
    public static final String COURSE = "";

    public enum CourseSections{
        CLU_BEGIN, PEOPLE_PERMISSOMS, SUMMARY, AUTHORS_RATIONALE, GOVERNANCE, COURSE_LOGISTICS, COURSE_INFO, LEARNING_OBJECTIVES,
        COURSE_REQUISITES, ACTIVE_DATES, FINANCIALS, ATTACHMENTS, COMMENTS, DOCUMENTS,
        PROGRAM_INFO, ASSEMBLER_TEST
    }

    protected DataModelDefinition modelDefinition;

    public void setModelDefinition(DataModelDefinition modelDefinition){
        this.modelDefinition = modelDefinition;
    }

    @Override
    public void configure(final WorkflowEnhancedController layout) {
        groupName = LUConstants.COURSE_GROUP_NAME;

        groupName = LUConstants.COURSE_GROUP_NAME;

        addCluStartSection(layout);

        if(modelDefinition.getMetadata().isCanEdit()) {
                String sections = getLabel(LUConstants.COURSE_SECTIONS);

            //ProposalInformation
            //layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.PROPOSAL_INFORMATION_LABEL_KEY)}, generateAuthorsRationaleSection());

                layout.addMenu(sections);

            //Course Content
                layout.addMenuItem(sections, generateCourseInfoSection());
                layout.addMenuItem(sections, generateCourseLogisticsSection());
                layout.addMenuItem(sections, generateLearningObjectivesSection());

            //Student Eligibility
                layout.addMenuItem(sections, generateCourseRequisitesSection());

            //Administrative
                layout.addMenuItem(sections, generateGovernanceSection());
                layout.addMenuItem(sections, generateActiveDatesSection());
                layout.addMenuItem(sections, generateFinancialsSection());
        }
            //Summary
            ViewCourseProposalSummaryConfigurer summaryConfigurer = new ViewCourseProposalSummaryConfigurer(type, state, groupName, modelDefinition);
            layout.addSpecialMenuItem(summaryConfigurer.generateSummarySection(layout.getWfUtilities()), "Review and Submit");

            //Tools
            String tools = "Tools";
            layout.addMenu(tools);
            layout.addMenuItem(tools, new CollaboratorTool(CourseSections.PEOPLE_PERMISSOMS, LUConstants.SECTION_AUTHORS_AND_COLLABORATORS,
                WORKFLOW_DOC_TYPE, getH2Title(LUConstants.SECTION_AUTHORS_AND_COLLABORATORS)));
            layout.addMenuItem(tools, new DocumentTool(CourseSections.DOCUMENTS, getLabel(LUConstants.TOOL_DOCUMENTS_LABEL_KEY)));

            layout.addContentWidget(layout.getWfUtilities().getWorkflowStatusLabel());
            layout.addView(new CommentPanel(CourseSections.COMMENTS, getLabel(LUConstants.TOOL_COMMENTS_LABEL_KEY)));
            layout.addContentWidget(new KSButton("Comments", ButtonStyle.DEFAULT_ANCHOR, new ClickHandler(){

                @Override
                public void onClick(ClickEvent event) {
                    layout.showView(CourseSections.COMMENTS);

                }
            }));

//      addCluStartSection(layout);
//
//      if(modelDefinition.getMetadata().isCanEdit()) {
//          String editTabLabel = getLabel(LUConstants.EDIT_TAB_LABEL_KEY);
//
//          //ProposalInformation
//          layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.PROPOSAL_INFORMATION_LABEL_KEY)}, generateAuthorsRationaleSection());
//
//          //Course Content
//          layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateCourseInfoSection());
//
//          layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateCourseLogisticsSection());
//          /*
//              layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateLearningObjectivesSection());
//            */
//
//          //Student Eligibility
//          layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.STUDENT_ELIGIBILITY_LABEL_KEY)}, generateCourseRequisitesSection());
//
//              //Administrative
//              layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateGovernanceSection());
//              layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateActiveDatesSection());
//              //layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateFinancialsSection());
//      }
//      //Review Proposal Tab
//      ViewCourseProposalSummaryConfigurer summaryConfigurer = new ViewCourseProposalSummaryConfigurer(type, state, groupName, modelDefinition);
//      layout.addSection(new String[] {getLabel(LUConstants.SUMMARY_LABEL_KEY)}, summaryConfigurer.generateSummarySection());
//
//      //Tool Tabs
//          layout.addTool(new CollaboratorTool(CourseSections.PEOPLE_PERMISSOMS, LUConstants.SECTION_AUTHORS_AND_COLLABORATORS,
//              getH2Title(LUConstants.SECTION_AUTHORS_AND_COLLABORATORS)));
//          layout.addTool(new CommentPanel(CourseSections.COMMENTS, getLabel(LUConstants.TOOL_COMMENTS_LABEL_KEY)));
//          layout.addTool(new DocumentTool(CourseSections.DOCUMENTS, getLabel(LUConstants.TOOL_DOCUMENTS_LABEL_KEY)));
    }

    public void addCluStartSection(WorkflowEnhancedController layout){
        VerticalSectionView section = initSectionView(CourseSections.CLU_BEGIN, LUConstants.START_LABEL_KEY);

        addField(section, COURSE_TITLE , generateMessageInfo(LUConstants.PROPOSAL_TITLE_LABEL_KEY));
        //addField(section, PROPOSAL + "/" + PROPOSER_PERSON, generateMessageInfo(LUConstants.PROPOSAL_PERSON_LABEL_KEY), new PersonList()) ;
        layout.addStartViewPopup(section);
    }


    /**
     * Adds a section for adding or modifying rules associated with a given course (program).
     *
     * @param layout - a content pane to which this section is added to
     * @return
     */
    protected SectionView generateCourseRequisitesSection() {

        List<Metadata> searchMetadata = new ArrayList<Metadata>();

        searchMetadata.add(modelDefinition.getMetadata(QueryPath.concat(null, "findCourse")));
        searchMetadata.add(modelDefinition.getMetadata(QueryPath.concat(null, "findProgram")));

        CourseRequisitesSectionView section = new CourseRequisitesSectionView(CourseSections.COURSE_REQUISITES, getLabel(LUConstants.REQUISITES_LABEL_KEY), CLU_PROPOSAL_MODEL, searchMetadata);
        //Setting the section title after initializing the widget won't do anything
        section.getLayout().setLayoutTitle(SectionTitle.generateH1Title(getLabel(LUConstants.REQUISITES_LABEL_KEY)));
        return section;
    }

    protected SectionView generateActiveDatesSection() {
        VerticalSectionView section = initSectionView(CourseSections.ACTIVE_DATES, LUConstants.ACTIVE_DATES_LABEL_KEY);

        addField(section, COURSE + "/" + START_TERM, generateMessageInfo(LUConstants.START_TERM_LABEL_KEY));
        addField(section, COURSE + "/" + END_TERM, generateMessageInfo(LUConstants.END_TERM_LABEL_KEY));
        addField(section, COURSE + "/" + PILOT_COURSE, generateMessageInfo(LUConstants.PILOT_COURSE_LABEL_KEY), new KSCheckBox("Yes, this is a one-time/pilot course"));
        
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

    protected SectionView generateGovernanceSection(){
        VerticalSectionView section = initSectionView(CourseSections.GOVERNANCE, LUConstants.GOVERNANCE_LABEL_KEY);

        addField(section, COURSE + "/" + CURRICULUM_OVERSIGHT_ORGS_, generateMessageInfo(LUConstants.ACADEMIC_SUBJECT_ORGS_KEY));
        addField(section, COURSE + "/" + CAMPUS_LOCATIONS, generateMessageInfo(LUConstants.CAMPUS_LOCATION_LABEL_KEY));
        addField(section, COURSE + "/" + ADMIN_ORGS, generateMessageInfo(LUConstants.ADMIN_ORG_LABEL_KEY));

        return section;
    }

    public SectionView generateCourseInfoSection(){
        VerticalSectionView section = initSectionView(CourseSections.COURSE_INFO, LUConstants.INFORMATION_LABEL_KEY);
        addField(section, PROPOSAL_TITLE, generateMessageInfo(LUConstants.PROPOSAL_TITLE_LABEL_KEY));
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
        GroupSection courseNumber = new GroupSection(getH3Title(LUConstants.IDENTIFIER_LABEL_KEY));
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
                        Arrays.asList(SUBJECT_AREA, LUConstants.SUBJECT_CODE_LABEL_KEY),
                        Arrays.asList(COURSE_NUMBER_SUFFIX, LUConstants.COURSE_NUMBER_LABEL_KEY)));
        addMultiplicityFields(result, COURSE + QueryPath.getPathSeparator() + JOINTS,
                LUConstants.ADD_EXISTING_LABEL_KEY,
                LUConstants.JOINT_OFFER_ITEM_LABEL_KEY,
                Arrays.asList(
                        Arrays.asList(CreditCourseJointsConstants.COURSE_ID, LUConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY)));
        addMultiplicityFields(result, COURSE + QueryPath.getPathSeparator() + VERSIONS,
                LUConstants.ADD_VERSION_CODE_LABEL_KEY,
                LUConstants.VERSION_CODE_LABEL_KEY,
                Arrays.asList(
                        Arrays.asList("variationCode", LUConstants.VERSION_CODE_LABEL_KEY),
                        Arrays.asList("variationTitle", LUConstants.TITLE_LABEL_KEY)));
        return result;
    }

    private void addMultiplicityFields(Section section, String path, String addItemlabelMessageKey,
            String itemLabelMessageKey, List<List<String>> fieldKeysAndLabels) {
        QueryPath parentPath = QueryPath.concat(path);

        MultiplicityConfiguration config = new MultiplicityConfiguration(MultiplicityConfiguration.MultiplicityType.GROUP,
                MultiplicityConfiguration.StyleType.TOP_LEVEL, getMetaData(parentPath.toString()));
        config.setAddItemLabel(getLabel(addItemlabelMessageKey));
        config.setItemLabel(getLabel(itemLabelMessageKey));
        config.setUpdateable(true);

        FieldDescriptor parentFd = buildFieldDescriptor(path, getLabel(itemLabelMessageKey), null);
        config.setParentFd(parentFd);

        if (fieldKeysAndLabels != null) {
            for (List<String> fieldKeyAndLabel : fieldKeysAndLabels) {
                FieldDescriptor fd = buildMultiplicityFD(fieldKeyAndLabel.get(0),
                        fieldKeyAndLabel.get(1), parentPath.toString());
                config.addField(fd);
            }
        }
        MultiplicitySection ms = new MultiplicitySection(config);
        section.addSection(ms);

    }

    private Metadata getMetaData(String fieldKey) {
        return modelDefinition.getMetadata(QueryPath.concat(fieldKey));
    }

    private FieldDescriptor buildMultiplicityFD( String fieldKey, String labelKey, String parentPath) {

        QueryPath path = QueryPath.concat(parentPath);
        int i = path.size();

        QueryPath fieldPath = QueryPath.concat(parentPath, QueryPath.getWildCard(), fieldKey);
        Metadata meta = modelDefinition.getMetadata(fieldPath);

        FieldDescriptor fd = new FieldDescriptor(fieldPath.toString(), generateMessageInfo(labelKey), meta);

        return fd;

    }

    private FieldDescriptor buildFieldDescriptor(String fieldKey, String messageKey,String parentPath) {
        return buildFieldDescriptor(fieldKey, messageKey, parentPath, null, null);
    }

    private FieldDescriptor buildFieldDescriptor(String fieldKey, String messageKey, String parentPath, Widget widget, ModelWidgetBinding<?> binding) {

        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        FieldDescriptor fd = new FieldDescriptor(path.toString(), generateMessageInfo(messageKey), meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        if (binding != null) {
            fd.setWidgetBinding(binding);
        }
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
        SectionTitle title = getH1Title(LUConstants.PROPOSAL_TITLE_SECTION_LABEL_KEY);
        VerticalSection description = initSection(title, !WITH_DIVIDER);
        title.setStyleName("cluProposalTitleSection");
        //FIXME [KSCOR-225] Temporary fix til we have a real rich text editor
        //addField(description, COURSE + "/" + DESCRIPTION, null);
        addField(description, COURSE + "/" + PROPOSAL_DESCRIPTION + "/" + RichTextInfoConstants.PLAIN, generateMessageInfo(LUConstants.DESCRIPTION_LABEL_KEY));
        addField(description, "proposalRationale", generateMessageInfo(LUConstants.PROPOSAL_RATIONALE_LABEL_KEY));
        return description;
    }

    public SectionView generateCourseLogisticsSection() {
        VerticalSectionView section = initSectionView(CourseSections.COURSE_LOGISTICS, LUConstants.LOGISTICS_LABEL_KEY);

        section.addSection(generateSchedulingSection());
        section.addSection(generateDurationSection());
        section.addSection(generateCourseFormatsSection());


        return section;
    }

    protected VerticalSection generateCourseFormatsSection() {
        //COURSE FORMATS
        VerticalSection courseFormats = initSection(getH3Title(LUConstants.FORMATS_LABEL_KEY), WITH_DIVIDER);
        addField(courseFormats, COURSE + "/" + FORMATS, null, new CourseFormatList(COURSE + "/" + FORMATS));
        return courseFormats;
    }

    protected VerticalSection generateSchedulingSection() {
        VerticalSection scheduling = initSection(getH3Title(LUConstants.SCHEDULING_LABEL_KEY), WITH_DIVIDER);
        addField(scheduling, COURSE + "/" + TERMS_OFFERED, generateMessageInfo(LUConstants.TERMS_OFFERED_LABEL_KEY));        
        return scheduling;
    }
    
    protected VerticalSection generateDurationSection() {
	    VerticalSection duration = initSection(getH3Title(LUConstants.DURATION_LITERAL_LABEL_KEY), WITH_DIVIDER);
	    GroupSection duration_group = new GroupSection();
	    addField(duration_group, COURSE + "/" + CreditCourseConstants.DURATION + "/" + "timeQuantity", generateMessageInfo(LUConstants.DURATION_LITERAL_LABEL_KEY)); //TODO DURATION ENUMERATION
	    addField(duration_group, COURSE + "/" + CreditCourseConstants.DURATION + "/" + "atpDurationTypeKey", generateMessageInfo(LUConstants.DURATION_TYPE_LABEL_KEY));
	    
	    duration.addSection(duration_group);
	    return duration;
    }

    protected VerticalSection generateInstructorsSection() {
        VerticalSection instructors = initSection(getH3Title(LUConstants.INSTRUCTOR_LABEL_KEY), WITH_DIVIDER);
        addField(instructors, COURSE + "/" + PRIMARY_INSTRUCTOR + "/personId");
        return instructors;
    }

    protected SectionView generateLearningObjectivesSection() {
        VerticalSectionView section = initSectionView(CourseSections.LEARNING_OBJECTIVES, LUConstants.LEARNING_OBJECTIVES_LABEL_KEY);
        //section.addSection(generateLearningObjectivesNestedSection());
        return section;
    }

    protected VerticalSection generateLearningObjectivesNestedSection() {
        VerticalSection los = initSection(null, NO_DIVIDER);
         
        QueryPath path = QueryPath.concat(null, COURSE + "/" + COURSE_SPECIFIC_LOS + "/" + "*" + "/" + CreditCourseCourseSpecificLOsConstants.INCLUDED_SINGLE_USE_LO + "/" + "description");
    	Metadata meta = modelDefinition.getMetadata(path);
        
        // FIXME [KSCOR-225]  where should repo key come from?
        FieldDescriptor fd = addField(los,
        								CreditCourseConstants.COURSE_SPECIFIC_LOS,
        								null,
        								new LOBuilder(type, state, groupName, "kuali.loRepository.key.singleUse", meta),
        								CreditCourseProposalConstants.COURSE);

        // have to do this here, because decision on binding is done in ks-core,
        // and we obviously don't want ks-core referring to LOBuilder
        fd.setWidgetBinding(LOBuilderBinding.INSTANCE);

        los.addStyleName("KS-LUM-Section-Divider");
        return los;
    }
    
    public class CourseFormatList extends UpdatableMultiplicityComposite {
        private final String parentPath;
        public CourseFormatList(String parentPath){
            super(StyleType.TOP_LEVEL);
            this.parentPath = parentPath;
            setAddItemLabel(getLabel(LUConstants.COURSE_ADD_FORMAT_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.FORMAT_LABEL_KEY));
        }

        public Widget createItem() {
            VerticalSection item = new VerticalSection();
            addField(item, ACTIVITIES, null, new CourseActivityList(QueryPath.concat(parentPath, String.valueOf(getAddItemKey()), ACTIVITIES).toString()),
                    parentPath + "/" + String.valueOf(getAddItemKey()));
            return item;
        }
    }

    public class CourseActivityList extends UpdatableMultiplicityComposite {

        private final String parentPath;
        public CourseActivityList(String parentPath){
            super(StyleType.SUB_LEVEL);
            this.parentPath = parentPath;
            setAddItemLabel(getLabel(LUConstants.ADD_ACTIVITY_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.ACTIVITY_LITERAL_LABEL_KEY));
        }

        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(getAddItemKey())).toString();
            GroupSection activity = new GroupSection();
            addField(activity, ACTIVITY_TYPE, generateMessageInfo(LUConstants.ACTIVITY_TYPE_LABEL_KEY), path);
            activity.nextLine();
            
            addField(activity, CONTACT_HOURS + "/" + "unitQuantity", generateMessageInfo(LUConstants.CONTACT_HOURS_LABEL_KEY) , path);
            addField(activity, CONTACT_HOURS + "/" + "unitType", null,  null, path);

            activity.nextLine();
            
            addField(activity, CreditCourseActivityConstants.DURATION + "/" + "atpDurationTypeKey", generateMessageInfo(LUConstants.DURATION_TYPE_LABEL_KEY), null, path);
            addField(activity, CreditCourseActivityConstants.DURATION + "/" + "timeQuantity", generateMessageInfo(LUConstants.DURATION_LITERAL_LABEL_KEY), path);

            activity.nextLine();
            
            addField(activity, DEFAULT_ENROLLMENT_ESTIMATE, generateMessageInfo(LUConstants.CLASS_SIZE_LABEL_KEY), path);

            return activity;
        }

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
                searchRpcServiceAsync.search(searchRequest, new AsyncCallback<SearchResult>() {

                    @Override
                    public void onSuccess(SearchResult result) {
                        for (SearchResultRow r : result.getRows()) {
                            people.addItem(r.getCells().get(0).getValue(), r.getCells().get(1).getValue());
                        }
                        us.setListItems(people);
                        us.selectItem(userId);
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Unable to contact the SearchService for the list of users");
                        people.addItem(userId, userId);
                        us.setListItems(people);
                        us.selectItem(userId);
                    }
                });
             */
        }

        public boolean isMultipleSelect(){
            return true;
        }
    }

    public class ProposerPersonList extends KSLabelList {
        public ProposerPersonList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }

    public class OfferedJointlyList extends UpdatableMultiplicityComposite {
        {
            setAddItemLabel(getLabel(LUConstants.ADD_EXISTING_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.JOINT_OFFER_ITEM_LABEL_KEY));
            //setMinEmptyItems(1);
        }

        private final String parentPath;
        public OfferedJointlyList(String parentPath){
            super(StyleType.TOP_LEVEL);
            this.parentPath = parentPath;
        }

        /*        @Override
        public MultiplicityItem getItemDecorator(StyleType style) {
            return new RemovableItem();
        }*/

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(getAddItemKey())).toString();
            GroupSection ns = new GroupSection();
            addField(ns, CreditCourseJointsConstants.COURSE_ID, generateMessageInfo(LUConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY), null, path);
            return ns;
        }
    }

    public class CrossListedList extends UpdatableMultiplicityComposite {
        {
            setAddItemLabel(getLabel(LUConstants.ADD_CROSS_LISTED_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.CROSS_LISTED_ITEM_LABEL_KEY));
        }

        private final String parentPath;
        public CrossListedList(String parentPath){
            super(StyleType.TOP_LEVEL);
            this.parentPath = parentPath;
        }

        /*        @Override
        public MultiplicityItem getItemDecorator(StyleType style) {
            return new RemovableItem();
        }*/

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(getAddItemKey())).toString();
            GroupSection ns = new GroupSection();
//            addField(ns, DEPARTMENT, generateMessageInfo(LUConstants.DEPT_LABEL_KEY), null, path);
//            ns.nextLine();
            addField(ns, SUBJECT_AREA, generateMessageInfo(LUConstants.SUBJECT_CODE_LABEL_KEY), path);
            addField(ns, COURSE_NUMBER_SUFFIX, generateMessageInfo(LUConstants.COURSE_NUMBER_LABEL_KEY), path);

            return ns;
        }
    }

    public class VersionCodeList extends UpdatableMultiplicityComposite {
        {
            setAddItemLabel(getLabel(LUConstants.ADD_VERSION_CODE_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.VERSION_CODE_LABEL_KEY));
        }
        private final String parentPath;
        public VersionCodeList(String parentPath){
            super(StyleType.TOP_LEVEL);
            this.parentPath = parentPath;
        }

        /*        @Override
            public MultiplicityItem getItemDecorator(StyleType style) {
                return new RemovableItem();
            }*/

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(getAddItemKey())).toString();
            GroupSection ns = new GroupSection();
            addField(ns, "variationCode", generateMessageInfo(LUConstants.CODE_LABEL_KEY), path);
            addField(ns, "variationTitle", generateMessageInfo(LUConstants.TITLE_LITERAL_LABEL_KEY), path);

            return ns;
        }
    }

    /*
     * Configuring Program specific screens.
     */
    public void configureProgramProposal(WorkflowEnhancedController layout, String objectKey, String typeKey, String stateKey) {

        groupName = LUConstants.PROGRAM_GROUP_NAME;

        addCluStartSection(layout);

        String programSections = "Program Sections";

        layout.addMenu(programSections);
        layout.addMenuItem(programSections, generateProgramInfoSection());

        String tools = "Tools";
        layout.addMenu(tools);
        layout.addMenuItem(tools, new CollaboratorTool(CourseSections.PEOPLE_PERMISSOMS, LUConstants.SECTION_AUTHORS_AND_COLLABORATORS,
            getWorkflowDocumentType(), getH3Title(LUConstants.SECTION_AUTHORS_AND_COLLABORATORS)));
        layout.addMenuItem(tools, new CommentPanel(CourseSections.COMMENTS, LUConstants.TOOL_COMMENTS_LABEL_KEY));
        layout.addMenuItem(tools, new DocumentTool(CourseSections.DOCUMENTS, LUConstants.TOOL_DOCUMENTS_LABEL_KEY));

        groupName = LUConstants.PROGRAM_GROUP_NAME;
    }


    public SectionView generateProgramInfoSection(){
        VerticalSectionView section = initSectionView(CourseSections.PROGRAM_INFO, LUConstants.INFORMATION_LABEL_KEY);
        section.addSection(generateShortTitleSection());
        return section;
    }

    protected VerticalSection generateShortTitleSection() {
        VerticalSection shortTitle = initSection(getH3Title(LUConstants.SHORT_TITLE_LABEL_KEY), WITH_DIVIDER);
        addField(shortTitle, "cluInfo/officialIdentifier/shortName", null);
        return shortTitle;
    }

    protected VerticalSectionView initSectionView (Enum<?> viewEnum, String labelKey) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), CLU_PROPOSAL_MODEL);
        section.addStyleName(LUConstants.STYLE_SECTION);
        return section;
    }


    protected VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section;
        if(title!=null){
            section = new VerticalSection(title);
        }else{
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
    protected FieldDescriptor addField(Section section, String fieldKey) {
        return addField(section, fieldKey, null, null, null);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey) {
        return addField(section, fieldKey, messageKey, null, null);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
        return addField(section, fieldKey, messageKey, widget, null);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, String parentPath) {
        return addField(section, fieldKey, messageKey, null, parentPath);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
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
        addField(justiFee, COURSE + "/" + FEES + "/0/" + JUSTIFICATION,  generateMessageInfo(LUConstants.JUSTIFICATION_FEE), new KSTextArea());

        FeeMultiplicity feeList = new FeeMultiplicity(COURSE + "/" + FEES, groupName, type, state);
        FieldDescriptor feeFD = addField(justiFee, COURSE + "/" + FEES, null, feeList);
        feeList.setModelDefinition(modelDefinition);
        feeFD.setWidgetBinding(new FeeMultiplicity.FeeMultiplicityBinding());

        section.addSection(justiFee);

        VerticalSection financialSection = initSection(getH3Title(LUConstants.FINANCIAL_INFORMATION), WITH_DIVIDER);

        FinancialInformationList finInfoList = new FinancialInformationList(COURSE + "/" + REVENUE_INFO + "/" + REVENUE_ORG, LUConstants.REVENUE);
        finInfoList.setMinEmptyItems(1);
        addField(financialSection, COURSE + "/" + REVENUE_INFO + "/" + REVENUE_ORG, generateMessageInfo(LUConstants.REVENUE) , finInfoList);

        // ExpenditureList expInfoList = new ExpenditureList(COURSE + "/" + EXPENDITURE_INFO + "/" + EXPENDITURE_ORG);
        FinancialInformationList expInfoList = new FinancialInformationList(COURSE + "/" + EXPENDITURE_INFO + "/" + EXPENDITURE_ORG, LUConstants.EXPENDITURE);
        addField(financialSection, COURSE + "/" + EXPENDITURE_INFO + "/" + EXPENDITURE_ORG, generateMessageInfo(LUConstants.EXPENDITURE), expInfoList);

        section.addSection(financialSection);

        return section;
    }

    // TODO -if the sleaze for forcing a single item to 100% is to stay longer than short-term, factor
    // common sleaziness up into a superclass
    public class FinancialInformationList extends UpdatableMultiplicityComposite {
        private final String parentPath;
        private final String labelKey;
        // keep track of removalCallbacks that super.addItem will be setting
        private Map<MultiplicityItem, Callback<MultiplicityItem>> removalCallbacks = new HashMap<MultiplicityItem, Callback<MultiplicityItem>>();
        private Callback<MultiplicityItem> oneHundredPercentCallback = new Callback<MultiplicityItem>() {
            public void exec(MultiplicityItem itemToRemove) {
                // exec the removalCallback that super.addItem() set (kind of an exec(super.removalCallback))
                removalCallbacks.get(itemToRemove).exec(itemToRemove);
                // remove it, since we won't need it any more
                removalCallbacks.remove(itemToRemove);
                // and do our own thing to set the remaining percentage to 100
                if ((getItems().size() - getRemovedItems().size()) == 1) {
                    MultiplicityItem currItem = null;
                    for (int i = 0; i < getItems().size(); i++) {
                        currItem = getItems().get(i);
                        if ( ! currItem.isDeleted() ) break;
                    }
                    if (null == currItem || currItem.isDeleted()) {
                        String errMsg = "Unable to find non-deleted item in removal callback";
                        GWT.log(errMsg, new RuntimeException(errMsg));
                    }
                    if(currItem!=null){
                        // So, how's this for sleazy? Even sleazier than the addItem() sleaziness below
                        Object itemWithHeader = currItem.getItemWidget();
                        GroupSection gSection = null;
                        // let's be paranoid
                        if (itemWithHeader instanceof RemovableItemWithHeader && ((RemovableItemWithHeader) itemWithHeader).getItemWidget() instanceof GroupSection)
                        {
                            gSection = (GroupSection) ((RemovableItemWithHeader) itemWithHeader).getItemWidget();
                            if (gSection.getFields().size() >= 2) {
                                Widget txtBox = gSection.getFields().get(1).getFieldWidget();
                                if (txtBox instanceof KSTextBox) {
                                    ((KSTextBox) txtBox).setValue("100.00%");
                                }
                            }
                        }
                    }
                }
            }
        };

        {
            setAddItemLabel(getLabel(LUConstants.ADD_ANOTHER_ORGANIZATION));
            setItemLabel(getLabel(LUConstants.ORGANIZATION));
        }

        public FinancialInformationList(String parentPath, String labelKey) {
            super(StyleType.TOP_LEVEL);
            this.parentPath = parentPath;
            this.labelKey = labelKey;
        }

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            addField(ns, AffiliatedOrgInfoConstants.ORG_ID, generateMessageInfo(LUConstants.REVENUE), path );
            FieldDescriptor fd = addField(ns, PERCENTAGE, generateMessageInfo(LUConstants.AMOUNT), path);
            fd.getFieldWidget();

            // Do our own validationCallback to make sure they add up to 100%?

                    return ns;
        }

        @Override
        public MultiplicityItem addItem() {
            MultiplicityItem returnItem = super.addItem();
            // this is _so_ wrong, but Brian and I couldn't figure out a better way to
            // force single item to be 100%
            removalCallbacks.put(returnItem, returnItem.getRemoveCallback());
            returnItem.setRemoveCallback(oneHundredPercentCallback);
            if (getItems().size() == 1) {
                // needs to be 100% for a single revenue org
                Widget txtBox = ((GroupSection) returnItem.getItemWidget()).getFields().get(1).getFieldWidget();
                ((KSTextBox) txtBox).setValue("100");
            }
            return returnItem;
        }
    }

    @Override
    public String getCourseTitlePath() {
        return COURSE_TITLE_PATH;
    }

    @Override
    public String getProposalIdPath() {
        return PROPOSAL_ID_PATH;
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
    public String[] getWorkflowRequiredFields() {
        return new String[]{"curriculumOversightOrgs"};
    }

    @Override
    public String getWorkflowDocumentType() {
        return WORKFLOW_DOC_TYPE;
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
        if (model.get("copyOfCourseId") != null){
            sb.append("Modify Course: ");
            sb.append(model.get("courseCode"));
            sb.append(" - ");
            sb.append(model.get("transcriptTitle"));
        } else {
            sb.append("New Course: ");
            sb.append(model.get(getProposalTitlePath()));
        }

        return sb.toString();
    }
}


class KeyListModelWigetBinding extends ModelWidgetBindingSupport<HasDataValue> {
    private String key;
    HasDataValueBinding hasDataValueBinding = HasDataValueBinding.INSTANCE;

    public KeyListModelWigetBinding(String key) {
        this.key = key;
    }

    @Override
    public void setModelValue(HasDataValue widget, DataModel model, String path) {
        // convert from the structure path/0/<id> into path/0/<key>/<id>
        hasDataValueBinding.setModelValue(widget, model, path);

        QueryPath qPath = QueryPath.parse(path);
        Value value = ((KSSelectedList)widget).getValueWithTranslations();

        Data idsData = null;
        Data idsDataStruct = null;

        if (value != null) {
            idsData = value.get();
        }
        if (idsData != null) {
            for (Data.Property p : idsData) {
                if(!"_runtimeData".equals(p.getKey())){
                    String id = p.getValue();
                    // old translation path path/_runtimeData/0/id-translation
                    QueryPath translationPath = new QueryPath();
                    translationPath.add(new Data.StringKey(qPath.toString()));
                    translationPath.add(new Data.StringKey("_runtimeData"));
                    translationPath.add(new Data.IntegerKey((Integer)p.getKey()));
                    translationPath.add(new Data.StringKey("id-translation"));

                    Data idItem = new Data();
                    String translation = model.get(translationPath.toString());
                    Data idItemRuntime = new Data();
                    Data idItemTranslation = new Data();
                    idsDataStruct = (idsDataStruct == null)? new Data() : idsDataStruct;
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

        if(middleManModel!=null){
            value = middleManModel.get(qPath);
        }

        if (value != null) {
            idsData = (Data) value;
            if (idsData != null) {
                for (Data.Property p : idsData) {
                    if(!"_runtimeData".equals(p.getKey())){
                        Data idItem = p.getValue();
                        String id = idItem.get(key);
                        Data runtimeData = idItem.get("_runtimeData");
                        Data translationData = runtimeData.get(key);
                        newIdsData = (newIdsData == null)? new Data() : newIdsData;
                        newIdsData.add(id);
                        newIdsRuntimeData = (newIdsRuntimeData == null)? new Data() : newIdsRuntimeData;
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