package org.kuali.student.krms.util;

import org.kuali.rice.krms.api.engine.Term;


public class KSKRMSExecutionConstants {

    public static final String STUDENT_DECEASED_TERM_NAME = "studentDeceased";
    public static final String CURRENT_DATE_TERM_NAME = "currentDate";
    public static final String STUDENT_ID_TERM_NAME = "studentId";
    public static final String COURSE_ID_TO_ENROLL_TERM_NAME = "courseIdToEnroll";

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
    
    //
	public static final String PERSON_ID_TERM_PROPERTY = "personIdProperty";
	public static final String COURSE_CODE_TERM_PROPERTY = "courseCodeProperty";
	public static final String TERM_ID_TERM_PROPERTY = "termIdProperty";
	public static final String CALC_TYPE_KEY_TERM_PROPERTY = "calculationTypeKeyProperty";
    public static final String ORG_ID_TERM_PROPERTY = "orgIdProperty";
    public static final String GRADE_TYPE_TERM_PROPERTY = "gradeTypeProperty";
    public static final String GRADE_TERM_PROPERTY = "gradeProperty";

    public static final String ORG_TYPE_KEY_TERM_PROPERTY = "orgTypeKey";
    public static final String ADMIN_ORG_NUMBER_TERM_NAME = "adminOrgNumber";

    public static final String COMPLETED_COURSE_CODE_TERM_NAME = "completedCourseCode";
    public static final String COMPLETED_COURSE_NUMBER_TERM_NAME ="completedCourseNumber";
    public static final String COMPLETED_COURSE_SET_TERM_NAME = "completedCourseSet";
    public static final String COMPLETED_COURSES_TERM_NAME = "completedCourses";
    public static final String COMPLETED_COURSE_TERM_NAME = "completedCourse";
    public static final String COMPLETED_COURSE_CREDITS_TERM_NAME = "completedCourseCredits";

    public static final String COMPLETED_EFFECTIVE_DATE_FROM_TERM_NAME = "completedEffectiveDateFrom";
    public static final String COMPLETED_EFFECTIVE_DATE_TO_TERM_NAME = "completedEffectiveDateTo";

    public static final String COMPLETED_LEARNING_OBJECTIVES_TERM_NAME = "completedLearningObjectives";
    public static final String COURSE_NUMBER_RANGE_TERM_NAME = "courseNumberRange";
    public static final String COURSE_SET_TERM_NAME = "courseSet";
    public static final String DEPT_NUMBER_TERM_NAME = "deptNumber";

    public static final String EFFECTIVE_DATE_FROM_TERM_NAME = "effectiveDateFrom";
    public static final String EFFECTIVE_DATE_TO_TERM_NAME = "effectiveDateTo";

    public static final String ENROLLED_COURSE_TERM_NAME = "enrolledCourse";
    public static final String ENROLLED_COURSE_BY_TERM_TERM_NAME = "enrolledCourseByTerm";
    public static final String ENROLLED_COURSE_CODE_TERM_NAME = "enrolledCourseCode";
    public static final String ENROLLED_COURSE_NUMBER_TERM_NAME = "enrolledCourseNumber";
    public static final String ENROLLED_COURSES_BY_TERM_TERM_NAME = "enrolledCoursesByTerm";
    public static final String ENROLLED_COURSE_SET_TERM_NAME = "enrolledCourseSet";
    public static final String ENROLLED_COURSES_TERM_NAME = "enrolledCourses";
    public static final String ENROLLED_EFFECTIVE_DATE_FROM_TERM_NAME = "enrolledEffectiveDateFrom";
    public static final String ENROLLED_EFFECTIVE_DATE_TO_TERM_NAME = "enrolledEffectiveDateTo";
    public static final String ENROLLED_LEARNING_OBJECTIVES_TERM_NAME = "enrolledLearningObjectives";

    public static final String FREE_TEXT_TERM_NAME = "freeText";
    public static final String GPA_TERM_NAME = "gpa";
    public static final String GRADE_TERM_NAME = "grade";
    public static final String GRADE_TYPE_TERM_NAME = "gradeType";

    public static final String LEARNING_OBJECTIVES_TERM_NAME = "learningObjectives";

    public static final String NUMBER_OF_COURSES_TERM_NAME = "numberOfCourses";
    public static final String NUMBER_OF_CREDITS_TERM_NAME = "numberOfCredits";

    public static final String SCORE_TERM_NAME = "score";
    public static final String SUBJECT_CODE_TERM_NAME = "subjectCode";
    public static final String TEST_TERM_NAME = "test";
	
	//		
	public static final String CONTEXT_INFO_TERM_NAME = "contextInfo";
}
