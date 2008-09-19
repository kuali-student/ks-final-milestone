package org.kuali.student.rules.translators;

import org.kuali.student.rules.repository.exceptions.RuleSetTranslatorException;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;

public interface RuleSetTranslator {
	/**
	 * 
	 * @param container
	 * @return
	 * @throws RuleSetTranslatorException
	 */
    public RuleSet translate(BusinessRuleContainerDTO container) throws RuleSetTranslatorException;
}
