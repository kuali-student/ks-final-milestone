/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.exemption.service.impl;

import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.dto.ExemptionRequestInfo;
import org.kuali.student.r2.core.exemption.service.ExemptionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExemptionServiceMockImpl implements ExemptionService, MockService {

	
    @Override
	public void clear() {

    	this.exemptionMap.clear();
    	this.exemptionRequestMap.clear();
    	
	}

	@Override
    public List<ValidationResultInfo> validateExemptionRequest(String validationTypeKey, ExemptionRequestInfo exemptionRequestInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, ExemptionRequestInfo> exemptionRequestMap = new LinkedHashMap<String, ExemptionRequestInfo>();

    @Override
    public ExemptionRequestInfo createExemptionRequest(String personId, String exemptionRequestTypeKey, ExemptionRequestInfo exemptionRequestInfo, ContextInfo contextInfo)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // create 
        if (!exemptionRequestTypeKey.equals(exemptionRequestInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        ExemptionRequestInfo copy = new ExemptionRequestInfo(exemptionRequestInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        exemptionRequestMap.put(copy.getId(), copy);
        return new ExemptionRequestInfo(copy);
    }

    @Override
    public ExemptionRequestInfo updateExemptionRequest(String exemptionRequestId, ExemptionRequestInfo exemptionRequestInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // update
        if (!exemptionRequestId.equals(exemptionRequestInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        ExemptionRequestInfo copy = new ExemptionRequestInfo(exemptionRequestInfo);
        ExemptionRequestInfo old = this.getExemptionRequest(exemptionRequestInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.exemptionRequestMap.put(exemptionRequestInfo.getId(), copy);
        return new ExemptionRequestInfo(copy);
    }

    @Override
    public StatusInfo deleteExemptionRequest(String exemptionRequestId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.exemptionRequestMap.remove(exemptionRequestId) == null) {
            throw new DoesNotExistException(exemptionRequestId);
        }
        return newStatus();
    }

    @Override
    public ExemptionRequestInfo getExemptionRequest(String exemptionRequestId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!this.exemptionRequestMap.containsKey(exemptionRequestId)) {
            throw new DoesNotExistException(exemptionRequestId);
        }
        return this.exemptionRequestMap.get(exemptionRequestId);
    }

    @Override
    public List<ExemptionRequestInfo> getExemptionRequestsByIds(List<String> exemptionRequestIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionRequestInfo> list = new ArrayList<ExemptionRequestInfo>();
        for (String id : exemptionRequestIds) {
            list.add(this.getExemptionRequest(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getExemptionRequestIdsByType(String exemptionRequestTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (ExemptionRequestInfo info : exemptionRequestMap.values()) {
            if (exemptionRequestTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<ExemptionRequestInfo> getRequestsByRequester(String requesterId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionRequestInfo> list = new ArrayList<ExemptionRequestInfo>();
        for (ExemptionRequestInfo info : exemptionRequestMap.values()) {
            if (requesterId.equals(info.getRequesterId())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<ExemptionRequestInfo> getRequestsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionRequestInfo> list = new ArrayList<ExemptionRequestInfo>();
        for (ExemptionRequestInfo info : exemptionRequestMap.values()) {
            if (personId.equals(info.getPersonId())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<ValidationResultInfo> validateExemption(String validationTypeKey, ExemptionInfo exemptionInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, ExemptionInfo> exemptionMap = new LinkedHashMap<String, ExemptionInfo>();

    @Override
    public ExemptionInfo createExemption(String exemptionRequestId, String exemptionTypeKey, ExemptionInfo exemptionInfo, ContextInfo contextInfo)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // create 
        if (!exemptionTypeKey.equals(exemptionInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        ExemptionInfo copy = new ExemptionInfo(exemptionInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        exemptionMap.put(copy.getId(), copy);
        return new ExemptionInfo(copy);
    }

    @Override
    public ExemptionInfo updateExemption(String exemptionId, ExemptionInfo exemptionInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // update
        if (!exemptionId.equals(exemptionInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        ExemptionInfo copy = new ExemptionInfo(exemptionInfo);
        ExemptionInfo old = this.getExemption(exemptionInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.exemptionMap.put(exemptionInfo.getId(), copy);
        return new ExemptionInfo(copy);
    }

    @Override
    public StatusInfo addUseToExemption(String exemptionId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("addUseToExemption has not been implemented");
    }

    @Override
    public StatusInfo deleteExemption(String exemptionId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.exemptionMap.remove(exemptionId) == null) {
            throw new DoesNotExistException(exemptionId);
        }
        return newStatus();
    }

    @Override
    public ExemptionInfo getExemption(String exemptionId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (!this.exemptionMap.containsKey(exemptionId)) {
            throw new DoesNotExistException(exemptionId);
        }
        return this.exemptionMap.get(exemptionId);
    }

    @Override
    public List<ExemptionInfo> getExemptionsByIds(List<String> exemptionIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionInfo> list = new ArrayList<ExemptionInfo>();
        for (String id : exemptionIds) {
            list.add(this.getExemption(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getExemptionIdsByType(String exemptionTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (ExemptionInfo info : exemptionMap.values()) {
            if (exemptionTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<ExemptionInfo> getExemptionsForPerson(String personId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getExemptionsForPerson has not been implemented");
    }

    @Override
    public List<ExemptionInfo> getExemptionsForRequest(String requestId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getExemptionsForRequest has not been implemented");
    }

    @Override
    public List<ExemptionInfo> getActiveExemptionsForPerson(String personId, Date asOfDate, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getActiveExemptionsForPerson has not been implemented");
    }

    @Override
    public List<ExemptionInfo> getExemptionsByTypeForPerson(String typeKey, String personId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionInfo> list = new ArrayList<ExemptionInfo>();
        for (ExemptionInfo info : exemptionMap.values()) {
            if (typeKey.equals(info.getTypeKey())) {
                if (personId.equals(info.getPersonId())) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    private boolean asOfCheck(Date asOfDate, ExemptionInfo info) {
        if (info.getEffectiveDate() != null) {
            if (asOfDate.before(info.getEffectiveDate())) {
                return false;

            }
        }
        if (info.getExpirationDate() != null) {
            if (asOfDate.after(info.getExpirationDate())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<ExemptionInfo> getActiveExemptionsByTypeForPerson(String typeKey, String personId, Date asOfDate, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionInfo> list = new ArrayList<ExemptionInfo>();
        for (ExemptionInfo info : exemptionMap.values()) {
            if (typeKey.equals(info.getTypeKey())) {
                if (personId.equals(info.getPersonId())) {
                    if (asOfCheck(asOfDate, info)) {
                        list.add(info);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<ExemptionInfo> getActiveExemptionsByTypeProcessAndCheckForPerson(String typeKey, String processKey, String checkId, String personId, Date asOfDate, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ExemptionInfo> list = new ArrayList<ExemptionInfo>();
        for (ExemptionInfo info : exemptionMap.values()) {
            if (typeKey.equals(info.getTypeKey())) {
                if (processKey.equals(info.getProcessKey())) {
                    if (checkId.equals(info.getCheckId())) {
                        if (personId.equals(info.getPersonId())) {
                            if (asOfCheck(asOfDate, info)) {
                                list.add(info);
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    private MetaInfo newMeta(ContextInfo contextInfo) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(contextInfo.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(contextInfo.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo contextInfo) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(contextInfo.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }
}
