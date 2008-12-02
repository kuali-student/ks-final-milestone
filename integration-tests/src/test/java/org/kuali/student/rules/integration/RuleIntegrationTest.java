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
package org.kuali.student.rules.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.rules.factfinder.service.FactFinderService;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.repository.service.RuleRepositoryService;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.ruleexecution.service.RuleExecutionService;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.entity.BusinessRule;
import org.kuali.student.rules.rulemanagement.entity.BusinessRuleAdapter;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RuleIntegrationTest {

	private static RuleRepositoryService ruleRepositoryService;
	private static RuleManagementService ruleManagementService;
	private static RuleExecutionService ruleExecutionService;
	private static FactFinderService factFinderService;

	private static ApplicationContext testBeanContext;
	
	private BusinessRuleInfoDTO businessRuleInfo1;
	private BusinessRuleInfoDTO businessRuleInfo2;
	
	@BeforeClass
    public static void setUpOnce() throws Exception {
		System.setProperty("ks.test.persistenceLocation", 
				"classpath:META-INF/rulemanagement-persistence.xml");
				//",classpath:META-INF/factfinder-persistence.xml");
		System.setProperty("ks.test.daoImplClasses", 
				"org.kuali.student.rules.rulemanagement.dao.impl.RuleManagementDAOImpl|classpath:test-beans.xml");
				//",org.kuali.student.rules.factfinder.dao.impl.FactFinderDAOImpl|classpath:fact-data-beans.xml");
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/rulemanagement-mock-service-context.xml");
		
		ruleRepositoryService = (RuleRepositoryService) applicationContext.getBean("repository");
		ruleManagementService = (RuleManagementService) applicationContext.getBean("ruleManagement");
		ruleExecutionService = (RuleExecutionService) applicationContext.getBean("ruleExecution");
		//factFinderService = (FactFinderService) applicationContext.getBean("factFinder");

		testBeanContext = new ClassPathXmlApplicationContext("/test-beans.xml");
	}

    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
		BusinessRule businessRule1 = (BusinessRule) testBeanContext.getBean("funcBusRule1");
		BusinessRule businessRule2 = (BusinessRule) testBeanContext.getBean("funcBusRule2");

		this.businessRuleInfo1 = BusinessRuleAdapter.getBusinessRuleInfoDTO(businessRule1);
		this.businessRuleInfo2 = BusinessRuleAdapter.getBusinessRuleInfoDTO(businessRule2);
		
		// Generate the Drools rule source code (DRL) and save it to the rule repository
		//ruleManagementService.updateBusinessRule(businessRuleInfo1.getBusinessRuleId(), businessRuleInfo1);
		//ruleManagementService.updateBusinessRule(businessRuleInfo2.getBusinessRuleId(), businessRuleInfo2);
    }

    @After
    public void tearDown() throws Exception {
    	this.businessRuleInfo1 = null;
    	this.businessRuleInfo2 = null;
    }

    @Test
    public void testExecuteBusinessRuleWithStaticFact_Rule1() throws Exception {
    	//BusinessRuleInfoDTO brInfoDTO = ruleManagementService.fetchDetailedBusinessRuleInfo("1");
    	BusinessRuleInfoDTO brInfoDTO = this.businessRuleInfo1;
    	// RuleId "1" already exists in the rule management database 
    	// from pre-loading test-beans.xml so we set the ruleId to "1000"
    	//brInfoDTO.setBusinessRuleId("1000");
        brInfoDTO.setName("CHEM100PRE_REQ");
        brInfoDTO.setAnchorValue("CHEM100");
    	
        String businessRuleId = ruleManagementService.createBusinessRule(brInfoDTO);
        assertEquals(brInfoDTO.getBusinessRuleId(), businessRuleId);

        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId, null);
        assertNotNull(result);

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());

        assertTrue(result.getExecutionResult());
        assertNotNull(result.getExecutionLog());
        assertNotNull(result.getReport());
        assertTrue(result.getReport().isSuccessful());
        assertNotNull(result.getReport().getSuccessMessage());
        assertNull(result.getReport().getFailureMessage());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());
        
        ruleManagementService.deleteBusinessRule(businessRuleId);
    }

    @Test
    public void testExecuteBusinessRuleWithStaticFact_Rule1_BadEffectiveDates() throws Exception {
    	//BusinessRuleInfoDTO brInfoDTO = ruleManagementService.fetchDetailedBusinessRuleInfo("1");
    	BusinessRuleInfoDTO brInfoDTO = this.businessRuleInfo1;
    	// RuleId "1" already exists in the rule management database 
    	// from pre-loading test-beans.xml so we set the ruleId to "2000"
    	//brInfoDTO.setBusinessRuleId("2000");
        brInfoDTO.setName("CHEM200PRE_REQ");
        brInfoDTO.setAnchorValue("CHEM200");

    	brInfoDTO.setEffectiveStartTime(new Date());
        brInfoDTO.setEffectiveEndTime(new Date());

        String businessRuleId = ruleManagementService.createBusinessRule(brInfoDTO);
        assertEquals(brInfoDTO.getBusinessRuleId(), businessRuleId);
		
        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId, null);
        assertNotNull(result);

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());

        assertTrue(result.getExecutionResult());
        assertNotNull(result.getExecutionLog());
        assertNotNull(result.getReport());
        assertFalse(result.getReport().isSuccessful());
        assertNotNull(result.getErrorMessage());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());

        ruleManagementService.deleteBusinessRule(businessRuleId);
    }

    @Test
    public void testExecuteBusinessRuleSnapshotWithStaticFact_Rule1() throws Exception {
    	//BusinessRuleInfoDTO brInfoDTO = ruleManagementService.fetchDetailedBusinessRuleInfo("1");
    	BusinessRuleInfoDTO brInfoDTO = this.businessRuleInfo1;
    	// RuleId "1" already exists in the rule management database 
    	// from pre-loading test-beans.xml so we set the ruleId to "3000"
    	//brInfoDTO.setBusinessRuleId("3000");
        brInfoDTO.setName("CHEM300PRE_REQ");
        brInfoDTO.setAnchorValue("CHEM300");

        String businessRuleId = ruleManagementService.createBusinessRule(brInfoDTO);
        assertEquals(brInfoDTO.getBusinessRuleId(), businessRuleId);
		
        //Create snapshot - Active business rule will create a snapshot
        brInfoDTO.setStatus(BusinessRuleStatus.ACTIVE.toString());
        ruleManagementService.updateBusinessRule(businessRuleId, brInfoDTO);
        
        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId, null);
        assertNotNull(result);

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());

        assertTrue(result.getExecutionResult());
        assertNotNull(result.getExecutionLog());
        assertNotNull(result.getReport());
        assertTrue(result.getReport().isSuccessful());
        assertNotNull(result.getReport().getSuccessMessage());
        assertNull(result.getReport().getFailureMessage());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());

        ruleManagementService.deleteBusinessRule(businessRuleId);
    }

    @Test
    public void testExecuteBusinessRuleWithStaticFact_Rule2() throws Exception {
    	//BusinessRuleInfoDTO brInfoDTO = ruleManagementService.fetchDetailedBusinessRuleInfo("1");
    	BusinessRuleInfoDTO brInfoDTO = this.businessRuleInfo2;
    	// RuleId "1" already exists in the rule management database 
    	// from pre-loading test-beans.xml so we set the ruleId to "4000"
    	//brInfoDTO.setBusinessRuleId("4000");
        brInfoDTO.setName("CHEM400PRE_REQ");
        brInfoDTO.setAnchorValue("CHEM400");
    	
        String businessRuleId = ruleManagementService.createBusinessRule(brInfoDTO);
        assertEquals(brInfoDTO.getBusinessRuleId(), businessRuleId);

        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId, null);
        assertNotNull(result);

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());

        assertTrue(result.getExecutionResult());
        assertNotNull(result.getExecutionLog());
        assertNotNull(result.getReport());
        assertFalse(result.getReport().isSuccessful());
        assertNotNull(result.getReport().getFailureMessage());
        assertNull(result.getReport().getSuccessMessage());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());

        ruleManagementService.deleteBusinessRule(businessRuleId);
    }

    @Test
    public void testExecuteBusinessRuleSnapshotWithStaticFact_Rule2() throws Exception {
    	//BusinessRuleInfoDTO brInfoDTO = ruleManagementService.fetchDetailedBusinessRuleInfo("1");
    	BusinessRuleInfoDTO brInfoDTO = this.businessRuleInfo2;
    	// RuleId "1" already exists in the rule management database 
    	// from pre-loading test-beans.xml so we set the ruleId to "5000"
    	//brInfoDTO.setBusinessRuleId("5000");
        brInfoDTO.setName("CHEM500PRE_REQ");
        brInfoDTO.setAnchorValue("CHEM500");

        String businessRuleId = ruleManagementService.createBusinessRule(brInfoDTO);
        assertEquals(brInfoDTO.getBusinessRuleId(), businessRuleId);
		
        //Create snapshot - Active business rule will create a snapshot
        brInfoDTO.setStatus(BusinessRuleStatus.ACTIVE.toString());
        ruleManagementService.updateBusinessRule(businessRuleId, brInfoDTO);
        
        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId, null);
        assertNotNull(result);

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());

        assertTrue(result.getExecutionResult());
        assertNotNull(result.getExecutionLog());
        assertNotNull(result.getReport());
        assertFalse(result.getReport().isSuccessful());
        assertNotNull(result.getReport().getFailureMessage());
        assertNull(result.getReport().getSuccessMessage());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());

        ruleManagementService.deleteBusinessRule(businessRuleId);
    }

    /*@Test
    public void testExecuteBusinessRule_DynamicFact_Rule1() throws Exception {
    	BusinessRuleInfoDTO brInfoDTO = this.businessRuleInfo2;
    	// RuleId "1" already exists in the rule management database 
    	// from pre-loading test-beans.xml so we set the ruleId to "6000"
    	//brInfoDTO.setBusinessRuleId("6000");
        brInfoDTO.setName("CHEM600PRE_REQ");
        brInfoDTO.setAnchorValue("CHEM600");

        String businessRuleId = ruleManagementService.createBusinessRule(brInfoDTO);
        assertEquals(brInfoDTO.getBusinessRuleId(), businessRuleId);
		
        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId);
        assertNotNull(result);

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());

        assertTrue(result.getExecutionResult());
        assertNotNull(result.getExecutionLog());
        assertNotNull(result.getReport());
        assertFalse(result.getReport().isSuccessful());
        assertNotNull(result.getReport().getFailureMessage());
        assertNull(result.getReport().getSuccessMessage());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());

        ruleManagementService.deleteBusinessRule(businessRuleId);
    }*/

}
