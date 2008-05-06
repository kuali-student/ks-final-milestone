/**
 *
 */
package org.kuali.student.rules.util;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.kuali.student.mock.MockCourseEnrollmentRequest;
import org.drools.common.DefaultFactHandle;


/**
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 *
 */
public class SumConstraintTest {

    //~ Instance fields --------------------------------------------------------

    ConstraintStrategy constraintStrategyTrue;
    ConstraintStrategy constraintStrategyFalse;
    MockCourseEnrollmentRequest requestTrue;
    MockCourseEnrollmentRequest requestFalse;
    Constraint constraintTrue;
    Constraint constraintFalse;
    

    //~ Methods ----------------------------------------------------------------

    /**
     * @throws java.lang.Exception
     */
    @Before public void setUp() throws Exception {

        //createConstraintStrategy();
        //constraint = new Constraint(constraintStrategy);
    }

    private void createConstraintStrategy(ComparisonOperator comparisonOperator) {

        constraintStrategyTrue = new SumConstraint<Number>("A true constraintID from DB",
                "org.kuali.student.mock.MockCourseEnrollmentRequest",
                "CoreStudyUnits", comparisonOperator, 5);
        
        constraintStrategyFalse = new SumConstraint<Number>("A false constraintID from DB",
                "org.kuali.student.mock.MockCourseEnrollmentRequest",
                "CoreStudyUnits", comparisonOperator, 5);
    }

    private void createRequest() {
        Propositions.init("A");
        
        requestTrue = new MockCourseEnrollmentRequest("Tom",
                    "Math 100, Math 101, Math 102, Math 105, Art 55", "1, 1, 1, 1, 1", true, "JUNIOR");
                    
        requestFalse = new MockCourseEnrollmentRequest("Tom",
                    "Math 100, Math 105, Art 55", "1, 2, 3, 4, 5", true, "JUNIOR");
    }
    
    /**
     * @throws java.lang.Exception
     */
    @After public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link org.kuali.drools.util.SubsetConstraint#apply())}.
     */
/*    @Test public void testApplySucceeds() {
        Boolean ok = constraint.apply();
        Assert.assertTrue("should have passed", Boolean.TRUE.equals(ok));

    }*/

    @Test
    public void runRule1Test() throws Exception{
        createConstraintStrategy(ComparisonOperator.EQUAL_TO);
        constraintTrue = new Constraint(constraintStrategyTrue);
        constraintFalse = new Constraint(constraintStrategyFalse);
        
        createRequest();
        constraintTrue.setRequest(requestTrue);
        constraintFalse.setRequest(requestFalse);
        
        RuleBase ruleBase = readRule();
        WorkingMemory workingMemory = ruleBase.newStatefulSession();
        workingMemory.insert(constraintTrue);
        workingMemory.insert(constraintFalse);
        workingMemory.fireAllRules();
        System.out.println("The Failure Message : " + Propositions.getFailureMessage("A"));
        System.out.println("End Test 1 \n\n");
    }
    
    @Test
    public void runRule2Test() throws Exception{
        createConstraintStrategy(ComparisonOperator.NOT_EQUAL_TO);
        constraintTrue = new Constraint(constraintStrategyTrue);
        constraintFalse = new Constraint(constraintStrategyFalse);
        
        createRequest();
        constraintTrue.setRequest(requestTrue);
        constraintFalse.setRequest(requestFalse);
        
        RuleBase ruleBase = readRule();
        WorkingMemory workingMemory = ruleBase.newStatefulSession();
        workingMemory.insert(constraintFalse);
        workingMemory.insert(constraintTrue);
        workingMemory.fireAllRules();
        System.out.println("The Failure Message : " + Propositions.getFailureMessage("A"));
        System.out.println("End Test 2 \n\n");
    }
    
    @Test
    public void runRule3Test() throws Exception{
        createConstraintStrategy(ComparisonOperator.GREATER_THAN);
        constraintTrue = new Constraint(constraintStrategyTrue);
        constraintFalse = new Constraint(constraintStrategyFalse);
        
        createRequest();
        constraintTrue.setRequest(requestTrue);
        constraintFalse.setRequest(requestFalse);
        
        RuleBase ruleBase = readRule();
        WorkingMemory workingMemory = ruleBase.newStatefulSession();
        workingMemory.insert(constraintFalse);
        workingMemory.insert(constraintTrue);
        workingMemory.fireAllRules();
        System.out.println("The Failure Message : " + Propositions.getFailureMessage("A"));
        System.out.println("End Test 3 \n\n");
    }
    
    @Test
    public void runRule4Test() throws Exception{
        createConstraintStrategy(ComparisonOperator.LESS_THAN);
        constraintTrue = new Constraint(constraintStrategyTrue);
        constraintFalse = new Constraint(constraintStrategyFalse);
        
        createRequest();
        constraintTrue.setRequest(requestTrue);
        requestFalse = new MockCourseEnrollmentRequest("Tom",
                "Math 100, Math 105, Art 55", "1, 2", true, "JUNIOR");
        constraintFalse.setRequest(requestFalse);
        
        RuleBase ruleBase = readRule();
        WorkingMemory workingMemory = ruleBase.newStatefulSession();
        workingMemory.insert(constraintFalse);
        workingMemory.insert(constraintTrue);
        workingMemory.fireAllRules();
        System.out.println("The Failure Message : " + Propositions.getFailureMessage("A"));
        System.out.println("End Test 4 \n\n");
    }

    private Set<Integer> parseList(String list) {
        HashSet<Integer> rval = new HashSet<Integer>();
        String[] toks = list.split("\\s*,\\s*");

        // there's a one-liner for this...
        for (String t : toks) {
            rval.add(Integer.valueOf(t));
        }

        return rval;
    }

    private static RuleBase readRule() throws Exception {
        //read in the source
        Reader source = new InputStreamReader( SubsetConstraint.class.getResourceAsStream( "/EnrollmentSumTest.drl" ) );
        
        PackageBuilder builder = new PackageBuilder();

        builder.addPackageFromDrl( source );

        Package pkg = builder.getPackage();
        
        //add the package to a rulebase (deploy the rule package).
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage( pkg );
        return ruleBase;
    }
}
