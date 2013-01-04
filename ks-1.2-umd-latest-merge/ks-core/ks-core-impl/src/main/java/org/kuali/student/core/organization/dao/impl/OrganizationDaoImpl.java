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

package org.kuali.student.core.organization.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.common.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.core.organization.dao.OrganizationDao;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.entity.Org;
import org.kuali.student.core.organization.entity.OrgOrgRelation;
import org.kuali.student.core.organization.entity.OrgOrgRelationType;
import org.kuali.student.core.organization.entity.OrgPersonRelation;
import org.kuali.student.core.organization.entity.OrgPersonRelationType;
import org.kuali.student.core.organization.entity.OrgPositionRestriction;

public class OrganizationDaoImpl extends AbstractSearchableCrudDaoImpl implements OrganizationDao {

	@PersistenceContext(unitName = "Organization")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

    @Override
    public List<OrgPersonRelation> getAllOrgPersonRelationsByPerson(String personId) {
        Query query = em.createNamedQuery("OrgPersonRelation.getAllOrgPersonRelationsByPerson");
        query.setParameter("personId", personId);
        @SuppressWarnings("unchecked")
        List<OrgPersonRelation> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<OrgPersonRelation> getAllOrgPersonRelationsByOrg(String orgId) {
        Query query = em.createNamedQuery("OrgPersonRelation.getAllOrgPersonRelationsByOrg");
        query.setParameter("orgId", orgId);
        @SuppressWarnings("unchecked")
        List<OrgPersonRelation> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<String> getPersonIdsForOrgByRelationType(String orgId, String orgPersonRelationTypeKey) {
        Query query = em.createNamedQuery("OrgPersonRelationType.getPersonIdsForOrgByRelationType");
        query.setParameter("orgId", orgId);
        query.setParameter("orgPersonRelationTypeKey", orgPersonRelationTypeKey);
        @SuppressWarnings("unchecked")
        List<String> resultList = query.getResultList();
        return resultList;
    }

	@Override
	public List<Org> getOrganizationsByIdList(List<String> orgIdList) {
		Query query = em.createNamedQuery("Org.getOrganizationsByIdList");
        query.setParameter("orgIdList", orgIdList);
        @SuppressWarnings("unchecked")
		List<Org> orgs = query.getResultList();
        return orgs;
	}

	@Override
	public List<OrgOrgRelation> getOrgOrgRelationsByOrg(String orgId) {
		Query query = em.createNamedQuery("OrgOrgRelation.OrgOrgRelation");
		query.setParameter("orgId", orgId);
		@SuppressWarnings("unchecked")
		List<OrgOrgRelation> orgOrgRelations = query.getResultList();
		return orgOrgRelations;
	}

	@Override
	public List<OrgPositionRestriction> getPositionRestrictionsByOrg(
			String orgId) {
		Query query = em.createNamedQuery("OrgPositionRestriction.findOrgPositionRestrictions");
		query.setParameter("orgId", orgId);
		@SuppressWarnings("unchecked")
		List<OrgPositionRestriction> orgPositionRestrictions = query.getResultList();
		return orgPositionRestrictions;

	}

	@Override
	public List<String> getAllDescendants(String orgId, String orgHierarchy) {
		Query query = em.createNamedQuery("OrgOrgRelation.getAllDescendants");
		query.setParameter("orgHierarchy", orgHierarchy);
		return getAllLevels(query, "orgId", orgId);
	}

	@Override
	public List<String> getAllAncestors(String orgId, String orgHierarchy) {
		Query query = em.createNamedQuery("OrgOrgRelation.getAncestors");
		query.setParameter("orgHierarchy", orgHierarchy);
		return getAllLevels(query, "orgId", orgId);
	}

	private List<String> getAllLevels(Query query, String paramName, String paramValue) {
		// Eliminate dup's by using a set, but maintain order elements were inserted in
		Set<String> valSet = new LinkedHashSet<String>();
		query.setParameter(paramName, paramValue);
		@SuppressWarnings("unchecked")
		List<String> nextLevelList = query.getResultList();
		valSet.addAll(nextLevelList);
		for (String resultStr : nextLevelList) {
			valSet.addAll(getAllLevels(query, paramName, resultStr));
		}
		return new ArrayList<String>(valSet);
	}

	@Override
	public List<OrgOrgRelationType> getOrgOrgRelationTypesForOrgHierarchy(String orgHierarchyKey) {
		Query query = em.createNamedQuery("OrgOrgRelationType.getOrgOrgRelationTypesForOrgHierarchy");
		query.setParameter("orgHierarchy", orgHierarchyKey);
		@SuppressWarnings("unchecked")
		List<OrgOrgRelationType> orgOrgRelationTypes = query.getResultList();
		return orgOrgRelationTypes;

	}

	@Override
	public boolean validatePositionRestriction(String orgId,
			String orgPersonRelationTypeKey) {
		Query query = em.createNamedQuery("OrgPositionRestriction.validatePositionRestriction");
		query.setParameter("orgId", orgId);
		query.setParameter("orgPersonRelationTypeKey", orgPersonRelationTypeKey);
		Long valid = (Long)query.getSingleResult();
		return valid.intValue()>0;
	}

	@Override
	public List<OrgPersonRelationType> getOrgPersonRelationTypesForOrgType(String orgTypeKey) {
		Query query = em.createNamedQuery("OrgPersonRelationType.getOrgPersonRelationTypesForOrgType");
		query.setParameter("orgTypeKey", orgTypeKey);
		@SuppressWarnings("unchecked")
		List<OrgPersonRelationType> orgRelationTypes = query.getResultList();
		return orgRelationTypes;
	}

	@Override
	public List<OrgOrgRelation> getOrgOrgRelationsByIdList(List<String> orgOrgRelationIdList) {
		Query query = em.createNamedQuery("OrgOrgRelation.getOrgOrgRelationsByIdList");
		query.setParameter("idList", orgOrgRelationIdList);
		@SuppressWarnings("unchecked")
		List<OrgOrgRelation> orgRelationTypes = query.getResultList();
		return orgRelationTypes;
	}

	@Override
	public List<OrgPersonRelation> getOrgPersonRelationsByIdList(List<String> orgPersonRelationIdList) {
		Query query = em.createNamedQuery("OrgPersonRelation.getOrgPersonRelationsByIdList");
		query.setParameter("idList", orgPersonRelationIdList);
		@SuppressWarnings("unchecked")
		List<OrgPersonRelation> orgRelations = query.getResultList();
		return orgRelations;
	}

	@Override
	public List<OrgPersonRelation> getOrgPersonRelationsByPerson(String personId, String orgId) {
		Query query = em.createNamedQuery("OrgPersonRelation.getOrgPersonRelationsByPerson");
		query.setParameter("personId", personId);
		query.setParameter("orgId", orgId);
		@SuppressWarnings("unchecked")
		List<OrgPersonRelation> orgRelations = query.getResultList();

		return orgRelations;
	}

	@Override
	public List<OrgOrgRelationType> getOrgOrgRelationTypesForOrgType(String orgTypeKey) {

		// TODO Check query, not sure if matching against right orgKeyType value
		Query query = em.createNamedQuery("OrgOrgRelationType.getOrgOrgRelationTypesForOrgType");
		query.setParameter("orgTypeKey", orgTypeKey);
		@SuppressWarnings("unchecked")
		List<OrgOrgRelationType> orgOrgRelations = query.getResultList();

		return orgOrgRelations;
	}

	@Override
	public List<OrgOrgRelation> getOrgOrgRelationsByRelatedOrg(String relatedOrgId) {
		Query query = em.createNamedQuery("OrgOrgRelation.getOrgOrgRelationsByRelatedOrg");
		query.setParameter("relatedOrgId", relatedOrgId);
		@SuppressWarnings("unchecked")
		List<OrgOrgRelation> orgOrgRelations = query.getResultList();

		return orgOrgRelations;

	}

	@Override
	public List<OrgTreeInfo> getOrgTreeInfo(String orgId,
			String orgHierarchyId) {
		Query query = em.createNamedQuery("OrgOrgRelation.getOrgTreeInfo");
		query.setParameter("orgId",orgId);
		query.setParameter("orgHierarchyId",orgHierarchyId);
		@SuppressWarnings("unchecked")
		List<OrgTreeInfo> orgTreeInfos = query.getResultList();

		return orgTreeInfos;
	}

	@Override
	public boolean hasOrgPersonRelation(String orgId, String personId, String orgPersonRelationTypeKey) {
		Query query = em.createNamedQuery("OrgPersonRelationType.hasOrgPersonRelation");
		query.setParameter("orgId", orgId);
		query.setParameter("personId", personId);
		query.setParameter("orgPersonRelationTypeKey", orgPersonRelationTypeKey);
		Long valid = (Long)query.getSingleResult();
		return valid.intValue()>0;
	}

	@Override
	public boolean hasOrgOrgRelation(String orgId, String comparisonOrgId,
			String orgOrgRelationTypeKey) {

		Query query = em.createNamedQuery("OrgOrgRelation.hasOrgOrgRelation");
		query.setParameter("orgId",orgId);
		query.setParameter("comparisonOrgId",comparisonOrgId);
		query.setParameter("orgOrgRelationTypeKey",orgOrgRelationTypeKey);

		Long relationCount = (Long)query.getSingleResult();

		return relationCount>0;
	}
	@Override
	public OrgPositionRestriction getPositionRestrictionByOrgAndPersonRelationTypeKey(String orgId, String orgPersonRelationTypeKey) {
		Query query = em.createNamedQuery("OrgPositionRestriction.getPositionRestrictionByOrgAndPersonRelationTypeKey");
		query.setParameter("orgId",orgId);
		query.setParameter("orgPersonRelationTypeKey",orgPersonRelationTypeKey);

		return (OrgPositionRestriction) query.getSingleResult();
	}

    @Override
    public Long getOrgMemebershipCount(String orgId) {
        Query query = em.createNamedQuery("OrgPersonRelation.getOrgMembershipCount");
        query.setParameter("orgId", orgId);
        
        return (Long)query.getSingleResult();
    }
    
    @Override
    public Org getOrgByRelatedOrgAndType(String relatedOrgId, String relationTypeKey) {
        Query query = em.createNamedQuery("Org.getOrgByRelatedOrgAndType");
        query.setParameter("relatedOrgId", relatedOrgId);
        query.setParameter("relationTypeKey", relationTypeKey);

        try{
            return (Org) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
}
