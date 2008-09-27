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
package org.kuali.student.rules.ruleexecution.drools;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.ruleexecution.RuleSetExecutor;
import org.kuali.student.rules.ruleexecution.drools.util.DroolsTestUtil;
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
    public void testExecute() throws Exception {
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
        List<Object> list = (List<Object>) executor.execute(agenda, facts);
        
        assertNotNull( list );

        String time = null;
        //while( it != null && it.hasNext() ) {
            //Object obj = it.next();
        for(Object obj : list) {
            if ( obj instanceof String ) {
                time = (String) obj;
                break;
            }
        }
        
        assertNotNull( time );
        assertTrue( time.startsWith( "Minute is even:" ) || time.startsWith( "Minute is odd:" ) );
    }

	@Test
    public void testExecuteWithDroolsCompiledRuleSet() throws Exception {
        RuleSetDTO ruleSet = DroolsTestUtil.createRuleSet();
        byte[] bytes = DroolsTestUtil.createPackage(ruleSet);
        ruleSet.setCompiledRuleSet(bytes);

        // Add facts
        List<Object> facts = new ArrayList<Object>();
        facts.add( Calendar.getInstance() );

        // Execute ruleset and fact
        List<Object> list = (List<Object>) executor.execute(ruleSet, facts);
        
        assertNotNull( list );

        // Iterate through returned rule engine objects
        String time = null;
        for(Object obj : list) {
            if ( obj instanceof String ) {
                time = (String) obj;
                break;
            }
        }
        
        assertNotNull( time );
        assertTrue( time.startsWith( "Minute is even:" ) || time.startsWith( "Minute is odd:" ) );
    }

	@Test
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
        List<Object> list = (List<Object>) executor.execute( agenda, factList );
        assertNotNull(list);
        assertTrue(factContainer1.getPropositionContainer().getRuleResult());
    }

}
