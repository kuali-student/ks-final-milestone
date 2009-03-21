package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.math.BigDecimal;
import java.util.Collection;

import org.kuali.student.rules.internal.common.statement.exceptions.IllegalFunctionStateException;

public class Sum<T extends Number> extends AbstractFunction {

	Number result;
	private Collection<T> input;

    public Sum() {
	}

    public Sum(Collection<T> input) {
    	this.input = input;
	}

	public Object compute() {
		if(this.input == null) {
			throw new IllegalFunctionStateException("Invalid input. Input cannot be null.");
		}

		Class<?> type = this.input.iterator().next().getClass();
		this.result = convertToNumber(type, sum());
		
		return this.result;
	}

    /**
     * This method sums all the elements in the fact list.
     * 
     * @return Sum of all the element in the fact list
     */
    protected Number sum() {
        BigDecimal sum = new BigDecimal("0.0");

        for (Number element : this.input) {
            sum = sum.add(new BigDecimal(element.toString()));
        }

        return sum;
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
