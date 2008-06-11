/**
 * 
 */
package org.kuali.student.rules.brms.drools.translator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.brms.repository.drools.rule.DroolsConstants;
import org.kuali.student.brms.repository.drools.rule.RuleFactory;
import org.kuali.student.brms.repository.drools.rule.RuleSetFactory;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleSet;
import org.kuali.student.rules.brms.core.entity.FunctionalBusinessRule;
import org.kuali.student.rules.brms.core.entity.RuleProposition;
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
        
        RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(packageName, businessRule.getDescription());
        ruleSet.addHeader("import java.util.*");
        ruleSet.addHeader("import org.kuali.student.rules.statement.*");
        ruleSet.addHeader("import org.kuali.student.rules.common.util.CourseEnrollmentRequest");

        String ruleSource = "";
        
        String functionString = businessRule.createAdjustedRuleFunctionString();        

        Function f = new Function( functionString );

        // create the final composite rule for the function
        List<String> symbols = f.getSymbols();
        
        Map<String, RuleProposition> functionalPropositionMap = businessRule.getRulePropositions();

        Map<String, Object> velocityContextMap = new HashMap<String, Object>();
        velocityContextMap.put("propositionMap", functionalPropositionMap);
        velocityContextMap.put("functionSymbols", symbols);
                
        try {

            RuleTemplate velocityRuleTemplate = new RuleTemplate();
            ruleSource = velocityRuleTemplate.process(VELOCITY_RULE_TEMPLATE, ruleName, velocityContextMap);

            saveRule(businessRule, ruleSet, ruleSource);        

            
        } catch (Exception e) {
            e.printStackTrace();
            ruleSet = null;
        }                
        
        return ruleSet;
    }

    
    
    // Add rule to ruleset created in constructor
    private void saveRule(FunctionalBusinessRule businessRule, RuleSet ruleSet, String ruleSourceCode) {
        String category = null;
        Rule rule = RuleFactory.getInstance().createDroolsRule(
                businessRule.getName(), businessRule.getDescription(), category, ruleSourceCode, 
                DroolsConstants.FORMAT_DRL);
        ruleSet.addRule(rule);
    }

    
    private String cleanRuleName(String name) {     
        return name.trim().replaceAll("[\\s-]", "_");
    }

}

