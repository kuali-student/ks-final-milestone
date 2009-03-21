package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.ArrayList;
import java.util.Collection;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.statement.propositions.functions.Function;
import org.kuali.student.rules.internal.common.statement.propositions.functions.Max;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

public class MaxProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private Function maxFunction;
    private T max;
	private Collection<T> fact;

    public MaxProposition() {
    }

    public MaxProposition(String id, String propositionName, 
    		ComparisonOperator operator, T expectedValue, Collection<T> fact,
    		RulePropositionDTO ruleProposition) {
        super(id, propositionName, PropositionType.MAX, operator, expectedValue);
        this.fact = fact;
        this.maxFunction = new Max<T>(this.fact);
	}

    @Override
    public Boolean apply() {
    	this.max = (T) this.maxFunction.compute();

        result = checkTruthValue(this.max, super.expectedValue);

        resultValues = new ArrayList<T>();
        resultValues.add(max);

        return result;
    }

    @Override
    public void buildMessageContextMap() {
        String maxStr = getTypeAsString(this.max);
        addMessageContext(MessageContextConstants.PROPOSITION_MAX_MESSAGE_CTX_KEY_MAX, maxStr);
    }
}
