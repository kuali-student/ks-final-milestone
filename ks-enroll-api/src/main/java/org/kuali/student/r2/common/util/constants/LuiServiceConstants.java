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
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;

import java.util.HashSet;

/**
 * Lui Service Constants
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
public class LuiServiceConstants {

    public static final String SERVICE_NAME_LOCAL_PART = LuiService.class.getSimpleName ();
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "lui";
    public static final String REF_OBJECT_URI_LUI = NAMESPACE + "/" + LuiInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_LUI_LUI_RELATION = NAMESPACE + "/" + LuiLuiRelationInfo.class.getSimpleName();
    public static final String LUI_KEY_PREFIX = "kuali.lui";

    /**
     * Course Offering, Format Offering, Registration Group Types
     */
    public static final String COURSE_OFFERING_TYPE_KEY = "kuali.lui.type.course.offering";
    // 6/13/2012 Per conversation with Norm/Melissa to include course with format in the format offering type key
    public static final String FORMAT_OFFERING_TYPE_KEY = "kuali.lui.type.course.format.offering";
    public static final String REGISTRATION_GROUP_TYPE_KEY = "kuali.lui.type.registration.group";

    public static final boolean isFormatOfferingTypeKey(String possibleType) {
        if (possibleType == null) {
            return false;
        }
        return FORMAT_OFFERING_TYPE_KEY.equals(possibleType.trim());
    }
    /**
     * Activity types
     * https://wiki.kuali.org/display/STUDENT/Activity+Types
     */
    // This is a grouping type used for type-type relations
    public static final String ACTIVITY_OFFERING_GROUP_TYPE_KEY = "kuali.lui.type.grouping.activity";
    // These are the activity offering types
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
    public static final String CLERKSHIP_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.clerkship";
    public static final String CLINIC_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.clinic";
    public static final String CONFERENCE_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.conference";
    public static final String PRACTICUM_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.practicum";
    public static final String QUIZ_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.quiz";
    public static final String SEMINAR_ACTIVITY_OFFERING_TYPE_KEY = "kuali.lui.type.activity.offering.seminar";

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
        VIDEO_CONF_ACTIVITY_OFFERING_TYPE_KEY,
        CLERKSHIP_ACTIVITY_OFFERING_TYPE_KEY,
        CLINIC_ACTIVITY_OFFERING_TYPE_KEY,
        CONFERENCE_ACTIVITY_OFFERING_TYPE_KEY,
        PRACTICUM_ACTIVITY_OFFERING_TYPE_KEY,
        QUIZ_ACTIVITY_OFFERING_TYPE_KEY,
        SEMINAR_ACTIVITY_OFFERING_TYPE_KEY
    };

    public static final String ACTIVITY_OFFERING_TYPE_KEY_PREFIX = "kuali.lui.type.activity.offering."; // Not a type
    // TODO: May want to do this for other groupings
    private static HashSet<String> ACTIVITY_TYPES_HASH_SET = null;
    public static synchronized boolean isActivityType(String possibleActivityType) {
        if (ACTIVITY_TYPES_HASH_SET == null) {
            // One time initialization
            ACTIVITY_TYPES_HASH_SET = new HashSet<String>();
            for (String aType: ALL_ACTIVITY_TYPES) {
                ACTIVITY_TYPES_HASH_SET.add(aType);
            }
        }
        return ACTIVITY_TYPES_HASH_SET.contains(possibleActivityType);
    }
    /**
     * Course Offering States based on:
     * https://wiki.kuali.org/display/STUDENT/Learning+Unit+Instance+Types+and+States#LearningUnitInstanceTypesandStates-Activity%2CCourseandRegGroupOffering
     * Implemented: 6/14/2012  by cclin
     */
    public static final String COURSE_OFFERING_LIFECYCLE_KEY = "kuali.course.offering.lifecycle";
    public static final String LUI_CO_STATE_DRAFT_KEY = "kuali.lui.course.offering.state.draft";
    public static final String LUI_CO_STATE_PLANNED_KEY = "kuali.lui.course.offering.state.planned";
    public static final String LUI_CO_STATE_OFFERED_KEY = "kuali.lui.course.offering.state.offered";
    public static final String LUI_CO_STATE_CANCELED_KEY = "kuali.lui.course.offering.state.canceled";
    public static final String LUI_CO_STATE_SUSPENDED_KEY = "kuali.lui.course.offering.state.suspended";

    public static final String[] COURSE_OFFERING_LIFECYCLE_STATE_KEYS = {
        LUI_CO_STATE_DRAFT_KEY,
        LUI_CO_STATE_PLANNED_KEY,
        LUI_CO_STATE_OFFERED_KEY,
        LUI_CO_STATE_CANCELED_KEY,
        LUI_CO_STATE_SUSPENDED_KEY
    };

    public static boolean isValidCourseOfferingState(String possibleState) {
        if (possibleState == null) {
            return false;
        }
        for (String state: COURSE_OFFERING_LIFECYCLE_STATE_KEYS) {
            if (state.equals(possibleState)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Format Offering States based on:
     * https://wiki.kuali.org/display/STUDENT/Learning+Unit+Instance+Types+and+States#LearningUnitInstanceTypesandStates-Activity%2CCourseandRegGroupOffering
     * Implemented: 6/14/2012  by cclin
     * The process is identical to the course offering.
     */
    public static final String FORMAT_OFFERING_LIFECYCLE_KEY = "kuali.format.offering.lifecycle";
    public static final String LUI_FO_STATE_DRAFT_KEY = "kuali.lui.format.offering.state.draft";
    public static final String LUI_FO_STATE_PLANNED_KEY = "kuali.lui.format.offering.state.planned";
    public static final String LUI_FO_STATE_OFFERED_KEY = "kuali.lui.format.offering.state.offered";
    public static final String LUI_FO_STATE_CANCELED_KEY = "kuali.lui.format.offering.state.canceled";
    public static final String LUI_FO_STATE_SUSPENDED_KEY = "kuali.lui.format.offering.state.suspended";


    public static final String[] FORMAT_OFFERING_LIFECYCLE_STATE_KEYS = {
            LUI_FO_STATE_DRAFT_KEY,
            LUI_FO_STATE_PLANNED_KEY,
            LUI_FO_STATE_OFFERED_KEY,
            LUI_FO_STATE_CANCELED_KEY,
            LUI_FO_STATE_SUSPENDED_KEY
    };

    public static boolean isValidFormatOfferingState(String possibleState) {
        if (possibleState == null) {
            return false;
        }
        for (String state: FORMAT_OFFERING_LIFECYCLE_STATE_KEYS) {
            if (state.equals(possibleState)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Activity Offering States based on:
     * https://wiki.kuali.org/display/STUDENT/Learning+Unit+Instance+Types+and+States#LearningUnitInstanceTypesandStates-Activity%2CCourseandRegGroupOffering
     * Implemented: 6/14/2012  by cclin
     */
    public static final String ACTIVITY_OFFERING_LIFECYCLE_KEY = "kuali.activity.offering.lifecycle";
    public static final String LUI_AO_STATE_DRAFT_KEY = "kuali.lui.activity.offering.state.draft";
    public static final String LUI_AO_STATE_SUBMITTED_KEY = "kuali.lui.activity.offering.state.submitted";
    public static final String LUI_AO_STATE_APPROVED_KEY = "kuali.lui.activity.offering.state.approved";
    public static final String LUI_AO_STATE_OFFERED_KEY = "kuali.lui.activity.offering.state.offered";
    public static final String LUI_AO_STATE_SUSPENDED_KEY = "kuali.lui.activity.offering.state.suspended";
    public static final String LUI_AO_STATE_CANCELED_KEY = "kuali.lui.activity.offering.state.canceled";

    public static final String[] ACTIVITY_OFFERING_LIFECYCLE_STATE_KEYS = {
        LUI_AO_STATE_DRAFT_KEY,
        LUI_AO_STATE_SUBMITTED_KEY,
        LUI_AO_STATE_APPROVED_KEY,
        LUI_AO_STATE_OFFERED_KEY,
        LUI_AO_STATE_SUSPENDED_KEY,
        LUI_AO_STATE_CANCELED_KEY
    };

    public static boolean isValidActivityOfferingState(String possibleState) {
        if (possibleState == null) {
            return false;
        }
        for (String state: ACTIVITY_OFFERING_LIFECYCLE_STATE_KEYS) {
            if (state.equals(possibleState)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Activity Offering Scheduling States
     * https://wiki.kuali.org/display/STUDENT/Activity+Offering+Scheduling+Lifecycle
     * Implemented: 8/30/2012  by Mezba Mahtab
     */
    public static final String LUI_AO_SCHEDULING_STATE_LIFECYCLE_KEY = "kuali.activity.offering.scheduling.lifecycle";
    public static final String LUI_AO_SCHEDULING_STATE_EXEMPT_KEY = "kuali.lui.activity.offering.scheduling.state.exempt";
    public static final String LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY = "kuali.lui.activity.offering.scheduling.state.scheduled";
    public static final String LUI_AO_SCHEDULING_STATE_UNSCHEDULED_KEY = "kuali.lui.activity.offering.scheduling.state.unscheduled";
    public static final String LUI_AO_SCHEDULING_STATE_ERROR_KEY = "kuali.lui.activity.offering.scheduling.state.error";

    public static final String[] LUI_AO_SCHEDULING_STATE_KEYS = {
            LUI_AO_SCHEDULING_STATE_EXEMPT_KEY,
            LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY,
            LUI_AO_SCHEDULING_STATE_UNSCHEDULED_KEY,
            LUI_AO_SCHEDULING_STATE_ERROR_KEY
    };

    public static boolean isValidActivityOfferingSchedulingState(String possibleState) {
        if (possibleState == null) {
            return false;
        }
        for (String state: LUI_AO_SCHEDULING_STATE_KEYS) {
            if (state.equals(possibleState)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deprecated AO states (see https://wiki.kuali.org/display/STUDENT/Learning+Unit+Instance+Types+and+States#LearningUnitInstanceTypesandStates-Activity%2CCourseandRegGroupOffering)
     */
    @Deprecated
    public static final String LUI_DELETED_STATE_KEY = "kuali.lui.state.deleted";


    /**
     * Deprecated AO states (see https://wiki.kuali.org/display/STUDENT/Learning+Unit+Instance+Types+and+States#LearningUnitInstanceTypesandStates-Activity%2CCourseandRegGroupOffering)
     */
    @Deprecated
    public static final String LUI_DRAFT_STATE_KEY = "kuali.lui.state.draft";

    /**
     *  LUI LUI Relation types
     */
    @Deprecated
    public static final String LUI_LUI_RELATION_ASSOCIATED_TYPE_KEY = "kuali.lui.lui.relation.associated";
    // Actual LUI LUI Relation types KSENROLL-2237
    public static final String LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY = "kuali.lui.lui.relation.type.deliveredvia.co2fo";
    public static final String LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY = "kuali.lui.lui.relation.type.deliveredvia.fo2ao";
    public static final String LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY = "kuali.lui.lui.relation.type.deliveredvia.fo2rg";
    public static final String LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY = "kuali.lui.lui.relation.type.registeredforvia.rg2ao";
    // This is a grouping type
    public static final String LUI_LUI_RELATION_REGISTEREDFORVIA_TYPE_KEY = "kuali.lui.lui.relation.type.registeredforvia";
    // This is a grouping type
    public static final String LUI_LUI_RELATION_DELIVEREDVIA_TYPE_KEY = "kuali.lui.lui.relation.type.deliveredvia";

    /**
     * LUI LUI Relation States
     */
    public static final String LUI_LUI_RELATION_LIFECYCLE_KEY = "kuali.lui.lui.relationship.lifecycle";
    public static final String LUI_LUI_RELATION_ACTIVE_STATE_KEY = "kuali.lui.lui.relation.state.active";
    public static final String LUI_LUI_RELATION_INACTIVE_STATE_KEY = "kuali.lui.lui.relation.state.inactive";
    public static final String[] LUI_LUI_RELATION_LIFECYCLE_KEYS = {LUI_LUI_RELATION_ACTIVE_STATE_KEY,
        LUI_LUI_RELATION_INACTIVE_STATE_KEY};
    
    /**
     *  LUI Capacity types
     */
    public static final String SEATPOOL_LUI_CAPACITY_TYPE_KEY = "kuali.lui.capacity.type.seatpool";
    
    /**
     * LUI Capacity States
     */
    public static final String LUI_CAPACITY_LIFECYCLE_KEY = "kuali.lui.capacity.lifecycle";
    public static final String LUI_CAPACITY_ACTIVE_STATE_KEY = "kuali.lui.capacity.state.active";
    public static final String LUI_CAPACITY_INACTIVE_STATE_KEY = "kuali.lui.capacity.state.inactive";
    public static final String[] LUI_CAPACITY_LIFECYCLE_KEYS = {LUI_CAPACITY_ACTIVE_STATE_KEY,
        LUI_CAPACITY_INACTIVE_STATE_KEY};

    /**
     *  LUI Identifier types
     */
    public static final String LUI_IDENTIFIER_OFFICIAL_TYPE_KEY = "kuali.lui.identifier.type.official";
    public static final String LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY = "kuali.lui.identifier.type.cross-listed";

    /**
     * LUI Identifier States
     */
    public static final String LUI_IDENTIFIER_LIFECYCLE_KEY = "kuali.lui.identifier.lifecycle";
    public static final String LUI_IDENTIFIER_ACTIVE_STATE_KEY = "kuali.lui.identifier.state.active";
    public static final String LUI_IDENTIFIER_INACTIVE_STATE_KEY = "kuali.lui.identifier.state.inactive";
    public static final String[] LUI_IDENTIFIER_LIFECYCLE_KEYS = {LUI_IDENTIFIER_ACTIVE_STATE_KEY,
        LUI_IDENTIFIER_INACTIVE_STATE_KEY
    };

    /**
     * LUI Set types
     */
    public static final String LUI_SET_COLOCATED_OFFERING_TYPE_KEY = "kuali.luiset.type.colocated.offering.set";

    /**
     * LUI Set states
     */
    public static final String LUI_SET_LIFECYCLE_KEY = "kuali.luiset.lifecycle";
    public static final String LUI_SET_ACTIVE_STATE_KEY = "kuali.luiset.state.active";
    public static final String LUI_SET_INACTIVE_STATE_KEY = "kuali.luiset.state.inactive";
    public static final String[] LUI_SET_LIFECYCLE_KEYS = { LUI_SET_ACTIVE_STATE_KEY, LUI_SET_INACTIVE_STATE_KEY };
    
    /**
     *  Waitlist types?
     *  These are values for the dynamic attribute, WAIT_LIST_TYPE_KEY_ATTR = "kuali.attribute.wait.list.type.key",
     *  defined in CourseOfferingServiceConstants
     */
    public static final String AUTOMATIC_WAITLIST_TYPE_KEY = "kuali.waitlist.type.automatic";
    public static final String SEMIAUTOMATIC_WAITLIST_TYPE_KEY = "kuali.waitlist.type.semiautomatic";  
    public static final String MANUAL_WAITLIST_TYPE_KEY = "kuali.waitlist.type.manual";
    public static final String[] ALL_WAITLIST_TYPES = { AUTOMATIC_WAITLIST_TYPE_KEY, SEMIAUTOMATIC_WAITLIST_TYPE_KEY,
        MANUAL_WAITLIST_TYPE_KEY
    };
    /**
     *  Registration ordering types?
     */
    public static final String ALL_REGISTRATION_ORDERING_TYPE_KEY = "kuali.registration.ordering.type.all";
//    public static final String SEQUENCED_REGISTRATION_ORDERING_TYPE_KEY = "kuali.registration.ordering.type.sequenced";   
    
    /**
     * Registration Group States
     * (See: https://wiki.kuali.org/display/STUDENT/Learning+Unit+Instance+Types+and+States#LearningUnitInstanceTypesandStates-RegistrationGroupLifecycle)
     *  See Jira KSENROLL-2238
     */
    public static final String REGISTRATION_GROUP_OFFERED_STATE_KEY = "kuali.lui.registration.group.state.offered";
    public static final String REGISTRATION_GROUP_PENDING_STATE_KEY = "kuali.lui.registration.group.state.pending";
    public static final String REGISTRATION_GROUP_CANCELED_STATE_KEY = "kuali.lui.registration.group.state.canceled";
    public static final String REGISTRATION_GROUP_SUSPENDED_STATE_KEY = "kuali.lui.registration.group.state.suspended";
    public static final String REGISTRATION_GROUP_INVALID_STATE_KEY = "kuali.lui.registration.group.state.invalid";
    // The process key for Registration Groups
    public static final String REGISTRATION_GROUP_LIFECYCLE_KEY = "kuali.course.registration.group.lifecycle";
    // List of all Registration Group state keys in an array
    public static final String[] REGISTRATION_GROUP_LIFECYCLE_KEY_STATES = {
            REGISTRATION_GROUP_OFFERED_STATE_KEY,
            REGISTRATION_GROUP_PENDING_STATE_KEY,
            REGISTRATION_GROUP_CANCELED_STATE_KEY,
            REGISTRATION_GROUP_SUSPENDED_STATE_KEY,
            REGISTRATION_GROUP_INVALID_STATE_KEY
    };

    /**
     * known lu codes
     */
    public static final String HONORS_LU_CODE = "kuali.lu.code.honorsOffering";

}
