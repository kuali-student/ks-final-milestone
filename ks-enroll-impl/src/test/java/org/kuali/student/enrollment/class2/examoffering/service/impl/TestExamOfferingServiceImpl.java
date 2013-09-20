/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.examoffering.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class1.lui.service.impl.LuiServiceDataLoader;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:examoffering-test-context.xml"})
@Transactional
public class TestExamOfferingServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(TestExamOfferingServiceImpl.class);

    @Resource
    private ExamOfferingService examOfferingService;

    @Resource(name = "luiServiceDataLoader")
    protected LuiServiceDataLoader dataLoader = new LuiServiceDataLoader();

    public ContextInfo callContext = null;
    public static String principalId = "123";

    @Before
    public void setUp() {
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
        try {
            dataLoader.loadData();
        } catch (Exception ex) {
            throw new RuntimeException (ex);
        }
    }

    @After
    public void cleanup() {
    }

    private ExamOfferingRelationInfo createExamOfferingRelationInfo (){
        ExamOfferingRelationInfo examOfferingRelationInfo = new ExamOfferingRelationInfo();
        examOfferingRelationInfo.setFormatOfferingId("Lui-6");
        examOfferingRelationInfo.setExamOfferingId("Lui-9");
        examOfferingRelationInfo.setActivityOfferingIds(Arrays.asList("AO-01","AO-02","AO-03"));
        examOfferingRelationInfo.setPopulationIds(Arrays.asList("PO-01", "PO-02", "PO-03"));
        return examOfferingRelationInfo;
    }

    @Test
    public void testCrudExamOfferingRelation()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException
    {
        ExamOfferingRelationInfo eoRelInfo = createExamOfferingRelationInfo();
        try {
            //Create
            ExamOfferingRelationInfo created = examOfferingService.createExamOfferingRelation("Lui-6", "Lui-9",
                    LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo, callContext);

            String examOfferingRelationId = created.getId();
            assertNotNull(created);
            assertNotNull(examOfferingRelationId);
            assertEquals(3, created.getActivityOfferingIds().size());

            //Get
            ExamOfferingRelationInfo retrieved = examOfferingService.getExamOfferingRelation(examOfferingRelationId, callContext);
            assertNotNull(retrieved);
            assertEquals(examOfferingRelationId, retrieved.getId());
            assertEquals(3, created.getActivityOfferingIds().size());

            //Update
            retrieved.setActivityOfferingIds(Arrays.asList("AO-01","AO-02","AO-03","AO-04"));
            ExamOfferingRelationInfo retrievedUpdated = examOfferingService.updateExamOfferingRelation(examOfferingRelationId, retrieved, callContext);
            assertNotNull(retrievedUpdated);
            assertEquals(examOfferingRelationId, retrievedUpdated.getId());
            assertEquals(4, retrievedUpdated.getActivityOfferingIds().size());

            //Delete
            StatusInfo ret = examOfferingService.deleteExamOfferingRelation(examOfferingRelationId, callContext);
            assertTrue(ret.getIsSuccess());
      } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }
    
    @Test 
    public void testGetExamOfferingRelationIdsByType ()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        
        ExamOfferingRelationInfo eoRelInfo1 = createExamOfferingRelationInfo();
        ExamOfferingRelationInfo eoRelInfo2 = createExamOfferingRelationInfo();
        eoRelInfo2.setExamOfferingId("Lui-10");
        List<String> examOfferingRelationIds;
        try {
            //Create
            ExamOfferingRelationInfo created1 = examOfferingService.createExamOfferingRelation("Lui-6", "Lui-9",
                    LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo1, callContext);
            ExamOfferingRelationInfo created2 = examOfferingService.createExamOfferingRelation("Lui-6", "Lui-10",
                                LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_FO_TO_EO_TYPE_KEY, eoRelInfo2, callContext);
            
            examOfferingRelationIds = examOfferingService.getExamOfferingRelationIdsByType(LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_FO_TO_EO_TYPE_KEY, callContext);

            assertNotNull(examOfferingRelationIds);
            assertEquals(2, examOfferingRelationIds.size());

            //Delete
            for (String examOfferingRelationId : examOfferingRelationIds) {
                StatusInfo ret = examOfferingService.deleteExamOfferingRelation(examOfferingRelationId, callContext);
                assertTrue(ret.getIsSuccess());
            }
        } catch (Exception ex) {
            fail("exception from service call :" + ex.getMessage());
        }
    }
    
}
