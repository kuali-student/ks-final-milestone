package org.kuali.student.brms.internal.common.statement.propositions;

import java.util.ArrayList;

import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.statement.MessageContextConstants;
import org.kuali.student.brms.internal.common.statement.propositions.functions.Min;

public class MinProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    private Min<T> minFunction;
    private T min;

    public MinProposition() {
    }
    
    public MinProposition(String id, String propositionName, 
    		ComparisonOperator operator, T expectedValue, 
    		FactResultInfo factDTO, String factColumn) {
        super(id, propositionName, PropositionType.MIN, operator, expectedValue, 
        		null, null, factDTO, factColumn);
        this.minFunction = new Min<T>(factDTO, factColumn);
	}

    @Override
    public Boolean apply() {
    	this.min = this.minFunction.compute();

        result = checkTruthValue(this.min);

        resultValues = new ArrayList<T>();
        resultValues.add(this.min);

        return result;
    }

    @Override
    public void buildMessageContextMap() {
        String minStr = getTypeAsString(this.min);
        addMessageContext(MessageContextConstants.PROPOSITION_MIN_MESSAGE_CTX_KEY_MIN, minStr);
    }
}
