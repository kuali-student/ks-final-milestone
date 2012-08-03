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
import org.kuali.student.r2.common.constants.CommonServiceConstants;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Course Offering Service Constants
 * @see LuiServiceConstants
 *
 * @author nwright
 */
public class CourseOfferingSetServiceConstants {

    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "courseOfferingSet";
    public static final String SERVICE_NAME_LOCAL_PART = "socService";
    public static final String REF_OBJECT_URI_SOC = NAMESPACE + "/" + SocInfo.class.getSimpleName();
    public static final String MAIN_SOC_TYPE_KEY = "kuali.soc.type.main";
    public static final String SUBJECT_AREA_SOC_TYPE_KEY = "kuali.soc.type.subject.area";
    public static final String UNITS_CONTENT_OWNER_SOC_TYPE_KEY = "kuali.soc.type.units.content.owner";
    public static final String UNITS_DEPLOYMENT_OWNER_SOC_TYPE_KEY = "kuali.soc.type.units.deployment.owner";
    // SOC states (see https://wiki.kuali.org/display/STUDENT/Course+Offering+Set+Types+and+States)
    public static final String DRAFT_SOC_STATE_KEY = "kuali.soc.state.draft";
    public static final String OPEN_SOC_STATE_KEY = "kuali.soc.state.open";
    public static final String LOCKED_SOC_STATE_KEY = "kuali.soc.state.locked ";
    public static final String FINALEDITS_SOC_STATE_KEY = "kuali.soc.state.finaledits";
    public static final String PUBLISHED_SOC_STATE_KEY = "kuali.soc.state.published";
    public static final String CLOSED_SOC_STATE_KEY = "kuali.soc.state.closed ";
    // rollover  types
    public static final String ROLLOVER_RESULT_TYPE_KEY = "kuali.soc.rollover.result.rollover";
    public static final String REVERSE_ROLLOVER_RESULT_TYPE_KEY = "kuali.soc.rollover.result.reverse";
    // states for rollover 
    public static final String SUBMITTED_RESULT_STATE_KEY = "kuali.soc.rollover.state.submitted";
    public static final String RUNNING_RESULT_STATE_KEY = "kuali.soc.rollover.state.running";
    public static final String FINISHED_RESULT_STATE_KEY = "kuali.soc.rollover.state.finished";
    public static final String ABORTED_RESULT_STATE_KEY = "kuali.soc.rollover.state.aborted";
    // item types
    public static final String CREATE_RESULT_ITEM_TYPE_KEY = "kuali.soc.rollover.result.item.create";
    public static final String DELETE_RESULT_ITEM_TYPE_KEY = "kuali.soc.rollover.result.item.delete";
    // item  states
    public static final String SUCCESS_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.item.state.success";
    public static final String WARNING_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.item.state.warning";
    public static final String INFO_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.item.state.info";
    public static final String ERROR_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.result.item.state.error";
    public static final String CREATED_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.result.item.state.processed.created";
    public static final String UPDATED_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.result.item.state.processed.updated";
    public static final String DELETED_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.result.item.state.processed.deleted";
    public static final String CANCELED_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.result.item.state.canceled";
    public static final String NEWVERSION_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.result.item.state.new.version";
    public static final String RETIRED_RESULT_ITEM_STATE_KEY = "kuali.soc.rollover.result.item.state.retired";

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
    public static final String NO_INSTRUCTORS_OPTION_KEY = "kuali.rollover.whatdata.noinstructors";
    // general processing
    public static final String LOG_SUCCESSES_OPTION_KEY = "kuali.rollover.processing.log.successes";
    public static final String LOG_FREQUENCY_OPTION_KEY_PREFIX = "kuali.rollover.processing.log.frequency.";
    public static final String HALT_ERRORS_MAX_OPTION_KEY_PREFIX = "kuali.rollover.processing.halt.error.max.";
    public static final String RUN_SYNCHRONOUSLY_OPTION_KEY = "kuali.rollover.processing.run.synchronously";
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
}
