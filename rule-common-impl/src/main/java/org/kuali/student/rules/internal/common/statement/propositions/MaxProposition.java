package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

public class MaxProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private T max;
	private Collection<T> fact;

    public final static String PROPOSITION_MESSAGE_CONTEXT_TOKEN_MAX = "prop_max";

    public MaxProposition() {
    }

    public MaxProposition(String id, String propositionName, 
    		ComparisonOperator operator, T expectedValue, Collection<T> fact,
    		RulePropositionDTO ruleProposition) {
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
    public void buildMessageContextMap() {
        String maxStr = getTypeAsString(this.max);
        addMessageContext(PROPOSITION_MESSAGE_CONTEXT_TOKEN_MAX, maxStr);
    }
}
