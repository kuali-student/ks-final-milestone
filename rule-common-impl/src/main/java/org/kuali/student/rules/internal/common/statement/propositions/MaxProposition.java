package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

public class MaxProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private T max;
	private Collection<T> fact;

    public final static String DEFAULT_SUCCESS_MESSAGE = "Maximum constraint fulfilled";
    public final static String DEFAULT_FAILURE_MESSAGE = "Maximum not met. Maximum found: #max#";

    public final static String MAX_REPORT_TEMPLATE_TOKEN = "max";

    public MaxProposition() {
    }

    public MaxProposition(String id, String propositionName, 
    		ComparisonOperator operator, T expectedValue, Collection<T> fact,
    		RulePropositionDTO ruleProposition) {
        super(id, propositionName, PropositionType.MAX, operator, expectedValue, ruleProposition);
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
        // TODO: Use the operator to compute exact message
        String maxStr = getTypeAsString(this.max);
        addMessageToken(MAX_REPORT_TEMPLATE_TOKEN, maxStr);
        buildDefaultReport(DEFAULT_SUCCESS_MESSAGE, DEFAULT_FAILURE_MESSAGE);
        return report;
    }
}
