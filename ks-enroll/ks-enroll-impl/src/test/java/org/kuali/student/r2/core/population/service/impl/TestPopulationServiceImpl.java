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
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import junit.framework.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.format;

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

    private PopulationRuleInfo _createExclusionPopulationRuleInfo() {
        PopulationRuleInfo populationRuleInfo = new PopulationRuleInfo();
        populationRuleInfo.setName("TestPopRule");
        RichTextInfo richTextInfo = new RichTextInfo();
        richTextInfo.setPlain("rule-plain");
        richTextInfo.setFormatted("rule-formatted");
        populationRuleInfo.setDescr(richTextInfo);
        populationRuleInfo.setStateKey(PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
        populationRuleInfo.setTypeKey(PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY);
        return populationRuleInfo;
    }

    private PopulationInfo _createPopulationInfo(Integer val) {
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
        populationInfo.setTypeKey(PopulationServiceConstants.POPULATION_TYPE_KEY);
        return populationInfo;
    }

    private List<PopulationInfo> _createPopulationList() {
        PopulationInfo ref = _createPopulationInfo(2);
        PopulationInfo three = _createPopulationInfo(3);
        PopulationInfo four = _createPopulationInfo(4);
        PopulationInfo five = _createPopulationInfo(5);
        List<PopulationInfo> popList = new ArrayList<PopulationInfo>();
        popList.add(ref);
        popList.add(three);
        popList.add(four);
        popList.add(five);
        return popList;
    }

    private List<String> _makePopulationsAndReturnIds() {
        List<PopulationInfo> popList = _createPopulationList();
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

    private void checkSameChildIds(List<String> idsOne, List<String> idsTwo) {
        for (String childId: idsOne) {
            assert(idsTwo.contains(childId));
        }
        for (String childId: idsTwo) {
            assert(idsOne.contains(childId));
        }
    }

    @Test
    public void testPopulationRuleCreateGet() {
        before();
        List<PopulationInfo> popList = _createPopulationList();
        try {
            // Create ref population and child population IDs
            PopulationInfo refCreated = populationService.createPopulation(popList.get(0), contextInfo);
            PopulationInfo threeCreated = populationService.createPopulation(popList.get(1), contextInfo);
            PopulationInfo fourCreated = populationService.createPopulation(popList.get(2), contextInfo);
            // Now the pop rule
            PopulationRuleInfo ruleInfo = _createExclusionPopulationRuleInfo();
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
            checkSameChildIds(ruleInfo.getChildPopulationIds(), ruleInfoFetched.getChildPopulationIds());
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    public void testUpdateDeleteAddChildPop() {
        before();
        List<String> popIds = _makePopulationsAndReturnIds();
        try {
            PopulationRuleInfo ruleInfo = _createExclusionPopulationRuleInfo();
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
            checkSameChildIds(ruleInfo.getChildPopulationIds(), ruleInfoFetched.getChildPopulationIds());
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
            checkSameChildIds(childIds, updatedFetchedIds);
        } catch (Exception e) {
            assert(false);
        }
    }
    @Test
    public void testPopulationCreateGet() {
        before();
        PopulationInfo info = _createPopulationInfo(null);
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
        PopulationInfo info = _createPopulationInfo(null);
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
        PopulationInfo info = _createPopulationInfo(null);
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


}
