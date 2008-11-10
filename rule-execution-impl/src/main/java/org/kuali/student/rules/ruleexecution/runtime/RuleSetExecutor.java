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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;

public interface RuleSetExecutor {

    public final static String DEFAULT_RULE_CACHE_KEY = "DEFAULT_RULE_CACHE_KEY"; 

    /**
     * Adds a rule set to the rule set execution cache.
     * This is a convenience method since rule sets are lazily loaded into the 
     * execution cache when <code>execute>/code> is performed. 
     * If <code>businessRuleType</code> is null then the 
     * <code>DEFAULT_RULE_CACHE_KEY</code> is used. 
     * See {@link #execute(BusinessRuleInfoDTO, RuleSetDTO, Map)}
     * 
     * @param businessRuleType
     * @param ruleSet
     * @see #execute(BusinessRuleInfoDTO, RuleSetDTO, Map)
     */
	public void addRuleSet(String businessRuleType, RuleSetDTO ruleSet);

	/**
     * Removes a rule set from the rule set execution cache.
	 * 
	 * @param businessRuleType Rule set type (category) to add rule set to
	 * @param ruleSetName Rule set name
	 */
    public void removeRuleSet(String businessRuleType, String ruleSetName);

    /**
     * Returns true is the <code>ruleSetName</code> exists in the 
     * <code>businessRuleType</code> execution cache.
     * 
	 * @param businessRuleType Rule set type (category) to add rule set to
	 * @param ruleSetName Rule set name
     * @return True if rule set exists in the business rule type execution cache; otherwise false
     */
    public boolean containsRuleSet(String businessRuleType, String ruleSetName);

	/**
	 * Returns the set of keys in the rule set cache.
	 * 
	 * @return Cache key set
	 */
    public Set<String> getCacheKeySet();

    /**
     * Executes an <code>agenda</code> with a list of <code>facts</code>.
     * 
     * @param agenda Agenda to execute
     * @param facts List of Facts for the <code>agenda</code>
     * @return Result of executing the <code>agenda</code>
     */
    //public ExecutionResult execute(RuntimeAgendaDTO agenda, Map<String, Object> factMap);

    /**
     * Executes a <code>ruleSet</code> with a list of <code>facts</code>.
     * 
     * @param businessRuleType Business rule type key (cache key)
     * @param ruleSet Rule set to execute
     * @param facts List of Facts for the <code>ruleSet</code>
     * @return Result of executing the <code>ruleSet</code>
     */
    public ExecutionResult execute(String businessRuleType, RuleSetDTO ruleSet, List<Object> facts);

    /**
     * Executes a business rule.
     * The BusinessRuleInfoDTO.getBusinessRuleTypeKey() is used as the key in 
     * rule set execution cache. If BusinessRuleInfoDTO.getBusinessRuleTypeKey()
     * is null then the <code>DEFAULT_RULE_CACHE_KEY</code> is used.
     * 
     * @param ruleSet Rule set to be executed
     * @param brInfo Business rule to be executed
     * @param factMap Map of facts (data)
     * @return Result of execution 
     */
    public ExecutionResult execute(BusinessRuleInfoDTO brInfo, RuleSetDTO ruleSet, Map<String, Object> factMap);
}
