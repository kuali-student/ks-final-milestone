package org.kuali.student.brms.repository;

import java.util.List;

public interface RuleSet extends Item
{
	public List<Rule> getRules();

	public byte[] getCompiledRuleSet();

	public Object getCompiledRuleSetObject();

	public boolean isSnapshot();
}
