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
package org.kuali.student.lum.lu.ui.course.client.configuration.course;
	
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.MultiplicityCompositeBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.MultiplicityItemBinding;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.RemovableItemWithHeader;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.UpdatableMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.commenttool.CommentPanel;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.AcademicSubjectOrgInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.AffiliatedOrgInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityContactHoursConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseCourseSpecificLOsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseExpenditureInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseFormatConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseJointsConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseRevenueInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.LearningObjectiveConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.removeinm4.LOBuilderBinding;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseRequisitesSectionView;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.viewclu.ViewCluConfigurer;
import org.kuali.student.lum.lu.ui.course.client.widgets.CollaboratorTool;
import org.kuali.student.lum.lu.ui.course.client.widgets.LOBuilder;
import org.kuali.student.lum.lu.ui.course.client.widgets.LRBuilder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
	
	
	/**
	 * This is the configuration factory class for creating a proposal.
	 *
	 * TODO: The following is a list of items that need to be fixed.
	 * 	1) All hardcoded drop downs need to be replaced with one populated via an enumeration lookup
	 *  2) Any pickers (eg. org, course, needs to be replaced wtih proper lookup based search pickers
	 *
	 * @author Kuali Student Team
	 *
	 */
	public class CourseConfigurer
	 implements CreditCourseProposalConstants,
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
	 CreditCourseExpenditureInfoConstants{
	
		//FIXME:  Initialize type and state
		protected String type = "course";
	    protected String state = "draft";
	    protected String groupName;
	
	    protected boolean WITH_DIVIDER = true;
	    protected boolean NO_DIVIDER = false;
	
	    public static final String CLU_PROPOSAL_MODEL					= "cluProposalModel";
	    public static final String PROPOSAL_ID_PATH						= "proposal/id";
	    public static final String PROPOSAL_TITLE_PATH					= "proposal/title";
	    public static final String COURSE_TITLE_PATH					= COURSE + "/" + COURSE_TITLE;
	    public static final String PROPOSAL_REFERENCE_TYPE_KEY			= "referenceType.clu.proposal";
	    public static final String PROPOSAL_REFERENCE_OBJECT_TYPE		= "kuali.lu.type.CreditCourse";
	
	    public enum CourseSections{
	        CLU_BEGIN, PEOPLE_PERMISSOMS, SUMMARY, AUTHORS_RATIONALE, GOVERNANCE, COURSE_LOGISTICS, COURSE_INFO, LEARNING_OBJECTIVES,
	        COURSE_REQUISITES, ACTIVE_DATES, FINANCIALS, ATTACHMENTS, COMMENTS, DOCUMENTS,
	        PROGRAM_INFO, ASSEMBLER_TEST
	    }
	
	    protected DataModelDefinition modelDefinition;
	
	    public void setModelDefinition(DataModelDefinition modelDefinition){
	    	this.modelDefinition = modelDefinition;
	    }
	
	    public void configureCourseProposal(ConfigurableLayout layout) {
	    	groupName = LUConstants.COURSE_GROUP_NAME;
	
	        addCluStartSection(layout);
	
	        String editTabLabel = getLabel(LUConstants.EDIT_TAB_LABEL_KEY);
	        
	        //ProposalInformation
	        layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.PROPOSAL_INFORMATION_LABEL_KEY)}, generateAuthorsRationaleSection());
	        
	        //Course Content
	        layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateCourseInfoSection());
	        layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateCourseLogisticsSection());
	        layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateLearningObjectivesSection());
	        
	        //Student Eligibility
	        layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.STUDENT_ELIGIBILITY_LABEL_KEY)}, generateCourseRequisitesSection());
	
	        //Administrative
	        layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateGovernanceSection());
	        layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateActiveDatesSection());
	        layout.addSection(new String[] {editTabLabel, getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateFinancialsSection());
	        
	        //Review Proposal Tab
	        ViewCourseProposalSummaryConfigurer summaryConfigurer = new ViewCourseProposalSummaryConfigurer(type, state, groupName, modelDefinition);
	        layout.addSection(new String[] {getLabel(LUConstants.SUMMARY_LABEL_KEY)}, summaryConfigurer.generateSummarySection());
	
	        //layout.addSection(new String[] {"Assembler Test"}, new AssemblerTestSection(CourseSections.ASSEMBLER_TEST, "Assembler Test"));
	
	        //Tool Tabs
	        layout.addTool(new CollaboratorTool(CourseSections.PEOPLE_PERMISSOMS, LUConstants.SECTION_AUTHORS_AND_COLLABORATORS, 
	        		getH2Title(LUConstants.SECTION_AUTHORS_AND_COLLABORATORS)));
	        layout.addTool(new CommentPanel(CourseSections.COMMENTS, getLabel(LUConstants.TOOL_COMMENTS_LABEL_KEY)));
	        layout.addTool(new DocumentTool(CourseSections.DOCUMENTS, getLabel(LUConstants.TOOL_DOCUMENTS_LABEL_KEY)));
	    }
	
	    public SectionView generateSummarySection(){
	        VerticalSectionView section = initSectionView(CourseSections.SUMMARY, LUConstants.SUMMARY_LABEL_KEY);
	
	    	section.enableValidation(false);
	        section.addSection(generateSummaryBrief(getH3Title(LUConstants.BRIEF_LABEL_KEY)));
	        section.addSection(generateSummaryDetails(getH3Title(LUConstants.FULL_VIEW_LABEL_KEY)));
	        return section;
	    }
	
	    protected VerticalSection generateSummaryDetails(SectionTitle title) {
	       return  ViewCluConfigurer.generateSummaryDetails(title);
		}
	
	    protected VerticalSection generateSummaryBrief(SectionTitle title) {
	        VerticalSection section = new VerticalSection(title);
	        section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
	        section.addStyleName(LUConstants.STYLE_SECTION);
	        addField(section, PROPOSAL + "/" + TITLE, getLabel(LUConstants.TITLE_LABEL_KEY) +":    ", new KSLabel());
	        addField(section, COURSE + "/" + SUBJECT_AREA, getLabel(LUConstants.DIVISION_LABEL_KEY), new KSLabel());
	        addField(section, COURSE + "/" + COURSE_NUMBER_SUFFIX, getLabel(LUConstants.SUFFIX_CODE_LABEL_KEY), new KSLabel());
	
	
	        // FIXME wilj: add proposal/delegate/collab person info to assembly
	        addField(section, PROPOSAL + "/" + PROPOSER_PERSON, getLabel(LUConstants.PROPOSER_LABEL_KEY), new ProposerPersonList());
	        addField(section, "proposalInfo/todo", getLabel(LUConstants.DELEGATE_LABEL_KEY), new KSLabel());
	        addField(section, "proposalInfo/todo", getLabel(LUConstants.COLLABORATORS_LABEL_KEY), new KSLabel());
	
	        // FIXME wilj: add create/update meta info to assembly
	        addField(section, PROPOSAL + "/" + META_INFO + "/" + CREATE_TIME, getLabel(LUConstants.CREATED_DATE_LABEL_KEY), new KSLabel());
	        addField(section, PROPOSAL + "/" + META_INFO + "/" + UPDATE_TIME, getLabel(LUConstants.LAST_CHANGED_DATE_LABEL_KEY), new KSLabel());
	
	        addField(section, COURSE + "/" + DESCRIPTION + "/" + RichTextInfoConstants.PLAIN, getLabel(LUConstants.DESCRIPTION_LABEL_LABEL_KEY), new KSLabel());
	       // TODO: Norm: find out why was this prefixed with proposal there is no state on proposal it is on the main object
	        addField(section, CreditCourseProposalConstants.STATE, getLabel(LUConstants.STATUS_LABEL_KEY), new KSLabel());
	        return section;
	    }
		
		public void addCluStartSection(ConfigurableLayout layout){
	        VerticalSectionView section = initSectionView(CourseSections.CLU_BEGIN, LUConstants.START_LABEL_KEY);
	
	        addField(section, PROPOSAL + "/" + TITLE , getLabel(LUConstants.PROPOSAL_TITLE_LABEL_KEY));
	        //This will need to be a person picker
	        // FIXME wilj: add proposal/delegate/collab person info to assembly
	        addField(section, PROPOSAL + "/" + PROPOSER_PERSON, getLabel(LUConstants.PROPOSAL_PERSON_LABEL_KEY), new PersonList()) ;
	        layout.addStartSection(section);
	    }
	
	
		protected SectionView generateAuthorsRationaleSection(){
			VerticalSectionView section = initSectionView(CourseSections.AUTHORS_RATIONALE, LUConstants.AUTHORS_RATIONAL);
			
		VerticalSection titleRationale = initSection(getH3Title(getLabel(LUConstants.PROPOSAL_TITLE_SECTION_LABEL_KEY)), WITH_DIVIDER);
		addField(titleRationale, PROPOSAL + "/" + TITLE , getLabel(LUConstants.PROPOSAL_TITLE_LABEL_KEY));
		addField(titleRationale, PROPOSAL + "/" + RATIONALE, getLabel(LUConstants.PROPOSAL_RATIONALE_LABEL_KEY));
		
		section.addSection(titleRationale);
			
			return section;
		}
		
		/**
	     * Adds a section for adding or modifying rules associated with a given course (program).
	     *
	     * @param layout - a content pane to which this section is added to
	     * @return
	     */
	    protected SectionView generateCourseRequisitesSection() {
	        CourseRequisitesSectionView section = new CourseRequisitesSectionView(CourseSections.COURSE_REQUISITES, getLabel(LUConstants.REQUISITES_LABEL_KEY));
	        //Setting the section title after initializing the widget won't do anything
	        section.setSectionTitle(SectionTitle.generateH1Title(getLabel(LUConstants.REQUISITES_LABEL_KEY)));
	        addField(section, SEARCH + "/" + "findCourse");
	        return section;
	    }
	
	    protected SectionView generateActiveDatesSection() {
	        VerticalSectionView section = initSectionView(CourseSections.ACTIVE_DATES, LUConstants.ACTIVE_DATES_LABEL_KEY);
	
	        section.addSection(generateActiveDateStartSection());
	        section.addSection(generateActiveDateEndSection());
	
	        VerticalSection firstExpectedOfferingSection = initSection(getH3Title("First Expected Offering"), NO_DIVIDER);
	        addField(firstExpectedOfferingSection, COURSE + "/" + FIRST_EXPECTED_OFFERING);
	        section.addSection(firstExpectedOfferingSection);
	
	        return section;
	    }
	
	    protected VerticalSection generateActiveDateEndSection() {
	        VerticalSection endDate = initSection(getH3Title(LUConstants.END_DATE_LABEL_KEY), WITH_DIVIDER);
	        addField(endDate, COURSE + "/" + EXPIRATION_DATE, getLabel(LUConstants.EXPIRATION_DATE_LABEL_KEY));
	        return endDate;
		}
	
	    protected VerticalSection generateActiveDateStartSection() {
	        VerticalSection startDate = initSection(getH3Title(LUConstants.START_DATE_LABEL_KEY), WITH_DIVIDER);
	        addField(startDate, COURSE + "/" + CreditCourseConstants.EFFECTIVE_DATE, getLabel(LUConstants.EFFECTIVE_DATE_LABEL_KEY));
	        return startDate;
		}
	
	    protected SectionView generateGovernanceSection(){
	        VerticalSectionView section = initSectionView(CourseSections.GOVERNANCE, LUConstants.GOVERNANCE_LABEL_KEY);
	
	        addField(section, COURSE + "/" + ACADEMIC_SUBJECT_ORGS, getLabel(LUConstants.ACADEMIC_SUBJECT_ORGS_KEY));
	        addField(section, COURSE + "/" + CAMPUS_LOCATIONS, getLabel(LUConstants.CAMPUS_LOCATION_LABEL_KEY));
	        addField(section, COURSE + "/" + DEPARTMENT, getLabel(LUConstants.ADMIN_ORG_LABEL_KEY));
	
	        return section;
	    }
	
		public SectionView generateCourseInfoSection(){
	        VerticalSectionView section = initSectionView(CourseSections.COURSE_INFO, LUConstants.INFORMATION_LABEL_KEY);
	
	        //FIXME: Label should be key to messaging, field type should come from dictionary?
	
	        section.addSection(generateCourseNumberSection());
	        section.addSection(generateCourseInfoShortTitleSection());
	        section.addSection(generateLongTitleSection());
	        section.addSection(generateDescriptionSection());
	        
	        return section;
	    }
	
		protected GroupSection generateCourseNumberSection() {
	
	        //COURSE NUMBER
	        GroupSection courseNumber = new GroupSection(getH3Title(LUConstants.IDENTIFIER_LABEL_KEY));
	        courseNumber.addStyleName(LUConstants.STYLE_SECTION);
	        courseNumber.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
	        addField(courseNumber, COURSE + "/" + SUBJECT_AREA, null);
	        addField(courseNumber, COURSE + "/" + COURSE_NUMBER_SUFFIX, null);
	
	        // TODO - hide cross-listed, offered jointly and version codes initially, with
	        // clickable label to disclose them
	        courseNumber.addSection(generateCrossListedSection());
	        courseNumber.addSection(generateOfferedJointlySection());
	        courseNumber.addSection(generateVersionCodesSection());
	
	        return courseNumber;
		}
	
		protected VerticalSection generateVersionCodesSection() {
	        //Version Codes
	        VerticalSection versionCodes = new VerticalSection(getH3Title(LUConstants.VERSION_CODES_LABEL_KEY));
	        addField(versionCodes, COURSE + "/" + VERSIONS, null, new VersionCodeList(COURSE + "/" + VERSIONS));
	        //versionCodes.addStyleName("KS-LUM-Section-Divider");
	        return versionCodes;
		}
	
		protected VerticalSection generateOfferedJointlySection() {
	        // Offered jointly
	        VerticalSection offeredJointly = new VerticalSection(getH3Title(LUConstants.JOINT_OFFERINGS_ALT_LABEL_KEY));
	        addField(offeredJointly, COURSE + "/" + JOINTS, null, new OfferedJointlyList(COURSE + "/" + JOINTS));
	        //offeredJointly.addStyleName("KS-LUM-Section-Divider");
	        return offeredJointly;
		}
	
		protected VerticalSection generateCrossListedSection() {
	        // Cross-listed
	        VerticalSection crossListed = new VerticalSection(getH3Title(LUConstants.CROSS_LISTED_ALT_LABEL_KEY));
	        // crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
	        addField(crossListed, COURSE + "/" + CROSS_LISTINGS, null, new CrossListedList(COURSE + "/" + CROSS_LISTINGS));
	        //crossListed.addStyleName("KS-LUM-Section-Divider");
	        return crossListed;
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
	
		protected VerticalSection generateDescriptionSection() {
	        VerticalSection description = initSection(getH3Title(LUConstants.DESCRIPTION_LABEL_KEY), WITH_DIVIDER);
	        //FIXME Temporary fix til we have a real rich text editor
	        //addField(description, COURSE + "/" + DESCRIPTION, null);
	        addField(description, COURSE + "/" + DESCRIPTION + "/" + RichTextInfoConstants.PLAIN, null);
	        return description;
		}
	
	    public SectionView generateCourseLogisticsSection() {
	        VerticalSectionView section = initSectionView(CourseSections.COURSE_LOGISTICS, LUConstants.LOGISTICS_LABEL_KEY);
	
	        section.addSection(generateInstructorsSection());
	        
	        section.addSection(generateSchedulingSection());
	        section.addSection(generateLearningResultsSection());
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
	        GroupSection duration = new GroupSection();
	        addField(duration, COURSE + "/" + CreditCourseConstants.DURATION + "/" + QUANTITY, getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY)); //TODO DURATION ENUMERATION
	        addField(duration, COURSE + "/" + CreditCourseConstants.DURATION + "/" + TERM_TYPE, getLabel(LUConstants.DURATION_TYPE_LABEL_KEY));
	        scheduling.addSection(duration);
	        return scheduling;
		}
	
	    protected VerticalSection generateInstructorsSection() {
	        VerticalSection instructors = initSection(getH3Title(LUConstants.INSTRUCTOR_LABEL_KEY), WITH_DIVIDER);
	        // FIXME wilj: do we need to set the instructor's orgId? or can we default it at the assembler level?
	        addField(instructors, COURSE + "/" + PRIMARY_INSTRUCTOR);
	        return instructors;
		}
	
	    protected SectionView generateLearningObjectivesSection() {
	        VerticalSectionView section = initSectionView(CourseSections.LEARNING_OBJECTIVES, LUConstants.LEARNING_OBJECTIVES_LABEL_KEY);
	        section.addSection(generateLearningObjectivesNestedSection());
	        return section;
	    }
	
	    protected VerticalSection generateLearningObjectivesNestedSection() {
	        VerticalSection los = initSection(null, NO_DIVIDER);
	         
	        QueryPath path = QueryPath.concat(null, COURSE + "/" + COURSE_SPECIFIC_LOS + "/" + "*" + "/" + CreditCourseCourseSpecificLOsConstants.INCLUDED_SINGLE_USE_LO + "/" + "description");
	    	Metadata meta = modelDefinition.getMetadata(path);
	        
	        // FIXME - where should repo key come from?
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
	
	    protected VerticalSection generateLearningResultsSection() {
	    	VerticalSection lrSection = initSection(getH3Title(LUConstants.LEARNING_RESULTS_LABEL_KEY), WITH_DIVIDER);
	    	LRBuilder lrBuilder = new LRBuilder(type, state, groupName, modelDefinition);
	    	addField(lrSection, CreditCourseConstants.COURSE_SPECIFIC_LOS, null, lrBuilder, CreditCourseProposalConstants.COURSE);
	    	return lrSection;
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
	            addField(activity, ACTIVITY_TYPE, getLabel(LUConstants.ACTIVITY_TYPE_LABEL_KEY), path);
	            activity.nextLine();
	
	            /* CreditInfo is deprecated, needs to be replaced with learning results
	            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
	            activity.addField(new FieldDescriptor("creditInfo", "Credit Value", Type.STRING));
	            activity.nextLine();
	            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
	            activity.addField(new FieldDescriptor("evalType", "Learning Result", Type.STRING));
	            activity.nextLine();
	            */
	
	            addField(activity, CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.QUANTITY, getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY), path);
	            addField(activity, CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.TIME_UNIT, getLabel(LUConstants.DURATION_TYPE_LABEL_KEY), null, path);
	
	            activity.nextLine();
	            addField(activity, CONTACT_HOURS + "/" + CreditCourseActivityContactHoursConstants.HRS, getLabel(LUConstants.CONTACT_HOURS_LABEL_KEY) , path);
	            addField(activity, CONTACT_HOURS + "/" + CreditCourseActivityContactHoursConstants.PER, null,  null, path);
	            addField(activity, DEFAULT_ENROLLMENT_ESTIMATE, getLabel(LUConstants.CLASS_SIZE_LABEL_KEY), path);
	
	            return activity;
	        }
	
	    }
	
	    // FIXME uncomment and fix AlternateAdminOrgList and AlternateInstructorList
	//    public class AlternateAdminOrgList extends MultiplicityCompositeWithLabels {
	//        {
	//            setAddItemLabel("Add an Alternate Admin Organization");
	//            setItemLabel("Organization ID ");
	//        }
	//
	//        @Override
	//        public Widget createItem() {
	//            MultiplicitySection multi = new MultiplicitySection("");
	//
	//            GroupSection ns = new GroupSection();
	//            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
	//            ns.addField(new FieldDescriptor("orgId", "Organization ID", Type.STRING, new OrgPicker() ));
	//            multi.addSection(ns);
	//
	//            return multi;
	//        }
	//    }
	//
	//    public class AlternateInstructorList extends MultiplicityCompositeWithLabels {
	//        {
	//            setAddItemLabel("Add an Alternate Instructor.");
	//            setItemLabel("Instructor ID");
	//        }
	//
	//        @Override
	//        public Widget createItem() {
	//            MultiplicitySection multi = new MultiplicitySection("");
	//
	//            GroupSection ns = new GroupSection();
	//            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
	//            ns.addField(new FieldDescriptor("personId", "Instructor ID", Type.STRING /*, new InstructorPicker() */ ));
	//            multi.addSection(ns);
	//
	//            return multi;
	//        }
	//    }
	
	    public class PersonList extends KSDropDown {
	        final SimpleListItems people = new SimpleListItems();
	
	        public PersonList() {
	            final PersonList us = this;
	            final String userId = Application.getApplicationContext().getUserId();
	            
	            //FIXME: Commented out search code to display drop down with only current user, and disable select            
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
	            addField(ns, CreditCourseJointsConstants.COURSE_ID, getLabel(LUConstants.COURSE_NUMBER_OR_TITLE_LABEL_KEY), null, path);
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
	            addField(ns, DEPARTMENT, getLabel(LUConstants.DEPT_LABEL_KEY), null, path);
	            ns.nextLine();
	            addField(ns, SUBJECT_AREA, getLabel(LUConstants.SUBJECT_CODE_LABEL_KEY), path);
	            addField(ns, COURSE_NUMBER_SUFFIX, getLabel(LUConstants.COURSE_NUMBER_LABEL_KEY), path);
	
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
	            addField(ns, "versionCode", getLabel(LUConstants.CODE_LABEL_KEY), path);
	            addField(ns, "versionTitle", getLabel(LUConstants.TITLE_LITERAL_LABEL_KEY), path);
	
	            return ns;
	        }
	    }
	
	    /*
	     * Configuring Program specific screens.
	     */
	    public void configureProgramProposal(ConfigurableLayout layout, String objectKey, String typeKey, String stateKey) {
	
	    	groupName = LUConstants.PROGRAM_GROUP_NAME;
	
	        addCluStartSection(layout);
	
	        layout.addSection(new String[] {getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateProgramInfoSection());
	
	        layout.addTool(new CollaboratorTool(CourseSections.PEOPLE_PERMISSOMS, LUConstants.SECTION_AUTHORS_AND_COLLABORATORS, 
	        		getH3Title(LUConstants.SECTION_AUTHORS_AND_COLLABORATORS)));
	        layout.addTool(new CommentPanel(CourseSections.COMMENTS, LUConstants.TOOL_COMMENTS_LABEL_KEY));
	        layout.addTool(new DocumentTool(CourseSections.DOCUMENTS, LUConstants.TOOL_DOCUMENTS_LABEL_KEY));
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
	        section.getSectionTitle().addStyleName("ks-heading-page-title");
	        return section;
	    }
	
	
	    protected VerticalSection initSection(SectionTitle title, boolean withDivider) {
	        VerticalSection section;
	    	if(title!=null){
	        	section = new VerticalSection(title);
	        	section.getSectionTitle().addStyleName("ks-heading-page-section");
	        }else{
	        	section = new VerticalSection();
	        }
	        section.addStyleName(LUConstants.STYLE_SECTION);
	        if (withDivider)
	            section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
	        return section;
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
	    protected FieldDescriptor addField(Section section, String fieldKey, String fieldLabel) {
	    	return addField(section, fieldKey, fieldLabel, null, null);
	    }
	    protected FieldDescriptor addField(Section section, String fieldKey, String fieldLabel, Widget widget) {
	    	return addField(section, fieldKey, fieldLabel, widget, null);
	    }
	    protected FieldDescriptor addField(Section section, String fieldKey, String fieldLabel, String parentPath) {
	        return addField(section, fieldKey, fieldLabel, null, parentPath);
	    }
	    protected FieldDescriptor addField(Section section, String fieldKey, String fieldLabel, Widget widget, String parentPath) {
	        QueryPath path = QueryPath.concat(parentPath, fieldKey);
	    	Metadata meta = modelDefinition.getMetadata(path);
	
	    	FieldDescriptor fd = new FieldDescriptor(path.toString(), fieldLabel, meta);
	    	if (widget != null) {
	    		fd.setFieldWidget(widget);
	    	}
	    	section.addField(fd);
	    	return fd;
	    }
	    protected SectionView generateFinancialsSection() {
	        VerticalSectionView section = initSectionView(CourseSections.FINANCIALS, LUConstants.FINANCIALS_LABEL_KEY);
	
	        VerticalSection justiFee = initSection(getH3Title(LUConstants.COURSE_FEE_TITLE), WITH_DIVIDER);
	        addField(justiFee, COURSE + "/" + FEES + "/0/" + JUSTIFICATION,  getLabel(LUConstants.JUSTIFICATION_FEE), new KSTextArea()); 
	        
	        FieldDescriptor feeFD = addField(justiFee, COURSE + "/" + FEES, null, new FeeList(COURSE + "/" + FEES));
	        feeFD.setWidgetBinding(new FeeListBinding());
	        
	        section.addSection(justiFee);
	        
        VerticalSection financialSection = initSection(getH3Title(LUConstants.FINANCIAL_INFORMATION), WITH_DIVIDER);
	        
	        FinancialInformationList finInfoList = new FinancialInformationList(COURSE + "/" + REVENUE_INFO + "/" + REVENUE_ORG);
	        finInfoList.setMinEmptyItems(1);
        addField(financialSection, COURSE + "/" + REVENUE_INFO + "/" + REVENUE_ORG, getLabel(LUConstants.REVENUE) , finInfoList);
	        
	        ExpenditureList expInfoList = new ExpenditureList(COURSE + "/" + EXPENDITURE_INFO + "/" + EXPENDITURE_ORG);
	        expInfoList.setMinEmptyItems(1);
	        addField(financialSection, COURSE + "/" + EXPENDITURE_INFO + "/" + EXPENDITURE_ORG, getLabel(LUConstants.EXPENDITURE), expInfoList);
	        
	        section.addSection(financialSection);
	
	        return section;
	    }
	    
	    // TODO all this down to FinancialInfomationList in another top-level class
	    // TODO - from enums
	    public class RateTypeList extends KSDropDown {
	        public RateTypeList() {
	            SimpleListItems types = new SimpleListItems();
	            types.addItem("variableRateFee", getLabel(LUConstants.VARIABLE_RATE));
	            types.addItem("fixedRateFee", getLabel(LUConstants.FIXED_RATE));
	            types.addItem("multipleRateFee", getLabel(LUConstants.MULTIPLE_RATE));
	            types.addItem("perCreditFee", getLabel(LUConstants.PER_CREDIT_RATE));
	            super.setListItems(types);
	        }
	    }
	    // TODO - from enums
	    public class FeeTypeList extends KSDropDown {
	        public FeeTypeList(){
	            SimpleListItems types = new SimpleListItems();
            types.addItem("kuali.enum.type.feeTypes.labFee", getLabel(LUConstants.LAB_FEE));
            types.addItem("kuali.enum.type.feeTypes.materialFee",  getLabel(LUConstants.MATERIAL_FEE));
            types.addItem("kuali.enum.type.feeTypes.studioFee", getLabel(LUConstants.STUDIO_FEE));
            types.addItem("kuali.enum.type.feeTypes.fieldTripFee", getLabel(LUConstants.FIELD_TRIP_FEE));
            types.addItem("kuali.enum.type.feeTypes.fieldStudyFee", getLabel(LUConstants.FIELD_STUDY_FEE));
            types.addItem("kuali.enum.type.feeTypes.administrativeFee", getLabel(LUConstants.ADMINISTRATIVE_FEE));
            types.addItem("kuali.enum.type.feeTypes.coopFee", getLabel(LUConstants.COOP_FEE));
            types.addItem("kuali.enum.type.feeTypes.greensFee",  getLabel(LUConstants.GREENS_FEE));
	            super.setListItems(types);
	        }
	    }    
	    
	    public class FeeList extends UpdatableMultiplicityComposite {
	    	
	        {
            setAddItemLabel(getLabel(LUConstants.ADD_A_FEE));
            setItemLabel(getLabel(LUConstants.FEE));
	        }
	        private final String parentPath;
	
	        public FeeList(String parentPath){
	            super(StyleType.TOP_LEVEL);
	            this.parentPath = parentPath;
	        }
	       
	        @Override
	        public Widget createItem() {
	        	// avoid collisions w/ existing fees in the model
	        	return new TransmogrifyingFeeRecordItem(parentPath, itemCount);
	        }
	
	        public Widget createItem(int itemCount) {
	        	return new TransmogrifyingFeeRecordItem(parentPath, itemCount);
	        }
	        
	        @Override
			public MultiplicityItem addItem() {
	        	return addItem(FeeInfoConstants.FIXED_RATE_FEE, 1000 - itemCount);
	        }
	        
			public MultiplicityItem addItem(String rateType, int modelIdx) {
				itemCount++;
				visualItemCount++;
			    //itemCount = itemsPanel.getWidgetCount();
			    MultiplicityItem item = getItemDecorator(style);
			    Widget itemWidget = createItem(itemCount);
			    ((TransmogrifyingFeeRecordItem) itemWidget).setRateType(rateType, modelIdx);
		    	
			    if (item != null){
				    item.setItemKey(new Integer(itemCount - 1));
				    item.setItemWidget(itemWidget);
				    item.setRemoveCallback(removeCallback);
			    } else if (itemWidget instanceof MultiplicityItem){
			    	item = (MultiplicityItem)itemWidget;
			    	item.setItemKey(new Integer(itemCount -1));
			    }
			    items.add(item);
			    item.redraw();
			    itemsPanel.add(item);
			    
			    return item;
			}
			
			public int getItemCount() {
				return itemCount;
			}
	    }
	    
	    public class TransmogrifyingFeeRecordItem extends GroupSection {
	
			private String parentPath;
			private FeeTypeList feeTypeList = new FeeTypeList();
			private RateTypeList rateTypeList = new RateTypeList();
			private GroupSection dropDownSection;
			private GroupSection fieldSection;
            private String fixedPath = "0/" + FeeInfoConstants.FIXED_RATE_FEE + "/";
            private String variablePath = "0/" + FeeInfoConstants.VARIABLE_RATE_FEE + "/";
            private String multiplePath = "0/" + FeeInfoConstants.MULTIPLE_RATE_FEE + "/";
            private String perCreditPath = "0/" + FeeInfoConstants.PER_CREDIT_FEE + "/";
            private SelectionChangeHandler dropDownHandler;
	            
			public TransmogrifyingFeeRecordItem(String parentPath, int itemCount) {
				
	            final int itemNumber = itemCount;
	            this.parentPath = parentPath;
	
	            // swap in/out sections (and their query paths) based on user input
	            dropDownHandler = new SelectionChangeHandler() {
	                @Override
	                public void onSelectionChange(SelectionChangeEvent event) {
	                    String selectedItem = ((KSSelectItemWidgetAbstract) event.getSource()).getSelectedItem();
	                    // 1000 - the number of this mutiplicity item to avoid any conflicts with existing 
	                    // model indices
	                    setRateType(selectedItem, 1000 - itemNumber);
			            feeTypeList.selectItem("kuali.enum.type.feeTypes.labFee");
	                }
	            };
			}

			public void setRateType(String rateType, int modelIdx) {
				clearSection();
                buildRateSpecificDropDownSection(rateType, modelIdx);
                addSection(dropDownSection);
                buildRateSpecificFieldSection(rateType, modelIdx);
                addSection(fieldSection);
			}
			
			private void clearSection() {
				if (null != dropDownSection) {
					removeSection(dropDownSection);
				}
				if (null != fieldSection) {
					removeSection(fieldSection);
				}
			}
			
	    	private void buildRateSpecificDropDownSection(String rateType, int modelIdx) {
	    		dropDownSection = new GroupSection();

	    		if (FeeInfoConstants.FIXED_RATE_FEE.equals(rateType)) {
		            CourseConfigurer.this.addField(dropDownSection, fixedPath + modelIdx + "/feeType", "Fee Type", feeTypeList, parentPath);
		            CourseConfigurer.this.addField(dropDownSection, fixedPath + modelIdx + "/rateType", "Rate Type", rateTypeList, parentPath);
	    		} else if (FeeInfoConstants.MULTIPLE_RATE_FEE.equals(rateType)) {
		            CourseConfigurer.this.addField(dropDownSection, multiplePath + modelIdx + "/feeType", "Fee Type", feeTypeList, parentPath);
		            CourseConfigurer.this.addField(dropDownSection, multiplePath + modelIdx + "/rateType", "Rate Type", rateTypeList, parentPath);
	    		} else if (FeeInfoConstants.PER_CREDIT_FEE.equals(rateType)) {
		            CourseConfigurer.this.addField(dropDownSection, perCreditPath + modelIdx + "/feeType", "Fee Type", feeTypeList, parentPath);
		            CourseConfigurer.this.addField(dropDownSection, perCreditPath + modelIdx + "/rateType", "Rate Type", rateTypeList, parentPath);
	    		} else if (FeeInfoConstants.VARIABLE_RATE_FEE.equals(rateType)) {
		            CourseConfigurer.this.addField(dropDownSection, variablePath + modelIdx + "/feeType", "Fee Type", feeTypeList, parentPath);
		            CourseConfigurer.this.addField(dropDownSection, variablePath + modelIdx + "/rateType", "Rate Type", rateTypeList, parentPath);
	    		}
	            rateTypeList.selectItem(rateType);
	            rateTypeList.addSelectionChangeHandler(dropDownHandler);
	    	}
	    	
	    	private void buildRateSpecificFieldSection(String rateType, int modelIdx) {
	    		fieldSection = new GroupSection();
	    		if (FeeInfoConstants.FIXED_RATE_FEE.equals(rateType)) {
		            CourseConfigurer.this.addField(fieldSection, fixedPath + modelIdx + "/amount", "Amount", parentPath );
	    		} else if (FeeInfoConstants.MULTIPLE_RATE_FEE.equals(rateType)) {
		            CourseConfigurer.this.addField(fieldSection, multiplePath + modelIdx + "/amount", "Amount", new MultipleFeeList(parentPath + "/" + multiplePath), parentPath );
	    		} else if (FeeInfoConstants.PER_CREDIT_FEE.equals(rateType)) {
		            CourseConfigurer.this.addField(fieldSection, perCreditPath + modelIdx + "/amount", "Amount (PerCredit)", parentPath );
	    		} else if (FeeInfoConstants.VARIABLE_RATE_FEE.equals(rateType)) {
		            CourseConfigurer.this.addField(fieldSection, variablePath + modelIdx + "/minAmount", "Amount", parentPath );
		            CourseConfigurer.this.addField(fieldSection, variablePath + modelIdx + "/maxAmount", "To", parentPath );
	    		}
	    	}
		}
	    
		public class FeeListBinding implements ModelWidgetBinding<FeeList> {
	
			@Override
			public void setModelValue(FeeList widget, DataModel model, String path) {
				// passthru; can't subclass
				MultiplicityCompositeBinding.INSTANCE.setModelValue(widget, model, path);
			}
	
			/*
			 * Yeah, duped this due to architectural issues. Technical debt, or
			 * fodder for DOL simplification?
			 */
			@Override
			public void setWidgetValue(FeeList widget, DataModel model, String path) {
		        widget.clear();
	
		        QueryPath qPath = QueryPath.parse(path);
		        Data data = null;
		        if(model!=null){
		        	data = model.get(qPath);
		        }
	
		        if (data != null) {
		            Iterator<Property> itr = data.realPropertyIterator();
		            if (itr.hasNext()) {
		            	FeeInfoHelper feeHelper = FeeInfoHelper.wrap((Data) itr.next().getValue());
		            	if (null != feeHelper.getFixedRateFee()) {
		            		addRateTypeSpecificItems(feeHelper.getFixedRateFee().realPropertyIterator(), FeeInfoConstants.FIXED_RATE_FEE, widget, model, path);
	        			}
		            	if (null != feeHelper.getVariableRateFee()) {
		            		addRateTypeSpecificItems(feeHelper.getVariableRateFee().realPropertyIterator(), FeeInfoConstants.VARIABLE_RATE_FEE, widget, model, path);
		            	}
		            	if (null != feeHelper.getPerCreditFee()) {
		            		addRateTypeSpecificItems(feeHelper.getPerCreditFee().realPropertyIterator(), FeeInfoConstants.PER_CREDIT_FEE, widget, model, path);
		            	}
		            	if (null != feeHelper.getMultipleRateFee()) {
		            		addRateTypeSpecificItems(feeHelper.getMultipleRateFee().realPropertyIterator(), FeeInfoConstants.MULTIPLE_RATE_FEE, widget, model, path);
		            	}
	            	}
	            }
	        }

			private void addRateTypeSpecificItems(Iterator<Property> iterator, String rateType, FeeList widget, DataModel model, String path) {
    			while (iterator.hasNext()) {
	                Property p = (Property) iterator.next();

	                if (p.getKey() instanceof Integer) {
	                	// use the index the model has for this item (as it's model index, not item index)
	                    MultiplicityItem item = widget.addItem(rateType, ((Integer) p.getKey()).intValue());
	                    
	                    // FIXME: Is assumption correct that data passed to setValue() has already been persisted
	                    item.setCreated(false);
	                    MultiplicityItemBinding.INSTANCE.setWidgetValue(item, model, path);
	                }
    			}
			}
		}
		
	    public class MultipleFeeList extends UpdatableMultiplicityComposite {
	        {
	            setAddItemLabel(getLabel(LUConstants.ADD_ANOTHER_FEE));
	            setItemLabel(getLabel(LUConstants.FEE));
	        }
	        private final String parentPath;
	        public MultipleFeeList(String parentPath){
	            super(StyleType.TOP_LEVEL);
	            this.parentPath = parentPath;
	        }
	        @Override
	        public Widget createItem() {
	            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
	            GroupSection ns = new GroupSection();
	            addField(ns, "another Fee", getLabel(LUConstants.FEE), path );
	            
	            return ns;
	        }
	    }
	    
    
    // TODO -if the sleaze for forcing a single item to 100% is to stay longer than short-term, factor
    // common sleaziness up into a superclass
    public class FinancialInformationList extends UpdatableMultiplicityComposite {
    	private Map<MultiplicityItem, Callback<MultiplicityItem>> removalCallbacks = new HashMap<MultiplicityItem, Callback<MultiplicityItem>>();
    	private Callback<MultiplicityItem> oneHundredPercentCallback = new Callback<MultiplicityItem>() {
            public void exec(MultiplicityItem itemToRemove) {
                removalCallbacks.get(itemToRemove).exec(itemToRemove);
                removalCallbacks.remove(itemToRemove);
                if ((getItems().size() - getRemovedItems().size()) == 1) {
                	MultiplicityItem currItem = null;
                	for (int i = 0; i < getItems().size(); i++) {
                		currItem = getItems().get(i);
                		if ( ! currItem.isDeleted() ) break;
                	}
                	if (null == currItem || currItem.isDeleted()) {
                		String errMsg = "Unable to find non-deleted item in FinancialInformationList's removal callback";
                		GWT.log(errMsg, new RuntimeException(errMsg));
                	}
                	// So, how's this for sleazy? Even sleazier than the addItem() sleaziness below
                	GroupSection gSection = (GroupSection) ((RemovableItemWithHeader) currItem).getItemWidget();
                	((KSTextBox) gSection.getFields().get(1).getFieldWidget()).setValue("100");
                }
            }
    	};
    	
        {
            setAddItemLabel(getLabel(LUConstants.ADD_ANOTHER_ORGANIZATION));
            setItemLabel(getLabel(LUConstants.ORGANIZATION));
        }
        private final String parentPath;
        public FinancialInformationList(String parentPath){
            super(StyleType.TOP_LEVEL);
            this.parentPath = parentPath;
        }
        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            addField(ns, AffiliatedOrgInfoConstants.ORG_ID, getLabel(LUConstants.REVENUE), path );
            FieldDescriptor fd = addField(ns, PERCENTAGE, getLabel(LUConstants.AMOUNT), path);
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
    
    public class ExpenditureList extends UpdatableMultiplicityComposite {
    	private Map<MultiplicityItem, Callback<MultiplicityItem>> removalCallbacks = new HashMap<MultiplicityItem, Callback<MultiplicityItem>>();
    	private Callback<MultiplicityItem> oneHundredPercentCallback = new Callback<MultiplicityItem>() {
            public void exec(MultiplicityItem itemToRemove) {
                removalCallbacks.get(itemToRemove).exec(itemToRemove);
                removalCallbacks.remove(itemToRemove);
                if ((getItems().size() - getRemovedItems().size()) == 1) {
                	MultiplicityItem currItem = null;
                	for (int i = 0; i < getItems().size(); i++) {
                		currItem = getItems().get(i);
                		if ( ! currItem.isDeleted() ) break;
                	}
                	if (null == currItem || currItem.isDeleted()) {
                		String errMsg = "Unable to find non-deleted item in ExpenditureList's removal callback";
                		GWT.log(errMsg, new RuntimeException(errMsg));
                	}
                	// So, how's this for sleazy?
                	GroupSection gSection = (GroupSection) ((RemovableItemWithHeader) currItem).getItemWidget();
                	((KSTextBox) gSection.getFields().get(1).getFieldWidget()).setValue("100");
                }
            }
    	};
        {
            setAddItemLabel(getLabel(LUConstants.ADD_ANOTHER_ORGANIZATION));
            setItemLabel(getLabel(LUConstants.ORGANIZATION));
        }
        private final String parentPath;
        public ExpenditureList(String parentPath){
            super(StyleType.TOP_LEVEL);
            this.parentPath = parentPath;
        }
        @Override
        public Widget createItem() {
        	
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            addField(ns, AffiliatedOrgInfoConstants.ORG_ID, getLabel(LUConstants.EXPENDITURE), path );
            FieldDescriptor fd = addField(ns, PERCENTAGE, getLabel(LUConstants.AMOUNT), path);

            return ns;
        }
        
        @Override
        public MultiplicityItem addItem() {
        	MultiplicityItem returnItem = super.addItem();
        	removalCallbacks.put(returnItem, returnItem.getRemoveCallback());
        	returnItem.setRemoveCallback(oneHundredPercentCallback);
        	if (getItems().size() == 1) {
        		// needs to be 100% for a single expenditure org
        		Widget txtBox = ((GroupSection) returnItem.getItemWidget()).getFields().get(1).getFieldWidget();
        		((KSTextBox) txtBox).setValue("100");
        	}
        	return returnItem;
        }
    }      
}
