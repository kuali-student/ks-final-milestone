package org.kuali.student.rules.BRMSCore.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kuali.student.rules.BRMSCore.dao.RulePropositionDAO;
import org.kuali.student.rules.BRMSCore.entity.RuleProposition;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Implements DAO interface for operations on Rule Proposition using Spring JPA
 * 
 * @author Zdenek Zraly (zdenek.zraly@ubc.ca)
 */
@Repository
public class RulePropositionDAOImpl extends JpaDaoSupport implements RulePropositionDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Persists RuleProposition.
     * 
     * @see org.kuali.student.rules.BRMSCore.dao.RulePropositionDAO#createRuleProposition(RuleProposition ruleProposition)
     */
    public RuleProposition createRuleProposition(RuleProposition ruleProposition) {
        em.persist(ruleProposition);
        return ruleProposition;
    }

    /**
     * Updates RuleProposition.
     * 
     * @see org.kuali.student.rules.BRMSCore.dao.RulePropositionDAO#updateRuleProposition(RuleProposition ruleProposition)
     */
    public RuleProposition updateRuleProposition(RuleProposition ruleProposition) {
        em.merge(ruleProposition);
        return ruleProposition;
    }

    /**
     * Searches RuleProposition. Returns null if Rule Proposition not found.
     * 
     * @see org.kuali.student.rules.BRMSCore.dao.RulePropositionDAO#lookupRuleProposition(RuleProposition ruleProposition)
     */
    public RuleProposition lookupRuleProposition(long id) {
        return em.find(RuleProposition.class, id);
    }

    /**
     * Deletes RuleProposition.
     * 
     * @see org.kuali.student.rules.BRMSCore.dao.RulePropositionDAO#deleteRuleProposition(RuleProposition ruleProposition)
     */
    public boolean deleteRuleProposition(RuleProposition ruleProposition) {
        em.remove(ruleProposition);
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
