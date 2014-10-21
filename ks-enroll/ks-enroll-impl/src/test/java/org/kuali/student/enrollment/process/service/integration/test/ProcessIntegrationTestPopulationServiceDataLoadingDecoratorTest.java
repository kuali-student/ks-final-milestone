/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.process.service.integration.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.kuali.student.core.population.service.impl.PopulationServiceMapImpl;
import org.kuali.student.core.population.service.decorators.PopulationServiceIsMemberEvaluatorDecorator;

/**
 *
 * @author nwright
 */
public class ProcessIntegrationTestPopulationServiceDataLoadingDecoratorTest {

    public ProcessIntegrationTestPopulationServiceDataLoadingDecoratorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isMember method, of class ProcessTestPopulationServiceMockImpl.
     */
    @Test
    public void testTestMethods() throws Exception {
        System.out.println("test Test methods");

        ContextInfo context = new ContextInfo();
        context.setPrincipalId("Test-tester");


        PopulationServiceMapImpl mapImpl = new PopulationServiceMapImpl ();
        PopulationServiceIsMemberEvaluatorDecorator evaluator = new PopulationServiceIsMemberEvaluatorDecorator (mapImpl);
        ProcessIntegrationTestPopulationServiceDataLoadingDecorator instance = new ProcessIntegrationTestPopulationServiceDataLoadingDecorator(evaluator);
        assertTrue(instance.isMemberAsOfDate(ProcessIntegrationTestConstants.PERSON_ID_AMBER_HOPKINS_2155, PopulationServiceConstants.EVERYONE_POPULATION_KEY, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate(ProcessIntegrationTestConstants.PERSON_ID_BARBARA_HARRIS_2016, PopulationServiceConstants.EVERYONE_POPULATION_KEY, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate(ProcessIntegrationTestConstants.PERSON_ID_BETTY_MARTIN_2005, PopulationServiceConstants.EVERYONE_POPULATION_KEY, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate(ProcessIntegrationTestConstants.PERSON_ID_CLIFFORD_RIDDLE_2397, PopulationServiceConstants.EVERYONE_POPULATION_KEY, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate(ProcessIntegrationTestConstants.PERSON_ID_EDDIE_PITTMAN_2406, PopulationServiceConstants.EVERYONE_POPULATION_KEY, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate(ProcessIntegrationTestConstants.PERSON_ID_JOHNNY_MANNING_2374, PopulationServiceConstants.EVERYONE_POPULATION_KEY, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate(ProcessIntegrationTestConstants.PERSON_ID_KARA_STONE_2272, PopulationServiceConstants.EVERYONE_POPULATION_KEY, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate(ProcessIntegrationTestConstants.PERSON_ID_NINA_WELCH_2166, PopulationServiceConstants.EVERYONE_POPULATION_KEY, context.getCurrentDate(), context));
        assertTrue(instance.isMemberAsOfDate(ProcessIntegrationTestConstants.PERSON_ID_TRACY_BURTON_2132, PopulationServiceConstants.EVERYONE_POPULATION_KEY, context.getCurrentDate(), context));

        assertTrue(instance.isMemberAsOfDate(ProcessIntegrationTestConstants.PERSON_ID_AMBER_HOPKINS_2155, PopulationServiceConstants.SUMMER_ONLY_STUDENT_POPULATION_KEY, context.getCurrentDate(), context));
        assertFalse(instance.isMemberAsOfDate(ProcessIntegrationTestConstants.PERSON_ID_BARBARA_HARRIS_2016, PopulationServiceConstants.SUMMER_ONLY_STUDENT_POPULATION_KEY, context.getCurrentDate(), context));
        assertFalse(instance.isMemberAsOfDate(ProcessIntegrationTestConstants.PERSON_ID_BETTY_MARTIN_2005, PopulationServiceConstants.SUMMER_ONLY_STUDENT_POPULATION_KEY, context.getCurrentDate(), context));


    }
}
