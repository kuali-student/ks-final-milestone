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
import org.kuali.student.common.test.util.RichTextTester;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
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
public class TestExamOfferingServiceImplConformanceExtendedCrud extends TestExamOfferingServiceImplConformanceBaseCrud
{

    // ========================================
    // DTO FIELD SPECIFIC METHODS
    // ========================================

    // ****************************************************
    //           ExamOfferingInfo
    // ****************************************************

    /*
        A method to set the fields for a ExamOffering in a 'test create' section prior to calling the 'create' operation.
    */
    public void testCrudExamOffering_setDTOFieldsForTestCreate(ExamOfferingInfo expected)
    {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
        expected.setName("name01");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));
        expected.setExamPeriodId("examPeriodId01");
        expected.setExamId("examId01");
        expected.setScheduleId("scheduleId01");
        expected.setSchedulingStateKey(ExamOfferingServiceConstants.EXAM_OFFERING_SCHEDULING_UNSCHEDULED_STATE_KEY);
    }

    /*
        A method to test the fields for a ExamOffering. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public void testCrudExamOffering_testDTOFieldsForTestCreateUpdate(ExamOfferingInfo expected, ExamOfferingInfo actual)
    {
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        assertEquals (expected.getName(), actual.getName());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
        assertEquals (expected.getExamPeriodId(), actual.getExamPeriodId());
        assertEquals (expected.getExamId(), actual.getExamId());
        assertEquals (expected.getScheduleId(), actual.getScheduleId());
        assertEquals (expected.getSchedulingStateKey(), actual.getSchedulingStateKey());
    }

    /*
        A method to set the fields for a ExamOffering in a 'test update' section prior to calling the 'update' operation.
    */
    public void testCrudExamOffering_setDTOFieldsForTestUpdate(ExamOfferingInfo expected)
    {
        expected.setName("name_Updated");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "descr_Updated"));
        expected.setExamPeriodId("examPeriodId_Updated");
        expected.setScheduleId("scheduleId_Updated");
    }

    /*
        A method to test the fields for a ExamOffering after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public void testCrudExamOffering_testDTOFieldsForTestReadAfterUpdate(ExamOfferingInfo expected, ExamOfferingInfo actual)
    {
        assertEquals (expected.getId(), actual.getId());
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        assertEquals (expected.getName(), actual.getName());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
        assertEquals (expected.getExamPeriodId(), actual.getExamPeriodId());
        assertEquals (expected.getExamId(), actual.getExamId());
        assertEquals (expected.getScheduleId(), actual.getScheduleId());
        assertEquals (expected.getSchedulingStateKey(), actual.getSchedulingStateKey());
    }

    /*
        A method to set the fields for a ExamOffering in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public void testCrudExamOffering_setDTOFieldsForTestReadAfterUpdate(ExamOfferingInfo expected)
    {
        expected.setName("name_Updated");
        expected.setExamPeriodId("examPeriodId_Updated");
        expected.setExamId("examId_Updated");
        expected.setScheduleId("scheduleId_Updated");
        expected.setSchedulingStateKey("schedulingStateKey_Updated");
    }


    // ****************************************************
    //           ExamOfferingRelationInfo
    // ****************************************************

    /*
        A method to set the fields for a ExamOfferingRelation in a 'test create' section prior to calling the 'create' operation.
    */
    public void testCrudExamOfferingRelation_setDTOFieldsForTestCreate(ExamOfferingRelationInfo expected)
    {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
    }

    /*
        A method to test the fields for a ExamOfferingRelation. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public void testCrudExamOfferingRelation_testDTOFieldsForTestCreateUpdate(ExamOfferingRelationInfo expected, ExamOfferingRelationInfo actual)
    {
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
    }

    /*
        A method to set the fields for a ExamOfferingRelation in a 'test update' section prior to calling the 'update' operation.
    */
    public void testCrudExamOfferingRelation_setDTOFieldsForTestUpdate(ExamOfferingRelationInfo expected)
    {
    }

    /*
        A method to test the fields for a ExamOfferingRelation after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public void testCrudExamOfferingRelation_testDTOFieldsForTestReadAfterUpdate(ExamOfferingRelationInfo expected, ExamOfferingRelationInfo actual)
    {
        assertEquals (expected.getId(), actual.getId());
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
    }

    /*
        A method to set the fields for a ExamOfferingRelation in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public void testCrudExamOfferingRelation_setDTOFieldsForTestReadAfterUpdate(ExamOfferingRelationInfo expected)
    {
    }


    // ========================================
    // SERVICE OPS NOT TESTED IN BASE TEST CLASS
    // ========================================

    /* Method Name: searchForExamOfferingIds */
    @Test
    public void test_searchForExamOfferingIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: searchForExamOfferings */
    @Test
    public void test_searchForExamOfferings()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: validateExamOffering */
    @Test
    public void test_validateExamOffering()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: changeExamOfferingState */
    @Test
    public void test_changeExamOfferingState()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: getExamOfferingsByExamPeriod */
    public void test_getExamOfferingsByExamPeriod()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException  {
    }

    /* Method Name: validateExamOfferingRelation */
    @Test
    public void test_validateExamOfferingRelation()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: getExamOfferingRelationsByFormatOffering */
    @Test
    public void test_getExamOfferingRelationsByFormatOffering()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: getExamOfferingRelationsByExamOffering */
    @Test
    public void test_getExamOfferingRelationsByExamOffering()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: getExamOfferingRelationIdsByActivityOffering */
    @Test
    public void test_getExamOfferingRelationIdsByActivityOffering()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: searchForExamOfferingRelationIds */
    @Test
    public void test_searchForExamOfferingRelationIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: searchForExamOfferingRelations */
    @Test
    public void test_searchForExamOfferingRelations()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

}