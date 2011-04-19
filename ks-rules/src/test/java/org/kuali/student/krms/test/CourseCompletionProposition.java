package org.kuali.student.krms.test;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermSpecification;
import org.kuali.rice.krms.framework.engine.Proposition;

public abstract class CourseCompletionProposition implements Proposition {
    
    private final Term studentCompletedCoursesAsset = new Term(new TermSpecification("studentCompletedCourseIds", "Collection<String>"));
    
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
    public boolean evaluate(ExecutionEnvironment environment) {
        
        Collection<String> enrolledCourses = null;
        
        try {
            enrolledCourses = environment.resolveTerm(studentCompletedCoursesAsset);
        } catch (TermResolutionException e) {
            throw new RuntimeException(e);
        }
        
        Collection<String> termCourses = getTermCourseIds();
        
        if(checkForAllCompleted) {
            return enrolledCourses.containsAll(termCourses);
        }
        else {
            return CollectionUtils.intersection(enrolledCourses, termCourses).size() >= minToComplete;
        }
    }
    
    protected abstract Collection<String> getTermCourseIds();
    
}
