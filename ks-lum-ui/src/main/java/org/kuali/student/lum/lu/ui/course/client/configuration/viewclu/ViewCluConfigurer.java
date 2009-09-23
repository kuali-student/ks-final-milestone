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


    // FIXME: initialize these values somehow
    private static String type;
    private static String state;

    public enum LuSections{
        CLU_BEGIN, AUTHOR, GOVERNANCE, COURSE_LOGISTICS, COURSE_INFO, LEARNING_OBJECTIVES, 
        COURSE_RESTRICTIONS, COURSE_REQUISITES, ACTIVE_DATES, FINANCIALS, PGM_REQUIREMENTS, ATTACHMENTS, COMMENTS, DOCUMENTS
    }

    public static void generateLayout(ConfigurableLayout layout){     

        layout.addSection(new String[] {getLabel(LUConstants.PROPOSAL_INFORMATION_LABEL_KEY)}, generateGovernanceSection());
        layout.addSection(new String[] {getLabel(LUConstants.PROPOSAL_INFORMATION_LABEL_KEY)}, generateCourseLogisticsSection());

        layout.addSection(new String[] {getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateCourseInformationSection());
        layout.addSection(new String[] {getLabel(LUConstants.ACADEMIC_CONTENT_LABEL_KEY)}, generateLearningObjectivesSection());

        layout.addSection(new String[] {getLabel(LUConstants.STUDENT_ELIGIBILITY_LABEL_KEY)}, generateCourseRestrictionsSection());
        layout.addSection(new String[] {getLabel(LUConstants.STUDENT_ELIGIBILITY_LABEL_KEY)}, generateCourseRequisitesSection());

        layout.addSection(new String[] {getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateActiveDatesSection());               
        layout.addSection(new String[] {getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateFinancialsSection());
        layout.addSection(new String[] {getLabel(LUConstants.ADMINISTRATION_LABEL_KEY)}, generateProgramRequirementsSection());

        //TODO Need a display version of these Tools
        layout.addTool(new CollaboratorTool());
        layout.addTool(new CommentPanel(LuSections.COMMENTS, "View Comments"));
        layout.addTool(new DocumentTool(LuSections.DOCUMENTS, "View Attached Documents"));

    }    

    private static SectionView generateGovernanceSection(){
        VerticalSectionView section = new VerticalSectionView(LuSections.GOVERNANCE, getLabel(LUConstants.GOVERNANCE_LABEL_KEY), CluProposalModelDTO.class);        
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.GOVERNANCE_LABEL_KEY)));

        //FIXME: Label should come from messaging, field type should come from dictionary?
//      section.addField(createMVCFieldDescriptor("campusLocationInfo", LUConstants.STRUCTURE_CLU_INFO, type, state));

        VerticalSection curriculumOversightSection = new VerticalSection();

        curriculumOversightSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.CURRICULUM_OVERSIGHT_LABEL_KEY)));
        curriculumOversightSection.addField(new FieldDescriptor("/studySubjectArea", "Subject Area:  ", Type.STRING, new KSLabel()));

        VerticalSection campusLocationSection = new VerticalSection();
        campusLocationSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.CAMPUS_LOCATION_LABEL_KEY)));
//        campusLocationSection.addField(new FieldDescriptor("campusLocationList", null, Type.LIST, new CampusLocationList()));

        VerticalSection adminOrgSection = new VerticalSection();
        adminOrgSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.PRIMARY_ADMIN_ORG_LABEL_KEY)));
        adminOrgSection.addField(new FieldDescriptor("/primaryAdminOrg/orgId", "Org ID:  ", Type.STRING, new KSLabel()));        

        VerticalSection altAdminOrgSection = new VerticalSection();
        altAdminOrgSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.ALT_ADMIN_ORGS_LABEL_KEY)));
        altAdminOrgSection.addField(new FieldDescriptor("alternateAdminOrgs", null, Type.LIST, new AlternateAdminOrgList()));        

        section.addSection(curriculumOversightSection);
        section.addSection(campusLocationSection);
        section.addSection(adminOrgSection);
        section.addSection(altAdminOrgSection);

        return section;

    }

    private static SectionView generateCourseLogisticsSection(){
        VerticalSectionView section = new VerticalSectionView(LuSections.COURSE_LOGISTICS, getLabel(LUConstants.LOGISTICS_LABEL_KEY), CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.LOGISTICS_LABEL_KEY)));

        //INSTRUCTORS
        CustomNestedSection primaryInstructor = new CustomNestedSection();
        primaryInstructor.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.PRIM_INSTR_LABEL_KEY)));
        primaryInstructor.addField(new FieldDescriptor("primaryInstructor/personId", "Person ID :   ", Type.STRING, new KSLabel()));
        primaryInstructor.addField(new FieldDescriptor("primaryInstructor/orgId", "Org ID :   ", Type.STRING, new KSLabel()));

        VerticalSection altInstructors = new VerticalSection();
        altInstructors.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.ALT_INSTR_LABEL_KEY)));
        altInstructors.addField(new FieldDescriptor("instructors", null, Type.LIST, new AlternateInstructorList()));        

        //CREDITS
        VerticalSection credits = new VerticalSection();
        credits.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.CREDITS_LABEL_KEY)));
        credits.addField(new FieldDescriptor("creditType", "Credit Type :   ", Type.STRING, new KSLabel()));//TODO CREDIT TYPE ENUMERATION
        credits.addField(new FieldDescriptor("creditInfo", "Credit Value :   ", Type.STRING, new KSLabel()));
        credits.addField(new FieldDescriptor("maxCredits", "Maximum Credits :   ", Type.STRING, new KSLabel()));
        credits.addField(new FieldDescriptor("defaultEnrollmentEstimate", "Default Enrollment :    ", Type.STRING, new KSLabel()));
        credits.addField(new FieldDescriptor("defaultMaximumEnrollment", "Maximum Enrollment :    ", Type.STRING, new KSLabel()));

        //LEARNING RESULTS
        VerticalSection learningResults = new VerticalSection();
        learningResults.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.LEARNING_RESULTS_LABEL_KEY)));
        learningResults.addField(new FieldDescriptor("evalType", "Evaluation Type :   ", Type.STRING, new KSLabel("evalType"))); //TODO EVAL TYPE ENUMERATION ????

        VerticalSection scheduling = new VerticalSection();
        scheduling.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.SCHEDULING_LABEL_KEY)));
        scheduling.addField(new FieldDescriptor("offeredAtpTypes", "Term :   ", Type.STRING, new KSLabel())); //TODO TERM ENUMERATION
        scheduling.addField(new FieldDescriptor("/stdDuration/atpDurationTypeKey", "Duration Type :   ", Type.STRING, new KSLabel())); 
        scheduling.addField(new FieldDescriptor("/stdDuration/timeQuantity", "Duration Time :   ", Type.STRING, new KSLabel())); 
        scheduling.addField(new FieldDescriptor("/intensity/atpDurationTypeKey", "Intensity Type :   ", Type.STRING, new KSLabel())); 
        scheduling.addField(new FieldDescriptor("/intensity/timeQuantity", "Intensity Time :   ", Type.STRING, new KSLabel())); 

        VerticalSection formatsSection = new VerticalSection();
        formatsSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.FORMATS_LABEL_KEY)));
        formatsSection.addField(new FieldDescriptor("courseFormats", "TO DO", Type.STRING, new KSLabel()));

        section.addSection(primaryInstructor);
        section.addSection(altInstructors);
        section.addSection(credits);
        section.addSection(learningResults);
        section.addSection(scheduling);
        section.addSection(formatsSection);


        return section;

    }

    private static SectionView generateCourseInformationSection(){
        VerticalSectionView section = new VerticalSectionView(LuSections.COURSE_INFO, getLabel(LUConstants.INFORMATION_LABEL_KEY), CluProposalModelDTO.class);        
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.INFORMATION_LABEL_KEY)));

        //FIXME: Label should be key to messaging, field type should come from dictionary?


        //COURSE IDENTIFIER
        //TODO: This should be a course number widget
        CustomNestedSection cluIdSection = new CustomNestedSection();
        cluIdSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.IDENTIFIER_LABEL_KEY))); //Section title constants)
        cluIdSection.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
        cluIdSection.addField(new FieldDescriptor("/officialIdentifier/code", "Code", Type.STRING, new KSLabel()));//TODO OrgSearch goes here?
        cluIdSection.addField(new FieldDescriptor("/officialIdentifier/division", "Division", Type.STRING, new KSLabel()));//TODO OrgSearch goes here?
        cluIdSection.addField(new FieldDescriptor("/officialIdentifier/level", "Level", Type.STRING, new KSLabel()));//TODO OrgSearch goes here?
        cluIdSection.addField(new FieldDescriptor("/officialIdentifier/variation", "Variation", Type.STRING, new KSLabel()));
        cluIdSection.addField(new FieldDescriptor("/officialIdentifier/suffixCode", "Suffix Code", Type.STRING, new KSLabel()));
        cluIdSection.nextRow();

        VerticalSection altIdSection = new VerticalSection();
        altIdSection.setSectionTitle(SectionTitle.generateH3Title("Alternate Identifiers"));
        altIdSection.addField(new FieldDescriptor("alternateIdentifiers", null, Type.LIST, new AlternateIdentifierList()));        


        // TODO Next 3 sections )advanced options) should be in a disclosure panel       
        //CROSS LISTED
        VerticalSection crossListedSection = new VerticalSection();
        crossListedSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.CROSS_LISTED_LABEL_KEY)));
//      crossListedSection.addField(new FieldDescriptor("crossListClus", null, Type.LIST, new CrossListedList()));//TODO Key is probably wrong
        crossListedSection.addField(new FieldDescriptor("crossListClus", "TO DO", Type.STRING, new KSLabel()));

        //OFFERED JOINTLY
        VerticalSection offeredJointlySection = new VerticalSection();
        offeredJointlySection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.JOINT_OFFERINGS_LABEL_KEY)));
//      offeredJointlySection.addField(new FieldDescriptor("jointClus", null, Type.LIST, new OfferedJointlyList()));//TODO Key is probably wrong
        offeredJointlySection.addField(new FieldDescriptor("jointClus", "TO DO", Type.STRING, new KSLabel()));

        //Version Codes
        VerticalSection versionCodesSection = new VerticalSection();
        versionCodesSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.VERSION_CODES_LABEL_KEY)));
//      versionCodesSection.addField(new FieldDescriptor("luLuRelationType.alias", null, Type.LIST, new VersionCodeList()));//TODO Key is probably wrong     
        versionCodesSection.addField(new FieldDescriptor("luLuRelationType.alias", "TO DO", Type.STRING, new KSLabel()));

        VerticalSection courseTitleSection = new VerticalSection();
        courseTitleSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.TITLE_LABEL_KEY)));
        courseTitleSection.addField(new FieldDescriptor("/officialIdentifier/longName", null, Type.STRING, new KSLabel()));

        VerticalSection transcriptTitle = new VerticalSection();
        transcriptTitle.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.SHORT_TITLE_LABEL_KEY)));
        transcriptTitle.addField(new FieldDescriptor("/officialIdentifier/shortName", null, Type.STRING, new KSLabel()));

        VerticalSection description = new VerticalSection();
        description.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.DESCRIPTION_LABEL_KEY)));
        description.addField(new FieldDescriptor("/desc/plain", null, Type.STRING,  new KSLabel()));

        VerticalSection rationale = new VerticalSection();
        rationale.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.RATIONALE_LABEL_KEY)));
        rationale.addField(new FieldDescriptor("/marketingDesc/plain", null, Type.STRING, new KSLabel()));        

        section.addSection(cluIdSection);
        section.addSection(altIdSection);
        //TODO Advanced options
//      section.addSection(crossListedSection);
//      section.addSection(offeredJointlySection);
//      section.addSection(versionCodesSection);
        section.addSection(courseTitleSection);
        section.addSection(transcriptTitle);
        section.addSection(description);
        section.addSection(rationale);

        return section;        

    }

    private static SectionView generateLearningObjectivesSection() {
        VerticalSectionView section = new VerticalSectionView(LuSections.LEARNING_OBJECTIVES, getLabel(LUConstants.LEARNING_OBJECTIVES_LABEL_KEY), CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.LEARNING_OBJECTIVES_LABEL_KEY)));

        section.addField(new FieldDescriptor("learningObjectives", "TO DO", Type.STRING, new KSLabel()));

        return section;        

    }

    private static SectionView generateCourseRestrictionsSection() {
        VerticalSectionView section = new VerticalSectionView(LuSections.COURSE_RESTRICTIONS, getLabel(LUConstants.RESTRICTIONS_LABEL_KEY), CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.RESTRICTIONS_LABEL_KEY)));
        section.addField(new FieldDescriptor("courseRestrictions", "TO DO", Type.STRING, new KSLabel()));

        return section;        

    }

    private static SectionView generateCourseRequisitesSection() {
        VerticalSectionView section = new VerticalSectionView(LuSections.COURSE_REQUISITES, getLabel(LUConstants.REQUISITES_LABEL_KEY), CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.REQUISITES_LABEL_KEY)));

        VerticalSection preqSection = new VerticalSection();
        preqSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.PREQS_LABEL_KEY)));
        preqSection.addField(new FieldDescriptor("prerequisites", "TO DO", Type.STRING, new KSLabel()));

        VerticalSection creqSection = new VerticalSection();
        creqSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.CREQS_LABEL_KEY)));
        creqSection.addField(new FieldDescriptor("corequisites", "TO DO", Type.STRING, new KSLabel()));

        section.addSection(preqSection);
        section.addSection(creqSection);

        return section;        

    }

    private static SectionView generateActiveDatesSection() {
        VerticalSectionView section = new VerticalSectionView(LuSections.ACTIVE_DATES, getLabel(LUConstants.ACTIVE_DATES_LABEL_KEY), CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.ACTIVE_DATES_LABEL_KEY)));

        VerticalSection startDateSection = new VerticalSection();
        startDateSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.START_DATE_LABEL_KEY)));
        startDateSection.addField(new FieldDescriptor("effectiveDate", null, Type.DATE, new KSLabel()));

        VerticalSection endDateSection = new VerticalSection();
        endDateSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.END_DATE_LABEL_KEY)));
        endDateSection.addField(new FieldDescriptor("expirationDate", null, Type.DATE, new KSLabel()));

        section.addSection(startDateSection);
        section.addSection(endDateSection);

        return section; 
    }

    private static SectionView generateFinancialsSection() {
        VerticalSectionView section = new VerticalSectionView(LuSections.FINANCIALS, getLabel(LUConstants.FINANCIALS_LABEL_KEY), CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.FINANCIALS_LABEL_KEY)));

        //TODO ALL KEYS in this section are place holders until we know actual keys
        VerticalSection feeTypeSection = new VerticalSection();
        feeTypeSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.FEE_TYPE_LABEL_KEY)));
        feeTypeSection.addField(new FieldDescriptor("feeType", null, Type.STRING, new KSLabel()));

        VerticalSection feeAmountSection = new VerticalSection();
        feeAmountSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.FEE_AMOUNT_LABEL_KEY)));
        feeAmountSection.addField(new FieldDescriptor("feeAmount", "$ :   ", Type.STRING, new KSLabel()));
        feeAmountSection.addField(new FieldDescriptor("taxable", "Taxable :   ", Type.STRING, new KSLabel()));//TODO checkboxes go here instead
        feeAmountSection.addField(new FieldDescriptor("feeDesc", "Description :   ", Type.STRING, new KSLabel()));
        feeAmountSection.addField(new FieldDescriptor("internalNotation", "Internal Fee Notation :   ", Type.STRING, new KSLabel()));

        section.addSection(feeTypeSection);
        section.addSection(feeAmountSection);
        return section;
    }

    private static SectionView generateProgramRequirementsSection() {
        VerticalSectionView section = new VerticalSectionView(LuSections.PGM_REQUIREMENTS, getLabel(LUConstants.PROGRAM_REQUIREMENTS_LABEL_KEY), CluProposalModelDTO.class);
        section.setSectionTitle(SectionTitle.generateH2Title(getLabel(LUConstants.PROGRAM_REQUIREMENTS_LABEL_KEY)));

        VerticalSection generalReqSection = new VerticalSection();
        generalReqSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.GENERAL_REQS_LABEL_KEY)));
        generalReqSection.addField(new FieldDescriptor("genRequirements", "TO DO", Type.STRING, new KSLabel()));

        VerticalSection deptReqSection = new VerticalSection();
        deptReqSection.setSectionTitle(SectionTitle.generateH3Title(getLabel(LUConstants.DEPT_REQS_LABEL_KEY)));
        deptReqSection.addField(new FieldDescriptor("deptRequirements", "TO DO", Type.STRING, new KSLabel()));

        section.addSection(generalReqSection);
        section.addSection(deptReqSection);
        return section;

    }

    //TODO: CampusLocationList
    public static class CampusLocationList extends MultiplicityComposite{

        @Override
        public Widget createItem() {
            MultiplicitySection multi = new MultiplicitySection("CampusLocationInfo");

            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            ns.addField(new FieldDescriptor("campusName", "Campus Name", Type.STRING, new KSLabel()));
            multi.addSection(ns);

            return multi;
        }
    }

    private static class AlternateInstructorList extends MultiplicityComposite{
        
        protected AlternateInstructorList () {
            this.setItemLabel("Instructor");      
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
            this.setItemLabel("Identifier");      
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
            this.setItemLabel("Org ID");      
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
        public Widget createItem() {
            return new CourseActivityList();
        }
    }

    // This will probably a custom clu activity widget that uses a CluInfo model dto.
    private static class CourseActivityList extends MultiplicityComposite{
        public int activityNumber = 1;
        public Widget createItem() {
            MultiplicitySection item = new MultiplicitySection("cluInfo");
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

}
