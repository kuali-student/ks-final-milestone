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

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
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
import org.kuali.student.r2.core.class1.type.service.TypeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ExamOfferingServiceImpl implements ExamOfferingService
{

    private LuiService luiService;
    private TypeService typeService;
    private CriteriaLookupService criteriaLookupService;


    @Override
    public ExamOfferingInfo getExamOffering(String examOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("getExamOffering has not been implemented");
    }

    @Override
    public List<ExamOfferingInfo> getExamOfferingsByIds(List<String> examOfferingIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<ExamOfferingInfo> list = new ArrayList<ExamOfferingInfo> ();
        for (String id: examOfferingIds) {
            list.add (this.getExamOffering(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getExamOfferingIdsByType(String examTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("getExamOfferingIdsByType has not been implemented");
    }

    @Override
    public List<String> searchForExamOfferingIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForExamOfferingIds has not been implemented");
    }

    @Override
    public List<ExamOfferingInfo> searchForExamOfferings(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForExamOfferings has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateExamOffering(String termId, String examId, String examTypeKey, String validationTypeKey, ExamOfferingInfo examOfferingInfo, ContextInfo contextInfo)
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
    public ExamOfferingInfo createExamOffering(String termId, String examId, String examTypeKey, ExamOfferingInfo examOfferingInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {

        throw new OperationFailedException ("createExamOffering has not been implemented");
    }

    @Override
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
        throw new OperationFailedException ("updateExamOffering has not been implemented");
    }

    @Override
    public StatusInfo deleteExamOffering(String examOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("deleteExamOffering has not been implemented");
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

        throw new OperationFailedException ("createExamOfferingRelation has not been implemented");
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

        throw new OperationFailedException ("updateExamOfferingRelation has not been implemented");
    }

    @Override
    public StatusInfo deleteExamOfferingRelation(String examOfferingRelationId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("deleteExamOfferingRelation has not been implemented");
    }

    @Override
    public ExamOfferingRelationInfo getExamOfferingRelation(String examOfferingRelationId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("getExamOfferingRelation has not been implemented");
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

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public CriteriaLookupService getCriteriaLookupService() {
        return criteriaLookupService;
    }

    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }
}

