/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.internal.common.statement.propositions;

import java.util.Collection;
import java.util.Map;

import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.internal.common.statement.exceptions.IllegalPropositionStateException;

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
     * Gets the message context map.
     * 
     * @return Message context map
     */
    public Map<String, Object> getMessageContextMap();
    
	/**
	 * Returns the cached value of apply method's result
	 * @return <code>true</code> if the constraint is met.
	 * @exception IllegalPropositionStateException
	 */
	public Boolean getResult();	
	
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
	
	/**
	 * Returns the proposition type.
	 * @return Proposition type
	 */
	public PropositionType getType();

	/**
	 * Returns the values of the proposition result.
	 * 
	 * @return Proposition result values
	 */
	public Collection<?> getResultValues();	
	
    public FactResultInfo getCriteria();

    public FactResultInfo getFact();

    public String getCriteriaColumn();

    public String getFactColumn();
}
