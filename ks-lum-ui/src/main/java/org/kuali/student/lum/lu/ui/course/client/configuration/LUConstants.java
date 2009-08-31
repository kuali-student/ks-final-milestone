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

    public static final String SECTION_ADMINISTRATIVE = "Administrative";
    public static final String SECTION_CREDITS = "Credits";
    public static final String SECTION_ACTIVE_DATES = "Active Dates";
    public static final String SECTION_FINANCIALS = "Financials";
    public static final String SECTION_PROGRAM_REQUIREMENTS = "Program Requirements";

    public static final String SECTION_ATTACHMENTS = "Attachments";
    public static final String SECTION_SUPPORTING_DOCUMENTS = "Supporting Documents";
    
    //Tool names
    public static final String TOOL_COMMENTS = "Proposal Comments";
    public static final String TOOL_DOCUMENTS = "Attach Documents";

    // Dictionary definitions
    public static final String STRUCTURE_CLU_INFO = "org.kuali.student.lum.lu.dto.CluInfo";
    public static final String STRUCTURE_CLU_ID_INFO = "org.kuali.student.lum.lu.dto.CluIdentifierInfo";
    public static final String STRUCTURE_PROPOSAL_INFO = "org.kuali.student.lum.proposal.dto.ProposalInfo";
    
    
 }

