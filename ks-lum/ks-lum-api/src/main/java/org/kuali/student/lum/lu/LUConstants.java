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

package org.kuali.student.lum.lu;


/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 *
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class LUConstants {


    public final static String COURSE_GROUP_NAME = "course";
    public final static String PROPOSAL_TYPE_COURSE_CREATE = "kuali.proposal.type.course.create";
    public final static String PROPOSAL_TYPE_COURSE_MODIFY = "kuali.proposal.type.course.modify";
    public final static String PROPOSAL_TYPE_COURSE_CREATE_ADMIN = "kuali.proposal.type.course.create.admin";    
    public final static String PROPOSAL_TYPE_COURSE_MODIFY_ADMIN = "kuali.proposal.type.course.modify.admin";  
    public final static String CLU_TYPE_CREDIT_COURSE = "kuali.lu.type.CreditCourse";
    public final static String PROGRAM_GROUP_NAME = "program";    
    public final static String PROPOSAL_TYPE_PROGRAM_CREATE = "kuali.proposal.type.program.create";
    public final static String CLU_TYPE_CREDIT_PROGRAM = "kuali.lu.type.CreditProgram";    
    
	// found this in https://test.kuali.org/confluence/display/KULSTU/LuConfig.Types.LuLuRelationType
    public final static String LU_LU_RELATION_TYPE_HAS_COURSE_FORMAT = "luLuRelationType.hasCourseFormat";
    public final static String LU_LU_RELATION_TYPE_CONTAINS = "luLuRelationType.contains";
    public final static String LU_LU_RELATION_TYPE_CROSS_LISTED = "luLuRelationType.alias";
    public final static String LU_LU_RELATION_TYPE_JOINTLY_OFFERED = "luLuRelationType.colocated";

    // Dictionary definitions
    public static final String STRUCTURE_CLU_INFO = "org.kuali.student.lum.lu.dto.CluInfo";
    public static final String STRUCTURE_CLU_ID_INFO = "org.kuali.student.lum.lu.dto.CluIdentifierInfo";
    public static final String STRUCTURE_PROPOSAL_INFO = "org.kuali.student.lum.proposal.dto.ProposalInfo";

	public static final String REF_DOC_RELATION_PROPOSAL_TYPE = "kuali.org.RefObjectType.ProposalInfo";



}

