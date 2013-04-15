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

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

/**
 * Course Offering Service Constants
 *
 * @author nwright
 * @see LuiServiceConstants
 */
public class CourseOfferingServiceConstants {

    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseOffering";
    public static final String SERVICE_NAME_LOCAL_PART = CourseOfferingService.class.getSimpleName();
    public static final String REF_OBJECT_URI_COURSE_OFFERING = NAMESPACE + "/" + CourseOfferingInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ACTIVITY_OFFERING = NAMESPACE + "/" + ActivityOfferingInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_REGISTRATION_GROUP = NAMESPACE + "/" + RegistrationGroupInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_SEAT_POOL_DEFINITION = NAMESPACE + "/" + SeatPoolDefinitionInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_AO_CLUSTER_DEFINITION = NAMESPACE + "/" + ActivityOfferingClusterInfo.class.getSimpleName();


    //dynamic attribute keys
    public static final String WAIT_LIST_TYPE_KEY_ATTR = "kuali.attribute.wait.list.type.key"; // Value is in WaitlistLevel enum
    public static final String WAIT_LIST_LEVEL_TYPE_KEY_ATTR = "kuali.attribute.wait.list.level.type.key";
    public static final String GRADE_ROSTER_LEVEL_TYPE_KEY_ATTR = "kuali.attribute.grade.roster.level.type.key";
    public static final String FINAL_EXAM_LEVEL_TYPE_KEY_ATTR = "kuali.attribute.final.exam.level.type";
    public static final String COURSE_EVALUATION_INDICATOR_ATTR = "kuali.attribute.course.evaluation.indicator";
    public static final String FINAL_EXAM_INDICATOR_ATTR = "kuali.attribute.final.exam.indicator";
    public static final String FUNDING_SOURCE_ATTR = "kuali.attribute.funding.source";
    public static final String FEES_ATTACHED_FLAG_ATTR = "kuali.attribute.where.fees.attached.flag";
    public static final String MAX_ENROLLMENT_IS_ESTIMATED_ATTR = "kuali.attribute.max.enrollment.is.estimate";
    public static final String WHERE_FEES_ATTACHED_FLAG_ATTR = "kuali.attribute.where.fees.attached.flag";
    public static final String IS_MAX_ENROLLMENT_ESTIMATE_ATTR = "kuali.attribute.max.enrollment.is.estimate";
    public static final String WAIT_LIST_INDICATOR_ATTR = "kuali.attribute.wait.list.indicator";
    public static final String COURSE_NUMBER_IN_SUFX_ATTR = "kuali.attribute.course.number.internal.suffix";
    public static final String IS_REGISTRATION_GROUP_GENERATED_INDICATOR_ATTR = "kuali.attribute.registration.group.is.generated";
    public static final String AOCLUSTER_ID_ATTR = "kuali.attribute.registration.group.aocluster.id";
    public static final String COLOCATED_SET_MAXIMUM_ENROLLMENT_ATTR = "kuali.attribute.colocatedset.max.enrollment";
    public static final String IS_MAX_ENROLLMENT_SHARED_ATTR = "kuali.attribute.colocatedset.is.max.enrollment.shared";

    // The type/states are defined in LuiServiceConstants.java

    //The list of all Student registration-specific grading options
    public static final String[] ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS = {
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL};
    // The list of all grading options
    public static final String[] ALL_GRADING_OPTION_TYPE_KEYS = {
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER_PASSING_TRANSLATION,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER_GRADUATE,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PNP_STANDARD,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER_PLUS_MINUS_STANDARD,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE_0_59,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE_60_69,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE_70_79,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE_80_84,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE_85_89,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE_90_MINUS94,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PERCENTAGE_95_100,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_ADMIN_STANDARD,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_ADMIN_IN_MINUSPROGRESS,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_SATISFACTORY,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_COMPLETEDNOTATION,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_DESIGNREVIEW,
            LrcServiceConstants.RESULT_GROUP_KEY_GRADE_RECITALREVIEW};

    public static final String APPEND_COURSE_OFFERING_CODE_SUFFIX_OPTION_KEY = "kuali.course.offering.optionkey.append.course.offering.suffix";
    public static final String APPEND_COURSE_OFFERING_IN_SUFFIX_OPTION_KEY = "kuali.course.offering.optionkey.append.course.offering.internal.suffix";

    public static final String COURSE_OFFERING_CODE_VALIDATION_ELEMENT = "courseOfferingCode";
    public static final String COURSE_OFFERING_CODE_UNIQUENESS_VALIDATION_MESSAGE = "Course offering code is not unique within the term";

    // Activity Offering Cluster States
    public static final String AOC_ACTIVE_STATE_KEY = "kuali.activity.offering.cluster.state.active";

    // Activity Offering Cluster Types - note: not a LUI type
    public static final String AOC_ROOT_TYPE_KEY = "kuali.activity.offering.cluster.type.activity.offering.cluster";
    
    // Search Types
    public static final String AUTOGEN_COUNTS_BY_CO = "kuali.search.type.course.offering.autogen.counts.by.course.offering";
    public static final String AUTOGEN_COUNTS_BY_FO = "kuali.search.type.course.offering.autogen.counts.by.format.offering";
    public static final String AUTOGEN_COUNTS_BY_AOC = "kuali.search.type.course.offering.autogen.counts.by.activity.offering.cluster";
    
    // Search Request Parameter Keys
    public static final String AUTOGEN_COUNTS_BY_CO_ID_PARAM = "kuali.search.type.course.offering.autogen.counts.by.course.offering.param.id";
    public static final String AUTOGEN_COUNTS_BY_FO_ID_PARAM = "kuali.search.type.course.offering.autogen.counts.by.format.offering.param.id";
    public static final String AUTOGEN_COUNTS_BY_AOC_ID_PARAM = "kuali.search.type.course.offering.autogen.counts.by.activity.offering.cluster.param.id";
    
    
    // Search Result Cell Keys (the kind of data that comes out of the Auto Gen Count Searches)
    public static final String AUTOGEN_COUNTS_TOTAL_AOS = "kuali.search.type.course.offering.autogen.counts.total.aos";
    public static final String AUTOGEN_COUNTS_TOTAL_AOCS = "kuali.search.type.course.offering.autogen.counts.total.aocs";
    public static final String AUTOGEN_COUNTS_TOTAL_RGS = "kuali.search.type.course.offering.autogen.counts.total.rgs";
    public static final String AUTOGEN_COUNTS_TOTAL_INVALID_RGS = "kuali.search.type.course.offering.autogen.counts.total.invalid.rgs";
    
    
    
    
    
}
