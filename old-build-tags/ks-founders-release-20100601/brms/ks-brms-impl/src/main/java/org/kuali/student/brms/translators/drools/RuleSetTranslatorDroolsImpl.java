/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.translators.drools;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.drools.repository.StateItem;
import org.kuali.student.brms.internal.common.runtime.ast.BooleanFunction;
import org.kuali.student.brms.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.brms.internal.common.utils.FactUtil;
import org.kuali.student.brms.repository.drools.rule.DroolsConstants;
import org.kuali.student.brms.repository.drools.rule.RuleFactory;
import org.kuali.student.brms.repository.drools.rule.RuleSetFactory;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleSet;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.translators.RuleSetTranslator;
import org.kuali.student.brms.translators.RuleSetValidator;
import org.kuali.student.brms.translators.RuleSetVerificationResult;
import org.kuali.student.brms.translators.exceptions.RuleSetTranslatorException;
import org.kuali.student.brms.util.CurrentDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class generates Drools rules source code from functional business rules.
 */
public class RuleSetTranslatorDroolsImpl implements RuleSetTranslator {
    /** SLF4J logging framework */
    private final static Logger logger = LoggerFactory.getLogger(RuleSetTranslatorDroolsImpl.class);
    
    private final static RuleSetFactory ruleSetFactory = RuleSetFactory.getInstance();

    private final static String VELOCITY_RULE_TEMPLATE1 = "velocity-templates/org/kuali/student/brms/translators/drools/RuleTemplate1-Init.vm";
    private final static String VELOCITY_RULE_TEMPLATE2 = "velocity-templates/org/kuali/student/brms/translators/drools/RuleTemplate2-Rule.vm";
    private final static String VELOCITY_RULE_TEMPLATE3 = "velocity-templates/org/kuali/student/brms/translators/drools/RuleTemplate3-RuleExpiryDate.vm";

	private List<String> ruleTemplates = Arrays.asList(new String[] {
			VELOCITY_RULE_TEMPLATE1, 
			VELOCITY_RULE_TEMPLATE2,
			VELOCITY_RULE_TEMPLATE3 });
    	
    protected final static String PACKAGE_PREFIX = "org.kuali.student.brms.";

    protected final static String INVALID_CHARACTERS_REGEX = "[^a-zA-Z0-9]";
    protected final static String VALID_RULE_NAME_REGEX = "[a-zA-Z_][a-zA-Z0-9_]*";
    
    private RuleSetValidator ruleSetValidator;
    
    public RuleSetTranslatorDroolsImpl() {
    }

    /**
     * Gets all rule templates.
     * 
     * @return All rule templates
     */
	public List<String> getRuleTemplates() {
		return this.ruleTemplates;
	}

	/**
     * Sets rule templates.
	 * 
	 * @param ruleTemplates Rule templates
	 */
	public void setRuleTemplates(final List<String> ruleTemplates) {
		this.ruleTemplates = ruleTemplates;
	}

    /**
     * Sets the rule set validator.
     * 
     * @param ruleSetValidator Rule set validator
     */
	public void setRuleSetValidator(final RuleSetValidator ruleSetValidator) {
		this.ruleSetValidator = ruleSetValidator;
	}

	/**
	 * Translates a functional business rule into a rule set.
	 * 
	 * @param businessRule A functional business rule
	 * @return A rule set
	 * @throws RuleSetTranslatorException Thrown if translating a rule set fails
	 */
    public synchronized RuleSet translate(final BusinessRuleInfo businessRule) throws RuleSetTranslatorException {
    	if(businessRule == null) {
            throw new RuleSetTranslatorException("BusinessRuleInfo is null");
    	}
    	RuleSet ruleSet = null;
    	String ruleSetName = getRuleSetName(businessRule);
    	if (businessRule.getCompiledId() != null && !businessRule.getCompiledId().trim().isEmpty()) {
        	ruleSet = ruleSetFactory.createRuleSet(businessRule.getCompiledId(), ruleSetName, businessRule.getDesc(), 1);
    	} else {
        	ruleSet = ruleSetFactory.createRuleSet(ruleSetName, businessRule.getDesc());
    	}

    	generateRules(ruleSet, businessRule);
        if (logger.isDebugEnabled()) {
        	logger.debug("BusinessRuleInfo: compiledId: " 
        			+ businessRule.getCompiledId()
        			+ "\n" + ruleSet.getContent());
        }
        validateRuleSet(ruleSet);
        return ruleSet;
    }
    
    /**
     * Gets the proper ruleset/package name from the <code>businessRule</code>.
     * 
     * @param businessRule Business rule
     * @return Rule set name
     */
    public static String getRuleSetName(final BusinessRuleInfo businessRule) {
    	String businessRuleName = removeInvalidCharacters(businessRule.getName());
    	if(!isValidRuleName(businessRuleName)) {
    		throw new RuleSetTranslatorException("Invalid rule name. " +
    				"Rule name must must start with a letter. Original rule name: '" + 
    				businessRule.getName() + "'. Adjusted rule name: '" + businessRuleName + "'");
    	}
    	String businessRuleTypeKey = removeInvalidCharacters(businessRule.getType());
    	if(!isValidRuleName(businessRuleTypeKey)) {
    		throw new RuleSetTranslatorException("Invalid rule type key. " +
    				"Rule type key must must start with a letter. Original rule type key: '" + 
    				businessRule.getType() + "'. Adjusted rule type key: '" + businessRuleTypeKey + "'");
    	}
    	String businessRuleId = removeInvalidCharacters(businessRule.getId());
    	String name = PACKAGE_PREFIX + businessRuleTypeKey + "_" + businessRuleId + "_" + businessRuleName;
    	return name;
    }
    
    /**
     * Validates than a rule set is valid.
     * 
     * @param ruleSet Rule set 
     * @throws RuleSetTranslatorException Thrown if rule set is invalid
     */
    private void validateRuleSet(RuleSet ruleSet) throws RuleSetTranslatorException {
        RuleSetVerificationResult result = ruleSetValidator.verify(ruleSet);
        if (!result.isRuleSetValid()) {
        	logger.warn("*****  Invalid Rule!  *****\n"+ruleSet.getContent()+"\n********************");
        	throw new RuleSetTranslatorException(result.getMessage());
        }
    }

    /**
     * Generates Drools rule source code using the velocity templates.
     *  
     * @param anchor Rule anchor
     * @param anchorTypeKey Rule anchor type key
     * @param ruleName Rule name
     * @param ruleDescription Rule description
     * @param functionString Rule function String (e.g. (A*B)+C))
     * @param functionalPropositionMap Rule proposition map
     * @param ruleSet Rule set
     * @param effectiveStartTime Rule effective start date
     * @param effectiveEndTime Rule effective end date
     */
    private void generateRules(RuleSet ruleSet, BusinessRuleInfo businessRule) {
        String ruleName = removeInvalidCharacters(businessRule.getName());
    	String uuid = UUID.randomUUID().toString();
        int i=1;
        addHeader(ruleSet);
        for(String template : ruleTemplates) {
        	// Rule names must be unique
        	String name = ruleName + "_T" + i++;
        	// Generate Drools rule source code
        	String ruleSource = generateRuleSourceCode(businessRule, name, uuid, template);
            addRule(name, businessRule, ruleSet, ruleSource);
        }
    }

    /**
     * Generates Drools rule source code.
     * 
     * @param businessRule Business rule
     * @param ruleName Rule name
     * @param uuid Rule uuid
     * @param template Rule template
     * @return Drools source code
     */
	private String generateRuleSourceCode(BusinessRuleInfo businessRule, String ruleName, String uuid, String template) {
        String functionString = BusinessRuleUtil.createAdjustedRuleFunctionString(businessRule);
		//removeInvalidCharacters(anchor);
		BooleanFunction function = new BooleanFunction(functionString);

		CurrentDateTime date = new CurrentDateTime();
		long effectiveStartDate = date.getDateAsLong(businessRule.getEffectiveDate());
		long effectiveEndDate = date.getDateAsLong(businessRule.getExpirationDate());

		// Create the final composite rule for the function
		List<String> symbols = function.getSymbols();
		Map<String, Object> velocityContextMap = new HashMap<String, Object>();
		velocityContextMap.put("ruleId", businessRule.getId());
		velocityContextMap.put("ruleName", ruleName);
		velocityContextMap.put("anchor", businessRule.getAnchor());
		velocityContextMap.put("anchorTypeKey", businessRule.getAnchorTypeKey());
		velocityContextMap.put("propositionMap", BusinessRuleUtil.getRulePropositions(businessRule));
		velocityContextMap.put("functionSymbols", symbols);
		velocityContextMap.put("functionString", functionString);
		velocityContextMap.put("effectiveStartTime", effectiveStartDate);
		velocityContextMap.put("effectiveEndTime", effectiveEndDate);
		velocityContextMap.put("factUtil", new FactUtil());
		velocityContextMap.put("uuid", uuid);

		RuleTemplate velocityRuleTemplate = new RuleTemplate();
		return velocityRuleTemplate.process(template, velocityContextMap);
	}

    /**
     * Adds Drools header information.
     * 
     * @param ruleSet Rule set
     */
	private void addHeader(final RuleSet ruleSet) {
        ruleSet.addHeader("import java.util.*;");
        ruleSet.addHeader("\n");
        ruleSet.addHeader("import java.math.BigDecimal;");
        ruleSet.addHeader("\n");
        ruleSet.addHeader("import org.slf4j.Logger;");
        ruleSet.addHeader("\n");
		ruleSet.addHeader("import org.slf4j.LoggerFactory;");
        ruleSet.addHeader("\n");
        ruleSet.addHeader("import org.kuali.student.brms.internal.common.entity.*;");
        ruleSet.addHeader("\n");
        ruleSet.addHeader("import org.kuali.student.brms.internal.common.statement.propositions.*;");
        ruleSet.addHeader("\n");
        ruleSet.addHeader("import org.kuali.student.brms.internal.common.statement.propositions.rules.*;");
        ruleSet.addHeader("\n");
        ruleSet.addHeader("import org.kuali.student.brms.internal.common.statement.report.*;");
        ruleSet.addHeader("\n");
        ruleSet.addHeader("import org.kuali.student.brms.rulemanagement.dto.*;");
        ruleSet.addHeader("\n");
        ruleSet.addHeader("import org.kuali.student.brms.util.FactContainer;");
        ruleSet.addHeader("\n");
        ruleSet.addHeader("import org.kuali.student.brms.util.FactContainer.State;");
        ruleSet.addHeader("\n");
        ruleSet.addHeader("import org.kuali.student.brms.util.CurrentDateTime;");
        ruleSet.addHeader("\n");
        ruleSet.addHeader("import org.kuali.student.brms.internal.common.utils.BusinessRuleUtil;");
        ruleSet.addHeader("\n");
    }

    /**
     * Adds a rule to a rule set.
     * 
     * @param businessRule
     *            Business rule to get name and description from
     * @param ruleSet
     *            Rule set to a rule to
     * @param ruleSourceCode
     *            Rule source code
     */
    private void addRule(String name, BusinessRuleInfo businessRule, RuleSet ruleSet, String ruleSourceCode) {
    	String category = null;
        Rule rule = RuleFactory.getInstance().createDroolsRule(name, businessRule.getDesc(),
        		category, ruleSourceCode, DroolsConstants.FORMAT_DRL);
        Calendar effectiveDate = Calendar.getInstance();
        Calendar expiryDate = Calendar.getInstance();
        
        effectiveDate.setTime(businessRule.getEffectiveDate());
        expiryDate.setTime(businessRule.getExpirationDate());
        
        rule.setEffectiveDate(effectiveDate);
        rule.setExpiryDate(expiryDate);
        rule.setStatus(StateItem.DRAFT_STATE_NAME);
        ruleSet.addRule(rule);
    }

    /**
     * Removes invalid characters from string.
     * 
     * @param s String to invalid characters from
     * @return String a valid characters
     */
    private static String removeInvalidCharacters(String s) {
    	return (s == null ? "" : s.replaceAll(INVALID_CHARACTERS_REGEX, ""));
    }
    
    /**
     * Determines whether a rule name is valid.
     * 
     * @param s Rule name
     * @return True if rule name is valid; otherwise false
     */
    private static boolean isValidRuleName(String s) {
    	return (s == null ? true : s.matches(VALID_RULE_NAME_REGEX));
    }
}
