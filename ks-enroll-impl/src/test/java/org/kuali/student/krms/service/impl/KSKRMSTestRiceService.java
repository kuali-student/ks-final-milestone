/**
 * Copyright 2005-2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.krms.service.impl;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.krms.api.engine.*;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.framework.engine.*;
import org.kuali.rice.krms.framework.engine.expression.ComparisonOperator;
import org.kuali.rice.krms.framework.engine.expression.ComparisonOperatorServiceImpl;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.core.process.krms.KSKRMSTestCase;
import org.kuali.student.r2.core.process.krms.evaluator.KRMSEvaluator;
import org.kuali.student.r2.core.process.krms.sample.ActionMock;
import org.kuali.student.r2.core.process.krms.sample.TermResolverMock;

import java.util.*;

import static junit.framework.Assert.assertNotNull;

/**
 * This test is used to test the Rice Krms integration for KS.
 *
 * This Test class needs a physical database to execute and is therefore @Ignored.
 * This is only used in development until a proper solution is found to make this
 * part of the CI environment.
 *
 * @Author: SW Genis
 */
@Ignore
public class KSKRMSTestRiceService extends KSKRMSTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();

    }

    @Test
    public void testUpdateAgendaItem() {

        AgendaItemDefinition.Builder createBuilder = AgendaItemDefinition.Builder.create(null, "10000");
        createBuilder.setRuleId("10008");

        AgendaItemDefinition agendaItem = KrmsRepositoryServiceLocator.getAgendaBoService().createAgendaItem(createBuilder.build());

        assertNotNull(agendaItem);
        assertNotNull(agendaItem.getRuleId());

        AgendaItemDefinition createdItem = KrmsRepositoryServiceLocator.getAgendaBoService().getAgendaItemById(agendaItem.getId());

        assertNotNull(createdItem);
        assertNotNull(createdItem.getRuleId());

        AgendaItemDefinition.Builder updateBuilder = AgendaItemDefinition.Builder.create(agendaItem);
        KrmsRepositoryServiceLocator.getAgendaBoService().updateAgendaItem(updateBuilder.build());

        AgendaItemDefinition updatedItem = KrmsRepositoryServiceLocator.getAgendaBoService().getAgendaItemById(agendaItem.getId());

        assertNotNull(updatedItem);
        assertNotNull(updatedItem.getRuleId());

    }

}