package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;

public class MinProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private Collection<T> fact;

    public MinProposition() {
    }
    
    public MinProposition(String id, String propositionName, ComparisonOperator operator, T expectedValue, Collection<T> fact) {
        super(id, propositionName, PropositionType.MIN, operator, expectedValue);
        this.fact = fact;
	}

    @Override
    public Boolean apply() {
    	T min = Collections.min(this.fact);

        result = checkTruthValue(min, super.expectedValue);

        cacheReport("Minimum not met: %s", min.toString(), super.expectedValue);

        resultValues = new ArrayList<T>();
        resultValues.add(min);

        return result;
    }

    @Override
    protected void cacheReport(String format, Object... args) {
        if (result) {
            report.setSuccessMessage("Minimum constraint fulfilled");
            return;
        }

        String max = (String) args[0];

        // TODO: Use the operator to compute exact message
        String advice = String.format(format, max);
        report.setFailureMessage(advice);
    }
}
