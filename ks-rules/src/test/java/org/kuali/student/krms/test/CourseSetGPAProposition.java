package org.kuali.student.krms.test;

import java.util.Collections;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.framework.engine.ComparisonOperator;

public class CourseSetGPAProposition extends ComparisonOverCourseSetProposition {

    private Float gpa;
    private ComparisonOperator operator;

    public CourseSetGPAProposition(String courseSetId, Float gpa, ComparisonOperator operator) {
        super(courseSetId);
        this.gpa = gpa;
        this.operator = operator;
    }

    @Override
    protected boolean performSingleCourseComparison(String courseId, ExecutionEnvironment environment) {
        
        Term term = new Term(Constants.gpaForCourseTermSpec, Collections.singletonMap(Constants.COURSE_ID_TERM_PROPERTY_NAME, courseId));
        
        Float discoveredGpa = null;
        
        try {
            discoveredGpa = environment.resolveTerm(term);
        } catch (TermResolutionException e) {
            throw new RuntimeException(e);
        }
        
        if(discoveredGpa == null) {
            return false;
        }
        
        return operator.compare(discoveredGpa, gpa);
    }

}
