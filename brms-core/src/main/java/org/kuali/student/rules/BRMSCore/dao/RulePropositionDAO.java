package org.kuali.student.rules.BRMSCore.dao;

import org.kuali.student.rules.BRMSCore.entity.RuleProposition;

/**
 * Defines DAO operations for Rule Proposition
 * 
 * @author Zdenek Zraly (zdenek.zraly@ubc.ca)
 */
public interface RulePropositionDAO {

    /**
     * Persists RuleProposition in database.
     * 
     * @param ruleProposition
     *            A rule proposition to persist in database.
     * @return persisted rule proposition.
     */
    public RuleProposition createRuleProposition(RuleProposition ruleProposition);

    /**
     * Updates RuleProposition in database.
     * 
     * @param ruleProposition
     *            Rule Proposition to update in database.
     * @return updated rule proposition.
     */
    public RuleProposition updateRuleProposition(RuleProposition ruleProposition);

    /**
     * Finds RuleProposition in database.
     * 
     * @param id
     *            ID of Rule Proposition to find in database.
     * @return Found rule proposition or null if proposition not found.
     */
    public RuleProposition lookupRuleProposition(long id);

    /**
     * Deletes RuleProposition from database.
     * 
     * @param ruleProposition
     *            Rule Proposition to delete from database.
     * @return true if operation completed successfully
     */
    public boolean deleteRuleProposition(RuleProposition ruleProposition);
}
