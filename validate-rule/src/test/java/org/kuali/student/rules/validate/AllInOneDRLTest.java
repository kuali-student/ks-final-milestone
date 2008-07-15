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
package org.kuali.student.rules.validate;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.kuali.student.rules.internal.common.facts.CourseEnrollmentRequest;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.util.FactContainer;

public class AllInOneDRLTest {
    private static StatefulSession workingMemory;
    private static RuleBase ruleBase;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        ruleBase = readRuleBase();
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        workingMemory = ruleBase.newStatefulSession();
    }

    @After
    public void tearDown() throws Exception {
        workingMemory.dispose();
        workingMemory = null;
    }

    @Test
    public void testFireRules_Math_Chem_Cpr() throws Exception {
        CourseEnrollmentRequest req1 = new CourseEnrollmentRequest("Math101");
        CourseEnrollmentRequest req2 = new CourseEnrollmentRequest("Chem201");
        CourseEnrollmentRequest req3 = new CourseEnrollmentRequest("Cpr101");
        Set<String> luiIds = new HashSet<String>(Arrays.asList("CPR101,MATH102,CHEM101,CHEM102".split(",")));
        req1.setLuiIds(luiIds);
        req3.setLuiIds(luiIds);
        Set<String> luiIds2 = new HashSet<String>(Arrays.asList("ENGL101,ENGL102,HIST101,HIST102".split(",")));
        req2.setLuiIds(luiIds2);

        FactContainer factContainer1 = new FactContainer("Math101", req1);
        FactContainer factContainer2 = new FactContainer("Chem201", req2);
        FactContainer factContainer3 = new FactContainer("Cpr101", req3);

        List<FactContainer> factList = Arrays.asList( factContainer1, factContainer2, factContainer3 );
        
        executeRules(factList);
        
        assertTrue( factContainer1.getPropositionContainer().getRuleResult() );
        assertFalse( factContainer2.getPropositionContainer().getRuleResult() );
        assertTrue( factContainer3.getPropositionContainer().getRuleResult() );
    }
    
    private void executeRules(List<FactContainer> factList) throws Exception {
        for( FactContainer fact : factList ) {
            workingMemory.insert(fact);
        }
        workingMemory.fireAllRules();
    }
    
    private static RuleBase readRuleBase() throws Exception {
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
