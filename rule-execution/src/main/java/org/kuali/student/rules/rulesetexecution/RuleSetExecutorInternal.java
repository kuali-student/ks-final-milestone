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
    public Object execute(String ruleSetId, List facts);

    /**
     * Caches a rule set's source code.
     * 
     * @param ruleSetId Rule set cache id
     * @param source Rule set source code
     */
    public void add(String ruleSetId, Reader source);

}