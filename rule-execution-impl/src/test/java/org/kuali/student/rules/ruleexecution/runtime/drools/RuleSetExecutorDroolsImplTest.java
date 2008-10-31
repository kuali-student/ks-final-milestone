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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.rules.factfinder.dto.FactCriteriaTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactResultTypeInfoDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.internal.common.utils.FactUtil;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.rules.ruleexecution.runtime.RuleSetExecutor;
import org.kuali.student.rules.ruleexecution.runtime.drools.util.DroolsTestUtil;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.ruleexecution.util.RuleManagementDtoFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rule-execution-context.xml"})
public class RuleSetExecutorDroolsImplTest {
	/** Rule set executor interface */
	@Resource
	private RuleSetExecutor executor;

    private final RuleManagementDtoFactory dtoFactory = RuleManagementDtoFactory.getInstance();

    private FactStructureDTO createFactStructure(String factStructureId, String criteriaTypeName) {
    	FactStructureDTO factStructure1 = new FactStructureDTO();
	    factStructure1.setFactStructureId(factStructureId);
	    FactCriteriaTypeInfoDTO criteriaTypeInfo1 = new FactCriteriaTypeInfoDTO();
	    criteriaTypeInfo1.setName(criteriaTypeName);
	    factStructure1.setCriteriaTypeInfo(criteriaTypeInfo1);
	    return factStructure1;
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
        // Rule set source code
    	RuleSetDTO ruleSet = DroolsTestUtil.getAverageIntersectionPropositionRuleSet();

    	byte[] bytes = DroolsTestUtil.createPackage(ruleSet);
        ruleSet.setCompiledRuleSet(bytes);

    	YieldValueFunctionDTO yvfAverage = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.SUBSET.toString());
    	YieldValueFunctionDTO yvfIntersection = dtoFactory.createYieldValueFunctionDTO(null, YieldValueFunctionType.INTERSECTION.toString());
		
		FactStructureDTO factStructure1 = createFactStructure("subset.id.1", "course.subset.criteria");
		yvfAverage.setFactStructureList(Arrays.asList(factStructure1));
		
		FactStructureDTO factStructure3 = createFactStructure("subset.id.2", "course.subset.criteria");
		FactStructureDTO factStructure4 = createFactStructure("subset.id.3", "course.subset.fact");
		yvfIntersection.setFactStructureList(Arrays.asList(factStructure3, factStructure4));

    	//String criteriaKey1 = FactUtil.getCriteriaKey(factStructure1);
    	String factKeyAverage = FactUtil.createFactKey(factStructure1);

    	String criteriaKeyIntersection = FactUtil.createCriteriaKey(factStructure3);
    	String factKeyIntersection = FactUtil.createFactKey(factStructure4);
		
		RulePropositionDTO propositionAverage = createRuleProposition(
    			YieldValueFunctionType.AVERAGE.toString(), 
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			Integer.class.getName(),
    			yvfAverage);
		RulePropositionDTO propositionIntersection = createRuleProposition(
    			YieldValueFunctionType.INTERSECTION.toString(), 
    			ComparisonOperator.EQUAL_TO.toString(),
    			"1",
    			Integer.class.getName(),
    			yvfIntersection);
		Map<String, RulePropositionDTO> propositionMap = new HashMap<String, RulePropositionDTO>();
		propositionMap.put("P1", propositionAverage);
		propositionMap.put("P2", propositionIntersection);
        
        // EXECUTION: Create facts
    	FactResultTypeInfoDTO columnMetaData1 = DroolsTestUtil.createColumnMetaData(BigDecimal.class.getName());
    	FactResultDTO factResultAverage = DroolsTestUtil.createFactResult(new String[] {"85.0","75.0","80.0"});
    	factResultAverage.setFactResultTypeInfo(columnMetaData1);
    	
    	FactResultTypeInfoDTO columnMetaData2 = DroolsTestUtil.createColumnMetaData(String.class.getName());
        FactResultDTO factResultIntersection = DroolsTestUtil.createFactResult(new String[] {"CPR101","MATH101","CHEM101"});
        factResultIntersection.setFactResultTypeInfo(columnMetaData2);

    	FactResultDTO factResultCriteriaIntersection = DroolsTestUtil.createFactResult(new String[] {"CPR101"});
    	factResultCriteriaIntersection.setFactResultTypeInfo(columnMetaData2);

        Map<String, Object> factMap = new HashMap<String, Object>();
        factMap.put(factKeyAverage, factResultAverage);
        factMap.put(criteriaKeyIntersection, factResultCriteriaIntersection);
        factMap.put(factKeyIntersection, factResultIntersection);

        // Execute ruleset and fact
        ExecutionResult result = executor.execute(ruleSet, anchor, propositionMap, factMap);
        assertNotNull(result);
        assertNotNull(result.getResults());
		assertNotNull(result.getExecutionLog());
		assertTrue(result.getReport().isSuccess());
	}

}
