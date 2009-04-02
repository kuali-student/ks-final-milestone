package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.math.BigDecimal;
import java.math.MathContext;

import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.statement.exceptions.IllegalFunctionStateException;

public class Average<T extends Number> extends AbstractFunction<Number> { 
	private Number result;
    private FactResultDTO factDTO;
    private String factColumnKey;
	private Sum<Number> sumFunction;

	public Average() {
	}

    public Average(FactResultDTO factDTO, String factColumnKey) {
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

    	this.sumFunction = new Sum<Number>(this.factDTO, this.factColumnKey);
		Number sum = (Number) this.sumFunction.compute();

		MathContext mc = MathContext.DECIMAL64;
		BigDecimal listSize = new BigDecimal(this.factDTO.getResultList().size());
		BigDecimal sum2 = new BigDecimal(sum.toString());
		this.result = sum2.divide(listSize, mc);
		
		return this.result;
	}
}
