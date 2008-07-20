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
package org.kuali.student.rules.brms.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.rules.RuleRuntime;
import javax.rules.RuleServiceProvider;
import javax.rules.RuleServiceProviderManager;
import javax.rules.StatelessRuleSession;
import javax.rules.admin.LocalRuleExecutionSetProvider;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;

import org.drools.jsr94.rules.RuleServiceProviderImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.rules.brms.repository.RuleEngineRepository;
import org.kuali.student.rules.brms.repository.drools.DroolsJackrabbitRepository;
import org.kuali.student.rules.brms.repository.drools.DroolsTestUtil;
import org.kuali.student.rules.brms.repository.drools.RuleEngineRepositoryDroolsImpl;
import org.kuali.student.rules.brms.repository.drools.rule.DroolsConstants;
import org.kuali.student.rules.brms.repository.drools.rule.RuleFactory;
import org.kuali.student.rules.brms.repository.drools.rule.RuleSetFactory;
import org.kuali.student.rules.brms.repository.exceptions.CategoryExistsException;
import org.kuali.student.rules.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.rules.brms.repository.exceptions.RuleExistsException;
import org.kuali.student.rules.brms.repository.exceptions.RuleSetExistsException;
import org.kuali.student.rules.brms.repository.rule.Category;
import org.kuali.student.rules.brms.repository.rule.CompilerResultList;
import org.kuali.student.rules.brms.repository.rule.Rule;
import org.kuali.student.rules.brms.repository.rule.RuleSet;
import org.kuali.student.rules.brms.repository.test.Email;
import org.kuali.student.rules.brms.repository.test.Message;

/**
 * This is a Drools rules engine repository test class. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleEngineRepositoryTest {
    /** Drools Jackrabbit repository */
    private static DroolsJackrabbitRepository jackrabbitRepository;
    /** Rule engine repository interface */
    private static RuleEngineRepository brmsRepository;
    /** Drools test utility class */
    private static DroolsTestUtil droolsTestUtil = DroolsTestUtil.getInstance();
    /** Rule engine utility class */
    private RuleEngineUtil ruleEngineUtil = RuleEngineUtil.getInstance();
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        URL url = RuleEngineRepositoryTest.class.getResource("/drools-repository");
        jackrabbitRepository = new DroolsJackrabbitRepository(url);
        //jackrabbitRepository.clearAll();
        jackrabbitRepository.startupRepository();
        jackrabbitRepository.login( droolsTestUtil.getSuperUserCredentials() );
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
        jackrabbitRepository.shutdownRepository();
        //jackrabbitRepository.clearAll();
    }

    @Before
    public void setUp() throws Exception {
        jackrabbitRepository.login(jackrabbitRepository.getCredentials());
        jackrabbitRepository.clearData();
        brmsRepository = new RuleEngineRepositoryDroolsImpl(jackrabbitRepository.getRepository());
    }

    @After
    public void tearDown() throws Exception {
        jackrabbitRepository.logout();
    }
    
    private RuleSet createRuleSet(String name, String description, List<String> header) throws RuleEngineRepositoryException {
        RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(
                name, description, DroolsConstants.FORMAT_DRL);
        if ( header != null && !header.isEmpty()) {
            for( int i=0; i<header.size(); i++ ) {
                ruleSet.addHeader( header.get( i ) );
            }
        }
        return ruleSet;
    }

    private static Rule createRuleDRL(String name, String description, String category, String content) throws RuleEngineRepositoryException {
        Rule rule = RuleFactory.getInstance().createDroolsRule(
                name, description, category, content, DroolsConstants.FORMAT_DRL);
        return rule;
    }

    private RuleSet createSimpleRuleSet(String ruleSetName) 
        throws RuleSetExistsException, RuleExistsException {
        return createSimpleRuleSet( ruleSetName, null );
    }

    private RuleSet createSimpleRuleSet(String ruleSetName, String category) 
        throws RuleSetExistsException, RuleExistsException {
        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar");
        RuleSet ruleSet = createRuleSet(ruleSetName, "My new rule set", header);
        
        Rule rule = createRuleDRL("rule_1", "My new rule 1", category, 
                droolsTestUtil.getSimpleRule1());
        ruleSet.addRule(rule);

        return brmsRepository.createRuleSet(ruleSet);
    }
    
    private RuleSet createSimpleRuleSet(String ruleSetName, List<String> header, String ruleName, String[] categories, int rules) {
        RuleSet ruleSet = createRuleSet(ruleSetName, "A rule set", header);
        for(int i=0; i<rules; i++) {
            Rule rule = createRuleDRL(ruleName + i, "My new rule 1", null,
                    "rule \"rule_" + i + "\" \n when \n then \nend");
            for(String name : categories) {
                rule.addCategoryName(name);
            }
            ruleSet.addRule(rule);
        }
        return ruleSet;
    }

    private void assertRule( Rule rule1, Rule rule2) throws Exception {
        assertEquals( rule1.getName(), rule2.getName() );
        assertEquals( rule1.getContent(), rule2.getContent() );
    }

    private void assertRuleSet( RuleSet ruleSet1 , RuleSet ruleSet2) throws Exception {
        assertEquals( ruleSet1.getName(), ruleSet2.getName() );
        assertEquals( ruleSet1.getDescription(), ruleSet2.getDescription() );
        assertEquals( ruleSet1.getHeader(), ruleSet2.getHeader() );
    }

    @Test
    public void testCreateCategory() throws Exception {
        boolean b = brmsRepository.createCategory("/", "MyCategory", "A test category 1.0 description");
        assertTrue(b);
    }
    
    @Test
    public void testCreateDuplicateCategory() throws Exception {
        boolean b = brmsRepository.createCategory("/", "MyCategory", "A test category 1.0 description");
        assertTrue(b);
        try {
            b = brmsRepository.createCategory("/", "MyCategory", "A test category 1.0 description");
            fail( "Creating a duplicate category should have thrown an Exception" );
        } catch( CategoryExistsException e ) {
            assertTrue( true );
        }
    }
    
    @Test
    public void testLoadCategoriesThatDoesNotExist() throws Exception {
        try {
            brmsRepository.loadChildCategories("/MyCategory");
            fail( "Loading a category that doesn't exist should have thrown an Exception " );
        } catch( RuleEngineRepositoryException e ) {
            assertTrue( true );
        }
    }
    
    @Test
    public void testCreateAndLoadCategories() throws Exception {
        boolean b = brmsRepository.createCategory("/", "EnrollmentRules", "A test category 1.0 description");
        assertTrue(b);
        b = brmsRepository.createCategory("/EnrollmentRules", "Math", "A Math category description");
        assertTrue(b);
        b = brmsRepository.createCategory("/EnrollmentRules", "English", "An English category description");
        assertTrue(b);
        b = brmsRepository.createCategory("/EnrollmentRules/Math", "PreReq", "A PreReq category description");
        assertTrue(b);
        b = brmsRepository.createCategory("/EnrollmentRules/Math", "CoReq", "A CoReq category description");
        assertTrue(b);

        List<String> category = brmsRepository.loadChildCategories("/");
        // assertTrue( category.length == 1 );
        assertEquals("EnrollmentRules", category.get(0));

        category = brmsRepository.loadChildCategories("/EnrollmentRules");
        assertTrue(category.size() == 2);
        assertEquals("Math", category.get(0));
        assertEquals("English", category.get(1));

        category = brmsRepository.loadChildCategories("/EnrollmentRules/Math");
        assertTrue(category.size() == 2);
        assertEquals("PreReq", category.get(0));
        assertEquals("CoReq", category.get(1));
    }

    @Test
    public void testRemoveCategory() throws Exception {
        boolean b = brmsRepository.createCategory("/", "testRemoveCategory", 
                "A test category description");
        assertTrue(b);
        brmsRepository.removeCategory("testRemoveCategory");
        List<String> list = brmsRepository.loadChildCategories("/");
        assertFalse(list.contains("testRemoveCategory"));
    }

    @Test
    public void testCreateAndLoadCompiledRuleSetSnapshot() throws Exception {
        createSimpleRuleSet("MyRuleSet");
        
        brmsRepository.createRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1", 
                "Snapshot Version 1");
        
        org.drools.rule.Package binPkg = (org.drools.rule.Package) 
            brmsRepository.loadCompiledRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1");

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());
    }
    
    @Test
    public void testCreateRuleSetSnapshot() throws Exception {
        RuleSet ruleSet1 = createSimpleRuleSet("MyRuleSet");
        
        brmsRepository.createRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1", 
                "Snapshot Version 1");
        
        RuleSet ruleSet2 = brmsRepository.loadRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1");

        assertNotNull(ruleSet2);
        assertFalse(ruleSet1.equals(ruleSet2));
    }
    
    @Test
    public void testCreateInvalidRuleSetSnapshot_UpdateInvalidRule() throws Exception {
        // Create a valid rule set
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");
        Rule rule = brmsRepository.loadRule(ruleSet.getRules().get(0).getUUID());
        // Update with an invalid rule 
        rule.setContent("Some invalid rule source code");
        brmsRepository.updateRule(rule);
        brmsRepository.checkinRule(ruleSet.getRules().get(0).getUUID(), "Checkin invalid source");
    
        try {
            brmsRepository.createRuleSetSnapshot("MyRuleSet", "SNAPSHOT-1", "Create invalid snapshot 1");
            fail("Creating rule set snapshot should fail because of invalid rule: "+rule.getContent());
        } catch (RuleEngineRepositoryException e) {
            assertTrue(true);
        }
    }    

    @Test
    public void testCreateRuleSetSnapshotVersions() throws Exception {
        createSimpleRuleSet("MyRuleSet");
        
        String expectedCheckinComment = "Snapshot Version 1";
        brmsRepository.createRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1", 
                expectedCheckinComment);
        brmsRepository.createRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot2", 
                expectedCheckinComment);
        
        RuleSet ruleSet2 = brmsRepository.loadRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1");

        assertEquals( 2, ruleSet2.getVersionNumber() );
        assertEquals( expectedCheckinComment, ruleSet2.getCheckinComment() );
    }
    
    @Test
    public void testReplaceRuleSetSnapshot() throws Exception {
        createSimpleRuleSet("MyRuleSet");
        
        brmsRepository.createRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1", 
                "Snapshot Version 1");
        // replace snapshot
        String expectedCheckinComment = "Snapshot Version 2";
        brmsRepository.replaceRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1", 
                expectedCheckinComment);
        
        RuleSet ruleSet = brmsRepository.loadRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1");
        assertEquals( 2, ruleSet.getVersionNumber() );
        assertEquals( expectedCheckinComment, ruleSet.getCheckinComment() );
    }
    
    @Test
    public void testReplaceNonExistingRuleSetSnapshot() throws Exception {
        createSimpleRuleSet("MyRuleSet");
        
            try {
            brmsRepository.replaceRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1", 
                    "Snapshot Version 1");
            fail("Replacing non-existing snapshot should have failed");
        } catch (RuleEngineRepositoryException e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void testLoadCompiledRuleSetSnapshotAndExecuteSnapshot() throws Exception {
        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar");
        header.add("import org.kuali.student.rules.brms.repository.test.Message");
        RuleSet ruleSet = createRuleSet("MyPackage", "My package description", header);
        Rule rule1 = createRuleDRL("rule_1", "Email Initialization Rule", null, droolsTestUtil.getSimpleRule3() );
        ruleSet.addRule(rule1);
        Rule rule2 = createRuleDRL("rule_2", "Email Validation Rule", null, droolsTestUtil.getSimpleRule4() );
        ruleSet.addRule(rule2);
        String rulesetUUID = brmsRepository.createRuleSet(ruleSet).getUUID();

        org.drools.rule.Package binPkg = (org.drools.rule.Package) brmsRepository.loadCompiledRuleSet(rulesetUUID);

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());

        droolsTestUtil.executeRule(binPkg, new Object[]{Calendar.getInstance()});

        brmsRepository.createRuleSetSnapshot("MyPackage", "SNAPSHOT1", "A snapshot");

        binPkg = (org.drools.rule.Package) brmsRepository.loadCompiledRuleSetSnapshot("MyPackage", "SNAPSHOT1");

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());

        Message message = new Message();
        Calendar calendar = Calendar.getInstance();
        droolsTestUtil.executeRule(binPkg, new Object[]{message, calendar});
        assertTrue(message.getMessage().startsWith("Minute is "));
    }

    @Test
    public void testLoadCompiledRuleSetAndExecute() throws Exception {
        // Create category
        boolean b = brmsRepository.createCategory("/", 
                "testLoadCompiledRuleSetAndExecute", "A test category 1.0 description");
        assertTrue(b);

        List<String> header = new ArrayList<String>();
        header.add("import java.util.regex.Pattern");
        header.add("import org.kuali.student.rules.brms.repository.test.Email");
        header.add("import org.kuali.student.rules.brms.repository.test.Message");
        RuleSet ruleSet = createRuleSet("MyPackage", "My package description", header);
        Rule rule1 = createRuleDRL("rule_1", "Email Initialization Rule", "testLoadCompiledRuleSetAndExecute", droolsTestUtil.getValidationRule1() );
        ruleSet.addRule(rule1);
        Rule rule2 = createRuleDRL("rule_2", "Email Validation Rule", "testLoadCompiledRuleSetAndExecute", droolsTestUtil.getValidationRule2() );
        ruleSet.addRule(rule2);
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet).getUUID();
        
        // Must compile a ruleset before it will save a compiled ruleset
        CompilerResultList results = brmsRepository.compileRuleSet(ruleSetUUID);

        // No errors
        assertNull(ruleEngineUtil.getErrorMessage(results), results);

        org.drools.rule.Package binPkg = (org.drools.rule.Package) brmsRepository.loadCompiledRuleSet(ruleSetUUID);

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());

        Email email = new Email("len.carlsen@ubc.ca");
        Message message = new Message();
        droolsTestUtil.executeRule(binPkg, new Object[]{email, message});
        assertEquals("Valid Email Address: len.carlsen@ubc.ca", message.getMessage());
    }

    @Test
    public void testLoadCompiledRuleSetObject() throws Exception {
        String ruleSetUUID = createSimpleRuleSet("MyPackage").getUUID();

        // Must compile a ruleset before it will save a compiled ruleset
        // A ruleset doesn't need to be checked in before compiling
        CompilerResultList results = brmsRepository.compileRuleSet(ruleSetUUID);
        assertNull(ruleEngineUtil.getErrorMessage(results), results);

        RuleSet ruleset = brmsRepository.loadRuleSet(ruleSetUUID);
        assertNotNull(ruleset);
        assertNotNull(ruleset.getCompiledRuleSet());
        assertNotNull(ruleset.getCompiledRuleSetObject());
        Class c = ruleset.getCompiledRuleSetObject().getClass();
        assertEquals("org.drools.rule.Package", c.getName());
    }

    @Test
    public void testJSR94() throws Exception {
        // Create category
        boolean b = brmsRepository.createCategory("/", "testJSR94", "A test category 1.0 description");
        assertTrue(b);

        String ruleSetUUID = createSimpleRuleSet("MyPackage").getUUID();
        
        CompilerResultList results = brmsRepository.compileRuleSet(ruleSetUUID);

        // No errors
        assertNull(ruleEngineUtil.getErrorMessage(results), results);

        org.drools.rule.Package binPkg = (org.drools.rule.Package) brmsRepository.loadCompiledRuleSet(ruleSetUUID);

        assertNotNull(binPkg);

        // ******************************************************
        // * JSR 94 API for registering and executing rule sets *
        // ******************************************************

        String RULE_SERVICE_PROVIDER = "http://drools.org/";
        String RULE_URI = "MyPackage";

        RuleServiceProviderManager.registerRuleServiceProvider(RULE_SERVICE_PROVIDER, RuleServiceProviderImpl.class);

        RuleServiceProvider ruleServiceProvider = RuleServiceProviderManager.getRuleServiceProvider(RULE_SERVICE_PROVIDER);

        RuleAdministrator ruleAdministrator = ruleServiceProvider.getRuleAdministrator();

        LocalRuleExecutionSetProvider ruleSetProvider = ruleAdministrator.getLocalRuleExecutionSetProvider(null);

        RuleExecutionSet ruleExecutionSet = ruleSetProvider.createRuleExecutionSet(binPkg, null);

        ruleAdministrator.registerRuleExecutionSet(RULE_URI, ruleExecutionSet, null);

        RuleRuntime ruleRuntime = ruleServiceProvider.getRuleRuntime();

        StatelessRuleSession statelessRuleSession = (StatelessRuleSession) ruleRuntime.createRuleSession(RULE_URI, null, RuleRuntime.STATELESS_SESSION_TYPE);

        Calendar now = Calendar.getInstance();
        List<Calendar> facts = new ArrayList<Calendar>();
        facts.add(now);
        statelessRuleSession.executeRules(facts);
    }

    @Test
    public void testLoadRuleSetWithInvalidUUID() throws Exception {
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);
        String rulesetUUID = brmsRepository.createRuleSet(ruleSet1).getUUID();
        
        assertNotNull(rulesetUUID);

        RuleSet ruleSet2 = brmsRepository.loadRuleSet(rulesetUUID);
        assertEquals(ruleSet2.getUUID(), rulesetUUID);

        try {
            brmsRepository.loadRuleSet("XYZ");
            fail("Rule set UUID='XYZ' should not exist");
        } catch (RuleEngineRepositoryException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCreateRuleSet() throws Exception {
        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar");
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", header);

        Rule rule1 = createRuleDRL("MyRule1", "My new rule 1", null, 
                droolsTestUtil.getSimpleRule1());
        ruleSet1.addRule(rule1);

        RuleSet ruleSet2 = brmsRepository.createRuleSet(ruleSet1);
        assertNotNull(ruleSet2);
        assertTrue( ruleSet2.getUUID() != null && !ruleSet2.getUUID().isEmpty() );
    }

    @Test
    public void testCreateDuplicateRuleSet() throws Exception {
        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar");
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", header);

        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1).getUUID();
        assertTrue( ruleSetUUID != null && !ruleSetUUID.isEmpty() );

        // Duplicate rule set
        RuleSet ruleSet2 = createRuleSet("MyRuleSet", "My new rule set", header);

        try {
            ruleSetUUID = brmsRepository.createRuleSet(ruleSet2).getUUID();
            assertTrue( ruleSetUUID != null && !ruleSetUUID.isEmpty() );
            fail( "Creating a duplicate rule set should have thrown a RuleSetExistsException" );
        } catch( RuleSetExistsException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testCreateRuleSet_MissingImport() throws Exception {
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);

        Rule rule1 = createRuleDRL("MyRule1", "My new rule 1", null, 
                droolsTestUtil.getSimpleRule1());
        ruleSet1.addRule(rule1);

        try {
            brmsRepository.createRuleSet(ruleSet1);
            fail("Should not be able to create rule set without an header (import)");
        }
        catch(RuleEngineRepositoryException e) {
            assertTrue( true );
        }
    }

    @Test
    public void testCreateRuleSet_MultipleImport() throws Exception {
        List<String> header = new ArrayList<String>();
        header.add("import java.util.regex.Pattern");
        header.add("import org.kuali.student.rules.brms.repository.test.Email;");
        header.add("import org.kuali.student.rules.brms.repository.test.Message;");
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "Email Initialization Rule", header);

        Rule rule1 = createRuleDRL("MyRule1", "My new rule 1", null, 
                droolsTestUtil.getValidationRule1());
        ruleSet1.addRule(rule1);

        Rule rule2 = createRuleDRL("MyRule2", "My new rule 2", null, 
                droolsTestUtil.getValidationRule2());
        ruleSet1.addRule(rule2);

        RuleSet ruleSet2 = brmsRepository.createRuleSet(ruleSet1);
        assertNotNull(ruleSet2);
        assertTrue( ruleSet2.getUUID() != null && !ruleSet2.getUUID().isEmpty() );

        //RuleSet ruleSet2 = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals(header.get(0)+";", ruleSet2.getHeaderList().get(0));
        assertEquals(header.get(1), ruleSet2.getHeaderList().get(1));
        assertEquals(header.get(2), ruleSet2.getHeaderList().get(2));
    }

    @Test
    public void testCreateRuleSet_OneRuleCategory() throws Exception {
        String category = "TestCategory1";
        String path = "/";
        brmsRepository.createCategory("/", category, "A test category 1");
        
        RuleSet ruleSet = createSimpleRuleSet("RuleSet1", null, "rule_", new String[] {category}, 2);
        ruleSet = brmsRepository.createRuleSet(ruleSet);
        
        assertNotNull(ruleSet);
        String expected = ruleSet.getRules().get(0).getCategoryNames().get(0);
        assertEquals(category, expected);
        expected = ruleSet.getRules().get(0).getCategories().get(0).getName();
        String expectedPath = ruleSet.getRules().get(0).getCategories().get(0).getPath();
        assertEquals(category, expectedPath);
    }
    
    @Test
    public void testCreateRuleSet_TwoRuleCategory() throws Exception {
        String category1 = "TestCategory1";
        brmsRepository.createCategory("/", category1, "A test category 1");
        String category2 = "TestCategory2";
        brmsRepository.createCategory("/", category2, "A test category 2");
        
        RuleSet ruleSet = createSimpleRuleSet("RuleSet1", null, "rule_", new String[] {category1,category2}, 2);
        ruleSet = brmsRepository.createRuleSet(ruleSet);

        assertNotNull(ruleSet);
        List<String> expected = ruleSet.getRules().get(0).getCategoryNames();
        assertTrue(expected.contains(category1));
        expected = ruleSet.getRules().get(0).getCategoryNames();
        assertTrue(expected.contains(category2));
    }
    
    @Test
    public void testCreateAndLoadRuleSet() throws Exception {
        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar");
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", header);

        String ruleCategory = null; //"MyCategory";
        
        Rule rule1 = createRuleDRL("MyRule1", "My new rule 1", ruleCategory, 
                droolsTestUtil.getSimpleRule1());
        ruleSet1.addRule(rule1);

        Rule rule2 = createRuleDRL("MyRule2", "My new rule 2", ruleCategory, 
                droolsTestUtil.getSimpleRule2());
        ruleSet1.addRule(rule2);

        //brmsRepository.createCategory("/", ruleCategory, "My new rule category");
        
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1).getUUID();
        assertTrue( ruleSetUUID != null && !ruleSetUUID.isEmpty() );
        
        RuleSet ruleSet2 = brmsRepository.loadRuleSet(ruleSetUUID);
        // Rule Set 
        assertRuleSet(ruleSet1, ruleSet2);
        // Rule 1 
        assertRule(ruleSet1.getRules().get(0), ruleSet2.getRules().get(0));
        // Rule 2 
        assertRule(ruleSet1.getRules().get(1), ruleSet2.getRules().get(1));
    }

    @Test
    public void testCreateAndLoadRuleSetByName() throws Exception {
        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar");
        RuleSet ruleSet = createRuleSet("MyRuleSet", "My new rule set", header);

        String ruleCategory = null; //"MyCategory";
        
        Rule rule1 = createRuleDRL("MyRule1", "My new rule 1", ruleCategory, 
                droolsTestUtil.getSimpleRule1());
        ruleSet.addRule(rule1);

        Rule rule2 = createRuleDRL("MyRule2", "My new rule 2", ruleCategory, 
                droolsTestUtil.getSimpleRule2());
        ruleSet.addRule(rule2);

        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet).getUUID();
        assertTrue( ruleSetUUID != null && !ruleSetUUID.isEmpty() );
        
        RuleSet ruleSet2 = brmsRepository.loadRuleSetByName(ruleSet.getName());
        // Rule Set 
        assertRuleSet(ruleSet, ruleSet2);
        // Rule 1 
        assertRule(ruleSet.getRules().get(0), ruleSet2.getRules().get(0));
        // Rule 2 
        assertRule(ruleSet.getRules().get(1), ruleSet2.getRules().get(1));
    }

    @Test
    public void testCheckinRuleSet() throws Exception {
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);
        
        // Rule Set Version 1
        RuleSet ruleSet = brmsRepository.createRuleSet(ruleSet1);

        //RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals( 1L, ruleSet.getVersionNumber());

        // Rule Set Version 2
        brmsRepository.checkinRuleSet(ruleSet.getUUID(), "Checkin Rule Set Version 2");
        
        ruleSet = brmsRepository.loadRuleSet(ruleSet.getUUID());
        assertEquals(2L, ruleSet.getVersionNumber());
    }

    @Test
    public void testUpdateRuleSetHeader() throws Exception {
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);
        
        // Rule Set Version 1
        RuleSet ruleSet = brmsRepository.createRuleSet(ruleSet1);

        //RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals(1L, ruleSet.getVersionNumber());

        // Update Rule Set with new header
        String header = "import java.util.Calendar;";
        ruleSet.addHeader(header);

        brmsRepository.updateRuleSet(ruleSet);
        
        ruleSet = brmsRepository.loadRuleSet(ruleSet.getUUID());
        assertEquals(header, ruleSet.getHeader().trim());
    }
    
    @Test
    public void testUpdateRuleSetWithOneRules() throws Exception {
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);
        
        // Rule Set Version 1
        RuleSet ruleSet = brmsRepository.createRuleSet(ruleSet1);

        //RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals(1L, ruleSet.getVersionNumber());

        Rule rule1 = createRuleDRL("rule_1", "My new rule 1", null, 
                droolsTestUtil.getSimpleRule1());
        ruleSet.addRule(rule1);
        
        ruleSet = brmsRepository.updateRuleSet(ruleSet);
        
        //ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals(1,ruleSet.getRules().size());
        String expectedContent = rule1.getContent();
        String actualContent = ruleSet.getRules().get(0).getContent();
        assertEquals(expectedContent, actualContent);
    }
    
    @Test
    public void testUpdateRuleSetWithTwoRules() throws Exception {
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);
        
        // Rule Set Version 1
        RuleSet ruleSet = brmsRepository.createRuleSet(ruleSet1);

        //RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals(1L, ruleSet.getVersionNumber());

        Rule rule1 = createRuleDRL("rule_1", "My new rule 1", null, 
                droolsTestUtil.getSimpleRule1());
        ruleSet.addRule(rule1);
        ruleSet = brmsRepository.updateRuleSet(ruleSet);

        Rule rule2 = createRuleDRL("rule_2", "My new rule 1", null, 
                droolsTestUtil.getSimpleRule2());
        ruleSet.addRule(rule2);
        ruleSet = brmsRepository.updateRuleSet(ruleSet);

        assertEquals(2,ruleSet.getRules().size());
        String expectedContent = rule2.getContent();
        String actualContent = ruleSet.getRules().get(1).getContent();
        assertEquals(expectedContent, actualContent);
    }
    
    @Test
    public void testUpdateRuleSetWithDuplicateRules() throws Exception {
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);
        
        // Rule Set Version 1
        RuleSet ruleSet = brmsRepository.createRuleSet(ruleSet1);

        //RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals(1L, ruleSet.getVersionNumber());

        Rule rule1 = createRuleDRL("rule_1", "My new rule 1", null, 
                droolsTestUtil.getSimpleRule1());
        ruleSet.addRule(rule1);
        
        ruleSet = brmsRepository.updateRuleSet(ruleSet);
        
        Rule rule2 = createRuleDRL("rule_1", "My new rule 1", null, 
                droolsTestUtil.getSimpleRule1());
        ruleSet.addRule(rule2);
        
        try {
            brmsRepository.updateRuleSet(ruleSet);
            fail("Update rule set should have thrown a RuleExistsException");
        } catch (RuleExistsException e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void testLoadCompiledRuleSet() throws Exception {
        String ruleSetUUID = createSimpleRuleSet("MyRuleSet").getUUID();
        
        org.drools.rule.Package binPkg = (org.drools.rule.Package) 
            brmsRepository.loadCompiledRuleSet(ruleSetUUID);

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());
    }
    
    @Test
    public void testLoadCompiledRuleSetAsBytes() throws Exception {
        String ruleSetUUID = createSimpleRuleSet("MyRuleSet").getUUID();
        
        byte[] bytes = brmsRepository.loadCompiledRuleSetAsBytes(ruleSetUUID);
        assertNotNull(bytes);
    }
    
    @Test
    public void testLoadCompiledRuleSetByName() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");
        
        org.drools.rule.Package binPkg = (org.drools.rule.Package) 
            brmsRepository.loadCompiledRuleSetByName(ruleSet.getName());

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());
    }
    
    @Test
    public void testLoadCompiledRuleSetAsBytesByName() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");
        
        byte[] bytes = brmsRepository.loadCompiledRuleSetAsBytesByName(ruleSet.getName());
        assertNotNull(bytes);
    }
    
    @Test
    public void testCreateRule() throws Exception {
        RuleSet ruleSet2 = createSimpleRuleSet("MyPackage");

        List<Rule> list = ruleSet2.getRules();
        assertNotNull(list);
        assertTrue(list.size() > 0);

        brmsRepository.checkinRule(list.get(0).getUUID(), "Checkin rule comments");

        Rule rule = brmsRepository.loadRule(list.get(0).getUUID());
        assertEquals(rule.getContent(), droolsTestUtil.getSimpleRule1());
        assertEquals(rule.getName(), "rule_1");
        assertEquals(rule.getDescription(), "My new rule 1");
        assertEquals(rule.getCheckinComment(), "Checkin rule comments");
    }

    @Test
    public void testCreateDuplicateRule() throws Exception {
        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar");
        RuleSet ruleSet = createRuleSet("MyRuleSet", "My new rule set", header);

        Rule rule = createRuleDRL("MyRule1", "My new rule 1", null, 
                droolsTestUtil.getSimpleRule1());
        ruleSet.addRule(rule);
        String uuid = brmsRepository.createRuleSet(ruleSet).getUUID();

        // Create duplicate rule
        RuleSet duplicateRuleSet = createRuleSet("MyDuplicateRule", "My new rule set", header);
        Rule duplicateRule = createRuleDRL("MyRule1", "My new rule 1", null, 
                droolsTestUtil.getSimpleRule1());
        duplicateRuleSet.addRule(duplicateRule);
        String duplicateUUID = brmsRepository.createRuleSet(duplicateRuleSet).getUUID();
        
        ruleSet = brmsRepository.loadRuleSet(uuid);
        duplicateRuleSet = brmsRepository.loadRuleSet(duplicateUUID);
        assertFalse(ruleSet.equals(duplicateRuleSet));

        rule = ruleSet.getRules().get(0);
        duplicateRule = duplicateRuleSet.getRules().get(0);
        assertEquals(rule.getContent(), duplicateRule.getContent());
    }

    @Test
    public void testCheckinRule() throws Exception {
        String ruleCategory = "MyCategory";
        brmsRepository.createCategory("/", ruleCategory, "My new rule category");

        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);

        Rule rule = createRuleDRL("MyRule1", "My new rule 1", ruleCategory, 
                "rule \"xxx\" when then end");
        ruleSet1.addRule( rule );
        
        // Rule Set Version 1
        RuleSet ruleSet2 = brmsRepository.createRuleSet(ruleSet1);

        // Rule Version 1
        //RuleSet ruleSet2 = brmsRepository.loadRuleSet(ruleSetUUID);
        brmsRepository.checkinRule(ruleSet2.getRules().get(0).getUUID(), "Checkin Rule Version 1");
        
        RuleSet ruleSet3 = brmsRepository.loadRuleSet(ruleSet2.getUUID());
        assertEquals( 1L, ruleSet3.getRules().get(0).getVersionNumber());
    }
    
    @Test
    public void testUpdateRule() throws Exception {
        String ruleCategory = "MyCategory";
        brmsRepository.createCategory("/", ruleCategory, "My new rule category");

        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);

        String oldContent = "rule \"xxx\" when then end";
        Rule rule = createRuleDRL("MyRule1", "My new rule 1", ruleCategory, 
                oldContent);
        ruleSet1.addRule( rule );
        
        // Create Rule
        RuleSet ruleSet2 = brmsRepository.createRuleSet(ruleSet1);
        //RuleSet ruleSet2 = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals( oldContent, ruleSet2.getRules().get(0).getContent());

        // Update Rule
        String newContent = "rule \"new_rule\" when then end";
        Rule newRule = ruleSet2.getRules().get(0);
        newRule.setContent(newContent);
        Rule updatedRule = brmsRepository.updateRule(newRule);
        
        //Rule updatedRule = brmsRepository.loadRule(newRule.getUUID());
        assertNotNull(updatedRule.getUUID());
        assertEquals(newContent, updatedRule.getContent());
    }

    @Test
    public void testRenameRuleSet() throws Exception {
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1).getUUID();
        assertNotNull(ruleSetUUID);

        String newUUID = brmsRepository.renameRuleSet(ruleSetUUID, 
                "testRenameRuleSet_NEW_NAME");
        RuleSet ruleSet = brmsRepository.loadRuleSet(newUUID);
        assertEquals(ruleSet.getUUID(), newUUID);
        assertEquals(ruleSet.getName(), "testRenameRuleSet_NEW_NAME");
    }

    @Test
    public void testRenameRule() throws Exception {
        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar");
        RuleSet ruleSet1 = createRuleSet("MyPackage", "My package description", header);
        Rule rule1 = createRuleDRL("rule_1", "Email Initialization Rule", null, droolsTestUtil.getSimpleRule1() );
        ruleSet1.addRule(rule1);
        RuleSet ruleSet2 = brmsRepository.createRuleSet(ruleSet1);

        //RuleSet ruleSet2 = brmsRepository.loadRuleSet(ruleSetUUID);

        String newRuleUUID = brmsRepository.renameRule(ruleSet2.getRules().get(0).getUUID(), "rule_1_NEW_NAME");
        Rule rule2 = brmsRepository.loadRule(newRuleUUID);
        assertEquals(rule2.getUUID(), newRuleUUID);
        assertEquals(rule2.getName(), "rule_1_NEW_NAME");
    }

    @Test
    public void testCreateStates() throws Exception {
        brmsRepository.createStatus("Active");

        String[] states = brmsRepository.loadStates();

        assertEquals(2, states.length);
        assertEquals("Draft", states[0]); // default state is Draft
        assertEquals("Active", states[1]);
    }

    @Test
    public void testLoadStates() throws Exception {
        brmsRepository.createStatus("Active");
        brmsRepository.createStatus("Inactive");

        String[] states = brmsRepository.loadStates();

        assertEquals(3, states.length);
        assertEquals("Draft", states[0]); // default state is Draft
        assertEquals("Active", states[1]);
        assertEquals("Inactive", states[2]);
    }

    @Test
    public void testLoadDefaultStatus() throws Exception {
        String[] states = brmsRepository.loadStates();
        assertEquals(1, states.length);
        assertEquals("Draft", states[0]); // default state is Draft
    }

    @Test
    public void testChangeRuleSetStatus() throws Exception {
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1).getUUID();

        brmsRepository.createStatus("Active");
        brmsRepository.createStatus("Inactive");

        brmsRepository.changeRuleSetStatus(ruleSetUUID, "Active");
        RuleSet ruleSet2 = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals(ruleSet2.getStatus(), "Active");

        brmsRepository.changeRuleSetStatus(ruleSetUUID, "Inactive");
        ruleSet2 = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals(ruleSet2.getStatus(), "Inactive");
    }

    @Test
    public void testChangeRuleStatus() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");
        Rule rule1 = ruleSet.getRules().get(0);
        
        brmsRepository.createStatus("Active");
        brmsRepository.createStatus("Inactive");

        brmsRepository.changeRuleStatus(rule1.getUUID(), "Active");
        Rule rule2 = brmsRepository.loadRule(rule1.getUUID());
        assertEquals(rule2.getStatus(), "Active");

        brmsRepository.changeRuleStatus(rule1.getUUID(), "Inactive");
        rule2 = brmsRepository.loadRule(rule1.getUUID());
        assertEquals(rule2.getStatus(), "Inactive");
    }

    @Test
    public void testCompileValidRuleSetSource() throws Exception {
        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar");
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", header);
        Rule rule1 = createRuleDRL("rule_1", "My new rule 1", null, droolsTestUtil.getSimpleRule1() );
        ruleSet1.addRule(rule1);
        Rule rule2 = createRuleDRL("rule_2", "My new rule 1", null, droolsTestUtil.getSimpleRule2() );
        ruleSet1.addRule(rule2);
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1).getUUID();

        try {
            String drl1 = droolsTestUtil.getSimpleDRL("MyRuleSet");
            String drl2 = brmsRepository.compileRuleSetSource(ruleSetUUID);
            assertEquals(drl1, drl2);
        } catch (RuleEngineRepositoryException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateInvalidRuleSetSource() throws Exception {
        try {
            List<String> header = new ArrayList<String>();
            header.add("import jav.util.Calend");
            RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", header);
            Rule rule1 = createRuleDRL("rule_1", "My new rule 1", null, droolsTestUtil.getSimpleRule1() );
            ruleSet1.addRule(rule1);
            brmsRepository.createRuleSet(ruleSet1);
        } catch (RuleEngineRepositoryException e) {
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    public void testCompileInvalidRuleSetSource() throws Exception {
        try {
            RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");
            ruleSet.addHeader("import jav.uti.Calend");
            brmsRepository.updateRuleSet(ruleSet);
            
            String drl = brmsRepository.compileRuleSetSource(ruleSet.getUUID());
            fail("Invalid rule should throw an exception: drl=\n" + drl);
        } catch (RuleEngineRepositoryException e) {
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    public void testCompileValidRuleSet() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");

        try {
            CompilerResultList results = brmsRepository.compileRuleSet(ruleSet.getUUID());
            assertNull(results);
        } catch (RuleEngineRepositoryException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCompileInvalidRuleSet() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");

        Rule rule = brmsRepository.loadRule(ruleSet.getRules().get(0).getUUID());
        rule.setContent("InvalidRuleSource");
        brmsRepository.updateRule(rule);
        brmsRepository.checkinRule(rule.getUUID(), "Some invalid source");

        try {
            CompilerResultList results = brmsRepository.compileRuleSet(ruleSet.getUUID());
            assertNotNull(results);
            assertTrue(results.toString().indexOf("InvalidRuleSource") > -1);
        } catch (RuleEngineRepositoryException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRemoveRule() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");
        String ruleUUID = ruleSet.getRules().get(0).getUUID();
        
        brmsRepository.removeRule(ruleUUID);

        try {
            brmsRepository.loadRule(ruleUUID);
            fail("Rule should not exist: UUID="+ruleUUID);
        } catch (RuleEngineRepositoryException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testRemoveRuleSet() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");
        String ruleSetUUID = ruleSet.getUUID();
        
        brmsRepository.removeRuleSet(ruleSetUUID);

        try {
            brmsRepository.loadRuleSet(ruleSetUUID);
            fail("Rule set should not exist: UUID="+ruleSetUUID);
        } catch (RuleEngineRepositoryException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testArchiveRuleSet() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");
        brmsRepository.checkinRule(ruleSet.getRules().get(0).getUUID(), "A new rule");

        // Archived Rule set
        brmsRepository.archiveRuleSet(ruleSet.getUUID(), "Archive rule set");
        assertTrue(brmsRepository.loadRuleSet(ruleSet.getUUID()).isArchived());
    }

    @Test
    public void testUnArchiveRuleSet() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");
        
        brmsRepository.checkinRule(ruleSet.getRules().get(0).getUUID(), "A new rule");

        // Archived Rule set
        brmsRepository.archiveRuleSet(ruleSet.getUUID(), "Archive rule set");

        assertTrue(brmsRepository.loadRuleSet(ruleSet.getUUID()).isArchived());

        // UnArchived Rule set
        brmsRepository.unArchiveRuleSet(ruleSet.getUUID(), "Unarchive rule set");

        assertFalse(brmsRepository.loadRuleSet(ruleSet.getUUID()).isArchived());
    }

    /*
     * TODO Fix this archive/unarchive rule set error @Test public void testLoadArchivedRuleSet() throws Exception { String
     * ruleSetUUID = createRuleSet( "testLoadArchivedRuleSet", null, false ); RuleSet ruleSet = brmsRepository.loadRuleSet(
     * ruleSetUUID ); assertEquals( ruleSetUUID, ruleSet.getUUID() ); String ruleSource = droolsTestUtil.getSimpleRules();
     * String ruleUUID = brmsRepository.createRule( ruleSetUUID, "rule_1", "Rule set description", ruleSource, null );
     * brmsRepository.checkinRule( ruleUUID, null ); // Archived Rule set brmsRepository.archiveRuleSet( ruleSetUUID, null );
     * List<RuleSet> list = brmsRepository.loadArchivedRuleSets(); assertNotNull( list ); assertTrue( list.size() > 0 );
     * assertEquals( ruleSet.getUUID(), list.get( 0 ).getUUID() ); assertEquals( !ruleSet.isArchived(), list.get( 0
     * ).isArchived() ); assertEquals( "Archived", list.get( 0 ).getCheckinComment() ); // UnArchived Rule set
     * brmsRepository.unArchiveRuleSet( ruleSetUUID, null ); RuleSet ruleSet2 = brmsRepository.loadRuleSet( ruleSetUUID );
     * assertEquals( ruleSet.getUUID(), ruleSet2.getUUID() ); assertEquals( !ruleSet.isArchived(), !ruleSet2.isArchived() );
     * assertEquals( "Unarchived", ruleSet2.getCheckinComment() ); }
     */

    @Test
    @Ignore( "loadArchivedRuleSets method is not implemented beacuse of a Drools bug" )
    public void testLoadArchivedRuleSet_FailTest() throws Exception {
        try {
            brmsRepository.loadArchivedRuleSets();
            fail("Loading archived rule sets should not have been implemented");
        } catch (RuleEngineRepositoryException e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void testArchiveUnArchiveRule() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");
        Rule rule = ruleSet.getRules().get(0);

        assertFalse(brmsRepository.loadRule(rule.getUUID()).isArchived());
        // Archived Rule
        String expectedArchiveComment = "Archive rule";
        brmsRepository.archiveRule(rule.getUUID(), expectedArchiveComment);
        assertTrue(brmsRepository.loadRule(rule.getUUID()).isArchived());

        List<Rule> list = brmsRepository.loadArchivedRules();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(rule.getUUID(), list.get(0).getUUID());
        assertEquals(!rule.isArchived(), list.get(0).isArchived());
        assertEquals(expectedArchiveComment, list.get(0).getCheckinComment());

        // UnArchived Rule
        String expectedUnarchiveComment = "Unarchive rule";
        brmsRepository.unArchiveRule(rule.getUUID(), expectedUnarchiveComment);

        Rule rule2 = brmsRepository.loadRule(rule.getUUID());
        assertEquals(rule.getUUID(), rule2.getUUID());
        assertEquals(!rule.isArchived(), !rule2.isArchived());
        assertEquals(expectedUnarchiveComment, rule2.getCheckinComment());
    }

    @Test
    public void testLoadRuleHistory() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");
        String ruleUUID = ruleSet.getRules().get(0).getUUID();
        
        // Version 1
        Rule rule2 = brmsRepository.loadRule(ruleUUID);
        brmsRepository.checkinRule(rule2.getUUID(), "Checkin version 1");

        // Version 2
        Rule rule3 = brmsRepository.loadRule(ruleUUID);
        brmsRepository.checkinRule(rule3.getUUID(), "Checkin version 2");

        // Version 3
        Rule rule4Head = brmsRepository.loadRule(ruleUUID);
        brmsRepository.checkinRule(rule4Head.getUUID(), "Checkin version 3 - HEAD version");

        List<Rule> ruleHistory = brmsRepository.loadRuleHistory(ruleUUID);
        assertEquals(2, ruleHistory.size());

        long version1 = ruleHistory.get(0).getVersionNumber();
        long version2 = ruleHistory.get(1).getVersionNumber();
        assertTrue(version1 != version2);
    }

    
    @Ignore // Implementation of loadRuleSetHistory has a bug
    @Test 
    public void testLoadRuleSetHistory() throws Exception { 
        String ruleSetUUID = createRuleSet("testLoadRuleSetHistory", "My new rule set",
                Arrays.asList("import java.util.Calendar")).getUUID();
        // String ruleUUID = createRule( ruleSetUUID, "testLoadRuleSetHistory", false );
        // Version 1 
        RuleSet ruleset1 = brmsRepository.loadRuleSet( ruleSetUUID );
        brmsRepository.checkinRuleSet(ruleset1.getUUID(), "Checkin version 1");
        // Version 2 
        RuleSet ruleset2 = brmsRepository.loadRuleSet( ruleSetUUID );
        brmsRepository.checkinRuleSet(ruleset2.getUUID(), "Checkin version 2");
        // Version 3 
        RuleSet ruleset3Head = brmsRepository.loadRuleSet( ruleSetUUID );
        brmsRepository.checkinRuleSet(ruleset3Head.getUUID(), "Checkin version 3 - HEAD version");
        List<RuleSet> rulesetHistory = brmsRepository.loadRuleSetHistory(ruleSetUUID);
        assertEquals(2, rulesetHistory.size());
        long version1 = rulesetHistory.get(0).getVersionNumber();
        long version2 = rulesetHistory.get(1).getVersionNumber();
        assertTrue(version1 != version2);
    }

    @Test
    public void testLoadRuleHistoryAndRestore() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");
        String ruleUUID = ruleSet.getRules().get(0).getUUID();
        
        // Version 1
        Rule rule2 = brmsRepository.loadRule(ruleUUID);
        brmsRepository.checkinRule(rule2.getUUID(), "Checkin version 1");

        // Version 2
        Rule rule3 = brmsRepository.loadRule(ruleUUID);
        brmsRepository.checkinRule(rule3.getUUID(), "Checkin version 2");

        // Version 3
        Rule rule4Head = brmsRepository.loadRule(ruleUUID);
        brmsRepository.checkinRule(rule4Head.getUUID(), "Checkin version 3 - HEAD version");

        List<Rule> ruleHistory = brmsRepository.loadRuleHistory(ruleUUID);
        assertEquals(2, ruleHistory.size());

        long version1 = ruleHistory.get(0).getVersionNumber();
        long version2 = ruleHistory.get(1).getVersionNumber();
        assertTrue(version1 != version2);

        Rule head = brmsRepository.loadRule(ruleUUID);
        brmsRepository.restoreVersion(ruleHistory.get(0).getVersionSnapshotUUID(), head.getUUID(), "Restoring head version 3 with version 1");

        // Version 4
        Rule rule = brmsRepository.loadRule(ruleUUID);
        assertEquals("Restoring head version 3 with version 1", rule.getCheckinComment());
        assertEquals(4, rule.getVersionNumber());
    }

    @Test
    public void testRebuildAllSnapshots() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("testRebuildAllSnapshots");
        
        brmsRepository.createRuleSetSnapshot("testRebuildAllSnapshots", "SNAPSHOT-1", "Build snapshot 1");

        long snapshotTime1 = brmsRepository.loadRuleSetSnapshot("testRebuildAllSnapshots", "SNAPSHOT-1").getLastModifiedDate().getTimeInMillis();

        brmsRepository.rebuildAllSnapshots();

        long snapshotTime2 = brmsRepository.loadRuleSetSnapshot("testRebuildAllSnapshots", "SNAPSHOT-1").getLastModifiedDate().getTimeInMillis();

        assertTrue(snapshotTime2 > snapshotTime1);
    }

    @Test
    public void testExportImportAsXmlRulesRepository() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");

        try {
            // Export repository
            byte[] export = brmsRepository.exportRulesRepositoryAsXml();
            brmsRepository.removeRule(ruleSet.getRules().get(0).getUUID());
            brmsRepository.importRulesRepositoryAsXml(export);
            assertTrue(true);
        } catch (RuleEngineRepositoryException e) {
            fail(e.getMessage());
        }

        RuleSet ruleSet2 = brmsRepository.loadRuleSet(ruleSet.getUUID());
        assertFalse(ruleSet2.getUUID() == null);
    }

    @Test
    public void testExportImportRulesRepository() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");

        try {
            // Export repository
            ByteArrayOutputStream export = brmsRepository.exportRulesRepositoryAsZip("repository-export.xml");
            assertNotNull(export);

            byte[] byteExport = toByteArrayFromZip(export, "repository-export.xml");
            assertNotNull(byteExport);

            brmsRepository.removeRule(ruleSet.getRules().get(0).getUUID());
            brmsRepository.importRulesRepositoryAsXml(byteExport);
            assertTrue(true);
        } catch (RuleEngineRepositoryException e) {
            fail(e.getMessage());
        }

        RuleSet ruleSet2 = brmsRepository.loadRuleSet(ruleSet.getUUID());
        assertFalse(ruleSet2.getUUID() == null);
    }

    private byte[] toByteArrayFromZip(ByteArrayOutputStream baos, String filename) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(baos.toByteArray());
        ZipInputStream zis = new ZipInputStream(bin);

        ZipEntry entry = zis.getNextEntry();
        assertEquals(entry.getName(), filename);
        assertFalse(entry.isDirectory());

        // Print XML repository file
        // FileOutputStream out = new FileOutputStream("C:/repository-export.zip");
        // out.write( export.toByteArray() );
        // out.close();

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = zis.read(buf)) > 0) {
            bout.write(buf, 0, len);
        }
        return bout.toByteArray();
    }

    @Test
    public void testCompileSourceValid() throws Exception {
        String packageName = "testCompileSourceValid";
        Reader drl = new StringReader(droolsTestUtil.getSimpleDRL(packageName));
        try {
            org.drools.rule.Package pkg = (org.drools.rule.Package) brmsRepository.compileSource(drl);
            assertTrue(pkg != null);
            assertTrue(pkg.isValid());
            assertTrue(pkg.getName().equals(packageName));
        } catch (RuleEngineRepositoryException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCompileSourceInvalidMissingPackage() throws Exception {
        String src = "Some invalid source code";
        Reader drl = new StringReader(src);
        try {
            brmsRepository.compileSource(drl);
            fail("Compilation should fail because of invalid rule");
        } catch (RuleEngineRepositoryException e) {
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    public void testCompileSourceInvalidPackage() throws Exception {
        String src = "package x y z";
        Reader drl = new StringReader(src);

        try {
            brmsRepository.compileSource(drl);
            fail("compilation should fail because of an invalid package");
        } catch (RuleEngineRepositoryException e) {
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    public void testCompileSourceInvalidImport() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("package testCompileSourceInvalidImport");
        sb.append("\n");
        sb.append("rule \"HelloDrools\"");
        sb.append("\n");
        sb.append("     when");
        sb.append("\n");
        sb.append("          now: Calendar()");
        sb.append("\n");
        sb.append("          eval ( ( now.get(Calendar.MINUTE) % 2 == 0 ) )");
        sb.append("\n");
        sb.append("     then");
        sb.append("\n");
        sb.append("          System.out.println(\"Minute is even \" + now.get(Calendar.MINUTE));");
        sb.append("\n");
        sb.append("end");

        Reader drl = new StringReader(sb.toString());

        try {
            brmsRepository.compileSource(drl);
            fail("compilation should fail because of a missing import");
        } catch (RuleEngineRepositoryException e) {
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    public void testCompileSourceInvalidRule() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("package testCompileSourceInvalidRule");
        sb.append("\n");
        sb.append("rule \"HelloDrools\"");
        sb.append("\n");
        sb.append("     when");
        sb.append("\n");
        sb.append("          now( x = 1 )");
        sb.append("\n");
        sb.append("     then");
        sb.append("\n");
        sb.append("          System.out.println(\"Success \");");
        sb.append("\n");
        sb.append("end");

        Reader drl = new StringReader(sb.toString());

        try {
            brmsRepository.compileSource(drl);
            fail("compilation should fail because of an invalid rule");
        } catch (RuleEngineRepositoryException e) {
            assertTrue(e.getMessage() != null);
        }
    }

    private boolean containsCategory(List<Category> categories, String categoryName, String path) {
        for(Category category : categories) {
            if(categoryName.equals(category.getName()) && path.equals(category.getPath())) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testLoadRuleSetByCategory() throws Exception {
        String category = "TestCategory";
        String path = "/";
        brmsRepository.createCategory(path, category, "A test category");
        
        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar;");

        RuleSet ruleSet1 = createSimpleRuleSet("RuleSet1", header, "ruleA_", new String[] {category}, 2);
        ruleSet1 = brmsRepository.createRuleSet(ruleSet1);
        RuleSet ruleSet2 = createSimpleRuleSet("RuleSet2", null, "ruleB_", new String[] {category}, 2);
        ruleSet2 = brmsRepository.createRuleSet(ruleSet2);
        RuleSet ruleSet3 = createSimpleRuleSet("RuleSet3", header, "ruleC_", new String[] {category}, 2);
        ruleSet3 = brmsRepository.createRuleSet(ruleSet3);
        
        RuleSet ruleSet = brmsRepository.loadRuleSetByCategory(category);
        
        assertEquals(6, ruleSet.getRules().size());

        assertEquals(1, ruleSet.getHeaderList().size());
        assertEquals(header, ruleSet.getHeaderList());
        
        List<Category> rule1Category = ruleSet.getRules().get(0).getCategories();
        List<Category> rule2Category = ruleSet.getRules().get(1).getCategories();

        assertTrue(containsCategory(rule1Category,category,category));
        assertTrue(containsCategory(rule2Category,category,category));
        
        assertTrue( ruleSet.getRules().get(0).getCategoryNames().contains(category) );
        assertTrue( ruleSet.getRules().get(1).getCategoryNames().contains(category) );
    }
}
