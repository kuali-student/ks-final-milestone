package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;

import org.kuali.student.rules.internal.common.statement.exceptions.IllegalFunctionStateException;

public class Average<T extends Number> extends AbstractFunction { 
	private Number result;
	private Collection<T> input;
	private Sum<T> sumFunction;

	public Average() {
	}

    public Average(Collection<T> input) {
    	this.input = input;
	}

	public Object compute() {
		if(this.input == null) {
			throw new IllegalFunctionStateException("Invalid input. Input cannot be null.");
		}
		
		this.sumFunction = new Sum<T>(input);
		this.sumFunction.compute();

		MathContext mc = MathContext.DECIMAL64;
		Number sum = (Number) this.sumFunction.getOutput();
		BigDecimal listSize = new BigDecimal(this.input.size());
		BigDecimal sum2 = new BigDecimal(sum.toString());
		this.result = sum2.divide(listSize, mc);
		
		return this.result;
	}

	public Integer getInputs() {
		return new Integer(1);
	}

	public Integer getOutputs() {
		return new Integer(1);
	}

	public Object getOutput() {
		return this.result;
	}

	public void setInput(Object input) {
		this.input = (Collection) input;
	}
}
