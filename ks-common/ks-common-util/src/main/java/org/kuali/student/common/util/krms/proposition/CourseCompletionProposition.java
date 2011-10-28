package org.kuali.student.common.util.krms.proposition;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;


public abstract class CourseCompletionProposition extends AbstractLeafProposition implements Proposition {
    
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
            	
        Collection<String> enrolledCourses = environment.resolveTerm(RulesExecutionConstants.completedCourseIdsTerm, this);

        Collection<String> termCourses = getTermCourseIds(environment);

        PropositionResult result = null;

        if(checkForAllCompleted) {
            result = new PropositionResult(enrolledCourses.containsAll(termCourses));
        }
       
        else {
            result = new PropositionResult(CollectionUtils.intersection(enrolledCourses, termCourses).size() >= minToComplete);
        }

        environment.getEngineResults().addResult(new BasicResult(ResultEvent.PropositionEvaluated, this, environment, result.getResult()));

        return result;
    
    }
   
    protected abstract Collection<String> getTermCourseIds(ExecutionEnvironment environment);
    
}
