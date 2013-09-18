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
 * Created by mahtabme on 7/15/13
 */
package org.kuali.student.enrollment.class2.examoffering.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.class2.examoffering.service.transformer.ExamOfferingTransformer;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
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
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.scheduling.infc.Schedule;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExamOfferingServiceImpl implements ExamOfferingService {

    private static final String PREDICATE_FACTORY_PATH_FOR_LUITYPE = "typeKey";

    private LuiService luiService;
    private SchedulingService schedulingService;
    private TypeService typeService;

    private ExamOfferingTransformer examOfferingTransformer;
    private static final String OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE = "unexpected";

    private static final Logger LOGGER = Logger.getLogger(ExamOfferingServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public ExamOfferingInfo getExamOffering(String examOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        LuiInfo luiInfo = getLuiService().getLui(examOfferingId,contextInfo);
        ExamOfferingInfo examOfferingInfo = new ExamOfferingInfo();
        getExamOfferingTransformer().lui2ExamOffering(luiInfo,examOfferingInfo,getSchedulingService(),contextInfo);
        return examOfferingInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamOfferingInfo> getExamOfferingsByIds(List<String> examOfferingIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<ExamOfferingInfo> list = new ArrayList<ExamOfferingInfo> ();
        for (String id: examOfferingIds) {
            list.add (this.getExamOffering(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getExamOfferingIdsByType(String examTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return luiService.getLuiIdsByType(examTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForExamOfferingIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        //Add cluType Predicate
        QueryByCriteria newCriteria = addLuiTypeEqualPredicate(criteria, ExamOfferingServiceConstants.EXAM_OFFERING_FINAL_TYPE_KEY);

        return this.getLuiService().searchForLuiIds(newCriteria, contextInfo);
    }

    @Override
    public List<ExamOfferingInfo> searchForExamOfferings(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        //Add cluType Predicate
        QueryByCriteria newCriteria = addLuiTypeEqualPredicate(criteria, ExamOfferingServiceConstants.EXAM_OFFERING_FINAL_TYPE_KEY);

        List<ExamOfferingInfo> examInfos = new ArrayList<ExamOfferingInfo>();
        List<LuiInfo> luiInfos = this.getLuiService().searchForLuis(newCriteria, contextInfo);
        for(LuiInfo luiInfo : luiInfos){
            ExamOfferingInfo examOffering = new ExamOfferingInfo();
            this.getExamOfferingTransformer().lui2ExamOffering(luiInfo, examOffering, this.getSchedulingService(), contextInfo);
            examInfos.add(examOffering);
        }

        return examInfos;
    }

    private QueryByCriteria addLuiTypeEqualPredicate(QueryByCriteria criteria, String cluType){
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                criteria.getPredicate(),
                PredicateFactory.equal(PREDICATE_FACTORY_PATH_FOR_LUITYPE, cluType)));
        return qbcBuilder.build();
    }

    @Override
    public List<ValidationResultInfo> validateExamOffering(String termId, String examId, String examTypeKey, String validationTypeKey,
                                                           ExamOfferingInfo examOfferingInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        // validate
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ExamOfferingInfo createExamOffering(String termId, String examId, String examTypeKey, ExamOfferingInfo examOfferingInfo,
                                               ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException  {

        LuiInfo luiToCreate = new LuiInfo();
        getExamOfferingTransformer().examOffering2Lui(examOfferingInfo, luiToCreate, contextInfo);
        LuiInfo createdLui = getLuiService().createLui(examId, termId, examTypeKey, luiToCreate, contextInfo);
        ExamOfferingInfo createdExamOffering = new ExamOfferingInfo();
        getExamOfferingTransformer().lui2ExamOffering(createdLui,createdExamOffering,getSchedulingService(),contextInfo);
        return createdExamOffering;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ExamOfferingInfo updateExamOffering(String examOfferingId, ExamOfferingInfo examOfferingInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        LuiInfo luiToUpdate = getLuiService().getLui(examOfferingId,contextInfo);
        getExamOfferingTransformer().examOffering2Lui(examOfferingInfo,luiToUpdate,contextInfo);
        getLuiService().updateLui(examOfferingId,luiToUpdate,contextInfo);
        LuiInfo updatedLui = getLuiService().getLui(examOfferingId,contextInfo);
        ExamOfferingInfo updatedExamOffering = new ExamOfferingInfo();
        getExamOfferingTransformer().lui2ExamOffering(updatedLui,updatedExamOffering,getSchedulingService(),contextInfo);
        return updatedExamOffering;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteExamOffering(String examOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        try {
            getLuiService().deleteLui(examOfferingId,contextInfo);
        } catch (DependentObjectsExistException e) {
            StatusInfo failedStatus = new StatusInfo();
            failedStatus.setSuccess(Boolean.FALSE);
            failedStatus.setMessage("ExamOffering could not be deleted because dependent child objects exist.");
            return failedStatus;
        }
        return new StatusInfo();
    }

    @Override
    public StatusInfo changeExamOfferingState(String examOfferingId, String stateKey, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("changeExamOfferingState has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateExamOfferingRelation(String formatOfferingId, String examOfferingId, String examOfferingTypeKey, String validationTypeKey, ExamOfferingRelationInfo examOfferingRelationInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public List<ExamOfferingInfo> getExamOfferingsByExamPeriod(String examPeriodId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException ("getExamOfferingsByExamPeriod has not been implemented");
    }

    @Override
    public ExamOfferingRelationInfo createExamOfferingRelation(String formatOfferingId, String examOfferingId, String examOfferingTypeKey, ExamOfferingRelationInfo examOfferingRelationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        //trap null parameters
        if (formatOfferingId == null){
            throw new MissingParameterException("Format offering ID is null");
        }
        if (examOfferingId == null){
            throw new MissingParameterException("Exam offering ID is null");
        }
        if (examOfferingRelationInfo.getFormatOfferingId().equalsIgnoreCase(formatOfferingId) && examOfferingRelationInfo.getExamOfferingId().equals(examOfferingId)) {
            throw new InvalidParameterException("IDs do not match");
        }
        //set FO / EO relations
        LuiLuiRelationInfo luiRel = new LuiLuiRelationInfo();
        //transform
        LuiLuiRelationInfo  luiRetrn;
        getExamOfferingTransformer().transformEORel2LuiLuiRel(examOfferingRelationInfo, luiRel);

        //save
        try {
            luiRetrn = getLuiService().createLuiLuiRelation(luiRel.getLuiId(), luiRel.getRelatedLuiId(), luiRel.getTypeKey(), luiRel, contextInfo);
        } catch (Exception ex) {
            throw new OperationFailedException(OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE, ex);
        }

        //transform and return saved info
        ExamOfferingRelationInfo examOfferingRelationInfoRtrn = new ExamOfferingRelationInfo();
        getExamOfferingTransformer().transformLuiLuiRel2EORel(luiRetrn, examOfferingRelationInfoRtrn);
        return examOfferingRelationInfoRtrn;
    }

    @Override
    public ExamOfferingRelationInfo updateExamOfferingRelation(String examOfferingRelationId, ExamOfferingRelationInfo examOfferingRelationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        //trap null parameters
        if (examOfferingRelationId == null){
            throw new MissingParameterException("examOfferingRelationId is null");
        }
        if (examOfferingRelationInfo == null){
            throw new MissingParameterException("examOfferingRelationInfo is null");
        }

        //transform
        LuiLuiRelationInfo  luiRel = new LuiLuiRelationInfo();
        LuiLuiRelationInfo luiRetrn;
        getExamOfferingTransformer().transformEORel2LuiLuiRel(examOfferingRelationInfo, luiRel);

        //update
        try {
            luiRetrn = getLuiService().updateLuiLuiRelation(examOfferingRelationId, luiRel, contextInfo);
        } catch (Exception ex) {
            throw new OperationFailedException(OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE, ex);
        }

        //transform and return saved info
        ExamOfferingRelationInfo examOfferingRelationInfoRtrn = new ExamOfferingRelationInfo();
        getExamOfferingTransformer().transformLuiLuiRel2EORel(luiRetrn, examOfferingRelationInfoRtrn);
        return examOfferingRelationInfoRtrn;
    }

    @Override
    public StatusInfo deleteExamOfferingRelation(String examOfferingRelationId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        //trap null parameter
        if (examOfferingRelationId == null){
            throw new MissingParameterException("examOfferingRelationId is null");
        }
        try {
            return getLuiService().deleteLuiLuiRelation(examOfferingRelationId, contextInfo);
        } catch (Exception ex) {
            throw new OperationFailedException(OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE, ex);
        }
    }

    @Override
    public ExamOfferingRelationInfo getExamOfferingRelation(String examOfferingRelationId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        //trap null parameter
        if (examOfferingRelationId == null){
            throw new MissingParameterException("examOfferingRelationId is null");
        }

        //Retrieve LuiLuiRelationInfo for the EO
        LuiLuiRelationInfo  luiRetrn;
        try {
            luiRetrn = getLuiService().getLuiLuiRelation(examOfferingRelationId, contextInfo);
        } catch (Exception ex) {
            throw new OperationFailedException(OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE, ex);
        }

        //return populated EORelationInfo
        ExamOfferingRelationInfo examOfferingRelationInfoRtrn = new ExamOfferingRelationInfo();
        getExamOfferingTransformer().transformLuiLuiRel2EORel(luiRetrn, examOfferingRelationInfoRtrn);
        return examOfferingRelationInfoRtrn;
    }

    @Override
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByIds(List<String> examOfferingRelationIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("getExamOfferingRelationsByIds has not been implemented");
    }

    @Override
    public List<String> getExamOfferingRelationIdsByType(String relationshipTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("getExamOfferingRelationIdsByType has not been implemented");
    }

    @Override
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByFormatOffering(String formatOfferingId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("getExamOfferingRelationsByFormatOffering has not been implemented");
    }

    @Override
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByExamOffering(String examOfferingId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("getExamOfferingRelationsByExamOffering has not been implemented");
    }

    @Override
    public List<String> getExamOfferingRelationIdsByActivityOfferingId(String activityOfferingId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("getExamOfferingRelationIdsByActivityOfferingId has not been implemented");
    }

    @Override
    public List<String> searchForExamOfferingRelationIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForExamOfferingRelationIds has not been implemented");
    }

    @Override
    public List<ExamOfferingRelationInfo> searchForExamOfferingRelations(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForExamOfferingRelations has not been implemented");
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

    public LuiService getLuiService() {
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public ExamOfferingTransformer getExamOfferingTransformer() {
        return examOfferingTransformer;
    }

    public void setExamOfferingTransformer(ExamOfferingTransformer examOfferingTransformer) {
        this.examOfferingTransformer = examOfferingTransformer;
    }
}

