package org.kuali.student.rules.validate;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.kuali.student.rules.common.util.CourseEnrollmentRequest;
import org.kuali.student.rules.statement.PropositionContainer;

public class AllInOneDRLTest {

    @Test
    public void testFireRule() throws Exception {
        CourseEnrollmentRequest req = new CourseEnrollmentRequest();
        Set<String> luiIds = new HashSet<String>(Arrays.asList("CPR101,MATH102,CHEM101,CHEM102".split(",")));
        req.setLuiIds(luiIds);
        PropositionContainer prop = new PropositionContainer();

        WorkingMemory workingMemory = readRule().newStatefulSession();
        workingMemory.insert(req);
        workingMemory.insert(prop);
        workingMemory.fireAllRules();
        assertTrue( prop.getRuleResult() );
        System.out.println( "Integer.MAX="+Integer.MAX_VALUE );
    }
    
    private static RuleBase readRule() throws Exception {
        //read in the source
        Reader source = new InputStreamReader( AllInOneDRLTest.class.getResourceAsStream( "/Math101Enrollment.drl" ) );
        
        PackageBuilder builder = new PackageBuilder();

        builder.addPackageFromDrl( source );

        Package pkg = builder.getPackage();
        
        //add the package to a rulebase (deploy the rule package).
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage( pkg );
        return ruleBase;
    }
}
