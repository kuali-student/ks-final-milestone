package org.kuali.student.rules.BRMSCore;

public interface RuleElementDAO {
	public RuleElement createRuleElement(RuleElement ruleElement);
	public RuleElement updateRuleElement(RuleElement ruleElement);
	public RuleElement lookupRuleElement(long id);
	public boolean deleteRuleElement(RuleElement ruleElement);
}
