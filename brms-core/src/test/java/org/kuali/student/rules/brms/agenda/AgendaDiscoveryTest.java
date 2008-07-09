package org.kuali.student.rules.brms.agenda;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.internal.common.agenda.AgendaRequest;
import org.kuali.student.rules.internal.common.agenda.entity.Agenda;
import org.kuali.student.rules.internal.common.agenda.entity.Anchor;
import org.kuali.student.rules.internal.common.agenda.entity.AnchorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@PersistenceFileLocation("classpath:META-INF/brms-persistence.xml")
@ContextConfiguration(locations = {"classpath:brms-core-test-context.xml"})
public class AgendaDiscoveryTest extends AbstractTransactionalDaoTest {

    @Autowired
    AgendaDiscovery agendaDiscovery;

    @Test
    public void testGetAgenda_StudentPlansCourse() {

        AgendaRequest request = new AgendaRequest("student", "course", "offered", "planned");
        AnchorType type = new AnchorType("course", "clu.type.course");
        Anchor anchor = new Anchor("math301", "Math-301", type);

        Agenda agenda = agendaDiscovery.getAgenda(request, anchor);
        assertNotNull(agenda);

        System.out.println("**************************************************");
        System.out.println("AgendaType = " + agenda.getAgendaType());
        System.out.println("AgendaType RuleTypes = " + agenda.getAgendaType().getBusinessRuleTypes());

        System.out.println("Agenda = " + agenda);
        System.out.println("Agenda Rules = " + agenda.getBusinessRules());
        System.out.println("**************************************************");
    }

    @Test
    public void testGetAgenda_StudentEnrollsInOfferedCourse() {

        AgendaRequest request = new AgendaRequest("student", "course", "offered", "enrolled");
        AnchorType type = new AnchorType("course", "clu.type.course");
        Anchor anchor = new Anchor("math301", "Math-301", type);

        Agenda agenda = agendaDiscovery.getAgenda(request, anchor);
        assertNotNull(agenda);

        System.out.println("**************************************************");
        System.out.println("AgendaType = " + agenda.getAgendaType());
        System.out.println("AgendaType RuleTypes = " + agenda.getAgendaType().getBusinessRuleTypes());

        System.out.println("Agenda = " + agenda);
        System.out.println("Agenda Rules = " + agenda.getBusinessRules());
        System.out.println("**************************************************");
    }

    /**
     * @return the agendaDiscovery
     */
    public AgendaDiscovery getAgendaDiscovery() {
        return agendaDiscovery;
    }

    /**
     * @param agendaDiscovery
     *            the agendaDiscovery to set
     */
    public void setAgendaDiscovery(AgendaDiscovery agendaDiscovery) {
        this.agendaDiscovery = agendaDiscovery;
    }
}
