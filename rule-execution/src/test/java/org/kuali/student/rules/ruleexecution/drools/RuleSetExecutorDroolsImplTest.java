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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.kuali.student.rules.common.agenda.entity.Agenda;
import org.kuali.student.rules.common.agenda.entity.AgendaType;
import org.kuali.student.rules.common.agenda.entity.Anchor;
import org.kuali.student.rules.common.agenda.entity.AnchorType;
import org.kuali.student.rules.common.agenda.entity.BusinessRuleSet;
import org.kuali.student.rules.common.agenda.entity.BusinessRuleType;
import org.kuali.student.rules.common.statement.PropositionContainer;
import org.kuali.student.rules.common.util.CourseEnrollmentRequest;
import org.kuali.student.rules.ruleexecution.util.RuleEngineRepositoryMock;
import org.kuali.student.rules.rulesetexecution.RuleSetExecutor;
import org.kuali.student.rules.rulesetexecution.drools.RuleSetExecutorDroolsImpl;
import org.kuali.student.rules.util.FactContainer;

public class RuleSetExecutorDroolsImplTest {

    @Test
    public void testExecute() throws Exception {
        // Create the agenda
        AgendaType agendaType = new AgendaType( "AgendaType.name", "AgendaType.type" );
        Agenda agenda = new Agenda( "agenda", agendaType );
        BusinessRuleType ruleType = new BusinessRuleType( "name", "type" );
        agenda.addBusinessRule( new BusinessRuleSet( "ruleId=uuid-123", "", ruleType, "" ) );
        
        // Create the rule set executor
        RuleSetExecutor executor = new RuleSetExecutorDroolsImpl( new RuleEngineRepositoryMock() );
        // Add facts
        List<Object> facts = new ArrayList<Object>();
        facts.add( Calendar.getInstance() );
        // Iterator through any returned rule engine objects
        Iterator it = (Iterator) executor.execute( agenda, facts );
        
        assertNotNull( it );

        String time = null;
        while( it != null && it.hasNext() ) {
            Object obj = it.next();
            //System.out.println( obj.getClass() + " = " + obj );
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
        Agenda agenda = new Agenda( "agenda", agendaType );
        BusinessRuleType ruleType = new BusinessRuleType( "name", "type" );
        BusinessRuleSet businessRule = new BusinessRuleSet( "ruleId=uuid-123", "", ruleType, "A*B*C" );
        agenda.addBusinessRule( businessRule );
        AnchorType anchorType = new AnchorType( "course", "clu.type.course" );
        Anchor anchor = new Anchor( "Math101", "Math101", anchorType );
        businessRule.setAnchor(anchor);

        CourseEnrollmentRequest req1 = new CourseEnrollmentRequest(anchor.getId());
        Set<String> luiIds = new HashSet<String>(Arrays.asList("CPR101,MATH102,CHEM101,CHEM102".split(",")));
        req1.setLuiIds(luiIds);
        
        PropositionContainer pc = new PropositionContainer();
        pc.setFunctionalRuleString("A*B*C");
        FactContainer factContainer1 = new FactContainer( "Math101", req1, pc );

        List<FactContainer> factList = Arrays.asList(factContainer1);
        
        // Create the rule set executor
        Reader source1 = new InputStreamReader( RuleSetExecutorDroolsImplTest.class.getResourceAsStream( "/Math101PreReqRules.drl" ) );

        RuleSetExecutor executor = new RuleSetExecutorDroolsImpl( new RuleEngineRepositoryMock(source1) );
        // Iterator through any returned rule engine objects
        Iterator it = (Iterator) executor.execute( agenda, factList );
        assertNotNull(it);
        assertTrue(factContainer1.getPropositionContainer().getRuleResult());
    }
    
}
