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

import org.kuali.student.enrollment.academicrecord.dto.attic.CreditsGradeInfo;
import org.kuali.student.enrollment.academicrecord.dto.attic.ExternalCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.attic.HonorsInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.attic.StudentRankInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.attic.StudentTransferCreditRecordInfo;
import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * This class holds the constants used by the Academic Record service
 *
 * @author tom
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class AcademicRecordServiceConstants {
    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "academicrecord";
    public static final String SERVICE_NAME_LOCAL_PART = "AcademicRecordService";
    public static final String REF_OBJECT_URI_CREDITS_GRADE = NAMESPACE + "/" + CreditsGradeInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_EXTERNAL_CREDENTIAL_RECORD = NAMESPACE + "/" + ExternalCredentialRecordInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_GPA = NAMESPACE + "/" + GPAInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_HONORS = NAMESPACE + "/" + HonorsInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_LOAD = NAMESPACE + "/" + LoadInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_STUDENT_COURSE_RECORD = NAMESPACE + "/" + StudentCourseRecordInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_STUDENT_RANK = NAMESPACE + "/" + StudentRankInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_STUDENT_TEST_SCORE_RECORD = NAMESPACE + "/" + StudentTestScoreRecordInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_STUDENT_TRANSFER_CREDIT_RECORD = NAMESPACE + "/" + StudentTransferCreditRecordInfo.class.getSimpleName();

    /**
     * Types
     */
    public static final String ACADEMIC_RECORD_CALCULATION_GPA_TYPE_KEY = "kuali.academic.record.type.calculation.gpa";
    
    
    // types
    public static final String LOAD_TYPE_CREDITS = "kuali.academic.record.load.type.credits";
    public static final String LOAD_TYPE_COURSES = "kuali.academic.record.load.type.courses";
    public static final String LOAD_TYPE_CODE_4_TIER = "kuali.academic.record.load.type.code.4.tier";
    // states
    public static final String LOAD_STATE_PRELIMIARY = "kuali.academic.record.load.state.preliminary";
    public static final String LOAD_STATE_FINAL = "kuali.academic.record.load.state.final";
    // Calculation types "LoadLevelType" code
    public static final String LOAD_CALC_CREDITS_INTEGER = "kuali.academic.record.calculation.type.load.credit.integer";
    public static final String LOAD_CALC_CREDITS_DECIMAL = "kuali.academic.record.calculation.type.load.credit.decimal";
    public static final String LOAD_CALC_COURSES_SIMPLE = "kuali.academic.record.calculation.type.load.courses.simple";
    public static final String LOAD_CALC_COURSES_TIERED = "kuali.academic.record.calculation.type.load.courses.tiered";
    public static final String LOAD_CALC_CODE_4_TIERS = "kuali.academic.record.calculation.type.load.code.4.tiers";
    
    // not sure where these "types" should be stored/managed but they are the possible values from the 4 tier code
    // These should probably be reviewed these tiers to come up with a scheme that fits most schools OOB
    public static final String LOAD_CODE_4_TIER_1_NO_LOAD = "kuali.academic.record.load.code.4.tier.0.no.load";
    public static final String LOAD_CODE_4_TIER_2_LT_HT = "kuali.academic.record.load.code.4.tier.2.less.than.half.time";
    public static final String LOAD_CODE_4_TIER_3_HT = "kuali.academic.record.load.code.4.tier.3.at.least.half.time";
    public static final String LOAD_CODE_4_TIER_4_FT = "kuali.academic.record.load.code.4.tier.4.full.time";

}

