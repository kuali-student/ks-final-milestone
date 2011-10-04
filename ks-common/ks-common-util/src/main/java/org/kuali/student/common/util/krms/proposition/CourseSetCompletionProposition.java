package org.kuali.student.common.util.krms.proposition;

import java.util.Collection;
import java.util.Collections;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.common.util.krms.RulesExecutionConstants;

public class CourseSetCompletionProposition extends CourseCompletionProposition {

    private String courseSetId;
    
    private Collection<String> courseIds;
    
    public CourseSetCompletionProposition(String courseSetId, Integer minToComplete) {
        super(minToComplete);
        this.courseSetId = courseSetId;
    }

    public CourseSetCompletionProposition(String courseSetId) {
        super();
        this.courseSetId = courseSetId;
    }

    @Override
    protected Collection<String> getTermCourseIds(ExecutionEnvironment environment) {
        
        if(courseIds == null) {
            
            Term term = new Term(RulesExecutionConstants.courseSetTermSpec, Collections.singletonMap(RulesExecutionConstants.COURSE_SET_ID_TERM_PROPERTY_NAME, courseSetId));

            courseIds = environment.resolveTerm(term, this);
        }
        
        return courseIds;
    }

}
