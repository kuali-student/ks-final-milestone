package org.kuali.student.rules.translators;

import java.util.List;

import org.kuali.student.rules.repository.exceptions.RuleSetTranslatorException;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;

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
