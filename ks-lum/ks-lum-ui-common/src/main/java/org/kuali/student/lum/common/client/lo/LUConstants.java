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

package org.kuali.student.lum.common.client.lo;


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
    public final static String LU_STATE_PROPOSED = "Proposed"; 
    public final static String LU_STATE_SUBMITTED = "Submitted";
    public final static String LU_STATE_WITHDRAWN = "Withdrawn";
    public final static String LU_STATE_APPROVED = "Approved";
    public final static String LU_STATE_NOT_APPROVED = "Not Approved";
    public final static String LU_STATE_ACTIVE = "Active";
    public final static String LU_STATE_RETIRED = "Retired";

    //Section names
    public static final String COURSE_SECTIONS = "Course Sections";
    public static final String SECTION_PROPOSAL_INFORMATION = "Proposal Information";
    public static final String SECTION_AUTHORS_AND_COLLABORATORS = "People & Permissions";
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
    public static final String TOOL_COMMENTS_LABEL_KEY = "toolComments";
    public static final String TOOL_DOCUMENTS_LABEL_KEY = "toolDocuments";
    public static final String SECTION_ATTACHMENTS = "Attachments";
    public static final String SECTION_SUPPORTING_DOCUMENTS = "Supporting Documents";
   
    //Styles
    public static final String STYLE_SECTION = "KS-LUM-Section";
    public static final String STYLE_SECTION_DIVIDER = "KS-LUM-Section-Divider";
    public static final String STYLE_BOTTOM_DIVIDER = "KS-LUM-Bottom-Divider";
    public static final String STYLE_TOP_DIVIDER = "KS-LUM-Top-Divider";
    
    // Dictionary definitions
    public static final String STRUCTURE_CLU_INFO = "org.kuali.student.lum.lu.dto.CluInfo";
    public static final String STRUCTURE_CLU_ID_INFO = "org.kuali.student.lum.lu.dto.CluIdentifierInfo";
    public static final String STRUCTURE_PROPOSAL_INFO = "org.kuali.student.lum.proposal.dto.ProposalInfo";

    // Message keys for top-level section label lookup
    public static final String COURSE_INFORMATION_LABEL_KEY = "cluCourseInformation";
    public static final String ACADEMIC_CONTENT_LABEL_KEY = "cluAcademicContent";
    public static final String STUDENT_ELIGIBILITY_LABEL_KEY = "cluStudentEligibility";
    public static final String ADMINISTRATION_LABEL_KEY = "cluAdministration";
    

    public static final String PROPOSAL_TITLE_LABEL_KEY = "cluProposalTitle";
    public static final String PROPOSAL_DIRECTIONS_LABEL_KEY = "cluProposalDirections";
    public static final String PROPOSAL_PERSON_LABEL_KEY = "cluProposalPerson";
    public static final String PROPOSAL_RATIONALE_LABEL_KEY = "cluProposalRationale";
    public static final String PROPOSAL_TITLE_SECTION_LABEL_KEY = "cluProposalTitleSection";
    
    //Authors & Rationale Labels
    public static final String AUTHORS_RATIONAL = "cluAuthorsRationale";
       
    public static final String EDIT_TAB_LABEL_KEY = "cluEditTab";
    
    //Summary labels
    public static final String SUMMARY_LABEL_KEY = "cluSummary";
    public static final String BRIEF_LABEL_KEY = "cluBrief";
    public static final String FULL_VIEW_LABEL_KEY = "cluFullView";
    public static final String PROPOSER_LABEL_KEY = "cluProposer";
    public static final String DELEGATE_LABEL_KEY = "cluDelegate";
    public static final String COLLABORATORS_LABEL_KEY = "cluCollaborators";
    
    // Governance labels
    public static final String GOVERNANCE_LABEL_KEY = "cluGovernance";
    public static final String ACADEMIC_SUBJECT_ORGS_KEY = "cluCurriculumOversight";
    public static final String CAMPUS_LOCATION_LABEL_KEY = "cluCampusLocation";
    public static final String ADMIN_ORGS_LABEL_KEY = "cluAdminOrgs";
    public static final String ADMIN_ORG_LABEL_KEY = "cluAdminOrg";
    public static final String ALT_ADMIN_ORG_LABEL_KEY = "cluAltAdminOrg";

    // Active Dates Labels
    public static final String START_TERM_LABEL_KEY = "cluStartTerm";
    public static final String END_TERM_LABEL_KEY = "cluEndTerm";
    public static final String PILOT_COURSE_LABEL_KEY = "cluPilotCourse";
    public static final String PILOT_COURSE_TEXT_LABEL_KEY = "cluPilotCourseText";
    
    //Logistics labels
    public static final String LOGISTICS_LABEL_KEY = "cluLogistics";
    public static final String INSTRUCTORS_LABEL_KEY = "cluInstructors";
    public static final String INSTRUCTOR_LABEL_KEY = "cluInstructor";
    
    public static final String CREDITS_LABEL_KEY = "cluCredits";
    public static final String CREDIT_LABEL_KEY = "cluCredit";
    public static final String CREDIT_VALUE_LABEL_KEY = "cluCreditValue";
    public static final String MAX_CREDITS_LABEL_KEY = "cluMaxCredits";
    
    // Learning Results Labels
    public static final String LEARNING_RESULTS_LABEL_KEY = "cluLearningResults";
    public static final String LEARNING_RESULTS_GRADES_ASSESSMENTS_LABEL_KEY = "cluLearningResultGradesAssessments";
    public static final String LEARNING_RESULTS_STUDENT_REGISTRATION_LABEL_KEY = "cluLearningResultStudentRegistration";
    public static final String LEARNING_RESULT_ASSESSMENT_SCALE_LABEL_KEY = "cluLearningResultAssessmentScale";
    public static final String LEARNING_RESULT_OUTCOMES_LABEL_KEY = "cluLearningResultOutcomes";
    public static final String LEARNING_RESULT_FINAL_EXAM_LABEL_KEY = "cluLearningResultFinalExam";
    public static final String ADD_LEARNING_RESULT_ASSESSMENT_SCALE_LABEL_KEY = "cluAddLearningResultAssessmentScale";
	public static final String LEARNING_RESULT_AUDIT_LABEL_KEY = "cluLearningResultAudit";
	public static final String LEARNING_RESULT_AUDIT_TEXT_LABEL_KEY = "cluLearningResultAuditText";
	public static final String LEARNING_RESULT_PASS_FAIL_LABEL_KEY = "cluLearningResultPassFail";
	public static final String LEARNING_RESULT_PASS_FAIL_TEXT_LABEL_KEY = "cluLearningResultPassFailText";
    public static final String ADD_LEARNING_RESULT_OUTCOME_LABEL_KEY = "cluAddLearningResultOutcome";
    public static final String LEARNING_RESULT_OUTCOME_LABEL_KEY = "cluLearningResultOutcome";
    public static final String LEARNING_RESULT_OUTCOME_TYPE_LABEL_KEY = "cluLearningResultOutcomeType";
    public static final String LEARNING_RESULT_STUDENT_REGI_OPTIONS_LABEL_KEY = "cluLearningResultsStudentRegiOptions";
    public static final String CREDIT_OPTION_FIXED_CREDITS_LABEL_KEY = "cluLearningResultsOutcomeFixedCredits";
    public static final String CREDIT_OPTION_MIN_CREDITS_LABEL_KEY = "cluLearningResultsOutcomeMinCredits";
    public static final String CREDIT_OPTION_MAX_CREDITS_LABEL_KEY = "cluLearningResultsOutcomeMaxCredits";

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
    public static final String COURSE_TITLE_LABEL_KEY = "courseTitle";
    public static final String SUBJECT_CODE_LABEL_KEY = "cluSubjectCode";
    public static final String LEVEL_LABEL_KEY = "cluLevel";
    
    public static final String SHORT_TITLE_LABEL_KEY = "cluShortTitle";
    public static final String DESCRIPTION_LABEL_KEY = "cluDescription";
    public static final String DESCRIPTION_LABEL_LABEL_KEY = "cluDescriptionLabel";
    public static final String STATUS_LABEL_KEY = "cluStatus";
    
    public static final String CROSS_LISTED_LABEL_KEY = "cluCrosslisted";
    public static final String CROSS_LISTED_ITEM_LABEL_KEY = "cluCrosslistedItem";
    public static final String CROSS_LISTED_ALT_LABEL_KEY = "cluCrosslistedAlt";
    public static final String ADD_CROSS_LISTED_LABEL_KEY = "cluAddCrosslisted";

    public static final String CL_V_J_LABEL_KEY = "cluCrossListedVersionJoin";
    public static final String JOINT_OFFER_ITEM_LABEL_KEY = "cluJointOfferItem";
    public static final String JOINT_OFFERINGS_LABEL_KEY = "cluJointOfferings";
    public static final String JOINT_OFFERINGS_SECTION_TITLE_LABEL_KEY = "cluJointOfferingsSectionTitle";
    public static final String JOINT_OFFERINGS_ALT_LABEL_KEY = "cluJointOfferingsAlt";
    public static final String EVALUATION_TYPE_LABEL_KEY = "cluEvaluationType";
    public static final String TERM_LITERAL_LABEL_KEY = "cluTermLiteral";
    public static final String DURATION_LITERAL_LABEL_KEY = "cluDurationLiteral";
    public static final String FINAL_EXAM_LABEL_KEY = "cluFinalExam";
    public static final String FINAL_EXAM_STATUS_LABEL_KEY = "cluFinalExamStatus";
    public static final String FINAL_EXAM_RATIONALE_LABEL_KEY = "cluFinalExamRationale";

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
    public static final String LEARNING_OBJECTIVE_WORD_SEARCH_KEY = "cluLOWordSearch";
    public static final String LEARNING_OBJECTIVE_CLUCODE_SEARCH_KEY = "cluLOCluCodeSearch";
    public static final String LO_SEARCH_LINK_KEY = "cluLOSearch";
    public static final String LO_SEARCH_AGAIN_LINK_KEY = "cluLOSearchAgain";
    public static final String LO_INSTRUCTIONS_KEY = "cluLOInstructions";
    public static final String LO_CATEGORY_KEY = "cluLOCategory";

    
    //    public static final String SYLLABUS_LABEL_KEY = "cluSyllabus";

    //Requisites labels
    public static final String RESTRICTIONS_LABEL_KEY = "cluCourseRestrictions";
    public static final String REQUISITES_LABEL_KEY = "cluCourseRequisites";
    public static final String PREQS_LABEL_KEY = "cluPreRequisites";
    public static final String CREQS_LABEL_KEY = "cluCoRequisites";
    public static final String AREQS_LABEL_KEY = "cluAntiRequisites";
    public static final String EREQS_LABEL_KEY = "cluEnrollRequisites";
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

    //  View Course labels
    public static final String CURRENT_VIEW_LABEL_KEY = "cluCurrentView";
    public static final String COURSE_DETAILS_LABEL_KEY = "cluCourseDetails";
    public static final String STATE_LABEL_KEY = "cluState";
    public static final String TYPE_LABEL_KEY = "cluType";
    public static final String PRIMARY_INSTRUCTOR_LABEL_KEY = "cluPrimaryInstructor";
    public static final String DISCLOSURE_PANEL_LABEL_KEY = "cluDisclosurePanelHeading";
    public static final String TERMS_OFFERED_LABEL_KEY = "cluTermsOffered";
    public static final String DURATION_TYPE_LABEL_KEY = "cluDurationType";
    public static final String DURATION_QUANTITY_LABEL_KEY = "cluDurationQuantity";
    public static final String FIRST_OFFERING_KEY = "cluFirstOffering";

    // Financial labels
    public static final String COURSE_FEE_TITLE = "cluCourseFeesTitle";
    public static final String JUSTIFICATION_FEE ="cluJustificationOfFees";
    public static final String FINANCIAL_INFORMATION ="cluFinancialInformation";
    public static final String REVENUE = "cluRevenue";
    public static final String AMOUNT = "cluAmount";
    public static final String EXPENDITURE = "cluExpenditure";
    public static final String VARIABLE_RATE ="cluVariableRate";
    public static final String FIXED_RATE = "cluFixedRate";
    public static final String MULTIPLE_RATE = "cluMultipleRate";
    public static final String PER_CREDIT_RATE = "cluPerCreditRate";
    public static final String LAB_FEE = "cluLabFee";
    public static final String MATERIAL_FEE = "cluMaterialFee";
    public static final String STUDIO_FEE = "cluStudioFee";
    public static final String FIELD_TRIP_FEE = "cluFieldTripFee";
    public static final String FIELD_STUDY_FEE= "cluFieldStudyFee";
    public static final String ADMINISTRATIVE_FEE = "cluAdministrativeFee";
    public static final String COOP_FEE = "cluCoopFee";
    public static final String GREENS_FEE = "cluGreensFee";
    public static final String ADD_A_FEE = "cluAddAfee";
    public static final String TO = "cluTo";
    public static final String RATE_TYPE = "cluRateType";
    public static final String ADD_ANOTHER_FEE = "cluAddAnotherFee";     
    public static final String FEE = "cluFee";
    public static final String ORGANIZATION = "cluOrganization";
    public static final String ADD_ANOTHER_ORGANIZATION = "cluAddAnotherOrganization";
    public static final String PERCENTAGE = "cluPercentage";



}

