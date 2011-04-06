package org.kuali.student.krms.test;

import java.util.Collection;

import org.kuali.rice.krms.api.ExecutionEnvironment;
import org.kuali.rice.krms.api.Proposition;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.lum.lu.service.LuService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ComparisonOverCourseSetProposition implements Proposition {

    private String courseSetId;
    
    private final boolean checkAllCourses;
    
    private Integer expectedCompareCount;
    
    private Collection<String> courseIds;
    
    @Autowired
    protected LuService luService;
    
    public ComparisonOverCourseSetProposition(String courseSetId) {
        this.courseSetId = courseSetId;
        this.checkAllCourses = true;
    }
    
    public ComparisonOverCourseSetProposition(String courseSetId, Integer numCourses) {
        this.courseSetId = courseSetId;
        this.checkAllCourses = false;
        this.expectedCompareCount = numCourses;
    }
    
    @Override
    public boolean evaluate(ExecutionEnvironment environment) {
        
        if(courseIds == null) {
            try {
                courseIds = luService.getAllCluIdsInCluSet(courseSetId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
        initializeComparisonTerm(environment);
        
        boolean singleCourseComparison;
        int trueComparisonCount = 0;
        
        for(String courseId : courseIds) {
            singleCourseComparison = performSingleCourseComparison(courseId);
            
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

    protected abstract boolean performSingleCourseComparison(String courseId);

    protected void initializeComparisonTerm(ExecutionEnvironment environment) {
    }

}
