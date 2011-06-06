package org.kuali.student.krms.test;

import java.util.Collections;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.framework.engine.ComparisonOperator;
import org.kuali.rice.krms.framework.engine.Proposition;

public class CourseSetCreditsProposition extends AbstractProposition implements Proposition {

    private ComparisonOperator operator;
    private Integer compareCreditCount;
    
    private Term creditsTerm;

    public CourseSetCreditsProposition(String courseSetId, ComparisonOperator operator, Integer compareCreditCount) {
        this.operator = operator;
        this.compareCreditCount = compareCreditCount;
        
        creditsTerm = new Term(Constants.completedCreditsForCourseSetTermSpec, Collections.singletonMap(Constants.COURSE_SET_ID_TERM_PROPERTY_NAME, courseSetId));
    }

    @Override
    public boolean evaluate(ExecutionEnvironment environment) {
        
        Integer termValue;
        try {
            termValue = environment.resolveTerm(creditsTerm);
        } catch (TermResolutionException e) {
            throw new RuntimeException(e);
        }
        
        boolean result = Boolean.valueOf(operator.compare(termValue, compareCreditCount));
        
        return result;
    }

}
