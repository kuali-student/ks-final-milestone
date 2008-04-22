package org.kuali.student.rules.BRMSCore;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Implements DAO interface for operations on Rule Element using Spring JPA
 * 
 * @author Zdenek Zraly (zdenek.zraly@ubc.ca)
 */
@Repository
public class RuleElementDAOImpl extends JpaDaoSupport implements RuleElementDAO {

    /**
     * Persists RuleElement in database.
     * 
     * @see org.kuali.student.rules.BRMSCore.RuleElementDAO#createRuleElement(RuleElement ruleElement)
     */
    public RuleElement createRuleElement(RuleElement ruleElement) {
        super.getJpaTemplate().persist(ruleElement);
        return ruleElement;
    }

    /**
     * Updates RuleElement in database.
     * 
     * @see org.kuali.student.rules.BRMSCore.RuleElementDAO#updateRuleElement(RuleElement ruleElement)
     */
    public RuleElement updateRuleElement(RuleElement ruleElement) {
        super.getJpaTemplate().merge(ruleElement);
        return ruleElement;
    }

    /**
     * Finds RuleElement in database.
     * 
     * @see org.kuali.student.rules.BRMSCore.RuleElementDAO#lookupRuleElement(RuleElement ruleElement)
     */
    public RuleElement lookupRuleElement(long id) {
        return super.getJpaTemplate().find(RuleElement.class, id);
    }

    /**
     * Deletes RuleElement from database.
     * 
     * @see org.kuali.student.rules.BRMSCore.RuleElementDAO#deleteRuleElement(RuleElement ruleElement)
     */
    public boolean deleteRuleElement(RuleElement ruleElement) {
        super.getJpaTemplate().remove(ruleElement);
        return true; // until I know better what needs to happen
    }
}
