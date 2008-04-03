package org.kuali.student.rules.BRMSCore;

public interface RulePropositionDAO {
	public RuleProposition createRuleProposition(RuleProposition ruleProposition);
	public RuleProposition updateRuleProposition(RuleProposition ruleProposition);
	public RuleProposition lookupRuleProposition(long id);
	public boolean deleteRuleProposition(RuleProposition ruleProposition);
}
