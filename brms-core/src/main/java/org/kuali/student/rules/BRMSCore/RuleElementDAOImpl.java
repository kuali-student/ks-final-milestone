package org.kuali.student.rules.BRMSCore;

import org.springframework.orm.jpa.support.JpaDaoSupport;

public class RuleElementDAOImpl extends JpaDaoSupport implements RuleElementDAO {

	public RuleElement createRuleElement(RuleElement ruleElement) {
		super.getJpaTemplate().persist(ruleElement);
		return ruleElement;
	}

	public RuleElement updateRuleElement(RuleElement ruleElement) {
		super.getJpaTemplate().merge(ruleElement);
		return ruleElement;
	}

	public RuleElement lookupRuleElement(long id) {
		return super.getJpaTemplate().find(RuleElement.class, id);
	}

    public boolean deleteRuleElement(RuleElement ruleElement) {
    	super.getJpaTemplate().remove(ruleElement);
        return true; //until I know better what needs to happen
    }
}
