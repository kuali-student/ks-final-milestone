package org.kuali.student.ruleexecution;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.drools.rule.Package;
import java.util.Calendar;
import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ruleexecution.drools.RuleSetExecutorDroolsImpl;
import org.kuali.student.ruleexecution.util.RuleEngineRepositoryMock;
import org.kuali.student.rules.brms.agenda.AgendaDiscovery;
import org.kuali.student.rules.brms.agenda.AgendaRequest;
import org.kuali.student.rules.brms.agenda.entity.Agenda;
import org.kuali.student.rules.brms.agenda.entity.Anchor;
import org.kuali.student.rules.brms.agenda.entity.AnchorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class AgendaRuleExectionTest {

    @Autowired
    AgendaDiscovery agendaDiscovery;
    
    @Test
    public void testExecuteAgenda() throws Exception {

        AgendaRequest request = new AgendaRequest( "student", "course", "offered", "planned" );
        AnchorType anchorType = new AnchorType( "course", "clu.type.course" );
        String anchorID = "math301";
        Anchor anchor = new Anchor( anchorID, "Math-301", anchorType );
        // Get the specific agenda for math301
        Agenda agenda = agendaDiscovery.getAgenda( request, anchor );
        // Load compiled rule set (Drools Package) from the repository 
        Package pkg = (Package) RuleEngineRepositoryMock.getInstance().loadCompiledRuleSet( anchorID );
        
        // Create the rule set executor (use Spring IoC)
        RuleSetExecutor executor = new RuleSetExecutorDroolsImpl();
        // Iterate through returned rule engine objects
        // This should not be done in production
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
