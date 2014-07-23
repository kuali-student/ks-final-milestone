/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.academicrecord.service;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
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
 *
 * @author nwright
 */
public class AcademicRecordServiceDecorator implements AcademicRecordService {

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
        return getNextDecorator().getAttemptedCourseRecordsForTerm(personId, termId, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecords(String personId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getCompletedCourseRecords(personId, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForCourse(String personId, String courseId,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getCompletedCourseRecordsForCourse(personId, courseId, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(String personId, String termId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getCompletedCourseRecordsForTerm(personId, termId, contextInfo);
    }

    @Override
    public GPAInfo getGPAForTerm(String personId, String termId, String calculationTypeKey, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getGPAForTerm(personId, termId, calculationTypeKey, contextInfo);
    }

    @Override
    public GPAInfo getCumulativeGPA(String personId, String calculationTypeKey, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getCumulativeGPA(personId, calculationTypeKey, contextInfo);
    }

    @Override
    public GPAInfo calculateGPA(List<StudentCourseRecordInfo> studentCourseRecordInfoList, String calculationTypeKey,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().calculateGPA(studentCourseRecordInfoList, calculationTypeKey, contextInfo);
    }

    @Override
    public GPAInfo getCumulativeGPAForProgram(String personId, String programId, String calculationTypeKey,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getCumulativeGPAForProgram(personId, programId, calculationTypeKey, contextInfo);
    }

    @Override
    public GPAInfo getCumulativeGPAForTermAndProgram(String personId, String programId, String termKey, String calculationTypeKey,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getCumulativeGPAForTermAndProgram(personId, programId, termKey, calculationTypeKey, contextInfo);
    }

    @Override
    public LoadInfo getLoadForTerm(String personId, String termId, String calculationTypeKey, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getLoadForTerm(personId, termId, calculationTypeKey, contextInfo);
    }

    @Override
    public List<StudentProgramRecordInfo> getStudentProgramRecords(String personId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentProgramRecords(personId, contextInfo);
    }

    @Override
    public List<StudentCredentialRecordInfo> getAwardedCredentials(String personId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getAwardedCredentials(personId, contextInfo);
    }

    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecords(String personId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getTestScoreRecords(personId, contextInfo);
    }

    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecordsByType(String personId, String testTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getTestScoreRecordsByType(personId, testTypeKey, contextInfo);
    }

    @Override
    public String getEarnedCreditsForTerm(String personId, String termId, String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getEarnedCreditsForTerm(personId, termId, calculationTypeKey, contextInfo);
    }

    @Override
    public String getEarnedCredits(String personId, String calculationTypeKey, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getEarnedCredits(personId, calculationTypeKey, contextInfo);
    }

    @Override
    public String getEarnedCumulativeCreditsForProgramAndTerm(String personId, String programId, String termId,
            String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getEarnedCumulativeCreditsForProgramAndTerm(personId, programId, termId, calculationTypeKey,
                contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourse(String personId, String courseId,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentCourseRecordsForCourse(personId, courseId, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourses(String personId, List<String> courseIds,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentCourseRecordsForCourses(personId, courseIds, contextInfo);
    }

    @Override
    public StudentProgramRecordInfo getStudentProgramRecord(String studentProgramRecordId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentProgramRecord(studentProgramRecordId, contextInfo);
    }

    @Override
    public List<StudentProgramRecordInfo> getStudentProgramRecordsByIds(List<String> studentProgramRecordIds,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentProgramRecordsByIds(studentProgramRecordIds, contextInfo);
    }

    @Override
    public List<String> getStudentProgramRecordIdsByType(String studentProgramRecordTypeKey, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentProgramRecordIdsByType(studentProgramRecordTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForStudentProgramRecordIds(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForStudentProgramRecordIds(criteria, contextInfo);
    }

    @Override
    public List<StudentProgramRecordInfo> searchForStudentProgramRecords(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForStudentProgramRecords(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateStudentProgramRecord(String validationTypeKey, String studentProgramTypeKey,
            StudentProgramRecordInfo studentProgramRecordInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().validateStudentProgramRecord(validationTypeKey, studentProgramTypeKey, studentProgramRecordInfo,
                contextInfo);
    }

    @Override
    public StudentProgramRecordInfo createStudentProgramRecord(String studentProgramRecordTypeKey, String personId,
            StudentProgramRecordInfo studentProgramRecord, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return getNextDecorator().createStudentProgramRecord(studentProgramRecordTypeKey, personId, studentProgramRecord,
                contextInfo);
    }

    @Override
    public StudentProgramRecordInfo updateStudentProgramRecord(String studentProgramRecordId,
            StudentProgramRecordInfo studentProgramRecord, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return getNextDecorator().updateStudentProgramRecord(studentProgramRecordId, studentProgramRecord, contextInfo);
    }

    @Override
    public StatusInfo deleteStudentProgramRecord(String studentProgramRecordId, ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deleteStudentProgramRecord(studentProgramRecordId, contextInfo);
    }

    @Override
    public StudentCourseRecordInfo getStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentCourseRecord(studentCourseRecordId, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsByIds(List<String> studentCourseRecordIds, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentCourseRecordsByIds(studentCourseRecordIds, contextInfo);
    }

    @Override
    public List<String> getStudentCourseRecordIdsByType(String studentCourseRecordTypeKey, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentCourseRecordIdsByType(studentCourseRecordTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForStudentCourseRecordIds(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForStudentCourseRecordIds(criteria, contextInfo);
    }

    @Override
    public List<StudentCourseRecordInfo> searchForStudentCourseRecords(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForStudentCourseRecords(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateStudentCourseRecord(String validationTypeKey, String studentCourseRecordTypeKey,
            StudentCourseRecordInfo studentCourseRecordInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().validateStudentCourseRecord(validationTypeKey, studentCourseRecordTypeKey,
                studentCourseRecordInfo,
                contextInfo);
    }

    @Override
    public StudentCourseRecordInfo createStudentCourseRecord(String studentCourseRecordTypeKey, String personId,
            StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return getNextDecorator().
                createStudentCourseRecord(studentCourseRecordTypeKey, personId, studentCourseRecord, contextInfo);
    }

    @Override
    public StudentCourseRecordInfo updateStudentCourseRecord(String studentCourseRecordId,
            StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return getNextDecorator().updateStudentCourseRecord(studentCourseRecordId, studentCourseRecord, contextInfo);
    }

    @Override
    public StatusInfo deleteStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deleteStudentCourseRecord(studentCourseRecordId, contextInfo);
    }

    @Override
    public StudentCredentialRecordInfo getStudentCredentialRecord(String studentCredentialRecordId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentCredentialRecord(studentCredentialRecordId, contextInfo);
    }

    @Override
    public List<String> getStudentCredentialRecordIdsByType(String studentCredentialRecordTypeKey, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentCredentialRecordIdsByType(studentCredentialRecordTypeKey, contextInfo);
    }

    @Override
    public List<StudentCredentialRecordInfo> getStudentCredentialRecordsByIds(List<String> studentCredentialRecordIds,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentCredentialRecordsByIds(studentCredentialRecordIds, contextInfo);
    }

    @Override
    public List<String> searchForStudentCredentialRecordIds(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForStudentCredentialRecordIds(criteria, contextInfo);
    }

    @Override
    public List<StudentCredentialRecordInfo> searchForStudentCredentialRecords(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForStudentCredentialRecords(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateStudentCredentialRecord(String validationTypeKey, String studentCredentialTypeKey,
            StudentCredentialRecordInfo studentCredentialRecord, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().validateStudentCredentialRecord(validationTypeKey, studentCredentialTypeKey,
                studentCredentialRecord,
                contextInfo);
    }

    @Override
    public StudentCredentialRecordInfo createStudentCredentialRecord(String studentCredentialRecordTypeKey, String personId,
            StudentCredentialRecordInfo studentCredentialRecord, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return getNextDecorator().createStudentCredentialRecord(studentCredentialRecordTypeKey, personId, studentCredentialRecord,
                contextInfo);
    }

    @Override
    public StudentCredentialRecordInfo updateStudentCredentialRecord(String studentCredentialRecordId,
            StudentCredentialRecordInfo studentCredentialRecord, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return getNextDecorator().updateStudentCredentialRecord(studentCredentialRecordId, studentCredentialRecord, contextInfo);
    }

    @Override
    public StatusInfo deleteStudentCredentialRecord(String studentCredentialRecordId, ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deleteStudentCredentialRecord(studentCredentialRecordId, contextInfo);
    }

    @Override
    public StudentTestScoreRecordInfo getStudentTestScoreRecord(String studentTestScoreRecordId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentTestScoreRecord(studentTestScoreRecordId, contextInfo);
    }

    @Override
    public List<StudentTestScoreRecordInfo> getStudentTestScoreRecordsByIds(List<String> studentTestScoreRecordIds,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentTestScoreRecordsByIds(studentTestScoreRecordIds, contextInfo);
    }

    @Override
    public List<String> getStudentTestScoreRecordIdsByType(String studentTestScoreRecordTypeKey, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getStudentTestScoreRecordIdsByType(studentTestScoreRecordTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForStudentTestScoreRecordIds(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForStudentTestScoreRecordIds(criteria, contextInfo);
    }

    @Override
    public List<StudentTestScoreRecordInfo> searchForStudentTestScoreRecords(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForStudentTestScoreRecords(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateStudentTestScoreRecord(String validationTypeKey,
            String studentTestScoreRecordTypeKey, StudentTestScoreRecordInfo studentTestScoreRecord, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().validateStudentTestScoreRecord(validationTypeKey, studentTestScoreRecordTypeKey,
                studentTestScoreRecord, contextInfo);
    }

    @Override
    public StudentTestScoreRecordInfo createStudentTestScoreRecord(String studentTestScoreRecordTypeKey, String personId,
            StudentTestScoreRecordInfo studentTestScoreRecord, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return getNextDecorator().createStudentTestScoreRecord(studentTestScoreRecordTypeKey, personId, studentTestScoreRecord,
                contextInfo);
    }

    @Override
    public StudentTestScoreRecordInfo updateStudentTestScoreRecord(String studentTestScoreRecordId,
            StudentTestScoreRecordInfo studentTestScoreRecord, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return getNextDecorator().updateStudentTestScoreRecord(studentTestScoreRecordId, studentTestScoreRecord, contextInfo);
    }

    @Override
    public StatusInfo deleteStudentTestScoreRecord(String studentTestScoreRecordId, ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deleteStudentTestScoreRecord(studentTestScoreRecordId, contextInfo);
    }

    @Override
    public GPAInfo getGPA(String gpaId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getGPA(gpaId, contextInfo);
    }

    @Override
    public List<GPAInfo> getGpasByIds(List<String> gpaIds, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getGpasByIds(gpaIds, contextInfo);
    }

    @Override
    public List<String> getGPAIdsByType(String gpaTypeKey, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getGPAIdsByType(gpaTypeKey, contextInfo);
    }

    @Override
    public List<GPAInfo> searchForGpas(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForGpas(criteria, contextInfo);
    }

    @Override
    public List<String> searchForGpaIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForGpaIds(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateGPA(String validationTypeKey, String gpaTypeKey, GPAInfo gpa,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().validateGPA(validationTypeKey, gpaTypeKey, gpa, contextInfo);
    }

    @Override
    public GPAInfo createGPA(String gpaTypeKey, GPAInfo gpa, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return getNextDecorator().createGPA(gpaTypeKey, gpa, contextInfo);
    }

    @Override
    public GPAInfo updateGPA(String gpaId, GPAInfo gpa, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return getNextDecorator().updateGPA(gpaId, gpa, contextInfo);
    }

    @Override
    public StatusInfo deleteGPA(String gpaId, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deleteGPA(gpaId, contextInfo);
    }

    @Override
    public LoadInfo getLoad(String loadId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getLoad(loadId, contextInfo);
    }

    @Override
    public List<LoadInfo> getLoadsByIds(List<String> loadIds, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getLoadsByIds(loadIds, contextInfo);
    }

    @Override
    public List<String> getLoadIdsByType(String loadTypeKey, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getLoadIdsByType(loadTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForLoadIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForLoadIds(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLoad(String validationTypeKey, String loadTypeKey, LoadInfo load,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().validateLoad(validationTypeKey, loadTypeKey, load, contextInfo);
    }

    @Override
    public LoadInfo createLoad(String loadTypeKey, LoadInfo load, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return getNextDecorator().createLoad(loadTypeKey, load, contextInfo);
    }

    @Override
    public LoadInfo updateLoad(String loadId, LoadInfo load, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return getNextDecorator().updateLoad(loadId, load, contextInfo);
    }

    @Override
    public StatusInfo deleteLoad(String loadId, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deleteLoad(loadId, contextInfo);
    }

}
