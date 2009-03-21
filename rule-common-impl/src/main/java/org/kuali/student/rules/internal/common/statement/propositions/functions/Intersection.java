package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.rules.internal.common.statement.exceptions.FunctionException;
import org.kuali.student.rules.internal.common.statement.exceptions.IllegalFunctionStateException;

public class Intersection<E> extends AbstractFunction { 

	public enum Operation {INTERSECTION, DIFFERENCE, SUBSET_DIFFERENCE}
	
    private List<Collection<E>> input;
	private Collection<E> criteriaSet;
    private Collection<E> factSet;
    private Collection<E> result;

    public Intersection() {
    }
    
    public Intersection(List<Collection<E>> input) {
    	this.input = input;
    }
    
	public Object compute() {
		if(this.input == null || this.input.size() != 2) {
			throw new IllegalFunctionStateException("Invalid input. " +
					"Number of inputs found: " + (this.input == null ? "Null" : this.input.size()));
		}
		
		this.criteriaSet = this.input.get(0);
		this.factSet = this.input.get(1);
		
		Operation operationType = Operation.valueOf(super.operation);
		switch(operationType) {
			case INTERSECTION: 
				this.result = intersection();
				break;
			case DIFFERENCE: 
				this.result = difference();
				break;
			case SUBSET_DIFFERENCE: 
				this.result = subSetDifference();
				break;
			default: 
				throw new FunctionException("Operation not found");
		}
		
		return this.result;
    }

    /**
     * Returns the intersection of the fact set with the criteria set.
     * 
     * @return the intersection
     */
    private Set<E> intersection() {
        Set<E> rval = new HashSet<E>(this.factSet);
        rval.retainAll(this.criteriaSet);

        return rval;
    }

    /**
     * Returns the disjunction of the criteria set from the fact set.
     * 
     * @return
     */
    private Set<E> difference() {
        HashSet<E> rval = new HashSet<E>(criteriaSet);
        rval.removeAll(this.factSet);

        return rval;
    }

    private Set<E> subSetDifference() {
        HashSet<E> rval = new HashSet<E>(factSet);
        rval.removeAll(this.criteriaSet);

        return rval;
    }

	public Integer getInputs() {
		return new Integer(2);
	}

	public Integer getOutputs() {
		return new Integer(1);
	}

	public Object getOutput() {
		return this.result;
	}

	public void setInput(Object input) {
		this.input = (List<Collection<E>>) input;
	}
}
