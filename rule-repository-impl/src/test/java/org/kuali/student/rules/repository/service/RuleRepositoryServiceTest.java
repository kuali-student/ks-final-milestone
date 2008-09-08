package org.kuali.student.rules.repository.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.rules.repository.drools.util.DroolsUtil;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;

public class RuleRepositoryServiceTest extends AbstractServiceTest {

    //private final static String businessRule = "A0";
    private final static String businessRule = "A0*B4+(C*D)";

    @Client(value="org.kuali.student.rules.repository.service.impl.RuleRepositoryServiceImpl", port="8181")
    public RuleRepositoryService service; 

    @BeforeClass
    public static void setUpOnce() throws Exception {
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private RuleSetDTO createRuleSet() {
    	RuleSetDTO dto = new RuleSetDTO("TestName", "Test description", "DRL");
    	return dto;
    }

    /*private List<org.kuali.student.rules.util.Constraint> getConstraints() {
        List<org.kuali.student.rules.util.Constraint> list = new ArrayList<org.kuali.student.rules.util.Constraint>();
        
        list.add( createConstraint( "A0: A constraintID from DB") );
        list.add( createConstraint( "B4: A constraintID from DB") );
        list.add( createConstraint( "C: A constraintID from DB") );
        list.add( createConstraint( "D: A constraintID from DB") );
        
        return list;
    }
    
    private org.kuali.student.rules.util.Constraint createConstraint( String constraintID ) {
        ConstraintStrategy cs = new MockConstraint();
        cs.setConstraintID(constraintID);
        org.kuali.student.rules.util.Constraint c = 
            new org.kuali.student.rules.util.Constraint(cs); 
        return c;
    }*/
    
    @Test
    public void testCreateAndLoadCategories() throws Exception {
        boolean b = service.createCategory("/", "EnrollmentRules", "A test category 1.0 description");
        assertTrue(b);
        b = service.createCategory("/EnrollmentRules", "Math", "A Math category description");
        assertTrue(b);
        b = service.createCategory("/EnrollmentRules", "English", "An English category description");
        assertTrue(b);
        b = service.createCategory("/EnrollmentRules/Math", "PreReq", "A PreReq category description");
        assertTrue(b);
        b = service.createCategory("/EnrollmentRules/Math", "CoReq", "A CoReq category description");
        assertTrue(b);

        List<String> category = service.fetchChildCategories("/");
        // assertTrue( category.length == 1 );
        assertEquals("EnrollmentRules", category.get(0));

        category = service.fetchChildCategories("/EnrollmentRules");
        assertTrue(category.size() == 2);
        assertEquals("Math", category.get(0));
        assertEquals("English", category.get(1));

        category = service.fetchChildCategories("/EnrollmentRules/Math");
        assertTrue(category.size() == 2);
        assertEquals("PreReq", category.get(0));
        assertEquals("CoReq", category.get(1));
    }

    @Test
    public void testCreateRuleSet() throws Exception {
    	RuleSetDTO ruleSet = service.createRuleSet( createRuleSet() );
        assertNotNull( ruleSet );
        service.removeRuleSet( ruleSet.getUUID() );
        assertTrue( true );
    }

    @Test
    public void testRemoveRuleSet() throws Exception {
    	RuleSetDTO ruleSet = service.createRuleSet( createRuleSet() );
        assertNotNull( ruleSet.getUUID() );
        service.removeRuleSet( ruleSet.getUUID() );
        assertTrue( true );
    }

    @Test
    public void testLoadRuleSet() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSet() );
        assertNotNull( ruleSet1 );
        
        RuleSetDTO ruleSet2 = service.fetchRuleSet(ruleSet1.getUUID());
        assertNotNull( ruleSet2 );
        System.out.println( "Rule Set: " + ruleSet2.toString());

        service.removeRuleSet( ruleSet1.getUUID() );
    }

    @Test
    public void testLoadCompiledRuleSet() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSet() );
        assertNotNull( ruleSet1 );
        
        byte[] binPkg = service.fetchCompiledRuleSet(ruleSet1.getUUID());
        org.drools.rule.Package pkg = DroolsUtil.getInstance().getPackage(binPkg);
        System.out.println( "pkg: " + pkg);
        assertNotNull(pkg);
        assertTrue(pkg.isValid());
        System.out.println( "Rule Set: " + pkg.getName());

        service.removeRuleSet( ruleSet1.getUUID() );
    }

    /*@Test
    public void testGetRuleSetAndExecute() throws Exception {
        String uuid = null;
        try {
            uuid = service.createRuleSet( createRuleSet() );
            assertNotNull( uuid );
            
            BinaryRuleSetObject bin = service.getRuleSet(uuid);
            assertNotNull( bin );
            assertNotNull( bin.getObject() );
            org.drools.rule.Package pkg = (org.drools.rule.Package) DroolsUtil.getInstance().getPackage( bin.getObject() );
            assertNotNull( pkg );
    
            Propositions prop = new Propositions();
            Propositions.init(businessRule);
            Propositions.setProposition("xxx", false);
            
            //DroolsTestUtil.getInstance().executeRule(pkg, new Object[]{prop, getConstraints().toArray()});
        } finally {
            service.removeRuleSet( uuid );
        }
    }

    @Test
    public void testGetRuleSet_MultipleTimes() throws Exception {
        String uuid = service.createRuleSet( createRuleSet() );
        assertNotNull( uuid );
        
        final int INTERATIONS = 1000;
        long beginTime = System.currentTimeMillis();
        for( int i=0; i<INTERATIONS; i++ ) {
            BinaryRuleSetObject bin = service.getRuleSet(uuid);
            assertNotNull( bin );
            assertNotNull( bin.getObject() );
        }
        long endTime = System.currentTimeMillis();
        double exeTime = (double) ( endTime - beginTime ) / 1000;
        System.out.println( "Execution Time: " + exeTime );

        service.removeRuleSet( uuid );
    }
    
    private class MockConstraint extends AbstractProposition<T> {
        
        @Override
        protected void cacheAdvice(String format, Object... args) {
            String propVar = (String) args[2];
            Propositions.setFailureMessage(propVar, "Some good failure message!");
            
        }

        @Override
        public Boolean apply(String propVar) {
            return ( getConstraintID().startsWith(propVar) ? true : false );
            //return false;
        }
    }*/

}
