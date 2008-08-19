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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.rules.internal.common.agenda.entity.Agenda;
import org.kuali.student.rules.internal.common.agenda.entity.AgendaType;
import org.kuali.student.rules.internal.common.agenda.entity.Anchor;
import org.kuali.student.rules.internal.common.agenda.entity.AnchorType;
import org.kuali.student.rules.internal.common.agenda.entity.BusinessRuleSet;
import org.kuali.student.rules.internal.common.agenda.entity.BusinessRuleSetType;
import org.kuali.student.rules.internal.common.facts.CourseEnrollmentRequest;
import org.kuali.student.rules.ruleexecution.util.RuleEngineRepositoryMock;
import org.kuali.student.rules.rulesetexecution.RuleSetExecutor;
import org.kuali.student.rules.util.FactContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rule-execution-context.xml"})
public class RuleSetExecutorDroolsImplTest {
	/** Rule set executor interface */
	@Resource
	private RuleSetExecutor executor;
	
	@Test
    public void testExecute() throws Exception {
        // Create the agenda
        AgendaType agendaType = new AgendaType( "AgendaType.name", "AgendaType.type" );
        Agenda agenda = new Agenda( "agenda1", agendaType );
        BusinessRuleSetType ruleType = new BusinessRuleSetType( "name", "type" );
        agenda.addBusinessRule( new BusinessRuleSet( "ruleId=uuid-123", "", ruleType, "" ) );
        
        // Add facts
        List<Object> facts = new ArrayList<Object>();
        facts.add( Calendar.getInstance() );
        // Iterator through any returned rule engine objects
        Iterator<?> it = (Iterator<?>) executor.execute( agenda, facts );
        
        assertNotNull( it );

        String time = null;
        while( it != null && it.hasNext() ) {
            Object obj = it.next();
            if ( obj instanceof String ) {
                time = (String) obj;
                break;
            }
        }
        
        assertNotNull( time );
        assertTrue( time.startsWith( "Minute is even:" ) || time.startsWith( "Minute is odd:" ) );
    }

    @Test
    public void testExecute2() throws Exception {
        // Create the agenda
        AgendaType agendaType = new AgendaType( "AgendaType.name", "AgendaType.type" );
        Agenda agenda = new Agenda( "agenda2", agendaType );
        BusinessRuleSetType ruleType = new BusinessRuleSetType( "name", "type" );
        BusinessRuleSet businessRule = new BusinessRuleSet( "ruleId=uuid-456", "", ruleType, "A*B*C" );
        AnchorType anchorType = new AnchorType( "course", "clu.type.course" );
        Anchor anchor = new Anchor( "Math101", "Math101", anchorType );
        businessRule.setAnchor(anchor);
        agenda.addBusinessRule( businessRule );

        CourseEnrollmentRequest req1 = new CourseEnrollmentRequest(anchor.getId());
        Set<String> luiIds = new HashSet<String>(Arrays.asList("CPR101,MATH102,CHEM101,CHEM102".split(",")));
        req1.setLuiIds(luiIds);
        
        FactContainer factContainer1 = new FactContainer( "Math101", req1 );

        List<FactContainer> factList = Arrays.asList(factContainer1);
        
        // Create the rule set executor
        Reader source1 = new InputStreamReader( RuleSetExecutorDroolsImplTest.class.getResourceAsStream( "/drools/drls/org/kuali/student/rules/ruleexecution/drools/Math101PreReqRules.drl" ) );
        ((RuleEngineRepositoryMock) executor.getRuleEngineRepository()).setSource(source1);

        // Iterator through any returned rule engine objects
        Iterator<?> it = (Iterator<?>) executor.execute( agenda, factList );
        assertNotNull(it);
        assertTrue(factContainer1.getPropositionContainer().getRuleResult());
    }

}
