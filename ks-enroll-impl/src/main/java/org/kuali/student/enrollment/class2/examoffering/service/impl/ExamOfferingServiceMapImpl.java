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

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.exam.dto.ExamInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
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

import javax.jws.WebParam;


public class ExamOfferingServiceMapImpl implements MockService, ExamOfferingService
{

    /////////////////////////////
    // DATA VARIABLES
    /////////////////////////////

    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, ExamOfferingInfo> examOfferingMap = new LinkedHashMap<String, ExamOfferingInfo>();
    private Map<String, ExamOfferingRelationInfo> examOfferingRelationMap = new LinkedHashMap<String, ExamOfferingRelationInfo>();

    /////////////////////////////
    // FUNCTIONALS
    /////////////////////////////

    @Override
    public void clear() {
        examOfferingMap.clear();
        examOfferingRelationMap.clear();
    }

    @Override
    public ExamOfferingInfo getExamOffering(String examOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.examOfferingMap.containsKey(examOfferingId)) {
            throw new DoesNotExistException(examOfferingId);
        }
        return new ExamOfferingInfo(this.examOfferingMap.get (examOfferingId));
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
        List<String> list = new ArrayList<String> ();
        for (ExamOfferingInfo info: examOfferingMap.values ()) {
            if (examTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
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
        // create
        if (!examTypeKey.equals (examOfferingInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        ExamOfferingInfo copy = new ExamOfferingInfo(examOfferingInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        examOfferingMap.put(copy.getId(), copy);
        return new ExamOfferingInfo(copy);
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
        // update
        if (!examOfferingId.equals (examOfferingInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        ExamOfferingInfo copy = new ExamOfferingInfo(examOfferingInfo);
        ExamOfferingInfo old = this.getExamOffering(examOfferingInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.examOfferingMap.put(examOfferingInfo.getId(), copy);
        return new ExamOfferingInfo(copy);
    }

    @Override
    public StatusInfo deleteExamOffering(String examOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.examOfferingMap.remove(examOfferingId) == null) {
            throw new DoesNotExistException(examOfferingId);
        }
        return newStatus();
    }

    @Override
    public StatusInfo changeExamOfferingState(String examOfferingId, String stateKey, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        ExamOfferingInfo eo = this.examOfferingMap.get(examOfferingId);
        if (eo==null){
            throw new DoesNotExistException(examOfferingId);
        }
        eo.setStateKey(stateKey);
        return newStatus();
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
        List<ExamOfferingInfo> list = new ArrayList<ExamOfferingInfo> ();
        for (ExamOfferingInfo info: examOfferingMap.values ()) {
            if (examPeriodId.equals(info.getExamPeriodId())) {
                list.add (this.getExamOffering(info.getId (), contextInfo));
            }
        }
        return list;
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
        // create
        if (!examOfferingTypeKey.equals (examOfferingRelationInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        ExamOfferingRelationInfo copy = new ExamOfferingRelationInfo(examOfferingRelationInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        examOfferingRelationMap.put(copy.getId(), copy);
        return new ExamOfferingRelationInfo(copy);
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
        // update
        if (!examOfferingRelationId.equals (examOfferingRelationInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        ExamOfferingRelationInfo copy = new ExamOfferingRelationInfo(examOfferingRelationInfo);
        ExamOfferingRelationInfo old = this.getExamOfferingRelation(examOfferingRelationInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.examOfferingRelationMap .put(examOfferingRelationInfo.getId(), copy);
        return new ExamOfferingRelationInfo(copy);
    }

    @Override
    public StatusInfo deleteExamOfferingRelation(String examOfferingRelationId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.examOfferingRelationMap.remove(examOfferingRelationId) == null) {
            throw new DoesNotExistException(examOfferingRelationId);
        }
        return newStatus();
    }

    @Override
    public ExamOfferingRelationInfo getExamOfferingRelation(String examOfferingRelationId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.examOfferingRelationMap.containsKey(examOfferingRelationId)) {
            throw new DoesNotExistException(examOfferingRelationId);
        }
        return new ExamOfferingRelationInfo(this.examOfferingRelationMap.get (examOfferingRelationId));
    }

    @Override
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByIds(List<String> examOfferingRelationIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<ExamOfferingRelationInfo> list = new ArrayList<ExamOfferingRelationInfo> ();
        for (String id: examOfferingRelationIds) {
            list.add (this.getExamOfferingRelation(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getExamOfferingRelationIdsByType(String relationshipTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (ExamOfferingRelationInfo info: examOfferingRelationMap.values ()) {
            if (relationshipTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByFormatOffering(String formatOfferingId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<ExamOfferingRelationInfo> list = new ArrayList<ExamOfferingRelationInfo> ();
        for (ExamOfferingRelationInfo info: examOfferingRelationMap.values ()) {
            if (formatOfferingId.equals(info.getFormatOfferingId())) {
                list.add (info);
            }
        }
        return list;
    }

    @Override
    public List<ExamOfferingRelationInfo> getExamOfferingRelationsByExamOffering(String examOfferingId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<ExamOfferingRelationInfo> list = new ArrayList<ExamOfferingRelationInfo> ();
        for (ExamOfferingRelationInfo info: examOfferingRelationMap.values ()) {
            if (examOfferingId.equals(info.getExamOfferingId())) {
                list.add (info);
            }
        }
        return list;
    }

    @Override
    public List<String> getExamOfferingRelationIdsByActivityOffering(String activityOfferingId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (ExamOfferingRelationInfo info: examOfferingRelationMap.values ()) {
            if (info.getActivityOfferingIds().contains(activityOfferingId)) {
                list.add (info.getId ());
            }
        }
        return list;
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

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
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

}

