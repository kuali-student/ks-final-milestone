/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by mahtabme on 12/19/13
 */
package org.kuali.student.enrollment.class2.population;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

/**
 * This class tests the PopulationService POC
 *
 * @author Mezba Mahtab
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:population-test-with-map-context.xml"})
public class TestPopulationServicePoc {

    // ====================
    // SETUP
    // ====================

    @Resource
    private PopulationService populationService;

    public PopulationService getPopulationService() {
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

    @Resource
    private PopulationServiceDataLoader dataLoader;

    private ContextInfo contextInfo = null;
    private String principalId = "123";

    @Before
    public void setUp()
    {
        principalId = "123";
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
    }

    @After
    public void cleanup() throws Exception {
        dataLoader.afterTest();
    }


    // ====================
    // TESTING
    // ====================

    @Test
    public void testDataLoad() throws Exception {
        dataLoader.beforeTest();
        assertNotNull(dataLoader.getFirstYearStudentPopulationId());
        assertNotNull(dataLoader.getFreshmenStudentPopulationId());
        assertNotNull(dataLoader.getInstructorPopulationId());
        assertNotNull(dataLoader.getStudentPopulationId());
        assertNotNull(dataLoader.getUndergraduteStudentPopulationId());

    }


}
