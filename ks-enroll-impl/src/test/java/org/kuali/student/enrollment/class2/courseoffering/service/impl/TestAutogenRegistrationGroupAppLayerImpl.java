/*
 * Copyright 2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.spring.log4j.KSLog4JConfigurer;
import org.kuali.student.enrollment.class2.courseoffering.service.applayer.AutogenRegistrationGroupAppLayer;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A test case for the autogen rg app layer helper.
 * 
 * @author Kuali Student Team 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-autogen-rg-test-context.xml"})
public class TestAutogenRegistrationGroupAppLayerImpl {
    private static final Logger log = KSLog4JConfigurer
            .getLogger(TestAutogenRegistrationGroupAppLayerImpl.class);

    @Resource (name="CourseOfferingService")
    protected CourseOfferingService coService;

    @Resource
    protected CourseService courseService;

    @Resource
    protected CourseOfferingServiceTestDataLoader dataLoader;

    @Resource(name = "LrcService")
    protected LRCService lrcService;

    
    @Resource
    private AutogenRegistrationGroupAppLayer appLayer;

    private ContextInfo contextInfo;
    
    /**
     * 
     */
    public TestAutogenRegistrationGroupAppLayerImpl() {
    }
    
    @Before
    public void beforeTest() throws Exception {
        dataLoader.beforeTest();
        
        contextInfo = new ContextInfo();
    
        contextInfo.setCurrentDate(new Date());
        
        contextInfo.setPrincipalId("test");
        contextInfo.setAuthenticatedPrincipalId("test");
    }
    
    @After
    public void afterTest() throws Exception {
        dataLoader.afterTest();
    }
    
    @Test
    public void testUserStoryEight () throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException {
        
        // create a default cluster
        List<ActivityOfferingInfo> activities = coService.getActivityOfferingsByFormatOffering(CourseOfferingServiceTestDataLoader.CHEM123_LEC_AND_LAB_FORMAT_OFFERING_ID, contextInfo);

        ActivityOfferingClusterInfo defaultAoc = CourseOfferingServiceTestDataUtils.createActivityOfferingCluster(CourseOfferingServiceTestDataLoader.CHEM123_LEC_AND_LAB_FORMAT_OFFERING_ID, "Default Cluster", activities);

        defaultAoc = coService.createActivityOfferingCluster(CourseOfferingServiceTestDataLoader.CHEM123_LEC_AND_LAB_FORMAT_OFFERING_ID, CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, defaultAoc, contextInfo);

        // Create a Registration Group
        List<BulkStatusInfo> generatedStatus = coService.generateRegistrationGroupsForCluster(defaultAoc.getId(), contextInfo);
        
        Assert.assertEquals(6,  generatedStatus.size());
        
        
        Integer aocSeatCount = appLayer.getSeatCountByActivityOfferingCluster(defaultAoc.getId(), contextInfo);
        
        Assert.assertNotNull(aocSeatCount);
        Assert.assertEquals(150, aocSeatCount.intValue());

        // need to test a case where the cap is applied.
        
        // 100 seat lecture, 200 seat lecture, 50 seat lab, 75 seat lab.
        // 100, 50
        // 100, 75 100
        // 200, 50
        // 200, 75 125 = 225
        
        /*
         * max (ao.maxEnrollment) for each activity type.  200 + 75 = 275
         * 
         * sum (rg seats) = 250 
         * 
         * It seems like we need some kind of ao aware test
         * 
         * 1
         */
        
        // need to test 2 reg groups
        
        // need to test 4 reg groups
        
        // need to test 16 reg groups.
        
        
        
    }
}
