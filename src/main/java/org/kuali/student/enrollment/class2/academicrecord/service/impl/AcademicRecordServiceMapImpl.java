/*
 * Copyright 2014 The Kuali Foundation
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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kuali Student Team
 */
public class AcademicRecordServiceMapImpl implements
        AcademicRecordService, MockService {

    private static final Logger log = LoggerFactory.getLogger(AcademicRecordServiceMapImpl.class);
    private Map<String, List<StudentProgramRecordInfo>> studentProgramRecordMap = new LinkedHashMap<String, List<StudentProgramRecordInfo>>();
    private Map<String, List<StudentCourseRecordInfo>> studentCourseRecordMap = new LinkedHashMap<String, List<StudentCourseRecordInfo>>();
    private Map<String, List<StudentCredentialRecordInfo>> studentCredentialRecordMap = new LinkedHashMap<String, List<StudentCredentialRecordInfo>>();
    private Map<String, List<StudentTestScoreRecordInfo>> studentTestScoreRecordMap = new LinkedHashMap<String, List<StudentTestScoreRecordInfo>>();
    private Map<String, List<GPAInfo>> gpaMap = new LinkedHashMap<String, List<GPAInfo>>();
    private Map<String, List<LoadInfo>> loadMap = new LinkedHashMap<String, List<LoadInfo>>();

    /* (non-Javadoc)
     * @see org.kuali.student.common.mock.MockService#clear()
     */
    @Override
    public void clear() {
        studentCourseRecordMap.clear();
        gpaMap.clear();
        loadMap.clear();
        studentProgramRecordMap.clear();
        studentCredentialRecordMap.clear();
        studentTestScoreRecordMap.clear();
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
        List<StudentCourseRecordInfo> studentCourseRecords = new ArrayList<StudentCourseRecordInfo>();
        List<StudentCourseRecordInfo> allStudentCourseRecords = studentCourseRecordMap.get(personId);

        for(StudentCourseRecordInfo studentCourseRecord : allStudentCourseRecords) {
            if(studentCourseRecord.getTermId().equals(termId)) {
                studentCourseRecords.add(studentCourseRecord);
            }
        }
        return studentCourseRecords;
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

        List<StudentCourseRecordInfo> completedStudentCourseRecords = new ArrayList<StudentCourseRecordInfo>();
        List<StudentCourseRecordInfo> allStudentCourseRecords = studentCourseRecordMap.get(personId);

        for(StudentCourseRecordInfo studentCourseRecord : allStudentCourseRecords) {
            if(studentCourseRecord.getAssignedGradeValue() != null
                    || studentCourseRecord.getAdministrativeGradeValue() != null) {
                completedStudentCourseRecords.add(studentCourseRecord);
            }
        }
        return completedStudentCourseRecords;
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

        List<StudentCourseRecordInfo> completedStudentCourseRecords = new ArrayList<StudentCourseRecordInfo>();
        List<StudentCourseRecordInfo> studentCourseRecords = studentCourseRecordMap.get(personId);
        for(StudentCourseRecordInfo studentCourseRecord : studentCourseRecords) {
            if(studentCourseRecord.getId().equals(courseId)
                    && (studentCourseRecord.getAssignedGradeValue() != null
                    || studentCourseRecord.getAdministrativeGradeValue() != null)) {
                completedStudentCourseRecords.add(studentCourseRecord);
            }
        }
        return completedStudentCourseRecords;
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

        List<StudentCourseRecordInfo> completedStudentCourseRecords = new ArrayList<StudentCourseRecordInfo>();
        List<StudentCourseRecordInfo> allStudentCourseRecords = studentCourseRecordMap.get(personId);

        for(StudentCourseRecordInfo studentCourseRecord : allStudentCourseRecords) {
            if(studentCourseRecord.getTermId().equals(termId)
                && (studentCourseRecord.getAssignedGradeValue() != null
                    || studentCourseRecord.getAdministrativeGradeValue() != null)) {
                completedStudentCourseRecords.add(studentCourseRecord);
            }
        }
        return completedStudentCourseRecords;
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
        List<GPAInfo> gpas = gpaMap.get(personId);
        for(GPAInfo gpa : gpas) {
            if(gpa.getCalculationTypeKey().equals(calculationTypeKey)) { // TODO: how to get GPA by term?
                return gpa;
            }
        }
        throw new OperationFailedException("Couldn't find GPA for personId:" + personId
                + ", termId: " + termId + ", calculationTypeKey: " + calculationTypeKey);
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
        List<GPAInfo> gpas = gpaMap.get(personId);
        for(GPAInfo gpa : gpas) {
            if(gpa.getCalculationTypeKey().equals(calculationTypeKey)) {
                return gpa;
            }
        }
        throw new OperationFailedException("Couldn't find GPA for personId:" + personId
                + ", calculationTypeKey: " + calculationTypeKey);
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#calculateGPA(java.util.List<org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo>, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public GPAInfo calculateGPA(List<StudentCourseRecordInfo> studentCourseRecordInfoList, String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        //This is a mock GPA calculation
        float totalCredits = 0.0f;
        float gradePoints = 0.0f;
        for (StudentCourseRecordInfo info : studentCourseRecordInfoList){
            float creditsForGPA = Float.parseFloat(info.getCreditsForGPA());
            gradePoints += Float.parseFloat(info.getCalculatedGradeValue())*creditsForGPA;
            totalCredits += creditsForGPA;
        }

        GPAInfo gpa = new GPAInfo();
        gpa.setCalculationTypeKey(calculationTypeKey);
        gpa.setScaleKey("1");
        gpa.setValue(String.valueOf(gradePoints/totalCredits));
        return gpa;
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

        List<GPAInfo> gpas = gpaMap.get(personId);
        for(GPAInfo gpa : gpas) {
            if(gpa.getCalculationTypeKey().equals(calculationTypeKey)
                    && gpa.getProgramId().equals(programId)) {
                return gpa;
            }
        }
        throw new OperationFailedException("Couldn't find GPA for personId:" + personId
                + ", calculationTypeKey: " + calculationTypeKey
                + ", programId: " + programId);
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

        List<GPAInfo> gpas = gpaMap.get(personId);
        for(GPAInfo gpa : gpas) {
            if(gpa.getCalculationTypeKey().equals(calculationTypeKey)
                    && gpa.getProgramId().equals(programId)) { // TODO: how to get termId of GPA?
                return gpa;
            }
        }
        throw new OperationFailedException("Couldn't find GPA for personId:" + personId
                + ", calculationTypeKey: " + calculationTypeKey
                + ", programId: " + programId);
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getLoadForTerm(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LoadInfo getLoadForTerm(
            String personId,
            String termId,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<LoadInfo> loads = loadMap.get(personId);
        for(LoadInfo load : loads) {
            if(load.getLoadLevelTypeKey().equals(loadLevelTypeKey)) { // TODO: how to get termId of Load?
                return load;
            }
        }
        throw new OperationFailedException("Couldn't find GPA for personId:" + personId
                + ", calculationTypeKey: " + loadLevelTypeKey
                + ", termId: " + termId);
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getProgramRecords(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentProgramRecordInfo> getStudentProgramRecords(
            String personId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<StudentProgramRecordInfo> resultsList = new ArrayList<StudentProgramRecordInfo>();

        if (!studentProgramRecordMap.keySet().contains(personId))
            throw new DoesNotExistException("No program records for student Id = " + personId);

        return studentProgramRecordMap.get(personId);

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

        List<StudentCredentialRecordInfo> studentCredentialRecords = studentCredentialRecordMap.get(personId);
        return studentCredentialRecords;
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

        List<StudentTestScoreRecordInfo> studentTestScoreRecords = studentTestScoreRecordMap.get(personId);

        return studentTestScoreRecords;
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getTestScoreRecordsByType(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecordsByType(
            String personId,
            String studentTestScoreRecordTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<StudentTestScoreRecordInfo> found = new ArrayList<StudentTestScoreRecordInfo>();
        Set<String> personIdKeys = studentTestScoreRecordMap.keySet();
        for(String personIdKey : personIdKeys) {
            List<StudentTestScoreRecordInfo> studentTestScoreRecords = studentTestScoreRecordMap.get(personIdKey);
            for(StudentTestScoreRecordInfo studentTestScoreRecord : studentTestScoreRecords) {
                if(studentTestScoreRecordTypeKey.equals(studentTestScoreRecord.getTypeKey())) {
                    found.add(studentTestScoreRecord);
                }
            }
        }
        return found;
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
        List<StudentCourseRecordInfo> records = studentCourseRecordMap.get(personId);
        for (StudentCourseRecordInfo studentCourseRecordInfo : records) { // TODO: what about term?
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
        List<StudentCourseRecordInfo> records = studentCourseRecordMap.get(personId);
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
            String termName,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        Integer credits = 0;
        List<StudentCourseRecordInfo> records = studentCourseRecordMap.get(personId);
        for (StudentCourseRecordInfo studentCourseRecordInfo : records) {
            if(studentCourseRecordInfo.getTermName().equals(termName)) { // TODO: what about programId?
                credits += Integer.parseInt(studentCourseRecordInfo.getCreditsEarned());
            }
        }
        return String.valueOf(credits);
    }


    @Override
    public StudentProgramRecordInfo getStudentProgramRecord(String studentProgramRecordId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_ID
        Set<String> personIdKeys = studentProgramRecordMap.keySet();
        for(String personIdKey : personIdKeys) {
            List<StudentProgramRecordInfo> studentProgramRecords = studentProgramRecordMap.get(personIdKey);
            for(StudentProgramRecordInfo studentProgramRecord : studentProgramRecords) {
                if(studentProgramRecordId.equals(studentProgramRecord.getId())) {
                    return studentProgramRecord;
                }
            }
        }
        throw new OperationFailedException("Could not find StudentProgramRecord: " + studentProgramRecordId);
    }

    @Override
    public List<StudentProgramRecordInfo> getStudentProgramRecordsByIds(List<String> studentProgramRecordIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<StudentProgramRecordInfo> list = new ArrayList<StudentProgramRecordInfo> ();
        for (String id: studentProgramRecordIds) {
            list.add (this.getStudentProgramRecord(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getStudentProgramRecordIdsByType(String studentProgramRecordTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> ids = new ArrayList<String> ();
        Set<String> personIdKeys = studentProgramRecordMap.keySet();
        for(String personIdKey : personIdKeys) {
            List<StudentProgramRecordInfo> studentProgramRecords = studentProgramRecordMap.get(personIdKey);
            for(StudentProgramRecordInfo studentProgramRecord : studentProgramRecords) {
                if(studentProgramRecordTypeKey.equals(studentProgramRecord.getTypeKey())) {
                    ids.add(studentProgramRecord.getId());
                }
            }
        }
        return ids;
    }

    @Override
    public List<String> searchForStudentProgramRecordIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForStudentProgramRecordIds has not been implemented");
    }

    @Override
    public List<StudentProgramRecordInfo> searchForStudentProgramRecords(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForStudentProgramRecords has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateStudentProgramRecord(String validationTypeKey, String studentProgramTypeKey, StudentProgramRecordInfo studentProgramRecordInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // VALIDATE
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public StudentProgramRecordInfo createStudentProgramRecord(String studentProgramRecordTypeKey, String personId, StudentProgramRecordInfo studentProgramRecord, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // CREATE
        if (!studentProgramRecordTypeKey.equals (studentProgramRecord.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        StudentProgramRecordInfo copy = new StudentProgramRecordInfo(studentProgramRecord);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        List<StudentProgramRecordInfo> studentProgramRecords = studentProgramRecordMap.get(personId);

        if (studentProgramRecords == null) {
            studentProgramRecords = new ArrayList<StudentProgramRecordInfo>();
            studentProgramRecordMap.put(personId, studentProgramRecords);
        }

        studentProgramRecords.add(copy);
        return new StudentProgramRecordInfo(copy);
    }

    @Override
    public StudentProgramRecordInfo updateStudentProgramRecord(String studentProgramRecordId, StudentProgramRecordInfo studentProgramRecord, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // UPDATE
        if (!studentProgramRecordId.equals (studentProgramRecord.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        StudentProgramRecordInfo copy = new StudentProgramRecordInfo(studentProgramRecord);
        StudentProgramRecordInfo old = this.getStudentProgramRecord(studentProgramRecord.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        List<StudentProgramRecordInfo> studentProgramRecords = studentProgramRecordMap.get(studentProgramRecord.getPersonId());
        if (studentProgramRecords == null) {
            studentProgramRecords = new ArrayList<StudentProgramRecordInfo>();
        }
        studentProgramRecordMap.put(studentProgramRecord.getPersonId(), studentProgramRecords);
        studentProgramRecords.add(copy);
        return new StudentProgramRecordInfo(copy);
    }

    @Override
    public StatusInfo deleteStudentProgramRecord(String studentProgramRecordId, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // DELETE
        if (this.studentProgramRecordMap.remove(studentProgramRecordId) == null) {
            throw new OperationFailedException(studentProgramRecordId);
        }
        return newStatus();
    }

    @Override
    public StudentCourseRecordInfo getStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        Set<String> personIdKeys = studentCourseRecordMap.keySet();
        for(String personIdKey : personIdKeys) {
            List<StudentCourseRecordInfo> studentCourseRecords = studentCourseRecordMap.get(personIdKey);
            for(StudentCourseRecordInfo studentCourseRecord : studentCourseRecords) {
                if(studentCourseRecordId.equals(studentCourseRecord.getId())) {
                    return studentCourseRecord;
                }
            }
        }
        throw new OperationFailedException(studentCourseRecordId);
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsByIds(List<String> studentCourseRecordIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<StudentCourseRecordInfo> list = new ArrayList<StudentCourseRecordInfo> ();
        for (String id: studentCourseRecordIds) {
            list.add (this.getStudentCourseRecord(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getStudentCourseRecordIdsByType(String studentCourseRecordTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> ids = new ArrayList<String> ();
        Set<String> personIdKeys = studentCourseRecordMap.keySet();
        for(String personIdKey : personIdKeys) {
            List<StudentCourseRecordInfo> studentCourseRecords = studentCourseRecordMap.get(personIdKey);
            for(StudentCourseRecordInfo studentCourseRecord : studentCourseRecords) {
                if(studentCourseRecordTypeKey.equals(studentCourseRecord.getTypeKey())) {
                    ids.add(studentCourseRecord.getId());
                }
            }
        }
        return ids;
    }

    @Override
    public List<String> searchForStudentCourseRecordIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForStudentCourseRecordIds has not been implemented");
    }

    @Override
    public List<StudentCourseRecordInfo> searchForStudentCourseRecords(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForStudentCourseRecords has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateStudentCourseRecord(String validationTypeKey, String studentCourseRecordTypeKey, StudentCourseRecordInfo studentCourseRecordInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // VALIDATE
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public StudentCourseRecordInfo createStudentCourseRecord(String studentCourseRecordTypeKey, String personId, StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // CREATE
        if (!studentCourseRecordTypeKey.equals (studentCourseRecord.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        StudentCourseRecordInfo copy = new StudentCourseRecordInfo(studentCourseRecord);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        List<StudentCourseRecordInfo> studentCourseRecords = studentCourseRecordMap.get(personId);
        if(studentCourseRecords == null) {
            studentCourseRecords = new ArrayList<StudentCourseRecordInfo>();
            studentCourseRecordMap.put(personId, studentCourseRecords);
        }
        studentCourseRecords.add(copy);
        return new StudentCourseRecordInfo(copy);
    }

    @Override
    public StudentCourseRecordInfo updateStudentCourseRecord(String studentCourseRecordId, StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // UPDATE
        if (!studentCourseRecordId.equals (studentCourseRecord.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        StudentCourseRecordInfo copy = new StudentCourseRecordInfo(studentCourseRecord);
        StudentCourseRecordInfo old = this.getStudentCourseRecord(studentCourseRecord.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        String personId = studentCourseRecord.getPersonId();
        List<StudentCourseRecordInfo> studentCourseRecords = studentCourseRecordMap.get(personId);
        if(studentCourseRecords == null) {
            throw new InvalidParameterException ("Attempted update, but the student course record: "
                    + studentCourseRecordId + " does not exist for student: " + personId);
        }
        studentCourseRecords.add(copy);
        studentCourseRecordMap.put(personId, studentCourseRecords);
        return new StudentCourseRecordInfo(copy);
    }

    @Override
    public StatusInfo deleteStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // DELETE
        if (this.studentCourseRecordMap.remove(studentCourseRecordId) == null) {
            throw new OperationFailedException(studentCourseRecordId);
        }
        return newStatus();
    }

    @Override
    public StudentCredentialRecordInfo getStudentCredentialRecord(String studentCredentialRecordId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {

        Set<String> keys = studentCredentialRecordMap.keySet();
        for(String key : keys) {
            List<StudentCredentialRecordInfo> studentCredentialRecords = studentCredentialRecordMap.get(key);
            for(StudentCredentialRecordInfo studentCredentialRecord : studentCredentialRecords) {
                if(studentCredentialRecordId.equals(studentCredentialRecord.getId())) {
                    return new StudentCredentialRecordInfo(studentCredentialRecord);
                }
            }
        }
        throw new OperationFailedException("StudentCredentialRecord not found: " + studentCredentialRecordId);
    }

    @Override
    public List<String> getStudentCredentialRecordIdsByType(String studentCredentialRecordTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> ids = new ArrayList<String> ();
        Set<String> personIdKeys = studentCredentialRecordMap.keySet();
        for(String personIdKey : personIdKeys) {
            List<StudentCredentialRecordInfo> studentCredentialRecords = studentCredentialRecordMap.get(personIdKey);
            for(StudentCredentialRecordInfo studentCredentialRecord : studentCredentialRecords) {
                if(studentCredentialRecordTypeKey.equals(studentCredentialRecord.getTypeKey())) {
                    ids.add(studentCredentialRecord.getId());
                }
            }
        }
        return ids;
    }

    @Override
    public List<StudentCredentialRecordInfo> getStudentCredentialRecordsByIds(List<String> studentCredentialRecordIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<StudentCredentialRecordInfo> list = new ArrayList<StudentCredentialRecordInfo> ();
        for (String id: studentCredentialRecordIds) {
            list.add (this.getStudentCredentialRecord(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> searchForStudentCredentialRecordIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForStudentCredentialRecordIds has not been implemented");
    }

    @Override
    public List<StudentCredentialRecordInfo> searchForStudentCredentialRecords(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForStudentCredentialRecords has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateStudentCredentialRecord(String validationTypeKey, String studentCredentialTypeKey, StudentCredentialRecordInfo studentCredentialRecord, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // VALIDATE
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public StudentCredentialRecordInfo createStudentCredentialRecord(String studentCredentialRecordTypeKey, String personId, StudentCredentialRecordInfo studentCredentialRecord, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // CREATE
        if (!studentCredentialRecordTypeKey.equals (studentCredentialRecord.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        StudentCredentialRecordInfo copy = new StudentCredentialRecordInfo(studentCredentialRecord);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        List<StudentCredentialRecordInfo> studentCredentialRecords = studentCredentialRecordMap.get(personId);

        if (studentCredentialRecords == null) {
            studentCredentialRecords = new ArrayList<StudentCredentialRecordInfo>();
            studentCredentialRecordMap.put(personId, studentCredentialRecords);
        }

        studentCredentialRecords.add(copy);
        return new StudentCredentialRecordInfo(copy);
    }

    @Override
    public StudentCredentialRecordInfo updateStudentCredentialRecord(String studentCredentialRecordId, StudentCredentialRecordInfo studentCredentialRecord, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // UPDATE
        if (!studentCredentialRecordId.equals (studentCredentialRecord.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        StudentCredentialRecordInfo copy = new StudentCredentialRecordInfo(studentCredentialRecord);
        StudentCredentialRecordInfo old = this.getStudentCredentialRecord(studentCredentialRecord.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        List<StudentCredentialRecordInfo> studentCredentialRecords = studentCredentialRecordMap.get(studentCredentialRecord.getPersonId());
        if (studentCredentialRecords == null) {
            studentCredentialRecords = new ArrayList<StudentCredentialRecordInfo>();
        }
        studentCredentialRecords.add(copy);
        studentCredentialRecordMap.put(studentCredentialRecord.getPersonId(), studentCredentialRecords);
        return new StudentCredentialRecordInfo(copy);
    }

    @Override
    public StatusInfo deleteStudentCredentialRecord(String studentCredentialRecordId, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // DELETE
        if (this.studentCredentialRecordMap.remove(studentCredentialRecordId) == null) {
            throw new OperationFailedException(studentCredentialRecordId);
        }
        return newStatus();
    }

    @Override
    public StudentTestScoreRecordInfo getStudentTestScoreRecord(String studentTestScoreRecordId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_ID
        Set<String> personIdKeys = studentTestScoreRecordMap.keySet();
        for(String personIdKey : personIdKeys) {
            List<StudentTestScoreRecordInfo> studentTestScoreRecords = studentTestScoreRecordMap.get(personIdKey);
            for(StudentTestScoreRecordInfo studentTestScoreRecord : studentTestScoreRecords) {
                if(studentTestScoreRecordId.equals(studentTestScoreRecord.getId())) {
                    return studentTestScoreRecord;
                }
            }
        }
        throw new OperationFailedException("Could not find StudentTestScoreRecord: " + studentTestScoreRecordId);
    }

    @Override
    public List<StudentTestScoreRecordInfo> getStudentTestScoreRecordsByIds(List<String> studentTestScoreRecordIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<StudentTestScoreRecordInfo> list = new ArrayList<StudentTestScoreRecordInfo> ();
        for (String id: studentTestScoreRecordIds) {
            list.add (this.getStudentTestScoreRecord(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getStudentTestScoreRecordIdsByType(String studentTestScoreRecordTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> ids = new ArrayList<String> ();
        Set<String> personIdKeys = studentTestScoreRecordMap.keySet();
        for(String personIdKey : personIdKeys) {
            List<StudentTestScoreRecordInfo> studentTestScoreRecords = studentTestScoreRecordMap.get(personIdKey);
            for(StudentTestScoreRecordInfo studentTestScoreRecord : studentTestScoreRecords) {
                if(studentTestScoreRecordTypeKey.equals(studentTestScoreRecord.getTypeKey())) {
                    ids.add(studentTestScoreRecord.getId());
                }
            }
        }
        return ids;
    }

    @Override
    public List<String> searchForStudentTestScoreRecordIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForStudentTestScoreRecordIds has not been implemented");
    }

    @Override
    public List<StudentTestScoreRecordInfo> searchForStudentTestScoreRecords(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForStudentTestScoreRecords has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateStudentTestScoreRecord(String validationTypeKey, String studentTestScoreRecordTypeKey, StudentTestScoreRecordInfo studentTestScoreRecord, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // VALIDATE
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public StudentTestScoreRecordInfo createStudentTestScoreRecord(String studentTestScoreRecordTypeKey, String personId, StudentTestScoreRecordInfo studentTestScoreRecord, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // CREATE
        if (!studentTestScoreRecordTypeKey.equals (studentTestScoreRecord.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        StudentTestScoreRecordInfo copy = new StudentTestScoreRecordInfo(studentTestScoreRecord);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        List<StudentTestScoreRecordInfo> studentTestScoreRecords = studentTestScoreRecordMap.get(personId);

        if (studentTestScoreRecords == null) {
            studentTestScoreRecords = new ArrayList<StudentTestScoreRecordInfo>();
            studentTestScoreRecordMap.put(personId, studentTestScoreRecords);
        }

        studentTestScoreRecords.add(copy);
        return new StudentTestScoreRecordInfo(copy);
    }

    @Override
    public StudentTestScoreRecordInfo updateStudentTestScoreRecord(String studentTestScoreRecordId, StudentTestScoreRecordInfo studentTestScoreRecord, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // UPDATE
        if (!studentTestScoreRecordId.equals (studentTestScoreRecord.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        StudentTestScoreRecordInfo copy = new StudentTestScoreRecordInfo(studentTestScoreRecord);
        StudentTestScoreRecordInfo old = this.getStudentTestScoreRecord(studentTestScoreRecord.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        List<StudentTestScoreRecordInfo> studentTestScoreRecords = studentTestScoreRecordMap.get(studentTestScoreRecord.getPersonId());
        if (studentTestScoreRecords == null) {
            studentTestScoreRecords = new ArrayList<StudentTestScoreRecordInfo>();
        }
        studentTestScoreRecordMap.put(studentTestScoreRecord.getPersonId(), studentTestScoreRecords);
        studentTestScoreRecords.add(copy);
        return new StudentTestScoreRecordInfo(copy);
    }

    @Override
    public StatusInfo deleteStudentTestScoreRecord(String studentTestScoreRecordId, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // DELETE
        if (this.studentTestScoreRecordMap.remove(studentTestScoreRecordId) == null) {
            throw new OperationFailedException(studentTestScoreRecordId);
        }
        return newStatus();
    }

    @Override
    public GPAInfo getGPA(String gpaId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_ID
        Set<String> personIdKeys = gpaMap.keySet();
        for(String personIdKey : personIdKeys) {
            List<GPAInfo> gpas = gpaMap.get(personIdKey);
            for(GPAInfo gpa : gpas) {
                if(gpaId.equals(gpa.getId())) {
                    return gpa;
                }
            }
        }
        throw new OperationFailedException("Could not find GPA: " + gpaId);
    }

    @Override
    public List<GPAInfo> getGpasByIds(List<String> gpaIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<GPAInfo> list = new ArrayList<GPAInfo> ();
        for (String id: gpaIds) {
            list.add (this.getGPA(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getGPAIdsByType(String gpaTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> ids = new ArrayList<String> ();
        Set<String> personIdKeys = gpaMap.keySet();
        for(String personIdKey : personIdKeys) {
            List<GPAInfo> gpas = gpaMap.get(personIdKey);
            for(GPAInfo gpa : gpas) {
                if(gpaTypeKey.equals(gpa.getTypeKey())) {
                    ids.add(gpa.getId());
                }
            }
        }
        return ids;
    }

    @Override
    public List<GPAInfo> searchForGpas(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForGpas has not been implemented");
    }

    @Override
    public List<String> searchForGpaIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForGpaIds has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateGPA(String validationTypeKey, String gpaTypeKey, GPAInfo gpa, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // VALIDATE
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public GPAInfo createGPA(String gpaTypeKey, GPAInfo gpa, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // CREATE
        if (!gpaTypeKey.equals (gpa.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        GPAInfo copy = new GPAInfo(gpa);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        String personId = gpa.getPersonId();
        List<GPAInfo> gpas = gpaMap.get(personId);

        if (gpas == null) {
            gpas = new ArrayList<GPAInfo>();
            gpaMap.put(personId, gpas);
        }

        gpas.add(copy);
        return new GPAInfo(copy);
    }

    @Override
    public GPAInfo updateGPA(String gpaId, GPAInfo gpa, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // UPDATE
        if (!gpaId.equals (gpa.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        GPAInfo copy = new GPAInfo(gpa);
        GPAInfo old = this.getGPA(gpa.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        List<GPAInfo> gpas = gpaMap.get(gpa.getPersonId());
        if (gpas == null) {
            gpas = new ArrayList<GPAInfo>();
        }
        gpaMap.put(gpa.getPersonId(), gpas);
        gpas.add(copy);
        return new GPAInfo(copy);
    }

    @Override
    public StatusInfo deleteGPA(String gpaId, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // DELETE
        if (this.gpaMap.remove(gpaId) == null) {
            throw new OperationFailedException(gpaId);
        }
        return newStatus();
    }

    @Override
    public LoadInfo getLoad(String loadId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_ID
        Set<String> personIdKeys = loadMap.keySet();
        for(String personIdKey : personIdKeys) {
            List<LoadInfo> loads = loadMap.get(personIdKey);
            for(LoadInfo load : loads) {
                if(loadId.equals(load.getId())) {
                    return load;
                }
            }
        }
        throw new OperationFailedException("Could not find Load: " + loadId);
    }

    @Override
    public List<LoadInfo> getLoadsByIds(List<String> loadIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<LoadInfo> list = new ArrayList<LoadInfo> ();
        for (String id: loadIds) {
            list.add (this.getLoad(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getLoadIdsByType(String loadTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> ids = new ArrayList<String> ();
        Set<String> personIdKeys = loadMap.keySet();
        for(String personIdKey : personIdKeys) {
            List<LoadInfo> loads = loadMap.get(personIdKey);
            for(LoadInfo load : loads) {
                if(loadTypeKey.equals(load.getTypeKey())) {
                    ids.add(load.getId());
                }
            }
        }
        return ids;
    }

    @Override
    public List<String> searchForLoadIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForLoadIds has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateLoad(String validationTypeKey, String loadTypeKey, LoadInfo load, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // VALIDATE
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public LoadInfo createLoad(String loadTypeKey, LoadInfo load, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // CREATE
        if (!loadTypeKey.equals (load.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        LoadInfo copy = new LoadInfo(load);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        String personId = load.getPersonId();
        List<LoadInfo> loads = loadMap.get(personId);

        if (loads == null) {
            loads = new ArrayList<LoadInfo>();
            loadMap.put(personId, loads);
        }

        loads.add(copy);
        return new LoadInfo(copy);
    }

    @Override
    public LoadInfo updateLoad(String loadId, LoadInfo load, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // UPDATE
        if (!loadId.equals (load.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        LoadInfo copy = new LoadInfo(load);
        LoadInfo old = this.getLoad(load.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        List<LoadInfo> loads = loadMap.get(load.getPersonId());
        if (loads == null) {
            loads = new ArrayList<LoadInfo>();
        }
        loadMap.put(load.getPersonId(), loads);
        loads.add(copy);
        return new LoadInfo(copy);
    }

    @Override
    public StatusInfo deleteLoad(String loadId, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // DELETE
        if (this.loadMap.remove(loadId) == null) {
            throw new OperationFailedException(loadId);
        }
        return newStatus();
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }
}
