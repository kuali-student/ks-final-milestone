package org.kuali.student.rules.BRMSCore;

import org.springframework.orm.jpa.support.JpaDaoSupport;

public class RulePropositionDAOImpl extends JpaDaoSupport implements RulePropositionDAO {

	@Override
	public RuleProposition createRuleProposition(RuleProposition ruleProposition) {
		super.getJpaTemplate().persist(ruleProposition);
		return ruleProposition;
	}

	@Override
	public RuleProposition updateRuleProposition(RuleProposition ruleProposition) {
		super.getJpaTemplate().merge(ruleProposition);
		return ruleProposition;
	}

	@Override
	public RuleProposition lookupRuleProposition(long id) {
		return super.getJpaTemplate().find(RuleProposition.class, id);
	}

    @Override
    public boolean deleteRuleProposition(RuleProposition ruleProposition) {
    	super.getJpaTemplate().remove(ruleProposition);
        return true; //until I know better what needs to happen
    }
}
