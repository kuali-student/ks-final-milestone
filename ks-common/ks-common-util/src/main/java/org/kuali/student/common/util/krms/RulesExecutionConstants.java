package org.kuali.student.common.util.krms;

import org.kuali.rice.krms.api.engine.Term;


public class RulesExecutionConstants {

    public static final String COMPLETED_CREDITS_FOR_COURSE_SET_TERM_NAME = "creditsForCourseSet";
    public static final String COURSE_ID_TO_ENROLL_TERM_NAME = "courseIdToEnroll";
    public static final String CONTEXT_INFO_TERM_NAME = "contextInfo";
    public static final String STUDENT_ID_TERM_NAME = "studentId";
    public static final String COMPLETED_COURSE_IDS_TERM_NAME = "studentCompletedCourseIds";
    public static final String GRADE_FOR_COURSE_TERM_NAME = "gradeForCourse";
    public static final String GPA_FOR_COURSE_TERM_NAME = "gpaForCourse";
    public static final String COURSE_SET_TERM_NAME = "courseSet";
    public static final String TEST_SET_SCORE_TERM_NAME = "testSetScore";
    public static final String ENROLLED_COURSE_IDS_TERM_NAME = "studentEnrolledCourseIds";
    
    public static final String STRING_TERM_TYPE = "String";
    public static final String FLOAT_TERM_TYPE = "Float";
    public static final String GRADE_VALUE_TERM_TYPE = "GradeValue";
    public static final String STRING_COLLECTION_TERM_TYPE = "Collection<String>";
    public static final String INTEGER_TERM_TYPE = "Integer";
    public static final String CONTEXT_INFO_TERM_TYPE = "ContextInfo";
    
    public static final String studentIdTermSpec = STUDENT_ID_TERM_NAME;

    public static final String courseIdToEnroll = COURSE_ID_TO_ENROLL_TERM_NAME;

    public static final String contextInfoTermSpec = CONTEXT_INFO_TERM_NAME;
    
    public static final String courseSetTermSpec = COURSE_SET_TERM_NAME;
    
    public static final String gpaForCourseTermSpec = GPA_FOR_COURSE_TERM_NAME;
    
    public static final String gradeForCourseTermSpec = GRADE_FOR_COURSE_TERM_NAME;
    
    public static final String completedCourseIdsTermSpec = COMPLETED_COURSE_IDS_TERM_NAME;

    public static final String enrolledCourseIdsTermSpec = ENROLLED_COURSE_IDS_TERM_NAME;
    
    public static final String testSetScoreTermSpec = TEST_SET_SCORE_TERM_NAME;
    
    public static final String completedCreditsForCourseSetTermSpec = COMPLETED_CREDITS_FOR_COURSE_SET_TERM_NAME;
    
    public static final Term completedCourseIdsTerm = new Term(completedCourseIdsTermSpec);

    public static final Term enrolledCourseIdsTerm = new Term(enrolledCourseIdsTermSpec);
    
    public static final String COURSE_SET_ID_TERM_PROPERTY_NAME = "courseSetId";
    public static final String COURSE_ID_TERM_PROPERTY_NAME = "courseId";
    public static final String TEST_SET_ID_TERM_PROPERTY = "testSetId";
    
    public static final String STATEMENT_EVENT_NAME = "statementEvent";
    
    
}
