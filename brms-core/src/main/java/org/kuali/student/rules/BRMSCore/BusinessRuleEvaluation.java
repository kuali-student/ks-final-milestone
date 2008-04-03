package org.kuali.student.rules.BRMSCore;

import javax.persistence.Embeddable;

@Embeddable
public class BusinessRuleEvaluation {

	String postCondition;
	boolean outcome;
	String[] advice;
		
	
	/**
	 * 
	 */
	public BusinessRuleEvaluation() {
		this.postCondition = null;
		this.outcome = false;
		this.advice = null;
	}

	/**
	 * @param postCondition
	 * @param outcome
	 * @param advice
	 */
	public BusinessRuleEvaluation(String postCondition, boolean outcome,
			String[] advice) {
		this.postCondition = postCondition;
		this.outcome = outcome;
		this.advice = advice;
	}

	/**
	 * @return the postCondition
	 */
	public final String getPostCondition() {
		return postCondition;
	}

	/**
	 * @param postCondition the postCondition to set
	 */
	public final void setPostCondition(String postCondition) {
		this.postCondition = postCondition;
	}

	/**
	 * @return the outcome
	 */
	public final boolean isOutcome() {
		return outcome;
	}

	/**
	 * @param outcome the outcome to set
	 */
	public final void setOutcome(boolean outcome) {
		this.outcome = outcome;
	}

	/**
	 * @return the advice
	 */
	public final String[] getAdvice() {
		return advice;
	}

	/**
	 * @param advice the advice to set
	 */
	public final void setAdvice(String[] advice) {
		this.advice = advice;
	}	
}
