/**
 * 
 */
package org.kuali.student.rules.BRMS.mapper;

import org.kuali.student.rules.BRMSCore.entity.ComparisonOperator;

/**
 * Apply a logical constraint.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
public interface Proposition<T extends Comparable<? super T>> {
	
	
	
	/**
	 * Evaluates the proposition to a truth value
	 * @return <code>true</code> if the constraint is met.
	 */
	public Boolean apply(ComparisonOperator operator, T expectedValue);
	
	/**
	 * Returns the cached value of apply method's result
	 * @return <code>true</code> if the constraint is met.
	 * @exception IllegalPropositionStateException
	 */
	public Boolean getResult();	
	
	/**
	 * An explanation of the results of the constraint.
	 * @return the advice
	 */
	public PropositionReport getReport();
	
}
