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
package org.kuali.student.rules.validate.courseprereq;

import java.io.InputStream;
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
import org.junit.Ignore;
import org.junit.Test;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.util.FactContainer;

/**
 * This test requires substantial memory if run in full. (see note
 * in method "readRuleBase")
 * To increase the size of memory allocation.  
 * Add the following Java VM environment settings
 * -Xms1024m -Xmx1024m -XX:PermSize=648m -XX:MaxPermSize=256m 
 * @author stse
 *
 */
public class CoursePrereqDRLTest {
    private static StatefulSession workingMemory;
    private static RuleBase ruleBase;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        //ruleBase = readRuleBase();
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        //workingMemory = ruleBase.newStatefulSession();
    }

    @After
    public void tearDown() throws Exception {
        //workingMemory.dispose();
        //workingMemory = null;
    }

    @Test
    public void testFireRules() throws Exception {
        /*CourseEnrollmentRequest req1 = new CourseEnrollmentRequest("ANAT391");
        Set<String> luiIds = new HashSet<String>(Arrays.asList("ANAT390".split(",")));
        req1.setLuiIds(luiIds);

        FactContainer factContainer1 = new FactContainer("ANAT391_1998W_null_352", req1);

        List<FactContainer> factList = Arrays.asList( factContainer1 );
        
        executeRules(factList);
        
        assertTrue( factContainer1.getPropositionContainer().getRuleResult() );*/
    }
    
    private void executeRules(List<FactContainer> factList) throws Exception {
        for( FactContainer fact : factList ) {
            workingMemory.insert(fact);
        }
        workingMemory.fireAllRules();
    }
    
    private static RuleBase readRuleBase() throws Exception {
        //read in the source
        int fileIndex = 0;
        InputStream is = null;
        Reader source = null;
        boolean moreFiles = true;
        String resourceName = null;
        RuleBase ruleBase = null;
        
        ruleBase = RuleBaseFactory.newRuleBase();
        PackageBuilder builder = new PackageBuilder();

        // for a full test remove the second loop condition "fileIndex < 10"
        // to load all DRL files
        while (moreFiles && fileIndex < 10) {
          try {
            resourceName = 
              "/drools/drls/org/kuali/student/rules/validate/courseprereq/prerequisites" + 
              Integer.toString(fileIndex) + ".drl";
            System.out.println("loading " + resourceName);
            is = CoursePrereqDRLTest.class.getResourceAsStream(
                resourceName );
            if (is == null) {
              System.out.println(resourceName + " not found no more drl files to load");
              moreFiles = false;
              break;
            }
            source = new InputStreamReader(is);
            builder.addPackageFromDrl(source);
            source = null;
          } finally {
            if (is != null) try {is.close();} catch (Exception e) {}
            if (source != null) try {source.close();} catch (Exception e) {}
          }
          fileIndex++;
        }
        Package pkg = builder.getPackage();
        ruleBase.addPackage(pkg);
        return ruleBase;
    }
}
