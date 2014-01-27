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
package org.kuali.student.core.population.service.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.infc.PopulationRule;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the PopulationService evaluation logic
 *
 * @author Mezba Mahtab
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:population-test-with-map-context.xml"})
public class TestPopulationServiceEvaluationLogic {

    // ====================
    // SETUP
    // ====================

    @Resource(name = "populationService")
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

        // the students should have same id in "person" and "student" enums
        assertEquals(PopulationTestPersonEnum.STUDENT1.getPersonId(), PopulationTestStudentEnum.STUDENT1.getPersonId());
        assertEquals(PopulationTestPersonEnum.STUDENT2.getPersonId(), PopulationTestStudentEnum.STUDENT2.getPersonId());
        assertEquals(PopulationTestPersonEnum.STUDENT3.getPersonId(), PopulationTestStudentEnum.STUDENT3.getPersonId());
        assertEquals(PopulationTestPersonEnum.STUDENT4.getPersonId(), PopulationTestStudentEnum.STUDENT4.getPersonId());
        assertEquals(PopulationTestPersonEnum.STUDENT5.getPersonId(), PopulationTestStudentEnum.STUDENT5.getPersonId());
        assertEquals(PopulationTestPersonEnum.STUDENT6.getPersonId(), PopulationTestStudentEnum.STUDENT6.getPersonId());
        assertEquals(PopulationTestPersonEnum.STUDENT7.getPersonId(), PopulationTestStudentEnum.STUDENT7.getPersonId());
        assertEquals(PopulationTestPersonEnum.STUDENT8.getPersonId(), PopulationTestStudentEnum.STUDENT8.getPersonId());
        assertEquals(PopulationTestPersonEnum.STUDENT9.getPersonId(), PopulationTestStudentEnum.STUDENT9.getPersonId());
        assertEquals(PopulationTestPersonEnum.STUDENT10.getPersonId(), PopulationTestStudentEnum.STUDENT10.getPersonId());
        assertEquals(PopulationTestPersonEnum.STUDENT11.getPersonId(), PopulationTestStudentEnum.STUDENT11.getPersonId());
        assertEquals(PopulationTestPersonEnum.STUDENT12.getPersonId(), PopulationTestStudentEnum.STUDENT12.getPersonId());

        // create today's date
        Date date = new Date(System.currentTimeMillis());

        // initially all the population ids in data loader should be null
        assertNull(dataLoader.getFirstYearStudentPopulationId());
        assertNull(dataLoader.getFreshmenStudentPopulationId());
        assertNull(dataLoader.getInstructorPopulationId());
        assertNull(dataLoader.getStudentPopulationId());
        assertNull(dataLoader.getUndergraduteStudentPopulationId());

        // load the data
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
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT1.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT4.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT5.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT7.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT10.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT2.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT3.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT6.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT8.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT9.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT11.getPersonId(), firstYearPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT12.getPersonId(), firstYearPop.getId(), date, contextInfo));

        // check population students
        PopulationInfo studentPop = populationService.getPopulation(dataLoader.getStudentPopulationId(), contextInfo);
        assertNotNull(studentPop);
        assertEquals(studentPop.getId(), dataLoader.getStudentPopulationId());
        PopulationRuleInfo studentPopRule = populationService.getPopulationRuleForPopulation(dataLoader.getStudentPopulationId(), contextInfo);
        assertNotNull(studentPopRule);
        assertEquals(12, studentPopRule.getPersonIds().size());
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT1.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT2.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT3.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT4.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT5.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT6.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT7.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT8.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT9.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT10.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT11.getPersonId(), studentPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT12.getPersonId(), studentPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR1.getPersonId(), studentPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR2.getPersonId(), studentPop.getId(), date, contextInfo));

        // check population instructors
        PopulationInfo instructorsPop = populationService.getPopulation(dataLoader.getInstructorPopulationId(), contextInfo);
        assertNotNull(instructorsPop);
        assertEquals(instructorsPop.getId(), dataLoader.getInstructorPopulationId());
        PopulationRuleInfo instructorsPopRule = populationService.getPopulationRuleForPopulation(dataLoader.getInstructorPopulationId(), contextInfo);
        assertNotNull(instructorsPopRule);
        assertEquals(2, instructorsPopRule.getPersonIds().size());
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR1.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR2.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.STUDENT1.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.STUDENT2.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.STUDENT3.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.STUDENT4.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.STUDENT5.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.STUDENT6.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.STUDENT7.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.STUDENT8.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.STUDENT9.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.STUDENT10.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.STUDENT11.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.STUDENT12.getPersonId(), instructorsPop.getId(), date, contextInfo));

        // check population undergraduate students
        PopulationInfo undergradPop = populationService.getPopulation(dataLoader.getUndergraduteStudentPopulationId(), contextInfo);
        assertNotNull(undergradPop);
        assertEquals(undergradPop.getId(), dataLoader.getUndergraduteStudentPopulationId());
        PopulationRuleInfo undergradPopRule = populationService.getPopulationRuleForPopulation(dataLoader.getUndergraduteStudentPopulationId(), contextInfo);
        assertNotNull(undergradPopRule);
        assertEquals(5, undergradPopRule.getPersonIds().size());
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT1.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT8.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT10.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT11.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT12.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT2.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT3.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT4.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT5.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT6.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT7.getPersonId(), undergradPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT9.getPersonId(), undergradPop.getId(), date, contextInfo));

        // check population freshmen (undergraduate first year) students
        PopulationInfo freshmenPop = populationService.getPopulation(dataLoader.getFreshmenStudentPopulationId(), contextInfo);
        assertNotNull(freshmenPop);
        assertEquals(freshmenPop.getId(), dataLoader.getFreshmenStudentPopulationId());
        PopulationRuleInfo freshmenPopRule = populationService.getPopulationRuleForPopulation(dataLoader.getFreshmenStudentPopulationId(), contextInfo);
        assertNotNull(freshmenPopRule);
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT1.getPersonId(), freshmenPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT10.getPersonId(), freshmenPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT2.getPersonId(), freshmenPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT3.getPersonId(), freshmenPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT4.getPersonId(), freshmenPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT5.getPersonId(), freshmenPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT6.getPersonId(), freshmenPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT7.getPersonId(), freshmenPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT8.getPersonId(), freshmenPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT9.getPersonId(), freshmenPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT11.getPersonId(), freshmenPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestStudentEnum.STUDENT12.getPersonId(), freshmenPop.getId(), date, contextInfo));

    }

    @Test
    public void testAddingRemovingPeople() throws Exception {

        // create today's date
        Date date = new Date(System.currentTimeMillis());

        // load the data
        dataLoader.beforeTest();

        // check population instructors
        PopulationInfo instructorsPop = populationService.getPopulation(dataLoader.getInstructorPopulationId(), contextInfo);
        assertNotNull(instructorsPop);
        assertEquals(instructorsPop.getId(), dataLoader.getInstructorPopulationId());
        PopulationRuleInfo instructorsPopRule = populationService.getPopulationRuleForPopulation(dataLoader.getInstructorPopulationId(), contextInfo);
        assertNotNull(instructorsPopRule);
        assertEquals(2, instructorsPopRule.getPersonIds().size());
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR1.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR2.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR3.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR4.getPersonId(), instructorsPop.getId(), date, contextInfo));

        // now will add instructor 3 in the population
        instructorsPopRule.setPersonIds(PopulationTestUtilities.addStringToListString(PopulationTestPersonEnum.INSTRUCTOR3.getPersonId(), instructorsPopRule.getPersonIds()));
        instructorsPopRule = populationService.updatePopulationRule(instructorsPopRule.getId(), instructorsPopRule, contextInfo);
        populationService.applyPopulationRuleToPopulation(instructorsPopRule.getId(), dataLoader.getInstructorPopulationId(), contextInfo);

        assertEquals(3, instructorsPopRule.getPersonIds().size());
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR1.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR2.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR3.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR4.getPersonId(), instructorsPop.getId(), date, contextInfo));

        // now will add instructor 4 in the population
        instructorsPopRule.setPersonIds(PopulationTestUtilities.addStringToListString(PopulationTestPersonEnum.INSTRUCTOR4.getPersonId(), instructorsPopRule.getPersonIds()));
        instructorsPopRule = populationService.updatePopulationRule(instructorsPopRule.getId(), instructorsPopRule, contextInfo);
        populationService.applyPopulationRuleToPopulation(instructorsPopRule.getId(), dataLoader.getInstructorPopulationId(), contextInfo);

        assertEquals(4, instructorsPopRule.getPersonIds().size());
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR1.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR2.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR3.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR4.getPersonId(), instructorsPop.getId(), date, contextInfo));

        // now will remove instructor 3 from the population
        instructorsPopRule.setPersonIds(PopulationTestUtilities.removeStringFromListString(PopulationTestPersonEnum.INSTRUCTOR3.getPersonId(), instructorsPopRule.getPersonIds()));
        instructorsPopRule = populationService.updatePopulationRule(instructorsPopRule.getId(), instructorsPopRule, contextInfo);
        populationService.applyPopulationRuleToPopulation(instructorsPopRule.getId(), dataLoader.getInstructorPopulationId(), contextInfo);

        assertEquals(3, instructorsPopRule.getPersonIds().size());
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR1.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR2.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR4.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR3.getPersonId(), instructorsPop.getId(), date, contextInfo));

        // now will remove instructor 4 from the population
        instructorsPopRule.setPersonIds(PopulationTestUtilities.removeStringFromListString(PopulationTestPersonEnum.INSTRUCTOR4.getPersonId(), instructorsPopRule.getPersonIds()));
        instructorsPopRule = populationService.updatePopulationRule(instructorsPopRule.getId(), instructorsPopRule, contextInfo);
        populationService.applyPopulationRuleToPopulation(instructorsPopRule.getId(), dataLoader.getInstructorPopulationId(), contextInfo);

        assertEquals(2, instructorsPopRule.getPersonIds().size());
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR1.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertTrue(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR2.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR3.getPersonId(), instructorsPop.getId(), date, contextInfo));
        assertFalse(populationService.isMemberAsOfDate(PopulationTestPersonEnum.INSTRUCTOR4.getPersonId(), instructorsPop.getId(), date, contextInfo));

    }


}
