/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.lum.mock.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.kuali.student.core.dto.RefDocRelationInfoMock;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.mock.service.DocumentRelationService;
import org.kuali.student.core.proposal.dto.ProposalDocRelationInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
@WebService(endpointInterface = "org.kuali.student.core.mock.service.DocumentRelationService", serviceName = "DocumentRelationService", portName = "DocumentRelationService", targetNamespace = "http://student.kuali.org/wsdl/documentRelation")
@Transactional(rollbackFor = {Throwable.class})
public class DocumentRelationServiceImpl implements DocumentRelationService {
    private static final String DOC_RELATION_TYPE = "kuali.proposal.docRelationType.attachment";
    private ProposalService proposalService;
    
    public DocumentRelationServiceImpl() {
    }

    @Override
    public void createRefDocRelation(String refId, String docId, RefDocRelationInfoMock info) throws Exception {

        ProposalDocRelationInfo docRelInfo = new ProposalDocRelationInfo();
        BeanUtils.copyProperties(info, docRelInfo);
        proposalService.createProposalDocRelation(DOC_RELATION_TYPE, docId, refId, docRelInfo);
    }

    @Override
    public List<RefDocRelationInfoMock> getRefDocIdsForRef(String id) throws Exception {
        List<ProposalDocRelationInfo> infos;
        try {
            infos = proposalService.getProposalDocRelationsByProposal(id);
        } catch (DoesNotExistException e) {
            System.out.println("%%%DOES NOT EXIST");
            return null;
        }
        List<RefDocRelationInfoMock> result = new ArrayList<RefDocRelationInfoMock>();
        for(ProposalDocRelationInfo propDocInfo : infos) {
            RefDocRelationInfoMock info = new RefDocRelationInfoMock();
            BeanUtils.copyProperties(propDocInfo, info, new String[]{"proposalId"});
            info.setRefId(propDocInfo.getProposalId());
            result.add(info);
        }
        
        return result;
    }

    @Override
    public StatusInfo deleteRefDocRelation(String id) throws Exception {
        return deleteLuDocRelation(id);
    }

    public StatusInfo deleteLuDocRelation(String docRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = proposalService.deleteProposalDocRelation(docRelationId);
        result.setSuccess(true);

        return result;
    }

    private void checkForMissingParameter(Object param, String paramName) throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

    public void setProposalService(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

}
