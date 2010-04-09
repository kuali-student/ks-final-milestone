/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.lum.mock.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RefDocRelationInfoMock;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.entity.Meta;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.mock.service.DocumentRelationService;
import org.kuali.student.core.proposal.dao.ProposalDao;
import org.kuali.student.core.proposal.entity.Proposal;
import org.kuali.student.core.proposal.entity.ProposalDocRelation;
import org.kuali.student.core.proposal.entity.ProposalDocRelationAttribute;
import org.kuali.student.core.proposal.entity.ProposalDocRelationType;
import org.kuali.student.core.proposal.entity.ProposalRichText;
import org.kuali.student.core.proposal.service.impl.ProposalAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
@WebService(endpointInterface = "org.kuali.student.core.mock.service.DocumentRelationService", serviceName = "DocumentRelationService", portName = "DocumentRelationService", targetNamespace = "http://student.kuali.org/wsdl/documentRelation")
@Transactional(rollbackFor = {Throwable.class})
public class DocumentRelationServiceImpl implements DocumentRelationService {

    private ProposalDao proposalDao;

    public DocumentRelationServiceImpl() {
    }

    @Override
    public void createRefDocRelation(String refId, String docId, RefDocRelationInfoMock info) throws Exception {

        Proposal proposal = proposalDao.fetch(Proposal.class, refId);
        ProposalDocRelation proposalDocRelation = new ProposalDocRelation();

        BeanUtils.copyProperties(info, proposalDocRelation, new String[]{"id", "desc", "type", "cluId", "attributes", "documentId", "metaInfo"});

        proposalDocRelation.setProposal(proposal);
        ProposalDocRelationType relType = proposalDao.fetch(ProposalDocRelationType.class, "proposalDocRelationType.doctype1");// info.getType());
        proposalDocRelation.setType(relType);
        proposalDocRelation.setDocumentId(docId);

        proposalDocRelation.setDescr(ProposalAssembler.toRichText(ProposalRichText.class, info.getDesc()));
        proposalDocRelation.setAttributes(ProposalAssembler.toGenericAttributes(ProposalDocRelationAttribute.class, info.getAttributes(), proposalDocRelation, proposalDao));

        proposalDao.create(proposalDocRelation);
    }

    @Override
    public List<RefDocRelationInfoMock> getRefDocIdsForRef(String id) throws Exception {

        List<RefDocRelationInfoMock> relationInfos = new ArrayList<RefDocRelationInfoMock>();
        List<ProposalDocRelation> documentRelations = null; 
        try {
            documentRelations = proposalDao.getProposalDocRelationsByProposal(id);
        } catch (DoesNotExistException e) {
            return null;
        }

        for (ProposalDocRelation relation : documentRelations) {
            RefDocRelationInfoMock relationInfo = new RefDocRelationInfoMock();

            BeanUtils.copyProperties(relation, relationInfo, new String[]{"descr", "type", "meta", "proposal", "attributes", "documentId", "metaInfo", "versionInd"});

            relationInfo.setRefId(id);
            relationInfo.setId(relation.getId());

            MetaInfo metaInfo = new MetaInfo();
            Meta meta = relation.getMeta();
            metaInfo.setCreateId(meta.getCreateId());
            metaInfo.setCreateTime(meta.getCreateTime());
            metaInfo.setUpdateId(meta.getUpdateId());
            metaInfo.setUpdateTime(meta.getUpdateTime());
            metaInfo.setVersionInd(String.valueOf(relation.getVersionInd()));
            relationInfo.setMetaInfo(metaInfo);

            RichTextInfo richTextInfo = null;
            ProposalRichText richText = relation.getDescr();
            if (richText != null) {
                richTextInfo = new RichTextInfo();
                richTextInfo.setFormatted(richText.getFormatted());
                richTextInfo.setPlain(richText.getPlain());
            }

            relationInfo.setDesc(richTextInfo);
            relationInfos.add(relationInfo);
        }
        return relationInfos;
    }

    @Override
    public StatusInfo deleteRefDocRelation(String id) throws Exception {
        return deleteLuDocRelation(id);
    }

    public StatusInfo deleteLuDocRelation(String docRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(docRelationId, "luDocRelationId");
        proposalDao.delete(ProposalDocRelation.class, docRelationId);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    private void checkForMissingParameter(Object param, String paramName) throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

    public void setProposalDao(ProposalDao proposalDao) {
        this.proposalDao = proposalDao;
    }

}
