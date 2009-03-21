package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.util.Collection;
import java.util.Collections;

import org.kuali.student.rules.internal.common.statement.exceptions.IllegalFunctionStateException;

public class Max<T extends Comparable<T>> extends AbstractFunction {

	private T result;
	private Collection<T> input;

    public Max() {
	}

    public Max(Collection<T> input) {
    	this.input = input;
	}

	public Object compute() {
		if(this.input == null) {
			throw new IllegalFunctionStateException("Invalid input. Input cannot be null.");
		}
		
		this.result = Collections.max(this.input);
		
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
