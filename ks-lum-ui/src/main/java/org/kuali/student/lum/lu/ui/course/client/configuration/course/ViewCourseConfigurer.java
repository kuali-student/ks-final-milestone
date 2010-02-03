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

import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.DisplayMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.MetaInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityContactHoursConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseDurationConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseFormatConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.LearningObjectiveConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseRequisitesSectionView;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.LuConfigurer.LuSections;
import org.kuali.student.lum.lu.ui.course.client.configuration.viewclu.ViewCluConfigurer;
import org.kuali.student.lum.lu.ui.course.client.widgets.Collaborators;

import com.google.gwt.user.client.ui.Widget;


/**
 * This is the configuration factory class for creating a proposal.
 *
 * TODO: The following is a list of items that need to be fixed.
 *  1) All hardcoded drop downs need to be replaced with one populated via an enumeration lookup
 *  2) Any pickers (eg. org, course, needs to be replaced wtih proper lookup based search pickers
 *  
 * @author Kuali Student Team
 *
 */
public class ViewCourseConfigurer 
implements 
CreditCourseProposalConstants,
//CreditCourseProposalInfoConstants,
CreditCourseConstants,
CreditCourseFormatConstants,
CreditCourseActivityConstants,
MetaInfoConstants,
CreditCourseDurationConstants,
FeeInfoConstants,
LearningObjectiveConstants
{

    private static final String COURSE_DETAILS_TAB = "Course Details";
    
    //FIXME: Need to fix this field key 
    private static final String CONSTANT_ORG_ID = "orgId";
    
    //FIXME:  Initialize type and state
    private String type = "course";
    private String state = "draft";
    private String groupName;

    private boolean WITH_DIVIDER = true;
    private boolean NO_DIVIDER = false;

    private DataModelDefinition modelDefinition;

    public void setModelDefinition(DataModelDefinition modelDefinition){
        this.modelDefinition = modelDefinition;
    }

    public void generateLayout(ConfigurableLayout layout) {
        groupName = LUConstants.COURSE_GROUP_NAME;

//      addCluStartSection(layout);

        layout.addSection(new String[] {COURSE_DETAILS_TAB, getLabel(LUConstants.COURSE_INFORMATION_LABEL_KEY)}, generateGovernanceSection());
        layout.addSection(new String[] {COURSE_DETAILS_TAB, getLabel(LUConstants.COURSE_INFORMATION_LABEL_KEY)}, generateCourseLogisticsSection());
        layout.addSection(new String[] {COURSE_DETAILS_TAB, getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateCourseInfoSection());
        layout.addSection(new String[] {COURSE_DETAILS_TAB, getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateLearningObjectivesSection());
        layout.addSection(new String[] {COURSE_DETAILS_TAB, getLabel(LUConstants.STUDENT_ELIGIBILITY_LABEL_KEY)}, generateCourseRequisitesSection());
        layout.addSection(new String[] {COURSE_DETAILS_TAB, getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateActiveDatesSection());
        layout.addSection(new String[] {COURSE_DETAILS_TAB, getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateFinancialsSection());   
//      layout.addSection(new String[] {getLabel(LUConstants.SUMMARY_LABEL_KEY)}, generateSummarySection());
//      layout.addSection(new String[] {"Assembler Test"}, new AssemblerTestSection(LuSections.ASSEMBLER_TEST, "Assembler Test"));

//      layout.addTool(new CollaboratorTool());
//      layout.addTool(new CommentPanel(LuSections.COMMENTS, LUConstants.TOOL_COMMENTS));
        layout.addTool(new DocumentTool(LuSections.DOCUMENTS, LUConstants.TOOL_DOCUMENTS));
    }

    public SectionView generateSummarySection(){
        VerticalSectionView section = initSectionView(LuSections.SUMMARY, LUConstants.SUMMARY_LABEL_KEY); 

        section.addSection(generateSummaryBrief(getH3Title(LUConstants.BRIEF_LABEL_KEY)));
        section.addSection(ViewCluConfigurer.generateSummaryDetails(getH3Title(LUConstants.FULL_VIEW_LABEL_KEY)));

        return section;
    }

    private VerticalSection generateSummaryBrief(SectionTitle title) {
        VerticalSection section = new VerticalSection();
//      section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
//      section.addStyleName(LUConstants.STYLE_SECTION);
//      section.setSectionTitle(title);
//      addField(section, PROPOSAL + "/" + TITLE, getLabel(LUConstants.TITLE_LABEL_KEY) +":    ", new KSLabel());
//      addField(section, COURSE + "/" + SUBJECT_AREA, getLabel(LUConstants.DIVISION_LABEL_KEY), new KSLabel());
//      addField(section, COURSE + "/" + COURSE_NUMBER_SUFFIX, getLabel(LUConstants.SUFFIX_CODE_LABEL_KEY), new KSLabel());


//      // FIXME wilj: add proposal/delegate/collab person info to assembly
//      addField(section, PROPOSAL + "/" + PROPOSER_PERSON, getLabel(LUConstants.PROPOSER_LABEL_KEY), new ProposerPersonList());
//      addField(section, "proposalInfo/todo", getLabel(LUConstants.DELEGATE_LABEL_KEY), new KSLabel());
//      addField(section, "proposalInfo/todo", getLabel(LUConstants.COLLABORATORS_LABEL_KEY), new KSLabel());

//      // FIXME wilj: add create/update meta info to assembly
//      addField(section, PROPOSAL + "/" + META_INFO + "/" + CREATE_TIME, getLabel(LUConstants.CREATED_DATE_LABEL_KEY), new KSLabel());
//      addField(section, PROPOSAL + "/" + META_INFO + "/" + UPDATE_TIME, getLabel(LUConstants.LAST_CHANGED_DATE_LABEL_KEY), new KSLabel());

//      addField(section, COURSE + "/" + DESCRIPTION + "/" + RichTextInfoConstants.PLAIN, getLabel(LUConstants.DESCRIPTION_LABEL_LABEL_KEY), new KSLabel());
//      // TODO: Norm: find out why was this prefixed with proposal there is no state on proposal it is on the main object
//      addField(section, CreditCourseProposalConstants.STATE, getLabel(LUConstants.STATUS_LABEL_KEY), new KSLabel());
        return section;
    } 

//  public void addCluStartSection(ConfigurableLayout layout){
//  VerticalSectionView section = initSectionView(LuSections.CLU_BEGIN, LUConstants.START_LABEL_KEY); 

////addField(section, PROPOSAL + "/" + TITLE , getLabel(LUConstants.PROPOSAL_TITLE_LABEL_KEY));
//////This will need to be a person picker
////// FIXME wilj: add proposal/delegate/collab person info to assembly
////addField(section, PROPOSAL + "/" + PROPOSER_PERSON, getLabel(LUConstants.PROPOSAL_PERSON_LABEL_KEY), new PersonList()) ;
//  layout.addStartSection(section);
//  }

    /**
     * Adds a section for adding or modifying rules associated with a given course (program).
     *
     * @param layout - a content pane to which this section is added to
     * @return
     */
    private SectionView generateCourseRequisitesSection() {
        
        
        CourseRequisitesSectionView section = new CourseRequisitesSectionView(LuSections.COURSE_REQUISITES, getLabel(LUConstants.REQUISITES_LABEL_KEY));
        section.setSectionTitle(SectionTitle.generateH1Title(getLabel(LUConstants.REQUISITES_LABEL_KEY)));

        /* TODO: Once we have a section that allows for flow between rule screens
        VerticalSection enrollmentSection = new VerticalSection();
        enrollmentSection.setSectionTitle(SectionTitle.generateH2Title("Enrollment Restrictions"));
        enrollmentSection.addField(new FieldDescriptor("rationalle", "Rationalle", Type.STRING));
        enrollmentSection.addField(new FieldDescriptor("rules", "Rules", Type.STRING));
        enrollmentSection.addWidget(new KSButton());
        section.addSection(enrollmentSection);   */

        return section;

    }

    private SectionView generateActiveDatesSection() {
        VerticalSectionView section = initSectionView(LuSections.ACTIVE_DATES, LUConstants.ACTIVE_DATES_LABEL_KEY); 

        //TODO need a format for these dates
        VerticalSection startDate = initSection(getH3Title(LUConstants.START_DATE_LABEL_KEY), WITH_DIVIDER);
        addField(startDate, EFFECTIVE_DATE, getLabel(LUConstants.EFFECTIVE_DATE_LABEL_KEY), new KSLabel());

        VerticalSection endDate = initSection(getH3Title(LUConstants.END_DATE_LABEL_KEY), WITH_DIVIDER);
        addField(endDate, EXPIRATION_DATE, getLabel(LUConstants.EXPIRATION_DATE_LABEL_KEY), new KSLabel());

        section.addSection(startDate);
        section.addSection(endDate);

        return section;
    }

    private SectionView generateFinancialsSection() {
        VerticalSectionView section = initSectionView(LuSections.FINANCIALS, LUConstants.FINANCIALS_LABEL_KEY); 

        //TODO ALL KEYS in this section are place holders until we know actual keys
        VerticalSection feeType = initSection(getH3Title(LUConstants.FEE_TYPE_LABEL_KEY), WITH_DIVIDER);
        addField(feeType, "cluInfo/feeType", null, new KSLabel());

        VerticalSection feeAmount = initSection(getH3Title(LUConstants.FEE_DESC_LABEL_KEY), WITH_DIVIDER);
        addField(feeAmount, FEES +"/" + FEE_AMOUNT, getLabel(LUConstants.CURRENCY_SYMBOL_LABEL_KEY), new KSLabel());
        addField(feeAmount, FEES + "/" + TAXABLE, getLabel(LUConstants.TAXABLE_SYMBOL_LABEL_KEY), new KSLabel());//TODO checkboxes go here instead
        addField(feeAmount, FEES + "/" + FEE_DESC, getLabel(LUConstants.FEE_DESC_LABEL_KEY), new KSLabel());
        addField(feeAmount, FEES + "/" + INTERNAL_NOTATION, getLabel(LUConstants.INTERNAL_FEE_NOTIFICATION_LABEL_KEY), new KSLabel());

        section.addSection(feeType);
        section.addSection(feeAmount);

        return section;
    }

    public SectionView generateGovernanceSection(){
        VerticalSectionView section = initSectionView(LuSections.GOVERNANCE, LUConstants.GOVERNANCE_LABEL_KEY); 

        VerticalSection oversight = initSection(getH3Title(LUConstants.CURRICULUM_OVERSIGHT_LABEL_KEY), WITH_DIVIDER);    
//FIXME:        addField(oversight, ACADEMIC_SUBJECT_ORGS, null, new CurriculumOversightList(ACADEMIC_SUBJECT_ORGS));

        VerticalSection campus = initSection(getH3Title(LUConstants.CAMPUS_LOCATION_LABEL_KEY), WITH_DIVIDER);    
        addField(campus, CAMPUS_LOCATIONS, null, new CampusLocationList());

        VerticalSection adminOrgs = initSection(getH3Title(LUConstants.ADMIN_ORG_LABEL_KEY), WITH_DIVIDER);    
        addField(adminOrgs, DEPARTMENT, null, new KSLabel());
//        addField(adminOrgs, COURSE + "/" + ALT_ADMIN_ORGS, null, new AlternateAdminOrgList(COURSE + "/" + ALT_ADMIN_ORGS));    

        section.addSection(oversight);
        section.addSection(campus);
        section.addSection(adminOrgs);

        return section;

    }

    public SectionView generateCourseInfoSection(){
        VerticalSectionView section = initSectionView(LuSections.COURSE_INFO, LUConstants.INFORMATION_LABEL_KEY); 

        //FIXME: Label should be key to messaging, field type should come from dictionary?

        //COURSE NUMBER
        GroupSection courseIdentifier = new GroupSection();
        courseIdentifier.addStyleName(LUConstants.STYLE_SECTION);
        courseIdentifier.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        courseIdentifier.setSectionTitle(getH3Title(LUConstants.IDENTIFIER_LABEL_KEY)); 
        //courseIdentifier.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
        addField(courseIdentifier, SUBJECT_AREA, null, new KSLabel());
        addField(courseIdentifier,  COURSE_NUMBER_SUFFIX, null, new KSLabel());

        // TODO - hide cross-listed, offered jointly and version codes initially, with
        // clickable label to disclose them

//FIXME:        // Cross-listed
//        VerticalSection crossListed = new VerticalSection();
//        crossListed.setSectionTitle(getH3Title(LUConstants.CROSS_LISTED_ALT_LABEL_KEY));
//        // crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
//        addField(crossListed,  CROSS_LISTINGS, null, new CrossListedList());
//        crossListed.addStyleName("KS-LUM-Section-Divider");
//        courseIdentifier.addSection(crossListed);
//
//        // Offered jointly
//        VerticalSection offeredJointly = new VerticalSection();
//        offeredJointly.setSectionTitle(getH3Title(LUConstants.JOINT_OFFERINGS_ALT_LABEL_KEY));
////      TODO     addField(offeredJointly, COURSE + "/" + JOINTS, null, new OfferedJointlyList());
//        offeredJointly.addStyleName("KS-LUM-Section-Divider");
//        courseIdentifier.addSection(offeredJointly);
//
//        //Version Codes
//        VerticalSection versionCodes = new VerticalSection();
//        versionCodes.setSectionTitle(getH3Title(LUConstants.VERSION_CODES_LABEL_KEY));
//        addField(versionCodes,  VERSIONS, null, new VersionCodeList());
//        versionCodes.addStyleName("KS-LUM-Section-Divider");
//        courseIdentifier.addSection(versionCodes);

        VerticalSection longTitle = initSection(getH3Title(LUConstants.TITLE_LABEL_KEY), WITH_DIVIDER);
        addField(longTitle,  COURSE_TITLE, null, new KSLabel());

        VerticalSection shortTitle = initSection(getH3Title(LUConstants.SHORT_TITLE_LABEL_KEY), WITH_DIVIDER);
        addField(shortTitle,  TRANSCRIPT_TITLE, null, new KSLabel());

        VerticalSection description = initSection(getH3Title(LUConstants.DESCRIPTION_LABEL_KEY), WITH_DIVIDER);
        addField(description, DESCRIPTION, null, new KSLabel());

        //TODO Confirm we don't need rationale
//      VerticalSection rationale = initSection(getH3Title(LUConstants.RATIONALE_LABEL_KEY), WITH_DIVIDER);
//      addField(rationale, COURSE + "/" + RATIONALE, null, new KSLabel());

        section.addSection(courseIdentifier);
        section.addSection(shortTitle);
        section.addSection(longTitle);
        section.addSection(description);
//      section.addSection(rationale);

        return section;
    }

//  public Callback<Boolean> getSubjectValidationCallback(final FieldDescriptor subjectFD, final String objectKey){
    //      return new Callback<Boolean>() {
    //        @Override
    //      public void exec(Boolean result) {
    //        ModelDTOConstraintSetupFactory bc = new ModelDTOConstraintSetupFactory();
    //      final Validator val = new Validator(bc, true);
    //    final ValidateResultEvent e = new ValidateResultEvent();
    //          ObjectStructure objStructure = Application.getApplicationContext().getDictionaryData(objectKey);
    //        if(objStructure == null){
    //          Window.alert("Cannot load dictionary(object structure)");
    //    }
    //  List<ValidationResultContainer> results = val.validateTypeStateObject((ModelDTO) m.get(), objStructure);
    //e.setValidationResult(results);// filled by calling the real validate code
    //Controller.findController(subjectFD.getFieldWidget()).fireApplicationEvent(e);


//  }
    //      };
    // }



    public SectionView generateCourseLogisticsSection() {
        VerticalSectionView section = initSectionView(LuSections.COURSE_LOGISTICS, LUConstants.LOGISTICS_LABEL_KEY); 

        VerticalSection instructors = initSection(getH3Title(LUConstants.INSTRUCTOR_LABEL_KEY), WITH_DIVIDER);
        // FIXME wilj: do we need to set the instructor's orgId? or can we default it at the assembler level?
        addField(instructors,  PRIMARY_INSTRUCTOR, null, new KSLabel());
//      TODO        addField(instructors, COURSE + "/" + ALTERNATE_INSTRUCTORS, null, new AlternateInstructorList());
//      instructors.addField(new FieldDescriptor("cluInfo/instructors", null, Type.LIST, new AlternateInstructorList()));

        //CREDITS
        //TODO: needs to be mapped to learning results

        VerticalSection scheduling = initSection(getH3Title(LUConstants.SCHEDULING_LABEL_KEY), WITH_DIVIDER);
        // FIXME wilj: termsOffered not currently populated by assembler
        addField(scheduling,  CreditCourseConstants.DURATION + "/" + TERM_TYPE, getLabel(LUConstants.TERM_LITERAL_LABEL_KEY), new AtpTypeList());
        addField(scheduling, CreditCourseConstants.DURATION + "/" + QUANTITY, getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY), new KSLabel()); //TODO DURATION ENUMERATION


        //COURSE FORMATS
        VerticalSection courseFormats = initSection(getH3Title(LUConstants.FORMATS_LABEL_KEY), WITH_DIVIDER);
        addField(courseFormats,  FORMATS, null, new CourseFormatList(FORMATS));

        section.addSection(instructors);
//      section.addSection(learningResults);
        section.addSection(scheduling);
        section.addSection(courseFormats);


        return section;
    }

    private SectionView generateLearningObjectivesSection() {
        VerticalSectionView section = initSectionView(LuSections.LEARNING_OBJECTIVES, LUConstants.LEARNING_OBJECTIVES_LABEL_KEY); 

        VerticalSection los = initSection(null, NO_DIVIDER);    
        addField(los, LEARNING_OBJECTIVES, null, new LearningObjectiveList());
//      LOBuilder builder = new LOBuilder(type, state, groupName);
//      addField(los, LEARNING_OBJECTIVES, null, builder);
//      los.addStyleName("KS-LUM-Section-Divider");
//      // So, how stinky is this HACK?
//      { // TODO - get rid of this in M4!!!
//      assert (los.getFields().size() == 1 && los.getFields().get(0).getFieldWidget() instanceof LOBuilder); 
//      los.getFields().get(0).setWidgetBinding(HasModelDTOValueWidgetBinding.INSTANCE);
//      }

        section.addSection(los);
        return section;        
    }

    private class AlternateAdminOrgList extends DisplayMultiplicityComposite{

        private final String parentPath;
        public AlternateAdminOrgList(String parentPath){
            this.parentPath = parentPath;
//            setItemLabel(getLabel(LUConstants.FORMAT_LABEL_KEY));
        }

        public Widget createItem() {
            VerticalSection item = new VerticalSection();
            addField(item, CONSTANT_ORG_ID, null, new KSLabel(), parentPath);
            return item;
        }
    }

    private class AlternateInstructorList extends DisplayMultiplicityComposite{

        private final String parentPath;
        public AlternateInstructorList(String parentPath){
            this.parentPath = parentPath;
//            setItemLabel(getLabel(LUConstants.FORMAT_LABEL_KEY));
        }

        public Widget createItem() {
            VerticalSection item = new VerticalSection();
            addField(item, "personId", null, new KSLabel(), parentPath);
            return item;
        }
    }
    public static class AtpTypeList extends KSLabelList {
        public AtpTypeList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }

    public class CampusLocationList extends KSLabelList {
        public CampusLocationList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }

    public class CluActivityTypeList extends KSLabelList{
        public CluActivityTypeList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }

    public class ContactHoursAtpTypeList extends KSLabelList{
        public ContactHoursAtpTypeList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }

    }

    public class CourseActivityList extends DisplayMultiplicityComposite {

        private final String parentPath;
        public CourseActivityList(String parentPath){
            this.parentPath = parentPath;
//            setItemLabel(getLabel(LUConstants.ACTIVITY_LITERAL_LABEL_KEY));
        }

        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection activity = new GroupSection();
            addField(activity, ACTIVITY_TYPE, getLabel(LUConstants.ACTIVITY_TYPE_LABEL_KEY), new CluActivityTypeList(), parentPath);
            activity.nextLine();

            /* CreditInfo is deprecated, needs to be replaced with learning results
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("creditInfo", "Credit Value", Type.STRING));
            activity.nextRow();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("evalType", "Learning Result", Type.STRING));
            activity.nextRow();
             */

            //activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            // FIXME need to get the term offered added to the activity metadata?
//          activity.addField(new FieldDescriptor("term", getLabel(LUConstants.TERM_LITERAL_LABEL_KEY), Type.STRING, new AtpTypeList())); 
            addField(activity, CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.QUANTITY, getLabel(LUConstants.DURATION_LITERAL_LABEL_KEY), path);
            addField(activity, CreditCourseActivityConstants.DURATION + "/" + CreditCourseActivityDurationConstants.TIME_UNIT, "Duration Type", new DurationAtpTypeList(), path);

            activity.nextLine();
            //activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            addField(activity, CONTACT_HOURS + "/" + CreditCourseActivityContactHoursConstants.HRS, "Contact Hours" , path);
            // FIXME look up what the label and implement as dropdown
            addField(activity, CONTACT_HOURS + "/" + CreditCourseActivityContactHoursConstants.PER, null,  new ContactHoursAtpTypeList(), path);
            addField(activity, DEFAULT_ENROLLMENT_ESTIMATE, getLabel(LUConstants.CLASS_SIZE_LABEL_KEY), path);

            return activity;
        }

    }

    public class CourseFormatList extends DisplayMultiplicityComposite {
        private final String parentPath;
        public CourseFormatList(String parentPath){
            this.parentPath = parentPath;
//            setItemLabel(getLabel(LUConstants.FORMAT_LABEL_KEY));
        }

        public Widget createItem() {
            VerticalSection item = new VerticalSection();
            addField(item, ACTIVITIES, null, new CourseActivityList(QueryPath.concat(parentPath, String.valueOf(itemCount-1), ACTIVITIES).toString()), parentPath);
            return item;
        }
    }
    
    public class CrossListedList extends DisplayMultiplicityComposite {        
        {
//            setItemLabel(getLabel(LUConstants.CROSS_LISTED_ITEM_LABEL_KEY));
        }

        @Override
        public Widget createItem() {
            GroupSection ns = new GroupSection();
            //ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            addField(ns, DEPARTMENT, getLabel(LUConstants.DEPT_LABEL_KEY), new KSLabel());
            ns.nextLine();
            //ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            addField(ns, SUBJECT_AREA, getLabel(LUConstants.SUBJECT_CODE_LABEL_KEY), new KSLabel());
            addField(ns, COURSE_NUMBER_SUFFIX, getLabel(LUConstants.COURSE_NUMBER_LABEL_KEY), new KSLabel());

            return ns;
        }
            }
    
    public class CurriculumOversightList extends DisplayMultiplicityComposite{

        private final String parentPath;
        public CurriculumOversightList(String parentPath){
            this.parentPath = parentPath;
//            setItemLabel(getLabel(LUConstants.ACTIVITY_LITERAL_LABEL_KEY));
        }

        public Widget createItem() {
            GroupSection ns = new GroupSection();
            //ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            addField(ns, CONSTANT_ORG_ID, null, new KSLabel());
                         
            return ns;
        }

    }

    public class DurationAtpTypeList extends KSLabelList{
        public DurationAtpTypeList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }

    }

    private class LearningObjectiveList extends DisplayMultiplicityComposite {
        {
//            setItemLabel(getLabel(LUConstants.VERSION_CODE_LABEL_KEY));
        }

        @Override
        public Widget createItem() {
            GroupSection ns = new GroupSection();
            //ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            addField(ns, "desc", getLabel(LUConstants.LEARNING_OBJECTIVE_LABEL_KEY));

            return ns;
        }
    }  

    public class VersionCodeList extends DisplayMultiplicityComposite {
        {
//            setItemLabel(getLabel(LUConstants.VERSION_CODE_LABEL_KEY));
        }

        @Override
        public Widget createItem() {
            GroupSection ns = new GroupSection();
            //ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            addField(ns, "versionCode", getLabel(LUConstants.CODE_LABEL_KEY), new KSLabel());
            addField(ns, "versionTitle", getLabel(LUConstants.TITLE_LITERAL_LABEL_KEY), new KSLabel());

            return ns;
        }
    }

    public SectionView generateProgramInfoSection(){
        VerticalSectionView section = initSectionView(LuSections.PROGRAM_INFO, LUConstants.INFORMATION_LABEL_KEY); 

        VerticalSection shortTitle = initSection(getH3Title(LUConstants.SHORT_TITLE_LABEL_KEY), WITH_DIVIDER);
        addField(shortTitle, "cluInfo/officialIdentifier/shortName", null);       

        section.addSection(shortTitle);

        return section;
    }    

    public class CollaboratorTool extends ToolView{
        public CollaboratorTool(){
            super(LuSections.AUTHOR, LUConstants.SECTION_AUTHORS_AND_COLLABORATORS);
        }

        @Override
        protected Widget createWidget() {
            return new Collaborators();
        }

    }

    private VerticalSectionView initSectionView (Enum<?> viewEnum, String labelKey) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), CourseConfigurer.CLU_PROPOSAL_MODEL);
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(getH1Title(labelKey));

        return section;
    }


    private VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection();
        if (title !=  null) {
            section.setSectionTitle(title);
        }
        section.addStyleName(LUConstants.STYLE_SECTION);
        if (withDivider)
            section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        return section;
    }

    private String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(groupName, type, state, labelKey);
    }

    private SectionTitle getH1Title(String labelKey) {
        return SectionTitle.generateH1Title(getLabel(labelKey));
    } 

    private SectionTitle getH2Title(String labelKey) {
        return SectionTitle.generateH2Title(getLabel(labelKey));
    } 

    private SectionTitle getH3Title(String labelKey) {
        return SectionTitle.generateH3Title(getLabel(labelKey));
    } 

    private SectionTitle getH4Title(String labelKey) {
        return SectionTitle.generateH4Title(getLabel(labelKey));
    } 

    private SectionTitle getH5Title(String labelKey) {
        return SectionTitle.generateH5Title(getLabel(labelKey));
    }


    private void addField(Section section, String fieldKey, String fieldLabel) {
        addField(section, fieldKey, fieldLabel, null, null);
    }
    private void addField(Section section, String fieldKey, String fieldLabel, Widget widget) {
        addField(section, fieldKey, fieldLabel, widget, null);
    }
    private void addField(Section section, String fieldKey, String fieldLabel, String parentPath) {
        addField(section, fieldKey, fieldLabel, null, parentPath);
    }
    private void addField(Section section, String fieldKey, String fieldLabel, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        FieldDescriptor fd = new FieldDescriptor(path.toString(), fieldLabel, meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        section.addField(fd);
    }

    // TODO look up field label and type from dictionary & messages

//  private Type translateDictType(String dictType) {
//  if (dictType.equalsIgnoreCase("String"))
//  return Type.STRING;
//  else
//  return null;
//  }

//  private FieldDescriptor createMVCFieldDescriptor(String fieldName,
//  String objectKey, String type, String state  ) {

//  Field f = LUDictionaryManager.getInstance().getField(objectKey, type, state, fieldName);

//  FieldDescriptor fd =
//  new FieldDescriptor(f.getKey(),
//  getLabel(type, state, f.getKey() ),
//  translateDictType(f.getFieldDescriptor().getDataType())
//  );
//  return fd;
//  }


//  private String getLabel(String type, String state, String fieldId) {
//  String labelKey = type + ":" + state + ":" + fieldId;
//  System.out.println(labelKey);
//  return context.getMessage(labelKey);
//  }


}