package org.kuali.student.rules.BRMSCore.dao;

import org.kuali.student.rules.BRMSCore.entity.RuleElement;

/**
 * Defines DAO operations for RuleRule Element
 * 
 * @author Zdenek Zraly (zdenek.zraly@ubc.ca)
 */
public interface RuleElementDAO {

    /**
     * Persists RuleElement in database.
     * 
     * @param ruleElement
     *            A rule element to persist in database.
     * @return persisted rule element.
     */
    public RuleElement createRuleElement(RuleElement ruleElement);

    /**
     * Updates RuleElement in database.
     * 
     * @param ruleElement
     *            A rule element to update in database.
     * @return updated rule element.
     */
    public RuleElement updateRuleElement(RuleElement ruleElement);

    /**
     * Finds RuleElement in database.
     * 
     * @param id
     *            ID of Rule Element to find in database.
     * @return found rule element or null if element not found.
     */
    public RuleElement lookupRuleElement(long id);

    /**
     * Deletes RuleElement from database.
     * 
     * @param ruleElement
     *            A rule element to delete from database.
     * @return true if element deleted.
     */
    public boolean deleteRuleElement(RuleElement ruleElement);
}
