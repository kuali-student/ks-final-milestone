/*
 * Copyright 2012 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.util.constants;

import org.kuali.student.enrollment.roster.dto.LprRosterInfo;
import org.kuali.student.enrollment.roster.dto.LprRosterEntryInfo;

import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * Constants used by for LuprRosterService
 */

public class LprRosterServiceConstants {

    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "roster";
    public static final String REF_OBJECT_URI_LPR_ROSTER = NAMESPACE + "/"
            + LprRosterInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_LPR_ROSTER_ENTRY = NAMESPACE + "/"
            + LprRosterEntryInfo.class.getSimpleName();

    /**
     * LprRoster types
     */
    public static final String LPRROSTER_COURSE_FINAL_GRADEROSTER_TYPE_KEY = "kuali.lpr.roster.type.course.grade.final";
    // reverted public static final String LPRROSTER_COURSE_FINAL_GRADEROSTER_TYPE_KEY = "kuali.roster.type.course.assessment.final ";
    // roster type keys
    // TODO: rename the one above to the one below as they point to the same thing
    public static final String LPRROSTER_COURSE_FINAL_GRADE_TYPE_KEY = "kuali.lpr.roster.type.course.grade.final";
    public static final String LPRROSTER_COURSE_MIDTERM_GRADE_TYPE_KEY = "kuali.lpr.roster.type.course.assessment.interim.midterm";
    public static final String LPRROSTER_COURSE_WAITLIST_TYPE_KEY = "kuali.lpr.roster.type.course.waitlist";
    public static final String LPRROSTER_COURSE_HOLD_UNTIL_LIST_TYPE_KEY = "kuali.lpr.roster.type.course.hold.until.list";    
    /**
     * LprRoster states
     */
      // grading roster process and state key
    public static final String LPRROSTER_GRADING_POCESS_KEY = "kuali.lpr.roster.process.course.grading";
    public static final String LPRROSTER_READY_STATE_KEY = "kuali.lpr.roster.state.ready";    
    public static final String LPRROSTER_SAVED_STATE_KEY = "kuali.lpr.roster.state.saved";
    public static final String LPRROSTER_SUBMITTED_STATE_KEY = "kuali.lpr.roster.state.submitted";
    public static final String[] LPRROSTER_GRADING_POCESS_KEYS = {LPRROSTER_READY_STATE_KEY, LPRROSTER_SAVED_STATE_KEY,
        LPRROSTER_SUBMITTED_STATE_KEY};
    // other roster process state keys
    public static final String LPRROSTER_LIST_POCESS_KEY = "kuali.lpr.roster.process.lists";   
    public static final String LPRROSTER_CREATED_STATE_KEY = "kuali.lpr.roster.state.created";    
    public static final String[] LPRROSTER_LIST_POCESS_KEYS = {LPRROSTER_CREATED_STATE_KEY};
    // TODO: switch these constants to those above since they now point to the same thing
    public static final String LPRROSTER_COURSE_FINAL_GRADEROSTER_NEW_STATE_KEY = "kuali.lpr.roster.state.created"; // Is this needed/to-be-used?
    public static final String LPRROSTER_COURSE_FINAL_GRADEROSTER_READY_STATE_KEY = "kuali.lpr.roster.state.ready";
    public static final String LPRROSTER_COURSE_FINAL_GRADEROSTER_SAVED_STATE_KEY = "kuali.lpr.roster.state.saved";
    public static final String LPRROSTER_COURSE_FINAL_GRADEROSTER_SUBMITTED_STATE_KEY = "kuali.lpr.roster.state.submitted";
}
