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
 * Lui Service Constants
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
//    public static final String COURSE_BUNDLE_TYPE_KEY = "kuali.lui.type.course.bundle";
    public static final String COURSE_OFFERING_TYPE_KEY = "kuali.lui.type.course.offering";
    public static final String REGISTRATION_GROUP_TYPE_KEY = "kuali.lui.type.registration.group";
//    public static final String COURSE_FORMAT_OFFERING_TYPE_KEY = "kuali.lui.type.course.format.offering";
    /**
     * TODO: LuiSeatCounts to hold seatpool information
     */
    /**
     * activity types
     */
    public static final String LECTURE_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.lecture";
    public static final String LAB_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.lab";
    public static final String DISCUSSION_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.discussion";
    public static final String TUTORIAL_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.tutorial";
    public static final String WEB_LECTURE_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.weblecture";
    public static final String WEB_DISCUSS_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.webdiscussion";
    public static final String DIRECTED_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.directed";
    public static final String STUDIO_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.studio";
    public static final String CORRESPOND_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.correspond";
    public static final String ACTIVITY_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.activity";
    public static final String COLLOQUIUM_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.colloquium";
    public static final String DEMONSTRATION_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.demonstration";
    public static final String FIELD_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.field";
    public static final String HOMEWORK_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.homework";
    public static final String INDEPEND_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.independ";
    public static final String INTERNSHIP_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.internship";
    public static final String PRIVATE_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.private";
    public static final String RECITATION_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.recitation";
    public static final String RESEARCH_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.research";
    public static final String SELF_PACED_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.selfpaced";
    public static final String COMP_BASED_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.compbased";
    public static final String VIDEO_CONF_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.videoconf";
    public static final String[] ALL_ACTIVITY_TYPES = {
        LECTURE_ACTIVITY_OFFERING_TYPE_KEY,
        LAB_ACTIVITY_OFFERING_TYPE_KEY,
        DISCUSSION_ACTIVITY_OFFERING_TYPE_KEY,
        TUTORIAL_ACTIVITY_OFFERING_TYPE_KEY,
        WEB_LECTURE_ACTIVITY_OFFERING_TYPE_KEY,
        WEB_DISCUSS_ACTIVITY_OFFERING_TYPE_KEY,
        DIRECTED_ACTIVITY_OFFERING_TYPE_KEY,
        STUDIO_ACTIVITY_OFFERING_TYPE_KEY,
        CORRESPOND_ACTIVITY_OFFERING_TYPE_KEY,
        ACTIVITY_ACTIVITY_OFFERING_TYPE_KEY,
        COLLOQUIUM_ACTIVITY_OFFERING_TYPE_KEY,
        DEMONSTRATION_ACTIVITY_OFFERING_TYPE_KEY,
        FIELD_ACTIVITY_OFFERING_TYPE_KEY,
        HOMEWORK_ACTIVITY_OFFERING_TYPE_KEY,
        INDEPEND_ACTIVITY_OFFERING_TYPE_KEY,
        INTERNSHIP_ACTIVITY_OFFERING_TYPE_KEY,
        PRIVATE_ACTIVITY_OFFERING_TYPE_KEY,
        RECITATION_ACTIVITY_OFFERING_TYPE_KEY,
        RESEARCH_ACTIVITY_OFFERING_TYPE_KEY,
        SELF_PACED_ACTIVITY_OFFERING_TYPE_KEY,
        COMP_BASED_ACTIVITY_OFFERING_TYPE_KEY,
        VIDEO_CONF_ACTIVITY_OFFERING_TYPE_KEY};
    /**
     * States
     */
    public static final String COURSE_OFFERING_PROCESS_KEY = "kuali.course.offering.process";
    public static final String LUI_DRAFT_STATE_KEY = "kuali.lui.state.draft";
    public static final String LUI_SUBMITTED_STATE_KEY = "kuali.lui.state.submitted";
    public static final String LUI_APROVED_STATE_KEY = "kuali.lui.state.approved";
    public static final String LUI_SCHEDULED_STATE_KEY = "kuali.lui.state.scheduled";
    public static final String LUI_DELETED_STATE_KEY = "kuali.lui.state.deleted";
    public static final String LUI_OFFERED_STATE_KEY = "kuali.lui.state.offered";
    public static final String LUI_CANCELED_STATE_KEY = "kuali.lui.state.canceled";
    public static final String LUI_SUSPENDED_STATE_KEY = "kuali.lui.state.suspended";
    public static final String[] COURSE_OFFERING_PROCESS_STATE_KEYS = {LUI_DRAFT_STATE_KEY,
        LUI_SUBMITTED_STATE_KEY,
        LUI_APROVED_STATE_KEY,
        LUI_SCHEDULED_STATE_KEY,
        LUI_DELETED_STATE_KEY,
        LUI_OFFERED_STATE_KEY,
        LUI_CANCELED_STATE_KEY,
        LUI_SUSPENDED_STATE_KEY
    };
    /**
     *  LUI LUI relation types
     */
    public static final String ASSOCIATED_LUI_LUI_RELATION_TYPE_KEY = "kuali.lui.lui.relation.associated";
    /**
     * LUI LUI relation States
     */
    public static final String LUI_LUI_RELATION_PROCESS_KEY = "kuali.lui.lui.relationship.process";
    public static final String LUI_LUI_RELATION_ACTIVE_STATE_KEY = "kuali.lui.lui.relation.state.active";
    public static final String LUI_LUI_RELATION_INACTIVE_STATE_KEY = "kuali.lui.lui.relation.state.inactive";
    public static final String[] LUI_LUI_RELATION_PROCESS_KEYS = {LUI_LUI_RELATION_ACTIVE_STATE_KEY,
        LUI_LUI_RELATION_INACTIVE_STATE_KEY};
    
    /**
     *  LUI CAPACITY types
     */
    public static final String SEATPOOL_LUI_CAPACITY_TYPE_KEY = "kuali.lui.capacity.seatpool";
    
    /**
     * LUI capacity States
     */
    public static final String LUI_CAPACITY_PROCESS_KEY = "kuali.lui.capacity.process";
    public static final String LUI_CAPACITY_ACTIVE_STATE_KEY = "kuali.lui.capacity.active";
    public static final String LUI_CAPACITY_INACTIVE_STATE_KEY = "kuali.lui.capacity.inactive";
    public static final String[] LUI_CAPACITY_PROCESS_KEYS = {LUI_CAPACITY_ACTIVE_STATE_KEY,
        LUI_CAPACITY_INACTIVE_STATE_KEY};
}
