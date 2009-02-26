package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

public class MinProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private T min;
	private Collection<T> fact;

    public final static String PROPOSITION_MESSAGE_CONTEXT_TOKEN_MIN = "prop_min";

    public MinProposition() {
    }
    
    public MinProposition(String id, String propositionName, 
    		ComparisonOperator operator, T expectedValue, Collection<T> fact,
    		RulePropositionDTO ruleProposition) {
        super(id, propositionName, PropositionType.MIN, operator, expectedValue);
        this.fact = fact;
	}

    @Override
    public Boolean apply() {
    	this.min = Collections.min(this.fact);

        result = checkTruthValue(min, super.expectedValue);

        resultValues = new ArrayList<T>();
        resultValues.add(min);

        return result;
    }

    @Override
    public void buildMessageContextMap() {
        String minStr = getTypeAsString(this.min);
        addMessageContext(PROPOSITION_MESSAGE_CONTEXT_TOKEN_MIN, minStr);
    }
}
