package org.kuali.student.krms.test;

import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.api.engine.TermSpecification;


public class Constants {

    public static final String COMPLETED_CREDITS_FOR_COURSE_SET_TERM_NAME = "creditsForCourseSet";
    public static final String STUDENT_ID_TERM_NAME = "studentId";
    public static final String COMPLETED_COURSE_IDS_TERM_NAME = "studentCompletedCourseIds";
    public static final String GRADE_FOR_COURSE_TERM_NAME = "gradeForCourse";
    public static final String GPA_FOR_COURSE_TERM_NAME = "gpaForCourse";
    public static final String COURSE_SET_TERM_NAME = "courseSet";
    public static final String TEST_SET_SCORE_TERM_NAME = "testSetScore";
    
    public static final String STRING_TERM_TYPE = "String";
    public static final String FLOAT_TERM_TYPE = "Float";
    public static final String GRADE_VALUE_TERM_TYPE = "GradeValue";
    public static final String STRING_COLLECTION_TERM_TYPE = "Collection<String>";
    public static final String INTEGER_TERM_TYPE = "Integer";
    
    public static final TermSpecification studentIdTermSpec = new TermSpecification(STUDENT_ID_TERM_NAME, STRING_TERM_TYPE);
    
    public static final TermSpecification courseSetTermSpec = new TermSpecification(COURSE_SET_TERM_NAME, STRING_COLLECTION_TERM_TYPE);
    
    public static final TermSpecification gpaForCourseTermSpec = new TermSpecification(GPA_FOR_COURSE_TERM_NAME, FLOAT_TERM_TYPE);
    
    public static final TermSpecification gradeForCourseTermSpec = new TermSpecification(GRADE_FOR_COURSE_TERM_NAME, GRADE_VALUE_TERM_TYPE);
    
    public static final TermSpecification completedCourseIdsTermSpec = new TermSpecification(COMPLETED_COURSE_IDS_TERM_NAME, STRING_COLLECTION_TERM_TYPE);
    
    public static final TermSpecification testSetScoreTermSpec = new TermSpecification(TEST_SET_SCORE_TERM_NAME, FLOAT_TERM_TYPE);
    
    public static final TermSpecification completedCreditsForCourseSetTermSpec = new TermSpecification(COMPLETED_CREDITS_FOR_COURSE_SET_TERM_NAME, INTEGER_TERM_TYPE);
    
    public static final Term completedCourseIdsTerm = new Term(completedCourseIdsTermSpec);
    
    public static final String COURSE_SET_ID_TERM_PROPERTY_NAME = "courseSetId";
    public static final String COURSE_ID_TERM_PROPERTY_NAME = "courseId";
    public static final String TEST_SET_ID_TERM_PROPERTY = "testSetId";
    
    public static final String PASS_GRADE_ID = "1";
    public static final String FAIL_GRADE_ID = "2";
    public static final String STATEMENT_EVENT_NAME = "statementEvent";
    
    
}
