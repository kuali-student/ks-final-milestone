package org.kuali.student.rules.ruleexecution.runtime;

import java.io.Reader;
import java.util.List;

import org.kuali.student.rules.repository.dto.RuleSetDTO;

public interface SimpleExecutor {
    /**
     * Default rule base type key.
     */
	public final static String DEFAULT_RULE_CACHE_KEY = "DEFAULT_RULE_CACHE_KEY"; 

    /**
     * Add a rule set's source code by <code>id</code> to the default 
     * execution cache.
     * 
     * @param id Rule set cache id
     * @param source Rule set source code
     */
    public void addRuleSet(String id, Reader source);

    /**
     * Adds a <code>ruleSet</code> to the <code>ruleBaseType</code> 
     * execution cache.
     * 
     * @param ruleBaseType Rule base type cache
     * @param ruleSet Rule set
     */
    public void addRuleSet(String ruleBaseType, RuleSetDTO ruleSet);

    /**
     * Removes a rule set from the default execution cache by <code>id</code>.
     * 
     * @param id Rule set cache id
     */
    public void removeRuleSet(String id);

    /**
     * Removes a rule set from the <code>ruleBaseType</code> execution cache 
     * by <code>id</code>.
     * 
     * @param ruleBaseType Rule base type cache
     * @param ruleSet Rule set
     */
    public void removeRuleSet(String ruleBaseType, RuleSetDTO ruleSet);

    /**
     * Returns true is the rule set <code>id</code> exists in the 
     * default execution cache.
     * 
     * @param id Rule set id
     * @return True if rule set exists execution cache; otherwise false
     */
    public boolean containsRuleSet(String id);

    /**
     * Returns true if the <code>ruleSetName</code> exists in the 
     * <code>ruleBaseType</code> execution cache.
     *  
     * @param ruleBaseType Rule base type cache
     * @param ruleSet Rule set
     * @return
     */
    public boolean containsRuleSet(String ruleBaseType, RuleSetDTO ruleSet);

    /**
     * Clears the rule set execution cache.
     */
    public void clearRuleSetCache();

    /**
     * Clears the <code>ruleBaseType</code> rule set execution cache.
     * 
     * @param ruleBaseType Rule base type
     * @see #addRuleSet(String, RuleSetDTO)
     */
    public void clearRuleSetCache(String ruleBaseType);

    /**
     * Executes rules in the default execution cache with a list of 
     * <code>facts</code>.
     * 
     * @param facts list of facts.
     * @return An execution result
     * @see #addRuleSet(String, Reader)
     * @see #containsRuleSet(String)
     * @see #removeRuleSet(String)
     */
    public ExecutionResult execute(List<?> facts);

    /**
     * Executes a rule set in the <code>ruleBaseType</code> execution cache 
     * by <code>id</code> with a list of <code>facts</code>.
     * 
     * @param ruleBaseType Rule base type cache
     * @param id Rule set id
     * @param facts List of facts
     * @return An execution result
     * @see #addRuleSet(String, RuleSetDTO)
     * @see #containsRuleSet(String, String)
     * @see #removeRuleSet(String, String)
     */
    public ExecutionResult execute(String ruleBaseType, List<?> facts);

    /**
     * Executes a rule set with a list of facts.
     * 
     * @param ruleSet Rule set
     * @param facts List of facts
     * @return An execution result
     */
    public ExecutionResult execute(RuleSetDTO ruleSet, List<?> facts);
}