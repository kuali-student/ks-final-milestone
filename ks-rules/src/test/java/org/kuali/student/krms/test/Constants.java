package org.kuali.student.krms.test;

import org.kuali.rice.krms.api.engine.TermSpecification;


public class Constants {

    public static final String GRADE_FOR_COURSE_TERM_NAME = "gradeForCourse";
    public static final String GPA_FOR_COURSE_TERM_NAME = "gpaForCourse";
    public static final String COURSE_SET_TERM_NAME = "courseSet";
    
    public static final String STRING_TERM_TYPE = "String";
    public static final String FLOAT_TERM_TYPE = "Float";
    public static final String GRADE_VALUE_TERM_TYPE = "GradeValue";
    
    public static final TermSpecification courseSetTermSpec = new TermSpecification(COURSE_SET_TERM_NAME, STRING_TERM_TYPE);
    
    public static final TermSpecification gpaForCourseTermSpec = new TermSpecification(GPA_FOR_COURSE_TERM_NAME, FLOAT_TERM_TYPE);
    
    public static final TermSpecification gradeForCourseTermSpec = new TermSpecification(GRADE_FOR_COURSE_TERM_NAME, GRADE_VALUE_TERM_TYPE);
    
    public static final String COURSE_SET_ID_TERM_PROPERTY_NAME = "courseSetId";
    public static final String COURSE_ID_TERM_PROPERTY_NAME = "courseId";
}
