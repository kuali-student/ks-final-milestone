package org.kuali.student.krms.test;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.PropositionResult;


public abstract class CourseCompletionProposition extends AbstractProposition implements Proposition {
    
    protected final boolean checkForAllCompleted;
    
    protected Integer minToComplete;
            
    public CourseCompletionProposition(Integer minToComplete) {    	
        this.checkForAllCompleted = false;
        this.minToComplete = minToComplete;
    }
    
    public CourseCompletionProposition() {
        checkForAllCompleted = true;
    }

    
    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
            	
        Collection<String> enrolledCourses = null;
        
        try {
        	        	
            enrolledCourses = environment.resolveTerm(Constants.completedCourseIdsTerm, this);
        } catch (TermResolutionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        Collection<String> termCourses = getTermCourseIds(environment);
               
        if(checkForAllCompleted) {
            return new PropositionResult(enrolledCourses.containsAll(termCourses), "");
        }
       
        else {
            return new PropositionResult(CollectionUtils.intersection(enrolledCourses, termCourses).size() >= minToComplete, "");
        }
    
    }
   
    protected abstract Collection<String> getTermCourseIds(ExecutionEnvironment environment);
    
}
