package org.kuali.student.rules.translators;

import org.kuali.student.rules.repository.exceptions.RuleSetTranslatorException;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;

public interface RuleSetTranslator {
	/**
	 * Translates a container of business rules into a rule set.
	 * 
	 * @param container business rule container
	 * @return A rule set
	 * @throws RuleSetTranslatorException Thrown if translating a rule set fails
	 */
    public RuleSet translate(BusinessRuleContainerDTO container) throws RuleSetTranslatorException;
}
