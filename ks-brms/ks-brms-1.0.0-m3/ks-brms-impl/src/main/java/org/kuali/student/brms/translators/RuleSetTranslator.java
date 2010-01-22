package org.kuali.student.brms.translators;

import org.kuali.student.brms.repository.rule.RuleSet;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.translators.exceptions.RuleSetTranslatorException;

public interface RuleSetTranslator {
	/**
	 * Translates a functional business rule into a rule set.
	 * 
	 * @param businessRule A functional business rule
	 * @return A rule set
	 * @throws RuleSetTranslatorException Thrown if translating a rule set fails
	 */
    public RuleSet translate(BusinessRuleInfo businessRule) throws RuleSetTranslatorException;
}
