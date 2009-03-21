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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.factfinder.dto.FactCriteriaTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultColumnInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.statement.MessageContextConstants;
import org.kuali.student.rules.internal.common.statement.propositions.rules.IntersectionRuleProposition;
import org.kuali.student.rules.internal.common.statement.propositions.rules.SubsetRuleProposition;
import org.kuali.student.rules.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.repository.drools.util.DroolsUtil;
import org.kuali.student.rules.repository.dto.RuleDTO;
import org.kuali.student.rules.repository.dto.RuleSetContainerDTO;
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

        RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSet("RuleSet1", category) );
        assertNotNull(ruleSet1);
    	RuleSetDTO ruleSet2 = service.createRuleSet( createRuleSet("RuleSet2", category) );
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
        //org.drools.rule.Package pkg = DroolsUtil.getInstance().getPackage(binPkg);
        org.drools.definition.KnowledgePackage pkg = DroolsUtil.getInstance().getKnowledgePackage(binPkg);
        assertNotNull(pkg);
        //assertTrue(pkg.isValid());

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
    public void testCreateRuleSetSnapshotVersions() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet(createRuleSet());
        assertNotNull(ruleSet1);

        RuleSetDTO snapshot1 = service.createRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1", "A new snapshot");
        assertFalse(ruleSet1.getUUID() == snapshot1.getUUID());
        assertEquals(2L, snapshot1.getVersionNumber());

        RuleSetDTO snapshot2 = service.fetchRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1");
        assertNotNull(snapshot2);
        assertFalse(ruleSet1.getUUID() == snapshot2.getUUID());
        assertEquals(2L, snapshot2.getVersionNumber());

        snapshot2 = service.fetchRuleSet(snapshot2.getUUID());
        assertNotNull(snapshot2);
        assertEquals(2L, snapshot2.getVersionNumber());
        
        RuleSetDTO rebuiltSnapshot1 = service.replaceRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1", "A rebuilt snapshot");
        assertFalse(ruleSet1.getUUID() == rebuiltSnapshot1.getUUID());
        assertEquals(2L, rebuiltSnapshot1.getVersionNumber());

        service.removeRuleSetSnapshot(rebuiltSnapshot1.getUUID(), rebuiltSnapshot1.getSnapshotName());
        service.removeRuleSet(ruleSet1.getUUID());
    }

    @Test
    public void testFetchRuleSetSnapshotsByCategory() throws Exception {
        String category = "RuleSetCollection";
        boolean b = service.createCategory("/", category, "A test category 1.0 description");
        assertTrue(b);

        RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSet("RuleSet1", category) );
        assertNotNull(ruleSet1);
    	RuleSetDTO ruleSet2 = service.createRuleSet( createRuleSet("RuleSet2", category) );
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
        service.removeRuleSet(ruleSet1.getUUID());
        service.removeRuleSet(ruleSet2.getUUID());
        service.removeCategory("/"+category);
    }

    @Test
    public void testReplaceRuleSetSnapshot() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet(createRuleSet());
        assertNotNull(ruleSet1);

        service.createRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1", "A new snapshot1");
        RuleSetDTO snapshot1 = service.fetchRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1");
        assertNotNull(snapshot1);

        service.createRuleSetSnapshot(ruleSet1.getUUID(), "snapshot2", "A new snapshot2");
        RuleSetDTO snapshot2 = service.fetchRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1");
        assertNotNull(snapshot2);

        service.replaceRuleSetSnapshot(ruleSet1.getUUID(), "snapshot2", "Replace current snapshot (snapshot1) with new snapshot2");

		snapshot2 = service.fetchRuleSetSnapshot(ruleSet1.getUUID(), "snapshot2");
        assertNotNull(snapshot2);
        assertEquals("snapshot2", snapshot2.getSnapshotName());

        service.removeRuleSetSnapshot(snapshot2.getUUID(), snapshot2.getSnapshotName());
        service.removeRuleSetSnapshot(snapshot1.getUUID(), snapshot1.getSnapshotName());
        service.removeRuleSet(ruleSet1.getUUID());
    }

    @Test
    public void testRebuildRuleSetSnapshot() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet(createRuleSet());
        assertNotNull(ruleSet1);

        RuleSetDTO snapshot1 = service.createRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1", "A new snapshot");

        service.rebuildRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1");

        RuleSetDTO snapshot2 = service.fetchRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1");

        assertTrue(snapshot2.getLastModifiedDate().getTimeInMillis() > snapshot1.getLastModifiedDate().getTimeInMillis());

        service.removeRuleSetSnapshot(snapshot1.getUUID(), snapshot1.getSnapshotName());
        service.removeRuleSet(ruleSet1.getUUID());
    }

    @Test
    public void testFetchCompiledRuleSetSnapshot() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSetWithRule() );
        assertNotNull( ruleSet1 );
        service.createRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1", "A new snapshot");
       
        byte[] binPkg = service.fetchRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1").getCompiledRuleSet();
        //org.drools.rule.Package pkg = DroolsUtil.getInstance().getPackage(binPkg);
        org.drools.definition.KnowledgePackage pkg = DroolsUtil.getInstance().getKnowledgePackage(binPkg);

        assertNotNull(pkg);
        //assertTrue(pkg.isValid());

        service.removeRuleSetSnapshot(ruleSet1.getUUID(), "snapshot1");
        service.removeRuleSet(ruleSet1.getUUID());
    }
    
    @Test
    public void testCreateAndRemoveState() throws Exception {
    	String uuid = service.createState("Active");
    	assertNotNull(uuid);
    	service.removeState("Active");

    	List<String> states = service.fetchStates();
        assertEquals(1, states.size());
        assertEquals("Draft", states.get(0)); // default state is Draft
    }
    
    @Test
    public void testCreateAndFetchState() throws Exception {
    	String uuid = service.createState("Active");
    	assertNotNull(uuid);

    	List<String> states = service.fetchStates();
        assertEquals(2, states.size());
        assertEquals("Draft", states.get(0)); // default state is Draft
        assertEquals("Active", states.get(1));
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

    private BusinessRuleInfoDTO createBusinessRule(String ruleId, String ruleName, 
    		String anchorValue, List<RuleElementDTO> ruleElementList) {
        BusinessRuleInfoDTO bri = new BusinessRuleInfoDTO();
    	//bri.setName("MyNewBusinessRule");
    	bri.setName(ruleName);
    	bri.setDesc("Some business rule");
    	bri.setSuccessMessage("Success message");
    	bri.setFailureMessage("Failure message");
    	//bri.setBusinessRuleId("1");
    	bri.setId(ruleId);
    	bri.setType("kuali.student.businessrule.typekey.course.corequisites");
    	bri.setAnchorTypeKey("kuali.student.lui.course.id");
    	bri.setAnchorValue(anchorValue);
    	bri.setBusinessRuleElementList(ruleElementList);
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 0, 0);
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 0, 0);
    	bri.setEffectiveDate(effectiveStartTime);
    	bri.setExpirationDate(effectiveEndTime);
    	return bri;
    }

    private RuleElementDTO getAndOperator() {
    	RuleElementDTO re = new RuleElementDTO();
        re.setName("And");
        re.setDescription("And");
        re.setBusinessRuleElemnetTypeKey("AND");
        
        return re;
    }
    
    private RulePropositionDTO createRuleProposition(
    		String yieldValueFunctionType, 
    		String comparisonOperator, 
    		String expectedValue,
    		String comparisonOperatorType,
    		YieldValueFunctionDTO yieldValueFunction) {

    	LeftHandSideDTO leftSide = dtoFactory.createLeftHandSideDTO(yieldValueFunction);
    	RightHandSideDTO rightSide = dtoFactory.createRightHandSideDTO(expectedValue);
        RulePropositionDTO ruleProp = dtoFactory.createRulePropositionDTO(
        		"co-requisites", comparisonOperatorType, 
        		comparisonOperator, leftSide, rightSide);
        return ruleProp;
    }

    private BusinessRuleInfoDTO getBusinessRule(String ruleId, String ruleName, String anchorValue) {
    	YieldValueFunctionDTO yieldValueFunction1 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SUBSET.toString());
    	YieldValueFunctionDTO yieldValueFunction2 = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		Map<String,String> subsetResultColumnKey = new HashMap<String, String>();
		subsetResultColumnKey.put(MessageContextConstants.PROPOSITION_SUBSET_COLUMN_KEY, "column1");

		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		factStructure1.setResultColumnKeyTranslations(subsetResultColumnKey);
		FactStructureDTO factStructure2 = createFactStructure("subset.id.2", "course.subset.fact");
		factStructure2.setResultColumnKeyTranslations(subsetResultColumnKey);

		yieldValueFunction1.setFactStructureList(Arrays.asList(factStructure1, factStructure2));
		
		Map<String,String> intersectionResultColumnKey = new HashMap<String, String>();
		intersectionResultColumnKey.put(MessageContextConstants.PROPOSITION_INTERSECTION_COLUMN_KEY, "column1");

		FactStructureDTO factStructure3 = createFactStructure("subset.id.3", "course.subset.criteria");
		factStructure3.setResultColumnKeyTranslations(intersectionResultColumnKey);
		FactStructureDTO factStructure4 = createFactStructure("subset.id.4", "course.subset.fact");
		factStructure4.setResultColumnKeyTranslations(intersectionResultColumnKey);

		yieldValueFunction2.setFactStructureList(Arrays.asList(factStructure3, factStructure4));

		List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        RuleElementDTO element1 = dtoFactory.createRuleElementDTO(
        		"element-1", "PROPOSITION", 
        		new Integer(1), createRuleProposition(
            			YieldValueFunctionType.SUBSET.toString(), 
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction1));
        RuleElementDTO element2 = dtoFactory.createRuleElementDTO(
        		"element-2", "PROPOSITION", 
        		new Integer(1), createRuleProposition(
            			YieldValueFunctionType.INTERSECTION.toString(), 
            			ComparisonOperator.EQUAL_TO.toString(),
            			"1",
            			Integer.class.getName(),
            			yieldValueFunction2));
    	ruleElementList.add(element1);
        ruleElementList.add(getAndOperator());
        ruleElementList.add(element2);

        // Create functional business rule
        BusinessRuleInfoDTO businessRule = createBusinessRule(ruleId, ruleName, anchorValue, ruleElementList);
        businessRule.setAnchorValue(anchorValue);

        return businessRule;
    }

    private BusinessRuleContainerDTO getBusinessRuleContainer(String ruleId, String ruleName, String anchorValue) {
    	BusinessRuleInfoDTO businessRule = getBusinessRule(ruleId, ruleName, anchorValue);
    	BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        container.getBusinessRules().add(businessRule);

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
    
    private FactStructureDTO createFactStructure(String factStructureId, String criteriaTypeName) {
    	FactStructureDTO factStructure1 = new FactStructureDTO();
	    factStructure1.setFactStructureId(factStructureId);
	    FactCriteriaTypeInfoDTO criteriaTypeInfo1 = new FactCriteriaTypeInfoDTO();
	    criteriaTypeInfo1.setName(criteriaTypeName);
	    factStructure1.setCriteriaTypeInfo(criteriaTypeInfo1);
	    return factStructure1;
    }
    
    private FactResultDTO createFactResult(String[] values) {
    	//List<String> list = Arrays.asList(values.split(","));
    	FactResultDTO factResult = new FactResultDTO();
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        for(String item : values) {
        	Map<String, String> row = new HashMap<String, String>();
	        row.put("column1", item);
	        resultList.add(row);
        }

        factResult.setResultList(resultList);
        return factResult;
    }

    private FactResultTypeInfoDTO createColumnMetaData(String dataType) {
    	Map<String, FactResultColumnInfoDTO> columnsInfoMap = new HashMap<String, FactResultColumnInfoDTO>();
    	FactResultColumnInfoDTO columnInfo = new FactResultColumnInfoDTO();
    	columnInfo.setKey("column1");
    	columnInfo.setDataType(dataType);
    	columnsInfoMap.put(columnInfo.getKey(), columnInfo);
    	FactResultTypeInfoDTO typeInfo = new FactResultTypeInfoDTO();
    	typeInfo.setResultColumnsMap(columnsInfoMap);
    	return typeInfo;
    }

    private FactContainer execute(RuleSetDTO ruleSet, BusinessRuleInfoDTO bri) throws Exception { 
    	// Proposition 0 - SUBSET 
    	List<RuleElementDTO> ruleElementList = bri.getBusinessRuleElementList();
    	RuleElementDTO ruleElement1 = ruleElementList.get(0);
    	List<FactStructureDTO> factList = ruleElement1.getBusinessRuleProposition().getLeftHandSide().getYieldValueFunction().getFactStructureList();
    	FactStructureDTO factStructure1 = factList.get(0);
    	FactStructureDTO factStructure2 = factList.get(1);

    	// Proposition 1 - AND 
    	// ruleElementList.get(1) is an AND operator and has no proposition
    	
    	// Proposition 2 - INTERSECTION 
    	RuleElementDTO ruleElement2 = ruleElementList.get(2);
    	List<FactStructureDTO> factList2 = ruleElement2.getBusinessRuleProposition().getLeftHandSide().getYieldValueFunction().getFactStructureList();
    	FactStructureDTO factStructure3 = factList2.get(0);
    	FactStructureDTO factStructure4 = factList2.get(1);
    	
    	String criteriaKey1 = FactUtil.createCriteriaKey(factStructure1);
    	String factKey1 = FactUtil.createFactKey(factStructure2);

    	String criteriaKey2 = FactUtil.createCriteriaKey(factStructure3);
    	String factKey2 = FactUtil.createFactKey(factStructure4);

        // EXECUTION: Create facts
    	FactResultTypeInfoDTO columnMetadata = createColumnMetaData(String.class.getName());

        FactResultDTO factResult1 = createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        factResult1.setFactResultTypeInfo(columnMetadata);

        FactResultDTO factResultCriteria1 = createFactResult(new String[] {"CPR101"});
        factResultCriteria1.setFactResultTypeInfo(columnMetadata);

        FactResultDTO factResult2 = createFactResult(new String[] {"CPR102","MATH102","CHEM102"});
        factResult2.setFactResultTypeInfo(columnMetadata);

        FactResultDTO factResultCriteria2 = createFactResult(new String[] {"MATH102"});
        factResultCriteria2.setFactResultTypeInfo(columnMetadata);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(criteriaKey1, factResultCriteria1);
        factMap.put(factKey1, factResult1);
        factMap.put(criteriaKey2, factResultCriteria2);
        factMap.put(factKey2, factResult2);

        //Map<String, YieldValueFunctionDTO> yvfMap = getYvfMap(businessRule);
    	Map<String, RulePropositionDTO> propMap = BusinessRuleUtil.getRulePropositions(bri);
        FactContainer facts =  new FactContainer(""+System.nanoTime(), bri.getAnchorValue(), bri.getAnchorTypeKey(), propMap, factMap);

        // Execute rule
        // Execute Drools rule set source code
        WorkingMemory workingMemory = getRuleBase(ruleSet.getContent()).newStatefulSession();
        workingMemory.insert(new CurrentDateTime());
        workingMemory.insert(facts);
        workingMemory.fireAllRules();

        return facts;
    }

    @Test
    public void testGenerateAndCreateRuleSet_BusinessRuleContainer() throws Exception {
    	BusinessRuleContainerDTO container = getBusinessRuleContainer("1", "MyNewBusinessRule", "CPR101");
    	// Generate and create new rule set
    	RuleSetContainerDTO ruleSetContainer = service.generateRuleSetForBusinessRuleContainer(container);

    	assertNotNull(ruleSetContainer);
    	assertNotNull(ruleSetContainer.getRuleSetList());
    	assertEquals(1, ruleSetContainer.getRuleSetList().size());
    	RuleSetDTO ruleSet = ruleSetContainer.getRuleSetList().get(0);
    	assertNotNull(ruleSet.getUUID());
        service.removeRuleSet(ruleSet.getUUID());
    }

    @Test
    public void testGenerateAndCreateRuleSet() throws Exception {
    	BusinessRuleInfoDTO businessRule = getBusinessRule("1", "MyNewBusinessRule", "CPR101");
    	// Generate and create new rule set
    	RuleSetDTO ruleSet1 = service.generateRuleSetForBusinessRule(businessRule);

    	assertNotNull(ruleSet1);
    	assertNotNull(ruleSet1.getUUID());
        service.removeRuleSet(ruleSet1.getUUID());
    }

    @Test
    public void testGenerateAndCreateRuleSet_CheckStatus() throws Exception {
    	BusinessRuleInfoDTO businessRule = getBusinessRule("1", "MyNewBusinessRule", "CPR101");
        businessRule.setState(BusinessRuleStatus.ACTIVE.toString());
    	// Generate and create new rule set
    	RuleSetDTO ruleSet1 = service.generateRuleSetForBusinessRule(businessRule);

    	assertNotNull(ruleSet1);
    	assertNotNull(ruleSet1.getUUID());
    	// Assert rule set
    	assertEquals(BusinessRuleStatus.ACTIVE.toString(), ruleSet1.getStatus());
    	// Assert rules
    	for(RuleDTO rule : ruleSet1.getRules().values()) {
	    	assertEquals(BusinessRuleStatus.ACTIVE.toString(), rule.getStatus());
    	}
        service.removeRuleSet(ruleSet1.getUUID());
    }

    @Test
    public void testGenerateAndCreateRuleSet_SameIdDifferentName() throws Exception {
    	BusinessRuleInfoDTO businessRule1 = getBusinessRule("1", "MyNewBusinessRule", "CPR101");
    	// Generate and create new rule set
    	RuleSetDTO ruleSet1 = service.generateRuleSetForBusinessRule(businessRule1);

    	assertNotNull(ruleSet1);
    	assertNotNull(ruleSet1.getUUID());

    	BusinessRuleInfoDTO businessRule2 = getBusinessRule("1", "MyOtherNewBusinessRule", "CPR101");
    	// Generate and create new rule set
    	RuleSetDTO ruleSet2 = service.generateRuleSetForBusinessRule(businessRule2);

    	assertNotNull(ruleSet2);
    	assertNotNull(ruleSet2.getUUID());
    	
    	service.removeRuleSet(ruleSet1.getUUID());
    	service.removeRuleSet(ruleSet2.getUUID());
    }

    @Test
    public void testGenerateAndCreateRuleSet_SameNameDifferentId() throws Exception {
    	BusinessRuleInfoDTO businessRule1 = getBusinessRule("1", "MyNewBusinessRule", "CPR101");
    	// Generate and create new rule set
    	RuleSetDTO ruleSet1 = service.generateRuleSetForBusinessRule(businessRule1);

    	assertNotNull(ruleSet1);
    	assertNotNull(ruleSet1.getUUID());

    	BusinessRuleInfoDTO businessRule2 = getBusinessRule("2", "MyNewBusinessRule", "CPR101");
    	// Generate and create new rule set
    	RuleSetDTO ruleSet2 = service.generateRuleSetForBusinessRule(businessRule2);

    	assertNotNull(ruleSet2);
    	assertNotNull(ruleSet2.getUUID());
    	
    	service.removeRuleSet(ruleSet1.getUUID());
    	service.removeRuleSet(ruleSet2.getUUID());
    }

    @Test
    public void testGenerateAndCreateRuleSet_SameNameAndSameId() throws Exception {
    	BusinessRuleInfoDTO businessRule1 = getBusinessRule("1", "MyNewBusinessRule", "CPR101");
    	// Generate and create new rule set
    	RuleSetDTO ruleSet1 = service.generateRuleSetForBusinessRule(businessRule1);

    	assertNotNull(ruleSet1);
    	assertNotNull(ruleSet1.getUUID());

    	BusinessRuleInfoDTO businessRule2 = getBusinessRule("1", "MyNewBusinessRule", "CPR101");
    	// Generate and create new rule set
    	try {
        	RuleSetDTO ruleSet2 = service.generateRuleSetForBusinessRule(businessRule2);
    		fail("Generating rule set with the same business rule id and name should have failed.");
    	} catch(OperationFailedException e) {
    		assertNotNull(e);
    		assertNotNull(e.getMessage());
    	}

    	service.removeRuleSet(ruleSet1.getUUID());
    }

    @Test
    public void testGenerateAndCreateRuleSet_NullRuleElements() throws Exception {
    	BusinessRuleInfoDTO businessRule1 = getBusinessRule("1", "MyNewBusinessRule", "CPR101");
    	businessRule1.setBusinessRuleElementList(null);
    	// Generate and create new rule set
    	RuleSetDTO ruleSet1 = service.generateRuleSetForBusinessRule(businessRule1);

    	assertNotNull(ruleSet1);
    	assertNotNull(ruleSet1.getUUID());
        service.removeRuleSet(ruleSet1.getUUID());
    }

    @Test
    public void testGenerateAndCreateRuleSet_EmptyRuleElements() throws Exception {
    	BusinessRuleInfoDTO businessRule1 = getBusinessRule("1", "MyNewBusinessRule", "CPR101");
    	businessRule1.setBusinessRuleElementList(new ArrayList<RuleElementDTO>());
    	// Generate and create new rule set
    	RuleSetDTO ruleSet1 = service.generateRuleSetForBusinessRule(businessRule1);

    	assertNotNull(ruleSet1);
    	assertNotNull(ruleSet1.getUUID());
        service.removeRuleSet(ruleSet1.getUUID());
    }

    @Test
    public void testGenerateAndUpdateRuleSet() throws Exception {
    	BusinessRuleInfoDTO businessRule1 = getBusinessRule("1", "MyNewBusinessRule", "CPR101");
    	// Generate and create new rule set
    	RuleSetDTO ruleSet1 = service.generateRuleSetForBusinessRule(businessRule1);

    	assertNotNull(ruleSet1);
    	assertNotNull(ruleSet1.getUUID());
    	assertEquals(1L, ruleSet1.getVersionNumber());
    	
    	// Update rule's RHS expected value and set rule set UUID
    	businessRule1.setCompiledId(ruleSet1.getUUID()); 
		RightHandSideDTO rhs = businessRule1.getBusinessRuleElementList().get(2).getBusinessRuleProposition().getRightHandSide();
		rhs.setExpectedValue("2");
    	// Generate (update) rule set again which creates a new version
    	RuleSetDTO ruleSet2 = service.generateRuleSetForBusinessRule(businessRule1);

    	assertNotNull(ruleSet2);
    	assertNotNull(ruleSet2.getUUID());
    	assertEquals(ruleSet1.getUUID(), ruleSet2.getUUID());
    	assertFalse(ruleSet1.getContent().equals(ruleSet2.getContent()));
    	assertEquals(1L, ruleSet2.getVersionNumber());
    	assertEquals(ruleSet1.getVersionNumber(), ruleSet2.getVersionNumber());
		
    	service.removeRuleSet(ruleSet2.getUUID());
    }

    @Test
    public void testGenerateRuleSetAndExecute() throws Exception {
    	BusinessRuleInfoDTO businessRule = getBusinessRule("1", "MyNewBusinessRule", "CPR101");
    	// Generate and create new rule set
    	RuleSetDTO ruleSet = service.generateRuleSetForBusinessRule(businessRule);

		assertNotNull(ruleSet);
		assertNotNull(ruleSet.getUUID());
		assertNotNull(ruleSet.getRules());
		
		FactContainer fact = execute(ruleSet, businessRule); 
		
        assertTrue(fact.getPropositionContainer().getRuleResult());
		service.removeRuleSet(ruleSet.getUUID());
    }
    
    @Test
    public void testGenerateAndUpdateRuleSetAndExecute() throws Exception {
    	BusinessRuleInfoDTO businessRule1 = getBusinessRule("1", "MyNewBusinessRule", "CPR101");
    	// Generate and create new rule set
    	RuleSetDTO ruleSet1 = service.generateRuleSetForBusinessRule(businessRule1);

    	assertNotNull(ruleSet1);
    	assertNotNull(ruleSet1.getUUID());
    	assertEquals(1L, ruleSet1.getVersionNumber());
    	
    	// Update rule's RHS expected value and set rule set UUID
    	businessRule1.setCompiledId(ruleSet1.getUUID()); 
		RightHandSideDTO rhs = businessRule1.getBusinessRuleElementList().get(2).getBusinessRuleProposition().getRightHandSide();
		rhs.setExpectedValue("2"); // which results in false since there's only 1 intersection 
    	// Generate (update) rule set again which creates a new version
    	RuleSetDTO ruleSet2 = service.generateRuleSetForBusinessRule(businessRule1);

    	assertNotNull(ruleSet2);
    	assertNotNull(ruleSet2.getUUID());
    	assertEquals(ruleSet1.getUUID(), ruleSet2.getUUID());
    	assertFalse(ruleSet1.getContent().equals(ruleSet2.getContent()));
    	assertEquals(1L, ruleSet2.getVersionNumber());
    	assertEquals(ruleSet1.getVersionNumber(), ruleSet2.getVersionNumber());
		
		FactContainer fact = execute(ruleSet2, businessRule1); 
        assertFalse(fact.getPropositionContainer().getRuleResult());
        service.removeRuleSet(ruleSet2.getUUID());
    }

}
