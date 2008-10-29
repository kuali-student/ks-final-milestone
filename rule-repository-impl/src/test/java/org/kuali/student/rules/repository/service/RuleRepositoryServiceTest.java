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
package org.kuali.student.rules.repository.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.factfinder.dto.FactCriteriaTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactParamDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactParamDTO.FactParamDefTime;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.repository.drools.util.DroolsUtil;
import org.kuali.student.rules.repository.dto.RuleDTO;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.translators.drools.RuleManagementDtoFactory;
import org.kuali.student.rules.util.CurrentDateTime;
import org.kuali.student.rules.util.FactContainer;

public class RuleRepositoryServiceTest extends AbstractServiceTest {

    private final RuleManagementDtoFactory dtoFactory = RuleManagementDtoFactory.getInstance();

    // Automatically loads rule-repository-mock-service-context.xml (*-mock-service-context.xml)
	// and auto-wires by type
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
    	return createRuleSet("TestName", null);
    }

    private RuleSetDTO createRuleSet(String name, String category) {
    	RuleSetDTO dto = new RuleSetDTO(name, "Test description", "DRL");
    	if (category != null) {
	    	dto.addCategory(category, "/");
    	}
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
        List<String> list = service.fetchCategories("/EnrollmentRules/Math");
        assertNull(list);

        service.removeCategory("/EnrollmentRules/Math");
        service.fetchCategories("/EnrollmentRules");
        assertNull(list);

        service.removeCategory("/EnrollmentRules");
        list = service.fetchCategories("/");
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

        List<String> category = service.fetchCategories("/");
        // assertTrue( category.length == 1 );
        assertEquals("EnrollmentRules", category.get(0));

        category = service.fetchCategories("/EnrollmentRules");
        assertTrue(category.size() == 1);
        assertEquals("Math", category.get(0));

        category = service.fetchCategories("/EnrollmentRules/Math");
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
		} catch (OperationFailedException e) {
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
    public void testFetchRuleSetsByCategory() throws Exception {
        String category = "RuleSetCollection";
        boolean b = service.createCategory("/", category, "A test category 1.0 description");
        assertTrue(b);

        RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSet("Rule-1", category) );
        assertNotNull(ruleSet1);
    	RuleSetDTO ruleSet2 = service.createRuleSet( createRuleSet("Rule-2", category) );
        assertNotNull(ruleSet2);

        List<RuleSetDTO> list = service.fetchRuleSetsByCategory(category);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(ruleSet1.getUUID(), list.get(0).getUUID());
        assertEquals(ruleSet2.getUUID(), list.get(1).getUUID());

        service.removeRuleSet(ruleSet1.getUUID());
        service.removeRuleSet(ruleSet2.getUUID());
        service.removeCategory("/"+category);
    }

    @Test
    public void testCheckinRuleSet() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSet() );
        assertNotNull(ruleSet1);
        assertEquals( 1L, ruleSet1.getVersionNumber());
        
        long version = service.checkinRuleSet(ruleSet1.getUUID(), "Checkin ruleset version 2");

        RuleSetDTO ruleSet2 = service.fetchRuleSet(ruleSet1.getUUID());
        assertNotNull(ruleSet2);
        assertEquals(version, ruleSet2.getVersionNumber());
        assertEquals(2L, version);

        service.removeRuleSet( ruleSet1.getUUID() );
    }

    @Test
    public void testFetchCompiledRuleSet() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSet() );
        assertNotNull( ruleSet1 );
        
        byte[] binPkg = service.fetchRuleSet(ruleSet1.getUUID()).getCompiledRuleSet();
        org.drools.rule.Package pkg = DroolsUtil.getInstance().getPackage(binPkg);
        assertNotNull(pkg);
        assertTrue(pkg.isValid());

        service.removeRuleSet( ruleSet1.getUUID() );
    }
    
    @Test
    public void testCreateAndRemoveRuleSetSnapshot() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet(createRuleSet());
        assertNotNull(ruleSet1);

        service.createRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1", "A new snapshot");
        RuleSetDTO snapshot = service.fetchRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1");
        assertNotNull( snapshot );
        assertEquals("snapshot1", snapshot.getSnapshotName());
        assertEquals("A new snapshot", snapshot.getCheckinComment());

        service.removeRuleSetSnapshot(snapshot.getUUID(), "snapshot1");
        try {
			snapshot = service.fetchRuleSetSnapshot(snapshot.getUUID(), "snapshot1");
			fail("Snapshot should have been removed");
		} catch (OperationFailedException e) {
			assertTrue(true);
		}

        service.removeRuleSet(ruleSet1.getUUID());
    }

    @Test
    public void testFetchRuleSetSnapshot() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet(createRuleSet());
        assertNotNull(ruleSet1);

        service.createRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1", "A new snapshot");
        RuleSetDTO snapshot = service.fetchRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1");
        assertNotNull( snapshot );
        assertEquals("snapshot1", snapshot.getSnapshotName());
        assertEquals("A new snapshot", snapshot.getCheckinComment());

        service.removeRuleSetSnapshot(snapshot.getUUID(), snapshot.getSnapshotName());
        service.removeRuleSet(ruleSet1.getUUID());
    }

    @Test
    public void testFetchRuleSetSnapshotsByCategory() throws Exception {
        String category = "RuleSetCollection";
        boolean b = service.createCategory("/", category, "A test category 1.0 description");
        assertTrue(b);

        RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSet("Rule-1", category) );
        assertNotNull(ruleSet1);
    	RuleSetDTO ruleSet2 = service.createRuleSet( createRuleSet("Rule-2", category) );
        assertNotNull(ruleSet2);
        
        service.createRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1", "A new snapshot");
        service.createRuleSetSnapshot(ruleSet2.getUUID(), "snapshot2", "A new snapshot");
        
        List<RuleSetDTO> list = service.fetchRuleSetSnapshotsByCategory(category);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(ruleSet1.getName(), list.get(0).getName());
        assertEquals("snapshot1", list.get(0).getSnapshotName());
        assertEquals(ruleSet2.getName(), list.get(1).getName());
        assertEquals("snapshot2", list.get(1).getSnapshotName());
        		
        service.removeRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1");
        service.removeRuleSetSnapshot(ruleSet2.getUUID(), "snapshot2");
        service.removeRuleSet( ruleSet1.getUUID() );
        service.removeRuleSet( ruleSet2.getUUID() );
        service.removeCategory("/"+category);
    }

    @Test
    public void testReplaceRuleSetSnapshot() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet(createRuleSet());
        assertNotNull(ruleSet1);

        service.createRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1", "A new snapshot");
        RuleSetDTO snapshot = service.fetchRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1");
        assertNotNull( snapshot );

        service.rebuildRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1", "Replace snapshot with new compilation");

		snapshot = service.fetchRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1");
        assertNotNull( snapshot );
        assertEquals("snapshot1", snapshot.getSnapshotName());

        service.removeRuleSetSnapshot(snapshot.getUUID(), snapshot.getSnapshotName());
        service.removeRuleSet(ruleSet1.getUUID());
    }

    @Test
    public void testFetchCompiledRuleSetSnapshot() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSetWithRule() );
        assertNotNull( ruleSet1 );
        service.createRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1", "A new snapshot");
       
        byte[] binPkg = service.fetchRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1").getCompiledRuleSet();
        org.drools.rule.Package pkg = DroolsUtil.getInstance().getPackage(binPkg);

        assertNotNull(pkg);
        assertTrue(pkg.isValid());

        service.removeRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1");
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

    private BusinessRuleInfoDTO createBusinessRule(String anchorValue, List<RuleElementDTO> ruleElementList) {
        BusinessRuleInfoDTO bri = new BusinessRuleInfoDTO();
    	bri.setName("MyNewBusinessRule");
    	bri.setDescription("Some business rule");
    	bri.setSuccessMessage("Success message");
    	bri.setFailureMessage("Failure message");
    	bri.setBusinessRuleId("1");
    	bri.setBusinessRuleTypeKey("kuali.student.businessrule.typekey.course.corequisites");
    	bri.setAnchorTypeKey("kuali.student.lui.course.id");
    	bri.setAnchorValue(anchorValue);
    	bri.setRuleElementList(ruleElementList);
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 0, 0);
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 0, 0);
    	bri.setEffectiveStartTime(effectiveStartTime);
    	bri.setEffectiveEndTime(effectiveEndTime);
    	return bri;
    }

    private RuleElementDTO getAndOperator() {
    	RuleElementDTO re = new RuleElementDTO();
        re.setName("And");
        re.setDescription("And");
        re.setOperation("AND");
        
        return re;
    }
    
    private RulePropositionDTO getRuleProposition(
    		String yieldValueFunctionType, 
    		Object criteria, 
    		String criteriaId,
    		String comparisonOperator, 
    		String expectedValue,
    		String comparisonOperatorType,
    		String factKey) {

    	YieldValueFunctionDTO yieldValueFunction = dtoFactory.createYieldValueFunctionDTO(null, yieldValueFunctionType);
    	LeftHandSideDTO leftSide = dtoFactory.createLeftHandSideDTO(yieldValueFunction);
    	RightHandSideDTO rightSide = dtoFactory.createRightHandSideDTO(expectedValue);
        RulePropositionDTO ruleProp = dtoFactory.createRulePropositionDTO(
        		"PROPOSITION_NAME", comparisonOperatorType, 
        		comparisonOperator, leftSide, rightSide);
        
        FactStructureDTO factStructure = new FactStructureDTO();
        //factStructure.setDataType(java.util.Set.class.getName());
        //factStructure.setFactStructureId("some id");
        factStructure.setAnchorFlag(false);

        // DEFINITION
        Map<String, FactParamDTO> factParamMap = new HashMap<String, FactParamDTO>();
        FactParamDTO param1 = new FactParamDTO();
        param1.setDataType("");
        param1.setDefTime(FactParamDefTime.DEFINITION);
        param1.setKey(criteriaId);
        factParamMap.put(criteriaId, param1);
        
        FactParamDTO param2 = new FactParamDTO();
        param2.setDataType("");
        param2.setDefTime(FactParamDefTime.EXECUTION);
        param2.setKey(factKey);
        factParamMap.put(factKey, param2);
        
        FactCriteriaTypeInfoDTO criteriaTypeInfo = new FactCriteriaTypeInfoDTO();
        criteriaTypeInfo.setFactParamMap(factParamMap);
        factStructure.setCriteriaTypeInfo(criteriaTypeInfo);
        
        Map<String,Object> paramValueMap = new HashMap<String,Object>();
        paramValueMap.put(criteriaId, criteria);
        factStructure.setParamValueMap(paramValueMap);
        
        List<FactStructureDTO> factStructureList = new ArrayList<FactStructureDTO>();
        factStructureList.add(factStructure);
        yieldValueFunction.setFactStructureList(factStructureList);

        return ruleProp;
    }

    private BusinessRuleContainerDTO getBusinessRuleContainer(
    		String anchorValue,
    		String criteriaKey1, String criteriaKey2,
    		String factKey1, String factKey2) {
    	// JAXB doesn't allow you to set a java.util.Set to a JAXB defined element
    	// only Java objects are allowed
    	//Set<String> criteria1 = new HashSet<String>(Arrays.asList("CPR101"));
    	//Set<String> criteria2 = new HashSet<String>(Arrays.asList("CPR101"));
    	String criteria1 = "CPR101";
    	String criteria2 = "CPR101";

    	// Create rule elements
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			YieldValueFunctionType.SUBSET.toString(), 
            			criteria1,
            			criteriaKey1,
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			factKey1));
        RuleElementDTO element2 = dtoFactory.createRuleElementDTO(
        		"element-2", "PROPOSITION", 
        		new Integer(1), getRuleProposition(
            			YieldValueFunctionType.INTERSECTION.toString(), 
            			criteria2,
            			criteriaKey2,
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			factKey2));
    	ruleElementList.add(element1);
        ruleElementList.add(getAndOperator());
        ruleElementList.add(element2);

        // Create functional business rule
        BusinessRuleInfoDTO bri = createBusinessRule(anchorValue, ruleElementList);
        bri.setAnchorValue(anchorValue);

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
    
    private FactResultDTO createFactResult(String values) {
    	List<String> list = Arrays.asList(values.split(","));
    	FactResultDTO factResult = new FactResultDTO();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for(String item : list) {
        	Map<String, Object> row = new HashMap<String, Object>();
	        row.put("column1", item);
	        resultList.add(row);
        }

        factResult.setResultList(resultList);
        return factResult;
    }

    private FactContainer execute(RuleSetDTO ruleSet, String anchor, 
    		String criteriaKey1, String criteriaKey2,
    		String factKey1, String factKey2) throws Exception {
        // EXECUTION: Create facts
        FactResultDTO factResult1 = createFactResult("CPR101,MATH101,CHEM101");
        FactResultDTO factResultCriteria1 = createFactResult("CPR101");
        FactResultDTO factResult2 = createFactResult("CPR102,MATH102,CHEM102");
        FactResultDTO factResultCriteria2 = createFactResult("MATH102");

    	//Map<String,Object> factMap = getFacts(factId1, factId2);
        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKey1, factResultCriteria1);
        factMap.put(factKey1, factResult1);
        factMap.put(criteriaKey2, factResultCriteria2);
        factMap.put(factKey2, factResult2);
        
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

    @Test
    public void testGenerateAndCreateRuleSet() throws Exception {
    	BusinessRuleContainerDTO container = getBusinessRuleContainer(
    			"CPR101", "CriteriaKey1", "CriteriaKey2", "factKey1", "factKey2");
    	// Generate and create new rule set
    	RuleSetDTO ruleSet1 = service.generateRuleSet(container);

    	assertNotNull(ruleSet1);
    	assertNotNull(ruleSet1.getUUID());
        service.removeRuleSet( ruleSet1.getUUID() );
    }

    @Test
    public void testGenerateAndUpdateRuleSet() throws Exception {
    	BusinessRuleContainerDTO container = getBusinessRuleContainer(
    			"CPR101", "CriteriaKey1", "CriteriaKey2", "factKey1", "factKey2");
    	// Generate and create new rule set
    	RuleSetDTO ruleSet1 = service.generateRuleSet(container);

    	assertNotNull(ruleSet1);
    	assertNotNull(ruleSet1.getUUID());

    	// Update rule's RHS expected value and set rule set UUID
		BusinessRuleInfoDTO bri = container.getBusinessRules().get(0);
		bri.setCompiledId(ruleSet1.getUUID());
		bri.setCompiledVersionNumber(ruleSet1.getVersionNumber());
		RightHandSideDTO rhs = bri.getRuleElementList().get(2).getRuleProposition().getRightHandSide();
		rhs.setExpectedValue("6.0");
    	// Generate and update rule set
    	RuleSetDTO ruleSet2 = service.generateRuleSet(container);

    	assertNotNull(ruleSet2);
    	assertNotNull(ruleSet2.getUUID());
    	assertFalse(ruleSet1.getContent().equals(ruleSet2.getContent()));
		
    	service.removeRuleSet( ruleSet2.getUUID() );
    }

    @Test
    public void testGenerateRuleSetAndExecute() throws Exception {
    	String factKey1 = "FactKey1";
    	String criteriaKey1 = "CriteriaKey1";
    	String factKey2 = "FactKey2";
    	String criteriaKey2 = "CriteriaKey2";

    	BusinessRuleContainerDTO container = getBusinessRuleContainer(
    			"CPR101", criteriaKey1, criteriaKey2, factKey1, factKey2);
    	// Generate and create new rule set
		RuleSetDTO ruleSet = service.generateRuleSet(container);
		assertNotNull(ruleSet);
		assertNotNull(ruleSet.getUUID());
		assertNotNull(ruleSet.getRules());
		
    	// Update rule's RHS expected value and set rule set UUID
		BusinessRuleInfoDTO bri = container.getBusinessRules().get(0);
		bri.setCompiledId(ruleSet.getUUID());
		bri.setCompiledVersionNumber(ruleSet.getVersionNumber());
		String anchor = bri.getAnchorValue();
		
		FactContainer fact = execute(ruleSet, anchor, 
				criteriaKey1, criteriaKey2, factKey1, factKey2);
				//propName1, propName2);
		
        assertTrue(fact.getPropositionContainer().getRuleResult());
		service.removeRuleSet(ruleSet.getUUID());
    }
    
}
