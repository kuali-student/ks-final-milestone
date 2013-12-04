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

import org.junit.Test;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.enrollment.class2.courseoffering.service.facade.AutogenCount;
import org.kuali.student.enrollment.class2.courseoffering.service.facade.CourseOfferingServiceFacade;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.search.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * Search Service only works when there is a JPA context so this test
 * 
 * creates the necessary context to allow us to test things
 * 
 * @author Kuali Student Team 
 * 
 *
 */
@ContextConfiguration(locations = {"classpath:co-autogen-rg-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingServiceFacadeSearchService extends
        TestCourseOfferingServiceImplM4 {
    private static final Logger log = LoggerFactory
            .getLogger(TestCourseOfferingServiceFacadeSearchService.class);

    @Resource (name="SearchService")
    private SearchService searchService;
    
    @Resource
    private CourseOfferingServiceFacade courseOfferingServiceFacade;

    @Resource
    private EntityManager entityManager;

    /**
     * 
     */
    public TestCourseOfferingServiceFacadeSearchService() {
        // TODO Auto-generated constructor stub
    }

    @Test
    public void testGetAoIdAndAoTypeByFO() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException {

        before();

        //This below given expected output is taken by executing CourseOfferingServiceImpl.getActivityOfferingsByFormatOffering separately.
        String formatOfferingId = "Lui-6";
        String str[] = {"Lui-2-->kuali.lui.type.activity.offering.lecture","Lui-5-->kuali.lui.type.activity.offering.lab","Lui-8-->kuali.lui.type.activity.offering.lecture"};
        List<String> expectedList= Arrays.asList(str);

        List<KeyValue> aoKVList = courseOfferingServiceFacade.getAoIdAndAoTypeByFO(formatOfferingId,contextInfo);
        List<String> resultList = new ArrayList<String>();

        assertNotNull(aoKVList);
        for (KeyValue aoIdType: aoKVList) {
            resultList.add(aoIdType.getKey() + "-->" + aoIdType.getValue());
        }

        assertEquals(expectedList.size(), aoKVList.size());
        assertEquals(expectedList.containsAll(resultList),true);

    }
    
    @Test
    public void testSearchService() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException {
        
        before();

        ActivityOfferingClusterInfo expected = _createAOC();
        
        //test createActivityOfferingCluster
        ActivityOfferingClusterInfo actual = coServiceImpl.createActivityOfferingCluster("Lui-6", CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, expected, contextInfo);
        assertNotNull(actual.getId());
        
        //test generateRegistrationGroupsForCluster and deleteRegistrationGroupsForCluster
        coServiceImpl.generateRegistrationGroupsForCluster(actual.getId(), contextInfo);

        List<RegistrationGroupInfo> rgList = coServiceImpl.getRegistrationGroupsByActivityOfferingCluster(actual.getId(), contextInfo);
        assertEquals(2, rgList.size());

        // make sure the data is in the db for the searches 
        entityManager.flush();
        
        AutogenCount counts = courseOfferingServiceFacade.getAutogenCountByCourseOffering("Lui-1", contextInfo);
        
        assertEquals(1, counts.getNumberOfActivityOfferingClusters().intValue());
        assertEquals(3, counts.getNumberOfActivityOfferings().intValue());
        assertEquals(2, counts.getNumberOfRegistrationGroups().intValue());
        assertEquals(0, counts.getNumberOfInvalidRegistrationGroups().intValue());
        
        counts = courseOfferingServiceFacade.getAutogenCountByFormatOffering("Lui-6", contextInfo);
        
        assertEquals(1, counts.getNumberOfActivityOfferingClusters().intValue());
        assertEquals(3, counts.getNumberOfActivityOfferings().intValue());
        assertEquals(2, counts.getNumberOfRegistrationGroups().intValue());
        assertEquals(0, counts.getNumberOfInvalidRegistrationGroups().intValue());
        
        counts = courseOfferingServiceFacade.getAutogenCountByActivtyOfferingCluster(actual.getId(), contextInfo);
        
        assertEquals(1, counts.getNumberOfActivityOfferingClusters().intValue());
        assertEquals(3, counts.getNumberOfActivityOfferings().intValue());
        assertEquals(2, counts.getNumberOfRegistrationGroups().intValue());
        assertEquals(0, counts.getNumberOfInvalidRegistrationGroups().intValue());
        
        log.info("");
        
    }
    
}
