package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;

public class MaxProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private T max;
	private Collection<T> fact;

    public MaxProposition() {
    }

    public MaxProposition(String id, String propositionName, ComparisonOperator operator, T expectedValue, Collection<T> fact) {
        super(id, propositionName, PropositionType.MAX, operator, expectedValue);
        this.fact = fact;
	}

    @Override
    public Boolean apply() {
    	max = Collections.max(this.fact);

        result = checkTruthValue(max, super.expectedValue);

        resultValues = new ArrayList<T>();
        resultValues.add(max);

        return result;
    }

    @Override
    public PropositionReport buildReport() {
        if (result) {
            report.setSuccessMessage("Maximum constraint fulfilled");
            return report;
        }

        // TODO: Use the operator to compute exact message
        String advice = String.format("Maximum not met: %s", this.max);
        report.setFailureMessage(advice);
        return report;
    }
}
