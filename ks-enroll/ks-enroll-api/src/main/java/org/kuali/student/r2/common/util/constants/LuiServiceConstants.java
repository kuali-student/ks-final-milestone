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

import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;

/**
 * This is a description of what this class does - Kamal don't forget
 * to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
public class LuiServiceConstants {

    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "lui";
    public static final String REF_OBJECT_URI_LUI = NAMESPACE + "/" + LuiInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_LUI_LUI_RELATION = NAMESPACE + "/" + LuiLuiRelationInfo.class.getSimpleName();
    public static final String LUI_KEY_PREFIX = "kuali.lui";
    /**
     * Types
     */
    public static final String COURSE_GROUP_OFFERING_TYPE_KEY = "kuali.lui.type.course.group.offering";
    public static final String COURSE_OFFERING_TYPE_KEY = "kuali.lui.type.course.offering";
    public static final String COURSE_FORMAT_OFFERING_TYPE_KEY = "kuali.lui.type.course.format.offering";
    public static final String COURSE_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.course.activity.offering ";
    /**
     * States
     */
    public static final String COURSE_OFFERING_PROCESS_KEY = "kuali.course.offering.process";
    public static final String LUI_PROPOSED_STATE_KEY = "kuali.lui.state.proposed";
    public static final String LUI_SUBMITTED_STATE_KEY = "kuali.lui.state.submitted";
    public static final String LUI_APROVED_STATE_KEY = "kuali.lui.state.approved";
    public static final String LUI_SCHEDULED_STATE_KEY = "kuali.lui.state.scheduled";
    public static final String LUI_WITHDRAWN_STATE_KEY = "kuali.lui.state.withdrawn";
    public static final String LUI_OFFERED_STATE_KEY = "kuali.lui.state.offered";
    public static final String LUI_CHANGED_STATE_KEY = "kuali.lui.state.changed";
    public static final String[] COURSE_OFFERING_PROCESS_STATE_KEYS = {LUI_PROPOSED_STATE_KEY,
        LUI_SUBMITTED_STATE_KEY,
        LUI_APROVED_STATE_KEY,
        LUI_SCHEDULED_STATE_KEY,
        LUI_WITHDRAWN_STATE_KEY,
        LUI_OFFERED_STATE_KEY,
        LUI_CHANGED_STATE_KEY};
}
