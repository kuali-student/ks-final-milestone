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
package org.kuali.student.rules.ruleexecution.service;

import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.entity.BusinessRule;
import org.kuali.student.rules.rulemanagement.entity.BusinessRuleAdapter;
import org.kuali.student.rules.rulemanagement.entity.FactStructure;
import org.kuali.student.rules.rulemanagement.service.RuleManagementService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

@Daos({@Dao(value = "org.kuali.student.rules.rulemanagement.dao.impl.RuleManagementDAOImpl", testDataFile = "classpath:test-beans.xml")})
@PersistenceFileLocation("classpath:META-INF/rulemanagement-persistence.xml")
public class RuleExecutionServiceTest extends AbstractServiceTest {

	// Automatically loads rule-repository-mock-service-context.xml 
	// (*-mock-service-context.xml) and auto-wires by type
	@Client(value="org.kuali.student.rules.ruleexecution.service.impl.RuleExecutionServiceImpl", port="8181")
    public RuleExecutionService ruleExecutionService;

	private static RuleManagementService ruleManagementService;
	
	// data-beans.xml - bean id="funcBusRule1"
	private static String businessRuleId1 = "11223344-1122-1122-1112-100000000001";
	// data-beans.xml - bean id="funcBusRule2"
	private static String businessRuleId2 = "11223344-1122-1122-1112-100000000011";

	private static BusinessRuleInfoDTO businessRuleInfo1;
	private static BusinessRuleInfoDTO businessRuleInfo2;

	private static FactStructureDTO factStructure1;
	//private static FactStructureDTO factStructure2;
	
    @BeforeClass
    public static void setUpOnce() throws Exception {
		ApplicationContext testBeanContext = new ClassPathXmlApplicationContext("/test-beans.xml");
		
		BusinessRule businessRule1 = (BusinessRule) testBeanContext.getBean("funcBusRule1");
		BusinessRule businessRule2 = (BusinessRule) testBeanContext.getBean("funcBusRule2");

		businessRuleInfo1 = BusinessRuleAdapter.getBusinessRuleInfoDTO(businessRule1);
		businessRuleInfo2 = BusinessRuleAdapter.getBusinessRuleInfoDTO(businessRule2);
		
		FactStructure fs1 = (FactStructure) testBeanContext.getBean("factStructure1");
		
		factStructure1 = BusinessRuleAdapter.getFactStructureDTO(fs1);
		
		// Generate the Drools rule source code (DRL) and save it to the rule repository
		ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();

		ruleManagementService = (RuleManagementService) applicationContext.getBean("ruleManagement");
		ruleManagementService.updateBusinessRule(businessRuleId1, businessRuleInfo1);
		ruleManagementService.updateBusinessRule(businessRuleId2, businessRuleInfo2);
		
		ruleManagementService.updateBusinessRuleState(businessRuleId1, BusinessRuleStatus.ACTIVE.toString());
		ruleManagementService.updateBusinessRuleState(businessRuleId2, BusinessRuleStatus.ACTIVE.toString());
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
    
    @Test
    public void testExecuteBusinessRule_StaticFact_TestBeans_Rule1() throws Exception {
    	ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId1, null);
        Assert.assertNotNull(result);

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());

        Assert.assertTrue(result.isExecutionSuccessful());
        Assert.assertNotNull(result.getExecutionLog());
        Assert.assertNotNull(result.getReport());

        System.out.println("Successful: " + result.getReport().isSuccessful());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());

        Assert.assertTrue(result.getReport().isSuccessful());
        Assert.assertNotNull(result.getReport().getSuccessMessage());
        Assert.assertNull(result.getReport().getFailureMessage());
    }

    @Test
    public void testExecuteBusinessRuleTest_StaticFact_TestBeans_Rule2() throws Exception {
    	ExecutionResultDTO result = ruleExecutionService.executeBusinessRuleTest(businessRuleInfo2, null);
        Assert.assertNotNull(result);

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());

        Assert.assertTrue(result.isExecutionSuccessful());
        Assert.assertNotNull(result.getExecutionLog());
        Assert.assertNotNull(result.getReport());

        System.out.println("Successful: " + result.getReport().isSuccessful());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());

        Assert.assertFalse(result.getReport().isSuccessful());
        Assert.assertNull(result.getReport().getSuccessMessage());
        Assert.assertNotNull(result.getReport().getFailureMessage());
    }

    /**
     * Cannot execute dynamic facts as an automated test since the 
     * facts service cannot be loaded through this test harness.
     */
    @Ignore
    @Test
    public void testExecuteBusinessRule_DynamicFact_TestBeans_Rule1() throws Exception {
        String factTypeKey = "fact.completed_course_list";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("factParam.studentId", "student1");
        paramMap.put("factParam.clusetId", "CPR 101, MATH 101, MATH 102");

        factStructure1.setStaticFact(false);
        factStructure1.setFactTypeKey(factTypeKey);
        factStructure1.setParamValueMap(paramMap);

        FactStructureDTO fs = businessRuleInfo1.getBusinessRuleElementList().get(0).getBusinessRuleProposition().getLeftHandSide().getYieldValueFunction().getFactStructureList().get(0);
        fs.setStaticFact(false);
        fs.setFactTypeKey(factTypeKey);
        fs.setParamValueMap(paramMap);
        
        ruleManagementService.updateBusinessRule(businessRuleId1, businessRuleInfo1);
        
    	ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId1, paramMap);
        Assert.assertNotNull(result);

        Assert.assertTrue(result.isExecutionSuccessful());
        Assert.assertNotNull(result.getExecutionLog());
        Assert.assertNotNull(result.getReport());
        Assert.assertTrue(result.getReport().isSuccessful());
        Assert.assertNotNull(result.getReport().getSuccessMessage());
        Assert.assertNull(result.getReport().getFailureMessage());

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());
    }
}
