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
package org.kuali.student.r2.core.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;

/**
 * This class holds the constants used by the Academic Calendar service
 *
 * @author tom
 */
public class AcademicCalendarServiceConstants {

    /**
     * Reference Object URI's
     */
    // TODO replace "acal" in namespace with "academiccalendar"
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "acal";
    public static final String SERVICE_NAME_LOCAL_PART = "AcademicCalendarService";
    public static final String REF_OBJECT_URI_TERM = NAMESPACE + "/" + TermInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_ACADEMIC_CALENDAR = NAMESPACE + "/" + AcademicCalendarInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_HOLIDAY_CALENDAR = NAMESPACE + "/" + HolidayCalendarInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_HOLIDAY = NAMESPACE + "/" + HolidayInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_KEY_DATE = NAMESPACE + "/" + KeyDateInfo.class.getSimpleName();

    /**
     * Academic Calendar Types
     */
    public static final String ACADEMIC_CALENDAR_TYPE_KEY = AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY;
    public static final String HOLIDAY_CALENDAR_TYPE_KEY = AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY;
    public static final String[] TERM_TYPE_KEYS = AtpServiceConstants.ATP_TERM_GROUPING;

    /**
     * Dynamic Attributes Keys
     */
    public static final String CAMPUS_KEY_DYNAMIC_ATTRIBUTE_KEY = "kuali.atp.attribute.campus.key";

    /**
     * Registration Date Groups
     */
    public static final String REGISTRATION_PERIOD_TYPE_KEY = AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY;
    public static final String ADD_DATE_TYPE_KEY = AtpServiceConstants.MILESTONE_COURSE_SELECTION_PERIOD_END_TYPE_KEY;
    public static final String DROP_DATE_TYPE_KEY = AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY;
    public static final String CLASSES_TYPE_KEY = AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY;
    public static final String FINAL_EXAM_PERIOD_TYPE_KEY = AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY;
    // TODO: Map to Grading Start Date periods
    // public static final String GRADING_PERIOD_START_TYPE_KEY = ????
    public static final String GRADING_PERIOD_END_TYPE_KEY = AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY;

    /**
     * Academic Calendar Process
     */
    public static final String ACADEMIC_CALENDAR_PROCESS_KEY = AtpServiceConstants.ATP_LIFECYCLE_KEY;
    public static final String ACADEMIC_CALENDAR_DRAFT_STATE_KEY = AtpServiceConstants.ATP_DRAFT_STATE_KEY;
    public static final String ACADEMIC_CALENDAR_OFFICIAL_STATE_KEY = AtpServiceConstants.ATP_OFFICIAL_STATE_KEY;
    public static final String[] ACADEMIC_CALENDAR_PROCESS_STATE_KEYS = {ACADEMIC_CALENDAR_DRAFT_STATE_KEY, ACADEMIC_CALENDAR_OFFICIAL_STATE_KEY};

    public static final String TERM_DRAFT_STATE_KEY = AtpServiceConstants.ATP_DRAFT_STATE_KEY;
    public static final String TERM_OFFICIAL_STATE_KEY = AtpServiceConstants.ATP_OFFICIAL_STATE_KEY;

    public static final String HOLIDAY_CALENDAR_DRAFT_STATE_KEY = AtpServiceConstants.ATP_DRAFT_STATE_KEY;
    public static final String HOLIDAY_CALENDAR_OFFICIAL_STATE_KEY = AtpServiceConstants.ATP_OFFICIAL_STATE_KEY;
}
