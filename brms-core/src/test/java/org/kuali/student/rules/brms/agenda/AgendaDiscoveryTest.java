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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@PersistenceFileLocation("classpath:META-INF/brms-persistence.xml")
@ContextConfiguration(locations = {"classpath:brms-core-test-context.xml"})
public class AgendaDiscoveryTest extends AbstractTransactionalDaoTest {

    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(AgendaDiscoveryTest.class);

    @Autowired
    AgendaDiscovery agendaDiscovery;

    @Test
    public void testGetAgenda_StudentPlansCourse() {

        AgendaRequest request = new AgendaRequest("student", "course", "offered", "planned");
        AnchorType type = new AnchorType("course", "clu.type.course");
        Anchor anchor = new Anchor("math301", "Math-301", type);

        Agenda agenda = agendaDiscovery.getAgenda(request, anchor);
        assertNotNull(agenda);

        logger.info("**************************************************");
        logger.info("AgendaType = " + agenda.getAgendaType());
        logger.info("AgendaType RuleTypes = " + agenda.getAgendaType().getBusinessRuleTypes());

        logger.info("Agenda = " + agenda);
        logger.info("Agenda Rules = " + agenda.getBusinessRules());
        logger.info("**************************************************");
    }

    @Test
    public void testGetAgenda_StudentEnrollsInOfferedCourse() {

        AgendaRequest request = new AgendaRequest("student", "course", "offered", "enrolled");
        AnchorType type = new AnchorType("course", "clu.type.course");
        Anchor anchor = new Anchor("math301", "Math-301", type);

        Agenda agenda = agendaDiscovery.getAgenda(request, anchor);
        assertNotNull(agenda);

        logger.info("**************************************************");
        logger.info("AgendaType = " + agenda.getAgendaType());
        logger.info("AgendaType RuleTypes = " + agenda.getAgendaType().getBusinessRuleTypes());

        logger.info("Agenda = " + agenda);
        logger.info("Agenda Rules = " + agenda.getBusinessRules());
        logger.info("**************************************************");
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
