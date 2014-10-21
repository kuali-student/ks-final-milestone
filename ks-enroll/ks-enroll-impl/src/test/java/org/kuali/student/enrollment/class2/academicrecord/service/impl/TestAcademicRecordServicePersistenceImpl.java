/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.academicrecord.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.model.StudentCourseRecordEntity;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.constants.AcademicRecordServiceConstants;
import org.springframework.test.AssertThrows;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jonrcook on 9/18/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ar-studentcourserecord-jpa-persistence-test-context.xml"})
@PersistenceFileLocation("classpath:META-INF/ar-persistence.xml")
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestAcademicRecordServicePersistenceImpl {

    public static final String TEST_PERSON_ID = "R.JESSICAL";
    public static final String TEST_CREATE_PERSON_ID = "R.JESSEA";
    public static final String TEST_COURSE_ID = "68e20a79-5f25-4e89-8518-18da6f89dc03";
    public static final String TEST_TERM_ID = "kuali.atp.2011Fall";

    @Resource
    private AcademicRecordService academicRecordService;

    @Resource
    private Map<String, List<StudentCourseRecordInfo>> studentToCourseRecordsMap;

    public TestAcademicRecordServicePersistenceImpl() {
        super();
        // Grab annotations and pass them as System properties
        if (this.getClass().isAnnotationPresent(PersistenceFileLocation.class)) {
            PersistenceFileLocation a = this.getClass().getAnnotation(
                    PersistenceFileLocation.class);
            System.setProperty("ks.test.persistenceLocation", a.value());
        } else {
            System.setProperty("ks.test.persistenceLocation",
                    "classpath:META-INF/persistence.xml");
        }
    }

    private void createStudentCourseRecord(StudentCourseRecordInfo studentCourseRecord,
                                            ContextInfo contextInfo) throws Exception {
        studentCourseRecord.setTypeKey(AcademicRecordServiceConstants.STUDENT_COURSE_RECORD_TYPE_KEY);
        studentCourseRecord.setId(UUIDHelper.genStringUUID());
        academicRecordService.createStudentCourseRecord(studentCourseRecord.getPersonId(),
                studentCourseRecord.getTypeKey(),
                studentCourseRecord, contextInfo);
    }

    @Before
    public void setup() throws Exception {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("admin");
        // load mock data into derby from ks-enroll-mock-academic-service.xml
        Set<String> keys = studentToCourseRecordsMap.keySet();
        for(String key : keys) {
            Object entry = studentToCourseRecordsMap.get(key);
            if(entry instanceof StudentCourseRecordInfo) {
                createStudentCourseRecord((StudentCourseRecordInfo) entry, contextInfo);
            } else {
                for (StudentCourseRecordInfo studentCourseRecord : (List<StudentCourseRecordInfo>)entry) {
                    createStudentCourseRecord(studentCourseRecord, contextInfo);
                }
            }
        }
    }

    @Test
    public void testCreate() throws Exception {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("admin");
        StudentCourseRecordInfo studentCourseRecordInfo = new StudentCourseRecordInfo();
        studentCourseRecordInfo.setTypeKey(AcademicRecordServiceConstants.STUDENT_COURSE_RECORD_TYPE_KEY);
        studentCourseRecordInfo.setStateKey(AcademicRecordServiceConstants.STUDENTCOURSERECORD_STATE_KEY_COMPLETED);
        studentCourseRecordInfo.setCourseId("339e49bd-bb68-47d6-92da-2b19ffc57bb0");
        studentCourseRecordInfo.setCourseOfferingId("12489180-d180-430c-9d90-efb6550f45f9");
        studentCourseRecordInfo.setCourseCode("BSCI258");
        studentCourseRecordInfo.setPersonId(TEST_CREATE_PERSON_ID);
        studentCourseRecordInfo.setTermId("kuali.atp.2011Fall");
        studentCourseRecordInfo.setTermName("Fall 2011");
        studentCourseRecordInfo.setAssignedGradeValue("B");
        studentCourseRecordInfo.setCalculatedGradeValue("B+");
        studentCourseRecordInfo.setCreditsAttempted("5");
        studentCourseRecordInfo.setCreditsEarned("5");
        StudentCourseRecordInfo createdScr = academicRecordService.createStudentCourseRecord(studentCourseRecordInfo.getPersonId(),
                studentCourseRecordInfo.getTypeKey(),
                studentCourseRecordInfo,
                contextInfo);
        StudentCourseRecordInfo shouldExist = academicRecordService.getStudentCourseRecord(createdScr.getId(), contextInfo);
        Assert.notNull(shouldExist);
        Assert.isTrue(shouldExist.getId().equals(createdScr.getId()));
    }

    @Test
    public void testGetStudentCourseRecordsForCourse() throws Exception {
        ContextInfo contextInfo = new ContextInfo();

        List<StudentCourseRecordInfo>  studentCourseRecords =
                academicRecordService.getStudentCourseRecordsForCourse(TEST_PERSON_ID, TEST_COURSE_ID, contextInfo);
        Assert.notNull(studentCourseRecords);
        Assert.notEmpty(studentCourseRecords);
    }

    @Test
    public void testGetCompletedCourseRecordsForCourse() throws Exception {
        ContextInfo contextInfo = new ContextInfo();

        List<StudentCourseRecordInfo>  studentCourseRecords =
                academicRecordService.getCompletedCourseRecordsForCourse(TEST_PERSON_ID, TEST_COURSE_ID, contextInfo);
        Assert.notNull(studentCourseRecords);
        Assert.notEmpty(studentCourseRecords);
        for(StudentCourseRecordInfo studentCourseRecordInfo : studentCourseRecords) {
            Assert.isTrue(studentCourseRecordInfo.getStateKey().
                    equals(AcademicRecordServiceConstants.STUDENTCOURSERECORD_STATE_KEY_COMPLETED));
        }
    }

    @Test
    public void testGetCompletedCourseRecords() throws Exception {
        ContextInfo contextInfo = new ContextInfo();

        List<StudentCourseRecordInfo>  studentCourseRecords =
                academicRecordService.getCompletedCourseRecords(TEST_PERSON_ID, contextInfo);
        Assert.notNull(studentCourseRecords);
        Assert.notEmpty(studentCourseRecords);
        for(StudentCourseRecordInfo studentCourseRecordInfo : studentCourseRecords) {
            Assert.isTrue(studentCourseRecordInfo.getStateKey().
                    equals(AcademicRecordServiceConstants.STUDENTCOURSERECORD_STATE_KEY_COMPLETED));
        }
    }

    @Test
    public void testGetAttemptedCourseRecordsForTerm() throws Exception {
        ContextInfo contextInfo = new ContextInfo();

        List<StudentCourseRecordInfo>  studentCourseRecords =
                academicRecordService.getAttemptedCourseRecordsForTerm(TEST_PERSON_ID, TEST_TERM_ID, contextInfo);
        Assert.notNull(studentCourseRecords);
        Assert.notEmpty(studentCourseRecords);
        for(StudentCourseRecordInfo studentCourseRecordInfo : studentCourseRecords) {
            Assert.notNull(studentCourseRecordInfo.getCreditsAttempted());
            Assert.isTrue(new Integer(studentCourseRecordInfo.getCreditsAttempted()) > 0);
        }
    }

    @Test
    public void testGetIdsByType() throws Exception {
        ContextInfo contextInfo = new ContextInfo();

        List<String>  studentCourseRecordIds = academicRecordService.getStudentCourseRecordIdsByType(
                AcademicRecordServiceConstants.STUDENT_COURSE_RECORD_TYPE_KEY, contextInfo);
        Assert.notNull(studentCourseRecordIds);
        Assert.notEmpty(studentCourseRecordIds);
    }

    @Test
    public void testUpdate() throws Exception {
        String creditsAttempted = "5";
        ContextInfo contextInfo = new ContextInfo();

        List<StudentCourseRecordInfo>  studentCourseRecords =
                academicRecordService.getStudentCourseRecordsForCourse(TEST_PERSON_ID, TEST_COURSE_ID,
                        contextInfo);
        for(StudentCourseRecordInfo studentCourseRecord : studentCourseRecords) {
            studentCourseRecord.setCreditsAttempted(creditsAttempted);
            String id = studentCourseRecord.getId();
            academicRecordService.updateStudentCourseRecord(id, studentCourseRecord, contextInfo);
            StudentCourseRecordInfo updated = academicRecordService.getStudentCourseRecord(id, contextInfo);
            String updateCreditsAttempted = updated.getCreditsAttempted();
            Assert.isTrue(updateCreditsAttempted.equals(creditsAttempted));
        }
        Assert.notNull(studentCourseRecords);
        Assert.notEmpty(studentCourseRecords);
    }

    @Test(expected=DoesNotExistException.class)
    public void testDelete() throws Exception {
        String creditsAttempted = "5";
        ContextInfo contextInfo = new ContextInfo();

        List<StudentCourseRecordInfo>  studentCourseRecords =
                academicRecordService.getStudentCourseRecordsForCourse(TEST_PERSON_ID, TEST_COURSE_ID, contextInfo);
        for(StudentCourseRecordInfo studentCourseRecord : studentCourseRecords) {
            String id = studentCourseRecord.getId();
            academicRecordService.deleteStudentCourseRecord(id, contextInfo);
            StudentCourseRecordInfo shouldntExist = academicRecordService.getStudentCourseRecord(id, contextInfo);
        }
    }

    public Map<String, List<StudentCourseRecordInfo>> getStudentToCourseRecordsMap() {
        return studentToCourseRecordsMap;
    }

    public void setStudentToCourseRecordsMap(Map<String, List<StudentCourseRecordInfo>> studentToCourseRecordsMap) {
        this.studentToCourseRecordsMap = studentToCourseRecordsMap;
    }

    public AcademicRecordService getAcademicRecordService() {
        return academicRecordService;
    }

    public void setAcademicRecordService(AcademicRecordService academicRecordService) {
        this.academicRecordService = academicRecordService;
    }
}
