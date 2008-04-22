package org.kuali.student.rules.BRMSCore;

/**
 * Defines DAO operations for Functional Business Rule
 * 
 * @author Zdenek Zraly (zdenek.zraly@ubc.ca)
 */
public interface FunctionalBusinessRuleDAO {

    /**
     * Creates FunctionalBusinessRule in database.
     * 
     * @param rule
     *            A business rule to persist in database.
     * @return persisted rule.
     */
    public FunctionalBusinessRule createBusinessRule(FunctionalBusinessRule rule);

    /**
     * Updates FunctionalBusinessRule in database.
     * 
     * @param rule
     *            A business rule to update in database.
     * @return rule used for update.
     */
    public FunctionalBusinessRule updateBusinessRule(FunctionalBusinessRule rule);

    /**
     * Delete FunctionalBusinessRule from database.
     * 
     * @param rule
     *            A business rule to delete from database.
     * @return true on success
     */
    public boolean deleteBusinessRule(FunctionalBusinessRule rule);

    /**
     * Finds FunctionalBusinessRule in database.
     * 
     * @param id
     *            ID of a business rule to locate in database
     * @return found functional business rule or null if element not found.
     */
    public FunctionalBusinessRule lookupBusinessRule(long id);

    /**
     * Finds FunctionalBusinessRule in database.
     * 
     * @param ruleIdentifier
     *            ID of a business rule to locate in database.
     * @return found functional business rule or null if element not found.
     */
    public FunctionalBusinessRule lookupBusinessRuleID(String ruleIdentifier);

}
