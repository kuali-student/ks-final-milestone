package org.kuali.student.rules.BRMSCore;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * Implements DAO interface for operations on Functional Business Rule entity using Spring JPA
 * 
 * @author Zdenek Zraly (zdenek.zraly@ubc.ca)
 */
// @Repository
public class FunctionalBusinessRuleDAOImpl extends JpaDaoSupport implements FunctionalBusinessRuleDAO {

    /**
     * Persists FunctionalBusinessRule in database.
     * 
     * @see org.kuali.student.rules.BRMSCore.FunctionalBusinessRuleDAO#createBusinessRule(FunctionalBusinessRule rule)
     */
    public FunctionalBusinessRule createBusinessRule(FunctionalBusinessRule rule) {
        super.getJpaTemplate().persist(rule);
        return rule;
    }

    /**
     * Updates FunctionalBusinessRule in database.
     * 
     * @see org.kuali.student.rules.BRMSCore.FunctionalBusinessRuleDAO#updateBusinessRule(FunctionalBusinessRule rule)
     */
    public FunctionalBusinessRule updateBusinessRule(FunctionalBusinessRule rule) {
        super.getJpaTemplate().merge(rule);
        return rule;
    }

    /**
     * Deletes FunctionalBusinessRule from database.
     * 
     * @see org.kuali.student.rules.BRMSCore.FunctionalBusinessRuleDAO#deleteBusinessRule(FunctionalBusinessRule rule)
     */
    public boolean deleteBusinessRule(FunctionalBusinessRule ruleSet) {
        super.getJpaTemplate().remove(ruleSet);
        return true; // until I know better what needs to happen
    }

    /**
     * Finds FunctionalBusinessRule in database.
     * 
     * @see org.kuali.student.rules.BRMSCore.FunctionalBusinessRuleDAO#lookupBusinessRule(long id)
     */
    public FunctionalBusinessRule lookupBusinessRule(long id) {
        return super.getJpaTemplate().find(FunctionalBusinessRule.class, id);
    }

    /**
     * Finds FunctionalBusinessRule in database.
     * 
     * @see org.kuali.student.rules.BRMSCore.FunctionalBusinessRuleDAO#lookupBusinessRule(String ruleIdentifier)
     */
    @SuppressWarnings(value = {"unchecked"})
    public FunctionalBusinessRule lookupBusinessRuleID(String ruleIdentifier) {

        List<FunctionalBusinessRule> result = super.getJpaTemplate().findByNamedQuery("FunctionalBusinessRule.findByRuleID", ruleIdentifier);

        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }
}
