package org.kuali.student.rules.ruleexecution.service;

import org.junit.Assert;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.ruleexecution.dto.ExecutionResultDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RuntimeAgendaDTO;
import org.kuali.student.rules.rulemanagement.entity.BusinessRule;
import org.kuali.student.rules.rulemanagement.entity.BusinessRuleAdapter;
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
	
	private static String businessRuleId1;
	private static String businessRuleId2;

    @BeforeClass
    public static void setUpOnce() throws Exception {
		ApplicationContext testBeanContext = new ClassPathXmlApplicationContext("/test-beans.xml");
		
		BusinessRule businessRule1 = (BusinessRule) testBeanContext.getBean("funcBusRule1");
		BusinessRule businessRule2 = (BusinessRule) testBeanContext.getBean("funcBusRule2");

		BusinessRuleInfoDTO businessRuleInfo1 = BusinessRuleAdapter.getBusinessRuleInfoDTO(businessRule1);
		BusinessRuleInfoDTO businessRuleInfo2 = BusinessRuleAdapter.getBusinessRuleInfoDTO(businessRule2);
		
		// Generate the Drools rule source code (DRL) and save it to the rule repository
		ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();

		RuleManagementService ruleManagementService = (RuleManagementService) applicationContext.getBean("ruleManagement");
		businessRuleId1 = ruleManagementService.createBusinessRule(businessRuleInfo1);
		businessRuleId2 = ruleManagementService.createBusinessRule(businessRuleInfo2);

		//RuleRepositoryService ruleRepositoryService = (RuleRepositoryService) applicationContext.getBean("repository");
		//System.out.println("\n\n********** "+ruleRepositoryService.fetchRuleSet(ruleManagementService.fetchBusinessRuleInfo(businessRuleInfo1.getBusinessRuleId()).getCompiledId()).getContent());
		//System.out.println("\n\n********** "+ruleRepositoryService.fetchRuleSet(ruleManagementService.fetchBusinessRuleInfo(businessRuleInfo2.getBusinessRuleId()).getCompiledId()).getContent());
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
    public void testExecuteAgenda() throws Exception {
    	RuntimeAgendaDTO agenda = null;
    	List<?> facts = null;
    }

    @Test
    public void testExecuteBusinessRule_StaticFact_TestBeans_Rule1() throws Exception {
    	ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId1);
        Assert.assertNotNull(result);

        Assert.assertTrue(result.getExecutionResult());
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

    @Test
    public void testExecuteBusinessRule_StaticFact_TestBeans_Rule2() throws Exception {
        ExecutionResultDTO result = ruleExecutionService.executeBusinessRule(businessRuleId2);
        Assert.assertNotNull(result);

        Assert.assertTrue(result.getExecutionResult());
        Assert.assertNotNull(result.getExecutionLog());
        Assert.assertNotNull(result.getReport());
        Assert.assertFalse(result.getReport().isSuccessful());
        Assert.assertNull(result.getReport().getSuccessMessage());
        Assert.assertNotNull(result.getReport().getFailureMessage());

        System.out.println("Execution log:\n" + result.getExecutionLog());
        System.out.println("Error message:\n" + result.getErrorMessage());
        System.out.println("Success message: " + result.getReport().getSuccessMessage());
        System.out.println("Failure message: " + result.getReport().getFailureMessage());
    }
}
