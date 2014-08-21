/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by pauldanielrichardson on 8/15/14
 */
package org.kuali.student.enrollment.class2.academicrecord.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.AcademicRecordServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class contains unit tests for TestAcademicRecordServicePeristenceMockImpl
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:academic-record-test-context.xml" })
public class TestAcademicRecordServicePersistenceMockImpl {

    @Resource(name = "AcademicRecordService")
    private AcademicRecordService academicRecordService;

    @Before
    public void setUp() {
    }

    @Test
    public void TestSingleStudentCourseRecordForCourse() throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        String personId = "R.JESSEA";
        String courseId = "339e49bd-bb68-47d6-92da-2b19ffc57bb0";
        ContextInfo contextInfo = new ContextInfo();

        List<StudentCourseRecordInfo> studentCourseRecordInfoList = academicRecordService.getStudentCourseRecordsForCourse(personId, courseId, contextInfo);

        assertNotNull(studentCourseRecordInfoList);
        assertTrue(KSCollectionUtils.getOptionalZeroElement(studentCourseRecordInfoList).getCourseCode().equals("BSCI258"));
        assertTrue(KSCollectionUtils.getRequiredZeroElement(studentCourseRecordInfoList).getAssignedGradeValue().equals("A"));
    }

    @Test
    public void TestMultipleStudentCourseRecordForCourse() throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        String personId = "R.JESSICAL";
        String courseId = "68e20a79-5f25-4e89-8518-18da6f89dc03";
        ContextInfo contextInfo = new ContextInfo();

        List<StudentCourseRecordInfo> studentCourseRecordInfoList = academicRecordService.getStudentCourseRecordsForCourse(personId, courseId, contextInfo);

        assertNotNull(studentCourseRecordInfoList);
        assertTrue(studentCourseRecordInfoList.size() == 2);
        boolean fall2010A = false;
        for (StudentCourseRecordInfo studentCourseRecordInfo:studentCourseRecordInfoList) {
            if (studentCourseRecordInfo.getTermId().equals("kuali.atp.2010Fall")
                    && studentCourseRecordInfo.getAssignedGradeValue().equals("A")
                    && studentCourseRecordInfo.getCourseCode().equals("HIST499")) {
                fall2010A = true;
            }
        }
        assertTrue(fall2010A);
    }

    @Test
    public void TestIsRepeated() throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        String personId = "R.JOANL";
        String courseId = "8af491cf-9c23-409e-adc8-dc184dcbced6";
        ContextInfo contextInfo = new ContextInfo();

        List<StudentCourseRecordInfo> studentCourseRecordInfoList=academicRecordService.getStudentCourseRecordsForCourse(personId, courseId, contextInfo);

        assertNotNull(studentCourseRecordInfoList);
        assertTrue(studentCourseRecordInfoList.size() == 2);
        boolean fall2011repeated = false;
        for (StudentCourseRecordInfo studentCourseRecordInfo:studentCourseRecordInfoList) {
            if (studentCourseRecordInfo.getTermId().equals("kuali.atp.2011Fall")
                    && studentCourseRecordInfo.getIsRepeated()
                    && studentCourseRecordInfo.getCourseCode().equals("PHYS260")) {
                fall2011repeated = true;
            }
        }
        assertTrue(fall2011repeated);
    }

    @Test
    public void TestIncomplete() throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        String personId = "R.JODYB";
        String courseId = "acdf9452-99b1-4910-8a56-1b95d6393fab";
        ContextInfo contextInfo = new ContextInfo();

        List<StudentCourseRecordInfo> studentCourseRecordInfoList = academicRecordService.getStudentCourseRecordsForCourse(personId, courseId, contextInfo);

        assertNotNull(studentCourseRecordInfoList);
        assertTrue(KSCollectionUtils.getRequiredZeroElement(studentCourseRecordInfoList).getCourseCode().equals("HIST352"));
        assertTrue(KSCollectionUtils.getRequiredZeroElement(studentCourseRecordInfoList).getAssignedGradeValue().equals("kuali.result.value.grade.admin.i"));
    }

    @Test
    public void TestWithdrawal() throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        String personId = "R.JOEM";
        String courseId = "3c0548bd-06b5-43ef-b064-30fa1e501564";
        ContextInfo contextInfo = new ContextInfo();

        List<StudentCourseRecordInfo> studentCourseRecordInfoList = academicRecordService.getStudentCourseRecordsForCourse(personId, courseId, contextInfo);

        assertNotNull(studentCourseRecordInfoList);
        assertTrue(KSCollectionUtils.getRequiredZeroElement(studentCourseRecordInfoList).getStateKey().equals(AcademicRecordServiceConstants.STUDENTCOURSERECORD_STATE_KEY_WITHDRAWN));
        assertTrue(KSCollectionUtils.getRequiredZeroElement(studentCourseRecordInfoList).getCourseCode().equals("BSCI103"));
        assertTrue(KSCollectionUtils.getRequiredZeroElement(studentCourseRecordInfoList).getAssignedGradeValue().equals("W"));
    }

    @Test
    public void TestStudentRecordNotFound() throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        String personId = "fakePerson";
        String courseId = "fakeCourseId";
        ContextInfo contextInfo = new ContextInfo();

        try {
            academicRecordService.getStudentCourseRecordsForCourse(personId, courseId, contextInfo);
            fail("DoesNotExistException should have been thrown");
        } catch (DoesNotExistException ex) {
            assertNotNull(ex.getMessage());
            assertTrue(ex.getMessage().equals("No course records for student Id = " + personId));
        }
    }

    @Test
    public void TestCourseNotFound() throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        String personId = "R.JOER";
        String courseId = "fakeCourseId";
        ContextInfo contextInfo = new ContextInfo();

        List<StudentCourseRecordInfo> studentCourseRecordInfoList=academicRecordService.getStudentCourseRecordsForCourse(personId, courseId, contextInfo);

        assertTrue(studentCourseRecordInfoList.size() == 0);
    }
}
