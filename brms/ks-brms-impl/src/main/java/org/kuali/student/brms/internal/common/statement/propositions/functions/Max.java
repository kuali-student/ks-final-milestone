package org.kuali.student.brms.internal.common.statement.propositions.functions;

import java.util.Collection;
import java.util.Collections;

import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.internal.common.statement.exceptions.IllegalFunctionStateException;

public class Max<T extends Comparable<T>> extends AbstractFunction<T> {

	private T result;

    private FactResultInfo factDTO;
    private String factColumnKey;
    
    public Max() {
	}

    public Max(FactResultInfo factDTO, String factColumnKey) {
    	this.factDTO = factDTO;
    	this.factColumnKey = factColumnKey;
	}

    public void setFact(FactResultInfo factDTO, String factColumnKey) {
    	this.factDTO = factDTO;
    	this.factColumnKey = factColumnKey;
    }

	public T compute() {
    	if(this.factDTO == null) {
    		throw new IllegalFunctionStateException("Fact is null: " + this.factDTO);
    	} else if(this.factColumnKey == null) {
    		throw new IllegalFunctionStateException("Fact column key is null: " + this.factColumnKey);
    	} else if(!containsFactColumnKey(this.factDTO, this.factColumnKey)) {
    		throw new IllegalFunctionStateException("Fact column key not found: " + this.factColumnKey);
    	}
		this.result = max();
		
		return this.result;
	}

	private T max() {
		Collection<T> facts = getCollection(this.factDTO, this.factColumnKey);
		return Collections.max(facts);
	}
}
