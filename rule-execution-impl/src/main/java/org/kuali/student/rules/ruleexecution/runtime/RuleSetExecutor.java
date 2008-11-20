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
package org.kuali.student.rules.ruleexecution.runtime;

import java.util.Map;

import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RuntimeAgendaDTO;

public interface RuleSetExecutor {
    /**
     * Adds or replaces a rule set in the rule set execution cache.
     * This is a convenience method since rule sets are lazily loaded into the 
     * execution cache when <code>execute>/code> is performed. 
     * 
     * @param businessRule Functional business rule
     * @param ruleSet Rule set
     * @see #execute(BusinessRuleInfoDTO, RuleSetDTO, Map)
     */
	public void addRuleSet(BusinessRuleInfoDTO businessRule, RuleSetDTO ruleSet);

	/**
     * Removes a rule set from the rule set execution cache.
	 * 
     * @param businessRule Functional business rule
	 * @param ruleSet Rule set
	 */
    public void removeRuleSet(BusinessRuleInfoDTO businessRule, RuleSetDTO ruleSet);

    /**
     * Returns true is the <code>ruleSetName</code> exists in the 
     * <code>businessRuleType</code> execution cache.
     * 
     * @param businessRule Functional business rule
	 * @param ruleSet Rule set
     * @return True if rule set exists in the business rule type execution cache; otherwise false
     */
    public boolean containsRuleSet(BusinessRuleInfoDTO businessRule);

    /**
     * Clears the rule set cache.
     */
    public void clearRuleSetCache();
    
    /**
     * <p>Executes an <code>agenda</code> with a map of <code>facts</code> and 
     * returns a list of execution results {@link ExecutionResult}.</p>
     * <p>The {@link ExecutionResult}'s id is set to the 
     * {@link BusinessRuleInfoDTO}'s business rule id.</p>
     * 
     * @param agenda Agenda to execute
     * @param facts List of Facts for the <code>agenda</code>
     * @return Result of executing the <code>agenda</code>
     */
    public AgendaExecutionResult execute(RuntimeAgendaDTO agenda, Map<String, Object> factMap);

    /**
     * Executes a business rule <code>businessRule</code> in a rule set <code>ruleSet</code>.
     * 
     * @param businessRule Business rule to be executed
     * @param factMap Map of facts (data)
     * @return Result of execution 
     */
    public ExecutionResult execute(BusinessRuleInfoDTO businessRule, Map<String, Object> factMap);
}
