/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.proposal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.entity.RichText;
import org.kuali.student.core.proposal.dto.ProposalDocRelationInfo;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.core.proposal.dto.ReferenceTypeInfo;
import org.kuali.student.core.proposal.entity.ObjectReference;
import org.kuali.student.core.proposal.entity.Proposal;
import org.kuali.student.core.proposal.entity.ProposalDocRelation;
import org.kuali.student.core.proposal.entity.ProposalOrg;
import org.kuali.student.core.proposal.entity.ProposalPerson;
import org.kuali.student.core.proposal.entity.ProposalType;
import org.kuali.student.core.proposal.entity.ReferenceType;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.springframework.beans.BeanUtils;

/**
 * Convert between DTO and enties
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class ProposalAssembler extends BaseAssembler {

    public static List<ProposalInfo> toProposalInfos(List<Proposal> entities) {
        List<ProposalInfo> dtos = new ArrayList<ProposalInfo>(entities.size());
        for (Proposal entity : entities) {
            dtos.add(toProposalInfo(entity));
        }
        return dtos;
    }

    public static ProposalInfo toProposalInfo(Proposal entity) {
        ProposalInfo dto = new ProposalInfo();


        BeanUtils.copyProperties(entity, dto,
                new String[] { "proposerPerson", "proposerOrg", "proposalReferenceType", "proposalReference", "detailDesc", "attributes", "type" });
        if (entity.getProposerPerson() != null) {
            List<String> personIds = new ArrayList<String>(entity.getProposerPerson().size());
            for (ProposalPerson person : entity.getProposerPerson()) {
                personIds.add(person.getPersonId());
            }
            dto.setProposerPerson(personIds);
        }
        if (entity.getProposerOrg() != null) {
            List<String> orgIds = new ArrayList<String>(entity.getProposerOrg().size());
            for (ProposalOrg org : entity.getProposerOrg()) {
                orgIds.add(org.getOrgId());
            }
            dto.setProposerOrg(orgIds);
        }

        dto.setProposalReferenceType(entity.getProposalReference().get(0).getType().getName());
        List<String> objectIds = new ArrayList<String>(entity.getProposalReference().size());
        for (ObjectReference object : entity.getProposalReference()) {
            objectIds.add(object.getObjectReferenceId());
        }
        dto.setProposalReference(objectIds);
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setType(entity.getType().getId());

        return dto;
    }

    public static List<ProposalTypeInfo> toProposalTypeInfos(List<ProposalType> entities) {
        List<ProposalTypeInfo> dtos = new ArrayList<ProposalTypeInfo>(entities.size());
        for (ProposalType entity : entities) {
            dtos.add(toProposalTypeInfo(entity));
        }
        return dtos;
    }

    public static ProposalTypeInfo toProposalTypeInfo(ProposalType entity) {
        ProposalTypeInfo dto = new ProposalTypeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "attributes" });

        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        return dto;
    }

    public static ProposalDocRelationInfo toProposalDocRelationInfo(ProposalDocRelation entity) {
        ProposalDocRelationInfo dto = new ProposalDocRelationInfo();


        BeanUtils.copyProperties(entity, dto,
                new String[] { "proposalId", "desc", "attributes", "metaInfo", "type" });

        dto.setProposalId(entity.getProposal().getId());
        dto.setDesc(toRichTextInfo(entity.getDesc()));
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
        dto.setType(entity.getType().getId());
        return dto;
    }
    
    public static List<ProposalDocRelationInfo> toProposalDocRelationInfos(List<ProposalDocRelation> entities){
        List<ProposalDocRelationInfo> dtos = new ArrayList<ProposalDocRelationInfo>(entities.size());
        for (ProposalDocRelation entity : entities) {
            dtos.add(toProposalDocRelationInfo(entity));
        }
        return dtos;
    }

    public static RichTextInfo toRichTextInfo(RichText entity) {
        RichTextInfo dto = new RichTextInfo();

        BeanUtils.copyProperties(entity, dto);

        return dto;
    }

    public static List<ReferenceTypeInfo> toReferenceTypeInfos(List<ReferenceType> entities) {
        List<ReferenceTypeInfo> dtos = new ArrayList<ReferenceTypeInfo>(entities.size());
        for (ReferenceType entity : entities) {
            dtos.add(toReferenceTypeInfo(entity));
        }
        return dtos;
    }

    public static ReferenceTypeInfo toReferenceTypeInfo(ReferenceType entity) {
        ReferenceTypeInfo dto = new ReferenceTypeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "attributes" });

        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        return dto;
    }

}
