/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.enrollment.class2.appointment.util;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class AppointmentConstants {

    public static final String REGISTRATION_WINDOWS_EDIT_PAGE = "registrationWindowsEditPage";
    public static final String SELECT_TERM_PAGE = "selectTermForRegWindows";
    public static final String REGISTRATION_WINDOWS_MANAGEMENT_VIEW = "registrationWindowsManagementView";

    public static final String REGISTRATION_WINDOWS_CONTROLLER_PATH = "registrationWindows";

    // Dialogs
    public static final String Registration_Windows_ConfirmDelete_Dialog = "KS-RegistrationWindowsManagement-ConfirmDelete-Dialog";
    public static final String Registration_Windows_ConfirmBreak_Dialog = "KS-RegistrationWindowsManagement-ConfirmBreakAppointments-Dialog";

    //Message keys
    public static final String APPOINTMENT_MSG_INFO_SAVED = "info.enroll.appointment.saved";
    public static final String APPOINTMENT_MSG_INFO_ASSIGNED = "info.enroll.appointment.assigned";
    public static final String APPOINTMENT_MSG_INFO_DELETED = "info.enroll.appointment.windowDeleted";
    public static final String APPOINTMENT_MSG_ERROR_TOO_MANY_STUDENTS = "error.enroll.appointment.tooManyStudents";
    public static final String APPOINTMENT_MSG_INFO_BREAK_APPOINTMENTS_SUCCESS = "info.enroll.appointment.breakAppointmentsSuccess";
    public static final String APPOINTMENT_MSG_ERROR_BREAK_APPOINTMENTS_FAILURE = "error.enroll.appointment.breakAppointmentsFailure";
    public static final String APPOINTMENT_MSG_ERROR_NO_TERMS_FOUND = "error.enroll.appointment.noTermsFound";
    public static final String APPOINTMENT_MSG_ERROR_NO_REG_PERIODS_FOR_TERM = "error.enroll.appointment.noRegPeriodsForTerm";
    public static final String APPOINTMENT_MSG_ERROR_END_DATE_REQUIRED_FOR_UNIFORM = "error.enroll.appointment.endDate.requiredForUniform";
    public static final String APPOINTMENT_MSG_ERROR_END_TIME_REQUIRED_FOR_UNIFORM = "error.enroll.appointment.endTime.requiredForUniform";
    public static final String APPOINTMENT_MSG_ERROR_START_DATE_OUT_OF_RANGE = "error.enroll.appointment.startDate.outOfRange";
    public static final String APPOINTMENT_MSG_ERROR_START_DATE_REQUIRED_FIELD = "error.enroll.appointment.startDate.required";
    public static final String APPOINTMENT_MSG_ERROR_START_TIME_REQUIRED_FIELD = "error.enroll.appointment.startTime.required";
    public static final String APPOINTMENT_MSG_ERROR_START_TIME_AM_PM_REQUIRED_FIELD = "error.enroll.appointment.startTimeAmPm.required";
    public static final String APPOINTMENT_MSG_ERROR_END_TIME_BEFORE_START_TIME = "error.enroll.appointment.endtTime.beforeStartTime";
    public static final String APPOINTMENT_MSG_ERROR_END_TIME_AM_PM_BEFORE_START_TIME_AM_PM = "error.enroll.appointment.endTimeAmPm.beforeStartTimeAmPm";
    public static final String APPOINTMENT_MSG_ERROR_DUPLICATE_WINDOW_FOR_PERIOD = "error.enroll.appointment.duplicateWindowName.forPeriod";

    public static final String APPOINTMENT_MSG_ERROR_END_DATE_OUT_OF_RANGE = "error.enroll.appointment.endDate.outOfRange";
    public static final String APPOINTMENT_MSG_ERROR_WINDOW_SAVE_FAIL = "error.enroll.appointment.windowSaveFailed";
    public static final String APPOINTMENT_MSG_ERROR_END_DATE_IS_BEFORE_START_DATE = "error.enroll.appointment.window.endDateBeforeStartDate";
}