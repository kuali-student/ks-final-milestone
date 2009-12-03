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


/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 *
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class LUConstants {

    //TODO These should probably be somewhere else as they are LUM wide constants - not just
    //     UI specific

    public final static String LU_TYPE_CREDIT_COURSE = "luType.shell.course";
    
    public final static String COURSE_GROUP_NAME = "course";
    public final static String PROPOSAL_TYPE_COURSE_CREATE = "kuali.proposal.type.course.create";
    public final static String CLU_TYPE_CREDIT_COURSE = "kuali.lu.type.CreditCourse";
    public final static String PROGRAM_GROUP_NAME = "program";    
    public final static String PROPOSAL_TYPE_PROGRAM_CREATE = "kuali.proposal.type.program.create";
    public final static String CLU_TYPE_CREDIT_PROGRAM = "kuali.lu.type.CreditProgram";    
    
	// found this in https://test.kuali.org/confluence/display/KULSTU/LuConfig.Types.LuLuRelationType
    public final static String LU_LU_RELATION_TYPE_HAS_COURSE_FORMAT = "luLuRelationType.hasCourseFormat";
    public final static String LU_LU_RELATION_TYPE_CONTAINS = "luLuRelationType.contains";
    public final static String LU_LU_RELATION_TYPE_CROSS_LISTED = "luLuRelationType.alias";
    public final static String LU_LU_RELATION_TYPE_JOINTLY_OFFERED = "luLuRelationType.colocated";

    // Valid states for Credit Course
    public final static String LU_STATE_PROPOSED = "Proposed"; // Should this be Draft
    public final static String LU_STATE_SUBMITTED = "Submitted";
    public final static String LU_STATE_WITHDRAWN = "Withdrawn";
    public final static String LU_STATE_APPROVED = "Approved";
    public final static String LU_STATE_NOT_APPROVED = "Not Approved";// Maybe Rejected would be a better value?
    public final static String LU_STATE_ACTIVATED = "Activated";
    public final static String LU_STATE_RETIRED = "Retired";

    //Section names
    public static final String SECTION_PROPOSAL_INFORMATION = "Proposal Information";
    public static final String SECTION_AUTHORS_AND_COLLABORATORS = "Authors + Collaborators";
    public static final String SECTION_GOVERNANCE = "Governance";
    public static final String SECTION_COURSE_LOGISTICS = "Course Logistics";

    public static final String SECTION_ACADEMIC_CONTENT = "Academic Content";
    public static final String SECTION_COURSE_INFORMATION = "Course Information";
    public static final String SECTION_LEARNING_OBJECTIVES = "Learning Objectives";
    public static final String SECTION_SYLLABUS = "Syllabus";
    public static final String SECTION_LEARNING_RESULTS = "Learning Results";

    public static final String SECTION_STUDENT_ELIGIBILITY = "Student Eligibility";
    public static final String SECTION_COURSE_RESTRICTIONS = "Course Restrictions";
    public static final String SECTION_PREQS_AND_CREQS = "Pre + Co Requisites";
    public static final String SECTION_COURSE_REQUISITES = "Course Requisites";

    public static final String SECTION_ADMINISTRATIVE = "Administrative";
    public static final String SECTION_CREDITS = "Credits";
    public static final String SECTION_ACTIVE_DATES = "Active Dates";
    public static final String SECTION_FINANCIALS = "Financials";
    public static final String SECTION_PROGRAM_REQUIREMENTS = "Program Requirements";    
    
    //Tools
    public static final String TOOL_COMMENTS = "Proposal Comments";
    public static final String TOOL_DOCUMENTS = "Attach Documents";
    public static final String SECTION_ATTACHMENTS = "Attachments";
    public static final String SECTION_SUPPORTING_DOCUMENTS = "Supporting Documents";
   
    //Styles
    public static final String STYLE_SECTION = "KS-LUM-Section";
    public static final String STYLE_SECTION_DIVIDER = "KS-LUM-Section-Divider";

    // Dictionary definitions
    public static final String STRUCTURE_CLU_INFO = "org.kuali.student.lum.lu.dto.CluInfo";
    public static final String STRUCTURE_CLU_ID_INFO = "org.kuali.student.lum.lu.dto.CluIdentifierInfo";
    public static final String STRUCTURE_PROPOSAL_INFO = "org.kuali.student.lum.proposal.dto.ProposalInfo";

    // Message keys for top-level section label lookup
    public static final String PROPOSAL_INFORMATION_LABEL_KEY = "cluProposalInformation";
    public static final String ACADEMIC_CONTENT_LABEL_KEY = "cluAcademicContent";
    public static final String STUDENT_ELIGIBILITY_LABEL_KEY = "cluStudentEligibility";
    public static final String ADMINISTRATION_LABEL_KEY = "cluAdministration";
    

    public static final String PROPOSAL_TITLE_LABEL_KEY = "cluProposalTitle";
    public static final String PROPOSAL_DIRECTIONS_LABEL_KEY = "cluProposalDirections";
    public static final String PROPOSAL_PERSON_LABEL_KEY = "cluProposalPerson";
    
    //Summary labels
    public static final String SUMMARY_LABEL_KEY = "cluSummary";
    public static final String BRIEF_LABEL_KEY = "cluBrief";
    public static final String FULL_VIEW_LABEL_KEY = "cluFullView";
    public static final String PROPOSER_LABEL_KEY = "cluProposer";
    public static final String DELEGATE_LABEL_KEY = "cluDelegate";
    public static final String COLLABORATORS_LABEL_KEY = "cluCollaborators";
    
    // Governance labels
    public static final String GOVERNANCE_LABEL_KEY = "cluGovernance";
    public static final String CURRICULUM_OVERSIGHT_LABEL_KEY = "cluCurriculumOversight";
    public static final String CAMPUS_LOCATION_LABEL_KEY = "cluCampusLocation";
    public static final String ADMIN_ORGS_LABEL_KEY = "cluAdminOrgs";
    public static final String ADMIN_ORG_LABEL_KEY = "cluAdminOrg";
    public static final String ALT_ADMIN_ORG_LABEL_KEY = "cluAltAdminOrg";

    //Logistics labels
    public static final String LOGISTICS_LABEL_KEY = "cluLogistics";
    public static final String INSTRUCTORS_LABEL_KEY = "cluInstructors";
    public static final String INSTRUCTOR_LABEL_KEY = "cluInstructor";
    
    public static final String CREDITS_LABEL_KEY = "cluCredits";
    public static final String CREDIT_LABEL_KEY = "cluCredit";
    public static final String CREDIT_VALUE_LABEL_KEY = "cluCreditValue";
    public static final String MAX_CREDITS_LABEL_KEY = "cluMaxCredits";
    
    public static final String LEARNING_RESULTS_LABEL_KEY = "cluLearningResults";
    public static final String SCHEDULING_LABEL_KEY = "cluScheduling";  
    public static final String FORMATS_LABEL_KEY = "cluFormats";
    public static final String FORMAT_LABEL_KEY = "cluFormat";
    public static final String COURSE_ADD_FORMAT_LABEL_KEY = "cluAddFormat";
    public static final String ADD_ACTIVITY_LABEL_KEY = "cluAddActivity";
    public static final String ACTIVITY_LITERAL_LABEL_KEY = "cluActivityLiteral";
    public static final String ACTIVITY_TYPE_LABEL_KEY = "cluActivityType";
    public static final String CONTACT_HOURS_LABEL_KEY = "cluContactHours";
    public static final String CLASS_SIZE_LABEL_KEY = "cluClassSize";

    //Information labels
    public static final String INFORMATION_LABEL_KEY = "cluInformation";
    
    public static final String IDENTIFIERS_LABEL_KEY = "cluIdentifiers";
    public static final String IDENTIFIER_LABEL_KEY = "cluIdentifier";
    public static final String ALT_IDENTIFIER_LABEL_KEY = "cluAltIdentifier";
    public static final String DIVISION_LABEL_KEY = "cluDivision";
    public static final String SUFFIX_CODE_LABEL_KEY = "cluSuffixCode";
    public static final String CODE_LABEL_KEY = "cluCode";
    public static final String TITLE_LITERAL_LABEL_KEY = "cluTitleLiteral";
    public static final String TITLE_LABEL_KEY = "cluTitle";
    public static final String SUBJECT_CODE_LABEL_KEY = "cluSubjectCode";
    
    public static final String SHORT_TITLE_LABEL_KEY = "cluShortTitle";
    public static final String DESCRIPTION_LABEL_KEY = "cluDescription";
    public static final String DESCRIPTION_LABEL_LABEL_KEY = "cluDescriptionLabel";
    public static final String STATUS_LABEL_KEY = "cluStatus";
    
    public static final String RATIONALE_LABEL_KEY = "cluRationale";

    public static final String CROSS_LISTED_LABEL_KEY = "cluCrosslisted";
    public static final String CROSS_LISTED_ITEM_LABEL_KEY = "cluCrosslistedItem";
    public static final String CROSS_LISTED_ALT_LABEL_KEY = "cluCrosslistedAlt";
    public static final String ADD_CROSS_LISTED_LABEL_KEY = "cluAddCrosslisted";

    public static final String JOINT_OFFER_ITEM_LABEL_KEY = "cluJointOfferItem";
    public static final String JOINT_OFFERINGS_LABEL_KEY = "cluJointOfferings";
    public static final String JOINT_OFFERINGS_ALT_LABEL_KEY = "cluJointOfferingsAlt";
    public static final String EVALUATION_TYPE_LABEL_KEY = "cluEvaluationType";
    public static final String TERM_LITERAL_LABEL_KEY = "cluTermLiteral";
    public static final String DURATION_LITERAL_LABEL_KEY = "cluDurationLiteral";

    public static final String VERSION_CODE_LABEL_KEY = "cluVersionCode";
    public static final String VERSION_CODES_LABEL_KEY = "cluVersionCodes";
    public static final String ADD_VERSION_CODE_LABEL_KEY = "cluAddVersionCode";
    public static final String COURSE_NUMBER_LABEL_KEY = "cluCourseNumber";
    public static final String COURSE_NUMBER_OR_TITLE_LABEL_KEY = "cluCourseNumberOrTitle";
    public static final String ADD_EXISTING_LABEL_KEY = "cluAddExisting";

    
    //Learning Objectives  labels
    public static final String LEARNING_OBJECTIVES_LABEL_KEY = "cluLearningObjectives";
    public static final String LEARNING_OBJECTIVE_LABEL_KEY = "cluLearningObjective";
    public static final String LEARNING_OBJECTIVE_LO_NAME_KEY = "cluLOName";
    public static final String LEARNING_OBJECTIVE_ADD_LABEL_KEY = "cluAddLOs";
//    public static final String SYLLABUS_LABEL_KEY = "cluSyllabus";

    //Requisites labels
    public static final String RESTRICTIONS_LABEL_KEY = "cluCourseRestrictions";
    public static final String REQUISITES_LABEL_KEY = "cluCourseRequisites";
    public static final String PREQS_LABEL_KEY = "cluPreRequisites";
    public static final String CREQS_LABEL_KEY = "cluCoRequisites";

    //Active Dates labels
    public static final String EFFECTIVE_DATE_LABEL_KEY = "cluEffectiveDate";
    public static final String ACTIVE_DATES_LABEL_KEY = "cluActiveDates";
    public static final String START_DATE_LABEL_KEY = "cluStartDate";
    public static final String START_LABEL_KEY = "cluStart";
    public static final String END_DATE_LABEL_KEY = "cluEndDate";
    public static final String EXPIRATION_DATE_LABEL_KEY = "cluExpirationDate";
    public static final String CREATED_DATE_LABEL_KEY = "cluCreatedDate";
    public static final String LAST_CHANGED_DATE_LABEL_KEY       = "cluLastChangedDate";

    //Financials labels
    public static final String FINANCIALS_LABEL_KEY = "cluFinancials";
    public static final String FEE_TYPE_LABEL_KEY = "cluFeeType";
    public static final String FEE_AMOUNT_LABEL_KEY = "cluFeeAmount";
    public static final String FEE_DESC_LABEL_KEY = "cluFeeDescription";
    public static final String INTERNAL_FEE_NOTIFICATION_LABEL_KEY = "cluInternalFeeNotification";
    public static final String CURRENCY_SYMBOL_LABEL_KEY = "cluCurrencySymbol";
    public static final String TAXABLE_SYMBOL_LABEL_KEY = "cluTaxable";

    //  Program Requirements  labels
    public static final String PROGRAM_REQUIREMENTS_LABEL_KEY = "cluProgramRequirements";
    public static final String GENERAL_REQS_LABEL_KEY = "cluGeneralRequirements";
    public static final String DEPT_REQS_LABEL_KEY = "cluDeptRequirements";
    public static final String DEPT_LABEL_KEY = "cluDept";

    public static final String LO_SEARCH_LINK = "cluLOSearch";

    public static final String LO_INSTRUCTIONS = "cluLOInstructions";


}

