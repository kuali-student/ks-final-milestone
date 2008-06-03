package org.kuali.student.brms.agenda;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.brms.agenda.entity.Agenda;
import org.kuali.student.brms.agenda.entity.Anchor;
import org.kuali.student.brms.agenda.entity.AnchorType;

public class AgendaDiscoveryTest {

    @Test
    public void testGetAgenda_StudentPlansCourse() {
        AgendaDiscovery agendaDiscovery = new AgendaDiscovery();
        AgendaRequest request = new AgendaRequest( "student", "course", "offered", "planned" );
        AnchorType type = new AnchorType( "course", "clu.type.course" );
        Anchor anchor = new Anchor( "math301", "Math-301", type );
        
        Agenda agenda = agendaDiscovery.getAgenda( request, anchor );
        assertNotNull( agenda );

        System.out.println( "**************************************************" );
        System.out.println( "AgendaType = " + agenda.getAgendaType() );
        System.out.println( "AgendaType RuleTypes = " + agenda.getAgendaType().getBusinessRuleTypes() );

        System.out.println( "Agenda = " + agenda );
        System.out.println( "Agenda Rules = " + agenda.getBusinessRules() );
        System.out.println( "**************************************************" );
    }

    @Test
    public void testGetAgenda_StudentEnrollsInOfferedCourse() {
        AgendaDiscovery agendaDiscovery = new AgendaDiscovery();
        AgendaRequest request = new AgendaRequest( "student", "course", "offered", "enrolled" );
        AnchorType type = new AnchorType( "course", "clu.type.course" );
        Anchor anchor = new Anchor( "math301", "Math-301", type );
        
        Agenda agenda = agendaDiscovery.getAgenda( request, anchor );
        assertNotNull( agenda );

        System.out.println( "**************************************************" );
        System.out.println( "AgendaType = " + agenda.getAgendaType() );
        System.out.println( "AgendaType RuleTypes = " + agenda.getAgendaType().getBusinessRuleTypes() );

        System.out.println( "Agenda = " + agenda );
        System.out.println( "Agenda Rules = " + agenda.getBusinessRules() );
        System.out.println( "**************************************************" );
    }
}
