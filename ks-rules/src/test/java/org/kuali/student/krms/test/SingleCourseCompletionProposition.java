package org.kuali.student.krms.test;

import java.util.Collection;
import java.util.Collections;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;

public class SingleCourseCompletionProposition extends CourseCompletionProposition {

    private Collection<String> courseIdCollection;
    
    public SingleCourseCompletionProposition(String courseId) {
        this.courseIdCollection = Collections.singletonList(courseId);
    }

    @Override
    protected Collection<String> getTermCourseIds(ExecutionEnvironment environment) {
        return courseIdCollection;
    }

}
