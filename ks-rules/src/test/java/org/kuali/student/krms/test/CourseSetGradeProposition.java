package org.kuali.student.krms.test;

import java.util.Collections;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermSpecification;
import org.kuali.rice.krms.framework.engine.ComparisonOperator;
import org.kuali.student.lum.lrc.dto.GradeInfo;
import org.kuali.student.lum.lrc.dto.GradeTypeInfo;
import org.kuali.student.lum.lrc.service.LrcService;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseSetGradeProposition extends ComparisonOverCourseSetProposition {

    private String gradeType;
    private String gradeValue;
    private ComparisonOperator operator;
    
    private GradeTypeInfo gradeTypeInfo;
    private GradeInfo gradeInfo;
    
    private LrcService lrcService;

    public CourseSetGradeProposition(String courseSetId, String gradeType, String gradeValue, Integer numCourses, ComparisonOperator operator) {
        super(courseSetId, numCourses);
        
        this.gradeType = gradeType;
        this.gradeValue = gradeValue;
        this.operator = operator;
    }

    public CourseSetGradeProposition(String courseSetId, String gradeType, String gradeValue, ComparisonOperator operator) {
        super(courseSetId);
        
        this.gradeType = gradeType;
        this.gradeValue = gradeValue;
        this.operator = operator;
    }

    @Override
    protected boolean performSingleCourseComparison(String courseId, ExecutionEnvironment environment) {
        GradeInfo discoveredGrade = null;
        
        Term term = new Term(Constants.gradeForCourseTermSpec, Collections.singletonMap(Constants.COURSE_ID_TERM_PROPERTY_NAME, courseId));
        
        try {
            discoveredGrade = environment.resolveTerm(term);
        } catch (TermResolutionException e) {
            throw new RuntimeException(e);
        }
        
        // TODO also needs some way of comparing grades
        
        return false;
    }

    @Override
    protected void initializeComparisonTerm(ExecutionEnvironment environment) {
        try {
            gradeTypeInfo = lrcService.getGradeType(gradeType);
            gradeInfo = lrcService.getGrade(gradeValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
