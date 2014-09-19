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


import org.kuali.rice.core.api.criteria.CriteriaLookupService;
import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.academicrecord.dao.StudentCourseRecordDao;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
import org.kuali.student.enrollment.academicrecord.model.StudentCourseRecordEntity;
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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class AcademicRecordServicePersistenceImpl implements AcademicRecordService {

    @Resource
    private StudentCourseRecordDao studentCourseRecordDao;

    // Criteria Lookup for this object
    private CriteriaLookupService studentCourseRecordCriteriaLookupService;

    @Override
    @Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<StudentCourseRecordInfo> getAttemptedCourseRecordsForTerm(String personId, String termId, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<StudentCourseRecordEntity> entities = studentCourseRecordDao.getAttemptedCourseRecordsForTerm(personId, termId);
        List<StudentCourseRecordInfo> list = new ArrayList<StudentCourseRecordInfo>(entities.size());
        for (StudentCourseRecordEntity entity : entities) {
            list.add(entity.toDto());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<StudentCourseRecordInfo> getCompletedCourseRecords(String personId, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getCompletedCourseRecords has not been implemented");
    }

    @Override
    @Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForCourse(String personId, String courseId, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<StudentCourseRecordEntity> entities = studentCourseRecordDao.getCompletedCourseRecordsForCourse(personId, courseId);
        List<StudentCourseRecordInfo> list = new ArrayList<StudentCourseRecordInfo>(entities.size());
        for (StudentCourseRecordEntity entity : entities) {
            list.add(entity.toDto());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(String personId, String termId, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<StudentCourseRecordEntity> entities = studentCourseRecordDao.getCompletedCourseRecordsForTerm(personId, termId);
        List<StudentCourseRecordInfo> list = new ArrayList<StudentCourseRecordInfo>(entities.size());
        for (StudentCourseRecordEntity entity : entities) {
            list.add(entity.toDto());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StudentCourseRecordInfo getStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_BY_ID
        StudentCourseRecordEntity entity = studentCourseRecordDao.find(studentCourseRecordId);
        if (entity == null) {
            throw new DoesNotExistException(studentCourseRecordId);
        }
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<StudentCourseRecordInfo> getStudentCourseRecordsByIds(List<String> studentCourseRecordIds, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_BY_IDS
        List<StudentCourseRecordEntity> entities = studentCourseRecordDao.findByIds(studentCourseRecordIds);
        List<StudentCourseRecordInfo> list = new ArrayList<StudentCourseRecordInfo>(entities.size());
        for (StudentCourseRecordEntity entity : entities) {
            if (entity == null) {
                throw new DoesNotExistException();
            }
            list.add(entity.toDto());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<String> getStudentCourseRecordIdsByType(String studentCourseRecordTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_IDS_BY_TYPE
        return studentCourseRecordDao.getIdsByType(studentCourseRecordTypeKey);
    }

    @Override
    @Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourse(String personId, String courseId, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<StudentCourseRecordEntity> entities = studentCourseRecordDao.getForCourse(personId, courseId);
        List<StudentCourseRecordInfo> list = new ArrayList<StudentCourseRecordInfo>(entities.size());
        for (StudentCourseRecordEntity entity : entities) {
            list.add(entity.toDto());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourses(String personId, List<String> courseIds, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<StudentCourseRecordEntity> entities = studentCourseRecordDao.getForCourses(personId, courseIds);
        List<StudentCourseRecordInfo> list = new ArrayList<StudentCourseRecordInfo>(entities.size());
        for (StudentCourseRecordEntity entity : entities) {
            list.add(entity.toDto());
        }
        return list;
    }

    @Override
    //@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<String> searchForStudentCourseRecordIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // SEARCH_FOR_IDS
        List<String> results = new ArrayList<String>();
        GenericQueryResults<StudentCourseRecordEntity> entities
                = studentCourseRecordCriteriaLookupService.lookup(StudentCourseRecordEntity.class, criteria);
        if (null != entities && !entities.getResults().isEmpty()) {
            for (StudentCourseRecordEntity entity : entities.getResults()) {
                results.add(entity.getId());
            }
        }
        return results;
    }

    @Override
    //@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<StudentCourseRecordInfo> searchForStudentCourseRecords(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // SEARCH_FOR_INFOS
        List<StudentCourseRecordInfo> results = new ArrayList<StudentCourseRecordInfo>();
        GenericQueryResults<StudentCourseRecordEntity> entities
                = studentCourseRecordCriteriaLookupService.lookup(StudentCourseRecordEntity.class, criteria);
        if (null != entities && !entities.getResults().isEmpty()) {
            for (StudentCourseRecordEntity entity : entities.getResults()) {
                results.add(entity.toDto());
            }
        }
        return results;
    }

    @Override
    //@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<ValidationResultInfo> validateStudentCourseRecord(String validationTypeKey, String studentCourseRecordTypeKey, StudentCourseRecordInfo studentCourseRecordInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Throwable.class})
    public StudentCourseRecordInfo createStudentCourseRecord(String personId, String studentCourseRecordTypeKey, StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException {
        // CREATE
        studentCourseRecord.setTypeKey(studentCourseRecordTypeKey);
        studentCourseRecord.setPersonId(personId);
        StudentCourseRecordEntity entity = new StudentCourseRecordEntity(studentCourseRecord);
        entity.setEntityCreated(contextInfo);
        studentCourseRecordDao.persist(entity);
        studentCourseRecordDao.getEm().flush();
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Throwable.class})
    public StudentCourseRecordInfo updateStudentCourseRecord(String studentCourseRecordId, StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException
            , VersionMismatchException {
        // UPDATE
        if (!studentCourseRecordId.equals(studentCourseRecord.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        StudentCourseRecordEntity entity = studentCourseRecordDao.find(studentCourseRecordId);
        if (entity == null) {
            throw new DoesNotExistException(studentCourseRecordId);
        }
        entity.fromDto(studentCourseRecord);
        entity.setEntityUpdated(contextInfo);
        entity = studentCourseRecordDao.merge(entity);
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = {Throwable.class})
    public StatusInfo deleteStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // DELETE
        StudentCourseRecordEntity entity = studentCourseRecordDao.find(studentCourseRecordId);
        if (entity == null) {
            throw new DoesNotExistException(studentCourseRecordId);
        }
        studentCourseRecordDao.remove(entity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public GPAInfo getGPAForTerm(String personId, String termId, String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getGPAForTerm has not been implemented");
    }

    @Override
    public GPAInfo getCumulativeGPA(String personId, String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("getCumulativeGPA has not been implemented");
    }

    @Override
    public GPAInfo calculateGPA(List<StudentCourseRecordInfo> studentCourseRecordInfoList, String calculationTypeKey,
                                ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("calculateGPA has not been implemented");
    }

    @Override
    public GPAInfo getCumulativeGPAForProgram(String personId, String programId, String calculationTypeKey,
                                              ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getCumulativeGPAForProgram has not been implemented");
    }

    @Override
    public GPAInfo getCumulativeGPAForTermAndProgram(String personId, String programId, String termKey,
                                                     String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getCumulativeGPAForTermAndProgram has not been implemented");
    }

    @Override
    public LoadInfo getLoadForTerm(String personId, String termId, String calculationTypeKey,
                                   ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getLoadForTerm has not been implemented");
    }

    @Override
    public List<StudentProgramRecordInfo> getProgramRecords(String personId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getProgramRecords has not been implemented");
    }

    @Override
    public List<StudentCredentialRecordInfo> getAwardedCredentials(String personId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getAwardedCredentials has not been implemented");
    }

    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecords(String personId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getTestScoreRecords has not been implemented");
    }

    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecordsByType(String personId, String testTypeKey,
                                                                      ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getTestScoreRecordsByType has not been implemented");
    }

    @Override
    public String getEarnedCreditsForTerm(String personId, String termId, String calculationTypeKey,
                                          ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getEarnedCreditsForTerm has not been implemented");
    }

    @Override
    public String getEarnedCredits(String personId, String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getEarnedCredits has not been implemented");
    }

    @Override
    public String getEarnedCumulativeCreditsForProgramAndTerm(String personId, String programId,
                                                              String termId, String calculationTypeKey,
                                                              ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getEarnedCumulativeCreditsForProgramAndTerm has not been implemented");
    }

    public StudentCourseRecordDao getStudentCourseRecordDao() {
        return this.studentCourseRecordDao;
    }

    public void setStudentCourseRecordDao(StudentCourseRecordDao studentCourseRecordDao) {
        this.studentCourseRecordDao = studentCourseRecordDao;
    }

    public CriteriaLookupService getStudentCourseRecordCriteriaLookupService() {
        return this.studentCourseRecordCriteriaLookupService;
    }

    public void setStudentCourseRecordCriteriaLookupService(CriteriaLookupService studentCourseRecordCriteriaLookupService) {
        this.studentCourseRecordCriteriaLookupService = studentCourseRecordCriteriaLookupService;
    }
}

