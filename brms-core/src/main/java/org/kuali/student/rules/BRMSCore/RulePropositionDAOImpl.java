package org.kuali.student.rules.BRMSCore;

import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 * Implements DAO interface for operations on Rule Proposition using Spring JPA
 * @author Zdenek Zraly (zdenek.zraly@ubc.ca)
 */
public class RulePropositionDAOImpl extends JpaDaoSupport implements RulePropositionDAO {

    /**
     * Persists RuleProposition.
     * @see org.kuali.student.rules.BRMSCore.RulePropositionDAO#createRuleProposition(RuleProposition ruleProposition)
     */
	public RuleProposition createRuleProposition(RuleProposition ruleProposition) {
		super.getJpaTemplate().persist(ruleProposition);
		return ruleProposition;
	}

    /**
     * Updates RuleProposition.
     * @see org.kuali.student.rules.BRMSCore.RulePropositionDAO#updateRuleProposition(RuleProposition ruleProposition)
     */
	public RuleProposition updateRuleProposition(RuleProposition ruleProposition) {
		super.getJpaTemplate().merge(ruleProposition);
		return ruleProposition;
	}

    /**
     * Searches RuleProposition. Returns null if Rule Proposition not found.
     * @see org.kuali.student.rules.BRMSCore.RulePropositionDAO#lookupRuleProposition(RuleProposition ruleProposition)
     */
	public RuleProposition lookupRuleProposition(long id) {
		return super.getJpaTemplate().find(RuleProposition.class, id);
	}

    /**
     * Deletes RuleProposition.
     * @see org.kuali.student.rules.BRMSCore.RulePropositionDAO#deleteRuleProposition(RuleProposition ruleProposition)
     */
    public boolean deleteRuleProposition(RuleProposition ruleProposition) {
    	super.getJpaTemplate().remove(ruleProposition);
        return true; //until I know better what needs to happen
    }
}
