package org.kuali.student.krms.test;

import java.util.Collection;

import org.kuali.rice.krms.api.Asset;
import org.kuali.rice.krms.api.AssetResolutionException;
import org.kuali.rice.krms.api.ExecutionEnvironment;
import org.kuali.rice.krms.api.Proposition;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.lum.lu.entity.CluSet;
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
