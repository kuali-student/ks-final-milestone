package org.kuali.student.krms.test;

import java.util.Collections;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.framework.engine.ComparisonOperator;
import org.kuali.student.lum.lrc.dto.GradeInfo;

public class CourseSetGradeProposition extends ComparisonOverCourseSetProposition {

    private ComparisonOperator operator;
    
    private GradeInfo gradeInfo;
    
    public CourseSetGradeProposition(String courseSetId, GradeInfo gradeInfo, Integer numCourses, ComparisonOperator operator) {
        super(courseSetId, numCourses);
        
        this.gradeInfo = gradeInfo;
        this.operator = operator;
    }

    public CourseSetGradeProposition(String courseSetId, GradeInfo gradeInfo, ComparisonOperator operator) {
        super(courseSetId);
        
        this.gradeInfo = gradeInfo;
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
        
        return operator.compare(Float.parseFloat(discoveredGrade.getRank()), Float.parseFloat(gradeInfo.getRank()));
    }

}
