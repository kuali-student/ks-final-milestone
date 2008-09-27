package org.kuali.student.rules.ruleexecution.drools.util;

import java.io.StringReader;

import org.drools.compiler.PackageBuilder;
import org.kuali.student.rules.repository.drools.util.DroolsUtil;
import org.kuali.student.rules.repository.dto.RuleDTO;
import org.kuali.student.rules.repository.dto.RuleSetDTO;

public class DroolsTestUtil {

    /**
     * Gets a simple rule. 
     * First rule determines whether the minutes of the hour is even.
     * Rule takes <code>java.util.Calendar</code> as fact (package import).
     * 
     * @param ruleName Rule name
     * @return a Drools rule
     */
	private static String getSimpleRule1(String ruleName) {
        ruleName = (ruleName == null ? "HelloDroolsEven" : ruleName);
        return 
			"rule \"" + ruleName + "\"" +
			"     when" +
			"          now: Calendar()" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 0 ) )" +
			"     then" +
			"          insert( \"Minute is even: \" + now.get(Calendar.MINUTE) );" +
			"end";
	}
	
	/**
	 * Gets a simple rule. 
	 * Rule determines whether the minutes of the hour is odd.
	 * Rule takes <code>java.util.Calendar</code> as fact (package import).
	 *  
	 * @return a Drools' rule
	 */
	private static String getSimpleRule2(String ruleName) {
        ruleName = (ruleName == null ? "HelloDroolsOdd" : ruleName);
		return 
            "rule \"" + ruleName + "\"" +
			"     when" +
			"          now: Calendar()" +
			"          eval ( ( now.get(Calendar.MINUTE) % 2 == 1 ) )" +
			"     then" +
			"          insert( \"Minute is odd: \" + now.get(Calendar.MINUTE) );" +
			"end";
	}

	public static RuleSetDTO createRuleSet() {
    	RuleSetDTO ruleSet = new RuleSetDTO("TestName", "Test description", "DRL");
    	ruleSet.addHeader("import java.util.Calendar");
    	
    	RuleDTO rule1 = new RuleDTO("rule1", "A rule", getSimpleRule1("rule1"), "DRL");
    	RuleDTO rule2 = new RuleDTO("rule2", "A rule", getSimpleRule2("rule2"), "DRL");
    	ruleSet.addRule(rule1);
    	ruleSet.addRule(rule2);
    	ruleSet.setContent(ruleSet.buildContent());
    	return ruleSet;
    }
	
	public static byte[] createPackage(RuleSetDTO ruleSet) throws Exception {
		PackageBuilder builder = new PackageBuilder();
		builder.addPackageFromDrl(new StringReader(ruleSet.getContent()));
        // Deserialize a Drools package
		return DroolsUtil.deserialize( builder.getPackage() );
	}
}
