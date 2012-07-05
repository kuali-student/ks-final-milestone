package org.kuali.student.r2.core.population.service.impl;

import org.apache.commons.collections.CollectionUtils;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

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
        crudInfoTester.initializeInfoForTestCreate(expected, PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY, PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
        assertNull(expected.getId());
        PopulationRuleInfo actual = populationService.createPopulationRule(expected, contextInfo);
        assertNotNull(actual.getId());
        crudInfoTester.testCreate(expected, actual);

        // test read
        expected = actual;
        crudInfoTester.initializeInfoForTestRead(expected);
        actual = populationService.getPopulationRule(actual.getId(), contextInfo);
        crudInfoTester.testRead(expected, actual);

        // test update
        expected = actual;
        crudInfoTester.initializeInfoForTestUpdate(expected);
        actual = populationService.updatePopulationRule(expected.getId(), expected, contextInfo);
        crudInfoTester.testUpdate(expected, actual);

        // test read again
        expected = actual;
        crudInfoTester.initializeInfoForTestRead(expected);
        actual = populationService.getPopulationRule(actual.getId(), contextInfo);
        crudInfoTester.testRead(expected, actual);

        // test delete
        StatusInfo status = populationService.deletePopulationRule(expected.getId(), contextInfo);
        crudInfoTester.testDelete(expected, actual, status);
        try {
            actual = populationService.getPopulationRule(expected.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted PopulationRule");
        } catch (DoesNotExistException dnee) {
            // expected
        }
    }

/*
    Changed 2012, July 05, by Mezba. Code reference: kept to compare CrudInfoTester which is a new way of doing common unit tests.

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
*/

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

    @Test
    public void testMiscOperations() throws Exception {
        // create a few populations
        List<String> populationIds = new ArrayList<String>();
        for (int i=0; i<5; i++) {
            PopulationInfo populationInfo = new PopulationInfo();
            crudInfoTester.initializeInfoForTestCreate(populationInfo, PopulationServiceConstants.POPULATION_TYPE_KEY, PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
            populationInfo.setName("pop" + i);
            populationInfo = populationService.createPopulation(populationInfo, contextInfo);
            populationIds.add(populationInfo.getId());
        }
        // test getPopulationsByIds
        List<PopulationInfo> populationInfos = populationService.getPopulationsByIds(populationIds, contextInfo);
        assertEquals(5, populationInfos.size());
        crudInfoTester.getIdEntityTester().checkListOfInfoAgainstListOfIds(populationInfos, populationIds);
        List<String> populationIds2 = new ArrayList<String>();
        populationIds2.add(populationIds.get(1));
        populationIds2.add(populationIds.get(2));
        List<PopulationInfo> populationInfos2 = populationService.getPopulationsByIds(populationIds2, contextInfo);
        assertEquals(2, populationInfos2.size());
        crudInfoTester.getIdEntityTester().checkListOfInfoAgainstListOfIds(populationInfos2, populationIds2);

        // test getPopulationIdsByType
        List<String> populationIdsByType = populationService.getPopulationIdsByType(PopulationServiceConstants.POPULATION_TYPE_KEY, contextInfo);
        assertEquals(5, populationIdsByType.size());
        assertTrue(CollectionUtils.isEqualCollection(populationIds, populationIdsByType));
        List<String> populationIdsByType2 = populationService.getPopulationIdsByType("INVALID", contextInfo);
        assertEquals(0, populationIdsByType2.size());

        // create some population rules
        String [] POPULATION_RULE_TYPES = {
                PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY,
                PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY,
                PopulationServiceConstants.POPULATION_RULE_TYPE_SEARCH_KEY,
                PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY,
                PopulationServiceConstants.POPULATION_RULE_TYPE_INTERSECTION_KEY,
                PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY
        };
        List<String> populationRuleIds = new ArrayList<String>();
        for (int i=0; i<POPULATION_RULE_TYPES.length; i++) {
            PopulationRuleInfo populationRuleInfo = new PopulationRuleInfo();
            crudInfoTester.initializeInfoForTestCreate(populationRuleInfo, POPULATION_RULE_TYPES[i], PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
            populationRuleInfo.setName("popRule" + i);
            populationRuleInfo = populationService.createPopulationRule(populationRuleInfo, contextInfo);
            populationRuleIds.add(populationRuleInfo.getId());
        }
        // test getPopulationRulesByIds
        List<PopulationRuleInfo> populationRuleInfos = populationService.getPopulationRulesByIds(populationRuleIds, contextInfo);
        assertEquals(POPULATION_RULE_TYPES.length, populationRuleInfos.size());
        crudInfoTester.getIdEntityTester().checkListOfInfoAgainstListOfIds(populationRuleInfos, populationRuleIds);
        List<String> populationRuleIds2 = new ArrayList<String>();
        populationRuleIds2.add(populationRuleIds.get(0));
        List<PopulationRuleInfo> populationRuleInfos2 = populationService.getPopulationRulesByIds(populationRuleIds2, contextInfo);
        assertEquals(1, populationRuleInfos2.size());
        crudInfoTester.getIdEntityTester().checkListOfInfoAgainstListOfIds(populationRuleInfos2, populationRuleIds2);

        // test getPopulationRuleIdsByType
        for (int i=0; i<POPULATION_RULE_TYPES.length; i++) {
            List<String> populationRuleIdsByType = populationService.getPopulationRuleIdsByType(POPULATION_RULE_TYPES[i], contextInfo);
            assertEquals(1, populationRuleIdsByType.size());
            assertEquals(populationRuleIdsByType.get(0), populationRuleIds.get(i));
        }
        List<String> populationRuleIdsByType2 = populationService.getPopulationRuleIdsByType("INVALID", contextInfo);
        assertEquals(0, populationRuleIdsByType2.size());

        // test getPopulationsForPopulationRule, applyPopulationRuleToPopulation, removePopulationRuleFromPopulation
        List<PopulationInfo> populationInfosForRule = populationService.getPopulationsForPopulationRule(populationRuleIds.get(0), contextInfo);
        assertEquals(0, populationInfosForRule.size());
        StatusInfo status_applyPopulationRuleToPopulation = populationService.applyPopulationRuleToPopulation(populationRuleIds.get(0), populationIds.get(0), contextInfo);
        assertNotNull(status_applyPopulationRuleToPopulation);
        populationInfosForRule = populationService.getPopulationsForPopulationRule(populationRuleIds.get(0), contextInfo);
        assertEquals(1, populationInfosForRule.size());
        status_applyPopulationRuleToPopulation = populationService.applyPopulationRuleToPopulation(populationRuleIds.get(0), populationIds.get(1), contextInfo); // add another population
        assertNotNull(status_applyPopulationRuleToPopulation);
        populationInfosForRule = populationService.getPopulationsForPopulationRule(populationRuleIds.get(0), contextInfo);
        assertEquals(2, populationInfosForRule.size());
        populationInfosForRule = populationService.getPopulationsForPopulationRule(populationRuleIds.get(1), contextInfo); // a rule with no population
        assertEquals(0, populationInfosForRule.size());
        status_applyPopulationRuleToPopulation = populationService.applyPopulationRuleToPopulation(populationRuleIds.get(1), populationIds.get(0), contextInfo); // change population's rule
        populationInfosForRule = populationService.getPopulationsForPopulationRule(populationRuleIds.get(0), contextInfo); // a rule now 1 population
        assertEquals(1, populationInfosForRule.size());
        populationInfosForRule = populationService.getPopulationsForPopulationRule(populationRuleIds.get(1), contextInfo); // a rule now 1 population
        assertEquals(1, populationInfosForRule.size());
        StatusInfo status_removePopulationRuleFromPopulation = populationService.removePopulationRuleFromPopulation(populationRuleIds.get(1), populationIds.get(0), contextInfo);
        populationInfosForRule = populationService.getPopulationsForPopulationRule(populationRuleIds.get(0), contextInfo); // a rule now 0 population
        assertEquals(1, populationInfosForRule.size());
        populationInfosForRule = populationService.getPopulationsForPopulationRule(populationRuleIds.get(1), contextInfo); // a rule now 1 population
        assertEquals(0, populationInfosForRule.size());

        // create some population categories
        List<String> populationCategIds = new ArrayList<String>();
        for (int i=0; i<3; i++) {
            PopulationCategoryInfo populationCategoryInfo = new PopulationCategoryInfo();
            crudInfoTester.initializeInfoForTestCreate(populationCategoryInfo, PopulationServiceConstants.POPULATION_CATEGORY_TYPE_KEY, PopulationServiceConstants.POPULATION_CATEGORY_ACTIVE_STATE_KEY);
            populationCategoryInfo.setName("popCateg" + i);
            populationCategoryInfo = populationService.createPopulationCategory(populationCategoryInfo.getTypeKey(), populationCategoryInfo, contextInfo);
            populationCategIds.add(populationCategoryInfo.getId());
        }
        // test getPopulationCategoriesByIds
        List<PopulationCategoryInfo> populationCategoryInfos = populationService.getPopulationCategoriesByIds(populationCategIds, contextInfo);
        assertEquals(3, populationCategoryInfos.size());
        crudInfoTester.getIdEntityTester().checkListOfInfoAgainstListOfIds(populationCategoryInfos, populationCategIds);
        List<String> populationCategIds2 = new ArrayList<String>();
        populationCategIds2.add(populationCategIds.get(0));
        populationCategIds2.add(populationCategIds.get(2));
        List<PopulationCategoryInfo> populationCategoryInfos2 = populationService.getPopulationCategoriesByIds(populationCategIds2, contextInfo);
        assertEquals(2, populationCategoryInfos2.size());
        crudInfoTester.getIdEntityTester().checkListOfInfoAgainstListOfIds(populationCategoryInfos2, populationCategIds2);

        // test getPopulationCategoryIdsByType
        List<String> populationCategoryIdsByType = populationService.getPopulationCategoryIdsByType(PopulationServiceConstants.POPULATION_CATEGORY_TYPE_KEY, contextInfo);
        assertEquals(3, populationCategoryIdsByType.size());
        assertTrue(CollectionUtils.isEqualCollection(populationCategIds, populationCategoryIdsByType));
        List<String> populationCategoryIdsByType2 = populationService.getPopulationCategoryIdsByType("INVALID", contextInfo);
        assertEquals(0, populationCategoryIdsByType2.size());

        // test getPopulationCategoriesForPopulation, addPopulationToPopulationCategory, removePopulationFromPopulationCategory
        List <PopulationCategoryInfo> popCategsForPop = populationService.getPopulationCategoriesForPopulation(populationIds.get(2), contextInfo); // no population categ assigned yet
        assertEquals(0, popCategsForPop.size());
        StatusInfo status_addPopulationToPopulationCategory = populationService.addPopulationToPopulationCategory(populationIds.get(2), populationCategIds.get(1), contextInfo);
        assertNotNull(status_addPopulationToPopulationCategory);
        popCategsForPop = populationService.getPopulationCategoriesForPopulation(populationIds.get(2), contextInfo); // 1 population categ assigned
        assertEquals(1, popCategsForPop.size());
        assertEquals(popCategsForPop.get(0).getId(),populationCategIds.get(1));
        status_addPopulationToPopulationCategory = populationService.addPopulationToPopulationCategory(populationIds.get(0), populationCategIds.get(0), contextInfo);
        assertNotNull(status_addPopulationToPopulationCategory);
        popCategsForPop = populationService.getPopulationCategoriesForPopulation(populationIds.get(2), contextInfo); // 1 population categ still assigned
        assertEquals(1, popCategsForPop.size());
        assertEquals(popCategsForPop.get(0).getId(),populationCategIds.get(1));
        popCategsForPop = populationService.getPopulationCategoriesForPopulation(populationIds.get(0), contextInfo); // 1 population categ assigned
        assertEquals(1, popCategsForPop.size());
        assertEquals(popCategsForPop.get(0).getId(),populationCategIds.get(0));
        StatusInfo status_removePopulationFromPopulationCategory = populationService.removePopulationFromPopulationCategory(populationIds.get(0), populationCategIds.get(0), contextInfo);
        assertNotNull(status_removePopulationFromPopulationCategory);
        popCategsForPop = populationService.getPopulationCategoriesForPopulation(populationIds.get(2), contextInfo); // 1 population categ still assigned
        assertEquals(1, popCategsForPop.size());
        assertEquals(popCategsForPop.get(0).getId(),populationCategIds.get(1));
        popCategsForPop = populationService.getPopulationCategoriesForPopulation(populationIds.get(0), contextInfo); // no population categ assigned
        assertEquals(0, popCategsForPop.size());
    }

}
