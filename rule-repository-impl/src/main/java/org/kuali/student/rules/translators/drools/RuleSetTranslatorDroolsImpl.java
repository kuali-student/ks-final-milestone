/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.translators.drools;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.drools.repository.StateItem;
import org.kuali.student.rules.internal.common.runtime.ast.Function;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.repository.drools.rule.DroolsConstants;
import org.kuali.student.rules.repository.drools.rule.RuleFactory;
import org.kuali.student.rules.repository.drools.rule.RuleSetFactory;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.translators.RuleSetTranslator;
import org.kuali.student.rules.translators.RuleSetValidator;
import org.kuali.student.rules.translators.RuleSetVerificationResult;
import org.kuali.student.rules.translators.exceptions.RuleSetTranslatorException;
import org.kuali.student.rules.util.CurrentDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class generates Drools rules source code from functional business rules.
 */
public class RuleSetTranslatorDroolsImpl implements RuleSetTranslator {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleSetTranslatorDroolsImpl.class);
    
    private final static RuleSetFactory ruleSetFactory = RuleSetFactory.getInstance();

    private static final String VELOCITY_RULE_TEMPLATE1_INIT = "velocity-templates/org/kuali/student/rules/brms/translators/drools/RuleTemplate1Init-v2.vm";
    private static final String VELOCITY_RULE_TEMPLATE2 = "velocity-templates/org/kuali/student/rules/brms/translators/drools/RuleTemplate2-v2.vm";

    private static final String PACKAGE_PREFIX = "org.kuali.student.rules.";

    private final static String INVALID_CHARACTERS_REGEX = "[^a-zA-Z0-9]";
    private final static String VALID_RULE_NAME_REGEX = "[a-zA-Z_][a-zA-Z0-9_]*";
    
    private RuleSetValidator ruleSetValidator;
    
    public RuleSetTranslatorDroolsImpl() {
    }

    /**
     * Sets the rule set validator.
     * 
     * @param ruleSetValidator Rule set validator
     */
	public void setRuleSetValidator(RuleSetValidator ruleSetValidator) {
		this.ruleSetValidator = ruleSetValidator;
	}

	/**
	 * Translates a functional business rule into a rule set.
	 * 
	 * @param businessRule A functional business rule
	 * @return A rule set
	 * @throws RuleSetTranslatorException Thrown if translating a rule set fails
	 */
    public synchronized RuleSet translate(BusinessRuleInfoDTO businessRule) throws RuleSetTranslatorException {
    	RuleSet ruleSet = null;
    	String ruleSetName = getRuleSetName(businessRule);
    	if (businessRule.getCompiledId() != null && !businessRule.getCompiledId().trim().isEmpty()) {
        	ruleSet = ruleSetFactory.createRuleSet(businessRule.getCompiledId(), ruleSetName, 1);
    	} else {
        	ruleSet = ruleSetFactory.createRuleSet(ruleSetName, businessRule.getDescription());
    	}
        addHeader(ruleSet);
        parseRule(ruleSet, businessRule);
        if (logger.isDebugEnabled()) {
        	logger.debug("BusinessRuleInfoDTO: compiledId: " 
        			+ businessRule.getCompiledId()
        			+ "\n" + ruleSet.getContent());
        }
        verifyRule(ruleSet);
        return ruleSet;
    }
    
    /**
     * Gets the proper ruleset/package name from the <code>businessRule</code>.
     * 
     * @param businessRule Business rule
     * @return Rule set name
     */
    public static String getRuleSetName(BusinessRuleInfoDTO businessRule) {
    	String businessRuleName = removeInvalidCharacters(businessRule.getOrigName());
    	if(!isValidRuleName(businessRuleName)) {
    		throw new RuleSetTranslatorException("Invalid rule name. " +
    				"Rule name must must start with a letter. Original rule name: '" + 
    				businessRule.getOrigName() + "'. Adjusted rule name: '" + businessRuleName + "'");
    	}
    	String businessRuleTypeKey = removeInvalidCharacters(businessRule.getBusinessRuleTypeKey());
    	if(!isValidRuleName(businessRuleTypeKey)) {
    		throw new RuleSetTranslatorException("Invalid rule type key. " +
    				"Rule type key must must start with a letter. Original rule type key: '" + 
    				businessRule.getBusinessRuleTypeKey() + "'. Adjusted rule type key: '" + businessRuleTypeKey + "'");
    	}
    	String businessRuleId = removeInvalidCharacters(businessRule.getBusinessRuleId());
    	String name = PACKAGE_PREFIX + businessRuleTypeKey + "_" + businessRuleId + "_" + businessRuleName;
    	return name;
    }
    
    private void verifyRule(RuleSet ruleSet) throws RuleSetTranslatorException {
        RuleSetVerificationResult result = ruleSetValidator.verify(ruleSet);
        if (!result.isRuleSetValid()) {
        	logger.warn("*****  Invalid Rule!  *****\n"+ruleSet.getContent()+"\n********************");
        	throw new RuleSetTranslatorException(result.getMessage());
        }
    }

    private void parseRule(RuleSet ruleSet, BusinessRuleInfoDTO businessRule) {
        checkBusinessRule(businessRule);
        
        String anchor = businessRule.getAnchorValue();
        String ruleName = removeInvalidCharacters(businessRule.getOrigName());
        String ruleDescription = businessRule.getDescription();
        String functionString = BusinessRuleUtil.createAdjustedRuleFunctionString(businessRule);
        Map<String, RulePropositionDTO> propositionMap = BusinessRuleUtil.getRulePropositions(businessRule);
		Date effectiveStartTime = businessRule.getEffectiveStartTime();
		Date effectiveEndTime = businessRule.getEffectiveEndTime();
        generateRules(anchor, ruleName, ruleDescription, functionString, 
        		propositionMap, ruleSet, effectiveStartTime, effectiveEndTime);
    }

    public void generateRules(String anchor, String ruleName, 
    						  String ruleDescription, String functionString,
    						  Map<String, RulePropositionDTO> functionalPropositionMap, 
    						  RuleSet ruleSet,
    						  Date effectiveStartTime,
    						  Date effectiveEndTime) {
        String initRuleName = ruleName+"_INIT";
        String uuid = UUID.randomUUID().toString();
        String rule1Source = generateRule1InitSourceCode(anchor, initRuleName, uuid, 
        		functionString, functionalPropositionMap, 
        		effectiveStartTime, effectiveEndTime);
        addRule(initRuleName, ruleDescription, ruleSet, rule1Source, effectiveStartTime, effectiveEndTime);

        String rule2Source = generateRule2SourceCode(anchor, ruleName, uuid,
        		functionString, functionalPropositionMap);
        addRule(ruleName, ruleDescription, ruleSet, rule2Source, effectiveStartTime, effectiveEndTime);
    }

    private String generateRule1InitSourceCode(String anchor, 
    										   String ruleName,
    										   String uuid,
    										   String functionString,
    										   Map<String, RulePropositionDTO> functionalPropositionMap,
    										   Date effectiveStartTime,
    										   Date effectiveEndTime) {
    	removeInvalidCharacters(anchor);
        Function function = new Function(functionString);

        CurrentDateTime date = new CurrentDateTime();
        long effStartDate = date.getDateAsLong(effectiveStartTime);
        long effEndDate = date.getDateAsLong(effectiveEndTime);
        
        // Create the final composite rule for the function
        List<String> symbols = function.getSymbols();
        Map<String, Object> velocityContextMap = new HashMap<String, Object>();
        velocityContextMap.put("anchor", anchor);
        velocityContextMap.put("ruleName", ruleName);
        velocityContextMap.put("propositionMap", functionalPropositionMap);
        velocityContextMap.put("functionSymbols", symbols);
        velocityContextMap.put("functionString", functionString);
        velocityContextMap.put("effectiveStartTime", effStartDate);
        velocityContextMap.put("effectiveEndTime", effEndDate);
        velocityContextMap.put("factUtil", new FactUtil());
        velocityContextMap.put("uuid", uuid);

        RuleTemplate velocityRuleTemplate = new RuleTemplate();
        return velocityRuleTemplate.process(VELOCITY_RULE_TEMPLATE1_INIT, velocityContextMap);
    }

    private String generateRule2SourceCode(String anchor, 
    									   String ruleName, 
    									   String uuid,
    									   String functionString,
    									   Map<String, RulePropositionDTO> functionalPropositionMap) {
    	removeInvalidCharacters(anchor);
        Function function = new Function(functionString);

        // Create the final composite rule for the function
        List<String> symbols = function.getSymbols();
        Map<String, Object> velocityContextMap = new HashMap<String, Object>();
        velocityContextMap.put("anchor", anchor);
        velocityContextMap.put("ruleName", ruleName);
        velocityContextMap.put("propositionMap", functionalPropositionMap);
        velocityContextMap.put("functionSymbols", symbols);
        velocityContextMap.put("functionString", functionString);
        velocityContextMap.put("uuid", uuid);

        RuleTemplate velocityRuleTemplate = new RuleTemplate();
        return velocityRuleTemplate.process(VELOCITY_RULE_TEMPLATE2, velocityContextMap);
    }
    
    public RuleSet createRuleSet(String anchor, String packageName, String description, 
    							 String ruleName, String functionString,
    							 Map<String, RulePropositionDTO> functionalPropositionMap,
    							 Date effectiveStartTime, Date effectiveEndTime) {
        RuleSet ruleSet = ruleSetFactory.createRuleSet(packageName, description);
        addHeader(ruleSet);
        generateRules(anchor, ruleName, "A rule description", functionString, 
        		functionalPropositionMap, ruleSet, effectiveStartTime, effectiveEndTime);
        return ruleSet;
    }

    public void addHeader(RuleSet ruleSet) {
        ruleSet.addHeader("import java.util.*");
        ruleSet.addHeader("import java.math.BigDecimal");
        ruleSet.addHeader("import org.slf4j.Logger;");
		ruleSet.addHeader("import org.slf4j.LoggerFactory;");
        ruleSet.addHeader("import org.kuali.student.rules.internal.common.entity.*");
        ruleSet.addHeader("import org.kuali.student.rules.internal.common.statement.propositions.*");
        ruleSet.addHeader("import org.kuali.student.rules.internal.common.statement.yvf.*");
        ruleSet.addHeader("import org.kuali.student.rules.rulemanagement.dto.*");
        ruleSet.addHeader("import org.kuali.student.rules.util.FactContainer");
        ruleSet.addHeader("import org.kuali.student.rules.util.FactContainer.State");
        ruleSet.addHeader("import org.kuali.student.rules.util.CurrentDateTime");
        ruleSet.addHeader("import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil");
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
    private void addRule(String name, String description, RuleSet ruleSet, 
    		String ruleSourceCode, Date effectiveStartTime, Date effectiveEndTime) {
        String category = null;
        Rule rule = RuleFactory.getInstance().createDroolsRule(name, description, category, ruleSourceCode,
                                                               DroolsConstants.FORMAT_DRL);
        Calendar effectiveDate = Calendar.getInstance();
        Calendar expiryDate = Calendar.getInstance();
        
        effectiveDate.setTime(effectiveStartTime);
        expiryDate.setTime(effectiveEndTime);
        
        rule.setEffectiveDate(effectiveDate);
        rule.setExpiryDate(expiryDate);
        rule.setStatus(StateItem.DRAFT_STATE_NAME);
        ruleSet.addRule(rule);
    }

    private void checkBusinessRule(BusinessRuleInfoDTO businessRule) {
    	if(businessRule == null) {
            throw new RuleSetTranslatorException("BusinessRuleInfoDTO is null");
    	}
    }

    private static String removeInvalidCharacters(String s) {
    	return (s == null ? "" : s.replaceAll(INVALID_CHARACTERS_REGEX, ""));
    }
    
    private static boolean isValidRuleName(String s) {
    	return (s == null ? true : s.matches(VALID_RULE_NAME_REGEX));
    }

}
