package org.kuali.student.r2.core.population.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.test.util.AttributeTester;
import org.kuali.student.enrollment.test.util.CrudInfoTester;
import org.kuali.student.enrollment.test.util.IdEntityTester;
import org.kuali.student.enrollment.test.util.MetaTester;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationCategoryInfo;
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
    public static String principalId2 = "321";
    public ContextInfo contextInfo = null;
    public CrudInfoTester crudInfoTester = null;

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
        principalId2 = "321";
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        crudInfoTester = new CrudInfoTester(principalId, principalId2, contextInfo);
        if (populationService instanceof MockService) {
            ((MockService) populationService).clear();
        }
    }

    @Test
    public void testCrudPopulation() throws Exception {
        // create
        PopulationInfo expected = new PopulationInfo();
        crudInfoTester.initializeInfoForTestCreate(expected, PopulationServiceConstants.POPULATION_TYPE_KEY, PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
        assertNull(expected.getId());
        PopulationInfo actual = populationService.createPopulation(expected, contextInfo);
        assertNotNull(actual.getId());
        crudInfoTester.testCreate(expected, actual);

        // test read
        expected = actual;
        crudInfoTester.initializeInfoForTestRead(expected);
        actual = populationService.getPopulation(actual.getId(), contextInfo);
        crudInfoTester.testRead(expected, actual);

        // test update
        expected = actual;
        crudInfoTester.initializeInfoForTestUpdate(expected);
        actual = populationService.updatePopulation(expected.getId(), expected, contextInfo);
        crudInfoTester.testUpdate(expected, actual);

        // test read again
        expected = actual;
        crudInfoTester.initializeInfoForTestRead(expected);
        actual = populationService.getPopulation(actual.getId(), contextInfo);
        crudInfoTester.testRead(expected, actual);

        // test delete
        StatusInfo status = populationService.deletePopulation(expected.getId(), contextInfo);
        crudInfoTester.testDelete(expected, actual, status);
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
        expected.setName("poprulename1");
        expected.setTypeKey(PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY);
        expected.setStateKey(PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
        new AttributeTester().add2ForCreate(expected.getAttributes());
        assertNull(expected.getId());
        PopulationRuleInfo actual = populationService.createPopulationRule(expected, contextInfo);
        assertNotNull(actual.getId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new IdEntityTester().check(expected, actual);
        new MetaTester().checkAfterCreate(actual.getMeta());

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
        expected.setName("poprulename2");
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


    @Test
    public void testCrudPopulationCategory() throws Exception {
        // create
        PopulationCategoryInfo expected = new PopulationCategoryInfo();
        expected.setName("popcategoryname1");
        expected.setTypeKey(PopulationServiceConstants.POPULATION_CATEGORY_TYPE_KEY);
        expected.setStateKey(PopulationServiceConstants.POPULATION_CATEGORY_ACTIVE_STATE_KEY);
        new AttributeTester().add2ForCreate(expected.getAttributes());
        PopulationCategoryInfo actual = populationService.createPopulationCategory(PopulationServiceConstants.POPULATION_CATEGORY_TYPE_KEY, expected, contextInfo);
        assertNotNull(actual.getId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new IdEntityTester().check(expected, actual);
        new MetaTester().checkAfterCreate(actual.getMeta());

        // test read
        expected = actual;
        for (AttributeInfo itemInfo : expected.getAttributes()) {
            // clear out any id's set during the persistence
            // to let the checks work properly
            itemInfo.setId(null);
        }
        actual = populationService.getPopulationCategory(actual.getId(), contextInfo);
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
        expected.setName("popcategoryname2");
        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        actual = populationService.updatePopulationCategory(expected.getId(), expected, contextInfo);
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
        actual = populationService.getPopulationCategory(actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // test delete
        StatusInfo status = populationService.deletePopulationCategory(expected.getId(), contextInfo);
        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            actual = populationService.getPopulationCategory(expected.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted PopulationCategory");
        } catch (DoesNotExistException dnee) {
            // expected
        }
    }

}
