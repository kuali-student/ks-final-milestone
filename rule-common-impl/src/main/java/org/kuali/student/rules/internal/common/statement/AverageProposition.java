package org.kuali.student.rules.internal.common.statement;

import java.math.BigDecimal;
import java.util.List;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;

public class AverageProposition<E extends Number> extends SumProposition<E> {

    private BigDecimal listSize;
    
	public AverageProposition(String propositionName, 
    						  ComparisonOperator operator, 
    						  String expectedValue, 
    						  List<E> factSet) {
    	super(propositionName, operator, expectedValue, factSet);
    	if (factSet == null || factSet.size() == 0) {
    		throw new IllegalArgumentException("Fact set cannot be null");
    	}
    	listSize = new BigDecimal(factSet.size());
    }

    @Override
    public Boolean apply() {
        BigDecimal expectedValue = new BigDecimal(expectedValueAsString);

        BigDecimal average = sum().divide(listSize);

        result = checkTruthValue(average, expectedValue);

        cacheReport("Total is short by %s", average, expectedValue);

        return result;
    }
}
