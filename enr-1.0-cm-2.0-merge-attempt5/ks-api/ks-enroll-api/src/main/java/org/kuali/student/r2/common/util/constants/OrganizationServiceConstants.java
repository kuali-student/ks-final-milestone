/**
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the * Educational Community License, Version 2.0
 * (the "License"); you may * not use this file except in compliance
 * with the License. You may * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.util.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.dto.OrgCodeInfo;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPositionRestrictionInfo;


/**
 * This class holds the constants used by the organization service
 *
 * @author tom
 */
public class OrganizationServiceConstants {

    /**
     * Reference Object URIs
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization";
    public static final String REF_OBJECT_URI_DEFAULT = NAMESPACE + "COMMON/DEFAULT";
    public static final String REF_OBJECT_URI_ORG = NAMESPACE + "/" + OrgInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ORG_CODE = NAMESPACE + "/" + OrgCodeInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ORG_HIERARCHY = NAMESPACE + "/" + OrgHierarchyInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ORG_ORG_RELATION = NAMESPACE + "/" + OrgOrgRelationInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ORG_PERSON_RELATION = NAMESPACE + "/" + OrgPersonRelationInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ORG_POSITION_RESTRICTION = NAMESPACE + "/" + OrgPositionRestrictionInfo.class.getSimpleName();
    public static final String SERVICE_NAME_LOCAL_PART = "OrganizationService";

    /**
     * Organization types (https://wiki.kuali.org/display/STUDENT/Organization+Types#)
     * KSENROLL-3877., KSENROLL-4436
     */
    public static final String ORGANIZATION_COLLEGE_TYPE_KEY = "kuali.org.College";
    public static final String ORGANIZATION_DEPARTMENT_TYPE_KEY = "kuali.org.Department";
    public static final String ORGANIZATION_DIVISION_TYPE_KEY = "kuali.org.Division";
    public static final String ORGANIZATION_SENATE_TYPE_KEY = "kuali.org.Senate";
    public static final String ORGANIZATION_PROGRAM_TYPE_KEY = "kuali.org.Program";
    public static final String ORGANIZATION_SCHOOL_TYPE_KEY = "kuali.org.School";
    public static final String ORGANIZATION_CENTER_TYPE_KEY = "kuali.org.Center";
    public static final String ORGANIZATION_TESTING_SERVICE_TYPE_KEY = "kuali.org.TestingService";
    public static final String ORGANIZATION_COMMITTEE_TYPE_KEY = "kuali.org.Committee";
    public static final String ORGANIZATION_ADVISORY_GROUP_TYPE_KEY = "kuali.org.AdvisoryGroup";
    public static final String ORGANIZATION_ACCREDITING_BODY_TYPE_KEY = "kuali.org.AccreditingBody";
    public static final String ORGANIZATION_CAMPUS_TYPE_KEY = "kuali.org.Campus";
    public static final String ORGANIZATION_ACADEMIC_INSTRUCTIONAL_RESEARCH_SUPPORT_TYPE_KEY = "kuali.org.AcademicInstructionalResearchSupport";
    public static final String ORGANIZATION_ACADEMIC_ADMINISTRATIVE_SUPPORT_TYPE_KEY = "kuali.org.AcademicAdministrativeSupport";
    public static final String ORGANIZATION_ACADEMIC_OUTREACH_TYPE_KEY = "kuali.org.AcademicOutreach";
    public static final String ORGANIZATION_UNIVERSITY_SUPPORT_TYPE_KEY = "kuali.org.UniversitySupport";
    public static final String ORGANIZATION_SUB_DEPARTMENT_TYPE_KEY = "kuali.org.SubDepartment";
    public static final String ORGANIZATION_INSTITUTION_TYPE_KEY = "kuali.org.Institution";
}
