package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import junit.framework.Assert;
import org.junit.Ignore;
import org.kuali.student.enrollment.class2.courseoffering.dao.SeatPoolDefinitionDao;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import org.kuali.student.r2.core.type.dto.TypeInfo;
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

    // ============================================== TESTS ======================================================
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





}