package org.kuali.student.brms.internal.common.statement.propositions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.statement.MessageContextConstants;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionDTO;

public class MinProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private T min;
	private Collection<T> fact;

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
        addMessageContext(MessageContextConstants.PROPOSITION_MIN_MESSAGE_CTX_KEY_MIN, minStr);
    }
}
