/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.proposal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.entity.RichText;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.proposal.dao.ProposalDao;
import org.kuali.student.core.proposal.dto.ProposalDocRelationInfo;
import org.kuali.student.core.proposal.dto.ProposalDocRelationTypeInfo;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.core.dto.ReferenceTypeInfo;
import org.kuali.student.core.proposal.entity.ProposalReference;
import org.kuali.student.core.proposal.entity.Proposal;
import org.kuali.student.core.proposal.entity.ProposalAttribute;
import org.kuali.student.core.proposal.entity.ProposalDocRelation;
import org.kuali.student.core.proposal.entity.ProposalDocRelationAttribute;
import org.kuali.student.core.proposal.entity.ProposalDocRelationType;
import org.kuali.student.core.proposal.entity.ProposalOrg;
import org.kuali.student.core.proposal.entity.ProposalPerson;
import org.kuali.student.core.proposal.entity.ProposalType;
import org.kuali.student.core.proposal.entity.ProposalReferenceType;
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
                new String[] { "proposerPerson", "proposerOrg", "proposalReferenceType", "proposalReference", "attributes", "type" });
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

        if (entity.getProposalReference() != null){
            if (entity.getProposalReference().size() > 0){
                dto.setProposalReferenceType(entity.getProposalReference().get(0).getType().getId());
            }
            List<String> objectIds = new ArrayList<String>(entity.getProposalReference().size());
            for (ProposalReference object : entity.getProposalReference()) {
                objectIds.add(object.getObjectReferenceId());
            }
            dto.setProposalReference(objectIds);
        }
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
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

    public static List<ReferenceTypeInfo> toReferenceTypeInfos(List<ProposalReferenceType> entities) {
        List<ReferenceTypeInfo> dtos = new ArrayList<ReferenceTypeInfo>(entities.size());
        for (ProposalReferenceType entity : entities) {
            dtos.add(toReferenceTypeInfo(entity));
        }
        return dtos;
    }

    public static ReferenceTypeInfo toReferenceTypeInfo(ProposalReferenceType entity) {
        ReferenceTypeInfo dto = new ReferenceTypeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "attributes" });

        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        return dto;
    }

    public static Proposal toProposal(String proposalTypeKey, ProposalInfo  proposalInfo, ProposalDao dao) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
        Proposal proposal;
        if (proposalInfo.getId() != null && !proposalInfo.getId().isEmpty()) {
            proposal = dao.fetch(Proposal.class, proposalInfo.getId());
            if (!String.valueOf(proposal.getVersionInd()).equals(proposalInfo.getMetaInfo().getVersionInd())){
                throw new VersionMismatchException("Proposal to be updated is not the current version");
            }
        } else {
            proposal = new Proposal();
            proposal.setProposerPerson(new ArrayList<ProposalPerson>(0));
            proposal.setProposerOrg(new ArrayList<ProposalOrg>(0));
        }

        // Copy all basic properties
        BeanUtils.copyProperties(proposalInfo, proposal, new String[] { "id", "type",
                "attributes", "metaInfo", "proposerPerson", "proposerOrg", "proposalReference" });

        // Copy Attributes
        proposal.setAttributes(toGenericAttributes(ProposalAttribute.class, proposalInfo.getAttributes(), proposal, dao));

        // TODO Rework when JPA gets cascading deletes (2.0)
        List<ProposalPerson> persons = proposal.getProposerPerson();
        for (ProposalPerson person : persons) {
            dao.getEm().remove(person);
        }
        List<ProposalOrg> orgs = proposal.getProposerOrg();
        for (ProposalOrg org : orgs) {
            dao.getEm().remove(org);
        }
        persons.clear();
        orgs.clear();

        if (proposalInfo.getProposerPerson() != null) {
            // Copy ProposerPersons
            for (String proposer : proposalInfo.getProposerPerson()) {
                ProposalPerson person;
                try {
                    person = dao.getProposalPerson(proposer);
                } catch (NoResultException e) {
                    person = new ProposalPerson();
                    person.setPersonId(proposer);
                    person.setProposal(proposal);
                }
                persons.add(person);
            }
        }

        if (proposalInfo.getProposerOrg() != null) {
            // Copy ProposerPersons
            for (String proposer : proposalInfo.getProposerOrg()) {
                ProposalOrg org;
                try {
                    org = dao.getProposalOrg(proposer);
                } catch (NoResultException e) {
                    ProposalOrg proposerOrg = new ProposalOrg();
                    proposerOrg.setOrgId(proposer);
                    proposerOrg.setProposal(proposal);
                    org = dao.create(proposerOrg);
                }
                orgs.add(org);
            }
            proposal.setProposerOrg(orgs);
        }

        if (proposalInfo.getProposalReference() != null) {
            // Copy propsal references
            List<ProposalReference> references = new ArrayList<ProposalReference>(proposalInfo.getProposalReference().size());
            for (String objectReferenceId : proposalInfo.getProposalReference()) {
                ProposalReference ref;
                try {
                    ref = dao.getObjectReference(objectReferenceId, proposalInfo.getProposalReferenceType());
                } catch (NoResultException e) {
                    ProposalReference objectReference = new ProposalReference();
                    objectReference.setObjectReferenceId(objectReferenceId);
                    ProposalReferenceType refType = dao.fetch(ProposalReferenceType.class, proposalInfo.getProposalReferenceType());
                    objectReference.setType(refType);
                    ref = dao.create(objectReference);
                }
                references.add(ref);
            }
            proposal.setProposalReference(references);
        }

        ProposalType proposalType = dao.fetch(ProposalType.class, proposalTypeKey);
        proposal.setType(proposalType);
        return proposal;
    }

    public static ProposalDocRelation toProposalDocRelation(String proposalDocRelationType, String documentId, String proposalId, ProposalDocRelationInfo proposalDocRelationInfo, ProposalDao dao) throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
        ProposalDocRelation proposalDocRelation = new ProposalDocRelation();

        if (proposalDocRelationInfo.getId() != null && !proposalDocRelationInfo.getId().isEmpty()) {
            proposalDocRelation = dao.fetch(ProposalDocRelation.class, proposalDocRelationInfo.getId());
            if (!String.valueOf(proposalDocRelation.getVersionInd()).equals(proposalDocRelationInfo.getMetaInfo().getVersionInd())){
                throw new VersionMismatchException("Proposal to be updated is not the current version");
            }
        } else {
            proposalDocRelation = new ProposalDocRelation();
        }

        BeanUtils.copyProperties(proposalDocRelationInfo, proposalDocRelation, new String[] { "proposalId", "type",
                "attributes", "metaInfo", "desc" });

        proposalDocRelation.setDocumentId(documentId);
        proposalDocRelation.setDesc(toRichText(proposalDocRelationInfo.getDesc()));
        proposalDocRelation.setAttributes(toGenericAttributes(ProposalDocRelationAttribute.class, proposalDocRelationInfo.getAttributes(), proposalDocRelation, dao));

        Proposal proposal = dao.fetch(Proposal.class, proposalId);
        proposalDocRelation.setProposal(proposal);

        ProposalDocRelationType docRelType = dao.fetch(ProposalDocRelationType.class, proposalDocRelationType);
        proposalDocRelation.setType(docRelType);

        return proposalDocRelation;
    }

    public static List<ProposalDocRelationTypeInfo> toProposalDocRelationTypeInfos(List<ProposalDocRelationType> entities) {
        List<ProposalDocRelationTypeInfo> dtos = new ArrayList<ProposalDocRelationTypeInfo>(entities.size());
        for (ProposalDocRelationType entity : entities) {
            dtos.add(toProposalDocRelationTypeInfo(entity));
        }
        return dtos;
    }

    public static ProposalDocRelationTypeInfo toProposalDocRelationTypeInfo(ProposalDocRelationType entity) {
        ProposalDocRelationTypeInfo dto = new ProposalDocRelationTypeInfo();

        BeanUtils.copyProperties(entity, dto, new String[]{"attributes"});

        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }
    
    public static List<String> toProposalDocRelationTypeKeyList(List<ProposalDocRelationType> entities){
        List<String> dtos = new ArrayList<String>(entities.size());
        for(ProposalDocRelationType entity:entities){
            dtos.add(entity.getId());
        }
        
        return dtos;
    }
}
