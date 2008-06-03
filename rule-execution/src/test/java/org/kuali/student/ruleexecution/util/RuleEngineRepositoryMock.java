package org.kuali.student.ruleexecution.util;

import java.io.StringReader;

import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

public class RuleEngineRepositoryMock {

    private RuleEngineRepositoryMock() {
    }

    public static RuleEngineRepositoryMock getInstance() {
        return new RuleEngineRepositoryMock();
    }

    /**
     * Gets a simple rule. 
     * First rule determines whether the minutes of the hour is even.
     * Rule takes <code>java.util.Calendar</code> as fact (package import).
     *  
     * @return a Drools' rule
     */
    private String getSimpleRule1() {
        return 
            "rule \"HelloDroolsEven\"" +
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
    private String getSimpleRule2() {
        return 
            "rule \"HelloDroolsOdd\"" +
            "     when" +
            "          now: Calendar()" +
            "          eval ( ( now.get(Calendar.MINUTE) % 2 == 1 ) )" +
            "     then" +
            "          insert( \"Minute is odd: \" + now.get(Calendar.MINUTE)  );" +
            "end";
    }

    /**
     * Return a Drools DRL file which includes 
     * <code>getSimpleRule1</code> and <code>getSimpleRule2</code>.
     * 
     * @param packageName Drools package name (ruleset)
     * @return Drools DRL
     */
    private String getSimplePackage() {
        return 
            "package testpackage" +
            "\n" +
            "import java.util.Calendar; " +
            "\n\n" +
            getSimpleRule1() +
            "\n" +
            getSimpleRule2() +
            "\n";
    }

    /**
     * Build a simple Drools package
     * 
     * @return A drools package
     * @throws Exception Any errors building package
     */
    private Package buildPackage() throws Exception {
        PackageBuilder builder = new PackageBuilder();
        StringReader drl = new StringReader( getSimplePackage() );
        builder.addPackageFromDrl( drl );
        return builder.getPackage();
    }
    
    /**
     *  Simulates loading a rule set (org.drools.rule.Package) from the 
     *  <code>RuleEngineRepository</code>.
     * 
     * @param uuid Rule set uuid
     * @return A Drools Package
     * @throws Exception
     */
    public Object loadCompiledRuleSet( String uuid ) throws Exception {
        return buildPackage();
    }
    
}
