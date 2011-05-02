package org.kuali.student.krms.test;

import java.util.Collection;
import java.util.Collections;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.Term;

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
            
            Term term = new Term(Constants.courseSetTermSpec, Collections.singletonMap(Constants.COURSE_SET_ID_TERM_PROPERTY_NAME, courseSetId));
            
            try {
                courseIds = environment.resolveTerm(term);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
        return courseIds;
    }

    

}
