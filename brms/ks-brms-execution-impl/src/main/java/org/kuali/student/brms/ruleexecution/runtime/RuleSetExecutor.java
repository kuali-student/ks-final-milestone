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
package org.kuali.student.brms.ruleexecution.runtime;

import java.util.Map;

import org.kuali.student.brms.repository.dto.RuleSetInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;

public interface RuleSetExecutor {
    /**
     * Adds or replaces a rule set in the rule set execution cache.
     * This is a convenience method since rule sets are lazily loaded into the 
     * execution cache when <code>execute>/code> is performed. 
     * 
     * @param businessRule Functional business rule
     * @param ruleSet Rule set
     * @see #execute(BusinessRuleInfo, RuleSetInfo, Map)
     */
	public void addRuleSet(BusinessRuleInfo businessRule, RuleSetInfo ruleSet);

	/**
     * Removes a rule set from the rule set execution cache.
	 * 
     * @param businessRule Functional business rule
	 * @param ruleSet Rule set
	 */
    public void removeRuleSet(BusinessRuleInfo businessRule, RuleSetInfo ruleSet);

    /**
     * Returns true is the <code>ruleSetName</code> exists in the 
     * <code>businessRuleType</code> execution cache.
     * 
     * @param businessRule Functional business rule
	 * @param ruleSet Rule set
     * @return True if rule set exists in the business rule type execution cache; otherwise false
     */
    public boolean containsRuleSet(BusinessRuleInfo businessRule);

    /**
     * Clears the rule set cache.
     */
    public void clearRuleSetCache();

    /**
     * Sets global read only objects for the rule engine. 
     * These objects are usually constants, Hibernate sessions, 
     * JPA session etc.
     * 
     * @param globalObjectMap Global key,value map
     */
    public void setGlobalObjects(Map<String, Object> globalObjectMap);

    /**
     * Executes a business rule <code>businessRule</code> in a rule set <code>ruleSet</code>.
     * 
     * @param businessRule Business rule to be executed
     * @param factMap Map of facts (data)
     * @return Result of execution 
     */
    public ExecutionResult execute(BusinessRuleInfo businessRule, Map<String, Object> factMap);
}
