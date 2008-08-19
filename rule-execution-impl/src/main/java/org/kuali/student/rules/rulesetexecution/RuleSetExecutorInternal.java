package org.kuali.student.rules.rulesetexecution;

import java.io.Reader;
import java.util.List;

public interface RuleSetExecutorInternal {

    /**
     * Executes a list of rule sets.
     * 
     * @param RuleSetId List of rule set ids
     * @param facts List of facts
     * @return
     */
    public Object execute(String ruleSetId, List<?> facts);

    /**
     * Caches a rule set's source code.
     * 
     * @param ruleSetId Rule set cache id
     * @param source Rule set source code
     */
    public void addRuleSet(String ruleSetId, Reader source);

    /**
     * Removes a cache rule set's source code.
     * 
     * @param ruleSetId Rule set cache id
     * @return True if rule set was removed otherwise false 
     */
    public boolean removeRuleSet(String ruleSetId);
}