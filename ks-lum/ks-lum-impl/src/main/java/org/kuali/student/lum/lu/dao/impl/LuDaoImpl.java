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

package org.kuali.student.lum.lu.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.common.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluCluRelation;
import org.kuali.student.lum.lu.entity.CluLoRelation;
import org.kuali.student.lum.lu.entity.CluPublication;
import org.kuali.student.lum.lu.entity.CluResult;
import org.kuali.student.lum.lu.entity.CluResultType;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.Lui;
import org.kuali.student.lum.lu.entity.LuiLuiRelation;

import edu.emory.mathcs.backport.java.util.Collections;

public class LuDaoImpl extends AbstractSearchableCrudDaoImpl implements LuDao {

	@PersistenceContext(unitName = "Lu")
	@Override
	public void setEm(EntityManager em) {
		super.setEm(em);
	}

	@Override
	public List<Clu> getClusByIdList(List<String> cluIdList) {
		Query query = em.createNamedQuery("Clu.findClusByIdList");
		query.setParameter("idList", cluIdList);
		@SuppressWarnings("unchecked")
		List<Clu> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<Clu> getClusByLuType(String luTypeKey, String luState) {
		Query query = em.createNamedQuery("Clu.getClusByLuType");
		query.setParameter("luTypeKey", luTypeKey);
		query.setParameter("luState", luState);
		@SuppressWarnings("unchecked")
		List<Clu> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<CluSet> getCluSetInfoByIdList(List<String> cluSetIdList) {
		Query query = em.createNamedQuery("CluSet.getCluSetInfoByIdList");
		query.setParameter("cluSetIdList", cluSetIdList);
		@SuppressWarnings("unchecked")
		List<CluSet> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<Lui> getLuisByIdList(List<String> luiIds) {
		Query query = em.createNamedQuery("Lui.getLuisByIdList");
		query.setParameter("luiIdList", luiIds);
		@SuppressWarnings("unchecked")
		List<Lui> luis = query.getResultList();
		return luis;
	}

	@Override
	public List<String> getLuiIdsByCluId(String cluId) {
		Query query = em.createNamedQuery("Lui.getLuiIdsByCluId");
		query.setParameter("cluId", cluId);
		@SuppressWarnings("unchecked")
		List<String> luiIds = query.getResultList();
		return luiIds;
	}

	@Override
	public List<Lui> getLuisByRelationType(String luiId,
			String luLuRelationTypeId) {
		Query query = em
				.createNamedQuery("LuiLuiRelation.getLuisByRelationType");
		query.setParameter("luiId", luiId);
		query.setParameter("luLuRelationTypeId", luLuRelationTypeId);

		@SuppressWarnings("unchecked")
		List<Lui> luis = query.getResultList();
		return luis;
	}

	@Override
	public List<String> getLuiIdsByRelationType(String relatedLuiId,
			String luLuRelationTypeId) {
		Query query = em
				.createNamedQuery("LuiLuiRelation.getLuiIdsByRelationType");
		query.setParameter("luiId", relatedLuiId);
		query.setParameter("luLuRelationTypeId", luLuRelationTypeId);

		@SuppressWarnings("unchecked")
		List<String> luiIds = query.getResultList();
		return luiIds;
	}

	@Override
	public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey) {
		Query query = em.createNamedQuery("Lui.getLuiIdsInAtpByCluId");
		query.setParameter("cluId", cluId);
		query.setParameter("atpKey", atpKey);
		@SuppressWarnings("unchecked")
		List<String> luiIds = query.getResultList();
		return luiIds;
	}

	@Override
	public Boolean isCluInCluSet(String cluId, String cluSetId) {
		Query query = em.createNamedQuery("CluSet.isCluInCluSet");
		query.setParameter("cluId", cluId);
		query.setParameter("cluSetId", cluSetId);
		Long valid = (Long) query.getSingleResult();
		return valid.intValue() > 0;
	}

	@Override
	public List<LuiLuiRelation> getLuiLuiRelations(String luiId) {
		Query query = em
				.createNamedQuery("LuiLuiRelation.getLuiLuiRelationsByLuiId");
		query.setParameter("luiId", luiId);
		@SuppressWarnings("unchecked")
		List<LuiLuiRelation> luiLuiRelations = query.getResultList();
		return luiLuiRelations;
	}

	@Override
	public List<CluCluRelation> getCluCluRelationsByClu(String cluId) {
		Query query = em.createNamedQuery("CluCluRelation.getCluCluRelation");
		query.setParameter("cluId", cluId);
		@SuppressWarnings("unchecked")
		List<CluCluRelation> cluCluRelations = query.getResultList();
		return cluCluRelations;
	}

	@Override
	public List<String> getCluIdsByLoId(String loId) {
		Query query = em.createNamedQuery("Clu.getCluIdsByLoId");
		query.setParameter("loId", loId);
		@SuppressWarnings("unchecked")
		List<String> cluIds = query.getResultList();
		return cluIds;
	}

	@Override
	public List<String> getRelatedCluIdsByCluId(String cluId,
			String luLuRelationTypeId) {
		Query query = em
				.createNamedQuery("CluCluRelation.getRelatedCluIdsByCluId");
		query.setParameter("cluId", cluId);
		query.setParameter("luLuRelationTypeId", luLuRelationTypeId);
		@SuppressWarnings("unchecked")
		List<String> relatedCluIds = query.getResultList();
		return relatedCluIds;
	}

	@Override
    public List<String> getCluIdsByRelatedCluId(String relatedCluId, String luLuRelationTypeId) {
        Query query = em.createNamedQuery("CluCluRelation.getCluIdsByRelatedCluId");
        query.setParameter("relatedCluId", relatedCluId);
        query.setParameter("luLuRelationTypeId", luLuRelationTypeId);
        @SuppressWarnings("unchecked")
        List<String> relatedCluIds = query.getResultList();
        return relatedCluIds;
    }

    @Override
	public List<Clu> getRelatedClusByCluId(String cluId,
			String luLuRelationTypeId) {
		Query query = em
				.createNamedQuery("CluCluRelation.getRelatedClusByCluId");
		query.setParameter("cluId", cluId);
		query.setParameter("luLuRelationTypeId", luLuRelationTypeId);
		@SuppressWarnings("unchecked")
		List<Clu> relatedClus = query.getResultList();
		return relatedClus;
	}

    @Override
	public List<Clu> getClusByRelatedCluId(String relatedCluId,
			String luLuRelationTypeId) {
		Query query = em
				.createNamedQuery("CluCluRelation.getClusByRelatedCluId");
		query.setParameter("relatedCluId", relatedCluId);
		query.setParameter("luLuRelationTypeId", luLuRelationTypeId);
		@SuppressWarnings("unchecked")
		List<Clu> relatedClus = query.getResultList();
		return relatedClus;
	}
    
    
    
	@Override
	public List<String> getRelatedLuiIdsByLuiId(String luiId,
			String luLuRelationTypeId) {
		Query query = em
				.createNamedQuery("LuiLuiRelation.getRelatedLuiIdsByLuiId");
		query.setParameter("luiId", luiId);
		query.setParameter("luLuRelationTypeId", luLuRelationTypeId);
		@SuppressWarnings("unchecked")
		List<String> relatedLuiIds = query.getResultList();
		return relatedLuiIds;
	}

	@Override
	public List<Lui> getRelatedLuisByLuiId(String luiId,
			String luLuRelationTypeId) {
		Query query = em
				.createNamedQuery("LuiLuiRelation.getRelatedLuisByLuiId");
		query.setParameter("luiId", luiId);
		query.setParameter("luLuRelationTypeId", luLuRelationTypeId);
		@SuppressWarnings("unchecked")
		List<Lui> relatedLuis = query.getResultList();
		return relatedLuis;
	}

	@Override
	public List<Clu> getClusByRelation(String parentCluId,
			String luLuRelationTypeKey) {
		Query query = em.createNamedQuery("Clu.getClusByRelation");
		query.setParameter("parentCluId", parentCluId);
		query.setParameter("luLuRelationTypeKey", luLuRelationTypeKey);
		@SuppressWarnings("unchecked")
		List<Clu> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<CluLoRelation> getCluLoRelationsByClu(String cluId) {
		Query query = em
				.createNamedQuery("CluLoRelation.getCluLoRelationByClu");
		query.setParameter("cluId", cluId);
		@SuppressWarnings("unchecked")
		List<CluLoRelation> cluLoRelations = query.getResultList();
		return cluLoRelations;
	}

	@Override
	public List<CluLoRelation> getCluLoRelationsByLo(String loId) {
		Query query = em
				.createNamedQuery("CluLoRelation.getCluLoRelationByLo");
		query.setParameter("loId", loId);
		@SuppressWarnings("unchecked")
		List<CluLoRelation> cluLoRelations = query.getResultList();
		return cluLoRelations;
	}

	@Override
	public List<CluLoRelation> getCluLoRelationsByCludIdAndLoId(String cluId,
			String loId) {
		Query query = em.createNamedQuery("CluLoRelation.getCluLoRelation");
		query.setParameter("cluId", cluId);
		query.setParameter("loId", loId);

		@SuppressWarnings("unchecked")
		List<CluLoRelation> rels = query.getResultList();
		return rels;
	}

	@Override
	public List<String> getAllowedLuLuRelationTypesForLuType(String luTypeId,
			String relatedLuTypeId) {
		Query query = em
				.createNamedQuery("AllowedLuLuRelationType.getAllowedTypesByLuTypes");
		query.setParameter("luTypeId", luTypeId);
		query.setParameter("relatedLuTypeId", relatedLuTypeId);
		@SuppressWarnings("unchecked")
		List<String> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<String> getAllowedCluLoRelationTypesForLuType(String luTypeId) {
		Query query = em
				.createNamedQuery("AllowedCluLoRealtionType.getAllowedTypesByLuType");
		query.setParameter("luTypeId", luTypeId);
		@SuppressWarnings("unchecked")
		List<String> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<String> getAllowedResultUsageTypesForLuType(String luTypeId) {
		Query query = em
				.createNamedQuery("AllowedResultUsageLuType.getAllowedTypesByLuType");
		query.setParameter("luTypeId", luTypeId);
		@SuppressWarnings("unchecked")
		List<String> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<String> getAllowedResultComponentTypesForResultUsageType(
			String resultUsageType) {
		Query query = em
				.createNamedQuery("AllowedResultComponentUsageType.getAllowedComponentsByUsageType");
		query.setParameter("resultUsageType", resultUsageType);
		@SuppressWarnings("unchecked")
		List<String> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<CluResultType> getAllowedCluResultTypesForLuType(String luTypeId) {
		Query query = em
				.createNamedQuery("AllowedCluResultLuType.getAllowedTypesByLuType");
		query.setParameter("luTypeId", luTypeId);
		@SuppressWarnings("unchecked")
		List<CluResultType> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<String> getCluIdsByResultUsageType(String resultUsageTypeKey) {
		Query query = em
				.createNamedQuery("CluResult.getCluIdByResultUsageType");
		query.setParameter("resultUsageType", resultUsageTypeKey);
		@SuppressWarnings("unchecked")
		List<String> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<String> getCluIdsByResultComponentId(String resultComponentId) {
		Query query = em
				.createNamedQuery("CluResult.getCluIdByResultComponentId");
		query.setParameter("resultComponentId", resultComponentId);
		@SuppressWarnings("unchecked")
		List<String> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<String> getAllowedLuLuRelationTypesByLuiId(String luiId,
			String relatedLuiId) {
		Query query = em
				.createNamedQuery("LuiLuiRelation.getRelationTypeByLuiId");
		query.setParameter("luiId", luiId);
		query.setParameter("relatedLuiId", relatedLuiId);
		@SuppressWarnings("unchecked")
		List<String> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<String> getAllowedLuLuRelationTypesByCluId(String cluId,
			String relatedCluId) {
		Query query = em
				.createNamedQuery("CluCluRelation.getRelationTypeByCluId");
		query.setParameter("cluId", cluId);
		query.setParameter("relatedCluId", relatedCluId);
		@SuppressWarnings("unchecked")
		List<String> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<CluResult> getCluResultByClu(String cluId) {
		Query query = em
				.createNamedQuery("CluResult.getCluResultByCluId");
		query.setParameter("cluId", cluId);
		@SuppressWarnings("unchecked")
		List<CluResult> resultList = query.getResultList();
		return resultList;
	}

    @Override
    public Clu getLatestCluVersion(String cluVersionIndId) {
        Query query = em.createNamedQuery("Clu.findLatestClu");
        query.setParameter("versionIndId", cluVersionIndId);
        Clu clu = (Clu)query.getSingleResult();
        return clu;
    }

	@Override
	public Clu getCurrentCluVersion(String cluVersionIndId) {
        Query query = em.createNamedQuery("Clu.findCurrentClu");
        query.setParameter("versionIndId", cluVersionIndId);
        query.setParameter("currentTime", new Date());
        Clu clu = (Clu)query.getSingleResult();
        return clu;
	}

	@Override
	public VersionDisplayInfo getCurrentCluVersionInfo(String cluVersionIndId, String objectTypeURI) {
        Query query = em.createNamedQuery("Clu.findCurrentVersionInfo");
        query.setParameter("versionIndId", cluVersionIndId);
        query.setParameter("currentTime", new Date());
        VersionDisplayInfo versionDisplayInfo = (VersionDisplayInfo)query.getSingleResult();
        versionDisplayInfo.setObjectTypeURI(objectTypeURI);
        return versionDisplayInfo;
	}

	@Override
	public VersionDisplayInfo getCurrentVersionOnDate(String versionIndId,
			String objectTypeURI, Date date) {
        Query query = em.createNamedQuery("Clu.findCurrentVersionOnDate");
        query.setParameter("versionIndId", versionIndId);
        query.setParameter("date", date);
        VersionDisplayInfo versionDisplayInfo = (VersionDisplayInfo)query.getSingleResult();
        versionDisplayInfo.setObjectTypeURI(objectTypeURI);
        return versionDisplayInfo;
	}

	@Override
	public VersionDisplayInfo getFirstVersion(String versionIndId,
			String objectTypeURI) {
        Query query = em.createNamedQuery("Clu.findFirstVersion");
        query.setParameter("versionIndId", versionIndId);
        VersionDisplayInfo versionDisplayInfo = (VersionDisplayInfo)query.getSingleResult();
        versionDisplayInfo.setObjectTypeURI(objectTypeURI);
        return versionDisplayInfo;
	}

	@Override
	public VersionDisplayInfo getLatestVersion(String versionIndId,
			String objectTypeURI) {
        Query query = em.createNamedQuery("Clu.findLatestVersion");
        query.setParameter("versionIndId", versionIndId);
        VersionDisplayInfo versionDisplayInfo = (VersionDisplayInfo)query.getSingleResult();
        versionDisplayInfo.setObjectTypeURI(objectTypeURI);
        return versionDisplayInfo;
	}

	@Override
	public VersionDisplayInfo getVersionBySequenceNumber(String versionIndId,
			String objectTypeURI, Long sequenceNumber) {
        Query query = em.createNamedQuery("Clu.findVersionBySequence");
        query.setParameter("versionIndId", versionIndId);
        query.setParameter("sequenceNumber", sequenceNumber);
        VersionDisplayInfo versionDisplayInfo = (VersionDisplayInfo)query.getSingleResult();
        versionDisplayInfo.setObjectTypeURI(objectTypeURI);
        return versionDisplayInfo;
	}

	@Override
	public List<VersionDisplayInfo> getVersions(String versionIndId,
			String objectTypeURI) {
        Query query = em.createNamedQuery("Clu.findVersions");
        query.setParameter("versionIndId", versionIndId);
        List<VersionDisplayInfo> versionDisplayInfos = (List<VersionDisplayInfo>)query.getResultList();
        if(versionDisplayInfos==null){
        	versionDisplayInfos = Collections.emptyList();
        }
        for(VersionDisplayInfo versionDisplayInfo:versionDisplayInfos){
        	versionDisplayInfo.setObjectTypeURI(objectTypeURI);
        }
        return versionDisplayInfos;
	}

	@Override
	public List<VersionDisplayInfo> getVersionsInDateRange(String versionIndId,
			String objectTypeURI, Date from, Date to) {
		if(from==null&&to==null){
			throw new IllegalArgumentException("from and to dates can not both be null");
		}
		Query query;
		if(from==null){
			query = em.createNamedQuery("Clu.findVersionsBeforeDate");
	        query.setParameter("versionIndId", versionIndId);
	        query.setParameter("date", to);			
		}else if(to==null){
			query = em.createNamedQuery("Clu.findVersionsAfterDate");
	        query.setParameter("versionIndId", versionIndId);
	        query.setParameter("date", from);
		}else{
			query = em.createNamedQuery("Clu.findVersionsInDateRange");
	        query.setParameter("versionIndId", versionIndId);
	        query.setParameter("from", from);
	        query.setParameter("to", to);
		}
		
        List<VersionDisplayInfo> versionDisplayInfos = (List<VersionDisplayInfo>)query.getResultList();
        if(versionDisplayInfos==null){
        	versionDisplayInfos = Collections.emptyList();
        }
        for(VersionDisplayInfo versionDisplayInfo:versionDisplayInfos){
        	versionDisplayInfo.setObjectTypeURI(objectTypeURI);
        }
        return versionDisplayInfos;
	}

	@Override
	public List<CluPublication> getCluPublicationsByType(
			String luPublicationTypeKey) {
        Query query = em.createNamedQuery("CluPublication.findCluPublicationsByType");
        query.setParameter("luPublicationTypeKey", luPublicationTypeKey);
        List<CluPublication> cluPublications = query.getResultList();
        return cluPublications;
	}

	@Override
	public List<CluPublication> getCluPublicationsByCluId(String cluId) {
        Query query = em.createNamedQuery("CluPublication.findPublicationsByCluId");
        query.setParameter("cluId", cluId);
        List<CluPublication> cluPublications = query.getResultList();
        return cluPublications;
	}

	@Override
	public List<CluSet> getCluSetsByCluVersionIndId(List<String> cluVersionIndIds) {
        Query query = em.createNamedQuery("CluSet.findCluSetsByCluVersionIndIds");
        query.setParameter("cluVersionIndIds", cluVersionIndIds);
        List<CluSet> cluSetIds = query.getResultList();
        return cluSetIds;
	}

	@Override
	public List<CluSet> getAllDynamicCluSets() {
        Query query = em.createNamedQuery("CluSet.findAllDynamicCluSets");
        List<CluSet> cluSetIds = query.getResultList();
        return cluSetIds;
	}
	
}
