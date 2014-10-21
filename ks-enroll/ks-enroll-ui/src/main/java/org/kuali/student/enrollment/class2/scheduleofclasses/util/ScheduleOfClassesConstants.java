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
 * Created by vgadiyak on 9/12/12
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.util;

/**
 * This class defines constants used in the Schedule of Classes ui
 *
 * @author Kuali Student Team
 */
public class ScheduleOfClassesConstants {

    // Errors
    public static final String SOC_MSG_ERROR_NO_PUBLISHED_TERM = "error.enroll.scheduleofclasses.termNoPublished";
    public static final String SOC_MSG_ERROR_TERM_IS_EMPTY = "error.enroll.scheduleofclasses.termIsEmpty";
    public static final String SOC_MSG_ERROR_COURSE_IS_EMPTY = "error.enroll.scheduleofclasses.courseIsEmpty";
    public static final String SOC_MSG_ERROR_INSTRUCTOR_IS_EMPTY = "error.enroll.scheduleofclasses.instructorIsEmpty";
    public static final String SOC_MSG_ERROR_DEPARTMENT_IS_EMPTY = "error.enroll.scheduleofclasses.departmentIsEmpty";
    public static final String SOC_MSG_ERROR_TITLEDESC_IS_EMPTY = "error.enroll.scheduleofclasses.titleDescIsEmpty";
    public static final String SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND = "error.enroll.scheduleofclasses.noCourseOfferingIsFound";
    public static final String SOC_MSG_ERROR_MULTIPLE_DEPARTMENT_IS_FOUND = "error.enroll.scheduleofclasses.multipleDepartmentIsFound";
    public static final String SOC_MSG_ERROR_MULTIPLE_INSTRUCTOR_IS_FOUND = "error.enroll.scheduleofclasses.multipleInstructorIsFound";

    // Page IDs
    public static final String SOC_RESULT_PAGE = "scheduleOfClassesResultsPage";

    // Help statements
    public static final String SOC_RESULT_PAGE_HELP_HONORS_COURSE = "Honors course";
    public static final String SOC_RESULT_PAGE_HELP_STUREG_PASSFAIL = "May be taken as Pass/Fail grading option";
    public static final String SOC_RESULT_PAGE_HELP_STUREG_AUDIT = "May be taken as Audit grading option";
    public static final String SOC_RESULT_PAGE_HELP_GRADING_SATISFACTORY = "Assessment by Satisfactory/Fail";
    public static final String SOC_RESULT_PAGE_HELP_GRADING_PERCENT = "Assessment by Percentage";
    public static final String SOC_RESULT_PAGE_HELP_HONORS_ACTIVITY = "Honors activity";

    // Images locations
    public static final String SOC_RESULT_PAGE_HONORS_COURSE_IMG = "../themes/ksboot/images/h.png";
    public static final String SOC_RESULT_PAGE_STUREG_PASSFAIL_IMG = "../themes/ksboot/images/p.png";
    public static final String SOC_RESULT_PAGE_STUREG_AUDIT_IMG = "../themes/ksboot/images/a.png";
    public static final String SOC_RESULT_PAGE_GRADING_SATISFACTORY_IMG = "../themes/ksboot/images/s.png";
    public static final String SOC_RESULT_PAGE_GRADING_PERCENT_IMG = "../themes/ksboot/images/percent.png";
    public static final String SOC_RESULT_PAGE_SUBTERM_IMG = "../themes/ksboot/images/subterm_icon.png";

    public static class ConfigProperties{
        public static final String AO_DISPLAY_FORMAT = "kuali.ks.enrollment.schoc.options.default_ao_display_format";
        public static final String ALLOW_SELECTABLE_AO_RENDERING = "kuali.ks.enrollment.schoc.options.allow_selectable_ao_rendering";
    }

    private ScheduleOfClassesConstants() {
        /* Sonar-fix: "Utility classes should not have a public or default constructor"
         * http://sonar.kuali.org/dashboard/index/86872
         */
    }

}
