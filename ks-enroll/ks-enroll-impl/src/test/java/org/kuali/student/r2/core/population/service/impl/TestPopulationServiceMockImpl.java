package org.kuali.student.r2.core.population.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.test.util.AttributeTester;
import org.kuali.student.enrollment.test.util.IdEntityTester;
import org.kuali.student.enrollment.test.util.MetaTester;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:population-test-context.xml"})
public class TestPopulationServiceMockImpl {

    /////////////////////////////////
    // DATA VARIABLES
    /////////////////////////////////

    @Resource
    private PopulationService populationService;

    public static String principalId = "123";
    public ContextInfo contextInfo = null;

    /////////////////////////////////
    // GETTERS AND SETTERS
    /////////////////////////////////
    public PopulationService gePopulationService() {
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

    ////////////////////////////
    // FUNCTIONALS
    ////////////////////////////

    @Before
    public void setUp() {
        principalId = "123";
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
    }

    @Test
    public void testCrudPopulation() throws Exception {
        // create
        PopulationInfo expected = new PopulationInfo();
        expected.setId("pop1");
        expected.setName("mezba fan club");
        expected.setTypeKey(PopulationServiceConstants.POPULATION_TYPE_KEY);
        expected.setStateKey(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
        new AttributeTester().add2ForCreate(expected.getAttributes());
        PopulationInfo actual = populationService.createPopulation(expected, contextInfo);
        assertNotNull(actual.getId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new IdEntityTester().check(expected, actual);
        new MetaTester().checkAfterCreate(actual.getMeta());
        assertEquals(expected.getId(), actual.getId());

        // test read
        expected = actual;
        for (AttributeInfo itemInfo : expected.getAttributes()) {
            // clear out any id's set during the persistence
            // to let the checks work properly
            itemInfo.setId(null);
        }
        actual = populationService.getPopulation(actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // test update
        expected = actual;
        for (AttributeInfo itemInfo : expected.getAttributes()) {
            // clear out any id's set during the persistence
            // to let the checks work properly
            itemInfo.setId(null);
        }
        expected.setName("Mezba Official Fan Club");
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = populationService.updatePopulation(expected.getId(), expected, contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // test read
        expected = actual;
        for (AttributeInfo itemInfo : expected.getAttributes()) {

            // clear out any id's set during the persistence
            // to let the checks work properly
            itemInfo.setId(null);
        }
        actual = populationService.getPopulation(actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // test delete
        StatusInfo status = populationService.deletePopulation(expected.getId(), contextInfo);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = populationService.getPopulation(expected.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted Population");
        } catch (DoesNotExistException dnee) {
            // expected
        }
    }


    @Test
    public void testCrudPopulationRule() throws Exception {
        // create
        PopulationRuleInfo expected = new PopulationRuleInfo();
        expected.setId("poprule1");
        expected.setName("how to be a Canadian");
        expected.setTypeKey(PopulationServiceConstants.POPULATION_RULE_TYPE_KEY);
        expected.setStateKey(PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
        new AttributeTester().add2ForCreate(expected.getAttributes());
        PopulationRuleInfo actual = populationService.createPopulationRule(expected, contextInfo);
        assertNotNull(actual.getId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new IdEntityTester().check(expected, actual);
        new MetaTester().checkAfterCreate(actual.getMeta());
        assertEquals(expected.getId(), actual.getId());

        // test read
        expected = actual;
        for (AttributeInfo itemInfo : expected.getAttributes()) {
            // clear out any id's set during the persistence
            // to let the checks work properly
            itemInfo.setId(null);
        }
        actual = populationService.getPopulationRule(actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // test update
        expected = actual;
        for (AttributeInfo itemInfo : expected.getAttributes()) {
            // clear out any id's set during the persistence
            // to let the checks work properly
            itemInfo.setId(null);
        }
        expected.setName("How To Tell You Are Canadian");
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = populationService.updatePopulationRule(expected.getId(), expected, contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // test read
        expected = actual;
        for (AttributeInfo itemInfo : expected.getAttributes()) {
            // clear out any id's set during the persistence
            // to let the checks work properly
            itemInfo.setId(null);
        }
        actual = populationService.getPopulationRule(actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // test delete
        StatusInfo status = populationService.deletePopulationRule(expected.getId(), contextInfo);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = populationService.getPopulationRule(expected.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted PopulationRule");
        } catch (DoesNotExistException dnee) {
            // expected
        }
    }

}
