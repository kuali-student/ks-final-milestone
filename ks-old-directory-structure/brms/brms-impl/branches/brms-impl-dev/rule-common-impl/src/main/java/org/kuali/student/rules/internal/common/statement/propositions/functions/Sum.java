package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.math.BigDecimal;
import java.util.Map;

import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.statement.exceptions.IllegalFunctionStateException;

public class Sum<T extends Number> extends AbstractFunction<Number> {

	private Number result;

    private FactResultDTO factDTO;
    private String factColumnKey;
    
    public Sum() {
	}

    public Sum(FactResultDTO factDTO, String factColumnKey) {
    	this.factDTO = factDTO;
    	this.factColumnKey = factColumnKey;
	}

    public void setFact(FactResultDTO factDTO, String factColumnKey) {
    	this.factDTO = factDTO;
    	this.factColumnKey = factColumnKey;
    }

    public Number compute() {
    	if(this.factDTO == null) {
    		throw new IllegalFunctionStateException("Fact is null: " + this.factDTO);
    	} else if(this.factColumnKey == null) {
    		throw new IllegalFunctionStateException("Fact column key is null: " + this.factColumnKey);
    	} else if(!containsFactColumnKey(this.factDTO, this.factColumnKey)) {
    		throw new IllegalFunctionStateException("Fact column key not found: " + this.factColumnKey);
    	}
    	this.result = sum();
		return this.result;
	}

    /**
     * This method sums all the elements in the fact list.
     * 
     * @return Sum of all the element in the fact list
     */
    protected Number sum() {
        BigDecimal sum = new BigDecimal("0.0");

        for (Map<String, String> row : this.factDTO.getResultList()) {
            String value = row.get(this.factColumnKey);
        	sum = sum.add(new BigDecimal(value));
        }

        return sum;
    }
}
