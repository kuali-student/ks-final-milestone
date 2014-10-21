/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by mahtabme on 7/4/13
 */
package org.kuali.student.enrollment.class2.exam.service.impl;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.class2.exam.service.transformer.ExamTransformer;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.enrollment.exam.service.ExamService;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.ExamServiceConstants;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents an implementation of the Exam service.
 *
 * @author Kuali Student Team
 */
public class ExamServiceImpl implements ExamService {

    private static final String PREDICATE_FACTORY_PATH_FOR_CLUTYPE = "luType.id";

    private CluService cluService;

    private ExamTransformer examTransformer;

    @Override
    public List<ValidationResultInfo> validateExam(String validationTypeKey, String examTypeKey, ExamInfo examInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public ExamInfo createExam(String examTypeKey, ExamInfo examInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        CluInfo cluInfo = new CluInfo();
        getExamTransformer().exam2Clu(examInfo, cluInfo, contextInfo);
        CluInfo createdClu = getCluService().createClu(examTypeKey, cluInfo, contextInfo);
        ExamInfo createdExam = new ExamInfo();
        getExamTransformer().clu2Exam(createdClu, createdExam, contextInfo);
        return createdExam;
    }

    @Override
    @Transactional(readOnly = true)
    public ExamInfo getExam(String examId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        CluInfo cluInfo = getCluService().getClu(examId, contextInfo);
        ExamInfo examInfo = new ExamInfo();
        getExamTransformer().clu2Exam(cluInfo, examInfo, contextInfo);
        return examInfo;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ExamInfo updateExam(String examId, ExamInfo examInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        CluInfo cluToUpdate = getCluService().getClu(examId, contextInfo);
        getExamTransformer().exam2Clu(examInfo, cluToUpdate, contextInfo);
        CluInfo updatedClu = getCluService().updateClu(examId, cluToUpdate, contextInfo);
        ExamInfo updatedExam = new ExamInfo();
        getExamTransformer().clu2Exam(updatedClu, updatedExam, contextInfo);
        return updatedExam;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteExam(String examId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        try {
            getCluService().deleteClu(examId, contextInfo);
        } catch (DependentObjectsExistException e) {
            StatusInfo failedStatus = new StatusInfo();
            failedStatus.setSuccess(Boolean.FALSE);
            failedStatus.setMessage("Exam could not be deleted because dependent child objects exist.");
            return failedStatus;
        }
        return new StatusInfo();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamInfo> getExamsByIds(List<String> examIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<ExamInfo> exams = new ArrayList<ExamInfo>(examIds.size());
        for (String examID : examIds) {
            exams.add(getExam(examID, contextInfo));
        }
        return exams;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getExamIdsByType(String examTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<String> ids = null;
        try {
            //Note: CluService does not use stateservice yet, therefore we use DtoContstants.STATE_ACTIVE instead
            // of ExamServiceConstants.EXAM_ACTIVE_STATE_KEY
            ids = getCluService().getCluIdsByLuType(examTypeKey, DtoConstants.STATE_ACTIVE, contextInfo);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(e.getMessage());
        }
        if (ids == null) {
            ids = new ArrayList<String>(0);
        }
        return ids;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForExamIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        //Add cluType Predicate
        QueryByCriteria newCriteria = addCluTypeEqualPredicate(criteria, ExamServiceConstants.EXAM_FINAL_TYPE_KEY);

        return this.getCluService().searchForCluIds(newCriteria, contextInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamInfo> searchForExams(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        //Add cluType Predicate
        QueryByCriteria newCriteria = addCluTypeEqualPredicate(criteria, ExamServiceConstants.EXAM_FINAL_TYPE_KEY);

        List<ExamInfo> examInfos = new ArrayList<ExamInfo>();
        List<CluInfo> cluInfos = this.getCluService().searchForClus(newCriteria, contextInfo);
        for (CluInfo cluInfo : cluInfos) {
            ExamInfo exam = new ExamInfo();
            this.getExamTransformer().clu2Exam(cluInfo, exam, contextInfo);
            examInfos.add(exam);
        }

        return examInfos;
    }

    private QueryByCriteria addCluTypeEqualPredicate(QueryByCriteria criteria, String cluType) {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                criteria.getPredicate(),
                PredicateFactory.equal(PREDICATE_FACTORY_PATH_FOR_CLUTYPE, cluType)));
        return qbcBuilder.build();
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

    public CluService getCluService() {
        return cluService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public void setExamTransformer(ExamTransformer examTransformer) {
        this.examTransformer = examTransformer;
    }

    public ExamTransformer getExamTransformer() {
        return examTransformer;
    }

}
