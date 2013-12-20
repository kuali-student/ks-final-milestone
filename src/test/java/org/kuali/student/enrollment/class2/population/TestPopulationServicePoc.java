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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.poc.rules.population.PopulationPocPersonEnum;
import org.kuali.student.poc.rules.population.PopulationPocStudentEnum;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.infc.PopulationRule;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

        Date date = new Date(System.currentTimeMillis());

        // initially all the population ids should be null
        assertNull(dataLoader.getFirstYearStudentPopulationId());
        assertNull(dataLoader.getFreshmenStudentPopulationId());
        assertNull(dataLoader.getInstructorPopulationId());
        assertNull(dataLoader.getStudentPopulationId());
        assertNull(dataLoader.getUndergraduteStudentPopulationId());

        // load the date
        dataLoader.beforeTest();

        // now the values should be there
        assertNotNull(dataLoader.getFirstYearStudentPopulationId());
        assertNotNull(dataLoader.getFreshmenStudentPopulationId());
        assertNotNull(dataLoader.getInstructorPopulationId());
        assertNotNull(dataLoader.getStudentPopulationId());
        assertNotNull(dataLoader.getUndergraduteStudentPopulationId());

        // check population first year students
        PopulationInfo firstYearPop = populationService.getPopulation(dataLoader.getFirstYearStudentPopulationId(), contextInfo);
        assertNotNull(firstYearPop);
        assertEquals(firstYearPop.getId(), dataLoader.getFirstYearStudentPopulationId());
        PopulationRuleInfo firstYearPopRule = populationService.getPopulationRuleForPopulation(dataLoader.getFirstYearStudentPopulationId(), contextInfo);
        assertNotNull(firstYearPopRule);
        assertEquals(5, firstYearPopRule.getPersonIds().size());
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT1.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT4.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT5.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT7.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT10.getPersonId(), firstYearPop.getId(), date, contextInfo));

        // check population students
        PopulationInfo studentPop = populationService.getPopulation(dataLoader.getStudentPopulationId(), contextInfo);
        assertNotNull(studentPop);
        assertEquals(studentPop.getId(), dataLoader.getStudentPopulationId());
        PopulationRuleInfo studentPopRule = populationService.getPopulationRuleForPopulation(dataLoader.getStudentPopulationId(), contextInfo);
        assertNotNull(studentPopRule);
        assertEquals(12, studentPopRule.getPersonIds().size());
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT1.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT2.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT3.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT4.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT5.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT6.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT7.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT8.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT9.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT10.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT11.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT12.getPersonId(), studentPop.getId(), date, contextInfo));

        // check population instructors
        PopulationInfo instructorsPop = populationService.getPopulation(dataLoader.getInstructorPopulationId(), contextInfo);
        assertNotNull(instructorsPop);
        assertEquals(instructorsPop.getId(), dataLoader.getInstructorPopulationId());
        PopulationRuleInfo instructorsPopRule = populationService.getPopulationRuleForPopulation(dataLoader.getInstructorPopulationId(), contextInfo);
        assertNotNull(instructorsPopRule);
        assertEquals(2, instructorsPopRule.getPersonIds().size());
        assertTrue(populationService.isMemberAsOfDate(PopulationPocPersonEnum.INSTRUCTOR1.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocPersonEnum.INSTRUCTOR2.getPersonId(), instructorsPop.getId(), date, contextInfo));

        // check population unbdergraduate students
        PopulationInfo undergradPop = populationService.getPopulation(dataLoader.getUndergraduteStudentPopulationId(), contextInfo);
        assertNotNull(undergradPop);
        assertEquals(undergradPop.getId(), dataLoader.getUndergraduteStudentPopulationId());
        PopulationRuleInfo undergradPopRule = populationService.getPopulationRuleForPopulation(dataLoader.getUndergraduteStudentPopulationId(), contextInfo);
        assertNotNull(undergradPopRule);
        assertEquals(5, undergradPopRule.getPersonIds().size());
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT1.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT8.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT10.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT11.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationPocStudentEnum.STUDENT12.getPersonId(), undergradPop.getId(), date, contextInfo));

    }


}
