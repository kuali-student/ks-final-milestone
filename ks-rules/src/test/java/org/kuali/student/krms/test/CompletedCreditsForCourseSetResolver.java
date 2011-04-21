package org.kuali.student.krms.test;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.engine.TermSpecification;

public class CompletedCreditsForCourseSetResolver implements TermResolver<Integer> {

    @Override
    public Set<TermSpecification> getPrerequisites() {
        return Collections.singleton(Constants.studentIdTermSpec);
    }

    @Override
    public TermSpecification getOutput() {
        return Constants.completedCreditsForCourseSetTermSpec;
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
    public Integer resolve(Map<TermSpecification, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        String studentId = resolvedPrereqs.get(Constants.studentIdTermSpec).toString();
        String courseSetId = parameters.get(Constants.COURSE_SET_ID_TERM_PROPERTY_NAME);
        
        
        // assumptions: courses 1 and 2 are worth 4 credits, course 3 is worth 3 credits
        //              student 1 has passed course 1, student 2: course 2, student 3: courses 1, 2, and 3
        //              course set 1 has courses 1 and 2, course set 3 has courses 2 and 3
        if(studentId.equals("1")) {
            if(courseSetId.equals("1")) {
                return 4;
            }
            else if(courseSetId.equals("2")) {
                return 0;
            }
        }
        
        if(studentId.equals("2")) {
            if(courseSetId.equals("1")) {
                return 4;
            }
            else if(courseSetId.equals("2")) {
                return 4;
            }
        }
        
        if(studentId.equals("3")) {
            if(courseSetId.equals("1")) {
                return 8;
            }
            else if(courseSetId.equals("2")) {
                return 7;
            }
        }
        
        return null;
    }

}
