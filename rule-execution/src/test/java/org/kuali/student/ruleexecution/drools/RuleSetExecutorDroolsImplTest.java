package org.kuali.student.ruleexecution.drools;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Iterator;

import org.drools.rule.Package;
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
        String ruleUUID = "uuid-123";
        Package pkg = (Package) RuleEngineRepositoryMock.getInstance().loadCompiledRuleSet( ruleUUID );

        // Create the agenda
        AgendaType agendaType = new AgendaType( "AgendaType.name", "AgendaType.type" );
        Agenda agenda = new Agenda( "agenda", agendaType );
        BusinessRuleType ruleType = new BusinessRuleType( "name", "type" );
        agenda.addBusinessRule( new BusinessRule( ruleUUID, ruleType, "" ) );
        
        // Create the rule set executor
        RuleSetExecutor executor = new RuleSetExecutorDroolsImpl();
        // Iterator through any returned rule engine objects
        Iterator it = (Iterator) executor.execute( agenda, pkg, Calendar.getInstance() );
        
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
