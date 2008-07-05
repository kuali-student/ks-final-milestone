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
package org.kuali.student.rules.ruleexecution;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.poc.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.rules.brms.agenda.AgendaDiscovery;
import org.kuali.student.rules.brms.agenda.AgendaRequest;
import org.kuali.student.rules.brms.agenda.entity.Agenda;
import org.kuali.student.rules.brms.agenda.entity.Anchor;
import org.kuali.student.rules.brms.agenda.entity.AnchorType;
import org.kuali.student.rules.brms.agenda.entity.BusinessRuleSet;
import org.kuali.student.rules.brms.agenda.entity.BusinessRuleType;
import org.kuali.student.rules.brms.repository.RuleEngineRepository;
import org.kuali.student.rules.ruleexecution.drools.RuleSetExecutorDroolsImpl;
import org.kuali.student.rules.ruleexecution.util.RuleEngineRepositoryMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@PersistenceFileLocation("classpath:META-INF/brms-persistence.xml")
@ContextConfiguration(locations = {"classpath:brms-core-test-context.xml"})
public class AgendaRuleExectionTest extends AbstractTransactionalDaoTest {

    @Autowired
    AgendaDiscovery agendaDiscovery;

    @Test
    public void testExecuteAgenda() throws Exception {

        AgendaRequest request = new AgendaRequest("student", "course", "offered", "planned");
        AnchorType anchorType = new AnchorType("course", "clu.type.course");
        String anchorID = "math301";
        Anchor anchor = new Anchor(anchorID, "Math-301", anchorType);
        // Get the specific agenda for math301
        Agenda agenda = agendaDiscovery.getAgenda(request, anchor);

        // Add a business rule since the agenda returns none
        BusinessRuleType ruleType = new BusinessRuleType("name", "type");
        agenda.addBusinessRule(new BusinessRuleSet("ruleId=uuid-123", "", ruleType, ""));

        System.out.println("\n\n\n************* agenda = " + agenda);
        System.out.println("\n\n\n************* rules = " + agenda.getBusinessRules() + "\n\n\n");

        // Create repository
        RuleEngineRepository ruleEngineRepository = new RuleEngineRepositoryMock();
        // Create the rule set executor (use Spring IoC)
        RuleSetExecutor executor = new RuleSetExecutorDroolsImpl(ruleEngineRepository);
        // Add facts
        List<Object> facts = new ArrayList<Object>();
        facts.add(Calendar.getInstance());
        // Iterate through returned rule engine objects
        // This should not be done in production
        Iterator it = (Iterator) executor.execute(agenda, facts);

        assertNotNull(it);

        String time = null;
        while (it != null && it.hasNext()) {
            Object obj = it.next();
            // System.out.println( obj.getClass() + " = " + obj );
            if (obj instanceof String) {
                time = (String) obj;
                break;
            }
        }

        System.out.println("testExecute: " + time);
        assertNotNull(time);
        assertTrue(time.startsWith("Minute is even:") || time.startsWith("Minute is odd:"));
    }
}
