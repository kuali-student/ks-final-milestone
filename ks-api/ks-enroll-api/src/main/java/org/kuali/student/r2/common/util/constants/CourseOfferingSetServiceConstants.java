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

import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Course Offering Service Constants
 * @see LuiServiceConstants
 *
 * @author nwright
 */
public class CourseOfferingSetServiceConstants {

    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseOfferingSet";
    public static final String SERVICE_NAME_LOCAL_PART = CourseOfferingSetService.class.getSimpleName();
    public static final String REF_OBJECT_URI_SOC = NAMESPACE + "/" + SocInfo.class.getSimpleName();
    public static final String MAIN_SOC_TYPE_KEY = "kuali.soc.type.main";
    public static final String SUBJECT_AREA_SOC_TYPE_KEY = "kuali.soc.type.subject.area";
    public static final String UNITS_CONTENT_OWNER_SOC_TYPE_KEY = "kuali.soc.type.units.content.owner";
    public static final String UNITS_DEPLOYMENT_OWNER_SOC_TYPE_KEY = "kuali.soc.type.units.deployment.owner";

    // SOC states (see https://wiki.kuali.org/display/STUDENT/Set+of+Course+Offerings+%28SOC%29+States)
    // SOC states (see https://wiki.kuali.org/display/STUDENT/Course+Offering+Set+Types+and+States)
    public static final String DRAFT_SOC_STATE_KEY = "kuali.soc.state.draft";
    public static final String OPEN_SOC_STATE_KEY = "kuali.soc.state.open";
    public static final String LOCKED_SOC_STATE_KEY = "kuali.soc.state.locked";
    public static final String FINALEDITS_SOC_STATE_KEY = "kuali.soc.state.finaledits";
    public static final String PUBLISHING_SOC_STATE_KEY = "kuali.soc.state.publishing";
    public static final String PUBLISHED_SOC_STATE_KEY = "kuali.soc.state.published";
    public static final String CLOSED_SOC_STATE_KEY = "kuali.soc.state.closed";

    public static final String SOC_LIFECYCLE_KEY = "kuali.soc.lifecycle";  // Not a state
    public static final String[] SOC_LIFECYCLE_STATE_KEYS = {
            DRAFT_SOC_STATE_KEY,
            OPEN_SOC_STATE_KEY,
            LOCKED_SOC_STATE_KEY,
            FINALEDITS_SOC_STATE_KEY,
            PUBLISHING_SOC_STATE_KEY,
            PUBLISHED_SOC_STATE_KEY,
            CLOSED_SOC_STATE_KEY
    };

    // SOC scheduling states (see https://wiki.kuali.org/display/STUDENT/SOC+Scheduling+States )
    public static final String SOC_SCHEDULING_STATE_NOT_STARTED = "kuali.soc.scheduling.state.notstarted";
    public static final String SOC_SCHEDULING_STATE_IN_PROGRESS = "kuali.soc.scheduling.state.inprogress";
    public static final String SOC_SCHEDULING_STATE_COMPLETED   = "kuali.soc.scheduling.state.completed";
    public static final String[] ALL_SOC_SCHEDULING_STATES = {
            SOC_SCHEDULING_STATE_NOT_STARTED,
            SOC_SCHEDULING_STATE_IN_PROGRESS,
            SOC_SCHEDULING_STATE_COMPLETED
    };

    // SOC scheduling states (see https://wiki.kuali.org/display/STUDENT/SOC+Scheduling+States )
    public static final String EO_SLOTTING_STATE_NOT_STARTED = "kuali.eo.slotting.state.notstarted";
    public static final String EO_SLOTTING_STATE_IN_PROGRESS = "kuali.eo.slotting.state.inprogress";
    public static final String EO_SLOTTING_STATE_COMPLETED   = "kuali.eo.slotting.state.completed";
    public static final String[] ALL_EO_SLOTTING_STATES = {
            EO_SLOTTING_STATE_NOT_STARTED,
            EO_SLOTTING_STATE_IN_PROGRESS,
            EO_SLOTTING_STATE_COMPLETED
    };

    // rollover  types
    public static final String ROLLOVER_RESULT_TYPE_KEY = "kuali.soc.rollover.results.type.rollover";
    public static final String REVERSE_ROLLOVER_RESULT_TYPE_KEY = "kuali.soc.rollover.results.type.reverse";

    // states for rollover
    public static final String SUBMITTED_RESULT_STATE_KEY = "kuali.soc.rollover.state.submitted";
    public static final String RUNNING_RESULT_STATE_KEY = "kuali.soc.rollover.state.running";
    public static final String FINISHED_RESULT_STATE_KEY = "kuali.soc.rollover.state.finished";
    public static final String ABORTED_RESULT_STATE_KEY = "kuali.soc.rollover.state.aborted";

    // item types
    public static final String CREATE_RESULT_ITEM_TYPE_KEY = "kuali.soc.rollover.result.item.type.create";
    public static final String DELETE_RESULT_ITEM_TYPE_KEY = "kuali.soc.rollover.result.item.type.delete";

    // item  states
    public static final String CREATED_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.result.item.state.processed.created";
    public static final String UPDATED_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.result.item.state.processed.updated";
    public static final String DELETED_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.result.item.state.processed.deleted";
    public static final String ERROR_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.result.item.state.error";
    public static final String CANCELED_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.result.item.state.canceled";
    public static final String NEWVERSION_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.result.item.state.new.version";
    public static final String RETIRED_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.result.item.state.retired";
    public static final List<String> SUCCESSFUL_RESULT_ITEM_STATES = Collections.unmodifiableList(Arrays.asList(
            CREATED_RESULT_ITEM_STATE_KEY,
            UPDATED_RESULT_ITEM_STATE_KEY,
            DELETED_RESULT_ITEM_STATE_KEY ));

    public static final String INFO_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.item.state.info";

    // dynamic attribute key for parameters
    public static final String PARAMETER_SOURCE_SOC_ID_ATTR_KEY = "kuali.parameter.source.soc.id";
    public static final String PARAMETER_TARGET_TERM_ID_ATTR_KEY = "kuali.parameter.target.term.id";
    public static final String PARAMETER_OPTION_KEY_ATTR_KEY = "kuali.parameter.option.key";

    // dynamic attribute key for results
    public static final String GLOBAL_RESULT_TARGET_SOC_ID_ATTR_KEY = "kuali.global.result.targetSocId";

    // which courses
    public static final String STILL_OFFERABLE_OPTION_KEY = "kuali.rollover.whatcourses.stillofferable";
    public static final String IF_NO_NEW_VERSION_OPTION_KEY = "kuali.rollover.whatcourses.ifnonewversion";
    public static final String IGNORE_CANCELLED_OPTION_KEY = "kuali.rollover.whatcourses.ignorecancelled";
    public static final String SKIP_IF_ALREADY_EXISTS_OPTION_KEY = "kuali.rollover.whatcourses.skipifalreadyexists";
    public static final String IGNORE_CANCELLED_AO_OPTION_KEY = "kuali.rollover.whatdata.nocanceledactivityofferings";

    // what data
    public static final String USE_CANONICAL_OPTION_KEY = "kuali.rollover.whatdata.usecanonical";
    public static final String NO_SCHEDULE_OPTION_KEY = "kuali.rollover.whatdata.noschedule";
    public static final String NO_SCHEDULE_ROOM_OPTION_KEY = "kuali.rollover.whatdata.noroom";
    public static final String NO_INSTRUCTORS_OPTION_KEY = "kuali.rollover.whatdata.noinstructors";

    // general processing
    public static final String LOG_SUCCESSES_OPTION_KEY = "kuali.rollover.processing.log.successes";
    public static final String LOG_FREQUENCY_OPTION_KEY_PREFIX = "kuali.rollover.processing.log.frequency.";
    public static final String HALT_ERRORS_MAX_OPTION_KEY_PREFIX = "kuali.rollover.processing.halt.error.max.";
    public static final String RUN_SYNCHRONOUSLY_OPTION_KEY = "kuali.rollover.processing.run.synchronously";
    public static final String BYPASS_BUSINESS_LOGIC_ON_SOC_STATE_CHANGE_FOR_AFT_TESTING = "kuali.aft-decorator.bypass.business.logic";

    // scheduling
    public static final String RUN_SCHEDULING_SYNCHRONOUSLY_OPTION_KEY = "kuali.scheduling.processing.run.synchronously";

    // Whether to check target term/subterm for official states at the rolloverCourseOffering level
    // Or at the rolloverSoc level.  If this option key exists, then no need for rolloverCourseOffering
    // to make this check.
    public static final String TARGET_TERM_VALIDATED_OPTION_KEY = "kuali.rollover.option.targetterm.validated";

    // KSENROLL-8062 Dynamic attributes for rolling over individual CO information that has access to "global settings"
    public static final String ROLLOVER_ASSIST_ID_DYNATTR_KEY = "kuali.rollover.context.att.rollover.assist.id";

    // KSENROLL-9460 Option key to continue without generating examofferings
    public static final String CONTINUE_WITHOUT_EXAM_OFFERINGS_OPTION_KEY = "kuali.rollover.option.continue.without.exam.offerings";

    // dynamic attributes for rollover info
    public static final String CO_CREATED_RESULT_DYNATTR_KEY = "kuali.soc.rollover.result.dynattr.course.offerings.created";
    public static final String CO_SKIPPED_RESULT_DYNATTR_KEY = "kuali.soc.rollover.result.dynattr.course.offerings.skipped";
    public static final String AO_CREATED_RESULT_DYNATTR_KEY = "kuali.soc.rollover.result.dynattr.activity.offerings.created";
    public static final String AO_SKIPPED_RESULT_DYNATTR_KEY = "kuali.soc.rollover.result.dynattr.activity.offerings.skipped";
    public static final String DATE_INITIATED_RESULT_DYNATTR_KEY = "kuali.soc.rollover.result.dynattr.date.initiated";
    public static final String DATE_COMPLETED_RESULT_DYNATTR_KEY = "kuali.soc.rollover.result.dynattr.date.completed";
    public static final Set<String> ALL_RESULT_DYNATTR_KEYS;
    static { // Store all the dynamic attribute keys in a set...makes lookup easier
        ALL_RESULT_DYNATTR_KEYS = new HashSet<String>();
        ALL_RESULT_DYNATTR_KEYS.add(CO_CREATED_RESULT_DYNATTR_KEY);
        ALL_RESULT_DYNATTR_KEYS.add(CO_SKIPPED_RESULT_DYNATTR_KEY);
        ALL_RESULT_DYNATTR_KEYS.add(AO_CREATED_RESULT_DYNATTR_KEY);
        ALL_RESULT_DYNATTR_KEYS.add(AO_SKIPPED_RESULT_DYNATTR_KEY);
        ALL_RESULT_DYNATTR_KEYS.add(DATE_INITIATED_RESULT_DYNATTR_KEY);
        ALL_RESULT_DYNATTR_KEYS.add(DATE_COMPLETED_RESULT_DYNATTR_KEY);
    }

    // general processing
    public static final String REVERSE_JUST_CREATES_OPTION_KEY = "kuali.reverse.rollover.just.creates";

    // canonical to course offering options
    public static final String CREDITS_MATCH_SCHEDULED_HOURS_OPTION_KEY = "kuali.canonical.course.to.course.offering.credits.match.scheduled.hours";
    public static final String NOT_COURSE_TITLE_OPTION_KEY = "kuali.canonical.course.to.course.offering.not.title";

    // Course is R1.  Grading/credit options appear to have changed in R2, so not worth copying.
    public static final String NOT_GRADING_CREDIT_OPTION_KEY = "kuali.canonical.course.to.course.offering.not.grading.credit.options";
    
    public static final String ACTIVITY_OFFERINGS_CREATED_SOC_ITEM_DYNAMIC_ATTRIBUTE = "activityOfferingsCreated";

    /**
     * Parameters for performing criteria searches for SOCs.
     */
    public static final class SearchParameters {
        public static final String SOC_STATE = "socState";
    }
}
