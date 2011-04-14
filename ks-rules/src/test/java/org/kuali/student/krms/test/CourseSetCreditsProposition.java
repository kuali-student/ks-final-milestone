package org.kuali.student.krms.test;

import java.util.HashMap;
import java.util.Map;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermSpecification;
import org.kuali.rice.krms.framework.engine.ComparisonOperator;
import org.kuali.rice.krms.framework.engine.Proposition;

public class CourseSetCreditsProposition implements Proposition {

    private ComparisonOperator operator;
    private final TermSpecification creditsAsset = new TermSpecification("completedCoursesInACourseListAsset", "Integer");
    private Integer compareCreditCount;
    
    private Term creditsTerm;

    public CourseSetCreditsProposition(String courseSetId, ComparisonOperator operator, Integer compareCreditCount) {
        this.operator = operator;
        this.compareCreditCount = compareCreditCount;
        
        
        Map<String, String> creditsParams = new HashMap<String, String>();
        creditsParams.put("courseSetId", courseSetId);
        creditsTerm = new Term(creditsAsset, creditsParams);
    }

    @Override
    public boolean evaluate(ExecutionEnvironment environment) {
        
        // add parameter to credits asset of course set before resolving asset
        // KRMS team will be defining this in the future
        // something like this:
        // asset.addProperty("courseSetId", courseSetId)
        
        Integer termValue;
        try {
            termValue = environment.resolveTerm(creditsTerm);
        } catch (TermResolutionException e) {
            // TODO Something better than this
            throw new RuntimeException(e);
        }
        
        boolean result = Boolean.valueOf(operator.compare(termValue, compareCreditCount));
        
        return result;
    }

}
