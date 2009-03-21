package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.exceptions.IllegalFunctionStateException;

public class ComparisonOperatorComparable<T extends Comparable<T>> extends AbstractFunction {

	private ComparisonOperator operator;
	private T expectedValue;
	private Comparable<T> computedValue;
	private Boolean result;
	private List<Object> input;

    public ComparisonOperatorComparable() {
	}

    public ComparisonOperatorComparable(ComparisonOperator operator, T expectedValue, Comparable<T> computedValue) {
    	this.input = new ArrayList<Object>();
    	this.input.add(operator);
    	this.input.add(expectedValue);
    	this.input.add(computedValue);
	}

	public Object compute() {
		if(this.input == null) {
			throw new IllegalFunctionStateException("Invalid input. Input cannot be null.");
		}
		
		this.operator = (ComparisonOperator) input.get(0);
		this.expectedValue = (T) input.get(1);
		this.computedValue = (T) input.get(2);
		this.result = compare();
		
		return this.result;
	}

    protected Boolean compare() {
        if (!(computedValue instanceof Comparable) || !(expectedValue instanceof Comparable)) {
            throw new IllegalArgumentException("Both computed value and expected values have to implement java.lang.Comparable.");
        }

        int compareValue = this.computedValue.compareTo(this.expectedValue);
        return compare(compareValue);
    }

    protected Boolean compare(Comparator<T> comparator, T computedValue, T expectedValue) {
        int compareValue = comparator.compare(computedValue, expectedValue);
        return compare(compareValue);
    }

    private Boolean compare(int compareValue) {
        Boolean truthValue = false;
        switch (this.operator) {
	        case EQUAL_TO:
	            truthValue = (compareValue == 0);
	            break;
	        case LESS_THAN:
	            truthValue = (compareValue == -1);
	            break;
	        case LESS_THAN_OR_EQUAL_TO:
	            truthValue = (compareValue == 0 || compareValue == -1);
	            break;
	        case GREATER_THAN:
	            truthValue = (compareValue == 1);
	            break;
	        case GREATER_THAN_OR_EQUAL_TO:
	            truthValue = (compareValue == 0 || compareValue == 1);
	            break;
	        case NOT_EQUAL_TO:
	            truthValue = (compareValue != 0);
	            break;
        }
	    return truthValue;
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
		this.input = (List) input;
	}
}
