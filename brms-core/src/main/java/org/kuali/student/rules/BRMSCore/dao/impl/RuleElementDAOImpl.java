package org.kuali.student.rules.BRMSCore.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kuali.student.rules.BRMSCore.dao.RuleElementDAO;
import org.kuali.student.rules.BRMSCore.entity.RuleElement;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Implements DAO interface for operations on Rule Element using Spring JPA
 * 
 * @author Zdenek Zraly (zdenek.zraly@ubc.ca)
 */
@Repository
public class RuleElementDAOImpl extends JpaDaoSupport implements RuleElementDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persists RuleElement in database.
     * 
     * @see org.kuali.student.rules.BRMSCore.dao.RuleElementDAO#createRuleElement(RuleElement ruleElement)
     */
    public RuleElement createRuleElement(RuleElement ruleElement) {
        em.persist(ruleElement);
        return ruleElement;
    }

    /**
     * Updates RuleElement in database.
     * 
     * @see org.kuali.student.rules.BRMSCore.dao.RuleElementDAO#updateRuleElement(RuleElement ruleElement)
     */
    public RuleElement updateRuleElement(RuleElement ruleElement) {
        em.merge(ruleElement);
        return ruleElement;
    }

    /**
     * Finds RuleElement in database.
     * 
     * @see org.kuali.student.rules.BRMSCore.dao.RuleElementDAO#lookupRuleElement(RuleElement ruleElement)
     */
    public RuleElement lookupRuleElement(long id) {
        return em.find(RuleElement.class, id);
    }

    /**
     * Deletes RuleElement from database.
     * 
     * @see org.kuali.student.rules.BRMSCore.dao.RuleElementDAO#deleteRuleElement(RuleElement ruleElement)
     */
    public boolean deleteRuleElement(RuleElement ruleElement) {
        em.remove(ruleElement);
        return true; // until I know better what needs to happen
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @param em
     *            the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
