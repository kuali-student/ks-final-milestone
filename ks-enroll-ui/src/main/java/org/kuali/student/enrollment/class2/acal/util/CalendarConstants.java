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
package org.kuali.student.enrollment.class2.acal.util;

import org.kuali.rice.krad.util.KRADConstants;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CalendarConstants {

    // Enrollment home page
    public static final String ENROLLMENT_HOME_VIEW = "enrollmentHomeView";

    //HolidayCalendar pages
    public static final String HOLIDAYCALENDAR_COPYPAGE = "holidayCalendarCopyPage";
    public static final String HOLIDAYCALENDAR_EDITPAGE = "holidayCalendarEditPage";
    public static final String HOLIDAYCALENDAR_VIEWPAGE = "holidayCalendarViewPage";
    public static final String HOLIDAYCALENDAR_FLOWVIEW = "holidayCalendarFlowView";

    //Dialogs
    public static final String SEARCH_DELETE_CONFIRMATION_DIALOG = "KS-CalendarSearch-ConfirmDelete-Dialog";
    public static final String ACADEMIC_DELETE_CONFIRMATION_DIALOG = "KS-AcademicCalendar-ConfirmDelete-Dialog";
    public static final String HOLIDAY_DELETE_CONFIRMATION_DIALOG = "KS-HolidayCalendar-ConfirmDelete-Dialog";
    public static final String TERM_DELETE_CONFIRMATION_DIALOG = "KS-AcademicTerm-ConfirmDelete-Dialog";

    //Acal
    public static final String ACAL_CONTROLLER_PATH = "academicCalendar";
    public static final String ACAL_VIEW = "academicCalendarFlowView";
    public static final String ACADEMIC_CALENDAR_COPY_PAGE = "academicCalendarCopyPage";
    public static final String ACADEMIC_CALENDAR_EDIT_PAGE = "academicCalendarEditPage";

    //HolidayCalendar
    public static final String HCAL_CONTROLLER_PATH = "holidayCalendar";
    public static final String HCAL_EDIT_VIEW = "holidayCalendarFlowView";

    //calendar types for search
    public static final String CALENDAR_SEARCH_CONTROLLER_PATH="calendarSearch";
    public static final String ACADEMICCALENDER = "AcademicCalendar";
    public static final String HOLIDAYCALENDER = "HolidayCalendar";
    public static final String TERM = "AcademicTerm";
    public static final String CALENDAR_SEARCH_VIEW= "calendarSearchView";
    public static final String CALENDAR_SEARCH_TYPE = "calendarSearchType";

    //method names
    public static final String AC_EDIT_METHOD = KRADConstants.START_METHOD;
    public static final String AC_VIEW_METHOD = KRADConstants.START_METHOD;
    public static final String AC_COPY_METHOD = "toCopy";
    public static final String HC_EDIT_METHOD = KRADConstants.START_METHOD;
    public static final String HC_VIEW_METHOD = KRADConstants.START_METHOD;
    public static final String HC_COPY_METHOD = "copyForNew";

    public static final String CALENDAR_ID = "id";
    public static final String PAGE_ID = "pageId";
    public static final String READ_ONLY_VIEW = "readOnlyView";
    public static final String SELECT_TAB = "selectTab";
    public static final String ACAL_INFO_TAB = "info";
    public static final String ACAL_TERM_TAB = "term";

    public static final String DEFAULT_END_TIME = "11:59";
    public static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";
    public static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";

    public static class MessageKeys{
        public static final String INFO_ACADEMIC_CALENDAR_OFFICIAL = "info.enroll.academiccalendar.official";
        public static final String INFO_ACADEMIC_CALENDAR_SAVED = "info.enroll.academiccalendar.saved";
        public static final String INFO_ACADEMIC_CALENDAR_DELETED = "info.enroll.academiccalendar.deleted";
        public static final String INFO_HOLIDAY_CALENDAR_OFFICIAL = "info.enroll.holidaycalendar.official";
        public static final String INFO_HOLIDAY_CALENDAR_SAVED = "info.enroll.holidaycalendar.saved";
        public static final String INFO_SEARCH_DELETE_SUCCESS = "info.enroll.search.delete.success";
        public static final String ERROR_DELETING = "error.enroll.search.delete.failed";
        public static final String ERROR_DATE_END_REQUIRED = "error.enroll.date.end.required";
        public static final String ERROR_TIME_START_AMPM_REQUIRED = "error.enroll.time.start.ampm.required";
        public static final String ERROR_TIME_END_AMPM_REQUIRED = "error.enroll.time.end.ampm.required";
        public static final String ERROR_ACAL_SAVE_FAILED = "error.enroll.acal.save.failed";
        public static final String ERROR_ACAL_SAVE_TERM_SAVE_FAILED = "error.enroll.acal.term.save.failed";
        public static final String ERROR_DUPLICATE_HCAL = "error.enroll.acal.duplicate.hcal";
        public static final String ERROR_KEY_DATE_TYPE_REQUIRED = "error.enroll.keydate.type.empty";
        public static final String ERROR_DUPLICATE_NAME  = "error.enroll.calendar.duplicateName";
        public static final String ERROR_DATE_NOT_IN_ACAL_RANGE = "error.enroll.event.dateNotInAcal";
        public static final String ERROR_INVALID_DATE_RANGE = "error.enroll.daterange.invalid";
        public static final String ERROR_DUPLICATE_TERM_NAME = "error.enroll.term.duplicateName";
        public static final String ERROR_TERM_NOT_IN_ACAL_RANGE = "error.enroll.term.dateNotInAcal";
        public static final String ERROR_INVALID_DATERANGE_KEYDATE = "error.enroll.keydate.dateNotInTerm";
        public static final String ERROR_CALCULATING_INSTRUCTIONAL_DAYS = "error.enroll.acal.instructionaldays.calculation";
    }

    public static final String GROWL_TITLE="growl.title";
    public static final String GROWL_MESSAGE="growl.message";
    public static final String GROWL_MESSAGE_PARAMS="growl.message.params";

}
