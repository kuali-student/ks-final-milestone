package org.kuali.student.krms.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.engine.TermSpecification;

public class CourseSetResolver implements TermResolver<Collection<String>> {

    @Override
    public Set<TermSpecification> getPrerequisites() {
        return Collections.emptySet();
    }

    @Override
    public TermSpecification getOutput() {
        return Constants.courseSetTermSpec;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(Constants.COURSE_SET_ID_TERM_PROPERTY_NAME);
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public Collection<String> resolve(Map<TermSpecification, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        String courseSetId = parameters.get(Constants.COURSE_SET_ID_TERM_PROPERTY_NAME);
        
        if(courseSetId == null) {
            throw new TermResolutionException("No parameter found with name: " + Constants.COURSE_SET_ID_TERM_PROPERTY_NAME, this, parameters);
        }
        
        if(courseSetId.equals("1")) {
            return Arrays.asList("1", "2");
        }
        else if(courseSetId.equals("2")) {
            return Arrays.asList("2", "3");
        }
        else {
            throw new TermResolutionException("Could not find course ids for course set: " + courseSetId, this, parameters);
        }
        
    }

}
