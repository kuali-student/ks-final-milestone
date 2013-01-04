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
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:population-test-context.xml"})
public class TestPopulationServiceMockImpl {

    /////////////////////////////////
    // CONSTANTS
    /////////////////////////////////

    // some test data constants
    private static final List<String> POP_RULE_AGENDA_IDS_1 = new ArrayList<String>(
            Arrays.asList("7", "8", "6")
    );

    private static final List<String> POP_RULE_AGENDA_IDS_2 = new ArrayList<String>(
            Arrays.asList("1", "2", "3")
    );

    private static final List<String> POP_RULE_GROUP_IDS_1 = new ArrayList<String>(
            Arrays.asList("2", "4", "6", "8")
    );
    private static final List<String> POP_RULE_GROUP_IDS_2 = new ArrayList<String>(
            Arrays.asList("1", "3", "5", "7")
    );

    private static final List<String> POP_RULE_PERSON_IDS_1 = new ArrayList<String>(
            Arrays.asList("2", "4")
    );
    private static final List<String> POP_RULE_PERSON_IDS_2 = new ArrayList<String>(
            Arrays.asList("1", "3")
    );

    private static final List<String> POP_RULE_CHILD_POP_IDS_1 = new ArrayList<String>(
            Arrays.asList("2", "5")
    );
    private static final List<String> POP_RULE_CHILD_POP_IDS_2 = new ArrayList<String>(
            Arrays.asList("0", "5")
    );

    private static final String POP_RULE_REF_CHILD_POP_ID_1 = "99";
    private static final String POP_RULE_REF_CHILD_POP_ID_2 = "101";

    private static final List<String> POP_RULE_SORT_ORDER_TYPE_KEYS_1 = new ArrayList<String>(
            Arrays.asList("key1", "key2")
    );
    private static final List<String> POP_RULE_SORT_ORDER_TYPE_KEYS_2 = new ArrayList<String>(
            Arrays.asList("key3", "key4")
    );

    private static final boolean POP_RULE_VARIES_BY_TIME = true;
    private static final boolean POP_RULE_NO_SUPPORT_FOR_GET_MEMBERS = false;

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
        expected.setVariesByTime(!POP_RULE_VARIES_BY_TIME);
        expected.setSupportsGetMembers(!POP_RULE_NO_SUPPORT_FOR_GET_MEMBERS);
        PopulationInfo actual = populationService.createPopulation(expected, contextInfo);
        assertNotNull(actual.getId());
        crudInfoTester.testCreate(expected, actual);
        assertEquals(0, expected.getSortOrderTypeKeys().size()); // no sort order type key is initialized
        assertEquals(0, actual.getSortOrderTypeKeys().size());
        crudInfoTester.getListOfStringTester().check(expected.getSortOrderTypeKeys(), actual.getSortOrderTypeKeys()); // not really necessary as both are empty
        assertEquals(expected.getVariesByTime(), !POP_RULE_VARIES_BY_TIME);
        assertEquals(expected.getVariesByTime(), actual.getVariesByTime());
        assertEquals(expected.getSupportsGetMembers(), !POP_RULE_NO_SUPPORT_FOR_GET_MEMBERS);
        assertEquals(expected.getSupportsGetMembers(), actual.getSupportsGetMembers());

        // test read
        expected = actual;
        crudInfoTester.initializeInfoForTestRead(expected);
        actual = populationService.getPopulation(actual.getId(), contextInfo);
        crudInfoTester.testRead(expected, actual);

        // test update
        expected = actual;
        crudInfoTester.initializeInfoForTestUpdate(expected, PopulationServiceConstants.POPULATION_INACTIVE_STATE_KEY);
        actual = populationService.updatePopulation(expected.getId(), expected, contextInfo);
        crudInfoTester.testUpdate(expected, actual);
        assertEquals(0, expected.getSortOrderTypeKeys().size()); // no sort order type key is initialized
        assertEquals(0, actual.getSortOrderTypeKeys().size());
        crudInfoTester.getListOfStringTester().check(expected.getSortOrderTypeKeys(), actual.getSortOrderTypeKeys()); // not really necessary as both are empty
        assertEquals(expected.getVariesByTime(), !POP_RULE_VARIES_BY_TIME);
        assertEquals(expected.getVariesByTime(), actual.getVariesByTime());
        assertEquals(expected.getSupportsGetMembers(), !POP_RULE_NO_SUPPORT_FOR_GET_MEMBERS);
        assertEquals(expected.getSupportsGetMembers(), actual.getSupportsGetMembers());

        // test read again
        expected = actual;
        crudInfoTester.initializeInfoForTestRead(expected);
        actual = populationService.getPopulation(actual.getId(), contextInfo);
        crudInfoTester.testRead(expected, actual);
        assertEquals(0, expected.getSortOrderTypeKeys().size()); // no sort order type key is initialized
        assertEquals(0, actual.getSortOrderTypeKeys().size());
        crudInfoTester.getListOfStringTester().check(expected.getSortOrderTypeKeys(), actual.getSortOrderTypeKeys()); // not really necessary as both are empty
        assertEquals(expected.getVariesByTime(), !POP_RULE_VARIES_BY_TIME);
        assertEquals(expected.getVariesByTime(), actual.getVariesByTime());
        assertEquals(expected.getSupportsGetMembers(), !POP_RULE_NO_SUPPORT_FOR_GET_MEMBERS);
        assertEquals(expected.getSupportsGetMembers(), actual.getSupportsGetMembers());

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
        expected.setAgendaIds(POP_RULE_AGENDA_IDS_1);
        expected.setGroupIds(POP_RULE_GROUP_IDS_1);
        expected.setPersonIds(POP_RULE_PERSON_IDS_1);
        expected.setChildPopulationIds(POP_RULE_CHILD_POP_IDS_1);
        expected.setReferencePopulationId(POP_RULE_REF_CHILD_POP_ID_1);
        expected.setSortOrderTypeKeys(POP_RULE_SORT_ORDER_TYPE_KEYS_1);
        expected.setVariesByTime(POP_RULE_VARIES_BY_TIME);
        expected.setSupportsGetMembers(POP_RULE_NO_SUPPORT_FOR_GET_MEMBERS);
        PopulationRuleInfo actual = populationService.createPopulationRule(expected, contextInfo);
        assertNotNull(actual.getId());
        crudInfoTester.testCreate(expected, actual);
        crudInfoTester.getListOfStringTester().check(expected.getAgendaIds(), POP_RULE_AGENDA_IDS_1);
        crudInfoTester.getListOfStringTester().check(expected.getAgendaIds(), actual.getAgendaIds());
        crudInfoTester.getListOfStringTester().check(expected.getGroupIds(), POP_RULE_GROUP_IDS_1);
        crudInfoTester.getListOfStringTester().check(expected.getGroupIds(), actual.getGroupIds());
        crudInfoTester.getListOfStringTester().check(expected.getPersonIds(), POP_RULE_PERSON_IDS_1);
        crudInfoTester.getListOfStringTester().check(expected.getPersonIds(), actual.getPersonIds());
        crudInfoTester.getListOfStringTester().check(expected.getChildPopulationIds(), POP_RULE_CHILD_POP_IDS_1);
        crudInfoTester.getListOfStringTester().check(expected.getChildPopulationIds(), actual.getChildPopulationIds());
        assertEquals(POP_RULE_REF_CHILD_POP_ID_1, expected.getReferencePopulationId());
        assertEquals(expected.getReferencePopulationId(), actual.getReferencePopulationId());
        crudInfoTester.getListOfStringTester().check(expected.getSortOrderTypeKeys(), POP_RULE_SORT_ORDER_TYPE_KEYS_1);
        crudInfoTester.getListOfStringTester().check(expected.getSortOrderTypeKeys(), actual.getSortOrderTypeKeys());
        assertEquals(expected.getVariesByTime(), POP_RULE_VARIES_BY_TIME);
        assertEquals(expected.getVariesByTime(), actual.getVariesByTime());
        assertEquals(expected.getSupportsGetMembers(), POP_RULE_NO_SUPPORT_FOR_GET_MEMBERS);
        assertEquals(expected.getSupportsGetMembers(), actual.getSupportsGetMembers());

        // test read
        expected = actual;
        crudInfoTester.initializeInfoForTestRead(expected);
        actual = populationService.getPopulationRule(actual.getId(), contextInfo);
        crudInfoTester.testRead(expected, actual);
        crudInfoTester.getListOfStringTester().check(expected.getAgendaIds(), POP_RULE_AGENDA_IDS_1);
        crudInfoTester.getListOfStringTester().check(expected.getAgendaIds(), actual.getAgendaIds());
        crudInfoTester.getListOfStringTester().check(expected.getGroupIds(), POP_RULE_GROUP_IDS_1);
        crudInfoTester.getListOfStringTester().check(expected.getGroupIds(), actual.getGroupIds());
        crudInfoTester.getListOfStringTester().check(expected.getPersonIds(), POP_RULE_PERSON_IDS_1);
        crudInfoTester.getListOfStringTester().check(expected.getPersonIds(), actual.getPersonIds());
        crudInfoTester.getListOfStringTester().check(expected.getChildPopulationIds(), POP_RULE_CHILD_POP_IDS_1);
        crudInfoTester.getListOfStringTester().check(expected.getChildPopulationIds(), actual.getChildPopulationIds());
        assertEquals(POP_RULE_REF_CHILD_POP_ID_1, expected.getReferencePopulationId());
        assertEquals(expected.getReferencePopulationId(), actual.getReferencePopulationId());
        crudInfoTester.getListOfStringTester().check(expected.getSortOrderTypeKeys(), POP_RULE_SORT_ORDER_TYPE_KEYS_1);
        crudInfoTester.getListOfStringTester().check(expected.getSortOrderTypeKeys(), actual.getSortOrderTypeKeys());
        assertEquals(expected.getVariesByTime(), POP_RULE_VARIES_BY_TIME);
        assertEquals(expected.getVariesByTime(), actual.getVariesByTime());
        assertEquals(expected.getSupportsGetMembers(), POP_RULE_NO_SUPPORT_FOR_GET_MEMBERS);
        assertEquals(expected.getSupportsGetMembers(), actual.getSupportsGetMembers());

        // test update
        expected = actual;
        crudInfoTester.initializeInfoForTestUpdate(expected, PopulationServiceConstants.POPULATION_RULE_INACTIVE_STATE_KEY);
        expected.setAgendaIds(POP_RULE_AGENDA_IDS_2);
        expected.setGroupIds(POP_RULE_GROUP_IDS_2);
        expected.setPersonIds(POP_RULE_PERSON_IDS_2);
        expected.setChildPopulationIds(POP_RULE_CHILD_POP_IDS_2);
        expected.setReferencePopulationId(POP_RULE_REF_CHILD_POP_ID_2);
        expected.setSortOrderTypeKeys(POP_RULE_SORT_ORDER_TYPE_KEYS_2);
        expected.setVariesByTime(!POP_RULE_VARIES_BY_TIME);
        expected.setSupportsGetMembers(!POP_RULE_NO_SUPPORT_FOR_GET_MEMBERS);
        actual = populationService.updatePopulationRule(expected.getId(), expected, contextInfo);
        crudInfoTester.testUpdate(expected, actual);
        crudInfoTester.getListOfStringTester().check(expected.getAgendaIds(), POP_RULE_AGENDA_IDS_2);
        crudInfoTester.getListOfStringTester().check(expected.getAgendaIds(), actual.getAgendaIds());
        crudInfoTester.getListOfStringTester().check(expected.getGroupIds(), POP_RULE_GROUP_IDS_2);
        crudInfoTester.getListOfStringTester().check(expected.getGroupIds(), actual.getGroupIds());
        crudInfoTester.getListOfStringTester().check(expected.getPersonIds(), POP_RULE_PERSON_IDS_2);
        crudInfoTester.getListOfStringTester().check(expected.getPersonIds(), actual.getPersonIds());
        crudInfoTester.getListOfStringTester().check(expected.getChildPopulationIds(), POP_RULE_CHILD_POP_IDS_2);
        crudInfoTester.getListOfStringTester().check(expected.getChildPopulationIds(), actual.getChildPopulationIds());
        assertEquals(POP_RULE_REF_CHILD_POP_ID_2, expected.getReferencePopulationId());
        assertEquals(expected.getReferencePopulationId(), actual.getReferencePopulationId());
        crudInfoTester.getListOfStringTester().check(expected.getSortOrderTypeKeys(), POP_RULE_SORT_ORDER_TYPE_KEYS_2);
        crudInfoTester.getListOfStringTester().check(expected.getSortOrderTypeKeys(), actual.getSortOrderTypeKeys());
        assertEquals(expected.getVariesByTime(), !POP_RULE_VARIES_BY_TIME);
        assertEquals(expected.getVariesByTime(), actual.getVariesByTime());
        assertEquals(expected.getSupportsGetMembers(), !POP_RULE_NO_SUPPORT_FOR_GET_MEMBERS);
        assertEquals(expected.getSupportsGetMembers(), actual.getSupportsGetMembers());

        // test read again
        expected = actual;
        crudInfoTester.initializeInfoForTestRead(expected);
        actual = populationService.getPopulationRule(actual.getId(), contextInfo);
        crudInfoTester.testRead(expected, actual);
        crudInfoTester.getListOfStringTester().check(expected.getAgendaIds(), POP_RULE_AGENDA_IDS_2);
        crudInfoTester.getListOfStringTester().check(expected.getAgendaIds(), actual.getAgendaIds());
        crudInfoTester.getListOfStringTester().check(expected.getGroupIds(), POP_RULE_GROUP_IDS_2);
        crudInfoTester.getListOfStringTester().check(expected.getGroupIds(), actual.getGroupIds());
        crudInfoTester.getListOfStringTester().check(expected.getPersonIds(), POP_RULE_PERSON_IDS_2);
        crudInfoTester.getListOfStringTester().check(expected.getPersonIds(), actual.getPersonIds());
        crudInfoTester.getListOfStringTester().check(expected.getChildPopulationIds(), POP_RULE_CHILD_POP_IDS_2);
        crudInfoTester.getListOfStringTester().check(expected.getChildPopulationIds(), actual.getChildPopulationIds());
        assertEquals(POP_RULE_REF_CHILD_POP_ID_2, expected.getReferencePopulationId());
        assertEquals(expected.getReferencePopulationId(), actual.getReferencePopulationId());
        crudInfoTester.getListOfStringTester().check(expected.getSortOrderTypeKeys(), POP_RULE_SORT_ORDER_TYPE_KEYS_2);
        crudInfoTester.getListOfStringTester().check(expected.getSortOrderTypeKeys(), actual.getSortOrderTypeKeys());
        assertEquals(expected.getVariesByTime(), !POP_RULE_VARIES_BY_TIME);
        assertEquals(expected.getVariesByTime(), actual.getVariesByTime());
        assertEquals(expected.getSupportsGetMembers(), !POP_RULE_NO_SUPPORT_FOR_GET_MEMBERS);
        assertEquals(expected.getSupportsGetMembers(), actual.getSupportsGetMembers());

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
            populationRuleInfo.setSortOrderTypeKeys(POP_RULE_SORT_ORDER_TYPE_KEYS_1);
            populationRuleInfo.setVariesByTime(POP_RULE_VARIES_BY_TIME);
            populationRuleInfo.setSupportsGetMembers(POP_RULE_NO_SUPPORT_FOR_GET_MEMBERS);
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
        for (PopulationInfo info : populationInfosForRule) {
            // check that the sort order type, varies by time and supports member is now that of the population rule
            crudInfoTester.getListOfStringTester().check(info.getSortOrderTypeKeys(), POP_RULE_SORT_ORDER_TYPE_KEYS_1);
            crudInfoTester.getListOfStringTester().check(info.getSortOrderTypeKeys(), populationRuleInfos.get(0).getSortOrderTypeKeys());
            assertEquals(info.getVariesByTime(), POP_RULE_VARIES_BY_TIME);
            assertEquals(info.getVariesByTime(), populationRuleInfos.get(0).getVariesByTime());
            assertEquals(info.getSupportsGetMembers(), POP_RULE_NO_SUPPORT_FOR_GET_MEMBERS);
            assertEquals(info.getSupportsGetMembers(), populationRuleInfos.get(0).getSupportsGetMembers());
        }

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
