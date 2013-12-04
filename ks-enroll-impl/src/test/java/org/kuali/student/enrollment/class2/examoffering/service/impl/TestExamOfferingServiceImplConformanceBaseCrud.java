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


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.IdEntityTester;
import org.kuali.student.common.test.util.KeyEntityTester;
import org.kuali.student.common.test.util.RelationshipTester;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeStateEntityInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.common.test.util.AttributeTester;
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
@ContextConfiguration(locations = {"classpath:examoffering-test-with-map-context.xml"})
public abstract class TestExamOfferingServiceImplConformanceBaseCrud {

    // ====================
    // SETUP
    // ====================

    @Resource
    public ExamOfferingService testService;
    public ExamOfferingService getExamOfferingService() { return testService; }
    public void setExamOfferingService(ExamOfferingService service) { testService = service; }

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
    //           ExamOfferingInfo
    // ****************************************************
    @Test
    public void testCrudExamOffering()
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
        ExamOfferingInfo expected = new ExamOfferingInfo ();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudExamOffering_setDTOFieldsForTestCreate (expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        ExamOfferingInfo actual = testService.createExamOffering ( expected.getExamPeriodId(), expected.getExamId(), expected.getTypeKey(), expected, contextInfo);

        assertNotNull(actual.getId());
        new IdEntityTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudExamOffering_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getExamOffering ( actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudExamOffering_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        expected = actual;

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudExamOffering_setDTOFieldsForTestUpdate (expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updateExamOffering ( expected.getId(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudExamOffering_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test read after update
        // -------------------------------------

        expected = actual;
        // code to get actual
        actual = testService.getExamOffering ( actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudExamOffering_testDTOFieldsForTestReadAfterUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());

        ExamOfferingInfo alphaDTO = actual;

        // create a 2nd DTO
        ExamOfferingInfo betaDTO = new ExamOfferingInfo ();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudExamOffering_setDTOFieldsForTestReadAfterUpdate (betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createExamOffering ( betaDTO.getExamPeriodId(), betaDTO.getExamId(), betaDTO.getTypeKey(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------

        List<String> examOfferingIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<ExamOfferingInfo> records = testService.getExamOfferingsByIds ( examOfferingIds, contextInfo);

        assertEquals(examOfferingIds.size(), records.size());
        assertEquals(0, examOfferingIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        examOfferingIds = new ArrayList<String>();
        examOfferingIds.add(alphaDTO.getId());
        examOfferingIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getExamOfferingsByIds ( examOfferingIds, contextInfo);

        assertEquals(examOfferingIds.size(), records.size());
        for (ExamOfferingInfo record : records)
        {
            if (!examOfferingIds.remove(record.getId()))
            {
                fail(record.getId());
            }
        }
        assertEquals(0, examOfferingIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01"
        examOfferingIds = testService.getExamOfferingIdsByType ("typeKey01", contextInfo);

        assertEquals(1, examOfferingIds.size());
        assertEquals(alphaDTO.getId(), examOfferingIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta"
        examOfferingIds = testService.getExamOfferingIdsByType ("typeKeyBeta", contextInfo);

        assertEquals(1, examOfferingIds.size());
        assertEquals(betaDTO.getId(), examOfferingIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------

        StatusInfo status = testService.deleteExamOffering ( actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try
        {
            ExamOfferingInfo record = testService.getExamOffering ( actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        }
        catch (DoesNotExistException dnee)
        {
            // expected
        }

    }

    /*
        A method to set the fields for a ExamOffering in a 'test create' section prior to calling the 'create' operation.
    */
    public abstract void testCrudExamOffering_setDTOFieldsForTestCreate(ExamOfferingInfo expected);

    /*
        A method to test the fields for a ExamOffering. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public abstract void testCrudExamOffering_testDTOFieldsForTestCreateUpdate(ExamOfferingInfo expected, ExamOfferingInfo actual);

    /*
        A method to set the fields for a ExamOffering in a 'test update' section prior to calling the 'update' operation.
    */
    public abstract void testCrudExamOffering_setDTOFieldsForTestUpdate(ExamOfferingInfo expected);

    /*
        A method to test the fields for a ExamOffering after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public abstract void testCrudExamOffering_testDTOFieldsForTestReadAfterUpdate(ExamOfferingInfo expected, ExamOfferingInfo actual);

    /*
        A method to set the fields for a ExamOffering in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public abstract void testCrudExamOffering_setDTOFieldsForTestReadAfterUpdate(ExamOfferingInfo expected);


    // ****************************************************
    //           ExamOfferingRelationInfo
    // ****************************************************
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
        // -------------------------------------
        // test create
        // -------------------------------------
        ExamOfferingRelationInfo expected = new ExamOfferingRelationInfo ();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudExamOfferingRelation_setDTOFieldsForTestCreate (expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        ExamOfferingRelationInfo actual = testService.createExamOfferingRelation ( expected.getFormatOfferingId(), expected.getExamOfferingId(), expected.getTypeKey(), expected, contextInfo);

        assertNotNull(actual.getId());
        new RelationshipTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudExamOfferingRelation_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getExamOfferingRelation ( actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new RelationshipTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudExamOfferingRelation_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        expected = actual;

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudExamOfferingRelation_setDTOFieldsForTestUpdate (expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updateExamOfferingRelation ( expected.getId(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new RelationshipTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudExamOfferingRelation_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test read after update
        // -------------------------------------

        expected = actual;
        // code to get actual
        actual = testService.getExamOfferingRelation ( actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new RelationshipTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudExamOfferingRelation_testDTOFieldsForTestReadAfterUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        ExamOfferingRelationInfo alphaDTO = actual;

        // create a 2nd DTO
        ExamOfferingRelationInfo betaDTO = new ExamOfferingRelationInfo ();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudExamOfferingRelation_setDTOFieldsForTestReadAfterUpdate (betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createExamOfferingRelation ( betaDTO.getFormatOfferingId(), betaDTO.getExamOfferingId(), betaDTO.getTypeKey(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------

        List<String> examOfferingRelationIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<ExamOfferingRelationInfo> records = testService.getExamOfferingRelationsByIds ( examOfferingRelationIds, contextInfo);

        assertEquals(examOfferingRelationIds.size(), records.size());
        assertEquals(0, examOfferingRelationIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        examOfferingRelationIds = new ArrayList<String>();
        examOfferingRelationIds.add(alphaDTO.getId());
        examOfferingRelationIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getExamOfferingRelationsByIds ( examOfferingRelationIds, contextInfo);

        assertEquals(examOfferingRelationIds.size(), records.size());
        for (ExamOfferingRelationInfo record : records)
        {
            if (!examOfferingRelationIds.remove(record.getId()))
            {
                fail(record.getId());
            }
        }
        assertEquals(0, examOfferingRelationIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01"
        examOfferingRelationIds = testService.getExamOfferingRelationIdsByType ("typeKey01", contextInfo);

        assertEquals(1, examOfferingRelationIds.size());
        assertEquals(alphaDTO.getId(), examOfferingRelationIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta"
        examOfferingRelationIds = testService.getExamOfferingRelationIdsByType ("typeKeyBeta", contextInfo);

        assertEquals(1, examOfferingRelationIds.size());
        assertEquals(betaDTO.getId(), examOfferingRelationIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------

        StatusInfo status = testService.deleteExamOfferingRelation ( actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try
        {
            ExamOfferingRelationInfo record = testService.getExamOfferingRelation ( actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        }
        catch (DoesNotExistException dnee)
        {
            // expected
        }

    }

    /*
        A method to set the fields for a ExamOfferingRelation in a 'test create' section prior to calling the 'create' operation.
    */
    public abstract void testCrudExamOfferingRelation_setDTOFieldsForTestCreate(ExamOfferingRelationInfo expected);

    /*
        A method to test the fields for a ExamOfferingRelation. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public abstract void testCrudExamOfferingRelation_testDTOFieldsForTestCreateUpdate(ExamOfferingRelationInfo expected, ExamOfferingRelationInfo actual);

    /*
        A method to set the fields for a ExamOfferingRelation in a 'test update' section prior to calling the 'update' operation.
    */
    public abstract void testCrudExamOfferingRelation_setDTOFieldsForTestUpdate(ExamOfferingRelationInfo expected);

    /*
        A method to test the fields for a ExamOfferingRelation after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public abstract void testCrudExamOfferingRelation_testDTOFieldsForTestReadAfterUpdate(ExamOfferingRelationInfo expected, ExamOfferingRelationInfo actual);

    /*
        A method to set the fields for a ExamOfferingRelation in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public abstract void testCrudExamOfferingRelation_setDTOFieldsForTestReadAfterUpdate(ExamOfferingRelationInfo expected);


    // ========================================
    // SERVICE OPS TESTED IN BASE TEST CLASS
    // ========================================
	
	/*
		The following methods are tested as part of CRUD operations for this service's DTOs:
			getExamOffering
			getExamOfferingsByIds
			getExamOfferingIdsByType
			createExamOffering
			updateExamOffering
			deleteExamOffering
			createExamOfferingRelation
			updateExamOfferingRelation
			deleteExamOfferingRelation
			getExamOfferingRelation
			getExamOfferingRelationsByIds
			getExamOfferingRelationIdsByType
	*/

    // ========================================
    // SERVICE OPS NOT TESTED IN BASE TEST CLASS
    // ========================================

    /* Method Name: searchForExamOfferingIds */
    @Test
    public abstract void test_searchForExamOfferingIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: searchForExamOfferings */
    @Test
    public abstract void test_searchForExamOfferings()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: validateExamOffering */
    @Test
    public abstract void test_validateExamOffering()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: changeExamOfferingState */
    @Test
    public abstract void test_changeExamOfferingState()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: getExamOfferingsByExamPeriod */
    public abstract void test_getExamOfferingsByExamPeriod()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException  ;

    /* Method Name: validateExamOfferingRelation */
    @Test
    public abstract void test_validateExamOfferingRelation()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: getExamOfferingRelationsByFormatOffering */
    @Test
    public abstract void test_getExamOfferingRelationsByFormatOffering()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: getExamOfferingRelationsByExamOffering */
    @Test
    public abstract void test_getExamOfferingRelationsByExamOffering()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: getExamOfferingRelationIdsByActivityOffering */
    @Test
    public abstract void test_getExamOfferingRelationIdsByActivityOffering()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: searchForExamOfferingRelationIds */
    @Test
    public abstract void test_searchForExamOfferingRelationIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: searchForExamOfferingRelations */
    @Test
    public abstract void test_searchForExamOfferingRelations()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

}
