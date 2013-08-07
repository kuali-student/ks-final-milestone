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
package org.kuali.student.service.impl.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.proposal.service.ProposalService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;

public class ProposalServiceMockImpl implements MockService, ProposalService {
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order

    private Map<String, ProposalInfo> proposalMap = new LinkedHashMap<String, ProposalInfo>();

    @Override
    public void clear() {
        this.proposalMap.clear();
    }

    @Override
    public ProposalInfo getProposal(String proposalId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_BY_ID
        if (!this.proposalMap.containsKey(proposalId)) {
            throw new OperationFailedException(proposalId);
        }
        return new ProposalInfo(this.proposalMap.get(proposalId));
    }

    @Override
    public List<ProposalInfo> getProposalsByIds(List<String> proposalIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_BY_IDS
        List<ProposalInfo> list = new ArrayList<ProposalInfo>();
        for (String id : proposalIds) {
            list.add(this.getProposal(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<ProposalInfo> getProposalsByProposalType(String proposalTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<ProposalInfo> list = new ArrayList<ProposalInfo>();
        for (ProposalInfo info : proposalMap.values()) {
            if (proposalTypeKey.equals(info.getTypeKey())) {
                list.add(new ProposalInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<ProposalInfo> getProposalsByReference(String referenceTypeKey, String referenceId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<ProposalInfo> list = new ArrayList<ProposalInfo>();
        for (ProposalInfo info : proposalMap.values()) {
            if (referenceTypeKey.equals(info.getTypeKey())) {
                if (info.getProposalReference().contains(referenceId)) {
                    list.add(new ProposalInfo(info));
                }
            }
        }
        return list;
    }

    @Override
    public List<ProposalInfo> getProposalsByState(String proposalState, String proposalTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<ProposalInfo> list = new ArrayList<ProposalInfo>();
        for (ProposalInfo info : proposalMap.values()) {
            if (proposalState.equals(info.getStateKey())) {
                if (proposalTypeKey.equals(info.getTypeKey())) {
                    list.add(new ProposalInfo(info));
                }
            }
        }
        return list;
    }

    @Override
    public ProposalInfo getProposalByWorkflowId(String workflowId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // GET_BY_ID
        if (!this.proposalMap.containsKey(workflowId)) {
            throw new OperationFailedException(workflowId);
        }
        return new ProposalInfo(this.proposalMap.get(workflowId));
    }

    @Override
    public List<ValidationResultInfo> validateProposal(String validationTypeKey, ProposalInfo proposalInfo, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public ProposalInfo createProposal(String proposalTypeKey, ProposalInfo proposalInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // CREATE
        if (!proposalTypeKey.equals(proposalInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        ProposalInfo copy = new ProposalInfo(proposalInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        proposalMap.put(copy.getId(), copy);
        return new ProposalInfo(copy);
    }

    @Override
    public ProposalInfo updateProposal(String proposalId, ProposalInfo proposalInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // UPDATE
        if (!proposalId.equals(proposalInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        ProposalInfo copy = new ProposalInfo(proposalInfo);
        ProposalInfo old = this.getProposal(proposalInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.proposalMap.put(proposalInfo.getId(), copy);
        return new ProposalInfo(copy);
    }

    @Override
    public StatusInfo deleteProposal(String proposalId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, DependentObjectsExistException, OperationFailedException, PermissionDeniedException {
        // DELETE
        if (this.proposalMap.remove(proposalId) == null) {
            throw new OperationFailedException(proposalId);
        }
        return newStatus();
    }

    @Override
    public List<String> searchForProposalIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForProposalIds has not been implemented");
    }

    @Override
    public List<ProposalInfo> searchForProposals(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForProposals has not been implemented");
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

    @Override
    public List<String> getObjectTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
