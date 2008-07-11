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
package org.kuali.student.rules.brms.agenda;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.internal.common.agenda.AgendaRequest;
import org.kuali.student.rules.internal.common.agenda.entity.Agenda;
import org.kuali.student.rules.internal.common.agenda.entity.Anchor;
import org.kuali.student.rules.internal.common.agenda.entity.AnchorType;
import org.kuali.student.rules.internal.common.agenda.entity.BusinessRuleSet;
import org.kuali.student.rules.internal.common.agenda.entity.BusinessRuleSetType;
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

    private boolean containsRuleSetType(Agenda agenda, String type) {
        List<BusinessRuleSetType> ruleSetTypes = agenda.getAgendaType().getBusinessRuleTypes();
        for(BusinessRuleSetType ruleSetType : ruleSetTypes ) {
            if (ruleSetType.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsRuleSet(Agenda agenda, String ruleName) {
        Collection<BusinessRuleSet> ruleSets = agenda.getBusinessRules();
        for(BusinessRuleSet ruleSet : ruleSets ) {
            if (ruleSet.getBusinessRuleName().equals(ruleName)) {
                return true;
            }
        }
    return false;
}

    @Test
    public void testGetAgenda_StudentPlansCourse() {

        AgendaRequest request = new AgendaRequest("student", "course", "offered", "planned");
        AnchorType type = new AnchorType("course", "course.co.req");
        Anchor anchor = new Anchor("CPR 201", "CPR 201", type);

        Agenda agenda = agendaDiscovery.getAgenda(request, anchor);
        
        logger.info("**************************************************");
        logger.info("AgendaType = " + agenda.getAgendaType());
        logger.info("AgendaType RuleTypes = " + agenda.getAgendaType().getBusinessRuleTypes());
        logger.info("Agenda = " + agenda);
        logger.info("Agenda Rules = " + agenda.getBusinessRules());
        logger.info("**************************************************");
        
        assertNotNull(agenda);
        assertTrue(containsRuleSetType(agenda, "course.pre.req"));
        assertTrue(containsRuleSetType(agenda, "course.co.req"));
        assertTrue(containsRuleSet(agenda, "Intermediate CPR"));
    }

    @Test
    public void testGetAgenda_StudentEnrollsInOfferedCourse() {

        AgendaRequest request = new AgendaRequest("student", "course", "offered", "enrolled");
        AnchorType type = new AnchorType("course", "course.co.req");
        Anchor anchor = new Anchor("CPR 201", "CPR 201", type);

        Agenda agenda = agendaDiscovery.getAgenda(request, anchor);
        Collection<BusinessRuleSet> ruleSets = agenda.getBusinessRules();
        List<BusinessRuleSetType> ruleSetTypes = agenda.getAgendaType().getBusinessRuleTypes();

        logger.info("**************************************************");
        logger.info("AgendaType = " + agenda.getAgendaType());
        logger.info("AgendaType RuleTypes = " + agenda.getAgendaType().getBusinessRuleTypes());
        logger.info("Agenda = " + agenda);
        logger.info("Agenda Rules = " + agenda.getBusinessRules());
        logger.info("**************************************************");

        assertNotNull(agenda);
        assertTrue(containsRuleSetType(agenda, "course.pre.req"));
        assertTrue(containsRuleSetType(agenda, "course.co.req"));
        assertTrue(containsRuleSet(agenda, "Intermediate CPR"));
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
