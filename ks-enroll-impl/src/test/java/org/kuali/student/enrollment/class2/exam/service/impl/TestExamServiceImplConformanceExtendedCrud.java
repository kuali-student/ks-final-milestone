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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.IdEntityTester;
import org.kuali.student.common.test.util.KeyEntityTester;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.enrollment.exam.service.ExamService;
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
public class TestExamServiceImplConformanceExtendedCrud extends TestExamServiceImplConformanceBaseCrud
{

    // ========================================
    // DTO FIELD SPECIFIC METHODS
    // ========================================

    // ****************************************************
    //           ExamInfo
    // ****************************************************

    /*
        A method to set the fields for a Exam in a 'test create' section prior to calling the 'create' operation.
    */
    public void testCrudExam_setDTOFieldsForTestCreate(ExamInfo expected)
    {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
        expected.setName("name01");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));
    }

    /*
        A method to test the fields for a Exam. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public void testCrudExam_testDTOFieldsForTestCreateUpdate(ExamInfo expected, ExamInfo actual)
    {
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        assertEquals (expected.getName(), actual.getName());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
    }

    /*
        A method to set the fields for a Exam in a 'test update' section prior to calling the 'update' operation.
    */
    public void testCrudExam_setDTOFieldsForTestUpdate(ExamInfo expected)
    {
        // expected.setTypeKey("typeKey_Updated");
        // expected.setStateKey("stateKey_Updated");
        expected.setName("name_Updated");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "descr_Updated"));
    }

    /*
        A method to test the fields for a Exam after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public void testCrudExam_testDTOFieldsForTestReadAfterUpdate(ExamInfo expected, ExamInfo actual)
    {
        assertEquals (expected.getId(), actual.getId());
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        assertEquals (expected.getName(), actual.getName());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
    }

    /*
        A method to set the fields for a Exam in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public void testCrudExam_setDTOFieldsForTestReadAfterUpdate(ExamInfo expected)
    {
        expected.setName("name_Updated");
    }


    // ========================================
    // SERVICE OPS NOT TESTED IN BASE TEST CLASS
    // ========================================

    /* Method Name: validateExam */
    @Test
    public void test_validateExam()
            throws 	DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException	{
    }

    /* Method Name: searchForExamIds */
    @Test
    public void test_searchForExamIds()
            throws 	InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException	{
    }

    /* Method Name: searchForExams */
    @Test
    public void test_searchForExams()
            throws 	InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException	{
    }

}


