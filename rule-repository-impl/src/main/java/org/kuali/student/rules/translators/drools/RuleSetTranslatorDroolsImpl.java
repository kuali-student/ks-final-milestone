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

import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.rules.internal.common.runtime.ast.Function;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.repository.drools.rule.DroolsConstants;
import org.kuali.student.rules.repository.drools.rule.RuleFactory;
import org.kuali.student.rules.repository.drools.rule.RuleSetFactory;
import org.kuali.student.rules.repository.exceptions.RuleSetTranslatorException;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.FactStructureDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.translators.RuleSetTranslator;
import org.kuali.student.rules.translators.util.Constants;
import org.kuali.student.rules.util.CurrentDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class generates Drools rules source code from functional business rules.
 */
public class RuleSetTranslatorDroolsImpl implements RuleSetTranslator {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(RuleSetTranslatorDroolsImpl.class);

    private static final String VELOCITY_RULE_TEMPLATE1_INIT = "velocity-templates/org/kuali/student/rules/brms/translators/drools/RuleTemplate1Init.vm";
    private static final String VELOCITY_RULE_TEMPLATE2 = "velocity-templates/org/kuali/student/rules/brms/translators/drools/RuleTemplate2.vm";

    private static final String PACKAGE_PREFIX = "org.kuali.student.rules.";

    private RuleSetVerifier ruleSetVerifier = new RuleSetVerifier();
    
    public RuleSetTranslatorDroolsImpl() {
    }

    /**
     * Parses and generates a rule set from a functional business rule.
     * 
     * @param container List of business rule
     * @throws RuleSetTranslatorException Any errors verifying a rule set
     * @return A rule set
     */
    public RuleSet translate(BusinessRuleContainerDTO container) throws RuleSetTranslatorException {
    	//verifyKeys(container);
    	String packageName = PACKAGE_PREFIX + container.getNamespace();
        RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(
        		packageName, container.getDescription());
        addHeader(ruleSet);
        for (BusinessRuleInfoDTO businessRule : container.getBusinessRules()) {
            parseRule(ruleSet, businessRule);
        }
        if (logger.isDebugEnabled()) {
        	logger.debug("Container namespace: "+container.getNamespace() 
        			+ "\n" +
        			ruleSet.getContent());
        }
        verifyRule(ruleSet);
        return ruleSet;
    }
    
    private void verifyKeys(BusinessRuleContainerDTO container) {
        for (BusinessRuleInfoDTO businessRule : container.getBusinessRules()) {
        	for(RuleElementDTO element : businessRule.getRuleElementList()) {
        		if (element.getRuleProposition() == null) {
        			return;
        		}
        		List<FactStructureDTO> facts = element.getRuleProposition().getLeftHandSide().getYieldValueFunction().getFactStructureList();
        		for(FactStructureDTO fact : facts) {
        			verifyDefinitionKeys(fact.getDefinitionVariableList());
        			verifyExecutionKeys(fact.getExecutionVariableList());
        		}
        	}
        }
    }

    private void verifyDefinitionKeys(Map<String,String> defMap) {
    	if (defMap.isEmpty()) {
    		throw new RuleSetTranslatorException("Definition key map contains no keys");
    	}
    	boolean found = false;
    	for(String key: Constants.getConstantValues()) {
    		if (defMap.containsKey(key)) {
    			found = true;
    			break;
	    	}
    	}
    	
    	if (!found) {
    		throw new RuleSetTranslatorException("No valid definition keys found");
    	}
    }
    
    private void verifyExecutionKeys(Map<String,String> exeMap) {
    	if (exeMap.isEmpty()) {
    		throw new RuleSetTranslatorException("Execution key map contains no keys");
    	}
    	for(String key: exeMap.keySet()) {
	    	String value = exeMap.get(key);
    		if (!Constants.containsConstantValue(key)) {
	    		throw new RuleSetTranslatorException("Invalid execution key value: " + key +"=" + value);
	    	}
    	}
    }
    
    private void verifyRule(RuleSet ruleSet) throws RuleSetTranslatorException {
        ruleSetVerifier.verify(new StringReader(ruleSet.getContent()));
    }

    private void parseRule(RuleSet ruleSet, BusinessRuleInfoDTO businessRule) {
        checkName(businessRule.getBusinessRuleTypeKey());

        String anchor = businessRule.getAnchorValue();
        String ruleName = businessRule.getName();
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
        String rule1Source = generateRule1InitSourceCode(anchor, initRuleName, 
        		functionString, functionalPropositionMap, 
        		effectiveStartTime, effectiveEndTime);
        addRule(initRuleName, ruleDescription, ruleSet, rule1Source);

        String rule2Source = generateRule2SourceCode(anchor, ruleName, 
        		functionString, functionalPropositionMap);
        addRule(ruleName, ruleDescription, ruleSet, rule2Source);
    }

    private String generateRule1InitSourceCode(String anchor, String ruleName, 
    										   String functionString,
    										   Map<String, RulePropositionDTO> functionalPropositionMap,
    										   Date effectiveStartTime,
    										   Date effectiveEndTime) {
        checkName(anchor);
        Function f = new Function(functionString);

        CurrentDateTime date = new CurrentDateTime();
        long effStartDate = date.getDateAsLong(effectiveStartTime);
        long effEndDate = date.getDateAsLong(effectiveEndTime);
        
        // Create the final composite rule for the function
        List<String> symbols = f.getSymbols();
        Map<String, Object> velocityContextMap = new HashMap<String, Object>();
        velocityContextMap.put("anchor", anchor);
        velocityContextMap.put("ruleName", ruleName);
        velocityContextMap.put("propositionMap", functionalPropositionMap);
        velocityContextMap.put("functionSymbols", symbols);
        velocityContextMap.put("functionString", functionString);
        velocityContextMap.put("effectiveStartTime", effStartDate);
        velocityContextMap.put("effectiveEndTime", effEndDate);
        velocityContextMap.put("factUtil", new FactUtil());
        velocityContextMap.put("FACT_STRUCTURE_ID", Constants.FACT_STRUCTURE_ID);
        velocityContextMap.put("DEF_CRITERIA_KEY", Constants.DEF_CRITERIA_KEY);
        velocityContextMap.put("EXE_FACT_KEY", Constants.EXE_FACT_KEY);

        RuleTemplate velocityRuleTemplate = new RuleTemplate();
        return velocityRuleTemplate.process(VELOCITY_RULE_TEMPLATE1_INIT, velocityContextMap);
    }

    private String generateRule2SourceCode(String anchor, String ruleName, 
    									   String functionString,
    									   Map<String, RulePropositionDTO> functionalPropositionMap) {
        checkName(anchor);
        Function f = new Function(functionString);

        // Create the final composite rule for the function
        List<String> symbols = f.getSymbols();
        Map<String, Object> velocityContextMap = new HashMap<String, Object>();
        velocityContextMap.put("anchor", anchor);
        velocityContextMap.put("ruleName", ruleName);
        velocityContextMap.put("propositionMap", functionalPropositionMap);
        velocityContextMap.put("functionSymbols", symbols);
        velocityContextMap.put("functionString", functionString);

        RuleTemplate velocityRuleTemplate = new RuleTemplate();
        return velocityRuleTemplate.process(VELOCITY_RULE_TEMPLATE2, velocityContextMap);
    }
    
    public RuleSet createRuleSet(String anchor, String packageName, String description, 
    							 String ruleName, String functionString,
    							 Map<String, RulePropositionDTO> functionalPropositionMap,
    							 Date effectiveStartTime, Date effectiveEndTime) {
        RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(packageName, description);
        addHeader(ruleSet);
        generateRules(anchor, ruleName, "A rule description", functionString, 
        		functionalPropositionMap, ruleSet, effectiveStartTime, effectiveEndTime);
        return ruleSet;
    }

    public void addHeader(RuleSet ruleSet) {
        ruleSet.addHeader("import java.util.*");
        ruleSet.addHeader("import org.kuali.student.rules.internal.common.entity.*");
        ruleSet.addHeader("import org.kuali.student.rules.internal.common.statement.*");
        ruleSet.addHeader("import org.kuali.student.rules.rulemanagement.dto.*");
        ruleSet.addHeader("import org.kuali.student.rules.util.FactContainer");
        ruleSet.addHeader("import org.kuali.student.rules.util.FactContainer.State");
        ruleSet.addHeader("import org.kuali.student.rules.util.CurrentDateTime");
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
    private void addRule(String name, String description, RuleSet ruleSet, String ruleSourceCode) {
        String category = null;
        Rule rule = RuleFactory.getInstance().createDroolsRule(name, description, category, ruleSourceCode,
                                                               DroolsConstants.FORMAT_DRL);
        ruleSet.addRule(rule);
    }

    /**
     * Checks that a name cannot contain any hyphens.
     * 
     * @param name
     *            Rule name
     * @return A clean rule name
     */
    private static void checkName(String name) {
        // return name.trim().replaceAll("[\\s-]", "_");
        if (name.trim().indexOf("-") > -1) {
            throw new RuleSetTranslatorException("Name cannot contain hyphens");
        }
    }

}
