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

/**
 * @author Kuali Student Team
 */
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
    public List<StudentCourseRecordInfo> getCompletedCourseRecords(String personId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getCompletedCourseRecords(personId, contextInfo);
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
    public List<StudentProgramRecordInfo> getStudentProgramRecords(String personId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return nextDecorator.getStudentProgramRecords(personId, contextInfo);
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

    @Override
    public StudentProgramRecordInfo getStudentProgramRecord(String studentProgramRecordId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStudentProgramRecord(studentProgramRecordId, contextInfo);
    }

    @Override
    public StudentCourseRecordInfo getStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStudentCourseRecord(studentCourseRecordId, contextInfo);
    }

    @Override
    public StudentCredentialRecordInfo getStudentCredentialRecord(String studentCredentialRecordId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStudentCredentialRecord(studentCredentialRecordId, contextInfo);
    }

    @Override
    public StudentTestScoreRecordInfo getStudentTestScoreRecord(String studentTestScoreRecordId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStudentTestScoreRecord(studentTestScoreRecordId, contextInfo);
    }

    @Override
    public GPAInfo getGPA(String gpaId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getGPA(gpaId, contextInfo);
    }

    @Override
    public LoadInfo getLoad(String loadId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLoad(loadId, contextInfo);
    }

    @Override
    public List<StudentProgramRecordInfo> getStudentProgramRecordsByIds(List<String> studentProgramRecordIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStudentProgramRecordsByIds(studentProgramRecordIds, contextInfo);
    }

    @Override
    public List<StudentCredentialRecordInfo> getStudentCredentialRecordsByIds(List<String> studentCredentialRecordIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStudentCredentialRecordsByIds(studentCredentialRecordIds, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsByIds(List<String> studentCourseRecordIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStudentCourseRecordsByIds(studentCourseRecordIds, contextInfo);
    }

    @Override
    public List<StudentTestScoreRecordInfo> getStudentTestScoreRecordsByIds(List<String> studentTestScoreRecordIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStudentTestScoreRecordsByIds(studentTestScoreRecordIds, contextInfo);
    }

    @Override
    public List<GPAInfo> getGpasByIds(List<String> gpaIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getGpasByIds(gpaIds, contextInfo);
    }

    @Override
    public List<LoadInfo> getLoadsByIds(List<String> loadIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLoadsByIds(loadIds, contextInfo);
    }

    @Override
    public List<String> getStudentProgramRecordIdsByType(String studentProgramRecordTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStudentProgramRecordIdsByType(studentProgramRecordTypeKey, contextInfo);
    }

    @Override
    public List<String> getStudentCourseRecordIdsByType(String studentCourseRecordTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStudentCourseRecordIdsByType(studentCourseRecordTypeKey, contextInfo);
    }

    @Override
    public List<String> getStudentCredentialRecordIdsByType(String studentCredentialRecordTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStudentCredentialRecordIdsByType(studentCredentialRecordTypeKey, contextInfo);
    }

    @Override
    public List<String> getStudentTestScoreRecordIdsByType(String studentTestScoreTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getStudentTestScoreRecordIdsByType(studentTestScoreTypeKey, contextInfo);
    }

    @Override
    public List<String> getGPAIdsByType(String gpaTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getGPAIdsByType(gpaTypeKey, contextInfo);
    }

    @Override
    public List<String> getLoadIdsByType(String loadTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.getLoadIdsByType(loadTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForStudentProgramRecordIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStudentProgramRecordIds(criteria, contextInfo);
    }

    @Override
    public List<String> searchForStudentCourseRecordIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStudentCourseRecordIds(criteria, contextInfo);
    }

    @Override
    public List<String> searchForStudentTestScoreRecordIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStudentTestScoreRecordIds(criteria, contextInfo);
    }

    @Override
    public List<String> searchForStudentCredentialRecordIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStudentCredentialRecordIds(criteria, contextInfo);
    }

    @Override
    public List<String> searchForGpaIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForGpaIds(criteria, contextInfo);
    }

    @Override
    public List<String> searchForLoadIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForLoadIds(criteria, contextInfo);
    }

    @Override
    public List<StudentProgramRecordInfo> searchForStudentProgramRecords(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStudentProgramRecords(criteria, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> searchForStudentCourseRecords(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStudentCourseRecords(criteria, contextInfo);
    }

    @Override
    public List<StudentCredentialRecordInfo> searchForStudentCredentialRecords(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStudentCredentialRecords(criteria, contextInfo);
    }

    @Override
    public List<StudentTestScoreRecordInfo> searchForStudentTestScoreRecords(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForStudentTestScoreRecords(criteria, contextInfo);
    }

    @Override
    public List<GPAInfo> searchForGpas(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.searchForGpas(criteria, contextInfo);
    }

    @Override
    public StudentProgramRecordInfo createStudentProgramRecord(String studentProgramRecordTypeKey,
                                                               String studentId,
                                                               StudentProgramRecordInfo studentProgramRecord,
                                                               ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createStudentProgramRecord(studentProgramRecordTypeKey, studentId, studentProgramRecord, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateStudentProgramRecord(String validationTypeKey, String objectTypeKey, StudentProgramRecordInfo studentProgramRecordInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateStudentProgramRecord(validationTypeKey, objectTypeKey, studentProgramRecordInfo, contextInfo);
    }

    @Override
    public StudentProgramRecordInfo updateStudentProgramRecord(String studentProgramRecordId, StudentProgramRecordInfo studentProgramRecord, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateStudentProgramRecord(studentProgramRecordId, studentProgramRecord, contextInfo);
    }

    @Override
    public StatusInfo deleteStudentProgramRecord(String studentProgramRecordId, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteStudentProgramRecord(studentProgramRecordId, contextInfo);
    }

    @Override
    public StudentCourseRecordInfo createStudentCourseRecord(String studentId, String courseRegistrationId, StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createStudentCourseRecord(studentId, courseRegistrationId, studentCourseRecord, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateStudentCourseRecord(String validationTypeKey, String objectTypeKey, StudentCourseRecordInfo studentCourseRecordInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateStudentCourseRecord(validationTypeKey, objectTypeKey, studentCourseRecordInfo, contextInfo);
    }

    @Override
    public StudentCourseRecordInfo updateStudentCourseRecord(String studentCourseRecordId, StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateStudentCourseRecord(studentCourseRecordId, studentCourseRecord, contextInfo);
    }

    @Override
    public StatusInfo deleteStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteStudentCourseRecord(studentCourseRecordId, contextInfo);
    }

    @Override
    public StudentCredentialRecordInfo createStudentCredentialRecord(String studentCredentialRecordTypeKey,
                                                                     String studentId, StudentCredentialRecordInfo studentCredentialRecord,
                                                                     ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createStudentCredentialRecord(studentCredentialRecordTypeKey, studentId, studentCredentialRecord, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateStudentCredentialRecord(String validationTypeKey, String objectTypeKey, StudentCredentialRecordInfo studentCredentialRecordInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateStudentCredentialRecord(validationTypeKey, objectTypeKey, studentCredentialRecordInfo, contextInfo);
    }

    @Override
    public StudentCredentialRecordInfo updateStudentCredentialRecord(String studentCredentialRecordId, StudentCredentialRecordInfo studentCredentialRecord, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateStudentCredentialRecord(studentCredentialRecordId, studentCredentialRecord, contextInfo);
    }

    @Override
    public StatusInfo deleteStudentCredentialRecord(String studentCredentialRecordId, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteStudentCredentialRecord(studentCredentialRecordId, contextInfo);
    }

    @Override
    public StudentTestScoreRecordInfo createStudentTestScoreRecord(String studentTestScoreRecordTypeKey,
                                                                   String studentId,
                                                                   StudentTestScoreRecordInfo studentTestScoreRecord,
                                                                   ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createStudentTestScoreRecord(studentTestScoreRecordTypeKey, studentId, studentTestScoreRecord, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateStudentTestScoreRecord(String validationTypeKey, String objectTypeKey, StudentTestScoreRecordInfo studentTestScoreRecordInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateStudentTestScoreRecord(validationTypeKey, objectTypeKey, studentTestScoreRecordInfo, contextInfo);
    }

    @Override
    public StudentTestScoreRecordInfo updateStudentTestScoreRecord(String studentTestScoreRecordId, StudentTestScoreRecordInfo studentTestScoreRecord, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateStudentTestScoreRecord(studentTestScoreRecordId, studentTestScoreRecord, contextInfo);
    }

    @Override
    public StatusInfo deleteStudentTestScoreRecord(String studentTestScoreRecordId, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteStudentTestScoreRecord(studentTestScoreRecordId, contextInfo);
    }

    @Override
    public GPAInfo createGPA(String gpaTypeKey, GPAInfo gpa, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createGPA(gpaTypeKey, gpa, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateGPA(String validationTypeKey, String objectTypeKey, GPAInfo gpaInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateGPA(validationTypeKey, objectTypeKey, gpaInfo, contextInfo);
    }

    @Override
    public GPAInfo updateGPA(String gpaId, GPAInfo gpa, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateGPA(gpaId, gpa, contextInfo);
    }

    @Override
    public StatusInfo deleteGPA(String gpaId, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteGPA(gpaId, contextInfo);
    }

    @Override
    public LoadInfo createLoad(String loadTypeKey, LoadInfo load, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createLoad(loadTypeKey, load, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLoad(String validationTypeKey, String objectTypeKey, LoadInfo loadInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateLoad(validationTypeKey, objectTypeKey, loadInfo, contextInfo);
    }

    @Override
    public LoadInfo updateLoad(String loadId, LoadInfo load, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return nextDecorator.updateLoad(loadId, load, contextInfo);
    }

    @Override
    public StatusInfo deleteLoad(String loadId, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteLoad(loadId, contextInfo);
    }

    
    
}
