/*
 * Copyright 2012 The Kuali Foundation
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
package org.kuali.student.enrollment.class2.academicrecord.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kuali Student Team
 */
public class AcademicRecordServiceClass2MockImpl implements
        AcademicRecordService, MockService {

    private static final Logger log = LoggerFactory
            .getLogger(AcademicRecordServiceClass2MockImpl.class);

    //Mock datastructures
    private Map<String, GPAInfo> gpasMap = new LinkedHashMap<String, GPAInfo>();
    private List<StudentCourseRecordInfo> courseRecordInfoList = new ArrayList<StudentCourseRecordInfo>();        //to be replaced with studentToCourseRecordsMap
    private Map<String, LoadInfo> loadsMap = new LinkedHashMap<String, LoadInfo>();
    private Map<String, StudentProgramRecordInfo> studentProgramRecordsMap = new LinkedHashMap<String, StudentProgramRecordInfo>();
    private Map<String, StudentCredentialRecordInfo> studentCredentialRecordsMap = new LinkedHashMap<String, StudentCredentialRecordInfo>();
    private Map<String, StudentTestScoreRecordInfo> studentTestScoreRecordsMap = new LinkedHashMap<String, StudentTestScoreRecordInfo>();

    private Map<String, List<StudentCourseRecordInfo>> studentToCourseRecordsMap = new HashMap<String, List<StudentCourseRecordInfo>>();
    private Map<String, List<StudentCourseRecordInfo>> termToCourseRecordsMap = new HashMap<String, List<StudentCourseRecordInfo>>();
    private Set<StudentCourseRecordInfo> studentCourseRecordsSet = new HashSet<StudentCourseRecordInfo>();

    // this is a bit of a hack until the record can contain the course id directly
    private Map<String, String> courseIdToCourseCodeMap = new HashMap<String, String>();

    /**
     *
     */
    public AcademicRecordServiceClass2MockImpl() {
        this.createDataForTermResolvers();
    }


    /* (non-Javadoc)
     * @see org.kuali.student.common.mock.MockService#clear()
     */
    @Override
    public void clear() {
        studentToCourseRecordsMap.clear();
        termToCourseRecordsMap.clear();
        courseIdToCourseCodeMap.clear();
        studentCourseRecordsSet.clear();

        gpasMap.clear();
        courseRecordInfoList.clear();
        loadsMap.clear();
        studentProgramRecordsMap.clear();
        studentCredentialRecordsMap.clear();
        studentTestScoreRecordsMap.clear();
    }

    /**
     * Store a course record for the term specified.  The caller is responsible for filling in the object correctly.
     *
     * @param studentId    the student who completed the course
     * @param termId       the term the course is from
     * @param courseRecord the course record itself.
     */
    public void storeStudentCourseRecord(String studentId, String termId, String courseId, StudentCourseRecordInfo courseRecord) {

        studentCourseRecordsSet.add(courseRecord);

        courseIdToCourseCodeMap.put(courseId, courseRecord.getCourseCode());

        // link to student
        List<StudentCourseRecordInfo> studentCourseList = studentToCourseRecordsMap.get(studentId);

        if (studentCourseList == null) {
            studentCourseList = new ArrayList<StudentCourseRecordInfo>();
            studentToCourseRecordsMap.put(studentId, studentCourseList);
        }

        studentCourseList.add(courseRecord);

        // link to term

        List<StudentCourseRecordInfo> termCourseList = termToCourseRecordsMap.get(termId);

        if (termCourseList == null) {
            termCourseList = new ArrayList<StudentCourseRecordInfo>();
            termToCourseRecordsMap.put(termId, termCourseList);
        }

        termCourseList.add(courseRecord);
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getAttemptedCourseRecordsForTerm(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCourseRecordInfo> getAttemptedCourseRecordsForTerm(
            String personId,
            String termId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<StudentCourseRecordInfo> courseRecords = new ArrayList<StudentCourseRecordInfo>();
        for (StudentCourseRecordInfo courseRecord : courseRecordInfoList) {
            if (courseRecord.getPersonId().equals(personId) && courseRecord.getTermName().equals(termId)) {
                courseRecords.add(courseRecord);
            }
        }
        return courseRecords;
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCompletedCourseRecords(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecords(
            String personId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        if (!studentToCourseRecordsMap.keySet().contains(personId))
            throw new DoesNotExistException("No course records for student Id = " + personId);

        return studentToCourseRecordsMap.get(personId);
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCompletedCourseRecordsForCourse(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForCourse(
            String personId, String courseId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<StudentCourseRecordInfo> resultsList = new ArrayList<StudentCourseRecordInfo>();

        if (!studentToCourseRecordsMap.keySet().contains(personId))
            throw new DoesNotExistException("No course records for student Id = " + personId);

        // Should not throw exception, course could still exist in cluservice and i do want to check if any student has completed it.
        //if (!courseIdToCourseCodeMap.keySet().contains(courseId))
        //    throw new DoesNotExistException("No course records for course id = " + courseId);

        List<StudentCourseRecordInfo> records = studentToCourseRecordsMap.get(personId);

        String courseCode = courseIdToCourseCodeMap.get(courseId);

        for (StudentCourseRecordInfo studentCourseRecordInfo : records) {

            if (studentCourseRecordInfo.getCourseCode().equals(courseCode))
                resultsList.add(studentCourseRecordInfo);

        }

        return resultsList;
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCompletedCourseRecordsForTerm(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(
            String personId,
            String termId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<StudentCourseRecordInfo> courseRecords = new ArrayList<StudentCourseRecordInfo>();
        for (StudentCourseRecordInfo courseRecord : courseRecordInfoList) {
            if (courseRecord.getPersonId().equals(personId) && courseRecord.getTermName().equals(termId) && (courseRecord.getAssignedGradeValue() != null || courseRecord.getAdministrativeGradeValue() != null)) {
                courseRecords.add(courseRecord);
            }
        }
        return courseRecords;
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getGPAForTerm(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public GPAInfo getGPAForTerm(String personId,
                                 String termId,
                                 String calculationTypeKey,
                                 ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return gpasMap.get("gpa1");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCumulativeGPA(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public GPAInfo getCumulativeGPA(
            String personId,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return gpasMap.get("gpa3");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCumulativeGPAForProgram(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public GPAInfo getCumulativeGPAForProgram(
            String personId,
            String programId,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return gpasMap.get("gpa2");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCumulativeGPAForTermAndProgram(java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public GPAInfo getCumulativeGPAForTermAndProgram(
            String personId,
            String programId,
            String termKey,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return gpasMap.get("gpa2");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getLoadForTerm(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LoadInfo getLoadForTerm(
            String personId,
            String termId,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return loadsMap.get("mediumLoad");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getProgramRecords(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentProgramRecordInfo> getProgramRecords(
            String personId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return Collections.singletonList(studentProgramRecordsMap.get("1"));
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getAwardedCredentials(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCredentialRecordInfo> getAwardedCredentials(
            String personId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return Collections.singletonList(studentCredentialRecordsMap.get("1"));
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getTestScoreRecords(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecords(
            String personId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return Collections.singletonList(studentTestScoreRecordsMap.get("1"));
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getTestScoreRecordsByType(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecordsByType(
            String personId,
            String testTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return Collections.singletonList(studentTestScoreRecordsMap.get("2"));
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getEarnedCreditsForTerm(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public String getEarnedCreditsForTerm(
            String personId,
            String termId,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        Integer credits = 0;
        List<StudentCourseRecordInfo> records = studentToCourseRecordsMap.get(personId);
        for (StudentCourseRecordInfo studentCourseRecordInfo : records) {
            credits += Integer.parseInt(studentCourseRecordInfo.getCreditsEarned());
        }
        return String.valueOf(credits);
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getEarnedCredits(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public String getEarnedCredits(
            String personId,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        Integer credits = 0;
        List<StudentCourseRecordInfo> records = studentToCourseRecordsMap.get(personId);
        for (StudentCourseRecordInfo studentCourseRecordInfo : records) {
            credits += Integer.parseInt(studentCourseRecordInfo.getCreditsEarned());
        }
        return String.valueOf(credits);
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getEarnedCumulativeCreditsForProgramAndTerm(java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public String getEarnedCumulativeCreditsForProgramAndTerm(
            String personId,
            String programId,
            String termId,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        Integer credits = 0;
        List<StudentCourseRecordInfo> records = studentToCourseRecordsMap.get(personId);
        for (StudentCourseRecordInfo studentCourseRecordInfo : records) {
            credits += Integer.parseInt(studentCourseRecordInfo.getCreditsEarned());
        }
        return String.valueOf(credits);
    }

    private void createDataForTermResolvers() {
        //StudentProgramRecordInfo
        StudentProgramRecordInfo programRecord = new StudentProgramRecordInfo();
        programRecord.setProgramId("mock.id.program1");
        programRecord.setProgramTitle("Program One");
        programRecord.setProgramCode("MP101");
        programRecord.setProgramTypeKey("mock.program.type.graduate");
        Calendar cal = Calendar.getInstance();
        cal.set(2012, Calendar.JANUARY, 1);
        programRecord.setAdmittedDate(cal.getTime().toString());
        programRecord.setCreditsEarned("2");
        programRecord.setClassStanding("14");
        studentProgramRecordsMap.put("1", programRecord);

        //StudentCredentialRecordInfo
        StudentCredentialRecordInfo credentialRecord = new StudentCredentialRecordInfo();
        credentialRecord.setProgramId("mock.id.program1");
        credentialRecord.setProgramCode("MP101");
        credentialRecord.setProgramTitle("Program One");
        credentialRecord.setAwardingInstitution("Mock University of Kuali");
        cal = Calendar.getInstance();
        cal.set(2012, Calendar.JANUARY, 1);
        credentialRecord.setDateAdmitted(cal.getTime());
        cal.set(2012, Calendar.NOVEMBER, 20);
        credentialRecord.setDateAwarded(cal.getTime());
        studentCredentialRecordsMap.put("1", credentialRecord);

        //StudentTestScoreRecordInfo
        StudentTestScoreRecordInfo testScoreRecord = new StudentTestScoreRecordInfo();
        testScoreRecord.setTestCode("mock.code.test1");
        testScoreRecord.setTestTitle("The First Mock Test");
        testScoreRecord.setTestTypeKey("mock.test.type.first");
        testScoreRecord.setScorePercent("70");
        testScoreRecord.setScoreValue("70");
        cal.set(2012, Calendar.JUNE, 03);
        testScoreRecord.setDateTaken(cal.getTime());
        studentTestScoreRecordsMap.put("1", testScoreRecord);

        testScoreRecord = new StudentTestScoreRecordInfo();
        testScoreRecord.setTestCode("mock.code.test2");
        testScoreRecord.setTestTitle("The Second Mock Test");
        testScoreRecord.setTestTypeKey("mock.test.type.second");
        testScoreRecord.setScorePercent("74");
        testScoreRecord.setScoreValue("74");
        cal.set(2012, Calendar.NOVEMBER, 9);
        testScoreRecord.setDateTaken(cal.getTime());
        studentTestScoreRecordsMap.put("2", testScoreRecord);

        //GPAInfo
        GPAInfo gpa = new GPAInfo();
        gpa.setCalculationTypeKey("mockTypeKey1");
        gpa.setScaleKey("1");
        gpa.setValue("1.9");
        gpasMap.put("gpa1", gpa);
        gpa = new GPAInfo();
        gpa.setCalculationTypeKey("mockTypeKey2");
        gpa.setScaleKey("1");
        gpa.setValue("2.9");
        gpasMap.put("gpa2", gpa);
        gpa = new GPAInfo();
        gpa.setCalculationTypeKey("mockTypeKey3");
        gpa.setScaleKey("1");
        gpa.setValue("3.9");
        gpasMap.put("gpa3", gpa);

        //LoadInfo
        LoadInfo load = new LoadInfo();
        load.setLoadLevelTypeKey("mock.TypeKey.MediumLoad");
        load.setTotalCredits("4");
        loadsMap.put("mediumLoad", load);

    }

}
