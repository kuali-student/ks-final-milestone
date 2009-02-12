package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;

public class MinProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private T min;
	private Collection<T> fact;

    public MinProposition() {
    }
    
    public MinProposition(String id, String propositionName, ComparisonOperator operator, T expectedValue, Collection<T> fact) {
        super(id, propositionName, PropositionType.MIN, operator, expectedValue);
        this.fact = fact;
	}

    @Override
    public Boolean apply() {
    	min = Collections.min(this.fact);

        result = checkTruthValue(min, super.expectedValue);

        resultValues = new ArrayList<T>();
        resultValues.add(min);

        return result;
    }

    @Override
    public PropositionReport buildReport() {
        if (result) {
            report.setSuccessMessage("Minimum constraint fulfilled");
            return report;
        }

        // TODO: Use the operator to compute exact message
        String advice = String.format("Minimum not met: %s", this.min);
        report.setFailureMessage(advice);
        return report;
    }
}
