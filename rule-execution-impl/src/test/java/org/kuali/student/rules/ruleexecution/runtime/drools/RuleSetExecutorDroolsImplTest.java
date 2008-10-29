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
package org.kuali.student.rules.ruleexecution.runtime.drools;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.InputStreamReader;
import java.io.Reader;
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

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.ruleexecution.dto.FactDTO;
import org.kuali.student.rules.ruleexecution.dto.ResultDTO;
import org.kuali.student.rules.ruleexecution.dto.ValueDTO;
import org.kuali.student.rules.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.RuleSetExecutor;
import org.kuali.student.rules.ruleexecution.runtime.drools.util.DroolsTestUtil;
import org.kuali.student.rules.ruleexecution.util.RuleEngineRepositoryMock;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RuntimeAgendaDTO;
import org.kuali.student.rules.util.FactContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rule-execution-context.xml"})
public class RuleSetExecutorDroolsImplTest {
	/** Rule set executor interface */
	@Resource
	private RuleSetExecutor executor;


    private Set<String> createSet(String list) {
        Set<String> set = new HashSet<String>();
        for( String s : list.split(",") ) {
        	set.add(s.trim());
        }
        return set;
    }
    
	@Test
    public void testExecuteSimpleRuleSet_Drools() throws Exception {
        RuleSetDTO ruleSet = DroolsTestUtil.createRuleSet();
        byte[] bytes = DroolsTestUtil.createPackage(ruleSet);
        ruleSet.setCompiledRuleSet(bytes);

        // Add facts
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2008);
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.MINUTE, 0);
        List<Object> facts = new ArrayList<Object>();
        facts.add(cal);

        // Execute ruleset and fact
        ExecutionResult result = executor.execute(ruleSet, facts);

        assertNotNull(result);
        assertNotNull(result.getResults());

        // Iterate through returned rule engine objects
        String time = null;
        for(Object obj : result.getResults()) {
            if ( obj instanceof String ) {
                time = (String) obj;
                break;
            }
        }
        
        assertNotNull( time );
        assertTrue( time.startsWith("Minute is even:"));
    }

	/*@Test
    public void testExecuteAgenda() throws Exception {
		RuntimeAgendaDTO agenda = new RuntimeAgendaDTO();
        List<BusinessRuleInfoDTO> briList = new ArrayList<BusinessRuleInfoDTO>();
        BusinessRuleInfoDTO br = new BusinessRuleInfoDTO();
        br.setCompiledId("uuid-123");
        briList.add(br);
        agenda.setBusinessRules(briList);

        // Add facts
        List<Object> facts = new ArrayList<Object>();
        facts.add( Calendar.getInstance() );
        // Iterator through any returned rule engine objects
        ExecutionResult result = executor.execute(agenda, facts);
        
        assertNotNull(result);
        assertNotNull(result.getResults());

        String time = null;
        for(Object obj : result.getResults()) {
            if ( obj instanceof String ) {
                time = (String) obj;
                break;
            }
        }
        
        assertNotNull(time);
        assertTrue(time.startsWith( "Minute is even:" ) || time.startsWith( "Minute is odd:" ));
    }*/

	@Test
    public void testExecuteAverageIntersectionProposition_DroolsRuleSet() throws Exception {
    	String anchor = "CPR101";
    	String factKey1 = "FactKey1";
    	String criteriaKey2 = "CriteriaKey2";
    	String factKey2 = "FactKey2";
        // DEFINITION and EXECUTION: Create rule definition and map execution keys
    	//Set<String> criteria = new HashSet<String>(Arrays.asList("CPR101"));
        // Rule set source code
    	RuleSetDTO ruleSet = DroolsTestUtil.getAverageIntersectionPropositionRuleSet();

    	byte[] bytes = DroolsTestUtil.createPackage(ruleSet);
        ruleSet.setCompiledRuleSet(bytes);

        // EXECUTION: Create facts
        FactResultDTO factResult1 = DroolsTestUtil.createFactResult(new BigDecimal[] {new BigDecimal(85.0),new BigDecimal(75.0),new BigDecimal (80.0)});
        FactResultDTO factResult2 = DroolsTestUtil.createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        FactResultDTO factResultCriteria1 = DroolsTestUtil.createFactResult(new String[] {"CPR101"});

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKey1, factResult1);
        factMap.put(criteriaKey2, factResultCriteria1);
        factMap.put(factKey2, factResult2);

        // Execute ruleset and fact
        ExecutionResult result = executor.execute(ruleSet, anchor, factMap);
        assertNotNull(result);
        assertNotNull(result.getResults());
		assertNotNull(result.getExecutionLog());
		assertTrue(result.getReport().isSuccess());
	}

	/*@Test
    public void testExecuteWithDroolsDRL() throws Exception {
		RuntimeAgendaDTO agenda = new RuntimeAgendaDTO();
        List<BusinessRuleInfoDTO> briList = new ArrayList<BusinessRuleInfoDTO>();
        BusinessRuleInfoDTO br = new BusinessRuleInfoDTO();
        br.setCompiledId("uuid-123");
        br.setAnchorTypeKey("Math101");
        briList.add(br);
        agenda.setBusinessRules(briList);
    	
        // Get facts
        String anchorId = "Math101";
        Set<String> courseSet = createSet("CPR101,MATH102,CHEM101,CHEM102");
        Map<String,Set<String>> factMap = new HashMap<String,Set<String>>(1);
        factMap.put("courseKey", courseSet);
        FactContainer factContainer1 =  new FactContainer(anchorId, factMap);

        List<FactContainer> factList = Arrays.asList(factContainer1);
        
        // Create the rule set executor
        Reader source1 = new InputStreamReader( RuleSetExecutorDroolsImplTest.class.getResourceAsStream( "/drools/drls/org/kuali/student/rules/ruleexecution/drools/Math101PreReqRules.drl" ) );
        ((RuleEngineRepositoryMock) executor.getRuleEngineRepository()).setSource(source1);

        // Iterator through any returned rule engine objects
        ExecutionResult result = executor.execute( agenda, factList );
        assertNotNull(result);
        assertNotNull(result.getResults());
        assertTrue(factContainer1.getPropositionContainer().getRuleResult());
    }*/

}
