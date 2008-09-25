/**
 * 
 */
package org.kuali.student.rules.internal.common.statement.propositions;

import org.kuali.student.rules.internal.common.statement.exceptions.IllegalPropositionStateException;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;

/**
 * Apply a logical constraint.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
public interface Proposition {
		
	/**
	 * Evaluates the proposition to a truth value
	 * @return <code>true</code> if the constraint is met.
	 */
	public Boolean apply();
	
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

	/**
	 * Returns the proposition name
	 * @return Proposition name
	 */
	public String getPropositionName();
}
