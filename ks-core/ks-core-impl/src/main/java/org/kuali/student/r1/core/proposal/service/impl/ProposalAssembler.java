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

package org.kuali.student.r1.core.proposal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.kuali.student.r1.common.dto.ReferenceTypeInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r1.common.service.impl.BaseAssembler;
import org.kuali.student.r1.core.proposal.dao.ProposalDao;
import org.kuali.student.r1.core.proposal.dto.ProposalInfo;
import org.kuali.student.r1.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.r1.core.proposal.entity.Proposal;
import org.kuali.student.r1.core.proposal.entity.ProposalAttribute;
import org.kuali.student.r1.core.proposal.entity.ProposalOrg;
import org.kuali.student.r1.core.proposal.entity.ProposalPerson;
import org.kuali.student.r1.core.proposal.entity.ProposalReference;
import org.kuali.student.r1.core.proposal.entity.ProposalReferenceType;
import org.kuali.student.r1.core.proposal.entity.ProposalType;
import org.springframework.beans.BeanUtils;

/**
 * Convert between DTO and enties
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Deprecated
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
        dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
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
        dto.setDesc(entity.getDescr());
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
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
            if (!String.valueOf(proposal.getVersionNumber()).equals(proposalInfo.getMetaInfo().getVersionInd())){
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

        //Update proposal to person relations
        Map<String,ProposalPerson> existingPersons = new HashMap<String,ProposalPerson>();
        if(proposal.getProposerPerson()==null){
        	proposal.setProposerPerson(new ArrayList<ProposalPerson>(proposalInfo.getProposerPerson().size()));
        }
        for(ProposalPerson person:proposal.getProposerPerson()){
        	existingPersons.put(person.getPersonId(), person);
        }
        proposal.getProposerPerson().clear();
        for (String proposerId : proposalInfo.getProposerPerson()) {
        	ProposalPerson person;
        	if(existingPersons.containsKey(proposerId)){
        		person = existingPersons.get(proposerId);
        		existingPersons.remove(proposerId);
        	}else{
        		person = new ProposalPerson();
        	}
            person.setPersonId(proposerId);
            person.setProposal(proposal);
            proposal.getProposerPerson().add(person);
        }
        for (ProposalPerson person : existingPersons.values()) {
            dao.delete(person);
        }
        
        //Update proposal to org relations
        Map<String,ProposalOrg> existingOrgs = new HashMap<String,ProposalOrg>();
        if(proposal.getProposerOrg()==null){
        	proposal.setProposerOrg(new ArrayList<ProposalOrg>(proposalInfo.getProposerOrg().size()));
        }
        for(ProposalOrg org:proposal.getProposerOrg()){
        	existingOrgs.put(org.getOrgId(), org);
        }
        proposal.getProposerOrg().clear();
        for (String orgId: proposalInfo.getProposerOrg()) {
        	ProposalOrg org;
        	if(existingOrgs.containsKey(orgId)){
        		org = existingOrgs.get(orgId);
        		existingOrgs.remove(orgId);
        	}else{
        		org = new ProposalOrg();
        	}
        	org.setOrgId(orgId);
        	org.setProposal(proposal);
            proposal.getProposerOrg().add(org);
        }
        for (ProposalOrg proposalOrg : existingOrgs.values()) {
            dao.delete(proposalOrg);
        }
        
//        //Update proposal to reference object relations
//        Map<String,ProposalReference> existingReferences = new HashMap<String,ProposalReference>();
//        if(proposal.getProposalReference()==null){
//        	proposal.setProposalReference(new ArrayList<ProposalReference>(proposalInfo.getProposalReference().size()));
//        }
//        for(ProposalReference reference:proposal.getProposalReference()){
//        	existingReferences.put(reference.getObjectReferenceId(), reference);
//        }
//        proposal.getProposalReference().clear();
//        for (String referenceId: proposalInfo.getProposalReference()) {
//        	ProposalReference reference;
//        	if(existingReferences.containsKey(referenceId)){
//        		reference = existingReferences.get(referenceId);
//        		existingReferences.remove(referenceId);
//        	}else{
//        		reference = new ProposalReference();
//        	}
//        	reference.setObjectReferenceId(referenceId);
//            ProposalReferenceType refType = dao.fetch(ProposalReferenceType.class, proposalInfo.getProposalReferenceType());
//            reference.setType(refType);
//            proposal.getProposalReference().add(reference);
//        }
//        for (ProposalReference reference : existingReferences.values()) {
//            dao.delete(reference);
//        }
        
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
}
