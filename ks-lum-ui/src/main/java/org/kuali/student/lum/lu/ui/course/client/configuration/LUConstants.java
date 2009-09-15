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
package org.kuali.student.lum.lu.ui.course.client.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    
    //Tool names
    public static final String TOOL_COMMENTS = "Proposal Comments";
    public static final String TOOL_DOCUMENTS = "Attach Documents";

    // Dictionary definitions
    public static final String STRUCTURE_CLU_INFO = "org.kuali.student.lum.lu.dto.CluInfo";
    public static final String STRUCTURE_CLU_ID_INFO = "org.kuali.student.lum.lu.dto.CluIdentifierInfo";
    public static final String STRUCTURE_PROPOSAL_INFO = "org.kuali.student.lum.proposal.dto.ProposalInfo";

    // Message keys for label lookup
    public static final String PROPOSAL_INFORMATION_LABEL_KEY = "cluProposalInformation";
    public static final String GOVERNANCE_LABEL_KEY = "cluGovernance";
    public static final String CURRICULUM_OVERSIGHT_LABEL_KEY = "cluCurriculumOversight";
    public static final String CAMPUS_LOCATION_LABEL_KEY = "cluCampusLocation";
    public static final String PRIMARY_ADMIN_ORG_LABEL_KEY = "cluPrimaryAdminOrg";
    public static final String ALT_ADMIN_ORGS_LABEL_KEY = "cluAltAdminOrgs";

    public static final String LOGISTICS_LABEL_KEY = "cluLogistics";
    public static final String PRIM_INSTR_LABEL_KEY = "cluPrimaryInstructor";
    public static final String ALT_INSTR_LABEL_KEY = "cluAlternateInstructors";
    public static final String CREDITS_LABEL_KEY = "cluCredits";
    public static final String LEARNING_RESULTS_LABEL_KEY = "cluLearningResults";
    public static final String SCHEDULING_LABEL_KEY = "cluScheduling";
    public static final String FORMATS_LABEL_KEY = "cluFormats";

    public static final String ACADEMIC_CONTENT_LABEL_KEY = "cluAcademicContent";
    public static final String INFORMATION_LABEL_KEY = "cluInformation";
    public static final String IDENTIFIER_LABEL_KEY = "cluIdentifier";
    public static final String CROSS_LISTED_LABEL_KEY = "cluCrosslisted";
    public static final String JOINT_OFFERINGS_LABEL_KEY = "cluJointOfferings";
    public static final String VERSION_CODES_LABEL_KEY = "cluVersionCodes";
    public static final String TITLE_LABEL_KEY = "cluTitle";
    public static final String SHORT_TITLE_LABEL_KEY = "cluShortTitle";
    public static final String DESCRIPTION_LABEL_KEY = "cluDescription";
    public static final String RATIONALE_LABEL_KEY = "cluRationale";
    public static final String LEARNING_OBJECTIVES_LABEL_KEY = "cluLearningObjectives";
    public static final String SYLLABUS_LABEL_KEY = "cluSyllabus";

    public static final String STUDENT_ELIGIBILITY_LABEL_KEY = "cluStudentEligibility";
    public static final String RESTRICTIONS_LABEL_KEY = "cluCourseRestrictions";
    public static final String REQUISITES_LABEL_KEY = "cluCourseRequisites";
    public static final String PREQS_LABEL_KEY = "cluPreRequisites";
    public static final String CREQS_LABEL_KEY = "cluCoRequisites";

    public static final String ADMINISTRATION_LABEL_KEY = "cluAdministration";
    public static final String ACTIVE_DATES_LABEL_KEY = "cluActiveDates";
    public static final String START_DATE_LABEL_KEY = "cluStartDate";
    public static final String END_DATE_LABEL_KEY = "cluEndDate";
    public static final String FINANCIALS_LABEL_KEY = "cluFinancials";
    public static final String FEE_TYPE_LABEL_KEY = "cluFeeType";
    public static final String FEE_AMOUNT_LABEL_KEY = "cluFeeAmount";
    public static final String FEE_DESC_LABEL_KEY = "cluFeeDesc";
    public static final String PROGRAM_REQUIREMENTS_LABEL_KEY = "cluProgramRequirements";
    public static final String GENERAL_REQS_LABEL_KEY = "cluGeneralRequirements";
    public static final String DEPT_REQS_LABEL_KEY = "cluDeptRequirements";

    public static final String SECTION_ATTACHMENTS = "Attachments";
    public static final String SECTION_SUPPORTING_DOCUMENTS = "Supporting Documents";

}

