/**
 * 
 */
package org.kuali.student.rules.brms.drools.translator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.rules.brms.core.entity.FunctionalBusinessRule;
import org.kuali.student.rules.brms.core.entity.RuleProposition;
import org.kuali.student.rules.brms.repository.drools.rule.DroolsConstants;
import org.kuali.student.rules.brms.repository.drools.rule.RuleFactory;
import org.kuali.student.rules.brms.repository.drools.rule.RuleSetFactory;
import org.kuali.student.rules.brms.repository.rule.Rule;
import org.kuali.student.rules.brms.repository.rule.RuleSet;
import org.kuali.student.rules.common.util.Function;


/**
 * @author Rich Diaz
 */
public class GenerateRuleSet {
    
    private static final String VELOCITY_RULE_TEMPLATE = "RuleTemplate.vm";
    
    private static final String PACKAGE_PREFIX = "org.kuali.student.rules.";
    
    protected static GenerateRuleSet _instance = new GenerateRuleSet();
    
    protected GenerateRuleSet() {
    }

    public static synchronized GenerateRuleSet getInstance() {
        return _instance;
    }
    
    public RuleSet parse(FunctionalBusinessRule businessRule) {
        String ruleName = cleanRuleName(businessRule.getName());
        String packageName = PACKAGE_PREFIX + cleanRuleName(businessRule.getBusinessRuleType()) + "." + ruleName; 
        
        String functionString = businessRule.createAdjustedRuleFunctionString();        

        RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(packageName, businessRule.getDescription());
        addHeader(ruleSet);
        
        String ruleSource = generateSourceCode( businessRule.getName(), functionString, businessRule.getRulePropositions() );
        addRule(businessRule.getName(), businessRule.getDescription(), ruleSet, ruleSource);        
        
        return ruleSet;
    }
    
    private String generateSourceCode(String ruleName, String functionString, Map<String, RuleProposition> functionalPropositionMap) {
        ruleName = cleanRuleName(ruleName);
        Function f = new Function( functionString );

        // Create the final composite rule for the function
        List<String> symbols = f.getSymbols();
        Map<String, Object> velocityContextMap = new HashMap<String, Object>();
        velocityContextMap.put("propositionMap", functionalPropositionMap);
        velocityContextMap.put("functionSymbols", symbols);
                
        RuleTemplate velocityRuleTemplate = new RuleTemplate();
        return velocityRuleTemplate.process(VELOCITY_RULE_TEMPLATE, ruleName, velocityContextMap);
    }
    
    public RuleSet createRuleSet(String packageName, String description, String ruleName, String functionString, Map<String, RuleProposition> functionalPropositionMap) {
        RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(packageName, description);
        addHeader(ruleSet);
        String source = generateSourceCode(ruleName, functionString, functionalPropositionMap);
        addRule( ruleName, "A description", ruleSet, source );
        return ruleSet;
    }

    private void addHeader(RuleSet ruleSet) {
        ruleSet.addHeader("import java.util.*");
        ruleSet.addHeader("import org.kuali.student.rules.statement.*");
        ruleSet.addHeader("import org.kuali.student.rules.common.util.CourseEnrollmentRequest");
    }
    
    /**
     * Adds a rule to a rule set.
     * 
     * @param businessRule Business rule to get name and description from
     * @param ruleSet Rule set to a rule to
     * @param ruleSourceCode Rule source code
     */
    private void addRule(String name, String description, RuleSet ruleSet, String ruleSourceCode) {
        String category = null;
        Rule rule = RuleFactory.getInstance().createDroolsRule(
                name, description, category, ruleSourceCode, 
                DroolsConstants.FORMAT_DRL);
        ruleSet.addRule(rule);
    }

    /**
     * Cleans a rule name
     * 
     * @param name Rule name
     * @return A clean rule name
     */
    private static String cleanRuleName(String name) {     
        return name.trim().replaceAll("[\\s-]", "_");
    }

}

