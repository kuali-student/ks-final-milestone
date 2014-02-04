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
    public StudentProgramRecordInfo updateStudentProgramRecord(String studentProgramRecordId, StudentProgramRecordInfo studentProgramRecord, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
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
    public StudentCourseRecordInfo updateStudentCourseRecord(String studentCourseRecordId, StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
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
    public StudentCredentialRecordInfo updateStudentCredentialRecord(String studentCredentialRecordId, StudentCredentialRecordInfo studentCredentialRecord, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
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
    public StudentTestScoreRecordInfo updateStudentTestScoreRecord(String studentTestScoreRecordId, StudentTestScoreRecordInfo studentTestScoreRecord, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.updateStudentTestScoreRecord(studentTestScoreRecordId, studentTestScoreRecord, contextInfo);
    }

    @Override
    public StatusInfo deleteStudentTestScoreRecord(String studentTestScoreRecordId, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteStudentTestScoreRecord(studentTestScoreRecordId, contextInfo);
    }

    @Override
    public GPAInfo createGPA(String gpaTypeKey, String studentId, String programId,
                             String resultScaleId, String atpId, GPAInfo gpa, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createGPA(gpaTypeKey, studentId, programId, resultScaleId, atpId, gpa, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateGPA(String validationTypeKey, String objectTypeKey, GPAInfo gpaInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateGPA(validationTypeKey, objectTypeKey, gpaInfo, contextInfo);
    }

    @Override
    public GPAInfo updateGPA(String gpaId, GPAInfo gpa, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.updateGPA(gpaId, gpa, contextInfo);
    }

    @Override
    public StatusInfo deleteGPA(String gpaId, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteGPA(gpaId, contextInfo);
    }

    @Override
    public LoadInfo createLoad(String loadTypeKey, String studentId, String atpId, LoadInfo load, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.createLoad(loadTypeKey, studentId, atpId, load, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateLoad(String validationTypeKey, String objectTypeKey, LoadInfo loadInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.validateLoad(validationTypeKey, objectTypeKey, loadInfo, contextInfo);
    }

    @Override
    public LoadInfo updateLoad(String loadId, LoadInfo load, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return nextDecorator.updateLoad(loadId, load, contextInfo);
    }

    @Override
    public StatusInfo deleteLoad(String loadId, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return nextDecorator.deleteLoad(loadId, contextInfo);
    }

    
    
}
