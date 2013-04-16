/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.common.util.constants;

import org.kuali.student.enrollment.lrr.dto.LearningResultRecordInfo;
import org.kuali.student.enrollment.lrr.dto.ResultSourceInfo;
import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * Grading Service Constants
 */
public class LrrServiceConstants {

    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "lrr";
    public static final String REF_OBJECT_URI_RESULT_RECORD = NAMESPACE + "/" + LearningResultRecordInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_RESULT_SOURCE = NAMESPACE + "/" + ResultSourceInfo.class.getSimpleName();
    // lrr types
    public static final String RESULT_RECORD_FINAL_GRADE_ASSIGNED_TYPE_KEY = "kuali.lrr.type.final.grade.assigned";
    public static final String RESULT_RECORD_FINAL_GRADE_ADMINISTRATIVE_TYPE_KEY = "kuali.lrr.type.final.grade.administrative";
    public static final String RESULT_RECORD_FINAL_GRADE_CALCULATED_TYPE_KEY = "kuali.lrr.type.final.grade.calculated";
    public static final String RESULT_RECORD_INTERIM_GRADE = "kuali.lrr.type.final.grade.interim";
    public static final String RESULT_RECORD_CREDITS_EARNED = "kuali.lrr.type.credits.earned";
    // lr source types
    public static final String RESULT_SOURCE_EARNED_NATIVELY = "kuali.lr.source.type.earned.natively";
    // lrr states
    public static final String COURSE_FINAL_GRADING_LIFECYCLE_KEY = "kuali.lrr.course.final.grading.lifecycle";
    public static final String RESULT_RECORD_SAVED_STATE_KEY = "kuali.lrr.state.saved";
    public static final String RESULT_RECORD_SUBMITTED_STATE_KEY = "kuali.lrr.state.submitted";
    public static final String RESULT_RECORD_ACCEPTED_STATE_KEY = "kuali.lrr.state.accepted";
    public static final String RESULT_RECORD_DELETED_STATE_KEY = "kuali.lrr.state.deleted";
    public static final String[] CCOURSE_FINAL_GRADING_LIFECYCLE = {RESULT_RECORD_SAVED_STATE_KEY,
        RESULT_RECORD_SUBMITTED_STATE_KEY,
        RESULT_RECORD_ACCEPTED_STATE_KEY};
}
