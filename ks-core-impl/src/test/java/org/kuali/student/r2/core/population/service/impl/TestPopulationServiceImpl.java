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
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

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

    private PopulationRuleInfo _constructPopulationRuleInfoByType(String popRuleType, Integer n) {
        PopulationRuleInfo populationRuleInfo = new PopulationRuleInfo();
        populationRuleInfo.setName("TestPopRule-" + n);
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("rule-plain-" + n);
        richTextInfo.setFormatted("rule-formatted-" + n);
        populationRuleInfo.setDescr(richTextInfo);
        populationRuleInfo.setStateKey(PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
        populationRuleInfo.setTypeKey(popRuleType);
        populationRuleInfo.setVariesByTime(false);
        populationRuleInfo.setSupportsGetMembers(false);
        return populationRuleInfo;
    }

    private PopulationRuleInfo _constructExclusionPopulationRuleInfo() {
        PopulationRuleInfo populationRuleInfo = new PopulationRuleInfo();
        populationRuleInfo.setName("TestPopRule");
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("rule-plain");
        richTextInfo.setFormatted("rule-formatted");
        populationRuleInfo.setDescr(richTextInfo);
        populationRuleInfo.setStateKey(PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
        populationRuleInfo.setTypeKey(PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY);
        populationRuleInfo.setVariesByTime(false);
        populationRuleInfo.setSupportsGetMembers(false);
        return populationRuleInfo;
    }

    private PopulationInfo _constructPopulationInfo(Integer val) {
        String extension = "";
        if (val != null) {
            extension += val;
        }
        PopulationInfo populationInfo = new PopulationInfo();
        populationInfo.setName("TestPop" + extension);
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("plain" + extension);
        richTextInfo.setFormatted("formatted" + extension);
        populationInfo.setDescr(richTextInfo);
        populationInfo.setStateKey(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
        populationInfo.setTypeKey(PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY);
        return populationInfo;
    }

    private List<PopulationInfo> _constructPopulationList() {
        PopulationInfo ref = _constructPopulationInfo(2);
        PopulationInfo three = _constructPopulationInfo(3);
        PopulationInfo four = _constructPopulationInfo(4);
        PopulationInfo five = _constructPopulationInfo(5);
        List<PopulationInfo> popList = new ArrayList<PopulationInfo>();
        popList.add(ref);
        popList.add(three);
        popList.add(four);
        popList.add(five);
        return popList;
    }

    private List<String> _makePopulationsAndReturnIds() {
        List<PopulationInfo> popList = _constructPopulationList();
        List<String> popIdsList = new ArrayList<String>();
        try {
            for (PopulationInfo popInfo: popList) {
                PopulationInfo created = populationService.createPopulation(popInfo, contextInfo);
                popIdsList.add(created.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return popIdsList;
    }

    private void checkSameIds(List<String> idsOne, List<String> idsTwo) {
        for (String id: idsOne) {
            assert(idsTwo.contains(id));
        }
        for (String id: idsTwo) {
            assert(idsOne.contains(id));
        }
    }
    // ============================================== TESTS ======================================================
    @Test
    public void testGetPopulationRuleForPopulation() {
        before();
        List<PopulationInfo> popList = _constructPopulationList();
        try {
            // Create ref population and child population IDs
            PopulationInfo refCreated = populationService.createPopulation(popList.get(0), contextInfo);
            PopulationInfo threeCreated = populationService.createPopulation(popList.get(1), contextInfo);
            PopulationInfo fourCreated = populationService.createPopulation(popList.get(2), contextInfo);
            PopulationRuleInfo ruleInfo = _constructPopulationRuleInfoByType(PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY, 4);
            List<String> childPopIds = new ArrayList<String>();
            childPopIds.add(threeCreated.getId());
            childPopIds.add(fourCreated.getId());
            ruleInfo.setChildPopulationIds(childPopIds);
            PopulationRuleInfo ruleInfoCreated = populationService.createPopulationRule(ruleInfo, contextInfo);
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void testGetPopulationsForPopulationRule() {
        before();
        List<PopulationInfo> popList = _constructPopulationList();
        try {
            // Create ref population and child population IDs
            PopulationInfo twoCreated = populationService.createPopulation(popList.get(0), contextInfo);
            PopulationInfo threeCreated = populationService.createPopulation(popList.get(1), contextInfo);
            PopulationInfo fourCreated = populationService.createPopulation(popList.get(2), contextInfo);
            // Now the pop rule
            PopulationRuleInfo ruleInfo = _constructPopulationRuleInfoByType(PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY, 2);
            List<String> children = new ArrayList<String>();
            children.add(twoCreated.getId());
            ruleInfo.setChildPopulationIds(children);
            PopulationRuleInfo ruleInfoCreated = populationService.createPopulationRule(ruleInfo, contextInfo);
            // Now apply this rule to the other populations
            populationService.applyPopulationRuleToPopulation(ruleInfoCreated.getId(),  threeCreated.getId(), contextInfo);
            populationService.applyPopulationRuleToPopulation(ruleInfoCreated.getId(),  fourCreated.getId(), contextInfo);
            // Create list of these IDs
            List<String> appliedPopIds = new ArrayList<String>();
            appliedPopIds.add(threeCreated.getId());
            appliedPopIds.add(fourCreated.getId());
            // And fetch it
            List<PopulationInfo> popInfos =
                    populationService.getPopulationsForPopulationRule(ruleInfoCreated.getId(), contextInfo);
            assertEquals(2, popInfos.size());
            // Make sure IDs are different
            assert(!popInfos.get(0).getId().equals(popInfos.get(1).getId()));
            // Check the ids that came back are the ones we applied it to
            for (PopulationInfo info: popInfos) {
                String infoId = info.getId();
                assert(appliedPopIds.contains(infoId));
            }
            // Now remove one of them
            populationService.removePopulationRuleFromPopulation(ruleInfoCreated.getId(), threeCreated.getId(), contextInfo);
            // Fetch again
            popInfos = populationService.getPopulationsForPopulationRule(ruleInfoCreated.getId(), contextInfo);
            assertEquals(1, popInfos.size());
            assertEquals(fourCreated.getId(), popInfos.get(0).getId());
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void testGetPopulationRuleIdsByType() {
        before();
        List<PopulationRuleInfo> ruleInfos = new ArrayList<PopulationRuleInfo>();
        PopulationRuleInfo one = _constructPopulationRuleInfoByType(PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY, 1);
        ruleInfos.add(one);
        PopulationRuleInfo two = _constructPopulationRuleInfoByType(PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY, 2);
        ruleInfos.add(two);
        PopulationRuleInfo three = _constructPopulationRuleInfoByType(PopulationServiceConstants.POPULATION_RULE_TYPE_INTERSECTION_KEY, 3);
        ruleInfos.add(three);
        try {
            List<String> popRuleIds = new ArrayList<String>();
            for (PopulationRuleInfo info: ruleInfos) {
                PopulationRuleInfo result = populationService.createPopulationRule(info, contextInfo);
                popRuleIds.add(result.getId());
            }
            // Check union types
            List<String> unionIds = popRuleIds.subList(0, 2);
            assertEquals(2, unionIds.size());
            List<String> popRuleIdsFetched =
                    populationService.getPopulationRuleIdsByType(PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY, contextInfo);
            assertEquals(2, popRuleIdsFetched.size());
            checkSameIds(unionIds, popRuleIdsFetched);
            // Check intersection types
            List<String> intersectionIds = popRuleIds.subList(2, 3);
            assertEquals(1, intersectionIds.size());
            popRuleIdsFetched =
                    populationService.getPopulationRuleIdsByType(PopulationServiceConstants.POPULATION_RULE_TYPE_INTERSECTION_KEY, contextInfo);
            assertEquals(1, popRuleIdsFetched.size());
            checkSameIds(intersectionIds, popRuleIdsFetched);
            // Check exclusion types
            popRuleIdsFetched =
                    populationService.getPopulationRuleIdsByType(PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY, contextInfo);
            assert(popRuleIdsFetched.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void testPopulationRuleCreateGet() {
        before();
        List<PopulationInfo> popList = _constructPopulationList();
        try {
            // Create ref population and child population IDs
            PopulationInfo refCreated = populationService.createPopulation(popList.get(0), contextInfo);
            PopulationInfo threeCreated = populationService.createPopulation(popList.get(1), contextInfo);
            PopulationInfo fourCreated = populationService.createPopulation(popList.get(2), contextInfo);
            // Now the pop rule
            PopulationRuleInfo ruleInfo = _constructExclusionPopulationRuleInfo();
            ruleInfo.setReferencePopulationId(refCreated.getId());
            List<String> childIds = new ArrayList<String>();
            childIds.add(threeCreated.getId());
            childIds.add(fourCreated.getId());
            ruleInfo.setChildPopulationIds(childIds);
            // Create the rule info
            PopulationRuleInfo ruleInfoCreated = populationService.createPopulationRule(ruleInfo, contextInfo);
            // Fetch it
            PopulationRuleInfo ruleInfoFetched = populationService.getPopulationRule(ruleInfoCreated.getId(), contextInfo);
            assertEquals(ruleInfo.getName(), ruleInfoFetched.getName());
            assertEquals(ruleInfo.getDescr().getPlain(), ruleInfoFetched.getDescr().getPlain());
            assertEquals(ruleInfo.getDescr().getFormatted(), ruleInfoFetched.getDescr().getFormatted());
            assertEquals(ruleInfo.getStateKey(), ruleInfoFetched.getStateKey());
            assertEquals(ruleInfo.getTypeKey(), ruleInfoFetched.getTypeKey());
            // Check ref population
            assertNotNull(ruleInfo.getReferencePopulationId());
            assertEquals(ruleInfo.getReferencePopulationId(), ruleInfoFetched.getReferencePopulationId());
            // Check child populations are same
            checkSameIds(ruleInfo.getChildPopulationIds(), ruleInfoFetched.getChildPopulationIds());
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    public void testPopulationRuleUpdateDeleteAddChildPop() {
        // Checks for CRUD too
        // This tests to see if changing the child populations and updating is working
        before();
        List<String> popIds = _makePopulationsAndReturnIds();
        try {
            PopulationRuleInfo ruleInfo = _constructExclusionPopulationRuleInfo();
            ruleInfo.setReferencePopulationId(popIds.get(0));
            List<String> childIds = new ArrayList<String>();
            childIds.add(popIds.get(1));
            childIds.add(popIds.get(2));
            ruleInfo.setChildPopulationIds(childIds);
            // Create the rule info
            PopulationRuleInfo ruleInfoCreated = populationService.createPopulationRule(ruleInfo, contextInfo);
            // Fetch it
            PopulationRuleInfo ruleInfoFetched = populationService.getPopulationRule(ruleInfoCreated.getId(), contextInfo);
            // Now check if they have the same child Ids
            checkSameIds(ruleInfo.getChildPopulationIds(), ruleInfoFetched.getChildPopulationIds());
            // Now change child ids
            childIds = new ArrayList<String>();
            childIds.add(popIds.get(2));
            childIds.add(popIds.get(3));
            ruleInfoFetched.setChildPopulationIds(childIds);
            // Update it
            PopulationRuleInfo x = populationService.updatePopulationRule(ruleInfoFetched.getId(), ruleInfoFetched, contextInfo);
            PopulationRuleInfo updatedFetched = populationService.getPopulationRule(ruleInfoCreated.getId(), contextInfo);
            // Now check if they have the same child Ids
            List<String> updatedFetchedIds = updatedFetched.getChildPopulationIds();
            checkSameIds(childIds, updatedFetchedIds);
            // Test delete
            populationService.deletePopulationRule(ruleInfoCreated.getId(), contextInfo);
            // Now try to get
            boolean exceptionThrown = false;
            try {
                populationService.getPopulation(ruleInfoCreated.getId(), contextInfo);
            } catch (DoesNotExistException e) {
                exceptionThrown = true;  // Should throw exception
            }
            if (!exceptionThrown) {
                assert(false);
            }
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    public void testPopulationCreateGet() {
        before();
        PopulationInfo info = _constructPopulationInfo(null);
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

    @Test
    public void testPopulationCreateUpdateGet() {
        before();
        PopulationInfo info = _constructPopulationInfo(null);
        try {
            String name = "TestPop2";
            String plain = "plain2";
            String formatted = "formatted2";
            PopulationInfo created = populationService.createPopulation(info, contextInfo);
            created.setName(name);
            RichTextInfo richTextInfo = new RichTextInfo();
            richTextInfo.setPlain(plain);
            richTextInfo.setFormatted(formatted);
            created.setDescr(richTextInfo);
            created.setStateKey(PopulationServiceConstants.POPULATION_INACTIVE_STATE_KEY);
            populationService.updatePopulation(created.getId(), created, contextInfo);
            PopulationInfo fetched = populationService.getPopulation(created.getId(), contextInfo);
            assertEquals(name, fetched.getName());
            assertEquals(plain, fetched.getDescr().getPlain());
            assertEquals(formatted, fetched.getDescr().getFormatted());
            assertEquals(PopulationServiceConstants.POPULATION_INACTIVE_STATE_KEY, fetched.getStateKey());
            assertEquals(info.getTypeKey(), fetched.getTypeKey());
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void testPopulationCreateUpdateGetDelete() {
        before();
        PopulationInfo info = _constructPopulationInfo(null);
        try {
            String name = "TestPop2";
            String plain = "plain2";
            String formatted = "formatted2";
            PopulationInfo created = populationService.createPopulation(info, contextInfo);
            created.setName(name);
            RichTextInfo richTextInfo = new RichTextInfo();
            richTextInfo.setPlain(plain);
            richTextInfo.setFormatted(formatted);
            created.setDescr(richTextInfo);
            created.setStateKey(PopulationServiceConstants.POPULATION_INACTIVE_STATE_KEY);
            populationService.updatePopulation(created.getId(), created, contextInfo);
            PopulationInfo fetched = populationService.getPopulation(created.getId(), contextInfo);
            assertEquals(name, fetched.getName());
            assertEquals(plain, fetched.getDescr().getPlain());
            assertEquals(formatted, fetched.getDescr().getFormatted());
            assertEquals(PopulationServiceConstants.POPULATION_INACTIVE_STATE_KEY, fetched.getStateKey());
            assertEquals(info.getTypeKey(), fetched.getTypeKey());
            populationService.deletePopulation(created.getId(), contextInfo);
            boolean exceptionThrown = false;
            try {
                populationService.getPopulation(created.getId(), contextInfo);
            } catch (DoesNotExistException e) {
                assert(true);
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                assert(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void testSearchForPopulations() {
        before();
        PopulationInfo info = _constructPopulationInfo(null);

        try {
            // Create ref population and child population IDs
            PopulationInfo infoCreated = populationService.createPopulation(info, contextInfo);

            // Fetch it
            PopulationInfo refFetched = populationService.getPopulation(infoCreated.getId(), contextInfo);

            // Testing search
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.like("name", "Test%")));
            QueryByCriteria criteria = qbcBuilder.build();

            List<PopulationInfo> infos = populationService.searchForPopulations(criteria, contextInfo);
            PopulationInfo infoTest = infos.get(0);

            assertEquals(infos.size(), 1);
            assertEquals(infoCreated.getName(), infoTest.getName());
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    public void testSearchForPopulationRules() {
        before();
        PopulationRuleInfo ruleInfo = _constructPopulationRuleInfoByType(PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY, 4);

        try {
            // Create ref population and child population IDs
            PopulationRuleInfo ruleInfoCreated = populationService.createPopulationRule(ruleInfo, contextInfo);

            // Testing search
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.like("name", "TestPop%")));
            QueryByCriteria criteria = qbcBuilder.build();

            List<PopulationRuleInfo> rules = populationService.searchForPopulationRules(criteria, contextInfo);
            PopulationRuleInfo ruleInfoTest = rules.get(0);

            assertEquals(rules.size(), 1);
            assertEquals(ruleInfoCreated.getName(), ruleInfoTest.getName());
        } catch (Exception e) {
            assert(false);
        }
    }

}
