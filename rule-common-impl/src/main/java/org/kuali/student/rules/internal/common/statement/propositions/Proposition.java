/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

	/**
	 * Return the proposition id.
	 * @return Proposition id
	 */
	public String getId();
}
