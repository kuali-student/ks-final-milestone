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
import org.junit.Test;
import org.kuali.student.brms.repository.drools.RuleEngineRepositoryDroolsImpl;
import org.kuali.student.brms.repository.drools.DroolsTestUtil;
import org.kuali.student.brms.repository.drools.DroolsJackrabbitRepository;
import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.brms.repository.rule.BuilderResultList;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleImpl;
import org.kuali.student.brms.repository.rule.RuleSet;
import org.kuali.student.brms.repository.rule.RuleSetImpl;
import org.kuali.student.brms.repository.test.Email;
import org.kuali.student.brms.repository.test.Message;

public class RuleEngineRepositoryTest {

    private static DroolsJackrabbitRepository jackrabbitRepository;

    private static RuleEngineRepository brmsRepository;

    @BeforeClass
    public static void setUpOnce() throws Exception {
        URL url = RuleEngineRepositoryTest.class.getResource("/repository");
        jackrabbitRepository = new DroolsJackrabbitRepository(url);
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

    private String createRuleSet(String categoryName) throws RuleEngineRepositoryException {
        return createRuleSet(categoryName, null, true);
    }

    private String createRuleSet(String categoryName, String facts, boolean checkin) throws RuleEngineRepositoryException {
        return RuleEngineUtil.createRuleSet(brmsRepository, categoryName, facts, checkin);
    }

    private String createRule(String ruleSetUUID, String categoryName) throws RuleEngineRepositoryException {
        return createRule(ruleSetUUID, categoryName, true);
    }

    private static String createRule(String ruleSetUUID, String categoryName, boolean checkin) throws RuleEngineRepositoryException {
        return RuleEngineUtil.createRule(brmsRepository, ruleSetUUID, categoryName, checkin);
    }

    private RuleSetImpl createRuleSet(String name, String description, List<String> facts) throws RuleEngineRepositoryException {
        RuleSetImpl ruleSet = new RuleSetImpl(name);
        ruleSet.setDescription(description);
        if ( facts != null && !facts.isEmpty()) {
            ruleSet.setHeaderList( facts );
        }
        return ruleSet;
    }

    private static RuleImpl createRuleDRL(String name, String description, String category, String content) throws RuleEngineRepositoryException {
        RuleImpl rule = new RuleImpl(name);
        rule.setDescription(description);
        rule.setCategory(category);
        rule.setFormat("drl");
        rule.setContent(content);
        return rule;
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
        String ruleCategory = "MyCategory";
        brmsRepository.createCategory("/", ruleCategory, "My new rule category");

        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar");
        RuleSetImpl ruleSet = createRuleSet("MyRuleSet", "My new rule set", header);
        
        RuleImpl rule = createRuleDRL("MyRule1", "My new rule 1", ruleCategory, 
                DroolsTestUtil.getSimpleRule1());
        ruleSet.addRule(rule);

        brmsRepository.createRuleSet(ruleSet);
        brmsRepository.createRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1", 
                false, "Snapshot Version 1");
        
        org.drools.rule.Package binPkg = (org.drools.rule.Package) 
            brmsRepository.loadCompiledRuleSetSnapshot("MyRuleSet", "MyRuleSetSnapshot1");

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());
    }
    
    @Test
    public void testLoadCompiledRuleSetSnapshotAndExecuteSnapshot() throws Exception {
        String rulesetUuid = brmsRepository.createRuleSet("testBinaryPackageCompile", "");
        String ruleUuid1 = brmsRepository.createRule(rulesetUuid, "rule_1", "", 
                DroolsTestUtil.getSimpleRule3(), null);
        //brmsRepository.checkinRule(ruleUuid1, null);
        String ruleUuid2 = brmsRepository.createRule(rulesetUuid, "rule_2", "", 
                DroolsTestUtil.getSimpleRule4(), null);
        //brmsRepository.checkinRule(ruleUuid2, null);
        brmsRepository.setFactsToRuleSet(rulesetUuid, "import java.util.Calendar " + "import org.kuali.student.brms.repository.test.Message");

        BuilderResultList results = brmsRepository.compileRuleSet(rulesetUuid);

        // No errors
        assertNull(RuleEngineUtil.getErrorMessage(results), results);

        org.drools.rule.Package binPkg = (org.drools.rule.Package) brmsRepository.loadCompiledRuleSet(rulesetUuid);

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());

        DroolsTestUtil.executeRule(binPkg, new Object[]{Calendar.getInstance()});

        brmsRepository.createRuleSetSnapshot("testBinaryPackageCompile", "SNAP1", false, "A snapshot");

        binPkg = (org.drools.rule.Package) brmsRepository.loadCompiledRuleSetSnapshot("testBinaryPackageCompile", "SNAP1");

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

        String rulesetUuid = brmsRepository.createRuleSet("testBinaryPackageCompile2", 
                "Email Validation Rule Package");
        brmsRepository.setFactsToRuleSet(rulesetUuid, 
                "import java.util.regex.Pattern " + 
                "import org.kuali.student.brms.repository.test.Email " + 
                "import org.kuali.student.brms.repository.test.Message");
        String ruleUuid1 = brmsRepository.createRule(rulesetUuid, "rule_1", 
                "Email Initialization Rule", 
                DroolsTestUtil.getValidationRule1(), 
                "testLoadCompiledRuleSetAndExecute");
        brmsRepository.checkinRule(ruleUuid1, null);
        String ruleUuid2 = brmsRepository.createRule(rulesetUuid, "rule_2", 
                "Email Validation Rule", 
                DroolsTestUtil.getValidationRule2(), 
                "testLoadCompiledRuleSetAndExecute");
        brmsRepository.checkinRule(ruleUuid2, null);

        // Must compile a ruleset before it will save a compiled ruleset
        BuilderResultList results = brmsRepository.compileRuleSet(rulesetUuid);

        // No errors
        assertNull(RuleEngineUtil.getErrorMessage(results), results);

        org.drools.rule.Package binPkg = (org.drools.rule.Package) brmsRepository.loadCompiledRuleSet(rulesetUuid);

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());

        Email email = new Email("len.carlsen@ubc.ca");
        Message message = new Message();
        DroolsTestUtil.executeRule(binPkg, new Object[]{email, message});
        assertEquals("Valid Email Address: len.carlsen@ubc.ca", message.getMessage());
    }

    @Test
    public void testLoadCompiledRuleSetObject() throws Exception {
        String rulesetUuid = brmsRepository.createRuleSet("MyPackage", "My package description");
        String ruleUuid1 = brmsRepository.createRule(rulesetUuid, "rule_1", "", DroolsTestUtil.getSimpleRule1(), null);
        String ruleUuid2 = brmsRepository.createRule(rulesetUuid, "rule_2", "", DroolsTestUtil.getSimpleRule2(), null);
        brmsRepository.setFactsToRuleSet(rulesetUuid, "import java.util.Calendar");

        // Must compile a ruleset before it will save a compiled ruleset
        // A ruleset doesn't need to be checked in before compiling
        BuilderResultList results = brmsRepository.compileRuleSet(rulesetUuid);
        assertNull(RuleEngineUtil.getErrorMessage(results), results);

        RuleSet ruleset = brmsRepository.loadRuleSet(rulesetUuid);
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

        String rulesetUuid = brmsRepository.createRuleSet("MyPackage", "My package description");
        String ruleUuid1 = brmsRepository.createRule(rulesetUuid, "rule_1", "", DroolsTestUtil.getSimpleRule1(), "testJSR94");
        brmsRepository.checkinRule(ruleUuid1, null);
        String ruleUuid2 = brmsRepository.createRule(rulesetUuid, "rule_2", "", DroolsTestUtil.getSimpleRule2(), "testJSR94");
        brmsRepository.checkinRule(ruleUuid2, null);
        brmsRepository.setFactsToRuleSet(rulesetUuid, "import java.util.Calendar");

        BuilderResultList results = brmsRepository.compileRuleSet(rulesetUuid);

        // No errors
        assertNull(RuleEngineUtil.getErrorMessage(results), results);

        org.drools.rule.Package binPkg = (org.drools.rule.Package) brmsRepository.loadCompiledRuleSet(rulesetUuid);

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
        String rulesetUuid = brmsRepository.createRuleSet("testCreateRuleSet", "Rule set description");
        assertNotNull(rulesetUuid);

        RuleSet ruleSet = brmsRepository.loadRuleSet(rulesetUuid);
        assertEquals(ruleSet.getUUID(), rulesetUuid);

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
        RuleSetImpl ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", header);

        RuleImpl rule1 = createRuleDRL("MyRule1", "My new rule 1", null, 
                DroolsTestUtil.getSimpleRule1());
        ruleSet1.addRule(rule1);

        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);
        assertTrue( ruleSetUUID != null && !ruleSetUUID.isEmpty() );
    }

    @Test
    public void testCreateRuleSet_MissingImport() throws Exception {
        RuleSetImpl ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);

        RuleImpl rule1 = createRuleDRL("MyRule1", "My new rule 1", null, 
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
        RuleSetImpl ruleSet1 = createRuleSet("MyRuleSet", "Email Initialization Rule", header);

        RuleImpl rule1 = createRuleDRL("MyRule1", "My new rule 1", null, 
                DroolsTestUtil.getValidationRule1());
        ruleSet1.addRule(rule1);

        RuleImpl rule2 = createRuleDRL("MyRule2", "My new rule 2", null, 
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
        RuleSetImpl ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", header);

        String ruleCategory = null; //"MyCategory";
        
        RuleImpl rule1 = createRuleDRL("MyRule1", "My new rule 1", ruleCategory, 
                DroolsTestUtil.getSimpleRule1());
        ruleSet1.addRule(rule1);

        RuleImpl rule2 = createRuleDRL("MyRule2", "My new rule 2", ruleCategory, 
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
        RuleSetImpl ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);
        
        // Rule Set Version 1
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);

        RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals( 1L, ruleSet.getVersionNumber());

        // Rule Set Version 2
        brmsRepository.checkinRuleSet(ruleSetUUID, "Checkin Rule Set Version 2");
        
        ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals( 2L, ruleSet.getVersionNumber());
    }
    
    public void testUpdateRuleSet() throws Exception {
        RuleSetImpl ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);
        
        // Rule Set Version 1
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet1);

        RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals( 1L, ruleSet.getVersionNumber());

        // Update Rule Set with new header
        RuleSetImpl newRuleSet = (RuleSetImpl) ruleSet;
        String header = "import java.util.List";
        newRuleSet.addHeader(header);
        brmsRepository.updateRuleSet(ruleSet);
        
        ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals( header, ruleSet.getHeader());
    }
    
    @Test
    public void testLoadCompiledRuleSet() throws Exception {
        String ruleCategory = "MyCategory";
        brmsRepository.createCategory("/", ruleCategory, "My new rule category");

        List<String> header = new ArrayList<String>();
        header.add("import java.util.Calendar");
        RuleSetImpl ruleSet = createRuleSet("MyRuleSet", "My new rule set", header);
        
        RuleImpl rule = createRuleDRL("MyRule1", "My new rule 1", ruleCategory, 
                DroolsTestUtil.getSimpleRule1());
        ruleSet.addRule(rule);
        
        String ruleSetUUID = brmsRepository.createRuleSet(ruleSet);
        
        org.drools.rule.Package binPkg = (org.drools.rule.Package) 
            brmsRepository.loadCompiledRuleSet(ruleSetUUID);

        assertNotNull(binPkg);
        assertTrue(binPkg.isValid());
    }
    
    @Test
    public void testCreateRule() throws Exception {
        String ruleSetUUID = createRuleSet("testCreateRule");
        String ruleUUID = createRule(ruleSetUUID, "testCreateRule");

        RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        List<Rule> list = ruleSet.getRules();
        assertNotNull(list);
        assertTrue(list.size() > 0);

        Rule rule = brmsRepository.loadRule(ruleUUID);
        assertEquals(rule.getUUID(), ruleUUID);
        assertEquals(rule.getContent(), DroolsTestUtil.getSimpleRule1());
        assertEquals(rule.getName(), "rule_1");
        assertEquals(rule.getDescription(), "Rule set description");
        assertEquals(rule.getCheckinComment(), "Checkin rule comments");
    }

    @Test
    public void testCheckinRule() throws Exception {
        String ruleCategory = "MyCategory";
        brmsRepository.createCategory("/", ruleCategory, "My new rule category");

        RuleSetImpl ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);

        RuleImpl rule = createRuleDRL("MyRule1", "My new rule 1", ruleCategory, 
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

        RuleSetImpl ruleSet1 = createRuleSet("MyRuleSet", "My new rule set", null);

        String oldContent = "rule \"xxx\" when then end";
        RuleImpl rule = createRuleDRL("MyRule1", "My new rule 1", ruleCategory, 
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
        String rulesetUuid = brmsRepository.createRuleSet("testRenameRuleSet", 
                "Rule set description");
        assertNotNull(rulesetUuid);

        String newUUID = brmsRepository.renameRuleSet(rulesetUuid, 
                "testRenameRuleSet_NEW_NAME");
        RuleSet ruleSet = brmsRepository.loadRuleSet(newUUID);
        assertEquals(ruleSet.getUUID(), newUUID);
        assertEquals(ruleSet.getName(), "testRenameRuleSet_NEW_NAME");
    }

    @Test
    public void testRenameRule() throws Exception {
        String ruleSetUUID = createRuleSet("testRenameRule");
        String ruleUUID = createRule(ruleSetUUID, "testRenameRule");

        String newRuleUUID = brmsRepository.renameRule(ruleUUID, "rule_1_NEW_NAME");
        Rule rule = brmsRepository.loadRule(newRuleUUID);
        assertEquals(rule.getUUID(), newRuleUUID);
        assertEquals(rule.getName(), "rule_1_NEW_NAME");
    }

    @Test
    public void testListStates() throws Exception {
        brmsRepository.createStatus("Active");
        brmsRepository.createStatus("Inactive");

        String[] states = brmsRepository.loadStates();

        assertEquals(3, states.length);
        assertEquals("Draft", states[0]); // default state is Draft
        assertEquals("Active", states[1]);
        assertEquals("Inactive", states[2]);
    }

    @Test
    public void testChangeRuleSetStatus() throws Exception {
        String ruleSetUUID = brmsRepository.createRuleSet("testChangeRuleSetStatus", "Rule set description");
        assertNotNull(ruleSetUUID);

        brmsRepository.createStatus("Active");
        brmsRepository.createStatus("Inactive");

        brmsRepository.changeRuleSetStatus(ruleSetUUID, "Active");
        RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals(ruleSet.getStatus(), "Active");

        brmsRepository.changeRuleSetStatus(ruleSetUUID, "Inactive");
        ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals(ruleSet.getStatus(), "Inactive");
    }

    @Test
    public void testChangeRuleStatus() throws Exception {
        String ruleSetUUID = createRuleSet("testChangeRuleStatus");
        String ruleUUID = createRule(ruleSetUUID, "testChangeRuleStatus");

        brmsRepository.createStatus("Active");
        brmsRepository.createStatus("Inactive");

        brmsRepository.changeRuleStatus(ruleUUID, "Active");
        Rule rule = brmsRepository.loadRule(ruleUUID);
        assertEquals(rule.getStatus(), "Active");

        brmsRepository.changeRuleStatus(ruleUUID, "Inactive");
        rule = brmsRepository.loadRule(ruleUUID);
        assertEquals(rule.getStatus(), "Inactive");
    }

    @Test
    public void testCompileValidRuleSetSource() throws Exception {
        String ruleSetUUID = createRuleSet("testCompileValidRuleSetSource", "import java.util.Calendar", false);
        // createRule( ruleSetUUID, "testCompileValidRuleSetSource" );
        brmsRepository.createRule(ruleSetUUID, "rule_1", "", DroolsTestUtil.getSimpleRule1(), "testCompileValidRuleSetSource");
        brmsRepository.createRule(ruleSetUUID, "rule_2", "", DroolsTestUtil.getSimpleRule2(), "testCompileValidRuleSetSource");

        try {
            String drl1 = DroolsTestUtil.getSimpleDRL("testCompileValidRuleSetSource");
            String drl2 = brmsRepository.compileRuleSetSource(ruleSetUUID);
            assertTrue(drl2.indexOf(drl1) > -1);
        } catch (RuleEngineRepositoryException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCompileInvalidRuleSetSource() throws Exception {
        String ruleSetUUID = createRuleSet("testCompileRuleSetSource", "import jav.util.Calend", false);
        createRule(ruleSetUUID, "testCompileRuleSetSource");

        try {
            String drl = brmsRepository.compileRuleSetSource(ruleSetUUID);
            fail("Invalid rule should throw an exception");
        } catch (RuleEngineRepositoryException e) {
            assertTrue(e.getMessage() != null);
        }
    }

    @Test
    public void testCompileValidRuleSet() throws Exception {
        String ruleSetUUID = createRuleSet("testCompileRuleSet", "import java.util.Calendar", false);
        createRule(ruleSetUUID, "testCompileRuleSet");

        try {
            BuilderResultList results = brmsRepository.compileRuleSet(ruleSetUUID);
            assertNull(results);
        } catch (RuleEngineRepositoryException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCompileInvalidRuleSet() throws Exception {
        String ruleSetUUID = createRuleSet("testCompileInvalidRuleSet", "import java.util.Calendar", false);
        String ruleUUID = createRule(ruleSetUUID, "testCompileInvalidRuleSet");

        Rule rule = brmsRepository.loadRule(ruleUUID);
        rule.setContent("InvalidRuleSource");
        brmsRepository.updateRule(rule);
        brmsRepository.checkinRule(rule.getUUID(), "Some invalid source");

        try {
            BuilderResultList results = brmsRepository.compileRuleSet(ruleSetUUID);
            assertNotNull(results);
            assertTrue(results.toString().indexOf("InvalidRuleSource") > -1);
        } catch (RuleEngineRepositoryException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRemoveRule() throws Exception {
        String ruleSetUUID = createRuleSet("testRemoveRule");
        String ruleUUID = createRule(ruleSetUUID, "testRemoveRule");

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
        String ruleSetUUID = createRuleSet("testArchiveRuleSet", null, false);

        RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals(ruleSetUUID, ruleSet.getUUID());

        String ruleSource = DroolsTestUtil.getSimpleRule1();
        String ruleUUID = brmsRepository.createRule(ruleSetUUID, "rule_1", "Rule set description", ruleSource, null);
        brmsRepository.checkinRule(ruleUUID, null);

        // Archived Rule set
        brmsRepository.archiveRuleSet(ruleSetUUID, null);

        assertTrue(brmsRepository.loadRuleSet(ruleSetUUID).isArchived());
    }

    @Test
    public void testUnArchiveRuleSet() throws Exception {
        String ruleSetUUID = createRuleSet("testUnArchiveRuleSet", null, false);

        RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertEquals(ruleSetUUID, ruleSet.getUUID());

        String ruleSource = DroolsTestUtil.getSimpleRule1();
        String ruleUUID = brmsRepository.createRule(ruleSetUUID, "rule_1", "Rule set description", ruleSource, null);
        brmsRepository.checkinRule(ruleUUID, null);

        // Archived Rule set
        brmsRepository.archiveRuleSet(ruleSetUUID, null);

        assertTrue(brmsRepository.loadRuleSet(ruleSetUUID).isArchived());

        // UnArchived Rule set
        brmsRepository.unArchiveRuleSet(ruleSetUUID, null);

        assertFalse(brmsRepository.loadRuleSet(ruleSetUUID).isArchived());
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
        String ruleSetUUID = createRuleSet("testArchiveUnArchiveRule");
        String ruleUUID = createRule(ruleSetUUID, "testArchiveUnArchiveRule");

        Rule rule = brmsRepository.loadRule(ruleUUID);

        assertFalse(brmsRepository.loadRule(ruleUUID).isArchived());
        // Archived Rule
        brmsRepository.archiveRule(ruleUUID, null);
        assertTrue(brmsRepository.loadRule(ruleUUID).isArchived());

        List<Rule> list = brmsRepository.loadArchivedRules();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertEquals(rule.getUUID(), list.get(0).getUUID());
        assertEquals(!rule.isArchived(), list.get(0).isArchived());
        assertEquals("Archived", list.get(0).getCheckinComment());

        // UnArchived Rule
        brmsRepository.unArchiveRule(ruleUUID, null);

        Rule rule2 = brmsRepository.loadRule(ruleUUID);
        assertEquals(rule.getUUID(), rule2.getUUID());
        assertEquals(!rule.isArchived(), !rule2.isArchived());
        assertEquals("Unarchived", rule2.getCheckinComment());
    }

    @Test
    public void testLoadRuleHistory() throws Exception {
        String ruleSetUUID = createRuleSet("testLoadRuleHistory", "import java.util.Calendar", false);
        String ruleUUID = createRule(ruleSetUUID, "testLoadRuleHistory", false);

        // Version 1
        Rule rule1 = brmsRepository.loadRule(ruleUUID);
        brmsRepository.checkinRule(rule1.getUUID(), "Checkin version 1");

        // Version 2
        Rule rule2 = brmsRepository.loadRule(ruleUUID);
        brmsRepository.checkinRule(rule2.getUUID(), "Checkin version 2");

        // Version 3
        Rule rule3Head = brmsRepository.loadRule(ruleUUID);
        brmsRepository.checkinRule(rule3Head.getUUID(), "Checkin version 3 - HEAD version");

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
        String ruleSetUUID = createRuleSet("testLoadRuleHistoryAndRestore", "import java.util.Calendar", false);
        String ruleUUID = createRule(ruleSetUUID, "testLoadRuleHistoryAndRestore", false);

        // Version 1
        Rule rule1 = brmsRepository.loadRule(ruleUUID);
        brmsRepository.checkinRule(rule1.getUUID(), "Checkin version 1");

        // Version 2
        Rule rule2 = brmsRepository.loadRule(ruleUUID);
        brmsRepository.checkinRule(rule2.getUUID(), "Checkin version 2");

        // Version 3
        Rule rule3Head = brmsRepository.loadRule(ruleUUID);
        brmsRepository.checkinRule(rule3Head.getUUID(), "Checkin version 3 - HEAD version");

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
        String ruleSetUUID = createRuleSet("testRebuildAllSnapshots", "import java.util.Calendar", true);
        String ruleUUID = createRule(ruleSetUUID, "testRebuildAllSnapshots");

        brmsRepository.createRuleSetSnapshot("testRebuildAllSnapshots", "SNAPSHOT-1", false, "Build snapshot 1");

        long snapshotTime1 = brmsRepository.loadRuleSetSnapshot("testRebuildAllSnapshots", "SNAPSHOT-1").getLastModifiedDate().getTimeInMillis();

        brmsRepository.rebuildAllSnapshots();

        long snapshotTime2 = brmsRepository.loadRuleSetSnapshot("testRebuildAllSnapshots", "SNAPSHOT-1").getLastModifiedDate().getTimeInMillis();

        assertTrue(snapshotTime2 > snapshotTime1);

        Rule rule = brmsRepository.loadRule(ruleUUID);
        rule.setContent("Some invalid source code");
        brmsRepository.updateRule(rule);
        brmsRepository.checkinRule(rule.getUUID(), "Checkin invalid source");

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
        String ruleSetUUID = createRuleSet("testExportImportAsXmlRulesRepository");
        String ruleUUID = createRule(ruleSetUUID, "testExportImportAsXmlRulesRepository");

        try {
            // Export repository
            byte[] export = brmsRepository.exportRulesRepositoryAsXml();
            brmsRepository.removeRule(ruleUUID);
            brmsRepository.importRulesRepository(export);
            assertTrue(true);
        } catch (RuleEngineRepositoryException e) {
            fail(e.getMessage());
        }

        RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertFalse(ruleSet.getUUID() == null);
    }

    @Test
    public void testExportImportRulesRepository() throws Exception {
        String ruleSetUUID = createRuleSet("testExportImportRulesRepository");
        String ruleUUID = createRule(ruleSetUUID, "testExportImportRulesRepository");

        try {
            // Export repository
            ByteArrayOutputStream export = brmsRepository.exportRulesRepositoryAsZip("repository-export.xml");
            assertNotNull(export);

            byte[] byteExport = toByteArrayFromZip(export, "repository-export.xml");
            assertNotNull(byteExport);

            brmsRepository.removeRule(ruleUUID);
            brmsRepository.importRulesRepository(byteExport);
            assertTrue(true);
        } catch (RuleEngineRepositoryException e) {
            fail(e.getMessage());
        }

        RuleSet ruleSet = brmsRepository.loadRuleSet(ruleSetUUID);
        assertFalse(ruleSet.getUUID() == null);
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
