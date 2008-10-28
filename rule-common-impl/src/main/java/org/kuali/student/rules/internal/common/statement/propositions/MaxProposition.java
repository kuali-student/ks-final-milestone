package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.Collection;
import java.util.Collections;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;

public class MaxProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private Collection<T> fact;

    public MaxProposition() {
    }

    public MaxProposition(String id, String propositionName, ComparisonOperator operator, T expectedValue, Collection<T> fact) {
        super(id, propositionName, operator, expectedValue);
        this.fact = fact;
	}

    @Override
    public Boolean apply() {
    	T max = Collections.max(this.fact);

        result = checkTruthValue(max, super.expectedValue);

        cacheReport("Maximum not met: %s", max.toString(), super.expectedValue);

        return result;
    }

    @Override
    protected void cacheReport(String format, Object... args) {
        if (result) {
            report.setSuccessMessage("Maximum constraint fulfilled");
            return;
        }

        String max = (String) args[0];

        // TODO: Use the operator to compute exact message
        String advice = String.format(format, max);
        report.setFailureMessage(advice);
    }

}
