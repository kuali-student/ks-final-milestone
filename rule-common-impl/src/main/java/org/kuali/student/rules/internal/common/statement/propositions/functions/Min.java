package org.kuali.student.rules.internal.common.statement.propositions.functions;

import java.util.Collection;
import java.util.Collections;

import org.kuali.student.rules.factfinder.dto.FactResultDTO;

//public class Min<T extends Comparable<T>> extends AbstractFunction {
public class Min extends AbstractFunction {

//	private T result;
//	private Collection<T> input;
	private Object result;

    private FactResultDTO factDTO;
    private String factColumnKey;
    
    public Min() {
	}

    public Min(FactResultDTO factDTO, String factColumnKey) {
    	this.factDTO = factDTO;
    	this.factColumnKey = factColumnKey;
	}

    public void setFact(FactResultDTO factDTO, String factColumnKey) {
    	this.factDTO = factDTO;
    	this.factColumnKey = factColumnKey;
    }

	public Object compute() {
//		if(this.input == null) {
//			throw new IllegalFunctionStateException("Invalid input. Input cannot be null.");
//		}
//		
//		this.result = Collections.min(this.input);
		this.result = min();
		
		return this.result;
	}

	private Comparable<Object> min() {
		Collection<Comparable<Object>> facts = getCollection(this.factDTO, this.factColumnKey);
		return Collections.min(facts);
	}
}
