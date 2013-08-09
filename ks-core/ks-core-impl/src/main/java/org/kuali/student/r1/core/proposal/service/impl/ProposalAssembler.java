/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.r1.core.proposal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r1.core.proposal.dao.ProposalDao;
import org.kuali.student.r1.core.proposal.entity.Proposal;
import org.kuali.student.r1.core.proposal.entity.ProposalAttribute;
import org.kuali.student.r1.core.proposal.entity.ProposalOrg;
import org.kuali.student.r1.core.proposal.entity.ProposalPerson;
import org.kuali.student.r1.core.proposal.entity.ProposalReference;
import org.kuali.student.r1.core.proposal.entity.ProposalReferenceType;
import org.kuali.student.r1.core.proposal.entity.ProposalType;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.service.assembly.BaseAssembler;

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
        dto.setId(entity.getId());
        dto.setTypeKey(entity.getType().getId());
        dto.setStateKey(entity.getState());
        dto.setName(entity.getName());
        dto.setDescr(new RichTextHelper().fromPlain(entity.getDetailDesc()));
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
        if (entity.getProposalReference() != null) {
            if (entity.getProposalReference().size() > 0) {
                dto.setProposalReferenceType(entity.getProposalReference().get(0).getType().getId());
            }
            List<String> objectIds = new ArrayList<String>(entity.getProposalReference().size());
            for (ProposalReference object : entity.getProposalReference()) {
                objectIds.add(object.getObjectReferenceId());
            }
            dto.setProposalReference(objectIds);
        }
        dto.setRationale(new RichTextHelper().fromPlain(entity.getRationale()));
        dto.setEffectiveDate(entity.getEffectiveDate());
        dto.setExpirationDate(entity.getExpirationDate());
        dto.setWorkflowId(entity.getWorkflowId());
        dto.setMeta(toMetaInfo(entity.getMeta(), entity.getVersionNumber()));
        dto.setAttributes(toAttributeList(entity.getAttributes()));
        return dto;
    }

    public static List<TypeInfo> toProposalTypeInfos(List<ProposalType> entities) {
        List<TypeInfo> dtos = new ArrayList<TypeInfo>(entities.size());
        for (ProposalType entity : entities) {
            dtos.add(toProposalTypeInfo(entity));
        }
        return dtos;
    }

    public static TypeInfo toProposalTypeInfo(ProposalType entity) {
        return BaseAssembler.toGenericTypeInfo(entity);
    }

    public static List<TypeInfo> toReferenceTypeInfos(List<ProposalReferenceType> entities) {
        List<TypeInfo> dtos = new ArrayList<TypeInfo>(entities.size());
        for (ProposalReferenceType entity : entities) {
            dtos.add(toReferenceTypeInfo(entity));
        }
        return dtos;
    }

    public static TypeInfo toReferenceTypeInfo(ProposalReferenceType entity) {
        return BaseAssembler.toGenericTypeInfo(entity);
    }

    public static Proposal toProposal(String proposalTypeKey, ProposalInfo info, ProposalDao dao)
            throws DoesNotExistException, VersionMismatchException, InvalidParameterException {
        Proposal entity;
        if (info.getId() != null && !info.getId().isEmpty()) {
            entity = dao.fetch(Proposal.class, info.getId());
            if (!String.valueOf(entity.getVersionNumber()).equals(info.getMeta().getVersionInd())) {
                throw new VersionMismatchException("Proposal to be updated is not the current version");
            }
        } else {
            entity = new Proposal();
            entity.setProposerPerson(new ArrayList<ProposalPerson>(0));
            entity.setProposerOrg(new ArrayList<ProposalOrg>(0));
        }
        // should only really do this on a create because type is readonly but this method is used for both
        // create and update so...
        ProposalType proposalType = dao.fetch(ProposalType.class, proposalTypeKey);
        entity.setType(proposalType);
        entity.setState(info.getStateKey());
        entity.setName(info.getName());
        if (info.getDescr() == null) {
            entity.setDetailDesc(null);
        } else {
            entity.setDetailDesc(info.getDescr().getPlain());
        }
        //Update proposal to person relations
        Map<String, ProposalPerson> existingPersons = new HashMap<String, ProposalPerson>();
        if (entity.getProposerPerson() == null) {
            entity.setProposerPerson(new ArrayList<ProposalPerson>(info.getProposerPerson().size()));
        }
        for (ProposalPerson person : entity.getProposerPerson()) {
            existingPersons.put(person.getPersonId(), person);
        }
        entity.getProposerPerson().clear();
        for (String proposerId : info.getProposerPerson()) {
            ProposalPerson person;
            if (existingPersons.containsKey(proposerId)) {
                person = existingPersons.get(proposerId);
                existingPersons.remove(proposerId);
            } else {
                person = new ProposalPerson();
            }
            person.setPersonId(proposerId);
            person.setProposal(entity);
            entity.getProposerPerson().add(person);
        }
        for (ProposalPerson person : existingPersons.values()) {
            dao.delete(person);
        }

        //Update proposal to org relations
        Map<String, ProposalOrg> existingOrgs = new HashMap<String, ProposalOrg>();
        if (entity.getProposerOrg() == null) {
            entity.setProposerOrg(new ArrayList<ProposalOrg>(info.getProposerOrg().size()));
        }
        for (ProposalOrg org : entity.getProposerOrg()) {
            existingOrgs.put(org.getOrgId(), org);
        }
        entity.getProposerOrg().clear();
        for (String orgId : info.getProposerOrg()) {
            ProposalOrg org;
            if (existingOrgs.containsKey(orgId)) {
                org = existingOrgs.get(orgId);
                existingOrgs.remove(orgId);
            } else {
                org = new ProposalOrg();
            }
            org.setOrgId(orgId);
            org.setProposal(entity);
            entity.getProposerOrg().add(org);
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

        if (info.getProposalReference() != null) {
            // Copy propsal references
            List<ProposalReference> references = new ArrayList<ProposalReference>(info.getProposalReference().size());
            for (String objectReferenceId : info.getProposalReference()) {
                ProposalReference ref;
                try {
                    ref = dao.getObjectReference(objectReferenceId, info.getProposalReferenceType());
                } catch (NoResultException e) {
                    ProposalReference objectReference = new ProposalReference();
                    objectReference.setObjectReferenceId(objectReferenceId);
                    ProposalReferenceType refType = dao.fetch(ProposalReferenceType.class, info.getProposalReferenceType());
                    objectReference.setType(refType);
                    ref = dao.create(objectReference);
                }
                references.add(ref);
            }
            entity.setProposalReference(references);
        }
        if (info.getRationale() == null) {
            entity.setRationale(null);
        } else {
            entity.setRationale(info.getRationale().getPlain());
        }
        entity.setEffectiveDate(info.getEffectiveDate());
        entity.setExpirationDate(info.getExpirationDate());
        entity.setWorkflowId(info.getWorkflowId());
        // Copy Attributes
        entity.setAttributes(toGenericAttributes(ProposalAttribute.class, info.getAttributes(), entity, dao));
        return entity;
    }
}
