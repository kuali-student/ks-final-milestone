package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import junit.framework.Assert;
import org.junit.Ignore;
import org.kuali.student.enrollment.class2.courseoffering.dao.SeatPoolDefinitionDao;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.infc.Population;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


/*
 * This class was used to test the class1 backed implementation of CourseOfferingService for CourseOffering, FormatOffering and ActivityOffering.
 *
 * For M4 it has been refactored.  Most of the test are now in TestCourseOfferingServiceMockImpl and only db dependent tests go here.
 *
 * See TestLprServiceImpl for an example.
 *
 * Once the tests can be run this should be unignored.
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingServiceImplM4 {
    @Resource
    private CourseOfferingService coServiceImpl;
    @Resource
    private PopulationService populationService;
    private SeatPoolDefinitionDao seatPoolDefinitionDao;
    private ContextInfo contextInfo;

    private void before() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("admin");
    }

    private SeatPoolDefinitionInfo _constructSeatPoolDefinitionInfoById (Integer val) {
        String extension = "";
        if (val != null) {
            extension += val;
        }
        SeatPoolDefinitionInfo seatPoolDefinitionInfo = new SeatPoolDefinitionInfo();
        seatPoolDefinitionInfo.setName("TestSeatPoolDefinitionInfo-Id" + extension);
        seatPoolDefinitionInfo.setStateKey("TestSeatPoolDefinitionInfo-StateKey1" + extension);
        seatPoolDefinitionInfo.setTypeKey("TestSeatPoolDefinitionInfo-TypeKey1" + extension);
        seatPoolDefinitionInfo.setExpirationMilestoneTypeKey("TestSeatPoolDefinitionInfo-MilestoneKey1" + extension);
        seatPoolDefinitionInfo.setIsPercentage(false);
        seatPoolDefinitionInfo.setSeatLimit(50);
        seatPoolDefinitionInfo.setProcessingPriority(3);
        return seatPoolDefinitionInfo;
    }

    private List<SeatPoolDefinitionInfo> _constructSeatPoolDefinitionInfoByIdList() {
        SeatPoolDefinitionInfo ref = _constructSeatPoolDefinitionInfoById(2);
        SeatPoolDefinitionInfo three = _constructSeatPoolDefinitionInfoById(3);
        SeatPoolDefinitionInfo four = _constructSeatPoolDefinitionInfoById(4);
        SeatPoolDefinitionInfo five = _constructSeatPoolDefinitionInfoById(5);
        List<SeatPoolDefinitionInfo> poolList = new ArrayList<SeatPoolDefinitionInfo>();
        poolList.add(ref);
        poolList.add(three);
        poolList.add(four);
        poolList.add(five);
        return poolList;
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

    private PopulationRuleInfo _constructExclusionPopulationRuleInfo() {
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

    // ============================================== TESTS ======================================================
    @Test
    public void testPopulation() {
        before();
        List<PopulationInfo> popList = _constructPopulationList();
        try {
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
            PopulationInfo combined = populationService.createPopulation(popList.get(3), contextInfo);
            populationService.applyPopulationRuleToPopulation(ruleInfoFetched.getId(), combined.getId(), contextInfo);
            SeatPoolDefinitionInfo info = _constructSeatPoolDefinitionInfoById(null);
            info.setPopulationId(combined.getId());
            SeatPoolDefinitionInfo created = coServiceImpl.createSeatPoolDefinition(info, contextInfo);
            PopulationInfo retrieved = populationService.getPopulation(created.getPopulationId(), contextInfo);
            assertEquals(combined.getId(), retrieved.getId());
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void testCreateSeatPoolDefinitionGet() {
        before();
        SeatPoolDefinitionInfo info = _constructSeatPoolDefinitionInfoById(null);
        try {
            SeatPoolDefinitionInfo created = coServiceImpl.createSeatPoolDefinition(info, contextInfo);
            SeatPoolDefinitionInfo fetched = coServiceImpl.getSeatPoolDefinition(created.getId(), contextInfo);
            Assert.assertEquals(info.getName(), fetched.getName());
            Assert.assertEquals(info.getStateKey(), fetched.getStateKey());
            Assert.assertEquals(info.getTypeKey(), fetched.getTypeKey());
            Assert.assertEquals(info.getExpirationMilestoneTypeKey(), fetched.getExpirationMilestoneTypeKey());
            Assert.assertEquals(info.getIsPercentage(), fetched.getIsPercentage());
            Assert.assertEquals(info.getSeatLimit(), fetched.getSeatLimit());
            Assert.assertEquals(info.getProcessingPriority(), fetched.getProcessingPriority());
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void testCreateSeatPoolDefinitionUpdateDelete() {
        before();
        SeatPoolDefinitionInfo info = _constructSeatPoolDefinitionInfoById(null);
        try {
            SeatPoolDefinitionInfo created = coServiceImpl.createSeatPoolDefinition(info, contextInfo);
            SeatPoolDefinitionInfo fetched = coServiceImpl.getSeatPoolDefinition(created.getId(), contextInfo);
            fetched.setSeatLimit(5);
            fetched.setExpirationMilestoneTypeKey(AtpServiceConstants.MILESTONE_SEATPOOL_FIRST_DAY_OF_CLASSES_TYPE_KEY);
            coServiceImpl.updateSeatPoolDefinition(fetched.getId(), fetched, contextInfo);
            SeatPoolDefinitionInfo fetched2 = coServiceImpl.getSeatPoolDefinition(created.getId(), contextInfo);
            assertEquals(new Integer(5), fetched2.getSeatLimit());
            assertEquals(AtpServiceConstants.MILESTONE_SEATPOOL_FIRST_DAY_OF_CLASSES_TYPE_KEY, fetched2.getExpirationMilestoneTypeKey());
            coServiceImpl.deleteSeatPoolDefinition(fetched.getId(), contextInfo);
            boolean found = true;
            try {
                coServiceImpl.getSeatPoolDefinition(fetched.getId(), contextInfo);
            } catch (DoesNotExistException e) {
                found = false;
            }
            if (found) {
                assert(false); // Exception should have been thrown
            }
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }
}