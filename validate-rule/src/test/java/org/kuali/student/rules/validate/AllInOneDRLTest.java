package org.kuali.student.rules.validate;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.junit.Test;
import org.kuali.student.rules.common.util.CourseEnrollmentRequest;
import org.kuali.student.rules.statement.PropositionContainer;


public class AllInOneDRLTest {

    /**
     * This method ...
     * 
     * @param args
     */
    @Test
    public void fireRule() {
        
        CourseEnrollmentRequest req = new CourseEnrollmentRequest();
        Set<String> luiIds = new HashSet<String>(Arrays.asList("CPR101,MATH102,CHEM101,CHEM102".split(",")));
        req.setLuiIds(luiIds);

        System.out.println("\nStarting rules");
        try {
            RuleBase ruleBase = readRule();
            WorkingMemory workingMemory = ruleBase.newStatefulSession();
            
            PropositionContainer prop = new PropositionContainer();
            workingMemory.insert(req);
            workingMemory.insert(prop);
            
            System.out.println("\ninserted constraint in rules and gona fire");
            workingMemory.fireAllRules();



            
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        System.out.println("End Test 1");        
        
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
