/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.academicrecord.service.decorators;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
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

import javax.jws.WebParam;

public class AcademicRecordServiceDecorator 
    implements AcademicRecordService {

    private AcademicRecordService nextDecorator;

    public AcademicRecordService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(AcademicRecordService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public List<StudentCourseRecordInfo> getAttemptedCourseRecordsForTerm(String personId, String termId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAttemptedCourseRecordsForTerm(personId, termId, contextInfo);
    }

    @Override
    public StudentCourseRecordInfo getStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getStudentCourseRecord(studentCourseRecordId, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsByIds(List<String> studentCourseRecordIds,
                                                                      ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStudentCourseRecordsByIds(studentCourseRecordIds, contextInfo);
    }

    @Override
    public List<String> getStudentCourseRecordIdsByType(String studentCourseRecordTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getStudentCourseRecordIdsByType(studentCourseRecordTypeKey, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecords(String personId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getCompletedCourseRecords(personId, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourse(String personId, String courseId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getStudentCourseRecordsForCourse(personId, courseId, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourses(String personId, List<String> courseIds, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getStudentCourseRecordsForCourses(personId, courseIds, contextInfo);
    }

    @Override
    public List<String> searchForStudentCourseRecordIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.searchForStudentCourseRecordIds(criteria, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> searchForStudentCourseRecords(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStudentCourseRecords(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateStudentCourseRecord(String validationTypeKey,
                                                                  String studentCourseRecordTypeKey,
                                                                  StudentCourseRecordInfo studentCourseRecordInfo,
                                                                  ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateStudentCourseRecord(validationTypeKey, studentCourseRecordTypeKey,
                studentCourseRecordInfo, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForCourse(String personId, String courseId,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getCompletedCourseRecordsForCourse(personId, courseId, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(String personId, String termId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getCompletedCourseRecordsForTerm(personId, termId, contextInfo);
    }

    @Override
    public StudentCourseRecordInfo createStudentCourseRecord(String personId,
                                                             String studentCourseRecordTypeKey,
                                                             StudentCourseRecordInfo studentCourseRecord,
                                                             ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return nextDecorator.createStudentCourseRecord(personId, studentCourseRecordTypeKey,
                studentCourseRecord, contextInfo);
    }

    @Override
    public StudentCourseRecordInfo updateStudentCourseRecord(String studentCourseRecordId,
                                                             StudentCourseRecordInfo studentCourseRecord,
                                                             ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateStudentCourseRecord(studentCourseRecordId, studentCourseRecord, contextInfo);
    }

    @Override
    public StatusInfo deleteStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteStudentCourseRecord(studentCourseRecordId, contextInfo);
    }

    @Override
    public GPAInfo getGPAForTerm(String personId, String termId, String calculationTypeKey, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getGPAForTerm(personId, termId, calculationTypeKey, contextInfo);
    }

    @Override
    public GPAInfo getCumulativeGPA(String personId, String calculationTypeKey, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getCumulativeGPA(personId, calculationTypeKey, contextInfo);
    }

    @Override
    public GPAInfo calculateGPA(
            List<StudentCourseRecordInfo> studentCourseRecordInfoList, String calculationTypeKey, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.calculateGPA(studentCourseRecordInfoList, calculationTypeKey, contextInfo);
    }

    @Override
    public GPAInfo getCumulativeGPAForProgram(String personId, String programId, String calculationTypeKey,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getCumulativeGPAForProgram(personId, programId, calculationTypeKey, contextInfo);
    }

    @Override
    public GPAInfo getCumulativeGPAForTermAndProgram(String personId, String programId, String termKey, String calculationTypeKey,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getCumulativeGPAForTermAndProgram(personId, programId, termKey, calculationTypeKey, contextInfo);
    }

    @Override
    public LoadInfo getLoadForTerm(String personId, String termId, String calculationTypeKey, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getLoadForTerm(personId, termId, calculationTypeKey, contextInfo);
    }

    @Override
    public List<StudentProgramRecordInfo> getProgramRecords(String personId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getProgramRecords(personId, contextInfo);
    }

    @Override
    public List<StudentCredentialRecordInfo> getAwardedCredentials(String personId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getAwardedCredentials(personId, contextInfo);
    }

    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecords(String personId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getTestScoreRecords(personId, contextInfo);
    }

    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecordsByType(String personId, String testTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getTestScoreRecordsByType(personId, testTypeKey, contextInfo);
    }

    @Override
    public String getEarnedCreditsForTerm(String personId, String termId, String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getEarnedCreditsForTerm(personId, termId, calculationTypeKey, contextInfo);
    }

    @Override
    public String getEarnedCredits(String personId, String calculationTypeKey, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getEarnedCredits(personId, calculationTypeKey, contextInfo);
    }

    @Override
    public String getEarnedCumulativeCreditsForProgramAndTerm(String personId, String programId, String termId,
            String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getEarnedCumulativeCreditsForProgramAndTerm(personId, programId, termId, calculationTypeKey,
                contextInfo);
    }

    
    
    
}
