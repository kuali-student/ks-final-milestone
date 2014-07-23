/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.academicrecord.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
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

public class AcademicRecordServiceMapImpl implements AcademicRecordService {
	// cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order

    private Map<String, StudentProgramRecordInfo> studentProgramRecordMap = new LinkedHashMap<String, StudentProgramRecordInfo>();
    private Map<String, StudentCourseRecordInfo> studentCourseRecordMap = new LinkedHashMap<String, StudentCourseRecordInfo>();
    private Map<String, StudentCredentialRecordInfo> studentCredentialRecordMap = new LinkedHashMap<String, StudentCredentialRecordInfo>();
    private Map<String, StudentTestScoreRecordInfo> studentTestScoreRecordMap = new LinkedHashMap<String, StudentTestScoreRecordInfo>();
    private Map<String, GPAInfo> gPAMap = new LinkedHashMap<String, GPAInfo>();
    private Map<String, LoadInfo> loadMap = new LinkedHashMap<String, LoadInfo>();

    public void clear() {
        this.studentProgramRecordMap.clear();
        this.studentCourseRecordMap.clear();
        this.studentCredentialRecordMap.clear();
        this.studentTestScoreRecordMap.clear();
        this.gPAMap.clear();
        this.loadMap.clear();
    }

    @Override
    public List<StudentCourseRecordInfo> getAttemptedCourseRecordsForTerm(String personId, String termId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getAttemptedCourseRecordsForTerm has not been implemented");
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecords(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getCompletedCourseRecords has not been implemented");
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForCourse(String personId, String courseId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getCompletedCourseRecordsForCourse has not been implemented");
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourse(String personId, String courseId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getStudentCourseRecordsForCourse has not been implemented");
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourses(String personId, List<String> courseIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getStudentCourseRecordsForCourses has not been implemented");
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(String personId, String termId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getCompletedCourseRecordsForTerm has not been implemented");
    }

    @Override
    public GPAInfo getGPAForTerm(String personId, String termId, String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getGPAForTerm has not been implemented");
    }

    @Override
    public GPAInfo getCumulativeGPA(String personId, String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getCumulativeGPA has not been implemented");
    }

    @Override
    public GPAInfo calculateGPA(List<StudentCourseRecordInfo> studentCourseRecordInfoList, String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("calculateGPA has not been implemented");
    }

    @Override
    public GPAInfo getCumulativeGPAForProgram(String personId, String programId, String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getCumulativeGPAForProgram has not been implemented");
    }

    @Override
    public GPAInfo getCumulativeGPAForTermAndProgram(String personId, String programId, String termKey, String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getCumulativeGPAForTermAndProgram has not been implemented");
    }

    @Override
    public LoadInfo getLoadForTerm(String personId, String termId, String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getLoadForTerm has not been implemented");
    }

    @Override
    public List<StudentProgramRecordInfo> getStudentProgramRecords(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getStudentProgramRecords has not been implemented");
    }

    @Override
    public List<StudentCredentialRecordInfo> getAwardedCredentials(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getAwardedCredentials has not been implemented");
    }

    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecords(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getTestScoreRecords has not been implemented");
    }

    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecordsByType(String personId, String testTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<StudentTestScoreRecordInfo> list = new ArrayList<StudentTestScoreRecordInfo>();
        for (StudentTestScoreRecordInfo info : studentTestScoreRecordMap.values()) {
            if (personId.equals(info.getTypeKey())) {
                if (testTypeKey.equals(info.getTypeKey())) {
                    list.add(new StudentTestScoreRecordInfo (info));
                }
            }
        }
        return list;
    }

    @Override
    public String getEarnedCreditsForTerm(String personId, String termId, String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getEarnedCreditsForTerm has not been implemented");
    }

    @Override
    public String getEarnedCredits(String personId, String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getEarnedCredits has not been implemented");
    }

    @Override
    public String getEarnedCumulativeCreditsForProgramAndTerm(String personId, String programId, String termId,
            String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getEarnedCumulativeCreditsForProgramAndTerm has not been implemented");
    }

    @Override
    public StudentProgramRecordInfo getStudentProgramRecord(String studentProgramRecordId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_ID
        if (!this.studentProgramRecordMap.containsKey(studentProgramRecordId)) {
            throw new DoesNotExistException(studentProgramRecordId);
        }
        return new StudentProgramRecordInfo(this.studentProgramRecordMap.get(studentProgramRecordId));
    }

    @Override
    public List<StudentProgramRecordInfo> getStudentProgramRecordsByIds(List<String> studentProgramRecordIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_IDS
        List<StudentProgramRecordInfo> list = new ArrayList<StudentProgramRecordInfo>();
        for (String id : studentProgramRecordIds) {
            list.add(this.getStudentProgramRecord(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getStudentProgramRecordIdsByType(String studentProgramRecordTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (StudentProgramRecordInfo info : studentProgramRecordMap.values()) {
            if (studentProgramRecordTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForStudentProgramRecordIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForStudentProgramRecordIds has not been implemented");
    }

    @Override
    public List<StudentProgramRecordInfo> searchForStudentProgramRecords(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForStudentProgramRecords has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateStudentProgramRecord(String validationTypeKey, String studentProgramTypeKey,
            StudentProgramRecordInfo studentProgramRecordInfo, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public StudentProgramRecordInfo createStudentProgramRecord(String studentProgramRecordTypeKey, String personId,
            StudentProgramRecordInfo studentProgramRecord, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // CREATE
        if (!studentProgramRecordTypeKey.equals(studentProgramRecord.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        StudentProgramRecordInfo copy = new StudentProgramRecordInfo(studentProgramRecord);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        studentProgramRecordMap.put(copy.getId(), copy);
        return new StudentProgramRecordInfo(copy);
    }

    @Override
    public StudentProgramRecordInfo updateStudentProgramRecord(String studentProgramRecordId,
            StudentProgramRecordInfo studentProgramRecord, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // UPDATE
        if (!studentProgramRecordId.equals(studentProgramRecord.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        StudentProgramRecordInfo copy = new StudentProgramRecordInfo(studentProgramRecord);
        StudentProgramRecordInfo old = this.getStudentProgramRecord(studentProgramRecord.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.studentProgramRecordMap.put(studentProgramRecord.getId(), copy);
        return new StudentProgramRecordInfo(copy);
    }

    @Override
    public StatusInfo deleteStudentProgramRecord(String studentProgramRecordId, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // DELETE
        if (this.studentProgramRecordMap.remove(studentProgramRecordId) == null) {
            throw new OperationFailedException(studentProgramRecordId);
        }
        return newStatus();
    }

    @Override
    public StudentCourseRecordInfo getStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_ID
        if (!this.studentCourseRecordMap.containsKey(studentCourseRecordId)) {
            throw new DoesNotExistException(studentCourseRecordId);
        }
        return new StudentCourseRecordInfo(this.studentCourseRecordMap.get(studentCourseRecordId));
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsByIds(List<String> studentCourseRecordIds, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_IDS
        List<StudentCourseRecordInfo> list = new ArrayList<StudentCourseRecordInfo>();
        for (String id : studentCourseRecordIds) {
            list.add(this.getStudentCourseRecord(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getStudentCourseRecordIdsByType(String studentCourseRecordTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (StudentCourseRecordInfo info : studentCourseRecordMap.values()) {
            if (studentCourseRecordTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForStudentCourseRecordIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForStudentCourseRecordIds has not been implemented");
    }

    @Override
    public List<StudentCourseRecordInfo> searchForStudentCourseRecords(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForStudentCourseRecords has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateStudentCourseRecord(String validationTypeKey, String studentCourseRecordTypeKey,
            StudentCourseRecordInfo studentCourseRecordInfo, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public StudentCourseRecordInfo createStudentCourseRecord(String studentCourseRecordTypeKey, String personId,
            StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // CREATE
        if (!studentCourseRecordTypeKey.equals(studentCourseRecord.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        StudentCourseRecordInfo copy = new StudentCourseRecordInfo(studentCourseRecord);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        studentCourseRecordMap.put(copy.getId(), copy);
        return new StudentCourseRecordInfo(copy);
    }

    @Override
    public StudentCourseRecordInfo updateStudentCourseRecord(String studentCourseRecordId,
            StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // UPDATE
        if (!studentCourseRecordId.equals(studentCourseRecord.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        StudentCourseRecordInfo copy = new StudentCourseRecordInfo(studentCourseRecord);
        StudentCourseRecordInfo old = this.getStudentCourseRecord(studentCourseRecord.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.studentCourseRecordMap.put(studentCourseRecord.getId(), copy);
        return new StudentCourseRecordInfo(copy);
    }

    @Override
    public StatusInfo deleteStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // DELETE
        if (this.studentCourseRecordMap.remove(studentCourseRecordId) == null) {
            throw new OperationFailedException(studentCourseRecordId);
        }
        return newStatus();
    }

    @Override
    public StudentCredentialRecordInfo getStudentCredentialRecord(String studentCredentialRecordId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_ID
        if (!this.studentCredentialRecordMap.containsKey(studentCredentialRecordId)) {
            throw new DoesNotExistException(studentCredentialRecordId);
        }
        return new StudentCredentialRecordInfo(this.studentCredentialRecordMap.get(studentCredentialRecordId));
    }

    @Override
    public List<String> getStudentCredentialRecordIdsByType(String studentCredentialRecordTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (StudentCredentialRecordInfo info : studentCredentialRecordMap.values()) {
            if (studentCredentialRecordTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<StudentCredentialRecordInfo> getStudentCredentialRecordsByIds(List<String> studentCredentialRecordIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_IDS
        List<StudentCredentialRecordInfo> list = new ArrayList<StudentCredentialRecordInfo>();
        for (String id : studentCredentialRecordIds) {
            list.add(this.getStudentCredentialRecord(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> searchForStudentCredentialRecordIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForStudentCredentialRecordIds has not been implemented");
    }

    @Override
    public List<StudentCredentialRecordInfo> searchForStudentCredentialRecords(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForStudentCredentialRecords has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateStudentCredentialRecord(String validationTypeKey, String studentCredentialTypeKey,
            StudentCredentialRecordInfo studentCredentialRecord, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public StudentCredentialRecordInfo createStudentCredentialRecord(String studentCredentialRecordTypeKey, String personId,
            StudentCredentialRecordInfo studentCredentialRecord, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // CREATE
        if (!studentCredentialRecordTypeKey.equals(studentCredentialRecord.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        StudentCredentialRecordInfo copy = new StudentCredentialRecordInfo(studentCredentialRecord);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        studentCredentialRecordMap.put(copy.getId(), copy);
        return new StudentCredentialRecordInfo(copy);
    }

    @Override
    public StudentCredentialRecordInfo updateStudentCredentialRecord(String studentCredentialRecordId,
            StudentCredentialRecordInfo studentCredentialRecord, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // UPDATE
        if (!studentCredentialRecordId.equals(studentCredentialRecord.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        StudentCredentialRecordInfo copy = new StudentCredentialRecordInfo(studentCredentialRecord);
        StudentCredentialRecordInfo old = this.getStudentCredentialRecord(studentCredentialRecord.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.studentCredentialRecordMap.put(studentCredentialRecord.getId(), copy);
        return new StudentCredentialRecordInfo(copy);
    }

    @Override
    public StatusInfo deleteStudentCredentialRecord(String studentCredentialRecordId, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // DELETE
        if (this.studentCredentialRecordMap.remove(studentCredentialRecordId) == null) {
            throw new OperationFailedException(studentCredentialRecordId);
        }
        return newStatus();
    }

    @Override
    public StudentTestScoreRecordInfo getStudentTestScoreRecord(String studentTestScoreRecordId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_ID
        if (!this.studentTestScoreRecordMap.containsKey(studentTestScoreRecordId)) {
            throw new DoesNotExistException(studentTestScoreRecordId);
        }
        return new StudentTestScoreRecordInfo(this.studentTestScoreRecordMap.get(studentTestScoreRecordId));
    }

    @Override
    public List<StudentTestScoreRecordInfo> getStudentTestScoreRecordsByIds(List<String> studentTestScoreRecordIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_IDS
        List<StudentTestScoreRecordInfo> list = new ArrayList<StudentTestScoreRecordInfo>();
        for (String id : studentTestScoreRecordIds) {
            list.add(this.getStudentTestScoreRecord(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getStudentTestScoreRecordIdsByType(String studentTestScoreRecordTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (StudentTestScoreRecordInfo info : studentTestScoreRecordMap.values()) {
            if (studentTestScoreRecordTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForStudentTestScoreRecordIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForStudentTestScoreRecordIds has not been implemented");
    }

    @Override
    public List<StudentTestScoreRecordInfo> searchForStudentTestScoreRecords(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForStudentTestScoreRecords has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateStudentTestScoreRecord(String validationTypeKey,
            String studentTestScoreRecordTypeKey, StudentTestScoreRecordInfo studentTestScoreRecord, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public StudentTestScoreRecordInfo createStudentTestScoreRecord(String studentTestScoreRecordTypeKey, String personId,
            StudentTestScoreRecordInfo studentTestScoreRecord, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // CREATE
        if (!studentTestScoreRecordTypeKey.equals(studentTestScoreRecord.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        StudentTestScoreRecordInfo copy = new StudentTestScoreRecordInfo(studentTestScoreRecord);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        studentTestScoreRecordMap.put(copy.getId(), copy);
        return new StudentTestScoreRecordInfo(copy);
    }

    @Override
    public StudentTestScoreRecordInfo updateStudentTestScoreRecord(String studentTestScoreRecordId,
            StudentTestScoreRecordInfo studentTestScoreRecord, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // UPDATE
        if (!studentTestScoreRecordId.equals(studentTestScoreRecord.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        StudentTestScoreRecordInfo copy = new StudentTestScoreRecordInfo(studentTestScoreRecord);
        StudentTestScoreRecordInfo old = this.getStudentTestScoreRecord(studentTestScoreRecord.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.studentTestScoreRecordMap.put(studentTestScoreRecord.getId(), copy);
        return new StudentTestScoreRecordInfo(copy);
    }

    @Override
    public StatusInfo deleteStudentTestScoreRecord(String studentTestScoreRecordId, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // DELETE
        if (this.studentTestScoreRecordMap.remove(studentTestScoreRecordId) == null) {
            throw new OperationFailedException(studentTestScoreRecordId);
        }
        return newStatus();
    }

    @Override
    public GPAInfo getGPA(String gpaId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_ID
        if (!this.gPAMap.containsKey(gpaId)) {
            throw new DoesNotExistException(gpaId);
        }
        return new GPAInfo(this.gPAMap.get(gpaId));
    }

    @Override
    public List<GPAInfo> getGpasByIds(List<String> gpaIds, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_IDS
        List<GPAInfo> list = new ArrayList<GPAInfo>();
        for (String id : gpaIds) {
            list.add(this.getGPA(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getGPAIdsByType(String gpaTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (GPAInfo info : gPAMap.values()) {
            if (gpaTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<GPAInfo> searchForGpas(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForGpas has not been implemented");
    }

    @Override
    public List<String> searchForGpaIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForGpaIds has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateGPA(String validationTypeKey, String gpaTypeKey, GPAInfo gpa,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public GPAInfo createGPA(String gpaTypeKey, GPAInfo gpa, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // CREATE
        if (!gpaTypeKey.equals(gpa.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        GPAInfo copy = new GPAInfo(gpa);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        gPAMap.put(copy.getId(), copy);
        return new GPAInfo(copy);
    }

    @Override
    public GPAInfo updateGPA(String gpaId, GPAInfo gpa, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // UPDATE
        if (!gpaId.equals(gpa.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        GPAInfo copy = new GPAInfo(gpa);
        GPAInfo old = this.getGPA(gpa.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.gPAMap.put(gpa.getId(), copy);
        return new GPAInfo(copy);
    }

    @Override
    public StatusInfo deleteGPA(String gpaId, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // DELETE
        if (this.gPAMap.remove(gpaId) == null) {
            throw new OperationFailedException(gpaId);
        }
        return newStatus();
    }

    @Override
    public LoadInfo getLoad(String loadId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_ID
        if (!this.loadMap.containsKey(loadId)) {
            throw new DoesNotExistException(loadId);
        }
        return new LoadInfo(this.loadMap.get(loadId));
    }

    @Override
    public List<LoadInfo> getLoadsByIds(List<String> loadIds, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_IDS
        List<LoadInfo> list = new ArrayList<LoadInfo>();
        for (String id : loadIds) {
            list.add(this.getLoad(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getLoadIdsByType(String loadTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (LoadInfo info : loadMap.values()) {
            if (loadTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForLoadIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForLoadIds has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateLoad(String validationTypeKey, String loadTypeKey, LoadInfo load,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public LoadInfo createLoad(String loadTypeKey, LoadInfo load, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // CREATE
        if (!loadTypeKey.equals(load.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        LoadInfo copy = new LoadInfo(load);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        loadMap.put(copy.getId(), copy);
        return new LoadInfo(copy);
    }

    @Override
    public LoadInfo updateLoad(String loadId, LoadInfo load, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // UPDATE
        if (!loadId.equals(load.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        LoadInfo copy = new LoadInfo(load);
        LoadInfo old = this.getLoad(load.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.loadMap.put(load.getId(), copy);
        return new LoadInfo(copy);
    }

    @Override
    public StatusInfo deleteLoad(String loadId, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
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
