/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.configuration.viewclu;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.CustomNestedSection;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.MultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.configurable.mvc.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.VerticalSectionView;
import org.kuali.student.common.ui.client.configurable.mvc.Section.FieldLabelType;
import org.kuali.student.common.ui.client.dictionary.DictionaryManager;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.Type;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.commenttool.CommentPanel;
import org.kuali.student.common.ui.client.widgets.documenttool.DocumentTool;
import org.kuali.student.common.ui.client.widgets.list.KSLabelList;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO;
import org.kuali.student.lum.lu.ui.course.client.widgets.Collaborators;

import com.google.gwt.user.client.ui.Widget;


/**
 * This is the configuration factory class for creating a proposal. 
 * 
 * @author Kuali Student Team
 *
 */
public class ViewCluConfigurer {


    
    private static boolean WITH_DIVIDER = true;
    private static boolean NO_DIVIDER = false;

    private static String type;
    private static String state;

    public enum LuSections{
        CLU_BEGIN, AUTHOR,  GOVERNANCE, COURSE_LOGISTICS, COURSE_INFO, LEARNING_OBJECTIVES, 
        COURSE_RESTRICTIONS, COURSE_REQUISITES, ACTIVE_DATES, FINANCIALS, PGM_REQUIREMENTS, 
        ATTACHMENTS, COMMENTS, DOCUMENTS
    }

    public static void generateLayout(ConfigurableLayout layout){     

        layout.addSection(new String[] {getLabel(LUConstants.PROPOSAL_INFORMATION_LABEL_KEY)}, generateGovernanceSection());
        layout.addSection(new String[] {getLabel(LUConstants.PROPOSAL_INFORMATION_LABEL_KEY)}, generateCourseLogisticsSection());

        layout.addSection(new String[] {getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateInformationSection());
        layout.addSection(new String[] {getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateLearningObjectivesSection());

        layout.addSection(new String[] {getLabel(LUConstants.STUDENT_ELIGIBILITY_LABEL_KEY)}, generateRestrictionsSection());
        layout.addSection(new String[] {getLabel(LUConstants.STUDENT_ELIGIBILITY_LABEL_KEY)}, generateRequisitesSection());

        layout.addSection(new String[] {getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateActiveDatesSection());               
        layout.addSection(new String[] {getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateFinancialsSection());
        layout.addSection(new String[] {getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateProgramRequirementsSection());


        //TODO Need a display version of these Tools
        layout.addTool(new CollaboratorTool());
        layout.addTool(new CommentPanel(LuSections.COMMENTS, "View Comments"));
        layout.addTool(new DocumentTool(LuSections.DOCUMENTS, "View Attached Documents"));

    }    

    //Top Level Sections here
   

    private static SectionView generateGovernanceSection(){
        VerticalSectionView section = new VerticalSectionView(LuSections.GOVERNANCE, getLabel(LUConstants.GOVERNANCE_LABEL_KEY), CluProposalModelDTO.class);        
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.GOVERNANCE_LABEL_KEY)));

        //FIXME: Label should come from messaging, field type should come from dictionary?
//      section.addField(createMVCFieldDescriptor("campusLocationInfo", LUConstants.STRUCTURE_CLU_INFO, type, state));

        section.addSection(generateCurriculumOversight(getH3Title(LUConstants.CURRICULUM_OVERSIGHT_LABEL_KEY), WITH_DIVIDER));
        section.addSection(generateCampusLocation(getH3Title(LUConstants.CAMPUS_LOCATION_LABEL_KEY), WITH_DIVIDER));
        section.addSection(generateAdminOrgs(getH3Title(LUConstants.ADMIN_ORGS_LABEL_KEY), WITH_DIVIDER));

        return section;

    }

    private static SectionView generateCourseLogisticsSection(){
        VerticalSectionView section = new VerticalSectionView(LuSections.COURSE_LOGISTICS, getLabel(LUConstants.LOGISTICS_LABEL_KEY), CluProposalModelDTO.class);
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.LOGISTICS_LABEL_KEY)));

        section.addSection(generateInstructors(getH3Title(LUConstants.INSTRUCTORS_LABEL_KEY), WITH_DIVIDER));
        section.addSection(generateCredits(getH3Title(LUConstants.CREDITS_LABEL_KEY), WITH_DIVIDER));
        section.addSection(generateLearningResults(getH3Title(LUConstants.LEARNING_RESULTS_LABEL_KEY), WITH_DIVIDER));
        section.addSection(generateScheduling(getH3Title(LUConstants.SCHEDULING_LABEL_KEY), WITH_DIVIDER));
        section.addSection(generateFormats(getH3Title(LUConstants.FORMATS_LABEL_KEY), WITH_DIVIDER));

        return section;

    }
    private static SectionView generateInformationSection(){
        VerticalSectionView section = new VerticalSectionView(LuSections.COURSE_INFO, getLabel(LUConstants.INFORMATION_LABEL_KEY), CluProposalModelDTO.class);        
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.INFORMATION_LABEL_KEY)));

        section.addSection(generateIdentifiers(getH3Title(LUConstants.IDENTIFIERS_LABEL_KEY), WITH_DIVIDER));
        // TODO Next 3 sections )advanced options) should be in a disclosure panel       
//      section.addSection(generateCrossListedSection());
//      section.addSection(generateJointOfferingsSection());
//      section.addSection(generateVersionCodesSection());
        section.addSection(generateTitles(getH3Title(LUConstants.TITLE_LABEL_KEY), WITH_DIVIDER));
         section.addSection(generateDescriptions(getH3Title(LUConstants.DESCRIPTION_LABEL_KEY), WITH_DIVIDER));

        return section;        

    }
 
    private static SectionView generateActiveDatesSection() {
        VerticalSectionView section = new VerticalSectionView(LuSections.ACTIVE_DATES, getLabel(LUConstants.ACTIVE_DATES_LABEL_KEY), CluProposalModelDTO.class);
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.ACTIVE_DATES_LABEL_KEY)));

        section.addSection(generateDates(null, WITH_DIVIDER));
        return section; 
    }


    
    
    private static SectionView generateLearningObjectivesSection() {
        VerticalSectionView section = new VerticalSectionView(LuSections.LEARNING_OBJECTIVES, getLabel(LUConstants.LEARNING_OBJECTIVES_LABEL_KEY), CluProposalModelDTO.class);
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.LEARNING_OBJECTIVES_LABEL_KEY)));

        section.addField(new FieldDescriptor("learningObjectives", "TO DO", Type.STRING, new KSLabel()));

        return section;        

    }
    
    private static SectionView generateFinancialsSection() {
        VerticalSectionView section = new VerticalSectionView(LuSections.FINANCIALS, getLabel(LUConstants.FINANCIALS_LABEL_KEY), CluProposalModelDTO.class);
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.FINANCIALS_LABEL_KEY)));

        //TODO ALL KEYS in this section are place holders until we know actual keys
        VerticalSection feeTypeSection = new VerticalSection();
        feeTypeSection.setSectionTitle(getH3Title(LUConstants.FEE_TYPE_LABEL_KEY));
        feeTypeSection.addField(new FieldDescriptor("cluInfo/feeinfo/feeType", null, Type.STRING, new KSLabel()));

        VerticalSection feeAmountSection = new VerticalSection();
        feeAmountSection.setSectionTitle(getH3Title(LUConstants.FEE_AMOUNT_LABEL_KEY));
        feeAmountSection.addField(new FieldDescriptor("cluInfo/feeinfo/feeAmount", "$ :   ", Type.STRING, new KSLabel()));
        feeAmountSection.addField(new FieldDescriptor("cluInfo/feeinfo/taxable", "Taxable :   ", Type.STRING, new KSLabel()));//TODO checkboxes go here instead
        feeAmountSection.addField(new FieldDescriptor("cluInfo/feeinfo/feeDesc", "Description :   ", Type.STRING, new KSLabel()));
        feeAmountSection.addField(new FieldDescriptor("cluInfo/feeinfo/internalNotation", "Internal Fee Notation :   ", Type.STRING, new KSLabel()));

        section.addSection(feeTypeSection);
        section.addSection(feeAmountSection);
        return section;
    }

    private static SectionView generateRestrictionsSection() {
        VerticalSectionView section = new VerticalSectionView(LuSections.COURSE_RESTRICTIONS, getLabel(LUConstants.RESTRICTIONS_LABEL_KEY), CluProposalModelDTO.class);
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.RESTRICTIONS_LABEL_KEY)));
        section.addField(new FieldDescriptor("courseRestrictions", "TO DO", Type.STRING, new KSLabel()));

        return section;        

    }

    private static SectionView generateRequisitesSection() {
        VerticalSectionView section = new VerticalSectionView(LuSections.COURSE_REQUISITES, getLabel(LUConstants.REQUISITES_LABEL_KEY), CluProposalModelDTO.class);
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.REQUISITES_LABEL_KEY)));

        VerticalSection preqSection = new VerticalSection();
        preqSection.setSectionTitle(getH3Title(LUConstants.PREQS_LABEL_KEY));
        preqSection.addField(new FieldDescriptor("prerequisites", "TO DO", Type.STRING, new KSLabel()));

        VerticalSection creqSection = new VerticalSection();
        creqSection.setSectionTitle(getH3Title(LUConstants.CREQS_LABEL_KEY));
        creqSection.addField(new FieldDescriptor("corequisites", "TO DO", Type.STRING, new KSLabel()));

        section.addSection(preqSection);
        section.addSection(creqSection);

        return section;        

    }
    
    private static SectionView generateProgramRequirementsSection() {
        VerticalSectionView section = new VerticalSectionView(LuSections.PGM_REQUIREMENTS, getLabel(LUConstants.PROGRAM_REQUIREMENTS_LABEL_KEY), CluProposalModelDTO.class);
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.PROGRAM_REQUIREMENTS_LABEL_KEY)));

        VerticalSection generalReqSection = new VerticalSection();
        generalReqSection.setSectionTitle(getH3Title(LUConstants.GENERAL_REQS_LABEL_KEY));
        generalReqSection.addField(new FieldDescriptor("genRequirements", "TO DO", Type.STRING, new KSLabel()));

        VerticalSection deptReqSection = new VerticalSection();
        deptReqSection.setSectionTitle(getH3Title(LUConstants.DEPT_REQS_LABEL_KEY));
        deptReqSection.addField(new FieldDescriptor("deptRequirements", "TO DO", Type.STRING, new KSLabel()));

        section.addSection(generalReqSection);
        section.addSection(deptReqSection);
        return section;

    }
    
    //Sub Sections here


    
    public static VerticalSection generateSummaryDetails(SectionTitle title) {
        VerticalSection section = initSection(title, true);

        VerticalSection governance = new VerticalSection();
        governance.addStyleName(LUConstants.STYLE_SECTION);
        governance.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        governance.setSectionTitle(getH4Title(LUConstants.GOVERNANCE_LABEL_KEY));
        governance.addSection(generateCurriculumOversight(getH5Title(getLabel(LUConstants.CURRICULUM_OVERSIGHT_LABEL_KEY)), NO_DIVIDER));
        governance.addSection(generateCampusLocation(getH5Title(getLabel(LUConstants.CAMPUS_LOCATION_LABEL_KEY)), NO_DIVIDER));
        governance.addSection(generateAdminOrgs(getH5Title(getLabel(LUConstants.ADMIN_ORGS_LABEL_KEY)), NO_DIVIDER));

        VerticalSection logistics = new VerticalSection();
        logistics.addStyleName(LUConstants.STYLE_SECTION);
        logistics.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        logistics.setSectionTitle(getH4Title(LUConstants.LOGISTICS_LABEL_KEY));
        logistics.addSection(generateInstructors((getH5Title(LUConstants.INSTRUCTORS_LABEL_KEY)), NO_DIVIDER));
        logistics.addSection(generateCredits(getH5Title(LUConstants.CREDITS_LABEL_KEY), NO_DIVIDER));
        logistics.addSection(generateLearningResults(getH5Title(LUConstants.LEARNING_RESULTS_LABEL_KEY), NO_DIVIDER));
        logistics.addSection(generateScheduling(getH5Title(LUConstants.SCHEDULING_LABEL_KEY), NO_DIVIDER));
        logistics.addSection(generateFormats(getH5Title(LUConstants.FORMATS_LABEL_KEY), NO_DIVIDER));
       
        
        VerticalSection information = new VerticalSection();
        information.addStyleName(LUConstants.STYLE_SECTION);
        information.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        information.setSectionTitle(getH4Title(LUConstants.INFORMATION_LABEL_KEY));
        information.addSection(generateIdentifiers(getH5Title(LUConstants.IDENTIFIERS_LABEL_KEY), NO_DIVIDER));
        information.addSection(generateTitles(getH5Title(LUConstants.TITLE_LABEL_KEY), NO_DIVIDER));
        information.addSection(generateDescriptions(getH5Title(LUConstants.DESCRIPTION_LABEL_KEY), NO_DIVIDER));
//        section.addSection(generateRestrictionsSection());
//        section.addSection(generateRequisitesSection());
//        section.addSection(generateFinancialsSection());
//        section.addSection(generateProgramRequirementsSection());
//        section.addSection(generateLearningObjectivesSection());
        
        section.addSection(governance);
        section.addSection(logistics);
        section.addSection(information);
        section.addSection(generateDates(getH5Title(LUConstants.ACTIVE_DATES_LABEL_KEY), NO_DIVIDER));

        return section;
    }
    
    private static VerticalSection generateAdminOrgs(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("cluInfo/primaryAdminOrg/orgId", null, Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/alternateAdminOrgs", null, Type.LIST, new AlternateAdminOrgList()));
        return section;
    }

    private static VerticalSection generateCampusLocation(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("cluInfo/campusLocationList", null, Type.LIST, new CampusLocationList()));
        return section;
    }

    private static VerticalSection generateCurriculumOversight(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("cluInfo/academicSubjectOrgs", null, Type.LIST, new AcademicSubjectOrgList()));
        return section;
    }


    private static VerticalSection generateFormats(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("courseFormats", null, Type.LIST, new CourseFormatList()));
        return section;
    }

    private static VerticalSection generateScheduling(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        
        CustomNestedSection ns = new CustomNestedSection();
        ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
        ns.addField(new FieldDescriptor("cluInfo/offeredAtpTypes", "Terms Offered :   ", Type.LIST, new OfferedAtpTypeList())); 
        ns.nextRow();
 
        section.addSection(ns);
        section.addField(new FieldDescriptor("cluInfo/stdDuration/atpDurationTypeKey", "Duration Type :   ", Type.STRING, new KSLabel())); 
        section.addField(new FieldDescriptor("cluInfo/stdDuration/timeQuantity", "Duration Time :   ", Type.STRING, new KSLabel())); 
        section.addField(new FieldDescriptor("cluInfo/intensity/atpDurationTypeKey", "Intensity Type :   ", Type.STRING, new KSLabel())); 
        section.addField(new FieldDescriptor("cluInfo/intensity/timeQuantity", "Intensity Time :   ", Type.STRING, new KSLabel()));

        return section;
    }

    private static VerticalSection generateLearningResults(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("evalType", "Evaluation Type :   ", Type.STRING, new KSLabel("evalType"))); //TODO EVAL TYPE ENUMERATION ????
        return section;
    }

    private static VerticalSection generateCredits(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("cluInfo/creditInfo/creditType", "Credit Type :   ", Type.STRING, new KSLabel()));//TODO CREDIT TYPE ENUMERATION
        section.addField(new FieldDescriptor("cluInfo/creditInfo", "Credit Value :   ", Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/maxCredits", "Maximum Credits :   ", Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/defaultEnrollmentEstimate", "Default Enrollment :    ", Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/defaultMaximumEnrollment", "Maximum Enrollment :    ", Type.STRING, new KSLabel()));
        return section;
    }

    private static VerticalSection generateInstructors(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("cluInfo/primaryInstructor/personId", "Person ID :      ", Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/primaryInstructor/orgId", "Org ID :      ", Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/instructors", null, Type.LIST, new AlternateInstructorList()));
        return section;
    }

    private static VerticalSection generateDescriptions(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("cluInfo/desc/plain", "Description:    ", Type.STRING,  new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/marketingDesc/plain", "Rationale:    ", Type.STRING, new KSLabel()));
        return section;
    }

    private static VerticalSection generateTitles(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("cluInfo/officialIdentifier/shortName", "Short Title:    ", Type.STRING, new KSLabel()));
         section.addField(new FieldDescriptor("cluInfo/officialIdentifier/longName", "Title:  ", Type.STRING, new KSLabel()));
        return section;
    }

    private static VerticalSection generateVersionCodes(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("luLuRelationType.alias", "TO DO", Type.STRING, new KSLabel()));
        return section;
    }

    private static VerticalSection generateJointOfferings(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("jointClus", "TO DO", Type.STRING, new KSLabel()));
        
        return section;
    }

    private static VerticalSection generateCrossListed(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.setSectionTitle(getH3Title(LUConstants.CROSS_LISTED_LABEL_KEY));
//      section.addField(new FieldDescriptor("crossListClus", null, Type.LIST, new CrossListedList()));//TODO Key is probably wrong
        section.addField(new FieldDescriptor("crossListClus", "TO DO", Type.STRING, new KSLabel()));
        
        return section;
    }

    private static VerticalSection generateIdentifiers(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        section.addField(new FieldDescriptor("cluInfo/officialIdentifier/code", "Code:    ", Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/officialIdentifier/division", "Division:    ", Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/officialIdentifier/level", "Level:    ", Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/officialIdentifier/variation", "Variation:    ", Type.STRING, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/officialIdentifier/suffixCode", "Suffix Code:    ", Type.STRING, new KSLabel()));
    
        section.addField(new FieldDescriptor("cluInfo/alternateIdentifiers", null, Type.LIST, new AlternateIdentifierList()));
       return section;
    }

    private static VerticalSection generateDates(SectionTitle title, boolean withDivider) {
        VerticalSection section = initSection(title, withDivider);
        
        section.addField(new FieldDescriptor("cluInfo/effectiveDate", "Start Date:  ", Type.DATE, new KSLabel()));
        section.addField(new FieldDescriptor("cluInfo/expirationDate", "End Date:   ", Type.DATE, new KSLabel()));
        return section;
    }

    public static class AcademicSubjectOrgList extends KSLabelList {
        public AcademicSubjectOrgList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }
    
    public static class CampusLocationList extends KSLabelList {
        public CampusLocationList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }

    public static class OfferedAtpTypeList extends KSLabelList {
        public OfferedAtpTypeList(){
            SimpleListItems list = new SimpleListItems();

            super.setListItems(list);
        }
    }


    private static class AlternateInstructorList extends MultiplicityComposite{

        protected AlternateInstructorList () {
            this.setItemLabel("Alternate Instructor");      
            this.setUpdateable(false);         
        }

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluInstructorInfo");

            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("personId", null, Type.STRING, new KSLabel()));
            ns.addField(new FieldDescriptor("orgId", null, Type.STRING, new KSLabel()));
            multi.addSection(ns);

            return multi;
        }

    }

    private static class AlternateIdentifierList extends MultiplicityComposite{

        protected AlternateIdentifierList () {
            this.setItemLabel("Alternate Identifier");      
            this.setUpdateable(false);         
        }

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluIdentifierInfo");

            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("code", null, Type.STRING, new KSLabel()));
            ns.addField(new FieldDescriptor("division", null, Type.STRING, new KSLabel()));
            ns.addField(new FieldDescriptor("level", null, Type.STRING, new KSLabel()));
            ns.addField(new FieldDescriptor("variation", null, Type.STRING, new KSLabel()));
            ns.addField(new FieldDescriptor("suffixCode", null, Type.STRING, new KSLabel()));
            multi.addSection(ns);

            return multi;
        }

    }

    private static class AlternateAdminOrgList extends MultiplicityComposite{

        protected AlternateAdminOrgList () {
            this.setItemLabel("Alternate Org ID");      
            this.setUpdateable(false);         
        }

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("AdminOrgInfo");

            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("orgId", null, Type.STRING, new KSLabel()));
            multi.addSection(ns);

            return multi;
        }

    }
    private static class CrossListedList extends MultiplicityComposite{

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluInfo");//TODO Probably totally wrong

            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("department", "Department", Type.STRING));
            ns.nextRow();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("division", "Subject Code", Type.STRING));
            ns.addField(new FieldDescriptor("suffixCode", "Course Number", Type.STRING));
            multi.addSection(ns);

            return multi;
        }

    }    
    private static class OfferedJointlyList extends MultiplicityComposite{

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluInfo");//TODO Probably totally wrong

            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("courseTitle", "Course Number or Title", Type.STRING));//TODO wrong key
            multi.addSection(ns);

            return multi;
        }

    }

    private static class VersionCodeList extends MultiplicityComposite{

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CluInfo");//TODO Probably totally wrong

            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("versionCode", "Code", Type.STRING));//TODO wrong key
            ns.addField(new FieldDescriptor("title", "Title", Type.STRING));//TODO wrong key
            multi.addSection(ns);

            return multi;
        }

    }



    private static class CourseFormatList extends MultiplicityComposite{
        public int formatNumber = 1;

        protected CourseFormatList () {
            this.setItemLabel("Format");      
            this.setUpdateable(false);         
        }

        public Widget createItem() {
            return new CourseActivityList();
        }
    }

    // This will probably a custom clu activity widget that uses a CluInfo model dto.
    private static class CourseActivityList extends MultiplicityComposite{
        public int activityNumber = 1;

        protected CourseActivityList () {
            this.setItemLabel("Activity");      
            this.setUpdateable(false);         
        }

        public Widget createItem() {
            MultiplicitySection item = new MultiplicitySection("CluInfo");
            CustomNestedSection activity = new CustomNestedSection();
            activity.setSectionTitle(SectionTitle.generateH3Title("Activity " + activityNumber));
            activity.addField(new FieldDescriptor("clu.type", "Acitivity Type", Type.STRING));
            activity.nextRow();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("creditInfo", "Credit Value", Type.STRING));
            activity.nextRow();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("evalType", "Learning Result", Type.STRING));
            activity.nextRow();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("term", "Term", Type.STRING));
            activity.addField(new FieldDescriptor("duration", "Activity Duration", Type.STRING)); //TODO dropdown need here?
            activity.nextRow();
            activity.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            activity.addField(new FieldDescriptor("clu.hours", "Contact Hours", Type.STRING));
            //TODO PER WHATEVER
            activity.addField(new FieldDescriptor("clu.method", "Delivery Method", Type.STRING));
            activity.addField(new FieldDescriptor("clu.size", "Class Size", Type.STRING));

            activityNumber++;
            item.addSection(activity);

            return item;
        }

    }

    // TODO look up field label and type from dictionary & messages
    private static FieldDescriptor createFieldDescriptor(String fieldName, 
            String objectKey, String type, String state  ) {

        Field f = DictionaryManager.getInstance().getField(objectKey, type, state, fieldName);

        FieldDescriptor fd = new FieldDescriptor(f.getKey(), 
                getLabel(f.getKey() ),   
                translateDictType(f.getFieldDescriptor().getDataType())               
        );
        return fd;
    }

    private static Type translateDictType(String dictType) {
        if (dictType.equalsIgnoreCase("String"))
            return Type.STRING;
        else
            return null;
    }

    public static class CollaboratorTool extends ToolView{
        public CollaboratorTool(){
            super(LuSections.AUTHOR, LUConstants.SECTION_AUTHORS_AND_COLLABORATORS);            
        }

        @Override
        protected Widget createWidget() {
            return new Collaborators();
        }

    }    

    private static String getLabel(String fieldId) {
        return Application.getApplicationContext().getUILabel(type, state, fieldId);
    }

    public static void setType(String type) {
        ViewCluConfigurer.type = type;
    }

    public static void setState(String state) {
        ViewCluConfigurer.state = state;
    }

    private static VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection();
        if (title !=  null) {
          section.setSectionTitle(title);
        }
        section.addStyleName(LUConstants.STYLE_SECTION);
        if (withDivider)
            section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        return section;
    }
    
    private static SectionTitle getH2Title(String labelKey) {
        return SectionTitle.generateH2Title(getLabel(labelKey));
    } 
    
    private static SectionTitle getH3Title(String labelKey) {
        return SectionTitle.generateH3Title(getLabel(labelKey));
    } 
    
    private static SectionTitle getH4Title(String labelKey) {
        return SectionTitle.generateH4Title(getLabel(labelKey));
    } 
    
    private static SectionTitle getH5Title(String labelKey) {
        return SectionTitle.generateH5Title(getLabel(labelKey));
    }
}
