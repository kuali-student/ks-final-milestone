package org.kuali.student.krms.util;

import org.kuali.rice.krms.api.engine.Term;


public class KSKRMSExecutionConstants {

    public static final String STUDENT_DECEASED_TERM_NAME = "studentDeceased";
    public static final String CURRENT_DATE_TERM_NAME = "currentDate";
    public static final String STUDENT_ID_TERM_NAME = "studentId";
    public static final String COURSE_ID_TO_ENROLL_TERM_NAME = "courseIdToEnroll";
    public static final String CONTEXT_INFO_TERM_NAME = "contextInfo";
    public static final String COURSE_SET_TERM_NAME = "courseSet";
    public static final String GPA_FOR_COURSE_TERM_NAME = "gpaForCourse";
    public static final String GRADES_FOR_COURSE_SET_TERM_NAME = "gradesForCourseSet";
    public static final String STUDENT_COMPLETED_COURSE_IDS_TERM_NAME = "studentCompletedCourseIds";
    public static final String STUDENT_ENROLLED_COURSE_IDS_TERM_NAME = "studentEnrolledCourseIds";
    public static final String TEST_SET_SCORE_TERM_NAME = "testSetScore";
    public static final String COMPLETED_CREDITS_FOR_COURSE_SET_TERM_NAME = "creditsForCourseSet";
    public static final String REGISTRATION_TERM_TERM_NAME = "registrationTerm";
    public static final String SUMMER_ONLY_STUDENT_TERM_NAME = "summerOnlyStudent";
    public static final String MILESTONES_BY_TYPE_TERM_NAME = "milestonesByType";
    public static final String MILESTONE_TERM_NAME = "milestone";
    public static final String STUDENT_REGISTRATION_HOLDS_TERM_NAME = "studentRegistrationHolds";

    public static final Term completedCourseIdsTerm = new Term(STUDENT_COMPLETED_COURSE_IDS_TERM_NAME);
    public static final Term enrolledCourseIdsTerm = new Term(STUDENT_ENROLLED_COURSE_IDS_TERM_NAME);
    public static final Term studentDeceasedTerm = new Term(STUDENT_DECEASED_TERM_NAME);

    public static final String COURSE_SET_ID_TERM_PROPERTY = "courseSetIdProperty";
    public static final String COURSE_ID_TERM_PROPERTY = "courseIdProperty";
    public static final String TEST_SET_ID_TERM_PROPERTY = "testSetIdProperty";
    public static final String MILESTONE_TYPE_TERM_PROPERTY = "milestoneTypeProperty";
    public static final String MILESTONE_ATP_KEY_TERM_PROPERTY = "milestoneAtpKeyProperty";
    public static final String MILESTONE_ID_TERM_PROPERTY = "milestoneIdProperty";
    public static final String ISSUE_KEY_TERM_PROPERTY = "issueKeyProperty";

    public static final String STATEMENT_EVENT_NAME = "statementEvent";
    public static final String PROCESS_EVENT_NAME = "processEvent";

    public static final String REGISTRATION_HOLD_WARNINGS_ATTRIBUTE = "registrationHoldWarnings";

    public static final String DOCTYPE_CONTEXT_QUALIFIER = "docTypeName";
    public static final String STUDENT_ELIGIBILITY_DOCTYPE = "Student.Eligibility";
    public static final String SUBPROCESS_EVALUATION_EXCEPTION = "subprocessEvaluationException";
    public static final String SUBPROCESS_EVALUATION_RESULTS = "subprocessEvaluationResults";
}
