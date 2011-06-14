package org.kuali.student.krms.test;

import java.util.Collections;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.framework.engine.ComparisonOperator;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.PropositionResult;


public class TestScoreCompareProposition extends AbstractProposition implements Proposition {

    private ComparisonOperator operator;
    private Float compareScore;
    private Term testScoreTerm;

    public TestScoreCompareProposition(ComparisonOperator operator, String testSetId, Float compareScore) {
        this.operator = operator;
        
        testScoreTerm = new Term(Constants.testSetScoreTermSpec, Collections.singletonMap(Constants.TEST_SET_ID_TERM_PROPERTY, testSetId));
        
        this.compareScore = compareScore;
    }

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        
        Float testScore = null;
        
        try {
            testScore = environment.resolveTerm(testScoreTerm, this);
        } catch (TermResolutionException e) {
            throw new RuntimeException(e);
        }
        
        
        return new PropositionResult(operator.compare(testScore, compareScore), "");
    }

}
