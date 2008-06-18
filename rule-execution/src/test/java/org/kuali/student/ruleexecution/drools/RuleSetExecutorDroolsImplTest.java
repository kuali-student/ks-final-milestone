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
package org.kuali.student.ruleexecution.drools;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.kuali.student.ruleexecution.RuleSetExecutor;
import org.kuali.student.ruleexecution.util.RuleEngineRepositoryMock;
import org.kuali.student.rules.brms.agenda.entity.Agenda;
import org.kuali.student.rules.brms.agenda.entity.AgendaType;
import org.kuali.student.rules.brms.agenda.entity.BusinessRule;
import org.kuali.student.rules.brms.agenda.entity.BusinessRuleType;

public class RuleSetExecutorDroolsImplTest {

    @Test
    public void testExecute() throws Exception {
        // Create the agenda
        AgendaType agendaType = new AgendaType( "AgendaType.name", "AgendaType.type" );
        Agenda agenda = new Agenda( "agenda", agendaType );
        BusinessRuleType ruleType = new BusinessRuleType( "name", "type" );
        agenda.addBusinessRule( new BusinessRule( "ruleId=uuid-123", ruleType, "" ) );
        
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
        
        System.out.println( "testExecute: " + time );
        assertNotNull( time );
        assertTrue( time.startsWith( "Minute is even:" ) || time.startsWith( "Minute is odd:" ) );
    }
}
