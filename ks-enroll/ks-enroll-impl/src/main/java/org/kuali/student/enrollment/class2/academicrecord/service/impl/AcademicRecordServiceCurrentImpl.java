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
 * Created by pauldanielrichardson on 8/18/14
 */
package org.kuali.student.enrollment.class2.academicrecord.service.impl;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.enrollment.class2.academicrecord.service.assembler.StudentCourseRecordAssembler;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.assembler.AssemblyException;
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
import org.kuali.student.r2.lum.clu.service.CluService;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

/**
 * This class returns the current registration data for a student as part of the Academic Record
 *
 * @author Kuali Student Team
 */
@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class AcademicRecordServiceCurrentImpl implements AcademicRecordService {
    private CourseRegistrationService courseRegService;
    private StudentCourseRecordAssembler courseRecordAssembler;
    private CourseOfferingService courseOfferingService;
    private CluService cluService;

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourse(String personId, String courseId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<StudentCourseRecordInfo> courseRecords = new ArrayList<>();
        try {
            List<CourseRegistrationInfo> regs = courseRegService.getCourseRegistrationsByStudent(personId, contextInfo);
            if (regs != null && !regs.isEmpty()) {
                for (CourseRegistrationInfo reg : regs) {
                    CourseOfferingInfo courseOfferingInfo = courseOfferingService.getCourseOffering(reg.getCourseOfferingId(), contextInfo);
                    String cluId = courseOfferingInfo.getCourseId();
                    String regVersionIndId = cluService.getVersionIndependentId(cluId, contextInfo);
                    if (regVersionIndId.equals(courseId)) {
                        StudentCourseRecordInfo courseRecord = courseRecordAssembler.assemble(reg, contextInfo);
                        if (courseRecord != null) {
                            courseRecords.add(courseRecord);
                        }
                    }
                }
            }
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException(e);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage(), e);
        }

        return courseRecords;
    }

    @Override
    public List<StudentCourseRecordInfo> getAttemptedCourseRecordsForTerm(
            String personId, String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        List<StudentCourseRecordInfo> courseRecords = new ArrayList<>();
        try {
            List<CourseRegistrationInfo> regs = courseRegService.getCourseRegistrationsByStudentAndTerm(personId, termId, context);
            if (regs != null && !regs.isEmpty()) {
                for (CourseRegistrationInfo reg : regs) {
                    StudentCourseRecordInfo courseRecord = courseRecordAssembler.assemble(reg, context);
                    if (courseRecord != null) {
                        courseRecords.add(courseRecord);
                    }
                }
            }
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException(e);
        } catch (AssemblyException e) {
            throw new OperationFailedException("AssemblyException : " + e.getMessage(), e);
        }

        return courseRecords;
    }

    @Override
    public StudentCourseRecordInfo getStudentCourseRecord(
            String studentCourseRecordId,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsByIds(
            List<String> studentCourseRecordIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<String> getStudentCourseRecordIdsByType(
            String studentCourseRecordTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecords(
            String personId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        List<StudentCourseRecordInfo> courseRecords = new ArrayList<>();
        try {
            List<CourseRegistrationInfo> regs = courseRegService.getCourseRegistrationsByStudent(personId, context);
            getCompletedCourseRecords(courseRecords, regs, context);
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException(e);
        }

        return courseRecords;
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(
            String personId, String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        List<StudentCourseRecordInfo> courseRecords = new ArrayList<>();
        try {
            List<CourseRegistrationInfo> regs = courseRegService.getCourseRegistrationsByStudentAndTerm(personId, termId, context);
            getCompletedCourseRecords(courseRecords, regs, context);
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException(e);
        }

        return courseRecords;
    }

    @Override
    public StudentCourseRecordInfo createStudentCourseRecord(
            String personId, String studentCourseRecordTypeKey,
            StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public StudentCourseRecordInfo updateStudentCourseRecord(
            String studentCourseRecordId,
                                                             StudentCourseRecordInfo studentCourseRecord,
                                                             ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public StatusInfo deleteStudentCourseRecord(
            String studentCourseRecordId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public GPAInfo getGPAForTerm(String personId, String termId,
                                 String calculationTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public GPAInfo getCumulativeGPA(String personId, String calculationTypeKey,
                                    ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public GPAInfo calculateGPA(List<StudentCourseRecordInfo> studentCourseRecordInfoList, String calculationTypeKey,
                                ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public String getEarnedCreditsForTerm(String personId, String termId,
                                          String calculationTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public String getEarnedCredits(String personId, String calculationTypeKey,
                                   ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForCourse(String personId, String courseId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<StudentCourseRecordInfo> courseRecords = new ArrayList<>();
        try {
            List<CourseOfferingInfo> courseOfferings = courseOfferingService.getCourseOfferingsByCourse(courseId, contextInfo);
            List<CourseRegistrationInfo> regs = new ArrayList<>();
            for(CourseOfferingInfo courseOffering : courseOfferings) {
               regs.addAll(courseRegService.getCourseRegistrationsByStudentAndCourseOffering(personId, courseOffering.getId(), contextInfo));
            }
            getCompletedCourseRecords(courseRecords, regs, contextInfo);
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException(e);
        }

        return courseRecords;
    }

    @Override
    public GPAInfo getCumulativeGPAForProgram(String personId, String programId, String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public GPAInfo getCumulativeGPAForTermAndProgram(String personId, String programId, String termKey, String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public LoadInfo getLoadForTerm(String personId, String termId, String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<StudentProgramRecordInfo> getProgramRecords(String personId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<StudentCredentialRecordInfo> getAwardedCredentials(String personId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecords(String personId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecordsByType(String personId, String testTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public String getEarnedCumulativeCreditsForProgramAndTerm(String personId, String programId, String termId, String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourses(String personId, List<String> courseIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<String> searchForStudentCourseRecordIds(QueryByCriteria criteria,
                                                        ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<StudentCourseRecordInfo> searchForStudentCourseRecords(QueryByCriteria criteria,
                                                                       ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    @Override
    public List<ValidationResultInfo> validateStudentCourseRecord(String validationTypeKey,
                                                                  String studentCourseRecordTypeKey,
                                                                  StudentCourseRecordInfo studentCourseRecordInfo,
                                                                  ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Method not yet implemented!");
    }

    private void getCompletedCourseRecords(List<StudentCourseRecordInfo> courseRecords, List<CourseRegistrationInfo> regs, ContextInfo context)
            throws OperationFailedException {
        if (regs != null && !regs.isEmpty()) {
            for (CourseRegistrationInfo reg : regs) {
                StudentCourseRecordInfo courseRecord;
                try {
                    courseRecord = courseRecordAssembler.assemble(reg, context);
                } catch (AssemblyException e) {
                    throw new OperationFailedException("AssemblyException : " + e.getMessage(), e);
                }

                if (courseRecord != null) {
                    if (courseRecord.getAssignedGradeValue() != null || courseRecord.getAdministrativeGradeValue() != null) {
                        courseRecords.add(courseRecord);
                    }
                }
            }
        }
    }

    public void setCourseRegService(CourseRegistrationService courseRegService) {
        this.courseRegService = courseRegService;
    }

    public void setCourseRecordAssembler(StudentCourseRecordAssembler courseRecordAssembler) {
        this.courseRecordAssembler = courseRecordAssembler;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }
}
