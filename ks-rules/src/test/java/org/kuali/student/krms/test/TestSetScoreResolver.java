package org.kuali.student.krms.test;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.engine.TermSpecification;

public class TestSetScoreResolver implements TermResolver<Float> {

    @Override
    public Set<TermSpecification> getPrerequisites() {
        return Collections.singleton(Constants.studentIdTermSpec);
    }

    @Override
    public TermSpecification getOutput() {
        return Constants.testSetScoreTermSpec;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(Constants.TEST_SET_ID_TERM_PROPERTY);
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public Float resolve(Map<TermSpecification, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        
        String testSetId = parameters.get(Constants.TEST_SET_ID_TERM_PROPERTY);
        
        if(testSetId.equals("1")) {
            return 88.8f;
        }
        
        throw new TermResolutionException("Could not find test score for test set id: " + testSetId);
    }

}
