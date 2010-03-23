package org.kuali.student.brms.internal.common.statement.propositions.functions;

import java.util.Comparator;

import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.statement.exceptions.IllegalFunctionStateException;

public class ComparisonOperatorComparable<T extends Comparable<T>> extends AbstractFunction<Boolean> {

	private ComparisonOperator operator;
	private T expectedValue;
	private Comparable<T> computedValue;
	private Boolean result;

    public ComparisonOperatorComparable() {
	}

    public ComparisonOperatorComparable(ComparisonOperator operator, T expectedValue, Comparable<T> computedValue) {
		this.operator = operator;
		this.expectedValue = expectedValue;
		this.computedValue = computedValue;
	}

	public Boolean compute() {
    	if(this.operator == null) {
    		throw new IllegalFunctionStateException("Operator is null: " + this.operator);
    	} else if(this.expectedValue == null) {
    		throw new IllegalFunctionStateException("Expected value key is null: " + this.expectedValue);
    	} else if(this.computedValue == null) {
    		throw new IllegalFunctionStateException("Computed value is null: " + this.computedValue);
    	}
		
		this.result = compare();
		
		return this.result;
	}

    protected Boolean compare() {
        if (!(this.computedValue instanceof Comparable<?>) || !(this.expectedValue instanceof Comparable<?>)) {
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

}
