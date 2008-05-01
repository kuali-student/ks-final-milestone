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
package org.kuali.student.brms.repository;

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
import org.kuali.student.brms.repository.drools.RuleEngineRepositoryDroolsImpl;
import org.kuali.student.brms.repository.drools.DroolsTestUtil;
import org.kuali.student.brms.repository.drools.DroolsJackrabbitRepository;
import org.kuali.student.brms.repository.drools.rule.RuleFactory;
import org.kuali.student.brms.repository.drools.rule.RuleSetFactory;
import org.kuali.student.brms.repository.exceptions.CategoryExistsException;
import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.brms.repository.exceptions.RuleExistsException;
import org.kuali.student.brms.repository.exceptions.RuleSetExistsException;
import org.kuali.student.brms.repository.rule.CompilerResultList;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleSet;
import org.kuali.student.brms.repository.test.Email;
import org.kuali.student.brms.repository.test.Message;

/**
 * This is a Drools rules engine repository test class. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleEngineRepositoryTest {

    private static DroolsJackrabbitRepository jackrabbitRepository;

    private static RuleEngineRepository brmsRepository;

    @BeforeClass
    public static void setUpOnce() throws Exception {
        URL url = RuleEngineRepositoryTest.class.getResource("/repository");
        jackrabbitRepository = new DroolsJackrabbitRepository(url);
        jackrabbitRepository.clearAll();
        jackrabbitRepository.initialize();
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
        jackrabbitRepository.shutdownRepository();
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

    private RuleSet createRuleSet(String name, String description, List<String> facts) throws RuleEngineRepositoryException {
        RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(name);
        ruleSet.setDescription(description);
        ruleSet.setFormat("drl");
        if ( facts != null && !facts.isEmpty()) {
            for( int i=0; i<facts.size(); i++ ) {
                ruleSet.addHeader( facts.get( i ) );
            }
        }
        return ruleSet;
    }

    private static Rule createRuleDRL(String name, String description, String category, String content) throws RuleEngineRepositoryException {
        Rule rule = RuleFactory.getInstance().createDroolsRule(name);
        rule.setDescription(description);
        rule.setCategory(category);
        rule.setFormat("drl");
        rule.setContent(content);
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
                DroolsTestUtil.getSimpleRule1());
        ruleSet.addRule(rule);

        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet);
        return brmsRepository.loadRuleSet(ruleSetUUID);
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
                false, "Snapshot Version 1");
        
        org.drools.rule.Package binPkg = (org.drools.rule.Package) 
            brmsRepository.loadCompiledRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1");

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());
    }
    
    @Test
    public void testCreateRuleSetSnapshot() throws Exception {
        RuleSet ruleSet1 = createSimpleRuleSet("MyRuleSet");
        
        brmsRepository.createRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1", 
                false, "Snapshot Version 1");
        
        RuleSet ruleSet2 = brmsRepository.loadRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1");

        assertNotNull(ruleSet2);
        assertFalse(ruleSet1.equals(ruleSet2));
    }

    @Test
    public void testCreateRuleSetSnapshotVersions() throws Exception {
        createSimpleRuleSet("MyRuleSet");
        
        String expectedCheckinComment = "Snapshot Version 1";
        brmsRepository.createRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1", 
                false, expectedCheckinComment);
        brmsRepository.createRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot2", 
                false, expectedCheckinComment);
        
        RuleSet ruleSet2 = brmsRepository.loadRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1");

        assertEquals( 2, ruleSet2.getVersionNumber() );
        assertEquals( expectedCheckinComment, ruleSet2.getCheckinComment() );
    }
    
    @Test
    public void testReplaceRuleSetSnapshot() throws Exception {
        createSimpleRuleSet("MyRuleSet");
        
        brmsRepository.createRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1", 
                false, "Snapshot Version 1");
        // replace snapshot
        String expectedCheckinComment = "Snapshot Version 2";
        brmsRepository.createRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1", 
                true, expectedCheckinComment);
        
        RuleSet ruleSet = brmsRepository.loadRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1");
        assertEquals( 2, ruleSet.getVersionNumber() );
        assertEquals( expectedCheckinComment, ruleSet.getCheckinComment() );
    }
    
    @Test
    public void testReplaceNonExistingRuleSetSnapshot() throws Exception {
        createSimpleRuleSet("MyRuleSet");
        
            try {
            brmsRepository.createRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1", 
                    true, "Snapshot Version 1");
            fail("Replacing non-existing snapshot should have failed");
        } catch (RuleEngineRepositoryException e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void testLoadCompiledRuleSetSnapshotAndExecuteSnapshot() throws Exception {
        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar");
        header.add("import org.kuali.student.brms.repository.test.Message");
        RuleSet ruleSet = createRuleSet("MyPackage", "My package description", header);
        Rule rule1 = createRuleDRL("rule_1", "Email Initialization Rule", null, DroolsTestUtil.getSimpleRule3() );
        ruleSet.addRule(rule1);
        Rule rule2 = createRuleDRL("rule_2", "Email Validation Rule", null, DroolsTestUtil.getSimpleRule4() );
        ruleSet.addRule(rule2);
        String rulesetUUID = brmsRepository.createRuleSet(ruleSet);

        CompilerResultList results = brmsRepository.compileRuleSet(rulesetUUID);

        // No errors
        assertNull(RuleEngineUtil.getErrorMessage(results), results);

        org.drools.rule.Package binPkg = (org.drools.rule.Package) brmsRepository.loadCompiledRuleSet(rulesetUUID);

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());

        DroolsTestUtil.executeRule(binPkg, new Object[]{Calendar.getInstance()});

        brmsRepository.createRuleSetSnapshot("MyPackage", "SNAPSHOT1", false, "A snapshot");

        binPkg = (org.drools.rule.Package) brmsRepository.loadCompiledRuleSetSnapshot("MyPackage", "SNAPSHOT1");

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());

        Message message = new Message();
        Calendar calendar = Calendar.getInstance();
        DroolsTestUtil.executeRule(binPkg, new Object[]{message, calendar});
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
        header.add("import org.kuali.student.brms.repository.test.Email");
        header.add("import org.kuali.student.brms.repository.test.Message");
        RuleSet ruleSet = createRuleSet("MyPackage", "My package description", header);
        Rule rule1 = createRuleDRL("rule_1", "Email Initialization Rule", "testLoadCompiledRuleSetAndExecute", DroolsTestUtil.getValidationRule1() );
        ruleSet.addRule(rule1);
        Rule rule2 = createRuleDRL("rule_2", "Email Validation Rule", "testLoadCompiledRuleSetAndExecute", DroolsTestUtil.getValidationRule2() );
        ruleSet.addRule(rule2);
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet);
        
        // Must compile a ruleset before it will save a compiled ruleset
        CompilerResultList results = brmsRepository.compileRuleSet(ruleSetUUID);

        // No errors
        assertNull(RuleEngineUtil.getErrorMessage(results), results);

        org.drools.rule.Package binPkg = (org.drools.rule.Package) brmsRepository.loadCompiledRuleSet(ruleSetUUID);

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());

        Email email = new Email("len.carlsen@ubc.ca");
        Message message = new Message();
        DroolsTestUtil.executeRule(binPkg, new Object[]{email, message});
        assertEquals("Valid Email Address: len.carlsen@ubc.ca", message.getMessage());
    }

    @Test
    public void testLoadCompiledRuleSetObject() throws Exception {
        String ruleSetUUID = createSimpleRuleSet("MyPackage").getUUID();

        // Must compile a ruleset before it will save a compiled ruleset
        // A ruleset doesn't need to be checked in before compiling
        CompilerResultList results = brmsRepository.compileRuleSet(ruleSetUUID);
        assertNull(RuleEngineUtil.getErrorMessage(results), results);

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
        assertNull(RuleEngineUtil.getErrorMessage(results), results);

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
        String rulesetUUID = brmsRepository.createRuleSet(ruleSet1);
        
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
                DroolsTestUtil.getSimpleRule1());
        ruleSet1.addRule(rule1);

        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);
        assertTrue( ruleSetUUID != null && !ruleSetUUID.isEmpty() );
    }

    @Test
    public void testCreateDuplicateRuleSet() throws Exception {
        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar");
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", header);

        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);
        assertTrue( ruleSetUUID != null && !ruleSetUUID.isEmpty() );

        // Duplicate rule set
        RuleSet ruleSet2 = createRuleSet("MyRuleSet", "My new rule set", header);

        try {
            ruleSetUUID = brmsRepository.createRuleSet(ruleSet2);
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
                DroolsTestUtil.getSimpleRule1());
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
        header.add("import org.kuali.student.brms.repository.test.Email;");
        header.add("import org.kuali.student.brms.repository.test.Message;");
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "Email Initialization Rule", header);

        Rule rule1 = createRuleDRL("MyRule1", "My new rule 1", null, 
                DroolsTestUtil.getValidationRule1());
        ruleSet1.addRule(rule1);

        Rule rule2 = createRuleDRL("MyRule2", "My new rule 2", null, 
                DroolsTestUtil.getValidationRule2());
        ruleSet1.addRule(rule2);

        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);
        assertTrue( ruleSetUUID != null && !ruleSetUUID.isEmpty() );

        RuleSet ruleSet2 = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals(header.get(0)+";", ruleSet2.getHeaderList().get(0));
        assertEquals(header.get(1), ruleSet2.getHeaderList().get(1));
        assertEquals(header.get(2), ruleSet2.getHeaderList().get(2));
    }

    @Test
    public void testCreateAndLoadRuleSet() throws Exception {
        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar");
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", header);

        String ruleCategory = null; //"MyCategory";
        
        Rule rule1 = createRuleDRL("MyRule1", "My new rule 1", ruleCategory, 
                DroolsTestUtil.getSimpleRule1());
        ruleSet1.addRule(rule1);

        Rule rule2 = createRuleDRL("MyRule2", "My new rule 2", ruleCategory, 
                DroolsTestUtil.getSimpleRule2());
        ruleSet1.addRule(rule2);

        //brmsRepository.createCategory("/", ruleCategory, "My new rule category");
        
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);
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
    public void testCheckinRuleSet() throws Exception {
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);
        
        // Rule Set Version 1
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);

        RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals( 1L, ruleSet.getVersionNumber());

        // Rule Set Version 2
        brmsRepository.checkinRuleSet(ruleSetUUID, "Checkin Rule Set Version 2");
        
        ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals( 2L, ruleSet.getVersionNumber());
    }

    @Test
    public void testUpdateRuleSet() throws Exception {
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);
        
        // Rule Set Version 1
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);

        RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals( 1L, ruleSet.getVersionNumber());

        // Update Rule Set with new header
        String header = "import java.util.List;";
        ruleSet.addHeader(header);
        brmsRepository.updateRuleSet(ruleSet);
        
        ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals( header, ruleSet.getHeader().trim());
    }
    
    @Test
    public void testLoadCompiledRuleSet() throws Exception {
        String ruleCategory = "MyCategory";
        brmsRepository.createCategory("/", ruleCategory, "My new rule category");

        String ruleSetUUID = createSimpleRuleSet("MyRuleSet").getUUID();
        
        org.drools.rule.Package binPkg = (org.drools.rule.Package) 
            brmsRepository.loadCompiledRuleSet(ruleSetUUID);

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());
    }
    
    @Test
    public void testCreateRule() throws Exception {
        RuleSet ruleSet2 = createSimpleRuleSet("MyPackage");

        List<Rule> list = ruleSet2.getRules();
        assertNotNull(list);
        assertTrue(list.size() > 0);

        brmsRepository.checkinRule(list.get(0).getUUID(), "Checkin rule comments");

        Rule rule = brmsRepository.loadRule(list.get(0).getUUID());
        assertEquals(rule.getContent(), DroolsTestUtil.getSimpleRule1());
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
                DroolsTestUtil.getSimpleRule1());
        ruleSet.addRule(rule);
        Rule duplateRule = createRuleDRL("MyRule1", "My new rule 1", null, 
                DroolsTestUtil.getSimpleRule1());
        ruleSet.addRule(duplateRule);

        try {
            brmsRepository.createRuleSet(ruleSet);
            fail( "Creating a duplicate rule should have thrown a RuleExistsException" );
        } catch( RuleExistsException e ) {
            assertTrue( true );
        }
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
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);

        // Rule Version 1
        RuleSet ruleSet2 = brmsRepository.loadRuleSet(ruleSetUUID);
        brmsRepository.checkinRule(ruleSet2.getRules().get(0).getUUID(), "Checkin Rule Version 1");
        
        RuleSet ruleSet3 = brmsRepository.loadRuleSet(ruleSetUUID);
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
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);
        RuleSet ruleSet2 = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals( oldContent, ruleSet2.getRules().get(0).getContent());

        // Update Rule
        String newContent = "rule \"new_rule\" when then end";
        Rule newRule = ruleSet2.getRules().get(0);
        newRule.setContent(newContent);
        brmsRepository.updateRule(newRule);
        
        Rule updatedRule = brmsRepository.loadRule(newRule.getUUID());
        assertEquals( newContent, updatedRule.getContent());
    }

    @Test
    public void testRenameRuleSet() throws Exception {
        RuleSet ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);
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
        Rule rule1 = createRuleDRL("rule_1", "Email Initialization Rule", null, DroolsTestUtil.getSimpleRule1() );
        ruleSet1.addRule(rule1);
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);

        RuleSet ruleSet2 = brmsRepository.loadRuleSet(ruleSetUUID);

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
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);

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
        Rule rule1 = createRuleDRL("rule_1", "My new rule 1", null, DroolsTestUtil.getSimpleRule1() );
        ruleSet1.addRule(rule1);
        Rule rule2 = createRuleDRL("rule_2", "My new rule 1", null, DroolsTestUtil.getSimpleRule2() );
        ruleSet1.addRule(rule2);
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);

        try {
            String drl1 = DroolsTestUtil.getSimpleDRL("MyRuleSet");
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
            Rule rule1 = createRuleDRL("rule_1", "My new rule 1", null, DroolsTestUtil.getSimpleRule1() );
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

            //List<String> header = new ArrayList<String>();
            //header.add("import jav.uti.Calend");
            //ruleSet.setHeaderList(header);
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
    public void testArchiveRuleSet() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");
        brmsRepository.checkinRule(ruleSet.getRules().get(0).getUUID(), null);

        // Archived Rule set
        brmsRepository.archiveRuleSet(ruleSet.getUUID(), null);
        assertTrue(brmsRepository.loadRuleSet(ruleSet.getUUID()).isArchived());
    }

    @Test
    public void testUnArchiveRuleSet() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");
        
        brmsRepository.checkinRule(ruleSet.getRules().get(0).getUUID(), null);

        // Archived Rule set
        brmsRepository.archiveRuleSet(ruleSet.getUUID(), null);

        assertTrue(brmsRepository.loadRuleSet(ruleSet.getUUID()).isArchived());

        // UnArchived Rule set
        brmsRepository.unArchiveRuleSet(ruleSet.getUUID(), null);

        assertFalse(brmsRepository.loadRuleSet(ruleSet.getUUID()).isArchived());
    }

    /*
     * TODO Fix this archive/unarchive rule set error @Test public void testLoadArchivedRuleSet() throws Exception { String
     * ruleSetUUID = createRuleSet( "testLoadArchivedRuleSet", null, false ); RuleSet ruleSet = brmsRepository.loadRuleSet(
     * ruleSetUUID ); assertEquals( ruleSetUUID, ruleSet.getUUID() ); String ruleSource = DroolsTestUtil.getSimpleRules();
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
        brmsRepository.archiveRule(rule.getUUID(), null);
        assertTrue(brmsRepository.loadRule(rule.getUUID()).isArchived());

        List<Rule> list = brmsRepository.loadArchivedRules();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(rule.getUUID(), list.get(0).getUUID());
        assertEquals(!rule.isArchived(), list.get(0).isArchived());
        assertEquals("Archived", list.get(0).getCheckinComment());

        // UnArchived Rule
        brmsRepository.unArchiveRule(rule.getUUID(), null);

        Rule rule2 = brmsRepository.loadRule(rule.getUUID());
        assertEquals(rule.getUUID(), rule2.getUUID());
        assertEquals(!rule.isArchived(), !rule2.isArchived());
        assertEquals("Unarchived", rule2.getCheckinComment());
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

    /*
     * @Test public void testLoadRuleSetHistory() throws Exception { String ruleSetUUID = createRuleSet(
     * "testLoadRuleSetHistory", "import java.util.Calendar", false ); //String ruleUUID = createRule( ruleSetUUID,
     * "testLoadRuleSetHistory", false ); // Version 1 RuleSet ruleset1 = brmsRepository.loadRuleSet( ruleSetUUID );
     * brmsRepository.checkinRuleSet( ruleset1.getUUID(), "Checkin version 1" ); // Version 2 RuleSet ruleset2 =
     * brmsRepository.loadRuleSet( ruleSetUUID ); brmsRepository.checkinRuleSet( ruleset2.getUUID(), "Checkin version 2" ); //
     * Version 3 RuleSet ruleset3Head = brmsRepository.loadRuleSet( ruleSetUUID ); brmsRepository.checkinRuleSet(
     * ruleset3Head.getUUID(), "Checkin version 3 - HEAD version" ); List<RuleSet> rulesetHistory =
     * brmsRepository.loadRuleSetHistory( ruleSetUUID ); assertEquals( 2, rulesetHistory.size() ); long version1 =
     * rulesetHistory.get( 0 ).getVersionNumber(); long version2 = rulesetHistory.get( 1 ).getVersionNumber(); assertTrue(
     * version1 != version2 ); }
     */

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
        
        brmsRepository.createRuleSetSnapshot("testRebuildAllSnapshots", "SNAPSHOT-1", false, "Build snapshot 1");

        long snapshotTime1 = brmsRepository.loadRuleSetSnapshot("testRebuildAllSnapshots", "SNAPSHOT-1").getLastModifiedDate().getTimeInMillis();

        brmsRepository.rebuildAllSnapshots();

        long snapshotTime2 = brmsRepository.loadRuleSetSnapshot("testRebuildAllSnapshots", "SNAPSHOT-1").getLastModifiedDate().getTimeInMillis();

        assertTrue(snapshotTime2 > snapshotTime1);

        Rule rule = brmsRepository.loadRule(ruleSet.getRules().get(0).getUUID());
        rule.setContent("Some invalid source code");
        brmsRepository.updateRule(rule);
        brmsRepository.checkinRule(ruleSet.getRules().get(0).getUUID(), "Checkin invalid source");

        brmsRepository.createRuleSetSnapshot("testRebuildAllSnapshots", "SNAPSHOT-2", false, "Build invalid snapshot 2");

        try {
            brmsRepository.rebuildAllSnapshots();
            fail("Rebuilding of shanpshots should fail because of invlaid rule: "+rule.getContent());
        } catch (RuleEngineRepositoryException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testExportImportAsXmlRulesRepository() throws Exception {
        RuleSet ruleSet = createSimpleRuleSet("MyRuleSet");

        try {
            // Export repository
            byte[] export = brmsRepository.exportRulesRepositoryAsXml();
            brmsRepository.removeRule(ruleSet.getRules().get(0).getUUID());
            brmsRepository.importRulesRepository(export);
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
            brmsRepository.importRulesRepository(byteExport);
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
        Reader drl = new StringReader(DroolsTestUtil.getSimpleDRL(packageName));
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

}
