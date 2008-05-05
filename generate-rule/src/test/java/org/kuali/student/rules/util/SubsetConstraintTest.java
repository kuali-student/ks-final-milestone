/**
 *
 */
package org.kuali.student.rules.util;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

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
public class SubsetConstraintTest {

    //~ Instance fields --------------------------------------------------------

    ConstraintStrategy constraintStrategy;
    MockCourseEnrollmentRequest request;
    Constraint constraint;

    //~ Methods ----------------------------------------------------------------

    /**
     * @throws java.lang.Exception
     */
    @Before public void setUp() throws Exception {
        
        //createRequest();
        createConstraintStrategy();
        constraint = new Constraint(constraintStrategy);
        //constraint.setRequest(request);
    }

    private void createConstraintStrategy() {

        Set<String> lr = parseList("Math 100, Math 101, Math 110");

        constraintStrategy = new SubsetConstraint<String>("A constraintID from DB",
                "org.kuali.student.mock.MockCourseEnrollmentRequest",
                "LearningResults", lr, 2);
    }

    private void createRequest(int ruleNumber) {
        Propositions.init("A");
        
        switch (ruleNumber) {
            case 1: request = new MockCourseEnrollmentRequest("Tom",
                    "Math 100, Math 101, Math 102, Math 105, Art 55", "1, 2, 3, 4, 5", true,
                    "JUNIOR");
                    break;
            case 2: request = new MockCourseEnrollmentRequest("Tom",
                    "Math 100, Math 105, Art 55", "1, 2, 3, 4, 5", true,
                       "JUNIOR");
                    break;
            default: request = null;
        }
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
        createRequest(1);
        constraint.setRequest(request);
        
        RuleBase ruleBase = readRule();
        WorkingMemory workingMemory = ruleBase.newStatefulSession();
        workingMemory.insert(constraint);
        workingMemory.fireAllRules();
        System.out.println("End Test 1");
    }
    
    @Test
    public void runRule2Test() throws Exception{
        createRequest(2);
        constraint.setRequest(request);
        
        RuleBase ruleBase = readRule();
        WorkingMemory workingMemory = ruleBase.newStatefulSession();
        workingMemory.insert(constraint);
        workingMemory.fireAllRules();
        System.out.println("End Test 2");
    }

    private Set<String> parseList(String list) {
        HashSet<String> rval = new HashSet<String>();
        String[] toks = list.split("\\s*,\\s*");

        // there's a one-liner for this...
        for (String t : toks) {
            rval.add(t);
        }

        return rval;
    }

    private static RuleBase readRule() throws Exception {
        //read in the source
        Reader source = new InputStreamReader( SubsetConstraint.class.getResourceAsStream( "/EnrollmentSubsetTest.drl" ) );
        
        PackageBuilder builder = new PackageBuilder();

        builder.addPackageFromDrl( source );

        Package pkg = builder.getPackage();
        
        //add the package to a rulebase (deploy the rule package).
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage( pkg );
        return ruleBase;
    }
}
