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
}
