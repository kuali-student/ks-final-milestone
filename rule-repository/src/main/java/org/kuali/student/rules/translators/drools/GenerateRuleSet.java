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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.rules.internal.common.entity.BusinessRule;
import org.kuali.student.rules.internal.common.entity.BusinessRuleContainer;
import org.kuali.student.rules.internal.common.entity.RuleProposition;
import org.kuali.student.rules.internal.common.runtime.ast.Function;
import org.kuali.student.rules.repository.drools.rule.DroolsConstants;
import org.kuali.student.rules.repository.drools.rule.RuleFactory;
import org.kuali.student.rules.repository.drools.rule.RuleSetFactory;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.translators.exceptions.GenerateRuleSetException;

/**
 * @author Rich Diaz
 */
public class GenerateRuleSet {

    private static final String VELOCITY_RULE_TEMPLATE1_INIT = "velocity-templates/org/kuali/student/rules/brms/translators/drools/RuleTemplate1Init.vm";
    private static final String VELOCITY_RULE_TEMPLATE2 = "velocity-templates/org/kuali/student/rules/brms/translators/drools/RuleTemplate2.vm";

    private static final String PACKAGE_PREFIX = "org.kuali.student.rules.";

    private static GenerateRuleSet _instance = new GenerateRuleSet();

    private RuleSetVerifier ruleSetVerifier = new RuleSetVerifier();
    
    private GenerateRuleSet() {
    }

    public static synchronized GenerateRuleSet getInstance() {
        return _instance;
    }

    /**
     * Parses and generates a rule set from a functional business rule.
     * 
     * @param container List of business rule
     * @throws GenerateRuleSetException Any errors verifying a rule set
     * @return A rule set
     */
    public RuleSet parse(BusinessRuleContainer container) throws GenerateRuleSetException {
        String packageName = PACKAGE_PREFIX + container.getNamespace();
        RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(packageName, container.getDescription());
        addHeader(ruleSet);
        for (BusinessRule businessRule : container.getBusinessRules()) {
            parseRule(ruleSet, businessRule);
        }
        verifyRule(ruleSet);
        return ruleSet;
    }
    
    private void verifyRule(RuleSet ruleSet) throws GenerateRuleSetException {
        ruleSetVerifier.verify(new StringReader(ruleSet.getContent()));
    }

    private void parseRule(RuleSet ruleSet, BusinessRule businessRule) {
        checkName(businessRule.getBusinessRuleType());

        String anchor = businessRule.getAnchor();
        String ruleName = businessRule.getName();
        String ruleDescription = businessRule.getDescription();
        String functionString = businessRule.createAdjustedRuleFunctionString();
        Map<String, RuleProposition> propositionMap = businessRule.getRulePropositions();
        generateRules(anchor, ruleName, ruleDescription, functionString, propositionMap,ruleSet);
    }

    public void generateRules(String anchor, String ruleName, String ruleDescription, String functionString,
            Map<String, RuleProposition> functionalPropositionMap, RuleSet ruleSet ) {
        String initRuleName = ruleName+"_INIT";
        String rule1Source = generateRule1InitSourceCode(anchor, initRuleName, functionString, functionalPropositionMap);
        addRule(initRuleName, ruleDescription, ruleSet, rule1Source);

        String rule2Source = generateRule2SourceCode(anchor, ruleName, functionString, functionalPropositionMap);
        addRule(ruleName, ruleDescription, ruleSet, rule2Source);
    }

    private String generateRule1InitSourceCode(String anchor, String ruleName, String functionString,
            Map<String, RuleProposition> functionalPropositionMap) {
        checkName(anchor);
        Function f = new Function(functionString);

        // Create the final composite rule for the function
        List<String> symbols = f.getSymbols();
        Map<String, Object> velocityContextMap = new HashMap<String, Object>();
        velocityContextMap.put("propositionMap", functionalPropositionMap);
        velocityContextMap.put("functionSymbols", symbols);
        velocityContextMap.put("functionString", functionString);

        RuleTemplate velocityRuleTemplate = new RuleTemplate();
        return velocityRuleTemplate.process(VELOCITY_RULE_TEMPLATE1_INIT, anchor, ruleName, velocityContextMap);
    }

    private String generateRule2SourceCode(String anchor, String ruleName, String functionString,
            Map<String, RuleProposition> functionalPropositionMap) {
        checkName(anchor);
        Function f = new Function(functionString);

        // Create the final composite rule for the function
        List<String> symbols = f.getSymbols();
        Map<String, Object> velocityContextMap = new HashMap<String, Object>();
        velocityContextMap.put("propositionMap", functionalPropositionMap);
        velocityContextMap.put("functionSymbols", symbols);
        velocityContextMap.put("functionString", functionString);

        RuleTemplate velocityRuleTemplate = new RuleTemplate();
        return velocityRuleTemplate.process(VELOCITY_RULE_TEMPLATE2, anchor, ruleName, velocityContextMap);
    }
    
    public RuleSet createRuleSet(String packageName, String description, String ruleName, String functionString,
            Map<String, RuleProposition> functionalPropositionMap) {
        RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(packageName, description);
        addHeader(ruleSet);
        generateRules(ruleName, ruleName, "A rule description", functionString, functionalPropositionMap, ruleSet);
        return ruleSet;
    }

    public void addHeader(RuleSet ruleSet) {
        ruleSet.addHeader("import java.util.*");
        ruleSet.addHeader("import org.kuali.student.rules.internal.common.entity.ComparisonOperator");
        ruleSet.addHeader("import org.kuali.student.rules.internal.common.statement.*");
        ruleSet.addHeader("import org.kuali.student.rules.internal.common.facts.FactRequest");
        ruleSet.addHeader("import org.kuali.student.rules.util.FactContainer");
        ruleSet.addHeader("import org.kuali.student.rules.util.FactContainer.State");
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
            throw new GenerateRuleSetException("Name cannot contain hyphens");
        }
    }

}
