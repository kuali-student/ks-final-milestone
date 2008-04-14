package org.kuali.student.rules.BRMSCore;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class FunctionalBusinessRuleDAOImpl extends JpaDaoSupport implements FunctionalBusinessRuleDAO {

	public FunctionalBusinessRule createBusinessRule(FunctionalBusinessRule rule) {
		super.getJpaTemplate().persist(rule);
		return rule;
	}

	public FunctionalBusinessRule updateBusinessRule(FunctionalBusinessRule rule) {
		super.getJpaTemplate().merge(rule);
		return rule;
	}

	public FunctionalBusinessRule lookupBusinessRule(long id) {
		return super.getJpaTemplate().find(FunctionalBusinessRule.class, id);
	}

    public boolean deleteBusinessRuleSet(FunctionalBusinessRule ruleSet) {
    	super.getJpaTemplate().remove(ruleSet);
        return true; //until I know better what needs to happen
    }

    @SuppressWarnings(value={"unchecked"})
	public FunctionalBusinessRule lookupBusinessRuleID(String ruleIdentifier) {
    	    	   	
		List<FunctionalBusinessRule> result = super.getJpaTemplate().findByNamedQuery("FunctionalBusinessRule.findByRuleID", ruleIdentifier);
		
		if (result.size() > 0) {
			return (FunctionalBusinessRule)result.get(0);
		} else {
			return null;
		}		    				
    }
}
