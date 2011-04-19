package org.kuali.student.krms.test;

import java.util.Collection;
import java.util.Collections;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.framework.engine.Proposition;

public abstract class ComparisonOverCourseSetProposition implements Proposition {

    private Term courseSetTerm;
    
    private final boolean checkAllCourses;
    
    private Integer expectedCompareCount;
    
    private Collection<String> courseIds;
    
    public ComparisonOverCourseSetProposition(String courseSetId) {
        this.courseSetTerm = new Term(Constants.courseSetTermSpec, Collections.singletonMap(Constants.COURSE_SET_ID_TERM_PROPERTY_NAME, courseSetId));
        this.checkAllCourses = true;
    }
    
    public ComparisonOverCourseSetProposition(String courseSetId, Integer numCourses) {
        this.courseSetTerm = new Term(Constants.courseSetTermSpec, Collections.singletonMap(Constants.COURSE_SET_ID_TERM_PROPERTY_NAME, courseSetId));
        this.checkAllCourses = false;
        this.expectedCompareCount = numCourses;
    }
    
    @Override
    public boolean evaluate(ExecutionEnvironment environment) {
        
        if(courseIds == null) {
            try {
                environment.resolveTerm(courseSetTerm);
            } catch (TermResolutionException e) {
                throw new RuntimeException(e);
            }
        }
        
        initializeComparisonTerm(environment);
        
        boolean singleCourseComparison;
        int trueComparisonCount = 0;
        
        for(String courseId : courseIds) {
            singleCourseComparison = performSingleCourseComparison(courseId, environment);
            
            if(singleCourseComparison) {
                trueComparisonCount++;
            }
            
            // if all courses must meet the comparison, and any one of them does not, the evaluation is false
            if(checkAllCourses && !singleCourseComparison) {
                return false;
            }
            
        }
        
        // if no single comparison failed and we are checking all courses, return true
        if(checkAllCourses) {
            return true;
        }
        // otherwise compare the number of true comparisons to the expected amount
        else {
            return trueComparisonCount >= expectedCompareCount;
        }
        
    }

    protected abstract boolean performSingleCourseComparison(String courseId, ExecutionEnvironment environment);

    protected void initializeComparisonTerm(ExecutionEnvironment environment) {
    }

}
