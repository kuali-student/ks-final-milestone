package org.kuali.student.rules.ruleexecution.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.ruleexecution.drools.util.DroolsTestUtil;
import org.kuali.student.rules.ruleexecution.dto.FactDTO;
import org.kuali.student.rules.ruleexecution.dto.ResultDTO;
import org.kuali.student.rules.ruleexecution.dto.ValueDTO;
import org.kuali.student.rules.rulemanagement.dto.RuntimeAgendaDTO;

public class RuleExecutionServiceTest extends AbstractServiceTest {

	// Automatically loads rule-repository-mock-service-context.xml (*-mock-service-context.xml)
	// and auto-wires by type
	@Client(value="org.kuali.student.rules.ruleexecution.service.impl.RuleExecutionServiceImpl", port="8181")
    public RuleExecutionService service; 

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
    
    @Test
    public void testExecuteAgenda() throws Exception {
    	RuntimeAgendaDTO agenda = null;
    	List<?> facts = null;
    }

    //@Ignore
    @Test
    public void testExecuteRuleSet() throws Exception {
        RuleSetDTO ruleSet = DroolsTestUtil.createRuleSet();
        byte[] bytes = DroolsTestUtil.createPackage(ruleSet);
        ruleSet.setCompiledRuleSet(bytes);

        // Add facts
        FactDTO fact = new FactDTO("1");
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        fact.addFact("1", "java.util.Calendar", String.valueOf(timeInMillis));

        ResultDTO result = this.service.executeRuleSet(ruleSet, fact);
        
        assertNotNull( result );

        // Iterate through returned rule engine objects
        String time = null;
        for(ValueDTO obj : result.getResults()) {
            if ( obj.getDataType().equals(String.class.getName())) {
                time = obj.getValue();
                break;
            }
        }
        
        assertNotNull( time );
        assertTrue( time.startsWith( "Minute is even:" ) || time.startsWith( "Minute is odd:" ) );
        System.out.println("Rule Engine Execution Objects: " + result.getResults());
    }

}
