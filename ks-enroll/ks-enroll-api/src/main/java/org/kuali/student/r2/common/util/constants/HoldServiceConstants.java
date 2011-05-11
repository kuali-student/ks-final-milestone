/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
package org.kuali.student.r2.common.util.constants;

import org.kuali.student.enrollment.hold.dto.HoldInfo;
import org.kuali.student.enrollment.hold.dto.IssueInfo;
import org.kuali.student.enrollment.hold.dto.RestrictionInfo;

/**
 * This class holds the constants used by the Hold service
 *
 * @author nwright
 */
public class HoldServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "hold";
    public static final String REF_OBJECT_URI_HOLD = NAMESPACE + "/" + HoldInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ISSUE = NAMESPACE + "/" + IssueInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_RESTRICTION = NAMESPACE + "/" + RestrictionInfo.class.getSimpleName();
    /**
     * Hold types
     */
    public static final String STUDENT_HOLD_TYPE_KEY = "kuali.hold.type.student";
    public static final String INTRUCTOR_HOLD_TYPE_KEY = "kuali.hold.type.instructor";
    /**
     * ISSUE TYPES
     */
    public static final String OVERDUE_LIBRARY_MATERIALS_ISSUE_TYPE_KEY = "kuali.hold.issue.type.overdue.library.materials";
    public static final String FINANCIAL_ISSUE_TYPE_KEY = "kuali.hold.issue.type.financial";
    public static final String MEDICAL_IMMUNIZATION_ISSUE_TYPE_KEY = "kuali.hold.issue.type.medical.immunization";
    public static final String MEDICAL_INSURANCE_ISSUE_TYPE_KEY = "kuali.hold.issue.type.medical.insurance";
    public static final String INTERNATIONAL_STUDENT_ISSUE_TYPE_KEY = "kuali.hold.issue.type.international.students";
    public static final String DISCIPLINE_ISSUE_TYPE_KEY = "kuali.hold.issue.type.discipline";
    public static final String ACADEMIC_PROGRESS_ISSUE_TYPE_KEY = "kuali.hold.issue.type.academic.progress";
    public static final String RESIDENCE_ISSUE_TYPE_KEY = "kuali.hold.issue.type.residencE";
    /**
     * RESTRICTION TYPES
     */
    public static final String REGISTERATION_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.registration";
    public static final String ADD_DROP_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.add.drop.class";
    public static final String REQUEST_TRANSCRIPT_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.request.transcript";
    public static final String AWARD_DEGREE_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.award.degree";
    public static final String RECEIVE_DIPLOMA_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.receive.diploma";
    public static final String ADD_CHARGES_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.add.charges";
    public static final String LIBRARY_PRIVILEGES_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.library.privileges";
    public static final String DORM_ACCESS_RESTRICTION_TYPE_KEY = "kuali.hold.restriction.type.dorm.access";
    /**
     * STATES FOR holds
     */
    public static final String STUDENT_HOLD_PROCESS_KEY = "kuali.hold.process.student";
    public static final String HOLD_ACIVE_STATE_KEY = "kuali.hold.state.active";
    public static final String HOLD_RELEASED_STATE_KEY = "kuali.hold.state.released";
    public static final String HOLD_CANCELED_STATE_KEY = "kuali.hold.state.canceled";
    public static final String[] STUDENT_HOLD_PROCESS_KEYS = {HOLD_ACIVE_STATE_KEY,
        HOLD_RELEASED_STATE_KEY,
        HOLD_CANCELED_STATE_KEY};
    /**
     * STATES FOR hold ISSUES
     */
    public static final String ISSUE_PROCESS_KEY = "kuali.hold.issue.process";
    public static final String ISSUE_ACIVE_STATE_KEY = "kuali.hold.issue.state.active";
    public static final String ISSUE_INACTIVE_STATE_KEY = "kuali.hold.issue.state.inactive";
    public static final String[] ISSUE_PROCESS_KEYS = {ISSUE_ACIVE_STATE_KEY,
        ISSUE_INACTIVE_STATE_KEY};
    /**
     * STATES FOR hold RESTRICTIONS
     */
    public static final String RESTRICTION_PROCESS_KEY = "kuali.hold.restriction.process";
    public static final String RESTRICTION_ACIVE_STATE_KEY = "kuali.hold.restriction.state.active";
    public static final String RESTRICTION_INACTIVE_STATE_KEY = "kuali.hold.restriction.state.inactive";
    public static final String[] RESTRICTION_PROCESS_KEYS = {RESTRICTION_ACIVE_STATE_KEY,
        RESTRICTION_INACTIVE_STATE_KEY};
}
