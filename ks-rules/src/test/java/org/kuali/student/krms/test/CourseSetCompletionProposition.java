package org.kuali.student.krms.test;

import java.util.Collection;

import org.kuali.student.lum.lu.service.LuService;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseSetCompletionProposition extends CourseCompletionProposition {

    @Autowired
    private LuService luService;
    
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
    protected Collection<String> getTermCourseIds() {
        
        if(courseIds == null) {
            try {
                courseIds = luService.getAllCluIdsInCluSet(courseSetId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
        return courseIds;
    }

    

}
