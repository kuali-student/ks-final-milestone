package org.kuali.student.krms.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.engine.TermSpecification;

public class CompletedCoursesResolver implements TermResolver<Collection<String>> {

    @Override
    public Set<TermSpecification> getPrerequisites() {
        return Collections.singleton(Constants.studentIdTermSpec);
    }

    @Override
    public TermSpecification getOutput() {
        return Constants.completedCourseIdsTermSpec;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public Collection<String> resolve(Map<TermSpecification, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        String studentId = resolvedPrereqs.get(Constants.studentIdTermSpec).toString();
                
        if(studentId.equals("1")) {
            return Arrays.asList("1");
        }
        else if(studentId.equals("2")) {
            return Arrays.asList("2", "3");
        }
        else if(studentId.equals("3")) {
            return Arrays.asList("1", "2", "3");
        }
        else {
            throw new TermResolutionException("Could not find completed course ids for student id: " + studentId, this, parameters);
        }
        
    }

}
