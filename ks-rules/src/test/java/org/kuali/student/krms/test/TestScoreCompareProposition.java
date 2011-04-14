package org.kuali.student.krms.test;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.framework.engine.ComparisonOperator;
import org.kuali.rice.krms.framework.engine.Proposition;

public class TestScoreCompareProposition implements Proposition {

    private ComparisonOperator operator;
    private String testSetId;
    private String compareScore;

    public TestScoreCompareProposition(ComparisonOperator operator, String testSetId, String compareScore) {
        this.operator = operator;
        this.testSetId = testSetId;
        this.compareScore = compareScore;
    }

    @Override
    public boolean evaluate(ExecutionEnvironment environment) {
        // TODO 
        return false;
    }

}
