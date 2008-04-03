package org.kuali.student.rules.BRMSCore;

public interface FunctionalBusinessRuleDAO {
	public FunctionalBusinessRule createBusinessRule(FunctionalBusinessRule ruleSet);
	public FunctionalBusinessRule updateBusinessRule(FunctionalBusinessRule ruleSet);
	public FunctionalBusinessRule lookupBusinessRule(long id);
	public FunctionalBusinessRule lookupBusinessRuleID(String ruleIdentifier);
	public boolean deleteBusinessRuleSet(FunctionalBusinessRule ruleSet);
}
