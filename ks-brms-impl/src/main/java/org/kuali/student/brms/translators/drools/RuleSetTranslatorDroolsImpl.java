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
package org.kuali.student.brms.translators.drools;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.translators.RuleSetTranslator;
import org.kuali.student.brms.translators.RuleSetValidator;
import org.kuali.student.brms.translators.RuleSetVerificationResult;
import org.kuali.student.brms.translators.exceptions.RuleSetTranslatorException;
import org.kuali.student.brms.util.CurrentDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class generates Drools rules source code from functional business rules.
 * TODO: This class needs to be re-factored.
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

	public List<String> getRuleTemplates() {
		return this.ruleTemplates;
	}

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
    	RuleSet ruleSet = null;
    	String ruleSetName = getRuleSetName(businessRule);
    	if (businessRule.getCompiledId() != null && !businessRule.getCompiledId().trim().isEmpty()) {
        	ruleSet = ruleSetFactory.createRuleSet(businessRule.getCompiledId(), ruleSetName, businessRule.getDesc(), 1);
    	} else {
        	ruleSet = ruleSetFactory.createRuleSet(ruleSetName, businessRule.getDesc());
    	}
        addHeader(ruleSet);
        parseRule(ruleSet, businessRule);
        if (logger.isDebugEnabled()) {
        	logger.debug("BusinessRuleInfo: compiledId: " 
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
    
    private void verifyRule(RuleSet ruleSet) throws RuleSetTranslatorException {
        RuleSetVerificationResult result = ruleSetValidator.verify(ruleSet);
        if (!result.isRuleSetValid()) {
        	logger.warn("*****  Invalid Rule!  *****\n"+ruleSet.getContent()+"\n********************");
        	throw new RuleSetTranslatorException(result.getMessage());
        }
    }

    private void parseRule(RuleSet ruleSet, BusinessRuleInfo businessRule) {
        checkBusinessRule(businessRule);
        
        String anchor = businessRule.getAnchor();
        String anchorTypeKey = businessRule.getAnchorTypeKey();
        String ruleName = removeInvalidCharacters(businessRule.getName());
        String ruleDescription = businessRule.getDesc();
        String functionString = BusinessRuleUtil.createAdjustedRuleFunctionString(businessRule);
        Map<String, RulePropositionInfo> propositionMap = BusinessRuleUtil.getRulePropositions(businessRule);
		Date effectiveStartTime = businessRule.getEffectiveDate();
		Date effectiveEndTime = businessRule.getExpirationDate();
        generateRules(anchor, anchorTypeKey, ruleName, ruleDescription, functionString, 
        		propositionMap, ruleSet, effectiveStartTime, effectiveEndTime);
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
    public void generateRules(String anchor, String anchorTypeKey, String ruleName, 
    						  String ruleDescription, String functionString,
    						  Map<String, RulePropositionInfo> functionalPropositionMap, 
    						  RuleSet ruleSet,
    						  Date effectiveStartTime,
    						  Date effectiveEndTime) {
    	String uuid = UUID.randomUUID().toString();
        int i=1;
        for(String template : ruleTemplates) {
        	// Rule names must be unique
        	String name = ruleName + "_T" + i++;
        	// Generate Drools rule source code
        	String ruleSource = generateRuleSourceCode(
            		anchor, anchorTypeKey, name, uuid,
            		functionString, functionalPropositionMap, 
            		effectiveStartTime, effectiveEndTime, template);
            addRule(name, ruleDescription, ruleSet, ruleSource, effectiveStartTime, effectiveEndTime);
        }
    }

	private String generateRuleSourceCode(String anchor,
                                          String anchorTypeKey, String ruleName, String uuid,
                                          String functionString,
                                          Map<String, RulePropositionInfo> functionalPropositionMap,
                                          Date effectiveStartTime, Date effectiveEndTime,
                                          String template) {
		removeInvalidCharacters(anchor);
		BooleanFunction function = new BooleanFunction(functionString);

		CurrentDateTime date = new CurrentDateTime();
		long effStartDate = date.getDateAsLong(effectiveStartTime);
		long effEndDate = date.getDateAsLong(effectiveEndTime);

		// Create the final composite rule for the function
		List<String> symbols = function.getSymbols();
		Map<String, Object> velocityContextMap = new HashMap<String, Object>();
		velocityContextMap.put("anchor", anchor);
		velocityContextMap.put("anchorTypeKey", anchorTypeKey);
		velocityContextMap.put("ruleName", ruleName);
		velocityContextMap.put("propositionMap", functionalPropositionMap);
		velocityContextMap.put("functionSymbols", symbols);
		velocityContextMap.put("functionString", functionString);
		velocityContextMap.put("effectiveStartTime", effStartDate);
		velocityContextMap.put("effectiveEndTime", effEndDate);
		velocityContextMap.put("factUtil", new FactUtil());
		velocityContextMap.put("uuid", uuid);

		RuleTemplate velocityRuleTemplate = new RuleTemplate();
		return velocityRuleTemplate.process(template, velocityContextMap);
	}

	/**
	 * Create rule set.
	 * 
	 * @param anchor Rule anchor
	 * @param anchorTypeKey Rule anchor type key
	 * @param packageName package name
	 * @param description Rule description
	 * @param ruleName Rule name
	 * @param functionString Rule function String (e.g. (A*B)+C))
     * @param functionalPropositionMap Rule proposition map
     * @param effectiveStartTime Rule effective start date
     * @param effectiveEndTime Rule effective end date
	 * @return rule set
	 */
    public RuleSet createRuleSet(String anchor, String anchorTypeKey, 
    							 String packageName, String description, 
    							 String ruleName, String functionString,
    							 Map<String, RulePropositionInfo> functionalPropositionMap,
    							 Date effectiveStartTime, Date effectiveEndTime) {
        RuleSet ruleSet = ruleSetFactory.createRuleSet(packageName, description);
        addHeader(ruleSet);
        generateRules(anchor, anchorTypeKey, ruleName, "A rule description", functionString, 
        		functionalPropositionMap, ruleSet, effectiveStartTime, effectiveEndTime);
        return ruleSet;
    }

    /**
     * Adds Drools header information.
     * 
     * @param ruleSet Rule set
     */
    public void addHeader(final RuleSet ruleSet) {
        ruleSet.addHeader("import java.util.*");
        ruleSet.addHeader("import java.math.BigDecimal");
        ruleSet.addHeader("import org.slf4j.Logger;");
		ruleSet.addHeader("import org.slf4j.LoggerFactory;");
        ruleSet.addHeader("import org.kuali.student.brms.internal.common.entity.*");
        ruleSet.addHeader("import org.kuali.student.brms.internal.common.statement.propositions.*");
        ruleSet.addHeader("import org.kuali.student.brms.internal.common.statement.propositions.rules.*");
        ruleSet.addHeader("import org.kuali.student.brms.internal.common.statement.report.*");
        ruleSet.addHeader("import org.kuali.student.brms.rulemanagement.dto.*");
        ruleSet.addHeader("import org.kuali.student.brms.util.FactContainer");
        ruleSet.addHeader("import org.kuali.student.brms.util.FactContainer.State");
        ruleSet.addHeader("import org.kuali.student.brms.util.CurrentDateTime");
        ruleSet.addHeader("import org.kuali.student.brms.internal.common.utils.BusinessRuleUtil");
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

    private void checkBusinessRule(BusinessRuleInfo businessRule) {
    	if(businessRule == null) {
            throw new RuleSetTranslatorException("BusinessRuleInfo is null");
    	}
    }

    private static String removeInvalidCharacters(String s) {
    	return (s == null ? "" : s.replaceAll(INVALID_CHARACTERS_REGEX, ""));
    }
    
    private static boolean isValidRuleName(String s) {
    	return (s == null ? true : s.matches(VALID_RULE_NAME_REGEX));
    }
}
