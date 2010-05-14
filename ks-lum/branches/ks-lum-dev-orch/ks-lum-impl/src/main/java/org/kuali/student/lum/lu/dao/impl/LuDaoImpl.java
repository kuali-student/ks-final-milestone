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
package org.kuali.student.lum.lu.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.student.core.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluCluRelation;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.LuDocumentRelation;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.Lui;
import org.kuali.student.lum.lu.entity.LuiLuiRelation;
import org.kuali.student.lum.lu.entity.ReqComponent;

public class LuDaoImpl extends AbstractSearchableCrudDaoImpl implements LuDao{
	
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
    public List<LuStatement> getLuStatementsForLuStatementType(String luStatementTypeKey) {
        Query query = em.createNamedQuery("LuStatement.getLuStatementsForLuStatementType");
        query.setParameter("luStatementTypeKey", luStatementTypeKey);
        @SuppressWarnings("unchecked")
        List<LuStatement> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<LuStatement> getLuStatements(List<String> luStatementIdList) {
        Query query = em.createNamedQuery("LuStatement.getLuStatements");
        query.setParameter("luStatementIdList", luStatementIdList);
        @SuppressWarnings("unchecked")
        List<LuStatement> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<ReqComponent> getReqComponentsByType(String reqComponentTypeKey) {
        Query query = em.createNamedQuery("ReqComponent.getReqComponentsByType");
        query.setParameter("reqComponentTypeKey", reqComponentTypeKey);
        @SuppressWarnings("unchecked")
        List<ReqComponent> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<ReqComponent> getReqComponents(List<String> reqComponentIdList) {
        Query query = em.createNamedQuery("ReqComponent.getReqComponents");
        query.setParameter("reqComponentIdList", reqComponentIdList);
        @SuppressWarnings("unchecked")
        List<ReqComponent> resultList = query.getResultList();
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
		Query query = em.createNamedQuery("LuiLuiRelation.getLuisByRelationType");
		query.setParameter("luiId", luiId);
		query.setParameter("luLuRelationTypeId", luLuRelationTypeId);

		@SuppressWarnings("unchecked")
		List<Lui> luis = query.getResultList();
		return luis;
	}

	@Override
	public List<String> getLuiIdsByRelationType(String relatedLuiId,
												String luLuRelationTypeId) {
		Query query = em.createNamedQuery("LuiLuiRelation.getLuiIdsByRelationType");
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
        Long valid = (Long)query.getSingleResult();
		return valid.intValue()>0;
	}

	@Override
	public List<LuiLuiRelation> getLuiLuiRelations(String luiId) {
		Query query = em.createNamedQuery("LuiLuiRelation.getLuiLuiRelationsByLuiId");
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
	public List<LuDocumentRelation> getLuDocRelationsByClu(String cluId) {
		Query query = em.createNamedQuery("LuDocumentRelation.getLuDocRelationsByClu");
        query.setParameter("cluId", cluId);
        @SuppressWarnings("unchecked")
		List<LuDocumentRelation> luDocRelations = query.getResultList();
        return luDocRelations;
	}

	@Override
	public List<LuDocumentRelation> getLuDocRelationsByDocument(
			String documentId) {
		Query query = em.createNamedQuery("LuDocumentRelation.getLuDocRelationsByDocument");
        query.setParameter("documentId", documentId);
        @SuppressWarnings("unchecked")
		List<LuDocumentRelation> luDocRelations = query.getResultList();
        return luDocRelations;
	}

	@Override
	public List<LuDocumentRelation> getLuDocRelationsByIdList(
			List<String> luDocRelationIds) {
		Query query = em.createNamedQuery("LuDocumentRelation.getLuDocRelationsByIdList");
        query.setParameter("luDocRelationIds", luDocRelationIds);
        @SuppressWarnings("unchecked")
		List<LuDocumentRelation> luDocRelations = query.getResultList();
        return luDocRelations;
	}

	@Override
	public List<LuDocumentRelation> getLuDocRelationsByType(
			String luDocRelationTypeId) {
		Query query = em.createNamedQuery("LuDocumentRelation.getLuDocRelationsByType");
        query.setParameter("luDocRelationTypeId", luDocRelationTypeId);
        @SuppressWarnings("unchecked")
		List<LuDocumentRelation> luDocRelations = query.getResultList();
        return luDocRelations;
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
		Query query = em.createNamedQuery("CluCluRelation.getRelatedCluIdsByCluId");
        query.setParameter("cluId", cluId);
        query.setParameter("luLuRelationTypeId", luLuRelationTypeId);
        @SuppressWarnings("unchecked")
		List<String> relatedCluIds = query.getResultList();
        return relatedCluIds;
	}

	@Override
	public List<Clu> getRelatedClusByCluId(String cluId,
			String luLuRelationTypeId) {
		Query query = em.createNamedQuery("CluCluRelation.getRelatedClusByCluId");
        query.setParameter("cluId", cluId);
        query.setParameter("luLuRelationTypeId", luLuRelationTypeId);
        @SuppressWarnings("unchecked")
		List<Clu> relatedClus = query.getResultList();
        return relatedClus;
	}

	@Override
	public List<String> getRelatedLuiIdsByLuiId(String luiId, String luLuRelationTypeId) {
		Query query = em.createNamedQuery("LuiLuiRelation.getRelatedLuiIdsByLuiId");
        query.setParameter("luiId", luiId);
        query.setParameter("luLuRelationTypeId", luLuRelationTypeId);
        @SuppressWarnings("unchecked")
		List<String> relatedLuiIds = query.getResultList();
        return relatedLuiIds;
	}

	@Override
	public List<Lui> getRelatedLuisByLuiId(String luiId, String luLuRelationTypeId) {
		Query query = em.createNamedQuery("LuiLuiRelation.getRelatedLuisByLuiId");
        query.setParameter("luiId", luiId);
        query.setParameter("luLuRelationTypeId", luLuRelationTypeId);
        @SuppressWarnings("unchecked")
		List<Lui> relatedLuis = query.getResultList();
        return relatedLuis;
	}

    @Override
    public List<LuStatement> getLuStatementsForClu(String cluId) {
        Query query = em.createNamedQuery("LuStatement.getLuStatementsForClu");
        query.setParameter("cluId", cluId);
        @SuppressWarnings("unchecked")
        List<LuStatement> resultList = query.getResultList();
        return resultList;
	}

	@Override
	public List<Clu> getClusByRelation(String parentCluId, String luLuRelationTypeKey) {
		Query query = em.createNamedQuery("Clu.getClusByRelation");
		query.setParameter("parentCluId", parentCluId);
		query.setParameter("luLuRelationTypeKey", luLuRelationTypeKey);
		@SuppressWarnings("unchecked")
		List<Clu> resultList = query.getResultList();
		return resultList;
	}

}
