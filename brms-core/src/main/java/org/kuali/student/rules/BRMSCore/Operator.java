package org.kuali.student.rules.BRMSCore;

import javax.persistence.Embeddable;

@Embeddable
public class Operator {

	ComparisonOperatorType value;
	
	/**
	 * 
	 */
	public Operator() {
		this.value = null;		
	}
	
	/**
	 * @param businessEntity
	 * @param facts
	 * @param className
	 */
	public Operator(ComparisonOperatorType value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public final ComparisonOperatorType getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public final void setValue(ComparisonOperatorType value) {
		this.value = value;
	}	
}
