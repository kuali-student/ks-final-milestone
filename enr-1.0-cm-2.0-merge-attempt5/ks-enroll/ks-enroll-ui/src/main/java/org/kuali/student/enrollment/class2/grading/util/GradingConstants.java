package org.kuali.student.enrollment.class2.grading.util;

/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//Core slice classes, just still around for reference.. needs cleanup
@Deprecated
public class GradingConstants {

    public static final String CLOSE_METHOD = "close";
    public static final String BACK_TO_GRADING_METHOD = "backToGrading";
    public static final String BACK_TO_TERM = "backToTerm";
    public static final String SAVE_METHOD = "save";
    public static final String SUBMIT_METHOD = "submit";
    public static final String LOAD_COURSES_METHOD = "loadCourses";
    public static final String LOAD_GRADES_ROSTER_METHOD = "loadGradeRoster";
    public static final String UNASSIGN_GRADE_METHOD = "unassignGrade";
    public static final String VIEW_GRADES = "viewGrades";
    public static final String IDENTITY_SERVICE_URL = "http://rice.kuali.org/kim/v2_0";
    public static final String IDENTITY_SERVICE_NAME = "identityService";

    public static final String SELECT_COURSE_OFFERING_PAGE = "courseOfferingPage";
    public static final String GRADE_ROSTER_PAGE = "gradeRosterPage";
    public static final String STUDENT_TERM_RECORD_PAGE = "selectTermPage";
    public static final String STUDENT_CREDIT_DETAILS_PAGE = "creditDetailsPage";

    public static final String CURRENT_TERM = "201108";
    public static final String INFO_GRADE_ROSTER_SAVED = "info.enroll.grading.saved";
    public static final String INFO_GRADE_ROSTER_SUBMITTED = "info.enroll.grading.submitted";
    public static final String INFO_GRADE_STUDENTS_NOT_FOUND = "info.enroll.grading.studentsNotFound";
    public static final String WARNING_GRADING_OPTIONS_NOT_FOUND = "info.enroll.grading.gradingOptionsnotFound";
    public static final String INFO_COURSE_NOT_FOUND_TO_GRADE = "info.enroll.grading.courseOfferingNotFound";

    public static final String GRADING_SERVICE_URL = "http://student.kuali.org/wsdl/grading";
    public static final String GRADING_SERVICE_NAME = "GradingService";

}
