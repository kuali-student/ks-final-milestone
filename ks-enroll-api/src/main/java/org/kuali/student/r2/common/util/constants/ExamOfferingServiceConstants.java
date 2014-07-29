/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by Mezba Mahtab (mezba.mahtab@utoronto.ca) on 7/15/13
 */
package org.kuali.student.r2.common.util.constants;

import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;

/**
 * This class represents constants for the ExamOffering Service.
 *
 * @author Mezba Mahtab (mezba.mahtab@utoronto.ca)
 */
public class ExamOfferingServiceConstants {

    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "examOffering";
    public static final String SERVICE_NAME_LOCAL_PART = ExamOfferingService.class.getSimpleName();
    public static final String REF_OBJECT_URI_EXAM_OFFERING = NAMESPACE + "/" + ExamOfferingInfo.class.getSimpleName();

    /////////////////
    // TYPES
    /////////////////

    public static final String EXAM_OFFERING_TYPE_KEY = "kuali.lui.type.exam.offering";
    public static final String EXAM_OFFERING_FINAL_TYPE_KEY = "kuali.lui.type.exam.offering.final";

    /////////////////
    // STATES
    /////////////////
    public static final String EXAM_OFFERING_DRAFT_STATE_KEY = "kuali.lui.exam.offering.state.draft";
    public static final String EXAM_OFFERING_OFFERED_STATE_KEY = "kuali.lui.exam.offering.state.offered";
    public static final String EXAM_OFFERING_SUSPENDED_STATE_KEY = "kuali.lui.exam.offering.state.suspended";
    public static final String EXAM_OFFERING_CANCELED_STATE_KEY = "kuali.lui.exam.offering.state.canceled";

    public static final String EXAM_OFFERING_SCHEDULING_EXEMPT_STATE_KEY = "kuali.lui.exam.offering.scheduling.state.exempt";
    public static final String EXAM_OFFERING_SCHEDULING_UNSCHEDULED_STATE_KEY = "kuali.lui.exam.offering.scheduling.state.unscheduled";
    public static final String EXAM_OFFERING_SCHEDULING_MATRIX_ERROR_STATE_KEY = "kuali.lui.exam.offering.scheduling.state.matrix.error";

    public static final String FINAL_EXAM_DRIVER_ATTR = "kuali.attribute.final.exam.driver";
    public static final String FINAL_EXAM_ACTIVITY_DRIVER_ATTR = "kuali.attribute.final.exam.activity.driver";
    public static final String EXAM_OFFERING_MATRIX_OVERRIDE_ATTR = "kuali.attribute.exam.offering.matrix.override";
    public static final String EXAM_OFFERING_SCHEDULING_STATE_ATTR = "kuali.attribute.exam.offering.scheduling.state.key";

    /////////////////
    // RESULT ACTIONS
    /////////////////
    public static final String EXAM_OFFERING_CREATED = "success.enroll.examoffering.finalexam.created";
    public static final String EXAM_OFFERING_UPDATED = "success.enroll.examoffering.finalexam.updated";
    public static final String EXAM_OFFERING_UNCHANGED = "success.enroll.examoffering.finalexam.unchanged";
    public static final String EXAM_OFFERING_MATRIX_NOT_FOUND = "warning.enroll.examoffering.finalexam.matrix.not.found";
    public static final String EXAM_OFFERING_AO_MATRIX_MATCH_NOT_FOUND = "warning.enroll.examoffering.finalexam.ao.matrix.match.not.found";
    public static final String EXAM_OFFERING_CO_MATRIX_MATCH_NOT_FOUND = "warning.enroll.examoffering.finalexam.co.matrix.match.not.found";
    public static final String EXAM_OFFERING_ACTIVITY_OFFERING_TIMESLOTS_NOT_FOUND = "warning.enroll.examoffering.finalexam.activity.offering.timeslots.not.found";
    public static final String EXAM_OFFERING_EXAM_PERIOD_NOT_FOUND = "error.enroll.examoffering.finalexam.exam.period.not.found";
    public static final String EXAM_OFFERING_SOC_NOT_FOUND = "error.enroll.examoffering.finalexam.soc.not.found";
    public static final String EXAM_OFFERING_TERM_NOT_FOUND = "error.enroll.examoffering.finalexam.term.not.found";
    public static final String EXAM_OFFERING_DRIVER_UNKNOWN = "info.enroll.examoffering.finalexam.driver.unknown";
    public static final String EXAM_OFFERING_GENERATED_PER_CO = "success.enroll.examoffering.finalexam.generated.per.co";
    public static final String EXAM_OFFERING_GENERATED_PER_FO = "success.enroll.examoffering.finalexam.generated.per.fo";
    public static final String EXAM_OFFERING_GENERATED_PER_AO = "success.enroll.examoffering.finalexam.generated.per.ao";
    public static final String EXAM_OFFERING_SLOTTED_SUCCESS = "success.enroll.examoffering.finalexam.slotted";
    public static final String EXAM_OFFERING_REMOVE_RDL_SUCCESS = "success.enroll.examoffering.finalexam.rdl.removed";

    public static final String EXAM_OFFERING_BULK_PROCESS = "error.enroll.examoffering.finalexam.bulk.process";
    public static final String EXAM_OFFERING_BULK_PROCESS_NOT_COMPLETED = "error.enroll.examoffering.finalexam.bulk.process.not.completed";

}
