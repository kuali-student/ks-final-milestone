package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.ArrayList;
import java.util.Collection;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.statement.propositions.functions.Function;
import org.kuali.student.rules.internal.common.statement.propositions.functions.Min;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

public class MinProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private Function minFunction;
    private T min;
	private Collection<T> fact;

    public MinProposition() {
    }
    
    public MinProposition(String id, String propositionName, 
    		ComparisonOperator operator, T expectedValue, Collection<T> fact,
    		RulePropositionDTO ruleProposition) {
        super(id, propositionName, PropositionType.MIN, operator, expectedValue);
        this.fact = fact;
        this.minFunction = new Min<T>(this.fact);
	}

    @Override
    public Boolean apply() {
    	this.min = (T) this.minFunction.compute();

        result = checkTruthValue(min, super.expectedValue);

        resultValues = new ArrayList<T>();
        resultValues.add(min);

        return result;
    }

    @Override
    public void buildMessageContextMap() {
        String minStr = getTypeAsString(this.min);
        addMessageContext(MessageContextConstants.PROPOSITION_MIN_MESSAGE_CTX_KEY_MIN, minStr);
    }
}
