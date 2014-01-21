/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.ges.service.impl;



import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.UUIDHelper;
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
import org.kuali.student.core.ges.dto.GesCriteriaInfo;
import org.kuali.student.core.ges.dto.ParameterInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class GesServiceMapImpl implements MockService, GesService {


    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, ParameterInfo> parameterMap = new LinkedHashMap<String, ParameterInfo>();
    private Map<String, ValueInfo> valueMap = new LinkedHashMap<String, ValueInfo>();



    @Override
    public void clear() {
        this.parameterMap.clear();
        this.valueMap.clear();
    }


    @Override
    public ParameterInfo getParameter(String parameterKey, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_BY_ID
        if (!this.parameterMap.containsKey(parameterKey)) {
            throw new DoesNotExistException(parameterKey);
        }
        return new ParameterInfo(this.parameterMap.get(parameterKey));
    }

    @Override
    public List<ParameterInfo> getParametersByKeys(List<String> parameterKeys, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_BY_IDS
        List<ParameterInfo> list = new ArrayList<ParameterInfo>();
        for (String id : parameterKeys) {
            list.add(this.getParameter(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getParameterKeysByType(String parameterTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (ParameterInfo info : parameterMap.values()) {
            if (parameterTypeKey.equals(info.getTypeKey())) {
                list.add(info.getKey());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForParameterKeys(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForParameterKeys has not been implemented");
    }

    @Override
    public List<ParameterInfo> searchForParameters(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForParameters has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateParameter(String validationTypeKey, String parameterTypeKey, ParameterInfo parameterInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public ParameterInfo createParameter(String parameterKey, String parameterTypeKey, ParameterInfo parameterInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , DataValidationErrorException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException {
        // CREATE

        if (!parameterTypeKey.equals((parameterInfo.getTypeKey()))) {
            throw new InvalidParameterException("The typeKey parameter does not match the typeKey on the info object");
        }

        if (!parameterKey.equals((parameterInfo.getKey()))) {
            throw new InvalidParameterException("The key parameter does not match the key on the info object");
        }

        ParameterInfo copy = new ParameterInfo(parameterInfo);
        if (copy.getKey() == null) {
            copy.setKey(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        parameterMap.put(copy.getKey(), copy);
        return new ParameterInfo(copy);
    }

    @Override
    public ParameterInfo updateParameter(String parameterKey, ParameterInfo parameterInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException
            , VersionMismatchException {
        // UPDATE
        if (!parameterKey.equals(parameterInfo.getKey())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        ParameterInfo old = this.getParameter(parameterInfo.getKey(), contextInfo);

        // CREATE

        if (!old.getTypeKey().equals(parameterInfo.getTypeKey())) {
            throw new ReadOnlyException("The new typeKey does not match the old typeKey on the info object");
        }

        if (!old.getKey().equals(parameterInfo.getKey())) {
            throw new ReadOnlyException("The new key does not match the old key on the info object");
        }

        ParameterInfo copy = new ParameterInfo(parameterInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.parameterMap.put(parameterInfo.getKey(), copy);
        return new ParameterInfo(copy);
    }

    @Override
    public StatusInfo deleteParameter(String parameterKey, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // DELETE
        if (this.parameterMap.remove(parameterKey) == null) {
            throw new DoesNotExistException(parameterKey);
        }
        return new StatusInfo();
    }

    @Override
    public ValueInfo getValue(String valueId, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_BY_ID
        if (!this.valueMap.containsKey(valueId)) {
            throw new DoesNotExistException(valueId);
        }
        return new ValueInfo(this.valueMap.get(valueId));
    }

    @Override
    public List<ValueInfo> getValuesByIds(List<String> valueIds, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_BY_IDS
        List<ValueInfo> list = new ArrayList<ValueInfo>();
        for (String id : valueIds) {
            list.add(this.getValue(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getValueIdsByType(String valueTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (ValueInfo info : valueMap.values()) {
            if (valueTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForValueIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForValueIds has not been implemented");
    }

    @Override
    public List<ValueInfo> searchForValues(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForValues has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateValue(String validationTypeKey, String valueTypeKey, String parameterKey, ValueInfo valueInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public ValueInfo createValue(String typeKey, String parameterKey, ValueInfo valueInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            , DataValidationErrorException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException {
        // CREATE
        if (!typeKey.equals(valueInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        if (!parameterKey.equals(valueInfo.getParameterKey())) {
            throw new InvalidParameterException("The parameter id parameter does not match the parameter id on the info object");
        }

        ValueInfo copy =  new ValueInfo(valueInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        valueMap.put(copy.getId(), copy);
        return new ValueInfo(copy);
    }

    @Override
    public ValueInfo updateValue(String valueId, ValueInfo valueInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            , DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException
            , ReadOnlyException
            , VersionMismatchException {
        // UPDATE
        if (!valueId.equals(valueInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        ValueInfo old = this.getValue(valueInfo.getId(), contextInfo);

        if (!old.getParameterKey().equals(valueInfo.getParameterKey())) {
            throw new InvalidParameterException("The new parameter id does not match the old parameter id on the info object");
        }

        ValueInfo copy = new ValueInfo(valueInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.valueMap.put(valueInfo.getId(), copy);
        return new ValueInfo(copy);
    }

    @Override
    public StatusInfo deleteValue(String valueId, ContextInfo contextInfo)
            throws DoesNotExistException
            , InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // DELETE
        if (this.valueMap.remove(valueId) == null) {
            throw new OperationFailedException(valueId);
        }
        return new StatusInfo();
    }

    @Override
    public List<ValueInfo> getValuesByParameter(String parameterKey, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<ValueInfo> list = new ArrayList<ValueInfo>();
        for (ValueInfo info : valueMap.values()) {
            if (parameterKey.equals(info.getParameterKey())) {
                list.add(new ValueInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<ValueInfo> evaluateValues(String parameterKey, GesCriteriaInfo criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        return new ArrayList<ValueInfo>();

    }

    @Override
    public List<ValueInfo> evaluateValuesOnDate(String parameterKey, GesCriteriaInfo criteria, Date onDate, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {


        return new ArrayList<ValueInfo>();
    }

    @Override
    public ValueInfo evaluateValue(String parameterKey, GesCriteriaInfo criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , DoesNotExistException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        throw new DoesNotExistException("There are not any applicable values for the given criteria");

    }

    @Override
    public ValueInfo evaluateValueOnDate(String parameterKey, GesCriteriaInfo criteria, Date onDate, ContextInfo contextInfo)
            throws InvalidParameterException
            , DoesNotExistException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
            throw new DoesNotExistException("There are not any applicable values for the given criteria");

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

