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
package org.kuali.student.enrollment.class2.exam.service.impl;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.IdEntityTester;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.enrollment.exam.service.ExamService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.RichTextTester;
import org.kuali.student.common.test.util.MetaTester;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:exam-test-with-map-context.xml"})
public abstract class TestExamServiceImplConformanceBaseCrud {

    // ====================
    // SETUP
    // ====================

    @Resource
    public ExamService testService;
    public ExamService getExamService() { return testService; }
    public void setExamService(ExamService service) { testService = service; }

    public ContextInfo contextInfo = null;
    public static String principalId = "123";

    @Before
    public void setUp()
    {
        principalId = "123";
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
    }

    // ====================
    // TESTING
    // ====================

    // ****************************************************
    //           ExamInfo
    // ****************************************************
    @Test
    public void testCrudExam()
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
        // -------------------------------------
        // test create
        // -------------------------------------
        ExamInfo expected = new ExamInfo ();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudExam_setDTOFieldsForTestCreate (expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        ExamInfo actual = testService.createExam ( expected.getTypeKey(), expected, contextInfo);

        assertNotNull(actual.getId());
        new IdEntityTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudExam_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getExam ( actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudExam_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        expected = actual;

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudExam_setDTOFieldsForTestUpdate (expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updateExam ( expected.getId(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudExam_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test read after update
        // -------------------------------------

        expected = actual;
        // code to get actual
        actual = testService.getExam ( actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudExam_testDTOFieldsForTestReadAfterUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        ExamInfo alphaDTO = actual;

        // create a 2nd DTO
        ExamInfo betaDTO = new ExamInfo ();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudExam_setDTOFieldsForTestReadAfterUpdate (betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createExam ( betaDTO.getTypeKey(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------

        List<String> examIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<ExamInfo> records = testService.getExamsByIds ( examIds, contextInfo);

        assertEquals(examIds.size(), records.size());
        assertEquals(0, examIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        examIds = new ArrayList<String>();
        examIds.add(alphaDTO.getId());
        examIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getExamsByIds ( examIds, contextInfo);

        assertEquals(examIds.size(), records.size());
        for (ExamInfo record : records)
        {
            if (!examIds.remove(record.getId()))
            {
                fail(record.getId());
            }
        }
        assertEquals(0, examIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01"
        examIds = testService.getExamIdsByType ("typeKey01", contextInfo);

        assertEquals(1, examIds.size());
        assertEquals(alphaDTO.getId(), examIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta"
        examIds = testService.getExamIdsByType ("typeKeyBeta", contextInfo);

        assertEquals(1, examIds.size());
        assertEquals(betaDTO.getId(), examIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------

        StatusInfo status = testService.deleteExam ( actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try
        {
            ExamInfo record = testService.getExam ( actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        }
        catch (DoesNotExistException dnee)
        {
            // expected
        }

    }

    /*
        A method to set the fields for a Exam in a 'test create' section prior to calling the 'create' operation.
    */
    public abstract void testCrudExam_setDTOFieldsForTestCreate(ExamInfo expected);

    /*
        A method to test the fields for a Exam. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public abstract void testCrudExam_testDTOFieldsForTestCreateUpdate(ExamInfo expected, ExamInfo actual);

    /*
        A method to set the fields for a Exam in a 'test update' section prior to calling the 'update' operation.
    */
    public abstract void testCrudExam_setDTOFieldsForTestUpdate(ExamInfo expected);

    /*
        A method to test the fields for a Exam after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public abstract void testCrudExam_testDTOFieldsForTestReadAfterUpdate(ExamInfo expected, ExamInfo actual);

    /*
        A method to set the fields for a Exam in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public abstract void testCrudExam_setDTOFieldsForTestReadAfterUpdate(ExamInfo expected);


    // ========================================
    // SERVICE OPS TESTED IN BASE TEST CLASS
    // ========================================
	
	/*
		The following methods are tested as part of CRUD operations for this service's DTOs:
			createExam
			getExam
			updateExam
			deleteExam
			getExamsByIds
			getExamIdsByType
	*/

    // ========================================
    // SERVICE OPS NOT TESTED IN BASE TEST CLASS
    // ========================================

    /* Method Name: validateExam */
    @Test
    public abstract void test_validateExam()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: searchForExamIds */
    @Test
    public abstract void test_searchForExamIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: searchForExams */
    @Test
    public abstract void test_searchForExams()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

}


