package org.kuali.student.rules.translators;

import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.translators.exceptions.RuleSetTranslatorException;

public interface RuleSetTranslator {
	/**
	 * Translates a functional business rule into a rule set.
	 * 
	 * @param businessRule A functional business rule
	 * @return A rule set
	 * @throws RuleSetTranslatorException Thrown if translating a rule set fails
	 */
    public RuleSet translate(BusinessRuleInfoDTO businessRule) throws RuleSetTranslatorException;
}
