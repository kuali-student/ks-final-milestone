package org.kuali.student.rules.repository.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.repository.drools.util.DroolsUtil;
import org.kuali.student.rules.repository.dto.RuleDTO;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.FactStructureDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.translators.util.Constants;
import org.kuali.student.rules.util.CurrentDateTime;
import org.kuali.student.rules.util.FactContainer;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;

public class RuleRepositoryServiceTest extends AbstractServiceTest {

    @Client(value="org.kuali.student.rules.repository.service.impl.RuleRepositoryServiceImpl", port="8181")
    public static RuleRepositoryService service; 

    @BeforeClass
    public static void setUpOnce() throws Exception {
    	//service = getService();
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
    
    /*private static RuleRepositoryService getService() {
        String serviceURL = "http://localhost:8080/brms-ws-0.0.1-SNAPSHOT/services/RuleRepositoryService";
        String namespace = "http://student.kuali.org/wsdl/brms/RuleRepository";
        String serviceName = "RuleRepositoryService";
        String serviceInterface = RuleRepositoryService.class.getName();

    	try {
			RuleRepositoryService service = (RuleRepositoryService) ServiceFactory.getPort(
			        serviceURL + "?wsdl", namespace, serviceName, serviceInterface, serviceURL);
			return service;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }*/

    private RuleSetDTO createRuleSet() {
    	RuleSetDTO dto = new RuleSetDTO("TestName", "Test description", "DRL");
    	return dto;
    }

    private RuleSetDTO createRuleSetWithRule() {
    	RuleSetDTO ruleSet = new RuleSetDTO("TestName", "Test description", "DRL");
    	RuleDTO rule = new RuleDTO("rule1", "A rule", "rule \"xxx\"\n when\n then\n end", "DRL");
    	ruleSet.addRule(rule);
    	return ruleSet;
    }

    @Test
    public void testCreateAndRemoveCategories() throws Exception {
        boolean b = service.createCategory("/", "EnrollmentRules", "A test category 1.0 description");
        assertTrue(b);
        b = service.createCategory("/EnrollmentRules", "Math", "A Math category description");
        assertTrue(b);
        b = service.createCategory("/EnrollmentRules/Math", "PreReq", "A PreReq category description");
        assertTrue(b);

        service.removeCategory("/EnrollmentRules/Math/PreReq");
        List<String> list = service.fetchChildCategories("/EnrollmentRules/Math");
        assertNull(list);

        service.removeCategory("/EnrollmentRules/Math");
        service.fetchChildCategories("/EnrollmentRules");
        assertNull(list);

        service.removeCategory("/EnrollmentRules");
        list = service.fetchChildCategories("/");
        assertNull(list);
    }

    @Test
    public void testCreateAndLoadCategories() throws Exception {
        boolean b = service.createCategory("/", "EnrollmentRules", "A test category 1.0 description");
        assertTrue(b);
        b = service.createCategory("/EnrollmentRules", "Math", "A Math category description");
        assertTrue(b);
        b = service.createCategory("/EnrollmentRules/Math", "PreReq", "A PreReq category description");
        assertTrue(b);

        List<String> category = service.fetchChildCategories("/");
        // assertTrue( category.length == 1 );
        assertEquals("EnrollmentRules", category.get(0));

        category = service.fetchChildCategories("/EnrollmentRules");
        assertTrue(category.size() == 1);
        assertEquals("Math", category.get(0));

        category = service.fetchChildCategories("/EnrollmentRules/Math");
        assertTrue(category.size() == 1);
        assertEquals("PreReq", category.get(0));
        
        service.removeCategory("/EnrollmentRules/Math/PreReq");
        service.removeCategory("/EnrollmentRules/Math");
        service.removeCategory("/EnrollmentRules");
    }

    @Test
    public void testCreateRuleSet() throws Exception {
    	RuleSetDTO ruleSet = service.createRuleSet( createRuleSet() );
        assertNotNull(ruleSet);
        service.removeRuleSet(ruleSet.getUUID());
        assertTrue(true);
    }

    @Test
    public void testRemoveRuleSet() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSet() );
        assertNotNull( ruleSet1.getUUID() );

        service.removeRuleSet( ruleSet1.getUUID() );
        try {
			RuleSetDTO ruleSet2 = service.fetchRuleSet(ruleSet1.getUUID());
			fail("Ruleset should have been removed");
		} catch (RuleEngineRepositoryException e) {
			assertTrue(true);
		}
    }

    @Test
    public void testFetchRuleSet() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSet() );
        assertNotNull(ruleSet1);
        
        RuleSetDTO ruleSet2 = service.fetchRuleSet(ruleSet1.getUUID());
        assertNotNull(ruleSet2);

        service.removeRuleSet( ruleSet1.getUUID() );
    }

    @Test
    public void testFetchCompiledRuleSet() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSet() );
        assertNotNull( ruleSet1 );
        
        byte[] binPkg = service.fetchCompiledRuleSet(ruleSet1.getUUID());
        org.drools.rule.Package pkg = DroolsUtil.getInstance().getPackage(binPkg);
        assertNotNull(pkg);
        assertTrue(pkg.isValid());

        service.removeRuleSet( ruleSet1.getUUID() );
    }
    
    @Test
    public void testCreateAndRemoveRuleSetSnapshot() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet(createRuleSet());
        assertNotNull(ruleSet1);

        service.createRuleSetSnapshot(ruleSet1.getName(), "snapshot1", "A new snapshot");
        RuleSetDTO snapshot = service.fetchRuleSetSnapshot(ruleSet1.getName(), "snapshot1");
        assertNotNull( snapshot );
        assertEquals("snapshot1", snapshot.getSnapshotName());
        assertEquals("A new snapshot", snapshot.getCheckinComment());

        service.removeRuleSetSnapshot(snapshot.getName(), "snapshot1");
        try {
			snapshot = service.fetchRuleSetSnapshot(snapshot.getName(), "snapshot1");
			fail("Snapshot should have been removed");
		} catch (RuleEngineRepositoryException e) {
			assertTrue(true);
		}

        service.removeRuleSet(ruleSet1.getUUID());
    }

    @Test
    public void testFetchRuleSetSnapshot() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet(createRuleSet());
        assertNotNull(ruleSet1);

        service.createRuleSetSnapshot(ruleSet1.getName(), "snapshot1", "A new snapshot");
        RuleSetDTO snapshot = service.fetchRuleSetSnapshot(ruleSet1.getName(), "snapshot1");
        assertNotNull( snapshot );
        assertEquals("snapshot1", snapshot.getSnapshotName());
        assertEquals("A new snapshot", snapshot.getCheckinComment());

        service.removeRuleSetSnapshot(snapshot.getName(), snapshot.getSnapshotName());
        service.removeRuleSet(ruleSet1.getUUID());
    }

    @Test
    public void testReplaceRuleSetSnapshot() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet(createRuleSet());
        assertNotNull(ruleSet1);

        service.createRuleSetSnapshot(ruleSet1.getName(), "snapshot1", "A new snapshot");
        RuleSetDTO snapshot = service.fetchRuleSetSnapshot(ruleSet1.getName(), "snapshot1");
        assertNotNull( snapshot );

        service.replaceRuleSetSnapshot(ruleSet1.getName(), "snapshot1", "Replace snapshot with new compilation");

		snapshot = service.fetchRuleSetSnapshot(snapshot.getName(), "snapshot1");
        assertNotNull( snapshot );
        assertEquals("snapshot1", snapshot.getSnapshotName());

        service.removeRuleSetSnapshot(snapshot.getName(), snapshot.getSnapshotName());
        service.removeRuleSet(ruleSet1.getUUID());
    }

    @Test
    public void testFetchCompiledRuleSetSnapshot() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSetWithRule() );
        assertNotNull( ruleSet1 );
        service.createRuleSetSnapshot(ruleSet1.getName(), "snapshot1", "A new snapshot");
       
        byte[] binPkg = service.fetchCompiledRuleSetSnapshot(ruleSet1.getName(), "snapshot1");
        org.drools.rule.Package pkg = DroolsUtil.getInstance().getPackage(binPkg);

        assertNotNull(pkg);
        assertTrue(pkg.isValid());

        service.removeRuleSetSnapshot(ruleSet1.getName(), "snapshot1");
        service.removeRuleSet( ruleSet1.getUUID() );
    }
    
    @Test
    public void testCreateAndRemoveState() throws Exception {
    	String uuid = service.createState("Active");
    	assertNotNull(uuid);
    	service.removeState("Active");

    	String[] states = service.fetchStates();
        assertEquals(1, states.length);
        assertEquals("Draft", states[0]); // default state is Draft
    }
    
    @Test
    public void testCreateAndFetchState() throws Exception {
    	String uuid = service.createState("Active");
    	assertNotNull(uuid);

    	String[] states = service.fetchStates();
        assertEquals(2, states.length);
        assertEquals("Draft", states[0]); // default state is Draft
        assertEquals("Active", states[1]);
    	service.removeState("Active");
    }
    
    @Test
    public void testChangeRuleSetState() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSet() );
        assertNotNull( ruleSet1 );
        assertEquals("Draft", ruleSet1.getStatus());

        service.createState("Active");
        service.createState("Inactive");
    	service.changeRuleSetState(ruleSet1.getUUID(), "Active");
    	
    	ruleSet1 = service.fetchRuleSet(ruleSet1.getUUID());
        assertEquals("Active", ruleSet1.getStatus());

        // Must remove ruleset before removing states
        service.removeRuleSet( ruleSet1.getUUID() );
        service.removeState("Active");
    	service.removeState("Inactive");
    }
    
    private Date createDate(int year, int month, int day, int hourOfDay, int minute, int seconds) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute, seconds);
    	return cal.getTime();
    }

    private BusinessRuleInfoDTO createBusinessRule(List<RuleElementDTO> ruleElementList) {
        BusinessRuleInfoDTO bri = new BusinessRuleInfoDTO();
    	bri.setName("Rule-1");
    	bri.setDescription("Some business rule");
    	bri.setSuccessMessage("Success message");
    	bri.setFailureMessage("Failure message");
    	bri.setBusinessRuleId("1");
    	bri.setBusinessRuleTypeKey("kuali.student.businessrule.typekey.course.corequisites");
    	bri.setAnchorTypeKey("kuali.student.lui.course.id");
    	bri.setAnchorValue("CPR101");
    	bri.setRuleElementList(ruleElementList);
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 0, 0);
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 0, 0);
    	bri.setEffectiveStartTime(effectiveStartTime);
    	bri.setEffectiveEndTime(effectiveEndTime);
    	return bri;
    }

    private RuleElementDTO createRule1(YieldValueFunctionDTO yieldValueFunction) {
    	// Rule 1
    	//YieldValueFunctionDTO yieldValueFunction = new YieldValueFunctionDTO();
        yieldValueFunction.setYieldValueFunctionType("INTERSECTION");
        
        LeftHandSideDTO leftHandSide = new LeftHandSideDTO();
        leftHandSide.setYieldValueFunction(yieldValueFunction);
       
        RightHandSideDTO rightHandSide = new RightHandSideDTO();
        rightHandSide.setExpectedValue("1");
        
        RulePropositionDTO ruleProposition = new RulePropositionDTO();
        ruleProposition.setName("Must-have-1-of-CPR101");
        ruleProposition.setDescription("Course intersection");
        ruleProposition.setLeftHandSide(leftHandSide);
        ruleProposition.setRightHandSide(rightHandSide);
        ruleProposition.setComparisonDataType(java.lang.Integer.class.getName());
        ruleProposition.setComparisonOperatorType(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

    	RuleElementDTO re = new RuleElementDTO();
        re.setName("course.intersection.1.of.cpr101");
        re.setDescription("Must have 1 of CPR 101");
        re.setOperation("PROPOSITION");
        re.setRuleProposition(ruleProposition);
        
        return re;
    }
    
    private RuleElementDTO createRule2(YieldValueFunctionDTO yieldValueFunction) {
    	// Rule 1
        yieldValueFunction.setYieldValueFunctionType("SUM");
        
        LeftHandSideDTO leftHandSide = new LeftHandSideDTO();
        leftHandSide.setYieldValueFunction(yieldValueFunction);
       
        RightHandSideDTO rightHandSide = new RightHandSideDTO();
        rightHandSide.setExpectedValue("10.0");
        
        RulePropositionDTO ruleProposition = new RulePropositionDTO();
        ruleProposition.setName("Course-credit-summation");
        ruleProposition.setDescription("Course credit summation");
        ruleProposition.setLeftHandSide(leftHandSide);
        ruleProposition.setRightHandSide(rightHandSide);
        ruleProposition.setComparisonDataType(java.lang.Double.class.getName());
        ruleProposition.setComparisonOperatorType(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

    	RuleElementDTO re = new RuleElementDTO();
        re.setName("course.credits.sum");
        re.setDescription("Pre req check for CPR 101");
        re.setOperation("PROPOSITION");
        re.setRuleProposition(ruleProposition);
        
        return re;
    }
    
    private void createFact1(YieldValueFunctionDTO yieldValueFunction1, String fact) {
    	// Facts - Rule 1 - 1 of CPR101
        FactStructureDTO fs1 = new FactStructureDTO();
        fs1.setDataType(java.util.Set.class.getName());
        fs1.setFactStructureId(fact);
        fs1.setAnchorFlag(false);

        Map<String,String> definitionVariableMap1 = new HashMap<String,String>();
        definitionVariableMap1.put(Constants.DEF_CRITERIA_KEY, "CPR101");
        fs1.setDefinitionVariableList(definitionVariableMap1);

        Map<String,String> executionVariableMap1 = new HashMap<String,String>();
        //executionVariableMap1.put(Constants.EXE_FACT_KEY, "intersection.courseSet");
        fs1.setExecutionVariableList(executionVariableMap1);
        
        List<FactStructureDTO> factStructureList1 = new ArrayList<FactStructureDTO>();
        factStructureList1.add(fs1);
        yieldValueFunction1.setFactStructureList(factStructureList1);
    }
    
    private void createFact2(YieldValueFunctionDTO yieldValueFunction2, String fact) {
        // Facts - Rule 2 - Sum of credit > 10.0
        FactStructureDTO fs2 = new FactStructureDTO();
        fs2.setDataType(java.math.BigDecimal.class.getName());
        fs2.setFactStructureId(fact);
        fs2.setAnchorFlag(false);

        // Not need for summation or averages
        Map<String,String> definitionVariableMap2 = new HashMap<String,String>();
        definitionVariableMap2.put(Constants.DEF_CRITERIA_KEY, null);
        fs2.setDefinitionVariableList(definitionVariableMap2);

        Map<String,String> executionVariableMap2 = new HashMap<String,String>();
        //executionVariableMap2.put(Constants.EXE_FACT_KEY, "summation.courseSet");
        fs2.setExecutionVariableList(executionVariableMap2);
        
        List<FactStructureDTO> factStructureList2 = new ArrayList<FactStructureDTO>();
        factStructureList2.add(fs2);
        yieldValueFunction2.setFactStructureList(factStructureList2);
    }
    
    private RuleElementDTO getAndOperator() {
    	RuleElementDTO re = new RuleElementDTO();
        re.setName("And");
        re.setDescription("And");
        re.setOperation("AND");
        
        return re;
    }
    
    private Set<String> createSet(String list) {
        Set<String> set = new HashSet<String>();
        for( String s : list.split(",") ) {
        	set.add(s.trim());
        }
        return set;
    }
    
    private List<BigDecimal> createList(String list) {
    	List<BigDecimal> set = new ArrayList<BigDecimal>();
        for( String s : list.split(",") ) {
        	set.add(new BigDecimal(s.trim()));
        }
        return set;
    }
    
    private Map<String,Object> getFacts(String factId1, String factId2) {
		Map<String,Object> factMap = new HashMap<String,Object>();

		Set<String> courseSet1 = createSet("CPR101,MATH102,CHEM100");
		factMap.put(factId1, courseSet1);

        List<BigDecimal> courseSet2 = createList("3.0,6.0,3.0");
        factMap.put(factId2, courseSet2);
        return factMap;
    }
    
    private BusinessRuleContainerDTO getBusinessRuleContainer(String fact1, String fact2) {
    	// Yield value functions
    	YieldValueFunctionDTO yieldValueFunction1 = new YieldValueFunctionDTO();
    	YieldValueFunctionDTO yieldValueFunction2 = new YieldValueFunctionDTO();

        // Rule elements
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        ruleElementList.add(createRule1(yieldValueFunction1));
        ruleElementList.add(getAndOperator());
        ruleElementList.add(createRule2(yieldValueFunction2));

        // Create functional business rule
        BusinessRuleInfoDTO bri = createBusinessRule(ruleElementList);

    	// Facts - Rule 1 - 1 of CPR101
    	createFact1(yieldValueFunction1,fact1);

        // Facts - Rule 2 - Sum of credit > 10.0
    	createFact2(yieldValueFunction2,fact2);

        // Parse and generate functional business rule into Drools rules
        BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        container.getBusinessRules().add(bri);
        
        return container;
    }
    
    private RuleBase getRuleBase(String source) throws Exception {
        PackageBuilder builder = new PackageBuilder();

        builder.addPackageFromDrl(new StringReader(source));

        Package pkg = builder.getPackage();

        // Add the package to a rulebase (deploy the rule package).
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage(pkg);
        return ruleBase;
    }

    private FactContainer execute(RuleSetDTO ruleSet, String anchor, 
    		String factId1, String factId2, 
    		String propName1, String propName2) throws Exception {
        factId1 = FactUtil.getFactKey(propName1, factId1, 0);
        factId2 = FactUtil.getFactKey(propName2, factId2, 0);
		Map<String,Object> factMap = getFacts(factId1, factId2);

		FactContainer facts =  new FactContainer(anchor, factMap);

        // Collection of Propositions
        PropositionContainer prop = facts.getPropositionContainer();

        // Execute rule
        // Execute Drools rule set source code
        WorkingMemory workingMemory = getRuleBase(ruleSet.getContent()).newStatefulSession();
        workingMemory.insert(new CurrentDateTime());
        workingMemory.insert(facts);
        workingMemory.fireAllRules();

        return facts;
    }

    // TODO: FixMe - Throws a SOAPFaultException when using AbstractServiceTest
	// and @Client annotation from within Eclipse??? Works when deployed to Tomcat???
    @Ignore
    @Test
    public void testGenerateRuleSet() throws Exception {
    	BusinessRuleContainerDTO container = getBusinessRuleContainer("fact1", "fact2");
    	RuleSetDTO ruleSet1 = service.generateRuleSet(container);
    	assertNotNull(ruleSet1);
    	assertNotNull(ruleSet1.getUUID());
        service.removeRuleSet( ruleSet1.getUUID() );
    }

    // TODO: FixMe - Throws a SOAPFaultException when using AbstractServiceTest
	// and @Client annotation from within Eclipse??? Works when deployed to Tomcat???
    @Ignore
    @Test
    public void testGenerateRuleSetAndExecute() throws Exception {
		String fact1 = "fact1";
		String fact2 = "fact2";
    	BusinessRuleContainerDTO container = getBusinessRuleContainer(fact1, fact2);
		RuleSetDTO ruleSet = service.generateRuleSet(container);

		assertNotNull(ruleSet);
		assertNotNull(ruleSet.getUUID());
		
		BusinessRuleInfoDTO bri = container.getBusinessRules().get(0);
		String anchor = bri.getAnchorValue();
		String propName1 = bri.getRuleElementList().get(0).getRuleProposition().getName();
		String propName2 = bri.getRuleElementList().get(2).getRuleProposition().getName();
		FactContainer fact = execute(ruleSet, anchor, fact1, fact2, propName1, propName2);
        assertTrue(fact.getPropositionContainer().getRuleResult());
		service.removeRuleSet(ruleSet.getUUID());
    }

}
