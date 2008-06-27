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
import org.kuali.student.rules.util.FactContainer;

public class AllInOneDRLTest {

    @Test
    public void testFireRule() throws Exception {
        CourseEnrollmentRequest req = new CourseEnrollmentRequest();
        Set<String> luiIds = new HashSet<String>(Arrays.asList("CPR101,MATH102,CHEM101,CHEM102".split(",")));
        req.setLuiIds(luiIds);
        PropositionContainer prop = new PropositionContainer();

        FactContainer ruleSetContainer1 = new FactContainer( "Math101" );
        ruleSetContainer1.setPropositionContainer(prop);
        ruleSetContainer1.setRequest(req);
        FactContainer ruleSetContainer2 = new FactContainer( "Chem201" );
        ruleSetContainer2.setPropositionContainer(prop);
        ruleSetContainer2.setRequest(req);
        FactContainer ruleSetContainer3 = new FactContainer( "Cpr101" );
        ruleSetContainer3.setPropositionContainer(prop);
        ruleSetContainer3.setRequest(req);

        WorkingMemory workingMemory = readRule().newStatefulSession();
        workingMemory.insert(ruleSetContainer1);
        workingMemory.insert(ruleSetContainer2);
        workingMemory.insert(ruleSetContainer3);
        //workingMemory.insert(req);
        //workingMemory.insert(prop);
        workingMemory.fireAllRules();
        assertTrue( prop.getRuleResult() );
    }
    
    private static RuleBase readRule() throws Exception {
        //read in the source
        Reader source1 = new InputStreamReader( AllInOneDRLTest.class.getResourceAsStream( "/Math101Enrollment.drl" ) );
        Reader source2 = new InputStreamReader( AllInOneDRLTest.class.getResourceAsStream( "/Chem201Enrollment.drl" ) );
        Reader source3 = new InputStreamReader( AllInOneDRLTest.class.getResourceAsStream( "/Cpr101Enrollment.drl" ) );
        
        PackageBuilder builder = new PackageBuilder();

        builder.addPackageFromDrl( source1 );
        builder.addPackageFromDrl( source2 );
        builder.addPackageFromDrl( source3 );

        Package pkg = builder.getPackage();
        
        //add the package to a rulebase (deploy the rule package).
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage( pkg );
        return ruleBase;
    }
}
