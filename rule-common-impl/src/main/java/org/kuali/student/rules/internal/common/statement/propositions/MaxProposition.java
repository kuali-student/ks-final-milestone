package org.kuali.student.rules.internal.common.statement.propositions;

import java.util.ArrayList;

import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.statement.propositions.functions.Max;

public class MaxProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private Max<T> maxFunction;
    private T max;

    public MaxProposition() {
    }

    public MaxProposition(String id, String propositionName, 
    		ComparisonOperator operator, T expectedValue, 
    		FactResultDTO factDTO, String factColumn) {
        super(id, propositionName, PropositionType.MAX, operator, expectedValue, 
        		null, null, factDTO, factColumn);
        this.maxFunction = new Max<T>(factDTO, factColumn);
	}

    @Override
    public Boolean apply() {
    	this.max = this.maxFunction.compute();

        result = checkTruthValue(this.max, super.expectedValue);

        resultValues = new ArrayList<T>();
        resultValues.add(this.max);

        return result;
    }

    @Override
    public void buildMessageContextMap() {
        String maxStr = getTypeAsString(this.max);
        addMessageContext(MessageContextConstants.PROPOSITION_MAX_MESSAGE_CTX_KEY_MAX, maxStr);
    }
}
