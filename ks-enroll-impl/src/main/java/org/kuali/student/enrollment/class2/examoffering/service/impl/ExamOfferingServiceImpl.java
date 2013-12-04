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

import org.apache.commons.lang.StringUtils;
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
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.state.service.StateTransitionsHelper;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.scheduling.infc.Schedule;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ExamOfferingServiceImpl implements ExamOfferingService {

    private static final String PREDICATE_FACTORY_PATH_FOR_LUITYPE = "typeKey";

    private LuiService luiService;
    private SchedulingService schedulingService;
    private TypeService typeService;
    private AcademicCalendarService acalService;

    private ExamOfferingTransformer examOfferingTransformer;
    private StateTransitionsHelper stateTransitionsHelper;
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
    @Transactional(readOnly = true)
    public List<String> getExamOfferingIdsByType(String examTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException {
        List<String> examOfferingIds = new ArrayList<String>();

        List<TypeInfo> allowedTypes = typeService.getAllowedTypesForType(examTypeKey, contextInfo);
        for (TypeInfo typeInfo : allowedTypes) {
            examOfferingIds.addAll(getLuiService().getLuiIdsByType(typeInfo.getKey(), contextInfo));
        }

        return examOfferingIds;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForExamOfferingIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        //Add cluType Predicate
        QueryByCriteria newCriteria = addLuiTypeEqualPredicate(criteria, ExamOfferingServiceConstants.EXAM_OFFERING_FINAL_TYPE_KEY);

        return this.getLuiService().searchForLuiIds(newCriteria, contextInfo);
    }

    @Override
    @Transactional(readOnly = true)
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
        getExamOfferingTransformer().lui2ExamOffering(createdLui, createdExamOffering, getSchedulingService(), contextInfo);
        return createdExamOffering;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ExamOfferingInfo updateExamOffering(String examOfferingId, ExamOfferingInfo examOfferingInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

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
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
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
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo changeExamOfferingState(String examOfferingId, String stateKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        if(StringUtils.isBlank(stateKey)) {
            throw new OperationFailedException("The next state key is empty");
        }

        LuiInfo lui = luiService.getLui(examOfferingId, contextInfo);
        String thisStateKey = lui.getStateKey();

        if (!StringUtils.equals(thisStateKey, stateKey)){
            StatusInfo statusInfo = getStateTransitionsHelper().processStateConstraints(examOfferingId,stateKey,contextInfo);
            if (statusInfo.getIsSuccess()){
                lui.setStateKey(stateKey);
                try{
                    luiService.updateLui(lui.getId(), lui, contextInfo);
                }catch(Exception e){
                    throw new OperationFailedException("Failed to update State", e);
                }

                String propagationKey = thisStateKey + ":" + stateKey;
                Map<String,StatusInfo> stringStatusInfoMap = getStateTransitionsHelper().processStatePropagations(examOfferingId,propagationKey,contextInfo);
                for (StatusInfo statusInfo1 : stringStatusInfoMap.values()) {
                    if (!statusInfo1.getIsSuccess()){
                        throw new OperationFailedException(statusInfo1.getMessage());
                    }
                }
            }

        }
        return new StatusInfo();
    }

    @Override
    public List<ValidationResultInfo> validateExamOfferingRelation(String formatOfferingId, String examOfferingId, String examOfferingTypeKey,
                             String validationTypeKey, ExamOfferingRelationInfo examOfferingRelationInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        // validate
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamOfferingInfo> getExamOfferingsByExamPeriod(String examPeriodId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        //trap null parameters
        if (examPeriodId == null){
            throw new MissingParameterException("Exam Period ID is null");
        }

        ExamPeriodInfo examPeriod = acalService.getExamPeriod(examPeriodId, contextInfo); // check exam period exists
        if (examPeriod == null) {
            throw new OperationFailedException("Didn't retrieve the exam period with the examPeriodId: " + examPeriodId);
        }

        List<String> examOfferingIds = luiService.getLuiIdsByAtpAndType(examPeriodId, LuiServiceConstants.FINAL_EXAM_OFFERING_TYPE_KEY, contextInfo);
        List<ExamOfferingInfo> examOfferingInfos = new ArrayList<ExamOfferingInfo>(examOfferingIds.size());
        examOfferingTransformer.luis2ExamOfferings(examOfferingIds, examOfferingInfos, schedulingService, contextInfo);

        return examOfferingInfos;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ExamOfferingRelationInfo createExamOfferingRelation(String formatOfferingId, String examOfferingId, String examOfferingTypeKey,
                               ExamOfferingRelationInfo examOfferingRelationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        //trap null parameters
        if (formatOfferingId == null){
            throw new MissingParameterException("Format offering ID is null");
        }
        if (examOfferingId == null){
            throw new MissingParameterException("Exam offering ID is null");
        }
        if (!examOfferingRelationInfo.getFormatOfferingId().equals(formatOfferingId) && !examOfferingRelationInfo.getExamOfferingId().equals(examOfferingId)) {
            throw new InvalidParameterException("IDs do not match");
        }
        //set FO / EO relations
        LuiLuiRelationInfo luiRel = new LuiLuiRelationInfo();
        //transform
        LuiLuiRelationInfo  luiRetrn;
        getExamOfferingTransformer().transformEORel2LuiLuiRel(examOfferingRelationInfo, luiRel, examOfferingTypeKey);

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
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ExamOfferingRelationInfo updateExamOfferingRelation(String examOfferingRelationId, ExamOfferingRelationInfo examOfferingRelationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        //trap null parameters
        if (examOfferingRelationId == null){
            throw new MissingParameterException("examOfferingRelationId is null");
        }
        if (examOfferingRelationInfo == null){
            throw new MissingParameterException("examOfferingRelationInfo is null");
        }

        LuiLuiRelationInfo luiRetrn;
        try {
            //transform
            LuiLuiRelationInfo  luiRel = getLuiService().getLuiLuiRelation(examOfferingRelationId, contextInfo);
            getExamOfferingTransformer().transformEORel2LuiLuiRel(examOfferingRelationInfo, luiRel, luiRel.getTypeKey());

            //update
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
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteExamOfferingRelation(String examOfferingRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

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
    @Transactional(readOnly = true)
    public ExamOfferingRelationInfo getExamOfferingRelation(String examOfferingRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

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
    @Transactional(readOnly = true)
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByIds(List<String> examOfferingRelationIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        //trap null parameter
        if (examOfferingRelationIds == null || examOfferingRelationIds.isEmpty()){
            throw new MissingParameterException("examOfferingRelationIds is null");
        }

        //Retrieve ExamOfferingRelationInfos
        List<ExamOfferingRelationInfo> eoRels = new ArrayList<ExamOfferingRelationInfo>();
        try {
            List<LuiLuiRelationInfo>  luiRels = getLuiService().getLuiLuiRelationsByIds(examOfferingRelationIds, contextInfo);
            for (LuiLuiRelationInfo luiRel : luiRels) {
                ExamOfferingRelationInfo examOfferingRelationInfo = new ExamOfferingRelationInfo();
                getExamOfferingTransformer().transformLuiLuiRel2EORel(luiRel, examOfferingRelationInfo);
                eoRels.add(examOfferingRelationInfo);
            }
        } catch (Exception ex) {
            throw new OperationFailedException(OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE, ex);
        }
        return eoRels;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getExamOfferingRelationIdsByType(String relationshipTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        //trap null parameter
        if (relationshipTypeKey == null){
            throw new MissingParameterException("relationshipTypeKey is null");
        }

        //Retrieve examOfferingRelationIds
        try {
            return getLuiService().getLuiLuiRelationIdsByType(relationshipTypeKey, contextInfo);
        } catch (Exception ex) {
            throw new OperationFailedException(OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE, ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByFormatOffering(String formatOfferingId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        //trap null parameter
        if (formatOfferingId == null){
            throw new MissingParameterException("formatOfferingId is null");
        }

        //Retrieve ExamOfferingRelationInfos
        List<ExamOfferingRelationInfo> eoRels = new ArrayList<ExamOfferingRelationInfo>();
        try {
            List<LuiLuiRelationInfo>  luiRels = getLuiService().getLuiLuiRelationsByLui(formatOfferingId, contextInfo);
            for (LuiLuiRelationInfo luiRel : luiRels) {
                if (luiRel.getTypeKey().equals(LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY) &&
                        luiRel.getLuiId().equals(formatOfferingId)) { //getLuiLuiRelationsByLui query brings back both luiId and relatedLuiId > select
                    ExamOfferingRelationInfo examOfferingRelationInfo = new ExamOfferingRelationInfo();
                    getExamOfferingTransformer().transformLuiLuiRel2EORel(luiRel, examOfferingRelationInfo);
                    eoRels.add(examOfferingRelationInfo);
                }
            }
        } catch (Exception ex) {
            throw new OperationFailedException(OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE, ex);
        }
        return eoRels;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByExamOffering(String examOfferingId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        //trap null parameter
        if (examOfferingId == null){
            throw new MissingParameterException("examOfferingId is null");
        }

        //Retrieve ExamOfferingRelationInfos
        List<ExamOfferingRelationInfo> eoRels = new ArrayList<ExamOfferingRelationInfo>();
        try {
            List<LuiLuiRelationInfo>  luiRels = getLuiService().getLuiLuiRelationsByLui(examOfferingId, contextInfo);
            for (LuiLuiRelationInfo luiRel : luiRels) {
                if (//luiRel.getTypeKey().equals(LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_FO_TO_EO_TYPE_KEY) &&  //type should be passed as param?
                        luiRel.getRelatedLuiId().equals(examOfferingId) ) { //getLuiLuiRelationsByLui query brings back both luiId and relatedLuiId > select
                    ExamOfferingRelationInfo examOfferingRelationInfo = new ExamOfferingRelationInfo();
                    getExamOfferingTransformer().transformLuiLuiRel2EORel(luiRel, examOfferingRelationInfo);
                    eoRels.add(examOfferingRelationInfo);
                }
            }
        } catch (Exception ex) {
            throw new OperationFailedException(OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE, ex);
        }
        return eoRels;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getExamOfferingRelationIdsByActivityOffering(String activityOfferingId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        //trap null parameter
        if (activityOfferingId == null){
            throw new MissingParameterException("activityOfferingId is null");
        }

        //Retrieve ExamOfferingRelationInfos
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal("luiLuiRelationType", LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_EO_TYPE_KEY),
                PredicateFactory.like("attributes[AO%]", activityOfferingId)));

        QueryByCriteria criteria = qbcBuilder.build();
        List<String> eoRetrnIds = this.getLuiService().searchForLuiLuiRelationIds(criteria, contextInfo);
        return eoRetrnIds;

    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForExamOfferingRelationIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //trap null parameter
        if (criteria == null){
            throw new MissingParameterException("criteria is null");
        }

        //Retrieve ExamOfferingRelationIds
        try {
            return getLuiService().searchForLuiLuiRelationIds(criteria, contextInfo);
        } catch (Exception ex) {
            throw new OperationFailedException(OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE, ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamOfferingRelationInfo> searchForExamOfferingRelations(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        //trap null parameter
        if (criteria == null){
            throw new MissingParameterException("criteria is null");
        }

        //Retrieve ExamOfferingRelation
        try {
            List<ExamOfferingRelationInfo> examOfferingRelationInfos = new ArrayList<ExamOfferingRelationInfo>();
            List<LuiLuiRelationInfo> luiLuiRelationInfos = this.getLuiService().searchForLuiLuiRelations(criteria, contextInfo);
            for(LuiLuiRelationInfo luiLuiRelationInfo : luiLuiRelationInfos){
                ExamOfferingRelationInfo examOfferingRelationInfo = new ExamOfferingRelationInfo();
                this.getExamOfferingTransformer().transformLuiLuiRel2EORel(luiLuiRelationInfo, examOfferingRelationInfo);
                examOfferingRelationInfos.add(examOfferingRelationInfo);
            }
            return examOfferingRelationInfos;
        } catch (Exception ex) {
            throw new OperationFailedException(OPERATION_FAILED_EXCEPTION_ERROR_MESSAGE, ex);
        }
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

    public AcademicCalendarService getAcalService() {
        return acalService;
    }

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    public ExamOfferingTransformer getExamOfferingTransformer() {
        return examOfferingTransformer;
    }

    public void setExamOfferingTransformer(ExamOfferingTransformer examOfferingTransformer) {
        this.examOfferingTransformer = examOfferingTransformer;
    }

    public StateTransitionsHelper getStateTransitionsHelper() {
        return stateTransitionsHelper;
    }

    public void setStateTransitionsHelper(StateTransitionsHelper stateTransitionsHelper) {
        this.stateTransitionsHelper = stateTransitionsHelper;
    }
}

