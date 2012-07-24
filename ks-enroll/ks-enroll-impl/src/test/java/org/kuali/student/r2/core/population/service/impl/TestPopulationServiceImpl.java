/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Charles on 7/16/12
 */
package org.kuali.student.r2.core.population.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import junit.framework.Assert;

import javax.annotation.Resource;
import static junit.framework.Assert.assertEquals;

/**
 * This class tests PopulationServiceImpl
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:population-impl-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestPopulationServiceImpl {
    @Resource
    private PopulationService populationService;

    private ContextInfo contextInfo;

    private void before() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("admin");
    }

    private PopulationInfo _createPopulationInfo() {
        PopulationInfo populationInfo = new PopulationInfo();
        populationInfo.setName("TestPop");
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("plain");
        richTextInfo.setFormatted("formatted");
        populationInfo.setDescr(richTextInfo);
        populationInfo.setStateKey(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
        populationInfo.setTypeKey(PopulationServiceConstants.POPULATION_TYPE_KEY);
        return populationInfo;
    }

    @Test
    public void testCreateGet() {
        before();
        PopulationInfo info = _createPopulationInfo();
        try {
            PopulationInfo created = populationService.createPopulation(info, contextInfo);
            PopulationInfo fetched = populationService.getPopulation(created.getId(), contextInfo);
            assertEquals(info.getName(), fetched.getName());
            assertEquals(info.getDescr().getPlain(), fetched.getDescr().getPlain());
            assertEquals(info.getDescr().getFormatted(), fetched.getDescr().getFormatted());
            assertEquals(info.getStateKey(), fetched.getStateKey());
            assertEquals(info.getTypeKey(), fetched.getTypeKey());
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }
}
